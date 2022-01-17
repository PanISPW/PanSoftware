package logic;

import logic.bean.LoginBean;
import logic.dao.UserDao;
import logic.entity.User;
import logic.enumeration.UserRole;
import logic.exception.DatabaseException;

import javax.security.auth.login.LoginException;

// @author Danilo D'Amico

public class LoginController {

    public UserRole loginUser(LoginBean bean) throws Exception {
        String user = bean.getUsername();
        String password = bean.getPassword();

        return UserDao.checkUserPassword(user, password);
    }

    public void signup(LoginBean bean) throws DatabaseException {

        User user = new User(bean.getUsername(), bean.getPassword(), bean.getEmail(), bean.getName(), bean.getSurname(), bean.getRole());
        try {
            user.save();
        } catch (Exception e) {
            throw new DatabaseException("signup failed");
        }
    }
}
