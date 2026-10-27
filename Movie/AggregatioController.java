package com.example.demo;

import java.util.List;

import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@RestController
@RequestMapping("/movies")
@CrossOrigin(origins = "http://localhost:5173")
public class MovieController {

    private final MovieRepository repo;
    private final MongoTemplate mongoTemplate;

    public MovieController(
            MovieRepository repo,
            MongoTemplate mongoTemplate) {
        this.repo = repo;
        this.mongoTemplate = mongoTemplate;
    }

    // ADD MOVIE

    @PostMapping
    public Movie addMovie(@RequestBody Movie movie) {
        return repo.save(movie);
    }

    // GET ALL MOVIES

    @GetMapping
    public List<Movie> getMovies() {
        return repo.findAll();
    }

    // GET MOVIE BY ID

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovie(@PathVariable String id) {
        return repo.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // UPDATE MOVIE

    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(
            @PathVariable String id,
            @RequestBody Movie movie) {

        if (!repo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        movie.setId(id);
        return ResponseEntity.ok(repo.save(movie));
    }

    // DELETE MOVIE

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable String id) {
        if (!repo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // FULLY BOOKED MOVIES: availableSeats = 0

    @GetMapping("/fully-booked")
    public List<Movie> getFullyBookedMovies() {

        Aggregation aggregation = newAggregation(
                match(Criteria.where("availableSeats").is(0))
        );

        return mongoTemplate.aggregate(
                aggregation, "movie", Movie.class
        ).getMappedResults();
    }

    // JOIN MOVIES WITH BOOKINGS

    @GetMapping("/with-bookings")
    public List<Document> getMoviesWithBookings() {

        LookupOperation lookup = lookup(
                "booking",
                "_id",
                "movieId",
                "bookingDetails"
        );

        Aggregation aggregation = newAggregation(lookup);

        AggregationResults<Document> results =
                mongoTemplate.aggregate(
                        aggregation, "movie", Document.class
                );

        return results.getMappedResults();
    }

    // MOVIES THAT HAVE AT LEAST ONE ACTIVE BOOKING
    // Each movie appears only once.

    @GetMapping("/booked")
    public List<Movie> getBookedMovies() {

        LookupOperation lookup = lookup(
                "booking",
                "_id",
                "movieId",
                "bookingDetails"
        );

        Aggregation aggregation = newAggregation(
                lookup,
                match(Criteria.where("bookingDetails.status").is("booked"))
        );

        return mongoTemplate.aggregate(
                aggregation, "movie", Movie.class
        ).getMappedResults();
    }

    // MOVIES WITH AVAILABLE SEATS

    @GetMapping("/available")
    public List<Movie> getAvailableMovies() {

        Aggregation aggregation = newAggregation(
                match(Criteria.where("availableSeats").gt(0))
        );

        return mongoTemplate.aggregate(
                aggregation, "movie", Movie.class
        ).getMappedResults();
    }
}
