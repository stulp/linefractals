import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
class LineCanvas extends JPanel implements MouseListener, MouseMotionListener {
  
  protected Line[] lines;
  protected boolean enabled;
  protected boolean auto_center;

  int grid_size = 20;  
  Dimension size = new Dimension(200,200);
  int point_radius = 8;
  
  private static int NONE_DRAGGED = -1000;
  int dragged_point = NONE_DRAGGED;  

  
  Point canvas_center = new Point(0,0);
  double fractal_center_x = 0.0; 
  double fractal_center_y = 0.0;
  double scale = 100.0;
  
  Dimension panel_size = new Dimension(0,0);
  boolean update_canvas;

  public LineCanvas() {
    lines = new Line[0];
    update_canvas = true;
    auto_center = false;
    enabled = false;
    repaint();
  }
  
  public void setAutoCenter(boolean auto_center) {
    this.auto_center = auto_center;
    if (auto_center) repaint();
  }
  
  public void setEnabled(boolean enabled) {
    if (enabled!=this.enabled) {
      if (enabled) {
        addMouseListener(this);
        addMouseMotionListener(this);
      } else {
        removeMouseListener(this);
        removeMouseMotionListener(this);
      }
      this.enabled = enabled;
    }
  }
  
  public void centerLines() {
    double min_x = 10000000.0;
    double min_y = 10000000.0;
    double max_x = -10000000.0;
    double max_y = -10000000.0;
    
    for (int i=0; i<lines.length; i++) {
      min_x = Math.min(min_x,lines[i].x1);
      min_x = Math.min(min_x,lines[i].x2);
      min_y = Math.min(min_y,lines[i].y1);
      min_y = Math.min(min_y,lines[i].y2);

      max_x = Math.max(max_x,lines[i].x1);
      max_x = Math.max(max_x,lines[i].x2);
      max_y = Math.max(max_y,lines[i].y1);
      max_y = Math.max(max_y,lines[i].y2);
    }
    

    double range_x = max_x-min_x;
    double range_y = max_y-min_y;
    
    fractal_center_x = min_x+0.5*range_x; 
    fractal_center_y = min_y+0.5*range_y; 
    
    Dimension current_size = getSize();
    
    double scale_x = 1000000.0;
    if (range_x>0.0) scale_x = current_size.getWidth()/range_x;
    
    double scale_y = 1000000.0;
    if (range_y>0.0) scale_y = current_size.getHeight()/range_y;
    
    scale = 0.9*Math.min(scale_x, scale_y);
    repaint();
  }

  private int distance(Point p1, Point p2) {
    int dx = p1.x - p2.x; 
    int dy = p1.y - p2.y;
    return (int)(Math.sqrt( dx*dx + dy*dy ));
  }
  
  public void mouseClicked(MouseEvent mouse_event) {
  }
  
  public void mouseEntered(MouseEvent mouse_event) {
  }
  
  public void mouseExited(MouseEvent mouse_event) {
  }
  
  public void mousePressed(MouseEvent mouse_event) {
    Point point = mouse_event.getPoint();
    
    // Clicked in one of the points?
    //for (int c=-1; c<lines.length; c++) {
    for (int c=0; c<lines.length-1; c++) { // Never edit end points
      double x;
      double y; 
      if (c==-1) {
         x = lines[0].x1;
         y = lines[0].y1;
      } else {
         x = lines[c].x2;
         y = lines[c].y2;
      }
      
      int dist = distance(toPoint(x,y),point);
      if (dist < point_radius) {
        // Clicked in the hole with this center!
        if (mouse_event.getButton()==MouseEvent.BUTTON3) {
          removePoint(c);
        } else {
          dragged_point = c;
        }
        return;
      }
    }
    
    // Clicked near one of the lines?
    for (int c=0; c<lines.length; c++) {
      double dist_line = lines[c].distance(toX(point), toY(point));
      int dist = (int)(scale*dist_line);
      if (dist < point_radius) {
        insertPoint(c,point);
        return;
      }
    }    
    
  }

  private void insertPoint(int index, Point new_point) {
    double new_x = toX(new_point);
    double new_y = toY(new_point);
    
    Line new_lines[] = new Line[lines.length+1];
    int new_i = 0;
    for (int i=0; i<lines.length; i++) {
      new_lines[new_i++] = lines[i];
      if (i==index) {

        Line new_line = new Line(new_x,new_y,lines[index].x2,lines[index].y2);

        new_lines[new_i++] = new_line;

        new_lines[index].x2 = new_x;
        new_lines[index].y2 = new_y;
      }
    }
    lines = new_lines;
    
    dragged_point = index;
    repaint();
  }

  private void removePoint(int index) {
    // (x2,y2) of line[index] must go
    // (x1,y1) of line[index+1] must go
    
    if (index<lines.length) {
      lines[index].x2 = lines[index+1].x2;
      lines[index].y2 = lines[index+1].y2;

      //lines[index+1].x2 = lines[index+1].x1;
      //lines[index+1].y2 = lines[index+1].y1;
      
      // Remove lines[index+1];
      Line new_lines[] = new Line[lines.length-1];
      int new_i = 0;
      for (int i=0; i<lines.length; i++) {
        new_lines[new_i++] = lines[i];
        if (i==index) i++;
      }
      lines = new_lines;
    }
    repaint();
  }
  
  public void mouseReleased(MouseEvent mouse_event) {
    /*
    Point point = mouse_event.getPoint();
    for (int c=-1; c<lines.length; c++) {
      if (c!=dragged_point) {
        double x;
        double y; 
        if (c==-1) {
           x = lines[0].x1;
           y = lines[0].y1;
        } else {
           x = lines[c].x2;
           y = lines[c].y2;
        }
        int dist = distance(toPoint(x,y),point);

        if (dist<point_radius) {
          System.out.println("Dropped very close!"); 
          removePoint(dragged_point);
          //if ( (c==-1) || (c==lines.length) ) {
          //  // Never remove begin or end point
          //  removePoint(dragged_point);
          //} else {
          //  removePoint(c);
          //}
        }
      }
    }
    */
    dragged_point = NONE_DRAGGED;
    repaint();
  }
  
  public void mouseMoved(MouseEvent mouse_event) {
  }

  public void mouseDragged(MouseEvent mouse_event) {
    if (dragged_point!=NONE_DRAGGED) {
      Point point = mouse_event.getPoint();
      double x = toX(point);
      double y = toY(point);
      if (dragged_point==-1) {
        //lines[0].x1 = x;
        //lines[0].y1 = y;
      } else if (dragged_point==(lines.length-1)) {
        //lines[lines.length-1].x2 = x;
        //lines[lines.length-1].y2 = y;
      } else {
        lines[dragged_point].x2 = x;
        lines[dragged_point].y2 = y;
        lines[dragged_point+1].x1 = x;
        lines[dragged_point+1].y1 = y;
      }
      repaint();
    }
  }
  /*
  public void update(Graphics g) {
    Graphics offgc;
    Image offscreen = null;
    Rectangle box = g.getClipBounds();
    // create the offscreen buffer and associated Graphics
    offscreen = createImage(box.width, box.height);
    offgc = offscreen.getGraphics();
    // clear the exposed area
    offgc.setColor(getBackground());
    offgc.fillRect(0, 0, box.width, box.height);
    offgc.setColor(getForeground());
    // do normal redraw
    offgc.translate(-box.x, -box.y);
    paint(offgc);
    // transfer offscreen to window
    g.drawImage(offscreen, box.x, box.y, this);
  }
*/

  protected Point toPoint(double x, double y) {
    x = scale*(x-fractal_center_x);
    y = scale*(y-fractal_center_y);
    return new Point(((int)x)+canvas_center.x,((int)y)+canvas_center.y);
  }

  protected double toX(Point p) {
    double x = p.x - canvas_center.x;
    x = (x/scale) + fractal_center_x;
    return x;
  }

  protected double toY(Point p) {
    double y = p.y - canvas_center.y;
    y = (y/scale) + fractal_center_y;
    return y;
  }

  public void paint(Graphics graphics) {
    if (auto_center) centerLines();
    
    Dimension current_size = getSize();
    if (current_size!=panel_size) {
      update_canvas = true;
      panel_size=current_size;
      canvas_center = new Point((int)current_size.getWidth()/2,(int)current_size.getHeight()/2);
    }
    
    if (update_canvas) {
      graphics.clearRect(0,0,(int)current_size.getWidth(),(int)current_size.getHeight());
      for (int l=0; l<lines.length; l++) {
        Line line = lines[l];
        Point p1 = toPoint(line.x1,line.y1);
        Point p2 = toPoint(line.x2,line.y2);
        graphics.setColor(Color.BLACK);
        graphics.drawLine(p1.x,p1.y,p2.x,p2.y);
        
        // Draw arrow
        int x = (int)(0.9*(p2.x-p1.x) + p1.x);
        int y = (int)(0.9*(p2.y-p1.y) + p1.y);
        int radius = 5;
        //graphics.fillOval(x-radius,y-radius,radius*2,radius*2);
        graphics.setColor(Color.GREEN);
        graphics.drawLine(p2.x,p2.y,x,y);
        
      }
      
      if (enabled) {
    
        /*    
        Point first_point = toPoint(init_line.x1, init_line.y1); 
        Point last_point = toPoint(init_line.x2, init_line.y2); 
        graphics.setColor(new Color(191,191,191));
        graphics.drawLine(first_point.x,first_point.y,last_point.x,last_point.y);
    
        */
        graphics.setColor(new Color(255,0,0));
        for (int l=1; l<lines.length-1; l++) {
          Line line = lines[l];
          Point p1 = toPoint(line.x1,line.y1);
          graphics.drawOval(p1.x-point_radius,p1.y-point_radius,2*point_radius,2*point_radius);

          Point p2 = toPoint(line.x2,line.y2);
          graphics.drawOval(p2.x-point_radius,p2.y-point_radius,2*point_radius,2*point_radius);
        }
      }
    }
  
    /*    
      // - If size changes: 
      if (!canvas_size.equals(current_size)) {
        // Size changed, so update background
        update_grid = true;
        canvas_size = current_size;
      }
  
      if (update_grid) {
        */
    update_canvas = false;
    
  }
}

