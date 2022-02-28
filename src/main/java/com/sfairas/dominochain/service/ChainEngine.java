package com.sfairas.dominochain.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sfairas.dominochain.model.Game;
import com.sfairas.dominochain.model.Tile;
import com.sfairas.dominochain.model.Vertex;

@Service
public class ChainEngine {

  private static final Logger log = LoggerFactory.getLogger(ChainEngine.class);

  public Game computeGraph(Tile startingTile, List<Tile> tiles) {
    HashMap<Integer, List<Vertex>> adjacencyList = getAdjacencyList(startingTile, tiles);

    // TODO: think about this one, there may be a case where there is no vertex with only 1 edge.
    // instead get the one with the less edges
    Integer root = Integer.MAX_VALUE;

    // find the vertex with the less edges and use it the root
    for(Map.Entry<Integer, List<Vertex>> entry : adjacencyList.entrySet()) {
      Integer key = entry.getKey();
      List<Vertex> vertices = entry.getValue();
      if(vertices.size() < root) {
        root = key;
      }
    }

    List<Integer> visited = breadthFirstSearch(adjacencyList, root);

    log.debug("Vertices visit seq: "+Arrays.toString(visited.toArray()));

    String chain = "["+visited.get(0);
    for (int i=1; i<visited.size()-1; i++) {
      Integer vx = visited.get(i);
      chain += ", "+vx+"], ["+vx;
    }
    chain += ", "+visited.get(visited.size()-1)+"]";

    int sum=0;
    for(int i=1; i<visited.size()-1; i++) {
      sum += visited.get(i);
    }

    Game game = Game.builder()
        .chain(chain)
        .maxSumVal(sum)
        .build();

    return game;
  }

  private HashMap<Integer, List<Vertex>> getAdjacencyList(Tile startingTile, List<Tile> tiles) {
    HashMap<Integer, List<Vertex>> adjacencyList = new HashMap<>();
    tiles.add(startingTile);

    tiles.forEach(t -> {
      if (!adjacencyList.containsKey(t.getLeftValue())) {
        adjacencyList.put(t.getLeftValue(), new LinkedList<>());
      }

      if (!adjacencyList.containsKey(t.getRightValue())) {
        adjacencyList.put(t.getRightValue(), new LinkedList<>());
      }

      adjacencyList.get(t.getLeftValue()).add(new Vertex(t.getRightValue()));
      adjacencyList.get(t.getRightValue()).add(new Vertex(t.getLeftValue()));
    });

    return adjacencyList;
  }

  private List<Integer> breadthFirstSearch(HashMap<Integer, List<Vertex>> adjacencyList, Integer root) {
    List<Integer> visited = new LinkedList<>();
    Integer currentVertex = root;
    do {
      visited.add(currentVertex);
      System.out.println("currentVertex: "+currentVertex);
      
      if(visited.size() >= 2) {
        // Get adjacency list of the previously visited vertex and set it to visited
        Integer previousVertex = visited.get(visited.size()-2);
        for (Vertex v : adjacencyList.get(currentVertex)) {
          if(v.getLabel().equals(previousVertex)) {
            System.out.println("setting "+v.getLabel() + "=true");
            v.setVisited(true);
            break;
          }
        }
      }
      
      Optional<Vertex> nextVertex = adjacencyList.get(currentVertex).stream().filter(v -> !v.isVisited()).findFirst();      

      if(nextVertex.isPresent()) {
        nextVertex.get().setVisited(true);
        currentVertex = nextVertex.get().getLabel();
      }
    } while(adjacencyList.get(currentVertex).stream().anyMatch(v -> !v.isVisited()));

    return visited;
  }
}
