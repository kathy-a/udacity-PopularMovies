package com.udacity.popularmovies.utilies;

import java.util.ArrayList;
import java.util.List;

import com.udacity.popularmovies.database.MovieEntity;


public class SampleData {

    private static final String SAMPLE_TITLE_1 = "Inception";
    private static final String SAMPLE_TITLE_2 = "DEADPOOL";
    private static final String SAMPLE_TITLE_3 = "AVENGERS";

    private static final String SAMPLE_POSTER_1 = "https://image.tmdb.org/t/p/w185/qmDpIHrmpJINaRKAfWQfftjCdyi.jpg";
    private static final String SAMPLE_POSTER_2 = "https://image.tmdb.org/t/p/w185/inVq3FRqcYIRl2la8iZikYYxFNR.jpg";
    private static final String SAMPLE_POSTER_3 = "https://image.tmdb.org/t/p/w185/cezWGskPY5x7GaglTTRN4Fugfb8.jpg";

    private static final String SAMPLE_PLOT_1 = "A simple plot";
    private static final String SAMPLE_PLOT_2 = "A plot with a\nline feed";
    private static final String SAMPLE_PLOT_3 = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.\n\n" +
            "Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?";

    private static final String SAMPLE_RATING_1 = "10/10";
    private static final String SAMPLE_RATING_2 = "8/10";
    private static final String SAMPLE_RATING_3 = "5/10";

    private static final String SAMPLE_DATE_1 = "2010-07-15";
    private static final String SAMPLE_DATE_2 = "2020-08-08";
    private static final String SAMPLE_DATE_3 = "2030-011-11";


    public static List<MovieEntity> getSampleMovieData() {
        List<MovieEntity> movie = new ArrayList<>();


        movie.add(new MovieEntity( 0, SAMPLE_TITLE_1, SAMPLE_POSTER_1, SAMPLE_PLOT_1, SAMPLE_RATING_1, SAMPLE_DATE_1));
        movie.add(new MovieEntity( 1, SAMPLE_TITLE_2, SAMPLE_POSTER_2, SAMPLE_PLOT_2, SAMPLE_RATING_2, SAMPLE_DATE_2));
        movie.add(new MovieEntity( 2, SAMPLE_TITLE_3, SAMPLE_POSTER_3, SAMPLE_PLOT_3, SAMPLE_RATING_3, SAMPLE_DATE_3));


        return movie;
    }



}
