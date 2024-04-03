package com.uk.app.Rest.Models;

import javax.persistence.*;
@lombok.Data
public class Acct {

    private String AcctNo;
    private String Name;
    private String LimitRef;
    private String Product;
    private String Ccy;
    private String WorkingBal;
    private String LedgerBal;
    private String ClearedBal;
    private String UseableBal;
    private String DateFrom;
    private String LockedAmount;
    private String ResponseCode;
    private String ResponseDescription;

}