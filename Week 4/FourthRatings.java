/** 
 * This class corresponds to the corresponding assignments in part four of the Duke University Java Programming: 
 * Build a Recommendation System course taught on Coursera.
 * This class seeks to improve the efficiency in which various processes of the ThirdRatings class are performed. 
 * Also add weighted average topics in the ratings and suggestions to different types of people.
 *  
 * @author (https://github.com/danielvillam) 
 * @version (June 25, 2024)
 */

import java.util.*;
public class FourthRatings {
    
    public FourthRatings() {
        this("ratings.csv");
    }
    
    public FourthRatings(String ratingsfile){
        RaterDatabase.addRatings("data/" + ratingsfile);
    }
    
    public Rater getRater(String rater_id){
        return RaterDatabase.getRater(rater_id);
    }
    
    public int getRaterSize(){
        return RaterDatabase.size();
    }
    
    public double getAverageByID(String id, int minimalRaters){
        ArrayList<Rater> Raters = RaterDatabase.getRaters();
        double count = 0;
        double numRatings = 0;
        double average = 0;
        
        if(minimalRaters == 0){
            return 0.0;
        }
        
        for (Rater r : Raters){
            HashMap<String,Rating> Ratings = r.getaRating();
            for(Rating rat : Ratings.values()){
                if(rat.getItem().equals(id)){
                    double value = rat.getValue();
                    numRatings++;
                    count = count + value;
                }
            }
        }
        
        if(numRatings< minimalRaters){
            return -1;
        }
        else{
            average = count/numRatings;
            return average;
        }
    }
    
    public ArrayList<Rating> getAverageRatings(int minimalRaters){
        ArrayList<Rating> averageRatings = new ArrayList<Rating>();
        ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter());
        
        for(String m : movies){
            getAverageByID(m,minimalRaters);
            Rating a = new Rating(m,getAverageByID(m,minimalRaters));
            if(a.getValue() > -1){
                averageRatings.add(a);
            }
        }
        return averageRatings;
    }
    
    public ArrayList<Rating> getAverageRatingsByFilter(int minimalRaters, Filter filterCriteria){
        ArrayList<Rating> averageAndFilter = new ArrayList<Rating>();
        ArrayList<String> movies = MovieDatabase.filterBy(filterCriteria);
        
        for(String m : movies){
            getAverageByID(m,minimalRaters);
            Rating a = new Rating(m,getAverageByID(m,minimalRaters));
            if(a.getValue() > -1){
                averageAndFilter.add(a);
            }
        }
        return averageAndFilter;
    }
    
    private double dotProduct(Rater me, Rater r){
        HashMap<String,Rating> myRatings = me.getaRating();
        HashMap<String,Rating> othersRatings = r.getaRating();
        double dotProduct = 0;
        
        for(int i=0; i<myRatings.size();i++){
            ArrayList<Rating> movieA = new ArrayList<Rating>();
            for(Rating a : myRatings.values()){
                 movieA.add(a);
            }
            for(Rating b : othersRatings.values()){
                if (movieA.toString().contains(b.getItem())){
                    for(i=0;i<movieA.size();i++){
                        if(movieA.get(i).getItem().equals(b.getItem())){
                            double finalValue =0;
                            finalValue = (b.getValue()-5)* (movieA.get(i).getValue()-5);
                            dotProduct = dotProduct+ finalValue;
                        }
                    }
                }
            }
        }
        return dotProduct;
    }
    
    private ArrayList<Rating> getSimilarities(String id){
        ArrayList<Rating> similarRatings = new ArrayList<Rating>();
        Rater me = RaterDatabase.getRater(id);
        
        for(Rater r : RaterDatabase.getRaters()){
            if(!r.getID().equals(id)){
                if(dotProduct(me,r)>0){
                    similarRatings.add(new Rating(r.getID(),dotProduct(me,r)));
                }
            }
        }
        Collections.sort(similarRatings,Collections.reverseOrder());
        return similarRatings;
    }
    
    public ArrayList<Rating> getSimilarRatings(String id, int numSimilarRaters, int minimalRaters){
        try{
            ArrayList<Rating> similarRatings = getSimilarities(id);
            ArrayList<Rating> getRatings = new ArrayList<Rating>();
            ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter());
            double numratings = 0;
            HashMap<String,ArrayList<Double>>favRaters = new HashMap<String,ArrayList<Double>>();
            
            for(String movieID : movies){
                for(int i=0; i<numSimilarRaters; i++){
                    Rating r = similarRatings.get(i);
                    double coef = r.getValue();
                    String rater_id = r.getItem();
                    ArrayList<Rater> Raters = RaterDatabase.getRaters();
                    
                    for(Rater rat : Raters){
                        HashMap<String,Rating> Ratings = rat.getaRating();
                        if(rater_id.equals(rat.getID())){
                            
                            for(Rating rats : Ratings.values()){
                                if(rats.getItem().equals(movieID)){
                                    ArrayList<Double> coefs = new ArrayList<Double>();
                                    if(!favRaters.containsKey(rats.getItem())){
                                        coefs.add(coef*rats.getValue());
                                        favRaters.put(rats.getItem(),coefs);
                                    }
                                    else{
                                        ArrayList<Double> mine = favRaters.get(rats.getItem());
                                        mine.add(coef*rats.getValue());
                                        favRaters.put(rats.getItem(),mine);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            
            for ( String s : favRaters.keySet()){
                if( favRaters.get(s).size() >=minimalRaters){
                    double total =0;
                    
                    for(double num : favRaters.get(s)){
                        total = total+ num;
                    }
                    double finalValue = total/favRaters.get(s).size();
                    getRatings.add(new Rating(s,finalValue));
                }
            }
            Collections.sort(getRatings,Collections.reverseOrder());
            return getRatings;
       }
       catch (Exception e){
           System.out.println("One of the variables is out of bounds, insert smaller parameter variables or another user");
           return null;
       }
   }
   
   public ArrayList<Rating> getSimilarRatingsByFilter(String id, int numSimilarRaters, int minimalRaters, Filter filterCriteria){
        try{
            ArrayList<Rating> similarRatings = getSimilarities(id);
            ArrayList<Rating> getRatings = new ArrayList<Rating>();
            ArrayList<String> movies = MovieDatabase.filterBy(filterCriteria);
            double numratings = 0;
            HashMap<String,ArrayList<Double>>favRaters = new HashMap<String,ArrayList<Double>>();
            
            for(String movieID : movies){
                for(int i=0; i<numSimilarRaters; i++){
                    Rating r = similarRatings.get(i);
                    double coef = r.getValue();
                    String rater_id = r.getItem();
                    ArrayList<Rater> Raters = RaterDatabase.getRaters();
                    
                    for(Rater rat : Raters){
                        HashMap<String,Rating> Ratings = rat.getaRating();
                        if(rater_id.equals(rat.getID())){
                            
                            for(Rating rats : Ratings.values()){
                                if(rats.getItem().equals(movieID)){
                                    ArrayList<Double> coefs = new ArrayList<Double>();
                                    if(!favRaters.containsKey(rats.getItem())){
                                        coefs.add(coef*rats.getValue());
                                        favRaters.put(rats.getItem(),coefs);
                                    }
                                    else{
                                        ArrayList<Double> mine = favRaters.get(rats.getItem());
                                        mine.add(coef*rats.getValue());
                                        favRaters.put(rats.getItem(),mine);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            
            for ( String s : favRaters.keySet()){
                if( favRaters.get(s).size() >=minimalRaters){
                    double total =0;
                    for(double num : favRaters.get(s)){
                        total = total+ num;
                    }
                    double finalValue = total/favRaters.get(s).size();
                    getRatings.add(new Rating(s,finalValue));
                }
            }
            Collections.sort(getRatings,Collections.reverseOrder());
            return getRatings;
       }
       catch (Exception e){
           System.out.println("One of the variables is out of bounds, insert smaller parameter variables or another user");
           return null;
       }
   }
}
