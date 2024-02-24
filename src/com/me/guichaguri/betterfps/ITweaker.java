package com.me.guichaguri.betterfps;

import java.io.File;
import java.util.List;

public interface ITweaker {
  void acceptOptions(List<String> paramList, File paramFile1, File paramFile2, String paramString);
  
  String getLaunchTarget();
  
  String[] getLaunchArguments();
}


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\guichaguri\betterfps\ITweaker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */