/** 
 * @author (https://github.com/danielvillam) 
 * @version (June 21, 2024)
 */

import edu.duke.*;
import java.util.*;
import org.apache.commons.csv.*;
public class FirstRatings {
    
    // Data preparation
    private ArrayList<Movie> csvconvert(CSVParser parser){
        ArrayList<Movie> movies = new ArrayList<Movie>();
        
        for(CSVRecord movie : parser){
            Movie tempMovie = new Movie(movie.get("id"),movie.get("title"),movie.get("year"), movie.get("genre"),movie.get("country"),movie.get("director"),movie.get("poster"),Integer.parseInt(movie.get("minutes")));
            movies.add(tempMovie);
        }
        return movies;
    } 
    
    // Required for public testLoadMovies(filename)
    private ArrayList<Movie> loadMovies(String filename){
        FileResource fr = new FileResource("data/" + filename);
        CSVParser parser = fr.getCSVParser();
        ArrayList<Movie> movies = csvconvert(parser);
        return movies;
    }
    
    // Manipulation of data to find information.
    private HashMap<String,Integer> DirectorAndMovies(ArrayList<Movie> movies){
        HashMap<String,Integer> mapDirector = new HashMap<String,Integer>();
        for(Movie movie : movies){
            String Director = movie.getDirector();
            String temp1 = "";
            int index = 0;
            if (Director.contains(",")){
                    temp1 = Director.substring(index,Director.indexOf(","));
                    index = temp1.length()+2;
                    String rest = Director.substring(index);
                    int index2= 0;
                    while(rest.contains(",")){
                       temp1 = rest.substring(index2,rest.indexOf(","));
                       if(!mapDirector.containsKey(temp1)){
                           mapDirector.put(temp1,1);
                        }
                       else{
                           mapDirector.put(temp1,mapDirector.get(temp1)+1);
                        }
                       index2 = temp1.length()+2;
                       rest= rest.substring(index2);
                       index2 = 0;
                    }
                    if(!mapDirector.containsKey(rest)){
                        mapDirector.put(rest,1);
                    }
                    else{
                        mapDirector.put(rest,mapDirector.get(rest)+1);
                    }
            }
            else{
                temp1 = Director;
            }
            if(!mapDirector.containsKey(temp1)){
                mapDirector.put(temp1,1);
            }
            else{
                mapDirector.put(temp1,mapDirector.get(temp1)+1);
            }
        }
        return mapDirector;
    }
    
    // Information required by the assignment
    public void testLoadMovies(){
        ArrayList<Movie> movies = loadMovies("ratedmoviesfull.csv");
        
        int count = 0;
        int comedy = 0;
        int minutesLong = 0;
        for(Movie movie : movies){
            count++;

            if (movie.getGenres().contains("Comedy")){
                comedy++;
            }

            if(movie.getMinutes() > 150){
                minutesLong++;
            }
        }
        System.out.println("Total movies = " + count);
        System.out.println("Total comedies = " + comedy);
        System.out.println("Movies longer than 150min = " + minutesLong);
        

        HashMap<String,Integer>mapDirector = DirectorAndMovies(movies);
        ArrayList<String> MaxDirectors = new ArrayList<String>();
        int max = 0;
        String maxDirector = "";
        for(String s : mapDirector.keySet()){
            if (mapDirector.get(s) > max){
                max = mapDirector.get(s);
            }
        }
        for(String s : mapDirector.keySet()){
            if (mapDirector.get(s) == max){
                MaxDirectors.add(s);
            }
        }

        System.out.println("Maximum number of movies by any director is: " + max);
        System.out.println("Directors who directed this many movies: \n");
        for(String s : MaxDirectors){
            System.out.println(s);
        }
    }
    
    // Convert csv file to supported information
    private HashMap<String, ArrayList<Rating>> csvLoadRaters(CSVParser parser){
    
       HashMap<String, ArrayList<Rating>> idMap = new HashMap<String, ArrayList<Rating>>();
       for(CSVRecord rate : parser){
            Rater tempRater = new Rater(rate.get("rater_id"));
            tempRater.addRating(rate.get("movie_id"),Double.parseDouble(rate.get("rating")));
            
            for(Rating  hey : tempRater.getMyRatings()){
                if(!idMap.containsKey(tempRater.getID())){
                    idMap.put(tempRater.getID(),tempRater.getMyRatings());
                }
                else{
                    idMap.get(tempRater.getID()).add(hey);
                }
            }
       }
       return idMap;
    }
    
    // Required for public testloadRaters(filename)
    private HashMap<String, ArrayList<Rating>> loadRaters(String filename){
        FileResource f = new FileResource("data/" + filename);
        CSVParser parser = f.getCSVParser();
        HashMap<String, ArrayList<Rating>> loadRaters = csvLoadRaters(parser);
        return loadRaters;
    }
    
    // Information required by the assignment
    public void testLoadRaters(){
       HashMap<String, ArrayList<Rating>> loadRaters = loadRaters("ratings.csv");
       System.out.println("Total raters = " + loadRaters.size());

       for(String s : loadRaters.keySet()){
           if(s.equals("193")){
              System.out.println("The user with the specific ID " + s + " voted " + loadRaters.get(s).size() + " movies");
           }
       }

       int max = 0;
       for(String s : loadRaters.keySet()){
           if(max < loadRaters.get(s).size()){
               max = loadRaters.get(s).size();
           }
       }
       System.out.println("Maximum number of movies rated per user: " + max);
       System.out.println("And they were voted by the users ID: ");
       int countMaxRaters = 0;
       for(String s : loadRaters.keySet()){
           if(loadRaters.get(s).size() == max){
               System.out.println(s);
               countMaxRaters++;
           }
       }
       System.out.println("Count max Raters: " + countMaxRaters);

       String movie_id = "1798709";
       int timesVoted = 0;
       for(ArrayList<Rating> totalRatings : loadRaters.values()){
           for(Rating eachMovie : totalRatings){
               String moviesID = eachMovie.getItem();
               if(movie_id.equals(moviesID)){
                  timesVoted++;
               }
            }
       }
       System.out.println("The movie with the ID: " + movie_id + " has been voted "  + timesVoted);

       ArrayList<String> list = new ArrayList<String>();
       for(ArrayList<Rating> totalRatings : loadRaters.values()){
           for(Rating eachMovie : totalRatings){
               String moviesID = eachMovie.getItem();
               if(!list.contains(moviesID)){
                   list.add(moviesID);
               }
           }
       }
       System.out.println("Number of total different movies rated: " + list.size());
     }
    
}
