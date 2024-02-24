/*     */ package awareline.main.utility.shader.ketaUtils.render;
/*     */ 
/*     */ import awareline.main.event.events.ketaShaderCall.RenderCallback;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.client.shader.Framebuffer;
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
/*     */ public class BloomUtil
/*     */ {
/*     */   private static final String OUTLINE_SHADER = "#version 120\n\nuniform sampler2D texture;\nuniform vec2 texelSize;\n\nuniform vec4 colour;\nuniform float radius;\n\nvoid main() {\n    float a = 0.0;\n    vec3 rgb = colour.rgb;\n    float closest = 1.0;\n    for (float x = -radius; x <= radius; x++) {\n        for (float y = -radius; y <= radius; y++) {\n            vec2 st = gl_TexCoord[0].st + vec2(x, y) * texelSize;\n            vec4 smpl = texture2D(texture, st);\n            float dist = distance(st, gl_TexCoord[0].st);\n            if (smpl.a > 0.0 && dist < closest) {               rgb = smpl.rgb;\n               closest = dist;\n            }\n            a += smpl.a*smpl.a;\n        }\n    }\n    vec4 smpl = texture2D(texture, gl_TexCoord[0].st);\n    gl_FragColor = vec4(rgb, a * colour.a / (4.0 * radius * radius)) * (smpl.a > 0.0 ? 0.0 : 1.0);\n}\n";
/*     */   private static final String VERTEX_SHADER = "#version 120 \n\nvoid main() {\n    gl_TexCoord[0] = gl_MultiTexCoord0;\n    gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;\n}";
/*     */   
/*  54 */   private static final GLShader shader = new GLShader("#version 120 \n\nvoid main() {\n    gl_TexCoord[0] = gl_MultiTexCoord0;\n    gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;\n}", "#version 120\n\nuniform sampler2D texture;\nuniform vec2 texelSize;\n\nuniform vec4 colour;\nuniform float radius;\n\nvoid main() {\n    float a = 0.0;\n    vec3 rgb = colour.rgb;\n    float closest = 1.0;\n    for (float x = -radius; x <= radius; x++) {\n        for (float y = -radius; y <= radius; y++) {\n            vec2 st = gl_TexCoord[0].st + vec2(x, y) * texelSize;\n            vec4 smpl = texture2D(texture, st);\n            float dist = distance(st, gl_TexCoord[0].st);\n            if (smpl.a > 0.0 && dist < closest) {               rgb = smpl.rgb;\n               closest = dist;\n            }\n            a += smpl.a*smpl.a;\n        }\n    }\n    vec4 smpl = texture2D(texture, gl_TexCoord[0].st);\n    gl_FragColor = vec4(rgb, a * colour.a / (4.0 * radius * radius)) * (smpl.a > 0.0 ? 0.0 : 1.0);\n}\n")
/*     */     {
/*     */       public void setupUniforms() {
/*  57 */         setupUniform("texture");
/*  58 */         setupUniform("texelSize");
/*  59 */         setupUniform("colour");
/*  60 */         setupUniform("radius");
/*  61 */         GL20.glUniform1i(getUniformLocation("texture"), 0);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       public void updateUniforms() {
/*  67 */         GL20.glUniform4f(getUniformLocation("colour"), 1.0F, 1.0F, 1.0F, 1.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  72 */         GL20.glUniform2f(getUniformLocation("texelSize"), 1.0F / 
/*  73 */             (Minecraft.getMinecraft()).displayWidth, 1.0F / 
/*  74 */             (Minecraft.getMinecraft()).displayHeight);
/*  75 */         GL20.glUniform1f(getUniformLocation("radius"), 5.0F);
/*     */       }
/*     */     };
/*     */   
/*     */   private static Framebuffer framebuffer;
/*     */   public static boolean disableBloom;
/*  81 */   private static final List<RenderCallback> renders = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void bloom(RenderCallback render) {
/*  87 */     if (disableBloom)
/*  88 */       return;  renders.add(render);
/*     */   }
/*     */   
/*     */   public static void drawAndBloom(RenderCallback render) {
/*  92 */     render.render();
/*  93 */     if (disableBloom)
/*  94 */       return;  renders.add(render);
/*     */   }
/*     */   
/*     */   public static void onRenderGameOverlay(ScaledResolution scaledResolution, Framebuffer mcFramebuffer) {
/*  98 */     if (framebuffer == null)
/*     */       return; 
/* 100 */     framebuffer.bindFramebuffer(false);
/*     */     
/* 102 */     for (RenderCallback callback : renders) {
/* 103 */       callback.render();
/*     */     }
/*     */     
/* 106 */     renders.clear();
/*     */     
/* 108 */     mcFramebuffer.bindFramebuffer(false);
/*     */ 
/*     */     
/* 111 */     shader.use();
/*     */     
/* 113 */     DrawUtil.glDrawFramebuffer(framebuffer.framebufferTexture, scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight());
/*     */     
/* 115 */     GL20.glUseProgram(0);
/*     */     
/* 117 */     framebuffer.framebufferClear();
/*     */     
/* 119 */     mcFramebuffer.bindFramebuffer(false);
/*     */   }
/*     */   
/*     */   public static void onFrameBufferResize(int width, int height) {
/* 123 */     if (framebuffer != null)
/*     */     {
/* 125 */       framebuffer.deleteFramebuffer();
/*     */     }
/*     */ 
/*     */     
/* 129 */     framebuffer = new Framebuffer(width, height, false);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\utility\shader\ketaUtils\render\BloomUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */