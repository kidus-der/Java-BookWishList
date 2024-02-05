package com.example.kidusder_mybookwishlist;

// the following class contains the properties
// of a book description
public class Book {

    //variables used in the description of a book
    private String title;
    private String author;
    private String genre;
    private int publication_year;
    private boolean isRead;

    // constructor for a book description
    public Book(String title, String author, String genre, int publication_year, boolean isRead) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.publication_year = publication_year;
        this.isRead = isRead;
    }

    // getters for each attribute
    public String getTitle() {
        return title;
    }
    public String getAuthor() {
        return author;
    }
    public String getGenre() {
        return genre;
    }
    public int getPublicationYear() {
        return publication_year;
    }
    public boolean isRead() {
        return isRead; // true or false value used in adapter ternary operator
    }


    // setters for attributes
    public void setTitle(String title) {

        // check for valid entry in title edit text
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty");
        } else if (title.length() > 50) { // enforce the 50 character max rule
            throw new IllegalArgumentException("Title cannot exceed 50 characters");
        }
        this.title = title;
    }
    public void setAuthor(String author) {

        // check for valid entry
        if (author == null || author.trim().isEmpty()) {
            throw new IllegalArgumentException("Author cannot be empty");
        } else if (author.length() > 30) { // author name not longer than 30 characters
            throw new IllegalArgumentException("Author cannot exceed 30 characters");
        }
        this.author = author;
    }
    public void setGenre(String genre) {

        // check if genre is left empty by user
        if (genre == null || genre.trim().isEmpty()) {
            throw new IllegalArgumentException("Genre cannot be empty");
        }
        this.genre = genre;
    }
    public void setPublication_year(int publication_year) {

        // we're told publication year must be
        // four digits
        if (publication_year < 1000 || publication_year > 9999) {
            throw new IllegalArgumentException("Invalid publication year");
        }
        this.publication_year = publication_year;
    }
    public void setRead(boolean read) {
        isRead = read;
    }

}
