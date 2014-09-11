package com.incra

import java.util.Date;

/**
 * The <i>ActivityCategory</i> domain class defines a collection of ActivityTypes.
 * 
 * Examples include: wellness, health
 * 
 * @author Jeff Risberg
 * @since 09/19/10
 */
class ActivityCategory {
	static final int AC_Physical = 1;
	static final int AC_Wellness = 2;
	static final int AC_FoodAndDrink = 3;

	String name
	Date dateCreated

	static constraints = {
		name(blank: false, unique:true)
		activityTypes()
		dateCreated(display: false)
	}

	static hasMany = [ activityTypes : ActivityType ]

	String toString() {
		"${name}"
	}
}
