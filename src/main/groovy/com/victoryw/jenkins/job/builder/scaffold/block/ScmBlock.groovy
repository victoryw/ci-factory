package com.victoryw.jenkins.job.builder.scaffold.block

import groovy.transform.builder.Builder
import groovy.transform.builder.SimpleStrategy

@Builder(builderStrategy = SimpleStrategy, prefix = '')
class ScmBlock {
    String branchName
    String repoUrl
    String credentialId

    def createScmBlock(def context) {
        context.scm {
            git {
                branch(branchName)
                remote {
                    url this.repoUrl
                    if (this.credentialId) {
                        credentials(this.credentialId)
                    }
                }
            }
        };
    }
}
