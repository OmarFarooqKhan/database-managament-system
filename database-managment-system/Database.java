import java.sql.*;
import java.util.Random;

public class Database {
	private final static String Username = "HIDDEN";
	private final static String Password = "HIDDEN";
	static String dbName = "jdbc:postgresql://mod-intro-databases.cs.bham.ac.uk/" + Username;

	public Database() {
		Connection dbConn = null;
		Statement stmnt;
		System.setProperty("jdbc.drivers", "org.postgresql.Driver");
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException ex) {
			System.out.println("Driver not found");
		}

		try {
			System.out.println("Connecting to database");
			dbConn = DriverManager.getConnection(dbName, Username, Password);
			
			System.out.println("Connected");
			stmnt = dbConn.createStatement();
			
			System.out.println("Clearing database");
			clearDb(stmnt);
			
			System.out.println("Creating tables");
			createTables(stmnt);
			
			System.out.println("Populating tables");
			populateVenue(stmnt);
			populateEntertainment(stmnt);
			populateMenu(stmnt);
			populateParty(stmnt);
			CommandLineInterface ui = new CommandLineInterface(dbConn);
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Connection failed");
		}
		finally {

				dbClose(dbConn);	
		}
	}
	
	public Connection getConn(Connection dbc) {
			return dbc;
	}
	
	public void dbClose(Connection db) {
			try {
				db.close();
				System.out.println("Closing connection");
			} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public void createTables(Statement stmnt) throws SQLException {
	String s = 
		"CREATE TABLE ENTERTAINMENT( "	+ "eid		    	INTEGER not NULL, "
								      	+ "description  	VarCHAR(255), " 
								      	+ "costprice   	MONEY, " 
								      	+ "			   	PRIMARY KEY(eid)); " 
								      	+

				"CREATE TABLE Menu( " 	+ "mid          	INTEGER not NULL, " 
										+ "description	varCHAR(255), "
										+ "costprice    	MONEY, " 
										+ "			   	PRIMARY KEY(mid)); "				
										+
										
				"CREATE TABLE Venue( " 	+ "vid          	INTEGER not NULL, "
										+ "name			varCHAR(255), "
										+ "venuecost    	MONEY, "
										+ "			   	PRIMARY KEY(vid)); " 
										+
				"CREATE TABLE Party( " 	+ "pid          	SERIAL not NULL, "
										+ "name			varCHAR(255), "
										+ "mid		    	INTEGER not NULL, "
										+ "vid			INTEGER not null, "
										+ "eid			INTEGER not null, " 
										+ "price			MONEY, "
										+ "timing       	TIMESTAMP, "
										+ "numberofguests INTEGER, "
										+ "			   	PRIMARY KEY(pid), " 
										+ "FOREIGN KEY (mid) REFERENCES Menu(mid), "
										+ "FOREIGN KEY (vid) REFERENCES Venue(vid), " 
										+ "FOREIGN KEY (eid) REFERENCES Entertainment(eid));";
		stmnt.executeUpdate(s);
	}
	
	
	
	public void populateVenue(Statement stmnt)  {
		String name;
		int venuecost;		
		String  []c = {"'School'", "'Great hall'", "'O2 arena'", "'Bramhall'", "'Manchester arena'", "'CST venue'",
				"'Roundhouse'", "'The arena'", "'Barclays center'", "'The venue'" };
		
        for (int i = 1; i < 101; i++) {
        		name = c[new Random().nextInt(c.length)];
        		venuecost = new Random().nextInt(10000) + 200;
      
        		String s = "INSERT INTO Venue(vid,name,venuecost) VALUES ("+ i +"," +
        		name + "," + venuecost + ");" ; 
        		
        		try {
					stmnt.executeUpdate(s);
				} catch (SQLException e) {

					e.printStackTrace();
				}
        }
	}
	
	
	public void populateEntertainment(Statement stmnt)  {
		String description;
		int costprice;  
		
		String[] c = { "'Clown'", "'Rock band'", "'Jazz band'", "'Opera singer'", "'Group of singers'",
				"'Christmas band'", "'Comedian'", "'Magican'", "'Dance show'", "'Live entertainment'" };
		
        for (int i = 1; i < 101; i++) {
        		description = c[new Random().nextInt(c.length)];
        		costprice = new Random().nextInt(1000) + 50;
        		String s = "INSERT INTO Entertainment(eid,description,costprice) VALUES ("+ i + "," +
        		description + "," + costprice + ");";
      
        		try {
					stmnt.executeUpdate(s);
				} catch (SQLException e) {
					e.printStackTrace();
				}
        }
	}
	
	
	
	
	public void populateParty(Statement stmnt) {
		String name;
		int price;
		int vid;
		int mid; 
		int eid;
		int numberofguests;
	
		String[] c = {"'Christmas dinner'","'Birthday party'","'Childrens party'","'Wedding'",
				"'New years'","'Engagement'","'Anniversary'","'Christmas ball'","'Re-union'",
				"'Winterball'"};
	
        for (int i = 1; i < 1001; i++) {
        		name  = c[new Random().nextInt(c.length)];
        		price = new Random().nextInt(12000) + 200;
        		numberofguests = new Random().nextInt(150) + 7;
        		mid = new Random().nextInt(100)+1;
        		vid = new Random().nextInt(100)+1;
        		eid = new Random().nextInt(100)+1;
        		
        		String s = "INSERT INTO Party(name,mid,vid,eid,price,timing,numberofguests) VALUES ("+
        		name + "," + mid  + "," + vid  + ","   + 
        		eid  + "," + price+ ",'"+ timeStamp() +"',"+ numberofguests + ");"   ; 
        
        try {
			stmnt.executeUpdate(s);
		} catch (SQLException e) {
			e.printStackTrace();
		}
        }
	}
	
	
	
	public void populateMenu(Statement stmnt) {
		String description;
		int costprice;
		
		String[] c = {"'Indian'","'Christmas themed'","'Chinese'","'Italian'","'French'","'Spanish'","'Vegan menu'","'Festive menu'","'British'","'Greek'"};
		
 		for (int i = 1; i < 101; i++) {
    			description = c[new Random().nextInt(c.length)];
    			costprice = new Random().nextInt(100) + 10;
    		String s = "INSERT INTO Menu (mid,description,costprice) VALUES ("+ i + "," +
    		description + "," + costprice + ");"  ; 
    		try {
				stmnt.executeUpdate(s);
			} catch (SQLException e) {
				e.printStackTrace();
			}
 		}
 		
	}
	
	
	
	
	private Timestamp timeStamp() {
		long offset = Timestamp.valueOf("2018-12-01 00:00:00").getTime();
		long end = Timestamp.valueOf("2019-01-01 00:00:00").getTime();
		long diff = end - offset + 1;
		Timestamp rand = new Timestamp(offset + (long)(Math.random() * diff));
		return rand;
	}
	
	public static void partyReport(Connection dbConn, int id) {
		 String sql = "SELECT party.pid, party.name, venue.name,  menu.description,"
				+ " entertainment.description,"
				+ " party.numberofguests, party.price, "
				+ " (venue.venuecost + entertainment.costprice "
				+ "+ (party.numberofguests * menu.costprice)) AS partycost"
				+ " FROM party "
				+ "INNER JOIN menu ON party.mid = menu.mid "
				+ "INNER JOIN venue ON party.vid = venue.vid "
				+ "INNER JOIN entertainment ON party.eid = entertainment.eid WHERE party.pid = ?";
				
		try {
			PreparedStatement stmnt = dbConn.prepareStatement(sql);
			int pid = Integer.valueOf(id);
			stmnt.setInt(1, pid);
			ResultSet rs = stmnt.executeQuery();
			
		if (rs.next()) {
			//SELECT party.pid, party.name, venue.name,  menu.description, entertainment.description, party.numberofguests, party.price,  (venue.venuecost + entertainment.costprice + (party.numberofguests * menu.costprice)) AS partycost FROM party INNER JOIN menu on party.mid = menu.mid INNER JOIN venue on party.vid = venue.vid INNER JOIN entertainment on party.eid = entertainment.eid;
			int numOfGuests = Integer.parseInt(rs.getString(6).replaceAll("[^0-9]", ""));
			int price =  Integer.parseInt(rs.getString(7).replaceAll("[^0-9]", ""));
			int partyCost = Integer.parseInt(rs.getString(8).replaceAll("[^0-9]", ""));
			int netprofit = (numOfGuests * price) - partyCost;
			System.out.println(""
					+ "Party ID: " + rs.getInt(1) +"\n"
					+ "Party name: " + rs.getString(2)+"\n"
					+ "Venue name: " + rs.getString(3)+"\n"
					+ "Menu: " + rs.getString(4)+"\n"
					+ "Entertainment: "+ rs.getString(5)+"\n"
					+ "Number of guests: "+rs.getInt(6)+"\n"
					+ "Price charged: "+rs.getString(7)+"\n"
					+ "Total cost: "+ rs.getString(8)+"\n"
					+ "Net profit: £"+ netprofit
					);
			}
		stmnt.close();
		}
	
		catch (SQLException e) { 
			e.printStackTrace();
		}
		

	}
	
	public static void menuReport(Connection dbConn, int id) {
		String sql ="SELECT menu.mid, menu.description, menu.costprice, party.numberofguests FROM party, menu WHERE party.mid= menu.mid and menu.mid = ?";
				
		try {
			PreparedStatement stmnt = dbConn.prepareStatement(sql);
			stmnt.setInt(1, id);
			ResultSet rs = stmnt.executeQuery();
			if (rs.next()) {
				System.out.println(""
						+ "Menu ID: " + rs.getString(1) +"\n"
						+ "Menu: " + rs.getString(2)+ "\n"
						+ "Menu cost: " + rs.getString(3) + "\n"
						//+ "Number of times menu was used: " +rs.getString(4) + "\n" //SELECT mid, SUM(numberofguests) AS Usage FROM party menu GROUP BY mid;
						+ "Number of guests: " +rs.getString(4) + "\n"
						);
			}
			stmnt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
		
	
	
	
	
	public void clearDb(Statement stmnt)  {
		String SQLstatement = "DROP TABLE Venue, Menu, Entertainment, Party CASCADE";
        try {
			stmnt.executeUpdate(SQLstatement);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
