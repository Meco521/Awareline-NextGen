/*    */ package net.minecraft.client.resources.model;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*    */ import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
/*    */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ 
/*    */ 
/*    */ public class BuiltInModel
/*    */   implements IBakedModel
/*    */ {
/*    */   private final ItemCameraTransforms cameraTransforms;
/*    */   
/*    */   public BuiltInModel(ItemCameraTransforms p_i46086_1_) {
/* 16 */     this.cameraTransforms = p_i46086_1_;
/*    */   }
/*    */ 
/*    */   
/*    */   public List<BakedQuad> getFaceQuads(EnumFacing facing) {
/* 21 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public List<BakedQuad> getGeneralQuads() {
/* 26 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isAmbientOcclusion() {
/* 31 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isGui3d() {
/* 36 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isBuiltInRenderer() {
/* 41 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public TextureAtlasSprite getParticleTexture() {
/* 46 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemCameraTransforms getItemCameraTransforms() {
/* 51 */     return this.cameraTransforms;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\resources\model\BuiltInModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */