node {
    stage('CHECKOUT') {
        git url: 'https://github.com/victoryw/ci-factory.git'
    }

    stage('UPLOAD TOOLS') {
        archiveArtifacts artifacts: 'src/tools/**/*', onlyIfSuccessful: true
    }

    stage('UPLOAD EXAMPLE') {
        archiveArtifacts artifacts: 'src/demo/**/*'
    }


    stage('CREATE SEED JOB') {
        jobDsl targets: ['./src/jobs/**/*Job.groovy'].join('\n'),
                removedJobAction: 'DELETE',
                removedViewAction: 'DELETE',
                lookupStrategy: 'SEED_JOB',
                additionalClasspath: 'src/main/groovy'
    }
}
