package com.pansoftware.logic;

import com.pansoftware.logic.bean.GoalBean;
import com.pansoftware.logic.dao.EventDao;
import com.pansoftware.logic.dao.EventGoalDao;
import com.pansoftware.logic.entity.Event;
import com.pansoftware.logic.entity.EventGoal;
import com.pansoftware.logic.enumeration.EventRequestState;
import com.pansoftware.logic.exception.DatabaseException;
import com.pansoftware.logic.exception.EmptyResultSetException;
import com.pansoftware.logic.exception.NoTransitionException;
import com.pansoftware.logic.exception.UserNotFoundException;
import com.pansoftware.logic.mail.SendMail;
import com.pansoftware.logic.util.Session;

import javax.security.auth.login.LoginException;
import java.util.List;

// @author Danilo D'Amico

// responsabile della persistenza
public class JoinEventController {

    public void sendNotificationEmail(EventRequestState state, String email) {

        String message;

        switch (state) {

            case PENDING:
                message = "There's a new goal waiting for approval";
                break;

            case REJECTED:
                message = "Your submission has been rejected";
                break;

            default:
                message = "Your submission has been approved";
        }

        SendMail.send(message, email);
    }

    public List<EventGoal> getPendingEventGoalList() throws UserNotFoundException, EmptyResultSetException, LoginException, DatabaseException {
        return EventGoalDao.getPendingEventGoalList(Session.getSession().getUser());
    }

    public List<Event> getEventList() throws UserNotFoundException, EmptyResultSetException, LoginException, DatabaseException {
        return EventDao.getEventList();
    }

    public boolean acceptJoinRequest(GoalBean bean) throws UserNotFoundException, EmptyResultSetException, LoginException, DatabaseException, NoTransitionException {

        EventGoal eventGoal = EventGoalDao.getEventGoal(bean.getUser(), bean.getId());

        if (eventGoal.getOrganizer().getUsername() == Session.getSession().getUser() && eventGoal.getState() == EventRequestState.PENDING) {
            eventGoal.acceptJoinRequest();
            return true;
        } else {
            return false;
        }
    }

    public boolean rejectJoinRequest(GoalBean bean) throws UserNotFoundException, EmptyResultSetException, LoginException, DatabaseException, NoTransitionException {

        EventGoal eventGoal = EventGoalDao.getEventGoal(bean.getUser(), bean.getId());

        if (eventGoal.getOrganizer().getUsername() == Session.getSession().getUser() && eventGoal.getState() == EventRequestState.PENDING) {
            eventGoal.rejectJoinRequest();
            return true;
        } else {
            return false;
        }
    }

}
