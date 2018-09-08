package simple.excuted.job

job('simple-executed-job').with {
    description('')
    displayName('simple-executed-job')

    steps {
        copyArtifacts('ci-factory') {
            includePatterns('src/tools/**/*')
            targetDirectory('tools')
        }

        copyArtifacts('ci-factory') {
            includePatterns('src/jobs/simple/excuted/job/tools/WriteIncrementalNumberToFile.groovy')
            targetDirectory('tools')
            flatten()
        }
        shell('cat tools/src/tools/1.txt')
        shell('cat ./tools/WriteIncrementalNumberToFile.groovy')
    }
}
