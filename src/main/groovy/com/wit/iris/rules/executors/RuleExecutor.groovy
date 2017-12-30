package com.wit.iris.rules.executors

import com.wit.iris.rules.Rule

/**
 * Created by dean on 30/10/17.
 */
class RuleExecutor {

    static Map execute(Rule rule, Map json){
        Binding binding = new Binding()
        binding.setVariable("json", json)
        GroovyShell shell = new GroovyShell(binding)

        Object result = shell.evaluate(rule.script)
        return result as Map
    }
}
