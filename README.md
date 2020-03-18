# Popular Movies App

## Project Overview
This app displays grid arrangement of movie posters sorted by most popular, top rated and favorites category.
Movie details such as original title, plot synopsis, user rating, release date, trailers and reviews can be access if movie poster is tap. 

### Detailed app behavior
- When a trailer is selected, app uses an intent to launch the trailer
- In the movie details screen, toggle button can be use to mark movie as as favorite or unfavorite it
- Movie id, original title, poster, plot synopsis, user rating and release date of the favorite movies are stored using Room and are updated whenever user favorite or unfavorite a movie
- Favorites option in movie homescreen is allowed to be access without internet connectivity. It displays the entire favorites collection based on the movie ids stored in the database.


## Requirements
Stable release versions of all libraries, Gradle, and Android Studio.

## API KEY INSTRUCTIONS:
1. Create "configuration.xml" resource file under values directory
2. Add string name "movie_db_api_key" and add the moviedb api key value

     
        <string name="movie_db_api_key">[APIKEY]</string>

