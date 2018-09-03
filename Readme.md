# CI-FACTORY
Thanks to [jenkins-job-dsl-plugin](https://github.com/jenkinsCI/job-dsl-plugin/) and [job-dsl-gradle-example](https://github.com/sheehan/job-dsl-gradle-example)

We could replay the creating of pipeline by following the [pipeline as code](https://www.thoughtworks.com/radar/techniques/pipelines-as-code) (this is different with the concept of **jenkins pipeline as code**). By this way, we could versioning the CI code, test the CI, and recreate the CI in any times, not only automaticly build the behaviors of the job.
> Teams are pushing for automation across their environments(testing), including their development infrastructure. Pipelines as code is defining the deployment pipeline through code instead of configuring a running CI/CD tool. LambdaCD, Drone, GoCD and Concourse are examples that allow usage of this technique. Also, configuration automation tools for CI/CD systems like GoMatic can be used to treat the deployment pipeline as code—versioned and tested.
## Setup the Dev env
### Project structor

``` bash
.
├── Jenkinsfile
├── build
│   └── debug-xml
│       └── jobs
│           ├── monitor-code-base.xml
│           └── simple-executed-job.xml
├── build.gradle
├── gradle.properties
├── gradlew
├── gradlew.bat
├── settings.gradle
└── src
    ├── jobs                            # DSL script files
    │   ├── monitorCodeBaseJob.groovy
    │   └── simpleExecutedJob.groovy
    ├── main
    │   ├── groovy
    │   │   └── com
    │   │       └── victoryw
    │   │           └── jenkins         # support classes
    │   │               └── job
    │   │                   └── builder
    │   │                       └── scaffold
    │   │                           └── MonitorJobBuilder.groovy
    │   └── resources
    │       └── idea.gdsl               # IDE support for IDEA
    └── test
        └── groovy
            ├── JobScriptsSpec.groovy
            ├── com
            │   └── victoryw
            │       └── jenkins
            │           └── job
            │               └── builder
            │                   └── scaffold
            └── support
                └── TestUtil.groovy
```

By following [Testing DSL Scripts](https://github.com/jenkinsci/job-dsl-plugin/wiki/Testing-DSL-Scripts) and [Jenkins Job DSL Gradle Example](https://github.com/sheehan/job-dsl-gradle-example), we create the test infrastructure to valid the the job script in the `src/jobs`; The common job builder classes and other support classes are defined in the `src/main/groovy`; IDE support file is in the `src/resource/idea.gdsl`, more info refer to the [IDE Support](https://github.com/jenkinsci/job-dsl-plugin/wiki/IDE-Support); the jobs config.xml will output to the `build/debug-xml` after the test run.

### How to setup the IDE support
Please refer the [IDE Support](https://github.com/jenkinsci/job-dsl-plugin/wiki/IDE-Support)
## How to use the project
### Setup the Jenkins with plugin
Before use this project, we should could the the jenkins with the plugins. (These manual operations are also automatic and versioning by **infrastructure-as-code**)
The below list the essential plugins:

* [git plugin](https://plugins.jenkins.io/git)
* [pipeline plugin](https://plugins.jenkins.io/workflow-aggregator)
* [job dsl plugin](https://plugins.jenkins.io/job-dsl), please disable the script security following the [wiki](https://github.com/jenkinsci/job-dsl-plugin/wiki/Script-Security#disabling-script-security)
* [Credentials Plugin](https://wiki.jenkins-ci.org/display/JENKINS/Credentials+Plugin)

### Create the seed job
For automatically generate the CI jobs/pipelines defined in the `jobs folder`, we should create a seed job following [Tutorial Using the Jenkins Job DSL](https://github.com/jenkinsCI/job-dsl-plugin/wiki/Tutorial---Using-the-Jenkins-Job-DSL).
All the behaviors of the seed job are defined in the Jenkinsfile.  
We only should create a jenkins pipeline by this repo.

### Maintain the CI jobs
#### Keep the CI jobs to latest
The jobs created by the seed job are defined in the jobs folder. When we change the file in this folder and push the commit to repo, the seed job will auto run resulting in creating, updaing and deleting the CI jobs.
![job maintenance](https://blog.codecentric.de/files/2015/09/Bildschirmfoto-2015-09-19-um-20.51.21.png)
More infomation, please see the [Using Jenkins Job DSL for Job Lifecycle Management](https://blog.codecentric.de/en/2015/10/using-jenkins-job-dsl-for-job-lifecycle-management/)

#### Keep the seed job run correctly
Lacking of the ACID, the seed job would not roll back when any correctly happend during the job running.  
So please run the `./gradlew test` to check whether the seed job could run the jobs correctly, and generate the job config xml to the `./build/debug-xml/jobs`
## Why I choose the Job Dsl
TODO