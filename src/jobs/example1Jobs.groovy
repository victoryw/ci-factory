job('simple-job').with {
    description('')
    displayName('example-1')

    steps {
        shell('echo "this is simple job"')
    }
}