package DB;

import java.sql.*;
// java.sql.Connection, java.sql.DriverManager, java.sql.ResultSet, java.ResultSet
// JDBC 시스템 라이브러리 불러오기

public class k27_DBTest {
    public static void main(String[] args) throws SQLException {
        // Class.forName() 메서드를 호출하여, mysql에서 제공하는 Driver 클래스를 JVM method area에 로딩시킨다.
        // JDBC 드라이버를 불러오는 기능이지만 현재는 자동으로 JDBC 드라이버 불러와서 사용할 필요가 없다.
        //Class.forName("com.mysql.cj.jdbc.Driver");
        // DriverManager 클래스의 static 메서드인 getConnection() 메서드를 호출해서, mysql에 연결하기 위한 커넥션 정보(url, user, password)를 입력한다.
        // MySQL 서버의 Timezone 설정이 UTC이기 때문에 UTC 접속 옵션울 넣어준다.
        Connection k27_conn = DriverManager.getConnection(
                "jdbc:mysql://192.168.23.96:33060/kopoctc?serverTimezone=UTC",
                "koposw27", "koposw27");
        // Statment 클래스의 static 메서드인 createStatemnet() 메서드를 conn 객체(입력 정보가 들어있는 인스턴스)에서 호출한다.
        // 해당 정보를 바탕으로 쿼리 입력이 가능한 상태를 구현한다.
        Statement k27_stmt = k27_conn.createStatement();
        // Result 클래스의 rest 인스턴스는 executeQuery() 메서드(쿼리문을 실행)의 리턴값(쿼리문 실행결과)을 받고 이를 String으로 출력하게 해준다.
        // 아래 예시에서는 show databases의 쿼리 실행 결과가 rset에 저장된다.
        // ResultSet으로 쿼리 실행 결과값을 받와야 할때는 executeQuery 문을 사용한다.
        ResultSet k27_rset = k27_stmt.executeQuery("show databases;");

        // 인스턴스 rset에서 호출하는 next() 메소드는 한번 실행할때마다 한 행씩 커서를 옮기면서
        // 해당 행 내용이 있으면 true, 해당 행 내용이 없으면 false를 반환한다.
        // while (rset.next())는 false(행이 없을때)가 될때까지 루프를 반복한다.
        while (k27_rset.next()) {
            // rest.getString은 해당 행의 몇 번째 칼럼을 불러와서 String형으로 리턴한다.
            // database는 첫 번째 칼럼만 존재하므로 rset.getString(1)만 있으면 모든 자료를 출력할 수 있다.
            System.out.println("값 : " + k27_rset.getString(1));
        }
        // 사용이 끝난 생성한 인스턴스를 종료시키고 메모리 초기화를 하는 close() 명령.
        k27_rset.close();
        k27_stmt.close();
        k27_conn.close();
    }
}
