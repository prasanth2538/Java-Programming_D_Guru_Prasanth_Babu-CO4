import java.sql.*;
public class FeeRepository {
 private final String url=System.getenv().getOrDefault("DB_URL","jdbc:mysql://localhost:3306/campus_events");
 private final String user=System.getenv().getOrDefault("DB_USER","root");
 private final String password=System.getenv().getOrDefault("DB_PASSWORD","");
 public void savePayment(String reg,double amount)throws SQLException{
  try(Connection c=DriverManager.getConnection(url,user,password);
      PreparedStatement p=c.prepareStatement("INSERT INTO fee_payments(reg_no,amount) VALUES(?,?)")){
   p.setString(1,reg);p.setDouble(2,amount);p.executeUpdate();
  }
 }
 public String receipt(String reg){return "Receipt request for "+reg+" (connect this to your institution receipt query).";}
}
