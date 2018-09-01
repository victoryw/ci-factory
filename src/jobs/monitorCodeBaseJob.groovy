import com.victoryw.jenkins.job.builder.scaffold.MonitorJobBuilder

def jobName = 'monitor-code-base'
def description = new MonitorJobBuilder().
        jobName('test-with-code').
        jobDescription(jobName)
println description.metaClass.getMethods()
description.build()
