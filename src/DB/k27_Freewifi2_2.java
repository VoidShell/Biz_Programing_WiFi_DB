package DB;

import java.io.*;
import java.sql.*;
import java.text.ParseException;
import java.util.Date;

public class k27_Freewifi2_2 {
    public static void main(String[] args) throws SQLException, IOException, ParseException {
        Connection conn = DriverManager.getConnection(
                "jdbc:mysql://192.168.23.96:33060/kopoctc?serverTimezone=UTC&zeroDateTimeBehavior=convertToNull",
                "koposw27", "koposw27");
        Statement stmt = conn.createStatement();

        FileInputStream fis = new FileInputStream("전국무료와이파이표준데이터.csv");
        InputStreamReader is = new InputStreamReader(fis, "EUC-KR");
        BufferedReader br = new BufferedReader(is);
        String readtxt;

        if ((readtxt = br.readLine()) == null) {
            System.out.println("빈 파일입니다.");
            return;
        }

        String QueryTxt;
        Date dateTmp;
        int LineCnt = 0;

        while ((readtxt = br.readLine()) != null) {
            String[] field = readtxt.split(",");

            QueryTxt = String.format("insert ignore into freewifi2 ("
                            + "inst_place, inst_place_detail, inst_city, inst_country, inst_place_flag,"
                            + "service_provider, wifi_ssid, inst_date, place_addr_road, place_addr_land,"
                            + "manage_office, manage_office_phone, latitude, longitude, write_date) "
                            + "values ("
                            + "'%s', '%s', '%s', '%s', '%s',"
                            + "'%s', '%s', '%s', '%s', '%s',"
                            + "'%s', '%s', %s, %s, '%s');",
                    field[0], field[1], field[2], field[3], field[4],
                    field[5], field[6], field[7], field[8], field[9],
                    field[10], field[11], field[12], field[13], field[14]);
            stmt.execute(QueryTxt);
            System.out.printf("%d번째 항목 Insert OK [%s]\n", LineCnt, QueryTxt);
            LineCnt++;
        }

        stmt.close();
        conn.close();
    }
}