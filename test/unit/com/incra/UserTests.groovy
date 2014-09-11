package com.incra

import grails.test.GrailsUnitTestCase;

class UserTests extends GrailsUnitTestCase {
	protected void setUp() {
		super.setUp()
		//PluginManagerHolder.pluginManager = [hasGrailsPlugin: { String name -> true }] as GrailsPluginManager
	}
	
	protected void tearDown() {
		super.tearDown()
		//PluginManagerHolder.pluginManager = null
	}
	
	void testConstraints() {
		def will = new User(userId: "william")
		mockForConstraintsTests(User, [ will ] )
		
		def testUser = new User()
		assertFalse testUser.validate()
		assertEquals "nullable", testUser.errors['userId']
		assertEquals "nullable", testUser.errors['password']
		
		testUser = new User(userId: "william", password: "william")
		assertFalse testUser.validate()
		assertEquals "unique", testUser.errors['userId']
		assertEquals 'minSize', testUser.errors['password']
		
		testUser = new User(userId: 'jeff', password: '12345678')
		assertTrue testUser.validate()
	}
}
