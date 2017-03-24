package org.anup.javabrains.messenger.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.anup.javabrains.messenger.model.Comment;
import org.anup.javabrains.messenger.model.Message;
public class CommentUpdateObject {
static final String DB_URL = "jdbc:mysql://localhost/messenger";
static final String USER = "root";
static final String PASS = "root";
Connection conn = null;
Statement stmt = null;
PreparedStatement pstmt= null;
public List<Comment> getAllComments(long messageId){
List<Comment> list = new ArrayList<>();
try{
	 Class.forName("com.mysql.jdbc.Driver");
		conn =(Connection) DriverManager.getConnection(DB_URL, USER, PASS);
String sql = "SELECT Comment_Id, Comment, Author, Created_Date from comment where Message_Id = ?";
pstmt = conn.prepareStatement(sql);
pstmt.setLong(1, messageId);
ResultSet rs = pstmt.executeQuery();
while(rs.next()){
Comment newcmt = new Comment();
newcmt.setId(rs.getInt("Comment_Id"));
newcmt.setMessage(rs.getString("Comment"));
newcmt.setAuthor(rs.getString("Author"));
newcmt.setCreated(rs.getDate("Created_Date"));
list.add(newcmt);
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
return list;
}
public Comment getComment(long messageid, long commentid){
//List<Message> list = new ArrayList<>();
Comment newcmt = new Comment();
try{
	 Class.forName("com.mysql.jdbc.Driver");
		conn =(Connection) DriverManager.getConnection(DB_URL, USER, PASS);
String sql = "SELECT Comment_Id, Comment, Author, Created_Date from comment where Message_Id = ? and Comment_Id = ?";
pstmt = conn.prepareStatement(sql);
pstmt.setLong(1, messageid);
pstmt.setLong(2, commentid);
ResultSet rs = pstmt.executeQuery();
while(rs.next()){
newcmt.setId(rs.getInt("Comment_Id"));
newcmt.setMessage(rs.getString("Comment"));
newcmt.setAuthor(rs.getString("Author"));
newcmt.setCreated(rs.getDate("Created_Date"));
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
return newcmt;
}
public void insertComment(long messageId, Comment comment){
try{
	 Class.forName("com.mysql.jdbc.Driver");
		conn =(Connection) DriverManager.getConnection(DB_URL, USER, PASS);
String sql = "Insert into Comment values (?,?,?,?,?)";
pstmt = conn.prepareStatement(sql);
pstmt.setLong(1, comment.getId());
pstmt.setLong(2, messageId);
pstmt.setString(3, comment.getMessage());
pstmt.setString(4, comment.getAuthor());
//pstmt.setDate(4, new java.sql.Date(message.getCreated().getTime()));
pstmt.setDate(5, new java.sql.Date(System.currentTimeMillis()));
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
public void updateComment(Comment comment){
try{
	 Class.forName("com.mysql.jdbc.Driver");
		conn =(Connection) DriverManager.getConnection(DB_URL, USER, PASS);
String sql = "update message set Message = ?, Author =? WHERE MessageId = ?";
pstmt = conn.prepareStatement(sql);
pstmt.setString(1, comment.getMessage());
pstmt.setString(2, comment.getAuthor());
pstmt.setLong(3, comment.getId());
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
public void deleteComment(long id){
try{
	 Class.forName("com.mysql.jdbc.Driver");
		conn =(Connection) DriverManager.getConnection(DB_URL, USER, PASS);
String sql = "DELETE FROM COMMENT WHERE Comment_Id = ?";
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