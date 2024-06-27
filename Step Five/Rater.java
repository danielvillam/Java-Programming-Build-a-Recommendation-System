/** 
 * This class corresponds to the corresponding assignments in part three of the Duke University Java Programming: 
 * Build a Recommendation System course taught on Coursera.
 * Interface Rater.
 * 
 * @author (https://github.com/danielvillam) 
 * @version (June 24, 2024)
 */

import org.apache.commons.csv.*;
import java.util.*;

public interface Rater {
    public void addRating(String item, double rating);
    
    public boolean hasRating(String item);
    
    public HashMap<String,Rating> getMyRating();
    
    public String getID();
    
    public double getRating(String item);

    public int numRatings();
    
    public ArrayList<String> getItemsRated();
}
