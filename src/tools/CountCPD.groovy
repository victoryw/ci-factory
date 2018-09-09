

def duplicationDetailReportPath = './result/duplication-detail-report.txt'
def file = new File(duplicationDetailReportPath)

def duplicateLineFoundRegx = /Found a (\d+) line/
def duplicatedFileRegx = /Starting at line/

def lastDuplicatedLineCount = 0;
def lastDuplicatedFileCount = 0;
def totalDuplicatedLineCount = 0;

file.eachLine {
    it ->
        def matchDuplicationFoundMatcher = (it =~ duplicateLineFoundRegx);
        if (matchDuplicationFoundMatcher.find()) {
            totalDuplicatedLineCount += lastDuplicatedFileCount * lastDuplicatedLineCount;
            lastDuplicatedFileCount = 0;
            lastDuplicatedLineCount = matchDuplicationFoundMatcher.group(1) as int;
            return
        }

        def matchDuplicatedFileMatcher = (it =~ duplicatedFileRegx);
        if (matchDuplicatedFileMatcher.find()) {
            lastDuplicatedFileCount ++;
            return
        }

        return
}
println totalDuplicatedLineCount;
