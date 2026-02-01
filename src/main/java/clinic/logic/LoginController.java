package clinic.logics;

import clinic.data.DataStore;
import clinic.model.Doctor;
import clinic.exceptions.LoginException;
import clinic.config.ConfigLoader;


public class LoginController {

    public static Object authenticate(String role, String username, String password) throws LoginException {
        if (role == null || role.isEmpty()) {
            throw new LoginException("Role must be selected.");
        }
        if (username == null || username.isEmpty()) {
            throw new LoginException("Username cannot be empty.");
        }
        if (password == null || password.isEmpty()) {
            throw new LoginException("Password cannot be empty.");
        }

        if ("Admin".equals(role)) {
            if (!username.equals(ConfigLoader.getAdminUsername()) ||
                    !password.equals(ConfigLoader.getAdminPassword())) {
                throw new LoginException("Invalid admin credentials.");
            }
            return "Admin"; // return a marker for admin success
        } else if ("Doctor".equals(role)) {
            for (Doctor d : DataStore.doctors) {
                if (d.login(username, password)) {
                    return d; // return the Doctor object on success
                }
            }
            throw new LoginException("Invalid doctor credentials.");
        }

        throw new LoginException("Unknown role.");
    }
}

