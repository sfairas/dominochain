package com.sfairas.dominochain.model;

import com.sfairas.dominochain.exception.InvalidTileValueException;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Tile {

  public static final int MINVAL = 1;
  public static final int MAXVAL = 10;
  
  private int leftValue;
  private int rightValue;
  // has this tile been played?
  boolean played;

  public Tile(int leftValue, int rightValue) {
    if(leftValue < MINVAL || leftValue > MAXVAL || rightValue < MINVAL || rightValue > MAXVAL) {
      throw new InvalidTileValueException(String.format("Tile with values [%s, %s] is invalid. Values should range between 1 and 10", leftValue, rightValue)); 
    }
    
    if(leftValue==rightValue) {
      throw new InvalidTileValueException(String.format("Tile with values [%s, %s] is invalid. Left and right values cannot be equal", leftValue, rightValue));
    }
      
    this.leftValue = leftValue;
    this.rightValue = rightValue;
    played = false;
  }
  
  public void flip() {
    int tmp = leftValue;
    leftValue = rightValue;
    rightValue = tmp;
  }
  
  @Override
  public String toString() {
    return "[" + leftValue + ", " + rightValue + "]";
  }

}
