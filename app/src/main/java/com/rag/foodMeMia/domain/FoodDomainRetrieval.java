package com.rag.foodMeMia.domain;

import android.annotation.SuppressLint;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public class FoodDomainRetrieval extends FoodDomain implements Serializable {

    private String uniqueId;

    public FoodDomainRetrieval() {

    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public class SortByAddedDate implements Comparator<FoodDomainRetrieval> {

        //this will sort from oldest to newest, to get the newest to oldest, you can just reverse it, you know that already boiii.
        @Override
        public int compare(FoodDomainRetrieval foodDomain1, FoodDomainRetrieval foodDomain2) {
            try {

                @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Date fdDate1 = simpleDateFormat.parse(foodDomain1.getAdded_at());
                Date fdDate2 = simpleDateFormat.parse(foodDomain2.getAdded_at());
                System.out.println("fd1 "+fdDate1+ " fd2 "+fdDate2);

                System.out.println(fdDate1.compareTo(fdDate2));

                return fdDate1.compareTo(fdDate2);
//
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

        }
    }

}
