package DBMS;

import static org.junit.Assert.*;

import java.io.File;
import java.io.PrintWriter;

import org.junit.Test;

public class OwnJunit {
	dbms d = new DataBase();
	@Test
	public void test() throws Throwable {
		assertEquals(dbms.DB_NOT_FOUND,
		d.input("CREATE TABLE Items(ItemID int,Name varchar(255),Price int);"));
		assertEquals(dbms.Con_DB, d.input("CREATE DATABASE D1;"));
		assertEquals(dbms.Con_DB, d.input("CREATE DATABASE D2;"));
		assertEquals(dbms.Con_DB, d.input("CREATE DATABASE D3;"));
		////
		assertEquals(dbms.Con_Table,
				d.input("CREATE TABLE Items(ItemID int,Name varchar(255),Price int);"));
		assertEquals(dbms.TABLE_ALREADY_EXISTS,
				d.input("CREATE TABLE Items(ItemID int,Name varchar(255),Price int);"));
		assertEquals(
				dbms.COLUMN_NOT_FOUND,
				d.input("INSERT INTO Items (ItemID,Name,Color)VALUES (1,'Orange','Red');"));
		assertEquals(
				dbms.COLUMN_TYPE_MISMATCH,
				d.input("INSERT INTO Items (ItemID,Name,Price)VALUES (1,'Orange','Red');"));
		assertEquals(
				dbms.COLUMN_TYPE_MISMATCH,
				d.input("INSERT INTO Items (ItemID,Name,Price)VALUES ('ff','Orange','22');"));
		assertEquals(
				dbms.TABLE_NOT_FOUND,
				d.input("INSERT INTO Persons (PersonID,LastName,FirstName,MiddleName)VALUES (1,'Mohamed','Tamer','Ali');"));
		
		assertEquals(dbms.Con_Delete, d.input("Delete * from Items;"));
		assertEquals(
				dbms.Con_insert,
				d.input("INSERT INTO Items VALUES (1,'Orange',12);"));
		assertEquals(
				dbms.Con_insert,
				d.input("INSERT INTO Items VALUES (2,'Apple',43);"));
		assertEquals(
				dbms.Con_insert,
				d.input("INSERT INTO Items VALUES (3,'Banana',55);"));
		assertEquals(
				dbms.Con_insert,
				d.input("INSERT INTO Items VALUES (4,'Nuts',112);"));
		assertEquals(
				dbms.Con_Update,
				d.input("UPDATE Items SET Name='Avocado', Price='20' WHERE ItemID= 4;"));
		assertEquals(dbms.NOT_MATCH_CRITERIA,
				d.input("DELETE FROM Items WHERE Name='koko';"));

		assertEquals(dbms.Con_Delete,
				d.input("DELETE FROM Items WHERE Price=55;"));
		
		// if u want to delete database name >>

//		PrintWriter writer = new PrintWriter(new File("currentdatabase.txt"));
//		writer.print("");
//		writer.close();
		
	}

}
