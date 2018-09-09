package tequlia.report.analyze

def desc = 'tequlia report analyze work'
def jobName = 'tequlia-report-analyze'
def dailyReportFileName = 'dailyDependency.csv'
job(jobName){
    description(desc)
    displayName(desc)

    steps {
        copyArtifacts('ci-factory') {
            includePatterns("src/demo/tequlia-java-db.out",
                    "src/demo/non-claim-java-to-db.out")
            targetDirectory('demo')
            flatten()
        }

        copyArtifacts('ci-factory') {
            includePatterns("src/tools/tequlia/report/analyze/**/*")
            targetDirectory('tools')
            flatten()
        }

        copyArtifacts(jobName) {
            includePatterns("result/${dailyReportFileName}")
            targetDirectory('lastSucceed')
            flatten()
            optional(true)
        }

        groovyScriptFile('tools/TequliaReportAnalyzer') {
            groovyInstallation('groovy')
            scriptParam('demo/tequlia-java-db.out')
            scriptParam('demo/non-claim-java-to-db.out')
            scriptParam('demo/claim-db-to-other-db.out')
            scriptParam('demo/other-db-to-claim-db.out')
            scriptParam("result/${dailyReportFileName}")
            scriptParam("lastSucceed/${dailyReportFileName}")
        }

        publishers {
            archiveArtifacts {
                pattern("result/${dailyReportFileName}")
                onlyIfSuccessful(true)
            }
        }
    }
}
