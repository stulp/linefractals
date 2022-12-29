import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
class MotifPanel extends JPanel implements MotifChangeListener, ActionListener {

  private Line init_line;
  private MotifCanvas motif_canvas;
  
  private JButton mirror_button;
  private JButton center_button;
  private JButton default_button;
  
  public MotifPanel() {
    motif_canvas = new MotifCanvas(new Motif());
    //motif_canvas.setMotif(new Motif());
    
    mirror_button = new JButton("Mirror");
    mirror_button.addActionListener(this);

    center_button = new JButton("Center");
    center_button.addActionListener(this);

    default_button = new JButton("Default");
    default_button.addActionListener(this);

    JPanel button_panel = new JPanel();
    button_panel.add(mirror_button);
    button_panel.add(center_button);
    button_panel.add(default_button);
    
    setLayout(new BorderLayout());
    add("Center",motif_canvas);
    add("South",button_panel);
    
  }
  
  public Motif getMotif() {
    return motif_canvas.getMotif();
  }
  
  public void setMotif(Motif motif) {
    motif_canvas.setMotif(motif);
    motif_canvas.centerLines();
  }

  public void motifChanged(Motif motif) {
    setMotif(motif); 
  }
  
  public void actionPerformed(ActionEvent action_event) {
    if (action_event.getSource() == mirror_button) {
      Motif motif = getMotif();
      motif.mirror();
      setMotif(motif);
    } else if (action_event.getSource() == center_button) {
      motif_canvas.centerLines();
    } else if (action_event.getSource() == default_button) {
      setMotif(new Motif());
    }
  }
  
  public void addMotifChangeListener(MotifChangeListener listener) {
     motif_canvas.addMotifChangeListener(listener);
  }

}

