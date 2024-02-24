/*     */ package net.minecraft.world.gen;
/*     */ 
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FlatLayerInfo
/*     */ {
/*     */   private final int field_175902_a;
/*     */   private IBlockState layerMaterial;
/*     */   private int layerCount;
/*     */   private int layerMinimumY;
/*     */   
/*     */   public FlatLayerInfo(int p_i45467_1_, Block p_i45467_2_) {
/*  18 */     this(3, p_i45467_1_, p_i45467_2_);
/*     */   }
/*     */ 
/*     */   
/*     */   public FlatLayerInfo(int p_i45627_1_, int height, Block layerMaterialIn) {
/*  23 */     this.layerCount = 1;
/*  24 */     this.field_175902_a = p_i45627_1_;
/*  25 */     this.layerCount = height;
/*  26 */     this.layerMaterial = layerMaterialIn.getDefaultState();
/*     */   }
/*     */ 
/*     */   
/*     */   public FlatLayerInfo(int p_i45628_1_, int p_i45628_2_, Block p_i45628_3_, int p_i45628_4_) {
/*  31 */     this(p_i45628_1_, p_i45628_2_, p_i45628_3_);
/*  32 */     this.layerMaterial = p_i45628_3_.getStateFromMeta(p_i45628_4_);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getLayerCount() {
/*  40 */     return this.layerCount;
/*     */   }
/*     */ 
/*     */   
/*     */   public IBlockState getLayerMaterial() {
/*  45 */     return this.layerMaterial;
/*     */   }
/*     */ 
/*     */   
/*     */   private Block getLayerMaterialBlock() {
/*  50 */     return this.layerMaterial.getBlock();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getFillBlockMeta() {
/*  58 */     return this.layerMaterial.getBlock().getMetaFromState(this.layerMaterial);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMinY() {
/*  66 */     return this.layerMinimumY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMinY(int minY) {
/*  74 */     this.layerMinimumY = minY;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/*     */     String s;
/*  81 */     if (this.field_175902_a >= 3) {
/*     */       
/*  83 */       ResourceLocation resourcelocation = (ResourceLocation)Block.blockRegistry.getNameForObject(getLayerMaterialBlock());
/*  84 */       s = (resourcelocation == null) ? "null" : resourcelocation.toString();
/*     */       
/*  86 */       if (this.layerCount > 1)
/*     */       {
/*  88 */         s = this.layerCount + "*" + s;
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/*  93 */       s = Integer.toString(Block.getIdFromBlock(getLayerMaterialBlock()));
/*     */       
/*  95 */       if (this.layerCount > 1)
/*     */       {
/*  97 */         s = this.layerCount + "x" + s;
/*     */       }
/*     */     } 
/*     */     
/* 101 */     int i = getFillBlockMeta();
/*     */     
/* 103 */     if (i > 0)
/*     */     {
/* 105 */       s = s + ":" + i;
/*     */     }
/*     */     
/* 108 */     return s;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\gen\FlatLayerInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */