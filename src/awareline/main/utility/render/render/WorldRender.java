/*     */ package awareline.main.utility.render.render;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.client.shader.Framebuffer;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import org.lwjgl.opengl.EXTFramebufferObject;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WorldRender
/*     */ {
/*  26 */   public static Minecraft mc = Minecraft.getMinecraft();
/*     */   
/*     */   public static List<Entity> getEntities() {
/*  29 */     return Arrays.asList((Entity[])mc.theWorld.loadedEntityList.stream().filter(entity -> (entity != mc.thePlayer)).toArray(x$0 -> new Entity[x$0]));
/*     */   }
/*     */   
/*     */   public static int getScaledHeight() {
/*  33 */     ScaledResolution scaledResolution = new ScaledResolution(mc);
/*  34 */     return scaledResolution.getScaledHeight();
/*     */   }
/*     */   
/*     */   public static int getScaledWidth() {
/*  38 */     ScaledResolution scaledResolution = new ScaledResolution(mc);
/*  39 */     return scaledResolution.getScaledWidth();
/*     */   }
/*     */   
/*     */   public static void renderOne() {
/*  43 */     checkSetupFBO();
/*  44 */     GL11.glPushAttrib(1048575);
/*  45 */     GL11.glDisable(3008);
/*  46 */     GL11.glDisable(3553);
/*  47 */     GL11.glDisable(2896);
/*  48 */     GL11.glEnable(3042);
/*  49 */     GL11.glBlendFunc(770, 771);
/*  50 */     GL11.glLineWidth(3.0F);
/*  51 */     GL11.glEnable(2848);
/*  52 */     GL11.glEnable(2960);
/*  53 */     GL11.glClear(1024);
/*  54 */     GL11.glClearStencil(15);
/*  55 */     GL11.glStencilFunc(512, 1, 15);
/*  56 */     GL11.glStencilOp(7681, 7681, 7681);
/*  57 */     GL11.glPolygonMode(1032, 6913);
/*     */   }
/*     */   
/*     */   public static void checkSetupFBO() {
/*  61 */     Framebuffer fbo = mc.getFramebuffer();
/*  62 */     if (fbo != null && fbo.depthBuffer > -1) {
/*  63 */       setupFBO(fbo);
/*  64 */       fbo.depthBuffer = -1;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void setupFBO(Framebuffer fbo) {
/*  69 */     EXTFramebufferObject.glDeleteRenderbuffersEXT(fbo.depthBuffer);
/*  70 */     int stencil_depth_buffer_ID = EXTFramebufferObject.glGenRenderbuffersEXT();
/*  71 */     EXTFramebufferObject.glBindRenderbufferEXT(36161, stencil_depth_buffer_ID);
/*  72 */     EXTFramebufferObject.glRenderbufferStorageEXT(36161, 34041, mc.displayWidth, mc.displayHeight);
/*  73 */     EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36128, 36161, stencil_depth_buffer_ID);
/*  74 */     EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36096, 36161, stencil_depth_buffer_ID);
/*     */   }
/*     */   
/*     */   public static void renderTwo() {
/*  78 */     GL11.glStencilFunc(512, 0, 15);
/*  79 */     GL11.glStencilOp(7681, 7681, 7681);
/*  80 */     GL11.glPolygonMode(1032, 6914);
/*     */   }
/*     */   
/*     */   public static void renderThree() {
/*  84 */     GL11.glStencilFunc(514, 1, 15);
/*  85 */     GL11.glStencilOp(7680, 7680, 7680);
/*  86 */     GL11.glPolygonMode(1032, 6913);
/*     */   }
/*     */   
/*     */   public static void renderFour(int color) {
/*  90 */     setColor(color);
/*  91 */     GL11.glDepthMask(false);
/*  92 */     GL11.glDisable(2929);
/*  93 */     GL11.glEnable(10754);
/*  94 */     GL11.glPolygonOffset(1.0F, -2000000.0F);
/*  95 */     OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
/*     */   }
/*     */   
/*     */   public static void renderFive() {
/*  99 */     GL11.glPolygonOffset(1.0F, 2000000.0F);
/* 100 */     GL11.glDisable(10754);
/* 101 */     GL11.glEnable(2929);
/* 102 */     GL11.glDepthMask(true);
/* 103 */     GL11.glDisable(2960);
/* 104 */     GL11.glDisable(2848);
/* 105 */     GL11.glHint(3154, 4352);
/* 106 */     GL11.glEnable(3042);
/* 107 */     GL11.glEnable(2896);
/* 108 */     GL11.glEnable(3553);
/* 109 */     GL11.glEnable(3008);
/* 110 */     GL11.glPopAttrib();
/*     */   }
/*     */   
/*     */   public static void drawBoundingBox(AxisAlignedBB aa) {
/* 114 */     Tessellator tessellator = Tessellator.getInstance();
/* 115 */     WorldRenderer worldRenderer = tessellator.getWorldRenderer();
/* 116 */     worldRenderer.begin(7, DefaultVertexFormats.POSITION);
/* 117 */     worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
/* 118 */     worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
/* 119 */     worldRenderer.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
/* 120 */     worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
/* 121 */     worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
/* 122 */     worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
/* 123 */     worldRenderer.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
/* 124 */     worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
/* 125 */     tessellator.draw();
/* 126 */     worldRenderer.begin(7, DefaultVertexFormats.POSITION);
/* 127 */     worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
/* 128 */     worldRenderer.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
/* 129 */     worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
/* 130 */     worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
/* 131 */     worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
/* 132 */     worldRenderer.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
/* 133 */     worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
/* 134 */     worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
/* 135 */     tessellator.draw();
/* 136 */     worldRenderer.begin(7, DefaultVertexFormats.POSITION);
/* 137 */     worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
/* 138 */     worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
/* 139 */     worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
/* 140 */     worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
/* 141 */     worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
/* 142 */     worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
/* 143 */     worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
/* 144 */     worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
/* 145 */     tessellator.draw();
/* 146 */     worldRenderer.begin(7, DefaultVertexFormats.POSITION);
/* 147 */     worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
/* 148 */     worldRenderer.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
/* 149 */     worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
/* 150 */     worldRenderer.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
/* 151 */     worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
/* 152 */     worldRenderer.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
/* 153 */     worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
/* 154 */     worldRenderer.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
/* 155 */     tessellator.draw();
/* 156 */     worldRenderer.begin(7, DefaultVertexFormats.POSITION);
/* 157 */     worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
/* 158 */     worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
/* 159 */     worldRenderer.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
/* 160 */     worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
/* 161 */     worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
/* 162 */     worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
/* 163 */     worldRenderer.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
/* 164 */     worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
/* 165 */     tessellator.draw();
/* 166 */     worldRenderer.begin(7, DefaultVertexFormats.POSITION);
/* 167 */     worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
/* 168 */     worldRenderer.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
/* 169 */     worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
/* 170 */     worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
/* 171 */     worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
/* 172 */     worldRenderer.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
/* 173 */     worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
/* 174 */     worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
/* 175 */     tessellator.draw();
/*     */   }
/*     */   
/*     */   public static void setColor(int colorHex) {
/* 179 */     float alpha = (colorHex >> 24 & 0xFF) / 255.0F;
/* 180 */     float red = (colorHex >> 16 & 0xFF) / 255.0F;
/* 181 */     float green = (colorHex >> 8 & 0xFF) / 255.0F;
/* 182 */     float blue = (colorHex & 0xFF) / 255.0F;
/* 183 */     GL11.glColor4f(red, green, blue, (alpha == 0.0F) ? 1.0F : alpha);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\utility\render\render\WorldRender.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */