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
    String archiveArtifactsPath

    Job build(DslFactory dslFactory) {
        assert gitRemoteUrl != null
        assert triggers != null
        assert validateFilesPath != null

        dslFactory.job(jobName) {
            description jobDescription


            scm {
                git {
                    remote {
                        url gitRemoteUrl
                    }
                }
            }

            triggers {
                scm triggers
            }

            steps {
                shell("ls -al ${validateFilesPath} >> ${archiveArtifactsPath}")
            }

            if(archiveArtifactsPath) {
                publishers {
                    archiveArtifacts archiveArtifactsPath
                }
            }
        }

    }

}
