import java.awt.*;import java.awt.event.*;import javax.swing.*;
public class Converter extends JFrame{
 private final JTextField amount=new JTextField(10);private final JComboBox<String> target=new JComboBox<>(new String[]{"USD","EUR"});private final JLabel result=new JLabel(" ");
 private static double rate(String c){return c.equals("USD")?.012:.011;}
 public Converter(){super("Currency Converter – Lambda");setLayout(new FlowLayout());JButton button=new JButton("Convert");
  add(new JLabel("INR"));add(amount);add(target);add(button);add(result);
  // TODO 1 completed: one lambda listener shared by Convert and Enter.
  ActionListener action=e->convert();button.addActionListener(action);amount.addActionListener(action);
  addWindowListener(new WindowAdapter(){@Override public void windowClosing(WindowEvent e){dispose();}});
  setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);pack();setLocationRelativeTo(null);
 }
 private void convert(){
  // TODO 2 completed: validate, format two decimals, show dialog.
  try{double v=Double.parseDouble(amount.getText().trim());if(!Double.isFinite(v)||v<0)throw new NumberFormatException();
   String c=(String)target.getSelectedItem();String s=String.format("%.2f %s",v*rate(c),c);result.setText(s);
   JOptionPane.showMessageDialog(this,s,"Conversion result",JOptionPane.INFORMATION_MESSAGE);
  }catch(NumberFormatException ex){result.setText("Invalid amount");JOptionPane.showMessageDialog(this,"Invalid amount","Input error",JOptionPane.ERROR_MESSAGE);}
 }
 public static void main(String[]a){SwingUtilities.invokeLater(()->new Converter().setVisible(true));}
}
