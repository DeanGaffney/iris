package com.wit.iris.page.objects

/**
 * Created by dean on 12/12/17.
 */
enum Wait {

    SHORT(10000), MEDIUM(60000), LONG(900000)

    private final int waitTime

    Wait(int waitTime){
        this.waitTime = waitTime
    }

    int getTime(){
        return this.waitTime
    }
}