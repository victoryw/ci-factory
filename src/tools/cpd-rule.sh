 #!/bin/bash

/pmd/bin/Run.sh --minimum-tokens 300 --files ./cbs > $1

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

