package com.me.theresa.fontRenderer.font.effect;

import java.util.List;

public interface ConfigurableEffect extends Effect {
  List getValues();
  
  void setValues(List paramList);
  
  public static interface Value {
    String getName();
    
    String getString();
    
    void setString(String param1String);
    
    Object getObject();
    
    void showDialog();
  }
}


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\theresa\fontRenderer\font\effect\ConfigurableEffect.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */