import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

@SuppressWarnings("serial")
class FractalPanel extends JPanel implements MotifChangeListener, BaseCurveChangeListener, ActionListener, ItemListener {
  
  private Line fractals[][];
  private Line base_curve[];
  private Motif motif;

  private LineCanvas fractal_canvas;
  
  private JLabel n_fractals_label;
  private JButton center_now_button;
  private JButton deeper_button;
  private JButton shallower_button;
  private JCheckBox auto_center_check_box;
  
  private static int MAX_LINES = 200000;
  private int cur_depth;

  public FractalPanel(Line[] base_curve, Motif motif) {    
    fractal_canvas = new LineCanvas();
    
    n_fractals_label = new JLabel("#fractals =");
    
    center_now_button = new JButton("Center");
    center_now_button.addActionListener(this);
    
    auto_center_check_box  = new JCheckBox("Auto Center"); 
    auto_center_check_box.addItemListener(this);
    auto_center_check_box.setSelected(false);
    
    deeper_button = new JButton("+++");
    deeper_button.addActionListener(this);
    
    shallower_button = new JButton("---");
    shallower_button.addActionListener(this);
    
    this.base_curve = base_curve;
    this.motif = motif;
    this.cur_depth = 2;
    reset();
    setAutoCenter(true);


    // Do layout
    JPanel centering_panel = new JPanel(new FlowLayout());
    //centering_panel.add(new JLabel("Center:"));
    centering_panel.add(center_now_button);
    centering_panel.add(auto_center_check_box);
     
    JPanel depth_panel = new JPanel(new FlowLayout());
    depth_panel.add(deeper_button);
    depth_panel.add(shallower_button);
    depth_panel.add(n_fractals_label);
    
    JPanel component_panel = new JPanel(new GridLayout(2,1));
    component_panel.add(depth_panel);
    component_panel.add(centering_panel);
    
    setLayout(new BorderLayout());
    add("South",component_panel);
    add("Center",fractal_canvas);
  }
  
  public void setMotif(Motif motif) {
    this.motif = motif;
    reset();
  }
  
  public void setBaseCurve(Line[] base_curve) {
    this.base_curve = base_curve;
    reset();
  }

  private void reset() {
    // Compute max_depth
    int max_depth = 0;
    int n_lines = 1;
    int ply = motif.ply();
    while ( (base_curve.length*n_lines*ply)<MAX_LINES) {
      max_depth++;
      n_lines *= ply;
      /*
      System.out.println("------------------");
      System.out.print("  max_depth: "+max_depth);
      System.out.print("  n_lines: "+n_lines);
      System.out.println("  ply: "+ply);
      */

    }

    fractals = new Line[max_depth][];
    fractals[0] = this.base_curve;
    for (int i=1; i<fractals.length; i++) {
      fractals[i] = new Line[0];
    }
    
    if (cur_depth>=fractals.length) {
      cur_depth = fractals.length-1; 
    }
    
    setDepth(cur_depth);    
    //setDepth(0);
  }

  private void computeLines(int depth) {
    if (fractals[depth].length == 0) { 
      computeLines(depth-1);
      fractals[depth] = motif.apply(fractals[depth-1]);
    }
    
  }
  
  private void setDepth(int depth) {
    
    
    shallower_button.setEnabled( depth>0 );
    deeper_button.setEnabled( depth<fractals.length );

    if (depth<0) depth=0;
    if (depth> (fractals.length-1) ) depth=fractals.length-1;
    
    cur_depth = depth;
    computeLines(cur_depth);


    
    fractal_canvas.lines = fractals[cur_depth];
    n_fractals_label.setText("#lines = "+fractals[cur_depth].length); 
    fractal_canvas.repaint();

  }
  
  
  public void motifChanged(Motif motif) {
    setMotif(motif);
  }

  public void baseCurveChanged(int base_curve_index, boolean bidirectional) {
    setBaseCurve(BaseCurve.getBaseCurve(base_curve_index, bidirectional));
  }

  public void actionPerformed(ActionEvent action_event) {
    if (action_event.getSource() == shallower_button) {
      setDepth(cur_depth-1);
      
    } else if (action_event.getSource() == deeper_button) {
      setDepth(cur_depth+1);
      
    } else if (action_event.getSource() == center_now_button) {
      fractal_canvas.centerLines();
      fractal_canvas.repaint();
      
    }
  }

  private void setAutoCenter(boolean auto_center) {
    fractal_canvas.setAutoCenter(auto_center);
    center_now_button.setEnabled(!auto_center);
    auto_center_check_box.setSelected(auto_center);
  }
  
  public void itemStateChanged(ItemEvent item_event) {
    Object source = item_event.getItemSelectable();
    int state = item_event.getStateChange();
    if (source == auto_center_check_box) {
      setAutoCenter(state==ItemEvent.SELECTED);
    }
  }

}

