package backend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Class with methods to manipulate Utility database.
 *
 * @version 1.0 23/11/21
 *
 * @author Team05
 *
 * Copyright (c) University of Sheffield 2021
 */

public class Utility {

	//the statement to use the query as parameter
		static PreparedStatement stmt = null;
		//result set generated by statement
		static ResultSet rs = null;
		
		/**
	     * A method to add a person to the Utility_Facility database. This works with the Properties database, if the value is false
	     * it will update to true for having the facility
	     *
	     * @param int propertyID, the ID of the property
	     * @param boolean centralHeating, if the property has centralHeating
	     * @param boolean washingMachine, if the property has a washing maching
	     * @param boolean dryingMachine, if the property has drier
	     * @param boolean fireExtinguisher, if the property has a fire extinguisher
	     * @param boolean smokeAlarm, if the property has a smoke alarm
	     * @param boolean firstAidKit, if the property has a first aid kit
	     */
		public static void addUtilities(int propertyID, boolean centralHeating, boolean washingMachine, boolean dryingMachine, boolean fireExtinguisher,
				boolean smokeAlarm, boolean firstAidKit) throws Exception {
			
			if (!Properties.hasUtility(propertyID)) {
				Properties.editProperty(propertyID, "hasUtility", "1");;
			}  else {
				Utility.deleteUtility(propertyID);
			}
			Connection con = DBAccess.connect();
			String query = "INSERT INTO Utility_Facility (propertyID,centralHeating,washingMachine,dryingMachine,fireExtinguisher,smokeAlarm,firstAidKit) "
					+ "VALUES ('" + propertyID + "','" + toTinyInt(centralHeating) + "','" + toTinyInt(washingMachine) + "','" + toTinyInt(dryingMachine) + "','" + toTinyInt(fireExtinguisher) +
					"','" + toTinyInt(smokeAlarm) + "','" + toTinyInt(firstAidKit) + "')";
			//Create statement with try-catch block
			try {
				stmt = con.prepareStatement(query);
				stmt.executeUpdate();
				System.out.println("Utilities have been added to property " + propertyID);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			
			DBAccess.disconnect();
		}
		
		/**
	     * A method to edit the details of a utility facility
	     *
	     * @param int property ID, the facilities property
	     * @param String columnName, the feature to be updated
	     * @param boolean newValue, the new value for the feature
	     */
		public static void editUtility(int propertyID, String columnName, boolean newValue) {
			
			Connection con = DBAccess.connect();
			String query = "UPDATE Utility_Facility SET " + columnName + "='" + toTinyInt(newValue) + "' WHERE propertyID ='" + propertyID + "'";
			//Create statement with try-catch block
			try {
				stmt = con.prepareStatement(query);
				stmt.executeUpdate();
				System.out.println("Property " + propertyID + "'s utility facility facility details " + columnName + " has been updated to " + newValue);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			DBAccess.disconnect();
		
		}
		
		/**
	     * A method for deleting a utility according to the property ID
	     *
	     * @param int propertyID, the IDs property
	     */
		public static void deleteUtility(int propertyID) throws Exception {
			
			Connection con = DBAccess.connect();
			String query = "DELETE FROM Utility_Facility WHERE propertyID = '" + propertyID + "'";
			//Create statement with try-catch block
			try {
				stmt = con.prepareStatement(query);
				stmt.executeUpdate();
				System.out.println("Utilities for property " + propertyID + " deleted." );
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			DBAccess.disconnect();
		
		}
		
		/**
	     * A 'get' method for retrieving the full description of each utility in the database
		 * @return 
	     *
	     * @return boolean toReturn, true if property has central heating
	     */
		public static List<String> getAllUtilities() {
			
			List<String> toReturn = new ArrayList<>();
			String query = "SELECT * FROM Utility_Facility";
			Connection con = DBAccess.connect();
			//Create statement with try-catch block
			try  {
				stmt = con.prepareStatement(query);
				rs = stmt.executeQuery();
				//means, whilst the result has another row
				while (rs.next()) {
					int ID = rs.getInt("propertyID");
					int centralHeating = rs.getInt("centralHeating");
					int washingMachine = rs.getInt("washingMachine");
					int dryingMachine = rs.getInt("dryingMachine");
					int fireExtinguisher = rs.getInt("fireExtinguisher");
					int smokeAlarm = rs.getInt("smokeAlarm");
					int firstAidKit = rs.getInt("firstAidKit");
					
					String toAdd = "Utilities for property " + ID + " are as follows: ";
					String centralHeatingIncluded = "";
					if (centralHeating == 1) {
						centralHeatingIncluded = "Centeral heating Included";
					} else {
						centralHeatingIncluded = "Centeral heating not Included";
					}
					String washingMachineIncluded = "";
					if (washingMachine == 1) {
						washingMachineIncluded = "Washing Machine Included";
					} else {
						washingMachineIncluded = "Washing Machine not Included";
					}
					String dryingMachineIncluded = "";
					if (dryingMachine == 1) {
						dryingMachineIncluded = "Drying Machine Included";
					} else {
						dryingMachineIncluded = "Drying Machine not Included";
					}
					String fireExtinguisherIncluded = "";
					if (fireExtinguisher == 1) {
						fireExtinguisherIncluded = "Fire Extinguisher Included";
					} else {
						fireExtinguisherIncluded = "Fire Extinguisher not Included";
					}
					String smokeAlarmIncluded = "";
					if (smokeAlarm == 1) {
						smokeAlarmIncluded = "Smoke Alarm Included";
					} else {
						smokeAlarmIncluded = "Smoke Alarm not Included";
					}
					String firstAidKitIncluded = "";
					if (firstAidKit == 1) {
						firstAidKitIncluded = "First Aid Kit Included";
					} else {
						firstAidKitIncluded = "First Aid Kit not Included";
					}
					toReturn.add(toAdd + centralHeatingIncluded + ", " + washingMachineIncluded + ", " + dryingMachineIncluded + ", " +
					fireExtinguisherIncluded + ", " + smokeAlarmIncluded + ", " + firstAidKitIncluded);
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			
			DBAccess.disconnect();
			return toReturn;
		}
		
		/**
	     * A 'get' method for retrieving the full description a properties utilities in the database
	     *
	     *@param propertyID, the properties ID
	     * @return boolean toReturn, the string containing the description of a properties utilities
	     */
		public static String getUtilities(int propertyID) {
			
			String toReturn = "";
			String query = "SELECT * FROM Utility_Facility WHERE propertyID = '" + propertyID + "'";
			Connection con = DBAccess.connect();
			//Create statement with try-catch block
			try  {
				stmt = con.prepareStatement(query);
				rs = stmt.executeQuery();
				//means, whilst the result has another row
				while (rs.next()) {
					int ID = rs.getInt("propertyID");
					int centralHeating = rs.getInt("centralHeating");
					int washingMachine = rs.getInt("washingMachine");
					int dryingMachine = rs.getInt("dryingMachine");
					int fireExtinguisher = rs.getInt("fireExtinguisher");
					int smokeAlarm = rs.getInt("smokeAlarm");
					int firstAidKit = rs.getInt("firstAidKit");
					
					String toAdd = "Utilities for property " + ID + " are as follows: ";
					String centralHeatingIncluded = "";
					if (centralHeating == 1) {
						centralHeatingIncluded = "Centeral heating Included";
					} else {
						centralHeatingIncluded = "Centeral heating not Included";
					}
					String washingMachineIncluded = "";
					if (washingMachine == 1) {
						washingMachineIncluded = "Washing Machine Included";
					} else {
						washingMachineIncluded = "Washing Machine not Included";
					}
					String dryingMachineIncluded = "";
					if (dryingMachine == 1) {
						dryingMachineIncluded = "Drying Machine Included";
					} else {
						dryingMachineIncluded = "Drying Machine not Included";
					}
					String fireExtinguisherIncluded = "";
					if (fireExtinguisher == 1) {
						fireExtinguisherIncluded = "Fire Extinguisher Included";
					} else {
						fireExtinguisherIncluded = "Fire Extinguisher not Included";
					}
					String smokeAlarmIncluded = "";
					if (smokeAlarm == 1) {
						smokeAlarmIncluded = "Smoke Alarm Included";
					} else {
						smokeAlarmIncluded = "Smoke Alarm not Included";
					}
					String firstAidKitIncluded = "";
					if (firstAidKit == 1) {
						firstAidKitIncluded = "First Aid Kit Included";
					} else {
						firstAidKitIncluded = "First Aid Kit not Included";
					}
					toReturn = toAdd + centralHeatingIncluded + ", " + washingMachineIncluded + ", " + dryingMachineIncluded + ", " +
					fireExtinguisherIncluded + ", " + smokeAlarmIncluded + ", " + firstAidKitIncluded;
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			
			DBAccess.disconnect();
			return toReturn;
		}
		
		/**
	     * A 'get' method for retrieving if the property has heating
	     *
	     * @param int propertyID, the properties specific ID
	     * @return boolean toReturn, true if property has central heating
	     */
		public static boolean getCentralHeating(int propertyID) {
			
			String query = "SELECT centralHeating FROM Utility_Facility" + " WHERE propertyID = '" + propertyID + "'";;
			Connection con = DBAccess.connect();
			//Create statement with try-catch block
			try  {
				stmt = con.prepareStatement(query);
				rs = stmt.executeQuery();
				//means, whilst the result has another row
				while (rs.next()) {
					int hasCentralHeating = rs.getInt("centralHeating");
					return Properties.toBoolean(hasCentralHeating);
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			
			DBAccess.disconnect();
			return false;
			
		}
		
		/**
	     * A 'get' method for retrieving if the property has a washing machine
	     *
	     * @param int propertyID, the properties specific ID
	     * @return boolean toReturn, true if property has a washing machine
	     */
		public static boolean getWashingMachine(int propertyID) {
			
			String query = "SELECT washingMachine FROM Utility_Facility" + " WHERE propertyID = '" + propertyID + "'";;
			Connection con = DBAccess.connect();
			//Create statement with try-catch block
			try  {
				stmt = con.prepareStatement(query);
				rs = stmt.executeQuery();
				//means, whilst the result has another row
				while (rs.next()) {
					int has = rs.getInt("washingMachine");
					return Properties.toBoolean(has);
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			
			DBAccess.disconnect();
			return false;
			
		}
		
		/**
	     * A 'get' method for retrieving if the property has a drier
	     *
	     * @param int propertyID, the properties specific ID
	     * @return boolean toReturn, true if property has a drier
	     */
		public static boolean getDryingMachine(int propertyID) {
			
			String query = "SELECT dryingMachine FROM Utility_Facility" + " WHERE propertyID = '" + propertyID + "'";;
			Connection con = DBAccess.connect();
			//Create statement with try-catch block
			try  {
				stmt = con.prepareStatement(query);
				rs = stmt.executeQuery();
				//means, whilst the result has another row
				while (rs.next()) {
					int has = rs.getInt("dryingMachine");
					return Properties.toBoolean(has);
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			
			DBAccess.disconnect();
			return false;
			
		}
		
		/**
	     * A 'get' method for retrieving if the property has a fire extinguisher
	     *
	     * @param int propertyID, the properties specific ID
	     * @return boolean toReturn, true if property has a fire extinguisher
	     */
		public static boolean getFireExtinguisher(int propertyID) {
			
			String query = "SELECT fireExtinguisher FROM Utility_Facility" + " WHERE propertyID = '" + propertyID + "'";;
			Connection con = DBAccess.connect();
			//Create statement with try-catch block
			try  {
				stmt = con.prepareStatement(query);
				rs = stmt.executeQuery();
				//means, whilst the result has another row
				while (rs.next()) {
					int has = rs.getInt("fireExtinguisher");
					return Properties.toBoolean(has);
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			
			DBAccess.disconnect();
			return false;
			
		}
		
		/**
	     * A 'get' method for retrieving if the property has a smoke alarm
	     *
	     * @param int propertyID, the properties specific ID
	     * @return boolean toReturn, true if property has a smoke alarm
	     */
		public static boolean getSmokeAlarm(int propertyID) {
			
			String query = "SELECT smokeAlarm FROM Utility_Facility" + " WHERE propertyID = '" + propertyID + "'";;
			Connection con = DBAccess.connect();
			//Create statement with try-catch block
			try  {
				stmt = con.prepareStatement(query);
				rs = stmt.executeQuery();
				//means, whilst the result has another row
				while (rs.next()) {
					int has = rs.getInt("smokeAlarm");
					return Properties.toBoolean(has);
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			
			DBAccess.disconnect();
			return false;
			
		}
		
		/**
	     * A 'get' method for retrieving if the property has a first aid kit
	     *
	     * @param int propertyID, the properties specific ID
	     * @return boolean toReturn, true if property has a first aid kit
	     */
		public static boolean getFirstAidKit(int propertyID) {
			
			String query = "SELECT firstAidKit FROM Utility_Facility" + " WHERE propertyID = '" + propertyID + "'";;
			Connection con = DBAccess.connect();
			//Create statement with try-catch block
			try  {
				stmt = con.prepareStatement(query);
				rs = stmt.executeQuery();
				//means, whilst the result has another row
				while (rs.next()) {
					int has = rs.getInt("firstAidKit");
					return Properties.toBoolean(has);
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			
			DBAccess.disconnect();
			return false;
			
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
}