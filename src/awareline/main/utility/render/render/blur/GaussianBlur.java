/*    */ package awareline.main.utility.render.render.blur;
/*    */ 
/*    */ import awareline.main.ui.gui.clickgui.RenderUtil;
/*    */ import awareline.main.utility.render.render.blur.util.ShaderUtil;
/*    */ import java.nio.FloatBuffer;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.OpenGlHelper;
/*    */ import net.minecraft.client.shader.Framebuffer;
/*    */ import org.lwjgl.BufferUtils;
/*    */ import org.lwjgl.opengl.GL20;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GaussianBlur
/*    */ {
/* 22 */   private static final Minecraft mc = Minecraft.getMinecraft();
/* 23 */   public static ShaderUtil blurShader = new ShaderUtil("client/advancedshader/shaders/gaussian.frag");
/* 24 */   public static Framebuffer framebuffer = new Framebuffer(1, 1, false);
/*    */   
/*    */   public static void setupUniforms(float dir1, float dir2, float radius) {
/* 27 */     blurShader.setUniformi("textureIn", new int[] { 0 });
/* 28 */     blurShader.setUniformf("texelSize", new float[] { 1.0F / mc.displayWidth, 1.0F / mc.displayHeight });
/* 29 */     blurShader.setUniformf("direction", new float[] { dir1, dir2 });
/* 30 */     blurShader.setUniformf("radius", new float[] { radius });
/* 31 */     FloatBuffer weightBuffer = BufferUtils.createFloatBuffer(256);
/* 32 */     int i = 0;
/* 33 */     while (i <= radius) {
/* 34 */       weightBuffer.put(calculateGaussianValue(i, radius / 2.0F));
/* 35 */       i++;
/*    */     } 
/* 37 */     weightBuffer.rewind();
/* 38 */     GL20.glUniform1(blurShader.getUniform("weights"), weightBuffer);
/*    */   }
/*    */   public static float calculateGaussianValue(float x, float sigma) {
/* 41 */     double PI = 3.141592653D;
/* 42 */     double output = 1.0D / Math.sqrt(2.0D * PI * (sigma * sigma));
/* 43 */     return (float)(output * Math.exp(-(x * x) / 2.0D * (sigma * sigma)));
/*    */   }
/*    */   public static void renderBlur(float radius) {
/* 46 */     GlStateManager.enableBlend();
/* 47 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 48 */     OpenGlHelper.glBlendFunc(770, 771, 1, 0);
/* 49 */     framebuffer = RenderUtil.createFrameBuffer(framebuffer);
/* 50 */     framebuffer.framebufferClear();
/* 51 */     framebuffer.bindFramebuffer(true);
/* 52 */     blurShader.init();
/* 53 */     setupUniforms(1.0F, 0.0F, radius);
/* 54 */     RenderUtil.bindTexture((mc.getFramebuffer()).framebufferTexture);
/* 55 */     ShaderUtil.drawQuads();
/* 56 */     framebuffer.unbindFramebuffer();
/* 57 */     blurShader.unload();
/* 58 */     mc.getFramebuffer().bindFramebuffer(true);
/* 59 */     blurShader.init();
/* 60 */     setupUniforms(0.0F, 1.0F, radius);
/* 61 */     RenderUtil.bindTexture(framebuffer.framebufferTexture);
/* 62 */     ShaderUtil.drawQuads();
/* 63 */     blurShader.unload();
/* 64 */     RenderUtil.resetColor();
/* 65 */     GlStateManager.bindTexture(0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\utility\render\render\blur\GaussianBlur.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */