package ContactManagementSystem;

//import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Database {

	private String url = "jdbc:mysql://localhost:3306/contact_management";
	private String user = "root";
	private String password = "";
	private Statement statement;
	
	public Database()throws SQLException {
		Connection connection = DriverManager.getConnection(url,user,password);
		statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
	}
	
	public ArrayList<Contact> getcontacts() throws SQLException{
		ArrayList<Contact> contacts = new ArrayList<>();
		String select = "SELECT * FROM `contacts`;";
		ResultSet rs = statement.executeQuery(select);
		while(rs.next()) {
			Contact c = new Contact();
			c.setID(rs.getInt("ID"));
			c.setFirstName(rs.getString("First Name"));
			c.setLastName(rs.getString("Last Name"));
			c.setPhoneNumber(rs.getString("Phone Number"));
			c.setEmail(rs.getString("Email"));
			contacts.add(c);
		}
		
		return contacts;
	}
	
	public void insertContact(Contact c) throws SQLException {
		String insert = "INSERT INTO `contacts`(`Id`, `First_Name`, `Last_Name`, `Phone_Number`, `Email`) VALUES ('"+c.getID()+"','"+c.getFirstName()+"','"+c.getLastName()+"','"+c.getPhoneNumber()+"','"+c.getEmail()+"')";
		statement.execute(insert);
	}
	
	public void updateContact(Contact c) throws SQLException {
		String update = "UPDATE `contacts` SET `Id`='"+c.getID()+"',`First_Name`='"+c.getFirstName()+"',`Last_Name`='"+c.getLastName()+"',`Phone_Number`='"+c.getPhoneNumber()+"',`Email`='"+c.getEmail()+"'";
		statement.execute(update);
	}
	
	public void deleteContact(Contact c) throws SQLException {
		String delete = "DELETE FROM `contacts` WHERE 'ID' = "+c.getID()+";";
		statement.execute(delete);
	}
	
	public int getNextId() throws SQLException {
		int id = 0;
		ArrayList<Contact> contacts = getcontacts();
		if(contacts.size()!=0) {
			Contact last = contacts.get(contacts.size()-1);
			id = last.getID()+1;
		}
		return id;
	}
}
