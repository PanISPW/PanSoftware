package logic.util;

import java.time.LocalDate;

import logic.enumeration.EventRequestState;
import logic.enumeration.EventType;
import logic.enumeration.ProductType;
import logic.enumeration.UserRole;

// @author Danilo D'Amico

public class DaoUtils {
	
	public static ProductType DatabaseIntToProductType(int databaseInt) {

		switch (databaseInt) {

		case 0:
			return ProductType.MAKEUP;

		case 1:
			return ProductType.FOOD;

		case 2:
			return ProductType.LIFESTYLE;
			
		case 3:
			return ProductType.OTHER;

		default:
			return ProductType.NOTSPECIFIED;
		}
	}
	
	public static int ProductTypeToDatabaseInt(ProductType type) {
		
		switch (type) {

		case MAKEUP:
			return 0;

		case FOOD:
			return 1;

		case LIFESTYLE:
			return 2;
			
		case OTHER:
			return 3;

		default:
			return 4;
		}
		
	}
	
	public static java.sql.Date LocalDateToSqlDateOrDefault(LocalDate localDate){
		java.sql.Date date;
		try {
			date = java.sql.Date.valueOf(localDate);
		} catch (NullPointerException e){
			return java.sql.Date.valueOf(DataValidation.setDefaultDate());
		}
		
		return date;
	}
	
	public static UserRole DatabaseIntToUserRole(int databaseInt) {
		
		switch (databaseInt) {

		case 1:
			return UserRole.ACTIVIST;

		case 2:
			return UserRole.BRANDMANAGER;

		default:
			return UserRole.USER;
		}
	}
	
	public static int UserRoleToDatabaseInt(UserRole role) {
		switch(role) {
		case ACTIVIST:
			return 1;
		case BRANDMANAGER:
			return 2;
		default:
			return 0;
		}
	}
	
	public static EventType DatabaseIntToEventType(int databaseInt) {
		switch(databaseInt) {
		case 1:
			return EventType.PRIVATE;
		
		default:
			return EventType.PUBLIC;
		}
	}
	
	public static int EventTypeToDatabaseInt(EventType type) {
		switch(type) {
		case PRIVATE:
			return 1;
		default:
			return 0;
		}
	}
	
	public static EventRequestState DatabaseIntToEventRequestState(int databaseInt) {
		switch (databaseInt) {
		
		case 0:
			return EventRequestState.PENDING;

		case 1:
			return EventRequestState.ACCEPTED;

		case 2:
			return EventRequestState.REJECTED;

		default:
			return EventRequestState.STARTING;
		}
	}
	
	public static int EventRequestStateToDatabaseInt(EventRequestState requestState) {
		switch (requestState) {
		
		case PENDING:
			return 0;

		case ACCEPTED:
			return 1;

		case REJECTED:
			return 2;

		default: // starting
			return 4;
		}
	}

}
