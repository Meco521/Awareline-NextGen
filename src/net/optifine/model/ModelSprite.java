/*     */ package net.optifine.model;
/*     */ 
/*     */ import net.minecraft.client.model.ModelRenderer;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class ModelSprite
/*     */ {
/*  13 */   private ModelRenderer modelRenderer = null;
/*  14 */   private int textureOffsetX = 0;
/*  15 */   private int textureOffsetY = 0;
/*  16 */   private float posX = 0.0F;
/*  17 */   private float posY = 0.0F;
/*  18 */   private float posZ = 0.0F;
/*  19 */   private int sizeX = 0;
/*  20 */   private int sizeY = 0;
/*  21 */   private int sizeZ = 0;
/*  22 */   private float sizeAdd = 0.0F;
/*  23 */   private float minU = 0.0F;
/*  24 */   private float minV = 0.0F;
/*  25 */   private float maxU = 0.0F;
/*  26 */   private float maxV = 0.0F;
/*     */ 
/*     */   
/*     */   public ModelSprite(ModelRenderer modelRenderer, int textureOffsetX, int textureOffsetY, float posX, float posY, float posZ, int sizeX, int sizeY, int sizeZ, float sizeAdd) {
/*  30 */     this.modelRenderer = modelRenderer;
/*  31 */     this.textureOffsetX = textureOffsetX;
/*  32 */     this.textureOffsetY = textureOffsetY;
/*  33 */     this.posX = posX;
/*  34 */     this.posY = posY;
/*  35 */     this.posZ = posZ;
/*  36 */     this.sizeX = sizeX;
/*  37 */     this.sizeY = sizeY;
/*  38 */     this.sizeZ = sizeZ;
/*  39 */     this.sizeAdd = sizeAdd;
/*  40 */     this.minU = textureOffsetX / modelRenderer.textureWidth;
/*  41 */     this.minV = textureOffsetY / modelRenderer.textureHeight;
/*  42 */     this.maxU = (textureOffsetX + sizeX) / modelRenderer.textureWidth;
/*  43 */     this.maxV = (textureOffsetY + sizeY) / modelRenderer.textureHeight;
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(Tessellator tessellator, float scale) {
/*  48 */     GlStateManager.translate(this.posX * scale, this.posY * scale, this.posZ * scale);
/*  49 */     float f = this.minU;
/*  50 */     float f1 = this.maxU;
/*  51 */     float f2 = this.minV;
/*  52 */     float f3 = this.maxV;
/*     */     
/*  54 */     if (this.modelRenderer.mirror) {
/*     */       
/*  56 */       f = this.maxU;
/*  57 */       f1 = this.minU;
/*     */     } 
/*     */     
/*  60 */     if (this.modelRenderer.mirrorV) {
/*     */       
/*  62 */       f2 = this.maxV;
/*  63 */       f3 = this.minV;
/*     */     } 
/*     */     
/*  66 */     renderItemIn2D(tessellator, f, f2, f1, f3, this.sizeX, this.sizeY, scale * this.sizeZ, this.modelRenderer.textureWidth, this.modelRenderer.textureHeight);
/*  67 */     GlStateManager.translate(-this.posX * scale, -this.posY * scale, -this.posZ * scale);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void renderItemIn2D(Tessellator tess, float minU, float minV, float maxU, float maxV, int sizeX, int sizeY, float width, float texWidth, float texHeight) {
/*  72 */     if (width < 6.25E-4F)
/*     */     {
/*  74 */       width = 6.25E-4F;
/*     */     }
/*     */     
/*  77 */     float f = maxU - minU;
/*  78 */     float f1 = maxV - minV;
/*  79 */     double d0 = (MathHelper.abs(f) * texWidth / 16.0F);
/*  80 */     double d1 = (MathHelper.abs(f1) * texHeight / 16.0F);
/*  81 */     WorldRenderer worldrenderer = tess.getWorldRenderer();
/*  82 */     GL11.glNormal3f(0.0F, 0.0F, -1.0F);
/*  83 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/*  84 */     worldrenderer.pos(0.0D, d1, 0.0D).tex(minU, maxV).endVertex();
/*  85 */     worldrenderer.pos(d0, d1, 0.0D).tex(maxU, maxV).endVertex();
/*  86 */     worldrenderer.pos(d0, 0.0D, 0.0D).tex(maxU, minV).endVertex();
/*  87 */     worldrenderer.pos(0.0D, 0.0D, 0.0D).tex(minU, minV).endVertex();
/*  88 */     tess.draw();
/*  89 */     GL11.glNormal3f(0.0F, 0.0F, 1.0F);
/*  90 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/*  91 */     worldrenderer.pos(0.0D, 0.0D, width).tex(minU, minV).endVertex();
/*  92 */     worldrenderer.pos(d0, 0.0D, width).tex(maxU, minV).endVertex();
/*  93 */     worldrenderer.pos(d0, d1, width).tex(maxU, maxV).endVertex();
/*  94 */     worldrenderer.pos(0.0D, d1, width).tex(minU, maxV).endVertex();
/*  95 */     tess.draw();
/*  96 */     float f2 = 0.5F * f / sizeX;
/*  97 */     float f3 = 0.5F * f1 / sizeY;
/*  98 */     GL11.glNormal3f(-1.0F, 0.0F, 0.0F);
/*  99 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/*     */     
/* 101 */     for (int i = 0; i < sizeX; i++) {
/*     */       
/* 103 */       float f4 = i / sizeX;
/* 104 */       float f5 = minU + f * f4 + f2;
/* 105 */       worldrenderer.pos(f4 * d0, d1, width).tex(f5, maxV).endVertex();
/* 106 */       worldrenderer.pos(f4 * d0, d1, 0.0D).tex(f5, maxV).endVertex();
/* 107 */       worldrenderer.pos(f4 * d0, 0.0D, 0.0D).tex(f5, minV).endVertex();
/* 108 */       worldrenderer.pos(f4 * d0, 0.0D, width).tex(f5, minV).endVertex();
/*     */     } 
/*     */     
/* 111 */     tess.draw();
/* 112 */     GL11.glNormal3f(1.0F, 0.0F, 0.0F);
/* 113 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/*     */     
/* 115 */     for (int j = 0; j < sizeX; j++) {
/*     */       
/* 117 */       float f7 = j / sizeX;
/* 118 */       float f10 = minU + f * f7 + f2;
/* 119 */       float f6 = f7 + 1.0F / sizeX;
/* 120 */       worldrenderer.pos(f6 * d0, 0.0D, width).tex(f10, minV).endVertex();
/* 121 */       worldrenderer.pos(f6 * d0, 0.0D, 0.0D).tex(f10, minV).endVertex();
/* 122 */       worldrenderer.pos(f6 * d0, d1, 0.0D).tex(f10, maxV).endVertex();
/* 123 */       worldrenderer.pos(f6 * d0, d1, width).tex(f10, maxV).endVertex();
/*     */     } 
/*     */     
/* 126 */     tess.draw();
/* 127 */     GL11.glNormal3f(0.0F, 1.0F, 0.0F);
/* 128 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/*     */     
/* 130 */     for (int k = 0; k < sizeY; k++) {
/*     */       
/* 132 */       float f8 = k / sizeY;
/* 133 */       float f11 = minV + f1 * f8 + f3;
/* 134 */       float f13 = f8 + 1.0F / sizeY;
/* 135 */       worldrenderer.pos(0.0D, f13 * d1, width).tex(minU, f11).endVertex();
/* 136 */       worldrenderer.pos(d0, f13 * d1, width).tex(maxU, f11).endVertex();
/* 137 */       worldrenderer.pos(d0, f13 * d1, 0.0D).tex(maxU, f11).endVertex();
/* 138 */       worldrenderer.pos(0.0D, f13 * d1, 0.0D).tex(minU, f11).endVertex();
/*     */     } 
/*     */     
/* 141 */     tess.draw();
/* 142 */     GL11.glNormal3f(0.0F, -1.0F, 0.0F);
/* 143 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/*     */     
/* 145 */     for (int l = 0; l < sizeY; l++) {
/*     */       
/* 147 */       float f9 = l / sizeY;
/* 148 */       float f12 = minV + f1 * f9 + f3;
/* 149 */       worldrenderer.pos(d0, f9 * d1, width).tex(maxU, f12).endVertex();
/* 150 */       worldrenderer.pos(0.0D, f9 * d1, width).tex(minU, f12).endVertex();
/* 151 */       worldrenderer.pos(0.0D, f9 * d1, 0.0D).tex(minU, f12).endVertex();
/* 152 */       worldrenderer.pos(d0, f9 * d1, 0.0D).tex(maxU, f12).endVertex();
/*     */     } 
/*     */     
/* 155 */     tess.draw();
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\model\ModelSprite.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */