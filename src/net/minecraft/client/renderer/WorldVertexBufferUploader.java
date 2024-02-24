/*     */ package net.minecraft.client.renderer;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.renderer.vertex.VertexFormat;
/*     */ import net.minecraft.client.renderer.vertex.VertexFormatElement;
/*     */ import net.minecraft.src.Config;
/*     */ import net.optifine.reflect.Reflector;
/*     */ import net.optifine.shaders.SVertexBuilder;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ public class WorldVertexBufferUploader
/*     */ {
/*     */   public void draw(WorldRenderer p_181679_1_) {
/*  16 */     if (p_181679_1_.getVertexCount() > 0) {
/*  17 */       if (p_181679_1_.getDrawMode() == 7 && Config.isQuadsToTriangles()) {
/*  18 */         p_181679_1_.quadsToTriangles();
/*     */       }
/*     */       
/*  21 */       VertexFormat vertexformat = p_181679_1_.getVertexFormat();
/*  22 */       int i = vertexformat.getNextOffset();
/*  23 */       ByteBuffer bytebuffer = p_181679_1_.getByteBuffer();
/*  24 */       List<VertexFormatElement> list = vertexformat.getElements();
/*  25 */       boolean flag = Reflector.ForgeVertexFormatElementEnumUseage_preDraw.exists();
/*  26 */       boolean flag1 = Reflector.ForgeVertexFormatElementEnumUseage_postDraw.exists();
/*     */       
/*  28 */       for (int j = 0; j < list.size(); j++) {
/*  29 */         VertexFormatElement vertexformatelement = list.get(j);
/*  30 */         VertexFormatElement.EnumUsage vertexformatelement$enumusage = vertexformatelement.getUsage();
/*     */         
/*  32 */         if (flag) {
/*  33 */           Reflector.callVoid(vertexformatelement$enumusage, Reflector.ForgeVertexFormatElementEnumUseage_preDraw, new Object[] { vertexformat, Integer.valueOf(j), Integer.valueOf(i), bytebuffer });
/*     */         } else {
/*  35 */           int k = vertexformatelement.getType().getGlConstant();
/*  36 */           int l = vertexformatelement.getIndex();
/*  37 */           bytebuffer.position(vertexformat.getOffset(j));
/*     */           
/*  39 */           switch (vertexformatelement$enumusage) {
/*     */             case POSITION:
/*  41 */               GL11.glVertexPointer(vertexformatelement.getElementCount(), k, i, bytebuffer);
/*  42 */               GL11.glEnableClientState(32884);
/*     */               break;
/*     */             
/*     */             case UV:
/*  46 */               OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit + l);
/*  47 */               GL11.glTexCoordPointer(vertexformatelement.getElementCount(), k, i, bytebuffer);
/*  48 */               GL11.glEnableClientState(32888);
/*  49 */               OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
/*     */               break;
/*     */             
/*     */             case COLOR:
/*  53 */               GL11.glColorPointer(vertexformatelement.getElementCount(), k, i, bytebuffer);
/*  54 */               GL11.glEnableClientState(32886);
/*     */               break;
/*     */             
/*     */             case NORMAL:
/*  58 */               GL11.glNormalPointer(k, i, bytebuffer);
/*  59 */               GL11.glEnableClientState(32885);
/*     */               break;
/*     */           } 
/*     */         } 
/*     */       } 
/*  64 */       if (p_181679_1_.isMultiTexture()) {
/*  65 */         p_181679_1_.drawMultiTexture();
/*  66 */       } else if (Config.isShaders()) {
/*  67 */         SVertexBuilder.drawArrays(p_181679_1_.getDrawMode(), 0, p_181679_1_.getVertexCount(), p_181679_1_);
/*     */       } else {
/*  69 */         GL11.glDrawArrays(p_181679_1_.getDrawMode(), 0, p_181679_1_.getVertexCount());
/*     */       } 
/*     */       
/*  72 */       int j1 = 0;
/*     */       
/*  74 */       for (int k1 = list.size(); j1 < k1; j1++) {
/*  75 */         VertexFormatElement vertexformatelement1 = list.get(j1);
/*  76 */         VertexFormatElement.EnumUsage vertexformatelement$enumusage1 = vertexformatelement1.getUsage();
/*     */         
/*  78 */         if (flag1) {
/*  79 */           Reflector.callVoid(vertexformatelement$enumusage1, Reflector.ForgeVertexFormatElementEnumUseage_postDraw, new Object[] { vertexformat, Integer.valueOf(j1), Integer.valueOf(i), bytebuffer });
/*     */         } else {
/*  81 */           int i1 = vertexformatelement1.getIndex();
/*     */           
/*  83 */           switch (vertexformatelement$enumusage1) {
/*     */             case POSITION:
/*  85 */               GL11.glDisableClientState(32884);
/*     */               break;
/*     */             
/*     */             case UV:
/*  89 */               OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit + i1);
/*  90 */               GL11.glDisableClientState(32888);
/*  91 */               OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
/*     */               break;
/*     */             
/*     */             case COLOR:
/*  95 */               GL11.glDisableClientState(32886);
/*  96 */               GlStateManager.resetColor();
/*     */               break;
/*     */             
/*     */             case NORMAL:
/* 100 */               GL11.glDisableClientState(32885);
/*     */               break;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 106 */     p_181679_1_.reset();
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\WorldVertexBufferUploader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */