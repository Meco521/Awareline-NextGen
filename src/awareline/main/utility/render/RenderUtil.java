/*     */ package awareline.main.utility.render;
/*     */ 
/*     */ import awareline.main.Client;
/*     */ import awareline.main.event.events.world.renderEvents.EventRender3D;
/*     */ import awareline.main.mod.implement.visual.HUD;
/*     */ import awareline.main.ui.gui.BlurBuffer;
/*     */ import awareline.main.utility.Utils;
/*     */ import awareline.main.utility.render.gl.GLClientState;
/*     */ import java.awt.Color;
/*     */ import java.nio.FloatBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.function.Consumer;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.Gui;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.culling.Frustum;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.Vec3;
/*     */ import org.lwjgl.BufferUtils;
/*     */ import org.lwjgl.opengl.Display;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.lwjgl.util.glu.GLU;
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
/*     */ public class RenderUtil
/*     */   implements Utils
/*     */ {
/*  46 */   private static final List<Integer> csBuffer = new ArrayList<>();
/*  47 */   private static final Consumer<Integer> ENABLE_CLIENT_STATE = GL11::glEnableClientState;
/*  48 */   private static final Consumer<Integer> DISABLE_CLIENT_STATE = GL11::glEnableClientState;
/*     */ 
/*     */   
/*     */   public static int width() {
/*  52 */     ScaledResolution sr = new ScaledResolution(mc);
/*  53 */     return sr.getScaledWidth();
/*     */   }
/*     */   
/*     */   public static int height() {
/*  57 */     ScaledResolution sr = new ScaledResolution(mc);
/*  58 */     return sr.getScaledHeight();
/*     */   }
/*     */   
/*     */   public static void pre3D() {
/*  62 */     GL11.glPushMatrix();
/*  63 */     GL11.glEnable(3042);
/*  64 */     GL11.glBlendFunc(770, 771);
/*  65 */     GL11.glShadeModel(7425);
/*  66 */     GL11.glDisable(3553);
/*  67 */     GL11.glEnable(2848);
/*  68 */     GL11.glDisable(2929);
/*  69 */     GL11.glDisable(2896);
/*  70 */     GL11.glDepthMask(false);
/*  71 */     GL11.glHint(3154, 4354);
/*     */   }
/*     */   
/*     */   public static void drawESP(Entity entity, int color) {
/*  75 */     double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * mc.timer.renderPartialTicks;
/*  76 */     double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * mc.timer.renderPartialTicks;
/*  77 */     double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * mc.timer.renderPartialTicks;
/*  78 */     double width = Math.abs((entity.getEntityBoundingBox()).maxX - (entity.getEntityBoundingBox()).minX);
/*  79 */     double height = Math.abs((entity.getEntityBoundingBox()).maxY - (entity.getEntityBoundingBox()).minY);
/*  80 */     Vec3 vec = new Vec3(x - width / 2.0D, y, z - width / 2.0D);
/*  81 */     Vec3 vec2 = new Vec3(x + width / 2.0D, y + height, z + width / 2.0D);
/*  82 */     pre3D();
/*  83 */     mc.entityRenderer.setupCameraTransform(mc.timer.renderPartialTicks, 2);
/*  84 */     RenderUtils.glColor(color);
/*  85 */     drawBoundingBox(new AxisAlignedBB(vec.xCoord - (mc.getRenderManager()).renderPosX, vec.yCoord - (mc.getRenderManager()).renderPosY, vec.zCoord - (mc.getRenderManager()).renderPosZ, vec2.xCoord - (mc.getRenderManager()).renderPosX, vec2.yCoord - (mc.getRenderManager()).renderPosY, vec2.zCoord - (mc.getRenderManager()).renderPosZ));
/*  86 */     GL11.glColor4f(0.0F, 0.0F, 0.0F, 1.0F);
/*  87 */     post3D();
/*     */   }
/*     */   
/*     */   public static void drawPath(Vec3 vec) {
/*  91 */     double x = vec.xCoord - (mc.getRenderManager()).renderPosX;
/*  92 */     double y = vec.yCoord - (mc.getRenderManager()).renderPosY;
/*  93 */     double z = vec.zCoord - (mc.getRenderManager()).renderPosZ;
/*  94 */     double width = 0.3D;
/*  95 */     double height = mc.thePlayer.getEyeHeight();
/*  96 */     pre3D();
/*  97 */     GL11.glLoadIdentity();
/*  98 */     mc.entityRenderer.setupCameraTransform(mc.timer.renderPartialTicks, 2);
/*  99 */     RenderUtils.glColor((new Color(((Double)HUD.r.get()).intValue(), ((Double)HUD.g.get()).intValue(), ((Double)HUD.b.get()).intValue())).getRGB());
/* 100 */     GL11.glLineWidth(2.0F);
/* 101 */     GL11.glBegin(3);
/* 102 */     GL11.glVertex3d(x - 0.3D, y, z - 0.3D);
/* 103 */     GL11.glVertex3d(x - 0.3D, y, z - 0.3D);
/* 104 */     GL11.glVertex3d(x - 0.3D, y + height, z - 0.3D);
/* 105 */     GL11.glVertex3d(x + 0.3D, y + height, z - 0.3D);
/* 106 */     GL11.glVertex3d(x + 0.3D, y, z - 0.3D);
/* 107 */     GL11.glVertex3d(x - 0.3D, y, z - 0.3D);
/* 108 */     GL11.glVertex3d(x - 0.3D, y, z + 0.3D);
/* 109 */     GL11.glEnd();
/* 110 */     GL11.glBegin(3);
/* 111 */     GL11.glVertex3d(x + 0.3D, y, z + 0.3D);
/* 112 */     GL11.glVertex3d(x + 0.3D, y + height, z + 0.3D);
/* 113 */     GL11.glVertex3d(x - 0.3D, y + height, z + 0.3D);
/* 114 */     GL11.glVertex3d(x - 0.3D, y, z + 0.3D);
/* 115 */     GL11.glVertex3d(x + 0.3D, y, z + 0.3D);
/* 116 */     GL11.glVertex3d(x + 0.3D, y, z - 0.3D);
/* 117 */     GL11.glEnd();
/* 118 */     GL11.glBegin(3);
/* 119 */     GL11.glVertex3d(x + 0.3D, y + height, z + 0.3D);
/* 120 */     GL11.glVertex3d(x + 0.3D, y + height, z - 0.3D);
/* 121 */     GL11.glEnd();
/* 122 */     GL11.glBegin(3);
/* 123 */     GL11.glVertex3d(x - 0.3D, y + height, z + 0.3D);
/* 124 */     GL11.glVertex3d(x - 0.3D, y + height, z - 0.3D);
/* 125 */     GL11.glEnd();
/* 126 */     post3D();
/*     */   }
/*     */   
/*     */   public static void post3D() {
/* 130 */     GL11.glDepthMask(true);
/* 131 */     GL11.glEnable(2929);
/* 132 */     GL11.glDisable(2848);
/* 133 */     GL11.glEnable(3553);
/* 134 */     GL11.glDisable(3042);
/* 135 */     GL11.glPopMatrix();
/* 136 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void drawBorderedRect(double x, double y, double x2, double y2, double l1, int col1, int col2) {
/* 142 */     Gui.drawRect(x, y, x2, y2, col2);
/* 143 */     float f = (col1 >> 24 & 0xFF) / 255.0F;
/* 144 */     float f2 = (col1 >> 16 & 0xFF) / 255.0F;
/* 145 */     float f3 = (col1 >> 8 & 0xFF) / 255.0F;
/* 146 */     float f4 = (col1 & 0xFF) / 255.0F;
/* 147 */     GL11.glEnable(3042);
/* 148 */     GL11.glDisable(3553);
/* 149 */     GL11.glBlendFunc(770, 771);
/* 150 */     GL11.glEnable(2848);
/* 151 */     GL11.glPushMatrix();
/* 152 */     GL11.glColor4f(f2, f3, f4, f);
/* 153 */     GL11.glLineWidth((float)l1);
/* 154 */     GL11.glBegin(1);
/* 155 */     GL11.glVertex2d(x, y);
/* 156 */     GL11.glVertex2d(x, y2);
/* 157 */     GL11.glVertex2d(x2, y2);
/* 158 */     GL11.glVertex2d(x2, y);
/* 159 */     GL11.glVertex2d(x, y);
/* 160 */     GL11.glVertex2d(x2, y);
/* 161 */     GL11.glVertex2d(x, y2);
/* 162 */     GL11.glVertex2d(x2, y2);
/* 163 */     GL11.glEnd();
/* 164 */     GL11.glPopMatrix();
/* 165 */     GL11.glEnable(3553);
/* 166 */     GL11.glDisable(3042);
/* 167 */     GL11.glDisable(2848);
/*     */   }
/*     */   
/*     */   public static void drawBorderedRect(float x, float y, float x2, float y2, float l1, int col1, int col2) {
/* 171 */     Gui.drawRect(x, y, x2, y2, col2);
/* 172 */     float f = (col1 >> 24 & 0xFF) / 255.0F;
/* 173 */     float f2 = (col1 >> 16 & 0xFF) / 255.0F;
/* 174 */     float f3 = (col1 >> 8 & 0xFF) / 255.0F;
/* 175 */     float f4 = (col1 & 0xFF) / 255.0F;
/* 176 */     GL11.glEnable(3042);
/* 177 */     GL11.glDisable(3553);
/* 178 */     GL11.glBlendFunc(770, 771);
/* 179 */     GL11.glEnable(2848);
/* 180 */     GL11.glPushMatrix();
/* 181 */     GL11.glColor4f(f2, f3, f4, f);
/* 182 */     GL11.glLineWidth(l1);
/* 183 */     GL11.glBegin(1);
/* 184 */     GL11.glVertex2d(x, y);
/* 185 */     GL11.glVertex2d(x, y2);
/* 186 */     GL11.glVertex2d(x2, y2);
/* 187 */     GL11.glVertex2d(x2, y);
/* 188 */     GL11.glVertex2d(x, y);
/* 189 */     GL11.glVertex2d(x2, y);
/* 190 */     GL11.glVertex2d(x, y2);
/* 191 */     GL11.glVertex2d(x2, y2);
/* 192 */     GL11.glEnd();
/* 193 */     GL11.glPopMatrix();
/* 194 */     GL11.glEnable(3553);
/* 195 */     GL11.glDisable(3042);
/* 196 */     GL11.glDisable(2848);
/*     */   }
/*     */   
/*     */   public static void startDrawing() {
/* 200 */     GL11.glEnable(3042);
/* 201 */     GL11.glEnable(3042);
/* 202 */     GL11.glBlendFunc(770, 771);
/* 203 */     GL11.glEnable(2848);
/* 204 */     GL11.glDisable(3553);
/* 205 */     GL11.glDisable(2929);
/* 206 */     mc.entityRenderer.setupCameraTransform(mc.timer.renderPartialTicks, 0);
/*     */   }
/*     */   
/*     */   public static void stopDrawing() {
/* 210 */     GL11.glDisable(3042);
/* 211 */     GL11.glEnable(3553);
/* 212 */     GL11.glDisable(2848);
/* 213 */     GL11.glDisable(3042);
/* 214 */     GL11.glEnable(2929);
/*     */   }
/*     */   
/*     */   public static void drawOutlinedBoundingBox(AxisAlignedBB aa) {
/* 218 */     Tessellator tessellator = Tessellator.getInstance();
/* 219 */     WorldRenderer worldRenderer = tessellator.getWorldRenderer();
/* 220 */     worldRenderer.begin(3, DefaultVertexFormats.POSITION);
/* 221 */     worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
/* 222 */     worldRenderer.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
/* 223 */     worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
/* 224 */     worldRenderer.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
/* 225 */     worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
/* 226 */     tessellator.draw();
/* 227 */     worldRenderer.begin(3, DefaultVertexFormats.POSITION);
/* 228 */     worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
/* 229 */     worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
/* 230 */     worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
/* 231 */     worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
/* 232 */     worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
/* 233 */     tessellator.draw();
/* 234 */     worldRenderer.begin(1, DefaultVertexFormats.POSITION);
/* 235 */     worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
/* 236 */     worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
/* 237 */     worldRenderer.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
/* 238 */     worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
/* 239 */     worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
/* 240 */     worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
/* 241 */     worldRenderer.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
/* 242 */     worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
/* 243 */     tessellator.draw();
/*     */   }
/*     */   
/*     */   public static void drawBoundingBox(AxisAlignedBB aa) {
/* 247 */     Tessellator tessellator = Tessellator.getInstance();
/* 248 */     WorldRenderer worldRenderer = tessellator.getWorldRenderer();
/* 249 */     worldRenderer.begin(7, DefaultVertexFormats.POSITION);
/* 250 */     worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
/* 251 */     worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
/* 252 */     worldRenderer.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
/* 253 */     worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
/* 254 */     worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
/* 255 */     worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
/* 256 */     worldRenderer.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
/* 257 */     worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
/* 258 */     tessellator.draw();
/* 259 */     worldRenderer.begin(7, DefaultVertexFormats.POSITION);
/* 260 */     worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
/* 261 */     worldRenderer.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
/* 262 */     worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
/* 263 */     worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
/* 264 */     worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
/* 265 */     worldRenderer.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
/* 266 */     worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
/* 267 */     worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
/* 268 */     tessellator.draw();
/* 269 */     worldRenderer.begin(7, DefaultVertexFormats.POSITION);
/* 270 */     worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
/* 271 */     worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
/* 272 */     worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
/* 273 */     worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
/* 274 */     worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
/* 275 */     worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
/* 276 */     worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
/* 277 */     worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
/* 278 */     tessellator.draw();
/* 279 */     worldRenderer.begin(7, DefaultVertexFormats.POSITION);
/* 280 */     worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
/* 281 */     worldRenderer.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
/* 282 */     worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
/* 283 */     worldRenderer.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
/* 284 */     worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
/* 285 */     worldRenderer.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
/* 286 */     worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
/* 287 */     worldRenderer.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
/* 288 */     tessellator.draw();
/* 289 */     worldRenderer.begin(7, DefaultVertexFormats.POSITION);
/* 290 */     worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
/* 291 */     worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
/* 292 */     worldRenderer.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
/* 293 */     worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
/* 294 */     worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
/* 295 */     worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
/* 296 */     worldRenderer.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
/* 297 */     worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
/* 298 */     tessellator.draw();
/* 299 */     worldRenderer.begin(7, DefaultVertexFormats.POSITION);
/* 300 */     worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
/* 301 */     worldRenderer.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
/* 302 */     worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
/* 303 */     worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
/* 304 */     worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
/* 305 */     worldRenderer.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
/* 306 */     worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
/* 307 */     worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
/* 308 */     tessellator.draw();
/*     */   }
/*     */   
/*     */   public static void rectangleBordered(double x, double y, double x1, double y1, double width, int internalColor, int borderColor) {
/* 312 */     rectangle(x + width, y + width, x1 - width, y1 - width, internalColor);
/* 313 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 314 */     rectangle(x + width, y, x1 - width, y + width, borderColor);
/* 315 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 316 */     rectangle(x, y, x + width, y1, borderColor);
/* 317 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 318 */     rectangle(x1 - width, y, x1, y1, borderColor);
/* 319 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 320 */     rectangle(x + width, y1 - width, x1 - width, y1, borderColor);
/* 321 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void rectangle(double left, double top, double right, double bottom, int color) {
/* 326 */     if (left < right) {
/* 327 */       double var5 = left;
/* 328 */       left = right;
/* 329 */       right = var5;
/*     */     } 
/* 331 */     if (top < bottom) {
/* 332 */       double var5 = top;
/* 333 */       top = bottom;
/* 334 */       bottom = var5;
/*     */     } 
/* 336 */     float var11 = (color >> 24 & 0xFF) / 255.0F;
/* 337 */     float var6 = (color >> 16 & 0xFF) / 255.0F;
/* 338 */     float var7 = (color >> 8 & 0xFF) / 255.0F;
/* 339 */     float var8 = (color & 0xFF) / 255.0F;
/* 340 */     Tessellator tessellator = Tessellator.getInstance();
/* 341 */     WorldRenderer worldRenderer = tessellator.getWorldRenderer();
/* 342 */     GlStateManager.enableBlend();
/* 343 */     GlStateManager.disableTexture2D();
/* 344 */     GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 345 */     GlStateManager.color(var6, var7, var8, var11);
/* 346 */     worldRenderer.begin(7, DefaultVertexFormats.POSITION);
/* 347 */     worldRenderer.pos(left, bottom, 0.0D).endVertex();
/* 348 */     worldRenderer.pos(right, bottom, 0.0D).endVertex();
/* 349 */     worldRenderer.pos(right, top, 0.0D).endVertex();
/* 350 */     worldRenderer.pos(left, top, 0.0D).endVertex();
/* 351 */     tessellator.draw();
/* 352 */     GlStateManager.enableTexture2D();
/* 353 */     GlStateManager.disableBlend();
/* 354 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*     */   }
/*     */   
/*     */   public static void drawGradientSideways(double left, double top, double right, double bottom, int col1, int col2) {
/* 358 */     float f = (col1 >> 24 & 0xFF) / 255.0F;
/* 359 */     float f1 = (col1 >> 16 & 0xFF) / 255.0F;
/* 360 */     float f2 = (col1 >> 8 & 0xFF) / 255.0F;
/* 361 */     float f3 = (col1 & 0xFF) / 255.0F;
/* 362 */     float f4 = (col2 >> 24 & 0xFF) / 255.0F;
/* 363 */     float f5 = (col2 >> 16 & 0xFF) / 255.0F;
/* 364 */     float f6 = (col2 >> 8 & 0xFF) / 255.0F;
/* 365 */     float f7 = (col2 & 0xFF) / 255.0F;
/* 366 */     GL11.glEnable(3042);
/* 367 */     GL11.glDisable(3553);
/* 368 */     GL11.glBlendFunc(770, 771);
/* 369 */     GL11.glEnable(2848);
/* 370 */     GL11.glShadeModel(7425);
/* 371 */     GL11.glPushMatrix();
/* 372 */     GL11.glBegin(7);
/* 373 */     GL11.glColor4f(f1, f2, f3, f);
/* 374 */     GL11.glVertex2d(left, top);
/* 375 */     GL11.glVertex2d(left, bottom);
/* 376 */     GL11.glColor4f(f5, f6, f7, f4);
/* 377 */     GL11.glVertex2d(right, bottom);
/* 378 */     GL11.glVertex2d(right, top);
/* 379 */     GL11.glEnd();
/* 380 */     GL11.glPopMatrix();
/* 381 */     GL11.glEnable(3553);
/* 382 */     GL11.glDisable(3042);
/* 383 */     GL11.glDisable(2848);
/* 384 */     GL11.glShadeModel(7424);
/*     */   }
/*     */   
/*     */   public static void drawCircle(float cx, float cy, float r, int num_segments, int c) {
/* 388 */     GL11.glPushMatrix();
/* 389 */     cx *= 2.0F;
/* 390 */     cy *= 2.0F;
/* 391 */     float f = (c >> 24 & 0xFF) / 255.0F;
/* 392 */     float f1 = (c >> 16 & 0xFF) / 255.0F;
/* 393 */     float f2 = (c >> 8 & 0xFF) / 255.0F;
/* 394 */     float f3 = (c & 0xFF) / 255.0F;
/* 395 */     float theta = (float)(6.2831852D / num_segments);
/* 396 */     float p = (float)Math.cos(theta);
/* 397 */     float s = (float)Math.sin(theta);
/* 398 */     float x = r * 2.0F;
/* 399 */     float y = 0.0F;
/* 400 */     enableGL2D();
/* 401 */     GL11.glScalef(0.5F, 0.5F, 0.5F);
/* 402 */     GL11.glColor4f(f1, f2, f3, f);
/* 403 */     GL11.glBegin(2);
/* 404 */     int ii = 0;
/* 405 */     while (ii < num_segments) {
/* 406 */       GL11.glVertex2f(x + cx, y + cy);
/* 407 */       float t = x;
/* 408 */       x = p * x - s * y;
/* 409 */       y = s * t + p * y;
/* 410 */       ii++;
/*     */     } 
/* 412 */     GL11.glEnd();
/* 413 */     GL11.glScalef(2.0F, 2.0F, 2.0F);
/* 414 */     disableGL2D();
/* 415 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 416 */     GL11.glPopMatrix();
/*     */   }
/*     */   
/*     */   public static void enableGL2D() {
/* 420 */     GL11.glDisable(2929);
/* 421 */     GL11.glEnable(3042);
/* 422 */     GL11.glDisable(3553);
/* 423 */     GL11.glBlendFunc(770, 771);
/* 424 */     GL11.glDepthMask(true);
/* 425 */     GL11.glEnable(2848);
/* 426 */     GL11.glHint(3154, 4354);
/* 427 */     GL11.glHint(3155, 4354);
/*     */   }
/*     */   
/*     */   public static void disableGL2D() {
/* 431 */     GL11.glEnable(3553);
/* 432 */     GL11.glDisable(3042);
/* 433 */     GL11.glEnable(2929);
/* 434 */     GL11.glDisable(2848);
/* 435 */     GL11.glHint(3154, 4352);
/* 436 */     GL11.glHint(3155, 4352);
/*     */   }
/*     */   
/*     */   public static void drawEntityESPVapeMark(double x, double y, double z, double width, double height, float red, float green, float blue, float alpha, float lineRed, float lineGreen, float lineBlue, float lineAlpha, float lineWdith) {
/* 440 */     GL11.glPushMatrix();
/* 441 */     GL11.glEnable(3042);
/* 442 */     GL11.glBlendFunc(770, 771);
/* 443 */     GL11.glDisable(3553);
/* 444 */     GL11.glEnable(2848);
/* 445 */     GL11.glDisable(2929);
/* 446 */     GL11.glDepthMask(false);
/* 447 */     GL11.glColor4f(red, green, blue, alpha);
/* 448 */     drawBoundingBox(new AxisAlignedBB(x - width, y, z - width, x + width, y + height, z + width));
/* 449 */     GL11.glDisable(2848);
/* 450 */     GL11.glEnable(3553);
/* 451 */     GL11.glEnable(2929);
/* 452 */     GL11.glDepthMask(true);
/* 453 */     GL11.glDisable(3042);
/* 454 */     GL11.glPopMatrix();
/*     */   }
/*     */   
/*     */   public static void drawEntityESP(double x, double y, double z, double width, double height, float red, float green, float blue, float alpha, float lineRed, float lineGreen, float lineBlue, float lineAlpha, float lineWdith) {
/* 458 */     GL11.glPushMatrix();
/* 459 */     GL11.glEnable(3042);
/* 460 */     GL11.glBlendFunc(770, 771);
/* 461 */     GL11.glDisable(3553);
/* 462 */     GL11.glEnable(2848);
/* 463 */     GL11.glDisable(2929);
/* 464 */     GL11.glDepthMask(false);
/* 465 */     GL11.glColor4f(red, green, blue, alpha);
/* 466 */     drawBoundingBox(new AxisAlignedBB(x - width, y, z - width, x + width, y + height, z + width));
/* 467 */     GL11.glLineWidth(lineWdith);
/* 468 */     GL11.glColor4f(lineRed, lineGreen, lineBlue, lineAlpha);
/* 469 */     drawOutlinedBoundingBox(new AxisAlignedBB(x - width, y, z - width, x + width, y + height, z + width));
/* 470 */     GL11.glDisable(2848);
/* 471 */     GL11.glEnable(3553);
/* 472 */     GL11.glEnable(2929);
/* 473 */     GL11.glDepthMask(true);
/* 474 */     GL11.glDisable(3042);
/* 475 */     GL11.glPopMatrix();
/*     */   }
/*     */   
/*     */   private static int HUDColor() {
/* 479 */     return (new Color(((Double)HUD.r.getValue()).intValue(), ((Double)HUD.g.getValue()).intValue(), ((Double)HUD.b.getValue()).intValue())).getRGB();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void Weave(float x, float y, float x1, float y1, float size, float length, boolean bloom) {
/* 484 */     drawGradientSideways(x + 2.5D, y + 2.5D, (x1 + size - 180.0F + length), (y1 + size) - 2.5D, bloom ? Client.instance
/* 485 */         .getClientColor(255) : (new Color(0, 0, 0, 180)).getRGB(), (new Color(0, 0, 0, 0)).getRGB());
/* 486 */     awareline.main.ui.gui.clickgui.RenderUtil.drawGradientSidewaysV(x + 2.5D, (y + 2.5F), (x1 - 273.0F + 59.0F), (y1 + size) - 2.5D, Client.instance
/* 487 */         .getClientColorAlpha(255), Client.instance.getClientColorAlpha(255));
/*     */   }
/*     */   
/*     */   public static void watermarkForFrame(float x, float y, float x1, float y1, float size) {
/* 491 */     if (HUD.getInstance.isEnabled() && ((Boolean)HUD.getInstance.blur.get()).booleanValue()) {
/* 492 */       BlurBuffer.blurArea(x + 2.5F, y + 2.5F, x1 + size - 45.0F - 169.0F + Client.instance.FontLoaders.regular24
/* 493 */           .getStringWidth(Client.instance.hudName), y1 + size - 8.0F, true);
/*     */     }
/*     */     
/* 496 */     awareline.main.ui.gui.clickgui.RenderUtil.drawRect((x + 2.5F), (y + 2.5F), (x1 + size - 45.0F - 161.0F + Client.instance.FontLoaders.regular24
/* 497 */         .getStringWidth(Client.instance.hudName)), (y1 + size) - 2.5D, (new Color(0, 0, 0, 150))
/* 498 */         .getRGB());
/* 499 */     awareline.main.ui.gui.clickgui.RenderUtil.drawRect(x + 2.5D, (y + 2.5F), (x1 - 273.0F + 59.0F), (y1 + size) - 2.5D, Client.instance
/* 500 */         .getClientColor(255));
/*     */   }
/*     */   
/*     */   public static void roundHUD(float x, float y, float width, float size, boolean enableOutline, boolean bloom) {
/* 504 */     if (enableOutline) {
/* 505 */       RoundedUtil.drawRoundOutline(x + 2.5F, y + 2.5F, width + size - 45.0F - 5.0F, 15.0F, 5.0F, 0.5F, bloom ? Client.instance
/* 506 */           .getClientColorNoHash(255) : new Color(0, 0, 0, 150), Client.instance.getClientColorAlphaNoHash(255));
/*     */     } else {
/* 508 */       RoundedUtil.drawRound(x + 2.5F, y + 2.5F, width + size - 45.0F - 5.0F, 15.0F, 5.0F, bloom ? Client.instance
/* 509 */           .getClientColorNoHash(255) : new Color(0, 0, 0, 150));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void Gamesense(float x, float y, float x1, float y1, float size, float color2, float color3, boolean bloom) {
/* 515 */     rectangleBordered(x + 2.5D, y + 2.5D, (x1 + size) - 2.5D, (y1 + size) - 2.5D, 0.5D, bloom ? Client.instance
/* 516 */         .getClientColor(255) : (new Color(0, 0, 0, 150)).getRGB(), (new Color(0, 0, 0, 0)).getRGB());
/* 517 */     if (((Boolean)HUD.rainbow.getValue()).booleanValue()) {
/* 518 */       drawGradientSideways((x + size * 3.0F), (y + 3.0F), (x1 - size * 2.0F), (y + 4.0F), ((Boolean)HUD.rainbow.getValue()).booleanValue() ? (int)color2 : HUDColor(), ((Boolean)HUD.rainbow.getValue()).booleanValue() ? (int)color3 : HUDColor());
/*     */     } else {
/* 520 */       int rainbowCol = RenderUtils.rainbow(System.nanoTime(), 0.0F, 1.0F).getRGB();
/* 521 */       Color col = new Color(rainbowCol);
/* 522 */       Color cuscolor = new Color(((Double)HUD.r.getValue()).intValue(), ((Double)HUD.g.getValue()).intValue(), ((Double)HUD.b.getValue()).intValue());
/* 523 */       Color Ranbow = new Color(cuscolor.getRed() / 255.0F, cuscolor.getGreen() / 255.0F, cuscolor.getBlue() / 255.0F, 1.0F - col.getGreen() / 400.0F);
/* 524 */       int rainbowCol2 = RenderUtils.rainbow(System.nanoTime() + 10L, 0.0F, 1.0F).getRGB();
/* 525 */       Color col2 = new Color(rainbowCol2);
/*     */       
/* 527 */       Color Ranbow2 = new Color(cuscolor.getRed() / 255.0F, cuscolor.getGreen() / 255.0F, cuscolor.getBlue() / 255.0F, 1.0F - col2.getGreen() / 400.0F);
/* 528 */       drawGradientSideways((x + size * 3.0F), (y + 3.0F), (x1 - size * 2.0F), (y + 4.0F), 
/* 529 */           ((Boolean)HUD.dynamicColor.getValue()).booleanValue() ? Ranbow2.getRGB() : HUDColor(), 
/* 530 */           ((Boolean)HUD.dynamicColor.getValue()).booleanValue() ? Ranbow.getRGB() : HUDColor());
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void entityESPBox(Entity e, Color color, EventRender3D event) {
/* 535 */     double renderPosX = (mc.getRenderManager()).renderPosX;
/* 536 */     double renderPosY = (mc.getRenderManager()).renderPosY;
/* 537 */     double renderPosZ = (mc.getRenderManager()).renderPosZ;
/* 538 */     double posX = e.lastTickPosX + (e.posX - e.lastTickPosX) * event.getPartialTicks() - renderPosX;
/* 539 */     double posY = e.lastTickPosY + (e.posY - e.lastTickPosY) * event.getPartialTicks() - renderPosY;
/* 540 */     double posZ = e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * event.getPartialTicks() - renderPosZ;
/* 541 */     AxisAlignedBB box = AxisAlignedBB.fromBounds(posX - e.width, posY, posZ - e.width, posX + e.width, posY + e.height + 0.2D, posZ + e.width);
/*     */     
/* 543 */     if (e instanceof EntityLivingBase) {
/* 544 */       box = AxisAlignedBB.fromBounds(posX - e.width + 0.2D, posY, posZ - e.width + 0.2D, posX + e.width - 0.2D, posY + e.height + (
/* 545 */           e.isSneaking() ? 0.02D : 0.2D), posZ + e.width - 0.2D);
/*     */     }
/*     */     
/* 548 */     EntityLivingBase e2 = (EntityLivingBase)e;
/* 549 */     if (e2.hurtTime != 0) {
/* 550 */       GL11.glColor4f(1.0F, 0.2F, 0.1F, 0.2F);
/*     */     } else {
/* 552 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.2F);
/*     */     } 
/* 554 */     drawBoundingBox(box);
/*     */   }
/*     */   
/*     */   public static void entityESPBox(Entity e, EventRender3D event) {
/* 558 */     double renderPosX = (mc.getRenderManager()).renderPosX;
/* 559 */     double renderPosY = (mc.getRenderManager()).renderPosY;
/* 560 */     double renderPosZ = (mc.getRenderManager()).renderPosZ;
/* 561 */     double posX = e.lastTickPosX + (e.posX - e.lastTickPosX) * event.getPartialTicks() - renderPosX;
/* 562 */     double posY = e.lastTickPosY + (e.posY - e.lastTickPosY) * event.getPartialTicks() - renderPosY;
/* 563 */     double posZ = e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * event.getPartialTicks() - renderPosZ;
/* 564 */     AxisAlignedBB box = AxisAlignedBB.fromBounds(posX - e.width, posY, posZ - e.width, posX + e.width, posY + e.height + 0.2D, posZ + e.width);
/* 565 */     if (e instanceof EntityLivingBase) {
/* 566 */       box = AxisAlignedBB.fromBounds(posX - e.width + 0.2D, posY, posZ - e.width + 0.2D, posX + e.width - 0.2D, posY + e.height + (e.isSneaking() ? 0.02D : 0.2D), posZ + e.width - 0.2D);
/*     */     }
/* 568 */     assert e instanceof EntityLivingBase;
/* 569 */     EntityLivingBase e2 = (EntityLivingBase)e;
/* 570 */     if (e2.hurtTime != 0) {
/* 571 */       GL11.glColor4f(1.0F, 0.2F, 0.1F, 0.2F);
/*     */     } else {
/* 573 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.2F);
/*     */     } 
/* 575 */     drawBoundingBox(box);
/*     */   }
/*     */   
/*     */   public static double[] convertTo2D(double x, double y, double z) {
/* 579 */     FloatBuffer screenCoords = BufferUtils.createFloatBuffer(3);
/* 580 */     IntBuffer viewport = BufferUtils.createIntBuffer(16);
/* 581 */     FloatBuffer modelView = BufferUtils.createFloatBuffer(16);
/* 582 */     FloatBuffer projection = BufferUtils.createFloatBuffer(16);
/* 583 */     GL11.glGetFloat(2982, modelView);
/* 584 */     GL11.glGetFloat(2983, projection);
/* 585 */     GL11.glGetInteger(2978, viewport);
/* 586 */     boolean result = GLU.gluProject((float)x, (float)y, (float)z, modelView, projection, viewport, screenCoords);
/* 587 */     (new double[3])[0] = screenCoords.get(0); (new double[3])[1] = (Display.getHeight() - screenCoords.get(1)); (new double[3])[2] = screenCoords.get(2); return result ? new double[3] : null;
/*     */   }
/*     */   
/*     */   public static void doGlScissor(float x, float y, float windowWidth2, float windowHeight2) {
/* 591 */     int scaleFactor = 1;
/* 592 */     float k = mc.gameSettings.guiScale;
/* 593 */     if (k == 0.0F) {
/* 594 */       k = 1000.0F;
/*     */     }
/* 596 */     while (scaleFactor < k && mc.displayWidth / (scaleFactor + 1) >= 320 && mc.displayHeight / (scaleFactor + 1) >= 240)
/*     */     {
/* 598 */       scaleFactor++;
/*     */     }
/* 600 */     GL11.glScissor((int)(x * scaleFactor), (int)(mc.displayHeight - (y + windowHeight2) * scaleFactor), (int)(windowWidth2 * scaleFactor), (int)(windowHeight2 * scaleFactor));
/*     */   }
/*     */ 
/*     */   
/*     */   public static void doGlScissor(int x, int y, int width, int height) {
/* 605 */     int scaleFactor = 1;
/* 606 */     int k = mc.gameSettings.guiScale;
/* 607 */     if (k == 0) {
/* 608 */       k = 1000;
/*     */     }
/* 610 */     while (scaleFactor < k && mc.displayWidth / (scaleFactor + 1) >= 320 && mc.displayHeight / (scaleFactor + 1) >= 240) {
/* 611 */       scaleFactor++;
/*     */     }
/* 613 */     GL11.glScalef(0.5F, 0.5F, 0.5F);
/* 614 */     GL11.glScissor(x * scaleFactor, mc.displayHeight - (y + height) * scaleFactor, width * scaleFactor, height * scaleFactor);
/* 615 */     GL11.glScalef(2.0F, 2.0F, 2.0F);
/*     */   }
/*     */   
/*     */   public static double interpolate(double current, double old, double scale) {
/* 619 */     return old + (current - old) * scale;
/*     */   }
/*     */   
/*     */   public static void arcEllipse(float n, float n2, float n3, float n4, float n5, float n6, int n7) {
/* 623 */     GlStateManager.color(0.0F, 0.0F, 0.0F);
/* 624 */     GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.0F);
/* 625 */     if (n3 > n4) {
/* 626 */       float n8 = n4;
/* 627 */       n4 = n3;
/* 628 */       n3 = n8;
/*     */     } 
/* 630 */     float p_color_3_ = (n7 >> 24 & 0xFF) / 255.0F;
/* 631 */     float p_color_0_ = (n7 >> 16 & 0xFF) / 255.0F;
/* 632 */     float p_color_1_ = (n7 >> 8 & 0xFF) / 255.0F;
/* 633 */     float p_color_2_ = (n7 & 0xFF) / 255.0F;
/*     */     
/* 635 */     GlStateManager.enableBlend();
/* 636 */     GlStateManager.disableTexture2D();
/* 637 */     GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 638 */     GlStateManager.color(p_color_0_, p_color_1_, p_color_2_, p_color_3_);
/* 639 */     if (p_color_3_ > 0.5F) {
/* 640 */       GL11.glEnable(2848);
/* 641 */       GL11.glLineWidth(1.0F);
/* 642 */       GL11.glBegin(3);
/* 643 */       float n9 = n4;
/* 644 */       while (n9 >= n3) {
/* 645 */         GL11.glVertex2f(n + (float)Math.cos(n9 * Math.PI / 180.0D) * n5 * 1.001F, n2 + (float)Math.sin(n9 * Math.PI / 180.0D) * n6 * 1.001F);
/* 646 */         n9 -= 4.0F;
/*     */       } 
/* 648 */       GL11.glEnd();
/* 649 */       GL11.glDisable(2848);
/*     */     } 
/* 651 */     GL11.glBegin(6);
/* 652 */     float n10 = n4;
/* 653 */     while (n10 >= n3) {
/* 654 */       GL11.glVertex2f(n + (float)Math.cos(n10 * Math.PI / 180.0D) * n5, n2 + (float)Math.sin(n10 * Math.PI / 180.0D) * n6);
/* 655 */       n10 -= 4.0F;
/*     */     } 
/* 657 */     GL11.glEnd();
/* 658 */     GlStateManager.enableTexture2D();
/* 659 */     GlStateManager.disableBlend();
/*     */   }
/*     */   
/*     */   public static void circle(float n, float n2, float n3, int n4) {
/* 663 */     arc(n, n2, 0.0F, 360.0F, n3, n4);
/*     */   }
/*     */   
/*     */   public static void circle(float n, float n2, float n3, Color color) {
/* 667 */     arc(n, n2, 0.0F, 360.0F, n3, color);
/*     */   }
/*     */   
/*     */   public static void arc(float n, float n2, float n3, float n4, float n5, int n6) {
/* 671 */     arcEllipse(n, n2, n3, n4, n5, n5, n6);
/*     */   }
/*     */   
/*     */   public static void arc(float n, float n2, float n3, float n4, float n5, Color color) {
/* 675 */     arcEllipse(n, n2, n3, n4, n5, n5, color);
/*     */   }
/*     */   
/*     */   public static void arcEllipse(float n, float n2, float n3, float n4, float n5, float n6, Color color) {
/* 679 */     GlStateManager.color(0.0F, 0.0F, 0.0F);
/* 680 */     GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.0F);
/* 681 */     if (n3 > n4) {
/* 682 */       float n7 = n4;
/* 683 */       n4 = n3;
/* 684 */       n3 = n7;
/*     */     } 
/*     */     
/* 687 */     GlStateManager.enableBlend();
/* 688 */     GlStateManager.disableTexture2D();
/* 689 */     GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 690 */     GlStateManager.color(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, color.getAlpha() / 255.0F);
/* 691 */     if (color.getAlpha() > 0.5F) {
/* 692 */       GL11.glEnable(2848);
/* 693 */       GL11.glLineWidth(1.0F);
/* 694 */       GL11.glBegin(3);
/* 695 */       float n8 = n4;
/* 696 */       while (n8 >= n3) {
/* 697 */         GL11.glVertex2f(n + (float)Math.cos(n8 * Math.PI / 180.0D) * n5 * 1.001F, n2 + (float)Math.sin(n8 * Math.PI / 180.0D) * n6 * 1.001F);
/* 698 */         n8 -= 4.0F;
/*     */       } 
/* 700 */       GL11.glEnd();
/* 701 */       GL11.glDisable(2848);
/*     */     } 
/* 703 */     GL11.glBegin(6);
/* 704 */     float n9 = n4;
/* 705 */     while (n9 >= n3) {
/* 706 */       GL11.glVertex2f(n + (float)Math.cos(n9 * Math.PI / 180.0D) * n5, n2 + (float)Math.sin(n9 * Math.PI / 180.0D) * n6);
/* 707 */       n9 -= 4.0F;
/*     */     } 
/* 709 */     GL11.glEnd();
/* 710 */     GlStateManager.enableTexture2D();
/* 711 */     GlStateManager.disableBlend();
/*     */   }
/*     */   
/*     */   public static void makeScissorBox(float x, float y, float x2, float y2) {
/* 715 */     ScaledResolution scaledResolution = new ScaledResolution(mc);
/* 716 */     int factor = scaledResolution.getScaleFactor();
/* 717 */     GL11.glScissor((int)(x * factor), (int)((scaledResolution.getScaledHeight() - y2) * factor), (int)((x2 - x) * factor), (int)((y2 - y) * factor));
/*     */   }
/*     */   
/*     */   public static void drawCornerBox(double x, double y, double x2, double y2, double lw, Color color) {
/* 721 */     double width = Math.abs(x2 - x);
/* 722 */     double height = Math.abs(y2 - y);
/* 723 */     double halfWidth = width / 4.0D;
/* 724 */     double halfHeight = height / 4.0D;
/* 725 */     RenderUtils.start2D();
/* 726 */     GL11.glPushMatrix();
/* 727 */     GL11.glLineWidth((float)lw);
/* 728 */     RenderUtils.setColor(color);
/*     */     
/* 730 */     GL11.glBegin(3);
/* 731 */     GL11.glVertex2d(x + halfWidth, y);
/* 732 */     GL11.glVertex2d(x, y);
/* 733 */     GL11.glVertex2d(x, y + halfHeight);
/* 734 */     GL11.glEnd();
/*     */ 
/*     */     
/* 737 */     GL11.glBegin(3);
/* 738 */     GL11.glVertex2d(x, y + height - halfHeight);
/* 739 */     GL11.glVertex2d(x, y + height);
/* 740 */     GL11.glVertex2d(x + halfWidth, y + height);
/* 741 */     GL11.glEnd();
/*     */     
/* 743 */     GL11.glBegin(3);
/* 744 */     GL11.glVertex2d(x + width - halfWidth, y + height);
/* 745 */     GL11.glVertex2d(x + width, y + height);
/* 746 */     GL11.glVertex2d(x + width, y + height - halfHeight);
/* 747 */     GL11.glEnd();
/*     */     
/* 749 */     GL11.glBegin(3);
/* 750 */     GL11.glVertex2d(x + width, y + halfHeight);
/* 751 */     GL11.glVertex2d(x + width, y);
/* 752 */     GL11.glVertex2d(x + width - halfWidth, y);
/* 753 */     GL11.glEnd();
/*     */     
/* 755 */     GL11.glPopMatrix();
/* 756 */     RenderUtils.stop2D();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void setupRender(boolean start) {
/* 761 */     if (start) {
/* 762 */       GlStateManager.enableBlend();
/* 763 */       GL11.glEnable(2848);
/* 764 */       GlStateManager.disableDepth();
/* 765 */       GlStateManager.disableTexture2D();
/* 766 */       GlStateManager.blendFunc(770, 771);
/* 767 */       GL11.glHint(3154, 4354);
/*     */     } else {
/* 769 */       GlStateManager.disableBlend();
/* 770 */       GlStateManager.enableTexture2D();
/* 771 */       GL11.glDisable(2848);
/* 772 */       GlStateManager.enableDepth();
/*     */     } 
/* 774 */     GlStateManager.depthMask(!start);
/*     */   }
/*     */   
/*     */   public static boolean isInViewFrustrum(AxisAlignedBB bb) {
/* 778 */     Frustum frustrum = new Frustum();
/* 779 */     Entity current = Minecraft.getMinecraft().getRenderViewEntity();
/* 780 */     frustrum.setPosition(current.posX, current.posY, current.posZ);
/* 781 */     return frustrum.isBoundingBoxInFrustum(bb);
/*     */   }
/*     */   
/*     */   public static boolean isInViewFrustrum(Entity entity) {
/* 785 */     return (isInViewFrustrum(entity.getEntityBoundingBox()) || entity.ignoreFrustumCheck);
/*     */   }
/*     */   
/*     */   public static void setupClientState(GLClientState state, boolean enabled) {
/* 789 */     csBuffer.clear();
/* 790 */     if (state.ordinal() > 0) {
/* 791 */       csBuffer.add(Integer.valueOf(state.getCap()));
/*     */     }
/* 793 */     csBuffer.add(Integer.valueOf(32884));
/* 794 */     csBuffer.forEach(enabled ? ENABLE_CLIENT_STATE : DISABLE_CLIENT_STATE);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void color(int color) {
/* 799 */     float f = (color >> 24 & 0xFF) / 255.0F;
/* 800 */     float f1 = (color >> 16 & 0xFF) / 255.0F;
/* 801 */     float f2 = (color >> 8 & 0xFF) / 255.0F;
/* 802 */     float f3 = (color & 0xFF) / 255.0F;
/* 803 */     GL11.glColor4f(f1, f2, f3, f);
/*     */   }
/*     */   
/*     */   public static void scissorStart(double x, double y, double width, double height) {
/* 807 */     GL11.glEnable(3089);
/* 808 */     ScaledResolution sr = new ScaledResolution(mc);
/* 809 */     double scale = sr.getScaleFactor();
/* 810 */     double finalHeight = height * scale;
/* 811 */     double finalY = (sr.getScaledHeight() - y) * scale;
/* 812 */     double finalX = x * scale;
/* 813 */     double finalWidth = width * scale;
/* 814 */     GL11.glScissor((int)finalX, (int)(finalY - finalHeight), (int)finalWidth, (int)finalHeight);
/*     */   }
/*     */   
/*     */   public static void scissorEnd() {
/* 818 */     GL11.glDisable(3089);
/*     */   }
/*     */   
/*     */   public static void drawStack(ItemStack stack, float x, float y) {
/* 822 */     GL11.glPushMatrix();
/* 823 */     if (mc.theWorld != null) {
/* 824 */       RenderHelper.enableGUIStandardItemLighting();
/*     */     }
/*     */     
/* 827 */     GlStateManager.pushMatrix();
/* 828 */     GlStateManager.disableAlpha();
/* 829 */     GlStateManager.clear(256);
/* 830 */     GlStateManager.enableBlend();
/*     */     
/* 832 */     (mc.getRenderItem()).zLevel = -150.0F;
/* 833 */     mc.getRenderItem().renderItemAndEffectIntoGUI(stack, (int)x, (int)y);
/* 834 */     (mc.getRenderItem()).zLevel = 0.0F;
/*     */     
/* 836 */     GlStateManager.enableBlend();
/* 837 */     float z = 0.5F;
/*     */     
/* 839 */     GlStateManager.scale(0.5F, 0.5F, 0.5F);
/* 840 */     GlStateManager.disableDepth();
/* 841 */     GlStateManager.disableLighting();
/* 842 */     GlStateManager.enableDepth();
/* 843 */     GlStateManager.scale(2.0F, 2.0F, 2.0F);
/* 844 */     GlStateManager.enableAlpha();
/* 845 */     GlStateManager.popMatrix();
/*     */     
/* 847 */     GL11.glPopMatrix();
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\utility\render\RenderUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */