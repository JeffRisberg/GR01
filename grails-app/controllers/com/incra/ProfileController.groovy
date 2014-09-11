package com.incra

class ProfileController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [profileInstanceList: Profile.list(params), profileInstanceTotal: Profile.count()]
    }

    def create = {
        def profileInstance = new Profile()
        profileInstance.properties = params
        return [profileInstance: profileInstance]
    }

    def save = {
        def profileInstance = new Profile(params)
        if (profileInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'profile.label', default: 'Profile'), profileInstance.id])}"
            redirect(action: "show", id: profileInstance.id)
        }
        else {
            render(view: "create", model: [profileInstance: profileInstance])
        }
    }

    def show = {
        def profileInstance = Profile.get(params.id)
        if (!profileInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'profile.label', default: 'Profile'), params.id])}"
            redirect(action: "list")
        }
        else {
            [profileInstance: profileInstance]
        }
    }

    def edit = {
        def profileInstance = Profile.get(params.id)
        if (!profileInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'profile.label', default: 'Profile'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [profileInstance: profileInstance]
        }
    }

    def update = {
        def profileInstance = Profile.get(params.id)
        if (profileInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (profileInstance.version > version) {
                    
                    profileInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'profile.label', default: 'Profile')] as Object[], "Another user has updated this Profile while you were editing")
                    render(view: "edit", model: [profileInstance: profileInstance])
                    return
                }
            }
            profileInstance.properties = params
            if (!profileInstance.hasErrors() && profileInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'profile.label', default: 'Profile'), profileInstance.id])}"
                redirect(action: "show", id: profileInstance.id)
            }
            else {
                render(view: "edit", model: [profileInstance: profileInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'profile.label', default: 'Profile'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def profileInstance = Profile.get(params.id)
        if (profileInstance) {
            try {
                profileInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'profile.label', default: 'Profile'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'profile.label', default: 'Profile'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'profile.label', default: 'Profile'), params.id])}"
            redirect(action: "list")
        }
    }
}
