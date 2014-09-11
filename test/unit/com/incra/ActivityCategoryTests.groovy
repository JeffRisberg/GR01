package com.incra

import grails.test.GrailsUnitTestCase;

class ActivityCategoryTests extends GrailsUnitTestCase {
	protected void setUp() {
		super.setUp()
	}
	
	protected void tearDown() {
		super.tearDown()
	}
	
	void testConstraints() {
		def cat1 = new ActivityCategory(name: "cat1")
		mockForConstraintsTests(ActivityCategory, [ cat1 ] )
		
		def testActCat = new ActivityCategory()
		assertFalse testActCat.validate()
		assertEquals "nullable", testActCat.errors['name']
		
		testActCat = new ActivityCategory(name: 'cat1')
		assertFalse testActCat.validate()
		assertEquals "unique", testActCat.errors['name']
		
		testActCat = new ActivityCategory(name: 'cat2')
		assertTrue testActCat.validate()
	}
}