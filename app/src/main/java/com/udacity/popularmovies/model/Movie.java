package com.udacity.popularmovies.model;

public class Movie {
    private String originalTitle;
    private String imageThumbnail;
    private String plotSynopsis;
    private String userRating;
    private String releaseDate;

    public Movie(){
    }

    public Movie(String originalTitle, String imageThumbnail, String plotSynopsis, String userRating, String releaseDate){
        this.originalTitle = originalTitle;
        this.imageThumbnail = imageThumbnail;
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


    public String getImageThumbnail(){ return imageThumbnail;}

    public void setImageThumbnail(String imageThumbnail){ this.imageThumbnail = imageThumbnail; }


    public String getPlotSynopsis(){ return plotSynopsis;}

    public void setPlotSynopsis(String plotSynopsis){ this.plotSynopsis = plotSynopsis; }


    public String getUserRating(){ return userRating; }

    public void setUserRating(String userRating){ this.userRating = userRating;}


    public String getReleaseDate(){ return releaseDate; }

    public void setReleaseDate(String releaseDate){ this.releaseDate = releaseDate; }


}

