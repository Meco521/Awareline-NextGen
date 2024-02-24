/*    */ package net.minecraft.client.renderer.tileentity;
/*    */ 
/*    */ import net.minecraft.client.gui.FontRenderer;
/*    */ import net.minecraft.client.renderer.WorldRenderer;
/*    */ import net.minecraft.client.renderer.texture.TextureManager;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.world.World;
/*    */ import net.optifine.entity.model.IEntityRenderer;
/*    */ 
/*    */ public abstract class TileEntitySpecialRenderer<T extends TileEntity>
/*    */   implements IEntityRenderer {
/* 13 */   protected static final ResourceLocation[] DESTROY_STAGES = new ResourceLocation[] { new ResourceLocation("textures/blocks/destroy_stage_0.png"), new ResourceLocation("textures/blocks/destroy_stage_1.png"), new ResourceLocation("textures/blocks/destroy_stage_2.png"), new ResourceLocation("textures/blocks/destroy_stage_3.png"), new ResourceLocation("textures/blocks/destroy_stage_4.png"), new ResourceLocation("textures/blocks/destroy_stage_5.png"), new ResourceLocation("textures/blocks/destroy_stage_6.png"), new ResourceLocation("textures/blocks/destroy_stage_7.png"), new ResourceLocation("textures/blocks/destroy_stage_8.png"), new ResourceLocation("textures/blocks/destroy_stage_9.png") };
/*    */   protected TileEntityRendererDispatcher rendererDispatcher;
/* 15 */   private Class tileEntityClass = null;
/* 16 */   private ResourceLocation locationTextureCustom = null;
/*    */ 
/*    */   
/*    */   public abstract void renderTileEntityAt(T paramT, double paramDouble1, double paramDouble2, double paramDouble3, float paramFloat, int paramInt);
/*    */   
/*    */   protected void bindTexture(ResourceLocation location) {
/* 22 */     TextureManager texturemanager = this.rendererDispatcher.renderEngine;
/*    */     
/* 24 */     if (texturemanager != null)
/*    */     {
/* 26 */       texturemanager.bindTexture(location);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   protected World getWorld() {
/* 32 */     return this.rendererDispatcher.worldObj;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setRendererDispatcher(TileEntityRendererDispatcher rendererDispatcherIn) {
/* 37 */     this.rendererDispatcher = rendererDispatcherIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public FontRenderer getFontRenderer() {
/* 42 */     return this.rendererDispatcher.getFontRenderer();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean forceTileEntityRender() {
/* 52 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void renderTileEntityFast(T p_renderTileEntityFast_1_, double p_renderTileEntityFast_2_, double p_renderTileEntityFast_4_, double p_renderTileEntityFast_6_, float p_renderTileEntityFast_8_, int p_renderTileEntityFast_9_, WorldRenderer p_renderTileEntityFast_10_) {}
/*    */ 
/*    */   
/*    */   public Class getEntityClass() {
/* 61 */     return this.tileEntityClass;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setEntityClass(Class p_setEntityClass_1_) {
/* 66 */     this.tileEntityClass = p_setEntityClass_1_;
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getLocationTextureCustom() {
/* 71 */     return this.locationTextureCustom;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setLocationTextureCustom(ResourceLocation p_setLocationTextureCustom_1_) {
/* 76 */     this.locationTextureCustom = p_setLocationTextureCustom_1_;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\tileentity\TileEntitySpecialRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */