import java.awt.*;import javax.swing.*;
public class LibrarySearch extends JFrame{
 private final JTextField query=new JTextField(20);private final JComboBox<String> category=new JComboBox<>(new String[]{"All","Books","Journals","Theses"});
 private final DefaultListModel<String> model=new DefaultListModel<>();private final JLabel status=new JLabel("Ready.");
 public LibrarySearch(){
  super("Campus Library Search");setLayout(new BorderLayout(8,8));
  JPanel search=new JPanel(new FlowLayout(FlowLayout.LEFT));search.add(new JLabel("Search:"));search.add(query);search.add(new JLabel("Category:"));search.add(category);
  JButton go=new JButton("Search");search.add(go);add(search,BorderLayout.NORTH);
  model.addElement("Java Programming – Available");model.addElement("Database Systems – 3 copies");model.addElement("Event-Driven GUI Design – Reference");model.addElement("Data Structures – 2 copies");
  JList<String> results=new JList<>(model);add(new JScrollPane(results),BorderLayout.CENTER);
  JPanel controls=new JPanel();JButton prev=new JButton("Prev"),next=new JButton("Next"),reserve=new JButton("Reserve");
  controls.add(prev);controls.add(next);controls.add(reserve);JPanel south=new JPanel(new BorderLayout());south.add(controls,BorderLayout.CENTER);south.add(status,BorderLayout.SOUTH);add(south,BorderLayout.SOUTH);
  go.addActionListener(e->status.setText(query.getText().trim().isEmpty()?"Enter a search term.":"Showing: "+query.getText().trim()));
  reserve.addActionListener(e->status.setText(results.getSelectedValue()==null?"Select a result.":"Reservation request: "+results.getSelectedValue()));
  prev.addActionListener(e->status.setText("Previous page (demo)."));next.addActionListener(e->status.setText("Next page (demo)."));
  setDefaultCloseOperation(EXIT_ON_CLOSE);setSize(620,420);setLocationRelativeTo(null);
 }
 public static void main(String[]a){SwingUtilities.invokeLater(()->new LibrarySearch().setVisible(true));}
}
