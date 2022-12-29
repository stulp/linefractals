import java.awt.Point;

class Motif {
  private double[][] distances = new double[2][];
  
  public Motif(double distances[][]) {
    this.distances = distances;
  }
  
  public Motif() {
    distances = new double[2][3];
    
    distances[0][0] = 0.33333;
    distances[1][0] = 0.0;

    distances[0][1] = 0.5;
    distances[1][1] = 0.5;

    distances[0][2] = 0.66666;
    distances[1][2] = 0.0;

  }
  
  void mirror() {
    for (int i=0; i<distances[0].length; i++) {
      distances[1][i] = -distances[1][i]; 
    }
  }
  
  int ply() {
    return distances[0].length+1;
  }
  
  Line[] apply(Line[] lines) {
    Line[] new_lines = new Line[(distances[0].length+1)*(lines.length)];
    int count = 0;
    for (int i=0; i<lines.length; i++) {
      Line[] cur_lines = apply(lines[i]); 
      for (int j=0; j<cur_lines.length; j++) {
        new_lines[count++] = cur_lines[j];
      }
    }
    return new_lines;
  } 
    
  Line[] apply(Line l) {

    // Length of line
    double length = Math.sqrt( (l.x1-l.x2)*(l.x1-l.x2) + (l.y1-l.y2)*(l.y1-l.y2) );
    // Angle of line
    double angle = Math.atan2( (l.x2-l.x1), (l.y2-l.y1) );

    int np = distances[0].length+2;
    double xs[] = new double[np];
    double ys[] = new double[np];
    
    xs[0] = l.x1;
    ys[0] = l.y1;
    
    for (int i=0; i<np-2; i++) {
      // Compute point on line
      double x = l.x1+(l.x2-l.x1)*distances[0][i];
      double y = l.y1+(l.y2-l.y1)*distances[0][i];
      
      // Compute point on line perpendicular to point on line
      x += length*Math.sin(angle+0.5*Math.PI)*distances[1][i];
      y += length*Math.cos(angle+0.5*Math.PI)*distances[1][i];
      
      xs[i+1] = x;
      ys[i+1] = y;
    }
    
    xs[np-1] = l.x2;
    ys[np-1] = l.y2;
    
    Line lines[] = new Line[np-1];
    for (int i=0; i<np-1; i++) {
      lines[i] = new Line(xs[i],ys[i],xs[i+1],ys[i+1]);
    }
    return lines;
  }
  
  public String toString() {
    String str = new String("Motif["+distances[0].length+"][");
    for (int i=0; i<distances[0].length; i++) {
      str += "("+distances[0][i]+","+distances[1][i]+"), ";
    }
    str += "]";
    return str; 
  }    
 
  
}
