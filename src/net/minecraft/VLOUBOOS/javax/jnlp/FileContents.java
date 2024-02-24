package net.minecraft.VLOUBOOS.javax.jnlp;

import java.io.InputStream;
import java.io.OutputStream;

public interface FileContents {
  String getName();
  
  InputStream getInputStream();
  
  OutputStream getOutputStream(boolean paramBoolean);
  
  long getLength();
  
  boolean canRead();
  
  boolean canWrite();
  
  JNLPRandomAccessFile getRandomAccessFile(String paramString);
  
  long getMaxLength();
  
  long setMaxLength(long paramLong);
}


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\VLOUBOOS\javax\jnlp\FileContents.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */