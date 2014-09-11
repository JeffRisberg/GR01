package com.incra

import grails.plugin.springcache.annotations.Cacheable

import org.hibernate.Criteria
import org.hibernate.Query
import org.hibernate.Session
import org.hibernate.criterion.Projections
import org.hibernate.criterion.Restrictions

/**
 * The <i>UserService</i> provides business logic around Users, such as lookup, registration, 
 * game plan generation, and processing changes to the selected set of activityTypes.
 *
 * @author Jeff Risberg
 * @since 09/20/10
 */
class UserService {

  static transactional = true

  def sessionFactory
  def springcacheService

  static final String CHARTDATA_CACHE_NAME = "chartDataCache"

  def getUser(params) {
    def user = User.findById(params.id)
    user
  }

  /**
   * Maintain login management information when logging in
   */
  def login(Object session, User user) {
    int loginCount = user.loginCount;

    user.loginCount = loginCount+1;
    user.lastLogin = new Date();

    session.user = user
  }

  /**
   * Maintain login management information when logging out
   */
  def logout(Object session) {
    session.user = null
  }

  /** 
   * Create the data set used on the game plan screen
   */
  def generateGamePlan(User user) {
    // Perform a reporting query
    def session = sessionFactory.getCurrentSession();
    Criteria crit = session.createCriteria(UserActivityType.class);

    crit.createAlias("activityType", "at", Criteria.LEFT_JOIN)
    crit.createAlias("at.activities", "a", Criteria.LEFT_JOIN)
    crit.createAlias("at.unitOfMeasure", "uom", Criteria.LEFT_JOIN)
    crit.add(Restrictions.eq("user", user));
    crit.add(Restrictions.eq("a.user", user));
    crit.setProjection(Projections.projectionList()
        .add(Projections.groupProperty("activityType"))
        .add(Projections.groupProperty("uom.name"))
        .add(Projections.count("a.activityType"))
        .add(Projections.sum("a.amount")));

    return crit.list()
  }

  /**
   * Create the data set used on the game plan screen
   */
  def generateChartData(User user) {
    println "generateChartData"
    springcacheService.doWithCache(CHARTDATA_CACHE_NAME, user.id, {
      // Perform a reporting query
      println "performing query"
      def session = sessionFactory.getCurrentSession();
      Criteria crit = session.createCriteria(UserActivityType.class);

      crit.createAlias("activityType", "at", Criteria.LEFT_JOIN)
      crit.createAlias("at.activities", "a", Criteria.LEFT_JOIN)
      crit.add(Restrictions.eq("user", user));
      crit.add(Restrictions.eq("a.user", user));
      crit.setProjection(Projections.projectionList()
          .add(Projections.groupProperty("activityType"))
          .add(Projections.count("a.activityType")));

      def result = crit.list()
      println result
      return result
    })
  }

  public resetChartDataCache(User user) {
    springcacheService.getOrCreateCache(CHARTDATA_CACHE_NAME).remove(user.id)
  }

  public flushChartDataCache() {
    springcacheService.getOrCreateCache(CHARTDATA_CACHE_NAME).flush()
  }

  /** 
   * Generate a list of the activityTypes and their current status 
   */
  @Cacheable("activityTypeCache")
  def generateActivityTypes(User user) {
    println "fetching ActivityTypes"
    Session session = sessionFactory.getCurrentSession()
    Query query = session.createQuery("select activityType.id from UserActivityType where user = :user")
    query.setParameter("user", user)
    List<Long> selectedActivityTypeIds = query.list()

    List<Object[]> list = new ArrayList()
    List<ActivityType> activityTypes = ActivityType.findAll()

    for (ActivityType at : activityTypes) {
      Object[] tuple = new Object[2]
      tuple[0] = at
      tuple[1] = selectedActivityTypeIds.contains(at.id)
      list.add(tuple)
    }
    list
  }

  /**
   * Process the list of activity types
   */
  def postActivityTypes(User user, Object params) {

    List<ActivityType> activityTypes = ActivityType.findAll()

    Session session = sessionFactory.getCurrentSession()
    Query query = session.createQuery("select activityType.id from UserActivityType where user = :user")
    query.setParameter("user", user)
    List<Long> selectedActivityTypeIds = query.list()

    for (ActivityType at : activityTypes) {
      Long atId = at.id;
      if (params.get("ActType_" + atId)) {
        if (selectedActivityTypeIds.contains(atId) == false) {
          UserActivityType uat = new UserActivityType()
          uat.activityType = at
          uat.effectivityStart = new Date()
          user.addToActivityTypes(uat)
        }
      }
      else {
        if (selectedActivityTypeIds.contains(atId) == true) {
          def uats = user.activityTypes
          for (UserActivityType uat : uats) {
            if (uat.activityType.id.equals(atId)) {
              user.removeFromActivityTypes(uat);
              break;
            }
          }
        }
      }
    }
  }
}
