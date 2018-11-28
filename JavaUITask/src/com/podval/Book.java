package com.podval;


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

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public void setGenre(String string){
        this.genre = Genre.fromString(string);
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public boolean isValidTitle(String string){
        return !string.matches("[\\x00-\\x1F\\x7F]");
    }

    public boolean isValidAuthor(){
        return true;
    }

    public boolean isValidGenre(){
        return true;
    }

    public boolean isValidCost(){
        return true;
    }

    public static Field getFieldByName(String fName){
        try {
            return Book.class.getDeclaredField(fName);
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return null;
    }

    public static Field getFieldById(int id){
        try{
            return Book.class.getDeclaredFields()[id];
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return null;
    }

    public static int getFieldsNumber(){
        return Book.class.getDeclaredFields().length;
    }

    public void setFieldById(int id, Object object) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if(id > 0 && id < Book.getFieldsNumber()) {

            String fieldName = this.getClass().getDeclaredFields()[id].getName();
            String methodName = "set" + fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);

            Method method = this.getClass().getMethod(methodName, object.getClass());

            method.invoke(this, object);
        }
        else{
            throw new NoSuchMethodException();
        }
    }

    public static Class getTypeOfFieldById(int id){
        return Book.getFieldById(id).getClass();
    }


}
