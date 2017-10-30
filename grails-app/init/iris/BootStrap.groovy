package iris

import com.wit.iris.elastic.ElasticEndpoint
import com.wit.iris.users.Role
import com.wit.iris.users.User
import com.wit.iris.users.UserRole

class BootStrap {

    def init = { servletContext ->

        environments {

            development {
                ElasticEndpoint endpoint = new ElasticEndpoint(name: "aws", url: "https://search-iris-ibwkuxcv4b2unly3c7d2d77v2a.eu-west-1.es.amazonaws.com", active: true).save(flush: true)
                println(endpoint.url)
                Role role = new Role(authority: 'USER_ROLE').save(flush: true)
                User user = new User(username: "admin", password: "password").save(flush: true)
                UserRole.create(user, role)

                UserRole.withSession{
                    it.flush()
                    it.clear()
                }
            }

        }

    }
    def destroy = {
    }
}
