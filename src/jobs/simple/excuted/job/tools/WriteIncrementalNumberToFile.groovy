package simple.excuted.job.tools

def currentNumber = 0;
def filePath = './result/incremental-result.out'
def file = new File(filePath)
def parentFolder = file.parentFile
if(!parentFolder.exists()) {
    parentFolder.mkdir()
}

file <<  currentNumber

