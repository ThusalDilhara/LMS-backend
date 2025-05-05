package com.bodima.project_lms.util;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Utilities {

    private DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private DecimalFormat decimalFormat = new DecimalFormat("#,###.00");

    public String generateRandomString(int length) {
        int leftLimit = 97;
        int rightLimit = 122;
        Random random = new Random();
        return random.ints(leftLimit,rightLimit+1)
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString().toUpperCase();
    }

    public String generateRandomNumber() {
        Random rnd = new Random();
        int n = 100000 + rnd.nextInt(900000);
        return n+"";
    }

    public String getDateTimeString(Date date) {
        return dateTimeFormat.format(date);
    }

    public String getDateString(Date date) {
        return dateFormat.format(date);
    }

    public String getDecimalString(double value) {
        return decimalFormat.format(value);
    }

    public Date getDateObject(String date) throws Exception{
        return dateFormat.parse(date);
    }

    public Date getDateTimeObject(String dateTimeString) throws ParseException {
        return dateTimeFormat.parse(dateTimeString);
    }
    public String getDateTimeStringAmPm(Date date) {

        return dateTimeFormat.format(date);
    }
}