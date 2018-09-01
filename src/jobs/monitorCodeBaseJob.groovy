import com.victoryw.jenkins.job.builder.scaffold.MonitorJobBuilder
import javaposse.jobdsl.dsl.DslFactory

def jobName = 'monitor-code-base'
new MonitorJobBuilder().
        jobName(jobName).
        jobDescription(jobName).
        build(this as DslFactory)
