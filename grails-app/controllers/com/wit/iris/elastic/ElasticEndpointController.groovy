package com.wit.iris.elastic


class ElasticEndpointController {

    def index(){
        List<ElasticEndpoint> endpoints = ElasticEndpoint.list()
        render(view: "index", model:[endpoints: endpoints])
    }

    def show(Long id) {
        ElasticEndpoint endpoint = ElasticEndpoint.get(id)
        render(template: "show", model: [endpoint: endpoint])
    }

    def create(){
        render(template: "create")
    }

    def save(ElasticEndpoint esEndpoint){
        ElasticEndpoint activeEndpoint = ElasticEndpoint.findWhere([active: true])
        if(activeEndpoint != null){
            activeEndpoint.active = false
            activeEndpoint.save(flush: true)
        }
        esEndpoint.active = esEndpoint.active.booleanValue()
        if(!(esEndpoint.validate() && esEndpoint.save(flush: true))){
            log.debug(esEndpoint.errors.allErrors*.toString())
        }
        redirect(view: "index")
    }

    def delete(Long id){
        ElasticEndpoint endpoint = ElasticEndpoint.get(id)
        endpoint.delete(flush: true)
        redirect(view: "index")
    }

    def edit(Long id){
        ElasticEndpoint endpoint = ElasticEndpoint.get(id)
        render(template: "edit", model:[endpoint: endpoint])
    }

    def update(Long id){
        ElasticEndpoint endpoint = ElasticEndpoint.get(id)
        endpoint.name = request.JSON.name as String
        endpoint.url = request.JSON.url as String
        endpoint.active = Boolean.valueOf(request.JSON.active).booleanValue()
        endpoint.save(flush: true)

        redirect(view: "index");
    }

}
