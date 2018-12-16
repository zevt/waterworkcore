package com.autowaterworks.core.model;


import lombok.Data;

@Data
public class Address {

  private String streetAddress1;
  private String streetAddress2;
  private String city;
  private String state;
  private String zipcode;

}
