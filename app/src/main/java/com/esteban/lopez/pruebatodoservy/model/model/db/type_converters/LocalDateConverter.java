package com.esteban.lopez.pruebatodoservy.model.model.db.type_converters;

import androidx.room.TypeConverter;

import java.time.LocalDate;


public class LocalDateConverter {

    @TypeConverter
    public static LocalDate toLocalDate(String date){
        return LocalDate.parse(date);
    }

    @TypeConverter
    public static String fromLocalDate(LocalDate LocalDate){
        return LocalDate == null ? null : LocalDate.toString();
    }
}