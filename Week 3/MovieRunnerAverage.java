/** 
 * @author (https://github.com/danielvillam) 
 * @version (June 24, 2024)
 */

import java.util.*;

// Class requested by assignment
public class MovieRunnerAverage {
    
    // Important data for evaluation assignment
    public void printAverageRatings(){
        SecondRatings sr = new SecondRatings("ratedmoviesfull.csv", "ratings.csv");
        System.out.println("Number of total movies: " + sr.getMovieSize());
        System.out.println("Number of total of Raters: " + sr.getRaterSize());
        
        // Modifiable item for the minimum ratings
        int minimum = 50;
        
        ArrayList<Rating> movies = sr.getAverageRatings(minimum);
        Collections.sort(movies);
        for(Rating r : movies){
             String item = r.getItem();
             String movieTitle = sr.getTitle(item);
             System.out.println((double)Math.round(r.getValue() * 10000d) / 10000d+ " " + movieTitle);
        }

        System.out.println("Movies with at least " + minimum + " ratings: " + movies.size());
    }
    
    
    // Facts about a specific movie
    public void getAverageRatingOneMovie(){
        SecondRatings sr = new SecondRatings("ratedmoviesfull.csv", "ratings.csv");
        String movieRequest = "Moneyball";
        String id = sr.getID(movieRequest);
         
        if(id.equals("NO SUCH TITLE.")){
            System.out.println(movieRequest + " Not found.");
        }
        else{
            double aveRating = sr.getAverageByID(id,1);
            System.out.println("The average rating for the movie " + movieRequest + " : " + aveRating);
        }
    }

}
