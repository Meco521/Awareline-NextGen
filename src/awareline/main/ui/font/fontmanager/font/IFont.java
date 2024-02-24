package awareline.main.ui.font.fontmanager.font;

public interface IFont {
  int getStringWidth(String paramString);
  
  double getStringWidth_double(String paramString);
  
  int getCharWidth(char paramChar);
  
  int getHeight();
  
  void drawStringWithShadow(String paramString, double paramDouble1, double paramDouble2, int paramInt);
  
  void drawString(String paramString, double paramDouble1, double paramDouble2, int paramInt);
  
  void drawCenteredStringWithShadow(String paramString, double paramDouble1, double paramDouble2, int paramInt);
  
  void drawCenteredString(String paramString, double paramDouble1, double paramDouble2, int paramInt);
  
  void drawStringWithOutline(String paramString, double paramDouble1, double paramDouble2, int paramInt);
  
  void drawCharWithShadow(char paramChar, double paramDouble1, double paramDouble2, int paramInt);
  
  void drawChar(char paramChar, double paramDouble1, double paramDouble2, int paramInt);
}


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\font\fontmanager\font\IFont.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */