/** 
 * This class corresponds to the corresponding assignments in part three of the Duke University Java Programming: 
 * Build a Recommendation System course taught on Coursera.
 * Movies are filtered for a specific director.
 * 
 * @author (https://github.com/danielvillam) 
 * @version (June 24, 2024)
 */

public class DirectorsFilter implements Filter {
    private String myDirector;
    
    public DirectorsFilter(String director){
        myDirector = director;
    }
    
    public boolean satisfies(String id){
        return myDirector.contains(MovieDatabase.getDirector(id));
    }
}
