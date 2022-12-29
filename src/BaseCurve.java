class BaseCurve {
  
  private Line[][] lines;
  
  public static int LEFT = 0;
  public static int RIGHT = 1;
  public static int BIDIRECTIONAL = 2;

/*  
  public BaseCurve(Line base_curve[]) {
    lines = new Line[3][];
    lines[LEFT] = base_curve;
    lines[RIGHT] = base_curve;
    lines[BIDIRECTIONAL] = new Line[2*base_curve.length]; 
    
    for (int i=0; i<lines[RIGHT].length; i++) {
      lines[RIGHT].invert();
      
      lines[BIDIRECTIONAL][2*i] = lines[LEFT][i];
      lines[BIDIRECTIONAL][2*i+1] = lines[LEFT][i];
    }
  }
  
  getLines();
  */
  
  
  public static int LINE = 0;
  public static int TRIANGLE = 1;
  public static int SQUARE = 2;
  public static int PENTAGON = 3;
  public static int HEXAGON = 4;
  
  private static String[] names = {"Line","Triangle","Square","Pentagon","Hexagon"};
  
  // For triangle
  private static double sqrt3 = Math.sqrt(3);
  
  // For pentagon, see http://mathworld.wolfram.com/Pentagon.html
  private static double c1 = 0.25*(Math.sqrt(5)-1);
  private static double c2 = 0.25*(Math.sqrt(5)+1);
  private static double s1 = 0.25*Math.sqrt(10+2*Math.sqrt(5));
  private static double s2 = 0.25*Math.sqrt(10-2*Math.sqrt(5));
  
  private static Line[][] base_curves = {
    // Line
    { 
      new Line(0.0, 0.0, 1.0, 0.0) 
    },
    
    // Triangle
    {
      new Line(0.0, 0.0, 1.0, 0.0),
      new Line(1.0, 0.0, 0.5, -0.5*sqrt3),
      new Line(0.5, -0.5*sqrt3, 0.0, 0.0)
    },
    
    // Square
    {
      new Line(0.0, 0.0, 0.0, 1.0),
      new Line(0.0, 1.0, 1.0, 1.0),
      new Line(1.0, 1.0, 1.0, 0.0),
      new Line(1.0, 0.0, 0.0, 0.0)
    },
    // Pentagon, see http://mathworld.wolfram.com/Pentagon.html
    // points: (0,1) - (s1,c1) - (s2,-c2) - (-s2,-c2) - (-s1,c1)  
    {
      new Line(0.0,-1.0,   s1, -c1),
      new Line( s1,-c1,   s2,c2),
      new Line( s2,c2,  -s2,c2),
      new Line(-s2,c2,  -s1,-c1),
      new Line(-s1,-c1,  0.0,-1.0)
    },
    // Hexagon
    {
      new Line(-1.0, 0.0,       -0.5, 0.5*sqrt3),
      new Line(-0.5, 0.5*sqrt3,  0.5, 0.5*sqrt3),
      new Line(0.5, 0.5*sqrt3,  1.0, 0.0),
      new Line(1.0, 0.0,        0.5, -0.5*sqrt3 ),
      new Line(0.5, -0.5*sqrt3, -0.5, -0.5*sqrt3 ),
      new Line(-0.5, -0.5*sqrt3, -1.0,0.0),
    }
  };
  
  public static String[] getNames() {
    return names;
  }

  public static String getName(int base_curve_index) {
    return names[base_curve_index];
  }
  
  public static Line[] getBaseCurve(int base_curve_index) {
    return getBaseCurve(base_curve_index,false);
  }

  public static Line[] getBaseCurve(int base_curve_index, boolean bidirectional) {
    Line[] base_curve = base_curves[base_curve_index];
    if (bidirectional) {
      int n = base_curve.length;
      Line[] ret_lines = new Line[2*n];
      for (int i=0; i<n; i++) {
        ret_lines[i] = base_curve[i];
        ret_lines[i+n] = Line.invert(base_curve[i]);
      }
      return ret_lines;
    }
    return base_curve;
  }
 
}
