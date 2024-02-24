package com.me.theresa.fontRenderer.font.opengl;

public interface Texture {
  boolean hasAlpha();
  
  String getTextureRef();
  
  void bind();
  
  int getImageHeight();
  
  int getImageWidth();
  
  float getHeight();
  
  float getWidth();
  
  int getTextureHeight();
  
  int getTextureWidth();
  
  void release();
  
  int getTextureID();
  
  byte[] getTextureData();
  
  void setTextureFilter(int paramInt);
}


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\theresa\fontRenderer\font\opengl\Texture.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */