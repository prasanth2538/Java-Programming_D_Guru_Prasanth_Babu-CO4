import java.sql.SQLException;
public class RegistrationService{
 private final RegistrationRepository repo;public RegistrationService(RegistrationRepository r){repo=r;}
 public void register(String id,String name,String event)throws Exception{
  if(blank(id)||blank(name)||blank(event))throw new IllegalArgumentException("Student ID, name and event are required.");
  try{repo.insert(id.trim(),name.trim(),event.trim());}catch(SQLException e){if("23000".equals(e.getSQLState()))throw new IllegalArgumentException("Student is already registered for this event.");throw e;}
 }
 private boolean blank(String s){return s==null||s.trim().isEmpty();}
}
