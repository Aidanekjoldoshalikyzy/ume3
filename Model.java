import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.JScrollPane;


class Model{
    View view;

    Model(View view) {
	this.view = view;
    }

    public void connect() {
     System.out.println("Connect to DB");
	try{
	Class.forName("com.mysql.jdbc.Driver");
	Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/Fen", "root", "");
	

	Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
//	String sqlQuery = "Select * from ume2 ";
//	String sqlQuery = "Select * from ume where id = '0'";
//	String sqlQuery = "Delete from ume where id = '0'";
//	String sqlQuery = "UPDATE ume SET id ='0', name ='mm', adress = 'bb', phone ='78'";
//	String sqlQuery = "DELETE FROM `ume` WHERE 0 ";
	String sqlQuery = "INSERT INTO ume (name, adress)" + "VALUES ('Cardinal', 'Stavanger')";

//	String sqlQuery = "delete from ume where name = 'Diana'";
//	String sqlQuery = "DELETE from ume";

/////////	String sqlQuery = "UPDATE ume " + "SET name = 'mmax' WHERE id = '4')";	
//	String sqlQuery = "INSERT INTO ume VALUES ('', 'RRR', 'bbb', '4512')";
	ResultSet resultSet = statement.executeQuery(sqlQuery);
	ResultSetMetaData metaData = resultSet.getMetaData();
	int numberOfColumns = metaData.getColumnCount();

	Vector<String> columnIdentifiers = new Vector<String>();
	for(int column = 0; column < numberOfColumns; column++){
		String value  = metaData.getColumnLabel(column + 1);
		columnIdentifiers.addElement(value);
	}
	System.out.println(columnIdentifiers);


	Vector<Vector>dataVector = new Vector<Vector>();
	while(resultSet.next()){
		Vector<String> rowTable = new Vector<String>();
		for(int i=1; i <=numberOfColumns; i++){
			String value = resultSet.getString(i);
			rowTable.addElement(value);
		}	
	System.out.println(rowTable);
	dataVector.addElement(rowTable);
		
	}


	view.frame.remove(view.scrollPane);

        view.table = new JTable(dataVector, columnIdentifiers);
        view.scrollPane = new JScrollPane(view.table);
        view.scrollPane.setBounds(50, 100, 600, 150);

	view.frame.add(view.scrollPane);

	view.update();

	} catch(SQLException sqlExc) {
	   System.out.println(sqlExc);
	} catch(ClassNotFoundException cnfExc) {
	   System.out.println(cnfExc);
	} 

    }
}