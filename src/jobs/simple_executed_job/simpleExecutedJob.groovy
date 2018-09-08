package simple_executed_job

job('simple-executed-job').with {
    description('')
    displayName('simple-executed-job')

    steps {
        copyArtifacts('ci-factory') {
            includePatterns('src/tools/**/*')
            targetDirectory('tools')
        }
        shell('cat tools/src/tools/1.txt')
    }
}
