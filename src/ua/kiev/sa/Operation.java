package ua.kiev.sa;


import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class Operation {
    private String login;
    public static ArrayList<Operation> operate = new ArrayList<Operation>();
    private String Date;
    private String cat;
    private String subcat;
    private SimpleDateFormat d;
    private String account;
    private float f;

    public Operation(String login, String cat, String subcategory,
                     SimpleDateFormat d, String account1, Float f) {
        this.login = login;
        this.d = d;
        this.cat = cat;
        this.account = account1;
        this.f = f;
        this.subcat = subcategory;

    }
    public Operation(String cat, String subcategory,
                      SimpleDateFormat d, String account1, Float f) {
        this.d = d;
        this.cat = cat;
        this.account = account1;
        this.f = f;
        this.subcat = subcategory;

    }

    public static String createDateForTextField() {

        Object[] obj = new Object[] { Calendar.getInstance().getTime() };
        String str = "{0,date,dd.MM.yyyy HH:mm}";
        MessageFormat mf = new MessageFormat(str);
        return mf.format(obj);
    }

    public static String createDate(SimpleDateFormat df) {
        SimpleDateFormat simDF = df;
        Calendar c = simDF.getDateInstance().getCalendar();
        Object[] obj = new Object[] { c.getTime() };
        String str = "{0,date,dd.MM.yyyy HH:mm}";
        MessageFormat mf = new MessageFormat(str);

        return mf.format(obj);

    }

    public static SimpleDateFormat createDateFromString(String dateString) {
        DateFormat dataDate = DateFormat.getInstance();
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("dd.MM.yyyy HH:mm");
        try {
            d = dataDate.parse(dateString);
            sdf.format(d);
        } catch (ParseException e) {
            System.out.println(e.getStackTrace());
        }
        return sdf;
    }


    public void income(String login, String cat, SimpleDateFormat d,
                       String accout, float f) {
        this.login = login;
        this.d = d;
        this.cat = cat;
        this.account = accout;
        this.f = f;
        if (f > 0) {

        }
    }

    @Override
    public String toString() {
        String a = Date + " " + "потратил " + "на " + cat + f;
        return a;
    }

    public SimpleDateFormat getD() {
        return d;
    }


    public String getCat() {
        return cat;
    }

    public String getSubcat() {
        return subcat;
    }

    public float getF() {
        return f;
    }

    public String getAccount() {
        return account;
    }




}
