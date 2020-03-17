package com.udacity.popularmovies.database;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 *  This class includes the database tables for room
 */

@Entity(tableName = "Movie")
public class MovieEntity {

    @PrimaryKey
    private int id = 0;
    private String originalTitle;
    private String poster;
    private String plotSynopsis;
    private String userRating;
    private String releaseDate;

    @Ignore
    public MovieEntity() {
    }

    public MovieEntity(int id, String originalTitle, String poster, String plotSynopsis, String userRating, String releaseDate) {
        this.id = id;
        this.originalTitle = originalTitle;
        this.poster = poster;
        this.plotSynopsis = plotSynopsis;
        this.userRating = userRating;
        this.releaseDate = releaseDate;
    }

    @Ignore
    public MovieEntity(String originalTitle, String poster, String plotSynopsis, String userRating, String releaseDate){
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", originalTitle='" + originalTitle + '\'' +
                ", poster='" + poster + '\'' +
                ", plotSynopsis='" + plotSynopsis + '\'' +
                ", userRating='" + userRating + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                '}';
    }
}


