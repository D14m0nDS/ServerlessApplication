package org.antoan.functions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Movie {
    private String title;
    private int year;
    private String genre;
    private String description;
    private String director;
    private String actors;

    // Default constructor for Gson

    public Movie() {}

}
