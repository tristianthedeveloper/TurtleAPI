package com.turtle.api;
/**
A simple class that holds 3 objects. 
*/
public class ImmutableTriplet<T, S, U> {


  private final T first;
  private final S middle;
  private final U last;


  public ImmutableTriplet(T first, S middle, U last) {
    this.first = first;
    this.middle = middle;
    this.last = last;
  }
  public T getFirst() {
    return this.first;
  }
  public S getSecond() {
    return this.middle;
  }
  public U getThird() {
    return this.last;
  }



}