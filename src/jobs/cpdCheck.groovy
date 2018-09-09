import com.victoryw.jenkins.job.builder.scaffold.block.ScmBlock
import com.victoryw.jenkins.job.builder.scaffold.block.ScmTrigger

def jobName = 'cbs-cpd-check'
def trigger = 'H/2 * * * *'
def resultFolder = 'result/'
def cpdReportFileName='duplication-summary-report.txt'
def currentCpdReportFilePath = "${resultFolder}${cpdReportFileName}"
def cpdRuleWrapperPath = './tools/src/tools/cpd-rule.sh'
def pmdScriptPath = '/pmd/bin/Run.sh'
def toolsSourceJobName = 'ci-factory';
def lastSucceedFileFolderPath = 'lastSucceed/'
def lastSucceedFilePath = "$lastSucceedFileFolderPath$cpdReportFileName"
job(jobName) {
    description(jobName)

    new ScmBlock().
            branchName('master').
            repoUrl('https://github.com/victoryw/spring-boot-quartz-example.git').
            createScmBlock(delegate)


    new ScmTrigger().
            cronExpression(trigger).
            createScmTrigger(delegate)


    steps {
        copyArtifacts(toolsSourceJobName) {
            includePatterns('src/tools/**/*')
            targetDirectory('tools')
        }

        copyArtifacts(jobName) {
            includePatterns(resultFolder)
            targetDirectory(lastSucceedFileFolderPath)
            flatten()
            optional()
            buildSelector {
                workspace()
            }
        }

        shell("""
            if [ ! -d ${resultFolder} ]; then
                mkdir ${resultFolder}
            fi
        """)

        shell("chmod +x ${cpdRuleWrapperPath}")
        shell("${cpdRuleWrapperPath} ${pmdScriptPath} $currentCpdReportFilePath")

        shell("groovy tools/src/tools/CountCPD.groovy > ${currentCpdReportFilePath}")

        shell("groovy tools/src/tools/ValidCpdIncremental.groovy $lastSucceedFilePath $currentCpdReportFilePath")

        publishers {
            archiveArtifacts {
                onlyIfSuccessful(false)
                pattern("${resultFolder}**/*")
            }
        }
    }
}
