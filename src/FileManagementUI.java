import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class FileManagementUI extends JFrame {
    private FileSystem fileSystem;
    private DefaultListModel<String> fileListModel;
    private JList<String> fileList;
    private DefaultListModel<String> fileDateModel;
    private JList<String> fileDateList;
    private DefaultListModel<String> fileSizeModel;
    private JList<String> fileSizeList;
    private DefaultListModel<String> openFilesModel;
    private JList<String> openFilesList;

    public FileManagementUI() {
        fileSystem = new FileSystem();

        setTitle("File Management System");
        setSize(1000, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(60, 120, 180));
        JLabel titleLabel = new JLabel("File Management System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        topPanel.add(titleLabel);
        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel leftPanel = new JPanel(new BorderLayout(5, 5));
        leftPanel.setBorder(BorderFactory.createTitledBorder("All Files"));

        //JPanel midPanel = new JPanel(new BorderLayout(5, 5));
        JPanel midPanel = new JPanel(new GridLayout(1,2));//used a gird for put two scroll panes
       // midPanel.setBorder(BorderFactory.createTitledBorder("Creation Date"));
        fileDateModel = new DefaultListModel<>();
        fileDateList = new JList<>(fileDateModel);
        fileDateList.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane dateScroll = new JScrollPane(fileDateList);
        fileSizeModel = new DefaultListModel<>();
        fileSizeList = new JList<>(fileSizeModel);
        fileSizeList.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane sizeScroll = new JScrollPane(fileSizeList);
        dateScroll.setBorder(BorderFactory.createTitledBorder("Date Modified"));
        sizeScroll.setBorder(BorderFactory.createTitledBorder("Size"));
        midPanel.add(dateScroll);
        midPanel.add(sizeScroll);
        //JPanel fakepanel = new JPanel(new GridLayout(2, 2, 5, 60));
        //midPanel.add(fakepanel,BorderLayout.SOUTH);
        

        fileListModel = new DefaultListModel<>();
        fileList = new JList<>(fileListModel);
        fileList.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane fileScroll = new JScrollPane(fileList);
        leftPanel.add(fileScroll, BorderLayout.CENTER);
        

        JPanel leftButtonPanel = new JPanel(new GridLayout(2, 2, 5, 5));

        JButton createBtn = new JButton("Create File");
        JButton openBtn = new JButton("Open File");
        JButton deleteBtn = new JButton("Delete File");
        JButton searchBtn = new JButton("Search File");

        createBtn.addActionListener(e -> createFile());
        openBtn.addActionListener(e -> openFile());
        deleteBtn.addActionListener(e -> deleteFile());
        searchBtn.addActionListener(e -> searchFile());

        leftButtonPanel.add(createBtn);
        leftButtonPanel.add(openBtn);
        leftButtonPanel.add(deleteBtn);
        leftButtonPanel.add(searchBtn);

        leftPanel.add(leftButtonPanel, BorderLayout.SOUTH);

        JPanel rightPanel = new JPanel(new BorderLayout(5, 5));
        rightPanel.setBorder(BorderFactory.createTitledBorder("Open Files"));

        openFilesModel = new DefaultListModel<>();
        openFilesList = new JList<>(openFilesModel);
        openFilesList.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane openScroll = new JScrollPane(openFilesList);
        rightPanel.add(openScroll, BorderLayout.CENTER);

        JPanel rightButtonPanel = new JPanel(new GridLayout(1, 2, 5, 5));

        JButton closeBtn = new JButton("Close File");
        closeBtn.addActionListener(e -> closeFile());
        rightButtonPanel.add(closeBtn, BorderLayout.SOUTH);

        JButton writeBtn = new JButton("Write to File");
        writeBtn.addActionListener(e -> writeFile());
        rightButtonPanel.add(writeBtn, BorderLayout.SOUTH);

        rightPanel.add(rightButtonPanel, BorderLayout.SOUTH);

        centerPanel.add(leftPanel);
        centerPanel.add(midPanel);
        centerPanel.add(rightPanel);

        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        add(bottomPanel, BorderLayout.SOUTH);

    }

    private void createFile() {
        String fileName = JOptionPane.showInputDialog(this,
                "Enter file name:",
                "Create File",
                JOptionPane.PLAIN_MESSAGE);

        if (fileName != null && !fileName.trim().isEmpty()) {
            fileSystem.createFile(fileName);
            refreshFileList();
        }
    }

    private void openFile() {
        String selected = fileList.getSelectedValue();
        if (selected == null) {
            JOptionPane.showMessageDialog(this,
                    "Please select a file to open",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        File file = fileSystem.searchFile(selected);
        if (file != null) {
            fileSystem.openFile(file);
            refreshOpenFilesList();
            refreshFileList();
        }
    }

    private void writeFile() {
        String selected = openFilesList.getSelectedValue();
        if (selected == null){
            JOptionPane.showMessageDialog(this,
                    "Please select a file to write",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE);
             return;
            }
        File file = fileSystem.searchFile(selected);

        if (file == null) return;

        String content = JOptionPane.showInputDialog(this, "Content:", file.getContent());
        if (content != null) {
            fileSystem.writeToFile(selected, content);
        }
    }

    private void closeFile() {
        String selected = openFilesList.getSelectedValue();
        if (selected == null) {
            JOptionPane.showMessageDialog(this,
                    "Please select a file to close",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        File file = fileSystem.searchFile(selected);
        if (file != null) {

            refreshOpenFilesList();
            refreshFileList();
            fileSystem.closeFile(file);        
        }
    }

    private void deleteFile() {
        String selected = fileList.getSelectedValue();
        if (selected == null) {
            JOptionPane.showMessageDialog(this,
                    "Please select a file to delete",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete '" + selected + "'?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            File file = fileSystem.searchFile(selected);
            if (file != null) {
                fileSystem.deleteFile(file);
                refreshFileList();
                refreshOpenFilesList();
            }
        }
    }

    private void searchFile() {
        String searchTerm = JOptionPane.showInputDialog(this,
                "Enter search term:",
                "Search File",
                JOptionPane.PLAIN_MESSAGE);

        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            File found = fileSystem.searchFile(searchTerm);
            if (found != null) {
                fileList.setSelectedValue(found.getName(), true);
            } else {
                JOptionPane.showMessageDialog(this,
                        "No file found matching '" + searchTerm + "'",
                        "Not Found",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private void refreshFileList() {
        fileListModel.clear();
        fileDateModel.clear();
        fileSizeModel.clear();
        ArrayList<File> files = fileSystem.getFiles();
        for (File file : files) {
            fileListModel.addElement(file.getName());
            fileDateModel.addElement(file.getcreateDate());
            fileSizeModel.addElement(String.valueOf(file.getSize()));
        }
    }

    private void refreshOpenFilesList() {
        openFilesModel.clear();
        fileDateModel.clear();
        fileSizeModel.clear();
        ArrayList<File> openFiles = fileSystem.getOpenFiles();
        for (File file : openFiles) {
            openFilesModel.addElement(file.getName());
            fileDateModel.addElement(file.getcreateDate());
            fileSizeModel.addElement(String.valueOf(file.getSize()));
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FileManagementUI ui = new FileManagementUI();
            ui.setVisible(true);
        });
    }
}
