package pipelineascode

pipelineJob('git-poll-declare-in-code') {
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
