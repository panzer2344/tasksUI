package com.podval;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class LibraryTable extends AbstractTableModel {

    private ArrayList<Book> books = null;

    LibraryTable(ArrayList<Book> books){
        this.books = books;
    }

    @Override
    public int getRowCount() {
        return books.size();
    }

    @Override
    public int getColumnCount() {
        return Book.getFieldsNumber();
    }

    @Override
    public String getColumnName(int c){
        String fieldName = "";

        fieldName = Book.getFieldById(c).getName();

        return fieldName;

        //return  fieldNameParts[fieldNameParts.length - 1];
        //return  fieldNameParts;
    }

    @Override
    public Object getValueAt(int r, int c) {
        Field field = Book.getFieldById(c);
        field.setAccessible(true);

        try {
            return field.get(books.get(r));
        } catch (Exception ex){
            ex.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean isCellEditable(int row, int col){
        return true;
    }

    private boolean isValidCellInput(Object aValue, int row, int col) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        return books.get(row).isValidInput(col, aValue);
    }

    private void showError(JFrame frame, String message){
        JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

        JFrame errorFrame = new JFrame();

        Book book = books.get(rowIndex);

        try {
            if(isValidCellInput(aValue, rowIndex, columnIndex)) {
                book.setFieldById(columnIndex, aValue);
            }
            else{
                showError(errorFrame, "Not valid input");
            }
        }
        catch (NoSuchMethodException nsme){
            nsme.printStackTrace();
            showError(errorFrame, "Error with finding handling method");
        }
        catch (InvocationTargetException ite){
            ite.printStackTrace();
            showError(errorFrame, "Error with finding handling method");
        }
        catch (IllegalAccessException iae){
            iae.printStackTrace();
            showError(errorFrame, "Error with finding handling method");
        }
    }

    public void saveToFile(String fileName) throws IOException {

        Gson gson = new Gson();

        System.out.println("gson created");

        gson.toJson(books, new FileWriter(fileName));
        //gson.toJson(books, new FileWriter("library.json"));
    }

    public void readFromFile(String fileName) throws FileNotFoundException {

        Gson gson = new Gson();

        books = gson.fromJson(new FileReader(fileName), new TypeToken<ArrayList<Book>>(){}.getType());
        //books = gson.fromJson(new FileReader("library.json"), books.getClass());
    }

}
