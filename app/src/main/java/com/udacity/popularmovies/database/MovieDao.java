package com.udacity.popularmovies.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

@Dao
public interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovie(MovieEntity movieEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<MovieEntity> movieList);

    @Delete
    void deleteMovie(MovieEntity movieEntity);

    @Query("SELECT * FROM Movie WHERE id = :id")
    MovieEntity getMovieById(int id);

    @Query("SELECT * FROM Movie")
    LiveData<List<MovieEntity>> getAll();

    @Query("DELETE FROM Movie")
    int deleteAll();

    @Query("SELECT COUNT(*) FROM Movie")
    int getCount();

}
