package udemy.java.organizze.helper;

import java.text.SimpleDateFormat;

public class DateCustom {

    public static String actualDate(){

        long date = System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = simpleDateFormat.format(date);

        return dateString;
    }

    public static String selectedDate(String date){
        String dateRetrieved[] = date.split("/");
        String day = dateRetrieved[0];
        String month = dateRetrieved[1];
        String year = dateRetrieved[2];

        String monthYear = month + year;

        return monthYear;
    }
}
