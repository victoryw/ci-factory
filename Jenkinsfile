node {
    stage('CHECKOUT') {
        git url: ' https://github.com/victoryw/ci-factory.git'
    }

    stage('CREATE SEED JOB') {
        dsl {
            external 'jobs/**/*Jobs.groovy'
            additionalClasspath 'src/main/groovy'
        }
    }
}