/*    */ package com.me.guichaguri.betterfps.tweaker;
/*    */ 
/*    */ import com.me.guichaguri.betterfps.BetterFpsHelper;
/*    */ import com.me.guichaguri.betterfps.ITweaker;
/*    */ import java.io.File;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BetterFpsTweaker
/*    */   implements ITweaker
/*    */ {
/*    */   private List<String> args;
/*    */   
/*    */   public void acceptOptions(List<String> args, File gameDir, File assetsDir, String profile) {
/* 19 */     this.args = new ArrayList<>(args);
/* 20 */     this.args.add("--version");
/* 21 */     this.args.add(profile);
/* 22 */     if (assetsDir != null) {
/* 23 */       this.args.add("--assetsDir");
/* 24 */       this.args.add(assetsDir.getAbsolutePath());
/*    */     } 
/* 26 */     if (gameDir != null) {
/* 27 */       this.args.add("--gameDir");
/* 28 */       this.args.add(gameDir.getAbsolutePath());
/*    */     } 
/*    */     
/* 31 */     BetterFpsHelper.MCDIR = gameDir;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getLaunchTarget() {
/* 36 */     return "net.minecraft.client.main.net.minecraft.client.main.Main";
/*    */   }
/*    */ 
/*    */   
/*    */   public String[] getLaunchArguments() {
/* 41 */     return new String[0];
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\guichaguri\betterfps\tweaker\BetterFpsTweaker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */