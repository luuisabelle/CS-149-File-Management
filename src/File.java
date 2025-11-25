import java.util.Date;

public class File {
    private String name;
    private String content;
    private double size;
    private Date creationDate;
    private Date modificationDate;

    public File(String name) {
        this.name = name;
        this.content = "";
        this.size = 0;
        this.creationDate = new Date();
        this.modificationDate = new Date();
    }

    public String getName() {
        return name;
    }
}