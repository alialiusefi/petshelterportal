package by.training.finaltask.parser;

import by.training.finaltask.action.Action;
import by.training.finaltask.entity.User;
import by.training.finaltask.exception.InvalidFormDataException;
import by.training.finaltask.service.UserServiceImpl;

import java.util.List;

/**
 * Parses Parameters from form to create User.
 */
public final class UserFormParser extends FormParser<User> {

    private static final int USERNAME = 0;
    private static final int PASSWORD = 1;
    private static final String USERNAME_REGEX = "^[a-zA-Z0-9]{4,16}$";
    private static final String PASSWORD_REGEX = "^[a-zA-Z0-9]{6,32}$";

    @Override
    public User parse(Action action, List<String> userParameters) throws InvalidFormDataException {
        if (!userParameters.isEmpty() && !userParameters.contains(null)
                && !userParameters.contains("")) {
            String username = userParameters.get(USERNAME);
            String password = userParameters.get(PASSWORD);
            if (!username.isEmpty() && username.matches(USERNAME_REGEX)) {
                if (!password.isEmpty() && password.matches(PASSWORD_REGEX)) {
                    return new User(
                            null,
                            username,
                            UserServiceImpl.md5(password),
                            null
                    );
                } else {
                    throw new InvalidFormDataException("incorrectPasswordFormat");
                }
            } else {
                throw new InvalidFormDataException("incorrectUsernameFormat");
            }
        }
        throw new InvalidFormDataException("fillAllFields");
    }

}



