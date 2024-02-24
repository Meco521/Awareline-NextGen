/*    */ package net.minecraft.client.main;
/*    */ 
/*    */ import com.mojang.authlib.properties.PropertyMap;
/*    */ import java.io.File;
/*    */ import java.net.Proxy;
/*    */ import net.minecraft.util.Session;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GameConfiguration
/*    */ {
/*    */   public final UserInformation userInfo;
/*    */   public final DisplayInformation displayInfo;
/*    */   public final FolderInformation folderInfo;
/*    */   public final GameInformation gameInfo;
/*    */   public final ServerInformation serverInfo;
/*    */   
/*    */   public GameConfiguration(UserInformation userInfoIn, DisplayInformation displayInfoIn, FolderInformation folderInfoIn, GameInformation gameInfoIn, ServerInformation serverInfoIn) {
/* 19 */     this.userInfo = userInfoIn;
/* 20 */     this.displayInfo = displayInfoIn;
/* 21 */     this.folderInfo = folderInfoIn;
/* 22 */     this.gameInfo = gameInfoIn;
/* 23 */     this.serverInfo = serverInfoIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public static class DisplayInformation
/*    */   {
/*    */     public final int width;
/*    */     public final int height;
/*    */     public final boolean fullscreen;
/*    */     public final boolean checkGlErrors;
/*    */     
/*    */     public DisplayInformation(int widthIn, int heightIn, boolean fullscreenIn, boolean checkGlErrorsIn) {
/* 35 */       this.width = widthIn;
/* 36 */       this.height = heightIn;
/* 37 */       this.fullscreen = fullscreenIn;
/* 38 */       this.checkGlErrors = checkGlErrorsIn;
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public static class FolderInformation
/*    */   {
/*    */     public final File mcDataDir;
/*    */     public final File resourcePacksDir;
/*    */     public final File assetsDir;
/*    */     public final String assetIndex;
/*    */     
/*    */     public FolderInformation(File mcDataDirIn, File resourcePacksDirIn, File assetsDirIn, String assetIndexIn) {
/* 51 */       this.mcDataDir = mcDataDirIn;
/* 52 */       this.resourcePacksDir = resourcePacksDirIn;
/* 53 */       this.assetsDir = assetsDirIn;
/* 54 */       this.assetIndex = assetIndexIn;
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public static class GameInformation
/*    */   {
/*    */     public final boolean isDemo;
/*    */     public final String version;
/*    */     
/*    */     public GameInformation(boolean isDemoIn, String versionIn) {
/* 65 */       this.isDemo = isDemoIn;
/* 66 */       this.version = versionIn;
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public static class ServerInformation
/*    */   {
/*    */     public final String serverName;
/*    */     public final int serverPort;
/*    */     
/*    */     public ServerInformation(String serverNameIn, int serverPortIn) {
/* 77 */       this.serverName = serverNameIn;
/* 78 */       this.serverPort = serverPortIn;
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public static class UserInformation
/*    */   {
/*    */     public final Session session;
/*    */     public final PropertyMap userProperties;
/*    */     public final PropertyMap profileProperties;
/*    */     public final Proxy proxy;
/*    */     
/*    */     public UserInformation(Session sessionIn, PropertyMap userPropertiesIn, PropertyMap profilePropertiesIn, Proxy proxyIn) {
/* 91 */       this.session = sessionIn;
/* 92 */       this.userProperties = userPropertiesIn;
/* 93 */       this.profileProperties = profilePropertiesIn;
/* 94 */       this.proxy = proxyIn;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\main\GameConfiguration.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */