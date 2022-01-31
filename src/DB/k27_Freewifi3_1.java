package DB;

import java.io.*;
import java.sql.*;
import java.text.ParseException;

public class k27_Freewifi3_1 {

    public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException, ParseException {
        // Class.forName() 메서드를 호출하여, mysql에서 제공하는 Driver 클래스를 JVM method area에 로딩시킨다.
        // JDBC 드라이버를 불러오는 기능이지만 현재는 자동으로 JDBC 드라이버 불러와서 사용할 필요가 없다.
        //Class.forName("com.mysql.cj.jdbc.Driver");
        // DriverManager 클래스의 static 메서드인 getConnection() 메서드를 호출해서,
        // MySQL에 연결하기 위한 커넥션 정보(url, user, password)를 입력한다.
        // MySQL 서버의 Timezone 설정이 UTC이기 때문에 UTC 접속 옵션울 넣어준다.
        Connection k27_conn = DriverManager.getConnection(
                "jdbc:mysql://192.168.23.96:33060/kopoctc?serverTimezone=UTC",
                "koposw27", "koposw27");
        // Statment 클래스의 static 메서드인 createStatemnet() 메서드를 conn 객체(입력 정보가 들어있는 인스턴스)에서 호출한다.
        // 해당 정보를 바탕으로 쿼리 입력이 가능한 상태를 구현한다.
        Statement k27_stmt = k27_conn.createStatement();
        // 현재 위치인 lat = 위도, lng = 경도 설정한다. (폴리텍 위치)
        double k27_lat = 37.3860521;
        double k27_lng = 127.1214038;
        // 쿼리문을 완성 할 String 변수 선언.
        String k27_QueryTxt;
        // 현재 위치(lat, lng)에서 가장 짧은 거리를 찾는 이중쿼리 구문
        // 거리공식(SQRT: 루트, POWER: 제곱)으로 모든 행에 대해서 거리를 구하고
        // 그 중에서 select MIN으로 찾은 최단 거리에 맞는 행을 찾는다.
        /*k27_QueryTxt = String.format("Select * from freewifi where " +
                        "SQRT( POWER( k27_latitude-%f,2) + POWER (k27_longitude-%f,2 ) ) = " +
                        "(select MIN( SQRT( POWER( k27_latitude-%f,2) + POWER (k27_longitude - %f,2 ) ) ) from freewifi);",
                k27_lat, k27_lng, k27_lat, k27_lng);*/
        // freewifi 테이블의 데이터를 모두 출력
        // k27_QueryTxt = "select * from freewifi";
        // freewifi 테이블에서 k27_service_provider가 정확히 SKT인 자료 출력
        // k27_QueryTxt = "select * from freewifi where k27_service_provider = 'SKT'";
        // freewifi 테이블에서 k27_service_provider가 SKT 단어가 포함되는 자료 출력
        // k27_QueryTxt = "select * from freewifi where k27_service_provider LIKE '%SK%'";
        // freewifi 테이블에서 k27_inst_country가 정확시 수원시인 자료 출력
        // k27_QueryTxt = "select * from freewifi where k27_inst_country = '수원시'";
        // freewifi 테이블에서 k27_inst_country가 수원 단어가 포함되는 자료 출력
        k27_QueryTxt = "select * from freewifi where k27_inst_country LIKE '%수원%'";


        // Result 클래스의 rest 인스턴스는 executeQuery() 메서드(쿼리문을 실행)의 리턴값(쿼리문 실행결과)을 받고 이를 String으로 출력하게 해준다.
        // 아래 예시에서는 QueryTxt의 쿼리 실행 결과가 rset에 저장된다.
        // ResultSet으로 쿼리 실행 결과값을 받와야 할때는 executeQuery 문을 사용한다.
        // select 문은 쿼리의 실행결과가 있고 이를 받아와야 한다.
        ResultSet k27_rset = k27_stmt.executeQuery(k27_QueryTxt);
        int k27_iCnt = 0;
        // 인스턴스 rset에서 호출하는 next() 메소드는 한번 실행할때마다 한 행씩 커서를 옮기면서
        // 해당 행 내용이 있으면 true, 해당 행 내용이 없으면 false를 반환한다.
        // while (rset.next())는 false(행이 없을때)가 될때까지 루프를 반복한다.
        while (k27_rset.next()) {
            // 각 칼럼의 자료형에 맞게 getString, getDate, getFloat을 써주고
            // 해당 인수로 각 칼럼의 순번을 써준다. 해당 메서드는 각 칼럼의 데이터값을 반환한다.
            System.out.printf("*(%d)********************************************\n", k27_iCnt++);
            System.out.printf("설치장소명         : %s\n", k27_rset.getString(1));
            System.out.printf("설치장소상세       : %s\n", k27_rset.getString(2));
            System.out.printf("설치시도명         : %s\n", k27_rset.getString(3));
            System.out.printf("설치시군구명       : %s\n", k27_rset.getString(4));
            System.out.printf("설치시설구분       : %s\n", k27_rset.getString(5));
            System.out.printf("서비스제공사명      : %s\n", k27_rset.getString(6));
            System.out.printf("와이파이SSID       : %s\n", k27_rset.getString(7));
            System.out.printf("설치년월           : %s\n", k27_rset.getDate(8));
            System.out.printf("소재지도로명주소    : %s\n", k27_rset.getString(9));
            System.out.printf("소재지지번주소      : %s\n", k27_rset.getString(10));
            System.out.printf("관리기관명         : %s\n", k27_rset.getString(11));
            System.out.printf("관리기관전화번호    : %s\n", k27_rset.getString(12));
            System.out.printf("위도              : %s\n", k27_rset.getFloat(13));
            System.out.printf("경도              : %s\n", k27_rset.getFloat(14));
            System.out.printf("데이터기준일자      : %s\n", k27_rset.getDate(15));
            System.out.println("***********************************************");
        }
        // 사용이 끝난 생성한 인스턴스를 종료시키고 메모리 초기화를 하는 close() 명령.
        k27_rset.close();
        k27_stmt.close();
        k27_conn.close();
    }
}