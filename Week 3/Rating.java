/** 
 * This class corresponds to the corresponding assignments in part one of the Duke University Java Programming: 
 * Build a Recommendation System course taught on Coursera.
 * It is a data object (PDO) class. The Rating object information is save.
 *  
 * @author (https://github.com/danielvillam) 
 * @version (June 20, 2024)
 */

// An immutable passive data object (PDO) to represent the rating data
public class Rating implements Comparable<Rating> {
    private String item;
    private double value;

    public Rating (String anItem, double aValue) {
        item = anItem;
        value = aValue;
    }

    // Returns item being rated
    public String getItem () {
        return item;
    }

    // Returns the value of this rating (as a number so it can be used in calculations)
    public double getValue () {
        return value;
    }

    // Returns a string of all the rating information
    public String toString () {
        return "[" + getItem() + ", " + getValue() + "]";
    }

    public int compareTo(Rating other) {
        if (value < other.value) return -1;
        if (value > other.value) return 1;
        
        return 0;
    }
}
