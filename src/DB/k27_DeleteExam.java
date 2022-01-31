package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class k27_DeleteExam {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        // Class.forName() 메서드를 호출하여, mysql에서 제공하는 Driver 클래스를 JVM method area에 로딩시킨다.
        // JDBC 드라이버를 불러오는 기능이지만 현재는 자동으로 JDBC 드라이버 불러와서 사용할 필요가 없다.
        //Class.forName("com.mysql.cj.jdbc.Driver");
        // DriverManager 클래스의 static 메서드인 getConnection() 메서드를 호출해서,
        // MySQL에 연결하기 위한 커넥션 정보(url, user, password)를 입력한다.
        // MySQL 서버의 Timezone 설정이 UTC이기 때문에 UTC 접속 옵션울 넣어준다.
        Connection k27_conn = DriverManager.getConnection("jdbc:mysql://192.168.23.96:33060/kopoctc?serverTimezone=UTC",
                "koposw27", "koposw27");
        // Statment 클래스의 static 메서드인 createStatemnet() 메서드를 conn 객체(입력 정보가 들어있는 인스턴스)에서 호출한다.
        // 해당 정보를 바탕으로 쿼리 입력이 가능한 상태를 구현한다.
        Statement k27_stmt = k27_conn.createStatement();
        // DBTest에서 한 것처럼 쿼리 입력 결과가 필요 없을 때는 excute를 사용한다.
        // excute 메서드는 쿼리의 입력의 성공 결과를 1, 0으로만 반환한다.
        // delete 쿼리로 examtable 삭제
        k27_stmt.execute("delete from examtable");

        // 사용이 끝난 생성한 인스턴스를 종료시키고 메모리를 반환 하는 close() 명령.
        k27_stmt.close();
        k27_conn.close();
    }
}