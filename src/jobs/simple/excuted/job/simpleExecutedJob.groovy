package simple.excuted.job

def jobName = 'simple-executed-job'
def resultFileName = 'incremental-result.out'
def resultFileFolderPath = 'result/'
def resultFilePath = "$resultFileFolderPath$resultFileName"
def lastSucceedFileFolderPath = 'lastSucceed/'
def lastSucceedFilePath = "$lastSucceedFileFolderPath$resultFileName"

def toolScriptName = 'WriteIncrementalNumberToFile.groovy'
def toolLocalFolderName="tools/"
def tooScriptPath= "$toolLocalFolderName$toolScriptName"
job(jobName).with {
    description('')
    displayName(jobName)

    steps {
        copyArtifacts('ci-factory') {
            includePatterns("src/tools/simple/excuted/job/$toolScriptName")
            targetDirectory(toolLocalFolderName)
            flatten()
        }

        copyArtifacts(jobName) {
            includePatterns(resultFilePath)
            targetDirectory(lastSucceedFileFolderPath)
            flatten()
            optional()
        }

        groovyScriptFile(tooScriptPath) {
            groovyInstallation('groovy')
            scriptParam(lastSucceedFilePath)
            scriptParam(resultFilePath)
        }

        publishers {
            archiveArtifacts {
                onlyIfSuccessful(true)
                pattern(resultFilePath)
            }
        }
    }
}
