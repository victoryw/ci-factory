package com.victoryw.jenkins.job.builder.scaffold.block

import groovy.transform.builder.Builder
import groovy.transform.builder.SimpleStrategy

@Builder(builderStrategy = SimpleStrategy, prefix = '')
class ScmTrigger {
    String cronExpression;

    public def createScmTrigger(def context) {
        context.triggers {
            scm cronExpression
        }
    }
}
