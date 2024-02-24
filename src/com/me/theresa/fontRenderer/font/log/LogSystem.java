package com.me.theresa.fontRenderer.font.log;

public interface LogSystem {
  void error(String paramString, Throwable paramThrowable);
  
  void error(Throwable paramThrowable);
  
  void error(String paramString);
  
  void warn(String paramString);
  
  void warn(String paramString, Throwable paramThrowable);
  
  void info(String paramString);
  
  void debug(String paramString);
}


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\theresa\fontRenderer\font\log\LogSystem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */