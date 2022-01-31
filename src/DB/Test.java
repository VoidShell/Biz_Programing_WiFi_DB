package DB;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Test {
    public static void main(String[] args) throws ParseException {

        Date dateTmp;
        String dateStringOutput;

        String string1 = "2015년 1월 14일";
        String string2 = "2015";
        String string3 = "Nov-11";

        SimpleDateFormat sdfYear = new SimpleDateFormat("yy");
        SimpleDateFormat sdfEnglish = new SimpleDateFormat("MMM-yy", Locale.ENGLISH);
        SimpleDateFormat sdfKorean = new SimpleDateFormat("yy년 MM월 dd일", Locale.ENGLISH);
        SimpleDateFormat sdfOutput = new SimpleDateFormat("yy-MM-dd");

        dateTmp = sdfKorean.parse(string1);
        dateStringOutput = sdfOutput.format(dateTmp);

        System.out.println(dateStringOutput);



    }
}
