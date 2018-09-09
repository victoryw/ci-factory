package tequlia.report.analyze

assert args.size() == 6
def claimToOtherDbFilePath = args[0]
def nonClaimToOtherDbFilePath = args[1]
def claimSpToOtherSpFilePath = args[2]
def OtherSpToClaimSpFilePath = args[3]
def outFilePath = args[4]
def lastSucceedOutFilePath = args[5]

def claimJavaToDbDepend = resolveJavaToDbFile(claimToOtherDbFilePath)
print("source is $claimJavaToDbDepend.source, sp is $claimJavaToDbDepend.sp, table is $claimJavaToDbDepend.table")
def nonClaimJavaToDbDepend = resolveJavaToDbFile(nonClaimToOtherDbFilePath)
print("source is $nonClaimJavaToDbDepend.source, sp is $nonClaimJavaToDbDepend.sp, table is $nonClaimJavaToDbDepend.table")
def claimSpToNoClaimSpDepend = resolveSpToSpFile(claimSpToOtherSpFilePath)
print("source is $claimSpToNoClaimSpDepend.source, sp is $claimSpToNoClaimSpDepend.sp")
def nonClaimSpToClaimSpDepend = resolveSpToSpFile(OtherSpToClaimSpFilePath)
print("source is $nonClaimSpToClaimSpDepend.source, sp is $nonClaimSpToClaimSpDepend.sp")

outputToCsv(outFilePath, lastSucceedOutFilePath,
        claimJavaToDbDepend.sp, claimJavaToDbDepend.table,
        nonClaimJavaToDbDepend.sp, nonClaimJavaToDbDepend.table,
        claimSpToNoClaimSpDepend.sp, nonClaimSpToClaimSpDepend.sp)

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

static def resolveSpToSpFile(String filePath) {
    def spToOtherSpFile = new File(filePath)

    if (!spToOtherSpFile.exists()) {
        throw new Exception("$filePath report file is missing")
    }

    def spBeginToken = '  '
    def spToOtherSpCount = 0
    spToOtherSpFile.eachLine {
        it ->
            if (it.isEmpty()) {
                return
            }

            if (it.startsWith(spBeginToken)) {
                spToOtherSpCount = spToOtherSpCount + 1
            }
            return
    }

    return [source: filePath, sp: spToOtherSpCount]
}

static def resolveJavaToDbFile(String filePath) {
    def splitCount = 0;
    def spBeginToken = '  '
    def javaToOtherDbFile = new File(filePath)
    if (!javaToOtherDbFile.exists()) {
        throw new Exception("$filePath report file is missing")
    }

    def spDepCount = 0
    def tableDepCount = 0

    javaToOtherDbFile.eachLine {
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
                       int claimJavaToNonClaimSpCount,
                       int claimJavaToNonClaimTableCount,
                       int nonClaimJavaToClaimSpCount,
                       int nonClaimJavaToClaimTableCount,
                       int claimSpToNonClaimSpCount,
                       int NonClaimSpToClaimSpCount) {
    def csvFile = new File(outputCsvPath);
    if (csvFile.exists()) {
        csvFile.delete()
    }

    def lastCsvFile = new File(lastSucceedOutFilePath);
    csvFile.parentFile.mkdir()
    if (!lastCsvFile.exists()) {
        csvFile.withWriter {
            out -> out.println 'Date, claimJavaToNonClaimSp, claimJavaToNonClaimTable, ' +
                    'nonClaimJavaToClaimSp, nonClaimJavaToClaimTable,' +
                    'claimSpToNoClaimSpCount, nonClaimSpToClaimSpCount'
        }
    } else {
        csvFile << lastCsvFile.text
    }

    def today = new Date().format('yyyy-MM-dd')

    def todayRecord = "$today, $claimJavaToNonClaimSpCount,$claimJavaToNonClaimTableCount" +
            ",$nonClaimJavaToClaimSpCount,$nonClaimJavaToClaimTableCount" +
            ",$claimSpToNonClaimSpCount,$NonClaimSpToClaimSpCount"

    csvFile.withWriterAppend {
        out ->
            out.println todayRecord
    }
}