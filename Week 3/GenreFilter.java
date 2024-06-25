/** 
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
