package org.kanishkaw.ratingdataservice.resource;

import org.kanishkaw.ratingdataservice.models.Rating;
import org.kanishkaw.ratingdataservice.models.UserRating;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/ratingsdata")
public class RatingResource {

    @GetMapping("/{movieId}")
    public Rating getRating(@PathVariable("movieId") String movieId) {
        return new Rating(movieId, 3);
    }

    @GetMapping("/users/{userId}")
    public UserRating getUserRatings(@PathVariable("userId") String userId) {
        List<Rating> ratings = Arrays.asList(
                new Rating("1223", 3),
                new Rating("1223", 4),
                new Rating("1223", 2)
        );
        return new UserRating(ratings);
    }
}
