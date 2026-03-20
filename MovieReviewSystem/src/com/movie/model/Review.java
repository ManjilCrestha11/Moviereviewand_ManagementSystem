package com.movie.model;

public class Review {
    private int reviewID;
    private int movieID;
    private String userName;
    private int rating;
    private String comment;

    public Review(int reviewID, int movieID, String userName, int rating, String comment) {
        this.reviewID = reviewID;
        this.movieID = movieID;
        this.userName = userName;
        this.rating = rating;
        this.comment = comment;
    }

    public int getMovieID() { return movieID; }
    public String getUserName() { return userName; }
    public int getRating() { return rating; }
    public String getComment() { return comment; }
}