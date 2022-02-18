package com.sfairas.dominochain.request;

import java.util.List;

import com.sfairas.dominochain.model.Tile;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TilesRequest {

  private Tile startingTile;
  private List<Tile> tiles;
}
