/*     */ package awareline.main.ui.gui;
/*     */ 
/*     */ import awareline.main.InstanceAccess;
/*     */ import awareline.main.mod.implement.move.antifallutily.TimeHelper;
/*     */ import awareline.main.mod.implement.visual.HUD;
/*     */ import awareline.main.utility.render.RenderUtil;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.shader.Framebuffer;
/*     */ import net.minecraft.client.shader.Shader;
/*     */ import net.minecraft.client.shader.ShaderGroup;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.lwjgl.opengl.EXTFramebufferObject;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class BlurBuffer implements InstanceAccess {
/*     */   private static ShaderGroup blurShader;
/*     */   private static Framebuffer buffer;
/*     */   private static float lastScale;
/*     */   private static int lastScaleWidth;
/*     */   private static int lastScaleHeight;
/*  25 */   private static final ResourceLocation shader = new ResourceLocation("shaders/post/blur.json");
/*     */   
/*  27 */   private static final TimeHelper updateTimer = new TimeHelper();
/*     */   
/*     */   public static void initFboAndShader() {
/*     */     try {
/*  31 */       buffer = new Framebuffer(mc.displayWidth, mc.displayHeight, true);
/*  32 */       buffer.setFramebufferColor(0.0F, 0.0F, 0.0F, 0.0F);
/*     */       
/*  34 */       blurShader = new ShaderGroup(mc.getTextureManager(), mc.getResourceManager(), buffer, shader);
/*  35 */       blurShader.createBindFramebuffers(mc.displayWidth, mc.displayHeight);
/*  36 */     } catch (Exception ex) {
/*  37 */       ex.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void setShaderConfigs(float intensity, float blurWidth, float blurHeight) {
/*  42 */     ((Shader)blurShader.getShaders().get(0)).getShaderManager().getShaderUniform("Radius").set(intensity);
/*  43 */     ((Shader)blurShader.getShaders().get(1)).getShaderManager().getShaderUniform("Radius").set(intensity);
/*     */     
/*  45 */     ((Shader)blurShader.getShaders().get(0)).getShaderManager().getShaderUniform("BlurDir").set(blurWidth, blurHeight);
/*  46 */     ((Shader)blurShader.getShaders().get(1)).getShaderManager().getShaderUniform("BlurDir").set(blurHeight, blurWidth);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void blurArea(float x, float y, float width, float height, boolean setupOverlay) {
/*  51 */     if (!blurEnabled()) {
/*     */       return;
/*     */     }
/*     */     
/*  55 */     ScaledResolution scale = new ScaledResolution(mc);
/*  56 */     float factor = scale.getScaleFactor();
/*  57 */     int factor2 = scale.getScaledWidth();
/*  58 */     int factor3 = scale.getScaledHeight();
/*  59 */     if (lastScale != factor || lastScaleWidth != factor2 || lastScaleHeight != factor3 || buffer == null || blurShader == null) {
/*  60 */       initFboAndShader();
/*     */     }
/*  62 */     lastScale = factor;
/*  63 */     lastScaleWidth = factor2;
/*  64 */     lastScaleHeight = factor3;
/*     */ 
/*     */     
/*  67 */     GL11.glEnable(3089);
/*  68 */     RenderUtil.doGlScissor(x, y, width, height);
/*  69 */     GL11.glPushMatrix();
/*  70 */     buffer.framebufferRenderExt(mc.displayWidth, mc.displayHeight, true);
/*  71 */     GL11.glPopMatrix();
/*  72 */     GL11.glDisable(3089);
/*     */     
/*  74 */     if (setupOverlay) {
/*  75 */       mc.entityRenderer.setupOverlayRendering();
/*     */     }
/*     */     
/*  78 */     GlStateManager.enableDepth();
/*     */   }
/*     */   
/*     */   public static void blurRoundArea(float x, float y, float width, float height, float roundRadius, boolean setupOverlay) {
/*  82 */     if (!blurEnabled()) {
/*     */       return;
/*     */     }
/*  85 */     ScaledResolution scale = new ScaledResolution(mc);
/*  86 */     float factor = scale.getScaleFactor();
/*  87 */     int factor2 = scale.getScaledWidth();
/*  88 */     int factor3 = scale.getScaledHeight();
/*     */     
/*  90 */     if (lastScale != factor || lastScaleWidth != factor2 || lastScaleHeight != factor3 || buffer == null || blurShader == null) {
/*  91 */       initFboAndShader();
/*     */     }
/*     */     
/*  94 */     lastScale = factor;
/*  95 */     lastScaleWidth = factor2;
/*  96 */     lastScaleHeight = factor3;
/*     */ 
/*     */     
/*  99 */     GL11.glEnable(3089);
/* 100 */     RenderUtil.doGlScissor(x, y, width, height);
/* 101 */     GL11.glPushMatrix();
/* 102 */     buffer.framebufferRenderExt(mc.displayWidth, mc.displayHeight, true);
/* 103 */     GL11.glPopMatrix();
/* 104 */     GL11.glDisable(3089);
/*     */     
/* 106 */     if (setupOverlay) {
/* 107 */       mc.entityRenderer.setupOverlayRendering();
/*     */     }
/*     */     
/* 110 */     GlStateManager.enableDepth();
/*     */   }
/*     */   
/*     */   public static void updateBlurBuffer(boolean setupOverlay) {
/* 114 */     if (!blurEnabled()) {
/*     */       return;
/*     */     }
/*     */     
/* 118 */     if (updateTimer.delay(16.666666F, true) && blurShader != null) {
/* 119 */       mc.getFramebuffer().unbindFramebuffer();
/*     */       
/* 121 */       setShaderConfigs(50.0F, 0.0F, 1.0F);
/* 122 */       buffer.bindFramebuffer(true);
/*     */       
/* 124 */       mc.getFramebuffer().framebufferRenderExt(mc.displayWidth, mc.displayHeight, true);
/*     */       
/* 126 */       if (OpenGlHelper.shadersSupported) {
/* 127 */         GlStateManager.matrixMode(5890);
/* 128 */         GlStateManager.pushMatrix();
/* 129 */         GlStateManager.loadIdentity();
/* 130 */         blurShader.loadShaderGroup(mc.timer.renderPartialTicks);
/* 131 */         GlStateManager.popMatrix();
/*     */       } 
/*     */       
/* 134 */       buffer.unbindFramebuffer();
/* 135 */       mc.getFramebuffer().bindFramebuffer(true);
/*     */       
/* 137 */       if (mc.getFramebuffer() != null && (mc.getFramebuffer()).depthBuffer > -1) {
/* 138 */         setupFBO(mc.getFramebuffer());
/* 139 */         (mc.getFramebuffer()).depthBuffer = -1;
/*     */       } 
/*     */       
/* 142 */       if (setupOverlay) {
/* 143 */         mc.entityRenderer.setupOverlayRendering();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void setupFBO(Framebuffer fbo) {
/* 149 */     EXTFramebufferObject.glDeleteRenderbuffersEXT(fbo.depthBuffer);
/* 150 */     int stencil_depth_buffer_ID = EXTFramebufferObject.glGenRenderbuffersEXT();
/* 151 */     EXTFramebufferObject.glBindRenderbufferEXT(36161, stencil_depth_buffer_ID);
/* 152 */     EXTFramebufferObject.glRenderbufferStorageEXT(36161, 34041, mc.displayWidth, mc.displayHeight);
/* 153 */     EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36128, 36161, stencil_depth_buffer_ID);
/* 154 */     EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36096, 36161, stencil_depth_buffer_ID);
/*     */   }
/*     */   
/*     */   public static boolean isFastRenderEnabled() {
/* 158 */     return Config.isFastRender();
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean blurEnabled() {
/* 163 */     if (isFastRenderEnabled()) {
/* 164 */       return false;
/*     */     }
/* 166 */     if (HUD.getInstance.isEnabled() && ((Boolean)HUD.getInstance.blur.get()).booleanValue() && OpenGlHelper.shadersSupported && 
/* 167 */       Minecraft.getMinecraft().getRenderViewEntity() instanceof net.minecraft.entity.player.EntityPlayer) {
/* 168 */       return true;
/*     */     }
/* 170 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\gui\BlurBuffer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */