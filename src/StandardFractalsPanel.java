import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

@SuppressWarnings("serial")
class StandardFractalsPanel extends JPanel implements ActionListener {
  
  private JComboBox<String> list;

  private Vector<MotifChangeListener> motif_changed_listeners = new Vector<>();
  private Vector<BaseCurveChangeListener> base_curve_changed_listeners = new Vector<>();
  
  
  public StandardFractalsPanel() {
    list = new JComboBox<>(StandardFractal.getNames());
    list.addActionListener(this);
    add(list);
  }

  public void actionPerformed(ActionEvent action_event) {
    if (action_event.getSource() == list) {
      int current_index = list.getSelectedIndex();
      StandardFractal fractal = StandardFractal.getStandardFractal(current_index);
      notifyListeners(fractal.getMotif());
      notifyListeners(fractal.getBaseCurveIndex(),fractal.getBidirectional());
    }
  }
  
  public void addMotifChangeListener(MotifChangeListener rcl) {
    motif_changed_listeners.add(rcl);
  }
    
  public void removeMotifChangeListener(MotifChangeListener rcl) {
    motif_changed_listeners.remove(rcl);
  }
    
  private void notifyListeners(Motif motif) {
    for (int i=0; i<motif_changed_listeners.size(); i++)
      motif_changed_listeners.get(i).motifChanged(motif);
  }

  public void addBaseCurveChangeListener(BaseCurveChangeListener bcl) {
    base_curve_changed_listeners.add(bcl);
  }
    
  public void removeBaseCurveChangeListener(BaseCurveChangeListener bcl) {
    base_curve_changed_listeners.remove(bcl);
  }
    
  private void notifyListeners(int base_curve_index, boolean bidirectional) {
    for (int i=0; i<base_curve_changed_listeners.size(); i++) {
      base_curve_changed_listeners.get(i).baseCurveChanged(base_curve_index,bidirectional); 
    }
  }
}
