package org.anup.javabrains.messenger.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.anup.javabrains.messenger.model.Message;

public class MessageUpdateObject {
	public Connection conn=null;
	 String DB_URL = "jdbc:mysql://www.papademas.net:3306/dbsec";
     String USER = "stud10";
	 String PASS = "stud10";
	 
	 Statement stmt = null;
	 PreparedStatement pstmt= null;

	 public List<Message> getAllMessages(){
	List<Message> list = new ArrayList<>();
	try{
	Class.forName("com.mysql.jdbc.Driver");
	conn =(Connection) DriverManager.getConnection(DB_URL, USER, PASS);
	System.out.println("Inside the COnnection");
	stmt = conn.createStatement();
	String sql = "SELECT MessageId, Message, Author, Created_Date from message"; 
	ResultSet rs = stmt.executeQuery(sql);
	while(rs.next()){
	Message newmsg = new Message();
	newmsg.setId(rs.getInt("MessageId"));
	newmsg.setMessage(rs.getString("Message"));
	newmsg.setAuthor(rs.getString("Author"));
	newmsg.setCreated(rs.getDate("Created_Date"));
	list.add(newmsg);
	}
	}catch(Exception e){
		e.printStackTrace();
	}finally{
	try{
	if(stmt!=null)
	stmt.close();
	if(conn!=null)
	conn.close();
	}catch(SQLException se2){
	}
	}
	return list;
	}

	 public Message getMessage(long id){
	//List<Message> list = new ArrayList<>();
	 Message newmsg = new Message();
	try{
		Class.forName("com.mysql.jdbc.Driver");
		conn =(Connection) DriverManager.getConnection(DB_URL, USER, PASS);
	String sql = "SELECT MessageId, Message, Author, Created_Date from message where MessageId = ?";
	pstmt = conn.prepareStatement(sql);
	pstmt.setLong(1, id);
	ResultSet rs = pstmt.executeQuery();
	while(rs.next()){
	newmsg.setId(rs.getInt("MessageId"));
	newmsg.setMessage(rs.getString("Message"));
	newmsg.setAuthor(rs.getString("Author"));
	newmsg.setCreated(rs.getDate("Created_Date"));
	//list.add(newmsg);
	}
	}catch(Exception e){
	e.printStackTrace();
	}finally{
	try{
	if(pstmt!=null)
	pstmt.close();
	if(conn!=null)
	conn.close();
	}catch(SQLException se2){
	}
	}
	return newmsg;
	}
	 public void insertMessage(Message message){
	 try{
		 Class.forName("com.mysql.jdbc.Driver");
			conn =(Connection) DriverManager.getConnection(DB_URL, USER, PASS);
	String sql = "Insert into Message values (?,?,?,?)";
	pstmt = conn.prepareStatement(sql);
	pstmt.setLong(1, message.getId());
	pstmt.setString(2, message.getMessage());
	pstmt.setString(3, message.getAuthor());
	//pstmt.setDate(4, new java.sql.Date(message.getCreated().getTime()));
	pstmt.setDate(4, new java.sql.Date(System.currentTimeMillis()));
	pstmt.executeUpdate();
	//conn.commit();
	 }catch(Exception e){
	 e.printStackTrace();
	 }finally{
		 try{
		 if(pstmt!=null)
		 pstmt.close();
		 if(conn!=null)
		 conn.close();
		 }catch(SQLException se2){
		 se2.printStackTrace();
		 }
		 }
		  }
		 public void updateMessage(Message message){
		  try{
			  Class.forName("com.mysql.jdbc.Driver");
				conn =(Connection) DriverManager.getConnection(DB_URL, USER, PASS);
		 String sql = "update message set Message = ?, Author =? WHERE MessageId = ?";
		 pstmt = conn.prepareStatement(sql);
		 pstmt.setString(1, message.getMessage());
		 pstmt.setString(2, message.getAuthor());
		 pstmt.setLong(3, message.getId());
		 pstmt.executeUpdate();
		  }catch(Exception e){
		  e.printStackTrace();
		  }finally{
		 try{
		 if(pstmt!=null)
		 pstmt.close();
		 if(conn!=null)
		 conn.close();
		 }catch(SQLException se2){
			 se2.printStackTrace();
			 }
			 }
			  }
			  public void deleteMessage(long id){
			  try{
				  Class.forName("com.mysql.jdbc.Driver");
					conn =(Connection) DriverManager.getConnection(DB_URL, USER, PASS);
			 String sql = "DELETE FROM message WHERE MessageId = ?";
			 pstmt = conn.prepareStatement(sql);
			 pstmt.setLong(1, id);
			 pstmt.executeUpdate();
			  }catch(Exception e){
			  e.printStackTrace();
			  }finally{
			 try{
			 if(pstmt!=null)
			 pstmt.close();
			 if(conn!=null)
			 conn.close();
			 }catch(SQLException se2){
			 se2.printStackTrace();
			 }
		 }
	  }
}
			
	

