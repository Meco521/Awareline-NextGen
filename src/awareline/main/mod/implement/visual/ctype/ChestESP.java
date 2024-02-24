/*     */ package awareline.main.mod.implement.visual.ctype;
/*     */ 
/*     */ import awareline.main.event.EventHandler;
/*     */ import awareline.main.event.events.world.renderEvents.EventRender2D;
/*     */ import awareline.main.event.events.world.renderEvents.EventRender3D;
/*     */ import awareline.main.mod.Module;
/*     */ import awareline.main.mod.ModuleType;
/*     */ import awareline.main.mod.implement.visual.HUD;
/*     */ import awareline.main.mod.values.Mode;
/*     */ import awareline.main.mod.values.Value;
/*     */ import awareline.main.ui.gui.clickgui.RenderUtil;
/*     */ import java.awt.Color;
/*     */ import java.nio.FloatBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.stream.Collectors;
/*     */ import javax.vecmath.Vector3d;
/*     */ import javax.vecmath.Vector4d;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.client.renderer.GLAllocation;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.client.shader.Framebuffer;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import org.lwjgl.opengl.Display;
/*     */ import org.lwjgl.opengl.EXTFramebufferObject;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.lwjgl.util.glu.GLU;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChestESP
/*     */   extends Module
/*     */ {
/*  42 */   public final Mode<String> mode = new Mode("Mode", new String[] { "Outline", "Filled", "Chams", "Box" }, "Outline"); public static ChestESP getInstance; private final IntBuffer viewport; private final FloatBuffer modelView;
/*     */   private final FloatBuffer projection;
/*     */   private final FloatBuffer vector;
/*     */   
/*  46 */   public ChestESP() { super("ChestESP", ModuleType.Render);
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
/* 184 */     this.viewport = GLAllocation.createDirectIntBuffer(16);
/* 185 */     this.modelView = GLAllocation.createDirectFloatBuffer(16);
/* 186 */     this.projection = GLAllocation.createDirectFloatBuffer(16);
/* 187 */     this.vector = GLAllocation.createDirectFloatBuffer(4); addSettings(new Value[] { (Value)this.mode }); getInstance = this; }
/*     */   private void renderOutline(TileEntity tileEntity) { double posX = tileEntity.getPos().getX(), posY = tileEntity.getPos().getY(), posZ = tileEntity.getPos().getZ(); AxisAlignedBB axisAlignedBB = null; Block block = mc.theWorld.getBlockState(new BlockPos(posX, posY, posZ)).getBlock(); Block x1 = mc.theWorld.getBlockState(new BlockPos(posX + 1.0D, posY, posZ)).getBlock(); Block x2 = mc.theWorld.getBlockState(new BlockPos(posX - 1.0D, posY, posZ)).getBlock(); Block z1 = mc.theWorld.getBlockState(new BlockPos(posX, posY, posZ + 1.0D)).getBlock(); Block z2 = mc.theWorld.getBlockState(new BlockPos(posX, posY, posZ - 1.0D)).getBlock(); if (x1 == block) { axisAlignedBB = renderOutlineZero(posX, posY, posZ); } else if (z2 == block) { axisAlignedBB = renderOutlineFirst(posX, posY, posZ); } else if (x2 != block && z1 != block) { axisAlignedBB = renderOutlineSecond(posX, posY, posZ); }  GlStateManager.disableAlpha(); GlStateManager.enableBlend(); GlStateManager.blendFunc(770, 771); GlStateManager.disableTexture2D(); GlStateManager.disableDepth(); GL11.glEnable(2848); float[] colors = getColorForTileEntity(); RenderHelper.drawCompleteBoxFilled(axisAlignedBB, 1.0F, toRGBAHex(colors[0] / 255.0F, colors[1] / 255.0F, colors[2] / 255.0F, 0.2F)); GL11.glDisable(2848); GlStateManager.enableDepth(); GlStateManager.enableTexture2D(); GlStateManager.enableBlend(); GlStateManager.enableAlpha(); }
/*     */   private AxisAlignedBB renderOutlineSecond(double posX, double posY, double posZ) { return new AxisAlignedBB(posX + 0.05000000074505806D - (mc.getRenderManager()).renderPosX, posY - (mc.getRenderManager()).renderPosY, posZ + 0.05000000074505806D - (mc.getRenderManager()).renderPosZ, posX + 0.949999988079071D - (mc.getRenderManager()).renderPosX, posY + 0.8999999761581421D - (mc.getRenderManager()).renderPosY, posZ + 0.949999988079071D - (mc.getRenderManager()).renderPosZ); }
/*     */   private AxisAlignedBB renderOutlineFirst(double posX, double posY, double posZ) { return new AxisAlignedBB(posX + 0.05000000074505806D - (mc.getRenderManager()).renderPosX, posY - (mc.getRenderManager()).renderPosY, posZ + 0.05000000074505806D - (mc.getRenderManager()).renderPosZ - 1.0D, posX + 0.949999988079071D - (mc.getRenderManager()).renderPosX, posY + 0.8999999761581421D - (mc.getRenderManager()).renderPosY, posZ + 0.949999988079071D - (mc.getRenderManager()).renderPosZ); }
/* 191 */   private AxisAlignedBB renderOutlineZero(double posX, double posY, double posZ) { return new AxisAlignedBB(posX + 0.05000000074505806D - (mc.getRenderManager()).renderPosX, posY - (mc.getRenderManager()).renderPosY, posZ + 0.05000000074505806D - (mc.getRenderManager()).renderPosZ, posX + 1.9500000476837158D - (mc.getRenderManager()).renderPosX, posY + 0.8999999761581421D - (mc.getRenderManager()).renderPosY, posZ + 0.949999988079071D - (mc.getRenderManager()).renderPosZ); } private Vector3d project2D(ScaledResolution scaledResolution, double x, double y, double z) { GL11.glGetFloat(2982, this.modelView);
/* 192 */     GL11.glGetFloat(2983, this.projection);
/* 193 */     GL11.glGetInteger(2978, this.viewport);
/*     */     
/* 195 */     if (GLU.gluProject((float)x, (float)y, (float)z, this.modelView, this.projection, this.viewport, this.vector))
/*     */     {
/* 197 */       return new Vector3d((this.vector.get(0) / scaledResolution.getScaleFactor()), ((
/* 198 */           Display.getHeight() - this.vector.get(1)) / scaledResolution.getScaleFactor()), this.vector.get(2));
/*     */     }
/*     */     
/* 201 */     return null; }
/*     */   public float[] getColorForTileEntity() { Color color = new Color(((Double)HUD.r.get()).intValue(), ((Double)HUD.g.get()).intValue(), ((Double)HUD.b.get()).intValue()); return new float[] { color.getRed(), color.getGreen(), color.getBlue(), 200.0F }; }
/*     */   public int toRGBAHex(float r, float g, float b, float a) { return ((int)(a * 255.0F) & 0xFF) << 24 | ((int)(r * 255.0F) & 0xFF) << 16 | ((int)(g * 255.0F) & 0xFF) << 8 | (int)(b * 255.0F) & 0xFF; }
/*     */   @EventHandler(3) public void onRender3D(EventRender3D event) { if (this.mode.is("Filled")) for (TileEntity tileEntity : mc.theWorld.getLoadedTileEntityList()) { if ((tileEntity instanceof net.minecraft.tileentity.TileEntityChest || tileEntity instanceof net.minecraft.tileentity.TileEntityEnderChest) && !tileEntity.isInvalid() && mc.theWorld.getBlockState(tileEntity.getPos()) != null) renderOutline(tileEntity);  }   }
/* 205 */   @EventHandler(3) public void on2D(EventRender2D event) { if (this.mode.is("Box")) for (TileEntity collectedEntity : mc.theWorld.getLoadedTileEntityList().stream().filter(e -> e instanceof net.minecraft.tileentity.TileEntityChest).collect(Collectors.toList())) { BlockPos pos = collectedEntity.getPos(); AxisAlignedBB aabb = new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), (pos.getX() + 1), (pos.getY() + 1), (pos.getZ() + 1)); List<Vector3d> vectors = Arrays.asList(new Vector3d[] { new Vector3d(aabb.minX, aabb.minY, aabb.minZ), new Vector3d(aabb.minX, aabb.maxY, aabb.minZ), new Vector3d(aabb.maxX, aabb.minY, aabb.minZ), new Vector3d(aabb.maxX, aabb.maxY, aabb.minZ), new Vector3d(aabb.minX, aabb.minY, aabb.maxZ), new Vector3d(aabb.minX, aabb.maxY, aabb.maxZ), new Vector3d(aabb.maxX, aabb.minY, aabb.maxZ), new Vector3d(aabb.maxX, aabb.maxY, aabb.maxZ) }); mc.entityRenderer.setupCameraTransform(event.getPartialTicks(), 0); Vector4d position = null; for (Vector3d vector : vectors) { vector = project2D(event.getResolution(), vector.x - (mc.getRenderManager()).viewerPosX, vector.y - (mc.getRenderManager()).viewerPosY, vector.z - (mc.getRenderManager()).viewerPosZ); if (vector != null && vector.z >= 0.0D && vector.z < 1.0D) { if (position == null) position = new Vector4d(vector.x, vector.y, vector.z, 0.0D);  position.x = Math.min(vector.x, position.x); position.y = Math.min(vector.y, position.y); position.z = Math.max(vector.x, position.z); position.w = Math.max(vector.y, position.w); }  }  mc.entityRenderer.setupOverlayRendering(); if (position != null) { double posX = position.x; double posY = position.y; double endPosX = position.z; double endPosY = position.w; RenderUtil.drawCornerBox(posX, posY, endPosX, endPosY, 3.0D, Color.BLACK); RenderUtil.drawCornerBox(posX, posY, endPosX, endPosY, 1.0D, new Color(((Double)HUD.r.get()).intValue(), ((Double)HUD.g.get()).intValue(), ((Double)HUD.b.get()).intValue())); }  }   } public void pre3D() { checkSetupFBO();
/* 206 */     GL11.glPushAttrib(1048575);
/* 207 */     GL11.glDisable(3008);
/* 208 */     GL11.glDisable(3553);
/* 209 */     GL11.glDisable(2896);
/* 210 */     GL11.glEnable(3042);
/* 211 */     GL11.glBlendFunc(770, 771);
/* 212 */     GL11.glLineWidth(3.0F);
/* 213 */     GL11.glEnable(2848);
/* 214 */     GL11.glEnable(2960);
/* 215 */     GL11.glClear(1024);
/* 216 */     GL11.glClearStencil(15);
/* 217 */     GL11.glStencilFunc(512, 1, 15);
/* 218 */     GL11.glStencilOp(7681, 7681, 7681);
/* 219 */     GL11.glPolygonMode(1032, 6913); }
/*     */ 
/*     */   
/*     */   public void checkSetupFBO() {
/* 223 */     Framebuffer framebuffer = mc.getFramebuffer();
/*     */     
/* 225 */     if (framebuffer != null && framebuffer.depthBuffer > -1) {
/* 226 */       setupFBO(framebuffer);
/*     */       
/* 228 */       framebuffer.depthBuffer = -1;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setupFBO(Framebuffer fbo) {
/* 233 */     EXTFramebufferObject.glDeleteRenderbuffersEXT(fbo.depthBuffer);
/* 234 */     int stencilDepthBufferId = EXTFramebufferObject.glGenRenderbuffersEXT();
/* 235 */     EXTFramebufferObject.glBindRenderbufferEXT(36161, stencilDepthBufferId);
/* 236 */     EXTFramebufferObject.glRenderbufferStorageEXT(36161, 34041, mc.displayWidth, mc.displayHeight);
/* 237 */     EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36128, 36161, stencilDepthBufferId);
/* 238 */     EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36096, 36161, stencilDepthBufferId);
/*     */   }
/*     */   
/*     */   public void setupStencil() {
/* 242 */     GL11.glStencilFunc(512, 0, 15);
/* 243 */     GL11.glStencilOp(7681, 7681, 7681);
/* 244 */     GL11.glPolygonMode(1032, 6914);
/*     */   }
/*     */   
/*     */   public void setupStencil2() {
/* 248 */     GL11.glStencilFunc(514, 1, 15);
/* 249 */     GL11.glStencilOp(7680, 7680, 7680);
/* 250 */     GL11.glPolygonMode(1032, 6913);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setupStencilFirst() {
/* 255 */     GL11.glStencilFunc(512, 0, 15);
/* 256 */     GL11.glStencilOp(7681, 7681, 7681);
/* 257 */     GL11.glPolygonMode(1032, 6914);
/*     */   }
/*     */   
/*     */   public void setupStencilSecond() {
/* 261 */     GL11.glStencilFunc(514, 1, 15);
/* 262 */     GL11.glStencilOp(7680, 7680, 7680);
/* 263 */     GL11.glPolygonMode(1032, 6913);
/*     */   }
/*     */   
/*     */   public void renderOutline(int color) {
/* 267 */     setColor(color);
/* 268 */     GL11.glDepthMask(false);
/* 269 */     GL11.glDisable(2929);
/* 270 */     GL11.glEnable(10754);
/* 271 */     GL11.glPolygonOffset(1.0F, -2000000.0F);
/* 272 */     OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
/*     */   }
/*     */   
/*     */   public void setColor(int i) {
/* 276 */     float f = (i >> 24 & 0xFF) / 255.0F;
/* 277 */     float f0 = (i >> 16 & 0xFF) / 255.0F;
/* 278 */     float f1 = (i >> 8 & 0xFF) / 255.0F;
/* 279 */     float f2 = (i & 0xFF) / 255.0F;
/* 280 */     if (f == 0.0F) {
/* 281 */       f = 1.0F;
/*     */     }
/* 283 */     GL11.glColor4f(f0, f1, f2, f);
/*     */   }
/*     */   
/*     */   public void post3D() {
/* 287 */     GL11.glPolygonOffset(1.0F, 2000000.0F);
/* 288 */     GL11.glDisable(10754);
/* 289 */     GL11.glEnable(2929);
/* 290 */     GL11.glDepthMask(true);
/* 291 */     GL11.glDisable(2960);
/* 292 */     GL11.glDisable(2848);
/* 293 */     GL11.glHint(3154, 4352);
/* 294 */     GL11.glEnable(3042);
/* 295 */     GL11.glEnable(2896);
/* 296 */     GL11.glEnable(3553);
/* 297 */     GL11.glEnable(3008);
/* 298 */     GL11.glPopAttrib();
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\visual\ctype\ChestESP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */