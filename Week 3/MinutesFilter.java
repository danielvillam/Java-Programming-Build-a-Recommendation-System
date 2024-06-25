/** 
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
