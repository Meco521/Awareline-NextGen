package com.me.theresa.fontRenderer.font.impl;

import com.me.theresa.fontRenderer.font.Color;

public interface Font {
  int getWidth(String paramString);
  
  int getHeight(String paramString);
  
  int getLineHeight();
  
  void drawString(float paramFloat1, float paramFloat2, String paramString);
  
  void drawString(float paramFloat1, float paramFloat2, String paramString, Color paramColor);
  
  void drawString(float paramFloat1, float paramFloat2, String paramString, Color paramColor, int paramInt1, int paramInt2);
}


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\theresa\fontRenderer\font\impl\Font.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */