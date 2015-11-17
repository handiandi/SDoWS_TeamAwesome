/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.niceview;

import javax.jws.WebService;

/**
 *
 * @author Mr. Awesome
 */
@WebService(serviceName = "BankService", portName = "BankPort", endpointInterface = "dk.dtu.imm.fastmoney.BankPortType", targetNamespace = "urn://fastmoney.imm.dtu.dk", wsdlLocation = "WEB-INF/wsdl/FastMoney/fastmoney.imm.dtu.dk_8080/BankService.wsdl")
public class FastMoney {

    public boolean chargeCreditCard(int group, dk.dtu.imm.fastmoney.types.CreditCardInfoType creditCardInfo, int amount, dk.dtu.imm.fastmoney.types.AccountType account) throws dk.dtu.imm.fastmoney.CreditCardFaultMessage {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public boolean validateCreditCard(int group, dk.dtu.imm.fastmoney.types.CreditCardInfoType creditCardInfo, int amount) throws dk.dtu.imm.fastmoney.CreditCardFaultMessage {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public boolean refundCreditCard(int group, dk.dtu.imm.fastmoney.types.CreditCardInfoType creditCardInfo, int amount, dk.dtu.imm.fastmoney.types.AccountType account) throws dk.dtu.imm.fastmoney.CreditCardFaultMessage {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }
    
}
