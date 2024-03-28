package org.antoan.functions;

import com.google.gson.Gson;
import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;
import org.antoan.functions.Movie;
import org.antoan.functions.Rating;
import java.util.Optional;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class HttpTriggerJava {
    private Connection getDatabaseConnection() throws SQLException {
        String url = "jdbc:postgresql://<hostname>:<port>/<database>";
        String username = "<username>";
        String password = "<password>";

        return DriverManager.getConnection(url, username, password);
    }

    @FunctionName("movieFunction")
    public HttpResponseMessage run(
            @HttpTrigger(
                    name = "req",
                    methods = {HttpMethod.POST},
                    route = "{action}",
                    authLevel = AuthorizationLevel.ANONYMOUS) HttpRequestMessage<Optional<String>> request,
            @BindingName("action") String action,
            final ExecutionContext context) {

        context.getLogger().info("Java HTTP trigger processed a request.");

        final String json = request.getBody().orElse(null);

        if (json == null) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("Please pass a body").build();
        }

        if ("movie".equals(action)) {
            Movie movie = new Gson().fromJson(json, Movie.class);
            insertMovieIntoDatabase(movie);
            return request.createResponseBuilder(HttpStatus.OK).body("Movie created").build();
        } else if ("rating".equals(action)) {
            Rating rating = new Gson().fromJson(json, Rating.class);
            insertRatingIntoDatabase(rating);
            return request.createResponseBuilder(HttpStatus.OK).body("Rating submitted").build();
        } else {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("Action not recognized").build();
        }
    }

    private void insertMovieIntoDatabase(Movie movie) {
        String sql = "INSERT INTO movies (title, year, genre, description, director, actors) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = getDatabaseConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, movie.getTitle());
            pstmt.setInt(2, movie.getYear());
            pstmt.setString(3, movie.getGenre());
            pstmt.setString(4, movie.getDescription());
            pstmt.setString(5, movie.getDirector());
            pstmt.setString(6, movie.getActors());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertRatingIntoDatabase(Rating rating) {
        String sql = "INSERT INTO ratings (title, opinion, rating, timestamp, author) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = getDatabaseConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, rating.getTitle());
            pstmt.setString(2, rating.getOpinion());
            pstmt.setInt(3, rating.getRating());
            pstmt.setObject(4, rating.getTimestamp());
            pstmt.setString(5, rating.getAuthor());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}