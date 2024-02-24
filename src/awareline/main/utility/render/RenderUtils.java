/*     */ package awareline.main.utility.render;
/*     */ 
/*     */ import awareline.main.utility.BlockUtils;
/*     */ import java.awt.Color;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.RenderGlobal;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.Timer;
/*     */ import net.minecraft.world.World;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.lwjgl.util.glu.Cylinder;
/*     */ 
/*     */ 
/*     */ public class RenderUtils
/*     */ {
/*  27 */   private static final Map<Integer, Boolean> glCapMap = new HashMap<>();
/*     */   
/*     */   public static void glColor(int hex) {
/*  30 */     float alpha = (hex >> 24 & 0xFF) / 255.0F;
/*  31 */     float red = (hex >> 16 & 0xFF) / 255.0F;
/*  32 */     float green = (hex >> 8 & 0xFF) / 255.0F;
/*  33 */     float blue = (hex & 0xFF) / 255.0F;
/*  34 */     GL11.glColor4f(red, green, blue, alpha);
/*     */   }
/*     */   
/*     */   public static void drawBlockBox(BlockPos blockPos, Color color, boolean outline) {
/*  38 */     Timer timer = (Minecraft.getMinecraft()).timer;
/*  39 */     double x = blockPos.getX() - (Minecraft.getMinecraft().getRenderManager()).renderPosX;
/*  40 */     double y = blockPos.getY() - (Minecraft.getMinecraft().getRenderManager()).renderPosY;
/*  41 */     double z = blockPos.getZ() - (Minecraft.getMinecraft().getRenderManager()).renderPosZ;
/*  42 */     AxisAlignedBB axisAlignedBB = new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D);
/*  43 */     Block block = BlockUtils.getBlock(blockPos);
/*     */     
/*  45 */     if (block != null) {
/*  46 */       EntityPlayerSP entityPlayerSP = (Minecraft.getMinecraft()).thePlayer;
/*     */       
/*  48 */       double posX = ((EntityPlayer)entityPlayerSP).lastTickPosX + (((EntityPlayer)entityPlayerSP).posX - ((EntityPlayer)entityPlayerSP).lastTickPosX) * timer.renderPartialTicks;
/*  49 */       double posY = ((EntityPlayer)entityPlayerSP).lastTickPosY + (((EntityPlayer)entityPlayerSP).posY - ((EntityPlayer)entityPlayerSP).lastTickPosY) * timer.renderPartialTicks;
/*  50 */       double posZ = ((EntityPlayer)entityPlayerSP).lastTickPosZ + (((EntityPlayer)entityPlayerSP).posZ - ((EntityPlayer)entityPlayerSP).lastTickPosZ) * timer.renderPartialTicks;
/*  51 */       axisAlignedBB = block.getSelectedBoundingBox((World)(Minecraft.getMinecraft()).theWorld, blockPos).expand(0.0020000000949949026D, 0.0020000000949949026D, 0.0020000000949949026D).offset(-posX, -posY, -posZ);
/*     */     } 
/*     */     
/*  54 */     GL11.glBlendFunc(770, 771);
/*  55 */     enableGlCap(3042);
/*  56 */     disableGlCap(new int[] { 3553, 2929 });
/*  57 */     GL11.glDepthMask(false);
/*     */     
/*  59 */     glColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), (color.getAlpha() == 255) ? (outline ? 26 : 35) : color.getAlpha()));
/*  60 */     drawFilledBox(axisAlignedBB);
/*     */     
/*  62 */     if (outline) {
/*  63 */       GL11.glLineWidth(1.0F);
/*  64 */       enableGlCap(2848);
/*  65 */       glColor(color);
/*  66 */       RenderGlobal.drawSelectionBoundingBox(axisAlignedBB);
/*     */     } 
/*     */     
/*  69 */     GlStateManager.resetColor();
/*  70 */     GL11.glDepthMask(true);
/*  71 */     resetCaps();
/*     */   }
/*     */   
/*     */   public static void resetCaps() {
/*  75 */     glCapMap.forEach(RenderUtils::setGlState);
/*     */   }
/*     */   
/*     */   public static void drawFilledBox(AxisAlignedBB axisAlignedBB) {
/*  79 */     Tessellator tessellator = Tessellator.getInstance();
/*  80 */     WorldRenderer worldRenderer = tessellator.getWorldRenderer();
/*  81 */     worldRenderer.begin(7, DefaultVertexFormats.POSITION);
/*  82 */     worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
/*  83 */     worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
/*  84 */     worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
/*  85 */     worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
/*  86 */     worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
/*  87 */     worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
/*  88 */     worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
/*  89 */     worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
/*  90 */     tessellator.draw();
/*  91 */     worldRenderer.begin(7, DefaultVertexFormats.POSITION);
/*  92 */     worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
/*  93 */     worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
/*  94 */     worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
/*  95 */     worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
/*  96 */     worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
/*  97 */     worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
/*  98 */     worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
/*  99 */     worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
/* 100 */     tessellator.draw();
/* 101 */     worldRenderer.begin(7, DefaultVertexFormats.POSITION);
/* 102 */     worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
/* 103 */     worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
/* 104 */     worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
/* 105 */     worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
/* 106 */     worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
/* 107 */     worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
/* 108 */     worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
/* 109 */     worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
/* 110 */     tessellator.draw();
/* 111 */     worldRenderer.begin(7, DefaultVertexFormats.POSITION);
/* 112 */     worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
/* 113 */     worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
/* 114 */     worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
/* 115 */     worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
/* 116 */     worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
/* 117 */     worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
/* 118 */     worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
/* 119 */     worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
/* 120 */     tessellator.draw();
/* 121 */     worldRenderer.begin(7, DefaultVertexFormats.POSITION);
/* 122 */     worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
/* 123 */     worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
/* 124 */     worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
/* 125 */     worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
/* 126 */     worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
/* 127 */     worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
/* 128 */     worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
/* 129 */     worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
/* 130 */     tessellator.draw();
/* 131 */     worldRenderer.begin(7, DefaultVertexFormats.POSITION);
/* 132 */     worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
/* 133 */     worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
/* 134 */     worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
/* 135 */     worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
/* 136 */     worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
/* 137 */     worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
/* 138 */     worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
/* 139 */     worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
/* 140 */     tessellator.draw();
/*     */   }
/*     */   
/*     */   public static int rainbow(int delay) {
/* 144 */     double rainbow = Math.ceil((System.currentTimeMillis() + delay) / 10.0D);
/* 145 */     return Color.getHSBColor((float)(rainbow % 360.0D / 360.0D), 0.5F, 1.0F).getRGB();
/*     */   }
/*     */   
/*     */   public static void glColor(Color color) {
/* 149 */     GL11.glColor4f(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, color.getAlpha() / 255.0F);
/*     */   }
/*     */   
/*     */   public static void enableGlCap(int cap) {
/* 153 */     setGlCap(cap, true);
/*     */   }
/*     */   
/*     */   public static void disableGlCap(int... caps) {
/* 157 */     for (int cap : caps)
/* 158 */       setGlCap(cap, false); 
/*     */   }
/*     */   
/*     */   public static void setGlCap(int cap, boolean state) {
/* 162 */     glCapMap.put(Integer.valueOf(cap), Boolean.valueOf(GL11.glGetBoolean(cap)));
/* 163 */     setGlState(cap, state);
/*     */   }
/*     */   
/*     */   public static void setGlState(int cap, boolean state) {
/* 167 */     if (state) {
/* 168 */       GL11.glEnable(cap);
/*     */     } else {
/* 170 */       GL11.glDisable(cap);
/*     */     } 
/*     */   }
/*     */   public static Color rainbow(long time, float count, float fade) {
/* 174 */     float hue = ((float)time + (1.0F + count) * 2.0E8F) / 1.0E10F % 1.0F;
/* 175 */     long color = Long.parseLong(Integer.toHexString(Color.HSBtoRGB(hue, 1.0F, 1.0F)), 16);
/* 176 */     Color c = new Color((int)color);
/* 177 */     return new Color(c.getRed() / 255.0F * fade, c.getGreen() / 255.0F * fade, c.getBlue() / 255.0F * fade, c.getAlpha() / 255.0F);
/*     */   }
/*     */   
/*     */   public static void enableSmoothLine(float width) {
/* 181 */     GL11.glDisable(3008);
/* 182 */     GL11.glEnable(3042);
/* 183 */     GL11.glBlendFunc(770, 771);
/* 184 */     GL11.glDisable(3553);
/* 185 */     GL11.glDisable(2929);
/* 186 */     GL11.glDepthMask(false);
/* 187 */     GL11.glEnable(2884);
/* 188 */     GL11.glEnable(2848);
/* 189 */     GL11.glHint(3154, 4354);
/* 190 */     GL11.glHint(3155, 4354);
/* 191 */     GL11.glLineWidth(width);
/*     */   }
/*     */   
/*     */   public static void disableSmoothLine() {
/* 195 */     GL11.glEnable(3553);
/* 196 */     GL11.glEnable(2929);
/* 197 */     GL11.glDisable(3042);
/* 198 */     GL11.glEnable(3008);
/* 199 */     GL11.glDepthMask(true);
/* 200 */     GL11.glCullFace(1029);
/* 201 */     GL11.glDisable(2848);
/* 202 */     GL11.glHint(3154, 4352);
/* 203 */     GL11.glHint(3155, 4352);
/*     */   }
/*     */   
/*     */   public static void drawCylinderESP(EntityLivingBase entity, double x, double y, double z) {
/* 207 */     GL11.glPushMatrix();
/* 208 */     GL11.glTranslated(x, y, z);
/* 209 */     GL11.glRotatef(-entity.width, 0.0F, 1.0F, 0.0F);
/* 210 */     glColor((new Color(1, 89, 1, 150)).getRGB());
/* 211 */     enableSmoothLine(2.0F);
/* 212 */     Cylinder c = new Cylinder();
/* 213 */     GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
/* 214 */     c.setDrawStyle(100011);
/* 215 */     c.draw(0.0F, 0.2F, 0.5F, 4, 200);
/* 216 */     disableSmoothLine();
/* 217 */     GL11.glPopMatrix();
/* 218 */     GL11.glPushMatrix();
/* 219 */     GL11.glTranslated(x, y + 0.5D, z);
/* 220 */     GL11.glRotatef(-entity.width, 0.0F, 1.0F, 0.0F);
/* 221 */     glColor((new Color(2, 168, 2, 150)).getRGB());
/* 222 */     enableSmoothLine(2.0F);
/* 223 */     GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
/* 224 */     c.setDrawStyle(100011);
/* 225 */     c.draw(0.2F, 0.0F, 0.5F, 4, 200);
/* 226 */     disableSmoothLine();
/* 227 */     GL11.glPopMatrix();
/*     */   }
/*     */   
/*     */   public static void start2D() {
/* 231 */     GL11.glEnable(3042);
/* 232 */     GL11.glDisable(3553);
/* 233 */     GL11.glBlendFunc(770, 771);
/* 234 */     GL11.glEnable(2848);
/*     */   }
/*     */   
/*     */   public static void stop2D() {
/* 238 */     GL11.glEnable(3553);
/* 239 */     GL11.glDisable(3042);
/* 240 */     GL11.glDisable(2848);
/* 241 */     GlStateManager.enableTexture2D();
/* 242 */     GlStateManager.disableBlend();
/* 243 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*     */   }
/*     */   
/*     */   public static void setColor(Color color) {
/* 247 */     float alpha = (color.getRGB() >> 24 & 0xFF) / 255.0F;
/* 248 */     float red = (color.getRGB() >> 16 & 0xFF) / 255.0F;
/* 249 */     float green = (color.getRGB() >> 8 & 0xFF) / 255.0F;
/* 250 */     float blue = (color.getRGB() & 0xFF) / 255.0F;
/* 251 */     GL11.glColor4f(red, green, blue, alpha);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void drawCornerBox(double x, double y, double x2, double y2, double lw, Color color) {
/* 256 */     double width = Math.abs(x2 - x);
/* 257 */     double height = Math.abs(y2 - y);
/* 258 */     double halfWidth = width / 4.0D;
/* 259 */     double halfHeight = height / 4.0D;
/* 260 */     start2D();
/* 261 */     GL11.glPushMatrix();
/* 262 */     GL11.glLineWidth((float)lw);
/* 263 */     setColor(color);
/*     */     
/* 265 */     GL11.glBegin(3);
/* 266 */     GL11.glVertex2d(x + halfWidth, y);
/* 267 */     GL11.glVertex2d(x, y);
/* 268 */     GL11.glVertex2d(x, y + halfHeight);
/* 269 */     GL11.glEnd();
/*     */ 
/*     */     
/* 272 */     GL11.glBegin(3);
/* 273 */     GL11.glVertex2d(x, y + height - halfHeight);
/* 274 */     GL11.glVertex2d(x, y + height);
/* 275 */     GL11.glVertex2d(x + halfWidth, y + height);
/* 276 */     GL11.glEnd();
/*     */     
/* 278 */     GL11.glBegin(3);
/* 279 */     GL11.glVertex2d(x + width - halfWidth, y + height);
/* 280 */     GL11.glVertex2d(x + width, y + height);
/* 281 */     GL11.glVertex2d(x + width, y + height - halfHeight);
/* 282 */     GL11.glEnd();
/*     */     
/* 284 */     GL11.glBegin(3);
/* 285 */     GL11.glVertex2d(x + width, y + halfHeight);
/* 286 */     GL11.glVertex2d(x + width, y);
/* 287 */     GL11.glVertex2d(x + width - halfWidth, y);
/* 288 */     GL11.glEnd();
/*     */     
/* 290 */     GL11.glPopMatrix();
/* 291 */     stop2D();
/*     */   }
/*     */   
/*     */   public static double interpolate(double current, double old, double scale) {
/* 295 */     return old + (current - old) * scale;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\utility\render\RenderUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */