package tequlia.report.analyze

assert args.size() == 4
def claimToOtherDbFilePath = args[0]
def nonClaimToOtherDbFilePath = args[1]
def outFilePath = args[2]
def lastSucceedOutFilePath = args[3]

def claimJavaToDbDepend = resolveJavaToDbFile(claimToOtherDbFilePath)
def nonClaimJavaToDbDepend = resolveJavaToDbFile(nonClaimToOtherDbFilePath)
print("source is $claimJavaToDbDepend.source, sp is $claimJavaToDbDepend.sp, table is $claimJavaToDbDepend.table")
print("source is $nonClaimJavaToDbDepend.source, sp is $nonClaimJavaToDbDepend.sp, table is $nonClaimJavaToDbDepend.table")
outputToCsv(outFilePath, lastSucceedOutFilePath,
        claimJavaToDbDepend.sp, claimJavaToDbDepend.table,
        nonClaimJavaToDbDepend.sp, nonClaimJavaToDbDepend.table)

enum RowContentType {
    SP,
    TABLE,
    OTHER
}

static def getRowType(int splitCount) {
    if (splitCount == 0)
        return RowContentType.SP
    if (splitCount == 1)
        return RowContentType.OTHER
    if (splitCount == 2)
        return RowContentType.TABLE
    return RowContentType.OTHER

}

static def getTheDependCount(String rowContent) {
    def deps = rowContent.split(" : ")
    def depCount = (deps[1].trim() as int)
    [name: deps[0].trim(), count: depCount]
}

static def resolveJavaToDbFile(String filePath) {
    def splitCount = 0;
    def spBeginToken = '  '
    def claimToOtherDbFile = new File(filePath)
    if (!claimToOtherDbFile.exists()) {
        throw new Exception('claim to other db report file is missing')
    }

    def rowContentType = RowContentType.SP
    def spDepCount = 0
    def tableDepCount = 0

    claimToOtherDbFile.eachLine {
        it ->
            if (it.isEmpty()) {
                return
            }

            if (it.startsWith('-----------')) {
                splitCount = splitCount + 1
                return
            }

            if (getRowType(splitCount) == RowContentType.SP) {
                if (!it.startsWith(spBeginToken)) {
                    int depCount = getTheDependCount(it).count
                    spDepCount = spDepCount + depCount;
                }
                return
            }

            if (getRowType(splitCount) == RowContentType.TABLE) {
                int depCount = getTheDependCount(it).count
                tableDepCount = tableDepCount + depCount;
                return
            }
            return
    }

    return [source: filePath, sp: spDepCount, table: tableDepCount]
}

static def outputToCsv(String outputCsvPath, String lastSucceedOutFilePath,
                       int claimJavaToOtherSpCount,
                       int claimJavaToOtherTableCount,
                       int nonClaimJavaToOtherSpCount,
                       int nonClaimJavaToOtherTableCount) {
    def csvFile = new File(outputCsvPath);
    if (csvFile.exists()) {
        csvFile.delete()
    }

    def lastCsvFile = new File(lastSucceedOutFilePath);
    csvFile.parentFile.mkdir()
    if (!lastCsvFile.exists()) {
        csvFile.withWriter {
            out -> out.println 'Date, claimJavaToOtherSp, claimJavaToOtherTable, nonClaimJavaToOtherSp, nonClaimJavaToOtherTable'
        }
    } else {
        csvFile << lastCsvFile.text
    }

    def today = new Date().format('yyyy-MM-dd')

    def todayRecord = "$today, $claimJavaToOtherSpCount, $claimJavaToOtherTableCount, " +
            "$nonClaimJavaToOtherSpCount, $nonClaimJavaToOtherTableCount"

    csvFile.withWriterAppend {
        out ->
            out.println todayRecord
    }
}