package com.incra

/**
 * The <i>ActivityCategoryController</i> class...
 *
 * @author Jeffrey Risberg
 * @since 09/19/10
 */
class ActivityCategoryController {
	
	def scaffold = true
	
	def index = {
		redirect(action: list)
	}
}
