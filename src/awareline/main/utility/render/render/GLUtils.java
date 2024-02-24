/*     */ package awareline.main.utility.render.render;
/*     */ 
/*     */ import java.nio.FloatBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import org.lwjgl.BufferUtils;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class GLUtils
/*     */ {
/*  20 */   public static Minecraft mc = Minecraft.getMinecraft();
/*  21 */   public static final FloatBuffer MODELVIEW = BufferUtils.createFloatBuffer(16);
/*  22 */   public static final FloatBuffer PROJECTION = BufferUtils.createFloatBuffer(16);
/*  23 */   public static final IntBuffer VIEWPORT = BufferUtils.createIntBuffer(16);
/*     */   
/*     */   public static void rescale(double factor) {
/*  26 */     rescale(mc.displayWidth / factor, mc.displayHeight / factor);
/*     */   }
/*     */   
/*     */   public static void rescaleMC() {
/*  30 */     ScaledResolution resolution = new ScaledResolution(mc);
/*  31 */     rescale((mc.displayWidth / resolution.getScaleFactor()), (mc.displayHeight / resolution.getScaleFactor()));
/*     */   }
/*     */   
/*     */   public static void rescale(double width, double height) {
/*  35 */     GlStateManager.clear(256);
/*  36 */     GlStateManager.matrixMode(5889);
/*  37 */     GlStateManager.loadIdentity();
/*  38 */     GlStateManager.ortho(0.0D, width, height, 0.0D, 1000.0D, 3000.0D);
/*  39 */     GlStateManager.matrixMode(5888);
/*  40 */     GlStateManager.loadIdentity();
/*  41 */     GlStateManager.translate(0.0F, 0.0F, -2000.0F);
/*     */   }
/*     */   
/*     */   public static void setup2DRendering(boolean blend) {
/*  45 */     if (blend) {
/*  46 */       startBlend();
/*     */     }
/*  48 */     GlStateManager.disableTexture2D();
/*     */   }
/*     */   
/*     */   public static void setup2DRendering() {
/*  52 */     setup2DRendering(true);
/*     */   }
/*     */   
/*     */   public static void end2DRendering() {
/*  56 */     GlStateManager.enableTexture2D();
/*  57 */     endBlend();
/*     */   }
/*     */   
/*     */   public static void endBlend() {
/*  61 */     GlStateManager.disableBlend();
/*     */   }
/*     */   
/*     */   public static void startBlend() {
/*  65 */     GlStateManager.enableBlend();
/*  66 */     GlStateManager.blendFunc(770, 771);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void init() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public static float[] getColor(int hex) {
/*  76 */     return new float[] { (hex >> 16 & 0xFF) / 255.0F, (hex >> 8 & 0xFF) / 255.0F, (hex & 0xFF) / 255.0F, (hex >> 24 & 0xFF) / 255.0F };
/*     */   }
/*     */   
/*     */   public static void glColor(int hex) {
/*  80 */     float[] color = getColor(hex);
/*  81 */     GlStateManager.color(color[0], color[1], color[2], color[3]);
/*     */   }
/*     */   
/*     */   public static void rotateX(float angle, double x, double y, double z) {
/*  85 */     GlStateManager.translate(x, y, z);
/*  86 */     GlStateManager.rotate(angle, 1.0F, 0.0F, 0.0F);
/*  87 */     GlStateManager.translate(-x, -y, -z);
/*     */   }
/*     */   
/*     */   public static void rotateY(float angle, double x, double y, double z) {
/*  91 */     GlStateManager.translate(x, y, z);
/*  92 */     GlStateManager.rotate(angle, 0.0F, 1.0F, 0.0F);
/*  93 */     GlStateManager.translate(-x, -y, -z);
/*     */   }
/*     */   
/*     */   public static void rotateZ(float angle, double x, double y, double z) {
/*  97 */     GlStateManager.translate(x, y, z);
/*  98 */     GlStateManager.rotate(angle, 0.0F, 0.0F, 1.0F);
/*  99 */     GlStateManager.translate(-x, -y, -z);
/*     */   }
/*     */   
/*     */   public static FloatBuffer getModelview() {
/* 103 */     return MODELVIEW;
/*     */   }
/*     */   
/*     */   public static FloatBuffer getProjection() {
/* 107 */     return PROJECTION;
/*     */   }
/*     */   
/*     */   public static IntBuffer getViewport() {
/* 111 */     return VIEWPORT;
/*     */   }
/*     */   
/*     */   public static void startSmooth() {
/* 115 */     GL11.glEnable(2848);
/* 116 */     GL11.glEnable(2881);
/* 117 */     GL11.glEnable(2832);
/* 118 */     GL11.glEnable(3042);
/* 119 */     GL11.glBlendFunc(770, 771);
/* 120 */     GL11.glHint(3154, 4354);
/* 121 */     GL11.glHint(3155, 4354);
/* 122 */     GL11.glHint(3153, 4354);
/*     */   }
/*     */   
/*     */   public static void endSmooth() {
/* 126 */     GL11.glDisable(2848);
/* 127 */     GL11.glDisable(2881);
/* 128 */     GL11.glEnable(2832);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\utility\render\render\GLUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */