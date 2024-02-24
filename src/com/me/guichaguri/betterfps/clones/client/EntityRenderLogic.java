/*    */ package com.me.guichaguri.betterfps.clones.client;
/*    */ 
/*    */ import com.me.guichaguri.betterfps.transformers.cloner.CopyBoolCondition;
/*    */ import com.me.guichaguri.betterfps.transformers.cloner.CopyMode;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.EntityRenderer;
/*    */ import net.minecraft.client.resources.IResourceManager;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntityRenderLogic
/*    */   extends EntityRenderer
/*    */ {
/*    */   @CopyMode(CopyMode.Mode.IGNORE)
/*    */   public EntityRenderLogic(Minecraft mcIn, IResourceManager manager) {
/* 17 */     super(mcIn, manager);
/*    */   }
/*    */   
/*    */   @CopyBoolCondition(key = "fog", value = false)
/*    */   @CopyMode(CopyMode.Mode.REPLACE)
/*    */   public void setupFog(int p1, float partialTicks) {
/* 23 */     if (p1 != -1)
/* 24 */       return;  super.setupFog(p1, partialTicks);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\guichaguri\betterfps\clones\client\EntityRenderLogic.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */