/*     */ package net.optifine;
/*     */ 
/*     */ import java.util.Properties;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ 
/*     */ public class CustomLoadingScreen
/*     */ {
/*     */   private final ResourceLocation locationTexture;
/*  15 */   private int scaleMode = 0;
/*  16 */   private int scale = 2;
/*     */   
/*     */   private final boolean center;
/*     */   private static final int SCALE_DEFAULT = 2;
/*     */   private static final int SCALE_MODE_FIXED = 0;
/*     */   private static final int SCALE_MODE_FULL = 1;
/*     */   private static final int SCALE_MODE_STRETCH = 2;
/*     */   
/*     */   public CustomLoadingScreen(ResourceLocation locationTexture, int scaleMode, int scale, boolean center) {
/*  25 */     this.locationTexture = locationTexture;
/*  26 */     this.scaleMode = scaleMode;
/*  27 */     this.scale = scale;
/*  28 */     this.center = center;
/*     */   }
/*     */ 
/*     */   
/*     */   public static CustomLoadingScreen parseScreen(String path, int dimId, Properties props) {
/*  33 */     ResourceLocation resourcelocation = new ResourceLocation(path);
/*  34 */     int i = parseScaleMode(getProperty("scaleMode", dimId, props));
/*  35 */     int j = (i == 0) ? 2 : 1;
/*  36 */     int k = parseScale(getProperty("scale", dimId, props), j);
/*  37 */     boolean flag = Config.parseBoolean(getProperty("center", dimId, props), false);
/*  38 */     CustomLoadingScreen customloadingscreen = new CustomLoadingScreen(resourcelocation, i, k, flag);
/*  39 */     return customloadingscreen;
/*     */   }
/*     */ 
/*     */   
/*     */   private static String getProperty(String key, int dim, Properties props) {
/*  44 */     if (props == null)
/*     */     {
/*  46 */       return null;
/*     */     }
/*     */ 
/*     */     
/*  50 */     String s = props.getProperty("dim" + dim + "." + key);
/*     */     
/*  52 */     if (s != null)
/*     */     {
/*  54 */       return s;
/*     */     }
/*     */ 
/*     */     
/*  58 */     s = props.getProperty(key);
/*  59 */     return s;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int parseScaleMode(String str) {
/*  66 */     if (str == null)
/*     */     {
/*  68 */       return 0;
/*     */     }
/*     */ 
/*     */     
/*  72 */     str = str.toLowerCase().trim();
/*     */     
/*  74 */     if (str.equals("fixed"))
/*     */     {
/*  76 */       return 0;
/*     */     }
/*  78 */     if (str.equals("full"))
/*     */     {
/*  80 */       return 1;
/*     */     }
/*  82 */     if (str.equals("stretch"))
/*     */     {
/*  84 */       return 2;
/*     */     }
/*     */ 
/*     */     
/*  88 */     CustomLoadingScreens.warn("Invalid scale mode: " + str);
/*  89 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int parseScale(String str, int def) {
/*  96 */     if (str == null)
/*     */     {
/*  98 */       return def;
/*     */     }
/*     */ 
/*     */     
/* 102 */     str = str.trim();
/* 103 */     int i = Config.parseInt(str, -1);
/*     */     
/* 105 */     if (i < 1) {
/*     */       
/* 107 */       CustomLoadingScreens.warn("Invalid scale: " + str);
/* 108 */       return def;
/*     */     } 
/*     */ 
/*     */     
/* 112 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawBackground(int width, int height) {
/* 119 */     GlStateManager.disableLighting();
/* 120 */     GlStateManager.disableFog();
/* 121 */     Tessellator tessellator = Tessellator.getInstance();
/* 122 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 123 */     Config.getTextureManager().bindTexture(this.locationTexture);
/* 124 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 125 */     double d0 = (16 * this.scale);
/* 126 */     double d1 = width / d0;
/* 127 */     double d2 = height / d0;
/* 128 */     double d3 = 0.0D;
/* 129 */     double d4 = 0.0D;
/*     */     
/* 131 */     if (this.center) {
/*     */       
/* 133 */       d3 = (d0 - width) / d0 * 2.0D;
/* 134 */       d4 = (d0 - height) / d0 * 2.0D;
/*     */     } 
/*     */     
/* 137 */     switch (this.scaleMode) {
/*     */       
/*     */       case 1:
/* 140 */         d0 = Math.max(width, height);
/* 141 */         d1 = (this.scale * width) / d0;
/* 142 */         d2 = (this.scale * height) / d0;
/*     */         
/* 144 */         if (this.center) {
/*     */           
/* 146 */           d3 = this.scale * (d0 - width) / d0 * 2.0D;
/* 147 */           d4 = this.scale * (d0 - height) / d0 * 2.0D;
/*     */         } 
/*     */         break;
/*     */ 
/*     */       
/*     */       case 2:
/* 153 */         d1 = this.scale;
/* 154 */         d2 = this.scale;
/* 155 */         d3 = 0.0D;
/* 156 */         d4 = 0.0D;
/*     */         break;
/*     */     } 
/* 159 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/* 160 */     worldrenderer.pos(0.0D, height, 0.0D).tex(d3, d4 + d2).color(255, 255, 255, 255).endVertex();
/* 161 */     worldrenderer.pos(width, height, 0.0D).tex(d3 + d1, d4 + d2).color(255, 255, 255, 255).endVertex();
/* 162 */     worldrenderer.pos(width, 0.0D, 0.0D).tex(d3 + d1, d4).color(255, 255, 255, 255).endVertex();
/* 163 */     worldrenderer.pos(0.0D, 0.0D, 0.0D).tex(d3, d4).color(255, 255, 255, 255).endVertex();
/* 164 */     tessellator.draw();
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\CustomLoadingScreen.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */