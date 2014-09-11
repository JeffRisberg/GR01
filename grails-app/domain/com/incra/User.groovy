package com.incra

import java.util.Date

/**
 * The <i>User</i> domain class defines one user, including the link to the user's profile.  The User
 * class also maintains a lastLogin and a loginCount.
 * 
 * @author Jeff Risberg
 * @since 09/18/10
 */
class User {

	String userId
	String password
	Profile profile
	Date lastLogin
	int loginCount = 0
	Date dateCreated

	static constraints = {
		userId(size: 3..20, unique: true)
		password(minSize: 8)
		lastLogin(editable: false, nullable: true)
		loginCount(editable: false)
		dateCreated(display: false)
		activities(display: false)
		activityTypes(display: false)
		profile(nullable: true)
	}

	static mapping = { profile lazy:false }

	static hasMany = [activityTypes : UserActivityType, activities : Activity]

	String toString() {
		"${profile.fullName}"
	}

	boolean equals(o) {
		if (this.is(o)) return true

		if (!(o instanceof User)) return false

		User user = (User) o

		// userId provides uniqueness
		if (userId != user.userId) return false

		return true
	}

	int hashCode() {
		// userId provides uniqueness
		return (userId != null ? userId.hashCode() : 0)
	}
}
