job('seed-job') {
    scm {
        github('https://github.com/victoryw/ci-factory.git')
    }

    triggers {
        scm 'H/5 * * * *'
    }

    steps {
        dsl {
            external 'jobs/**/*Jobs.groovy'
            additionalClasspath 'src/main/groovy'
        }
    }
}