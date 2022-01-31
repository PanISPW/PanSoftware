package com.pansoftware.test;

// @author Danilo D'Amico

import com.pansoftware.logic.ManageGoalController;
import com.pansoftware.logic.bean.UpdateStepsBean;
import com.pansoftware.logic.enumeration.GoalType;
import com.pansoftware.logic.exception.EmptyResultSetException;
import com.pansoftware.logic.exception.InvalidDataException;
import com.pansoftware.logic.exception.UserNotFoundException;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestManageGoalController {

    @Test
    public void testUpdateStepsCompletedMoreThanMax() throws InvalidDataException, UserNotFoundException, SQLException, EmptyResultSetException {

        // goal that's already part of the database
        final String user = "leosciascia";
        final int id = 1;

        UpdateStepsBean bean = new UpdateStepsBean();
        bean.setType(GoalType.GOAL);

        bean.setUpdateId(id);
        bean.setUpdateUser(user);

        // The Goal has 6 steps. Let's put we've completed 7.

        bean.setStepsCompleted(7);
        ManageGoalController.updateSteps(bean);

        final int output = ManageGoalController.getGoal(user, id).getStepsCompleted();

        assertEquals(6, output);
    }

}
