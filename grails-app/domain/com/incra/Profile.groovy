package com.incra

/** 
 * The <i>Profile</i> domain class provides information about one user.  The most important 
 * aspect (for this example) is that it supports storing a photo.
 * 
 * @author Jeff Risberg
 * @since 09/19/10
 */
class Profile {

	static belongsTo = User

	String fullName
	String email
	String timeZone
	String country
	byte[] photo

	static constraints = {
		fullName(nullable: false)
		email(email: true, nullable: true)
		photo(nullable: true, maxSize: 1000000)
		country(nullable: true)
		timeZone(nullable: true)
	}

	String toString() {
		"Profile for ${fullName} (${id})"
	}
}
