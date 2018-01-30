package com.wit.iris.charts.enums

/**
 * Created by dean on 14/10/17.
 */
enum ChartType {

    BAR("Bar"),
    BUBBLE("Bubble"),
    PIE("Pie"),
    LINE("Line"),
    STATE_LIST("StateList"),
    STATE_DISC("StateDisc");

    private String value;

    private ChartType(String value){
        this.value = value;
    }

    String getValue(){ return this.value; }
}