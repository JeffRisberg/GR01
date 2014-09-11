package com.incra

import grails.test.GrailsUnitTestCase;

class UserIntegrationTests extends GrailsUnitTestCase {
	
	void testFirstSaveEver() {
		def user = new User(userId: 'joe', password: 'secret77')
		user.validate()
		
		assertNotNull user.save()
		assertNotNull user.id
		
		def foundUser = User.get(user.id)
		assertEquals 'joe', foundUser.userId
	}
	
	void testSaveAndUpdate() {
		def user = new User(userId: 'joe', password: 'secret77')
		assertNotNull user.save()
		
		def foundUser = User.get(user.id)
		foundUser.password = 'sesame77'
		foundUser.save()
		
		def editedUser = User.get(user.id)
		assertEquals 'sesame77', editedUser.password
	}
	
	void testSaveThenDelete() {	
		def user = new User(userId: 'joe', password: 'secret77')
		assertNotNull user.save()
		
		def foundUser = User.get(user.id)
		foundUser.delete()
		
		assertFalse User.exists(foundUser.id)
	}
	
	void testEvilSave() {
		def user = new User(userId: 'chuch_norris',	password: 'tiny')
		
		assertFalse user.validate()
		assertTrue user.hasErrors()
		def errors = user.errors
		
		assertEquals "minSize.notmet",
				errors.getFieldError("password").code
		assertEquals "tiny",
				errors.getFieldError("password").rejectedValue
		
		assertNull errors.getFieldError("userId")
	}
	
	void testEvilSaveCorrected() {
		def user = new User(userId: 'chuch_norris',	password: 'tiny')
		assertFalse(user.validate())
		assertTrue(user.hasErrors())
		
		user.password = "fistfist"
		
		assertTrue(user.validate())
		assertFalse(user.hasErrors())
	}
}
