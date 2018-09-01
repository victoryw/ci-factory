package com.victoryw.jenkins.job.builder.scaffold

import groovy.transform.builder.Builder
import groovy.transform.builder.SimpleStrategy
import javaposse.jobdsl.dsl.DslFactory
import javaposse.jobdsl.dsl.Job

@Builder(builderStrategy = SimpleStrategy, prefix = '')
class MonitorJobBuilder {
    String jobName;
    String jobDescription;
    String gitRemoteUrl;
    String triggers;
    String validateFilesPath;

    Job build(DslFactory dslFactory) {
        dslFactory.job(jobName) {
            description jobDescription
            scm {
                git {
                    remote {
                        if (gitRemoteUrl) {
                            url gitRemoteUrl
                        }
                    }
                }
            }

            triggers {
                if (triggers) {
                    scm triggers
                }
            }

            steps {
                if (validateFilesPath) {
                    shell("echo ${validateFilesPath}")
                }

            }
        }

    }

}
