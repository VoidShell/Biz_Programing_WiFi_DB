package DB;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Main {

    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException, ParseException {



        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection k37_conn = DriverManager.getConnection("jdbc:mysql://192.168.23.95:33060/koposw37?zeroDateTimeBehavior=convertToNull", "root", "root");
        Statement k37_stmt = k37_conn.createStatement();

//      File k37_f=new File("C:\\Users\\mmm\\Desktop\\전국무료와이파이표준데이터인근님.csv");
//      BufferedReader k37_br=new BufferedReader(new FileReader(k37_f));
        String k37_readtxt;

        FileInputStream fis = new FileInputStream("전국무료와이파이표준데이터1.csv");
        InputStreamReader is = new InputStreamReader(fis, "EUC-KR");
        BufferedReader k37_br = new BufferedReader(is);




        if((k37_readtxt=k37_br.readLine())==null) {
            System.out.println("빈파일입니다\n");
            return;
        }

        String[] field_name=k37_readtxt.split(",");
        int LineCnt=0;
        Date date = new Date();
        Date k37_date;

        while((k37_readtxt=k37_br.readLine()) !=null) {
            String[] k37_field = k37_readtxt.split(",");
            String k37_QueryTxt;


            SimpleDateFormat k37_standardForm = new SimpleDateFormat("yyyy-MM-dd");

            SimpleDateFormat k37_format1 = new SimpleDateFormat("MMM-yyyy", Locale.US);
            SimpleDateFormat k37_format2 = new SimpleDateFormat("MMM-yy", Locale.US);
            SimpleDateFormat k37_format3 = new SimpleDateFormat("yyyy.MM");
            SimpleDateFormat k37_format4 = new SimpleDateFormat("yyyy");
            SimpleDateFormat k37_format5 = new SimpleDateFormat("yyyy년MM월dd일", Locale.KOREA);
            SimpleDateFormat k37_format6 = new SimpleDateFormat("yyyyMM");
            SimpleDateFormat k37_format7 = new SimpleDateFormat("yyyy-MMM", Locale.US);
            SimpleDateFormat k37_format8 = new SimpleDateFormat("yy-MMM", Locale.US);





            if (k37_field[7].matches("^[a-zA-z]{3}[-][0-9]{4}")) { // 배열의 index 7에 해당되는 값이 해당 정규표현식에 해당되면 아래 코드 실행
                k37_date = k37_format1.parse(k37_field[7]); // 배열의 index 7의 내용을 simpledateformat을 사용해 파싱한다
                k37_field[7] = k37_standardForm.format(k37_date); // 파싱한 것을 다시 sim포맷을 사용해 String으로 다시 변환

            } else if (k37_field[7].matches("[a-zA-z]{3}[-][0-9]{2}")) { // 배열의 index 7에 해당되는 값이 해당 정규표현식에 해당되면 아래 코드 실행
                k37_date = k37_format2.parse(k37_field[7]);// 배열의 index 7의 내용을 simpledateformat을 사용해 파싱한다
                k37_field[7] = k37_standardForm.format(k37_date);// 파싱한 것을 다시 sim포맷을 사용해 String으로 다시 변환

            } else if (k37_field[7].matches("^[0-9]{4}.[0-9]{2}")) {// 배열의 index 7에 해당되는 값이 해당 정규표현식에 해당되면 아래 코드 실행
                k37_date = k37_format3.parse(k37_field[7]);// 배열의 index 7의 내용을 simpledateformat을 사용해 파싱한다
                k37_field[7] = k37_standardForm.format(k37_date);// 파싱한 것을 다시 sim포맷을 사용해 String으로 다시 변환

            } else if (k37_field[7].matches("^[0-9]{4}")) {// 배열의 index 7에 해당되는 값이 해당 정규표현식에 해당되면 아래 코드 실행
                k37_date = k37_format4.parse(k37_field[7]);// 배열의 index 7의 내용을 simpledateformat을 사용해 파싱한다
                k37_field[7] = k37_standardForm.format(k37_date);// 파싱한 것을 다시 sim포맷을 사용해 String으로 다시 변환

            } else if (k37_field[7].matches("^[0-9]{4}[가-힣][\\s][0-9]{2}[가-힣][\\s][0-9]{2}[가-힣]")) {// 배열의 index 7에 해당되는 값이 해당 정규표현식에 해당되면 아래 코드 실행
                k37_date = k37_format5.parse(k37_field[7]);// 배열의 index 7의 내용을 simpledateformat을 사용해 파싱한다
                k37_field[7] = k37_standardForm.format(k37_date);// 파싱한 것을 다시 sim포맷을 사용해 String으로 다시 변환

            } else if (k37_field[7].matches("^[0-9]{6}")) {
                k37_date = k37_format6.parse(k37_field[7]);
                k37_field[7] = k37_standardForm.format(k37_date);

            } else if (k37_field[7].matches("^[0-9]{4}[-][A-Za-z]{3}")) {
                k37_date = k37_format7.parse(k37_field[7]);
                k37_field[7] = k37_standardForm.format(k37_date);


            } else if (k37_field[7].equals("")) {

                k37_field[7] = "";



            } else {

            }

//if (!k37_field[7].matches("^[0-9]{4}[-][0-9][0-9][-][0-9][0-9]")) {
//   System.out.println("no");
//   System.out.println(k37_field[7]);
//   System.out.println(k37_field[0]);
//   System.out.println(k37_field[1]);
//   break;
//
//}

            k37_QueryTxt = String.format("insert ignore into freewifi4 ("
                            + "inst_place, inst_place_detail, inst_city, inst_country, inst_place_flag, "
                            + "service_provider, wifi_ssid, inst_date, place_addr_road, place_addr_land, "
                            + "manage_office, manage_office_phone, latitude, longitude, write_date)"
                            + "values ("
                            + "'%s', '%s', '%s', '%s', '%s',"
                            + "'%s', '%s', '%s', '%s', '%s',"
                            + "'%s', '%s', '%s', '%s', '%s');",
                    k37_field[0], k37_field[1], k37_field[2], k37_field[3], k37_field[4],
                    k37_field[5], k37_field[6], k37_field[7], k37_field[8], k37_field[9],
                    k37_field[10], k37_field[11], k37_field[12], k37_field[13], k37_field[14]);
            k37_stmt.execute(k37_QueryTxt);
            System.out.printf("%d번째 Insert OK [%s]\n", LineCnt, k37_QueryTxt);
            LineCnt++;


        }
        k37_br.close();
        k37_stmt.close();
        k37_conn.close();
    }
}