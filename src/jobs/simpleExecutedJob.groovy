job('simple-executed-job').with {
    description('')
    displayName('simple-executed-job')

    steps {
	copyArtifact {
		projectName('ci-factory')
		filter('src/tools/**/*')
		target('tools')
	}
        shell('cat tools/src/tools/1.txt')
    }
}
