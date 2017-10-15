package iris

import com.wit.iris.users.Role
import com.wit.iris.users.User
import com.wit.iris.users.UserRole

class BootStrap {

    def init = { servletContext ->

        Role role = new Role(authority: 'USER_ROLE').save(flush: true)
        User user = new User(username: "deangaffney", password: "password").save(flush: true)
        UserRole.create(user, role)

        UserRole.withSession{
            it.flush()
            it.clear()
        }
    }
    def destroy = {
    }
}
