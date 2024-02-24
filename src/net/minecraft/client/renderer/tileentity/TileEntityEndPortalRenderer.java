/*     */ package net.minecraft.client.renderer.tileentity;
/*     */ import java.nio.FloatBuffer;
/*     */ import java.util.Random;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.ActiveRenderInfo;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityEndPortal;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.optifine.shaders.ShadersRender;
/*     */ 
/*     */ public class TileEntityEndPortalRenderer extends TileEntitySpecialRenderer<TileEntityEndPortal> {
/*  16 */   private static final ResourceLocation END_SKY_TEXTURE = new ResourceLocation("textures/environment/end_sky.png");
/*  17 */   private static final ResourceLocation END_PORTAL_TEXTURE = new ResourceLocation("textures/entity/end_portal.png");
/*  18 */   private static final Random field_147527_e = new Random(31100L);
/*  19 */   FloatBuffer field_147528_b = GLAllocation.createDirectFloatBuffer(16);
/*     */ 
/*     */   
/*     */   public void renderTileEntityAt(TileEntityEndPortal te, double x, double y, double z, float partialTicks, int destroyStage) {
/*  23 */     if (!Config.isShaders() || !ShadersRender.renderEndPortal(te, x, y, z, partialTicks, destroyStage, 0.75F)) {
/*     */       
/*  25 */       float f = (float)this.rendererDispatcher.entityX;
/*  26 */       float f1 = (float)this.rendererDispatcher.entityY;
/*  27 */       float f2 = (float)this.rendererDispatcher.entityZ;
/*  28 */       GlStateManager.disableLighting();
/*  29 */       field_147527_e.setSeed(31100L);
/*  30 */       float f3 = 0.75F;
/*     */       
/*  32 */       for (int i = 0; i < 16; i++) {
/*     */         
/*  34 */         GlStateManager.pushMatrix();
/*  35 */         float f4 = (16 - i);
/*  36 */         float f5 = 0.0625F;
/*  37 */         float f6 = 1.0F / (f4 + 1.0F);
/*     */         
/*  39 */         if (i == 0) {
/*     */           
/*  41 */           bindTexture(END_SKY_TEXTURE);
/*  42 */           f6 = 0.1F;
/*  43 */           f4 = 65.0F;
/*  44 */           f5 = 0.125F;
/*  45 */           GlStateManager.enableBlend();
/*  46 */           GlStateManager.blendFunc(770, 771);
/*     */         } 
/*     */         
/*  49 */         if (i >= 1)
/*     */         {
/*  51 */           bindTexture(END_PORTAL_TEXTURE);
/*     */         }
/*     */         
/*  54 */         if (i == 1) {
/*     */           
/*  56 */           GlStateManager.enableBlend();
/*  57 */           GlStateManager.blendFunc(1, 1);
/*  58 */           f5 = 0.5F;
/*     */         } 
/*     */         
/*  61 */         float f7 = (float)-(y + f3);
/*  62 */         float f8 = f7 + (float)(ActiveRenderInfo.getPosition()).yCoord;
/*  63 */         float f9 = f7 + f4 + (float)(ActiveRenderInfo.getPosition()).yCoord;
/*  64 */         float f10 = f8 / f9;
/*  65 */         f10 = (float)(y + f3) + f10;
/*  66 */         GlStateManager.translate(f, f10, f2);
/*  67 */         GlStateManager.texGen(GlStateManager.TexGen.S, 9217);
/*  68 */         GlStateManager.texGen(GlStateManager.TexGen.T, 9217);
/*  69 */         GlStateManager.texGen(GlStateManager.TexGen.R, 9217);
/*  70 */         GlStateManager.texGen(GlStateManager.TexGen.Q, 9216);
/*  71 */         GlStateManager.texGen(GlStateManager.TexGen.S, 9473, func_147525_a(1.0F, 0.0F, 0.0F, 0.0F));
/*  72 */         GlStateManager.texGen(GlStateManager.TexGen.T, 9473, func_147525_a(0.0F, 0.0F, 1.0F, 0.0F));
/*  73 */         GlStateManager.texGen(GlStateManager.TexGen.R, 9473, func_147525_a(0.0F, 0.0F, 0.0F, 1.0F));
/*  74 */         GlStateManager.texGen(GlStateManager.TexGen.Q, 9474, func_147525_a(0.0F, 1.0F, 0.0F, 0.0F));
/*  75 */         GlStateManager.enableTexGenCoord(GlStateManager.TexGen.S);
/*  76 */         GlStateManager.enableTexGenCoord(GlStateManager.TexGen.T);
/*  77 */         GlStateManager.enableTexGenCoord(GlStateManager.TexGen.R);
/*  78 */         GlStateManager.enableTexGenCoord(GlStateManager.TexGen.Q);
/*  79 */         GlStateManager.popMatrix();
/*  80 */         GlStateManager.matrixMode(5890);
/*  81 */         GlStateManager.pushMatrix();
/*  82 */         GlStateManager.loadIdentity();
/*  83 */         GlStateManager.translate(0.0F, (float)(Minecraft.getSystemTime() % 700000L) / 700000.0F, 0.0F);
/*  84 */         GlStateManager.scale(f5, f5, f5);
/*  85 */         GlStateManager.translate(0.5F, 0.5F, 0.0F);
/*  86 */         GlStateManager.rotate((i * i * 4321 + i * 9) * 2.0F, 0.0F, 0.0F, 1.0F);
/*  87 */         GlStateManager.translate(-0.5F, -0.5F, 0.0F);
/*  88 */         GlStateManager.translate(-f, -f2, -f1);
/*  89 */         f8 = f7 + (float)(ActiveRenderInfo.getPosition()).yCoord;
/*  90 */         GlStateManager.translate((float)(ActiveRenderInfo.getPosition()).xCoord * f4 / f8, (float)(ActiveRenderInfo.getPosition()).zCoord * f4 / f8, -f1);
/*  91 */         Tessellator tessellator = Tessellator.getInstance();
/*  92 */         WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/*  93 */         worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
/*  94 */         float f11 = (field_147527_e.nextFloat() * 0.5F + 0.1F) * f6;
/*  95 */         float f12 = (field_147527_e.nextFloat() * 0.5F + 0.4F) * f6;
/*  96 */         float f13 = (field_147527_e.nextFloat() * 0.5F + 0.5F) * f6;
/*     */         
/*  98 */         if (i == 0)
/*     */         {
/* 100 */           f11 = f12 = f13 = f6;
/*     */         }
/*     */         
/* 103 */         worldrenderer.pos(x, y + f3, z).color(f11, f12, f13, 1.0F).endVertex();
/* 104 */         worldrenderer.pos(x, y + f3, z + 1.0D).color(f11, f12, f13, 1.0F).endVertex();
/* 105 */         worldrenderer.pos(x + 1.0D, y + f3, z + 1.0D).color(f11, f12, f13, 1.0F).endVertex();
/* 106 */         worldrenderer.pos(x + 1.0D, y + f3, z).color(f11, f12, f13, 1.0F).endVertex();
/* 107 */         tessellator.draw();
/* 108 */         GlStateManager.popMatrix();
/* 109 */         GlStateManager.matrixMode(5888);
/* 110 */         bindTexture(END_SKY_TEXTURE);
/*     */       } 
/*     */       
/* 113 */       GlStateManager.disableBlend();
/* 114 */       GlStateManager.disableTexGenCoord(GlStateManager.TexGen.S);
/* 115 */       GlStateManager.disableTexGenCoord(GlStateManager.TexGen.T);
/* 116 */       GlStateManager.disableTexGenCoord(GlStateManager.TexGen.R);
/* 117 */       GlStateManager.disableTexGenCoord(GlStateManager.TexGen.Q);
/* 118 */       GlStateManager.enableLighting();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private FloatBuffer func_147525_a(float p_147525_1_, float p_147525_2_, float p_147525_3_, float p_147525_4_) {
/* 124 */     this.field_147528_b.clear();
/* 125 */     this.field_147528_b.put(p_147525_1_).put(p_147525_2_).put(p_147525_3_).put(p_147525_4_);
/* 126 */     this.field_147528_b.flip();
/* 127 */     return this.field_147528_b;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\tileentity\TileEntityEndPortalRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */