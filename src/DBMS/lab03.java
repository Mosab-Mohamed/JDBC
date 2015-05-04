package DBMS;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

import junit.framework.TestCase;

import org.junit.Test;

public class lab03 extends TestCase {

	dbms d = new DataBase(); // need to be initialized by the implemented class

	@Test
	public void test_1() throws Throwable {
		assertEquals(
				dbms.PARSING_ERROR,
				d.input("CREATE TABLE Persons PersonID int,LastName varchar(255),FirstName varchar(255),Address varchar(255),City varchar(255);"));
		assertEquals(dbms.PARSING_ERROR, d.input("CRETE DATABASE LAB3;"));

		assertEquals(
				dbms.PARSING_ERROR,
				d.input("INSERT Persons (PersonID,LastName,FirstName,MiddleName)VALUES (1,'Mohamed','Tamer','Ali');"));

		assertEquals(dbms.DB_NOT_FOUND, d.input("DELETE FROM Persons;"));

		assertEquals(dbms.PARSING_ERROR, d.input("SELECT *  Customers;"));

		assertEquals(
				dbms.PARSING_ERROR,
				d.input("UPDATE Customers  ContactName='Alfred Schmidt', City='Hamburg' WHERE CustomerName='Alfreds Futterkiste'; "));

	}

	@Test
	public void test_2() throws Throwable {
		assertEquals(
				dbms.DB_NOT_FOUND,
				d.input("CREATE TABLE Persons(PersonID int,LastName varchar(255),FirstName varchar(255),Address varchar(255),City varchar(255));"));
		assertEquals(dbms.Con_DB, d.input("CREATE DATABASE LAB3;"));
		assertEquals(
				dbms.TABLE_NOT_FOUND,
				d.input("INSERT INTO O (PersonID,LastName,FirstName)VALUES (1, 23,'Tamer');"));

		assertEquals(
				dbms.Con_Table,
				d.input("CREATE TABLE Persons(PersonID int,LastName varchar(255),FirstName varchar(255),Address varchar(255),City varchar(255));"));
		assertEquals(
				dbms.TABLE_ALREADY_EXISTS,
				d.input("CREATE TABLE Persons(PersonID int,LastName varchar(255),FirstName varchar(255),Address varchar(255),City varchar(255));"));

		assertEquals(
				dbms.COLUMN_NOT_FOUND,
				d.input("INSERT INTO Persons (PersonID,LastName,FirstName,MiddleName)VALUES (1,'Mohamed','Tamer','Ali');"));

		assertEquals(
				dbms.COLUMN_TYPE_MISMATCH,
				d.input("INSERT INTO Persons (PersonID,LastName,FirstName)VALUES (1, 23,'Tamer');"));

		assertEquals(
				dbms.TABLE_NOT_FOUND,
				d.input("INSERT INTO O (PersonID,LastName,FirstName)VALUES (1, 23,'Tamer');"));

	}

	@Test
	public void test_3() throws Throwable {
		assertEquals(dbms.Con_Delete, d.input("Delete * from Persons;"));
		assertEquals(
				dbms.Con_insert,
				d.input("INSERT INTO Persons (PersonID,LastName,FirstName,Address,City) VALUES (1,'Mohamed','Ali','11 street','Tanta');"));
		assertEquals(
				dbms.Con_insert,
				d.input("INSERT INTO Persons (PersonID,LastName,FirstName,Address,City) VALUES (2,'Mohamed','Tamer','11 street','Alexandria');"));
		assertEquals(
				dbms.Con_insert,
				d.input("INSERT INTO Persons VALUES (3,'Ahmad','Mohsen','12 street','Cairo');"));
		assertEquals(
				dbms.Con_insert,
				d.input("INSERT INTO Persons VALUES (4,'Bassem','Yasser','43 street','Banha');"));

		assertEquals(
				"1 Mohamed Ali 11 street Tanta\n2 Mohamed Tamer 11 street Alexandria\n3 Ahmad Mohsen 12 street Cairo\n4 Bassem Yasser 43 street Banha",
				d.input("select * from Persons"));

		assertEquals(
				"2 Mohamed Tamer 11 street Alexandria\n3 Ahmad Mohsen 12 street Cairo\n4 Bassem Yasser 43 street Banha",
				d.input("select * from Persons where PersonID > 1"));

		assertEquals(
				"1 Mohamed Ali 11 street Tanta\n2 Mohamed Tamer 11 street Alexandria",
				d.input("select * from Persons where LastName='Mohamed'"));

		assertEquals(
				"Mohsen",
				d.input("select FirstName from Persons where Address='12 street'"));

		assertEquals(
				dbms.Con_Update,
				d.input("UPDATE Persons SET LastName='Salem', City='Hamburg' WHERE PersonID= 1;"));    // names

		assertEquals("Ali",
				d.input("select FirstName from Persons where City='Hamburg'"));

		assertEquals("1 Salem Ali 11 street Hamburg",
				d.input("select * from Persons where City='Hamburg'"));

		assertEquals(dbms.NOT_MATCH_CRITERIA,
				d.input("DELETE FROM Persons WHERE LastName='salem';"));

		assertEquals(dbms.Con_Delete,
				d.input("DELETE FROM Persons WHERE LastName='Salem';"));

		assertEquals(dbms.NOT_MATCH_CRITERIA,
				d.input("select * from Persons where City='Hamburg'"));
		
		// if u want to delete database name >>
			
//		PrintWriter writer = new PrintWriter(new File("currentdatabase.txt"));
//		writer.print("");
//		writer.close();
	}
	
	
	
}
