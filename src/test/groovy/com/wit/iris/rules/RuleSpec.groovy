package com.wit.iris.rules

import grails.testing.gorm.DataTest
import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class RuleSpec extends Specification implements DomainUnitTest<Rule>, DataTest {

    Rule rule

    @Override
    Class[] getDomainClassesToMock() {
        return [Rule]
    }

    def setup() {
        rule = new Rule(script: "println('Hello World')")
    }

    def cleanup() {
    }

    void "test creating a rule"(){
        when: "I save a rule"
        rule.save(flush: true)

        then: "The rule is saved"
        Rule.count() == 1
    }

    void "test deleting a rule"(){
        setup: "save the rule"
        rule.save(flush: true)

        when: "I delete a rule"
        rule.delete(flush: true)

        then: "It no longer exists"
        Rule.count() == 0
    }

    void "test editing a rule"(){
        setup: "save the rule"
        rule.save(flush: true)

        when: "I edit a rule"
        rule.script = "println('Goodbye world')"

        and: "I save the update"
        rule.save(flush: true)

        then:" I can find the rule by its new script"
        assert Rule.findByScript("println('Goodbye world')") != null
    }

    void "test script constraints"(){
        when: "I leave the rule script null"
        rule.script = null

        then: "it is not valid"
        assert !rule.validate()

        when: "I leave the rule script blank"
        rule.script = ''

        then: "it is not valid"
        assert !rule.validate()
    }

}
