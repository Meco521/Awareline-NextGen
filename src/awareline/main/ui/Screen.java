package awareline.main.ui;

import awareline.main.utility.Utils;

public interface Screen extends Utils {
  default void onDrag(int mouseX, int mouseY) {}
  
  void initGui();
  
  void keyTyped(char paramChar, int paramInt);
  
  void drawScreen(int paramInt1, int paramInt2);
  
  void mouseClicked(int paramInt1, int paramInt2, int paramInt3);
  
  void mouseReleased(int paramInt1, int paramInt2, int paramInt3);
}


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\Screen.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */