/*    */ package com.viamcp.platform;
/*    */ 
/*    */ import de.gerrygames.viarewind.api.ViaRewindConfig;
/*    */ import de.gerrygames.viarewind.api.ViaRewindPlatform;
/*    */ import java.util.logging.Logger;
/*    */ import us.myles.ViaVersion.api.Via;
/*    */ 
/*    */ public class ViaRewindPlatformImplementation
/*    */   implements ViaRewindPlatform
/*    */ {
/*    */   public ViaRewindPlatformImplementation() {
/* 12 */     init(new ViaRewindConfig()
/*    */         {
/*    */           public ViaRewindConfig.CooldownIndicator getCooldownIndicator() {
/* 15 */             return ViaRewindConfig.CooldownIndicator.TITLE;
/*    */           }
/*    */ 
/*    */           
/*    */           public boolean isReplaceAdventureMode() {
/* 20 */             return true;
/*    */           }
/*    */ 
/*    */           
/*    */           public boolean isReplaceParticles() {
/* 25 */             return true;
/*    */           }
/*    */         });
/*    */   }
/*    */ 
/*    */   
/*    */   public Logger getLogger() {
/* 32 */     return Via.getPlatform().getLogger();
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\viamcp\platform\ViaRewindPlatformImplementation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */