package com.sfairas.dominochain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sfairas.dominochain.model.Game;
import com.sfairas.dominochain.model.Tile;
import com.sfairas.dominochain.service.ChainEngine;

@SpringBootTest
class DominochainApplicationTests {

  @Autowired
  private ChainEngine chainEngine;
  
	@Test
	void longChain() {
	  Tile startingTile = new Tile(1, 9); 
	  List<Tile> tiles = new ArrayList<>();

	  tiles.add(new Tile(3, 5));
	  tiles.add(new Tile(2, 9));
	  tiles.add(new Tile(1, 2));
	  tiles.add(new Tile(9, 3));
	  tiles.add(new Tile(9, 2));
	  tiles.add(new Tile(1, 5));
	  tiles.add(new Tile(5, 4));
	  tiles.add(new Tile(2, 7));
	  tiles.add(new Tile(5, 4));
	  tiles.add(new Tile(6, 7));
	  tiles.add(new Tile(8, 3));
	  tiles.add(new Tile(7, 2));
	  tiles.add(new Tile(2, 7));
	  tiles.add(new Tile(2, 3));
	  tiles.add(new Tile(9, 2));
	  tiles.add(new Tile(4, 5));
	  tiles.add(new Tile(5, 6));
	  tiles.add(new Tile(7, 2));
	  tiles.add(new Tile(8, 9));
	  tiles.add(new Tile(1, 10));
	  tiles.add(new Tile(10, 4));
	  tiles.add(new Tile(10, 3));
	  tiles.add(new Tile(5, 10));
	  tiles.add(new Tile(3, 7));
	  tiles.add(new Tile(5, 2));
	  tiles.add(new Tile(9, 1));
	  tiles.add(new Tile(9, 1));

	  Game game = chainEngine.computeGraph(startingTile, tiles);
	  assertEquals(game.getMaxSumVal(), 140);
	}

	/**
	 * One of the possible solutions is: [1,9][9,1][1,2][2,9][9,3][3,5] with a total chain value of 24  
	 */
	@Test
  void shortChain() {
    Tile startingTile = new Tile(1, 9); 
    List<Tile> tiles = new ArrayList<>();
    
    tiles.add(new Tile(2, 9));
    tiles.add(new Tile(9, 3));
    tiles.add(new Tile(9, 1));
    tiles.add(new Tile(3, 5));
    tiles.add(new Tile(1, 2));
    
    Game game = chainEngine.computeGraph(startingTile, tiles);
    assertEquals(game.getMaxSumVal(), 24);
	}
	
	/**
   * One of the possible solutions is: [1,9][9,1][1,2][2,9][9,3][3,5] with a total chain value of 24  
   * with a remaining tile [6,2] which is the only Tile that does not fit this chain
   */
  @Test
  void shortChainWithRemainingTile() {
    Tile startingTile = new Tile(1, 9); 
    List<Tile> tiles = new ArrayList<>();
    
    tiles.add(new Tile(2, 9));
    tiles.add(new Tile(9, 3));
    tiles.add(new Tile(9, 1));
    tiles.add(new Tile(3, 5));
    tiles.add(new Tile(6, 2));
    tiles.add(new Tile(1, 2));
    
    Game game = chainEngine.computeGraph(startingTile, tiles);
    assertEquals(game.getMaxSumVal(), 24);
    // the only Tile that does not fit in this chain is [6,2] so test that this is the remaining one out of the chain
  }
}
