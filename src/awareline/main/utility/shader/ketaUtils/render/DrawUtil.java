/*      */ package awareline.main.utility.shader.ketaUtils.render;
/*      */ 
/*      */ import awareline.main.utility.math.MathUtil;
/*      */ import java.awt.Color;
/*      */ import java.io.IOException;
/*      */ import java.nio.FloatBuffer;
/*      */ import java.nio.IntBuffer;
/*      */ import javax.imageio.ImageIO;
/*      */ import net.minecraft.client.Minecraft;
/*      */ import net.minecraft.client.gui.ScaledResolution;
/*      */ import net.minecraft.client.renderer.GLAllocation;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.util.AxisAlignedBB;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.minecraft.util.Vec3;
/*      */ import org.lwjgl.opengl.Display;
/*      */ import org.lwjgl.opengl.GL11;
/*      */ import org.lwjgl.opengl.GL14;
/*      */ import org.lwjgl.opengl.GL20;
/*      */ import org.lwjgl.util.glu.GLU;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class DrawUtil
/*      */ {
/*   27 */   private static final FloatBuffer WND_POS_BUFFER = GLAllocation.createDirectFloatBuffer(4);
/*   28 */   private static final IntBuffer VIEWPORT_BUFFER = GLAllocation.createDirectIntBuffer(16);
/*   29 */   private static final FloatBuffer MODEL_MATRIX_BUFFER = GLAllocation.createDirectFloatBuffer(16);
/*   30 */   private static final FloatBuffer PROJECTION_MATRIX_BUFFER = GLAllocation.createDirectFloatBuffer(16);
/*   31 */   private static final IntBuffer SCISSOR_BUFFER = GLAllocation.createDirectIntBuffer(16);
/*      */   private static StaticallySizedImage checkmarkImage;
/*      */   private static StaticallySizedImage warningImage;
/*      */   private static final String CIRCLE_FRAG_SHADER = "#version 120\n\nuniform float innerRadius;\nuniform vec4 colour;\n\nvoid main() {\n   vec2 pixel = gl_TexCoord[0].st;\n   vec2 centre = vec2(0.5, 0.5);\n   float d = length(pixel - centre);\n   float c = smoothstep(d+innerRadius, d+innerRadius+0.01, 0.5-innerRadius);\n   float a = smoothstep(0.0, 1.0, c) * colour.a;\n   gl_FragColor = vec4(colour.rgb, a);\n}\n";
/*      */   public static final String VERTEX_SHADER = "#version 120 \n\nvoid main() {\n    gl_TexCoord[0] = gl_MultiTexCoord0;\n    gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;\n}";
/*      */   
/*      */   public static double animateProgress(double current, double target, double speed) {
/*   38 */     if (current < target) {
/*   39 */       double inc = 1.0D / Minecraft.getDebugFPS() * speed;
/*   40 */       if (target - current < inc) {
/*   41 */         return target;
/*      */       }
/*   43 */       return current + inc;
/*      */     } 
/*   45 */     if (current > target) {
/*   46 */       double inc = 1.0D / Minecraft.getDebugFPS() * speed;
/*   47 */       if (current - target < inc) {
/*   48 */         return target;
/*      */       }
/*   50 */       return current - inc;
/*      */     } 
/*      */ 
/*      */     
/*   54 */     return current;
/*      */   }
/*      */   
/*      */   public static double bezierBlendAnimation(double t) {
/*   58 */     return t * t * (3.0D - 2.0D * t);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void glDrawTriangle(double x, double y, double x1, double y1, double x2, double y2, int colour) {
/*   66 */     GL11.glDisable(3553);
/*      */     
/*   68 */     boolean restore = glEnableBlend();
/*      */     
/*   70 */     GL11.glEnable(2881);
/*   71 */     GL11.glHint(3155, 4354);
/*      */     
/*   73 */     glColour(colour);
/*      */ 
/*      */     
/*   76 */     GL11.glBegin(4);
/*      */     
/*   78 */     GL11.glVertex2d(x, y);
/*   79 */     GL11.glVertex2d(x1, y1);
/*   80 */     GL11.glVertex2d(x2, y2);
/*      */     
/*   82 */     GL11.glEnd();
/*      */ 
/*      */     
/*   85 */     GL11.glEnable(3553);
/*      */     
/*   87 */     glRestoreBlend(restore);
/*      */     
/*   89 */     GL11.glDisable(2881);
/*   90 */     GL11.glHint(3155, 4352);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glDrawFramebuffer(int framebufferTexture, int width, int height) {
/*   95 */     GL11.glBindTexture(3553, framebufferTexture);
/*      */     
/*   97 */     GL11.glDisable(3008);
/*      */     
/*   99 */     boolean restore = glEnableBlend();
/*      */     
/*  101 */     GL11.glBegin(7);
/*      */     
/*  103 */     GL11.glTexCoord2f(0.0F, 1.0F);
/*  104 */     GL11.glVertex2f(0.0F, 0.0F);
/*      */     
/*  106 */     GL11.glTexCoord2f(0.0F, 0.0F);
/*  107 */     GL11.glVertex2f(0.0F, height);
/*      */     
/*  109 */     GL11.glTexCoord2f(1.0F, 0.0F);
/*  110 */     GL11.glVertex2f(width, height);
/*      */     
/*  112 */     GL11.glTexCoord2f(1.0F, 1.0F);
/*  113 */     GL11.glVertex2f(width, 0.0F);
/*      */     
/*  115 */     GL11.glEnd();
/*      */     
/*  117 */     glRestoreBlend(restore);
/*      */     
/*  119 */     GL11.glEnable(3008);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void glDrawPlusSign(double x, double y, double size, double rotation, int colour) {
/*  128 */     GL11.glDisable(3553);
/*      */     
/*  130 */     boolean restore = glEnableBlend();
/*      */     
/*  132 */     GL11.glEnable(2848);
/*  133 */     GL11.glHint(3154, 4354);
/*      */     
/*  135 */     GL11.glLineWidth(1.0F);
/*      */     
/*  137 */     GL11.glPushMatrix();
/*      */     
/*  139 */     GL11.glTranslated(x, y, 0.0D);
/*      */     
/*  141 */     GL11.glRotated(rotation, 0.0D, 1.0D, 1.0D);
/*      */     
/*  143 */     GL11.glDisable(2929);
/*  144 */     GL11.glDepthMask(false);
/*      */     
/*  146 */     glColour(colour);
/*      */ 
/*      */     
/*  149 */     GL11.glBegin(1);
/*      */ 
/*      */     
/*  152 */     GL11.glVertex2d(-(size / 2.0D), 0.0D);
/*  153 */     GL11.glVertex2d(size / 2.0D, 0.0D);
/*      */     
/*  155 */     GL11.glVertex2d(0.0D, -(size / 2.0D));
/*  156 */     GL11.glVertex2d(0.0D, size / 2.0D);
/*      */     
/*  158 */     GL11.glEnd();
/*      */ 
/*      */     
/*  161 */     GL11.glEnable(2929);
/*  162 */     GL11.glDepthMask(true);
/*      */     
/*  164 */     GL11.glPopMatrix();
/*      */     
/*  166 */     GL11.glEnable(3553);
/*      */     
/*  168 */     glRestoreBlend(restore);
/*      */     
/*  170 */     GL11.glDisable(2848);
/*  171 */     GL11.glHint(3154, 4352);
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
/*      */   public static void glDrawFilledEllipse(double x, double y, double radius, int startIndex, int endIndex, int polygons, boolean smooth, int colour) {
/*  183 */     boolean restore = glEnableBlend();
/*      */     
/*  185 */     if (smooth) {
/*      */       
/*  187 */       GL11.glEnable(2881);
/*  188 */       GL11.glHint(3155, 4354);
/*      */     } 
/*      */     
/*  191 */     GL11.glDisable(3553);
/*      */     
/*  193 */     glColour(colour);
/*      */     
/*  195 */     GL11.glDisable(2884);
/*      */ 
/*      */     
/*  198 */     GL11.glBegin(9);
/*      */ 
/*      */     
/*  201 */     GL11.glVertex2d(x, y);
/*      */     double i;
/*  203 */     for (i = startIndex; i <= endIndex; i++) {
/*  204 */       double theta = 6.283185307179586D * i / polygons;
/*      */       
/*  206 */       GL11.glVertex2d(x + radius * Math.cos(theta), y + radius * Math.sin(theta));
/*      */     } 
/*      */ 
/*      */     
/*  210 */     GL11.glEnd();
/*      */ 
/*      */     
/*  213 */     glRestoreBlend(restore);
/*      */     
/*  215 */     if (smooth) {
/*      */       
/*  217 */       GL11.glDisable(2881);
/*  218 */       GL11.glHint(3155, 4352);
/*      */     } 
/*      */     
/*  221 */     GL11.glEnable(2884);
/*      */     
/*  223 */     GL11.glEnable(3553);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void glDrawFilledEllipse(double x, double y, float radius, int colour) {
/*  231 */     boolean restore = glEnableBlend();
/*      */     
/*  233 */     GL11.glEnable(2832);
/*  234 */     GL11.glHint(3153, 4354);
/*      */     
/*  236 */     GL11.glDisable(3553);
/*      */     
/*  238 */     glColour(colour);
/*      */     
/*  240 */     GL11.glPointSize(radius);
/*      */     
/*  242 */     GL11.glBegin(0);
/*      */     
/*  244 */     GL11.glVertex2d(x, y);
/*      */     
/*  246 */     GL11.glEnd();
/*      */ 
/*      */     
/*  249 */     glRestoreBlend(restore);
/*      */     
/*  251 */     GL11.glDisable(2832);
/*  252 */     GL11.glHint(3153, 4352);
/*      */     
/*  254 */     GL11.glEnable(3553);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void glScissorBox(double x, double y, double width, double height, ScaledResolution scaledResolution) {
/*  260 */     if (!GL11.glIsEnabled(3089)) {
/*  261 */       GL11.glEnable(3089);
/*      */     }
/*  263 */     int scaling = scaledResolution.getScaleFactor();
/*      */     
/*  265 */     GL11.glScissor((int)(x * scaling), 
/*  266 */         (int)((scaledResolution.getScaledHeight() - y + height) * scaling), (int)(width * scaling), (int)(height * scaling));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void glRestoreScissor() {
/*  272 */     if (!GL11.glIsEnabled(3089)) {
/*  273 */       GL11.glEnable(3089);
/*      */     }
/*      */     
/*  276 */     GL11.glScissor(SCISSOR_BUFFER.get(0), SCISSOR_BUFFER.get(1), SCISSOR_BUFFER
/*  277 */         .get(2), SCISSOR_BUFFER.get(3));
/*      */   }
/*      */   
/*      */   public static void glEndScissor() {
/*  281 */     GL11.glDisable(3089);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[] worldToScreen(double[] positionVector, AxisAlignedBB boundingBox, double[] projection, double[] projectionBuffer) {
/*  288 */     double position[], bounds[][] = { { boundingBox.minX, boundingBox.minY, boundingBox.minZ }, { boundingBox.minX, boundingBox.maxY, boundingBox.minZ }, { boundingBox.minX, boundingBox.maxY, boundingBox.maxZ }, { boundingBox.minX, boundingBox.minY, boundingBox.maxZ }, { boundingBox.maxX, boundingBox.minY, boundingBox.minZ }, { boundingBox.maxX, boundingBox.maxY, boundingBox.minZ }, { boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ }, { boundingBox.maxX, boundingBox.minY, boundingBox.maxZ } };
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
/*      */ 
/*      */ 
/*      */     
/*  302 */     if (positionVector != null) {
/*  303 */       if (!worldToScreen(positionVector, projectionBuffer, projection[2])) {
/*  304 */         return null;
/*      */       }
/*  306 */       position = new double[] { projection[0], projection[1], -1.0D, -1.0D, projectionBuffer[0], projectionBuffer[1] };
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/*  312 */       position = new double[] { projection[0], projection[1], -1.0D, -1.0D };
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  318 */     for (double[] vector : bounds) {
/*  319 */       if (worldToScreen(vector, projectionBuffer, projection[2])) {
/*  320 */         double projected_x = projectionBuffer[0];
/*  321 */         double projected_y = projectionBuffer[1];
/*      */         
/*  323 */         position[0] = Math.min(position[0], projected_x);
/*  324 */         position[1] = Math.min(position[1], projected_y);
/*  325 */         position[2] = Math.max(position[2], projected_x);
/*  326 */         position[3] = Math.max(position[3], projected_y);
/*      */       } 
/*      */     } 
/*      */     
/*  330 */     return position;
/*      */   }
/*      */   
/*      */   public static boolean worldToScreen(double[] in, double[] out, double scaling) {
/*  334 */     GL11.glGetFloat(2982, MODEL_MATRIX_BUFFER);
/*  335 */     GL11.glGetFloat(2983, PROJECTION_MATRIX_BUFFER);
/*  336 */     GL11.glGetInteger(2978, VIEWPORT_BUFFER);
/*      */     
/*  338 */     if (GLU.gluProject((float)in[0], (float)in[1], (float)in[2], MODEL_MATRIX_BUFFER, PROJECTION_MATRIX_BUFFER, VIEWPORT_BUFFER, WND_POS_BUFFER)) {
/*      */ 
/*      */       
/*  341 */       float zCoordinate = WND_POS_BUFFER.get(2);
/*      */       
/*  343 */       if (zCoordinate < 0.0F || zCoordinate > 1.0F) return false;
/*      */       
/*  345 */       out[0] = WND_POS_BUFFER.get(0) / scaling;
/*      */ 
/*      */       
/*  348 */       out[1] = (Display.getHeight() - WND_POS_BUFFER.get(1)) / scaling;
/*  349 */       return true;
/*      */     } 
/*      */     
/*  352 */     return false;
/*      */   }
/*      */   
/*      */   public static void glColour(int color) {
/*  356 */     GL11.glColor4ub((byte)(color >> 16 & 0xFF), (byte)(color >> 8 & 0xFF), (byte)(color & 0xFF), (byte)(color >> 24 & 0xFF));
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
/*      */   public static void glDrawGradientLine(double x, double y, double x1, double y1, float lineWidth, int colour) {
/*  369 */     boolean restore = glEnableBlend();
/*      */     
/*  371 */     GL11.glDisable(3553);
/*      */     
/*  373 */     GL11.glLineWidth(lineWidth);
/*      */     
/*  375 */     GL11.glEnable(2848);
/*  376 */     GL11.glHint(3154, 4354);
/*      */     
/*  378 */     GL11.glShadeModel(7425);
/*      */     
/*  380 */     int noAlpha = ColourUtil.removeAlphaComponent(colour);
/*      */     
/*  382 */     GL11.glDisable(3008);
/*      */ 
/*      */     
/*  385 */     GL11.glBegin(3);
/*      */ 
/*      */     
/*  388 */     glColour(noAlpha);
/*  389 */     GL11.glVertex2d(x, y);
/*      */     
/*  391 */     double dif = x1 - x;
/*      */     
/*  393 */     glColour(colour);
/*  394 */     GL11.glVertex2d(x + dif * 0.4D, y);
/*      */     
/*  396 */     GL11.glVertex2d(x + dif * 0.6D, y);
/*      */     
/*  398 */     glColour(noAlpha);
/*  399 */     GL11.glVertex2d(x1, y1);
/*      */ 
/*      */     
/*  402 */     GL11.glEnd();
/*      */     
/*  404 */     GL11.glEnable(3008);
/*      */     
/*  406 */     GL11.glShadeModel(7424);
/*      */ 
/*      */     
/*  409 */     glRestoreBlend(restore);
/*      */     
/*  411 */     GL11.glDisable(2848);
/*  412 */     GL11.glHint(3154, 4352);
/*      */     
/*  414 */     GL11.glEnable(3553);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void glDrawLine(double x, double y, double x1, double y1, float lineWidth, boolean smoothed, int colour) {
/*  425 */     boolean restore = glEnableBlend();
/*      */     
/*  427 */     GL11.glDisable(3553);
/*      */     
/*  429 */     GL11.glLineWidth(lineWidth);
/*      */     
/*  431 */     if (smoothed) {
/*      */       
/*  433 */       GL11.glEnable(2848);
/*  434 */       GL11.glHint(3154, 4354);
/*      */     } 
/*      */     
/*  437 */     glColour(colour);
/*      */ 
/*      */     
/*  440 */     GL11.glBegin(1);
/*      */ 
/*      */     
/*  443 */     GL11.glVertex2d(x, y);
/*      */     
/*  445 */     GL11.glVertex2d(x1, y1);
/*      */ 
/*      */     
/*  448 */     GL11.glEnd();
/*      */ 
/*      */     
/*  451 */     glRestoreBlend(restore);
/*  452 */     if (smoothed) {
/*      */       
/*  454 */       GL11.glDisable(2848);
/*  455 */       GL11.glHint(3154, 4352);
/*      */     } 
/*      */     
/*  458 */     GL11.glEnable(3553);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void glDrawPlayerFace(double x, double y, double width, double height, ResourceLocation skinLocation) {
/*  467 */     Minecraft.getMinecraft().getTextureManager().bindTexture(skinLocation);
/*      */     
/*  469 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  470 */     float eightPixelOff = 0.125F;
/*      */     
/*  472 */     GL11.glBegin(7);
/*      */     
/*  474 */     GL11.glTexCoord2f(0.125F, 0.125F);
/*  475 */     GL11.glVertex2d(x, y);
/*      */     
/*  477 */     GL11.glTexCoord2f(0.125F, 0.25F);
/*  478 */     GL11.glVertex2d(x, y + height);
/*      */     
/*  480 */     GL11.glTexCoord2f(0.25F, 0.25F);
/*  481 */     GL11.glVertex2d(x + width, y + height);
/*      */     
/*  483 */     GL11.glTexCoord2f(0.25F, 0.125F);
/*  484 */     GL11.glVertex2d(x + width, y);
/*      */     
/*  486 */     GL11.glEnd();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void glDrawSidewaysGradientRect(double x, double y, double width, double height, int startColour, int endColour) {
/*  496 */     boolean restore = glEnableBlend();
/*      */     
/*  498 */     GL11.glDisable(3553);
/*      */     
/*  500 */     GL11.glShadeModel(7425);
/*      */ 
/*      */     
/*  503 */     GL11.glBegin(7);
/*      */ 
/*      */     
/*  506 */     glColour(startColour);
/*  507 */     GL11.glVertex2d(x, y);
/*  508 */     GL11.glVertex2d(x, y + height);
/*      */     
/*  510 */     glColour(endColour);
/*  511 */     GL11.glVertex2d(x + width, y + height);
/*  512 */     GL11.glVertex2d(x + width, y);
/*      */ 
/*      */     
/*  515 */     GL11.glEnd();
/*      */ 
/*      */     
/*  518 */     GL11.glShadeModel(7424);
/*      */     
/*  520 */     GL11.glEnable(3553);
/*      */     
/*  522 */     glRestoreBlend(restore);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void glDrawFilledRect(double x, double y, double x1, double y1, int startColour, int endColour) {
/*  532 */     boolean restore = glEnableBlend();
/*      */     
/*  534 */     GL11.glDisable(3553);
/*      */     
/*  536 */     GL11.glShadeModel(7425);
/*      */ 
/*      */     
/*  539 */     GL11.glBegin(7);
/*      */ 
/*      */     
/*  542 */     glColour(startColour);
/*  543 */     GL11.glVertex2d(x, y);
/*  544 */     glColour(endColour);
/*  545 */     GL11.glVertex2d(x, y1);
/*      */     
/*  547 */     GL11.glVertex2d(x1, y1);
/*  548 */     glColour(startColour);
/*  549 */     GL11.glVertex2d(x1, y);
/*      */ 
/*      */     
/*  552 */     GL11.glEnd();
/*      */ 
/*      */     
/*  555 */     GL11.glShadeModel(7424);
/*      */ 
/*      */     
/*  558 */     GL11.glEnable(3553);
/*      */     
/*  560 */     glRestoreBlend(restore);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void glDrawOutlinedQuad(double x, double y, double width, double height, float thickness, int colour) {
/*  570 */     boolean restore = glEnableBlend();
/*      */     
/*  572 */     GL11.glDisable(3553);
/*      */     
/*  574 */     glColour(colour);
/*      */     
/*  576 */     GL11.glLineWidth(thickness);
/*      */ 
/*      */     
/*  579 */     GL11.glBegin(2);
/*      */     
/*  581 */     GL11.glVertex2d(x, y);
/*  582 */     GL11.glVertex2d(x, y + height);
/*  583 */     GL11.glVertex2d(x + width, y + height);
/*  584 */     GL11.glVertex2d(x + width, y);
/*      */ 
/*      */     
/*  587 */     GL11.glEnd();
/*      */ 
/*      */     
/*  590 */     GL11.glEnable(3553);
/*      */     
/*  592 */     glRestoreBlend(restore);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void drawHollowRoundedRect(double x, double y, double width, double height, double cornerRadius, boolean smoothed, Color color) {
/*  602 */     GL11.glDisable(3553);
/*  603 */     GL11.glEnable(2848);
/*  604 */     GL11.glEnable(3042);
/*  605 */     GL11.glColor4f(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, color.getAlpha() / 255.0F);
/*  606 */     GL11.glLineWidth(1.0F);
/*  607 */     GL11.glBegin(2);
/*  608 */     double cornerX = x + width - cornerRadius;
/*  609 */     double cornerY = y + height - cornerRadius; int i;
/*  610 */     for (i = 0; i <= 90; i += 30)
/*  611 */       GL11.glVertex2d(cornerX + Math.sin(i * Math.PI / 180.0D) * cornerRadius, cornerY + Math.cos(i * Math.PI / 180.0D) * cornerRadius); 
/*  612 */     GL11.glEnd();
/*  613 */     cornerX = x + width - cornerRadius;
/*  614 */     cornerY = y + cornerRadius;
/*  615 */     GL11.glBegin(2);
/*  616 */     for (i = 90; i <= 180; i += 30)
/*  617 */       GL11.glVertex2d(cornerX + Math.sin(i * Math.PI / 180.0D) * cornerRadius, cornerY + Math.cos(i * Math.PI / 180.0D) * cornerRadius); 
/*  618 */     GL11.glEnd();
/*  619 */     cornerX = x + cornerRadius;
/*  620 */     cornerY = y + cornerRadius;
/*  621 */     GL11.glBegin(2);
/*  622 */     for (i = 180; i <= 270; i += 30)
/*  623 */       GL11.glVertex2d(cornerX + Math.sin(i * Math.PI / 180.0D) * cornerRadius, cornerY + Math.cos(i * Math.PI / 180.0D) * cornerRadius); 
/*  624 */     GL11.glEnd();
/*  625 */     cornerX = x + cornerRadius;
/*  626 */     cornerY = y + height - cornerRadius;
/*  627 */     GL11.glBegin(2);
/*  628 */     for (i = 270; i <= 360; i += 30)
/*  629 */       GL11.glVertex2d(cornerX + Math.sin(i * Math.PI / 180.0D) * cornerRadius, cornerY + Math.cos(i * Math.PI / 180.0D) * cornerRadius); 
/*  630 */     GL11.glEnd();
/*  631 */     GL11.glDisable(3042);
/*  632 */     GL11.glDisable(2848);
/*  633 */     GL11.glEnable(3553);
/*  634 */     glDrawLine(x + cornerRadius, y, x + width - cornerRadius, y, 1.0F, smoothed, color.getRGB());
/*  635 */     glDrawLine(x + cornerRadius, y + height, x + width - cornerRadius, y + height, 1.0F, smoothed, color.getRGB());
/*  636 */     glDrawLine(x, y + cornerRadius, x, y + height - cornerRadius, 1.0F, smoothed, color.getRGB());
/*  637 */     glDrawLine(x + width, y + cornerRadius, x + width, y + height - cornerRadius, 1.0F, smoothed, color.getRGB());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void glDrawOutlinedQuadGradient(double x, double y, double width, double height, float thickness, int colour, int secondaryColour) {
/*  647 */     boolean restore = glEnableBlend();
/*      */     
/*  649 */     GL11.glDisable(3553);
/*      */     
/*  651 */     GL11.glLineWidth(thickness);
/*      */ 
/*      */     
/*  654 */     GL11.glShadeModel(7425);
/*  655 */     GL11.glBegin(2);
/*      */ 
/*      */     
/*  658 */     glColour(colour);
/*  659 */     GL11.glVertex2d(x, y);
/*  660 */     GL11.glVertex2d(x, y + height);
/*      */     
/*  662 */     glColour(secondaryColour);
/*  663 */     GL11.glVertex2d(x + width, y + height);
/*  664 */     GL11.glVertex2d(x + width, y);
/*      */ 
/*      */     
/*  667 */     GL11.glEnd();
/*  668 */     GL11.glShadeModel(7424);
/*      */ 
/*      */     
/*  671 */     GL11.glEnable(3553);
/*      */     
/*  673 */     glRestoreBlend(restore);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static {
/*      */     try {
/*  681 */       checkmarkImage = new StaticallySizedImage(ImageIO.read(ResourceUtil.getResourceStream("icons/notifications/success.png")), true, 2);
/*  682 */       warningImage = new StaticallySizedImage(ImageIO.read(ResourceUtil.getResourceStream("icons/notifications/warning.png")), true, 2);
/*  683 */     } catch (IOException ignored) {
/*  684 */       checkmarkImage = null;
/*  685 */       warningImage = null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void glDrawCheckmarkImage(double x, double y, double width, double height, int colour) {
/*  695 */     checkmarkImage.draw(x, y, width, height, colour);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void glDrawWarningImage(double x, double y, double width, double height, int colour) {
/*  703 */     warningImage.draw(x, y, width, height, colour);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void glDrawFilledQuad(double x, double y, double width, double height, int colour) {
/*  712 */     boolean restore = glEnableBlend();
/*      */     
/*  714 */     GL11.glDisable(3553);
/*      */     
/*  716 */     glColour(colour);
/*      */ 
/*      */     
/*  719 */     GL11.glBegin(7);
/*      */     
/*  721 */     GL11.glVertex2d(x, y);
/*  722 */     GL11.glVertex2d(x, y + height);
/*  723 */     GL11.glVertex2d(x + width, y + height);
/*  724 */     GL11.glVertex2d(x + width, y);
/*      */ 
/*      */     
/*  727 */     GL11.glEnd();
/*      */ 
/*      */     
/*  730 */     glRestoreBlend(restore);
/*      */     
/*  732 */     GL11.glEnable(3553);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void glDrawFilledQuad(double x, double y, double width, double height, int startColour, int endColour) {
/*  742 */     boolean restore = glEnableBlend();
/*      */     
/*  744 */     GL11.glDisable(3553);
/*      */     
/*  746 */     GL11.glShadeModel(7425);
/*      */ 
/*      */     
/*  749 */     GL11.glBegin(7);
/*      */     
/*  751 */     glColour(startColour);
/*  752 */     GL11.glVertex2d(x, y);
/*      */     
/*  754 */     glColour(endColour);
/*  755 */     GL11.glVertex2d(x, y + height);
/*  756 */     GL11.glVertex2d(x + width, y + height);
/*      */     
/*  758 */     glColour(startColour);
/*  759 */     GL11.glVertex2d(x + width, y);
/*      */ 
/*      */     
/*  762 */     GL11.glEnd();
/*      */     
/*  764 */     GL11.glShadeModel(7424);
/*      */ 
/*      */     
/*  767 */     glRestoreBlend(restore);
/*      */     
/*  769 */     GL11.glEnable(3553);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void glDrawFilledRect(double x, double y, double x1, double y1, int colour) {
/*  778 */     boolean restore = glEnableBlend();
/*      */     
/*  780 */     GL11.glDisable(3553);
/*      */     
/*  782 */     glColour(colour);
/*      */ 
/*      */     
/*  785 */     GL11.glBegin(7);
/*      */     
/*  787 */     GL11.glVertex2d(x, y);
/*  788 */     GL11.glVertex2d(x, y1);
/*  789 */     GL11.glVertex2d(x1, y1);
/*  790 */     GL11.glVertex2d(x1, y);
/*      */ 
/*      */     
/*  793 */     GL11.glEnd();
/*      */ 
/*      */     
/*  796 */     glRestoreBlend(restore);
/*      */     
/*  798 */     GL11.glEnable(3553);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void glDrawArcFilled(double x, double y, float radius, float angleStart, float angleEnd, int segments, int colour) {
/*  809 */     boolean restore = glEnableBlend();
/*      */     
/*  811 */     GL11.glDisable(3553);
/*      */     
/*  813 */     glColour(colour);
/*      */     
/*  815 */     GL11.glDisable(2884);
/*      */     
/*  817 */     GL11.glTranslated(x, y, 0.0D);
/*      */     
/*  819 */     GL11.glBegin(9);
/*      */ 
/*      */     
/*  822 */     GL11.glVertex2f(0.0F, 0.0F);
/*      */     
/*  824 */     float[][] vertices = MathUtil.getArcVertices(radius, angleStart, angleEnd, segments);
/*      */     
/*  826 */     for (float[] vertex : vertices)
/*      */     {
/*  828 */       GL11.glVertex2f(vertex[0], vertex[1]);
/*      */     }
/*      */ 
/*      */     
/*  832 */     GL11.glEnd();
/*      */     
/*  834 */     GL11.glTranslated(-x, -y, 0.0D);
/*      */     
/*  836 */     glRestoreBlend(restore);
/*      */     
/*  838 */     GL11.glEnable(2884);
/*      */     
/*  840 */     GL11.glEnable(3553);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void glDrawArcOutline(double x, double y, float radius, float angleStart, float angleEnd, float lineWidth, int colour) {
/*  851 */     int segments = (int)(radius * 4.0F);
/*      */     
/*  853 */     boolean restore = glEnableBlend();
/*      */     
/*  855 */     GL11.glDisable(3553);
/*      */     
/*  857 */     GL11.glLineWidth(lineWidth);
/*      */     
/*  859 */     glColour(colour);
/*      */     
/*  861 */     GL11.glEnable(2848);
/*  862 */     GL11.glHint(3154, 4354);
/*      */     
/*  864 */     GL11.glTranslated(x, y, 0.0D);
/*      */     
/*  866 */     GL11.glBegin(3);
/*      */     
/*  868 */     float[][] vertices = MathUtil.getArcVertices(radius, angleStart, angleEnd, segments);
/*      */     
/*  870 */     for (float[] vertex : vertices)
/*      */     {
/*  872 */       GL11.glVertex2f(vertex[0], vertex[1]);
/*      */     }
/*      */ 
/*      */     
/*  876 */     GL11.glEnd();
/*      */     
/*  878 */     GL11.glTranslated(-x, -y, 0.0D);
/*      */     
/*  880 */     GL11.glDisable(2848);
/*  881 */     GL11.glHint(3154, 4352);
/*      */     
/*  883 */     glRestoreBlend(restore);
/*      */     
/*  885 */     GL11.glEnable(3553);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void glDrawPoint(double x, double y, float radius, ScaledResolution scaledResolution, int colour) {
/*  895 */     boolean restore = glEnableBlend();
/*      */     
/*  897 */     GL11.glEnable(2832);
/*  898 */     GL11.glHint(3153, 4354);
/*      */     
/*  900 */     GL11.glDisable(3553);
/*      */     
/*  902 */     glColour(colour);
/*      */     
/*  904 */     GL11.glPointSize(radius * GL11.glGetFloat(2982) * scaledResolution.getScaleFactor());
/*      */     
/*  906 */     GL11.glBegin(0);
/*      */     
/*  908 */     GL11.glVertex2d(x, y);
/*      */     
/*  910 */     GL11.glEnd();
/*      */ 
/*      */     
/*  913 */     glRestoreBlend(restore);
/*      */     
/*  915 */     GL11.glDisable(2832);
/*  916 */     GL11.glHint(3153, 4352);
/*      */     
/*  918 */     GL11.glEnable(3553);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void glDrawRoundedOutline(double x, double y, double width, double height, float lineWidth, RoundingMode roundingMode, float rounding, int colour) {
/*  929 */     boolean bLeft = false;
/*  930 */     boolean tLeft = false;
/*  931 */     boolean bRight = false;
/*  932 */     boolean tRight = false;
/*      */     
/*  934 */     switch (roundingMode) {
/*      */       case TOP:
/*  936 */         tLeft = true;
/*  937 */         tRight = true;
/*      */         break;
/*      */       case BOTTOM:
/*  940 */         bLeft = true;
/*  941 */         bRight = true;
/*      */         break;
/*      */       case FULL:
/*  944 */         tLeft = true;
/*  945 */         tRight = true;
/*  946 */         bLeft = true;
/*  947 */         bRight = true;
/*      */         break;
/*      */       case LEFT:
/*  950 */         bLeft = true;
/*  951 */         tLeft = true;
/*      */         break;
/*      */       case RIGHT:
/*  954 */         bRight = true;
/*  955 */         tRight = true;
/*      */         break;
/*      */       case TOP_LEFT:
/*  958 */         tLeft = true;
/*      */         break;
/*      */       case TOP_RIGHT:
/*  961 */         tRight = true;
/*      */         break;
/*      */       case BOTTOM_LEFT:
/*  964 */         bLeft = true;
/*      */         break;
/*      */       case BOTTOM_RIGHT:
/*  967 */         bRight = true;
/*      */         break;
/*      */     } 
/*      */ 
/*      */     
/*  972 */     GL11.glTranslated(x, y, 0.0D);
/*      */     
/*  974 */     boolean restore = glEnableBlend();
/*      */     
/*  976 */     if (tLeft)
/*      */     {
/*  978 */       glDrawArcOutline(rounding, rounding, rounding, 270.0F, 360.0F, lineWidth, colour);
/*      */     }
/*      */ 
/*      */     
/*  982 */     if (tRight)
/*      */     {
/*  984 */       glDrawArcOutline(width - rounding, rounding, rounding, 0.0F, 90.0F, lineWidth, colour);
/*      */     }
/*      */ 
/*      */     
/*  988 */     if (bLeft)
/*      */     {
/*  990 */       glDrawArcOutline(rounding, height - rounding, rounding, 180.0F, 270.0F, lineWidth, colour);
/*      */     }
/*      */ 
/*      */     
/*  994 */     if (bRight)
/*      */     {
/*  996 */       glDrawArcOutline(width - rounding, height - rounding, rounding, 90.0F, 180.0F, lineWidth, colour);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1001 */     GL11.glDisable(3553);
/*      */     
/* 1003 */     glColour(colour);
/*      */ 
/*      */     
/* 1006 */     GL11.glBegin(1);
/*      */     
/* 1008 */     if (tLeft) {
/* 1009 */       GL11.glVertex2d(0.0D, rounding);
/*      */     } else {
/* 1011 */       GL11.glVertex2d(0.0D, 0.0D);
/*      */     } 
/*      */     
/* 1014 */     if (bLeft) {
/* 1015 */       GL11.glVertex2d(0.0D, height - rounding);
/* 1016 */       GL11.glVertex2d(rounding, height);
/*      */     } else {
/* 1018 */       GL11.glVertex2d(0.0D, height);
/* 1019 */       GL11.glVertex2d(0.0D, height);
/*      */     } 
/*      */     
/* 1022 */     if (bRight) {
/* 1023 */       GL11.glVertex2d(width - rounding, height);
/* 1024 */       GL11.glVertex2d(width, height - rounding);
/*      */     } else {
/* 1026 */       GL11.glVertex2d(width, height);
/* 1027 */       GL11.glVertex2d(width, height);
/*      */     } 
/*      */     
/* 1030 */     if (tRight) {
/* 1031 */       GL11.glVertex2d(width, rounding);
/* 1032 */       GL11.glVertex2d(width - rounding, 0.0D);
/*      */     } else {
/* 1034 */       GL11.glVertex2d(width, 0.0D);
/* 1035 */       GL11.glVertex2d(width, 0.0D);
/*      */     } 
/*      */     
/* 1038 */     if (tLeft) {
/* 1039 */       GL11.glVertex2d(rounding, 0.0D);
/*      */     } else {
/* 1041 */       GL11.glVertex2d(0.0D, 0.0D);
/*      */     } 
/*      */ 
/*      */     
/* 1045 */     GL11.glEnd();
/*      */ 
/*      */     
/* 1048 */     glRestoreBlend(restore);
/*      */     
/* 1050 */     GL11.glTranslated(-x, -y, 0.0D);
/*      */     
/* 1052 */     GL11.glEnable(3553);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1080 */   private static final GLShader CIRCLE_SHADER = new GLShader("#version 120 \n\nvoid main() {\n    gl_TexCoord[0] = gl_MultiTexCoord0;\n    gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;\n}", "#version 120\n\nuniform float innerRadius;\nuniform vec4 colour;\n\nvoid main() {\n   vec2 pixel = gl_TexCoord[0].st;\n   vec2 centre = vec2(0.5, 0.5);\n   float d = length(pixel - centre);\n   float c = smoothstep(d+innerRadius, d+innerRadius+0.01, 0.5-innerRadius);\n   float a = smoothstep(0.0, 1.0, c) * colour.a;\n   gl_FragColor = vec4(colour.rgb, a);\n}\n")
/*      */     {
/*      */       public void setupUniforms() {
/* 1083 */         setupUniform("colour");
/* 1084 */         setupUniform("innerRadius");
/*      */       }
/*      */     };
/*      */ 
/*      */   
/*      */   private static final String ROUNDED_QUAD_FRAG_SHADER = "#version 120\nuniform float width;\nuniform float height;\nuniform float radius;\nuniform vec4 colour;\n\nfloat SDRoundedRect(vec2 p, vec2 b, float r) {\n    vec2 q = abs(p) - b + r;\n    return min(max(q.x, q.y), 0.0) + length(max(q, 0.0)) - r;\n}\n\nvoid main() {\n    vec2 size = vec2(width, height);\n    vec2 pixel = gl_TexCoord[0].st * size;\n    vec2 centre = 0.5 * size;\n    float b = SDRoundedRect(pixel - centre, centre, radius);\n    float a = 1.0 - smoothstep(0, 1.0, b);\n    gl_FragColor = vec4(colour.rgb, colour.a * a);\n}";
/*      */ 
/*      */   
/*      */   public static void glDrawSemiCircle(double x, double y, double diameter, float innerRadius, double percentage, int colour) {
/* 1093 */     boolean restore = glEnableBlend();
/*      */     
/* 1095 */     boolean alphaTest = GL11.glIsEnabled(3008);
/* 1096 */     if (alphaTest) GL11.glDisable(3008);
/*      */     
/* 1098 */     GL20.glUseProgram(CIRCLE_SHADER.getProgram());
/* 1099 */     GL20.glUniform1f(CIRCLE_SHADER.getUniformLocation("innerRadius"), innerRadius);
/* 1100 */     GL20.glUniform4f(CIRCLE_SHADER.getUniformLocation("colour"), (colour >> 16 & 0xFF) / 255.0F, (colour >> 8 & 0xFF) / 255.0F, (colour & 0xFF) / 255.0F, (colour >> 24 & 0xFF) / 255.0F);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1106 */     GL11.glBegin(7);
/*      */     
/* 1108 */     GL11.glTexCoord2f(0.0F, 0.0F);
/* 1109 */     GL11.glVertex2d(x, y);
/*      */     
/* 1111 */     GL11.glTexCoord2f(0.0F, 1.0F);
/* 1112 */     GL11.glVertex2d(x, y + diameter);
/*      */     
/* 1114 */     GL11.glTexCoord2f(1.0F, 1.0F);
/* 1115 */     GL11.glVertex2d(x + diameter, y + diameter);
/*      */     
/* 1117 */     GL11.glTexCoord2f(1.0F, 0.0F);
/* 1118 */     GL11.glVertex2d(x + diameter, y);
/*      */     
/* 1120 */     GL11.glEnd();
/*      */     
/* 1122 */     GL20.glUseProgram(0);
/*      */     
/* 1124 */     if (alphaTest) GL11.glEnable(3008);
/*      */     
/* 1126 */     glRestoreBlend(restore);
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
/*      */ 
/*      */   
/* 1150 */   private static final GLShader ROUNDED_QUAD_SHADER = new GLShader("#version 120 \n\nvoid main() {\n    gl_TexCoord[0] = gl_MultiTexCoord0;\n    gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;\n}", "#version 120\nuniform float width;\nuniform float height;\nuniform float radius;\nuniform vec4 colour;\n\nfloat SDRoundedRect(vec2 p, vec2 b, float r) {\n    vec2 q = abs(p) - b + r;\n    return min(max(q.x, q.y), 0.0) + length(max(q, 0.0)) - r;\n}\n\nvoid main() {\n    vec2 size = vec2(width, height);\n    vec2 pixel = gl_TexCoord[0].st * size;\n    vec2 centre = 0.5 * size;\n    float b = SDRoundedRect(pixel - centre, centre, radius);\n    float a = 1.0 - smoothstep(0, 1.0, b);\n    gl_FragColor = vec4(colour.rgb, colour.a * a);\n}")
/*      */     {
/*      */       public void setupUniforms() {
/* 1153 */         setupUniform("width");
/* 1154 */         setupUniform("height");
/* 1155 */         setupUniform("colour");
/* 1156 */         setupUniform("radius");
/*      */       }
/*      */     };
/*      */ 
/*      */   
/*      */   private static final String RAINBOW_FRAG_SHADER = "#version 120\nuniform float width;\nuniform float height;\nuniform float radius;\nuniform float u_time;\n\nfloat SDRoundedRect(vec2 p, vec2 b, float r) {\n    vec2 q = abs(p) - b + r;\n    return min(max(q.x, q.y), 0.0) + length(max(q, 0.0)) - r;\n}\n\nvoid main() {\n    vec2 size = vec2(width, height);\n    vec2 pixel = gl_TexCoord[0].st * size;\n    vec2 centre = 0.5 * size;\n    float b = SDRoundedRect(pixel - centre, centre, radius);\n    float a = 1.0 - smoothstep(0, 1.0, b);\n    vec3 colour = 0.5 + 0.5*cos(u_time+gl_TexCoord[0].st.x+vec3(0,2,4));\n    gl_FragColor = vec4(colour, a);\n}";
/*      */   
/*      */   public static void glDrawRoundedQuad(double x, double y, float width, float height, float radius, int colour) {
/* 1164 */     boolean restore = glEnableBlend();
/*      */     
/* 1166 */     boolean alphaTest = GL11.glIsEnabled(3008);
/* 1167 */     if (alphaTest) GL11.glDisable(3008);
/*      */     
/* 1169 */     GL20.glUseProgram(ROUNDED_QUAD_SHADER.getProgram());
/* 1170 */     GL20.glUniform1f(ROUNDED_QUAD_SHADER.getUniformLocation("width"), width);
/* 1171 */     GL20.glUniform1f(ROUNDED_QUAD_SHADER.getUniformLocation("height"), height);
/* 1172 */     GL20.glUniform1f(ROUNDED_QUAD_SHADER.getUniformLocation("radius"), radius);
/* 1173 */     GL20.glUniform4f(ROUNDED_QUAD_SHADER.getUniformLocation("colour"), (colour >> 16 & 0xFF) / 255.0F, (colour >> 8 & 0xFF) / 255.0F, (colour & 0xFF) / 255.0F, (colour >> 24 & 0xFF) / 255.0F);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1179 */     GL11.glDisable(3553);
/*      */     
/* 1181 */     GL11.glBegin(7);
/*      */     
/* 1183 */     GL11.glTexCoord2f(0.0F, 0.0F);
/* 1184 */     GL11.glVertex2d(x, y);
/*      */     
/* 1186 */     GL11.glTexCoord2f(0.0F, 1.0F);
/* 1187 */     GL11.glVertex2d(x, y + height);
/*      */     
/* 1189 */     GL11.glTexCoord2f(1.0F, 1.0F);
/* 1190 */     GL11.glVertex2d(x + width, y + height);
/*      */     
/* 1192 */     GL11.glTexCoord2f(1.0F, 0.0F);
/* 1193 */     GL11.glVertex2d(x + width, y);
/*      */     
/* 1195 */     GL11.glEnd();
/*      */     
/* 1197 */     GL20.glUseProgram(0);
/*      */     
/* 1199 */     GL11.glEnable(3553);
/*      */     
/* 1201 */     if (alphaTest) GL11.glEnable(3008);
/*      */     
/* 1203 */     glRestoreBlend(restore);
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
/*      */ 
/*      */ 
/*      */   
/* 1228 */   private static final GLShader GL_COLOUR_SHADER = new GLShader("#version 120 \n\nvoid main() {\n    gl_TexCoord[0] = gl_MultiTexCoord0;\n    gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;\n}", "#version 120\nuniform float width;\nuniform float height;\nuniform float radius;\nuniform float u_time;\n\nfloat SDRoundedRect(vec2 p, vec2 b, float r) {\n    vec2 q = abs(p) - b + r;\n    return min(max(q.x, q.y), 0.0) + length(max(q, 0.0)) - r;\n}\n\nvoid main() {\n    vec2 size = vec2(width, height);\n    vec2 pixel = gl_TexCoord[0].st * size;\n    vec2 centre = 0.5 * size;\n    float b = SDRoundedRect(pixel - centre, centre, radius);\n    float a = 1.0 - smoothstep(0, 1.0, b);\n    vec3 colour = 0.5 + 0.5*cos(u_time+gl_TexCoord[0].st.x+vec3(0,2,4));\n    gl_FragColor = vec4(colour, a);\n}")
/*      */     {
/* 1230 */       private final long initTime = System.currentTimeMillis();
/*      */ 
/*      */       
/*      */       public void setupUniforms() {
/* 1234 */         setupUniform("width");
/* 1235 */         setupUniform("height");
/* 1236 */         setupUniform("radius");
/* 1237 */         setupUniform("u_time");
/*      */       }
/*      */ 
/*      */       
/*      */       public void updateUniforms() {
/* 1242 */         GL20.glUniform1f(GL20.glGetUniformLocation(getProgram(), "u_time"), (float)(System.currentTimeMillis() - this.initTime) / 1000.0F);
/*      */       }
/*      */     };
/*      */   
/*      */   public static void glDrawRoundedQuadRainbow(double x, double y, float width, float height, float radius) {
/* 1247 */     boolean restore = glEnableBlend();
/*      */     
/* 1249 */     boolean alphaTest = GL11.glIsEnabled(3008);
/* 1250 */     if (alphaTest) GL11.glDisable(3008);
/*      */     
/* 1252 */     GL_COLOUR_SHADER.use();
/* 1253 */     GL20.glUniform1f(GL_COLOUR_SHADER.getUniformLocation("width"), width);
/* 1254 */     GL20.glUniform1f(GL_COLOUR_SHADER.getUniformLocation("height"), height);
/* 1255 */     GL20.glUniform1f(GL_COLOUR_SHADER.getUniformLocation("radius"), radius);
/*      */     
/* 1257 */     GL11.glDisable(3553);
/*      */     
/* 1259 */     GL11.glBegin(7);
/*      */     
/* 1261 */     GL11.glTexCoord2f(0.0F, 0.0F);
/* 1262 */     GL11.glVertex2d(x, y);
/*      */     
/* 1264 */     GL11.glTexCoord2f(0.0F, 1.0F);
/* 1265 */     GL11.glVertex2d(x, y + height);
/*      */     
/* 1267 */     GL11.glTexCoord2f(1.0F, 1.0F);
/* 1268 */     GL11.glVertex2d(x + width, y + height);
/*      */     
/* 1270 */     GL11.glTexCoord2f(1.0F, 0.0F);
/* 1271 */     GL11.glVertex2d(x + width, y);
/*      */ 
/*      */     
/* 1274 */     GL11.glEnd();
/*      */     
/* 1276 */     GL20.glUseProgram(0);
/*      */     
/* 1278 */     GL11.glEnable(3553);
/*      */     
/* 1280 */     if (alphaTest) GL11.glEnable(3008);
/*      */     
/* 1282 */     glRestoreBlend(restore);
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
/*      */   public static void glDrawRoundedRect(double x, double y, double width, double height, RoundingMode roundingMode, float rounding, float scaleFactor, int colour) {
/* 1294 */     boolean bLeft = false;
/* 1295 */     boolean tLeft = false;
/* 1296 */     boolean bRight = false;
/* 1297 */     boolean tRight = false;
/*      */     
/* 1299 */     switch (roundingMode) {
/*      */       case TOP:
/* 1301 */         tLeft = true;
/* 1302 */         tRight = true;
/*      */         break;
/*      */       case BOTTOM:
/* 1305 */         bLeft = true;
/* 1306 */         bRight = true;
/*      */         break;
/*      */       case FULL:
/* 1309 */         tLeft = true;
/* 1310 */         tRight = true;
/* 1311 */         bLeft = true;
/* 1312 */         bRight = true;
/*      */         break;
/*      */       case LEFT:
/* 1315 */         bLeft = true;
/* 1316 */         tLeft = true;
/*      */         break;
/*      */       case RIGHT:
/* 1319 */         bRight = true;
/* 1320 */         tRight = true;
/*      */         break;
/*      */       case TOP_LEFT:
/* 1323 */         tLeft = true;
/*      */         break;
/*      */       case TOP_RIGHT:
/* 1326 */         tRight = true;
/*      */         break;
/*      */       case BOTTOM_LEFT:
/* 1329 */         bLeft = true;
/*      */         break;
/*      */       case BOTTOM_RIGHT:
/* 1332 */         bRight = true;
/*      */         break;
/*      */     } 
/*      */     
/* 1336 */     float alpha = (colour >> 24 & 0xFF) / 255.0F;
/*      */ 
/*      */     
/* 1339 */     boolean restore = glEnableBlend();
/*      */ 
/*      */     
/* 1342 */     glColour(colour);
/*      */ 
/*      */     
/* 1345 */     GL11.glTranslated(x, y, 0.0D);
/*      */     
/* 1347 */     GL11.glDisable(3553);
/*      */ 
/*      */     
/* 1350 */     GL11.glBegin(9);
/*      */     
/* 1352 */     if (tLeft) {
/* 1353 */       GL11.glVertex2d(rounding, rounding);
/* 1354 */       GL11.glVertex2d(0.0D, rounding);
/*      */     } else {
/* 1356 */       GL11.glVertex2d(0.0D, 0.0D);
/*      */     } 
/*      */     
/* 1359 */     if (bLeft) {
/* 1360 */       GL11.glVertex2d(0.0D, height - rounding);
/* 1361 */       GL11.glVertex2d(rounding, height - rounding);
/* 1362 */       GL11.glVertex2d(rounding, height);
/*      */     } else {
/* 1364 */       GL11.glVertex2d(0.0D, height);
/*      */     } 
/*      */     
/* 1367 */     if (bRight) {
/* 1368 */       GL11.glVertex2d(width - rounding, height);
/* 1369 */       GL11.glVertex2d(width - rounding, height - rounding);
/* 1370 */       GL11.glVertex2d(width, height - rounding);
/*      */     } else {
/* 1372 */       GL11.glVertex2d(width, height);
/*      */     } 
/*      */     
/* 1375 */     if (tRight) {
/* 1376 */       GL11.glVertex2d(width, rounding);
/* 1377 */       GL11.glVertex2d(width - rounding, rounding);
/* 1378 */       GL11.glVertex2d(width - rounding, 0.0D);
/*      */     } else {
/* 1380 */       GL11.glVertex2d(width, 0.0D);
/*      */     } 
/*      */     
/* 1383 */     if (tLeft) {
/* 1384 */       GL11.glVertex2d(rounding, 0.0D);
/*      */     }
/*      */ 
/*      */     
/* 1388 */     GL11.glEnd();
/*      */ 
/*      */     
/* 1391 */     GL11.glEnable(2832);
/* 1392 */     GL11.glHint(3153, 4354);
/*      */ 
/*      */     
/* 1395 */     GL11.glPointSize(rounding * 2.0F * GL11.glGetFloat(2982) * scaleFactor);
/*      */     
/* 1397 */     GL11.glBegin(0);
/*      */     
/* 1399 */     if (tLeft)
/*      */     {
/* 1401 */       GL11.glVertex2d(rounding, rounding);
/*      */     }
/*      */     
/* 1404 */     if (tRight)
/*      */     {
/* 1406 */       GL11.glVertex2d(width - rounding, rounding);
/*      */     }
/*      */     
/* 1409 */     if (bLeft)
/*      */     {
/* 1411 */       GL11.glVertex2d(rounding, height - rounding);
/*      */     }
/*      */     
/* 1414 */     if (bRight)
/*      */     {
/* 1416 */       GL11.glVertex2d(width - rounding, height - rounding);
/*      */     }
/*      */     
/* 1419 */     GL11.glEnd();
/*      */ 
/*      */     
/* 1422 */     GL11.glDisable(2832);
/* 1423 */     GL11.glHint(3153, 4352);
/*      */     
/* 1425 */     glRestoreBlend(restore);
/*      */     
/* 1427 */     GL11.glTranslated(-x, -y, 0.0D);
/*      */     
/* 1429 */     GL11.glEnable(3553);
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
/*      */   public static void glDrawRoundedRectEllipse(double x, double y, double width, double height, RoundingMode roundingMode, int roundingDef, double roundingLevel, int colour) {
/* 1441 */     boolean bLeft = false;
/* 1442 */     boolean tLeft = false;
/* 1443 */     boolean bRight = false;
/* 1444 */     boolean tRight = false;
/*      */     
/* 1446 */     switch (roundingMode) {
/*      */       case TOP:
/* 1448 */         tLeft = true;
/* 1449 */         tRight = true;
/*      */         break;
/*      */       case BOTTOM:
/* 1452 */         bLeft = true;
/* 1453 */         bRight = true;
/*      */         break;
/*      */       case FULL:
/* 1456 */         tLeft = true;
/* 1457 */         tRight = true;
/* 1458 */         bLeft = true;
/* 1459 */         bRight = true;
/*      */         break;
/*      */       case LEFT:
/* 1462 */         bLeft = true;
/* 1463 */         tLeft = true;
/*      */         break;
/*      */       case RIGHT:
/* 1466 */         bRight = true;
/* 1467 */         tRight = true;
/*      */         break;
/*      */       case TOP_LEFT:
/* 1470 */         tLeft = true;
/*      */         break;
/*      */       case TOP_RIGHT:
/* 1473 */         tRight = true;
/*      */         break;
/*      */       case BOTTOM_LEFT:
/* 1476 */         bLeft = true;
/*      */         break;
/*      */       case BOTTOM_RIGHT:
/* 1479 */         bRight = true;
/*      */         break;
/*      */     } 
/*      */ 
/*      */     
/* 1484 */     GL11.glTranslated(x, y, 0.0D);
/*      */     
/* 1486 */     GL11.glEnable(2881);
/* 1487 */     GL11.glHint(3154, 4354);
/*      */     
/* 1489 */     boolean restore = glEnableBlend();
/*      */     
/* 1491 */     if (tLeft)
/*      */     {
/* 1493 */       glDrawFilledEllipse(roundingLevel, roundingLevel, roundingLevel, (int)(roundingDef * 0.5D), (int)(roundingDef * 0.75D), roundingDef, false, colour);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1498 */     if (tRight)
/*      */     {
/* 1500 */       glDrawFilledEllipse(width - roundingLevel, roundingLevel, roundingLevel, (int)(roundingDef * 0.75D), roundingDef, roundingDef, false, colour);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1505 */     if (bLeft)
/*      */     {
/* 1507 */       glDrawFilledEllipse(roundingLevel, height - roundingLevel, roundingLevel, (int)(roundingDef * 0.25D), (int)(roundingDef * 0.5D), roundingDef, false, colour);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1512 */     if (bRight)
/*      */     {
/* 1514 */       glDrawFilledEllipse(width - roundingLevel, height - roundingLevel, roundingLevel, 0, (int)(roundingDef * 0.25D), roundingDef, false, colour);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1520 */     GL11.glDisable(2881);
/* 1521 */     GL11.glHint(3154, 4352);
/*      */ 
/*      */     
/* 1524 */     GL11.glDisable(3553);
/*      */     
/* 1526 */     glColour(colour);
/*      */ 
/*      */     
/* 1529 */     GL11.glBegin(9);
/*      */     
/* 1531 */     if (tLeft) {
/* 1532 */       GL11.glVertex2d(roundingLevel, roundingLevel);
/* 1533 */       GL11.glVertex2d(0.0D, roundingLevel);
/*      */     } else {
/* 1535 */       GL11.glVertex2d(0.0D, 0.0D);
/*      */     } 
/*      */     
/* 1538 */     if (bLeft) {
/* 1539 */       GL11.glVertex2d(0.0D, height - roundingLevel);
/* 1540 */       GL11.glVertex2d(roundingLevel, height - roundingLevel);
/* 1541 */       GL11.glVertex2d(roundingLevel, height);
/*      */     } else {
/* 1543 */       GL11.glVertex2d(0.0D, height);
/*      */     } 
/*      */     
/* 1546 */     if (bRight) {
/* 1547 */       GL11.glVertex2d(width - roundingLevel, height);
/* 1548 */       GL11.glVertex2d(width - roundingLevel, height - roundingLevel);
/* 1549 */       GL11.glVertex2d(width, height - roundingLevel);
/*      */     } else {
/* 1551 */       GL11.glVertex2d(width, height);
/*      */     } 
/*      */     
/* 1554 */     if (tRight) {
/* 1555 */       GL11.glVertex2d(width, roundingLevel);
/* 1556 */       GL11.glVertex2d(width - roundingLevel, roundingLevel);
/* 1557 */       GL11.glVertex2d(width - roundingLevel, 0.0D);
/*      */     } else {
/* 1559 */       GL11.glVertex2d(width, 0.0D);
/*      */     } 
/*      */     
/* 1562 */     if (tLeft) {
/* 1563 */       GL11.glVertex2d(roundingLevel, 0.0D);
/*      */     }
/*      */ 
/*      */     
/* 1567 */     GL11.glEnd();
/*      */ 
/*      */     
/* 1570 */     glRestoreBlend(restore);
/*      */     
/* 1572 */     GL11.glTranslated(-x, -y, 0.0D);
/*      */     
/* 1574 */     GL11.glEnable(3553);
/*      */   }
/*      */   
/*      */   public static boolean glEnableBlend() {
/* 1578 */     boolean wasEnabled = GL11.glIsEnabled(3042);
/*      */     
/* 1580 */     if (!wasEnabled) {
/* 1581 */       GL11.glEnable(3042);
/* 1582 */       GL14.glBlendFuncSeparate(770, 771, 1, 0);
/*      */     } 
/*      */     
/* 1585 */     return wasEnabled;
/*      */   }
/*      */   
/*      */   public static void glRestoreBlend(boolean wasEnabled) {
/* 1589 */     if (!wasEnabled) {
/* 1590 */       GL11.glDisable(3042);
/*      */     }
/*      */   }
/*      */   
/*      */   public static float interpolate(float old, float now, float progress) {
/* 1595 */     return old + (now - old) * progress;
/*      */   }
/*      */   
/*      */   public static double interpolate(double old, double now, double progress) {
/* 1599 */     return old + (now - old) * progress;
/*      */   }
/*      */   
/*      */   public static Vec3 interpolate(Vec3 old, Vec3 now, double progress) {
/* 1603 */     Vec3 difVec = now.subtract(old);
/* 1604 */     return new Vec3(old.xCoord + difVec.xCoord * progress, old.yCoord + difVec.yCoord * progress, old.zCoord + difVec.zCoord * progress);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[] interpolate(Entity entity, float partialTicks) {
/* 1610 */     return new double[] {
/* 1611 */         interpolate(entity.prevPosX, entity.posX, partialTicks), 
/* 1612 */         interpolate(entity.prevPosY, entity.posY, partialTicks), 
/* 1613 */         interpolate(entity.prevPosZ, entity.posZ, partialTicks)
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static AxisAlignedBB interpolate(Entity entity, AxisAlignedBB boundingBox, float partialTicks) {
/* 1620 */     float invertedPT = 1.0F - partialTicks;
/* 1621 */     return boundingBox.offset((entity.posX - entity.prevPosX) * -invertedPT, (entity.posY - entity.prevPosY) * -invertedPT, (entity.posZ - entity.prevPosZ) * -invertedPT);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void glDrawBoundingBox(AxisAlignedBB bb, float lineWidth, boolean filled) {
/* 1631 */     if (filled) {
/*      */       
/* 1633 */       GL11.glBegin(8);
/*      */       
/* 1635 */       GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
/* 1636 */       GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
/*      */       
/* 1638 */       GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
/* 1639 */       GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
/*      */       
/* 1641 */       GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
/* 1642 */       GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
/*      */       
/* 1644 */       GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
/* 1645 */       GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
/*      */       
/* 1647 */       GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
/* 1648 */       GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
/*      */       
/* 1650 */       GL11.glEnd();
/*      */ 
/*      */       
/* 1653 */       GL11.glBegin(7);
/*      */       
/* 1655 */       GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
/* 1656 */       GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
/* 1657 */       GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
/* 1658 */       GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
/*      */       
/* 1660 */       GL11.glEnd();
/*      */       
/* 1662 */       GL11.glCullFace(1028);
/*      */ 
/*      */       
/* 1665 */       GL11.glBegin(7);
/*      */       
/* 1667 */       GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
/* 1668 */       GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
/* 1669 */       GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
/* 1670 */       GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
/*      */       
/* 1672 */       GL11.glEnd();
/*      */       
/* 1674 */       GL11.glCullFace(1029);
/*      */     } 
/*      */ 
/*      */     
/* 1678 */     if (lineWidth > 0.0F) {
/* 1679 */       GL11.glLineWidth(lineWidth);
/*      */       
/* 1681 */       GL11.glEnable(2848);
/* 1682 */       GL11.glHint(3154, 4354);
/*      */       
/* 1684 */       GL11.glBegin(3);
/*      */ 
/*      */       
/* 1687 */       GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
/* 1688 */       GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
/* 1689 */       GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
/* 1690 */       GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
/* 1691 */       GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
/*      */ 
/*      */       
/* 1694 */       GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
/* 1695 */       GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
/* 1696 */       GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
/* 1697 */       GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
/* 1698 */       GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
/*      */       
/* 1700 */       GL11.glEnd();
/*      */       
/* 1702 */       GL11.glBegin(1);
/*      */       
/* 1704 */       GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
/* 1705 */       GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
/*      */       
/* 1707 */       GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
/* 1708 */       GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
/*      */       
/* 1710 */       GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
/* 1711 */       GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
/*      */       
/* 1713 */       GL11.glEnd();
/*      */       
/* 1715 */       GL11.glDisable(2848);
/* 1716 */       GL11.glHint(3154, 4352);
/*      */     } 
/*      */   }
/*      */   
/*      */   public enum RoundingMode
/*      */   {
/* 1722 */     TOP_LEFT,
/* 1723 */     BOTTOM_LEFT,
/* 1724 */     TOP_RIGHT,
/* 1725 */     BOTTOM_RIGHT,
/*      */     
/* 1727 */     LEFT,
/* 1728 */     RIGHT,
/*      */     
/* 1730 */     TOP,
/* 1731 */     BOTTOM,
/*      */     
/* 1733 */     FULL;
/*      */   }
/*      */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\utility\shader\ketaUtils\render\DrawUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */