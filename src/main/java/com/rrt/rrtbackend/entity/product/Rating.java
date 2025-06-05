package com.rrt.rrtbackend.entity.product;

import jakarta.persistence.Embeddable;

@Embeddable
public class Rating {
    private double rate;
    private double count;
    public Rating(){}
    public Rating(double rate, double count){
        this.rate = rate;
        this.count = count;
    }
    public double getRate(){
        return rate;
    }
    public void setRate(double rate){
        this.rate = rate;
    }

    public double getCount(){
        return count;
    }
    public void setCount(double count){
        this.count = count;
    }
    
}
