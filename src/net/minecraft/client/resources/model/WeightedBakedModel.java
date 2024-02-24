/*     */ package net.minecraft.client.resources.model;
/*     */ 
/*     */ import com.google.common.collect.ComparisonChain;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*     */ import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.WeightedRandom;
/*     */ 
/*     */ 
/*     */ public class WeightedBakedModel
/*     */   implements IBakedModel
/*     */ {
/*     */   private final int totalWeight;
/*     */   private final List<MyWeighedRandomItem> models;
/*     */   private final IBakedModel baseModel;
/*     */   
/*     */   public WeightedBakedModel(List<MyWeighedRandomItem> p_i46073_1_) {
/*  22 */     this.models = p_i46073_1_;
/*  23 */     this.totalWeight = WeightedRandom.getTotalWeight(p_i46073_1_);
/*  24 */     this.baseModel = ((MyWeighedRandomItem)p_i46073_1_.get(0)).model;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<BakedQuad> getFaceQuads(EnumFacing facing) {
/*  29 */     return this.baseModel.getFaceQuads(facing);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<BakedQuad> getGeneralQuads() {
/*  34 */     return this.baseModel.getGeneralQuads();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAmbientOcclusion() {
/*  39 */     return this.baseModel.isAmbientOcclusion();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isGui3d() {
/*  44 */     return this.baseModel.isGui3d();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBuiltInRenderer() {
/*  49 */     return this.baseModel.isBuiltInRenderer();
/*     */   }
/*     */ 
/*     */   
/*     */   public TextureAtlasSprite getParticleTexture() {
/*  54 */     return this.baseModel.getParticleTexture();
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemCameraTransforms getItemCameraTransforms() {
/*  59 */     return this.baseModel.getItemCameraTransforms();
/*     */   }
/*     */ 
/*     */   
/*     */   public IBakedModel getAlternativeModel(long p_177564_1_) {
/*  64 */     return ((MyWeighedRandomItem)WeightedRandom.getRandomItem(this.models, Math.abs((int)p_177564_1_ >> 16) % this.totalWeight)).model;
/*     */   }
/*     */   
/*     */   public static class Builder
/*     */   {
/*  69 */     private final List<WeightedBakedModel.MyWeighedRandomItem> listItems = Lists.newArrayList();
/*     */ 
/*     */     
/*     */     public Builder add(IBakedModel p_177677_1_, int p_177677_2_) {
/*  73 */       this.listItems.add(new WeightedBakedModel.MyWeighedRandomItem(p_177677_1_, p_177677_2_));
/*  74 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public WeightedBakedModel build() {
/*  79 */       Collections.sort(this.listItems);
/*  80 */       return new WeightedBakedModel(this.listItems);
/*     */     }
/*     */ 
/*     */     
/*     */     public IBakedModel first() {
/*  85 */       return ((WeightedBakedModel.MyWeighedRandomItem)this.listItems.get(0)).model;
/*     */     }
/*     */   }
/*     */   
/*     */   static class MyWeighedRandomItem
/*     */     extends WeightedRandom.Item
/*     */     implements Comparable<MyWeighedRandomItem> {
/*     */     protected final IBakedModel model;
/*     */     
/*     */     public MyWeighedRandomItem(IBakedModel p_i46072_1_, int p_i46072_2_) {
/*  95 */       super(p_i46072_2_);
/*  96 */       this.model = p_i46072_1_;
/*     */     }
/*     */ 
/*     */     
/*     */     public int compareTo(MyWeighedRandomItem p_compareTo_1_) {
/* 101 */       return ComparisonChain.start().compare(p_compareTo_1_.itemWeight, this.itemWeight).compare(getCountQuads(), p_compareTo_1_.getCountQuads()).result();
/*     */     }
/*     */ 
/*     */     
/*     */     protected int getCountQuads() {
/* 106 */       int i = this.model.getGeneralQuads().size();
/*     */       
/* 108 */       for (EnumFacing enumfacing : EnumFacing.values())
/*     */       {
/* 110 */         i += this.model.getFaceQuads(enumfacing).size();
/*     */       }
/*     */       
/* 113 */       return i;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 118 */       return "MyWeighedRandomItem{weight=" + this.itemWeight + ", model=" + this.model + '}';
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\resources\model\WeightedBakedModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */