package org.example;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class StreamsExample {

    public static void main(final String[] args) {

        List<Author> authors = Library.getAuthors();
        
        banner("Authors information");
        // SOLVED With functional interfaces declared
        Consumer<Author> authorPrintConsumer = new Consumer<Author>() {
            @Override
            public void accept(Author author) {
                System.out.println(author);
            }
        };
        authors
            .stream()
            .forEach(authorPrintConsumer);

        // SOLVED With functional interfaces used directly
        authors
            .stream()
            .forEach(System.out::println);

        banner("Active authors");
        // TODO With functional interfaces declared
        Predicate<Author> predicate=(author)-> author.active;
        for(Author author:authors){
            if (predicate.test(author)) System.out.println(author);
        }

        banner("Active authors - lambda");
        // TODO With functional interfaces used directly
        authors.stream().filter(predicate).forEach(author -> System.out.println(author));

        banner("Active books for all authors");
        // TODO With functional interfaces declared
        Predicate<Book> bookPredicate=(book)->book.published;
        for(Author author:authors){

            if(author.books.stream().anyMatch(bookPredicate)) {
                System.out.println("Author name : " + author.name);
                for (Book book : author.books) {
                    if (bookPredicate.test(book)) {
                        System.out.println(book);
                    }
                }
            }
        }

        banner("Active books for all authors - lambda");
        // TODO With functional interfaces used directly
        authors.forEach(author -> {
            if(author.books.stream().anyMatch(bookPredicate)){
                System.out.println("Author name : " + author.name);
                author.books.stream().filter(bookPredicate).forEach(System.out::println);
            }
        });


        banner("Average price for all books in the library");
        // TODO With functional interfaces declared
        double avg=0.0;
        int count=0;
        for (Author author:authors){
            for (Book book:author.books){
                avg+=book.price;
                count++;
            }
        }
        System.out.println(avg/count);


        banner("Average price for all books in the library - lambda");
        // TODO With functional interfaces used directly
        double averagePrice = authors.stream()
                .flatMap(author -> author.books.stream())
                .mapToInt(book -> book.price)
                .average()
                .orElse(0.0);
        System.out.println(averagePrice);



        banner("Active authors that have at least one published book");
        // TODO With functional interfaces declared
        for(Author author:authors){
            if(author.books.stream().anyMatch(bookPredicate)) {
                System.out.println("Author name : " + author.name);
            }
        }

        banner("Active authors that have at least one published book - lambda");
        // TODO With functional interfaces used directly
        authors.forEach(author -> {
            if(author.books.stream().anyMatch(bookPredicate)){
                System.out.println("Author name : " + author.name);
            }
        });

    }

    private static void banner(final String m) {
        System.out.println("#### " + m + " ####");
    }
}


class Library {
    public static List<Author> getAuthors() {
        return Arrays.asList(
            new Author("Author A", true, Arrays.asList(
                new Book("A1", 100, true),
                new Book("A2", 200, true),
                new Book("A3", 220, true))),
            new Author("Author B", true, Arrays.asList(
                new Book("B1", 80, true),
                new Book("B2", 80, false),
                new Book("B3", 190, true),
                new Book("B4", 210, true))),
            new Author("Author C", true, Arrays.asList(
                new Book("C1", 110, true),
                new Book("C2", 120, false),
                new Book("C3", 130, true))),
            new Author("Author D", false, Arrays.asList(
                new Book("D1", 200, true),
                new Book("D2", 300, false))),
            new Author("Author X", true, Collections.emptyList()));
    }
}

class Author {
    String name;
    boolean active;
    List<Book> books;

    Author(String name, boolean active, List<Book> books) {
        this.name = name;
        this.active = active;
        this.books = books;
    }

    @Override
    public String toString() {
        return name + "\t| " + (active ? "Active" : "Inactive");
    }
}

class Book {
    String name;
    int price;
    boolean published;

    Book(String name, int price, boolean published) {
        this.name = name;
        this.price = price;
        this.published = published;
    }

    @Override
    public String toString() {
        return name + "\t| " + "\t| $" + price + "\t| " + (published ? "Published" : "Unpublished");
    }
}
