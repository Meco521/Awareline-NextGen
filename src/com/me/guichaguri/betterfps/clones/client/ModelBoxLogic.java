/*    */ package com.me.guichaguri.betterfps.clones.client;
/*    */ 
/*    */ import com.me.guichaguri.betterfps.transformers.cloner.CopyBoolCondition;
/*    */ import com.me.guichaguri.betterfps.transformers.cloner.CopyMode;
/*    */ import net.minecraft.client.model.ModelBox;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.renderer.WorldRenderer;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ModelBoxLogic
/*    */   extends ModelBox
/*    */ {
/*    */   @CopyMode(CopyMode.Mode.IGNORE)
/*    */   public ModelBoxLogic(ModelRenderer renderer, int i1, int i2, float f1, float f2, float f3, int i3, int i4, int i5, float f4) {
/* 18 */     super(renderer, i1, i2, f1, f2, f3, i3, i4, i5, f4);
/*    */   }
/*    */ 
/*    */   
/*    */   @CopyBoolCondition(key = "fastBoxRender", value = true)
/*    */   @CopyMode(CopyMode.Mode.REPLACE)
/*    */   public void render(WorldRenderer renderer, float scale) {
/* 25 */     boolean b = GL11.glIsEnabled(2884);
/* 26 */     if (b) {
/* 27 */       super.render(renderer, scale);
/*    */     } else {
/* 29 */       GL11.glEnable(2884);
/* 30 */       super.render(renderer, scale);
/* 31 */       GL11.glDisable(2884);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\guichaguri\betterfps\clones\client\ModelBoxLogic.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */