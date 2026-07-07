package pl.nagrywarka.distribution;

public class Distribution {
    private String fileName;
    private String filePath;

    public Distribution(String fileName, String filePath) {
        this.fileName = fileName;
        this.filePath = filePath;
    }

    public Distribution(){
    
    }

    public String getFilePath() {
        return filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return fileName;
    }
}