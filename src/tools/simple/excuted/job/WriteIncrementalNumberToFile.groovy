package simple.excuted.job


assert args.size() == 2
def lastSucceedFilePath = args[0]
def filePath = args[1]

static def getLastSucceedResult(String lastSucceedFilePath) {

    def lastSucceedFile = new File(lastSucceedFilePath)
    if (lastSucceedFile.exists()) {
        return lastSucceedFile.withReader {
            return it.readLine() as Integer
        }
    }
    null
}

static void writeCurrentNumberToFile(int currentNumber, String filePath) {
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

Integer lastSucceedResult = getLastSucceedResult(lastSucceedFilePath);
def currentNumber = 1 + (lastSucceedResult == null ? 0 : lastSucceedResult);
writeCurrentNumberToFile(currentNumber, filePath)


