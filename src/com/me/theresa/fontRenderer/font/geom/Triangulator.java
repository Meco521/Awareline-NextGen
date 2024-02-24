package com.me.theresa.fontRenderer.font.geom;

import java.io.Serializable;

public interface Triangulator extends Serializable {
  int getTriangleCount();
  
  float[] getTrianglePoint(int paramInt1, int paramInt2);
  
  void addPolyPoint(float paramFloat1, float paramFloat2);
  
  void startHole();
  
  boolean triangulate();
}


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\theresa\fontRenderer\font\geom\Triangulator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */