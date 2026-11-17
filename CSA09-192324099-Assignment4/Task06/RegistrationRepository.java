import java.sql.*;import java.util.*;
public class RegistrationRepository{
 private final String url=System.getenv().getOrDefault("DB_URL","jdbc:mysql://localhost:3306/campus_events"),user=System.getenv().getOrDefault("DB_USER","root"),pwd=System.getenv().getOrDefault("DB_PASSWORD","");
 private Connection connect()throws SQLException{return DriverManager.getConnection(url,user,pwd);}
 public void insert(String id,String name,String event)throws SQLException{
  try(Connection c=connect();PreparedStatement p=c.prepareStatement("INSERT INTO registrations(student_id,student_name,event_name) VALUES(?,?,?)")){
   p.setString(1,id);p.setString(2,name);p.setString(3,event);p.executeUpdate();
  }
 }
 public java.util.List<Registration> page(String event,int limit,int offset)throws SQLException{
  java.util.List<Registration> rows=new ArrayList<>();boolean all=event==null||event.equals("All events");
  String sql=all?"SELECT * FROM registrations ORDER BY registration_id DESC LIMIT ? OFFSET ?":"SELECT * FROM registrations WHERE event_name=? ORDER BY registration_id DESC LIMIT ? OFFSET ?";
  try(Connection c=connect();PreparedStatement p=c.prepareStatement(sql)){int i=1;if(!all)p.setString(i++,event);p.setInt(i++,limit);p.setInt(i,offset);
   try(ResultSet r=p.executeQuery()){while(r.next()){Timestamp t=r.getTimestamp("registered_at");rows.add(new Registration(r.getLong("registration_id"),r.getString("student_id"),r.getString("student_name"),r.getString("event_name"),t.toLocalDateTime()));}}
  }return rows;
 }
}
