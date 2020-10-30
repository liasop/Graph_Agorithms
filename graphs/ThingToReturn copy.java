package graph;
import java.util.HashMap;
import data.*;

/**
* A Helper class for returning both dist and prev in dijkstras algorithm.
*
* @author Lia Chin-Purcell and Sarah Walling-Bell
* @version November 28, 2018
*
*/
public class ThingToReturn{
  private HashMap<Movie, Integer> dist;
  private HashMap<Movie, Movie> prev;

  public ThingToReturn(HashMap<Movie, Integer> dist, HashMap<Movie, Movie> prev){
    this.dist = dist;
    this.prev = prev;
  }

  /**
  * get dist
  */
  public HashMap<Movie, Integer> getDist(){
    return dist;
  }

  /**
  * get prev
  */
  public HashMap<Movie, Movie> getPrev(){
    return prev;
  }
}
