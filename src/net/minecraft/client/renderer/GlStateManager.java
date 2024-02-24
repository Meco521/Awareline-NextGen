/*      */ package net.minecraft.client.renderer;
/*      */ 
/*      */ import java.nio.ByteBuffer;
/*      */ import java.nio.FloatBuffer;
/*      */ import java.nio.IntBuffer;
/*      */ import net.minecraft.src.Config;
/*      */ import net.optifine.SmartAnimations;
/*      */ import net.optifine.render.GlAlphaState;
/*      */ import net.optifine.render.GlBlendState;
/*      */ import net.optifine.shaders.Shaders;
/*      */ import net.optifine.util.LockCounter;
/*      */ import org.lwjgl.opengl.GL11;
/*      */ import org.lwjgl.opengl.GL14;
/*      */ 
/*      */ 
/*      */ public class GlStateManager
/*      */ {
/*   18 */   private static final AlphaState alphaState = new AlphaState();
/*   19 */   private static final BooleanState lightingState = new BooleanState(2896);
/*   20 */   private static final BooleanState[] lightState = new BooleanState[8];
/*   21 */   private static final ColorMaterialState colorMaterialState = new ColorMaterialState();
/*   22 */   private static final BlendState blendState = new BlendState();
/*   23 */   private static final DepthState depthState = new DepthState();
/*   24 */   private static final FogState fogState = new FogState();
/*   25 */   private static final CullState cullState = new CullState();
/*   26 */   private static final PolygonOffsetState polygonOffsetState = new PolygonOffsetState();
/*   27 */   private static final ColorLogicState colorLogicState = new ColorLogicState();
/*   28 */   private static final TexGenState texGenState = new TexGenState();
/*   29 */   private static final ClearState clearState = new ClearState();
/*   30 */   private static final StencilState stencilState = new StencilState();
/*   31 */   private static final BooleanState normalizeState = new BooleanState(2977);
/*   32 */   public static int activeTextureUnit = 0;
/*   33 */   public static TextureState[] textureState = new TextureState[32];
/*   34 */   private static int activeShadeModel = 7425;
/*   35 */   private static final BooleanState rescaleNormalState = new BooleanState(32826);
/*   36 */   private static final ColorMask colorMaskState = new ColorMask();
/*   37 */   private static final Color colorState = new Color();
/*      */   public static boolean clearEnabled = true;
/*   39 */   private static final LockCounter alphaLock = new LockCounter();
/*   40 */   private static final GlAlphaState alphaLockState = new GlAlphaState();
/*   41 */   private static final LockCounter blendLock = new LockCounter();
/*   42 */   private static final GlBlendState blendLockState = new GlBlendState();
/*      */   
/*      */   private static boolean creatingDisplayList = false;
/*      */   
/*      */   public static void pushAttrib() {
/*   47 */     GL11.glPushAttrib(8256);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void popAttrib() {
/*   52 */     GL11.glPopAttrib();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void disableAlpha() {
/*   57 */     if (alphaLock.isLocked()) {
/*      */       
/*   59 */       alphaLockState.setDisabled();
/*      */     }
/*      */     else {
/*      */       
/*   63 */       alphaState.alphaTest.setDisabled();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void enableAlpha() {
/*   69 */     if (alphaLock.isLocked()) {
/*      */       
/*   71 */       alphaLockState.setEnabled();
/*      */     }
/*      */     else {
/*      */       
/*   75 */       alphaState.alphaTest.setEnabled();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void alphaFunc(int func, float ref) {
/*   81 */     if (alphaLock.isLocked()) {
/*      */       
/*   83 */       alphaLockState.setFuncRef(func, ref);
/*      */ 
/*      */     
/*      */     }
/*   87 */     else if (func != alphaState.func || ref != alphaState.ref) {
/*      */       
/*   89 */       alphaState.func = func;
/*   90 */       alphaState.ref = ref;
/*   91 */       GL11.glAlphaFunc(func, ref);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void enableLighting() {
/*   98 */     lightingState.setEnabled();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void disableLighting() {
/*  103 */     lightingState.setDisabled();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void enableLight(int light) {
/*  108 */     lightState[light].setEnabled();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void disableLight(int light) {
/*  113 */     lightState[light].setDisabled();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void enableColorMaterial() {
/*  118 */     colorMaterialState.colorMaterial.setEnabled();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void disableColorMaterial() {
/*  123 */     colorMaterialState.colorMaterial.setDisabled();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void colorMaterial(int face, int mode) {
/*  128 */     if (face != colorMaterialState.face || mode != colorMaterialState.mode) {
/*      */       
/*  130 */       colorMaterialState.face = face;
/*  131 */       colorMaterialState.mode = mode;
/*  132 */       GL11.glColorMaterial(face, mode);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void disableDepth() {
/*  138 */     depthState.depthTest.setDisabled();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void enableDepth() {
/*  143 */     depthState.depthTest.setEnabled();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void depthFunc(int depthFunc) {
/*  148 */     if (depthFunc != depthState.depthFunc) {
/*      */       
/*  150 */       depthState.depthFunc = depthFunc;
/*  151 */       GL11.glDepthFunc(depthFunc);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void depthMask(boolean flagIn) {
/*  157 */     if (flagIn != depthState.maskEnabled) {
/*      */       
/*  159 */       depthState.maskEnabled = flagIn;
/*  160 */       GL11.glDepthMask(flagIn);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void disableBlend() {
/*  166 */     if (blendLock.isLocked()) {
/*      */       
/*  168 */       blendLockState.setDisabled();
/*      */     }
/*      */     else {
/*      */       
/*  172 */       blendState.blend.setDisabled();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void enableBlend() {
/*  178 */     if (blendLock.isLocked()) {
/*      */       
/*  180 */       blendLockState.setEnabled();
/*      */     }
/*      */     else {
/*      */       
/*  184 */       blendState.blend.setEnabled();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void blendFunc(int srcFactor, int dstFactor) {
/*  190 */     if (blendLock.isLocked()) {
/*      */       
/*  192 */       blendLockState.setFactors(srcFactor, dstFactor);
/*      */ 
/*      */     
/*      */     }
/*  196 */     else if (srcFactor != blendState.srcFactor || dstFactor != blendState.dstFactor || srcFactor != blendState.srcFactorAlpha || dstFactor != blendState.dstFactorAlpha) {
/*      */       
/*  198 */       blendState.srcFactor = srcFactor;
/*  199 */       blendState.dstFactor = dstFactor;
/*  200 */       blendState.srcFactorAlpha = srcFactor;
/*  201 */       blendState.dstFactorAlpha = dstFactor;
/*      */       
/*  203 */       if (Config.isShaders())
/*      */       {
/*  205 */         Shaders.uniform_blendFunc.setValue(srcFactor, dstFactor, srcFactor, dstFactor);
/*      */       }
/*      */       
/*  208 */       GL11.glBlendFunc(srcFactor, dstFactor);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void tryBlendFuncSeparate(int srcFactor, int dstFactor, int srcFactorAlpha, int dstFactorAlpha) {
/*  215 */     if (blendLock.isLocked()) {
/*      */       
/*  217 */       blendLockState.setFactors(srcFactor, dstFactor, srcFactorAlpha, dstFactorAlpha);
/*      */ 
/*      */     
/*      */     }
/*  221 */     else if (srcFactor != blendState.srcFactor || dstFactor != blendState.dstFactor || srcFactorAlpha != blendState.srcFactorAlpha || dstFactorAlpha != blendState.dstFactorAlpha) {
/*      */       
/*  223 */       blendState.srcFactor = srcFactor;
/*  224 */       blendState.dstFactor = dstFactor;
/*  225 */       blendState.srcFactorAlpha = srcFactorAlpha;
/*  226 */       blendState.dstFactorAlpha = dstFactorAlpha;
/*      */       
/*  228 */       if (Config.isShaders())
/*      */       {
/*  230 */         Shaders.uniform_blendFunc.setValue(srcFactor, dstFactor, srcFactorAlpha, dstFactorAlpha);
/*      */       }
/*      */       
/*  233 */       OpenGlHelper.glBlendFunc(srcFactor, dstFactor, srcFactorAlpha, dstFactorAlpha);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void enableFog() {
/*  240 */     fogState.fog.setEnabled();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void disableFog() {
/*  245 */     fogState.fog.setDisabled();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setFog(int param) {
/*  250 */     if (param != fogState.mode) {
/*      */       
/*  252 */       fogState.mode = param;
/*  253 */       GL11.glFogi(2917, param);
/*      */       
/*  255 */       if (Config.isShaders())
/*      */       {
/*  257 */         Shaders.setFogMode(param);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setFogDensity(float param) {
/*  264 */     if (param < 0.0F)
/*      */     {
/*  266 */       param = 0.0F;
/*      */     }
/*      */     
/*  269 */     if (param != fogState.density) {
/*      */       
/*  271 */       fogState.density = param;
/*  272 */       GL11.glFogf(2914, param);
/*      */       
/*  274 */       if (Config.isShaders())
/*      */       {
/*  276 */         Shaders.setFogDensity(param);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setFogStart(float param) {
/*  283 */     if (param != fogState.start) {
/*      */       
/*  285 */       fogState.start = param;
/*  286 */       GL11.glFogf(2915, param);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setFogEnd(float param) {
/*  292 */     if (param != fogState.end) {
/*      */       
/*  294 */       fogState.end = param;
/*  295 */       GL11.glFogf(2916, param);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glFogi(int p_glFogi_0_, int p_glFogi_1_) {
/*  301 */     GL11.glFogi(p_glFogi_0_, p_glFogi_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void enableCull() {
/*  306 */     cullState.cullFace.setEnabled();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void disableCull() {
/*  311 */     cullState.cullFace.setDisabled();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void cullFace(int mode) {
/*  316 */     if (mode != cullState.mode) {
/*      */       
/*  318 */       cullState.mode = mode;
/*  319 */       GL11.glCullFace(mode);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void enablePolygonOffset() {
/*  325 */     polygonOffsetState.polygonOffsetFill.setEnabled();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void disablePolygonOffset() {
/*  330 */     polygonOffsetState.polygonOffsetFill.setDisabled();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void doPolygonOffset(float factor, float units) {
/*  335 */     if (factor != polygonOffsetState.factor || units != polygonOffsetState.units) {
/*      */       
/*  337 */       polygonOffsetState.factor = factor;
/*  338 */       polygonOffsetState.units = units;
/*  339 */       GL11.glPolygonOffset(factor, units);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void enableColorLogic() {
/*  345 */     colorLogicState.colorLogicOp.setEnabled();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void disableColorLogic() {
/*  350 */     colorLogicState.colorLogicOp.setDisabled();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void colorLogicOp(int opcode) {
/*  355 */     if (opcode != colorLogicState.opcode) {
/*      */       
/*  357 */       colorLogicState.opcode = opcode;
/*  358 */       GL11.glLogicOp(opcode);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void enableTexGenCoord(TexGen p_179087_0_) {
/*  364 */     (texGenCoord(p_179087_0_)).textureGen.setEnabled();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void disableTexGenCoord(TexGen p_179100_0_) {
/*  369 */     (texGenCoord(p_179100_0_)).textureGen.setDisabled();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void texGen(TexGen texGen, int param) {
/*  374 */     TexGenCoord glstatemanager$texgencoord = texGenCoord(texGen);
/*      */     
/*  376 */     if (param != glstatemanager$texgencoord.param) {
/*      */       
/*  378 */       glstatemanager$texgencoord.param = param;
/*  379 */       GL11.glTexGeni(glstatemanager$texgencoord.coord, 9472, param);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void texGen(TexGen p_179105_0_, int pname, FloatBuffer params) {
/*  385 */     GL11.glTexGen((texGenCoord(p_179105_0_)).coord, pname, params);
/*      */   }
/*      */ 
/*      */   
/*      */   private static TexGenCoord texGenCoord(TexGen p_179125_0_) {
/*  390 */     switch (p_179125_0_) {
/*      */       
/*      */       case S:
/*  393 */         return texGenState.s;
/*      */       
/*      */       case T:
/*  396 */         return texGenState.t;
/*      */       
/*      */       case R:
/*  399 */         return texGenState.r;
/*      */       
/*      */       case Q:
/*  402 */         return texGenState.q;
/*      */     } 
/*      */     
/*  405 */     return texGenState.s;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setActiveTexture(int texture) {
/*  411 */     if (activeTextureUnit != texture - OpenGlHelper.defaultTexUnit) {
/*      */       
/*  413 */       activeTextureUnit = texture - OpenGlHelper.defaultTexUnit;
/*  414 */       OpenGlHelper.setActiveTexture(texture);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void enableTexture2D() {
/*  420 */     (textureState[activeTextureUnit]).texture2DState.setEnabled();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void disableTexture2D() {
/*  425 */     (textureState[activeTextureUnit]).texture2DState.setDisabled();
/*      */   }
/*      */ 
/*      */   
/*      */   public static int generateTexture() {
/*  430 */     return GL11.glGenTextures();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void deleteTexture(int texture) {
/*  435 */     if (texture != 0) {
/*      */       
/*  437 */       GL11.glDeleteTextures(texture);
/*      */       
/*  439 */       for (TextureState glstatemanager$texturestate : textureState) {
/*      */         
/*  441 */         if (glstatemanager$texturestate.textureName == texture)
/*      */         {
/*  443 */           glstatemanager$texturestate.textureName = 0;
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void bindTexture(int texture) {
/*  451 */     if (texture != (textureState[activeTextureUnit]).textureName) {
/*      */       
/*  453 */       (textureState[activeTextureUnit]).textureName = texture;
/*  454 */       GL11.glBindTexture(3553, texture);
/*      */       
/*  456 */       if (SmartAnimations.isActive())
/*      */       {
/*  458 */         SmartAnimations.textureRendered(texture);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void enableNormalize() {
/*  465 */     normalizeState.setEnabled();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void disableNormalize() {
/*  470 */     normalizeState.setDisabled();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void shadeModel(int mode) {
/*  475 */     if (mode != activeShadeModel) {
/*      */       
/*  477 */       activeShadeModel = mode;
/*  478 */       GL11.glShadeModel(mode);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void enableRescaleNormal() {
/*  484 */     rescaleNormalState.setEnabled();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void disableRescaleNormal() {
/*  489 */     rescaleNormalState.setDisabled();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void viewport(int x, int y, int width, int height) {
/*  494 */     GL11.glViewport(x, y, width, height);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void colorMask(boolean red, boolean green, boolean blue, boolean alpha) {
/*  499 */     if (red != colorMaskState.red || green != colorMaskState.green || blue != colorMaskState.blue || alpha != colorMaskState.alpha) {
/*      */       
/*  501 */       colorMaskState.red = red;
/*  502 */       colorMaskState.green = green;
/*  503 */       colorMaskState.blue = blue;
/*  504 */       colorMaskState.alpha = alpha;
/*  505 */       GL11.glColorMask(red, green, blue, alpha);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void clearDepth(double depth) {
/*  511 */     if (depth != clearState.depth) {
/*      */       
/*  513 */       clearState.depth = depth;
/*  514 */       GL11.glClearDepth(depth);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void clearColor(float red, float green, float blue, float alpha) {
/*  520 */     if (red != clearState.color.red || green != clearState.color.green || blue != clearState.color.blue || alpha != clearState.color.alpha) {
/*      */       
/*  522 */       clearState.color.red = red;
/*  523 */       clearState.color.green = green;
/*  524 */       clearState.color.blue = blue;
/*  525 */       clearState.color.alpha = alpha;
/*  526 */       GL11.glClearColor(red, green, blue, alpha);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void clear(int mask) {
/*  532 */     if (clearEnabled)
/*      */     {
/*  534 */       GL11.glClear(mask);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static void matrixMode(int mode) {
/*  540 */     GL11.glMatrixMode(mode);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void loadIdentity() {
/*  545 */     GL11.glLoadIdentity();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void pushMatrix() {
/*  550 */     GL11.glPushMatrix();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void popMatrix() {
/*  555 */     GL11.glPopMatrix();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void getFloat(int pname, FloatBuffer params) {
/*  560 */     GL11.glGetFloat(pname, params);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void ortho(double left, double right, double bottom, double top, double zNear, double zFar) {
/*  565 */     GL11.glOrtho(left, right, bottom, top, zNear, zFar);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void rotate(float angle, float x, float y, float z) {
/*  570 */     GL11.glRotatef(angle, x, y, z);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void scale(float x, float y, float z) {
/*  575 */     GL11.glScalef(x, y, z);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void scale(double x, double y, double z) {
/*  580 */     GL11.glScaled(x, y, z);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void translate(float x, float y, float z) {
/*  585 */     GL11.glTranslatef(x, y, z);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void translate(double x, double y, double z) {
/*  590 */     GL11.glTranslated(x, y, z);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void multMatrix(FloatBuffer matrix) {
/*  595 */     GL11.glMultMatrix(matrix);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void color(float colorRed, float colorGreen, float colorBlue, float colorAlpha) {
/*  600 */     if (colorRed != colorState.red || colorGreen != colorState.green || colorBlue != colorState.blue || colorAlpha != colorState.alpha) {
/*      */       
/*  602 */       colorState.red = colorRed;
/*  603 */       colorState.green = colorGreen;
/*  604 */       colorState.blue = colorBlue;
/*  605 */       colorState.alpha = colorAlpha;
/*  606 */       GL11.glColor4f(colorRed, colorGreen, colorBlue, colorAlpha);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void color(float colorRed, float colorGreen, float colorBlue) {
/*  612 */     color(colorRed, colorGreen, colorBlue, 1.0F);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void resetColor() {
/*  617 */     colorState.red = colorState.green = colorState.blue = colorState.alpha = -1.0F;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glTexCoordPointer(int p_glTexCoordPointer_0_, int p_glTexCoordPointer_1_, int p_glTexCoordPointer_2_, int p_glTexCoordPointer_3_) {
/*  622 */     GL11.glTexCoordPointer(p_glTexCoordPointer_0_, p_glTexCoordPointer_1_, p_glTexCoordPointer_2_, p_glTexCoordPointer_3_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glTexCoordPointer(int p_glTexCoordPointer_0_, int p_glTexCoordPointer_1_, int p_glTexCoordPointer_2_, ByteBuffer p_glTexCoordPointer_3_) {
/*  627 */     GL11.glTexCoordPointer(p_glTexCoordPointer_0_, p_glTexCoordPointer_1_, p_glTexCoordPointer_2_, p_glTexCoordPointer_3_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glVertexPointer(int p_glVertexPointer_0_, int p_glVertexPointer_1_, int p_glVertexPointer_2_, int p_glVertexPointer_3_) {
/*  632 */     GL11.glVertexPointer(p_glVertexPointer_0_, p_glVertexPointer_1_, p_glVertexPointer_2_, p_glVertexPointer_3_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glVertexPointer(int p_glVertexPointer_0_, int p_glVertexPointer_1_, int p_glVertexPointer_2_, ByteBuffer p_glVertexPointer_3_) {
/*  637 */     GL11.glVertexPointer(p_glVertexPointer_0_, p_glVertexPointer_1_, p_glVertexPointer_2_, p_glVertexPointer_3_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glColorPointer(int p_glColorPointer_0_, int p_glColorPointer_1_, int p_glColorPointer_2_, int p_glColorPointer_3_) {
/*  642 */     GL11.glColorPointer(p_glColorPointer_0_, p_glColorPointer_1_, p_glColorPointer_2_, p_glColorPointer_3_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glColorPointer(int p_glColorPointer_0_, int p_glColorPointer_1_, int p_glColorPointer_2_, ByteBuffer p_glColorPointer_3_) {
/*  647 */     GL11.glColorPointer(p_glColorPointer_0_, p_glColorPointer_1_, p_glColorPointer_2_, p_glColorPointer_3_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glDisableClientState(int p_glDisableClientState_0_) {
/*  652 */     GL11.glDisableClientState(p_glDisableClientState_0_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glEnableClientState(int p_glEnableClientState_0_) {
/*  657 */     GL11.glEnableClientState(p_glEnableClientState_0_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glBegin(int p_glBegin_0_) {
/*  662 */     GL11.glBegin(p_glBegin_0_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glEnd() {
/*  667 */     GL11.glEnd();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glDrawArrays(int p_glDrawArrays_0_, int p_glDrawArrays_1_, int p_glDrawArrays_2_) {
/*  672 */     GL11.glDrawArrays(p_glDrawArrays_0_, p_glDrawArrays_1_, p_glDrawArrays_2_);
/*      */     
/*  674 */     if (Config.isShaders() && !creatingDisplayList) {
/*      */       
/*  676 */       int i = Shaders.activeProgram.getCountInstances();
/*      */       
/*  678 */       if (i > 1) {
/*      */         
/*  680 */         for (int j = 1; j < i; j++) {
/*      */           
/*  682 */           Shaders.uniform_instanceId.setValue(j);
/*  683 */           GL11.glDrawArrays(p_glDrawArrays_0_, p_glDrawArrays_1_, p_glDrawArrays_2_);
/*      */         } 
/*      */         
/*  686 */         Shaders.uniform_instanceId.setValue(0);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void callList(int list) {
/*  693 */     GL11.glCallList(list);
/*      */     
/*  695 */     if (Config.isShaders() && !creatingDisplayList) {
/*      */       
/*  697 */       int i = Shaders.activeProgram.getCountInstances();
/*      */       
/*  699 */       if (i > 1) {
/*      */         
/*  701 */         for (int j = 1; j < i; j++) {
/*      */           
/*  703 */           Shaders.uniform_instanceId.setValue(j);
/*  704 */           GL11.glCallList(list);
/*      */         } 
/*      */         
/*  707 */         Shaders.uniform_instanceId.setValue(0);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void callLists(IntBuffer p_callLists_0_) {
/*  714 */     GL11.glCallLists(p_callLists_0_);
/*      */     
/*  716 */     if (Config.isShaders() && !creatingDisplayList) {
/*      */       
/*  718 */       int i = Shaders.activeProgram.getCountInstances();
/*      */       
/*  720 */       if (i > 1) {
/*      */         
/*  722 */         for (int j = 1; j < i; j++) {
/*      */           
/*  724 */           Shaders.uniform_instanceId.setValue(j);
/*  725 */           GL11.glCallLists(p_callLists_0_);
/*      */         } 
/*      */         
/*  728 */         Shaders.uniform_instanceId.setValue(0);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glDeleteLists(int p_glDeleteLists_0_, int p_glDeleteLists_1_) {
/*  735 */     GL11.glDeleteLists(p_glDeleteLists_0_, p_glDeleteLists_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glNewList(int p_glNewList_0_, int p_glNewList_1_) {
/*  740 */     GL11.glNewList(p_glNewList_0_, p_glNewList_1_);
/*  741 */     creatingDisplayList = true;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glEndList() {
/*  746 */     GL11.glEndList();
/*  747 */     creatingDisplayList = false;
/*      */   }
/*      */ 
/*      */   
/*      */   public static int glGetError() {
/*  752 */     return GL11.glGetError();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glTexImage2D(int p_glTexImage2D_0_, int p_glTexImage2D_1_, int p_glTexImage2D_2_, int p_glTexImage2D_3_, int p_glTexImage2D_4_, int p_glTexImage2D_5_, int p_glTexImage2D_6_, int p_glTexImage2D_7_, IntBuffer p_glTexImage2D_8_) {
/*  757 */     GL11.glTexImage2D(p_glTexImage2D_0_, p_glTexImage2D_1_, p_glTexImage2D_2_, p_glTexImage2D_3_, p_glTexImage2D_4_, p_glTexImage2D_5_, p_glTexImage2D_6_, p_glTexImage2D_7_, p_glTexImage2D_8_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glTexSubImage2D(int p_glTexSubImage2D_0_, int p_glTexSubImage2D_1_, int p_glTexSubImage2D_2_, int p_glTexSubImage2D_3_, int p_glTexSubImage2D_4_, int p_glTexSubImage2D_5_, int p_glTexSubImage2D_6_, int p_glTexSubImage2D_7_, IntBuffer p_glTexSubImage2D_8_) {
/*  762 */     GL11.glTexSubImage2D(p_glTexSubImage2D_0_, p_glTexSubImage2D_1_, p_glTexSubImage2D_2_, p_glTexSubImage2D_3_, p_glTexSubImage2D_4_, p_glTexSubImage2D_5_, p_glTexSubImage2D_6_, p_glTexSubImage2D_7_, p_glTexSubImage2D_8_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glCopyTexSubImage2D(int p_glCopyTexSubImage2D_0_, int p_glCopyTexSubImage2D_1_, int p_glCopyTexSubImage2D_2_, int p_glCopyTexSubImage2D_3_, int p_glCopyTexSubImage2D_4_, int p_glCopyTexSubImage2D_5_, int p_glCopyTexSubImage2D_6_, int p_glCopyTexSubImage2D_7_) {
/*  767 */     GL11.glCopyTexSubImage2D(p_glCopyTexSubImage2D_0_, p_glCopyTexSubImage2D_1_, p_glCopyTexSubImage2D_2_, p_glCopyTexSubImage2D_3_, p_glCopyTexSubImage2D_4_, p_glCopyTexSubImage2D_5_, p_glCopyTexSubImage2D_6_, p_glCopyTexSubImage2D_7_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glGetTexImage(int p_glGetTexImage_0_, int p_glGetTexImage_1_, int p_glGetTexImage_2_, int p_glGetTexImage_3_, IntBuffer p_glGetTexImage_4_) {
/*  772 */     GL11.glGetTexImage(p_glGetTexImage_0_, p_glGetTexImage_1_, p_glGetTexImage_2_, p_glGetTexImage_3_, p_glGetTexImage_4_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glTexParameterf(int p_glTexParameterf_0_, int p_glTexParameterf_1_, float p_glTexParameterf_2_) {
/*  777 */     GL11.glTexParameterf(p_glTexParameterf_0_, p_glTexParameterf_1_, p_glTexParameterf_2_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glTexParameteri(int p_glTexParameteri_0_, int p_glTexParameteri_1_, int p_glTexParameteri_2_) {
/*  782 */     GL11.glTexParameteri(p_glTexParameteri_0_, p_glTexParameteri_1_, p_glTexParameteri_2_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static int glGetTexLevelParameteri(int p_glGetTexLevelParameteri_0_, int p_glGetTexLevelParameteri_1_, int p_glGetTexLevelParameteri_2_) {
/*  787 */     return GL11.glGetTexLevelParameteri(p_glGetTexLevelParameteri_0_, p_glGetTexLevelParameteri_1_, p_glGetTexLevelParameteri_2_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static int getActiveTextureUnit() {
/*  792 */     return OpenGlHelper.defaultTexUnit + activeTextureUnit;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void bindCurrentTexture() {
/*  797 */     GL11.glBindTexture(3553, (textureState[activeTextureUnit]).textureName);
/*      */   }
/*      */ 
/*      */   
/*      */   public static int getBoundTexture() {
/*  802 */     return (textureState[activeTextureUnit]).textureName;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void deleteTextures(IntBuffer p_deleteTextures_0_) {
/*  807 */     p_deleteTextures_0_.rewind();
/*      */     
/*  809 */     while (p_deleteTextures_0_.position() < p_deleteTextures_0_.limit()) {
/*      */       
/*  811 */       int i = p_deleteTextures_0_.get();
/*  812 */       deleteTexture(i);
/*      */     } 
/*      */     
/*  815 */     p_deleteTextures_0_.rewind();
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isFogEnabled() {
/*  820 */     return fogState.fog.currentState;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setFogEnabled(boolean p_setFogEnabled_0_) {
/*  825 */     fogState.fog.setState(p_setFogEnabled_0_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void lockAlpha(GlAlphaState p_lockAlpha_0_) {
/*  830 */     if (!alphaLock.isLocked()) {
/*      */       
/*  832 */       getAlphaState(alphaLockState);
/*  833 */       setAlphaState(p_lockAlpha_0_);
/*  834 */       alphaLock.lock();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void unlockAlpha() {
/*  840 */     if (alphaLock.unlock())
/*      */     {
/*  842 */       setAlphaState(alphaLockState);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static void getAlphaState(GlAlphaState p_getAlphaState_0_) {
/*  848 */     if (alphaLock.isLocked()) {
/*      */       
/*  850 */       p_getAlphaState_0_.setState(alphaLockState);
/*      */     }
/*      */     else {
/*      */       
/*  854 */       p_getAlphaState_0_.setState(alphaState.alphaTest.currentState, alphaState.func, alphaState.ref);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setAlphaState(GlAlphaState p_setAlphaState_0_) {
/*  860 */     if (alphaLock.isLocked()) {
/*      */       
/*  862 */       alphaLockState.setState(p_setAlphaState_0_);
/*      */     }
/*      */     else {
/*      */       
/*  866 */       alphaState.alphaTest.setState(p_setAlphaState_0_.isEnabled());
/*  867 */       alphaFunc(p_setAlphaState_0_.getFunc(), p_setAlphaState_0_.getRef());
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void lockBlend(GlBlendState p_lockBlend_0_) {
/*  873 */     if (!blendLock.isLocked()) {
/*      */       
/*  875 */       getBlendState(blendLockState);
/*  876 */       setBlendState(p_lockBlend_0_);
/*  877 */       blendLock.lock();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void unlockBlend() {
/*  883 */     if (blendLock.unlock())
/*      */     {
/*  885 */       setBlendState(blendLockState);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static void getBlendState(GlBlendState p_getBlendState_0_) {
/*  891 */     if (blendLock.isLocked()) {
/*      */       
/*  893 */       p_getBlendState_0_.setState(blendLockState);
/*      */     }
/*      */     else {
/*      */       
/*  897 */       p_getBlendState_0_.setState(blendState.blend.currentState, blendState.srcFactor, blendState.dstFactor, blendState.srcFactorAlpha, blendState.dstFactorAlpha);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setBlendState(GlBlendState p_setBlendState_0_) {
/*  903 */     if (blendLock.isLocked()) {
/*      */       
/*  905 */       blendLockState.setState(p_setBlendState_0_);
/*      */     }
/*      */     else {
/*      */       
/*  909 */       blendState.blend.setState(p_setBlendState_0_.isEnabled());
/*      */       
/*  911 */       if (!p_setBlendState_0_.isSeparate()) {
/*      */         
/*  913 */         blendFunc(p_setBlendState_0_.getSrcFactor(), p_setBlendState_0_.getDstFactor());
/*      */       }
/*      */       else {
/*      */         
/*  917 */         tryBlendFuncSeparate(p_setBlendState_0_.getSrcFactor(), p_setBlendState_0_.getDstFactor(), p_setBlendState_0_.getSrcFactorAlpha(), p_setBlendState_0_.getDstFactorAlpha());
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glMultiDrawArrays(int p_glMultiDrawArrays_0_, IntBuffer p_glMultiDrawArrays_1_, IntBuffer p_glMultiDrawArrays_2_) {
/*  924 */     GL14.glMultiDrawArrays(p_glMultiDrawArrays_0_, p_glMultiDrawArrays_1_, p_glMultiDrawArrays_2_);
/*      */     
/*  926 */     if (Config.isShaders() && !creatingDisplayList) {
/*      */       
/*  928 */       int i = Shaders.activeProgram.getCountInstances();
/*      */       
/*  930 */       if (i > 1) {
/*      */         
/*  932 */         for (int j = 1; j < i; j++) {
/*      */           
/*  934 */           Shaders.uniform_instanceId.setValue(j);
/*  935 */           GL14.glMultiDrawArrays(p_glMultiDrawArrays_0_, p_glMultiDrawArrays_1_, p_glMultiDrawArrays_2_);
/*      */         } 
/*      */         
/*  938 */         Shaders.uniform_instanceId.setValue(0);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   static {
/*  945 */     for (int i = 0; i < 8; i++)
/*      */     {
/*  947 */       lightState[i] = new BooleanState(16384 + i);
/*      */     }
/*      */     
/*  950 */     for (int j = 0; j < textureState.length; j++)
/*      */     {
/*  952 */       textureState[j] = new TextureState();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static void resetTexture2D() {
/*  958 */     (textureState[activeTextureUnit]).textureName = -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class AlphaState
/*      */   {
/*  969 */     public GlStateManager.BooleanState alphaTest = new GlStateManager.BooleanState(3008);
/*  970 */     public int func = 519;
/*  971 */     public float ref = -1.0F;
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
/*      */   static class BlendState
/*      */   {
/*  985 */     public GlStateManager.BooleanState blend = new GlStateManager.BooleanState(3042);
/*  986 */     public int srcFactor = 1;
/*  987 */     public int dstFactor = 0;
/*  988 */     public int srcFactorAlpha = 1;
/*  989 */     public int dstFactorAlpha = 0;
/*      */   }
/*      */ 
/*      */   
/*      */   static class BooleanState
/*      */   {
/*      */     private final int capability;
/*      */     
/*      */     boolean currentState = false;
/*      */     
/*      */     public BooleanState(int capabilityIn) {
/* 1000 */       this.capability = capabilityIn;
/*      */     }
/*      */ 
/*      */     
/*      */     public void setDisabled() {
/* 1005 */       setState(false);
/*      */     }
/*      */ 
/*      */     
/*      */     public void setEnabled() {
/* 1010 */       setState(true);
/*      */     }
/*      */ 
/*      */     
/*      */     public void setState(boolean state) {
/* 1015 */       if (state != this.currentState) {
/*      */         
/* 1017 */         this.currentState = state;
/*      */         
/* 1019 */         if (state) {
/*      */           
/* 1021 */           GL11.glEnable(this.capability);
/*      */         }
/*      */         else {
/*      */           
/* 1025 */           GL11.glDisable(this.capability);
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class ClearState
/*      */   {
/* 1039 */     public double depth = 1.0D;
/* 1040 */     public GlStateManager.Color color = new GlStateManager.Color(0.0F, 0.0F, 0.0F, 0.0F);
/* 1041 */     public int field_179204_c = 0;
/*      */   }
/*      */ 
/*      */   
/*      */   static class Color
/*      */   {
/* 1047 */     public float red = 1.0F;
/* 1048 */     public float green = 1.0F;
/* 1049 */     public float blue = 1.0F;
/* 1050 */     public float alpha = 1.0F;
/*      */ 
/*      */ 
/*      */     
/*      */     public Color() {}
/*      */ 
/*      */     
/*      */     public Color(float redIn, float greenIn, float blueIn, float alphaIn) {
/* 1058 */       this.red = redIn;
/* 1059 */       this.green = greenIn;
/* 1060 */       this.blue = blueIn;
/* 1061 */       this.alpha = alphaIn;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class ColorLogicState
/*      */   {
/* 1072 */     public GlStateManager.BooleanState colorLogicOp = new GlStateManager.BooleanState(3058);
/* 1073 */     public int opcode = 5379;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class ColorMask
/*      */   {
/*      */     public boolean red = true;
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean green = true;
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean blue = true;
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean alpha = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class ColorMaterialState
/*      */   {
/* 1101 */     public GlStateManager.BooleanState colorMaterial = new GlStateManager.BooleanState(2903);
/* 1102 */     public int face = 1032;
/* 1103 */     public int mode = 5634;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class CullState
/*      */   {
/* 1114 */     public GlStateManager.BooleanState cullFace = new GlStateManager.BooleanState(2884);
/* 1115 */     public int mode = 1029;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class DepthState
/*      */   {
/* 1127 */     public GlStateManager.BooleanState depthTest = new GlStateManager.BooleanState(2929);
/*      */     public boolean maskEnabled = true;
/* 1129 */     public int depthFunc = 513;
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
/*      */   static class FogState
/*      */   {
/* 1143 */     public GlStateManager.BooleanState fog = new GlStateManager.BooleanState(2912);
/* 1144 */     public int mode = 2048;
/* 1145 */     public float density = 1.0F;
/* 1146 */     public float start = 0.0F;
/* 1147 */     public float end = 1.0F;
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
/*      */   static class PolygonOffsetState
/*      */   {
/* 1160 */     public GlStateManager.BooleanState polygonOffsetFill = new GlStateManager.BooleanState(32823);
/* 1161 */     public GlStateManager.BooleanState polygonOffsetLine = new GlStateManager.BooleanState(10754);
/* 1162 */     public float factor = 0.0F;
/* 1163 */     public float units = 0.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class StencilFunc
/*      */   {
/* 1175 */     public int field_179081_a = 519;
/* 1176 */     public int field_179079_b = 0;
/* 1177 */     public int field_179080_c = -1;
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
/*      */   static class StencilState
/*      */   {
/* 1191 */     public GlStateManager.StencilFunc field_179078_a = new GlStateManager.StencilFunc();
/* 1192 */     public int field_179076_b = -1;
/* 1193 */     public int field_179077_c = 7680;
/* 1194 */     public int field_179074_d = 7680;
/* 1195 */     public int field_179075_e = 7680;
/*      */   }
/*      */ 
/*      */   
/*      */   public enum TexGen
/*      */   {
/* 1201 */     S,
/* 1202 */     T,
/* 1203 */     R,
/* 1204 */     Q;
/*      */   }
/*      */   
/*      */   static class TexGenCoord
/*      */   {
/*      */     public GlStateManager.BooleanState textureGen;
/*      */     public int coord;
/* 1211 */     public int param = -1;
/*      */ 
/*      */     
/*      */     public TexGenCoord(int p_i46254_1_, int p_i46254_2_) {
/* 1215 */       this.coord = p_i46254_1_;
/* 1216 */       this.textureGen = new GlStateManager.BooleanState(p_i46254_2_);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class TexGenState
/*      */   {
/* 1229 */     public GlStateManager.TexGenCoord s = new GlStateManager.TexGenCoord(8192, 3168);
/* 1230 */     public GlStateManager.TexGenCoord t = new GlStateManager.TexGenCoord(8193, 3169);
/* 1231 */     public GlStateManager.TexGenCoord r = new GlStateManager.TexGenCoord(8194, 3170);
/* 1232 */     public GlStateManager.TexGenCoord q = new GlStateManager.TexGenCoord(8195, 3171);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class TextureState
/*      */   {
/* 1243 */     public GlStateManager.BooleanState texture2DState = new GlStateManager.BooleanState(3553);
/* 1244 */     public int textureName = 0;
/*      */   }
/*      */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\GlStateManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */