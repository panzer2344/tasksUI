package com.podval;

import java.util.ArrayList;

public class LibraryGenerator {

    public static LibraryTable generate(ArrayList<Book> books) {

        books.add(new Book("B1", new Author("Afn1", "Aln1"), Genre.ARTISCTIC_LITERATURE, 1));
        books.add(new Book("B2", new Author("Afn2", "Aln2"), Genre.FANTASY, 2));
        books.add(new Book("B3", new Author("Afn3", "Aln3"), Genre.MANUAL, 3));
        books.add(new Book("B4", new Author("Afn4", "Aln4"), Genre.SCIENCE_FICTION, 4));
        books.add(new Book("B5", new Author("Afn5", "Aln5"), Genre.UNKNOWN, 5));
        books.add(new Book("B6", new Author("Afn6", "Aln6"), Genre.ARTISCTIC_LITERATURE, 6));

        books.add(new Book("B7"));
        books.add(new Book("B8", new Author("Afn8", "Aln8")));
        books.add(new Book("B9", 9));

        LibraryTable libraryTable = new LibraryTable(books);

        return libraryTable;
    }

}
