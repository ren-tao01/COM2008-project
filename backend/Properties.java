package backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import net.proteanit.sql.DbUtils;

/**
 * Class with methods to manipulate properties database.
 *
 * @version 1.0 23/11/21
 *
 * @author Team05
 *
 * Copyright (c) University of Sheffield 2021
 */

public class Properties {

	//the statement to use the query as parameter
	static PreparedStatement stmt = null;
	//result set generated by statement
	static ResultSet rs = null;
	
	/**
     * This is a void method used to print all properties in the Properties database.
     */
	public static void viewProperties() {
		
		String query = "SELECT * FROM Property";
		Connection con = DBAccess.connect();
		//Create statement with try-catch block
		try  {
			stmt = con.prepareStatement(query);
			rs = stmt.executeQuery();
			//means, whilst the result has another row
			while (rs.next()) {
				int ID = rs.getInt("propertyID");
				String hostID = rs.getString("hostID");
				String shortName = rs.getString("shortName");
				String information = rs.getString("information");
				int breakfast = rs.getInt("breakfast");
				int houseNumber = rs.getInt("houseNumber");
				String houseName = rs.getString("houseName");
				String street = rs.getString("street");
				String city = rs.getString("city");
				String postcode = rs.getString("postcode");
				String bookedBy = rs.getString("bookedBy");
				int hasSleeping = rs.getInt("hasSleeping");
				int hasBathing = rs.getInt("hasBathing");
				int hasKitchen = rs.getInt("hasKitchen");
				int hasLiving = rs.getInt("hasLiving");
				int hasUtility = rs.getInt("hasUtility");
				int hasOutdoor = rs.getInt("hasOutdoor");
				String breakfastIncluded = "";
				if (breakfast == 1) {
					breakfastIncluded = "Breakfast Included";
				} else {
					breakfastIncluded = "Breakfast Not Included";
				}
				
				System.out.println("Host: " + hostID + ", Property ID: " + ID + ", Name: " + shortName + ", Location: " + city + ", " + breakfastIncluded + ", Booked by: " +
						bookedBy);
				//This part is not working yet, problem with the concat as it is returning true when necessary?
				/*
				String facilities = "";
				if (toBoolean(hasSleeping)) {facilities.concat("Sleeping, ");}
				if (toBoolean(hasBathing)) {facilities.concat("Bathing, ");}
				if (toBoolean(hasKitchen)) {facilities.concat("Kitchen, ");}
				if (toBoolean(hasLiving)) {facilities.concat("Living, ");}
				if (toBoolean(hasUtility)) {facilities.concat("Utility, ");}
				if (toBoolean(hasSleeping)) {facilities.concat("and Outdoor");}
				System.out.println("This property has: " + facilities + " facilities.");
				*/
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		DBAccess.disconnect();
		
	}
	
	/**
     * A 'get' method for a hosts result set in properties
     *
     * @param int hostID, the hosts specific ID
     * @return ResultSet rs, the result set containing the hosts properties info
     */
	public static ResultSet getHostsPropertiesResultSet(String hostID) {
		
		String query = "SELECT propertyID,shortName,information,breakfast,houseNumber,houseName,street,city,postcode,hasSleeping,hasBathing,hasKitchen,hasLiving,"
				+ "hasUtility,hasOutdoor FROM Property" + " WHERE hostID = '" + hostID + "'";
		Connection con = DBAccess.connect();
        try  {
			stmt = con.prepareStatement(query);
			rs = stmt.executeQuery();
			return rs;
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return rs;
	}
	
	
	/**
     * A 'get' method for receiving all properties
     *
     * @param int hostID, the hosts specific ID
     * @return List<Integer> toReturn, the property ID's in the system
     */
	public static List<Integer> getAllProperties() {
		
		String query = "SELECT propertyID FROM Property";
		List<Integer> toReturn = new ArrayList<>();
		Connection con = DBAccess.connect();
		//Create statement with try-catch block
		try  {
			stmt = con.prepareStatement(query);
			rs = stmt.executeQuery();
			//means, whilst the result has another row
			while (rs.next()) {
				int propertyID = rs.getInt("propertyID");
				toReturn.add(propertyID);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		DBAccess.disconnect();
		return toReturn;
		
	}
	
	/**
     * A 'get' method for receiving all properties of a single host
     *
     * @param int hostID, the hosts specific ID
     * @return List<Integer> toReturn, the property ID's registered to a host
     */
	public static List<Integer> getHostsProperties(String hostID) {
		
		String query = "SELECT propertyID FROM Property" + " WHERE hostID = '" + hostID + "'";
		List<Integer> toReturn = new ArrayList<>();
		Connection con = DBAccess.connect();
		//Create statement with try-catch block
		try  {
			stmt = con.prepareStatement(query);
			rs = stmt.executeQuery();
			//means, whilst the result has another row
			while (rs.next()) {
				int propertyID = rs.getInt("propertyID");
				toReturn.add(propertyID);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		DBAccess.disconnect();
		return toReturn;
		
	}
	
	/**
     * A 'get' method for receiving the hostID of a property using a propertyID
     *
     * @param int propertyID, the properties specific ID
     * @return String toReturn, the hostsID/email
     */
	public static String getHostID(int propertyID) {
		
		String query = "SELECT hostID FROM Property" + " WHERE propertyID = '" + propertyID + "'";;
		Connection con = DBAccess.connect();
		String toReturn = "";
		//Create statement with try-catch block
		try  {
			stmt = con.prepareStatement(query);
			rs = stmt.executeQuery();
			//means, whilst the result has another row
			while (rs.next()) {
				String hostID = rs.getString("hostID");
				toReturn = toReturn + hostID;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		DBAccess.disconnect();
		return toReturn;
	}
	
	
	/**
     * A 'get' method for finding out who the property is currently booked by of a property using a propertyID
     *
     * @param int propertyID, the properties specific ID
     * @return String toReturn, the person who has currently got the property booked
     */
	public static String getBookedBy(int propertyID) {
		
		String query = "SELECT bookedBy FROM Property" + " WHERE propertyID = '" + propertyID + "'";;
		Connection con = DBAccess.connect();
		String toReturn = "";
		try  {
			stmt = con.prepareStatement(query);
			rs = stmt.executeQuery();
			//means, whilst the result has another row
			while (rs.next()) {
				String bookedBy = rs.getString("bookedBy");
				toReturn = toReturn + bookedBy;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		DBAccess.disconnect();
		return toReturn;
	}
	
	
	/**
     * A 'get' method for finding out who the property is currently requested by of a property using a propertyID
     *
     * @param int propertyID, the properties specific ID
     * @return String toReturn, the person who has currently got the property requested
     */
	public static String getRequestedBy(int propertyID) {
		
		String query = "SELECT requestedBy FROM Property" + " WHERE propertyID = '" + propertyID + "'";;
		Connection con = DBAccess.connect();
		String toReturn = "";
		try  {
			stmt = con.prepareStatement(query);
			rs = stmt.executeQuery();
			//means, whilst the result has another row
			while (rs.next()) {
				String requestedBy = rs.getString("requestedBy");
				toReturn = toReturn + requestedBy;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		DBAccess.disconnect();
		return toReturn;
	}
	

	
	/**
     * A 'get' method for receiving the information about a property provided by the host
     *
     * @param int propertyID, the properties specific ID
     * @return String toReturn, the information for the property
     */
	public static String getInformation(int propertyID) {
		
		String query = "SELECT information FROM Property" + " WHERE propertyID = '" + propertyID + "'";;
		Connection con = DBAccess.connect();
		String toReturn = "";
		//Create statement with try-catch block
		try  {
			stmt = con.prepareStatement(query);
			rs = stmt.executeQuery();
			//means, whilst the result has another row
			while (rs.next()) {
				String information = rs.getString("information");
				toReturn = toReturn + information;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		DBAccess.disconnect();
		return toReturn;
	}
	
	/**
     * A 'get' method for retrieving the general location of a property. This is only the city and postcode.
     * Confidential location (next method) is to be released when parties agree on the booking.
     *
     * @param int propertyID, the properties specific ID
     * @return String toReturn, a string containing the general location details of a property
     */
	public static String getGeneralLocation(int propertyID) {
		
		String query = "SELECT city, postcode FROM Property" + " WHERE propertyID = '" + propertyID + "'";;
		Connection con = DBAccess.connect();
		String toReturn = "";
		//Create statement with try-catch block
		try  {
			stmt = con.prepareStatement(query);
			rs = stmt.executeQuery();
			//means, whilst the result has another row
			while (rs.next()) {
				String city = rs.getString("city");
				String postcode = rs.getString("postcode");
				toReturn = city + ", " + postcode;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		DBAccess.disconnect();
		return toReturn;
		
	}
	
	/**
     * A 'get' method for retrieving the private location of a property. This builds on the city and postcode,
     * adding house number, house name, and street.
     *
     * @param int propertyID, the properties specific ID
     * @return String toReturn, a string containing the full location details of a property
     */
	public static String getPrivateLocation(int propertyID) {
		
		String query = "SELECT city, postcode, houseNumber, houseName, street FROM Property" + " WHERE propertyID = '" + propertyID + "'";;
		Connection con = DBAccess.connect();
		String toReturn = "";
		//Create statement with try-catch block
		try  {
			stmt = con.prepareStatement(query);
			rs = stmt.executeQuery();
			//means, whilst the result has another row
			while (rs.next()) {
				String city = rs.getString("city");
				String postcode = rs.getString("postcode");
				String street = rs.getString("street");
				String houseNumber = rs.getString("houseNumber");
				String houseName = rs.getString("houseName");

				toReturn = houseName + "," + houseNumber + "," + street + ", " + city + ", " + postcode;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		DBAccess.disconnect();
		return toReturn;
		
	}
	
	/**
     * A set method for setting who the property is currently requested by of a property using a propertyID
     *
     * @param int propertyID, the properties specific ID
     * @return String toReturn, the person who has currently got the property requested
     */
	public static void setRequestedBy(int propertyID, String newRequest) {
		
		String alreadyRequested = getRequestedBy(propertyID);
		if (alreadyRequested.isEmpty()) {
			try {
				editProperty(propertyID, "requestedBy", newRequest);
				System.out.println("This property has now been requested by: " + newRequest);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				editProperty(propertyID, "requestedBy", alreadyRequested + ", " + newRequest);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
     * A set method for setting who the property is currently requested by of a property using a propertyID
     *
     * @param int propertyID, the properties specific ID
     * @return String toReturn, the person who has currently got the property requested
     */
	public static void setBookedBy(int propertyID, String newBooker) {
		
		String alreadyBooked = getBookedBy(propertyID);
		if (alreadyBooked.isEmpty()) {
			try {
				editProperty(propertyID, "bookedBy", newBooker);
				System.out.println("This property has now been booked by: " + newBooker);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("This property is already booked!");
		}
	}
	
	/**
     * A method for retrieving if the property has kitchen facilities
     *
     * @param int propertyID, the properties specific ID
     * @return boolean toReturn, true if property has a kitchen
     */
	public static boolean hasKitchen(int propertyID) {
		
		String query = "SELECT hasKitchen FROM Property" + " WHERE propertyID = '" + propertyID + "'";;
		Connection con = DBAccess.connect();
		//Create statement with try-catch block
		try  {
			stmt = con.prepareStatement(query);
			rs = stmt.executeQuery();
			//means, whilst the result has another row
			while (rs.next()) {
				int hasKitchen = rs.getInt("hasKitchen");
				return toBoolean(hasKitchen);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		DBAccess.disconnect();
		return false;
		
	}
	
	/**
     * A method for retrieving if the property has bathing facilities
     *
     * @param int propertyID, the properties specific ID
     * @return boolean toReturn, true if property has bathing
     */
	public static boolean hasBathing(int propertyID) {
		
		String query = "SELECT hasBathing FROM Property" + " WHERE propertyID = '" + propertyID + "'";;
		Connection con = DBAccess.connect();
		//Create statement with try-catch block
		try  {
			stmt = con.prepareStatement(query);
			rs = stmt.executeQuery();
			//means, whilst the result has another row
			while (rs.next()) {
				int hasBathing = rs.getInt("hasBathing");
				return toBoolean(hasBathing);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		DBAccess.disconnect();
		return false;
		
	}
	
	/**
     * A method for retrieving if the property has sleeping facilities
     *
     * @param int propertyID, the properties specific ID
     * @return boolean toReturn, true if property has sleeping facilities
     */
	public static boolean hasSleeping(int propertyID) {
		
		String query = "SELECT hasSleeping FROM Property" + " WHERE propertyID = '" + propertyID + "'";;
		Connection con = DBAccess.connect();
		//Create statement with try-catch block
		try  {
			stmt = con.prepareStatement(query);
			rs = stmt.executeQuery();
			//means, whilst the result has another row
			while (rs.next()) {
				int hasSleeping = rs.getInt("hasSleeping");
				return toBoolean(hasSleeping);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		DBAccess.disconnect();
		return false;
		
	}
	
	/**
     * A method for retrieving if the property has living facilities
     *
     * @param int propertyID, the properties specific ID
     * @return boolean toReturn, true if property has living facilities
     */
	public static boolean hasLiving(int propertyID) {
		
		String query = "SELECT hasLiving FROM Property" + " WHERE propertyID = '" + propertyID + "'";;
		Connection con = DBAccess.connect();
		//Create statement with try-catch block
		try  {
			stmt = con.prepareStatement(query);
			rs = stmt.executeQuery();
			//means, whilst the result has another row
			while (rs.next()) {
				int hasLiving = rs.getInt("hasLiving");
				return toBoolean(hasLiving);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		DBAccess.disconnect();
		return false;
		
	}
	
	/**
     * A method for retrieving if the property has utility facilities
     *
     * @param int propertyID, the properties specific ID
     * @return boolean toReturn, true if property has utility facilities
     */
	public static boolean hasUtility(int propertyID) {
		
		String query = "SELECT hasUtility FROM Property" + " WHERE propertyID = '" + propertyID + "'";;
		Connection con = DBAccess.connect();
		//Create statement with try-catch block
		try  {
			stmt = con.prepareStatement(query);
			rs = stmt.executeQuery();
			//means, whilst the result has another row
			while (rs.next()) {
				int hasUtility = rs.getInt("hasUtility");
				return toBoolean(hasUtility);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		DBAccess.disconnect();
		return false;
		
	}
	
	/**
     * A method for retrieving if the property has living facilities
     *
     * @param int propertyID, the properties specific ID
     * @return boolean toReturn, true if property has living facilities
     */
	public static boolean hasOutdoor(int propertyID) {
		
		String query = "SELECT hasOutdoor FROM Property" + " WHERE propertyID = '" + propertyID + "'";;
		Connection con = DBAccess.connect();
		//Create statement with try-catch block
		try  {
			stmt = con.prepareStatement(query);
			rs = stmt.executeQuery();
			//means, whilst the result has another row
			while (rs.next()) {
				int hasOutdoor = rs.getInt("hasOutdoor");
				return toBoolean(hasOutdoor);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		DBAccess.disconnect();
		return false;
		
	}
	
	/**
     * This is a helper function for finding out the latest property ID. This allows it so when a property is made,
     * it will automatically be assigned the latest property ID +1
     *
     * @return int latestID, the number of the highest property ID.
     */
	public static int viewLatestPropertyID() {
		
		String query = "SELECT propertyID FROM Property";
		int latestID = 0;
		Connection con = DBAccess.connect();
		//Create statement with try-catch block
		try  {
			stmt = con.prepareStatement(query);
			rs = stmt.executeQuery();
			//means, whilst the result has another row
			while (rs.next()) {
				int ID = rs.getInt("propertyID");
				if (latestID < ID) {
					latestID = ID;
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		DBAccess.disconnect();
		return latestID;
	}
	
	/**
     * A void 'get' kind of method for printing the properties what have a certain value in a column.
     * For example, if I wanted to retrieve properties with the postcode S10 5BQ I would put
     * viewCertainProperties("postcode", "S105BQ")
     *
     * @param String columnName, the feature you would like to search by
     * @param String columnValue, the corresponding value of the feature 
	 * @return 
     */
	public static List viewCertainProperties(String columnName, String columnValue) {
		
		int newInt = 0;
		int bValue = 0;
		
		//this is so if the input in columnValue is a number or a string, it will be converted.
		String query = "SELECT * FROM Property" + " WHERE " + columnName + "= '" + columnValue + "'";
		if (columnName == "propertyID" || columnName == "houseNumber") {
			newInt = Integer.valueOf(columnValue);
			query = "SELECT * FROM Property" + " WHERE " + columnName + "= '" + newInt + "'";
		} else if (columnValue == "true") {
			bValue = 1;
			query = "SELECT * FROM Property" + " WHERE " + columnName + "= '" + bValue + "'";
		} else if (columnValue == "false") {
			query = "SELECT * FROM Property" + " WHERE " + columnName + "= '" + bValue + "'";
		}
		Connection con = DBAccess.connect();
		//Create statement with try-catch block
		List result = new ArrayList();
		try  {
			stmt = con.prepareStatement(query);
			rs = stmt.executeQuery();
			
			//means, whilst the result has another row
			while (rs.next()) {
				
				HashMap dict = new HashMap();
				int ID = rs.getInt("propertyID");
				dict.put("propertyID", ID);
				String hostID = rs.getString("hostID");
				dict.put("hostID", hostID);
				String shortName = rs.getString("shortName");
				dict.put("shortName", shortName);
				String information = rs.getString("information");
				dict.put("information", information);
				int breakfast = rs.getInt("breakfast");
				dict.put("breakfast", breakfast);
				int houseNumber = rs.getInt("houseNumber");
				dict.put("houseNumber", houseNumber);
				String houseName = rs.getString("houseName");
				dict.put("houseName", houseName);
				String street = rs.getString("street");
				dict.put("street", street);
				String city = rs.getString("city");
				dict.put("city", city);
				String postcode = rs.getString("postcode");
				dict.put("postcode", postcode);
//				String picture = rs.getString("picture");
				String bookedBy = rs.getString("bookedBy");
				dict.put("bookedBy", bookedBy);
				String reqBy = rs.getString("requestedBy");
				dict.put("requestedBy", reqBy);
				
				result.add(dict);
//				System.out.println("Host: " + hostID + ", Property ID: " + ID + ", Name: " + shortName + ", Location: " + city );
			}
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		DBAccess.disconnect();
		return result;
		
	}
	
	/**
     * A method to change a boolean value to a tiny int, to avoid confusion when adding properties
     *
     * @param boolean i, the boolean to be converted
     * @return toReturn, corresponding value of 1 (true) or 0 (false)
     */
	public static int toTinyInt(boolean i) throws IllegalArgumentException {
		if (!(i == true || i == false)) {throw new IllegalArgumentException("Value must be true or false");}
		int toReturn = 0;
		if (i == true) {
			return 1;
		}
		return toReturn;
	}
	
	/**
     * method to change an int to a boolean value, to avoid confusion again and to use in methods
     *
     * @param int i, to be converted
     * @return toReturn, corresponding value of true (1) or false (0)
     */
	public static boolean toBoolean(int i) throws IllegalArgumentException {
		if (!(i == 1 || i == 0)) {throw new IllegalArgumentException("Value must be 1 or 0");}
		boolean toReturn = false;
		if (i == 1) {
			return true;
		}
		return toReturn;
	}
	
	/**
     * This adds a property. Automatically sets the bookedBy as null.
     *
     * @param String hostID, the owner of the property
     * @param String shortName, the short name for the house
     * @param String information, basic information given about the house, like extra notes
     * @param Boolean breakfast, if the property provides breakfast
     * @param int houseNumber, the houses house number
     * @param String houseName, the name of the house
     * @param String street, the houses street
     * @param String city, the city of the house
     * @param String postcode, the houses post code
     * @param picture, the picture file name of the house
     * @param boolean hasSleeping, if the house has sleeping facilities
     * @param boolean hasBathing, if the house has bathing facilities
     * @param boolean hasKitchen, if the house has kitchen facilities
     * @param boolean hasLiving, if the house has living facilities
     * @param boolean hasUtility, if the house has utility facilities
     * @param boolean hasOutdoor, if the house has outdoor facilities
     */
	public static void addProperty(String hostID, String shortName, String information, boolean breakfast, int houseNumber, String houseName, String street,
		String city, String postcode, boolean hasSleeping, boolean hasBathing, boolean hasKitchen, boolean hasLiving, boolean hasUtility, 
		boolean hasOutdoor) throws Exception {
	
		Connection con = DBAccess.connect();
		
		if (!Host.userExists(hostID)) {
			throw new IllegalArgumentException("Host ID needs to match an existing Host.");
		}
		
		int propertyID = viewLatestPropertyID() + 1;
		
		String query = "INSERT INTO Property (propertyID, hostID, shortName, information, breakfast, houseNumber, houseName, street, city, postcode, "
				+ "hasSleeping, hasBathing, hasKitchen, hasLiving, hasUtility, hasOutdoor, bookedBy, requestedBy) "
				+ "VALUES ('" + propertyID + "','" + hostID + "','" + shortName + "','" + information + "','" + toTinyInt(breakfast) + "','" + houseNumber + "','" 
				+ houseName + "','" + street + "','" + city + "','" + postcode + "','" + toTinyInt(hasSleeping) + "','" + toTinyInt(hasBathing) + "','" 
				+ toTinyInt(hasKitchen) + "','" + toTinyInt(hasLiving) + "','" + toTinyInt(hasUtility) + "','" + toTinyInt(hasOutdoor) + "','" + "" + "" + "','" +"')";
		
		//Create statement with try-catch block
		try {
			stmt = con.prepareStatement(query);
			stmt.executeUpdate(query);
			System.out.println("Property " + propertyID + " added.");
			Host.updateNumProperties(hostID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		DBAccess.disconnect();
	}
		
	/**
     * This method deletes a property according to its propertyID (unique primary key).
     * Also updates the number of properties a host has automatically.
     *
     * @param int propertyID, the properties specific ID
     */
	public static void deleteProperty(int propertyID) throws Exception {
		
		Connection con = DBAccess.connect();
		String query = "DELETE FROM Property WHERE propertyID = '" + propertyID + "'";
		//Create statement with try-catch block
		try {
			//need to delete these before deleting from PK table
			Review.deleteReviews(propertyID);
			ChargeBands.deleteChargeBands(propertyID);
			Bookings.deletePropertiesBookings(propertyID);
			if (Properties.hasUtility(propertyID)) { Utility.deleteUtility(propertyID); }
			if (Properties.hasKitchen(propertyID)) { Kitchen.deleteKitchen(propertyID); }
			if (Properties.hasLiving(propertyID)) { Living.deleteLiving(propertyID); }
			if (Properties.hasBathing(propertyID)) { Bathing.deleteBathing(propertyID); }
			if (Properties.hasOutdoor(propertyID)) { Outdoor.deleteOutdoor(propertyID); }
			if (Properties.hasSleeping(propertyID)) { Sleeping.deleteSleeping(propertyID); }

			//then, deleting from property table
			stmt = con.prepareStatement(query);
			stmt.executeUpdate(query);
			System.out.println("Property " + propertyID + " deleted." );
			String host = getHostID(propertyID);
			Host.updateNumProperties(host);
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		DBAccess.disconnect();
	
	}

	
	/**
     * This is a method used to delete all of a hosts properties. This is used in Person, where if
     * a person is deleted all of their properties are also deleted.
     *
     * @param String email, the hosts unique email
     */
	public static void deleteHostsProperties(String email) throws Exception {
		
		List<Integer> propNums = getHostsProperties(email);
		//Create statement with try-catch block
		try {
			for (int i=0; i < propNums.size(); i++) {
				deleteProperty(propNums.get(i));
			}
			System.out.println("Host " + email + "'s properties have been deleted." );
			Host.updateNumProperties(email);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		DBAccess.disconnect();
	
	}
	
	/**
     * A 'get' method for retrieving the general location of a property. This is only the city and postcode.
     * Confidential location (next method) is to be released when parties agree on the booking.
     *
     * @param int propertyID, the properties specific ID
     * @param String columnName, the feature assignment of the property to be changed
     * @return String newValue, the new value to be replacing the old in the specific feature.
     */
	public static void editProperty(int propertyID, String columnName, String newValue) throws Exception {
		
		Connection con = DBAccess.connect();
		if (columnName == "breakfast" || columnName == "houseNumber" || columnName == "hasSleeping" || columnName == "hasBathing" || 
				columnName == "hasKitchen" || columnName == "hasLiving" || columnName == "hasUtility" || columnName == "hasOutdoor") {
			int newValueInt = Integer.parseInt(newValue);
			String query = "UPDATE Property SET " + columnName + "='" + newValueInt + "' WHERE propertyID ='" + propertyID + "'";
			//Create statement with try-catch block
			try {
				stmt = con.prepareStatement(query);
				stmt.executeUpdate(query);
				System.out.println("Property " + propertyID + "'s " + columnName + " has been updated to " + newValue);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			String query = "UPDATE Property SET " + columnName + "='" + newValue + "' WHERE propertyID ='" + propertyID + "'";
			//Create statement with try-catch block
			try {
				stmt = con.prepareStatement(query);
				stmt.executeUpdate(query);
				System.out.println("Property " + propertyID + "'s " + columnName + " has been updated to " + newValue);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		DBAccess.disconnect();
	
	}
	
	/**
     * A boolean method to check if a user exists
     * 
     * @param userID, the persons email
     */
	public static boolean propertyExists(int propertyID) {
		
		String query = "SELECT * FROM Property WHERE propertyID = '" + propertyID + "'";
		Connection con = DBAccess.connect();
		//Create statement with try-catch block
		try  {
			stmt = con.prepareStatement(query);
			rs = stmt.executeQuery();
			//means, whilst the result has another row
			while (rs.next()) {
				return true;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		}
		
		DBAccess.disconnect();
		
		return false;
	}
	
	public static void deleteAllProperties() {
		List<Integer> propIDs = getAllProperties();
		for (int i=0; i<= getAllProperties().size(); i++) {
			try {
				deleteProperty(propIDs.get(i));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
		
	//This main method is just to demonstrate how everything works :)
	public static void main(String[] args) throws Exception {
		/*Person.addPerson("@test.co.uk", "Mr", "Test", "Man", "HOST", "07958484745", "Test123");
		Person.addPerson("@tester.co.uk", "Mr", "Test", "Man", "HOST", "07958484745", "Test123");
		addProperty("@test.co.uk", "crazy building", "This building is mint", true, 69, "castle1", "sheffield lane",
				"sheffield", "s10 5bh", true, true, true, true, true, true);
		addProperty("@tester.co.uk", "crazyyy building", "This building is good", false, 420, "castle2", "sheffield road",
				"sheffield", "s10 5bz", true, true, false, true, true, true);
		setBookedBy(1, "Jordan");
		System.out.println(getBookedBy(1));
		System.out.println(hasBathing(1));
		viewProperties();
		viewCertainProperties("propertyID", "1");
		editProperty(getHostsProperties("@test.co.uk").get(0), "information", "This building is mental");
		viewProperties();
		Person.deletePerson("@test.co.uk");
		Person.deletePerson("@tester.co.uk");
		viewProperties();*/
		//this part just makes sure all properties added for this demo get deleted
		/*
		List<Integer> propIDs = getAllProperties();
		for (int i=0; i<= getAllProperties().size(); i++) {
			deleteProperty(propIDs.get(i));
		}
		*/
		System.out.print(Properties.hasLiving(23));
		Properties.deleteAllProperties();
	}
	
}
