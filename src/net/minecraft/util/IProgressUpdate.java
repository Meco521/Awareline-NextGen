package net.minecraft.util;

public interface IProgressUpdate {
  void displaySavingString(String paramString);
  
  void resetProgressAndMessage(String paramString);
  
  void displayLoadingString(String paramString);
  
  void setLoadingProgress(int paramInt);
  
  void setDoneWorking();
}


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraf\\util\IProgressUpdate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */