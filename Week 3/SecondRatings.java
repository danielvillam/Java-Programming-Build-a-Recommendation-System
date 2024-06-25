/** 
 * @author (https://github.com/danielvillam) 
 * @version (June 21, 2024)
 */

import java.util.*;

// Class requested by assignment
public class SecondRatings {
    private ArrayList<Movie> myMovies;
    private ArrayList<Rater> myRaters;

    private HashMap<String, HashMap<String,Rating>> loadRaters;
    
    public SecondRatings() {
        // default constructor
        this("ratedmovies_short.csv", "ratings_short.csv");
    }
    
    // Constructor to load files with firstRatings class
    public SecondRatings(String moviefile, String ratingsfile){
        FirstRatings fr = new FirstRatings();
        
        myMovies = fr.loadMovies(moviefile);
        myRaters = fr.csvRead(ratingsfile);
        loadRaters = fr.loadRaters(ratingsfile);
    }
    
    // Number of movies
    public int getMovieSize(){
        return myMovies.size();
    }
    
    // Number of rating 
    public int getRaterSize(){
        return loadRaters.size();
    }

    // Average by movie ID
    public double getAverageByID(String id, int minimalRaters){
        double count = 0;
        double numRatings = 0;
        double average = 0;
        
        if(minimalRaters == 0){
            return 0.0;
        }
        for(HashMap<String,Rating> rating : loadRaters.values()){
            for(Rating rat : rating.values()){
                if(rat.getItem().equals(id)){
                    double value = rat.getValue();
                    numRatings++;
                    count = count + value;
                }
            }
        }
            
        if(numRatings< minimalRaters){
            return -1;
        }
        else{
            average = count/numRatings;
            return average;
        }
    }
    
    // Average with minimum number of raters
    public ArrayList<Rating> getAverageRatings(int minimalRaters){
        ArrayList<Rating> averageRatings = new ArrayList<Rating>();
        for(Movie m : myMovies){
            String movie_id = m.getID();
            getAverageByID(movie_id,minimalRaters);
            Rating a = new Rating(movie_id, getAverageByID(movie_id,minimalRaters));
            if(a.getValue() > -1){
                averageRatings.add(a);
            }
        }
        return averageRatings;
    }
    
    public String getTitle(String id){
        for(Movie m : myMovies){
            if(id.equals(m.getID())){
                return m.getTitle();
            }
        }
        return "Movie ID not found in the database";
    }
    
    public String getID(String title){
        for(Movie m : myMovies){
            if(title.equals(m.getTitle())){
                return m.getID();
            }
        }
        return "NO SUCH TITLE.";
    }
}
