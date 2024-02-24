/*     */ package net.optifine.render;
/*     */ 
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.src.Config;
/*     */ 
/*     */ 
/*     */ public class Blender
/*     */ {
/*     */   public static final int BLEND_ALPHA = 0;
/*     */   public static final int BLEND_ADD = 1;
/*     */   public static final int BLEND_SUBSTRACT = 2;
/*     */   public static final int BLEND_MULTIPLY = 3;
/*     */   public static final int BLEND_DODGE = 4;
/*     */   public static final int BLEND_BURN = 5;
/*     */   public static final int BLEND_SCREEN = 6;
/*     */   public static final int BLEND_OVERLAY = 7;
/*     */   public static final int BLEND_REPLACE = 8;
/*     */   public static final int BLEND_DEFAULT = 1;
/*     */   
/*     */   public static int parseBlend(String str) {
/*  21 */     if (str == null)
/*     */     {
/*  23 */       return 1;
/*     */     }
/*     */ 
/*     */     
/*  27 */     str = str.toLowerCase().trim();
/*     */     
/*  29 */     if (str.equals("alpha"))
/*     */     {
/*  31 */       return 0;
/*     */     }
/*  33 */     if (str.equals("add"))
/*     */     {
/*  35 */       return 1;
/*     */     }
/*  37 */     if (str.equals("subtract"))
/*     */     {
/*  39 */       return 2;
/*     */     }
/*  41 */     if (str.equals("multiply"))
/*     */     {
/*  43 */       return 3;
/*     */     }
/*  45 */     if (str.equals("dodge"))
/*     */     {
/*  47 */       return 4;
/*     */     }
/*  49 */     if (str.equals("burn"))
/*     */     {
/*  51 */       return 5;
/*     */     }
/*  53 */     if (str.equals("screen"))
/*     */     {
/*  55 */       return 6;
/*     */     }
/*  57 */     if (str.equals("overlay"))
/*     */     {
/*  59 */       return 7;
/*     */     }
/*  61 */     if (str.equals("replace"))
/*     */     {
/*  63 */       return 8;
/*     */     }
/*     */ 
/*     */     
/*  67 */     Config.warn("Unknown blend: " + str);
/*  68 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setupBlend(int blend, float brightness) {
/*  75 */     switch (blend) {
/*     */       
/*     */       case 0:
/*  78 */         GlStateManager.disableAlpha();
/*  79 */         GlStateManager.enableBlend();
/*  80 */         GlStateManager.blendFunc(770, 771);
/*  81 */         GlStateManager.color(1.0F, 1.0F, 1.0F, brightness);
/*     */         break;
/*     */       
/*     */       case 1:
/*  85 */         GlStateManager.disableAlpha();
/*  86 */         GlStateManager.enableBlend();
/*  87 */         GlStateManager.blendFunc(770, 1);
/*  88 */         GlStateManager.color(1.0F, 1.0F, 1.0F, brightness);
/*     */         break;
/*     */       
/*     */       case 2:
/*  92 */         GlStateManager.disableAlpha();
/*  93 */         GlStateManager.enableBlend();
/*  94 */         GlStateManager.blendFunc(775, 0);
/*  95 */         GlStateManager.color(brightness, brightness, brightness, 1.0F);
/*     */         break;
/*     */       
/*     */       case 3:
/*  99 */         GlStateManager.disableAlpha();
/* 100 */         GlStateManager.enableBlend();
/* 101 */         GlStateManager.blendFunc(774, 771);
/* 102 */         GlStateManager.color(brightness, brightness, brightness, brightness);
/*     */         break;
/*     */       
/*     */       case 4:
/* 106 */         GlStateManager.disableAlpha();
/* 107 */         GlStateManager.enableBlend();
/* 108 */         GlStateManager.blendFunc(1, 1);
/* 109 */         GlStateManager.color(brightness, brightness, brightness, 1.0F);
/*     */         break;
/*     */       
/*     */       case 5:
/* 113 */         GlStateManager.disableAlpha();
/* 114 */         GlStateManager.enableBlend();
/* 115 */         GlStateManager.blendFunc(0, 769);
/* 116 */         GlStateManager.color(brightness, brightness, brightness, 1.0F);
/*     */         break;
/*     */       
/*     */       case 6:
/* 120 */         GlStateManager.disableAlpha();
/* 121 */         GlStateManager.enableBlend();
/* 122 */         GlStateManager.blendFunc(1, 769);
/* 123 */         GlStateManager.color(brightness, brightness, brightness, 1.0F);
/*     */         break;
/*     */       
/*     */       case 7:
/* 127 */         GlStateManager.disableAlpha();
/* 128 */         GlStateManager.enableBlend();
/* 129 */         GlStateManager.blendFunc(774, 768);
/* 130 */         GlStateManager.color(brightness, brightness, brightness, 1.0F);
/*     */         break;
/*     */       
/*     */       case 8:
/* 134 */         GlStateManager.enableAlpha();
/* 135 */         GlStateManager.disableBlend();
/* 136 */         GlStateManager.color(1.0F, 1.0F, 1.0F, brightness);
/*     */         break;
/*     */     } 
/* 139 */     GlStateManager.enableTexture2D();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void clearBlend(float rainBrightness) {
/* 144 */     GlStateManager.disableAlpha();
/* 145 */     GlStateManager.enableBlend();
/* 146 */     GlStateManager.blendFunc(770, 1);
/* 147 */     GlStateManager.color(1.0F, 1.0F, 1.0F, rainBrightness);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\render\Blender.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */