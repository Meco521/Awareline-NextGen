/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Map;
/*     */ import net.minecraft.block.material.MapColor;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.texture.DynamicTexture;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.Vec4b;
/*     */ import net.minecraft.world.storage.MapData;
/*     */ 
/*     */ 
/*     */ public class MapItemRenderer
/*     */ {
/*  19 */   static final ResourceLocation mapIcons = new ResourceLocation("textures/map/map_icons.png");
/*     */   final TextureManager textureManager;
/*  21 */   private final Map<String, Instance> loadedMaps = Maps.newHashMap();
/*     */ 
/*     */   
/*     */   public MapItemRenderer(TextureManager textureManagerIn) {
/*  25 */     this.textureManager = textureManagerIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateMapTexture(MapData mapdataIn) {
/*  33 */     getMapRendererInstance(mapdataIn).updateMapTexture();
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderMap(MapData mapdataIn, boolean p_148250_2_) {
/*  38 */     getMapRendererInstance(mapdataIn).render(p_148250_2_);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Instance getMapRendererInstance(MapData mapdataIn) {
/*  46 */     Instance mapitemrenderer$instance = this.loadedMaps.get(mapdataIn.mapName);
/*     */     
/*  48 */     if (mapitemrenderer$instance == null) {
/*     */       
/*  50 */       mapitemrenderer$instance = new Instance(mapdataIn);
/*  51 */       this.loadedMaps.put(mapdataIn.mapName, mapitemrenderer$instance);
/*     */     } 
/*     */     
/*  54 */     return mapitemrenderer$instance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearLoadedMaps() {
/*  62 */     for (Instance mapitemrenderer$instance : this.loadedMaps.values())
/*     */     {
/*  64 */       this.textureManager.deleteTexture(mapitemrenderer$instance.location);
/*     */     }
/*     */     
/*  67 */     this.loadedMaps.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   class Instance
/*     */   {
/*     */     private final MapData mapData;
/*     */     private final DynamicTexture mapTexture;
/*     */     final ResourceLocation location;
/*     */     private final int[] mapTextureData;
/*     */     
/*     */     Instance(MapData mapdataIn) {
/*  79 */       this.mapData = mapdataIn;
/*  80 */       this.mapTexture = new DynamicTexture(128, 128);
/*  81 */       this.mapTextureData = this.mapTexture.getTextureData();
/*  82 */       this.location = MapItemRenderer.this.textureManager.getDynamicTextureLocation("map/" + mapdataIn.mapName, this.mapTexture);
/*     */       
/*  84 */       for (int i = 0; i < this.mapTextureData.length; i++)
/*     */       {
/*  86 */         this.mapTextureData[i] = 0;
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     void updateMapTexture() {
/*  92 */       for (int i = 0; i < 16384; i++) {
/*     */         
/*  94 */         int j = this.mapData.colors[i] & 0xFF;
/*     */         
/*  96 */         if (j / 4 == 0) {
/*     */           
/*  98 */           this.mapTextureData[i] = ((i + i / 128 & 0x1) << 3) + 16 << 24;
/*     */         }
/*     */         else {
/*     */           
/* 102 */           this.mapTextureData[i] = MapColor.mapColorArray[j / 4].getMapColor(j & 0x3);
/*     */         } 
/*     */       } 
/*     */       
/* 106 */       this.mapTexture.updateDynamicTexture();
/*     */     }
/*     */ 
/*     */     
/*     */     void render(boolean noOverlayRendering) {
/* 111 */       int i = 0;
/* 112 */       int j = 0;
/* 113 */       Tessellator tessellator = Tessellator.getInstance();
/* 114 */       WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 115 */       float f = 0.0F;
/* 116 */       MapItemRenderer.this.textureManager.bindTexture(this.location);
/* 117 */       GlStateManager.enableBlend();
/* 118 */       GlStateManager.tryBlendFuncSeparate(1, 771, 0, 1);
/* 119 */       GlStateManager.disableAlpha();
/* 120 */       worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 121 */       worldrenderer.pos((i + f), ((j + 128) - f), -0.009999999776482582D).tex(0.0D, 1.0D).endVertex();
/* 122 */       worldrenderer.pos(((i + 128) - f), ((j + 128) - f), -0.009999999776482582D).tex(1.0D, 1.0D).endVertex();
/* 123 */       worldrenderer.pos(((i + 128) - f), (j + f), -0.009999999776482582D).tex(1.0D, 0.0D).endVertex();
/* 124 */       worldrenderer.pos((i + f), (j + f), -0.009999999776482582D).tex(0.0D, 0.0D).endVertex();
/* 125 */       tessellator.draw();
/* 126 */       GlStateManager.enableAlpha();
/* 127 */       GlStateManager.disableBlend();
/* 128 */       MapItemRenderer.this.textureManager.bindTexture(MapItemRenderer.mapIcons);
/* 129 */       int k = 0;
/*     */       
/* 131 */       for (Vec4b vec4b : this.mapData.mapDecorations.values()) {
/*     */         
/* 133 */         if (!noOverlayRendering || vec4b.func_176110_a() == 1) {
/*     */           
/* 135 */           GlStateManager.pushMatrix();
/* 136 */           GlStateManager.translate(i + vec4b.func_176112_b() / 2.0F + 64.0F, j + vec4b.func_176113_c() / 2.0F + 64.0F, -0.02F);
/* 137 */           GlStateManager.rotate((vec4b.func_176111_d() * 360) / 16.0F, 0.0F, 0.0F, 1.0F);
/* 138 */           GlStateManager.scale(4.0F, 4.0F, 3.0F);
/* 139 */           GlStateManager.translate(-0.125F, 0.125F, 0.0F);
/* 140 */           byte b0 = vec4b.func_176110_a();
/* 141 */           float f1 = (b0 % 4) / 4.0F;
/* 142 */           float f2 = (b0 / 4) / 4.0F;
/* 143 */           float f3 = (b0 % 4 + 1) / 4.0F;
/* 144 */           float f4 = (b0 / 4 + 1) / 4.0F;
/* 145 */           worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 146 */           float f5 = -0.001F;
/* 147 */           worldrenderer.pos(-1.0D, 1.0D, (k * -0.001F)).tex(f1, f2).endVertex();
/* 148 */           worldrenderer.pos(1.0D, 1.0D, (k * -0.001F)).tex(f3, f2).endVertex();
/* 149 */           worldrenderer.pos(1.0D, -1.0D, (k * -0.001F)).tex(f3, f4).endVertex();
/* 150 */           worldrenderer.pos(-1.0D, -1.0D, (k * -0.001F)).tex(f1, f4).endVertex();
/* 151 */           tessellator.draw();
/* 152 */           GlStateManager.popMatrix();
/* 153 */           k++;
/*     */         } 
/*     */       } 
/*     */       
/* 157 */       GlStateManager.pushMatrix();
/* 158 */       GlStateManager.translate(0.0F, 0.0F, -0.04F);
/* 159 */       GlStateManager.scale(1.0F, 1.0F, 1.0F);
/* 160 */       GlStateManager.popMatrix();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\gui\MapItemRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */