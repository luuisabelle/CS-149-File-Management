import java.util.ArrayList;
import java.util.Date;

public class File {
    private String name;
    private String content;
    private double size;
    private Date creationDate;
    private Date modificationDate;
    private ArrayList<Integer> storedBlocks;

    public File(String name) {
        this.name = name;
        this.content = "";
        this.size = 0;
        this.creationDate = new Date();
        this.modificationDate = new Date();
        this.storedBlocks = new ArrayList<>();
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