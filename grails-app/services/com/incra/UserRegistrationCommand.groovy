package com.incra

class UserRegistrationCommand {
	
	String userId
	String password
	String passwordRepeat
	
	String fullName
	String email
	String timeZone
	String country
	byte[] photo
	
	static constraints = {
		userId(size: 3..20)
		
		// Ensure password does not match userid
		password(minSize: 8, blank: false,
		validator: { passwd, urc ->
			return passwd != urc.userId
		})
		passwordRepeat(nullable: false,
		validator: { passwd2, urc ->
			return passwd2 == urc.password
		})
		fullName(nullable: true)
		email(email: true, nullable: true)
		timeZone(nullable: true)
		country(nullable: true)
		photo(nullable: true)
	}
}

