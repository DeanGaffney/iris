package iris

import com.wit.iris.dashboards.Dashboard
import com.wit.iris.elastic.ElasticEndpoint
import com.wit.iris.revisions.Revision
import com.wit.iris.rules.Rule
import com.wit.iris.schemas.IrisSchema
import com.wit.iris.schemas.SchemaField
import com.wit.iris.users.Role
import com.wit.iris.users.User
import com.wit.iris.users.UserRole
import com.wit.iris.schemas.enums.FieldType
import org.grails.web.json.JSONObject

class BootStrap {

    def dashboardService

    def init = { servletContext ->

        environments {

            development {
                new ElasticEndpoint(name: "aws", url: "https://search-iris-ibwkuxcv4b2unly3c7d2d77v2a.eu-west-1.es.amazonaws.com/", active: true).save(flush: true)
                Role role = new Role(authority: 'USER_ROLE').save(flush: true)
                User user = new User(username: "admin", password: "password").save(flush: true)
                UserRole.create(user, role)

                new IrisSchema(name: "pi_monitor", esIndex: "pi_monitor",
                        user: user, schemaFields: [new SchemaField(name: "roomTemp", fieldType: FieldType.DOUBLE.getValue())],
                        refreshInterval: 10000).save(flush: true)

                new IrisSchema(name: "shakespeare", esIndex: "shakespeare",
                           user: user, schemaFields: [new SchemaField(name: "line_id", fieldType: FieldType.INT.getValue()),
                                                      new SchemaField(name: "line_number", fieldType: FieldType.STRING.getValue()),
                                                      new SchemaField(name: "play_name", fieldType: FieldType.STRING.getValue()),
                                                      new SchemaField(name: "speaker", fieldType: FieldType.STRING.getValue()),
                                                      new SchemaField(name: "speech_number", fieldType: FieldType.INT.getValue()),
                                                      new SchemaField(name: "text_entry", fieldType: FieldType.STRING.getValue()),
                                                      new SchemaField(name: "type", fieldType: FieldType.STRING.getValue())],
                         refreshInterval: 10000).save(flush: true)

                String nodeScript = "json.osName = json.osName.toUpperCase()\n" +
                                    "json.memFree = json.memFree / 1024 / 1024/ 1024\n" +
                                    "return json"

                Rule nodeRule = new Rule(script: nodeScript)

                new IrisSchema(name: "node_agent", esIndex: "node_agent",
                        user: user, schemaFields: [new SchemaField(name: "osName", fieldType: FieldType.STRING.getValue()),
                                                   new SchemaField(name: "uptime", fieldType: FieldType.DOUBLE.getValue()),
                                                   new SchemaField(name: "cpuSpeedGHZ", fieldType: FieldType.FLOAT.getValue()),
                                                   new SchemaField(name: "memTotal", fieldType: FieldType.LONG.getValue()),
                                                   new SchemaField(name: "memFree", fieldType: FieldType.LONG.getValue()),
                                                   new SchemaField(name: "memUsed", fieldType: FieldType.LONG.getValue()),
                                                   new SchemaField(name: "cpuCurrentLoad", fieldType: FieldType.DOUBLE.getValue())],
                        refreshInterval: 10000, rule: nodeRule).save(flush: true)

                JSONObject dashboardJson = new JSONObject("{\n" +
                        "    \"grid\": {\n" +
                        "        \"serializedData\": [\n" +
                        "            {\n" +
                        "                \"chartName\": \"Memory Used Bar\",\n" +
                        "                \"schemaId\": 3,\n" +
                        "                \"x\": 0,\n" +
                        "                \"width\": 3,\n" +
                        "                \"chartType\": \"Bar\",\n" +
                        "                \"y\": 0,\n" +
                        "                \"data\": {\n" +
                        "                    \"size\": 0,\n" +
                        "                    \"aggs\": {\n" +
                        "                        \"aggs_1\": {\n" +
                        "                            \"terms\": {\n" +
                        "                                \"field\": \"osName\"\n" +
                        "                            },\n" +
                        "                            \"aggs\": {\n" +
                        "                                \"aggs_2\": {\n" +
                        "                                    \"max\": {\n" +
                        "                                        \"field\": \"memUsed\"\n" +
                        "                                    }\n" +
                        "                                }\n" +
                        "                            }\n" +
                        "                        }\n" +
                        "                    }\n" +
                        "                },\n" +
                        "                \"id\": \"widget-1516647373575\",\n" +
                        "                \"height\": 5\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"chartName\": \"Memory Used Bubble\",\n" +
                        "                \"schemaId\": 3,\n" +
                        "                \"x\": 3,\n" +
                        "                \"width\": 3,\n" +
                        "                \"chartType\": \"Bubble\",\n" +
                        "                \"y\": 0,\n" +
                        "                \"data\": {\n" +
                        "                    \"size\": 0,\n" +
                        "                    \"aggs\": {\n" +
                        "                        \"aggs_1\": {\n" +
                        "                            \"terms\": {\n" +
                        "                                \"field\": \"osName\"\n" +
                        "                            },\n" +
                        "                            \"aggs\": {\n" +
                        "                                \"aggs_2\": {\n" +
                        "                                    \"max\": {\n" +
                        "                                        \"field\": \"memUsed\"\n" +
                        "                                    }\n" +
                        "                                }\n" +
                        "                            }\n" +
                        "                        }\n" +
                        "                    }\n" +
                        "                },\n" +
                        "                \"id\": \"widget-1516647404135\",\n" +
                        "                \"height\": 5\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"chartName\": \"Memory Used Pie\",\n" +
                        "                \"schemaId\": 3,\n" +
                        "                \"x\": 6,\n" +
                        "                \"width\": 3,\n" +
                        "                \"chartType\": \"Pie\",\n" +
                        "                \"y\": 0,\n" +
                        "                \"data\": {\n" +
                        "                    \"size\": 0,\n" +
                        "                    \"aggs\": {\n" +
                        "                        \"aggs_1\": {\n" +
                        "                            \"terms\": {\n" +
                        "                                \"field\": \"osName\"\n" +
                        "                            },\n" +
                        "                            \"aggs\": {\n" +
                        "                                \"aggs_2\": {\n" +
                        "                                    \"max\": {\n" +
                        "                                        \"field\": \"memUsed\"\n" +
                        "                                    }\n" +
                        "                                }\n" +
                        "                            }\n" +
                        "                        }\n" +
                        "                    }\n" +
                        "                },\n" +
                        "                \"id\": \"widget-1516647421899\",\n" +
                        "                \"height\": 5\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"chartName\": \"Memory Used Line\",\n" +
                        "                \"schemaId\": 3,\n" +
                        "                \"x\": 9,\n" +
                        "                \"width\": 3,\n" +
                        "                \"chartType\": \"Line\",\n" +
                        "                \"y\": 0,\n" +
                        "                \"data\": {\n" +
                        "                    \"size\": 0,\n" +
                        "                    \"aggs\": {\n" +
                        "                        \"aggs_1\": {\n" +
                        "                            \"terms\": {\n" +
                        "                                \"field\": \"osName\"\n" +
                        "                            },\n" +
                        "                            \"aggs\": {\n" +
                        "                                \"aggs_2\": {\n" +
                        "                                    \"max\": {\n" +
                        "                                        \"field\": \"memUsed\"\n" +
                        "                                    }\n" +
                        "                                }\n" +
                        "                            }\n" +
                        "                        }\n" +
                        "                    }\n" +
                        "                },\n" +
                        "                \"id\": \"widget-1516647434927\",\n" +
                        "                \"height\": 5\n" +
                        "            }\n" +
                        "        ]\n" +
                        "    },\n" +
                        "    \"name\": \"node\"\n" +
                        "}")

                Dashboard dashboard = dashboardService.createDashboard(dashboardJson)
                dashboard.setUser(user)
                dashboard.setRevision(new Revision(comment: "master"))
                dashboard.save(flush: true)

                UserRole.withSession{
                    it.flush()
                    it.clear()
                }

            }
            production {
                Role role = new Role([authority: 'USER_ROLE']).save(flush: true)
                User user = new User([username: "admin", password: "password"]).save(flush: true)
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
