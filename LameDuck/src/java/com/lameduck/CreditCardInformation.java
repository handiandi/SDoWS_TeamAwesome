/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lameduck;

import java.util.Calendar;

/**
 *
 * @author jeppe
 */
class CreditCardInformation {
    final String name;
    final int number;
    final Calendar expDate;

    public String getName() {
        return name;
    }

    public int getNumber() {
        return number;
    }

    public Calendar getExpDate() {
        return expDate;
    }

    public CreditCardInformation(String name, int number, Calendar expDate) {
        this.name = name;
        this.number = number;
        this.expDate = expDate;
    }
}
