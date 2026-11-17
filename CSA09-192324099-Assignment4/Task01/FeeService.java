public class FeeService {
 private final FeeRepository repo;
 public FeeService(FeeRepository r){repo=r;}
 public String pay(String reg,String text)throws Exception{
  if(reg==null||reg.trim().isEmpty())throw new IllegalArgumentException("Registration number is required.");
  double a;try{a=Double.parseDouble(text.trim());}catch(Exception e){throw new IllegalArgumentException("Enter a valid amount.");}
  if(!Double.isFinite(a)||a<=0)throw new IllegalArgumentException("Amount must be greater than zero.");
  repo.savePayment(reg.trim(),a);return String.format("Payment %.2f recorded.",a);
 }
 public String receipt(String reg){if(reg.trim().isEmpty())throw new IllegalArgumentException("Enter registration number.");return repo.receipt(reg);}
}
