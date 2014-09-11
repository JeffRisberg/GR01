package com.incra

import java.util.Date;

/**
 * The <i>ActivityType</i> domain class defines a type of activity, such as "Treadmill", 
 * or "Bicycle".
 * 
 * @author Jeff Risberg
 * @since 09/19/10
 */
class ActivityType {

	String name
	UnitOfMeasure unitOfMeasure
	Date dateCreated
	Date lastUpdated

	static constraints = {
		activityCategory()
		name(blank: false, unique: true)
		dateCreated(display: false)
	}

	static belongsTo = [ activityCategory : ActivityCategory ]

	static hasMany = [ activities : Activity ]

	String toString() {
		"${name}"
	}
}
