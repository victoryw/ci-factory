node {
    stage('CHECKOUT') {
        git url: ' https://github.com/victoryw/ci-factory.git'
    }

    stage('CREATE SEED JOB') {
        jobDsl targets: ['./src/jobs/**/*.groovy'].join('\n'),
                removedJobAction: 'DELETE',
                removedViewAction: 'DELETE',
                lookupStrategy: 'SEED_JOB',
                additionalClasspath: 'src/main/groovy'
    }
}