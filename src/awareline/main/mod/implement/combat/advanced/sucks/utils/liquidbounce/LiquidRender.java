/*     */ package awareline.main.mod.implement.combat.advanced.sucks.utils.liquidbounce;
/*     */ 
/*     */ import awareline.main.Client;
/*     */ import awareline.main.utility.BlockUtils;
/*     */ import java.awt.Color;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.client.gui.Gui;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.RenderGlobal;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.Timer;
/*     */ import net.minecraft.world.World;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class LiquidRender
/*     */ {
/*  39 */   public static final Minecraft mc = Minecraft.getMinecraft();
/*  40 */   private static final Map<Integer, Boolean> glCapMap = new HashMap<>();
/*     */   
/*     */   public static int deltaTime;
/*     */   
/*     */   public static void drawBlockBox(BlockPos blockPos, Color color, boolean outline) {
/*  45 */     Timer timer = mc.timer;
/*     */     
/*  47 */     double x = blockPos.getX() - (mc.getRenderManager()).renderPosX;
/*  48 */     double y = blockPos.getY() - (mc.getRenderManager()).renderPosY;
/*  49 */     double z = blockPos.getZ() - (mc.getRenderManager()).renderPosZ;
/*     */     
/*  51 */     AxisAlignedBB axisAlignedBB = new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D);
/*  52 */     Block block = BlockUtils.getBlock(blockPos);
/*     */     
/*  54 */     if (block != null) {
/*  55 */       EntityPlayerSP entityPlayerSP = mc.thePlayer;
/*     */       
/*  57 */       double posX = ((EntityPlayer)entityPlayerSP).lastTickPosX + (((EntityPlayer)entityPlayerSP).posX - ((EntityPlayer)entityPlayerSP).lastTickPosX) * timer.renderPartialTicks;
/*  58 */       double posY = ((EntityPlayer)entityPlayerSP).lastTickPosY + (((EntityPlayer)entityPlayerSP).posY - ((EntityPlayer)entityPlayerSP).lastTickPosY) * timer.renderPartialTicks;
/*  59 */       double posZ = ((EntityPlayer)entityPlayerSP).lastTickPosZ + (((EntityPlayer)entityPlayerSP).posZ - ((EntityPlayer)entityPlayerSP).lastTickPosZ) * timer.renderPartialTicks;
/*     */ 
/*     */       
/*  62 */       axisAlignedBB = block.getSelectedBoundingBox((World)mc.theWorld, blockPos).expand(0.0020000000949949026D, 0.0020000000949949026D, 0.0020000000949949026D).offset(-posX, -posY, -posZ);
/*     */     } 
/*     */     
/*  65 */     GL11.glBlendFunc(770, 771);
/*  66 */     enableGlCap(3042);
/*  67 */     disableGlCap(new int[] { 3553, 2929 });
/*  68 */     GL11.glDepthMask(false);
/*     */     
/*  70 */     glColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), (color.getAlpha() == 255) ? (outline ? 26 : 35) : color
/*  71 */           .getAlpha()));
/*  72 */     drawFilledBox(axisAlignedBB);
/*     */     
/*  74 */     if (outline) {
/*  75 */       GL11.glLineWidth(1.0F);
/*  76 */       enableGlCap(2848);
/*  77 */       glColor(color);
/*  78 */       RenderGlobal.drawSelectionBoundingBox(axisAlignedBB);
/*     */     } 
/*     */     
/*  81 */     GlStateManager.resetColor();
/*  82 */     GL11.glDepthMask(true);
/*  83 */     resetCaps();
/*     */   }
/*     */   
/*     */   public static void drawEntityBox(Entity entity, Color color, boolean outline) {
/*  87 */     Timer timer = mc.timer;
/*     */     
/*  89 */     GL11.glBlendFunc(770, 771);
/*  90 */     enableGlCap(3042);
/*  91 */     disableGlCap(new int[] { 3553, 2929 });
/*  92 */     GL11.glDepthMask(false);
/*     */ 
/*     */     
/*  95 */     double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * timer.renderPartialTicks - (mc.getRenderManager()).renderPosX;
/*     */     
/*  97 */     double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * timer.renderPartialTicks - (mc.getRenderManager()).renderPosY;
/*     */     
/*  99 */     double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * timer.renderPartialTicks - (mc.getRenderManager()).renderPosZ;
/*     */     
/* 101 */     AxisAlignedBB entityBox = entity.getEntityBoundingBox();
/* 102 */     AxisAlignedBB axisAlignedBB = new AxisAlignedBB(entityBox.minX - entity.posX + x - 0.05D, entityBox.minY - entity.posY + y, entityBox.minZ - entity.posZ + z - 0.05D, entityBox.maxX - entity.posX + x + 0.05D, entityBox.maxY - entity.posY + y + 0.15D, entityBox.maxZ - entity.posZ + z + 0.05D);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 111 */     if (outline) {
/* 112 */       GL11.glLineWidth(1.0F);
/* 113 */       enableGlCap(2848);
/* 114 */       glColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 95));
/* 115 */       RenderGlobal.drawSelectionBoundingBox(axisAlignedBB);
/*     */     } 
/*     */     
/* 118 */     glColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), outline ? 26 : color.getAlpha()));
/* 119 */     drawFilledBox(axisAlignedBB);
/* 120 */     GlStateManager.resetColor();
/* 121 */     GL11.glDepthMask(true);
/* 122 */     resetCaps();
/*     */   }
/*     */   
/*     */   public static void drawAxisAlignedBB(AxisAlignedBB axisAlignedBB, Color color) {
/* 126 */     GL11.glBlendFunc(770, 771);
/* 127 */     GL11.glEnable(3042);
/* 128 */     GL11.glLineWidth(2.0F);
/* 129 */     GL11.glDisable(3553);
/* 130 */     GL11.glDisable(2929);
/* 131 */     GL11.glDepthMask(false);
/* 132 */     if (color != null)
/* 133 */       glColor(color); 
/* 134 */     drawFilledBox(axisAlignedBB);
/* 135 */     GlStateManager.resetColor();
/* 136 */     GL11.glEnable(3553);
/* 137 */     GL11.glEnable(2929);
/* 138 */     GL11.glDepthMask(true);
/* 139 */     GL11.glDisable(3042);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void drawPlatform(double y, Color color, double size) {
/* 144 */     double renderY = y - (mc.getRenderManager()).renderPosY;
/*     */     
/* 146 */     drawAxisAlignedBB(new AxisAlignedBB(size, renderY + 0.02D, size, -size, renderY, -size), color);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void drawPlatform(Entity entity, Color color) {
/* 151 */     Timer timer = mc.timer;
/*     */ 
/*     */     
/* 154 */     double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * timer.renderPartialTicks - (mc.getRenderManager()).renderPosX;
/*     */     
/* 156 */     double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * timer.renderPartialTicks - (mc.getRenderManager()).renderPosY;
/*     */     
/* 158 */     double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * timer.renderPartialTicks - (mc.getRenderManager()).renderPosZ;
/*     */ 
/*     */ 
/*     */     
/* 162 */     AxisAlignedBB axisAlignedBB = entity.getEntityBoundingBox().offset(-entity.posX, -entity.posY, -entity.posZ).offset(x, y, z);
/*     */     
/* 164 */     drawAxisAlignedBB(new AxisAlignedBB(axisAlignedBB.minX, axisAlignedBB.maxY + 0.2D, axisAlignedBB.minZ, axisAlignedBB.maxX, axisAlignedBB.maxY + 0.26D, axisAlignedBB.maxZ), color);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void drawFilledBox(AxisAlignedBB axisAlignedBB) {
/* 171 */     Tessellator tessellator = Tessellator.getInstance();
/* 172 */     WorldRenderer worldRenderer = tessellator.getWorldRenderer();
/* 173 */     worldRenderer.begin(7, DefaultVertexFormats.POSITION);
/* 174 */     worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
/* 175 */     worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
/* 176 */     worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
/* 177 */     worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
/* 178 */     worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
/* 179 */     worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
/* 180 */     worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
/* 181 */     worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
/* 182 */     tessellator.draw();
/* 183 */     worldRenderer.begin(7, DefaultVertexFormats.POSITION);
/* 184 */     worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
/* 185 */     worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
/* 186 */     worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
/* 187 */     worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
/* 188 */     worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
/* 189 */     worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
/* 190 */     worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
/* 191 */     worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
/* 192 */     tessellator.draw();
/* 193 */     worldRenderer.begin(7, DefaultVertexFormats.POSITION);
/* 194 */     worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
/* 195 */     worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
/* 196 */     worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
/* 197 */     worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
/* 198 */     worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
/* 199 */     worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
/* 200 */     worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
/* 201 */     worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
/* 202 */     tessellator.draw();
/* 203 */     worldRenderer.begin(7, DefaultVertexFormats.POSITION);
/* 204 */     worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
/* 205 */     worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
/* 206 */     worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
/* 207 */     worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
/* 208 */     worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
/* 209 */     worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
/* 210 */     worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
/* 211 */     worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
/* 212 */     tessellator.draw();
/* 213 */     worldRenderer.begin(7, DefaultVertexFormats.POSITION);
/* 214 */     worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
/* 215 */     worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
/* 216 */     worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
/* 217 */     worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
/* 218 */     worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
/* 219 */     worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
/* 220 */     worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
/* 221 */     worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
/* 222 */     tessellator.draw();
/* 223 */     worldRenderer.begin(7, DefaultVertexFormats.POSITION);
/* 224 */     worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
/* 225 */     worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
/* 226 */     worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
/* 227 */     worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
/* 228 */     worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
/* 229 */     worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
/* 230 */     worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
/* 231 */     worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
/* 232 */     tessellator.draw();
/*     */   }
/*     */   
/*     */   public static void drawRect(float x, float y, float x2, float y2, int color) {
/* 236 */     GL11.glEnable(3042);
/* 237 */     GL11.glDisable(3553);
/* 238 */     GL11.glBlendFunc(770, 771);
/* 239 */     GL11.glEnable(2848);
/* 240 */     GL11.glPushMatrix();
/* 241 */     glColor(color);
/* 242 */     GL11.glBegin(7);
/* 243 */     GL11.glVertex2d(x2, y);
/* 244 */     GL11.glVertex2d(x, y);
/* 245 */     GL11.glVertex2d(x, y2);
/* 246 */     GL11.glVertex2d(x2, y2);
/* 247 */     GL11.glEnd();
/* 248 */     GL11.glPopMatrix();
/* 249 */     GL11.glEnable(3553);
/* 250 */     GL11.glDisable(3042);
/* 251 */     GL11.glDisable(2848);
/*     */   }
/*     */   
/*     */   public static void drawRect(float x, float y, float x2, float y2, Color color) {
/* 255 */     drawRect(x, y, x2, y2, color.getRGB());
/*     */   }
/*     */ 
/*     */   
/*     */   public static void drawBorderedRect(float x, float y, float x2, float y2, float width, int color1, int color2) {
/* 260 */     drawRect(x, y, x2, y2, color2);
/* 261 */     GL11.glEnable(3042);
/* 262 */     GL11.glDisable(3553);
/* 263 */     GL11.glBlendFunc(770, 771);
/* 264 */     GL11.glEnable(2848);
/* 265 */     GL11.glPushMatrix();
/* 266 */     glColor(color1);
/* 267 */     GL11.glLineWidth(width);
/* 268 */     GL11.glBegin(1);
/* 269 */     GL11.glVertex2d(x, y);
/* 270 */     GL11.glVertex2d(x, y2);
/* 271 */     GL11.glVertex2d(x2, y2);
/* 272 */     GL11.glVertex2d(x2, y);
/* 273 */     GL11.glVertex2d(x, y);
/* 274 */     GL11.glVertex2d(x2, y);
/* 275 */     GL11.glVertex2d(x, y2);
/* 276 */     GL11.glVertex2d(x2, y2);
/* 277 */     GL11.glEnd();
/* 278 */     GL11.glPopMatrix();
/* 279 */     GL11.glEnable(3553);
/* 280 */     GL11.glDisable(3042);
/* 281 */     GL11.glDisable(2848);
/*     */   }
/*     */   
/*     */   public static void drawLoadingCircle(float x, float y) {
/* 285 */     for (int i = 0; i < 4; i++) {
/* 286 */       int rot = (int)(System.nanoTime() / 5000000L * i % 360L);
/* 287 */       drawCircle(x, y, (i * 10), rot - 180, rot);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void drawCircle(float x, float y, float radius, int start, int end) {
/* 292 */     GlStateManager.enableBlend();
/* 293 */     GlStateManager.disableTexture2D();
/* 294 */     GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 295 */     glColor(Color.WHITE);
/*     */     
/* 297 */     GL11.glEnable(2848);
/* 298 */     GL11.glLineWidth(2.0F);
/* 299 */     GL11.glBegin(3); float i;
/* 300 */     for (i = end; i >= start; i -= 4.0F)
/* 301 */       GL11.glVertex2f((float)(x + Math.cos(i * Math.PI / 180.0D) * (radius * 1.001F)), (float)(y + Math.sin(i * Math.PI / 180.0D) * (radius * 1.001F))); 
/* 302 */     GL11.glEnd();
/* 303 */     GL11.glDisable(2848);
/*     */     
/* 305 */     GlStateManager.enableTexture2D();
/* 306 */     GlStateManager.disableBlend();
/*     */   }
/*     */   
/*     */   public static void drawFilledCircle(int xx, int yy, float radius, Color col) {
/* 310 */     int sections = 50;
/* 311 */     double dAngle = 6.283185307179586D / sections;
/*     */ 
/*     */     
/* 314 */     GL11.glPushMatrix();
/* 315 */     GL11.glEnable(3042);
/* 316 */     GL11.glDisable(3553);
/* 317 */     GL11.glBlendFunc(770, 771);
/* 318 */     GL11.glEnable(2848);
/* 319 */     GL11.glBegin(6);
/*     */     
/* 321 */     for (int i = 0; i < sections; i++) {
/* 322 */       float x = (float)(radius * Math.sin(i * dAngle));
/* 323 */       float y = (float)(radius * Math.cos(i * dAngle));
/*     */       
/* 325 */       GL11.glColor4f(col.getRed() / 255.0F, col.getGreen() / 255.0F, col.getBlue() / 255.0F, col.getAlpha() / 255.0F);
/* 326 */       GL11.glVertex2f(xx + x, yy + y);
/*     */     } 
/* 328 */     GlStateManager.color(0.0F, 0.0F, 0.0F);
/* 329 */     GL11.glEnd();
/* 330 */     GL11.glEnable(3553);
/* 331 */     GL11.glDisable(3042);
/* 332 */     GL11.glDisable(2848);
/* 333 */     GL11.glPopMatrix();
/*     */   }
/*     */   
/*     */   public static void drawImage(ResourceLocation image, int x, int y, int width, int height) {
/* 337 */     GL11.glDisable(2929);
/* 338 */     GL11.glEnable(3042);
/* 339 */     GL11.glDepthMask(false);
/* 340 */     OpenGlHelper.glBlendFunc(770, 771, 1, 0);
/* 341 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 342 */     mc.getTextureManager().bindTexture(image);
/* 343 */     Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0F, 0.0F, width, height, width, height);
/* 344 */     GL11.glDepthMask(true);
/* 345 */     GL11.glDisable(3042);
/* 346 */     GL11.glEnable(2929);
/*     */   }
/*     */   
/*     */   public static void glColor(Color color) {
/* 350 */     GL11.glColor4f(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, color.getAlpha() / 255.0F);
/*     */   }
/*     */   
/*     */   private static void glColor(int hex) {
/* 354 */     float alpha = (hex >> 24 & 0xFF) / 255.0F;
/* 355 */     float red = (hex >> 16 & 0xFF) / 255.0F;
/* 356 */     float green = (hex >> 8 & 0xFF) / 255.0F;
/* 357 */     float blue = (hex & 0xFF) / 255.0F;
/* 358 */     GL11.glColor4f(red, green, blue, alpha);
/*     */   }
/*     */   
/*     */   public static Color hexColor(int hex) {
/* 362 */     float alpha = (hex >> 24 & 0xFF) / 255.0F;
/* 363 */     float red = (hex >> 16 & 0xFF) / 255.0F;
/* 364 */     float green = (hex >> 8 & 0xFF) / 255.0F;
/* 365 */     float blue = (hex & 0xFF) / 255.0F;
/*     */     
/* 367 */     return new Color(red, green, blue, alpha);
/*     */   }
/*     */   
/*     */   public static void draw2D(EntityLivingBase entity, double posX, double posY, double posZ, int color, int backgroundColor) {
/* 371 */     GlStateManager.pushMatrix();
/* 372 */     GlStateManager.translate(posX, posY, posZ);
/* 373 */     GL11.glNormal3f(0.0F, 0.0F, 0.0F);
/* 374 */     GlStateManager.rotate(-(mc.getRenderManager()).playerViewY, 0.0F, 1.0F, 0.0F);
/* 375 */     GlStateManager.scale(-0.1D, -0.1D, 0.1D);
/* 376 */     GL11.glDisable(2929);
/* 377 */     GL11.glBlendFunc(770, 771);
/* 378 */     GlStateManager.enableTexture2D();
/* 379 */     GlStateManager.depthMask(true);
/*     */     
/* 381 */     drawRect(-7.0F, 2.0F, -4.0F, 3.0F, color);
/* 382 */     drawRect(4.0F, 2.0F, 7.0F, 3.0F, color);
/* 383 */     drawRect(-7.0F, 0.5F, -6.0F, 3.0F, color);
/* 384 */     drawRect(6.0F, 0.5F, 7.0F, 3.0F, color);
/*     */     
/* 386 */     drawRect(-7.0F, 3.0F, -4.0F, 3.3F, backgroundColor);
/* 387 */     drawRect(4.0F, 3.0F, 7.0F, 3.3F, backgroundColor);
/* 388 */     drawRect(-7.3F, 0.5F, -7.0F, 3.3F, backgroundColor);
/* 389 */     drawRect(7.0F, 0.5F, 7.3F, 3.3F, backgroundColor);
/*     */     
/* 391 */     GlStateManager.translate(0.0D, 21.0D + -((entity.getEntityBoundingBox()).maxY - (entity.getEntityBoundingBox()).minY) * 12.0D, 0.0D);
/*     */     
/* 393 */     drawRect(4.0F, -20.0F, 7.0F, -19.0F, color);
/* 394 */     drawRect(-7.0F, -20.0F, -4.0F, -19.0F, color);
/* 395 */     drawRect(6.0F, -20.0F, 7.0F, -17.5F, color);
/* 396 */     drawRect(-7.0F, -20.0F, -6.0F, -17.5F, color);
/*     */     
/* 398 */     drawRect(7.0F, -20.0F, 7.3F, -17.5F, backgroundColor);
/* 399 */     drawRect(-7.3F, -20.0F, -7.0F, -17.5F, backgroundColor);
/* 400 */     drawRect(4.0F, -20.3F, 7.3F, -20.0F, backgroundColor);
/* 401 */     drawRect(-7.3F, -20.3F, -4.0F, -20.0F, backgroundColor);
/*     */ 
/*     */     
/* 404 */     GL11.glEnable(2929);
/* 405 */     GlStateManager.popMatrix();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void draw2D(BlockPos blockPos, int color, int backgroundColor) {
/* 410 */     double posX = blockPos.getX() + 0.5D - (mc.getRenderManager()).renderPosX;
/* 411 */     double posY = blockPos.getY() - (mc.getRenderManager()).renderPosY;
/* 412 */     double posZ = blockPos.getZ() + 0.5D - (mc.getRenderManager()).renderPosZ;
/*     */     
/* 414 */     GlStateManager.pushMatrix();
/* 415 */     GlStateManager.translate(posX, posY, posZ);
/* 416 */     GL11.glNormal3f(0.0F, 0.0F, 0.0F);
/* 417 */     GlStateManager.rotate(-(mc.getRenderManager()).playerViewY, 0.0F, 1.0F, 0.0F);
/* 418 */     GlStateManager.scale(-0.1D, -0.1D, 0.1D);
/* 419 */     setGlCap(2929, false);
/* 420 */     GL11.glBlendFunc(770, 771);
/* 421 */     GlStateManager.enableTexture2D();
/* 422 */     GlStateManager.depthMask(true);
/*     */     
/* 424 */     drawRect(-7.0F, 2.0F, -4.0F, 3.0F, color);
/* 425 */     drawRect(4.0F, 2.0F, 7.0F, 3.0F, color);
/* 426 */     drawRect(-7.0F, 0.5F, -6.0F, 3.0F, color);
/* 427 */     drawRect(6.0F, 0.5F, 7.0F, 3.0F, color);
/*     */     
/* 429 */     drawRect(-7.0F, 3.0F, -4.0F, 3.3F, backgroundColor);
/* 430 */     drawRect(4.0F, 3.0F, 7.0F, 3.3F, backgroundColor);
/* 431 */     drawRect(-7.3F, 0.5F, -7.0F, 3.3F, backgroundColor);
/* 432 */     drawRect(7.0F, 0.5F, 7.3F, 3.3F, backgroundColor);
/*     */     
/* 434 */     GlStateManager.translate(0.0F, 9.0F, 0.0F);
/*     */     
/* 436 */     drawRect(4.0F, -20.0F, 7.0F, -19.0F, color);
/* 437 */     drawRect(-7.0F, -20.0F, -4.0F, -19.0F, color);
/* 438 */     drawRect(6.0F, -20.0F, 7.0F, -17.5F, color);
/* 439 */     drawRect(-7.0F, -20.0F, -6.0F, -17.5F, color);
/*     */     
/* 441 */     drawRect(7.0F, -20.0F, 7.3F, -17.5F, backgroundColor);
/* 442 */     drawRect(-7.3F, -20.0F, -7.0F, -17.5F, backgroundColor);
/* 443 */     drawRect(4.0F, -20.3F, 7.3F, -20.0F, backgroundColor);
/* 444 */     drawRect(-7.3F, -20.3F, -4.0F, -20.0F, backgroundColor);
/*     */ 
/*     */     
/* 447 */     resetCaps();
/* 448 */     GlStateManager.popMatrix();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void renderNameTag(String string, double x, double y, double z) {
/* 453 */     GL11.glPushMatrix();
/* 454 */     GL11.glTranslated(x - (mc.getRenderManager()).renderPosX, y - (mc.getRenderManager()).renderPosY, z - (mc.getRenderManager()).renderPosZ);
/* 455 */     GL11.glNormal3f(0.0F, 1.0F, 0.0F);
/* 456 */     GL11.glRotatef(-(mc.getRenderManager()).playerViewY, 0.0F, 1.0F, 0.0F);
/* 457 */     GL11.glRotatef((mc.getRenderManager()).playerViewX, 1.0F, 0.0F, 0.0F);
/* 458 */     GL11.glScalef(-0.05F, -0.05F, 0.05F);
/* 459 */     setGlCap(2896, false);
/* 460 */     setGlCap(2929, false);
/* 461 */     setGlCap(3042, true);
/* 462 */     GL11.glBlendFunc(770, 771);
/*     */     
/* 464 */     int width = Client.instance.FontLoaders.SF32.getStringWidth(string) / 2;
/*     */     
/* 466 */     Gui.drawRect((-width - 1), -1.0D, (width + 1), 17.5D, -2147483648);
/* 467 */     Client.instance.FontLoaders.SF32.drawStringWithShadow(string, -width, 1.5D, Color.WHITE.getRGB());
/*     */     
/* 469 */     resetCaps();
/* 470 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 471 */     GL11.glPopMatrix();
/*     */   }
/*     */   
/*     */   public static void drawLine(double x, double y, double x1, double y1, float width) {
/* 475 */     GL11.glDisable(3553);
/* 476 */     GL11.glLineWidth(width);
/* 477 */     GL11.glBegin(1);
/* 478 */     GL11.glVertex2d(x, y);
/* 479 */     GL11.glVertex2d(x1, y1);
/* 480 */     GL11.glEnd();
/* 481 */     GL11.glEnable(3553);
/*     */   }
/*     */   
/*     */   public static void makeScissorBox(float x, float y, float x2, float y2) {
/* 485 */     ScaledResolution scaledResolution = new ScaledResolution(mc);
/* 486 */     int factor = scaledResolution.getScaleFactor();
/* 487 */     GL11.glScissor((int)(x * factor), (int)((scaledResolution.getScaledHeight() - y2) * factor), (int)((x2 - x) * factor), (int)((y2 - y) * factor));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void resetCaps() {
/* 495 */     glCapMap.forEach(LiquidRender::setGlState);
/*     */   }
/*     */   
/*     */   public static void enableGlCap(int cap) {
/* 499 */     setGlCap(cap, true);
/*     */   }
/*     */   
/*     */   public static void enableGlCap(int... caps) {
/* 503 */     for (int cap : caps)
/* 504 */       setGlCap(cap, true); 
/*     */   }
/*     */   
/*     */   public static void disableGlCap(int cap) {
/* 508 */     setGlCap(cap, true);
/*     */   }
/*     */   
/*     */   public static void disableGlCap(int... caps) {
/* 512 */     for (int cap : caps)
/* 513 */       setGlCap(cap, false); 
/*     */   }
/*     */   
/*     */   public static void setGlCap(int cap, boolean state) {
/* 517 */     glCapMap.put(Integer.valueOf(cap), Boolean.valueOf(GL11.glGetBoolean(cap)));
/* 518 */     setGlState(cap, state);
/*     */   }
/*     */   
/*     */   public static void setGlState(int cap, boolean state) {
/* 522 */     if (state) {
/* 523 */       GL11.glEnable(cap);
/*     */     } else {
/* 525 */       GL11.glDisable(cap);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\combat\advanced\suck\\utils\liquidbounce\LiquidRender.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */