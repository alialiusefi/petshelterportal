package by.training.finaltask.action;

import by.training.finaltask.dao.mysql.DAOEnum;
import by.training.finaltask.entity.Adoption;
import by.training.finaltask.entity.Pet;
import by.training.finaltask.entity.Role;
import by.training.finaltask.entity.User;
import by.training.finaltask.exception.InvalidFormDataException;
import by.training.finaltask.exception.PersistentException;
import by.training.finaltask.parser.AdoptionFormParser;
import by.training.finaltask.service.serviceinterface.AdoptionService;
import by.training.finaltask.service.serviceinterface.PetService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class EditAdoptionAction extends AuthorizedUserAction {

    private static final Logger LOGGER = LogManager.getLogger(EditAdoptionAction.class);
    private static final AdoptionFormParser formParser = new AdoptionFormParser();
    private static final String MESSAGE = "message";
    private static final String ADOPTIONID = "adoptionID";

    public EditAdoptionAction() {
        this.allowedRoles.add(Role.STAFF);
        this.allowedRoles.add(Role.GUEST);
    }

    @Override
    public Action.Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            User authUser = (User) session.getAttribute("authorizedUser");
            if (authUser != null && this.allowedRoles.contains(authUser.getUserRole())) {
                String adoptionIDParam = request.getParameter(ADOPTIONID);
                int adoptionID;
                if (adoptionIDParam != null) {
                    adoptionID = Integer.parseInt(adoptionIDParam);
                } else {
                    return new Forward(request.getHeader("referer"));
                }
                request.setAttribute(ADOPTIONID,adoptionID);
                AdoptionService service = (AdoptionService)
                        factory.createService(DAOEnum.ADOPTION);
                Adoption adoption = service.get(adoptionID);
                PetService petService = (PetService) factory.createService(DAOEnum.PET);
                Pet pet = petService.get(adoption.getPetID());
                request.setAttribute("pet",pet);
                request.setAttribute("adoption", adoption);
                List<String> adoptionParam = new ArrayList<>();
                addAdoptionParametersToList(request, adoption, adoptionParam);
                try {
                    Forward forward = new Forward("/home.jsp");
                    if(authUser.getUserRole() == Role.STAFF) {
                        forward.setForward("/adoptions/staff/findadoption.html");
                    }
                    if(authUser.getUserRole() == Role.GUEST)
                    {
                        forward.setForward("/adoptions/guest/myadoptions.html");
                    }
                    Adoption newAdoption = formParser.parse(this, adoptionParam);
                    newAdoption.setId(adoptionID);
                    service.updateAdoption(newAdoption);
                    forward.getAttributes().put(MESSAGE,"recordUpdatedSuccessfully");
                    return forward;
                } catch (InvalidFormDataException e) {
                    request.setAttribute(MESSAGE, e.getMessage());
                    return null;
                }
            }
            LOGGER.info(String.format("%s - attempted to access %s and was stopped due to not enough" +
                    "privileges", request.getRemoteAddr(),request.getRequestURI()));
        }
        LOGGER.info(String.format("%s - attempted to access %s and failed",
                request.getRemoteAddr(),request.getRequestURI()));
        throw new PersistentException("forbiddenAccess");
    }

    private void addAdoptionParametersToList(HttpServletRequest request,
                                             Adoption oldAdoption, List<String> parameters) {
        parameters.add(request.getParameter(ADOPTIONID));
        parameters.add(String.valueOf(oldAdoption.getPetID()));
        parameters.add(request.getParameter("adoptionStart"));
        String adoptionEndDate = request.getParameter("adoptionEnd");
        if (adoptionEndDate == null) {
            parameters.add("INDEFINITE");
        } else {
            parameters.add(adoptionEndDate);
        }
        User authUser = (User) request.getSession(false).getAttribute("authorizedUser");
        int userID = authUser.getId();
        parameters.add(String.valueOf(userID));
    }


}
