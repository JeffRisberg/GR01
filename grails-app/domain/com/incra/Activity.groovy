package com.incra


/**
 * The <i>Activity</i> domain class records one chunk of activity by a user.  In this version, 
 * all activities are recorded by a startDate and an amount.
 * 
 * @author Jeff Risberg
 * @since 09/19/10
 */
class Activity {

  Date startDate
  int amount
  String description
  Date dateCreated
  Date lastUpdated

  static constraints = {
    user()
    activityType()
    startDate(nullable: false)
    amount(blank: false)
    description(blank: true)
    dateCreated(display: false)
  }

  static belongsTo = [ user : User, activityType : ActivityType ]

  String toString() {
    "${user.profile.fullName} : ${activityType.name} on ${startDate}"
  }
}
