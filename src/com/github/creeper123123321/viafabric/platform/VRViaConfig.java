/*    */ package com.github.creeper123123321.viafabric.platform;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.net.URL;
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import us.myles.ViaVersion.AbstractViaConfig;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class VRViaConfig
/*    */   extends AbstractViaConfig
/*    */ {
/* 38 */   private static final List<String> UNSUPPORTED = Arrays.asList(new String[] { "anti-xray-patch", "bungee-ping-interval", "bungee-ping-save", "bungee-servers", "quick-move-action-fix", "nms-player-ticking", "item-cache", "velocity-ping-interval", "velocity-ping-save", "velocity-servers", "blockconnection-method", "change-1_9-hitbox", "change-1_14-hitbox" });
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public VRViaConfig(File configFile) {
/* 44 */     super(configFile);
/*    */     
/* 46 */     reloadConfig();
/*    */   }
/*    */ 
/*    */   
/*    */   public URL getDefaultConfigURL() {
/* 51 */     return getClass().getClassLoader().getResource("assets/viaversion/config.yml");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void handleConfig(Map<String, Object> config) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public List<String> getUnsupportedOptions() {
/* 61 */     return UNSUPPORTED;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isAntiXRay() {
/* 66 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isItemCache() {
/* 71 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isNMSPlayerTicking() {
/* 76 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean is1_12QuickMoveActionFix() {
/* 81 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getBlockConnectionMethod() {
/* 86 */     return "packet";
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean is1_9HitboxFix() {
/* 91 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean is1_14HitboxFix() {
/* 96 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\github\creeper123123321\viafabric\platform\VRViaConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */