import com.victoryw.jenkins.job.builder.scaffold.MonitorJobBuilder
import javaposse.jobdsl.dsl.DslFactory

def jobName = 'monitor-code-base'
def codeRepo = 'https://github.com/victoryw/delay-queue.git'
new MonitorJobBuilder().
        jobName(jobName).
        jobDescription(jobName).
        gitRemoteUrl(codeRepo).
        triggers('H/2 * * * *').
        validateFilesPath('./redis/src/main').
        archiveArtifactsPath('1.out').
        build(this as DslFactory)
