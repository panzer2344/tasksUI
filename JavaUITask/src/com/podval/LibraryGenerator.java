package com.podval;

import java.util.ArrayList;

public class LibraryGenerator {

    public static LibraryTable generate(ArrayList<Book> books) {

        books.add(new Book("B1", new Author("Scooby", "Doo"), Genre.ARTISTIC_LITERATURE, 1));
        books.add(new Book("B2", new Author("Iosif", "Stalin"), Genre.FANTASY, 2));
        books.add(new Book("B3", new Author("Winston", "Cherchil"), Genre.MANUAL, 3));
        books.add(new Book("B4", new Author("Mao", "ZeDoon"), Genre.SCIENCE_FICTION, 4));
        books.add(new Book("B5", new Author("Mr", "Smith"), Genre.UNKNOWN, 5));
        books.add(new Book("B6", new Author("Neo", "FromMatrix"), Genre.ARTISTIC_LITERATURE, 6));

        books.add(new Book("B7"));
        books.add(new Book("B8", new Author("Lev", "Tolstoi")));
        books.add(new Book("B9", 9));

        LibraryTable libraryTable = new LibraryTable(books);

        return libraryTable;
    }

}
