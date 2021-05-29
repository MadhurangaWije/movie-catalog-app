package org.kanishkaw.moviecatalogservice.resources;


import org.kanishkaw.moviecatalogservice.models.Movie;
import org.kanishkaw.moviecatalogservice.models.Rating;
import org.kanishkaw.moviecatalogservice.models.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.kanishkaw.moviecatalogservice.models.CatalogItem;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WebClient.Builder webclientBuilder;

    @GetMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {

        // get all rated movie IDs
        UserRating userRatings = restTemplate.getForObject("http://rating-data-service/ratingsdata/users/" + userId, UserRating.class);

        // For each movie Id, call movie info service and get details
        return  userRatings.getUserRatings().stream().map(rating -> {
            Movie movie = webclientBuilder
                    .baseUrl("http://movie-info-service")
                    .build()
                    .get()
                    .uri("/movie/" + rating.getMovieId())
                    .retrieve()
                    .bodyToMono(Movie.class)
                    .block();
            return new CatalogItem(movie.getName(), "Movie description", rating.getRating());
        }).collect(Collectors.toList());

    }

}
