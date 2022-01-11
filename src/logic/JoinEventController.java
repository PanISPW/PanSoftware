package logic;

import java.util.List;

import logic.bean.GoalBean;
import logic.dao.EventDao;
import logic.dao.EventGoalDao;
import logic.entity.Event;
import logic.entity.EventGoal;
import logic.enumeration.EventRequestState;
import logic.exception.UserNotFoundException;
import logic.mail.SendMail;
import logic.util.Session;

// @author Danilo D'Amico

// responsabile della persistenza
public class JoinEventController {
	
	public void sendNotificationEmail(EventRequestState state, String email) {
		
		String message;
		
		switch(state) {
		
		case PENDING:
			message = "There's a new goal waiting for approval";
		case REJECTED:
			message = "Your submission has been rejected";
		default:
			message = "Your submission has been approved";
		}
		
		SendMail.send(message, email);	
	}
	
	public List<EventGoal> getPendingEventGoalList() throws UserNotFoundException, Exception{
		return EventGoalDao.getPendingEventGoalList(Session.getSession().getUser());
	}
	
	public List<Event> getEventList() throws UserNotFoundException, Exception{
		return EventDao.getEventList();
	}
	
	public boolean acceptJoinRequest(GoalBean bean) throws UserNotFoundException, Exception {
		
		EventGoal eventGoal = EventGoalDao.getEventGoal(bean.getUser(), bean.getId());
		
		if(eventGoal.getOrganizer().getUsername() == Session.getSession().getUser() && eventGoal.getState() == EventRequestState.PENDING) {
			eventGoal.acceptJoinRequest();
			return true;
		} else {
			return false;
		}
	}
	
	public boolean rejectJoinRequest(GoalBean bean) throws UserNotFoundException, Exception {
		
		EventGoal eventGoal = EventGoalDao.getEventGoal(bean.getUser(), bean.getId());
		
		if(eventGoal.getOrganizer().getUsername() == Session.getSession().getUser() && eventGoal.getState() == EventRequestState.PENDING) {
			eventGoal.rejectJoinRequest();
			return true;
		} else {
			return false;
		}
	}
	
}
