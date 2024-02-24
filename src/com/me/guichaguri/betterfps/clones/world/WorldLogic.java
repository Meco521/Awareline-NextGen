/*    */ package com.me.guichaguri.betterfps.clones.world;
/*    */ 
/*    */ import net.minecraft.profiler.Profiler;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.world.WorldServer;
/*    */ import net.minecraft.world.storage.ISaveHandler;
/*    */ import net.minecraft.world.storage.WorldInfo;
/*    */ 
/*    */ 
/*    */ public class WorldLogic
/*    */   extends WorldServer
/*    */ {
/*    */   private static final int radius = 5;
/*    */   
/*    */   private WorldLogic(MinecraftServer server, ISaveHandler saveHandlerIn, WorldInfo info, int dimensionId, Profiler profilerIn) {
/* 16 */     super(server, saveHandlerIn, info, dimensionId, profilerIn);
/*    */   }
/*    */   
/*    */   public void test() {
/* 20 */     TestClass pair = null;
/* 21 */     if (pair.happyBoolean) {
/*    */       return;
/*    */     }
/* 24 */     test();
/*    */   }
/*    */   
/*    */   public class TestClass {
/*    */     public boolean happyBoolean;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\guichaguri\betterfps\clones\world\WorldLogic.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */