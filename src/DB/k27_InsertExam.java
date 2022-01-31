package DB;

import java.sql.*;

public class k27_InsertExam {
    public static void main(String args[]) throws ClassNotFoundException, SQLException {
        // Class.forName() 메서드를 호출하여, mysql에서 제공하는 Driver 클래스를 JVM method area에 로딩시킨다.
        // JDBC 드라이버를 불러오는 기능이지만 현재는 자동으로 JDBC 드라이버 불러와서 사용할 필요가 없다.
        //Class.forName("com.mysql.cj.jdbc.Driver");
        // DriverManager 클래스의 static 메서드인 getConnection() 메서드를 호출해서, mysql에 연결하기 위한 커넥션 정보(url, user, password)를 입력한다.
        // MySQL 서버의 Timezone 설정이 UTC이기 때문에 UTC 접속 옵션울 넣어준다.
        Connection k27_conn = DriverManager.getConnection("jdbc:mysql://192.168.23.96:33060/kopoctc?serverTimezone=UTC",
                "koposw27", "koposw27");
        // Statment 클래스의 static 메서드인 createStatemnet() 메서드를 conn 객체(입력 정보가 들어있는 인스턴스)에서 호출한다.
        // 해당 정보를 바탕으로 쿼리 입력이 가능한 상태를 구현한다.
        Statement k27_stmt = k27_conn.createStatement();
        // DBTest에서 한 것처럼 쿼리 입력 결과가 필요 없을 때는 excute를 사용한다.
        // excute 메서드는 쿼리의 입력의 성공 결과를 1, 0으로만 반환한다.
        // insert는 테이블에 순서대로 행단위로 자료를 입력한다. 첫 번째 괄호에 기입한 칼럼대로 value 뒤 괄호 안에도 해당 자료형 그대로 입력.
        k27_stmt.execute("insert into examtable (k27_name, k27_studentid, k27_kor, k27_eng, k27_mat) values ('효민',209901,95,100,95);");
        k27_stmt.execute("insert into examtable (k27_name, k27_studentid, k27_kor, k27_eng, k27_mat) values ('보람',209902,95,95,95);");
        k27_stmt.execute("insert into examtable (k27_name, k27_studentid, k27_kor, k27_eng, k27_mat) values ('은정',209903,100,100,100);");
        k27_stmt.execute("insert into examtable (k27_name, k27_studentid, k27_kor, k27_eng, k27_mat) values ('지연',209904,100,95,90);");
        k27_stmt.execute("insert into examtable (k27_name, k27_studentid, k27_kor, k27_eng, k27_mat) values ('소연',209905,80,100,70);");
        k27_stmt.execute("insert into examtable (k27_name, k27_studentid, k27_kor, k27_eng, k27_mat) values ('큐리',209906,100,100,70);");
        k27_stmt.execute("insert into examtable (k27_name, k27_studentid, k27_kor, k27_eng, k27_mat) values ('화영',209907,70,70,70);");
        // 사용이 끝난 생성한 인스턴스를 종료시키고 메모리를 반환 하는 close() 명령.
        k27_stmt.close();
        k27_conn.close();
    }
}