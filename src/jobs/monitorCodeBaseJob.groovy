import com.victoryw.jenkins.job.builder.scaffold.MonitorJobBuilder

def jobName = 'monitor-code-base'
new MonitorJobBuilder().
        jobName('test-with-code').
        jobDescription(jobName).
        build()
