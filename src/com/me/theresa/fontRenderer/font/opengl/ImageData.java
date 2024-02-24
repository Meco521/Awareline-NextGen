package com.me.theresa.fontRenderer.font.opengl;

import java.nio.ByteBuffer;

public interface ImageData {
  int getDepth();
  
  int getWidth();
  
  int getHeight();
  
  int getTexWidth();
  
  int getTexHeight();
  
  ByteBuffer getImageBufferData();
}


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\theresa\fontRenderer\font\opengl\ImageData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */