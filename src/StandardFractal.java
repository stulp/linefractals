class StandardFractal {
  private String name;
  private int base_curve_index;
  private boolean bidirectional;
  private Motif motif;
  
  // TODO: pentaflake
  
  
  // http://mathworld.wolfram.com/KochSnowflake.html
  private static StandardFractal standard_fractals[] = {
    new StandardFractal(
      "Koch Snowflake",
      BaseCurve.TRIANGLE,
      false,
      new Motif(        
        new double[][] {
          { 0.33333,                  0.5, 0.66666 },
          {     0.0, -0.1666*Math.sqrt(3),     0.0 }
        }
      )
    ),
    
    // http://mathworld.wolfram.com/KochAntisnowflake.html
    new StandardFractal(
      "Koch Antisnowflake",
      BaseCurve.TRIANGLE,
      false,
      new Motif(        
        new double[][] {
          { 0.33333,                  0.5, 0.66666 },
          {     0.0,  0.1666*Math.sqrt(3),     0.0 }
        }
      )
    ),
    
    // http://mathworld.wolfram.com/GosperIsland.html
    new StandardFractal(
      "Flowsnake",
      BaseCurve.HEXAGON,
      false,
      new Motif(        
        new double[][] {
          { 0.33333,                  0.5, 0.66666 },
          {     0.0, -0.1666*Math.sqrt(3),     0.0 }
        }
      )
    ),
    
    // http://mathworld.wolfram.com/CesaroFractal.html
    new StandardFractal(
      "Torn Square",
      BaseCurve.SQUARE,
      false,
      new Motif(        
        new double[][] {
          { 0.48,  0.5, 0.52 },
          {  0.0, 0.47,  0.0 }
        }
      )
    ),
    
    // http://library.thinkquest.org/26242/full/types/ch1.html
    new StandardFractal(
      "Levy Curve",
      BaseCurve.LINE,
      false,
      new Motif(        
        new double[][] {
          { 0.5},
          { 0.5}
        }
      )
    ),
    
    // http://library.thinkquest.org/26242/full/types/ch1.html
    new StandardFractal(
      "Sierpinski Triangle",
      BaseCurve.TRIANGLE,
      false,
      new Motif(        
        new double[][] {
          { 0.5,              0.25,              0.75, 0.5},
          { 0.0, 0.25*Math.sqrt(3), 0.25*Math.sqrt(3), 0.0}
        }
      )
    ),
    
    // http://library.thinkquest.org/26242/full/types/ch1.html
    new StandardFractal(
      "Koch Island",
      BaseCurve.SQUARE,
      false,
      new Motif(        
        new double[][] {
          { 0.25,  0.25,  0.50, 0.50, 0.75, 0.75},
          { 0.00, -0.25, -0.25, 0.25, 0.25, 0.00}
        }
      )
    )/*,
    
    // http://en.wikipedia.org/wiki/Space-filling_curve
    new StandardFractal(
      "Peano Curve",
      BaseCurve.LINE,
      false,
      new Motif(        
        new double[][] {
          { 0.0, 0.5, 0.5, 1.0, 1.0  },
          { 1.0, 1.0, 0.0, 0.0, 1.0 }
        }
      )
    ),
    
    new StandardFractal(
      "Peano Curve II",
      BaseCurve.LINE,
      false,
      new Motif(        
        new double[][] {
          { 0.50, 0.75,  0.25,  0.50 },
          { 0.50, 0.25, -0.25, -0.50 }
        }
      )
    )
    
    */
    
  };
  
  public static String[] getNames() {
    String names[] = new String[standard_fractals.length];
    for(int i=0; i<standard_fractals.length; i++) {
      names[i] = standard_fractals[i].getName();
    }
    return names;
  }
  
  public static StandardFractal getStandardFractal(int index) {
    return standard_fractals[index];
     
  }
  
  public StandardFractal(String name, int base_curve_index, Motif motif) {
    this.name = name;
    this.base_curve_index = base_curve_index;
    this.bidirectional = false;
    this.motif = motif;
  }
  
  public StandardFractal(String name, int base_curve_index, boolean bidirectional, Motif motif) {
    this.name = name;
    this.base_curve_index = base_curve_index;
    this.bidirectional = bidirectional;
    this.motif = motif;
  }
  
  public String getName() {
    return name; 
  }

  public int getBaseCurveIndex() {
    return base_curve_index;
  }
  
  public boolean getBidirectional() {
    return bidirectional;
  }
  
  public Line[] getBaseCurve() {
    return BaseCurve.getBaseCurve(base_curve_index,bidirectional); 
  }
  
  public Motif getMotif() {
    return motif;
  }
  
}
