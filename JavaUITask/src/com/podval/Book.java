package com.podval;


import org.w3c.dom.*;

import javax.print.Doc;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.FileAlreadyExistsException;
import java.util.regex.Pattern;

public class Book {
    private String title = "";
    private Author author = Author.DEFAULT_AUTHOR;
    private Genre genre = Genre.UNKNOWN;
    private Integer cost = 0;

    public Book(String title, Author author, Genre genre, Integer cost) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.cost = cost;
    }

    public Book(String title) {
        this.title = title;
    }

    public Book(String title, Integer cost) {
        this.title = title;
        this.cost = cost;
    }

    public Book(String title, Author author) {
        this.title = title;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public void setAuthor(String string) {
        String[] splited = string.split(" ");

        this.author = new Author(splited[0], splited[1]);
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public void setGenre(String string) {
        this.genre = Genre.fromString(string);
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public void setCost(String string) {
        this.cost = Integer.parseInt(string);
    }

    public boolean isValidTitle(String string) {

        if ("".equals(string))
            return false;

        return string.matches("^[а-яА-Яa-z\\sA-Z\\-0-9]*$");
    }

    public boolean isValidAuthor(String string) {

        String[] splitStr = string.split(" ");

        if (splitStr.length != 2)
            return false;

        if ("".equals(splitStr[0])) {
            return false;
        } else {
            if ("".equals(splitStr[1])) {
                return false;
            }
        }

        return splitStr[0].matches("^[а-яА-Яa-z\\sA-Z\\-]*$") &&
                splitStr[1].matches("^[а-яА-Яa-z\\sA-Z\\-]*$");
    }

    public boolean isValidGenre(String string) {

        try {
            Genre.fromString(string);
        } catch (IllegalArgumentException iae) {
            iae.printStackTrace();
            return false;
        }

        return true;

    }

    public boolean isValidCost(String string) {

        try {
            if (Integer.parseInt(string) < 0) {
                return false;
            }
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
            return false;
        }

        return true;

    }

    public String titleInputHelp() {
        return "Title must be a not empty string of letters and numbers";
    }

    public String authorInputHelp() {
        return "Author field must be a not empty string of two words of letters";
    }

    public String GenreInputHelp() {
        return "Genre must be equal to one of genre combobox elements";
    }

    public String costInputHelp() {
        return "Cost must be an integer number greater than zero";
    }

    public static Field getFieldByName(String fName) {
        try {
            return Book.class.getDeclaredField(fName);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public static Field getFieldById(int id) {
        try {
            return Book.class.getDeclaredFields()[id];
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public static int getFieldsNumber() {
        return Book.class.getDeclaredFields().length;
    }

    public void setFieldById(int id, Object object) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (id >= 0 && id < Book.getFieldsNumber()) {

            String fieldName = this.getClass().getDeclaredFields()[id].getName();
            String methodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);

            Method method = this.getClass().getMethod(methodName, object.getClass());

            method.invoke(this, object);
        } else {
            throw new NoSuchMethodException();
        }
    }

    public void setFieldByName(String fieldName, Object object) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (fieldName != null && !fieldName.equals("")) {

            String methodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);

            Method method = this.getClass().getMethod(methodName, object.getClass());

            method.invoke(this, object);
        } else {
            throw new NoSuchMethodException();
        }
    }

    public static Class getTypeOfFieldById(int id) {
        return Book.getFieldById(id).getType();
    }

    public boolean isValidInput(int id, Object object) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        if (id >= 0 && id < Book.getFieldsNumber()) {

            String fieldName = this.getClass().getDeclaredFields()[id].getName();
            String methodName = "isValid" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);

            Method method = this.getClass().getMethod(methodName, object.getClass());

            return (boolean) method.invoke(this, object);

        } else {
            throw new NoSuchMethodException();
        }

    }

    public String getFieldValueById(int id) throws NoSuchMethodException{

        Object result = null;

        if (id >= 0 && id < Book.getFieldsNumber()) {

            String fieldName = this.getClass().getDeclaredFields()[id].getName();
            String methodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);

            Method method = null;
            try {
                method = this.getClass().getMethod(methodName);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }

            try {
                result = method.invoke(this);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } else {
            throw new NoSuchMethodException();
        }

        return result.toString();
    }

    private void addXmlParameter(Document doc, Element bookNode, String name, String value) {
        Element parameter = doc.createElement(name);
        Text textValue = doc.createTextNode(value);
        parameter.appendChild(textValue);
        bookNode.appendChild(parameter);
    }

    public Element toXmlNode(Document doc, int number) {

        Text textValue = null;
        Element parameter = null;

        // book
        Element bookNode = doc.createElement("book");
        bookNode.setAttribute("number", "" + number);

        // title
        addXmlParameter(doc, bookNode, "title", this.title);

        // author
        addXmlParameter(doc, bookNode, "author", this.author.toString());

        //genre
        addXmlParameter(doc, bookNode, "genre", this.genre.toString());

        //cost
        addXmlParameter(doc, bookNode, "cost", this.cost.toString());

        return bookNode;

    }

    public Book fromXmlNode(Element dom, Node bookNode) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        NodeList childNodes = bookNode.getChildNodes();

        for (int i = 1; i < childNodes.getLength(); i += 2) {

            Node currentNode = childNodes.item(i);
            String fieldName = currentNode.getNodeName();
            String fieldValue = currentNode.getTextContent();

            this.setFieldByName(fieldName, fieldValue);

        }

        return this;
    }


    public String inputHelpByFieldId(int id) {

        String result = "";

        String fieldName = this.getClass().getDeclaredFields()[id].getName();
        String methodName = fieldName + "InputHelp";

        Method method = null;
        try {

            method = this.getClass().getMethod(methodName);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        try {

            result = (String) method.invoke(this);

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return result;

    }

}
