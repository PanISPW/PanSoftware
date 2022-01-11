package logic;

import java.time.LocalDate;
import java.util.List;

import logic.barcode.BarcodeScanner;
import logic.bean.AnswerAdviceGoalBean;
import logic.bean.BarcodeBean;
import logic.bean.GoalBean;
import logic.bean.UpdateStepsBean;
import logic.dao.AdviceGoalDao;
import logic.dao.EventDao;
import logic.dao.EventGoalDao;
import logic.dao.GoalDao;
import logic.dao.UserDao;
import logic.entity.AdviceGoal;
import logic.entity.Event;
import logic.entity.EventGoal;
import logic.entity.Goal;
import logic.entity.User;
import logic.enumeration.EventRequestState;
import logic.enumeration.UserRole;
import logic.exception.NotEnoughPermissionsException;
import logic.exception.UserNotFoundException;
import logic.mail.SendMail;
import logic.util.Session;

// @author Danilo D'Amico

public class ManageGoalController {
	
	private static void addReminder(String email, LocalDate day) {
		SendMail.sendOnDay(email, day);
	}
	
	public void createGoal(GoalBean bean) throws Exception {
		User user = UserDao.getUser(Session.getSession().getUser());
		GoalFactory.getGoalFactory().createGoal(bean, user);
		
		if(bean.isReminder()) {
			addReminder(user.getEmail(), bean.getDeadline());
		}
	}
	
	public static void updateSteps(UpdateStepsBean bean) throws Exception {
		String username = Session.getSession().getUser();
		int stepsCompleted = bean.getStepsCompleted();
		int numberOfSteps;
		
		switch(bean.getType()) {
		case ADVICEGOAL:
			
			numberOfSteps = AdviceGoalDao.getAdviceGoal(username, bean.getId()).getNumberOfSteps();
			if( numberOfSteps < stepsCompleted) {
				stepsCompleted = numberOfSteps;
			}
			
			AdviceGoalDao.updateStepsAdviceGoal(stepsCompleted, bean.getId(), username);
			break;
		case EVENTGOAL:
			
			numberOfSteps = EventGoalDao.getEventGoal(username, bean.getId()).getNumberOfSteps();
			if( numberOfSteps < stepsCompleted) {
				stepsCompleted = numberOfSteps;
			}
			
			EventGoalDao.updateStepsEventGoal(stepsCompleted, bean.getId(), username);
			break;
		default:
			
			numberOfSteps = GoalDao.getGoal(username, bean.getId()).getNumberOfSteps();
			if( numberOfSteps < stepsCompleted) {
				stepsCompleted = numberOfSteps;
			}
			
			GoalDao.updateStepsGoal(stepsCompleted, bean.getId(), username);
		}
		
	}
	
	public static List<AdviceGoal> getAdviceGoalList() throws UserNotFoundException, Exception{
		return AdviceGoalDao.getAdviceGoalList(Session.getSession().getUser());
	}
	
	//
	public static List<AdviceGoal> getUnansweredMakeupAdvice() throws UserNotFoundException, Exception{
		return AdviceGoalDao.getUnansweredMakeupAdvice();
	}
	
	public static List<AdviceGoal> getUnansweredFoodAdvice() throws UserNotFoundException, Exception{
		return AdviceGoalDao.getUnansweredFoodAdvice();
	}
	
	public static List<AdviceGoal> getUnansweredLifestyleAdvice() throws UserNotFoundException, Exception{
		return AdviceGoalDao.getUnansweredLifestyleAdvice();
	}
	
	public static List<AdviceGoal> getUnansweredOtherAdvice() throws UserNotFoundException, Exception{
		return AdviceGoalDao.getUnansweredOtherAdvice();
	}
	
	public static List<Goal> getGoalList() throws UserNotFoundException, Exception{
		return GoalDao.getGoalList(Session.getSession().getUser());
	}
	
	public static List<EventGoal> getEventGoalList() throws UserNotFoundException, Exception{
		return EventGoalDao.getEventGoalList(Session.getSession().getUser());
	}
	
	public static List<Event> getEventList() throws UserNotFoundException, Exception{
		return EventDao.getEventList();
	}
	
	public static List<EventGoal> getPendingEventGoalList() throws UserNotFoundException, Exception{
		return EventGoalDao.getPendingEventGoalList(Session.getSession().getUser());
	}
	
	public static BarcodeBean getBarcode() {
		return BarcodeScanner.getBarcode();
	}
	
	public static void updateProductType(GoalBean bean, int id) throws Exception {
		AdviceGoalDao.updateProductTypeAdviceGoal(bean.getType(), id, Session.getSession().getUser());
	}
	
	public static void answerAdviceGoal(AnswerAdviceGoalBean bean) throws UserNotFoundException, Exception{
		UserRole role = UserDao.getUser(Session.getSession().getUser()).getRole();
		
		if(role.equals(UserRole.USER))
			throw new NotEnoughPermissionsException();
		
		AdviceGoalDao.answerAdviceGoal(bean.getId(), bean.getUser(), Session.getSession().getUser(), bean.getAnswer());
	}
	
	public static void insertBarcode(BarcodeBean bean) throws Exception {
		AdviceGoalDao.insertBarcode(bean.getBarcode(), bean.getId(), Session.getSession().getUser());
	}
	
	public static void joinEvent(GoalBean bean, int id) throws UserNotFoundException, Exception {
		User userEntity = UserDao.getUser(Session.getSession().getUser());
		Event eventEntity = EventDao.getEvent(bean.getEventId(), bean.getEventOrganizer());
		
		new EventGoal(bean.getName(), bean.getDescription(), bean.getNumberOfSteps(), bean.getStepsCompleted(), bean.getDeadline(), id, userEntity, eventEntity, EventRequestState.STARTING);
	}
	
	public static void acceptEventGoal(GoalBean bean) throws UserNotFoundException, Exception {
		
		if(!bean.getState().equals(EventRequestState.PENDING)) {
			throw new Exception("Selected goal not waiting for approval. Operation denied");
		}
		
		if(!Session.getSession().getUser().equals(bean.getEventOrganizer())) {
			throw new Exception("You do not have permission to update the state of this goal");
		}
		
		User userEntity = UserDao.getUser(bean.getUser());		
		Event eventEntity = EventDao.getEvent(bean.getEventId(), bean.getEventOrganizer());
		
		EventGoal goal = new EventGoal(bean.getName(), bean.getDescription(), bean.getNumberOfSteps(), bean.getStepsCompleted(), bean.getDeadline(), bean.getId(), userEntity, eventEntity, bean.getState());
		goal.acceptJoinRequest();
		
		if(goal.getState().equals(EventRequestState.ACCEPTED))
			EventGoalDao.acceptEventGoal(goal.getId(), goal.getUser().getUsername());

			
	}
	
	public static void rejectEventGoal(GoalBean bean) throws UserNotFoundException, Exception {
		
		if(!bean.getState().equals(EventRequestState.PENDING)) {
			throw new Exception("Selected goal not waiting for approval. Operation denied");
		}
		
		if(!Session.getSession().getUser().equals(bean.getEventOrganizer())) {
			throw new Exception("You do not have permission to update the state of this goal");
		}
		
		User userEntity = UserDao.getUser(bean.getUser());	
		Event eventEntity = EventDao.getEvent(bean.getEventId(), bean.getEventOrganizer());
		
		EventGoal goal = new EventGoal(bean.getName(), bean.getDescription(), bean.getNumberOfSteps(), bean.getStepsCompleted(), bean.getDeadline(), bean.getId(), userEntity, eventEntity, bean.getState());
		
		System.out.println("rejectEventGoal " + bean.getEventOrganizer() + " " + String.valueOf(bean.getEventId()));
		
		goal.rejectJoinRequest();
		
		if(goal.getState().equals(EventRequestState.REJECTED))
			EventGoalDao.rejectEventGoal(goal.getId(), goal.getUser().getUsername());
	}
	
	public static User getCurrentUser() throws UserNotFoundException, Exception {
		String user = Session.getSession().getUser();
		
		return UserDao.getUser(user);
	}
	

}
