package com.javarush.island.sharifullina.entity;

import lombok.Getter;

@Getter
public class Limit {

   private double maxWeight;
   private int maxCount;
   private int maxSpeed;
   private double maxFood;
   private int flockSize;
   private int chanceToBorn;
   public Limit(){}
}
