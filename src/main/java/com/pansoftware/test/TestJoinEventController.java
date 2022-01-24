package com.pansoftware.test;

// @author Danilo D'Amico

// testing resilience to event join approval by the wrong user

import com.pansoftware.logic.JoinEventController;
import com.pansoftware.logic.bean.EventGoalBean;
import com.pansoftware.logic.exception.EmptyResultSetException;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestJoinEventController {

    @Test
    public void testAcceptJoinRequestNonExistingGoal() {

        assertThrows(EmptyResultSetException.class, () -> {

            EventGoalBean bean = new EventGoalBean();
            bean.setEventId(4);
            bean.setUser("Alcibiade");

            JoinEventController.acceptJoinRequest(bean);
        });
    }
}
