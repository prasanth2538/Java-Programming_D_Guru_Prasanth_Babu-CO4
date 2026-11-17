import java.awt.*;import java.util.List;import javax.swing.*;import javax.swing.table.DefaultTableModel;
public class EventRegistrationApp extends JFrame{
 private final RegistrationRepository repo=new RegistrationRepository();private final RegistrationService service=new RegistrationService(repo);
 private final JTextField sid=new JTextField(12),name=new JTextField(16);
 private final JComboBox<String> event=new JComboBox<>(new String[]{"Tech Fest","Cultural Night","Sports Meet","Workshop"});
 private final JComboBox<String> filter=new JComboBox<>(new String[]{"All events","Tech Fest","Cultural Night","Sports Meet","Workshop"});
 private final JLabel status=new JLabel("Ready.");private final DefaultTableModel model=new DefaultTableModel(new String[]{"ID","Student ID","Name","Event","Registered At"},0){public boolean isCellEditable(int r,int c){return false;}};
 private final JTable table=new JTable(model);private final int pageSize=20;private int page=0;
 public EventRegistrationApp(){super("Campus Event Registration");setLayout(new BorderLayout());
  JMenuBar mb=new JMenuBar();JMenu file=new JMenu("File"),help=new JMenu("Help");JMenuItem refresh=new JMenuItem("Refresh"),exit=new JMenuItem("Exit"),about=new JMenuItem("About");file.add(refresh);file.add(exit);help.add(about);mb.add(file);mb.add(help);setJMenuBar(mb);
  refresh.addActionListener(e->load());exit.addActionListener(e->dispose());about.addActionListener(e->JOptionPane.showMessageDialog(this,"Campus Event Registration – CO4"));
  JTabbedPane tabs=new JTabbedPane();JPanel form=new JPanel(new GridBagLayout());GridBagConstraints c=new GridBagConstraints();c.insets=new Insets(6,6,6,6);c.anchor=GridBagConstraints.WEST;
  row(form,c,0,"Student ID:",sid);row(form,c,1,"Student name:",name);row(form,c,2,"Event:",event);JButton register=new JButton("Register");c.gridx=1;c.gridy=3;form.add(register,c);register.addActionListener(e->register());
  tabs.addTab("Register",form);
  JPanel view=new JPanel(new BorderLayout());JPanel top=new JPanel();JButton apply=new JButton("Apply Filter"),prev=new JButton("Previous"),next=new JButton("Next");top.add(new JLabel("Event:"));top.add(filter);top.add(apply);view.add(top,BorderLayout.NORTH);view.add(new JScrollPane(table),BorderLayout.CENTER);JPanel nav=new JPanel();nav.add(prev);nav.add(next);view.add(nav,BorderLayout.SOUTH);
  apply.addActionListener(e->{page=0;load();});prev.addActionListener(e->{if(page>0){page--;load();}});next.addActionListener(e->{page++;load();});tabs.addTab("View registrations",view);
  add(tabs,BorderLayout.CENTER);add(status,BorderLayout.SOUTH);setDefaultCloseOperation(EXIT_ON_CLOSE);setSize(800,500);setLocationRelativeTo(null);load();
 }
 private void row(JPanel p,GridBagConstraints c,int y,String label,Component field){c.gridx=0;c.gridy=y;p.add(new JLabel(label),c);c.gridx=1;p.add(field,c);}
 private void register(){String i=sid.getText(),n=name.getText(),ev=(String)event.getSelectedItem();status.setText("Saving...");
  new SwingWorker<Void,Void>(){protected Void doInBackground()throws Exception{service.register(i,n,ev);return null;}
   protected void done(){try{get();status.setText("Registration saved.");JOptionPane.showMessageDialog(EventRegistrationApp.this,"Registration successful.");load();}
    catch(Exception x){Throwable t=x.getCause()==null?x:x.getCause();status.setText("Error: "+t.getMessage());JOptionPane.showMessageDialog(EventRegistrationApp.this,t.getMessage(),"Registration error",JOptionPane.ERROR_MESSAGE);}}
  }.execute();
 }
 private void load(){String ev=(String)filter.getSelectedItem();status.setText("Loading...");
  new SwingWorker<List<Registration>,Void>(){protected List<Registration> doInBackground()throws Exception{return repo.page(ev,pageSize,page*pageSize);}
   protected void done(){try{List<Registration> rs=get();model.setRowCount(0);for(Registration r:rs)model.addRow(new Object[]{r.getId(),r.getStudentId(),r.getStudentName(),r.getEventName(),r.getRegisteredAt()});status.setText("Loaded "+rs.size()+" row(s), page "+(page+1));}
    catch(Exception x){Throwable t=x.getCause()==null?x:x.getCause();status.setText("Database error: "+t.getMessage());}}
  }.execute();
 }
 public static void main(String[]a){SwingUtilities.invokeLater(()->new EventRegistrationApp().setVisible(true));}
}
