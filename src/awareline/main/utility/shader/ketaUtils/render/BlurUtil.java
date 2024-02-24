/*     */ package awareline.main.utility.shader.ketaUtils.render;
/*     */ 
/*     */ import awareline.main.mod.implement.visual.HUD;
/*     */ import awareline.main.utility.chat.Helper;
/*     */ import java.nio.FloatBuffer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.shader.Framebuffer;
/*     */ import net.minecraft.src.Config;
/*     */ import org.lwjgl.BufferUtils;
/*     */ import org.lwjgl.opengl.Display;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.lwjgl.opengl.GL13;
/*     */ import org.lwjgl.opengl.GL20;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class BlurUtil
/*     */ {
/*     */   public static final String VERTEX_SHADER = "#version 120 \n\nvoid main() {\n    gl_TexCoord[0] = gl_MultiTexCoord0;\n    gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;\n}";
/*     */   private static final String BLUR_FRAG_SHADER = "#version 120\n\nuniform sampler2D texture;\nuniform sampler2D texture2;\nuniform vec2 texelSize;\nuniform vec2 direction;\nuniform float radius;\nuniform float weights[256];\n\nvoid main() {\n    vec4 color = vec4(0.0);\n    vec2 texCoord = gl_TexCoord[0].st;\n    if (direction.y == 0)\n        if (texture2D(texture2, texCoord).a == 0.0) return;\n    for (float f = -radius; f <= radius; f++) {\n        color += texture2D(texture, texCoord + f * texelSize * direction) * (weights[int(abs(f))]);\n    }\n    gl_FragColor = vec4(color.rgb, 1.0);\n}";
/*     */   
/*  52 */   private static final GLShader blurShader = new GLShader("#version 120 \n\nvoid main() {\n    gl_TexCoord[0] = gl_MultiTexCoord0;\n    gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;\n}", "#version 120\n\nuniform sampler2D texture;\nuniform sampler2D texture2;\nuniform vec2 texelSize;\nuniform vec2 direction;\nuniform float radius;\nuniform float weights[256];\n\nvoid main() {\n    vec4 color = vec4(0.0);\n    vec2 texCoord = gl_TexCoord[0].st;\n    if (direction.y == 0)\n        if (texture2D(texture2, texCoord).a == 0.0) return;\n    for (float f = -radius; f <= radius; f++) {\n        color += texture2D(texture, texCoord + f * texelSize * direction) * (weights[int(abs(f))]);\n    }\n    gl_FragColor = vec4(color.rgb, 1.0);\n}")
/*     */     {
/*     */       public void setupUniforms() {
/*  55 */         setupUniform("texture");
/*  56 */         setupUniform("texture2");
/*  57 */         setupUniform("texelSize");
/*  58 */         setupUniform("radius");
/*  59 */         setupUniform("direction");
/*  60 */         setupUniform("weights");
/*     */       }
/*     */ 
/*     */       
/*     */       public void updateUniforms() {
/*  65 */         float radius = 25.0F;
/*     */         
/*  67 */         GL20.glUniform1i(getUniformLocation("texture"), 0);
/*  68 */         GL20.glUniform1i(getUniformLocation("texture2"), 20);
/*  69 */         GL20.glUniform1f(getUniformLocation("radius"), 25.0F);
/*     */         
/*  71 */         FloatBuffer buffer = BufferUtils.createFloatBuffer(256);
/*  72 */         float blurRadius = 12.5F;
/*  73 */         for (int i = 0; i <= 12.5F; i++) {
/*  74 */           buffer.put(BlurUtil.calculateGaussianOffset(i, 6.25F));
/*     */         }
/*  76 */         buffer.rewind();
/*     */         
/*  78 */         GL20.glUniform1(getUniformLocation("weights"), buffer);
/*     */         
/*  80 */         GL20.glUniform2f(getUniformLocation("texelSize"), 1.0F / 
/*  81 */             Display.getWidth(), 1.0F / 
/*  82 */             Display.getHeight());
/*     */       }
/*     */     };
/*  85 */   private static final List<double[]> blurAreas = (List)new ArrayList<>();
/*     */   
/*     */   public static boolean disableBlur;
/*     */   
/*     */   private static Framebuffer framebuffer;
/*     */   
/*     */   private static Framebuffer framebufferRender;
/*     */   
/*     */   public static boolean isFastRenderEnabled() {
/*  94 */     return Config.isFastRender();
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean blurEnabled() {
/*  99 */     if (isFastRenderEnabled()) {
/* 100 */       return false;
/*     */     }
/* 102 */     if (HUD.getInstance.isEnabled() && ((Boolean)HUD.getInstance.blur.get()).booleanValue() && OpenGlHelper.shadersSupported && 
/* 103 */       Minecraft.getMinecraft().getRenderViewEntity() instanceof net.minecraft.entity.player.EntityPlayer) {
/* 104 */       return true;
/*     */     }
/* 106 */     return false;
/*     */   }
/*     */   
/*     */   public static void blurArea(double x, double y, double width, double height) {
/* 110 */     if (!isFastRenderEnabled()) {
/* 111 */       if (!blurEnabled()) {
/*     */         return;
/*     */       }
/*     */       
/* 115 */       if (disableBlur) {
/*     */         return;
/*     */       }
/*     */       
/* 119 */       blurAreas.add(new double[] { x, y, width, height });
/*     */     } else {
/* 121 */       Helper.sendMessage("Automatically close fastender because you have enabled the blur option in HUD");
/* 122 */       (Minecraft.getMinecraft()).gameSettings.ofFastRender = false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void onRenderGameOverlay(Framebuffer mcFramebuffer, ScaledResolution sr) {
/* 127 */     if (!blurEnabled()) {
/*     */       return;
/*     */     }
/*     */     
/* 131 */     if (framebuffer == null || framebufferRender == null || blurAreas.isEmpty()) {
/*     */       return;
/*     */     }
/* 134 */     framebufferRender.framebufferClear();
/*     */     
/* 136 */     framebufferRender.bindFramebuffer(false);
/*     */     
/* 138 */     for (double[] area : blurAreas) {
/* 139 */       DrawUtil.glDrawFilledQuad(area[0], area[1], area[2], area[3], -16777216);
/*     */     }
/*     */     
/* 142 */     blurAreas.clear();
/*     */ 
/*     */     
/* 145 */     boolean restore = DrawUtil.glEnableBlend();
/*     */ 
/*     */ 
/*     */     
/* 149 */     framebuffer.bindFramebuffer(false);
/* 150 */     blurShader.use();
/* 151 */     onPass(1);
/*     */     
/* 153 */     glDrawFramebuffer(sr, mcFramebuffer);
/* 154 */     GL20.glUseProgram(0);
/*     */ 
/*     */ 
/*     */     
/* 158 */     mcFramebuffer.bindFramebuffer(false);
/* 159 */     blurShader.use();
/* 160 */     onPass(0);
/*     */     
/* 162 */     GL13.glActiveTexture(34004);
/* 163 */     GL11.glBindTexture(3553, framebufferRender.framebufferTexture);
/* 164 */     GL13.glActiveTexture(33984);
/*     */     
/* 166 */     glDrawFramebuffer(sr, framebuffer);
/* 167 */     GL20.glUseProgram(0);
/*     */ 
/*     */     
/* 170 */     DrawUtil.glRestoreBlend(restore);
/*     */   }
/*     */   
/*     */   private static void onPass(int pass) {
/* 174 */     GL20.glUniform2f(blurShader.getUniformLocation("direction"), (1 - pass), pass);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void glDrawFramebuffer(ScaledResolution scaledResolution, Framebuffer framebuffer) {
/* 179 */     GL11.glBindTexture(3553, framebuffer.framebufferTexture);
/*     */     
/* 181 */     GL11.glBegin(7);
/*     */     
/* 183 */     GL11.glTexCoord2f(0.0F, 1.0F);
/* 184 */     GL11.glVertex2i(0, 0);
/*     */     
/* 186 */     GL11.glTexCoord2f(0.0F, 0.0F);
/* 187 */     GL11.glVertex2i(0, scaledResolution.getScaledHeight());
/*     */     
/* 189 */     GL11.glTexCoord2f(1.0F, 0.0F);
/* 190 */     GL11.glVertex2i(scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight());
/*     */     
/* 192 */     GL11.glTexCoord2f(1.0F, 1.0F);
/* 193 */     GL11.glVertex2i(scaledResolution.getScaledWidth(), 0);
/*     */     
/* 195 */     GL11.glEnd();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void onFrameBufferResize(int width, int height) {
/* 200 */     if (!blurEnabled()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 205 */     if (framebuffer != null) {
/* 206 */       framebuffer.deleteFramebuffer();
/*     */     }
/* 208 */     if (framebufferRender != null) {
/* 209 */       framebufferRender.deleteFramebuffer();
/*     */     }
/*     */ 
/*     */     
/* 213 */     framebuffer = new Framebuffer(width, height, false);
/* 214 */     framebufferRender = new Framebuffer(width, height, false);
/*     */   }
/*     */   
/*     */   static float calculateGaussianOffset(float x, float sigma) {
/* 218 */     float pow = x / sigma;
/* 219 */     return (float)(1.0D / Math.abs(sigma) * 2.50662827463D * Math.exp(-0.5D * pow * pow));
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\utility\shader\ketaUtils\render\BlurUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */