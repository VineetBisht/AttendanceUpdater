/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.bg;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 *
 * @author Vineet
 */
public class Percentage {                               
private final SimpleIntegerProperty att;
private final SimpleIntegerProperty total;
private final SimpleDoubleProperty percentage;

 public Percentage(int att,int total,double percentage){
    this.att=new SimpleIntegerProperty(att);
    this.total=new SimpleIntegerProperty(total);
    this.percentage=new SimpleDoubleProperty(percentage);
 }

public double getPercentage(){
return percentage.get();
}
 
public void setPercentage(double percentage){
    this.percentage.set(percentage);
} 

 public int getAtt(){
    return att.get();
}

public void setAtt(int att){
    this.att.set(att);
}

public int getTotal(){
    return total.get();
}

public void setTotal(int total){
    this.total.set(total);
}
}  

