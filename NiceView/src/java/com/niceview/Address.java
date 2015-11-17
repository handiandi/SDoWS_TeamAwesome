/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.niceview;

/**
 *
 * @author Mr. Awesome
 */
public class Address {
    private String address;
    private String city;
    private String country;
    
    public Address(String address, String city, String country){
        this.address = address;
        this.city = city;
        this.country = country;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }
    
    
}