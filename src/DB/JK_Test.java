package DB;


import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class JK_Test {

    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException, ParseException {
        //Class.forName("com.mysql.jdbc.Driver"); // jar파일 안에 있던 com.mysql.jdbc패키지의 Driver클래스를 로딩합니다.
        Connection k39_conn = DriverManager.getConnection(
                "jdbc:mysql://192.168.23.96:33060/kopoctc?serverTimezone=UTC",
                "koposw27", "koposw27");
        // jdbc을 통해서 Mysql과 연동합니다.
        /*
         * DriverManager의 gerConnection()메서드는 MySQL DB의 URL과 유저, 패스워드를 파라메터로 받고
         * Connection의 인스턴스를 반환한다.
         */
        Statement k39_stmt = k39_conn.createStatement();
        /*
         * Statement는 데이터베이스에 DML쿼리(삽입, 수정, 삭제, 조회)를 할 때 필요한 객체다. createStatement()는
         * 인터페이스 java.sql. Connection의 메소드 SQL문을 데이터베이스에 보내기 위한 Statement 오브젝트를 생성한다.
         */

        FileInputStream fis = new FileInputStream("전국무료와이파이표준데이터1.csv");
        InputStreamReader is = new InputStreamReader(fis, "EUC-KR");
        BufferedReader k39_br = new BufferedReader(is);

        String k39_readtxt = ""; // 파일을 읽어오는데 불필요한 첫줄 처리를 위한 String 변수
        if ((k39_readtxt = k39_br.readLine()) == null) { // 한줄을 읽어와서 null일때 if문으로 들어간다.
            System.out.printf("빈파일 입니다\n"); // if문에서 빈파일이라는 문자열을 출력한다.
            return;
        }

        //String[] k39_field_name = k39_readtxt.split(","); // 첫줄을 \t를 기준으로 잘라서 배열에 넣어준다.

        SimpleDateFormat sdfYY = new SimpleDateFormat("yyyy");
        SimpleDateFormat sdfYM1 = new SimpleDateFormat("yyyyMM");
        SimpleDateFormat sdfYM2 = new SimpleDateFormat("yy.MM", Locale.ENGLISH);
        SimpleDateFormat sdfKorYMD = new SimpleDateFormat("yy년 MM월 dd일", Locale.KOREAN);
        SimpleDateFormat sdfEngYM = new SimpleDateFormat("yy-MMM", Locale.ENGLISH);
        SimpleDateFormat sdfEngMY = new SimpleDateFormat("MMM-yy", Locale.ENGLISH);
        SimpleDateFormat sdfYMD = new SimpleDateFormat("yyyy-MM-dd");
        Date k39_inst_date;
        ArrayList<String> testArray = new ArrayList<>();

        int k39_LineCnt = 0; // 몇 번째 라인인지를 확인하기 위해 Linecut 변수를 선언 초기화
        while ((k39_readtxt = k39_br.readLine()) != null) { // 두 번째 줄을 읽어와서 readtxt에 저장
            String[] k39_field = k39_readtxt.split(",");

            // 스트링을 데이터형으로 변환
            try {
                k39_inst_date = sdfYM1.parse(k39_field[7]);
                k39_field[7] = sdfYMD.format(k39_inst_date);
            } catch (ParseException k39_yearAndMonth) {
                try {
                    k39_inst_date = sdfYY.parse(k39_field[7]);
                    k39_field[7] = sdfYMD.format(k39_inst_date);
                } catch (ParseException k39_fullFormat) {
                    try {
                        k39_inst_date = sdfYM2.parse(k39_field[7]);
                        k39_field[7] = sdfYMD.format(k39_inst_date);
                    } catch (ParseException k39_onlyYear) {
                        try {
                            k39_inst_date = sdfKorYMD.parse(k39_field[7]);
                            k39_field[7] = sdfYMD.format(k39_inst_date);
                        } catch (ParseException k39_YearandMonth) {
                            try {
                                k39_inst_date = sdfEngYM.parse(k39_field[7]);
                                k39_field[7] = sdfYMD.format(k39_inst_date);
                            } catch (ParseException k39_YearcommaMonth) {
                                try {
                                    k39_inst_date = sdfEngMY.parse(k39_field[7]);
                                    k39_field[7] = sdfYMD.format(k39_inst_date);
                                } catch (ParseException k39_nullcheck) {
                                    k39_inst_date = sdfYMD.parse("1000-01-01");
                                    k39_field[7] = sdfYMD.format(k39_inst_date);
                                }
                            }
                        }
                    }
                }
            }

            String k39_QueryTxt = ""; // 뽑아낸 데이터를 넣을 문자열 변수 QueryTxt를 생성
            k39_QueryTxt = String.format(
                    "insert ignore into freewifi3 ("
                            + "inst_place, inst_place_detail, inst_city, inst_country, inst_place_flag, " // 설치장소명,
                            // 설치장소상세,
                            // 설치시군구명,
                            // 설치시설구분
                            + "service_provider, wifi_ssid, inst_date, place_addr_road, place_addr_land, " // 서비스제공사명,
                            // 와이파이SSID,
                            // 설치년월,
                            // 소재지도로명주소
                            + "manage_office, manage_office_phone, latitude, longitude, write_date)" // 소재지지번주소, 관리기관명,
                            // 관리기관전화번호, 위도,
                            // 경도, 데이터기준일자
                            + "values (" + "'%s', '%s', '%s', '%s', '%s', " // s프린트에프 서식으로 15개의 데이터를 받는다.
                            + "'%s' , '%s', '%s', '%s', '%s', " + "'%s', '%s', '%s', '%s', '%s');",
                    k39_field[0], k39_field[1], k39_field[2], k39_field[3], k39_field[4], // 탭으로 나눈 데이터를 s에 넣어준다.
                    k39_field[5], k39_field[6], k39_field[7], k39_field[8], k39_field[9], k39_field[10],
                    k39_field[11], k39_field[12], k39_field[13], k39_field[14]);
            k39_stmt.execute(k39_QueryTxt); // 데이터의 한 행을 insert문으로 만든 것을 excute메서드에 넣는다.
            System.out.printf("%d번째 항목 Insert OK [%s]\n", k39_LineCnt, k39_QueryTxt); // 라인 컷트를 한 것을 출력하고, 그와 함께 해당 행의
            // 퀴리를 출력하여 확인한다.

            k39_LineCnt++; // 한 행의 입력이 마무리됐으면 변수값을 하나 올려줘 라인 갯수를 올려준다.
        }

        k39_br.close(); // 열었으면 다 닫아주기.
        k39_stmt.close();
        k39_conn.close();
    }
}