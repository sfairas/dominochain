package com.sfairas.dominochain.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sfairas.dominochain.model.Game;
import com.sfairas.dominochain.request.TilesRequest;
import com.sfairas.dominochain.service.ChainEngine;

@RestController
public class RestApiController {

  @Autowired
  private ChainEngine chainEngine;

  @PostMapping("/chain/compute")
  public Game computeChain(@RequestBody(required = true) TilesRequest tileReq) {

    Game game = chainEngine.computeGraph(tileReq.getStartingTile(), tileReq.getTiles());

    return game;
  } 
}