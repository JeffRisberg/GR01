
/**
 * The <i>LoggingFilters</i> is a simple version of request logging
 */
class LoggingFilters {

  def filters = {
    all(controller:'*', action:'*') {
      before = {
        println "--->" + controllerName + " " + actionName + " " + request.getMethod()
      }
      after = {
        println "<---" + controllerName + " " + actionName
      }
      afterView = {
      }
    }
  }
}
