/*    */ package com.viamcp.platform;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.util.logging.Logger;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import nl.matsv.viabackwards.ViaBackwards;
/*    */ import nl.matsv.viabackwards.api.ViaBackwardsConfig;
/*    */ import nl.matsv.viabackwards.api.ViaBackwardsPlatform;
/*    */ import us.myles.ViaVersion.api.Via;
/*    */ 
/*    */ public class ViaBackwardsPlatformImplementation
/*    */   implements ViaBackwardsPlatform
/*    */ {
/*    */   public ViaBackwardsPlatformImplementation() {
/* 15 */     ViaBackwards.init(this, new ViaBackwardsConfig()
/*    */         {
/*    */           public boolean addCustomEnchantsToLore() {
/* 18 */             return true;
/*    */           }
/*    */ 
/*    */           
/*    */           public boolean addTeamColorTo1_13Prefix() {
/* 23 */             return true;
/*    */           }
/*    */ 
/*    */           
/*    */           public boolean isFix1_13FacePlayer() {
/* 28 */             return true;
/*    */           }
/*    */ 
/*    */           
/*    */           public boolean alwaysShowOriginalMobName() {
/* 33 */             return true;
/*    */           }
/*    */         });
/* 36 */     init((Minecraft.getMinecraft()).mcDataDir);
/*    */   }
/*    */ 
/*    */   
/*    */   public Logger getLogger() {
/* 41 */     return Via.getPlatform().getLogger();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void disable() {}
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isOutdated() {
/* 51 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public File getDataFolder() {
/* 56 */     return (Minecraft.getMinecraft()).mcDataDir;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\viamcp\platform\ViaBackwardsPlatformImplementation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */