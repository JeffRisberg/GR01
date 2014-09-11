package com.incra

/**
 * The <i>ImageController</i> class...
 * 
 * @author Jeff Risberg
 * @since 09/20/10
 */
class PhotoUploadCommand {
	Long userId
	byte[] photo
}

class ImageController {
	
	def upload = { PhotoUploadCommand puc ->
		def user = User.findById(puc.userId)
		if (user != null) {
			if (puc.photo.length < 1000000) {
				user.profile.photo = puc.photo
				redirect(controller: 'user', action: 'profile', id: puc.userId)
			}
			else {
				flash.message = "Image file too large"
				redirect(controller : 'image', action : form)
			}
		}
		else {
			flash.message = "Invalid user id"
			redirect(controller : 'user', action : form)
		}
	}
	
	def form = {
		// pass through to upload form
	}
	
	def view = {
		// path through to "view photo" page
	}
	
	def renderImage = {
		def user = User.findByUserId(params.id)
		if (user && user?.profile?.photo) {
			response.setContentLength(user.profile.photo.length)
			response.getOutputStream().write(user.profile.photo)
		} else {
			response.sendError(404)
		}
	}
}

