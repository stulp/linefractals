import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

@SuppressWarnings("serial")
public class FractalApplication extends JFrame {
  
  private FractalPanel fractal_panel; 
  private MotifPanel motif_panel; 
  private BaseCurvePanel base_curve_panel; 
  private StandardFractalsPanel standard_fractals_panel;
  
  public FractalApplication() {

    // Make the components
    motif_panel = new MotifPanel();
    base_curve_panel = new BaseCurvePanel();
    standard_fractals_panel = new StandardFractalsPanel();
    
    Motif motif = motif_panel.getMotif();
    Line[] base_curve = base_curve_panel.getBaseCurve();
    fractal_panel = new FractalPanel(base_curve,motif);
    
    // base curve and motif
    
    
    // Make them look nice
    fractal_panel.setBorder(BorderFactory.createTitledBorder("Fractal"));
    motif_panel.setBorder(BorderFactory.createTitledBorder("Motif"));
    base_curve_panel.setBorder(BorderFactory.createTitledBorder("Base Curve"));
    standard_fractals_panel.setBorder(BorderFactory.createTitledBorder("Well Known Fractals"));

    // Add listeners
    motif_panel.addMotifChangeListener(fractal_panel);
    base_curve_panel.addBaseCurveChangeListener(fractal_panel);

    standard_fractals_panel.addMotifChangeListener(fractal_panel);
    standard_fractals_panel.addBaseCurveChangeListener(fractal_panel);
    standard_fractals_panel.addMotifChangeListener(motif_panel);
    standard_fractals_panel.addBaseCurveChangeListener(base_curve_panel);

    // Do layout
    JPanel design_panel = new JPanel(new GridLayout(2,1));
    design_panel.add(base_curve_panel);
    design_panel.add(motif_panel);

    JPanel base_motif_panel = new JPanel(new BorderLayout());
    base_motif_panel.add("North",standard_fractals_panel);
    base_motif_panel.add("Center",design_panel);

    getContentPane().setLayout(new BorderLayout());    
    getContentPane().add("West",base_motif_panel);
    getContentPane().add("Center",fractal_panel);
  }
  
  public static void main(String[] args) {
    FractalApplication frame = new FractalApplication();
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.setPreferredSize(new Dimension(1200, 600));
    frame.pack();
    frame.setVisible(true);		
	}
}
