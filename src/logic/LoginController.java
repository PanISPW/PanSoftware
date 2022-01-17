package logic;

import logic.bean.LoginBean;
import logic.dao.UserDao;
import logic.entity.User;
import logic.enumeration.UserRole;
import logic.exception.DatabaseException;
import logic.exception.UserNotFoundException;

import javax.security.auth.login.LoginException;
import java.sql.SQLException;

// @author Danilo D'Amico

public class LoginController {

    public UserRole loginUser(LoginBean bean) throws UserNotFoundException, SQLException, LoginException {
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
