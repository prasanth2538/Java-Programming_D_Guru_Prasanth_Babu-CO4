import java.awt.*;import java.awt.event.*;import java.util.*;import javax.swing.*;
public class AppointmentDesk extends JFrame{
 private final JTextField patient=new JTextField(14);
 private final JComboBox<String> dept=new JComboBox<>(new String[]{"Cardiology","Dermatology","Orthopedics"});
 private final JComboBox<String> doctor=new JComboBox<>();
 private final JComboBox<String> slot=new JComboBox<>(new String[]{"09:00","10:00","11:30","14:00"});
 private final JLabel status=new JLabel("Complete the form.");private final JButton book=new JButton("Book");
 private boolean booking=false;private final Map<String,java.util.List<String>> doctors=new LinkedHashMap<>();
 public AppointmentDesk(){
  super("Hospital Outpatient Appointment Desk");
  doctors.put("Cardiology",Arrays.asList("Dr. Kumar","Dr. Meena"));
  doctors.put("Dermatology",Arrays.asList("Dr. Ravi","Dr. Asha"));
  doctors.put("Orthopedics",Arrays.asList("Dr. John","Dr. Divya"));
  JPanel form=new JPanel(new GridLayout(4,2,8,8));
  form.add(new JLabel("Patient ID:"));form.add(patient);form.add(new JLabel("Department:"));form.add(dept);
  form.add(new JLabel("Doctor:"));form.add(doctor);form.add(new JLabel("Time slot:"));form.add(slot);
  JPanel bottom=new JPanel();bottom.add(book);bottom.add(status);add(form);add(bottom,BorderLayout.SOUTH);
  dept.addItemListener(e->{if(e.getStateChange()==ItemEvent.SELECTED)refreshDoctors();});
  book.addActionListener(e->bookAppointment());refreshDoctors();
  setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
  addWindowListener(new WindowAdapter(){@Override public void windowClosing(WindowEvent e){
   if(booking&&JOptionPane.showConfirmDialog(AppointmentDesk.this,"Booking in progress. Close?","Confirm",JOptionPane.YES_NO_OPTION)!=JOptionPane.YES_OPTION)return;
   dispose();}});
  setSize(520,260);setLocationRelativeTo(null);
 }
 private void refreshDoctors(){doctor.removeAllItems();java.util.List<String> list=doctors.get(dept.getSelectedItem());if(list!=null)for(String d:list)doctor.addItem(d);}
 private void bookAppointment(){
  if(patient.getText().trim().isEmpty()||doctor.getSelectedItem()==null||slot.getSelectedItem()==null){status.setText("Enter patient ID and select options.");return;}
  booking=true;book.setEnabled(false);status.setText("Booking...");
  javax.swing.Timer t=new javax.swing.Timer(700,e->{status.setText("Appointment confirmed for "+patient.getText().trim());booking=false;book.setEnabled(true);});
  t.setRepeats(false);t.start();
 }
 public static void main(String[]a){SwingUtilities.invokeLater(()->new AppointmentDesk().setVisible(true));}
}
