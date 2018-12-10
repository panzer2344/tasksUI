package com.podval;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.*;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

    private void showWarning(JFrame frame, String message){
        JOptionPane.showMessageDialog(frame, message, "Warning", JOptionPane.WARNING_MESSAGE);
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

        JFrame errorFrame = new JFrame();

        Book book = books.get(rowIndex);

        try {
            if(isValidCellInput(aValue, rowIndex, columnIndex)) {

                int answer = JOptionPane.showConfirmDialog(
                        errorFrame,
                        "Are you sure you want to change this field? ",
                        "Removing",
                        JOptionPane.YES_NO_OPTION);

                if(answer == JOptionPane.YES_OPTION) {

                    book.setFieldById(columnIndex, aValue);

                }else{
                    if("".equals(book.getFieldValueById(columnIndex))){
                        books.remove(rowIndex);
                        fireTableDataChanged();
                    }
                }

            }
            else{
                if("".equals(book.getTitle())){
                    books.remove(rowIndex);
                    showWarning(errorFrame, "Not valid input: " + book.inputHelpByFieldId(columnIndex));
                    fireTableDataChanged();
                }
                else {
                    showWarning(errorFrame, "Not valid input: " + book.inputHelpByFieldId(columnIndex));
                }
            }
        }
        catch (NoSuchMethodException nsme){
            nsme.printStackTrace();
            showError(errorFrame, "Error with finding handling method(NoSuchMethodException)");
        }
        catch (InvocationTargetException ite){
            ite.printStackTrace();
            showError(errorFrame, "Error with finding handling method(InvocationTargetException)");
        }
        catch (IllegalAccessException iae){
            iae.printStackTrace();
            showError(errorFrame, "Error with finding handling method(IllegalAccessException)");
        }
    }

    public void saveToFile(String fileName) throws IOException {

        Document dom;
        Element e = null;

        // instance of a DocumentBuilderFactory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            // use factory to get an instance of document builder
            DocumentBuilder db = dbf.newDocumentBuilder();
            // create instance of DOM
            dom = db.newDocument();

            // create the root element
            Element rootEle = dom.createElement("library");

            for(int i = 0; i < books.size(); i++){
                e = books.get(i).toXmlNode(dom, i);

                rootEle.appendChild(e);
            }

            dom.appendChild(rootEle);

            try {
                Transformer tr = TransformerFactory.newInstance().newTransformer();
                tr.setOutputProperty(OutputKeys.INDENT, "yes");
                tr.setOutputProperty(OutputKeys.METHOD, "xml");
                tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

                // send DOM to file
                tr.transform(new DOMSource(dom),
                        new StreamResult(new FileOutputStream(fileName)));

            } catch (TransformerException te) {

                te.printStackTrace();

            } catch (IOException ioe) {

                ioe.printStackTrace();

            }
        } catch (ParserConfigurationException pce) {

            pce.printStackTrace();

        }

    }

    public void readFromFile(String fileName) throws FileNotFoundException {
        Document dom;

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {

            DocumentBuilder db = dbf.newDocumentBuilder();

            dom = db.parse(fileName);

            Element doc = dom.getDocumentElement();

            NodeList bookNodes = doc.getElementsByTagName("book");

            //clear books ArrayList
            books.clear();

            for(int i = 0; i < bookNodes.getLength(); i++){

                Node bookNode = bookNodes.item(i);

                Book newBook = null;

                try {
                    newBook = new Book("").fromXmlNode(doc, bookNode);
                } catch (NoSuchMethodException e) {

                    e.printStackTrace();
                    showError(new JFrame(), "cant read this file(NoSuchMethodException)");

                } catch (IllegalAccessException e) {

                    e.printStackTrace();
                    showError(new JFrame(), "cant read this file(IllegalAccessException)");

                } catch (InvocationTargetException e) {

                    e.printStackTrace();
                    showError(new JFrame(), "cant read this file(InvocationTargetException)");

                }

                books.add(newBook);
            }

        } catch (ParserConfigurationException pce) {

            pce.printStackTrace();
            System.out.println(pce.getMessage());

        } catch (SAXException se) {

            se.printStackTrace();
            System.out.println(se.getMessage());

        } catch (IOException ioe) {

            ioe.printStackTrace();
            System.err.println(ioe.getMessage());

        }

    }


}
