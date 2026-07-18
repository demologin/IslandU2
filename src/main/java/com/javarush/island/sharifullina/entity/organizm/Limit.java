package com.javarush.island.sharifullina.entity.organizm;

import lombok.Getter;
import lombok.Setter;

public class Limit {
   @Getter   @Setter
   private double maxWeight;
   @Getter   @Setter
   private int maxCount;
   @Getter   @Setter
   private int maxSpeed;
   @Getter   @Setter
   private double maxFood;
   public Limit(){}

   @Override
   public String toString() {
      return "Limit [maxWeight=" + maxWeight + ", maxCount=" + maxCount + ", maxSpeed=" + maxSpeed + ", maxFood=" + maxFood + "]";
   }
}
