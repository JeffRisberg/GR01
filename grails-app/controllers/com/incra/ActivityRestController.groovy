package com.incra

import grails.converters.JSON

class ActivityRestController {

  def list = {
    def activityList = Activity.list()
    withFormat {
      xml { [ activities: activityList ] }
      json { render activityList as JSON }
      html { [ activities: activityList ] }
    }
  }

  def show = {
    Activity activity = Activity.get(params.id)
    if (activity) {
      withFormat {
        xml { [ activity: activity ] }
        json { render activity as JSON }
        html { [ activity: activity ] }
      }
    }
    else {
      response.sendError(404)
    }
  }

  def save = {
    def xml = request.XML
    def activity = new Activity()
    activity.description = xml.description.text()
    activity.user = User.get(xml.user.@id.text())
    activity.activityType = ActivityType.get(xml.activityType.@id.text())
    activity.amount = 12
    activity.startDate = new Date()

    activity.validate()
    println activity.errors
    def markup
    if (activity.save()) {
      markup = { status("OK") }
    }
    else {
      markup = { status("FAIL") }
    }
    render contentType: "text/xml; charset=utf-8",
        markup
  }
}
