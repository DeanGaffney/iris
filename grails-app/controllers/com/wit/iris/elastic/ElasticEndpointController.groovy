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

}
