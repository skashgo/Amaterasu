/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.amaterasu.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

/**
 *
 * @author Usuario
 */
public class Format {

    public static String unAccent(String s) {
        //
        // JDK1.5
        //   use sun.text.Normalizer.normalize(s, Normalizer.DECOMP, 0);
        //
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("");
    }

    public static BigDecimal getBigDecimal(String s) {
        if (s == null || s.isEmpty()) {
            return BigDecimal.ZERO;
        }
        s = s.replace(".", "").replace(",", ".");
        return new BigDecimal(s);
    }

    public static String getString(BigDecimal b) {
        if (b == null) {
            return "0,00";
        }
        DecimalFormat df = new DecimalFormat("#,###.00");
        return df.format(b);
    }

    public static String getString(Float f) {
        DecimalFormat df = new DecimalFormat("#,###.00");
        return df.format(f);
    }

    public static String getString(Double d) {
        DecimalFormat df = new DecimalFormat("#,###.00");
        return df.format(d);
    }

    public static String getDateFormat(Date date, String format) {
        if (date == null) {
            return "";
        }
        if (format == null || format.isEmpty()) {
            return date.toString();
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * To date.
     *
     * @param timestamp the timestamp
     *
     * @return the java.util. date
     */
    public static java.util.Date toDate(java.sql.Timestamp timestamp) {
        long milliseconds = timestamp.getTime() + (timestamp.getNanos() / 1000000);
        return new java.util.Date(milliseconds);
    }

    /**
     * To date.
     *
     * @param str the str
     *
     * @return the java.util. date
     */
    public static java.util.Date toDate(String str) throws ParseException {
        java.util.Date res = null;

        String pattern = "dd/MM/yyyy";


        SimpleDateFormat format = new SimpleDateFormat(pattern);

        res = format.parse(str);


        return res;
    }

    /**
     * Converte a primeira letra da String para maiuscula
     *
     * @param palavra
     * @return
     */
    public static String maiuscula1(String palavra) {
        if (palavra != null && !palavra.equals("")) {
            return palavra.substring(0, 1).toUpperCase() + palavra.substring(1);
        } else {
            return "";
        }
    }

    /**
     * Converte a primeira letra da String para minuscula
     *
     * @param palavra
     * @return
     */
    public static String minuscula1(String palavra) {
        if (palavra != null && !palavra.equals("")) {
            return palavra.substring(0, 1).toLowerCase() + palavra.substring(1);
        } else {
            return "";
        }
    }
}
