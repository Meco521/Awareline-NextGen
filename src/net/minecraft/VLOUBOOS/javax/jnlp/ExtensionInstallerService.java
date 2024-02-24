package net.minecraft.VLOUBOOS.javax.jnlp;

import java.net.URL;

public interface ExtensionInstallerService {
  String getInstallPath();
  
  String getExtensionVersion();
  
  URL getExtensionLocation();
  
  void hideProgressBar();
  
  void hideStatusWindow();
  
  void setHeading(String paramString);
  
  void setStatus(String paramString);
  
  void updateProgress(int paramInt);
  
  void installSucceeded(boolean paramBoolean);
  
  void installFailed();
  
  void setJREInfo(String paramString1, String paramString2);
  
  void setNativeLibraryInfo(String paramString);
  
  String getInstalledJRE(URL paramURL, String paramString);
}


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\VLOUBOOS\javax\jnlp\ExtensionInstallerService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */