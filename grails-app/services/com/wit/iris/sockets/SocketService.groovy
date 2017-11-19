package com.wit.iris.sockets

import grails.gorm.transactions.Transactional
import groovy.json.JsonOutput
import org.springframework.messaging.simp.SimpMessagingTemplate

@Transactional
class SocketService {

    SimpMessagingTemplate brokerMessagingTemplate

    /**
     * Sends dashboard.chart data to specific dashboard.chart types for specific schemas
     * @param esIndex - the name of the schema index which is used as a unique subscription socket endpoint
     * @param chartType - the type of dashboard.chart (Bubble, Pie etc...)
     * @param data - the data to be send to the dashboard.chart
     */
    void sendDataToClient(String esIndex, String chartType, Map data){
        brokerMessagingTemplate.convertAndSend "$esIndex/$chartType", JsonOutput.toJson(data)
    }
}
