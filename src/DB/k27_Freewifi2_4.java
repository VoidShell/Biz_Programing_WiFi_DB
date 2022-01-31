package DB;


import java.io.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class k27_Freewifi2_4 {
    // 문자열에서 특정 문자의 개수를 반환하는 메소드
    // 첫 번째 파라미터 String 문자열, 두 번째 파라미터 검색할 문자형 c
    // Date 형태에서 하이픈 개수로 분기문을 추가 설정해 준다.
    static int k27_getCharNumber(String k27_str, char k27_c) {
        int k27_count = 0;
        // for 문을 str의 길이만큼 반복
        for (int k27_i = 0; k27_i < k27_str.length(); k27_i++) {
            // str을 chrAt메소드로 해당 인덱스 부분이 파라미터로 받은 문자형인지 확인
            if (k27_str.charAt(k27_i) == k27_c)
                // 개수를 나타내는 count 변수 증가
                k27_count++;
        }
        // 개수 나타내는 count 변수 반환
        return k27_count;
    }

    public static void main(String[] args) throws SQLException, IOException, ParseException {
        // Class.forName() 메서드를 호출하여, mysql에서 제공하는 Driver 클래스를 JVM method area에 로딩시킨다.
        // JDBC 드라이버를 불러오는 기능이지만 현재는 자동으로 JDBC 드라이버 불러와서 사용할 필요가 없다.
        //Class.forName("com.mysql.cj.jdbc.Driver");
        // DriverManager 클래스의 static 메서드인 getConnection() 메서드를 호출해서,
        // MySQL에 연결하기 위한 커넥션 정보(url, user, password)를 입력한다.
        // MySQL 서버의 Timezone 설정이 UTC이기 때문에 UTC 접속 옵션울 넣어준다.
        Connection k27_conn = DriverManager.getConnection(
                "jdbc:mysql://192.168.23.96:33060/kopoctc?serverTimezone=UTC&zeroDateTimeBehavior=convertToNull",
                "koposw27", "koposw27");
        // Statment 클래스의 static 메서드인 createStatemnet() 메서드를 conn 객체(입력 정보가 들어있는 인스턴스)에서 호출한다.
        // 해당 정보를 바탕으로 쿼리 입력이 가능한 상태를 구현한다.
        Statement k27_stmt = k27_conn.createStatement();

        // FileInputStr의 인스턴스 fis 생성하고 이를 InputStreamReader의 생성자의 인수로 넣어준다.
        // 이때 EUC-KR 옵션을 줘서 EUC-KR cvs 파일을 제대로 불러올 수 있도록 한다.
        // 절대 경로를 기입하지 않으면 프로젝트 폴더의 해당 파일을 불러온다.
        FileInputStream k27_fis = new FileInputStream("전국무료와이파이표준데이터1.csv");
        InputStreamReader k27_is = new InputStreamReader(k27_fis, "EUC-KR");
        // 데이터의 숫자가 많기 때문에 에러처리 없이 들어가도록 BufferedReader를 사용한다.
        // 위에서 생성한 InputStreamReader의 인스턴스를 BufferedReader의 생성자의 인수로 넣는다.
        BufferedReader k27_br = new BufferedReader(k27_is);
        String k27_readtxt;

        // br.readLine()을 통해 한줄을 통째로 불러오고 이에 대한 반환값을 readtxt 변수에 저장한다.
        // br.readLine() 1회 실행시키면서 커서를 다음 칸으로 옮긴다. (다음부터는 2행에 커서를 가리킨다.)
        // 처음 1행을 불러온 값이 null이면 빈 파일로 판단하고 메시지를 출력한다.
        if ((k27_readtxt = k27_br.readLine()) == null) {
            System.out.println("빈 파일입니다.");
            return;
        }

        // 쿼리문을 완성 할 String 변수 선언.
        String k27_QueryTxt;
        // 프로그래밍에 쓰이지 않는 구문이라 주석처리한다.
        // String[] file_name = readtxt.split("\t");
        // 처리한 내역의 순서를 표기하기 위한 count 변수 선언 및 초기화.

        // 엑셀에 섞여있는 date 양식을 검출하기 위해 존재하는 경우의 수 모두 조사
        // [input sdf] SimpleDateFormat으로 해당 날짜의 포맷을 만들고 해당 인스턴스로 문자형 표기를 Date형으로 parsing.
        // [output sdf] 출력형 sdf인스턴스의 format 메서드로 Date형으로 바꾼 데이터를 String으로 변환하여 field[7]에 다시 대입
        SimpleDateFormat k27_sdfYY = new SimpleDateFormat("yy");    // 예: 2005
        SimpleDateFormat k27_sdfYM1 = new SimpleDateFormat("yyMM"); // 예: 200506
        // 날짜 양식에 Month가 영문일 경우 Locale. English를 해준다.
        SimpleDateFormat k27_sdfYM2 = new SimpleDateFormat("yy.MM", Locale.ENGLISH);    // 예: 2005.Sep
        SimpleDateFormat k27_sdfKorYMD = new SimpleDateFormat("yy년 MM월 dd일", Locale.KOREAN);    // 예: 2005년 12월 14일
        SimpleDateFormat k27_sdfEngYM = new SimpleDateFormat("yy-MMM", Locale.ENGLISH); // 예: 15-Sep
        SimpleDateFormat k27_sdfEngMY = new SimpleDateFormat("MMM-yy", Locale.ENGLISH); // 예: Sep-15
        SimpleDateFormat k27_sdfYMD = new SimpleDateFormat("yyyy-MM-dd"); // 예: 1992-05-25

        // 작업 처리 회수를 기록하는 카운트 변수
        int k27_LineCnt = 0;
        // Date 형으로 임시 파싱을 위한 date 변수
        Date k27_dateTmp;
        // 문자열에서 -(하이픈)의 개수를 기록하는 변수
        int k27_flagHyphen = 0;

        // br.readLine() 함수로 읽어온 행의 데이터를 readtxt에 String으로 저장한다.
        // 읽어온 행이 null이 되면 데이터가 없다는 뜻이기 루프를 멈춘다.
        // 읽어온 행이 null이 아니면 루프를 계속 반복한다.
        while ((k27_readtxt = k27_br.readLine()) != null) {
            // String형 field 배열을 선언하고 이를 split 메서드를 컴마(,) 단위로 칼럼을 자르고
            // 이를 field 배열에 순서대로 저장한다.
            String[] k27_field = k27_readtxt.split(",");
            // instDate 칼럼의 자료 -(하이픈) 개수를 세는 작업
            k27_flagHyphen = k27_getCharNumber(k27_field[7], '-');

            // 다음의 조건 양식에 대해서 각 String을 검출하고 해당 자료들을
            // yyyy-MM-dd의 양식으로 출력을 통일시킴. (sdfYMD 인스턴스 형태)
            // 예) 2005 : 하이픈을 포함하지 않고 길이가 4
            if (k27_flagHyphen == 0 && k27_field[7].length() == 4) {
                k27_dateTmp = k27_sdfYY.parse(k27_field[7]);
                k27_field[7] = k27_sdfYMD.format(k27_dateTmp);
                // 예) 200506 : 하이픈을 포함하지 않고 길이가 6
            } else if (k27_flagHyphen == 0 && k27_field[7].length() == 6) {
                k27_dateTmp = k27_sdfYM1.parse(k27_field[7]);
                k27_field[7] = k27_sdfYMD.format(k27_dateTmp);
                // 예) 2005.Sep : 하이픈을 포함하지 않고 .(마침표) 포함
            } else if (k27_flagHyphen == 0 && k27_field[7].contains(".")) {
                k27_dateTmp = k27_sdfYM2.parse(k27_field[7]);
                k27_field[7] = k27_sdfYMD.format(k27_dateTmp);
                // 예) 2005년 5월 1일 : 하이픈을 포함하지 않고 "년"이라는 문자열 포함
            } else if (k27_flagHyphen == 0 && k27_field[7].contains("년")) {
                k27_dateTmp = k27_sdfKorYMD.parse(k27_field[7]);
                k27_field[7] = k27_sdfYMD.format(k27_dateTmp);
                // 예) 15-Sep : 하이픈을 포함하고 하이픈의 위치가 2(3번째) && 길이가 6인 경우
            } else if (k27_flagHyphen == 1 && k27_field[7].indexOf("-") == 2 && k27_field[7].length() == 6) {
                k27_dateTmp = k27_sdfEngYM.parse(k27_field[7]);
                k27_field[7] = k27_sdfYMD.format(k27_dateTmp);
                // 예) 2015-Sep : 하이픈을 포함하고 하이픈의 위치가 4(5번째)
            } else if (k27_flagHyphen == 1 && k27_field[7].indexOf("-") == 4) {
                k27_dateTmp = k27_sdfEngYM.parse(k27_field[7]);
                k27_field[7] = k27_sdfYMD.format(k27_dateTmp);
                // 예) Sep-15 : 하이픈을 포함하고 하이픈의 위치가 3(4번째)
            } else if (k27_flagHyphen == 1 && k27_field[7].indexOf("-") == 3) {
                k27_dateTmp = k27_sdfEngMY.parse(k27_field[7]);
                k27_field[7] = k27_sdfYMD.format(k27_dateTmp);
                // 예) 2015-05-12 : 하이픈이 두개인 경우
            } else if (k27_flagHyphen == 2) {
                k27_dateTmp = k27_sdfYMD.parse(k27_field[7]);
                k27_field[7] = k27_sdfYMD.format(k27_dateTmp);
                // else (그 외 조건 = 빈값): Date형은 빈값이 안 들어가서 Date의 최소 입력 날짜인 1000-01-01을 입력한다.
            } else {
                k27_dateTmp = k27_sdfYMD.parse("1000-01-01");
                k27_field[7] = k27_sdfYMD.format(k27_dateTmp);
            }
            // String.format 함수는 서식 지정자를 활용해서 원하는 양식으로 String을 완성한다.
            // printf와 작동원리가 동일하다.
            // field[12], field[13]은 double 형 위도, 경도인데 double 형은 insert에서 입력시 ''(홑따옴표)로 감싸지 않음)
            // String.format으로 최종적인 쿼리문을 String 문장으로 완성하는 것이다.
            k27_QueryTxt = String.format("insert ignore into freewifi ("
                            + "k27_inst_place, k27_inst_place_detail, k27_inst_city, k27_inst_country, k27_inst_place_flag,"
                            + "k27_service_provider, k27_wifi_ssid, k27_inst_date, k27_place_addr_road, k27_place_addr_land,"
                            + "k27_manage_office, k27_manage_office_phone, k27_latitude, k27_longitude, k27_write_date) "
                            + "values ("
                            + "'%s', '%s', '%s', '%s', '%s',"
                            + "'%s', '%s', '%s', '%s', '%s',"
                            + "'%s', '%s', %s, %s, '%s');",
                    k27_field[0], k27_field[1], k27_field[2], k27_field[3], k27_field[4],
                    k27_field[5], k27_field[6], k27_field[7], k27_field[8], k27_field[9],
                    k27_field[10], k27_field[11], k27_field[12], k27_field[13], k27_field[14]);
            // 완성한 QueryTxt 문장을 execute 메서드로 실행하여 쿼리문 입력.
            k27_stmt.execute(k27_QueryTxt);
            // 처리 회수와 완성된 쿼리문 출력
            System.out.printf("%d번째 항목 Insert OK [%s]\n", k27_LineCnt, k27_QueryTxt);
            // 처리 회수 카운트 변수 증가(+1)
            k27_LineCnt++;
        }
        // 사용이 끝난 생성한 인스턴스를 종료시키고 메모리를 반환 하는 close() 명령.
        k27_stmt.close();
        k27_conn.close();
    }
}