package com.k.o.smart4aviation.tools;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;

import java.awt.*;
import java.time.LocalDate;

public class DateRetriever {
    public static String retrieveDateAndTime(DatePicker datePicker, IntegerField timeH, IntegerField timeM, IntegerField timeS, TextField zone){
        LocalDate date = datePicker.getValue();
        String month = String.valueOf(date.getMonthValue());
        String day = String.valueOf(date.getDayOfMonth());
        String h = "00";
        String m = "00";
        String s = "00";

        if(timeH.isInvalid() || timeM.isInvalid() || timeS.isInvalid()){
            return "";
        }

        if(timeH.getValue() != null) {
            if(timeH.getValue() < 10){
                h = "0" + timeH.getValue();
            }
            else
               h = String.valueOf(timeH.getValue());
        }
        if(timeM.getValue() != null) {
            if(timeM.getValue() < 10){
                m = "0" + timeM.getValue();
            }
            else
                m = String.valueOf(timeM.getValue());
        }
        if(timeS.getValue() != null) {
            if(timeS.getValue() < 10){
                s = "0" + timeS.getValue();
            }
            else
                s = String.valueOf(timeS.getValue());
        }

        if(date.getMonthValue() < 10){
            month = "0" + date.getMonthValue();
        }
        if(date.getDayOfMonth() < 10){
            day = "0" + date.getDayOfMonth();
        }

        String dateString = date.getYear() + "-" + month + "-" + day + "T" + h + ":" + m + ":" + s + zone.getValue();
        return dateString;
    }
    public static String retrieveDate(DatePicker datePicker){
        LocalDate date = datePicker.getValue();
        String month = String.valueOf(date.getMonthValue());
        String day = String.valueOf(date.getDayOfMonth());
        return date.getYear() + "-" + month + "-" + day;
    }
}
