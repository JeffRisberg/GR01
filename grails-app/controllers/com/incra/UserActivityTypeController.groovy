package com.incra

/**
 * The <i>UserActivityTypeController</i> class...
 *
 * @author Jeffrey Risberg
 * @since 09/19/10
 */
class UserActivityTypeController {
	
	def scaffold = true
	
	def index = {
		redirect(action: list)
	}
}
