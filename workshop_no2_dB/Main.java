package workshop_no2_dB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {

	public static void main(String[] args) {
		User firstUser = new User();
		firstUser.setUsername("Jerzy");
		firstUser.setEmail("jurekkiler@rysia.pl");
		firstUser.setPassword("numerboczny7775");
		User secondUser = new User("Marian", "marianp@rysia.pl", "Pazdzioch69");
		User thirdUser = new User("Boczek", "marianp@rysia.pl", "ArnoldRzeznik");
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/active_record?useSSL=false", 
				"root", 
				"coderslab")) {
//			firstUser.saveToDB(conn);
//			secondUser.saveToDB(conn);
//			thirdUser.saveToDB(conn);
//			System.out.println(User.loadUserById(conn, 2));
			User editedUser = User.loadUserById(conn, 1);
			editedUser.delete(conn);
			System.out.println(editedUser.getId());
			User[] temp = User.loadAllUsers(conn);
			for (int i=0; i<temp.length; i++) {
				System.out.println(temp[i]);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
