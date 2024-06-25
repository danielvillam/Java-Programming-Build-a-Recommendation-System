/** 
 * This class corresponds to the corresponding assignments in part three of the Duke University Java Programming: 
 * Build a Recommendation System course taught on Coursera.
 * It is a copy of the Rater class with efficiency implementations.
 *
 * @author (https://github.com/danielvillam) 
 * @version (June 24, 2024)
 */

import org.apache.commons.csv.*;
import java.util.*;

public class EfficientRater implements Rater{
    private String myID;
    private HashMap<String,Rating> myRatings;
    
    public EfficientRater(String id) {
        myID = id;
        myRatings = new HashMap<String,Rating>();
    }
    
    // Add a new rating to the array
    public void addRating(String item, double rating) {
        Rating newRat = new Rating(item,rating);
        myRatings.put(newRat.getItem(),new Rating(item,rating));
    }
    
    // Ratings return.
    public HashMap<String,Rating> getaRating(){
        return myRatings;
    }
    
    // There is or is not a rating.
    public boolean hasRating(String item) {
        if (myRatings.containsKey(item)){
            return true;
        }
        return false;
    }
    
    public String getID() {
        return myID;
    }
    
    // Specific rating of a movie.
    public double getRating(String item) {
        for(int k=0; k < myRatings.size(); k++){
            if (myRatings.get(k).getItem().equals(item)){
                return myRatings.get(k).getValue();
            }
        }
        return -1;
    }
    
    // Number of ratings.
    public int numRatings() {
        return myRatings.size();
    }
    
    // Ratings are returned.
    public ArrayList<String> getItemsRated() {
        ArrayList<String> list = new ArrayList<String>();
        for(int k=0; k < myRatings.size(); k++){
            list.add(myRatings.get(k).getItem());
        }
        return list;
    }
}
