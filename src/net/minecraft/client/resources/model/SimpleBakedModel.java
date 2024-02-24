/*     */ package net.minecraft.client.resources.model;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*     */ import net.minecraft.client.renderer.block.model.BreakingFour;
/*     */ import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
/*     */ import net.minecraft.client.renderer.block.model.ModelBlock;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ 
/*     */ 
/*     */ public class SimpleBakedModel
/*     */   implements IBakedModel
/*     */ {
/*     */   protected final List<BakedQuad> generalQuads;
/*     */   protected final List<List<BakedQuad>> faceQuads;
/*     */   protected final boolean ambientOcclusion;
/*     */   protected final boolean gui3d;
/*     */   protected final TextureAtlasSprite texture;
/*     */   protected final ItemCameraTransforms cameraTransforms;
/*     */   
/*     */   public SimpleBakedModel(List<BakedQuad> generalQuadsIn, List<List<BakedQuad>> faceQuadsIn, boolean ambientOcclusionIn, boolean gui3dIn, TextureAtlasSprite textureIn, ItemCameraTransforms cameraTransformsIn) {
/*  24 */     this.generalQuads = generalQuadsIn;
/*  25 */     this.faceQuads = faceQuadsIn;
/*  26 */     this.ambientOcclusion = ambientOcclusionIn;
/*  27 */     this.gui3d = gui3dIn;
/*  28 */     this.texture = textureIn;
/*  29 */     this.cameraTransforms = cameraTransformsIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<BakedQuad> getFaceQuads(EnumFacing facing) {
/*  34 */     return this.faceQuads.get(facing.ordinal());
/*     */   }
/*     */ 
/*     */   
/*     */   public List<BakedQuad> getGeneralQuads() {
/*  39 */     return this.generalQuads;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAmbientOcclusion() {
/*  44 */     return this.ambientOcclusion;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isGui3d() {
/*  49 */     return this.gui3d;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBuiltInRenderer() {
/*  54 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public TextureAtlasSprite getParticleTexture() {
/*  59 */     return this.texture;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemCameraTransforms getItemCameraTransforms() {
/*  64 */     return this.cameraTransforms;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Builder
/*     */   {
/*     */     private final List<BakedQuad> builderGeneralQuads;
/*     */     private final List<List<BakedQuad>> builderFaceQuads;
/*     */     private final boolean builderAmbientOcclusion;
/*     */     private TextureAtlasSprite builderTexture;
/*     */     private final boolean builderGui3d;
/*     */     private final ItemCameraTransforms builderCameraTransforms;
/*     */     
/*     */     public Builder(ModelBlock model) {
/*  78 */       this(model.isAmbientOcclusion(), model.isGui3d(), model.getAllTransforms());
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder(IBakedModel bakedModel, TextureAtlasSprite texture) {
/*  83 */       this(bakedModel.isAmbientOcclusion(), bakedModel.isGui3d(), bakedModel.getItemCameraTransforms());
/*  84 */       this.builderTexture = bakedModel.getParticleTexture();
/*     */       
/*  86 */       for (EnumFacing enumfacing : EnumFacing.values())
/*     */       {
/*  88 */         addFaceBreakingFours(bakedModel, texture, enumfacing);
/*     */       }
/*     */       
/*  91 */       addGeneralBreakingFours(bakedModel, texture);
/*     */     }
/*     */ 
/*     */     
/*     */     private void addFaceBreakingFours(IBakedModel bakedModel, TextureAtlasSprite texture, EnumFacing facing) {
/*  96 */       for (BakedQuad bakedquad : bakedModel.getFaceQuads(facing))
/*     */       {
/*  98 */         addFaceQuad(facing, (BakedQuad)new BreakingFour(bakedquad, texture));
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     private void addGeneralBreakingFours(IBakedModel p_177647_1_, TextureAtlasSprite texture) {
/* 104 */       for (BakedQuad bakedquad : p_177647_1_.getGeneralQuads())
/*     */       {
/* 106 */         addGeneralQuad((BakedQuad)new BreakingFour(bakedquad, texture));
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     private Builder(boolean ambientOcclusion, boolean gui3d, ItemCameraTransforms cameraTransforms) {
/* 112 */       this.builderGeneralQuads = Lists.newArrayList();
/* 113 */       this.builderFaceQuads = Lists.newArrayListWithCapacity(6);
/*     */       
/* 115 */       for (EnumFacing enumfacing : EnumFacing.values())
/*     */       {
/* 117 */         this.builderFaceQuads.add(Lists.newArrayList());
/*     */       }
/*     */       
/* 120 */       this.builderAmbientOcclusion = ambientOcclusion;
/* 121 */       this.builderGui3d = gui3d;
/* 122 */       this.builderCameraTransforms = cameraTransforms;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder addFaceQuad(EnumFacing facing, BakedQuad quad) {
/* 127 */       ((List<BakedQuad>)this.builderFaceQuads.get(facing.ordinal())).add(quad);
/* 128 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder addGeneralQuad(BakedQuad quad) {
/* 133 */       this.builderGeneralQuads.add(quad);
/* 134 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder setTexture(TextureAtlasSprite texture) {
/* 139 */       this.builderTexture = texture;
/* 140 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public IBakedModel makeBakedModel() {
/* 145 */       if (this.builderTexture == null)
/*     */       {
/* 147 */         throw new RuntimeException("Missing particle!");
/*     */       }
/*     */ 
/*     */       
/* 151 */       return new SimpleBakedModel(this.builderGeneralQuads, this.builderFaceQuads, this.builderAmbientOcclusion, this.builderGui3d, this.builderTexture, this.builderCameraTransforms);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\resources\model\SimpleBakedModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */