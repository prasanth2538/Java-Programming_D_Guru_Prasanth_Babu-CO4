import java.awt.*;import java.awt.event.*;import javax.swing.*;
public class ConverterAnonymous extends JFrame{
 JTextField amount=new JTextField(10);JComboBox<String> target=new JComboBox<>(new String[]{"USD","EUR"});JLabel result=new JLabel(" ");
 public ConverterAnonymous(){super("Anonymous Listener");setLayout(new FlowLayout());JButton b=new JButton("Convert");add(new JLabel("INR"));add(amount);add(target);add(b);add(result);
 ActionListener h=new ActionListener(){public void actionPerformed(ActionEvent e){try{double v=Double.parseDouble(amount.getText().trim());String c=(String)target.getSelectedItem();String s=String.format("%.2f %s",v*(c.equals("USD")?.012:.011),c);result.setText(s);JOptionPane.showMessageDialog(ConverterAnonymous.this,s);}catch(Exception x){result.setText("Invalid amount");JOptionPane.showMessageDialog(ConverterAnonymous.this,"Invalid amount");}}};
 b.addActionListener(h);amount.addActionListener(h);addWindowListener(new WindowAdapter(){@Override public void windowClosing(WindowEvent e){dispose();}});setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);pack();}
 public static void main(String[]a){SwingUtilities.invokeLater(()->new ConverterAnonymous().setVisible(true));}
}
