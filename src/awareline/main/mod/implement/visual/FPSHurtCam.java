/*     */ package awareline.main.mod.implement.visual;
/*     */ 
/*     */ import awareline.main.event.EventHandler;
/*     */ import awareline.main.event.events.world.renderEvents.EventRender2D;
/*     */ import awareline.main.mod.Module;
/*     */ import awareline.main.mod.ModuleType;
/*     */ import awareline.main.mod.implement.visual.sucks.WingRenderer.ColorUtils;
/*     */ import awareline.main.mod.values.Mode;
/*     */ import awareline.main.mod.values.Numbers;
/*     */ import awareline.main.mod.values.Option;
/*     */ import awareline.main.mod.values.Value;
/*     */ import java.awt.Color;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ 
/*     */ public class FPSHurtCam extends Module {
/*  20 */   private final Mode<String> colormode = new Mode("Color", new String[] { "HealthColor", "CustomColor" }, "DistanceHealth");
/*  21 */   private final Option<Boolean> rainbow = new Option("Rainbow", Boolean.valueOf(false));
/*  22 */   private final Option<Boolean> HUDColor = new Option("HUDColor", Boolean.valueOf(true));
/*  23 */   private final Numbers<Double> r = new Numbers("Red", Double.valueOf(120.0D), Double.valueOf(0.0D), Double.valueOf(255.0D), Double.valueOf(5.0D));
/*  24 */   private final Numbers<Double> g = new Numbers("Green", Double.valueOf(120.0D), Double.valueOf(0.0D), Double.valueOf(255.0D), Double.valueOf(5.0D));
/*  25 */   private final Numbers<Double> b = new Numbers("Blue", Double.valueOf(255.0D), Double.valueOf(0.0D), Double.valueOf(255.0D), Double.valueOf(5.0D));
/*     */   
/*     */   public FPSHurtCam() {
/*  28 */     super("FPSHurtCam", ModuleType.Render);
/*  29 */     addSettings(new Value[] { (Value)this.colormode, (Value)this.r, (Value)this.g, (Value)this.b, (Value)this.HUDColor, (Value)this.rainbow });
/*     */   }
/*     */   @EventHandler
/*     */   private void onRender3D(EventRender2D event) {
/*     */     int n3, n2, n;
/*  34 */     ScaledResolution sr = event.getResolution();
/*  35 */     double left = 0.0D;
/*  36 */     double top = 0.0D;
/*  37 */     double right = sr.getScaledWidth();
/*  38 */     double bottom = 30.0D;
/*     */ 
/*     */ 
/*     */     
/*  42 */     if (this.colormode.isCurrentMode("HealthColor")) {
/*  43 */       n = 255;
/*  44 */       n2 = 15;
/*  45 */       n3 = 15;
/*     */     }
/*  47 */     else if (!((Boolean)this.rainbow.getValue()).booleanValue()) {
/*  48 */       if (((Boolean)this.HUDColor.getValue()).booleanValue()) {
/*  49 */         n = ((Double)HUD.r.getValue()).intValue();
/*  50 */         n2 = ((Double)HUD.g.getValue()).intValue();
/*  51 */         n3 = ((Double)HUD.b.getValue()).intValue();
/*     */       } else {
/*  53 */         n = ((Double)this.r.getValue()).intValue();
/*  54 */         n2 = ((Double)this.g.getValue()).intValue();
/*  55 */         n3 = ((Double)this.b.getValue()).intValue();
/*     */       } 
/*     */     } else {
/*  58 */       n = ColorUtils.rainbow(1L, 1.0F).getRed() / 255;
/*  59 */       n2 = ColorUtils.rainbow(1L, 1.0F).getGreen() / 255;
/*  60 */       n3 = ColorUtils.rainbow(1L, 1.0F).getBlue() / 255;
/*     */     } 
/*     */     
/*  63 */     drawVerticalGradientSideways(0.0D, 0.0D, right, 30.0D, (new Color(n, n2, n3, mc.thePlayer.hurtTime * 20)).getRGB(), 0);
/*  64 */     double left2 = 0.0D;
/*  65 */     double top2 = (sr.getScaledHeight() - 30);
/*  66 */     double right2 = sr.getScaledWidth();
/*  67 */     double bottom2 = sr.getScaledHeight();
/*  68 */     drawVerticalGradientSideways(0.0D, top2, right2, bottom2, 0, (new Color(n, n2, n3, mc.thePlayer.hurtTime * 20)).getRGB());
/*  69 */     double left3 = 0.0D;
/*  70 */     double top3 = 0.0D;
/*  71 */     double right3 = 30.0D;
/*  72 */     drawHorizontalGradientSideways(0.0D, 0.0D, 30.0D, bottom2, (new Color(n, n2, n3, mc.thePlayer.hurtTime * 20)).getRGB(), 0);
/*  73 */     double left4 = (sr.getScaledWidth() - 30);
/*  74 */     double top4 = 0.0D;
/*  75 */     double right4 = sr.getScaledWidth();
/*  76 */     drawHorizontalGradientSideways(left4, 0.0D, right4, bottom2, 0, (new Color(n, n2, n3, mc.thePlayer.hurtTime * 20)).getRGB());
/*     */   }
/*     */   
/*     */   public void drawVerticalGradientSideways(double left, double top, double right, double bottom, int col1, int col2) {
/*  80 */     float f = (col1 >> 24 & 0xFF) / 255.0F;
/*  81 */     float f2 = (col1 >> 16 & 0xFF) / 255.0F;
/*  82 */     float f3 = (col1 >> 8 & 0xFF) / 255.0F;
/*  83 */     float f4 = (col1 & 0xFF) / 255.0F;
/*  84 */     float f5 = (col2 >> 24 & 0xFF) / 255.0F;
/*  85 */     float f6 = (col2 >> 16 & 0xFF) / 255.0F;
/*  86 */     float f7 = (col2 >> 8 & 0xFF) / 255.0F;
/*  87 */     float f8 = (col2 & 0xFF) / 255.0F;
/*  88 */     GlStateManager.disableTexture2D();
/*  89 */     GlStateManager.enableBlend();
/*  90 */     GlStateManager.disableAlpha();
/*  91 */     GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/*  92 */     GlStateManager.shadeModel(7425);
/*  93 */     Tessellator tessellator = Tessellator.getInstance();
/*  94 */     WorldRenderer world = tessellator.getWorldRenderer();
/*  95 */     world.begin(7, DefaultVertexFormats.POSITION_COLOR);
/*  96 */     world.pos(right, top, 0.0D).color(f2, f3, f4, f).endVertex();
/*  97 */     world.pos(left, top, 0.0D).color(f2, f3, f4, f).endVertex();
/*  98 */     world.pos(left, bottom, 0.0D).color(f6, f7, f8, f5).endVertex();
/*  99 */     world.pos(right, bottom, 0.0D).color(f6, f7, f8, f5).endVertex();
/* 100 */     tessellator.draw();
/* 101 */     GlStateManager.shadeModel(7424);
/* 102 */     GlStateManager.disableBlend();
/* 103 */     GlStateManager.enableAlpha();
/* 104 */     GlStateManager.enableTexture2D();
/*     */   }
/*     */   
/*     */   public void drawHorizontalGradientSideways(double left, double top, double right, double bottom, int col1, int col2) {
/* 108 */     float f = (col1 >> 24 & 0xFF) / 255.0F;
/* 109 */     float f2 = (col1 >> 16 & 0xFF) / 255.0F;
/* 110 */     float f3 = (col1 >> 8 & 0xFF) / 255.0F;
/* 111 */     float f4 = (col1 & 0xFF) / 255.0F;
/* 112 */     float f5 = (col2 >> 24 & 0xFF) / 255.0F;
/* 113 */     float f6 = (col2 >> 16 & 0xFF) / 255.0F;
/* 114 */     float f7 = (col2 >> 8 & 0xFF) / 255.0F;
/* 115 */     float f8 = (col2 & 0xFF) / 255.0F;
/* 116 */     GlStateManager.disableTexture2D();
/* 117 */     GlStateManager.enableBlend();
/* 118 */     GlStateManager.disableAlpha();
/* 119 */     GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 120 */     GlStateManager.shadeModel(7425);
/* 121 */     Tessellator tessellator = Tessellator.getInstance();
/* 122 */     WorldRenderer world = tessellator.getWorldRenderer();
/* 123 */     world.begin(7, DefaultVertexFormats.POSITION_COLOR);
/* 124 */     world.pos(left, top, 0.0D).color(f2, f3, f4, f).endVertex();
/* 125 */     world.pos(left, bottom, 0.0D).color(f2, f3, f4, f).endVertex();
/* 126 */     world.pos(right, bottom, 0.0D).color(f6, f7, f8, f5).endVertex();
/* 127 */     world.pos(right, top, 0.0D).color(f6, f7, f8, f5).endVertex();
/* 128 */     tessellator.draw();
/* 129 */     GlStateManager.shadeModel(7424);
/* 130 */     GlStateManager.disableBlend();
/* 131 */     GlStateManager.enableAlpha();
/* 132 */     GlStateManager.enableTexture2D();
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\visual\FPSHurtCam.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */