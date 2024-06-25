/** 
 * @author (https://github.com/danielvillam) 
 * @version (June 21, 2024)
 */

import edu.duke.*;
import java.util.*;
import org.apache.commons.csv.*;

// Class requested by assignment one
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
    public ArrayList<Movie> loadMovies(String filename){
        FileResource fr = new FileResource(filename);
        CSVParser parser = fr.getCSVParser();
        ArrayList<Movie> MovieData = csvconvert(parser);
        return MovieData;
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
        for(Movie move : movies){
            count++;
            if (move.getGenres().contains("Comedy")){
                comedy++;
            }
            if(move.getMinutes() > 150){
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
    
    // New method for special function of the second part of the assignment.
    private ArrayList<Rater> csvRater(CSVParser parser){
        ArrayList<Rater> loadRaters = new ArrayList<Rater>();
        for(CSVRecord rate : parser){
            Rater newRater = new EfficientRater(rate.get("rater_id"));
            loadRaters.add(newRater);
            newRater.addRating(rate.get("movie_id"),Double.parseDouble(rate.get("rating")));
        }
        return loadRaters;
    }
    
    // New method for special function of the second part of the assignment.
    public ArrayList<Rater> csvRead(String filename){
        FileResource f = new FileResource("data/" + filename);
        CSVParser parser = f.getCSVParser();
        ArrayList<Rater> loadRaters = csvRater(parser);
        return loadRaters;
    }
    
    // Convert csv file to supported information.
    private HashMap<String, HashMap<String,Rating>> csvLoadRaters(CSVParser parser){
       HashMap<String, HashMap<String,Rating>> idMap = new HashMap<String, HashMap<String,Rating>>();
        for(CSVRecord rate : parser){
           Rater tempRater = new EfficientRater(rate.get("rater_id"));
           tempRater.addRating(rate.get("movie_id"),Double.parseDouble(rate.get("rating")));
           for(String hey : tempRater.getaRating().keySet()){
              if(!idMap.containsKey(tempRater.getID())){
                   idMap.put(tempRater.getID(),tempRater.getaRating());
              }
              else{
                   idMap.get(tempRater.getID()).put(hey,tempRater.getaRating().get(hey));
              }
                }
        }
       return idMap;
    }
    
    // Required for public testloadRaters(filename)
    public HashMap<String, HashMap<String,Rating>> loadRaters(String filename){
        FileResource f = new FileResource("data/" + filename);
        CSVParser parser = f.getCSVParser();
        HashMap<String, HashMap<String,Rating>> loadRaters = csvLoadRaters(parser);
        return loadRaters;
    }
    
    // Information required by the assignment
    public void testLoadRaters(){
       String filename = "ratings.csv"; 
       HashMap<String, HashMap<String,Rating>> loadRaters = loadRaters(filename);
       
       for(HashMap<String,Rating> rating : loadRaters.values()){
            for(Rating rat : rating.values()){
            }
       }
       
       System.out.println("Total raters = " + loadRaters.size());
       String specficRater = "193";
       for(HashMap<String,Rating> rating : loadRaters.values()){
            for(Rating rat : rating.values()){
               if(rat.equals(specficRater)){
                  System.out.println("The user with the specific ID " + rat + " voted " + loadRaters.get(rat).size() + " movies");
               }
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
       System.out.println("Amount of TOP1 user/s by no. of votes: " + countMaxRaters);
       String movie_id = "1798709";
       int timesVoted = 0;
       for(HashMap<String,Rating> rating : loadRaters.values()){
            for(Rating eachMovie : rating.values()){
               String moviesID = eachMovie.getItem();
               if(movie_id.equals(moviesID)){
                  timesVoted++;
               }
            }
        }
       System.out.println("The movie with the ID: " + movie_id + " has been voted "  + timesVoted + " times");
       ArrayList<String> list = new ArrayList<String>();
       for(HashMap<String,Rating> rating : loadRaters.values()){
            for(Rating eachMovie : rating.values()){
               String moviesID = eachMovie.getItem();
               if(!list.contains(moviesID)){
                   list.add(moviesID);
               }
           }
       }
       System.out.println("Number of total different movies rated: " + list.size());
     }
}
