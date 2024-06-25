/** 
 * This class corresponds to the corresponding assignments in part three of the Duke University Java Programming: 
 * Build a Recommendation System course taught on Coursera.
 * Movies are filtered for a specific Genre.
 *   
 * @author (https://github.com/danielvillam) 
 * @version (June 24, 2024)
 */

public class GenreFilter implements Filter {
    private String myGenre;
    
    public GenreFilter(String genre){
        myGenre = genre;
    }
    
    @Override
    public boolean satisfies(String id) {
        return MovieDatabase.getGenres(id).contains(myGenre);
    }
}
