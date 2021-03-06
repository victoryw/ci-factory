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
    String archiveArtifactsPath;

    Job build(DslFactory dslFactory) {
        assert gitRemoteUrl != null
        assert triggers != null
        assert validateFilesPath != null

        def job = dslFactory.job(jobName)
        job.with {
            description jobDescription
        }

        job.with {
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
                copyArtifacts(jobName) {
                    includePatterns(archiveArtifactsPath)
                    targetDirectory('latestSucceed/')
                    flatten()
                    buildSelector {
                        workspace()
                    }
                }

                shell('''
if [ ! -f "$myFile" ]; then 
    rm 1.out
fi
echo \${BUILD_NUMBER} > 1.out''')

                shell("cat latestSucceed/${archiveArtifactsPath}")
            }

            if (archiveArtifactsPath) {
                publishers {
                    archiveArtifacts archiveArtifactsPath
                }
            }
        }

        job.steps {
            shell('echo 111');
        }
        return job;
    }

}
