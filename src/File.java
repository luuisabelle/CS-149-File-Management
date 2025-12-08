import java.util.ArrayList;
import java.util.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class File {
    private String name;
    private String content;
    private double size;
    private LocalDateTime creationDate;
    private Date modificationDate;
    private ArrayList<Integer> storedBlocks;

    public File(String name) {
        this.name = name;
        this.content = "";
        this.size = 0;
        this.creationDate = LocalDateTime.now();
        this.modificationDate = new Date();
        this.storedBlocks = new ArrayList<>();
    }

    public String getcreateDate() {//changed the data to datetimeformatter to sim dates on windows
        return creationDate.format(DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss"));
    }
    public String getmodDate() {
        return modificationDate.toString();
    }
    public String getName() {
        return name;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public double getSize() {
        return size;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void addStoredBlock(int block) {
        storedBlocks.add(block);
    }

    public void setStoredBlocks(ArrayList<Integer> storedBlocks) {
        this.storedBlocks = storedBlocks;
    }

    public ArrayList<Integer> getStoredBlocks() {
        return storedBlocks;
    }
}