package com.incra

/**
 * @author Jeffrey Risberg
 * @since October 2010
 */
class UserController {

  static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

  static navigation = [
    [group: 'tabs', action:'search', order: 90],
    [action: 'register', order: 99, isVisible: { true }]
  ]

  def userService

  def index = {
    redirect(action: "list", params: params)
  }

  def list = {
    params.max = Math.min(params.max ? params.int('max') : 10, 100)

    [userInstanceList: User.list(params), userInstanceTotal: User.count()]
  }

  def create = {
    def userInstance = new User()
    userInstance.properties = params
    return [userInstance: userInstance]
  }

  def save = {
    def userInstance = new User(params)
    if (userInstance.save(flush: true)) {
      flash.message = "${message(code: 'default.created.message', args: [message(code: 'user.label', default: 'User'), userInstance.id])}"
      redirect(action: "show", id: userInstance.id)
    }
    else {
      render(view: "create", model: [userInstance: userInstance])
    }
  }

  def show = {
    def userInstance = User.get(params.id)
    if (!userInstance) {
      flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])}"
      redirect(action: "list")
    }
    else {
      [userInstance: userInstance]
    }
  }

  def edit = {
    def userInstance = User.get(params.id)
    if (!userInstance) {
      flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])}"
      redirect(action: "list")
    }
    else {
      return [userInstance: userInstance]
    }
  }

  def update = {
    def userInstance = User.get(params.id)
    if (userInstance) {
      if (params.version) {
        def version = params.version.toLong()
        if (userInstance.version > version) {

          userInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [
            message(code: 'user.label', default: 'User')]
          as Object[], "Another user has updated this User while you were editing")
          render(view: "edit", model: [userInstance: userInstance])
          return
        }
      }
      userInstance.properties = params
      if (!userInstance.hasErrors() && userInstance.save(flush: true)) {
        flash.message = "${message(code: 'default.updated.message', args: [message(code: 'user.label', default: 'User'), userInstance.id])}"
        redirect(action: "show", id: userInstance.id)
      }
      else {
        render(view: "edit", model: [userInstance: userInstance])
      }
    }
    else {
      flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])}"
      redirect(action: "list")
    }
  }

  def delete = {
    def userInstance = User.get(params.id)
    if (userInstance) {
      try {
        userInstance.delete(flush: true)
        flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'user.label', default: 'User'), params.id])}"
        redirect(action: "list")
      }
      catch (org.springframework.dao.DataIntegrityViolationException e) {
        flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'user.label', default: 'User'), params.id])}"
        redirect(action: "show", id: params.id)
      }
    }
    else {
      flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])}"
      redirect(action: "list")
    }
  }


  // show the profile for the current user
  def profile = {
    if (session.user) {
      def user = User.findById(session.user.id)

      return [ profile : user.profile, userId : user.userId ]
    } else {
      response.sendError(404)
    }
  }

  // the Game Plan is a screen that summarizes all activities for the current user
  def gamePlan = {
    if (session.user) {
      def user = User.findById(session.user.id)

      def resultList = userService.generateGamePlan(user)

      return [ user : user, profile : user.profile, resultList : resultList ]
    } else {
      response.sendError(404)
    }
  }

  // the Chart screen provides a chart
  def chart = {
    if (session.user) {
      def user = User.findById(session.user.id)
      println "showing chart for " + user

      def resultList = userService.generateChartData(user)

      def activitiesOnDay = [:]
      resultList.each { row ->
        activitiesOnDay[row[0]] = row[1]
      }

      [ user : user, profile : user.profile, activitiesOnDay : activitiesOnDay ]
    } else {
      response.sendError(404)
    }
  }

  // Prepare the activity type selector screen
  def selectActivityTypes = {
    if (session.user) {
      def user = User.findById(session.user.id)

      def activityTypes = userService.generateActivityTypes(user)

      [ user : user, profile : user.profile, activityTypes : activityTypes ]
    } else {
      response.sendError(404)
    }
  }

  def postActivityTypes = {
    if (session.user) {
      def user = User.findById(session.user.id)

      userService.postActivityTypes(user, params)

      redirect(action: gamePlan)
    } else {
      response.sendError(404)
    }
  }

  def cancelActivityTypes = { redirect(action: gamePlan) }

  /**
   * Start creating a new activity for the current user
   */
  def newActivity = {
    if (session.user) {
      def user = User.findById(session.user.id)
      def unitNameStr = ""

      // prepare drop-downs
      def ats = new ArrayList<ActivityType>()
      for (uat in user.activityTypes) {
        ats.add(uat.activityType);
      }
      if (ats.size() > 0)
        unitNameStr = ats[0].unitOfMeasure.name

      [ user : user, profile : user.profile, activityTypes : ats, unitName : unitNameStr ]
    } else {
      response.sendError(404)
    }
  }

  def postActivity = {
    if (session.user) {
      def user = User.findById(session.user.id)
      def activityType = ActivityType.findById(params.activityTypeId)

      def activity = new Activity(params)
      activity.user = session.user
      activity.activityType = activityType

      if (activity.save(flush:true)) {
        flash.message = "Great work"
        userService.resetChartDataCache(session.user)
        redirect(action: gamePlan)
      } else {
        flash.message = "Invalid amount"
        redirect(action: newActivity)
      }
    }
    else {
      response.sendError(404)
    }
  }

  def cancelActivity = { redirect(action: gamePlan) }

  def search = {
  }

  def results = {
    def profileProps = Profile.metaClass.properties*.name
    def profiles = Profile.withCriteria {
      "${params.queryType}" {

        params.each { field, value ->

          if (profileProps.grep(field) && value) {
            ilike(field, value)
          }
        }
      }
    }
    [ profiles : profiles ]
  }

  def register = { UserRegistrationCommand urc ->
    if (urc.hasErrors()) {
      return [ user : urc ]
    } else {
      def user = new User(urc.properties)
      user.profile = new Profile(urc.properties)
      if (user.save()) {
        flash.message = "Welcome aboard, ${urc.fullName ?: urc.userId}"
        redirect(uri: '/')
      } else {
        // maybe not unique userId?
        return [ user : urc ]
      }
    }
  }

  // Perform a login operation with the given userId string.  Verify password.
  // Login by setting the user into the session.
  def login = {
    if (params.userId) {
      def user = User.findByUserId(params.userId)
      if (user) {
        if (user.password.equals(params.password)) {
          userService.login(session, user)
          redirect(uri: '/')
        }
        else {
          flash.message = "password error"
          redirect(uri: '/')
        }
      }
      else {
        flash.message = "User not found"
        redirect(uri: '/')
      }
    }
  }

  // Perform a logout operation, by clearing the user from the session.
  def logout = {
    userService.logout(session)
    redirect(uri: '/')
  }
}
