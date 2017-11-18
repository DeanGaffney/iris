package com.wit.iris.sockets

import grails.gorm.transactions.Transactional
import groovy.json.JsonOutput
import org.springframework.messaging.simp.SimpMessagingTemplate

@Transactional
class SocketService {

    SimpMessagingTemplate brokerMessagingTemplate

    /**
     * Sends chart data to specific chart types for specific schemas
     * @param esIndex - the name of the schema index which is used as a unique subscription socket endpoint
     * @param chartType - the type of chart (Bubble, Pie etc...)
     * @param data - the data to be send to the chart
     */
    void sendDataToClient(String esIndex, String chartType, Map data){
        brokerMessagingTemplate.convertAndSend "$esIndex/$chartType", JsonOutput.toJson(data)
    }
}
