job = pipelineJob('example-1')
job.with {
    description('')
    displayName('example-1')

    scm {
        git('https://github.com/victoryw/test-jekinsfile.git')
    }

    definition {
        cpsScm {
            scm {
                git('https://github.com/victoryw/test-jekinsfile.git')
            }
        }
    }

    triggers {
        scm('*/2 * * * *')
    }
}