package com.sfairas.dominochain.service;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sfairas.dominochain.model.Game;
import com.sfairas.dominochain.model.Tile;

@Service
public class ChainEngine {
  
  private static final int MAXRUN = 1000;
  private Random random = new Random();
  
  private static final Logger log = LoggerFactory.getLogger(ChainEngine.class);

  /**
   * Compute the Domino chain along with the common values between connected pieces
   * @param startingTile The starting {@link Tile}
   * @param tiles A {@link List} of tiles with values ranging between 1 and 10. Tiles with the same values i.e [4,4] are not valid. The list can contain duplicate Tiles. 
   * @return A {@link Game} object containing the resulting chain along with the maximum sum of common values between connected pieces as well as any remaining unallocated Tiles
   */
  public Game computeChainAndSum(Tile startingTile, List<Tile> tiles) {
    TreeMap<Integer, Game> gameScores = new TreeMap<>();
    
    for(int j = 0; j < MAXRUN; j++) {
      LinkedList<Tile> tilesChain = new LinkedList<>();
      tilesChain.add(startingTile);
      startingTile.setPlayed(true);
      
      for (int i = 0; i < MAXRUN; i++) {
        int chainLeftSideNum = tilesChain.getFirst().getLeftValue();
        int chainRightSideNum = tilesChain.getLast().getRightValue();
        List<Tile> unPlayedTiles = tiles.stream()
            .filter(t -> !t.isPlayed() && t.hasValueOnEitherSide(chainLeftSideNum, chainRightSideNum))
            .collect(Collectors.toList());

        if(unPlayedTiles.isEmpty()) {
          break;
        }
        int rand = random.nextInt(unPlayedTiles.size());
        addTileToChain(tilesChain, unPlayedTiles.get(rand));
      }

      List<Tile> finalUnPlayedTiles = tiles.stream().filter(t -> !t.isPlayed()).collect(Collectors.toList());
      int sum = getChainValue(tilesChain);

      Game game = Game.builder()
          .chain(Arrays.toString(tilesChain.toArray()))
          .maxSumVal(sum)
          .unallocatedTiles(finalUnPlayedTiles)
          .build();

      gameScores.put(sum, game);
      
      // set all played to false to start another game run
      tiles.forEach(t -> {t.setPlayed(false);});
    }

    log.debug("Chain: "+gameScores.lastEntry().getValue().getChain()); 
    log.debug("Sum: "+gameScores.lastEntry().getKey());
    log.debug("Unallocated tiles: "+gameScores.lastEntry().getValue().getUnallocatedTiles());

    // TreeMap is by default sorted according to the natural ordering of its keys so the last value is the one
    // with the maximum sum of common values between Tiles.
    return gameScores.lastEntry().getValue();
  }

  private int getChainValue(LinkedList<Tile> tilesChain) {
    return tilesChain.stream().map(Tile::getRightValue).reduce(0, Integer::sum) - tilesChain.getLast().getRightValue();
  }

  /**
   * Attempt to add a tile to the chain. Attempt to place it on the side with the greatest number so as to maximise
   * the common values between connected pieces
   * @param tilesChain The current chain
   * @param tileToAdd The {@link Tile} to add
   */
  private void addTileToChain(LinkedList<Tile> tilesChain, Tile tileToAdd) {
    Tile first = tilesChain.getFirst();
    Tile last = tilesChain.getLast();

    // see which side has the highest value and try to match that one first
    if(first.getLeftValue() > last.getRightValue()) {
      addToFront(tilesChain, tileToAdd, first);
      addToEnd(tilesChain, tileToAdd, last);
    }else {
      addToEnd(tilesChain, tileToAdd, last);
      addToFront(tilesChain, tileToAdd, first);
    }
  }

  /**
   * Attempt to a tile to the front of the chain
   * @param tilesChain The current chain
   * @param tileToAdd The {@link Tile} to add
   * @param first The first tile of the chain
   */
  private void addToFront(LinkedList<Tile> tilesChain, Tile tileToAdd, Tile first) {
    if(!tileToAdd.isPlayed() && first.getLeftValue() == tileToAdd.getRightValue()) {
      tileToAdd.setPlayed(true);
      tilesChain.addFirst(tileToAdd);
    }else if(!tileToAdd.isPlayed() && first.getLeftValue() == tileToAdd.getLeftValue()) {
      tileToAdd.flip();
      tileToAdd.setPlayed(true);
      tilesChain.addFirst(tileToAdd);
    }
  }

  /**
   * Attempt to a tile to the end of the chain
   * @param tilesChain The current chain
   * @param tileToAdd The {@link Tile} to add
   * @param first The last tile of the chain
   */
  private void addToEnd(LinkedList<Tile> tilesChain, Tile tileToAdd, Tile last) {
    if(!tileToAdd.isPlayed() && last.getRightValue() == tileToAdd.getLeftValue()) {
      tileToAdd.setPlayed(true);
      tilesChain.addLast(tileToAdd);
    }else if(!tileToAdd.isPlayed() && last.getRightValue() == tileToAdd.getRightValue()) {
      tileToAdd.flip();
      tileToAdd.setPlayed(true);
      tilesChain.addLast(tileToAdd);
    }
  }
}
