import java.time.LocalDateTime;
public class Registration{
 private final long id;private final String studentId,studentName,eventName;private final LocalDateTime registeredAt;
 public Registration(long i,String s,String n,String e,LocalDateTime t){id=i;studentId=s;studentName=n;eventName=e;registeredAt=t;}
 public long getId(){return id;}public String getStudentId(){return studentId;}public String getStudentName(){return studentName;}public String getEventName(){return eventName;}public LocalDateTime getRegisteredAt(){return registeredAt;}
}
