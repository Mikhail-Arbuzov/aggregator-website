package com.aggregator.aggregator_website.services;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Component
public class StatisticDataConvector {

    public double bounceRateConvector(String bounceRate){
        double number = Double.parseDouble(bounceRate);
        double scale = Math.pow(10,2);
        double result = Math.ceil(number*100*scale)/scale;
        return result;
    }

    public String visitsConvectorInStr(String visits){
        return getFormatVisit(visits);
    }

    public String timeOnSiteConvector(String timeOnSite){
        return getStrFormat(timeOnSite);
    }

    public LocalTime timeConvector(String timeOnSite){
        String strf = getStrFormat(timeOnSite);
        LocalTime resultTime = LocalTime.parse(strf, DateTimeFormatter.ofPattern("HH:mm:ss"));
        return resultTime;
    }

    private String getStrFormat(String time){
        BigDecimal timeSite = new BigDecimal(time);
        BigDecimal timeSite2 = timeSite.setScale(0,RoundingMode.HALF_UP);
        long sec = timeSite2.longValue();
        long s = sec % 60;
        long m = (sec / 60) % 60;
        long h = (sec / (60 * 60)) % 24;
        String str = String.format("%02d:%02d:%02d", h, m, s);
        return str;
    }

    private String getFormatVisit(String value){
        final long TT = 100000L;
        final long M = 1000000L;
        final long B = 1000000000L;
        final long TRILLION = 1000000000000L;

        String results ="";

        BigDecimal res = new BigDecimal(value);
        long val = res.longValue();

        if (val < TT){
            results = String.valueOf(val);
        }

        else if(val < M){
            BigDecimal divide4 = new BigDecimal("1000");
            BigDecimal res3 = res.divide(divide4,1, RoundingMode.HALF_UP);
            results = res3 + " тыс.";
        }

        else if(val < B){
            BigDecimal divide2 = new BigDecimal("1000000");
            BigDecimal numb2 = res.divide(divide2,1, RoundingMode.HALF_UP);
            results = numb2 + " млн.";
        }

        else if(val < TRILLION){
            BigDecimal divide = new BigDecimal("1000000000");
            BigDecimal numb = res.divide(divide,1, RoundingMode.HALF_UP);
            results = numb + " млрд.";
        }

        else if(val >= TRILLION){
            BigDecimal divide3 = new BigDecimal("1000000000000");
            BigDecimal numb3 = res.divide(divide3,1, RoundingMode.HALF_UP);
            results = numb3 + " трлн.";
        }

        return results;

    }

    public String monthConvector(String month){
        return getMonth(month);
    }

    private String getMonth(String m){
        String month = "";
        switch (m){
            case "01":
                month = "Январь";
                break;
            case "02":
                month = "Февраль";
                break;
            case "03":
                month = "Март";
                break;
            case "04":
                month = "Апрель";
                break;
            case "05":
                month = "Май";
                break;
            case "06":
                month = "Июнь";
                break;
            case "07":
                month = "Июль";
                break;
            case "08":
                month = "Август";
                break;
            case "09":
                month = "Сентябрь";
                break;
            case "10":
                month = "Октябрь";
                break;
            case "11":
                month = "Ноябрь";
                break;
            case "12":
                month = "Декабрь";
                break;
        }
        return month;
    }
}
