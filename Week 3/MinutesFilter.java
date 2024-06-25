/** 
 * This class corresponds to the corresponding assignments in part three of the Duke University Java Programming: 
 * Build a Recommendation System course taught on Coursera.
 * The movies are filtered between a minimum and maximum duration.
 *  
 * @author (https://github.com/danielvillam) 
 * @version (June 24, 2024)
 */

public class MinutesFilter implements Filter{
    private int myMinMinutes;
    private int myMaxMinutes;
    public MinutesFilter(int minMinutes, int maxMinutes){
        myMinMinutes = minMinutes;
        myMaxMinutes = maxMinutes;
    }
    
    public boolean satisfies(String id){
        return MovieDatabase.getMinutes(id) >= myMinMinutes && 
               MovieDatabase.getMinutes(id) <= myMaxMinutes;
    }
}
