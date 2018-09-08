package simple.excuted.job

static def getLastSucceedResult() {
    def lastSucceedFilePath = './lastSucceed/incremental-result.out'
    def lastSucceedFile = new File(lastSucceedFilePath)
    if (lastSucceedFile.exists()) {
        return lastSucceedFile.withReader {
            return it.readLine() as Integer
        }
    }
    null
}

static void writeCurrentNumberToFile(int currentNumber) {
    def filePath = './result/incremental-result.out'
    def file = new File(filePath)
    def parentFolder = file.parentFile
    if (!parentFolder.exists()) {
        parentFolder.mkdir()
    }
    if (file.exists()) {
        file.delete()
    }
    file << currentNumber
}

Integer lastSucceedResult = getLastSucceedResult();
def currentNumber = 1 + (lastSucceedResult == null ? 0 : lastSucceedResult);
writeCurrentNumberToFile(currentNumber)


