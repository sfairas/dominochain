package com.sfairas.dominochain.exception;

public class InvalidTileValueException extends RuntimeException {

  private static final long serialVersionUID = 1514211402848625270L;

  public InvalidTileValueException(String message) {
    super(message);
  }
}
