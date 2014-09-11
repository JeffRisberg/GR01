package com.incra

import java.util.Date

/**
 * The <i>UserGroup</i> domain 
 * 
 * @author Jeff Risberg
 * @since 11/16/11
 */
class UserGroup {

    String name
    Date dateCreated

    static constraints = {
        dateCreated(display: false)
        members(display: false)
    }

    static hasMany = [members : User]

    String toString() {
        "$name}"
    }

    boolean equals(o) {
        if (this.is(o)) return true

        if (!(o instanceof UserGroup)) return false

        UserGroup user = (UserGroup) o

        // name provides uniqueness
        if (name != user.name) return false

        return true
    }

    int hashCode() {
        // name provides uniqueness
        return (name != null ? name.hashCode() : 0)
    }
}
