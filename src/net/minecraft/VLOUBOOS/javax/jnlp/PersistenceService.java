package net.minecraft.VLOUBOOS.javax.jnlp;

import java.net.URL;

public interface PersistenceService {
  public static final int CACHED = 0;
  
  public static final int TEMPORARY = 1;
  
  public static final int DIRTY = 2;
  
  long create(URL paramURL, long paramLong);
  
  FileContents get(URL paramURL);
  
  void delete(URL paramURL);
  
  String[] getNames(URL paramURL);
  
  int getTag(URL paramURL);
  
  void setTag(URL paramURL, int paramInt);
}


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\VLOUBOOS\javax\jnlp\PersistenceService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */