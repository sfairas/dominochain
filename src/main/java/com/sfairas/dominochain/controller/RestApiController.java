package com.sfairas.dominochain.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
  private static final Logger log = LoggerFactory.getLogger(RestApiController.class);
  
  @PostMapping("/")
  public Game getChain(@RequestBody(required = true) TilesRequest tileReq) {

    //Game resp = chainEngine.calculateChainAndSum(tileReq.getStartingTile(), tileReq.getTiles());
    
    Game game = Game.builder()
        .chain("test")
        .maxSumVal(10)
        .build();

    return game;
  }
  
}
