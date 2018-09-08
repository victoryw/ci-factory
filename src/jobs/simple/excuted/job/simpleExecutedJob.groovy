package simple.excuted.job

def jobName = 'simple-executed-job'
job(jobName).with {
    description('')
    displayName(jobName)

    steps {
        copyArtifacts('ci-factory') {
            includePatterns('src/tools/simple/excuted/job/WriteIncrementalNumberToFile.groovy')
            targetDirectory('tools')
            flatten()
        }

        copyArtifacts(jobName) {
            includePatterns('result/incremental-result.out')
            targetDirectory('lastSucceed')
            flatten()
            optional()
        }

        groovyScriptFile('./tools/WriteIncrementalNumberToFile.groovy') {
            groovyInstallation('groovy-2.4.15')
        }

        publishers {
            archiveArtifacts {
                onlyIfSuccessful(true)
                pattern("result/**/*")
            }
        }
    }
}
