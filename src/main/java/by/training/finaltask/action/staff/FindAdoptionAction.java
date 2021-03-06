package by.training.finaltask.action.staff;

import by.training.finaltask.action.AuthorizedUserAction;
import by.training.finaltask.action.Pagination;
import by.training.finaltask.dao.mysql.DAOEnum;
import by.training.finaltask.entity.*;
import by.training.finaltask.exception.PersistentException;
import by.training.finaltask.service.serviceinterface.AdoptionService;
import by.training.finaltask.service.serviceinterface.PetService;
import by.training.finaltask.service.serviceinterface.UserInfoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FindAdoptionAction extends AuthorizedUserAction {

    private static final Logger LOGGER = LogManager.getLogger(FindAdoptionAction.class);
    private static int ROWCOUNT = 5;


    public FindAdoptionAction() {
        this.allowedRoles.add(Role.STAFF);
    }


    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response)
            throws PersistentException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            User authUser = (User) session.getAttribute("authorizedUser");
            if (authUser != null && allowedRoles.contains(authUser.getUserRole())) {
                @SuppressWarnings("unchecked")
                List<Adoption> adoptions = (List<Adoption>) request.getAttribute("adoptionResults");
                if (adoptions == null) {
                    AdoptionService service = (AdoptionService) factory
                            .createService(DAOEnum.ADOPTION);
                    Pagination pagination = new Pagination(
                            service.getAllCount(),
                            ROWCOUNT, request.getParameter("page"));
                    request.setAttribute("amountOfPages", pagination.getAmountOfPages());
                    adoptions = service.getAll(pagination.getOffset(), ROWCOUNT);
                    request.setAttribute("paginationURL", "/adoptions/staff/findadoption.html");
                }
                List<Pet> pets = getPetForEveryAdoption(adoptions);
                List<UserInfo> userInfos = getUserInfoForEveryAdoption(adoptions);
                request.setAttribute("adoptionResults", adoptions);
                request.setAttribute("userInfoResults", userInfos);
                request.setAttribute("petResults", pets);
                return null;
            }
            Forward forward = new Forward("/login.html");
            forward.getAttributes().put("message", "pleaseLogIn");
            return forward;
        }
        LOGGER.info(String.format("%s - attempted to access %s and failed",
                request.getRemoteAddr(), request.getRequestURI()));
        throw new PersistentException("forbiddenAccess");
    }

    private List<Pet> getPetForEveryAdoption(List<Adoption> adoptions)
            throws PersistentException {
        List<Pet> pets = new ArrayList<>();
        Map<Integer, Pet> petMap = new HashMap<>();
        for (Adoption adoption : adoptions) {
            if (!petMap.containsKey(adoption.getPetID())) {
                PetService service = (PetService) factory.createService(DAOEnum.PET);
                Pet pet = service.get(adoption.getPetID());
                pets.add(pet);
                petMap.put(pet.getId(), pet);
                continue;
            }
            pets.add(petMap.get(adoption.getPetID()));
        }
        return pets;
    }

    private List<UserInfo> getUserInfoForEveryAdoption(List<Adoption> adoptions)
            throws PersistentException {
        List<UserInfo> userInfos = new ArrayList<>();
        Map<Integer, UserInfo> userInfoMap = new HashMap<>();
        for (Adoption adoption : adoptions) {
            if (!userInfoMap.containsKey(adoption.getUserID())) {
                UserInfoService service = (UserInfoService) factory.createService(DAOEnum.USERINFO);
                UserInfo userInfo = service.get(adoption.getPetID());
                userInfos.add(userInfo);
                userInfoMap.put(userInfo.getId(), userInfo);
                continue;
            }
            userInfos.add(userInfoMap.get(adoption.getUserID()));
        }
        return userInfos;
    }
}
