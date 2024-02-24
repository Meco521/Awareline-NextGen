package com.me.theresa.fontRenderer.font.impl;

import com.me.theresa.fontRenderer.font.Color;
import com.me.theresa.fontRenderer.font.geom.Shape;
import com.me.theresa.fontRenderer.font.geom.Vector2f;

public interface ShapeFill {
  Color colorAt(Shape paramShape, float paramFloat1, float paramFloat2);
  
  Vector2f getOffsetAt(Shape paramShape, float paramFloat1, float paramFloat2);
}


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\theresa\fontRenderer\font\impl\ShapeFill.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */