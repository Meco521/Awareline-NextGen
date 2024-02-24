/*     */ package awareline.main.utility.render.render.blur;
/*     */ 
/*     */ import awareline.main.utility.render.render.RenderUtils;
/*     */ import awareline.main.utility.render.render.blur.util.GLUtil;
/*     */ import awareline.main.utility.render.render.blur.util.ShaderUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.shader.Framebuffer;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.lwjgl.opengl.GL13;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class KawaseBlur
/*     */ {
/*  23 */   private static final Minecraft mc = Minecraft.getMinecraft();
/*  24 */   public static ShaderUtil kawaseDown = new ShaderUtil("kawaseDown");
/*  25 */   public static ShaderUtil kawaseUp = new ShaderUtil("kawaseUp");
/*  26 */   public static Framebuffer framebuffer = new Framebuffer(1, 1, false);
/*     */   
/*     */   private static int currentIterations;
/*     */   
/*     */   public static void setupUniforms(float offset) {
/*  31 */     kawaseDown.setUniformf("offset", new float[] { offset, offset });
/*  32 */     kawaseUp.setUniformf("offset", new float[] { offset, offset });
/*     */   }
/*     */   
/*     */   private static void initFramebuffers(float iterations) {
/*  36 */     for (Framebuffer currentBuffer : framebufferList) {
/*  37 */       currentBuffer.deleteFramebuffer();
/*     */     }
/*  39 */     framebufferList.clear();
/*  40 */     framebuffer = RenderUtils.createFrameBuffer(null);
/*  41 */     framebufferList.add(framebuffer);
/*  42 */     int i = 1;
/*  43 */     while (i <= iterations) {
/*     */       
/*  45 */       Framebuffer currentBuffer = new Framebuffer((int)(mc.displayWidth / Math.pow(2.0D, i)), (int)(mc.displayHeight / Math.pow(2.0D, i)), false);
/*  46 */       currentBuffer.setFramebufferFilter(9729);
/*  47 */       GlStateManager.bindTexture(currentBuffer.framebufferTexture);
/*  48 */       GL11.glTexParameteri(3553, 10242, 33648);
/*  49 */       GL11.glTexParameteri(3553, 10243, 33648);
/*  50 */       GlStateManager.bindTexture(0);
/*  51 */       framebufferList.add(currentBuffer);
/*  52 */       i++;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void renderBlur(int stencilFrameBufferTexture, int iterations, int offset) {
/*  58 */     if (currentIterations != iterations || framebuffer.framebufferWidth != mc.displayWidth || framebuffer.framebufferHeight != mc.displayHeight) {
/*  59 */       initFramebuffers(iterations);
/*  60 */       currentIterations = iterations;
/*     */     } 
/*  62 */     renderFBO(framebufferList.get(1), (mc.getFramebuffer()).framebufferTexture, kawaseDown, offset); int i;
/*  63 */     for (i = 1; i < iterations; i++) {
/*  64 */       renderFBO(framebufferList.get(i + 1), ((Framebuffer)framebufferList.get(i)).framebufferTexture, kawaseDown, offset);
/*     */     }
/*  66 */     for (i = iterations; i > 1; i--) {
/*  67 */       renderFBO(framebufferList.get(i - 1), ((Framebuffer)framebufferList.get(i)).framebufferTexture, kawaseUp, offset);
/*     */     }
/*  69 */     Framebuffer lastBuffer = framebufferList.get(0);
/*  70 */     lastBuffer.framebufferClear();
/*  71 */     lastBuffer.bindFramebuffer(false);
/*  72 */     kawaseUp.init();
/*  73 */     kawaseUp.setUniformf("offset", new float[] { offset, offset });
/*  74 */     kawaseUp.setUniformi("inTexture", new int[] { 0 });
/*  75 */     kawaseUp.setUniformi("check", new int[] { 1 });
/*  76 */     kawaseUp.setUniformi("textureToCheck", new int[] { 16 });
/*  77 */     kawaseUp.setUniformf("halfpixel", new float[] { 1.0F / lastBuffer.framebufferWidth, 1.0F / lastBuffer.framebufferHeight });
/*  78 */     kawaseUp.setUniformf("iResolution", new float[] { lastBuffer.framebufferWidth, lastBuffer.framebufferHeight });
/*  79 */     GL13.glActiveTexture(34000);
/*  80 */     RenderUtils.bindTexture(stencilFrameBufferTexture);
/*  81 */     GL13.glActiveTexture(33984);
/*  82 */     RenderUtils.bindTexture(((Framebuffer)framebufferList.get(1)).framebufferTexture);
/*  83 */     ShaderUtil.drawQuads();
/*  84 */     kawaseUp.unload();
/*  85 */     mc.getFramebuffer().bindFramebuffer(true);
/*  86 */     RenderUtils.bindTexture(((Framebuffer)framebufferList.get(0)).framebufferTexture);
/*  87 */     RenderUtils.setAlphaLimit(0.0F);
/*  88 */     GLUtil.startBlend();
/*  89 */     ShaderUtil.drawQuads();
/*  90 */     GlStateManager.bindTexture(0);
/*     */   }
/*     */   
/*     */   private static void renderFBO(Framebuffer framebuffer, int framebufferTexture, ShaderUtil shader, float offset) {
/*  94 */     framebuffer.framebufferClear();
/*  95 */     framebuffer.bindFramebuffer(false);
/*  96 */     shader.init();
/*  97 */     RenderUtils.bindTexture(framebufferTexture);
/*  98 */     shader.setUniformf("offset", new float[] { offset, offset });
/*  99 */     shader.setUniformi("inTexture", new int[] { 0 });
/* 100 */     shader.setUniformi("check", new int[] { 0 });
/* 101 */     shader.setUniformf("halfpixel", new float[] { 1.0F / framebuffer.framebufferWidth, 1.0F / framebuffer.framebufferHeight });
/* 102 */     shader.setUniformf("iResolution", new float[] { framebuffer.framebufferWidth, framebuffer.framebufferHeight });
/* 103 */     ShaderUtil.drawQuads();
/* 104 */     shader.unload();
/*     */   }
/*     */ 
/*     */   
/* 108 */   private static final List<Framebuffer> framebufferList = new ArrayList<>();
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\utility\render\render\blur\KawaseBlur.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */