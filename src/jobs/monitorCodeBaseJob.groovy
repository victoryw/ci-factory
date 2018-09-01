import com.victoryw.jenkins.job.builder.scaffold.MonitorJobBuilder
import javaposse.jobdsl.dsl.DslFactory

def jobName = 'monitor-code-base'
new MonitorJobBuilder().
        jobName('test-with-code').
        jobDescription(jobName).
        build(this as DslFactory)
