package com.podval;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.function.Consumer;

public class MainWindow {

    private JTable table;
    private ArrayList<Book> books;
    private final Dimension WINDOW_SIZE = new Dimension(860, 370);
    ;
    private JPanel btnPanel;
    private JPanel topPanel;
    private JFrame jfrm;
    private JScrollPane jscrlp;
    private JPanel tablePanel;
    private Dimension tableSize;
    private LibraryTable libraryTable;
    private JMenuBar menuBar;
    private JMenu menu;
    private ArrayList<JMenuItem> menuItems;
    private ArrayList<JButton> topPanelBtns;


    public MainWindow() {

        books = new ArrayList<>();

        initFrame();

        initMenuBar();

        initTopPanel();

        initTablePanel();

        jfrm.setResizable(false);
        jfrm.pack();
        jfrm.setVisible(true);
    }

    private void initFrame() {
        jfrm = new JFrame("JTableExample");

        jfrm.getContentPane().setLayout(new BorderLayout());

        jfrm.setPreferredSize(WINDOW_SIZE);

        jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initTablePanel() {

        initScrollPaneTable();

        tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());

        initTableSize();

        tablePanel.add(jscrlp, BorderLayout.CENTER);
        jfrm.getContentPane().add(tablePanel, BorderLayout.CENTER);
    }

    private void initTableSize() {
        int preferedTableHeight = (table.getRowHeight() * libraryTable.getRowCount());
        int maxTableHeight = (int) (0.7 * WINDOW_SIZE.height);

        tableSize = new Dimension(jfrm.getWidth() - 100, preferedTableHeight < maxTableHeight ? preferedTableHeight : maxTableHeight);
        table.setPreferredScrollableViewportSize(tableSize);
        table.setSize(tableSize);
    }

    private void initScrollPaneTable() {
        libraryTable = LibraryGenerator.generate(books);
        table = new JTable(libraryTable);
        jscrlp = new JScrollPane(table);
    }

    private void initTopPanel() {

        topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());

        initTopButtonsPanel();

        jfrm.getContentPane().add(topPanel, BorderLayout.NORTH);
    }

    private void initTopButtonsPanel() {

        btnPanel = new JPanel();
        btnPanel.setLayout(new FlowLayout());

        topPanelBtns = new ArrayList<>();

        initAddToTableBtn();
        initRemoveFromTableBtn();

        for (JButton btn : topPanelBtns)
            btnPanel.add(btn);

        topPanel.add(btnPanel, BorderLayout.EAST);
    }

    private void initAddToTableBtn() {

        JButton btnAddToTable = new JButton("Add");

        btnAddToTable.addActionListener(e -> {

            NewRowFrame nrf = new NewRowFrame();

            books.add(new Book("newBook"));
            libraryTable.fireTableDataChanged();
        });


        topPanelBtns.add(btnAddToTable);
    }

    private void initRemoveFromTableBtn() {
        JButton btnRemoveFromTable = new JButton("Remove");

        topPanelBtns.add(btnRemoveFromTable);
    }

    private void initMenuBar() {

        menuBar = new JMenuBar();

        menu = new JMenu("File");

        initMenuItems();

        for (JMenuItem item : menuItems)
            menu.add(item);

        menuBar.add(menu);

        jfrm.setJMenuBar(menuBar);
    }

    private void initMenuItems() {

        menuItems = new ArrayList<>();

        initSaveMenuBtn();
        initOpenMenuBtn();

    }

    private String fileNameFromDialog(){

        String fileName = null;

        File currentDirFile = new File(".");
        String helper = currentDirFile.getAbsolutePath();
        String currentDir = helper.substring(0, helper.length() - currentDirFile.getName().length());

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(currentDir));

        int returnVal = fileChooser.showOpenDialog(jfrm);

        if (returnVal == JFileChooser.APPROVE_OPTION)
            fileName = fileChooser.getSelectedFile().getAbsolutePath();

        return fileName;
    }

    private void saveFileAction(){

        try {

            libraryTable.saveToFile(fileNameFromDialog());

        }  catch (IOException e) {

            e.printStackTrace();
            JOptionPane.showMessageDialog(new JFrame(), "Cant save to file", "Error", JOptionPane.ERROR_MESSAGE);

        }

    }

    private void readFileAction(){

        try {

            libraryTable.readFromFile(fileNameFromDialog());
            libraryTable.fireTableDataChanged();

        }  catch (IOException e) {

            e.printStackTrace();
            JOptionPane.showMessageDialog(new JFrame(), "Cant read from file", "Error", JOptionPane.ERROR_MESSAGE);

        }

    }

    private void initSaveMenuBtn() {

        JMenuItem saveMenuBtn = new JMenuItem("Save...");

        saveMenuBtn.addActionListener(e -> saveFileAction());

        menuItems.add(saveMenuBtn);
    }

    private void initOpenMenuBtn() {

        JMenuItem openMenuBtn = new JMenuItem("Open...");

        openMenuBtn.addActionListener(e -> readFileAction() );

        menuItems.add(openMenuBtn);
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(MainWindow::new);

    }
}
