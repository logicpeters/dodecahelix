package org.dmp.mongo.api;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ejb.Stateless;

@Stateless
public class Greeting {
    
  public String greetSomeone(String name) {
    StringBuilder builder = new StringBuilder();
    builder = builder.append("Hi, ");
    builder = builder.append(name);
    builder = builder.append("\n");
    builder = builder.append("Today it's the ");
    
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    builder = builder.append(sdf.format(new Date()));
    return builder.toString();
  }
}

