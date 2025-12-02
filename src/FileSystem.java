import java.util.ArrayList;

public class FileSystem {
    private ArrayList<File> files;
    private ArrayList<File> openFiles;

    public static final int TOTAL_BLOCKS = 100;
    public static final int BLOCK_SIZE = 64;
    private boolean[] allocatedBlocks;

    public ArrayList<File> getFiles() {
        return files;
    }

    public FileSystem() {
        allocatedBlocks = new boolean[TOTAL_BLOCKS];
        files = new ArrayList<>();
        openFiles = new ArrayList<>();
    }

    public void createFile(String name) {
        if (searchFile(name) == null) {
            files.add(new File(name));
        }
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

    public ArrayList<File> findAllFile(String searchName) {
        ArrayList<File> foundFiles = new ArrayList<>();
        for  (File file : files) {
            if (file.getName().contains(searchName)) {
                foundFiles.add(file);
            }
        }
        return foundFiles;
    }

    public ArrayList<File> getOpenFiles() {
        return openFiles;
    }

    public void deleteFile(File file) {
        if (!openFiles.contains(file)) {
            files.remove(file);
        }
        for (Integer i : file.getStoredBlocks()) {
            allocatedBlocks[i] = false;
        }

    }

    public void writeToFile(String name, String content) {
        File file = searchFile(name);
        if (file != null && openFiles.contains(file)) {
            file.setContent(content);
            file.setSize(content.length());
            int numberOfBlocks = (int) file.getSize() /  BLOCK_SIZE;
            for (int i = 0; i < numberOfBlocks; i++) {
                file.addStoredBlock(allocateBlock());
            }
        }
    }

    public int allocateBlock() {
        for (int i = 0; i < TOTAL_BLOCKS; i++) {
            if (!allocatedBlocks[i]) {
                allocatedBlocks[i] = true;
                return i;
            }
        }
        return -1;
    }
}

