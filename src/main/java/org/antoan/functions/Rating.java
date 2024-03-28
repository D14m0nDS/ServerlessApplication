package org.antoan.functions;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
@Getter
@Setter
public class Rating {
    private String title;
    private String opinion;
    private int rating;
    private OffsetDateTime timestamp;
    private String author;

    // Default constructor for Gson
    public Rating() {}

}
