package com.incra

import java.util.Date;

/**
 * The <i>UserActivityType</i> domain class keeps track of what activityTypes a user has signed up for, and when.
 * 
 * @author Jeff Risberg
 * @since 09/19/10
 */
class UserActivityType {
	
	ActivityType activityType
	Date effectivityStart
	Date effectivityEnd
	
	static constraints = {
		user(nullable: true)
		activityType()
		effectivityStart()
		effectivityEnd(nullable: true)
	}
	
	static belongsTo = [ user : User ]
	
	String toString() {
		if (user) {
			"${user.profile.fullName} : ${activityType.name}"
		} else {
			"no user : ${activityType.name}"
		}
	}
}
