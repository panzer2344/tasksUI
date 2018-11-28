package com.podval;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
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

    private boolean isValidCellInput(Object aValue, int row, int col){
        if( Book.getTypeOfFieldById(col) == aValue.getClass() ) {

        }
    }

    private void showError(JFrame frame){
        JOptionPane.showMessageDialog(frame, "Error with finding handling method", "Dialog", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

        JFrame errorFrame = new JFrame();

        Book book = books.get(rowIndex);

        try {
            book.setFieldById(columnIndex, aValue);
        }
        catch (NoSuchMethodException nsme){
            nsme.printStackTrace();
            showError(errorFrame);
        }
        catch (InvocationTargetException ite){
            ite.printStackTrace();
            showError(errorFrame);
        }
        catch (IllegalAccessException iae){
            iae.printStackTrace();
            showError(errorFrame);
        }
    }

    public void saveToFile(String fileName){

    }

    public void readFromFile(String fileName){

    }

}
