package com.pansoftware.logic;

import com.pansoftware.logic.bean.EventGoalBean;
import com.pansoftware.logic.dao.EventGoalDao;
import com.pansoftware.logic.entity.EventGoal;
import com.pansoftware.logic.enumeration.EventRequestState;
import com.pansoftware.logic.exception.EmptyResultSetException;
import com.pansoftware.logic.exception.NoTransitionException;
import com.pansoftware.logic.mail.SendMail;
import com.pansoftware.logic.util.Session;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

// @author Danilo D'Amico

// responsabile della persistenza
public class JoinEventController {

    public void sendNotificationEmail(final EventRequestState state, final String email) {

        final String message = switch (state) {
            case PENDING -> "There's a new goal waiting for approval";
            case REJECTED -> "Your submission has been rejected";
            default -> "Your submission has been approved";
        };

        SendMail.send(message, email);
    }

    public static List<EventGoal> getPendingEventGoalList() throws EmptyResultSetException, SQLException {
        return EventGoalDao.getPendingEventGoalList(Session.getSession().getUser());
    }

    public static void acceptJoinRequest(final EventGoalBean bean) throws EmptyResultSetException, NoTransitionException, SQLException {

        final EventGoal eventGoal = EventGoalDao.getEventGoal(bean.getUser(), bean.getId());

        if (Objects.equals(eventGoal.getEvent().getUser().getUsername(), Session.getSession().getUser()) && eventGoal.getState() == EventRequestState.PENDING) {
            eventGoal.acceptJoinRequest();
        } else {
            throw new NoTransitionException("It is impossible to accept this Join Request");
        }
    }

    public static void rejectJoinRequest(final EventGoalBean bean) throws EmptyResultSetException, NoTransitionException, SQLException {

        final EventGoal eventGoal = EventGoalDao.getEventGoal(bean.getUser(), bean.getId());

        if (Objects.equals(eventGoal.getEvent().getUser().getUsername(), Session.getSession().getUser()) && eventGoal.getState() == EventRequestState.PENDING) {
            eventGoal.rejectJoinRequest();
        } else {
            throw new NoTransitionException("It is impossible to reject this Join Request");
        }

    }

}
