package iris

import com.wit.iris.elastic.ElasticEndpoint
import com.wit.iris.schemas.Schema
import com.wit.iris.schemas.SchemaField
import com.wit.iris.users.Role
import com.wit.iris.users.User
import com.wit.iris.users.UserRole
import com.wit.iris.schemas.enums.FieldType

class BootStrap {

    def init = { servletContext ->

        environments {

            development {
                new ElasticEndpoint(name: "aws", url: "https://search-iris-ibwkuxcv4b2unly3c7d2d77v2a.eu-west-1.es.amazonaws.com/", active: true).save(flush: true)
                Role role = new Role(authority: 'USER_ROLE').save(flush: true)
                User user = new User(username: "admin", password: "password").save(flush: true)
                UserRole.create(user, role)

                new Schema(name: "pi_monitor", esIndex: "pi_monitor",
                        user: user, schemaFields: [new SchemaField(name: "roomTemp", fieldType: FieldType.DOUBLE.getValue())],
                        refreshInterval: 10000).save(flush: true)

                new Schema(name: "shakespeare", esIndex: "shakespeare",
                           user: user, schemaFields: [new SchemaField(name: "line_id", fieldType: FieldType.INT.getValue()),
                                                      new SchemaField(name: "line_number", fieldType: FieldType.STRING.getValue()),
                                                      new SchemaField(name: "play_name", fieldType: FieldType.STRING.getValue()),
                                                      new SchemaField(name: "speaker", fieldType: FieldType.STRING.getValue()),
                                                      new SchemaField(name: "speech_number", fieldType: FieldType.INT.getValue()),
                                                      new SchemaField(name: "text_entry", fieldType: FieldType.STRING.getValue()),
                                                      new SchemaField(name: "type", fieldType: FieldType.STRING.getValue())],
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
