/*     */ package awareline.main.mod.implement.visual.sucks.CompassUtil;
/*     */ 
/*     */ import awareline.main.Client;
/*     */ import awareline.main.ui.animations.AnimationUtil;
/*     */ import awareline.main.ui.font.cfont.CFontRenderer;
/*     */ import awareline.main.ui.gui.clickgui.RenderUtil;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.awt.Color;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AwarelineCompass
/*     */ {
/*  18 */   public static final List<Degree> degrees = Lists.newArrayList();
/*  19 */   public final float width = 205.0F;
/*  20 */   public final float height = 52.0F;
/*  21 */   final CFontRenderer font = Client.instance.FontLoaders.regular20;
/*     */   public float offX;
/*     */   public float offY;
/*     */   private float animY;
/*     */   
/*     */   public AwarelineCompass() {
/*  27 */     degrees.add(new Degree("N", 1));
/*  28 */     degrees.add(new Degree("195", 2));
/*  29 */     degrees.add(new Degree("210", 2));
/*  30 */     degrees.add(new Degree("NE", 3));
/*  31 */     degrees.add(new Degree("240", 2));
/*  32 */     degrees.add(new Degree("255", 2));
/*  33 */     degrees.add(new Degree("E", 1));
/*  34 */     degrees.add(new Degree("285", 2));
/*  35 */     degrees.add(new Degree("300", 2));
/*  36 */     degrees.add(new Degree("SE", 3));
/*  37 */     degrees.add(new Degree("330", 2));
/*  38 */     degrees.add(new Degree("345", 2));
/*  39 */     degrees.add(new Degree("S", 1));
/*  40 */     degrees.add(new Degree("15", 2));
/*  41 */     degrees.add(new Degree("30", 2));
/*  42 */     degrees.add(new Degree("SW", 3));
/*  43 */     degrees.add(new Degree("60", 2));
/*  44 */     degrees.add(new Degree("75", 2));
/*  45 */     degrees.add(new Degree("W", 1));
/*  46 */     degrees.add(new Degree("105", 2));
/*  47 */     degrees.add(new Degree("120", 2));
/*  48 */     degrees.add(new Degree("NW", 3));
/*  49 */     degrees.add(new Degree("150", 2));
/*  50 */     degrees.add(new Degree("165", 2));
/*     */   }
/*     */   
/*     */   public void draw(ScaledResolution sr, boolean drawLine) {
/*  54 */     this.animY = AnimationUtil.moveUD(this.animY, 8.0F);
/*  55 */     float globalY = 22.0F - this.animY;
/*     */     
/*  57 */     preRender();
/*  58 */     float center = sr.getScaledWidth() / 2.0F;
/*  59 */     int count = 0;
/*  60 */     float yaaahhrewindTime = (Minecraft.getMinecraft()).thePlayer.rotationYaw % 360.0F * 2.0F + 1080.0F;
/*     */     
/*  62 */     float middle = sr.getScaledWidth();
/*  63 */     float centerX = middle / 2.0F + this.offX;
/*     */     
/*  65 */     RenderUtil.startGlScissor(sr.getScaledWidth() / 2 - 100, (int)(25.0F - globalY), 200, (int)(25.0F + globalY));
/*  66 */     if (drawLine) {
/*  67 */       RenderUtil.drawRect(centerX - 102.5F, this.offY + 21.0F - globalY, centerX + 102.5F, this.offY + 26.0F - 4.0F - globalY, Client.instance.getClientColor(255));
/*     */     }
/*  69 */     RenderUtil.drawRect(centerX - 102.5F, this.offY + 22.0F - globalY, centerX + 102.5F, this.offY + 52.0F - globalY, -1442840576);
/*  70 */     for (Degree d : degrees) {
/*  71 */       RenderUtil.drawRect(0.0F, 0.0F, 0.0F, 0.0F, 0);
/*  72 */       float location = center + (count * 30) - yaaahhrewindTime;
/*  73 */       float completeLocation = location - (this.font.getStringWidth(d.text) / 2);
/*  74 */       int opacity = opacity(sr, completeLocation);
/*  75 */       if (d.type == 1 && opacity != 16777215) {
/*  76 */         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*     */         
/*  78 */         this.font.drawString(d.text, completeLocation, 25.0F - globalY, opacity(sr, completeLocation));
/*     */       } 
/*  80 */       if (d.type == 2 && opacity != 16777215) {
/*  81 */         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  82 */         RenderUtil.drawRect(location - 0.5F, 29.0F - globalY, location + 0.5F, 34.0F - globalY, opacity(sr, completeLocation));
/*  83 */         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  84 */         this.font.drawString(d.text, completeLocation, 37.5F - globalY, opacity(sr, completeLocation));
/*     */       } 
/*  86 */       if (d.type == 3 && opacity != 16777215) {
/*  87 */         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  88 */         this.font.drawString(d.text, completeLocation, 25.0F - globalY + this.font.getHeight() / 2.0F - this.font.getHeight() / 2.0F, opacity(sr, completeLocation));
/*     */       } 
/*  90 */       count++;
/*     */     } 
/*  92 */     for (Degree d : degrees) {
/*  93 */       RenderUtil.drawRect(0.0F, 0.0F, 0.0F, 0.0F, 0);
/*  94 */       float location = center + (count * 30) - yaaahhrewindTime;
/*  95 */       float completeLocation = location - (this.font.getStringWidth(d.text) / 2);
/*  96 */       if (d.type == 1) {
/*  97 */         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  98 */         this.font.drawString(d.text, completeLocation, 25.0F - globalY, opacity(sr, completeLocation));
/*     */       } 
/* 100 */       if (d.type == 2) {
/* 101 */         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 102 */         RenderUtil.drawRect(location - 0.5F, 29.0F - globalY, location + 0.5F, 34.0F - globalY, opacity(sr, completeLocation));
/* 103 */         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 104 */         this.font.drawString(d.text, completeLocation, 37.5F - globalY, opacity(sr, completeLocation));
/*     */       } 
/* 106 */       if (d.type == 3) {
/* 107 */         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 108 */         this.font.drawString(d.text, completeLocation, 25.0F - globalY + this.font.getHeight() / 2.0F - (this.font.getHeight() / 2), opacity(sr, completeLocation));
/*     */       } 
/* 110 */       count++;
/*     */     } 
/* 112 */     for (Degree d : degrees) {
/* 113 */       RenderUtil.drawRect(0.0F, 0.0F, 0.0F, 0.0F, 0);
/* 114 */       float location = center + (count * 30) - yaaahhrewindTime;
/* 115 */       float completeLocation = location - (this.font.getStringWidth(d.text) / 2);
/* 116 */       if (d.type == 1) {
/* 117 */         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 118 */         this.font.drawString(d.text, completeLocation, 25.0F - globalY, opacity(sr, completeLocation));
/*     */       } 
/* 120 */       if (d.type == 2) {
/* 121 */         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 122 */         RenderUtil.drawRect(location - 0.5F, 29.0F - globalY, location + 0.5F, 34.0F - globalY, opacity(sr, completeLocation));
/* 123 */         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 124 */         this.font.drawString(d.text, completeLocation, 37.5F - globalY, opacity(sr, completeLocation));
/*     */       } 
/* 126 */       if (d.type == 3) {
/* 127 */         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 128 */         this.font.drawString(d.text, completeLocation, 25.0F - globalY + this.font.getHeight() / 2.0F - this.font.getHeight() / 2.0F, opacity(sr, completeLocation));
/*     */       } 
/* 130 */       count++;
/*     */     } 
/* 132 */     RenderUtil.stopGlScissor();
/*     */   }
/*     */   public void drawBloom(ScaledResolution sr, boolean drawLine) {
/* 135 */     this.animY = AnimationUtil.moveUD(this.animY, 8.0F);
/* 136 */     float globalY = 22.0F - this.animY;
/*     */     
/* 138 */     preRender();
/*     */     
/* 140 */     float middle = sr.getScaledWidth();
/* 141 */     float centerX = middle / 2.0F + this.offX;
/*     */     
/* 143 */     RenderUtil.startGlScissor(sr.getScaledWidth() / 2 - 100, (int)(25.0F - globalY), 200, (int)(25.0F + globalY));
/* 144 */     if (drawLine) {
/* 145 */       RenderUtil.drawRect(centerX - 102.5F, this.offY + 21.0F - globalY, centerX + 102.5F, this.offY + 26.0F - 4.0F - globalY, Client.instance
/* 146 */           .getClientColor(255));
/*     */     }
/* 148 */     RenderUtil.drawRect(centerX - 102.5F, this.offY + 22.0F - globalY, centerX + 102.5F, this.offY + 52.0F - globalY, Client.instance.getClientColor(255));
/* 149 */     RenderUtil.stopGlScissor();
/*     */   }
/*     */   
/*     */   public void preRender() {
/* 153 */     GlStateManager.disableAlpha();
/* 154 */     GlStateManager.enableBlend();
/*     */   }
/*     */   
/*     */   public int opacity(ScaledResolution sr, float offset) {
/* 158 */     float offs = 255.0F - Math.abs(sr.getScaledWidth() / 2.0F - offset) * 1.8F;
/* 159 */     Color c = new Color(255, 255, 255, (int)Math.min(Math.max(0.0F, offs), 255.0F));
/* 160 */     return c.getRGB();
/*     */   }
/*     */   
/*     */   public class Degree {
/*     */     public final String text;
/*     */     public final int type;
/*     */     
/*     */     public Degree(String s, int t) {
/* 168 */       this.text = s;
/* 169 */       this.type = t;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\visual\sucks\CompassUtil\AwarelineCompass.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */