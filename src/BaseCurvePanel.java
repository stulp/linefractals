import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;


@SuppressWarnings("serial")
class BaseCurvePanel extends JPanel implements BaseCurveChangeListener, ActionListener, ItemListener {
  
  private int current_index;
  

  //private JButton line_button = new JButton(new ImageIcon("buttonicons/line.png")); 
  private JButton line_button = new JButton("Line"); 
  private JComboBox<String> list;
  private JCheckBox bidirect_check_box;
  
  private boolean bidirectional;

  private LineCanvas base_curve_canvas;

  public BaseCurvePanel() {
    String names[] = BaseCurve.getNames(); 
    list = new JComboBox<>(names);
    list.addActionListener(this);
    
    bidirect_check_box  = new JCheckBox("Bidirectional"); 
    bidirect_check_box.addItemListener(this);
    bidirectional = false;
    bidirect_check_box.setSelected(bidirectional);
    
    JPanel widget_panel = new JPanel();
    widget_panel.setBackground(Color.WHITE);
    widget_panel.add(list);
    widget_panel.add(bidirect_check_box);
    
    //line_button.setBorderPainted(false);
    line_button.setBackground(Color.WHITE);
    line_button.setSize(new Dimension(32,32));
    widget_panel.add(line_button);

    current_index = BaseCurve.LINE;
    
    base_curve_canvas = new LineCanvas();
    base_curve_canvas.lines = BaseCurve.getBaseCurve(current_index);
    base_curve_canvas.setAutoCenter(true);
    base_curve_canvas.repaint();

    setLayout(new BorderLayout());
    add("South",widget_panel);
    add("Center",base_curve_canvas);
    
  }
  
  public Line[] getBaseCurve() {
    return BaseCurve.getBaseCurve(current_index,bidirectional);
  }
  
  private void setBaseCurve(int base_curve_index, boolean bidirectional) {
    base_curve_canvas.lines = BaseCurve.getBaseCurve(base_curve_index,bidirectional);
    //base_curve_canvas.centerLines();
    base_curve_canvas.repaint();
    
    list.setSelectedIndex(base_curve_index);
    bidirect_check_box.setSelected(bidirectional);
  }

  public void baseCurveChanged(int base_curve_index, boolean bidirectional) {
    setBaseCurve(base_curve_index,bidirectional);     
  }

  private Vector<BaseCurveChangeListener> base_curve_changed_listeners = new Vector<>();
  
  public void addBaseCurveChangeListener(BaseCurveChangeListener bcl) {
    base_curve_changed_listeners.add(bcl);
  }
    
  public void removeBaseCurveChangeListener(BaseCurveChangeListener bcl) {
    base_curve_changed_listeners.remove(bcl);
  }
    
  private void notifyListeners() {
    for (int i=0; i<base_curve_changed_listeners.size(); i++)
      base_curve_changed_listeners.get(i).baseCurveChanged(current_index,bidirectional);
    
  }

  
  public void actionPerformed(ActionEvent action_event) {
    if (action_event.getSource() == list) {
      current_index = list.getSelectedIndex();
      setBaseCurve(current_index,bidirectional);
    }
    notifyListeners();
  }
  
  public void itemStateChanged(ItemEvent item_event) {
    Object source = item_event.getItemSelectable();
    int state = item_event.getStateChange();
    if (source == bidirect_check_box) {
      bidirectional = (state==ItemEvent.SELECTED);
    }
    notifyListeners();
  }
  
}

