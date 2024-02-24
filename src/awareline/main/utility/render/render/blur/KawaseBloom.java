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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class KawaseBloom
/*     */ {
/*  21 */   private static final Minecraft mc = Minecraft.getMinecraft();
/*  22 */   public static ShaderUtil kawaseDown = new ShaderUtil("kawaseDownBloom");
/*  23 */   public static ShaderUtil kawaseUp = new ShaderUtil("kawaseUpBloom");
/*  24 */   public static Framebuffer framebuffer = new Framebuffer(1, 1, true);
/*     */   
/*     */   private static int currentIterations;
/*     */   
/*     */   private static void initFramebuffers(float iterations) {
/*  29 */     for (Framebuffer currentBuffer : framebufferList) {
/*  30 */       currentBuffer.deleteFramebuffer();
/*     */     }
/*  32 */     framebufferList.clear();
/*  33 */     framebuffer = RenderUtils.createFrameBuffer(null, true);
/*  34 */     framebufferList.add(framebuffer);
/*  35 */     int i = 1;
/*  36 */     while (i <= iterations) {
/*     */       
/*  38 */       Framebuffer currentBuffer = new Framebuffer((int)(mc.displayWidth / Math.pow(2.0D, i)), (int)(mc.displayHeight / Math.pow(2.0D, i)), true);
/*  39 */       currentBuffer.setFramebufferFilter(9729);
/*  40 */       GlStateManager.bindTexture(currentBuffer.framebufferTexture);
/*  41 */       GL11.glTexParameteri(3553, 10242, 33648);
/*  42 */       GL11.glTexParameteri(3553, 10243, 33648);
/*  43 */       GlStateManager.bindTexture(0);
/*  44 */       framebufferList.add(currentBuffer);
/*  45 */       i++;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void renderBlur(int framebufferTexture, int iterations, int offset) {
/*  51 */     if (currentIterations != iterations || framebuffer.framebufferWidth != mc.displayWidth || framebuffer.framebufferHeight != mc.displayHeight) {
/*  52 */       initFramebuffers(iterations);
/*  53 */       currentIterations = iterations;
/*     */     } 
/*  55 */     RenderUtils.setAlphaLimit(0.0F);
/*  56 */     GlStateManager.enableBlend();
/*  57 */     GlStateManager.blendFunc(1, 1);
/*  58 */     GL11.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
/*  59 */     renderFBO(framebufferList.get(1), framebufferTexture, kawaseDown, offset); int i;
/*  60 */     for (i = 1; i < iterations; i++) {
/*  61 */       renderFBO(framebufferList.get(i + 1), ((Framebuffer)framebufferList.get(i)).framebufferTexture, kawaseDown, offset);
/*     */     }
/*  63 */     for (i = iterations; i > 1; i--) {
/*  64 */       renderFBO(framebufferList.get(i - 1), ((Framebuffer)framebufferList.get(i)).framebufferTexture, kawaseUp, offset);
/*     */     }
/*  66 */     Framebuffer lastBuffer = framebufferList.get(0);
/*  67 */     lastBuffer.framebufferClear();
/*  68 */     lastBuffer.bindFramebuffer(false);
/*  69 */     kawaseUp.init();
/*  70 */     kawaseUp.setUniformf("offset", new float[] { offset, offset });
/*  71 */     kawaseUp.setUniformi("inTexture", new int[] { 0 });
/*  72 */     kawaseUp.setUniformi("check", new int[] { 1 });
/*  73 */     kawaseUp.setUniformi("textureToCheck", new int[] { 16 });
/*  74 */     kawaseUp.setUniformf("halfpixel", new float[] { 1.0F / lastBuffer.framebufferWidth, 1.0F / lastBuffer.framebufferHeight });
/*  75 */     kawaseUp.setUniformf("iResolution", new float[] { lastBuffer.framebufferWidth, lastBuffer.framebufferHeight });
/*  76 */     GlStateManager.setActiveTexture(34000);
/*  77 */     RenderUtils.bindTexture(framebufferTexture);
/*  78 */     GlStateManager.setActiveTexture(33984);
/*  79 */     RenderUtils.bindTexture(((Framebuffer)framebufferList.get(1)).framebufferTexture);
/*  80 */     ShaderUtil.drawQuads();
/*  81 */     kawaseUp.unload();
/*  82 */     GlStateManager.clearColor(0.0F, 0.0F, 0.0F, 0.0F);
/*  83 */     mc.getFramebuffer().bindFramebuffer(false);
/*  84 */     RenderUtils.bindTexture(((Framebuffer)framebufferList.get(0)).framebufferTexture);
/*  85 */     RenderUtils.setAlphaLimit(0.0F);
/*  86 */     GLUtil.startBlend();
/*  87 */     ShaderUtil.drawQuads();
/*  88 */     GlStateManager.bindTexture(0);
/*  89 */     RenderUtils.setAlphaLimit(0.0F);
/*  90 */     GLUtil.startBlend();
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


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\utility\render\render\blur\KawaseBloom.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */