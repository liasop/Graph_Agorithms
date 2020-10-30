package graph;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashMap;
import java.util.Map;
import java.util.HashSet;

/**
* A directed Graph object
*
* @author Lia Chin-Purcell and Sarah Walling-Bell
* @version 9/27/18
*/

public class Graph<V> implements GraphIfc<V> {
  private int edgeCount;
  private HashMap<V, ArrayList<V>> adjacencyList;

  /**
  * Constructs an empty directed graph.
  **/
  public Graph(){
    adjacencyList = new HashMap<V, ArrayList<V>>();
    edgeCount = 0;
  }

  /**
  * Returns the number of vertices in the graph
  * @return The number of vertices in the graph
  */
  public int numVertices(){
    return adjacencyList.size();
  }

  /**
  * Returns the number of edges in the graph
  * @return The number of edges in the graph
  */
  public int numEdges(){
    //iterates over the Map and sums the number of outgoing edges for each node v
    return edgeCount;
  }

  /**
  * Removes all vertices from the graph
  */
  public void clear(){
    adjacencyList.clear();
  }

  /**
  * Adds a vertex to the graph. This method has no effect if the vertex already exists in the graph.
  * @param v The vertex to be added
  */
  public void addVertex(V v){
    //if v is not in the graph, put it in the graph
    if(!adjacencyList.containsKey(v)){
      ArrayList list = new ArrayList<V>();
      adjacencyList.put(v,list);
    }
  }

  /**
  * Adds an edge between vertices u and v in the graph.
  * @param u A vertex in the graph
  * @param v A vertex in the graph
  * @throws IllegalArgumentException if either vertex does not occur in the graph.
  */
  public void addEdge(V u, V v) throws IllegalArgumentException{
    //if both nodes are in the graph, add v to u's list
    if (containsNode(u) && containsNode(v)){
      ArrayList list = adjacencyList.get(u);
      if(list.contains(v)){
        System.out.println("edge already exists");
      }
      else{
      adjacencyList.get(u).add(v);
      edgeCount++;
      }
    }
    else{
      throw new IllegalArgumentException ("one or more verticies not found in graph");
    }
  }

  /**
  * Returns the set of all vertices in the graph.
  * @return A set containing all vertices in the graph
  */
  public Set<V> getVertices(){
    return adjacencyList.keySet();
  }

  /**
  * Returns the neighbors of v in the graph. A neighbor is a vertex that is connected to
  * v by an edge. If the graph is directed, this returns the vertices u for which an
  * edge (v, u) exists.
  *
  * @param v An existing node in the graph
  * @return All neighbors of v in the graph.
  * @throws IllegalArgumentException if vertex does not occur in the graph
  */
  public List<V> getNeighbors(V v) throws IllegalArgumentException {
    //searches the outer adjacencyList for the node, and returns the arraylist
    //held by the node
    ArrayList list = adjacencyList.get(v);
    if(list != null){
      return list;
    }
    throw new IllegalArgumentException("the vertex is not in the graph");
  }

  /**
  * Determines whether the given vertex is already contained in the graph. The comparison
  * is based on the <code>equals()</code> method in the class V.
  *
  * @param v The vertex to be tested.
  * @return True if v exists in the graph, false otherwise.
  */
  public boolean containsNode(V v){
    //searches the outer adjacencyList for node v
    return adjacencyList.containsKey(v);
  }

  /**
  * Determines whether an edge exists between two vertices. In a directed graph,
  * this returns true only if the edge starts at v and ends at u.
  * @param v A node in the graph
  * @param u A node in the graph
  * @return True if an edge exists between the two vertices
  * @throws IllegalArgumentException if either vertex does not occur in the graph
  */
  public boolean edgeExists(V v, V u) throws IllegalArgumentException {
    //get the first vertex
    // if the first vertex exists search the first vertex's List
    //for the second vertex.
    if (containsNode(u) && containsNode(v)){
    ArrayList list = adjacencyList.get(v);
    if(list != null){
      for(int i =0; i <list.size(); i++){
        if(list.get(i).equals(u)){
          return true;
        }
      }
      return false;
    }
  }
    throw new IllegalArgumentException("one or more of the  verticies were not found");
  }

  /**
  * Returns the degree of the vertex. In a directed graph, this returns the outdegree of the
  * vertex.
  * @param v A vertex in the graph
  * @return The degree of the vertex
  * @throws IllegalArgumentException if the vertex does not occur in the graph
  */
  public int degree(V v) throws IllegalArgumentException {
    //gets the vertex, if it is found, return its length
    ArrayList list = adjacencyList.get(v);
    if(list != null){
      return list.size();
    }
    throw new IllegalArgumentException("the vertex is not in the graph");
  }

  /**
  * Returns a string representation of the graph. The string representation shows all
  * vertices and edges in the graph.
  * @return A string representation of the graph
  */
  public String toString(){
    //print each vertext with an arrow pointing to the arrayList it contains.
    //ex: 4 --> [0, 3, 7]
    String str = "";
    for(Map.Entry<V, ArrayList<V>> entry : adjacencyList.entrySet()){
      V key = entry.getKey();
      ArrayList<V> value = entry.getValue();

      str += key + " --> ";
      if (value.size() > 0){
        str += "[" + value.get(0);
        for (int j = 1; j < value.size(); j++){
          str += ", " + value.get(j);
        }
        str += "]\n";
      }
      else {
        str += "[]\n";
      }
    }
    return str;
  }

}
