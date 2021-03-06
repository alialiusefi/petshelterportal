package by.training.finaltask.action.guest;

import by.training.finaltask.action.AuthorizedUserAction;
import by.training.finaltask.action.Pagination;
import by.training.finaltask.dao.mysql.DAOEnum;
import by.training.finaltask.entity.Adoption;
import by.training.finaltask.entity.Pet;
import by.training.finaltask.entity.Role;
import by.training.finaltask.entity.User;
import by.training.finaltask.exception.PersistentException;
import by.training.finaltask.service.serviceinterface.AdoptionService;
import by.training.finaltask.service.serviceinterface.PetService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyAdoptionsAction extends AuthorizedUserAction {

    private static final Logger LOGGER = LogManager.getLogger(MyAdoptionsAction.class);
    private static final int ROWCOUNT = 5;

    public MyAdoptionsAction() {
        this.allowedRoles.add(Role.GUEST);
    }

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            User authUser = (User) session.getAttribute("authorizedUser");
            if (authUser != null && allowedRoles.contains(authUser.getUserRole())) {
                @SuppressWarnings("unchecked")
                List<Adoption> adoptions = (List<Adoption>) request.getAttribute("adoptionResults");
                if (adoptions == null) {
                    AdoptionService service = (AdoptionService) factory
                            .createService(DAOEnum.ADOPTION);
                    Pagination pagination = new Pagination(service.getAllCountCurrentUser(
                            authUser.getId()), ROWCOUNT, request.getParameter("page"));
                    request.setAttribute("amountOfPages", pagination.getAmountOfPages());
                    adoptions = service.getAllCurrentUser(authUser.getId(), pagination.getOffset(), ROWCOUNT);
                    request.setAttribute("paginationURL", "/adoptions/guest/myadoptions.html");
                }
                List<Pet> pets = getPetForEveryAdoption(adoptions);
                request.setAttribute("adoptionResults", adoptions);
                request.setAttribute("petResults", pets);
                return null;
            }

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

}
