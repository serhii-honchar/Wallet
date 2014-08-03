package ua.kiev.sa;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class User implements Serializable {
    private String login;
    private String password;
    private String surname;
    private String firstName;
    private String organization;
    private String sex;
    private String account;
    private Float sum;
    private ArrayList<Account> acc = new ArrayList<Account>();
    Account a;
    String accountName;
    Float accountSum;
    String currency;
//    private ArrayList<Account> acc;



    public User(String log, String surname, String firstName,
                String organization, String s, ArrayList<Account> h) {
        this.login = log;
//        this.password = pass;
        this.surname = surname;
        this.firstName = firstName;
        this.organization = organization;
        this.sex=s;
        acc = h;

    }

    public User(String login, String surname, String firstName,
                String organization, HashMap<String, Float> g) {
        for (Map.Entry<String, Float> entry : g.entrySet()) {
            account = entry.getKey();
            sum = entry.getValue();
            acc.add(new Account(account, sum, "UAH"));
        }
        this.login = login;
        this.surname = surname;
        this.firstName = firstName;
        this.organization = organization;
//              this.sex=s;
    }

    @Override
    public String toString() {

        return getSurname() + " " + getFirstName() + " " + "from " +
                getOrganization() + " has " + a.getSum() + " on " + a.getAccount();
    }

    public String writingToFile() {
        return getLogin() + "/" + getPassword() + "/" + getFirstName() + "/" +
                getSurname() + "/" + getOrganization() + "/"  ;
//                + a.getSum() + "/" +
//                a.getAccount() + "/";
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getSurname() {
        return surname;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getOrganization() {
        return organization;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        String c = password;

        return password;
    }


    public void setAcc(ArrayList<Account> acc) {
        this.acc = acc;
    }

    public ArrayList<Account> getAcc() {
        return acc;
    }


}


