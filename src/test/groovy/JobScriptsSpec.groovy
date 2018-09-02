import javaposse.jobdsl.dsl.DslScriptLoader
import javaposse.jobdsl.plugin.JenkinsJobManagement
import org.junit.ClassRule
import org.jvnet.hudson.test.JenkinsRule
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

class JobScriptsSpec extends Specification {
    @Shared
    @ClassRule
    JenkinsRule jenkinsRule = new JenkinsRule()
    static private String jobFolderName = './src/jobs'

    @Unroll
    def 'test script in the file #file.name'(File file) {
        given:
        def jobManagement = new JenkinsJobManagement(System.out, [:], new File('.'))

        when:
        new DslScriptLoader(jobManagement).runScript(file.text)

        then:
        noExceptionThrown()

        where:
        file << jobFiles
    }

    static List<File> getJobFiles() {
        List<File> files = []
        new File(jobFolderName).eachFileRecurse {
            if (it.name.endsWith('.groovy')) {
                files << it
            }
        }
        files
    }
}
