/*      */ package awareline.main.ui.gui.clickgui;
/*      */ 
/*      */ import awareline.main.ui.gui.clickgui.mode.powerx.NLRenderUtil;
/*      */ import awareline.main.utility.render.color.ColourUtil;
/*      */ import awareline.main.utility.render.gl.GLUtil;
/*      */ import awareline.main.utility.shader.ketaUtils.render.DrawUtil;
/*      */ import java.awt.Color;
/*      */ import net.minecraft.client.Minecraft;
/*      */ import net.minecraft.client.entity.AbstractClientPlayer;
/*      */ import net.minecraft.client.gui.Gui;
/*      */ import net.minecraft.client.gui.ScaledResolution;
/*      */ import net.minecraft.client.renderer.GlStateManager;
/*      */ import net.minecraft.client.renderer.OpenGlHelper;
/*      */ import net.minecraft.client.renderer.RenderHelper;
/*      */ import net.minecraft.client.renderer.Tessellator;
/*      */ import net.minecraft.client.renderer.WorldRenderer;
/*      */ import net.minecraft.client.renderer.entity.RenderItem;
/*      */ import net.minecraft.client.renderer.entity.RenderManager;
/*      */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*      */ import net.minecraft.client.shader.Framebuffer;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.EntityLivingBase;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.minecraft.util.Vec3;
/*      */ import org.lwjgl.opengl.GL11;
/*      */ 
/*      */ public enum RenderUtil {
/*      */   public static final Minecraft mc;
/*   31 */   INSTANCE;
/*      */   static {
/*   33 */     mc = Minecraft.getMinecraft();
/*      */   }
/*      */   public static int width() {
/*   36 */     return (new ScaledResolution(mc)).getScaledWidth();
/*      */   }
/*      */   
/*      */   public static int height() {
/*   40 */     return (new ScaledResolution(mc)).getScaledHeight();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void scaleStart(float x, float y, float scale) {
/*   45 */     GL11.glPushMatrix();
/*   46 */     GL11.glTranslatef(x, y, 0.0F);
/*   47 */     GL11.glScalef(scale, scale, 1.0F);
/*   48 */     GL11.glTranslatef(-x, -y, 0.0F);
/*      */   }
/*      */   
/*      */   public static void scaleEnd() {
/*   52 */     GL11.glPopMatrix();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void drawRect(float left, float top, float right, float bottom, int color) {
/*   58 */     if (left < right) {
/*   59 */       float f4 = left;
/*   60 */       left = right;
/*   61 */       right = f4;
/*      */     } 
/*      */     
/*   64 */     if (top < bottom) {
/*   65 */       float f4 = top;
/*   66 */       top = bottom;
/*   67 */       bottom = f4;
/*      */     } 
/*      */     
/*   70 */     float f3 = (color >> 24 & 0xFF) / 255.0F;
/*   71 */     float f = (color >> 16 & 0xFF) / 255.0F;
/*   72 */     float f1 = (color >> 8 & 0xFF) / 255.0F;
/*   73 */     float f2 = (color & 0xFF) / 255.0F;
/*   74 */     Tessellator tessellator = Tessellator.getInstance();
/*   75 */     WorldRenderer WorldRenderer = tessellator.getWorldRenderer();
/*   76 */     GlStateManager.enableBlend();
/*   77 */     GlStateManager.disableTexture2D();
/*   78 */     GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/*   79 */     GlStateManager.color(f, f1, f2, f3);
/*   80 */     WorldRenderer.begin(7, DefaultVertexFormats.POSITION);
/*   81 */     WorldRenderer.pos(left, bottom, 0.0D).endVertex();
/*   82 */     WorldRenderer.pos(right, bottom, 0.0D).endVertex();
/*   83 */     WorldRenderer.pos(right, top, 0.0D).endVertex();
/*   84 */     WorldRenderer.pos(left, top, 0.0D).endVertex();
/*   85 */     tessellator.draw();
/*   86 */     GlStateManager.enableTexture2D();
/*   87 */     GlStateManager.disableBlend();
/*      */   }
/*      */   
/*      */   public static void drawRectForFloat(float l, float t, float r, float b, int c) {
/*   91 */     drawRect(l, t, r, b, c);
/*      */   }
/*      */   
/*      */   public static void start2D() {
/*   95 */     GL11.glEnable(3042);
/*   96 */     GL11.glDisable(3553);
/*   97 */     GL11.glBlendFunc(770, 771);
/*   98 */     GL11.glEnable(2848);
/*      */   }
/*      */   
/*      */   public static void stop2D() {
/*  102 */     GL11.glEnable(3553);
/*  103 */     GL11.glDisable(3042);
/*  104 */     GL11.glDisable(2848);
/*  105 */     GlStateManager.enableTexture2D();
/*  106 */     GlStateManager.disableBlend();
/*  107 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*      */   }
/*      */   
/*      */   public static void setColor(Color color) {
/*  111 */     float alpha = (color.getRGB() >> 24 & 0xFF) / 255.0F;
/*  112 */     float red = (color.getRGB() >> 16 & 0xFF) / 255.0F;
/*  113 */     float green = (color.getRGB() >> 8 & 0xFF) / 255.0F;
/*  114 */     float blue = (color.getRGB() & 0xFF) / 255.0F;
/*  115 */     GL11.glColor4f(red, green, blue, alpha);
/*      */   }
/*      */   
/*      */   public static void drawCornerBox(double x, double y, double x2, double y2, double lw, Color color) {
/*  119 */     double width = Math.abs(x2 - x);
/*  120 */     double height = Math.abs(y2 - y);
/*  121 */     double halfWidth = width / 4.0D;
/*  122 */     double halfHeight = height / 4.0D;
/*  123 */     start2D();
/*  124 */     GL11.glPushMatrix();
/*  125 */     GL11.glLineWidth((float)lw);
/*  126 */     setColor(color);
/*      */     
/*  128 */     GL11.glBegin(3);
/*  129 */     GL11.glVertex2d(x + halfWidth, y);
/*  130 */     GL11.glVertex2d(x, y);
/*  131 */     GL11.glVertex2d(x, y + halfHeight);
/*  132 */     GL11.glEnd();
/*      */ 
/*      */     
/*  135 */     GL11.glBegin(3);
/*  136 */     GL11.glVertex2d(x, y + height - halfHeight);
/*  137 */     GL11.glVertex2d(x, y + height);
/*  138 */     GL11.glVertex2d(x + halfWidth, y + height);
/*  139 */     GL11.glEnd();
/*      */     
/*  141 */     GL11.glBegin(3);
/*  142 */     GL11.glVertex2d(x + width - halfWidth, y + height);
/*  143 */     GL11.glVertex2d(x + width, y + height);
/*  144 */     GL11.glVertex2d(x + width, y + height - halfHeight);
/*  145 */     GL11.glEnd();
/*      */     
/*  147 */     GL11.glBegin(3);
/*  148 */     GL11.glVertex2d(x + width, y + halfHeight);
/*  149 */     GL11.glVertex2d(x + width, y);
/*  150 */     GL11.glVertex2d(x + width - halfWidth, y);
/*  151 */     GL11.glEnd();
/*      */     
/*  153 */     GL11.glPopMatrix();
/*  154 */     stop2D();
/*      */   }
/*      */   
/*      */   public static void color(int color) {
/*  158 */     float f = (color >> 24 & 0xFF) / 255.0F;
/*  159 */     float f1 = (color >> 16 & 0xFF) / 255.0F;
/*  160 */     float f2 = (color >> 8 & 0xFF) / 255.0F;
/*  161 */     float f3 = (color & 0xFF) / 255.0F;
/*  162 */     GL11.glColor4f(f1, f2, f3, f);
/*      */   }
/*      */   
/*      */   public static void drawImage(ResourceLocation image, double x, double y, double width, double height, int color) {
/*  166 */     GlStateManager.pushMatrix();
/*  167 */     resetColor();
/*  168 */     GL11.glDisable(2929);
/*  169 */     GL11.glEnable(3042);
/*  170 */     GL11.glDepthMask(false);
/*  171 */     OpenGlHelper.glBlendFunc(770, 771, 1, 0);
/*  172 */     Color color1 = new Color(color);
/*  173 */     GL11.glColor4f(color1.getRed() / 255.0F, color1.getGreen() / 255.0F, color1.getBlue() / 255.0F, color1.getAlpha() / 255.0F);
/*  174 */     mc.getTextureManager().bindTexture(image);
/*  175 */     drawModalRectWithCustomSizedTexture((float)x, (float)y, 0.0F, 0.0F, (float)width, (float)height, (float)width, (float)height);
/*  176 */     GL11.glDepthMask(true);
/*  177 */     GL11.glDisable(3042);
/*  178 */     GL11.glEnable(2929);
/*  179 */     GlStateManager.popMatrix();
/*      */   }
/*      */   
/*      */   public static void drawModalRectWithCustomSizedTexture(float x, float y, float u, float v, float width, float height, float textureWidth, float textureHeight) {
/*  183 */     float f = 1.0F / textureWidth;
/*  184 */     float f1 = 1.0F / textureHeight;
/*  185 */     Tessellator tessellator = Tessellator.getInstance();
/*  186 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/*  187 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/*  188 */     worldrenderer.pos(x, (y + height), 0.0D).tex((u * f), ((v + height) * f1)).endVertex();
/*  189 */     worldrenderer.pos((x + width), (y + height), 0.0D).tex(((u + width) * f), ((v + height) * f1)).endVertex();
/*  190 */     worldrenderer.pos((x + width), y, 0.0D).tex(((u + width) * f), (v * f1)).endVertex();
/*  191 */     worldrenderer.pos(x, y, 0.0D).tex((u * f), (v * f1)).endVertex();
/*  192 */     tessellator.draw();
/*      */   }
/*      */   
/*      */   public static void FixJumpGLBug(float x, float y, float width, float height) {
/*  196 */     Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0F, 0.0F, width, height, width, height);
/*      */   }
/*      */   
/*      */   public static void drawImage(ResourceLocation image, float x, float y, float width, float height) {
/*  200 */     GlStateManager.disableAlpha();
/*  201 */     GlStateManager.disableRescaleNormal();
/*  202 */     GlStateManager.disableLighting();
/*  203 */     GL11.glEnable(3042);
/*  204 */     GL11.glDepthMask(false);
/*  205 */     OpenGlHelper.glBlendFunc(770, 771, 1, 0);
/*  206 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  207 */     mc.getTextureManager().bindTexture(image);
/*  208 */     Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0F, 0.0F, width, height, width, height);
/*  209 */     GL11.glDepthMask(true);
/*  210 */     GL11.glEnable(2929);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void arc(float x, float y, float start, float end, float radius, int color) {
/*  215 */     arcEllipse(x, y, start, end, radius, radius, color);
/*      */   }
/*      */   
/*      */   public static void arcEllipse(float x, float y, float start, float end, float w, float h, int color) {
/*  219 */     GlStateManager.color(0.0F, 0.0F, 0.0F);
/*  220 */     GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.0F);
/*      */     
/*  222 */     if (start > end) {
/*  223 */       float temp = end;
/*  224 */       end = start;
/*  225 */       start = temp;
/*      */     } 
/*      */     
/*  228 */     float var11 = (color >> 24 & 0xFF) / 255.0F;
/*  229 */     float var12 = (color >> 16 & 0xFF) / 255.0F;
/*  230 */     float var13 = (color >> 8 & 0xFF) / 255.0F;
/*  231 */     float var14 = (color & 0xFF) / 255.0F;
/*  232 */     GlStateManager.enableBlend();
/*  233 */     GlStateManager.disableTexture2D();
/*  234 */     GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/*  235 */     GlStateManager.color(var12, var13, var14, var11);
/*      */ 
/*      */ 
/*      */     
/*  239 */     if (var11 > 0.5F) {
/*  240 */       GL11.glEnable(2848);
/*  241 */       GL11.glLineWidth(2.0F);
/*  242 */       GL11.glBegin(3);
/*      */       float f;
/*  244 */       for (f = end; f >= start; f -= 4.0F) {
/*  245 */         float ldx = (float)Math.cos(f * Math.PI / 180.0D) * w * 1.001F;
/*  246 */         float ldy = (float)Math.sin(f * Math.PI / 180.0D) * h * 1.001F;
/*  247 */         GL11.glVertex2f(x + ldx, y + ldy);
/*      */       } 
/*      */       
/*  250 */       GL11.glEnd();
/*  251 */       GL11.glDisable(2848);
/*      */     } 
/*      */     
/*  254 */     GL11.glBegin(6);
/*      */     float i;
/*  256 */     for (i = end; i >= start; i -= 4.0F) {
/*  257 */       float ldx = (float)Math.cos(i * Math.PI / 180.0D) * w;
/*  258 */       float ldy = (float)Math.sin(i * Math.PI / 180.0D) * h;
/*  259 */       GL11.glVertex2f(x + ldx, y + ldy);
/*      */     } 
/*      */     
/*  262 */     GL11.glEnd();
/*  263 */     GlStateManager.enableTexture2D();
/*  264 */     GlStateManager.disableBlend();
/*      */   }
/*      */   
/*      */   public static void drawGradientSideways(double left, double top, double right, double bottom, int col1, int col2) {
/*  268 */     float f = (col1 >> 24 & 0xFF) / 255.0F;
/*  269 */     float f1 = (col1 >> 16 & 0xFF) / 255.0F;
/*  270 */     float f2 = (col1 >> 8 & 0xFF) / 255.0F;
/*  271 */     float f3 = (col1 & 0xFF) / 255.0F;
/*  272 */     float f4 = (col2 >> 24 & 0xFF) / 255.0F;
/*  273 */     float f5 = (col2 >> 16 & 0xFF) / 255.0F;
/*  274 */     float f6 = (col2 >> 8 & 0xFF) / 255.0F;
/*  275 */     float f7 = (col2 & 0xFF) / 255.0F;
/*  276 */     GL11.glEnable(3042);
/*  277 */     GL11.glDisable(3553);
/*  278 */     GL11.glBlendFunc(770, 771);
/*  279 */     GL11.glEnable(2848);
/*  280 */     GL11.glShadeModel(7425);
/*  281 */     GL11.glPushMatrix();
/*  282 */     GL11.glBegin(7);
/*  283 */     GL11.glColor4f(f1, f2, f3, f);
/*  284 */     GL11.glVertex2d(left, top);
/*  285 */     GL11.glVertex2d(left, bottom);
/*  286 */     GL11.glColor4f(f5, f6, f7, f4);
/*  287 */     GL11.glVertex2d(right, bottom);
/*  288 */     GL11.glVertex2d(right, top);
/*  289 */     GL11.glEnd();
/*  290 */     GL11.glPopMatrix();
/*  291 */     GL11.glEnable(3553);
/*  292 */     GL11.glDisable(3042);
/*  293 */     GL11.glDisable(2848);
/*  294 */     GL11.glShadeModel(7424);
/*      */   }
/*      */   
/*      */   public static void drawGradientSidewaysForFloat(float left, float top, float right, float bottom, int col1, int col2) {
/*  298 */     float f = (col1 >> 24 & 0xFF) / 255.0F;
/*  299 */     float f1 = (col1 >> 16 & 0xFF) / 255.0F;
/*  300 */     float f2 = (col1 >> 8 & 0xFF) / 255.0F;
/*  301 */     float f3 = (col1 & 0xFF) / 255.0F;
/*  302 */     float f4 = (col2 >> 24 & 0xFF) / 255.0F;
/*  303 */     float f5 = (col2 >> 16 & 0xFF) / 255.0F;
/*  304 */     float f6 = (col2 >> 8 & 0xFF) / 255.0F;
/*  305 */     float f7 = (col2 & 0xFF) / 255.0F;
/*  306 */     GL11.glEnable(3042);
/*  307 */     GL11.glDisable(3553);
/*  308 */     GL11.glBlendFunc(770, 771);
/*  309 */     GL11.glEnable(2848);
/*  310 */     GL11.glShadeModel(7425);
/*  311 */     GL11.glPushMatrix();
/*  312 */     GL11.glBegin(7);
/*  313 */     GL11.glColor4f(f1, f2, f3, f);
/*  314 */     GL11.glVertex2d(left, top);
/*  315 */     GL11.glVertex2d(left, bottom);
/*  316 */     GL11.glColor4f(f5, f6, f7, f4);
/*  317 */     GL11.glVertex2d(right, bottom);
/*  318 */     GL11.glVertex2d(right, top);
/*  319 */     GL11.glEnd();
/*  320 */     GL11.glPopMatrix();
/*  321 */     GL11.glEnable(3553);
/*  322 */     GL11.glDisable(3042);
/*  323 */     GL11.glDisable(2848);
/*  324 */     GL11.glShadeModel(7424);
/*      */   }
/*      */   
/*      */   public static void rectangleBordered(double x, double y, double x1, double y1, double width, int internalColor, int borderColor) {
/*  328 */     rectangle(x + width, y + width, x1 - width, y1 - width, internalColor);
/*  329 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  330 */     rectangle(x + width, y, x1 - width, y + width, borderColor);
/*  331 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  332 */     rectangle(x, y, x + width, y1, borderColor);
/*  333 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  334 */     rectangle(x1 - width, y, x1, y1, borderColor);
/*  335 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  336 */     rectangle(x + width, y1 - width, x1 - width, y1, borderColor);
/*  337 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void rectangle(double left, double top, double right, double bottom, int color) {
/*  342 */     if (left < right) {
/*  343 */       double var11 = left;
/*  344 */       left = right;
/*  345 */       right = var11;
/*      */     } 
/*      */     
/*  348 */     if (top < bottom) {
/*  349 */       double var11 = top;
/*  350 */       top = bottom;
/*  351 */       bottom = var11;
/*      */     } 
/*      */     
/*  354 */     float var111 = (color >> 24 & 0xFF) / 255.0F;
/*  355 */     float var6 = (color >> 16 & 0xFF) / 255.0F;
/*  356 */     float var7 = (color >> 8 & 0xFF) / 255.0F;
/*  357 */     float var8 = (color & 0xFF) / 255.0F;
/*  358 */     Tessellator tessellator = Tessellator.getInstance();
/*  359 */     WorldRenderer worldRenderer = tessellator.getWorldRenderer();
/*  360 */     GlStateManager.enableBlend();
/*  361 */     GlStateManager.disableTexture2D();
/*  362 */     GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/*  363 */     GlStateManager.color(var6, var7, var8, var111);
/*  364 */     worldRenderer.begin(7, DefaultVertexFormats.POSITION);
/*  365 */     worldRenderer.pos(left, bottom, 0.0D).endVertex();
/*  366 */     worldRenderer.pos(right, bottom, 0.0D).endVertex();
/*  367 */     worldRenderer.pos(right, top, 0.0D).endVertex();
/*  368 */     worldRenderer.pos(left, top, 0.0D).endVertex();
/*  369 */     tessellator.draw();
/*  370 */     GlStateManager.enableTexture2D();
/*  371 */     GlStateManager.disableBlend();
/*  372 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*      */   }
/*      */   
/*      */   public static void drawBorderedRect2(float x, float y, float width, float height, float borderWidth, int rectColor, int borderColor) {
/*  376 */     drawRect(x + borderWidth, y + borderWidth, width - borderWidth * 2.0F, height - borderWidth * 2.0F, rectColor);
/*  377 */     drawRect(x, y, width, borderWidth, borderColor);
/*  378 */     drawRect(x, y + borderWidth, borderWidth, height - borderWidth, borderColor);
/*  379 */     drawRect(x + width - borderWidth, y + borderWidth, borderWidth, height - borderWidth, borderColor);
/*  380 */     drawRect(x + borderWidth, y + height - borderWidth, width - borderWidth * 2.0F, borderWidth, borderColor);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void drawBorderedRect(double x2, double d2, double x22, double e2, float l1, int col1, int col2) {
/*  385 */     drawRect(x2, d2, x22, e2, col2);
/*  386 */     float f2 = (col1 >> 24 & 0xFF) / 255.0F;
/*  387 */     float f22 = (col1 >> 16 & 0xFF) / 255.0F;
/*  388 */     float f3 = (col1 >> 8 & 0xFF) / 255.0F;
/*  389 */     float f4 = (col1 & 0xFF) / 255.0F;
/*  390 */     GL11.glEnable(3042);
/*  391 */     GL11.glDisable(3553);
/*  392 */     GL11.glBlendFunc(770, 771);
/*  393 */     GL11.glEnable(2848);
/*  394 */     GL11.glPushMatrix();
/*  395 */     GL11.glColor4f(f22, f3, f4, f2);
/*  396 */     GL11.glLineWidth(l1);
/*  397 */     GL11.glBegin(1);
/*  398 */     GL11.glVertex2d(x2, d2);
/*  399 */     GL11.glVertex2d(x2, e2);
/*  400 */     GL11.glVertex2d(x22, e2);
/*  401 */     GL11.glVertex2d(x22, d2);
/*  402 */     GL11.glVertex2d(x2, d2);
/*  403 */     GL11.glVertex2d(x22, d2);
/*  404 */     GL11.glVertex2d(x2, e2);
/*  405 */     GL11.glVertex2d(x22, e2);
/*  406 */     GL11.glEnd();
/*  407 */     GL11.glPopMatrix();
/*  408 */     GL11.glEnable(3553);
/*  409 */     GL11.glDisable(3042);
/*  410 */     GL11.glDisable(2848);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void drawBorderedRectNotSmooth(double x2, double d2, double x22, double e2, float l1, int col1, int col2) {
/*  415 */     drawRect(x2, d2, x22, e2, col2);
/*  416 */     float f2 = (col1 >> 24 & 0xFF) / 255.0F;
/*  417 */     float f22 = (col1 >> 16 & 0xFF) / 255.0F;
/*  418 */     float f3 = (col1 >> 8 & 0xFF) / 255.0F;
/*  419 */     float f4 = (col1 & 0xFF) / 255.0F;
/*  420 */     GL11.glEnable(3042);
/*  421 */     GL11.glDisable(3553);
/*  422 */     GL11.glBlendFunc(770, 771);
/*  423 */     GL11.glEnable(2848);
/*  424 */     GL11.glPushMatrix();
/*  425 */     GL11.glColor4f(f22, f3, f4, f2);
/*  426 */     GL11.glLineWidth(l1);
/*  427 */     GL11.glBegin(1);
/*  428 */     GL11.glVertex2d(x2, d2);
/*  429 */     GL11.glVertex2d(x2, e2);
/*  430 */     GL11.glVertex2d(x22, e2);
/*  431 */     GL11.glVertex2d(x22, d2);
/*  432 */     GL11.glVertex2d(x2, d2);
/*  433 */     GL11.glVertex2d(x22, d2);
/*  434 */     GL11.glVertex2d(x2, e2);
/*  435 */     GL11.glVertex2d(x22, e2);
/*  436 */     GL11.glEnd();
/*  437 */     GL11.glPopMatrix();
/*  438 */     GL11.glEnable(3553);
/*  439 */     GL11.glDisable(3042);
/*  440 */     GL11.glDisable(2848);
/*      */   }
/*      */   
/*      */   public static void drawEntityOnScreen(int p_147046_0_, int p_147046_1_, int p_147046_2_, float p_147046_3_, float p_147046_4_, EntityLivingBase p_147046_5_) {
/*  444 */     GlStateManager.enableColorMaterial();
/*  445 */     GlStateManager.pushMatrix();
/*  446 */     GlStateManager.translate(p_147046_0_, p_147046_1_, 40.0F);
/*  447 */     GlStateManager.scale(-p_147046_2_, p_147046_2_, p_147046_2_);
/*  448 */     GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
/*  449 */     float var6 = p_147046_5_.renderYawOffset;
/*  450 */     float var7 = p_147046_5_.rotationYaw;
/*  451 */     float var8 = p_147046_5_.rotationPitch;
/*  452 */     float var9 = p_147046_5_.prevRotationYawHead;
/*  453 */     float var10 = p_147046_5_.rotationYawHead;
/*  454 */     GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
/*  455 */     RenderHelper.enableStandardItemLighting();
/*  456 */     GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
/*  457 */     GlStateManager.rotate(-((float)Math.atan((p_147046_4_ / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
/*  458 */     p_147046_5_.renderYawOffset = (float)Math.atan((p_147046_3_ / 40.0F)) * -14.0F;
/*  459 */     p_147046_5_.rotationYaw = (float)Math.atan((p_147046_3_ / 40.0F)) * -14.0F;
/*  460 */     p_147046_5_.rotationPitch = -((float)Math.atan((p_147046_4_ / 40.0F))) * 15.0F;
/*  461 */     p_147046_5_.rotationYawHead = p_147046_5_.rotationYaw;
/*  462 */     p_147046_5_.prevRotationYawHead = p_147046_5_.rotationYaw;
/*  463 */     GlStateManager.translate(0.0F, 0.0F, 0.0F);
/*  464 */     RenderManager var11 = mc.getRenderManager();
/*  465 */     var11.setPlayerViewY(180.0F);
/*  466 */     var11.setRenderShadow(false);
/*  467 */     var11.renderEntityWithPosYaw((Entity)p_147046_5_, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
/*  468 */     var11.setRenderShadow(true);
/*  469 */     p_147046_5_.renderYawOffset = var6;
/*  470 */     p_147046_5_.rotationYaw = var7;
/*  471 */     p_147046_5_.rotationPitch = var8;
/*  472 */     p_147046_5_.prevRotationYawHead = var9;
/*  473 */     p_147046_5_.rotationYawHead = var10;
/*  474 */     GlStateManager.popMatrix();
/*  475 */     RenderHelper.disableStandardItemLighting();
/*  476 */     GlStateManager.disableRescaleNormal();
/*  477 */     GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
/*  478 */     GlStateManager.disableTexture2D();
/*  479 */     GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void drawRect(double d, double e, double g, double h, int color) {
/*  484 */     if (d < g) {
/*  485 */       int f3 = (int)d;
/*  486 */       d = g;
/*  487 */       g = f3;
/*      */     } 
/*      */     
/*  490 */     if (e < h) {
/*  491 */       int f3 = (int)e;
/*  492 */       e = h;
/*  493 */       h = f3;
/*      */     } 
/*      */     
/*  496 */     float f31 = (color >> 24 & 0xFF) / 255.0F;
/*  497 */     float f = (color >> 16 & 0xFF) / 255.0F;
/*  498 */     float f1 = (color >> 8 & 0xFF) / 255.0F;
/*  499 */     float f2 = (color & 0xFF) / 255.0F;
/*  500 */     Tessellator tessellator = Tessellator.getInstance();
/*  501 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/*  502 */     GlStateManager.enableBlend();
/*  503 */     GlStateManager.disableTexture2D();
/*  504 */     GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/*  505 */     GlStateManager.color(f, f1, f2, f31);
/*  506 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION);
/*  507 */     worldrenderer.pos(d, h, 0.0D).endVertex();
/*  508 */     worldrenderer.pos(g, h, 0.0D).endVertex();
/*  509 */     worldrenderer.pos(g, e, 0.0D).endVertex();
/*  510 */     worldrenderer.pos(d, e, 0.0D).endVertex();
/*  511 */     tessellator.draw();
/*  512 */     GlStateManager.enableTexture2D();
/*  513 */     GlStateManager.disableBlend();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void drawRoundedRect(float left, float top, float right, float bottom, int color) {
/*  518 */     drawRect(left - 0.5F, top + 0.5F, left, bottom - 0.5F, color);
/*      */     
/*  520 */     drawRect(right, top + 0.5F, right + 0.5F, bottom - 0.5F, color);
/*      */     
/*  522 */     drawRect(left + 0.5F, top - 0.5F, right - 0.5F, top, color);
/*      */     
/*  524 */     drawRect(left + 0.5F, bottom, right - 0.5F, bottom + 0.5F, color);
/*  525 */     drawRect(left, top, right, bottom, color);
/*      */   }
/*      */   
/*      */   public static void drawRoundRect(double d, double e, double g, double h, int color) {
/*  529 */     drawRect(d + 1.0D, e, g - 1.0D, h, color);
/*  530 */     drawRect(d, e + 1.0D, d + 1.0D, h - 1.0D, color);
/*  531 */     drawRect(d + 1.0D, e + 1.0D, d + 0.5D, e + 0.5D, color);
/*  532 */     drawRect(d + 1.0D, e + 1.0D, d + 0.5D, e + 0.5D, color);
/*  533 */     drawRect(g - 1.0D, e + 1.0D, g - 0.5D, e + 0.5D, color);
/*  534 */     drawRect(g - 1.0D, e + 1.0D, g, h - 1.0D, color);
/*  535 */     drawRect(d + 1.0D, h - 1.0D, d + 0.5D, h - 0.5D, color);
/*  536 */     drawRect(g - 1.0D, h - 1.0D, g - 0.5D, h - 0.5D, color);
/*      */   }
/*      */   
/*      */   public static void pre() {
/*  540 */     GL11.glDisable(2929);
/*  541 */     GL11.glDisable(3553);
/*  542 */     GL11.glEnable(3042);
/*  543 */     GL11.glBlendFunc(770, 771);
/*      */   }
/*      */   
/*      */   public static void post() {
/*  547 */     GL11.glDisable(3042);
/*  548 */     GL11.glEnable(3553);
/*  549 */     GL11.glEnable(2929);
/*  550 */     GL11.glColor3d(1.0D, 1.0D, 1.0D);
/*      */   }
/*      */   
/*      */   public static void drawFastRoundedRect(float x0, float y0, float x1, float y1, float radius, int color) {
/*  554 */     float f2 = (color >> 24 & 0xFF) / 255.0F;
/*  555 */     float f3 = (color >> 16 & 0xFF) / 255.0F;
/*  556 */     float f4 = (color >> 8 & 0xFF) / 255.0F;
/*  557 */     float f5 = (color & 0xFF) / 255.0F;
/*  558 */     GL11.glDisable(2884);
/*  559 */     GL11.glDisable(3553);
/*  560 */     GL11.glEnable(3042);
/*      */     
/*  562 */     GL11.glBlendFunc(770, 771);
/*  563 */     OpenGlHelper.glBlendFunc(770, 771, 1, 0);
/*  564 */     GL11.glColor4f(f3, f4, f5, f2);
/*  565 */     GL11.glBegin(5);
/*  566 */     GL11.glVertex2f(x0 + radius, y0);
/*  567 */     GL11.glVertex2f(x0 + radius, y1);
/*  568 */     GL11.glVertex2f(x1 - radius, y0);
/*  569 */     GL11.glVertex2f(x1 - radius, y1);
/*  570 */     GL11.glEnd();
/*  571 */     GL11.glBegin(5);
/*  572 */     GL11.glVertex2f(x0, y0 + radius);
/*  573 */     GL11.glVertex2f(x0 + radius, y0 + radius);
/*  574 */     GL11.glVertex2f(x0, y1 - radius);
/*  575 */     GL11.glVertex2f(x0 + radius, y1 - radius);
/*  576 */     GL11.glEnd();
/*  577 */     GL11.glBegin(5);
/*  578 */     GL11.glVertex2f(x1, y0 + radius);
/*  579 */     GL11.glVertex2f(x1 - radius, y0 + radius);
/*  580 */     GL11.glVertex2f(x1, y1 - radius);
/*  581 */     GL11.glVertex2f(x1 - radius, y1 - radius);
/*  582 */     GL11.glEnd();
/*  583 */     GL11.glBegin(6);
/*  584 */     float f6 = x1 - radius;
/*  585 */     float f7 = y0 + radius;
/*  586 */     GL11.glVertex2f(f6, f7);
/*      */     int j;
/*  588 */     for (j = 0; j <= 18; j++) {
/*  589 */       float f8 = j * 5.0F;
/*  590 */       GL11.glVertex2f((float)(f6 + radius * Math.cos(Math.toRadians(f8))), (float)(f7 - radius * Math.sin(Math.toRadians(f8))));
/*      */     } 
/*  592 */     GL11.glEnd();
/*  593 */     GL11.glBegin(6);
/*  594 */     f6 = x0 + radius;
/*  595 */     f7 = y0 + radius;
/*  596 */     GL11.glVertex2f(f6, f7);
/*  597 */     for (j = 0; j <= 18; j++) {
/*  598 */       float f9 = j * 5.0F;
/*  599 */       GL11.glVertex2f((float)(f6 - radius * Math.cos(Math.toRadians(f9))), (float)(f7 - radius * Math.sin(Math.toRadians(f9))));
/*      */     } 
/*  601 */     GL11.glEnd();
/*  602 */     GL11.glBegin(6);
/*  603 */     f6 = x0 + radius;
/*  604 */     f7 = y1 - radius;
/*  605 */     GL11.glVertex2f(f6, f7);
/*  606 */     for (j = 0; j <= 18; j++) {
/*  607 */       float f10 = j * 5.0F;
/*  608 */       GL11.glVertex2f((float)(f6 - radius * Math.cos(Math.toRadians(f10))), (float)(f7 + radius * Math.sin(Math.toRadians(f10))));
/*      */     } 
/*  610 */     GL11.glEnd();
/*  611 */     GL11.glBegin(6);
/*  612 */     f6 = x1 - radius;
/*  613 */     f7 = y1 - radius;
/*  614 */     GL11.glVertex2f(f6, f7);
/*  615 */     for (j = 0; j <= 18; j++) {
/*  616 */       float f11 = j * 5.0F;
/*  617 */       GL11.glVertex2f((float)(f6 + radius * Math.cos(Math.toRadians(f11))), (float)(f7 + radius * Math.sin(Math.toRadians(f11))));
/*      */     } 
/*  619 */     GL11.glEnd();
/*  620 */     GL11.glEnable(3553);
/*  621 */     GL11.glEnable(2884);
/*  622 */     GL11.glDisable(3042);
/*  623 */     GlStateManager.enableTexture2D();
/*  624 */     GlStateManager.disableBlend();
/*      */   }
/*      */   
/*      */   public static void drawRoundedRectSmooth(float x, float y, float x2, float y2, float round, int color) {
/*  628 */     x = (float)(x + (round / 2.0F) + 0.5D);
/*  629 */     y = (float)(y + (round / 2.0F) + 0.5D);
/*  630 */     x2 = (float)(x2 - (round / 2.0F) + 0.5D);
/*  631 */     y2 = (float)(y2 - (round / 2.0F) + 0.5D);
/*  632 */     drawRect(x, y, x2, y2, color);
/*  633 */     circle(x2 - round / 2.0F, y + round / 2.0F, round, color);
/*  634 */     circle(x + round / 2.0F, y2 - round / 2.0F, round, color);
/*  635 */     circle(x + round / 2.0F, y + round / 2.0F, round, color);
/*  636 */     circle(x2 - round / 2.0F, y2 - round / 2.0F, round, color);
/*  637 */     drawRect(x - round / 2.0F - 0.5F, y + round / 2.0F, x2, y2 - round / 2.0F, color);
/*  638 */     drawRect(x, y + round / 2.0F, x2 + round / 2.0F + 0.5F, y2 - round / 2.0F, color);
/*  639 */     drawRect(x + round / 2.0F, y - round / 2.0F - 0.5F, x2 - round / 2.0F, y2 - round / 2.0F, color);
/*  640 */     drawRect(x + round / 2.0F, y, x2 - round / 2.0F, y2 + round / 2.0F + 0.5F, color);
/*      */   }
/*      */   
/*      */   public static void smoothRender(int value) {
/*  644 */     switch (value) {
/*      */       
/*      */       case 1:
/*  647 */         GL11.glBlendFunc(770, 771);
/*  648 */         GL11.glEnable(3042);
/*      */         
/*  650 */         GL11.glEnable(2832);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  655 */         GL11.glHint(3153, 4354);
/*      */         
/*  657 */         GL11.glEnable(2848);
/*  658 */         GL11.glHint(3154, 4354);
/*      */         
/*  660 */         GL11.glEnable(2881);
/*  661 */         GL11.glHint(3155, 4354);
/*      */         break;
/*      */       
/*      */       case 2:
/*  665 */         GL11.glDisable(3042);
/*  666 */         GL11.glDisable(2832);
/*  667 */         GL11.glDisable(2848);
/*  668 */         GL11.glDisable(2881);
/*      */         break;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void drawFastRoundedRect(int x0, float y0, int x1, float y1, float radius, int color) {
/*  676 */     float f2 = (color >> 24 & 0xFF) / 255.0F;
/*  677 */     float f3 = (color >> 16 & 0xFF) / 255.0F;
/*  678 */     float f4 = (color >> 8 & 0xFF) / 255.0F;
/*  679 */     float f5 = (color & 0xFF) / 255.0F;
/*  680 */     GL11.glDisable(2884);
/*  681 */     GL11.glDisable(3553);
/*  682 */     GL11.glEnable(3042);
/*  683 */     GL11.glBlendFunc(770, 771);
/*  684 */     OpenGlHelper.glBlendFunc(770, 771, 1, 0);
/*  685 */     GL11.glColor4f(f3, f4, f5, f2);
/*  686 */     GL11.glBegin(5);
/*  687 */     GL11.glVertex2f(x0 + radius, y0);
/*  688 */     GL11.glVertex2f(x0 + radius, y1);
/*  689 */     GL11.glVertex2f(x1 - radius, y0);
/*  690 */     GL11.glVertex2f(x1 - radius, y1);
/*  691 */     GL11.glEnd();
/*  692 */     GL11.glBegin(5);
/*  693 */     GL11.glVertex2f(x0, y0 + radius);
/*  694 */     GL11.glVertex2f(x0 + radius, y0 + radius);
/*  695 */     GL11.glVertex2f(x0, y1 - radius);
/*  696 */     GL11.glVertex2f(x0 + radius, y1 - radius);
/*  697 */     GL11.glEnd();
/*  698 */     GL11.glBegin(5);
/*  699 */     GL11.glVertex2f(x1, y0 + radius);
/*  700 */     GL11.glVertex2f(x1 - radius, y0 + radius);
/*  701 */     GL11.glVertex2f(x1, y1 - radius);
/*  702 */     GL11.glVertex2f(x1 - radius, y1 - radius);
/*  703 */     GL11.glEnd();
/*  704 */     GL11.glBegin(6);
/*  705 */     float f6 = x1 - radius;
/*  706 */     float f7 = y0 + radius;
/*  707 */     GL11.glVertex2f(f6, f7);
/*      */     int j;
/*  709 */     for (j = 0; j <= 18; j++) {
/*  710 */       float f8 = j * 5.0F;
/*  711 */       GL11.glVertex2f((float)(f6 + radius * Math.cos(Math.toRadians(f8))), (float)(f7 - radius * Math.sin(Math.toRadians(f8))));
/*      */     } 
/*  713 */     GL11.glEnd();
/*  714 */     GL11.glBegin(6);
/*  715 */     f6 = x0 + radius;
/*  716 */     f7 = y0 + radius;
/*  717 */     GL11.glVertex2f(f6, f7);
/*  718 */     for (j = 0; j <= 18; j++) {
/*  719 */       float f9 = j * 5.0F;
/*  720 */       GL11.glVertex2f((float)(f6 - radius * Math.cos(Math.toRadians(f9))), (float)(f7 - radius * Math.sin(Math.toRadians(f9))));
/*      */     } 
/*  722 */     GL11.glEnd();
/*  723 */     GL11.glBegin(6);
/*  724 */     f6 = x0 + radius;
/*  725 */     f7 = y1 - radius;
/*  726 */     GL11.glVertex2f(f6, f7);
/*  727 */     for (j = 0; j <= 18; j++) {
/*  728 */       float f10 = j * 5.0F;
/*  729 */       GL11.glVertex2f((float)(f6 - radius * Math.cos(Math.toRadians(f10))), (float)(f7 + radius * Math.sin(Math.toRadians(f10))));
/*      */     } 
/*  731 */     GL11.glEnd();
/*  732 */     GL11.glBegin(6);
/*  733 */     f6 = x1 - radius;
/*  734 */     f7 = y1 - radius;
/*  735 */     GL11.glVertex2f(f6, f7);
/*  736 */     for (j = 0; j <= 18; j++) {
/*  737 */       float f11 = j * 5.0F;
/*  738 */       GL11.glVertex2f((float)(f6 + radius * Math.cos(Math.toRadians(f11))), (float)(f7 + radius * Math.sin(Math.toRadians(f11))));
/*      */     } 
/*  740 */     GL11.glEnd();
/*  741 */     GL11.glEnable(3553);
/*  742 */     GL11.glEnable(2884);
/*  743 */     GL11.glDisable(3042);
/*  744 */     GlStateManager.enableTexture2D();
/*  745 */     GlStateManager.disableBlend();
/*      */   }
/*      */   
/*      */   public static void drawGradientSidewaysVForFloat(float left, float top, float right, float bottom, int col1, int col2) {
/*  749 */     float f = (col1 >> 24 & 0xFF) / 255.0F;
/*  750 */     float f1 = (col1 >> 16 & 0xFF) / 255.0F;
/*  751 */     float f2 = (col1 >> 8 & 0xFF) / 255.0F;
/*  752 */     float f3 = (col1 & 0xFF) / 255.0F;
/*  753 */     float f4 = (col2 >> 24 & 0xFF) / 255.0F;
/*  754 */     float f5 = (col2 >> 16 & 0xFF) / 255.0F;
/*  755 */     float f6 = (col2 >> 8 & 0xFF) / 255.0F;
/*  756 */     float f7 = (col2 & 0xFF) / 255.0F;
/*  757 */     GL11.glEnable(3042);
/*  758 */     GL11.glDisable(3553);
/*  759 */     GL11.glBlendFunc(770, 771);
/*  760 */     GL11.glEnable(2848);
/*  761 */     GL11.glShadeModel(7425);
/*  762 */     GL11.glPushMatrix();
/*  763 */     GL11.glBegin(7);
/*  764 */     GL11.glColor4f(f1, f2, f3, f);
/*  765 */     GL11.glVertex2d(left, bottom);
/*  766 */     GL11.glVertex2d(right, bottom);
/*  767 */     GL11.glColor4f(f5, f6, f7, f4);
/*  768 */     GL11.glVertex2d(right, top);
/*  769 */     GL11.glVertex2d(left, top);
/*  770 */     GL11.glEnd();
/*  771 */     GL11.glPopMatrix();
/*  772 */     GL11.glEnable(3553);
/*  773 */     GL11.glDisable(3042);
/*  774 */     GL11.glDisable(2848);
/*  775 */     GL11.glShadeModel(7424);
/*  776 */     drawRect(0.0F, 0.0F, 0.0F, 0.0F, 0);
/*      */   }
/*      */   
/*      */   public static void drawGradientSidewaysV(double left, double top, double right, double bottom, int col1, int col2) {
/*  780 */     float f = (col1 >> 24 & 0xFF) / 255.0F;
/*  781 */     float f1 = (col1 >> 16 & 0xFF) / 255.0F;
/*  782 */     float f2 = (col1 >> 8 & 0xFF) / 255.0F;
/*  783 */     float f3 = (col1 & 0xFF) / 255.0F;
/*  784 */     float f4 = (col2 >> 24 & 0xFF) / 255.0F;
/*  785 */     float f5 = (col2 >> 16 & 0xFF) / 255.0F;
/*  786 */     float f6 = (col2 >> 8 & 0xFF) / 255.0F;
/*  787 */     float f7 = (col2 & 0xFF) / 255.0F;
/*  788 */     GL11.glEnable(3042);
/*  789 */     GL11.glDisable(3553);
/*  790 */     GL11.glBlendFunc(770, 771);
/*  791 */     GL11.glEnable(2848);
/*  792 */     GL11.glShadeModel(7425);
/*  793 */     GL11.glPushMatrix();
/*  794 */     GL11.glBegin(7);
/*  795 */     GL11.glColor4f(f1, f2, f3, f);
/*  796 */     GL11.glVertex2d(left, bottom);
/*  797 */     GL11.glVertex2d(right, bottom);
/*  798 */     GL11.glColor4f(f5, f6, f7, f4);
/*  799 */     GL11.glVertex2d(right, top);
/*  800 */     GL11.glVertex2d(left, top);
/*  801 */     GL11.glEnd();
/*  802 */     GL11.glPopMatrix();
/*  803 */     GL11.glEnable(3553);
/*  804 */     GL11.glDisable(3042);
/*  805 */     GL11.glDisable(2848);
/*  806 */     GL11.glShadeModel(7424);
/*  807 */     drawRect(0.0F, 0.0F, 0.0F, 0.0F, 0);
/*      */   }
/*      */   
/*      */   public static void startGlScissor(int x, int y, int width, int height) {
/*  811 */     int scaleFactor = (new ScaledResolution(mc)).getScaleFactor();
/*  812 */     GL11.glPushMatrix();
/*  813 */     GL11.glEnable(3089);
/*  814 */     GL11.glScissor(x * scaleFactor, mc.displayHeight - (y + height) * scaleFactor, width * scaleFactor, (height + 14) * scaleFactor);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void drawRoundRect(double xPosition, double yPosition, double endX, double endY, int radius, int color) {
/*  820 */     double width = endX - xPosition;
/*  821 */     double height = endY - yPosition;
/*  822 */     Gui.drawRect(xPosition + radius, yPosition + radius, xPosition + width - radius, yPosition + height - radius, color);
/*      */     
/*  824 */     Gui.drawRect(xPosition, yPosition + radius, xPosition + radius, yPosition + height - radius, color);
/*  825 */     Gui.drawRect(xPosition + width - radius, yPosition + radius, xPosition + width, yPosition + height - radius, color);
/*      */     
/*  827 */     Gui.drawRect(xPosition + radius, yPosition, xPosition + width - radius, yPosition + radius, color);
/*  828 */     Gui.drawRect(xPosition + radius, yPosition + height - radius, xPosition + width - radius, yPosition + height, color);
/*      */     
/*  830 */     Gui.drawFilledCircle(xPosition + radius, yPosition + radius, radius, color, 1);
/*  831 */     Gui.drawFilledCircle(xPosition + radius, yPosition + height - radius, radius, color, 2);
/*  832 */     Gui.drawFilledCircle(xPosition + width - radius, yPosition + radius, radius, color, 3);
/*  833 */     Gui.drawFilledCircle(xPosition + width - radius, yPosition + height - radius, radius, color, 4);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void stopGlScissor() {
/*  838 */     GL11.glDisable(3089);
/*  839 */     GL11.glPopMatrix();
/*      */   }
/*      */   
/*      */   public static void drawGradientRect(double left, double top, double right, double bottom, int startColor, int endColor) {
/*  843 */     float f = (startColor >> 24 & 0xFF) / 255.0F;
/*  844 */     float f2 = (startColor >> 16 & 0xFF) / 255.0F;
/*  845 */     float f3 = (startColor >> 8 & 0xFF) / 255.0F;
/*  846 */     float f4 = (startColor & 0xFF) / 255.0F;
/*  847 */     float f5 = (endColor >> 24 & 0xFF) / 255.0F;
/*  848 */     float f6 = (endColor >> 16 & 0xFF) / 255.0F;
/*  849 */     float f7 = (endColor >> 8 & 0xFF) / 255.0F;
/*  850 */     float f8 = (endColor & 0xFF) / 255.0F;
/*  851 */     GlStateManager.disableTexture2D();
/*  852 */     GlStateManager.enableBlend();
/*  853 */     GlStateManager.disableAlpha();
/*  854 */     GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/*  855 */     GlStateManager.shadeModel(7425);
/*  856 */     Tessellator tessellator = Tessellator.getInstance();
/*  857 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/*  858 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
/*  859 */     worldrenderer.pos(right, top, 0.0D).color(f2, f3, f4, f).endVertex();
/*  860 */     worldrenderer.pos(left, top, 0.0D).color(f2, f3, f4, f).endVertex();
/*  861 */     worldrenderer.pos(left, bottom, 0.0D).color(f6, f7, f8, f5).endVertex();
/*  862 */     worldrenderer.pos(right, bottom, 0.0D).color(f6, f7, f8, f5).endVertex();
/*  863 */     tessellator.draw();
/*  864 */     GlStateManager.shadeModel(7424);
/*  865 */     GlStateManager.disableBlend();
/*  866 */     GlStateManager.enableAlpha();
/*  867 */     GlStateManager.enableTexture2D();
/*      */   }
/*      */   
/*      */   public static void drawBorderedRect(double left, double top, double right, double bottom, double borderWidth, int insideColor, int borderColor, boolean borderIncludedInBounds) {
/*  871 */     drawRect(left - (borderIncludedInBounds ? 0.0D : borderWidth), top - (borderIncludedInBounds ? 0.0D : borderWidth), right + (borderIncludedInBounds ? 0.0D : borderWidth), bottom + (borderIncludedInBounds ? 0.0D : borderWidth), borderColor);
/*  872 */     drawRect(left + (borderIncludedInBounds ? borderWidth : 0.0D), top + (borderIncludedInBounds ? borderWidth : 0.0D), right - (borderIncludedInBounds ? borderWidth : 0.0D), bottom - (borderIncludedInBounds ? borderWidth : 0.0D), insideColor);
/*      */   }
/*      */   
/*      */   public static void prepareScissorBox(float x, float y, float x2, float y2) {
/*  876 */     int factor = (new ScaledResolution(mc)).getScaleFactor();
/*  877 */     GL11.glScissor((int)(x * factor), 
/*  878 */         (int)(((new ScaledResolution(mc)).getScaledHeight() - y2) * factor), (int)((x2 - x) * factor), (int)((y2 - y) * factor));
/*      */   }
/*      */ 
/*      */   
/*      */   public static Vec3 interpolateRender(EntityPlayer player) {
/*  883 */     float part = mc.timer.renderPartialTicks;
/*  884 */     double interpX = player.lastTickPosX + (player.posX - player.lastTickPosX) * part;
/*  885 */     double interpY = player.lastTickPosY + (player.posY - player.lastTickPosY) * part;
/*  886 */     double interpZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * part;
/*  887 */     return new Vec3(interpX, interpY, interpZ);
/*      */   }
/*      */   
/*      */   public static void setupRender(boolean start) {
/*  891 */     if (start) {
/*  892 */       GlStateManager.enableBlend();
/*  893 */       GL11.glEnable(2848);
/*  894 */       GlStateManager.disableDepth();
/*  895 */       GlStateManager.disableTexture2D();
/*  896 */       GlStateManager.blendFunc(770, 771);
/*  897 */       GL11.glHint(3154, 4354);
/*      */     } else {
/*  899 */       GlStateManager.disableBlend();
/*  900 */       GlStateManager.enableTexture2D();
/*  901 */       GL11.glDisable(2848);
/*  902 */       GlStateManager.enableDepth();
/*      */     } 
/*  904 */     GlStateManager.depthMask(!start);
/*      */   }
/*      */   
/*      */   public static void glColor(int hex) {
/*  908 */     float alpha = (hex >> 24 & 0xFF) / 255.0F;
/*  909 */     float red = (hex >> 16 & 0xFF) / 255.0F;
/*  910 */     float green = (hex >> 8 & 0xFF) / 255.0F;
/*  911 */     float blue = (hex & 0xFF) / 255.0F;
/*  912 */     GL11.glColor4f(red, green, blue, alpha);
/*      */   }
/*      */   
/*      */   public static void glColor(Color color) {
/*  916 */     GL11.glColor4f(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, color.getAlpha() / 255.0F);
/*      */   }
/*      */   
/*      */   public static void drawRect(float x, float y, float x1, float y1) {
/*  920 */     GL11.glBegin(7);
/*  921 */     GL11.glVertex2f(x, y1);
/*  922 */     GL11.glVertex2f(x1, y1);
/*  923 */     GL11.glVertex2f(x1, y);
/*  924 */     GL11.glVertex2f(x, y);
/*  925 */     GL11.glEnd();
/*      */   }
/*      */   
/*      */   public static void drawGradient(double x, double y, double x2, double y2, int col1, int col2) {
/*  929 */     float f = (col1 >> 24 & 0xFF) / 255.0F;
/*  930 */     float f1 = (col1 >> 16 & 0xFF) / 255.0F;
/*  931 */     float f2 = (col1 >> 8 & 0xFF) / 255.0F;
/*  932 */     float f3 = (col1 & 0xFF) / 255.0F;
/*  933 */     float f4 = (col2 >> 24 & 0xFF) / 255.0F;
/*  934 */     float f5 = (col2 >> 16 & 0xFF) / 255.0F;
/*  935 */     float f6 = (col2 >> 8 & 0xFF) / 255.0F;
/*  936 */     float f7 = (col2 & 0xFF) / 255.0F;
/*  937 */     GL11.glEnable(3042);
/*  938 */     GL11.glDisable(3553);
/*  939 */     GL11.glBlendFunc(770, 771);
/*  940 */     GL11.glEnable(2848);
/*  941 */     GL11.glShadeModel(7425);
/*  942 */     GL11.glPushMatrix();
/*  943 */     GL11.glBegin(7);
/*  944 */     GL11.glColor4f(f1, f2, f3, f);
/*  945 */     GL11.glVertex2d(x2, y);
/*  946 */     GL11.glVertex2d(x, y);
/*  947 */     GL11.glColor4f(f5, f6, f7, f4);
/*  948 */     GL11.glVertex2d(x, y2);
/*  949 */     GL11.glVertex2d(x2, y2);
/*  950 */     GL11.glEnd();
/*  951 */     GL11.glPopMatrix();
/*  952 */     GL11.glEnable(3553);
/*  953 */     GL11.glDisable(3042);
/*  954 */     GL11.glDisable(2848);
/*  955 */     GL11.glShadeModel(7424);
/*  956 */     GL11.glColor4d(1.0D, 1.0D, 1.0D, 1.0D);
/*      */   }
/*      */   
/*      */   public static void drawEntityOnScreen(int posX, int posY, int scale, EntityLivingBase ent) {
/*  960 */     GlStateManager.enableColorMaterial();
/*  961 */     GlStateManager.pushMatrix();
/*  962 */     GlStateManager.translate(posX, posY, 50.0F);
/*  963 */     GlStateManager.scale(-scale, scale, scale);
/*  964 */     GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
/*  965 */     float f = ent.renderYawOffset;
/*  966 */     float f1 = ent.rotationYaw;
/*  967 */     float f2 = ent.rotationPitch;
/*  968 */     float f3 = ent.prevRotationYawHead;
/*  969 */     float f4 = ent.rotationYawHead;
/*  970 */     GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
/*  971 */     RenderHelper.enableStandardItemLighting();
/*  972 */     GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
/*  973 */     GlStateManager.rotate(0.0F, 1.0F, 0.0F, 0.0F);
/*  974 */     ent.renderYawOffset = (10 * ent.ticksExisted % 360);
/*  975 */     ent.rotationYaw = (10 * ent.ticksExisted % 360);
/*  976 */     ent.rotationPitch = 0.0F;
/*  977 */     ent.rotationYawHead = ent.rotationYaw;
/*  978 */     ent.prevRotationYawHead = ent.rotationYaw;
/*  979 */     GlStateManager.translate(0.0F, 0.0F, 0.0F);
/*  980 */     mc.getRenderManager().setPlayerViewY(180.0F);
/*  981 */     mc.getRenderManager().setRenderShadow(false);
/*  982 */     mc.getRenderManager().renderEntityWithPosYaw((Entity)ent, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
/*  983 */     mc.getRenderManager().setRenderShadow(true);
/*  984 */     ent.renderYawOffset = f;
/*  985 */     ent.rotationYaw = f1;
/*  986 */     ent.rotationPitch = f2;
/*  987 */     ent.prevRotationYawHead = f3;
/*  988 */     ent.rotationYawHead = f4;
/*  989 */     GlStateManager.popMatrix();
/*  990 */     RenderHelper.disableStandardItemLighting();
/*  991 */     GlStateManager.disableRescaleNormal();
/*  992 */     GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
/*  993 */     GlStateManager.disableTexture2D();
/*  994 */     GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
/*      */   }
/*      */   
/*      */   public static void renderItemStack(ItemStack stack, int x, int y) {
/*  998 */     GlStateManager.pushMatrix();
/*  999 */     GlStateManager.depthMask(true);
/* 1000 */     GlStateManager.clear(256);
/* 1001 */     RenderHelper.enableStandardItemLighting();
/* 1002 */     (mc.getRenderItem()).zLevel = -150.0F;
/* 1003 */     GlStateManager.disableDepth();
/* 1004 */     GlStateManager.disableTexture2D();
/* 1005 */     GlStateManager.enableBlend();
/* 1006 */     GlStateManager.enableAlpha();
/* 1007 */     GlStateManager.enableTexture2D();
/* 1008 */     GlStateManager.enableLighting();
/* 1009 */     GlStateManager.enableDepth();
/* 1010 */     mc.getRenderItem().renderItemAndEffectIntoGUI(stack, x, y);
/* 1011 */     RenderItem renderItem = mc.getRenderItem();
/* 1012 */     renderItem.renderItemOverlays(mc.fontRendererObj, stack, x, y);
/* 1013 */     (mc.getRenderItem()).zLevel = 0.0F;
/* 1014 */     RenderHelper.disableStandardItemLighting();
/* 1015 */     GlStateManager.disableCull();
/* 1016 */     GlStateManager.enableAlpha();
/* 1017 */     GlStateManager.disableBlend();
/* 1018 */     GlStateManager.disableLighting();
/* 1019 */     GlStateManager.disableDepth();
/* 1020 */     GlStateManager.enableDepth();
/* 1021 */     GlStateManager.popMatrix();
/*      */   }
/*      */   
/*      */   public static void drawScaledCustomSizeModalRect(double d, double e, float u, float v, int uWidth, int vHeight, float size, float size2, float tileWidth, float tileHeight) {
/* 1025 */     float f = 1.0F / tileWidth;
/* 1026 */     float f1 = 1.0F / tileHeight;
/* 1027 */     Tessellator tessellator = Tessellator.getInstance();
/* 1028 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 1029 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 1030 */     worldrenderer.pos(d, e + size2, 0.0D).tex((u * f), ((v + vHeight) * f1)).endVertex();
/* 1031 */     worldrenderer.pos(d + size, e + size2, 0.0D).tex(((u + uWidth) * f), ((v + vHeight) * f1)).endVertex();
/* 1032 */     worldrenderer.pos(d + size, e, 0.0D).tex(((u + uWidth) * f), (v * f1)).endVertex();
/* 1033 */     worldrenderer.pos(d, e, 0.0D).tex((u * f), (v * f1)).endVertex();
/* 1034 */     tessellator.draw();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void newarc(float x, float y, float start, float end, float w, float h, int color, float lineWidth) {
/* 1039 */     if (start > end) {
/* 1040 */       float temp = end;
/* 1041 */       end = start;
/* 1042 */       start = temp;
/*      */     } 
/* 1044 */     float alpha = (color >> 24 & 0xFF) / 255.0F;
/* 1045 */     float red = (color >> 16 & 0xFF) / 255.0F;
/* 1046 */     float green = (color >> 8 & 0xFF) / 255.0F;
/* 1047 */     float blue = (color & 0xFF) / 255.0F;
/* 1048 */     GlStateManager.enableBlend();
/* 1049 */     GlStateManager.disableTexture2D();
/* 1050 */     GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
/* 1051 */     GlStateManager.color(red, green, blue, alpha);
/* 1052 */     GL11.glEnable(2881);
/* 1053 */     GL11.glEnable(2848);
/* 1054 */     GL11.glLineWidth(lineWidth);
/* 1055 */     GL11.glBegin(3); float i;
/* 1056 */     for (i = end; i >= start; i -= 4.0F) {
/* 1057 */       GL11.glVertex2d(x + Math.cos(i * Math.PI / 180.0D) * w * 1.001D, y + Math.sin(i * Math.PI / 180.0D) * h * 1.001D);
/*      */     }
/* 1059 */     GL11.glEnd();
/* 1060 */     GL11.glDisable(2848);
/* 1061 */     GL11.glDisable(2881);
/* 1062 */     GlStateManager.enableTexture2D();
/* 1063 */     GlStateManager.disableBlend();
/*      */   }
/*      */   
/*      */   public static void NewArcOfIO(float x, float y, float start, float end, float radius, int color, float lineWidth) {
/* 1067 */     newarc(x, y, start, end, radius, radius, color, lineWidth);
/*      */   }
/*      */   
/*      */   public static void drawOutFullCircle(float x, float y, float radius, int fill, float lineWidth, float start, float end) {
/* 1071 */     NewArcOfIO(x, y, start, end, radius, fill, lineWidth);
/*      */   }
/*      */   
/*      */   public static void drawOutFullCircle(float x, float y, float radius, int fill, float lineWidth) {
/* 1075 */     NewArcOfIO(x, y, 0.0F, 360.0F, radius, fill, lineWidth);
/*      */   }
/*      */   
/*      */   public static void drawScaledRect(int x, int y, float u, float v, int uWidth, int vHeight, int width, int height, float tileWidth, float tileHeight) {
/* 1079 */     Gui.drawScaledCustomSizeModalRect(x, y, u, v, uWidth, vHeight, width, height, tileWidth, tileHeight);
/*      */   }
/*      */   
/*      */   public static void drawIcon(float x, float y, int sizex, int sizey, ResourceLocation resourceLocation) {
/* 1083 */     GL11.glPushMatrix();
/* 1084 */     mc.getTextureManager().bindTexture(resourceLocation);
/* 1085 */     GL11.glEnable(3042);
/* 1086 */     GL11.glBlendFunc(770, 771);
/* 1087 */     GL11.glEnable(2848);
/* 1088 */     GlStateManager.enableRescaleNormal();
/* 1089 */     GlStateManager.enableAlpha();
/* 1090 */     GlStateManager.alphaFunc(516, 0.1F);
/* 1091 */     GlStateManager.enableBlend();
/* 1092 */     GlStateManager.blendFunc(770, 771);
/* 1093 */     GL11.glTranslatef(x, y, 10.0F);
/* 1094 */     drawScaledRect(0, 0, 0.0F, 0.0F, sizex, sizey, sizex, sizey, sizex, sizey);
/* 1095 */     GlStateManager.disableAlpha();
/* 1096 */     GlStateManager.disableRescaleNormal();
/* 1097 */     GlStateManager.disableLighting();
/* 1098 */     GlStateManager.disableRescaleNormal();
/* 1099 */     GL11.glDisable(2848);
/* 1100 */     GlStateManager.disableBlend();
/* 1101 */     GL11.glPopMatrix();
/*      */   }
/*      */   
/*      */   public static void drawRoundedRect(float x, float y, float x2, float y2, float round, int color) {
/* 1105 */     x += (float)((round / 2.0F) + 0.5D);
/* 1106 */     y += (float)((round / 2.0F) + 0.5D);
/* 1107 */     x2 -= (float)((round / 2.0F) + 0.5D);
/* 1108 */     y2 -= (float)((round / 2.0F) + 0.5D);
/* 1109 */     drawRect((int)x, (int)y, (int)x2, (int)y2, color);
/* 1110 */     circle(x2 - round / 2.0F, y + round / 2.0F, round, color);
/* 1111 */     circle(x + round / 2.0F, y2 - round / 2.0F, round, color);
/* 1112 */     circle(x + round / 2.0F, y + round / 2.0F, round, color);
/* 1113 */     circle(x2 - round / 2.0F, y2 - round / 2.0F, round, color);
/* 1114 */     drawRect((int)(x - round / 2.0F - 0.5F), (int)(y + round / 2.0F), (int)x2, (int)(y2 - round / 2.0F), color);
/* 1115 */     drawRect((int)x, (int)(y + round / 2.0F), (int)(x2 + round / 2.0F + 0.5F), (int)(y2 - round / 2.0F), color);
/* 1116 */     drawRect((int)(x + round / 2.0F), (int)(y - round / 2.0F - 0.5F), (int)(x2 - round / 2.0F), (int)(y2 - round / 2.0F), color);
/* 1117 */     drawRect((int)(x + round / 2.0F), (int)y, (int)(x2 - round / 2.0F), (int)(y2 + round / 2.0F + 0.5F), color);
/*      */   }
/*      */   
/*      */   public static void circle(float x, float y, float radius, int fill) {
/* 1121 */     arc(x, y, 0.0F, 360.0F, radius, fill);
/*      */   }
/*      */   
/*      */   public static void drawCustomImage(float x, float y, float width, float height, float texWidth, float texHeight, ResourceLocation image) {
/* 1125 */     GL11.glPushMatrix();
/* 1126 */     GlStateManager.enableBlend();
/* 1127 */     GlStateManager.disableAlpha();
/*      */     
/* 1129 */     mc.getTextureManager().bindTexture(image);
/* 1130 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 1131 */     Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0F, 0.0F, width, height, texWidth, texHeight);
/* 1132 */     GlStateManager.disableBlend();
/* 1133 */     GlStateManager.enableAlpha();
/* 1134 */     GL11.glPopMatrix();
/*      */   }
/*      */   
/*      */   public static void drawCustomImage(float x, float y, float width, float height, float texWidth, float texHeight, float alpha, ResourceLocation image) {
/* 1138 */     GL11.glPushMatrix();
/* 1139 */     GlStateManager.enableBlend();
/* 1140 */     GlStateManager.disableAlpha();
/* 1141 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 1142 */     mc.getTextureManager().bindTexture(image);
/* 1143 */     GlStateManager.color(1.0F, 1.0F, 1.0F, alpha);
/* 1144 */     Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0F, 0.0F, width, height, texWidth, texHeight);
/* 1145 */     GlStateManager.disableBlend();
/* 1146 */     GlStateManager.enableAlpha();
/* 1147 */     GL11.glPopMatrix();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void drawCustomImage(int x, int y, int width, int height, ResourceLocation image) {
/* 1152 */     GL11.glDisable(2929);
/* 1153 */     GL11.glEnable(3042);
/* 1154 */     GL11.glDepthMask(false);
/* 1155 */     OpenGlHelper.glBlendFunc(770, 771, 1, 0);
/* 1156 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 1157 */     mc.getTextureManager().bindTexture(image);
/* 1158 */     Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0F, 0.0F, width, height, width, height);
/* 1159 */     GL11.glDepthMask(true);
/* 1160 */     GL11.glDisable(3042);
/* 1161 */     GL11.glEnable(2929);
/*      */   }
/*      */   
/*      */   public static void drawArc(float n, float n2, double n3, int n4, int n5, double n6, int n7) {
/* 1165 */     n3 *= 2.0D;
/* 1166 */     n *= 2.0F;
/* 1167 */     n2 *= 2.0F;
/* 1168 */     float n8 = (n4 >> 24 & 0xFF) / 255.0F;
/* 1169 */     float n9 = (n4 >> 16 & 0xFF) / 255.0F;
/* 1170 */     float n10 = (n4 >> 8 & 0xFF) / 255.0F;
/* 1171 */     float n11 = (n4 & 0xFF) / 255.0F;
/* 1172 */     GL11.glDisable(2929);
/* 1173 */     GL11.glEnable(3042);
/* 1174 */     GL11.glDisable(3553);
/* 1175 */     GL11.glBlendFunc(770, 771);
/* 1176 */     GL11.glDepthMask(true);
/* 1177 */     GL11.glEnable(2848);
/* 1178 */     GL11.glHint(3154, 4354);
/* 1179 */     GL11.glHint(3155, 4354);
/* 1180 */     GL11.glScalef(0.5F, 0.5F, 0.5F);
/* 1181 */     GL11.glLineWidth(n7);
/* 1182 */     GL11.glEnable(2848);
/* 1183 */     GL11.glColor4f(n9, n10, n11, n8);
/* 1184 */     GL11.glBegin(3);
/* 1185 */     int n12 = n5;
/* 1186 */     while (n12 <= n6) {
/* 1187 */       GL11.glVertex2d(n + Math.sin(n12 * Math.PI / 180.0D) * n3, n2 + Math.cos(n12 * Math.PI / 180.0D) * n3);
/* 1188 */       n12++;
/*      */     } 
/* 1190 */     GL11.glEnd();
/* 1191 */     GL11.glDisable(2848);
/* 1192 */     GL11.glScalef(2.0F, 2.0F, 2.0F);
/* 1193 */     GL11.glEnable(3553);
/* 1194 */     GL11.glDisable(3042);
/* 1195 */     GL11.glEnable(2929);
/* 1196 */     GL11.glDisable(2848);
/* 1197 */     GL11.glHint(3154, 4352);
/* 1198 */     GL11.glHint(3155, 4352);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void MdrawRect(double d, double e, double g, double h, int color) {
/* 1203 */     if (d < g) {
/* 1204 */       int i = (int)d;
/* 1205 */       d = g;
/* 1206 */       g = i;
/*      */     } 
/*      */     
/* 1209 */     if (e < h) {
/* 1210 */       int j = (int)e;
/* 1211 */       e = h;
/* 1212 */       h = j;
/*      */     } 
/*      */     
/* 1215 */     float f3 = (color >> 24 & 0xFF) / 255.0F;
/* 1216 */     float f = (color >> 16 & 0xFF) / 255.0F;
/* 1217 */     float f1 = (color >> 8 & 0xFF) / 255.0F;
/* 1218 */     float f2 = (color & 0xFF) / 255.0F;
/* 1219 */     Tessellator tessellator = Tessellator.getInstance();
/* 1220 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 1221 */     GlStateManager.enableBlend();
/* 1222 */     GlStateManager.disableTexture2D();
/* 1223 */     GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 1224 */     GlStateManager.color(f, f1, f2, f3);
/* 1225 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION);
/* 1226 */     worldrenderer.pos(d, h, 0.0D).endVertex();
/* 1227 */     worldrenderer.pos(g, h, 0.0D).endVertex();
/* 1228 */     worldrenderer.pos(g, e, 0.0D).endVertex();
/* 1229 */     worldrenderer.pos(d, e, 0.0D).endVertex();
/* 1230 */     tessellator.draw();
/* 1231 */     GlStateManager.enableTexture2D();
/* 1232 */     GlStateManager.disableBlend();
/*      */   }
/*      */   
/*      */   public static void resetColor() {
/* 1236 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void drawGradientRect2(double left, double top, double right, double bottom, int startColor, int endColor) {
/* 1249 */     float sa = (startColor >> 24 & 0xFF) / 255.0F;
/* 1250 */     float sr = (startColor >> 16 & 0xFF) / 255.0F;
/* 1251 */     float sg = (startColor >> 8 & 0xFF) / 255.0F;
/* 1252 */     float sb = (startColor & 0xFF) / 255.0F;
/* 1253 */     float ea = (endColor >> 24 & 0xFF) / 255.0F;
/* 1254 */     float er = (endColor >> 16 & 0xFF) / 255.0F;
/* 1255 */     float eg = (endColor >> 8 & 0xFF) / 255.0F;
/* 1256 */     float eb = (endColor & 0xFF) / 255.0F;
/* 1257 */     GlStateManager.disableTexture2D();
/* 1258 */     GlStateManager.enableBlend();
/* 1259 */     GlStateManager.disableAlpha();
/* 1260 */     GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 1261 */     GlStateManager.shadeModel(7425);
/* 1262 */     Tessellator tessellator = Tessellator.getInstance();
/* 1263 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 1264 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
/* 1265 */     worldrenderer.pos(left, bottom, 0.0D).color(sr, sg, sb, sa).endVertex();
/* 1266 */     worldrenderer.pos(right, bottom, 0.0D).color(er, eg, eb, ea).endVertex();
/* 1267 */     worldrenderer.pos(right, top, 0.0D).color(er, eg, eb, ea).endVertex();
/* 1268 */     worldrenderer.pos(left, top, 0.0D).color(sr, sg, sb, sa).endVertex();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1275 */     tessellator.draw();
/* 1276 */     GlStateManager.shadeModel(7424);
/* 1277 */     GlStateManager.disableBlend();
/* 1278 */     GlStateManager.enableAlpha();
/* 1279 */     GlStateManager.enableTexture2D();
/*      */   }
/*      */   
/*      */   public static boolean isHovered(float x, float y, float x2, float y2, int mouseX, int mouseY) {
/* 1283 */     return (mouseX >= x && mouseX <= x2 && mouseY >= y && mouseY <= y2);
/*      */   }
/*      */   
/*      */   private static void drawCircle(double xPos, double yPos, double radius) {
/* 1287 */     double theta = 0.017453292519943295D;
/* 1288 */     double tangetial_factor = Math.tan(0.017453292519943295D);
/* 1289 */     double radial_factor = Math.cos(0.017453292519943295D);
/* 1290 */     double x = radius;
/* 1291 */     double y = 0.0D;
/* 1292 */     for (int i = 0; i < 360; i++) {
/* 1293 */       GL11.glVertex2d(x + xPos, y + yPos);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1298 */       double tx = -y;
/* 1299 */       double ty = x;
/*      */ 
/*      */       
/* 1302 */       x += tx * tangetial_factor;
/* 1303 */       y += ty * tangetial_factor;
/*      */ 
/*      */       
/* 1306 */       x *= radial_factor;
/* 1307 */       y *= radial_factor;
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void drawCircle(double xPos, double yPos, double radius, Color color) {
/* 1312 */     NLRenderUtil.startRender();
/* 1313 */     NLRenderUtil.color(color);
/* 1314 */     GL11.glBegin(9);
/*      */     
/* 1316 */     drawCircle(xPos, yPos, radius);
/*      */     
/* 1318 */     GL11.glEnd();
/*      */     
/* 1320 */     GL11.glEnable(2848);
/* 1321 */     GL11.glLineWidth(2.0F);
/* 1322 */     GL11.glBegin(2);
/*      */     
/* 1324 */     drawCircle(xPos, yPos, radius);
/*      */     
/* 1326 */     GL11.glEnd();
/* 1327 */     NLRenderUtil.stopRender();
/*      */   }
/*      */   
/*      */   public static boolean needsNewFramebuffer(Framebuffer framebuffer) {
/* 1331 */     return (framebuffer == null || framebuffer.framebufferWidth != mc.displayWidth || framebuffer.framebufferHeight != mc.displayHeight);
/*      */   }
/*      */   
/*      */   public static Framebuffer createFrameBuffer(Framebuffer framebuffer, boolean depth) {
/* 1335 */     if (needsNewFramebuffer(framebuffer)) {
/* 1336 */       if (framebuffer != null) {
/* 1337 */         framebuffer.deleteFramebuffer();
/*      */       }
/* 1339 */       return new Framebuffer(mc.displayWidth, mc.displayHeight, depth);
/*      */     } 
/* 1341 */     return framebuffer;
/*      */   }
/*      */   
/*      */   public static Framebuffer createFrameBuffer(Framebuffer framebuffer) {
/* 1345 */     if (framebuffer == null || framebuffer.framebufferWidth != mc.displayWidth || framebuffer.framebufferHeight != mc.displayHeight) {
/* 1346 */       if (framebuffer != null) {
/* 1347 */         framebuffer.deleteFramebuffer();
/*      */       }
/* 1349 */       return new Framebuffer(mc.displayWidth, mc.displayHeight, true);
/*      */     } 
/* 1351 */     return framebuffer;
/*      */   }
/*      */   
/*      */   public static void rect(float v, float v1, double v2, float v3, Color color) {
/* 1355 */     drawRect(v, v1, v2, v3, color.getRGB());
/*      */   }
/*      */   
/*      */   public static void scissor(double x, double y, double width, double height) {
/* 1359 */     ScaledResolution sr = new ScaledResolution(mc);
/* 1360 */     double scale = sr.getScaleFactor();
/*      */     
/* 1362 */     y = sr.getScaledHeight() - y;
/*      */     
/* 1364 */     x *= scale;
/* 1365 */     y *= scale;
/* 1366 */     width *= scale;
/* 1367 */     height *= scale;
/*      */     
/* 1369 */     GL11.glScissor((int)x, (int)(y - height), (int)width, (int)height);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void drawOutlinedRect(float x, float y, float width, float height, float lineSize, int lineColor) {
/* 1374 */     drawRect(x, y, width, y + lineSize, lineColor);
/* 1375 */     drawRect(x, height - lineSize, width, height, lineColor);
/* 1376 */     drawRect(x, y + lineSize, x + lineSize, height - lineSize, lineColor);
/* 1377 */     drawRect(width - lineSize, y + lineSize, width, height - lineSize, lineColor);
/*      */   }
/*      */   
/*      */   public static int reAlpha(int color, float alpha) {
/*      */     try {
/* 1382 */       Color c = new Color(color);
/* 1383 */       float r = 0.003921569F * c.getRed();
/* 1384 */       float g = 0.003921569F * c.getGreen();
/* 1385 */       float b = 0.003921569F * c.getBlue();
/* 1386 */       return (new Color(r, g, b, alpha)).getRGB();
/* 1387 */     } catch (Throwable e) {
/* 1388 */       e.printStackTrace();
/*      */       
/* 1390 */       return color;
/*      */     } 
/*      */   }
/*      */   public static int darker(int hexColor, int factor) {
/* 1394 */     float alpha = (hexColor >> 24 & 0xFF);
/* 1395 */     float red = Math.max((hexColor >> 16 & 0xFF) - (hexColor >> 16 & 0xFF) / 100.0F / factor, 0.0F);
/* 1396 */     float green = Math.max((hexColor >> 8 & 0xFF) - (hexColor >> 8 & 0xFF) / 100.0F / factor, 0.0F);
/* 1397 */     float blue = Math.max((hexColor & 0xFF) - (hexColor & 0xFF) / 100.0F / factor, 0.0F);
/* 1398 */     return (int)((((int)alpha << 24) + ((int)red << 16) + ((int)green << 8)) + blue);
/*      */   }
/*      */   
/*      */   public static void drawFace(double x, double y, double width, double height, AbstractClientPlayer target) {
/* 1402 */     ResourceLocation skin = target.getLocationSkin();
/* 1403 */     mc.getTextureManager().bindTexture(skin);
/* 1404 */     GL11.glEnable(3042);
/* 1405 */     GL11.glColor4f(255.0F, 255.0F, 255.0F, 1.0F);
/*      */     
/* 1407 */     float hurtTimePercentage = (target.hurtTime - mc.timer.renderPartialTicks) / target.maxHurtTime;
/*      */     
/* 1409 */     if (hurtTimePercentage > 0.0F) {
/* 1410 */       x += (1.0F * hurtTimePercentage);
/* 1411 */       y += (1.0F * hurtTimePercentage);
/* 1412 */       height -= (2.0F * hurtTimePercentage);
/* 1413 */       width -= (2.0F * hurtTimePercentage);
/*      */     } 
/*      */     
/* 1416 */     drawScaledCustomSizeModalRect(x, y, 8.0F, 8.0F, 8, 8, (float)width, (float)height, 64.0F, 64.0F);
/*      */     
/* 1418 */     if (hurtTimePercentage > 0.0D) {
/* 1419 */       GL11.glTranslated(x, y, 0.0D);
/* 1420 */       GL11.glDisable(3553);
/* 1421 */       boolean restore = DrawUtil.glEnableBlend();
/* 1422 */       GL11.glShadeModel(7425);
/* 1423 */       GL11.glDisable(3008);
/*      */       
/* 1425 */       float lineWidth = 10.0F;
/* 1426 */       GL11.glLineWidth(10.0F);
/*      */       
/* 1428 */       int fadeOutColour = ColourUtil.fadeTo(0, ColourUtil.blendHealthColours((target.getHealth() / target.getMaxHealth())), hurtTimePercentage);
/*      */       
/* 1430 */       GL11.glBegin(7);
/*      */ 
/*      */       
/* 1433 */       DrawUtil.glColour(fadeOutColour);
/* 1434 */       GL11.glVertex2d(0.0D, 0.0D);
/* 1435 */       GL11.glVertex2d(0.0D, height);
/* 1436 */       DrawUtil.glColour(16711680);
/* 1437 */       GL11.glVertex2d(10.0D, height - 10.0D);
/* 1438 */       GL11.glVertex2d(10.0D, 10.0D);
/*      */ 
/*      */       
/* 1441 */       DrawUtil.glColour(16711680);
/* 1442 */       GL11.glVertex2d(width - 10.0D, 10.0D);
/* 1443 */       GL11.glVertex2d(width - 10.0D, height - 10.0D);
/* 1444 */       DrawUtil.glColour(fadeOutColour);
/* 1445 */       GL11.glVertex2d(width, height);
/* 1446 */       GL11.glVertex2d(width, 0.0D);
/*      */ 
/*      */       
/* 1449 */       DrawUtil.glColour(fadeOutColour);
/* 1450 */       GL11.glVertex2d(0.0D, 0.0D);
/* 1451 */       DrawUtil.glColour(16711680);
/* 1452 */       GL11.glVertex2d(10.0D, 10.0D);
/* 1453 */       GL11.glVertex2d(width - 10.0D, 10.0D);
/* 1454 */       DrawUtil.glColour(fadeOutColour);
/* 1455 */       GL11.glVertex2d(width, 0.0D);
/*      */ 
/*      */       
/* 1458 */       DrawUtil.glColour(16711680);
/* 1459 */       GL11.glVertex2d(10.0D, height - 10.0D);
/* 1460 */       DrawUtil.glColour(fadeOutColour);
/* 1461 */       GL11.glVertex2d(0.0D, height);
/* 1462 */       GL11.glVertex2d(width, height);
/* 1463 */       DrawUtil.glColour(16711680);
/* 1464 */       GL11.glVertex2d(width - 10.0D, height - 10.0D);
/*      */       
/* 1466 */       GL11.glEnd();
/*      */       
/* 1468 */       GL11.glEnable(3008);
/* 1469 */       GL11.glShadeModel(7424);
/* 1470 */       DrawUtil.glRestoreBlend(restore);
/* 1471 */       GL11.glEnable(3553);
/* 1472 */       GL11.glTranslated(-x, -y, 0.0D);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void color(int color, float alpha) {
/* 1478 */     float r = (color >> 16 & 0xFF) / 255.0F;
/* 1479 */     float g = (color >> 8 & 0xFF) / 255.0F;
/* 1480 */     float b = (color & 0xFF) / 255.0F;
/* 1481 */     GlStateManager.color(r, g, b, alpha);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setAlphaLimit(float limit) {
/* 1486 */     GlStateManager.enableAlpha();
/* 1487 */     GlStateManager.alphaFunc(516, (float)(limit * 0.01D));
/*      */   }
/*      */   
/*      */   public static void drawGoodCircle(double x, double y, float radius, int color) {
/* 1491 */     color(color);
/* 1492 */     GLUtil.setup2DRendering();
/*      */     
/* 1494 */     GL11.glEnable(2832);
/* 1495 */     GL11.glHint(3153, 4354);
/* 1496 */     GL11.glPointSize(radius * (2 * mc.gameSettings.guiScale));
/*      */     
/* 1498 */     GL11.glBegin(0);
/* 1499 */     GL11.glVertex2d(x, y);
/* 1500 */     GL11.glEnd();
/*      */     
/* 1502 */     GLUtil.end2DRendering();
/*      */   }
/*      */   
/*      */   public static void drawRect2(double x, double y, double width, double height, int color) {
/* 1506 */     resetColor();
/* 1507 */     setAlphaLimit(0.0F);
/* 1508 */     GLUtil.setup2DRendering(true);
/*      */     
/* 1510 */     Tessellator tessellator = Tessellator.getInstance();
/* 1511 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/*      */     
/* 1513 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
/* 1514 */     worldrenderer.pos(x, y, 0.0D).color(color).endVertex();
/* 1515 */     worldrenderer.pos(x, y + height, 0.0D).color(color).endVertex();
/* 1516 */     worldrenderer.pos(x + width, y + height, 0.0D).color(color).endVertex();
/* 1517 */     worldrenderer.pos(x + width, y, 0.0D).color(color).endVertex();
/* 1518 */     tessellator.draw();
/*      */     
/* 1520 */     GLUtil.end2DRendering();
/*      */   }
/*      */   
/*      */   public static void renderRoundedRect(float x, float y, float width, float height, float radius, int color) {
/* 1524 */     drawGoodCircle((x + radius), (y + radius), radius, color);
/* 1525 */     drawGoodCircle((x + width - radius), (y + radius), radius, color);
/* 1526 */     drawGoodCircle((x + radius), (y + height - radius), radius, color);
/* 1527 */     drawGoodCircle((x + width - radius), (y + height - radius), radius, color);
/*      */     
/* 1529 */     drawRect2((x + radius), y, (width - radius * 2.0F), height, color);
/* 1530 */     drawRect2(x, (y + radius), width, (height - radius * 2.0F), color);
/*      */   }
/*      */   
/*      */   public static boolean isHoveringBound(float mouseX, float mouseY, float xLeft, float yUp, float width, float height) {
/* 1534 */     return (mouseX > xLeft && mouseX < xLeft + width && mouseY > yUp && mouseY < yUp + height);
/*      */   }
/*      */   
/*      */   public static void bindTexture(int texture) {
/* 1538 */     GL11.glBindTexture(3553, texture);
/*      */   }
/*      */   public static void drawGradientRectBordered(double left, double top, double right, double bottom, double width, int startColor, int endColor, int borderStartColor, int borderEndColor) {
/* 1541 */     drawGradientRect(left + width, top + width, right - width, bottom - width, startColor, endColor);
/* 1542 */     drawGradientRect(left + width, top, right - width, top + width, borderStartColor, borderEndColor);
/* 1543 */     drawGradientRect(left, top, left + width, bottom, borderStartColor, borderEndColor);
/* 1544 */     drawGradientRect(right - width, top, right, bottom, borderStartColor, borderEndColor);
/* 1545 */     drawGradientRect(left + width, bottom - width, right - width, bottom, borderStartColor, borderEndColor);
/*      */   }
/*      */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\gui\clickgui\RenderUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */