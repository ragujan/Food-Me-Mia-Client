package com.rag.foodMeMia.helper;

import java.text.DecimalFormat;

public class Util {
    public static String twoDecimalPrice(double price){
          DecimalFormat decfor = new DecimalFormat("0.00");
          return decfor.format(price);
    }
}
