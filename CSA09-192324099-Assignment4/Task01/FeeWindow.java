import java.awt.*;import java.awt.event.*;import javax.swing.*;
public class FeeWindow extends JFrame{
 private final JTextField reg=new JTextField(14),amount=new JTextField(14);
 private final JLabel status=new JLabel("Enter payment details.");
 private final FeeService service=new FeeService(new FeeRepository());
 public FeeWindow(){
  super("Student Fee Payment");setLayout(new BorderLayout(8,8));
  JPanel form=new JPanel(new GridLayout(2,2,6,6));
  form.add(new JLabel("Registration number:"));form.add(reg);form.add(new JLabel("Amount:"));form.add(amount);
  JButton pay=new JButton("Pay"),clear=new JButton("Clear"),receipt=new JButton("Receipt");
  JPanel buttons=new JPanel();buttons.add(pay);buttons.add(clear);buttons.add(receipt);
  add(buttons,BorderLayout.NORTH);add(form,BorderLayout.CENTER);add(status,BorderLayout.SOUTH);
  pay.addActionListener(e->{try{status.setText(service.pay(reg.getText(),amount.getText()));}catch(Exception x){status.setText("Error: "+x.getMessage());}});
  clear.addActionListener(e->{reg.setText("");amount.setText("");status.setText("Cleared.");});
  receipt.addActionListener(e->{try{status.setText(service.receipt(reg.getText()));}catch(Exception x){status.setText(x.getMessage());}});
  addWindowListener(new WindowAdapter(){@Override public void windowClosing(WindowEvent e){dispose();}});
  setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);setSize(420,180);setLocationRelativeTo(null);
 }
 public static void main(String[]a){EventQueue.invokeLater(()->new FeeWindow().setVisible(true));}
}
