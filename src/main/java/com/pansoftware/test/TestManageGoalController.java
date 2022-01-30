package com.pansoftware.test;

// @author Danilo D'Amico

import com.pansoftware.logic.ManageGoalController;
import com.pansoftware.logic.bean.UpdateStepsBean;
import com.pansoftware.logic.enumeration.GoalType;
import com.pansoftware.logic.exception.DatabaseException;
import com.pansoftware.logic.exception.EmptyResultSetException;
import com.pansoftware.logic.exception.InvalidDataException;
import com.pansoftware.logic.exception.UserNotFoundException;
import org.junit.Test;

import javax.security.auth.login.LoginException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestManageGoalController {

    @Test
    public void testUpdateStepsCompletedMoreThanMax() throws InvalidDataException, UserNotFoundException, SQLException, EmptyResultSetException, LoginException, DatabaseException {

        final String user = "leosciascia";
        final int id = 1;

        final UpdateStepsBean bean = new UpdateStepsBean();
        bean.setType(GoalType.GOAL);
        bean.setStepsCompleted(0);

        // goal that's already part of the database
        bean.setUpdateId(id);
        bean.setUpdateUser(user);

        ManageGoalController.updateSteps(bean);

        // The Goal has 4 steps. Let's put we've completed 5.

        bean.setStepsCompleted(5);
        ManageGoalController.updateSteps(bean);

        final int output = ManageGoalController.getGoal(user, id).getStepsCompleted();

        assertEquals(4, output);
    }

}
