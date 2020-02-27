package com.udacity.popularmovies.model;


//TODO: Investigate if this model is needed for Project Part 2 Implementation

public class Movie {


    private String originalTitle;
    private String poster;
    private String plotSynopsis;
    private String userRating;
    private String releaseDate;



    public Movie(String originalTitle, String poster, String plotSynopsis, String userRating, String releaseDate){
        this.originalTitle = originalTitle;
        this.poster = poster;
        this.plotSynopsis = plotSynopsis;
        this.userRating = userRating;
        this.releaseDate = releaseDate;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }


    public String getPoster(){ return poster;}

    public void setPoster(String poster){ this.poster = poster; }


    public String getPlotSynopsis(){ return plotSynopsis;}

    public void setPlotSynopsis(String plotSynopsis){ this.plotSynopsis = plotSynopsis; }


    public String getUserRating(){ return userRating; }

    public void setUserRating(String userRating){ this.userRating = userRating;}


    public String getReleaseDate(){ return releaseDate; }

    public void setReleaseDate(String releaseDate){ this.releaseDate = releaseDate; }
}


