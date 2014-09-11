package com.incra


/**
 * The <i>ActivityController</i> class provides the timeline/summary action as well as the usual scaffolding
 * 
 * @author Jeffrey Risberg
 * @since 09/19/10
 */
class ActivityController {

  def userService
  def activityService
  def scaffold = true

  static navigation = [
    [group: 'tabs', action: 'timeline', title: 'My Timeline', order: 1],
    [action: 'global', title: 'Everyone', order: 2],
  ]

  def index = { redirect(action: list) }

  def ajaxGetUomName = {
     def activityType = ActivityType.get(params.id)
    render activityType?.unitOfMeasure.name
  }

  def global = {
    def activities = Activity.findAll()
    [ activities : activities, activityCount : activities.size() ]
  }

  def timeline = {
    if (session.user) {
      def user = User.findById(session.user.id)

      [ user : user, profile : user.profile ]
    } else {
      flash.message = "Invalid user id"
      redirect(controller : 'user', action : list)
    }
  }
}
