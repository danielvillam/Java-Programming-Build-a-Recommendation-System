/** 
 * This class corresponds to the corresponding assignments in part three of the Duke University Java Programming: 
 * Build a Recommendation System course taught on Coursera.
 * Movies are filtered for a specific YearAfter.
 *  
 * @author (https://github.com/danielvillam) 
 * @version (June 24, 2024)
 */

public class YearAfterFilter implements Filter {
    private int myYear;
    
    public YearAfterFilter(int year) {
        myYear = year;
    }
    
    @Override
    public boolean satisfies(String id) {
        return MovieDatabase.getYear(id) >= myYear;
    }

}
