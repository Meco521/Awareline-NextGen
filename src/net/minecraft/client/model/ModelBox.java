/*     */ package net.minecraft.client.model;
/*     */ 
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ModelBox
/*     */ {
/*     */   private final PositionTextureVertex[] vertexPositions;
/*     */   private final TexturedQuad[] quadList;
/*     */   public final float posX1;
/*     */   public final float posY1;
/*     */   public final float posZ1;
/*     */   public final float posX2;
/*     */   public final float posY2;
/*     */   public final float posZ2;
/*     */   public String boxName;
/*     */   
/*     */   public ModelBox(ModelRenderer renderer, int p_i46359_2_, int p_i46359_3_, float p_i46359_4_, float p_i46359_5_, float p_i46359_6_, int p_i46359_7_, int p_i46359_8_, int p_i46359_9_, float p_i46359_10_) {
/*  36 */     this(renderer, p_i46359_2_, p_i46359_3_, p_i46359_4_, p_i46359_5_, p_i46359_6_, p_i46359_7_, p_i46359_8_, p_i46359_9_, p_i46359_10_, renderer.mirror);
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelBox(ModelRenderer p_i0_1_, int[][] p_i0_2_, float p_i0_3_, float p_i0_4_, float p_i0_5_, float p_i0_6_, float p_i0_7_, float p_i0_8_, float p_i0_9_, boolean p_i0_10_) {
/*  41 */     this.posX1 = p_i0_3_;
/*  42 */     this.posY1 = p_i0_4_;
/*  43 */     this.posZ1 = p_i0_5_;
/*  44 */     this.posX2 = p_i0_3_ + p_i0_6_;
/*  45 */     this.posY2 = p_i0_4_ + p_i0_7_;
/*  46 */     this.posZ2 = p_i0_5_ + p_i0_8_;
/*  47 */     this.vertexPositions = new PositionTextureVertex[8];
/*  48 */     this.quadList = new TexturedQuad[6];
/*  49 */     float f = p_i0_3_ + p_i0_6_;
/*  50 */     float f1 = p_i0_4_ + p_i0_7_;
/*  51 */     float f2 = p_i0_5_ + p_i0_8_;
/*  52 */     p_i0_3_ -= p_i0_9_;
/*  53 */     p_i0_4_ -= p_i0_9_;
/*  54 */     p_i0_5_ -= p_i0_9_;
/*  55 */     f += p_i0_9_;
/*  56 */     f1 += p_i0_9_;
/*  57 */     f2 += p_i0_9_;
/*     */     
/*  59 */     if (p_i0_10_) {
/*     */       
/*  61 */       float f3 = f;
/*  62 */       f = p_i0_3_;
/*  63 */       p_i0_3_ = f3;
/*     */     } 
/*     */     
/*  66 */     PositionTextureVertex positiontexturevertex7 = new PositionTextureVertex(p_i0_3_, p_i0_4_, p_i0_5_, 0.0F, 0.0F);
/*  67 */     PositionTextureVertex positiontexturevertex = new PositionTextureVertex(f, p_i0_4_, p_i0_5_, 0.0F, 8.0F);
/*  68 */     PositionTextureVertex positiontexturevertex1 = new PositionTextureVertex(f, f1, p_i0_5_, 8.0F, 8.0F);
/*  69 */     PositionTextureVertex positiontexturevertex2 = new PositionTextureVertex(p_i0_3_, f1, p_i0_5_, 8.0F, 0.0F);
/*  70 */     PositionTextureVertex positiontexturevertex3 = new PositionTextureVertex(p_i0_3_, p_i0_4_, f2, 0.0F, 0.0F);
/*  71 */     PositionTextureVertex positiontexturevertex4 = new PositionTextureVertex(f, p_i0_4_, f2, 0.0F, 8.0F);
/*  72 */     PositionTextureVertex positiontexturevertex5 = new PositionTextureVertex(f, f1, f2, 8.0F, 8.0F);
/*  73 */     PositionTextureVertex positiontexturevertex6 = new PositionTextureVertex(p_i0_3_, f1, f2, 8.0F, 0.0F);
/*  74 */     this.vertexPositions[0] = positiontexturevertex7;
/*  75 */     this.vertexPositions[1] = positiontexturevertex;
/*  76 */     this.vertexPositions[2] = positiontexturevertex1;
/*  77 */     this.vertexPositions[3] = positiontexturevertex2;
/*  78 */     this.vertexPositions[4] = positiontexturevertex3;
/*  79 */     this.vertexPositions[5] = positiontexturevertex4;
/*  80 */     this.vertexPositions[6] = positiontexturevertex5;
/*  81 */     this.vertexPositions[7] = positiontexturevertex6;
/*  82 */     this.quadList[0] = makeTexturedQuad(new PositionTextureVertex[] { positiontexturevertex4, positiontexturevertex, positiontexturevertex1, positiontexturevertex5 }, p_i0_2_[4], false, p_i0_1_.textureWidth, p_i0_1_.textureHeight);
/*  83 */     this.quadList[1] = makeTexturedQuad(new PositionTextureVertex[] { positiontexturevertex7, positiontexturevertex3, positiontexturevertex6, positiontexturevertex2 }, p_i0_2_[5], false, p_i0_1_.textureWidth, p_i0_1_.textureHeight);
/*  84 */     this.quadList[2] = makeTexturedQuad(new PositionTextureVertex[] { positiontexturevertex4, positiontexturevertex3, positiontexturevertex7, positiontexturevertex }, p_i0_2_[1], true, p_i0_1_.textureWidth, p_i0_1_.textureHeight);
/*  85 */     this.quadList[3] = makeTexturedQuad(new PositionTextureVertex[] { positiontexturevertex1, positiontexturevertex2, positiontexturevertex6, positiontexturevertex5 }, p_i0_2_[0], true, p_i0_1_.textureWidth, p_i0_1_.textureHeight);
/*  86 */     this.quadList[4] = makeTexturedQuad(new PositionTextureVertex[] { positiontexturevertex, positiontexturevertex7, positiontexturevertex2, positiontexturevertex1 }, p_i0_2_[2], false, p_i0_1_.textureWidth, p_i0_1_.textureHeight);
/*  87 */     this.quadList[5] = makeTexturedQuad(new PositionTextureVertex[] { positiontexturevertex3, positiontexturevertex4, positiontexturevertex5, positiontexturevertex6 }, p_i0_2_[3], false, p_i0_1_.textureWidth, p_i0_1_.textureHeight);
/*     */     
/*  89 */     if (p_i0_10_)
/*     */     {
/*  91 */       for (TexturedQuad texturedquad : this.quadList)
/*     */       {
/*  93 */         texturedquad.flipFace();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private TexturedQuad makeTexturedQuad(PositionTextureVertex[] p_makeTexturedQuad_1_, int[] p_makeTexturedQuad_2_, boolean p_makeTexturedQuad_3_, float p_makeTexturedQuad_4_, float p_makeTexturedQuad_5_) {
/* 100 */     return (p_makeTexturedQuad_2_ == null) ? null : (p_makeTexturedQuad_3_ ? new TexturedQuad(p_makeTexturedQuad_1_, p_makeTexturedQuad_2_[2], p_makeTexturedQuad_2_[3], p_makeTexturedQuad_2_[0], p_makeTexturedQuad_2_[1], p_makeTexturedQuad_4_, p_makeTexturedQuad_5_) : new TexturedQuad(p_makeTexturedQuad_1_, p_makeTexturedQuad_2_[0], p_makeTexturedQuad_2_[1], p_makeTexturedQuad_2_[2], p_makeTexturedQuad_2_[3], p_makeTexturedQuad_4_, p_makeTexturedQuad_5_));
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelBox(ModelRenderer renderer, int textureX, int textureY, float p_i46301_4_, float p_i46301_5_, float p_i46301_6_, int p_i46301_7_, int p_i46301_8_, int p_i46301_9_, float p_i46301_10_, boolean p_i46301_11_) {
/* 105 */     this.posX1 = p_i46301_4_;
/* 106 */     this.posY1 = p_i46301_5_;
/* 107 */     this.posZ1 = p_i46301_6_;
/* 108 */     this.posX2 = p_i46301_4_ + p_i46301_7_;
/* 109 */     this.posY2 = p_i46301_5_ + p_i46301_8_;
/* 110 */     this.posZ2 = p_i46301_6_ + p_i46301_9_;
/* 111 */     this.vertexPositions = new PositionTextureVertex[8];
/* 112 */     this.quadList = new TexturedQuad[6];
/* 113 */     float f = p_i46301_4_ + p_i46301_7_;
/* 114 */     float f1 = p_i46301_5_ + p_i46301_8_;
/* 115 */     float f2 = p_i46301_6_ + p_i46301_9_;
/* 116 */     p_i46301_4_ -= p_i46301_10_;
/* 117 */     p_i46301_5_ -= p_i46301_10_;
/* 118 */     p_i46301_6_ -= p_i46301_10_;
/* 119 */     f += p_i46301_10_;
/* 120 */     f1 += p_i46301_10_;
/* 121 */     f2 += p_i46301_10_;
/*     */     
/* 123 */     if (p_i46301_11_) {
/*     */       
/* 125 */       float f3 = f;
/* 126 */       f = p_i46301_4_;
/* 127 */       p_i46301_4_ = f3;
/*     */     } 
/*     */     
/* 130 */     PositionTextureVertex positiontexturevertex7 = new PositionTextureVertex(p_i46301_4_, p_i46301_5_, p_i46301_6_, 0.0F, 0.0F);
/* 131 */     PositionTextureVertex positiontexturevertex = new PositionTextureVertex(f, p_i46301_5_, p_i46301_6_, 0.0F, 8.0F);
/* 132 */     PositionTextureVertex positiontexturevertex1 = new PositionTextureVertex(f, f1, p_i46301_6_, 8.0F, 8.0F);
/* 133 */     PositionTextureVertex positiontexturevertex2 = new PositionTextureVertex(p_i46301_4_, f1, p_i46301_6_, 8.0F, 0.0F);
/* 134 */     PositionTextureVertex positiontexturevertex3 = new PositionTextureVertex(p_i46301_4_, p_i46301_5_, f2, 0.0F, 0.0F);
/* 135 */     PositionTextureVertex positiontexturevertex4 = new PositionTextureVertex(f, p_i46301_5_, f2, 0.0F, 8.0F);
/* 136 */     PositionTextureVertex positiontexturevertex5 = new PositionTextureVertex(f, f1, f2, 8.0F, 8.0F);
/* 137 */     PositionTextureVertex positiontexturevertex6 = new PositionTextureVertex(p_i46301_4_, f1, f2, 8.0F, 0.0F);
/* 138 */     this.vertexPositions[0] = positiontexturevertex7;
/* 139 */     this.vertexPositions[1] = positiontexturevertex;
/* 140 */     this.vertexPositions[2] = positiontexturevertex1;
/* 141 */     this.vertexPositions[3] = positiontexturevertex2;
/* 142 */     this.vertexPositions[4] = positiontexturevertex3;
/* 143 */     this.vertexPositions[5] = positiontexturevertex4;
/* 144 */     this.vertexPositions[6] = positiontexturevertex5;
/* 145 */     this.vertexPositions[7] = positiontexturevertex6;
/* 146 */     this.quadList[0] = new TexturedQuad(new PositionTextureVertex[] { positiontexturevertex4, positiontexturevertex, positiontexturevertex1, positiontexturevertex5 }, textureX + p_i46301_9_ + p_i46301_7_, textureY + p_i46301_9_, textureX + p_i46301_9_ + p_i46301_7_ + p_i46301_9_, textureY + p_i46301_9_ + p_i46301_8_, renderer.textureWidth, renderer.textureHeight);
/* 147 */     this.quadList[1] = new TexturedQuad(new PositionTextureVertex[] { positiontexturevertex7, positiontexturevertex3, positiontexturevertex6, positiontexturevertex2 }, textureX, textureY + p_i46301_9_, textureX + p_i46301_9_, textureY + p_i46301_9_ + p_i46301_8_, renderer.textureWidth, renderer.textureHeight);
/* 148 */     this.quadList[2] = new TexturedQuad(new PositionTextureVertex[] { positiontexturevertex4, positiontexturevertex3, positiontexturevertex7, positiontexturevertex }, textureX + p_i46301_9_, textureY, textureX + p_i46301_9_ + p_i46301_7_, textureY + p_i46301_9_, renderer.textureWidth, renderer.textureHeight);
/* 149 */     this.quadList[3] = new TexturedQuad(new PositionTextureVertex[] { positiontexturevertex1, positiontexturevertex2, positiontexturevertex6, positiontexturevertex5 }, textureX + p_i46301_9_ + p_i46301_7_, textureY + p_i46301_9_, textureX + p_i46301_9_ + p_i46301_7_ + p_i46301_7_, textureY, renderer.textureWidth, renderer.textureHeight);
/* 150 */     this.quadList[4] = new TexturedQuad(new PositionTextureVertex[] { positiontexturevertex, positiontexturevertex7, positiontexturevertex2, positiontexturevertex1 }, textureX + p_i46301_9_, textureY + p_i46301_9_, textureX + p_i46301_9_ + p_i46301_7_, textureY + p_i46301_9_ + p_i46301_8_, renderer.textureWidth, renderer.textureHeight);
/* 151 */     this.quadList[5] = new TexturedQuad(new PositionTextureVertex[] { positiontexturevertex3, positiontexturevertex4, positiontexturevertex5, positiontexturevertex6 }, textureX + p_i46301_9_ + p_i46301_7_ + p_i46301_9_, textureY + p_i46301_9_, textureX + p_i46301_9_ + p_i46301_7_ + p_i46301_9_ + p_i46301_7_, textureY + p_i46301_9_ + p_i46301_8_, renderer.textureWidth, renderer.textureHeight);
/*     */     
/* 153 */     if (p_i46301_11_)
/*     */     {
/* 155 */       for (int i = 0; i < this.quadList.length; i++)
/*     */       {
/* 157 */         this.quadList[i].flipFace();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(WorldRenderer renderer, float scale) {
/* 164 */     for (int i = 0; i < this.quadList.length; i++) {
/*     */       
/* 166 */       TexturedQuad texturedquad = this.quadList[i];
/*     */       
/* 168 */       if (texturedquad != null)
/*     */       {
/* 170 */         texturedquad.draw(renderer, scale);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelBox setBoxName(String name) {
/* 177 */     this.boxName = name;
/* 178 */     return this;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\model\ModelBox.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */