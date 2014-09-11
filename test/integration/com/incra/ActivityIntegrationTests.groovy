package com.incra

import grails.test.GrailsUnitTestCase;

class ActivityIntegrationTests extends GrailsUnitTestCase {
	
	def sessionFactory
	
	void testFirstSaveEver() {
		def user = User.get(2L)
		assertNotNull user
		
		def activityType = ActivityType.get(3L)
		assertNotNull activityType
		
		def activity = new Activity(activityType: activityType, user: user, startDate: new Date(), amount: 33, description: 'example')
		activity.validate()
		
		assertNotNull activity.save()
		assertNotNull activity.id
		
		def foundActivity = Activity.get(activity.id)
		assertEquals 33, foundActivity.amount
	}
	
	void testAssignActivityType() {
		def user = User.get(2L)
		assertNotNull user
		
		def activityType = ActivityType.get(3L)
		assertNotNull activityType
		
		def userActivityType = new UserActivityType(activityType: activityType, user: user, effectivityStart: new Date())
		userActivityType.validate()
		
		assertNotNull userActivityType.save()
	}
	
	void testGamePlan() {
		UserService userService = new UserService()
		userService.setSessionFactory(sessionFactory)
		
		def user = User.get(2L)
		assertNotNull user
		
		def activityType = ActivityType.get(3L)
		assertNotNull activityType
		
		def userActivityType = new UserActivityType(activityType: activityType, user: user, effectivityStart: new Date())
		userActivityType.validate()
		
		assertNotNull userActivityType.save()
		
		def activity = new Activity(activityType: activityType, user: user, startDate: new Date(), 
				amount: 33, description: 'example1')
		activity.validate()
		
		assertNotNull activity.save()
		
		activity = new Activity(activityType: activityType, user: user, startDate: new Date(), 
				amount: 33, description: 'example2')
		activity.validate()
		
		assertNotNull activity.save()
		
		// run the gamePlan method of the userService
		def result = userService.generateGamePlan(user);
		
		assertNotNull result
		assertEquals 1, result.size()
		
		Object[] firstResult = (Object[]) result.get(0)
		assertEquals 2, firstResult[2]
		assertEquals 66, firstResult[3]		
	}
}
