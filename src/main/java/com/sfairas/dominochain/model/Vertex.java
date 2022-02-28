package com.sfairas.dominochain.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Vertex {

  @EqualsAndHashCode.Include
  private Integer label;

  private boolean visited;

  public Vertex(Integer vertex) {
    this.label = vertex;
    this.visited = false;
  }
}
