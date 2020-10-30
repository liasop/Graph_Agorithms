package graph;
import util.*;
import graph.*;
import data.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
* A Grapher Analyzer class, helper class for movie lens analyzer. Contains Floyd Warshall's for finding diamter and average path length
* of the graph. Also contains dijkstras for finding the shortest path to any movie from a start movie.
*
* @author Lia Chin-Purcell and Sarah Walling-Bell
* @version November 28, 2018
*
*/

public class GraphAlgorithms {
  private int diam;
  private double avgPathLength;
  private final int INF = 120000; //YIKES!

  public GraphAlgorithms(){
    diam = 0;
    avgPathLength = 0;
  }
  /**
  * Method runs the Floyd-Warshall algorithm
  *
  * @param graph the graph
  * @param movieMap map of movies
  */
  public int[][] floydWarshall(Graph<Movie> graph, HashMap<Integer,Movie> movieMap){
    //this whole thing just makes the adjacency matrix
    int totalPathLength = 0;
    int size = movieMap.size() + 1;
    int count = 0;
    int[][] adjMatrix = new int[size][size];
    for(int i = 0; i <size;i++){
      for(int j = 0; j <size;j++){
        adjMatrix[i][j] = INF;
      }
    }
    for(Map.Entry<Integer, Movie> entry : movieMap.entrySet()) {
      Integer id = entry.getKey();
      Movie mov = entry.getValue();
      //get the list of movies it is attatched to
      if(graph.containsNode(mov)){
        ArrayList<Movie> list = (ArrayList<Movie>)graph.getNeighbors(mov);
        //for all the movies in the list, put in 1 for each edge
        for(int i = 0; i < list.size(); i++){
          int idOfMovie = list.get(i).getMovieId();
          adjMatrix[id][idOfMovie] = 1;
        }
      }
    }
    //now we go through the matricies
    for(int k = 1; k < size; k++){
      int[][] newAdjMatrix = new int[size][size];
      for(int j = 1; j< size; j++){
        for(int h = 1; h < size; h++){
          newAdjMatrix[j][h] = min(adjMatrix[j][h], adjMatrix[j][k] + adjMatrix[k][h]);
          //finding max path length
          if(newAdjMatrix[j][h] != INF && newAdjMatrix[j][h] > diam){
            diam = newAdjMatrix[j][h];
          }
          //total path length, only on last iteration
          if(k == (size - 1) && newAdjMatrix[j][h] != INF){
            count++;
            totalPathLength += newAdjMatrix[j][h];
          }
        }
      }
      adjMatrix = newAdjMatrix;
    }
    avgPathLength = (double)totalPathLength / count;
    return adjMatrix;
  }

  /**
  * Method runs Dijkstras algorithm
  * @param g the graph
  * @param s the start vertex in the graph
  */
  public ThingToReturn dijkstras(Graph<Movie> graph, Movie s, HashMap<Integer,Movie> movieMap){

    PriorityQueue pq = new PriorityQueue();
    //dist holds shortest path distances from s to each other vertex
    HashMap<Movie, Integer> dist = new HashMap<Movie, Integer>();
    //prev holds the previous node on the shortest path from s to each other vertex
    HashMap<Movie, Movie> prev = new HashMap<Movie, Movie>();


    //Initialize pq and dist as infinity (max integer value) and prev as null
    //for each node in the graph. If the node is start node, pq and dist are
    //initialized to 0.
    Set<Movie> nodes = graph.getVertices();
    //Iterator<V> itr = nodes.iterator();

    for(Movie mov : nodes){
      if (mov.getMovieId() == s.getMovieId()){
        dist.put(mov, 0);//distance from the root node to itself is zero
      }
      else{
        dist.put(mov, INF);//all other distances are infinity
      }
      pq.push(dist.get(mov), mov.getMovieId());//push onto priority queue (distance, id)
      //  pq.printHeap();
      prev.put(mov, null);
    }

    int alt;
    while (pq.size() > 0){
      int movId = pq.topElement(); //shortest pathLength from s to node u. u is the id of the movie
      for (Movie v : graph.getNeighbors(movieMap.get(movId))){
        alt = dist.get(movieMap.get(movId)) + 1;
        if (alt < dist.get(v)){
          dist.replace(v, dist.get(v), alt);
          prev.replace(v, movieMap.get(movId));
          if(pq.isPresent(v.getMovieId())){
            pq.changePriority(v.getMovieId(), alt);
          }
        }
      }
      pq.pop();
    }
    ThingToReturn thing = new ThingToReturn(dist,prev);
    return thing;
  }

  /**
  * returns the diamter of the graph;
  * @return the diamter
  */
  public int diameter(){
    return diam;
  }
  /**
  * return the average path length of the graph
  * @return the average path length
  */
  public double avgPathLength(){
    return avgPathLength;
  }

  /*********************************************************
  *     These are the private methods
  *********************************************************/

  //find the min
  private static int min(int one, int two){
    int min = one;
    if(two < one){
      min = two;
    }
    return min;
  }

}
