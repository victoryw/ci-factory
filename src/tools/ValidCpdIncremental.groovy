

assert args.size() == 2

def lastSucceedFilePath = args[0]
def currentCpdFilePath = args[1]

static def getCpdResult(String cpdResultFilePath) {
    def cpdFilePath = new File(cpdResultFilePath)
    if (cpdFilePath.exists()) {
        return cpdFilePath.withReader {
            return it.readLine() as Integer
        }
    }
    null
}

def lastCpdResult = getCpdResult(lastSucceedFilePath)
def currentCpdResult = getCpdResult(currentCpdFilePath)

if(lastCpdResult != null && currentCpdResult > lastCpdResult) {
    throw new Exception('new push make the increased cpd')
}