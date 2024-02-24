/*     */ package awareline.main.ui.gui.clickgui.mode.powerx;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.Gui;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ public class NLRenderUtil
/*     */ {
/*  14 */   protected static final Minecraft MC = Minecraft.getMinecraft();
/*  15 */   public static final NLRenderUtil INSTANCE = new NLRenderUtil();
/*     */ 
/*     */   
/*     */   public float delta;
/*     */ 
/*     */ 
/*     */   
/*     */   public static void startRender() {
/*  23 */     GL11.glEnable(3042);
/*  24 */     GL11.glBlendFunc(770, 771);
/*  25 */     GL11.glDisable(3553);
/*  26 */     GL11.glDisable(3008);
/*  27 */     GL11.glDisable(2884);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void stopRender() {
/*  34 */     GL11.glEnable(2884);
/*  35 */     GL11.glEnable(3008);
/*  36 */     GL11.glEnable(3553);
/*  37 */     GL11.glDisable(3042);
/*  38 */     color(Color.white);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void color(Color color) {
/*  47 */     GL11.glColor4d(color.getRed() / 255.0D, color.getGreen() / 255.0D, color.getBlue() / 255.0D, color.getAlpha() / 255.0D);
/*     */   }
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
/*     */   public final void drawRect(double posX, double posY, double width, double height, Color color) {
/*  60 */     startRender();
/*  61 */     color(color);
/*  62 */     GL11.glBegin(7);
/*     */     
/*  64 */     GL11.glVertex2d(posX, posY);
/*  65 */     GL11.glVertex2d(posX + width, posY);
/*  66 */     GL11.glVertex2d(posX + width, posY + height);
/*  67 */     GL11.glVertex2d(posX, posY + height);
/*     */     
/*  69 */     GL11.glEnd();
/*  70 */     stopRender();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void drawCircle(double xPos, double yPos, double radius, Color color) {
/*  82 */     startRender();
/*  83 */     color(color);
/*  84 */     GL11.glBegin(9);
/*     */     
/*  86 */     drawCircle(xPos, yPos, radius);
/*     */     
/*  88 */     GL11.glEnd();
/*     */     
/*  90 */     GL11.glEnable(2848);
/*  91 */     GL11.glLineWidth(2.0F);
/*  92 */     GL11.glBegin(2);
/*     */     
/*  94 */     drawCircle(xPos, yPos, radius);
/*     */     
/*  96 */     GL11.glEnd();
/*  97 */     stopRender();
/*     */   }
/*     */   
/*     */   private void drawCircle(double xPos, double yPos, double radius) {
/* 101 */     double theta = 0.017453292519943295D;
/* 102 */     double tangetial_factor = Math.tan(0.017453292519943295D);
/* 103 */     double radial_factor = Math.cos(0.017453292519943295D);
/* 104 */     double x = radius;
/* 105 */     double y = 0.0D;
/* 106 */     for (int i = 0; i < 360; i++) {
/* 107 */       GL11.glVertex2d(x + xPos, y + yPos);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 112 */       double tx = -y;
/* 113 */       double ty = x;
/*     */ 
/*     */       
/* 116 */       x += tx * tangetial_factor;
/* 117 */       y += ty * tangetial_factor;
/*     */ 
/*     */       
/* 120 */       x *= radial_factor;
/* 121 */       y *= radial_factor;
/*     */     } 
/*     */   }
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
/*     */   public final void drawRoundedRect(double x, double y, double width, double height, double rounding, Color color) {
/* 136 */     drawRect(x + rounding, y, width - rounding * 2.0D, height, color);
/* 137 */     drawRect(x, y + rounding, width, height - rounding * 2.0D, color);
/* 138 */     x += 0.5D;
/* 139 */     y += 0.5D;
/* 140 */     width--;
/* 141 */     height--;
/* 142 */     drawCircle(x + rounding, y + rounding, rounding, color);
/* 143 */     drawCircle(x + width - rounding, y + rounding, rounding, color);
/* 144 */     drawCircle(x + rounding, y + height - rounding, rounding, color);
/* 145 */     drawCircle(x + width - rounding, y + height - rounding, rounding, color);
/*     */   }
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
/*     */   public final void image(ResourceLocation imageLocation, double x, double y, double width, double height) {
/* 159 */     GL11.glTexParameteri(3553, 10241, 9728);
/* 160 */     GL11.glTexParameteri(3553, 10240, 9728);
/* 161 */     GlStateManager.enableBlend();
/* 162 */     GlStateManager.blendFunc(770, 771);
/* 163 */     GlStateManager.disableAlpha();
/* 164 */     GlStateManager.disableCull();
/* 165 */     MC.getTextureManager().bindTexture(imageLocation);
/* 166 */     Gui.drawModalRectWithCustomSizedTexture((int)x, (int)y, 0.0F, 0.0F, (int)width, (int)height, (int)width, (int)height);
/* 167 */     GlStateManager.enableCull();
/* 168 */     GlStateManager.enableAlpha();
/* 169 */     GlStateManager.disableBlend();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void makeOutlinedBoundingBox(AxisAlignedBB bb) {
/* 178 */     GL11.glEnable(2848);
/* 179 */     GL11.glBegin(1);
/*     */     
/* 181 */     GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
/* 182 */     GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
/* 183 */     GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
/* 184 */     GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
/* 185 */     GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
/* 186 */     GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
/* 187 */     GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
/* 188 */     GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
/*     */     
/* 190 */     GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
/* 191 */     GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
/* 192 */     GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
/* 193 */     GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
/* 194 */     GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
/* 195 */     GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
/* 196 */     GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
/* 197 */     GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
/*     */     
/* 199 */     GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
/* 200 */     GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
/*     */     
/* 202 */     GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
/* 203 */     GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
/*     */     
/* 205 */     GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
/* 206 */     GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
/*     */     
/* 208 */     GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
/* 209 */     GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
/*     */ 
/*     */     
/* 212 */     GL11.glEnd();
/* 213 */     GL11.glDisable(2848);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void drawFilledBoundingBox(AxisAlignedBB bb) {
/* 222 */     GL11.glBegin(7);
/*     */     
/* 224 */     GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
/* 225 */     GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
/* 226 */     GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
/* 227 */     GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
/*     */     
/* 229 */     GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
/* 230 */     GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
/* 231 */     GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
/* 232 */     GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
/*     */     
/* 234 */     GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
/* 235 */     GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
/* 236 */     GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
/* 237 */     GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
/*     */     
/* 239 */     GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
/* 240 */     GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
/* 241 */     GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
/* 242 */     GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
/*     */     
/* 244 */     GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
/* 245 */     GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
/* 246 */     GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
/* 247 */     GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
/*     */     
/* 249 */     GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
/* 250 */     GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
/* 251 */     GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
/* 252 */     GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
/*     */     
/* 254 */     GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
/* 255 */     GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
/* 256 */     GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
/* 257 */     GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
/*     */     
/* 259 */     GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
/* 260 */     GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
/* 261 */     GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
/* 262 */     GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
/*     */     
/* 264 */     GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
/* 265 */     GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
/* 266 */     GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
/* 267 */     GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
/*     */     
/* 269 */     GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
/* 270 */     GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
/* 271 */     GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
/* 272 */     GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
/*     */     
/* 274 */     GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
/* 275 */     GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
/* 276 */     GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
/* 277 */     GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
/*     */     
/* 279 */     GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
/* 280 */     GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
/* 281 */     GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
/* 282 */     GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
/*     */     
/* 284 */     GL11.glEnd();
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\gui\clickgui\mode\powerx\NLRenderUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */