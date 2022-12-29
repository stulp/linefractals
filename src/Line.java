import java.awt.*;

class Line {

  double x1;
  double y1;
  double x2;
  double y2;
    
  public Line(double x1, double y1, double x2, double y2) {
    this.x1 = x1;
    this.y1 = y1;
    this.x2 = x2;
    this.y2 = y2;
  }
  
  public double distance(double x3, double y3) {
    // http://astronomy.swin.edu.au/~pbourke/geometry/pointline/
    double mag = Math.sqrt( (x2-x1)*(x2-x1) + (y2-y1)*(y2-y1) );
    double u = ( (x3-x1)*(x2-x1) + (y3-y1)*(y2-y1) )/(mag*mag);

    // Point on line, closest to (x3,y3)
    double xp = 0.0;
    double yp = 0.0;
    if (u<0) {
      xp = x1; 
      yp = y1; 
    } else if (u>1.0) {
      xp = x2; 
      yp = y2;
    } else {
      xp = x1+u*(x2-x1); 
      yp = y1+u*(y2-y1); 
    }
    
    double dx = xp-x3;
    double dy = yp-y3;
    double dist = Math.sqrt(dx*dx + dy*dy);
    return dist;
  }

  public static Line invert(Line l) {
    return new Line(l.x2,l.y2,l.x1,l.y1);
  }
  
}
