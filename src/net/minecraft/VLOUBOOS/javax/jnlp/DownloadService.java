package net.minecraft.VLOUBOOS.javax.jnlp;

import java.net.URL;

public interface DownloadService {
  boolean isResourceCached(URL paramURL, String paramString);
  
  boolean isPartCached(String paramString);
  
  boolean isPartCached(String[] paramArrayOfString);
  
  boolean isExtensionPartCached(URL paramURL, String paramString1, String paramString2);
  
  boolean isExtensionPartCached(URL paramURL, String paramString, String[] paramArrayOfString);
  
  void loadResource(URL paramURL, String paramString, DownloadServiceListener paramDownloadServiceListener);
  
  void loadPart(String paramString, DownloadServiceListener paramDownloadServiceListener);
  
  void loadPart(String[] paramArrayOfString, DownloadServiceListener paramDownloadServiceListener);
  
  void loadExtensionPart(URL paramURL, String paramString1, String paramString2, DownloadServiceListener paramDownloadServiceListener);
  
  void loadExtensionPart(URL paramURL, String paramString, String[] paramArrayOfString, DownloadServiceListener paramDownloadServiceListener);
  
  void removeResource(URL paramURL, String paramString);
  
  void removePart(String paramString);
  
  void removePart(String[] paramArrayOfString);
  
  void removeExtensionPart(URL paramURL, String paramString1, String paramString2);
  
  void removeExtensionPart(URL paramURL, String paramString, String[] paramArrayOfString);
  
  DownloadServiceListener getDefaultProgressWindow();
}


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\VLOUBOOS\javax\jnlp\DownloadService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */