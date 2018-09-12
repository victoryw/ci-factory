package pipelineascode

import com.victoryw.jenkins.job.builder.scaffold.block.ScmTrigger

def trigger = 'H/2 * * * *'
pipelineJob('git-poll-declare-in-code') {
    new ScmTrigger().
            cronExpression(trigger).
            createScmTrigger(delegate)

    definition {
        cps {
            sandbox()
            script('''
                node {
                    stage('CHECK OUT') {
                        git poll: true url:https://github.com/victoryw/spring-boot-openid-example.git
                    }
                    
                    stage('SHOW THE CHANGE FILE') {
                        sh 'cat README.md'
                    }
                }
            ''')
        }
    }
}
