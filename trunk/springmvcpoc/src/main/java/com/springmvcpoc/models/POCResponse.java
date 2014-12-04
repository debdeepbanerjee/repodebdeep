package com.springmvcpoc.models;
 
import java.io.Serializable;
 
public class POCResponse implements Serializable{
/**
               *
                */
               private static final long serialVersionUID = 1L;
private String name;
private String greetings;
public String getName() {
               return name;
}
public void setName(String name) {
               this.name = name;
}
public String getGreetings() {
               return greetings;
}
public void setGreetings(String greetings) {
               this.greetings = greetings;
}
}