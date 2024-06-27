/** 
 * This class corresponds to the corresponding assignments in part five of the Duke University Java Programming: 
 * Build a Recommendation System course taught on Coursera.
 * In this class the movie recommendation system is put to work through www.dukelearntoprogram.com
 *  
 * @author (https://github.com/danielvillam) 
 * @version (June 26, 2024)
 */

import java.util.*;
public class RecommendationRunner implements Recommender{
    
    // Returns a list of strings representing movie IDs that will be used to present movies to the user for review.
    public ArrayList<String> getItemsToRate(){
         Random random = new Random();
         //The selection of movies will be random; the yearAfter will be between 1970 and 1990
         int minYear = 1970;
         int maxYear = 1990;
         int randomYear = random.nextInt((maxYear - minYear) + 1) + minYear;
         //System.out.println("Random year: " + randomYear);
         
         //The selection of movies will be random; The genre will be between: comedy, drama and romance
         int minNumber = 1;
         int maxNumber = 3;
         int randomNumber = random.nextInt((maxNumber - minNumber) + 1) + minNumber;
         
         String[] genres = {"Comedy", "Drama", "Romance"};
         String genreRandom = genres[randomNumber - 1];
         GenreFilter genre = new GenreFilter(genreRandom);
         YearAfterFilter year = new YearAfterFilter(randomYear);
         
         //The duration of the films to be shown will be between 60 and 150 minutes.
         MinutesFilter minutes = new MinutesFilter(60,150);
         
         AllFilters all = new AllFilters();
         all.addFilter(year);
         all.addFilter(minutes);
         
         ArrayList<String> myMovies = MovieDatabase.filterBy(all);
         ArrayList<String> myMoviesRandom = new ArrayList<String>(); 
         for(int i=0; i<20 ;i++){
              int index = random.nextInt(myMovies.size());
              myMoviesRandom.add(myMovies.get(index));
         }
         return myMoviesRandom;
    }
    
    // Print the movie recommendation in html to display through a web page
    public void printRecommendationsFor (String webRaterID){
        try{
             FourthRatings dataBase = new FourthRatings("ratings.csv");
             MovieDatabase MovieData = new MovieDatabase();
             MovieData.initialize("ratedmoviesfull.csv");
             RaterDatabase RatDat = new RaterDatabase();
             RatDat.initialize("data/ratings.csv");
             ArrayList<Rating> recommendations = dataBase.getSimilarRatings(webRaterID,5,2);
             ArrayList<Rating> finalRecommendations = new ArrayList<Rating>();
             
             // This if is for the case in which there are no recommendations associated with the user.
             if(recommendations.size()==0){
                 System.out.println("Sorry, no movies found for you in the database with these parameters, try to vote again and the program will generate better movies for you to rate ;)");
                }
                
             // This if is to print only 10 movies if the final list is too large.
             if (recommendations.size()>10){
                for(int i=0;i<10;i++){
                    finalRecommendations.add(recommendations.get(i));
                }
                System.out.println("The system recommends you all these movies:");
                System.out.println("<style>th{color:blue}img{  border: 1px solid #ddd;border-radius: 4px;padding: 5px;width: 150px;}</style>");
                System.out.println("<table>");
                System.out.println("<tr><th>Movie</th><th>Poster</th></tr>");
                for(Rating r : finalRecommendations){
                     System.out.println("<tr>");
                     String movieTitle = MovieDatabase.getTitle(r.getItem()); 
                     String poster = MovieDatabase.getPoster(r.getItem());
                     System.out.println("<th>"+movieTitle +"</th>"+"<th>"+"<img src="+poster+">"+"</th>");
                     System.out.println("</tr>");
                }
                System.out.println("</table>");
             }
             // This else is to print all movies if the list is between 1 and 10.
             else{
                 System.out.println("The system recommends you all these movies:");
                 System.out.println("<style>th{font-size:150% color:black}#th1{color:blue font-size:175%}img{  border: 1px solid #ddd;border-radius: 4px;padding: 5px;width: 150px;}</style>");
                 System.out.println("<table>");
                 System.out.println("<tr><th id="+"th1"+">Movie</th><th id="+"th1"+">Poster</th></tr>");
                 for(Rating r : recommendations){
                     System.out.println("<tr>");
                     String movieTitle = MovieDatabase.getTitle(r.getItem()); 
                     String poster = MovieDatabase.getPoster(r.getItem());
                     System.out.println("<th>"+movieTitle +"</th>"+"<th>"+"<img src="+poster+">"+"</th>");
                      System.out.println("</tr>");
                 }
                 System.out.println("</table>");
                }
        }
        catch(Exception e){
            System.out.println(".Please <a link href="+"https://www.dukelearntoprogram.com/capstone/recommender.php?id=V6pdHUg5PA3to1"+">click here to return</a>.");
        }
    }
}
