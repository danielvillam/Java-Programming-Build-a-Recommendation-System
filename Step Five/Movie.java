/** 
 * This class corresponds to the corresponding assignments in part one of the Duke University Java Programming: 
 * Build a Recommendation System course taught on Coursera.
 * It is a data object (PDO) class. The movie object information is save.
 * 
 * @author (https://github.com/danielvillam) 
 * @version (June 20, 2024)
 */

import java.util.ArrayList;
import java.util.Arrays;

// An immutable passive data object (PDO) to represent item data
public class Movie {
    private String id;
    private String title;
    private int year;
    private String genres;
    private String director;
    private String country;
    private String poster;
    private int minutes;

    public Movie (String anID, String aTitle, String aYear, String theGenres) {
        // just in case data file contains extra whitespace
        id = anID.trim();
        title = aTitle.trim();
        year = Integer.parseInt(aYear.trim());
        genres = theGenres;
    }

    public Movie (String anID, String aTitle, String aYear, String theGenres, String aCountry, String aDirector,
    String aPoster, int theMinutes) {
        // just in case data file contains extra whitespace
        id = anID.trim();
        title = aTitle.trim();
        year = Integer.parseInt(aYear.trim());
        genres = theGenres;
        director = aDirector;
        country = aCountry;
        poster = aPoster;
        minutes = theMinutes;
    }

    // Returns ID associated with this item
    public String getID () {
        return id;
    }

    // Returns title of this item
    public String getTitle () {
        return title;
    }

    // Returns year in which this item was published
    public int getYear () {
        return year;
    }

    // Returns genres associated with this item
    public String getGenres () {
        return genres;
    }
    
    // Returns country associated with this item
    public String getCountry(){
        return country;
    }
    
    // Returns the director associated with this item
    public String getDirector(){
        return director;
    }

    // Returns the poster associated with this item
    public String getPoster(){
        return poster;
    }
    
    // Returns minutes of duration associated with this item
    public int getMinutes(){
        return minutes;
    }

    // Returns a string of the item's information
    public String toString () {
        String result = "Movie [id=" + id + ", title=" + title + ", year=" + year;
        result += ", genres= " + genres + "]";
        return result;
    }
}
