/*    */ package net.minecraft.client.renderer.entity.layers;
/*    */ 
/*    */ import awareline.main.mod.implement.visual.Animations;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBiped;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
/*    */ import net.minecraft.client.renderer.entity.RendererLivingEntity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ public class LayerHeldItem
/*    */   implements LayerRenderer<EntityLivingBase>
/*    */ {
/*    */   private final RendererLivingEntity<?> livingEntityRenderer;
/*    */   
/*    */   public LayerHeldItem(RendererLivingEntity<?> livingEntityRendererIn) {
/* 23 */     this.livingEntityRenderer = livingEntityRendererIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public void doRenderLayer(EntityLivingBase entitylivingbaseIn, float p_177141_2_, float p_177141_3_, float partialTicks, float p_177141_5_, float p_177141_6_, float p_177141_7_, float scale) {
/* 28 */     ItemStack itemstack = entitylivingbaseIn.getHeldItem();
/*    */     
/* 30 */     if (itemstack != null) {
/*    */       
/* 32 */       GlStateManager.pushMatrix();
/*    */       
/* 34 */       if ((this.livingEntityRenderer.getMainModel()).isChild) {
/*    */         
/* 36 */         float f = 0.5F;
/* 37 */         GlStateManager.translate(0.0F, 0.625F, 0.0F);
/* 38 */         GlStateManager.rotate(-20.0F, -1.0F, 0.0F, 0.0F);
/* 39 */         GlStateManager.scale(f, f, f);
/*    */       } 
/* 41 */       if (entitylivingbaseIn == (Minecraft.getMinecraft()).thePlayer && 
/* 42 */         (Minecraft.getMinecraft()).thePlayer.isBlocking() && Animations.getInstance
/* 43 */         .isEnabled() && ((Boolean)Animations.getInstance.betterThirdPersonBlock
/* 44 */         .getValue()).booleanValue()) {
/* 45 */         if (entitylivingbaseIn.isSneaking()) {
/* 46 */           ((ModelBiped)this.livingEntityRenderer.getMainModel()).postRenderArm(0.0325F);
/* 47 */           GlStateManager.translate(-0.58F, 0.3F, -0.2F);
/* 48 */           GlStateManager.rotate(-24390.0F, 137290.0F, -2009900.0F, -2054900.0F);
/*    */         } else {
/* 50 */           ((ModelBiped)this.livingEntityRenderer.getMainModel()).postRenderArm(0.0325F);
/* 51 */           GlStateManager.translate(-0.48F, 0.2F, -0.2F);
/* 52 */           GlStateManager.rotate(-24390.0F, 137290.0F, -2009900.0F, -2054900.0F);
/*    */         } 
/*    */       } else {
/* 55 */         ((ModelBiped)this.livingEntityRenderer.getMainModel()).postRenderArm(0.0625F);
/*    */       } 
/* 57 */       GlStateManager.translate(-0.0625F, 0.4375F, 0.0625F);
/*    */       
/* 59 */       if (entitylivingbaseIn instanceof EntityPlayer && ((EntityPlayer)entitylivingbaseIn).fishEntity != null)
/*    */       {
/* 61 */         itemstack = new ItemStack((Item)Items.fishing_rod, 0);
/*    */       }
/*    */       
/* 64 */       Item item = itemstack.getItem();
/* 65 */       Minecraft minecraft = Minecraft.getMinecraft();
/*    */       
/* 67 */       if (item instanceof net.minecraft.item.ItemBlock && Block.getBlockFromItem(item).getRenderType() == 2) {
/*    */         
/* 69 */         GlStateManager.translate(0.0F, 0.1875F, -0.3125F);
/* 70 */         GlStateManager.rotate(20.0F, 1.0F, 0.0F, 0.0F);
/* 71 */         GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
/* 72 */         float f1 = 0.375F;
/* 73 */         GlStateManager.scale(-f1, -f1, f1);
/*    */       } 
/*    */       
/* 76 */       if (entitylivingbaseIn.isSneaking())
/*    */       {
/* 78 */         GlStateManager.translate(0.0F, 0.203125F, 0.0F);
/*    */       }
/*    */       
/* 81 */       minecraft.getItemRenderer().renderItem(entitylivingbaseIn, itemstack, ItemCameraTransforms.TransformType.THIRD_PERSON);
/* 82 */       GlStateManager.popMatrix();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shouldCombineTextures() {
/* 88 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\entity\layers\LayerHeldItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */