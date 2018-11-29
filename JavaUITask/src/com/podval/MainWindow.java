package com.podval;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.EventObject;
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
    private JMenu helpMenu;
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

        jfrm.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        initExitHandler();
    }

    private void initExitHandler(){
        jfrm.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int answer = JOptionPane.showConfirmDialog(
                        jfrm,
                        "Do you want to save current table? ",
                        "Closing",
                        JOptionPane.YES_NO_OPTION);

                if(answer == JOptionPane.YES_OPTION) {
                    saveFileAction();
                }

                System.exit(0);
            }
        });
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

        initTableCellEditors(table);
    }

    private void initTableCellEditors(JTable table) {

        for (int i = 0; i < table.getColumnCount(); i++) {

            if (Book.getTypeOfFieldById(i) == Genre.class)
                table.getColumnModel().getColumn(i).setCellEditor(new EnumCellEditor());

        }
    }

    private void dropHelpMessage(){

        System.out.println("dropHelpMenu");

        String helpMessage = "To edit field, double-click on it. \n" +
                " If you want to finish editing combobox field\n, use ENTER to accept changes" +
                " or use ESCAPE to discard";

        JOptionPane.showMessageDialog(jfrm, helpMessage, "Help", JOptionPane.INFORMATION_MESSAGE);

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

        /*btnAddToTable.addActionListener(e -> {

            NewRowFrame nrf = new NewRowFrame();

            books.add(new Book("newBook"));
            libraryTable.fireTableDataChanged();
        });*/

        btnAddToTable.addActionListener( e -> {

            books.add(new Book(""));
            libraryTable.fireTableDataChanged();

            table.requestFocus();
            table.editCellAt(books.size() - 1, 0);

            //libraryTable.setValueAt(table.getValueAt(books.size() - 1, 0), books.size() - 1, 0);

        });


        topPanelBtns.add(btnAddToTable);
    }

    private void initRemoveFromTableBtn() {

        JButton btnRemoveFromTable = new JButton("Remove");

        btnRemoveFromTable.addActionListener(e -> {

            int rowNum = table.getSelectedRow();

            int answer = JOptionPane.showConfirmDialog(
                    jfrm,
                    "Are you sure you want to remove this row? ",
                    "Removing",
                    JOptionPane.YES_NO_OPTION);

            if(answer == JOptionPane.YES_OPTION) {

                books.remove(rowNum);
                libraryTable.fireTableDataChanged();

            }

        });

        topPanelBtns.add(btnRemoveFromTable);
    }

    private void initMenuBar() {

        menuBar = new JMenuBar();

        menu = new JMenu("File");

        helpMenu = new JMenu("Help");
        initHelpMenu();


        initMenuItems();

        for (JMenuItem item : menuItems)
            menu.add(item);

        menuBar.add(menu);
        menuBar.add(helpMenu);

        jfrm.setJMenuBar(menuBar);

    }

    private void initHelpMenu(){

        helpMenu.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                dropHelpMessage();
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}

        });

    }

    private void initMenuItems() {

        menuItems = new ArrayList<>();

        initSaveMenuBtn();
        initOpenMenuBtn();

    }

    private String fileNameFromDialog() {

        String fileName = null;

        File currentDirFile = new File(".");
        String helper = currentDirFile.getAbsolutePath();
        String currentDir = helper.substring(0, helper.length() - currentDirFile.getName().length());

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(currentDir));

        int returnVal = fileChooser.showOpenDialog(jfrm);

        if (returnVal == JFileChooser.APPROVE_OPTION) {

            fileName = fileChooser.getSelectedFile().getAbsolutePath();

        }

        return fileName;
    }

    private void saveFileAction() {

        try {

            String fileName = fileNameFromDialog();

            if(fileName != null) {

                JOptionPane.showMessageDialog(jfrm, "File has been saved", "Saved", JOptionPane.INFORMATION_MESSAGE);
                libraryTable.saveToFile(fileName);

            } else {

                JOptionPane.showMessageDialog(jfrm, "Saving discarded", "Not saved", JOptionPane.INFORMATION_MESSAGE);

            }


        } catch (IOException e) {

            e.printStackTrace();
            JOptionPane.showMessageDialog(new JFrame(), "Cant save to file", "Error", JOptionPane.ERROR_MESSAGE);

        }

    }

    private void readFileAction() {

        int answer = JOptionPane.showConfirmDialog(
                jfrm,
                "Do you want to save current table? ",
                "Closing",
                JOptionPane.YES_NO_OPTION);

        if(answer == JOptionPane.YES_OPTION) {
            saveFileAction();
        }

        try {

            libraryTable.readFromFile(fileNameFromDialog());
            libraryTable.fireTableDataChanged();

        } catch (IOException e) {

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

        openMenuBtn.addActionListener(e -> readFileAction());

        menuItems.add(openMenuBtn);
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(MainWindow::new);

    }
}
