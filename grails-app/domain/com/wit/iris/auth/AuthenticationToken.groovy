package com.wit.iris.auth

class AuthenticationToken {

    String tokenValue
    String username

    static mapping = {
        tokenValue sqlType: 'text'
        version false
    }
}