package connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class connect {

	private static final String url = "jdbc:oracle:thin:@127.0.0.1:1521:XE";
	private static final String id = "hr";
	private static final String pw = "hr";

	public connect() {
		super();
		// TODO Auto-generated constructor stub
	}

	static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static Connection conn() {
		Connection con = null;
		try {
			con = DriverManager.getConnection(url, id, pw);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return con;

	}

	public static void closeSign(Connection con) {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
