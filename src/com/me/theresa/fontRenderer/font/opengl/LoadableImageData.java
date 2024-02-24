package com.me.theresa.fontRenderer.font.opengl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public interface LoadableImageData extends ImageData {
  void configureEdging(boolean paramBoolean);
  
  ByteBuffer loadImage(InputStream paramInputStream) throws IOException;
  
  ByteBuffer loadImage(InputStream paramInputStream, boolean paramBoolean, int[] paramArrayOfint) throws IOException;
  
  ByteBuffer loadImage(InputStream paramInputStream, boolean paramBoolean1, boolean paramBoolean2, int[] paramArrayOfint) throws IOException;
}


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\theresa\fontRenderer\font\opengl\LoadableImageData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */