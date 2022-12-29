import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

@SuppressWarnings("serial")
class MotifCanvas extends LineCanvas {

  Line init_line;
  
  public MotifCanvas(Motif motif) {
    setEnabled(true);
    setMotif(motif);
    repaint();
  }
  
  private String toString(double[] xs, double[] ys) {
    String str = new String();
    for (int p=0; p<xs.length; p++) {
      str += "("+xs[p]+","+ys[p]+"), ";
    }
    return str;
  }

  public Motif getMotif() {
    int np = lines.length-1;
    double xs[] = new double[np];
    double ys[] = new double[np];
    
    double x0 = lines[0].x1;
    double y0 = lines[0].y1;
    double x_last = lines[np].x2;
    double y_last = lines[np].y2;

    for (int p=0; p<np; p++) {
      xs[p] = lines[p].x2;
      ys[p] = lines[p].y2;      
    }
    
    // Transform it so that xs[0],ys[0] = (0,0)
    for (int p=0; p<np; p++) {
      xs[p] -= x0;
      ys[p] -= y0;
    }

    // Rotate it
    /*
    double angle = Math.atan2(xs[np-1]-xs[0],ys[np-1]-ys[0]);
    System.out.println("angle: "+angle);
    for (int p=0; p<np; p++) {
        xs[p] -= points[0].x;
      ys[p] -= points[0].y;
    }
    */

    // Scale it from 0-1
    double dx = x_last-x0;
    double dy = y_last-y0;
    double length = Math.sqrt( dx*dx + dy*dy );
    for (int p=0; p<np; p++) {
      xs[p] /= length;
      ys[p] /= length;
    }
    
    double distances[][] = {ys, xs};
    return new Motif(distances);
    
  }
  
  public void setMotif(Motif motif) {
    init_line = new Line(0.0,0.0,0.0,1.0);
    lines = motif.apply(init_line);
    repaint();
    //centerLines();
    //mirror();
    notifyListeners(getMotif());
  }
  
  private Vector<MotifChangeListener> motif_changed_listeners = new Vector<>();
  
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

  public void mouseReleased(MouseEvent mouse_event) {
    super.mouseReleased(mouse_event);
    notifyListeners(getMotif());
  }
}
