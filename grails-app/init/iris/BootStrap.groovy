package iris

import com.wit.iris.auth.AuthenticationToken
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
                new AuthenticationToken(
                        username: "admin",
                        tokenValue: "eyJhbGciOiJIUzI1NiJ9.eyJwcmluY2lwYWwiOiJINHNJQUFBQUFBQUFBSlZTdjBcL2JRQlIrRG9rb1FpcFFpVW9NZENuZGtDUEJtS21ndEJLeVFrV2FoVXF0THZiREhKenZ6TjBaa2dWbGdpRURxQzFTSlZaR1wvaE5ZK2dkVVpXQmw3dHAzaHVEQWdyakpmdmY1K1wvVjhmZ01WbzJFeDFvd0w0NmNpaTduMFRhcTVqQTJHbWVhMjYyY0dkWVEyUjN6TWdTMmF3TzN4U3VBRlVPS1JoVmZCRnR0bFZjRmtYRjF0YjJGb2F4ME5DMHJIZDR3Ym1pVzRwXC9TMmY4OGRLbzBQQkFwcTc2d0VvK3N3eGNKUVpkSTJsS3gzVXE0eFdvZkpZaGFvY051TnBrTzZRV2s1RTJZWU9vcVN0UVZHQVl5enpHNHFVdVZvTEV6Y21zMHNGOVVtMmxvQUwxSm1ETGw3bEtScG5YVjM3MnhLU3JBRCsxRHVwQjRkNnU2ZGdcL3FPeDE5V1FsQnFycVNaYThsRVJYeURPM0hpNzgxK1wvMzEwMm11VkFLaVQrYWVcL0tlWXpTOUM3K1BydlRWNjBGMXA0UFdTOWdOVTZLYm1aS3BnXC9hM1RLZjM1OStuRnljXC9obGhKUWQ0c1B6OXpIM1wvcTY1N3JKS1VxYVpWVU03SXRxOXNuc204cVdueVFkYjZQcE5ucVFDNlkrU0ZxTjdpWUtZNHBhMUVvTytMWXl0clFiMWI2MW1mYzI5VlZpVWNFbWlMXC9QTWJsbCtvR2hWXC9ldmp5Nk8zZjRsZ0JTcTdUR1JJbFU4V29FYVd0RkVmbkpcL01qdis4NnVjQkJqXC96ZjFtZmFkb1FBd0FBIiwic3ViIjoiYWRtaW4iLCJyb2xlcyI6WyJST0xFX1VTRVIiXSwiZXhwIjoxNTIwNDI2NDM4LCJpYXQiOjE1MjA0MjI4Mzh9.D-Ky6RgRQZrbfd4I5W642cV2ymJXwlWSIUG-rVWBo3o"
                ).save(flush: true)
                new ElasticEndpoint(name: "aws", url: "https://search-iris-ibwkuxcv4b2unly3c7d2d77v2a.eu-west-1.es.amazonaws.com/", active: true).save(flush: true)
                Role role = new Role(authority: 'ROLE_USER').save(flush: true)
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
                new AuthenticationToken(
                        username: "admin",
                        tokenValue: "eyJhbGciOiJIUzI1NiJ9.eyJwcmluY2lwYWwiOiJINHNJQUFBQUFBQUFBSlZTdjBcL2JRQlIrRG9rb1FpcFFpVW9NZENuZGtDUEJtS21ndEJLeVFrV2FoVXF0THZiREhKenZ6TjBaa2dWbGdpRURxQzFTSlZaR1wvaE5ZK2dkVVpXQmw3dHAzaHVEQWdyakpmdmY1K1wvVjhmZ01WbzJFeDFvd0w0NmNpaTduMFRhcTVqQTJHbWVhMjYyY0dkWVEyUjN6TWdTMmF3TzN4U3VBRlVPS1JoVmZCRnR0bFZjRmtYRjF0YjJGb2F4ME5DMHJIZDR3Ym1pVzRwXC9TMmY4OGRLbzBQQkFwcTc2d0VvK3N3eGNKUVpkSTJsS3gzVXE0eFdvZkpZaGFvY051TnBrTzZRV2s1RTJZWU9vcVN0UVZHQVl5enpHNHFVdVZvTEV6Y21zMHNGOVVtMmxvQUwxSm1ETGw3bEtScG5YVjM3MnhLU3JBRCsxRHVwQjRkNnU2ZGdcL3FPeDE5V1FsQnFycVNaYThsRVJYeURPM0hpNzgxK1wvMzEwMm11VkFLaVQrYWVcL0tlWXpTOUM3K1BydlRWNjBGMXA0UFdTOWdOVTZLYm1aS3BnXC9hM1RLZjM1OStuRnljXC9obGhKUWQ0c1B6OXpIM1wvcTY1N3JKS1VxYVpWVU03SXRxOXNuc204cVdueVFkYjZQcE5ucVFDNlkrU0ZxTjdpWUtZNHBhMUVvTytMWXl0clFiMWI2MW1mYzI5VlZpVWNFbWlMXC9QTWJsbCtvR2hWXC9ldmp5Nk8zZjRsZ0JTcTdUR1JJbFU4V29FYVd0RkVmbkpcL01qdis4NnVjQkJqXC96ZjFtZmFkb1FBd0FBIiwic3ViIjoiYWRtaW4iLCJyb2xlcyI6WyJST0xFX1VTRVIiXSwiZXhwIjoxNTIwNDI2NDM4LCJpYXQiOjE1MjA0MjI4Mzh9.D-Ky6RgRQZrbfd4I5W642cV2ymJXwlWSIUG-rVWBo3o"
                ).save(flush: true)
                Role role = new Role([authority: 'ROLE_USER']).save(flush: true)
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
