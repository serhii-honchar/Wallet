package ua.kiev.sa;

import java.io.Serializable;

import java.io.StreamTokenizer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Account implements Serializable {
    private String account;
    private float sum;
    private static ArrayList<Account> accounts = new ArrayList<Account>();
    private String currency;
    private static ArrayList<String> accNames;

    public Account(String account, float sum, String curr) {
        this.account = account;
        this.sum = sum;
        this.currency = curr;
    }



    public void setAccount(String account) {
        this.account = account;
    }

    public String getAccount() {
        return account;
    }

    public void setSum(float _sum) {
        sum += _sum;
    }

    public float getSum() {
        return sum;
    }

    @Override
    public String toString() {
        return "На счету " + getAccount() + " НАХОДИТСЯ " + getSum();
    }

    public static ArrayList<String> getAccountsNames() {
        accNames = new ArrayList<String>();
        for (Account acc : accounts) {
            accNames.add(acc.getAccount());
        }
        return accNames;
    }
    public String toStringText(){
       return "_________________"+this.getAccount()+" "+this.getSum();


    }
}
