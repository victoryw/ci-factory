 #!/bin/bash

/usr/local/lib/tw-tools/pmd/bin/run.sh cpd --minimum-tokens 300 --files ./cbs > ./result/duplication-detail-report.txt

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

