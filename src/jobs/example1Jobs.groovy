job('simple-executed-job').with {
    description('')
    displayName('simple-executed-job')

    steps {
        shell('echo "this is simple job"')
    }
}