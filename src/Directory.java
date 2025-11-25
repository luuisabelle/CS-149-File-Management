import java.util.ArrayList;

public class Directory {
    private ArrayList<File> files = new ArrayList<>();
    private ArrayList<File> openFiles = new ArrayList<>();

    public ArrayList<File> getFiles() {
        return files;
    }

    public void createFile(String name) {
        files.add(new File(name));
    }

    public void openFile(File file) {
        openFiles.add(file);
    }

    public void closeFile(File file) {
        openFiles.remove(file);
    }

    public File searchFile(String name) {
        for  (File file : files) {
            if (file.getName().contains(name)) {
                return file;
            }
        }
        return null;
    }

    public void deleteFile(File file) {
        if (!openFiles.contains(file)) {
            files.remove(file);
        }
    }
}

