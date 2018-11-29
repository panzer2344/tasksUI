package com.podval;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
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
        // Make an  instance of the DocumentBuilderFactory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            // use the factory to take an instance of the document builder
            DocumentBuilder db = dbf.newDocumentBuilder();
            // parse using the builder to get the DOM mapping of the
            // XML file
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
                    showError(new JFrame(), "cant read this file");

                } catch (IllegalAccessException e) {

                    e.printStackTrace();
                    showError(new JFrame(), "cant read this file");

                } catch (InvocationTargetException e) {

                    e.printStackTrace();
                    showError(new JFrame(), "cant read this file");

                }

                books.add(newBook);
            }

            /*role1 = getTextValue(role1, doc, "role1");
            if (role1 != null) {
                if (!role1.isEmpty())
                    rolev.add(role1);
            }
            role2 = getTextValue(role2, doc, "role2");
            if (role2 != null) {
                if (!role2.isEmpty())
                    rolev.add(role2);
            }
            role3 = getTextValue(role3, doc, "role3");
            if (role3 != null) {
                if (!role3.isEmpty())
                    rolev.add(role3);
            }
            role4 = getTextValue(role4, doc, "role4");
            if ( role4 != null) {
                if (!role4.isEmpty())
                    rolev.add(role4);
            }
            return true;*/

        } catch (ParserConfigurationException pce) {
            System.out.println(pce.getMessage());
        } catch (SAXException se) {
            System.out.println(se.getMessage());
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }

    }
}
