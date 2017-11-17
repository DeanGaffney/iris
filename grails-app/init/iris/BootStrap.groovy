package iris

import com.wit.iris.elastic.ElasticEndpoint
import com.wit.iris.schemas.Schema
import com.wit.iris.schemas.SchemaField
import com.wit.iris.users.Role
import com.wit.iris.users.User
import com.wit.iris.users.UserRole

class BootStrap {

    def init = { servletContext ->

        environments {

            development {
                new ElasticEndpoint(name: "aws", url: "https://search-iris-ibwkuxcv4b2unly3c7d2d77v2a.eu-west-1.es.amazonaws.com", active: true).save(flush: true)
                Role role = new Role(authority: 'USER_ROLE').save(flush: true)
                User user = new User(username: "admin", password: "password").save(flush: true)
                UserRole.create(user, role)
                new Schema(name: "pi_monitor", esIndex: "pi_monitor",
                        user: user, schemaFields: [new SchemaField(name: "roomTemp", fieldType: "double")],
                        refreshInterval: 10000).save(flush: true)
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
