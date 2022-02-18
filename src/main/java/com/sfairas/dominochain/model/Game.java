package com.sfairas.dominochain.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class Game {

  String chain;
  int maxSumVal;
  List<Tile> unallocatedTiles;
}
