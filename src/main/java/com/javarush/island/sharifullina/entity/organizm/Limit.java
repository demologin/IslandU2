package com.javarush.island.sharifullina.entity.organizm;

import lombok.Getter;
import lombok.Setter;
@Getter   @Setter
public class Limit {

   private double maxWeight;
   private int maxCount;
   private int maxSpeed;
   private double maxFood;
   private int flockSize;
   private int chanceToBorn;
   public Limit(){}

}
