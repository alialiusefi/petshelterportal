package by.training.finaltask.action;

import by.training.finaltask.dao.mysql.DAOEnum;
import by.training.finaltask.entity.Adoption;
import by.training.finaltask.entity.Role;
import by.training.finaltask.entity.User;
import by.training.finaltask.exception.InvalidFormDataException;
import by.training.finaltask.exception.PersistentException;
import by.training.finaltask.parser.AdoptionFormParser;
import by.training.finaltask.service.serviceinterface.AdoptionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class FindAdoptionBetweenDatesAction extends AuthorizedUserAction {

    private static final Logger LOGGER = LogManager.getLogger(FindAdoptionBetweenDatesAction.class);
    private static int ROWCOUNT = 5;
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String DATEFROM = "dateFrom";
    private static final String DATETO = "dateTo";

    public FindAdoptionBetweenDatesAction() {
        this.allowedRoles.add(Role.GUEST);
        this.allowedRoles.add(Role.STAFF);
    }


    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            User authUser = (User) session.getAttribute("authorizedUser");
            if (authUser != null && this.allowedRoles.contains(authUser.getUserRole())) {
                String adoptionStartParam = request.getParameter(DATEFROM);
                String adoptionEndParam = request.getParameter(DATETO);
                if (adoptionStartParam == null && adoptionEndParam == null) {
                    adoptionStartParam = (String) session.getAttribute(DATEFROM);
                    adoptionEndParam = (String) session.getAttribute(DATETO);
                } else {
                    session.setAttribute(DATEFROM, adoptionStartParam);
                    session.setAttribute(DATETO, adoptionEndParam);
                }
                try {
                    GregorianCalendar start =
                            AdoptionFormParser.parseDate(DATE_FORMAT, adoptionStartParam);
                    GregorianCalendar end =
                            AdoptionFormParser.parseDate(DATE_FORMAT, adoptionEndParam);
                    AdoptionService service = (AdoptionService)
                            factory.createService(DAOEnum.ADOPTION);
                    Forward forward = new Forward(request.getHeader("referer"));
                    List<Adoption> adoptions = new ArrayList<>();
                    if (authUser.getUserRole() == Role.GUEST) {
                        Pagination pagination = new Pagination(
                                service.getCountBetweenDatesCurrentUser(
                                        authUser.getId(), start, end), ROWCOUNT,
                                request.getParameter("page"));
                        forward.getAttributes().put("amountOfPages", pagination.getAmountOfPages());
                        adoptions = service.getAllBetweenDatesCurrentUser(authUser.getId(),
                                start, end, pagination.getOffset(), ROWCOUNT);
                    }
                    if (authUser.getUserRole() == Role.STAFF) {
                        Pagination pagination = new Pagination(service.getCountBetweenDates(start, end),
                                ROWCOUNT, request.getParameter("page"));
                        forward.getAttributes().put("amountOfPages", pagination.getAmountOfPages());
                        adoptions = service.getAllBetweenDates(start, end, pagination.getOffset(), ROWCOUNT);
                    }
                    forward.getAttributes().put("paginationURL", "/adoptions/staff/findadoptionbetweendates.html");
                    forward.getAttributes().put("adoptionResults", adoptions);
                    return forward;
                } catch (InvalidFormDataException e) {
                    Forward forward = new Forward(request.getHeader("referer"));
                    forward.getAttributes().put("message", e.getMessage());
                    return forward;
                }
            }
            LOGGER.info(String.format("%s - attempted to access %s and stopped due to not enough" +
                    "privileges", request.getRemoteAddr(),request.getRequestURI()));
        }
        LOGGER.info(String.format("%s - attempted to access %s and failed",
                request.getRemoteAddr(),request.getRequestURI()));
        throw new PersistentException("forbiddenAccess");
    }
}
