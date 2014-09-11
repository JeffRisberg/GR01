package com.incra

import org.codehaus.groovy.grails.commons.ConfigurationHolder

import com.incra.export.ExcelExporter

/**
 * The <i>UserReportController</i> is a test case for the Excel generation technology.
 * 
 * @author Jeffrey Risberg
 * @since October 2011
 */
class UserReportController {

  def index = {
    params.max = Math.min(params.max ? params.int('max') : 10, 100)

    if (params?.format && params.format != "html"){
      response.contentType = ConfigurationHolder.config.grails.mime.types[params.format]
      response.setHeader("Content-disposition", "attachment; filename=users.${params.extension}")

      List fields = [
        "userId",
        "password",
        "lastLogin"
      ]
      Map labels = [userId: "User Id", password: "Password", lastLogin: "When they last logged in"]

      // Formatter closure
      def upperCase = { domain, value -> return value.toUpperCase() }

      Map formatters = [userId: upperCase]
      Map parameters = [sheetName: "Cool People", "column.widths": [0.2, 0.3, 0.4], titles: [
          "Challenge Name or some other very long string",
          "Group Name or some other very long string",
          "Report Date or some other very long string"]
      ]

      ExcelExporter ee = new ExcelExporter()
      ee.labels = labels
      ee.formatters = formatters
      ee.parameters = parameters
      ee.exportData(response.outputStream, User.list(params), fields)
    }

    [userInstanceList: User.list(params), userInstanceTotal: User.count()]
  }
}
