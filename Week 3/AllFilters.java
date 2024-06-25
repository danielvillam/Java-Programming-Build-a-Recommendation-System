/** 
 * This class corresponds to the corresponding assignments in part three of the Duke University Java Programming: 
 * Build a Recommendation System course taught on Coursera.
 * All filters.
 *  
 * @author (https://github.com/danielvillam) 
 * @version (June 24, 2024)
 */

import java.util.ArrayList;

public class AllFilters implements Filter {
    ArrayList<Filter> filters;
    
    public AllFilters() {
        filters = new ArrayList<Filter>();
    }

    public void addFilter(Filter f) {
        filters.add(f);
    }

    @Override
    public boolean satisfies(String id) {
        for(Filter f : filters) {
            if (! f.satisfies(id)) {
                return false;
            }
        }
        
        return true;
    }

}
