 #!/bin/bash


$1 cpd --minimum-tokens $4 --files $2 > $3

status=$?

if test ${status} -eq 0
then
    exit 0
fi

if test ${status} -eq 4
then
    exit 0
fi

exit 1

