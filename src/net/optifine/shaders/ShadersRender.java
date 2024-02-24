/*     */ package net.optifine.shaders;
/*     */ import java.nio.IntBuffer;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.EntityRenderer;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.ItemRenderer;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.RenderGlobal;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.culling.ClippingHelper;
/*     */ import net.minecraft.client.renderer.culling.Frustum;
/*     */ import net.minecraft.client.renderer.culling.ICamera;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.optifine.reflect.Reflector;
/*     */ import org.lwjgl.opengl.EXTFramebufferObject;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.lwjgl.opengl.GL20;
/*     */ import org.lwjgl.opengl.GL30;
/*     */ 
/*     */ public class ShadersRender {
/*  25 */   private static final ResourceLocation END_PORTAL_TEXTURE = new ResourceLocation("textures/entity/end_portal.png");
/*     */ 
/*     */   
/*     */   public static void setFrustrumPosition(ICamera frustum, double x, double y, double z) {
/*  29 */     frustum.setPosition(x, y, z);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void setupTerrain(RenderGlobal renderGlobal, Entity viewEntity, double partialTicks, ICamera camera, int frameCount, boolean playerSpectator) {
/*  34 */     renderGlobal.setupTerrain(viewEntity, partialTicks, camera, frameCount, playerSpectator);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void beginTerrainSolid() {
/*  39 */     if (Shaders.isRenderingWorld) {
/*     */       
/*  41 */       Shaders.fogEnabled = true;
/*  42 */       Shaders.useProgram(Shaders.ProgramTerrain);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void beginTerrainCutoutMipped() {
/*  48 */     if (Shaders.isRenderingWorld)
/*     */     {
/*  50 */       Shaders.useProgram(Shaders.ProgramTerrain);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static void beginTerrainCutout() {
/*  56 */     if (Shaders.isRenderingWorld)
/*     */     {
/*  58 */       Shaders.useProgram(Shaders.ProgramTerrain);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static void endTerrain() {
/*  64 */     if (Shaders.isRenderingWorld)
/*     */     {
/*  66 */       Shaders.useProgram(Shaders.ProgramTexturedLit);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static void beginTranslucent() {
/*  72 */     if (Shaders.isRenderingWorld) {
/*     */       
/*  74 */       if (Shaders.usedDepthBuffers >= 2) {
/*     */         
/*  76 */         GlStateManager.setActiveTexture(33995);
/*  77 */         Shaders.checkGLError("pre copy depth");
/*  78 */         GL11.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, Shaders.renderWidth, Shaders.renderHeight);
/*  79 */         Shaders.checkGLError("copy depth");
/*  80 */         GlStateManager.setActiveTexture(33984);
/*     */       } 
/*     */       
/*  83 */       Shaders.useProgram(Shaders.ProgramWater);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void endTranslucent() {
/*  89 */     if (Shaders.isRenderingWorld)
/*     */     {
/*  91 */       Shaders.useProgram(Shaders.ProgramTexturedLit);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static void renderHand0(EntityRenderer er, float par1, int par2) {
/*  97 */     if (!Shaders.isShadowPass) {
/*     */       
/*  99 */       boolean flag = Shaders.isItemToRenderMainTranslucent();
/* 100 */       boolean flag1 = Shaders.isItemToRenderOffTranslucent();
/*     */       
/* 102 */       if (!flag || !flag1) {
/*     */         
/* 104 */         Shaders.readCenterDepth();
/* 105 */         Shaders.beginHand(false);
/* 106 */         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 107 */         Shaders.setSkipRenderHands(flag, flag1);
/* 108 */         er.renderHand(par1, par2, true, false, false);
/* 109 */         Shaders.endHand();
/* 110 */         Shaders.setHandsRendered(!flag, !flag1);
/* 111 */         Shaders.setSkipRenderHands(false, false);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void renderHand1(EntityRenderer er, float par1, int par2) {
/* 118 */     if (!Shaders.isShadowPass && !Shaders.isBothHandsRendered()) {
/*     */       
/* 120 */       Shaders.readCenterDepth();
/* 121 */       GlStateManager.enableBlend();
/* 122 */       Shaders.beginHand(true);
/* 123 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 124 */       Shaders.setSkipRenderHands(Shaders.isHandRenderedMain(), Shaders.isHandRenderedOff());
/* 125 */       er.renderHand(par1, par2, true, false, true);
/* 126 */       Shaders.endHand();
/* 127 */       Shaders.setHandsRendered(true, true);
/* 128 */       Shaders.setSkipRenderHands(false, false);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void renderItemFP(ItemRenderer itemRenderer, float par1, boolean renderTranslucent) {
/* 134 */     Shaders.setRenderingFirstPersonHand(true);
/* 135 */     GlStateManager.depthMask(true);
/*     */     
/* 137 */     if (renderTranslucent) {
/*     */       
/* 139 */       GlStateManager.depthFunc(519);
/* 140 */       GL11.glPushMatrix();
/* 141 */       IntBuffer intbuffer = Shaders.activeDrawBuffers;
/* 142 */       Shaders.setDrawBuffers(Shaders.drawBuffersNone);
/* 143 */       Shaders.renderItemKeepDepthMask = true;
/* 144 */       itemRenderer.renderItemInFirstPerson(par1);
/* 145 */       Shaders.renderItemKeepDepthMask = false;
/* 146 */       Shaders.setDrawBuffers(intbuffer);
/* 147 */       GL11.glPopMatrix();
/*     */     } 
/*     */     
/* 150 */     GlStateManager.depthFunc(515);
/* 151 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 152 */     itemRenderer.renderItemInFirstPerson(par1);
/* 153 */     Shaders.setRenderingFirstPersonHand(false);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void renderFPOverlay(EntityRenderer er, float par1, int par2) {
/* 158 */     if (!Shaders.isShadowPass) {
/*     */       
/* 160 */       Shaders.beginFPOverlay();
/* 161 */       er.renderHand(par1, par2, false, true, false);
/* 162 */       Shaders.endFPOverlay();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void beginBlockDamage() {
/* 168 */     if (Shaders.isRenderingWorld) {
/*     */       
/* 170 */       Shaders.useProgram(Shaders.ProgramDamagedBlock);
/*     */       
/* 172 */       if (Shaders.ProgramDamagedBlock.getId() == Shaders.ProgramTerrain.getId()) {
/*     */         
/* 174 */         Shaders.setDrawBuffers(Shaders.drawBuffersColorAtt0);
/* 175 */         GlStateManager.depthMask(false);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void endBlockDamage() {
/* 182 */     if (Shaders.isRenderingWorld) {
/*     */       
/* 184 */       GlStateManager.depthMask(true);
/* 185 */       Shaders.useProgram(Shaders.ProgramTexturedLit);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void renderShadowMap(EntityRenderer entityRenderer, int pass, float partialTicks, long finishTimeNano) {
/* 191 */     if (Shaders.usedShadowDepthBuffers > 0 && --Shaders.shadowPassCounter <= 0) {
/*     */       
/* 193 */       Minecraft minecraft = Minecraft.getMinecraft();
/* 194 */       minecraft.mcProfiler.endStartSection("shadow pass");
/* 195 */       RenderGlobal renderglobal = minecraft.renderGlobal;
/* 196 */       Shaders.isShadowPass = true;
/* 197 */       Shaders.shadowPassCounter = Shaders.shadowPassInterval;
/* 198 */       Shaders.preShadowPassThirdPersonView = minecraft.gameSettings.thirdPersonView;
/* 199 */       minecraft.gameSettings.thirdPersonView = 1;
/* 200 */       Shaders.checkGLError("pre shadow");
/* 201 */       GL11.glMatrixMode(5889);
/* 202 */       GL11.glPushMatrix();
/* 203 */       GL11.glMatrixMode(5888);
/* 204 */       GL11.glPushMatrix();
/* 205 */       minecraft.mcProfiler.endStartSection("shadow clear");
/* 206 */       EXTFramebufferObject.glBindFramebufferEXT(36160, Shaders.sfb);
/* 207 */       Shaders.checkGLError("shadow bind sfb");
/* 208 */       minecraft.mcProfiler.endStartSection("shadow camera");
/* 209 */       entityRenderer.setupCameraTransform(partialTicks, 2);
/* 210 */       Shaders.setCameraShadow(partialTicks);
/* 211 */       Shaders.checkGLError("shadow camera");
/* 212 */       Shaders.useProgram(Shaders.ProgramShadow);
/* 213 */       GL20.glDrawBuffers(Shaders.sfbDrawBuffers);
/* 214 */       Shaders.checkGLError("shadow drawbuffers");
/* 215 */       GL11.glReadBuffer(0);
/* 216 */       Shaders.checkGLError("shadow readbuffer");
/* 217 */       EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36096, 3553, Shaders.sfbDepthTextures.get(0), 0);
/*     */       
/* 219 */       if (Shaders.usedShadowColorBuffers != 0)
/*     */       {
/* 221 */         EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36064, 3553, Shaders.sfbColorTextures.get(0), 0);
/*     */       }
/*     */       
/* 224 */       Shaders.checkFramebufferStatus("shadow fb");
/* 225 */       GL11.glClearColor(1.0F, 1.0F, 1.0F, 1.0F);
/* 226 */       GL11.glClear((Shaders.usedShadowColorBuffers != 0) ? 16640 : 256);
/* 227 */       Shaders.checkGLError("shadow clear");
/* 228 */       minecraft.mcProfiler.endStartSection("shadow frustum");
/* 229 */       ClippingHelper clippinghelper = ClippingHelperShadow.getInstance();
/* 230 */       minecraft.mcProfiler.endStartSection("shadow culling");
/* 231 */       Frustum frustum = new Frustum(clippinghelper);
/* 232 */       Entity entity = minecraft.getRenderViewEntity();
/* 233 */       double d0 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks;
/* 234 */       double d1 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks;
/* 235 */       double d2 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks;
/* 236 */       frustum.setPosition(d0, d1, d2);
/* 237 */       GlStateManager.shadeModel(7425);
/* 238 */       GlStateManager.enableDepth();
/* 239 */       GlStateManager.depthFunc(515);
/* 240 */       GlStateManager.depthMask(true);
/* 241 */       GlStateManager.colorMask(true, true, true, true);
/* 242 */       GlStateManager.disableCull();
/* 243 */       minecraft.mcProfiler.endStartSection("shadow prepareterrain");
/* 244 */       minecraft.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
/* 245 */       minecraft.mcProfiler.endStartSection("shadow setupterrain");
/* 246 */       int i = 0;
/* 247 */       i = entityRenderer.frameCount;
/* 248 */       entityRenderer.frameCount = i + 1;
/* 249 */       renderglobal.setupTerrain(entity, partialTicks, (ICamera)frustum, i, minecraft.thePlayer.isSpectator());
/* 250 */       minecraft.mcProfiler.endStartSection("shadow updatechunks");
/* 251 */       minecraft.mcProfiler.endStartSection("shadow terrain");
/* 252 */       GlStateManager.matrixMode(5888);
/* 253 */       GlStateManager.pushMatrix();
/* 254 */       GlStateManager.disableAlpha();
/* 255 */       renderglobal.renderBlockLayer(EnumWorldBlockLayer.SOLID, partialTicks, 2, entity);
/* 256 */       Shaders.checkGLError("shadow terrain solid");
/* 257 */       GlStateManager.enableAlpha();
/* 258 */       renderglobal.renderBlockLayer(EnumWorldBlockLayer.CUTOUT_MIPPED, partialTicks, 2, entity);
/* 259 */       Shaders.checkGLError("shadow terrain cutoutmipped");
/* 260 */       minecraft.getTextureManager().getTexture(TextureMap.locationBlocksTexture).setBlurMipmap(false, false);
/* 261 */       renderglobal.renderBlockLayer(EnumWorldBlockLayer.CUTOUT, partialTicks, 2, entity);
/* 262 */       Shaders.checkGLError("shadow terrain cutout");
/* 263 */       minecraft.getTextureManager().getTexture(TextureMap.locationBlocksTexture).restoreLastBlurMipmap();
/* 264 */       GlStateManager.shadeModel(7424);
/* 265 */       GlStateManager.alphaFunc(516, 0.1F);
/* 266 */       GlStateManager.matrixMode(5888);
/* 267 */       GlStateManager.popMatrix();
/* 268 */       GlStateManager.pushMatrix();
/* 269 */       minecraft.mcProfiler.endStartSection("shadow entities");
/*     */       
/* 271 */       if (Reflector.ForgeHooksClient_setRenderPass.exists())
/*     */       {
/* 273 */         Reflector.callVoid(Reflector.ForgeHooksClient_setRenderPass, new Object[] { Integer.valueOf(0) });
/*     */       }
/*     */       
/* 276 */       renderglobal.renderEntities(entity, (ICamera)frustum, partialTicks);
/* 277 */       Shaders.checkGLError("shadow entities");
/* 278 */       GlStateManager.matrixMode(5888);
/* 279 */       GlStateManager.popMatrix();
/* 280 */       GlStateManager.depthMask(true);
/* 281 */       GlStateManager.disableBlend();
/* 282 */       GlStateManager.enableCull();
/* 283 */       GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 284 */       GlStateManager.alphaFunc(516, 0.1F);
/*     */       
/* 286 */       if (Shaders.usedShadowDepthBuffers >= 2) {
/*     */         
/* 288 */         GlStateManager.setActiveTexture(33989);
/* 289 */         Shaders.checkGLError("pre copy shadow depth");
/* 290 */         GL11.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, Shaders.shadowMapWidth, Shaders.shadowMapHeight);
/* 291 */         Shaders.checkGLError("copy shadow depth");
/* 292 */         GlStateManager.setActiveTexture(33984);
/*     */       } 
/*     */       
/* 295 */       GlStateManager.disableBlend();
/* 296 */       GlStateManager.depthMask(true);
/* 297 */       minecraft.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
/* 298 */       GlStateManager.shadeModel(7425);
/* 299 */       Shaders.checkGLError("shadow pre-translucent");
/* 300 */       GL20.glDrawBuffers(Shaders.sfbDrawBuffers);
/* 301 */       Shaders.checkGLError("shadow drawbuffers pre-translucent");
/* 302 */       Shaders.checkFramebufferStatus("shadow pre-translucent");
/*     */       
/* 304 */       if (Shaders.isRenderShadowTranslucent()) {
/*     */         
/* 306 */         minecraft.mcProfiler.endStartSection("shadow translucent");
/* 307 */         renderglobal.renderBlockLayer(EnumWorldBlockLayer.TRANSLUCENT, partialTicks, 2, entity);
/* 308 */         Shaders.checkGLError("shadow translucent");
/*     */       } 
/*     */       
/* 311 */       if (Reflector.ForgeHooksClient_setRenderPass.exists()) {
/*     */         
/* 313 */         RenderHelper.enableStandardItemLighting();
/* 314 */         Reflector.call(Reflector.ForgeHooksClient_setRenderPass, new Object[] { Integer.valueOf(1) });
/* 315 */         renderglobal.renderEntities(entity, (ICamera)frustum, partialTicks);
/* 316 */         Reflector.call(Reflector.ForgeHooksClient_setRenderPass, new Object[] { Integer.valueOf(-1) });
/* 317 */         RenderHelper.disableStandardItemLighting();
/* 318 */         Shaders.checkGLError("shadow entities 1");
/*     */       } 
/*     */       
/* 321 */       GlStateManager.shadeModel(7424);
/* 322 */       GlStateManager.depthMask(true);
/* 323 */       GlStateManager.enableCull();
/* 324 */       GlStateManager.disableBlend();
/* 325 */       GL11.glFlush();
/* 326 */       Shaders.checkGLError("shadow flush");
/* 327 */       Shaders.isShadowPass = false;
/* 328 */       minecraft.gameSettings.thirdPersonView = Shaders.preShadowPassThirdPersonView;
/* 329 */       minecraft.mcProfiler.endStartSection("shadow postprocess");
/*     */       
/* 331 */       if (Shaders.hasGlGenMipmap) {
/*     */         
/* 333 */         if (Shaders.usedShadowDepthBuffers >= 1) {
/*     */           
/* 335 */           if (Shaders.shadowMipmapEnabled[0]) {
/*     */             
/* 337 */             GlStateManager.setActiveTexture(33988);
/* 338 */             GlStateManager.bindTexture(Shaders.sfbDepthTextures.get(0));
/* 339 */             GL30.glGenerateMipmap(3553);
/* 340 */             GL11.glTexParameteri(3553, 10241, Shaders.shadowFilterNearest[0] ? 9984 : 9987);
/*     */           } 
/*     */           
/* 343 */           if (Shaders.usedShadowDepthBuffers >= 2 && Shaders.shadowMipmapEnabled[1]) {
/*     */             
/* 345 */             GlStateManager.setActiveTexture(33989);
/* 346 */             GlStateManager.bindTexture(Shaders.sfbDepthTextures.get(1));
/* 347 */             GL30.glGenerateMipmap(3553);
/* 348 */             GL11.glTexParameteri(3553, 10241, Shaders.shadowFilterNearest[1] ? 9984 : 9987);
/*     */           } 
/*     */           
/* 351 */           GlStateManager.setActiveTexture(33984);
/*     */         } 
/*     */         
/* 354 */         if (Shaders.usedShadowColorBuffers >= 1) {
/*     */           
/* 356 */           if (Shaders.shadowColorMipmapEnabled[0]) {
/*     */             
/* 358 */             GlStateManager.setActiveTexture(33997);
/* 359 */             GlStateManager.bindTexture(Shaders.sfbColorTextures.get(0));
/* 360 */             GL30.glGenerateMipmap(3553);
/* 361 */             GL11.glTexParameteri(3553, 10241, Shaders.shadowColorFilterNearest[0] ? 9984 : 9987);
/*     */           } 
/*     */           
/* 364 */           if (Shaders.usedShadowColorBuffers >= 2 && Shaders.shadowColorMipmapEnabled[1]) {
/*     */             
/* 366 */             GlStateManager.setActiveTexture(33998);
/* 367 */             GlStateManager.bindTexture(Shaders.sfbColorTextures.get(1));
/* 368 */             GL30.glGenerateMipmap(3553);
/* 369 */             GL11.glTexParameteri(3553, 10241, Shaders.shadowColorFilterNearest[1] ? 9984 : 9987);
/*     */           } 
/*     */           
/* 372 */           GlStateManager.setActiveTexture(33984);
/*     */         } 
/*     */       } 
/*     */       
/* 376 */       Shaders.checkGLError("shadow postprocess");
/* 377 */       EXTFramebufferObject.glBindFramebufferEXT(36160, Shaders.dfb);
/* 378 */       GL11.glViewport(0, 0, Shaders.renderWidth, Shaders.renderHeight);
/* 379 */       Shaders.activeDrawBuffers = null;
/* 380 */       minecraft.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
/* 381 */       Shaders.useProgram(Shaders.ProgramTerrain);
/* 382 */       GL11.glMatrixMode(5888);
/* 383 */       GL11.glPopMatrix();
/* 384 */       GL11.glMatrixMode(5889);
/* 385 */       GL11.glPopMatrix();
/* 386 */       GL11.glMatrixMode(5888);
/* 387 */       Shaders.checkGLError("shadow end");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void preRenderChunkLayer(EnumWorldBlockLayer blockLayerIn) {
/* 393 */     if (Shaders.isRenderBackFace(blockLayerIn))
/*     */     {
/* 395 */       GlStateManager.disableCull();
/*     */     }
/*     */     
/* 398 */     if (OpenGlHelper.useVbo()) {
/*     */       
/* 400 */       GL11.glEnableClientState(32885);
/* 401 */       GL20.glEnableVertexAttribArray(Shaders.midTexCoordAttrib);
/* 402 */       GL20.glEnableVertexAttribArray(Shaders.tangentAttrib);
/* 403 */       GL20.glEnableVertexAttribArray(Shaders.entityAttrib);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void postRenderChunkLayer(EnumWorldBlockLayer blockLayerIn) {
/* 409 */     if (OpenGlHelper.useVbo()) {
/*     */       
/* 411 */       GL11.glDisableClientState(32885);
/* 412 */       GL20.glDisableVertexAttribArray(Shaders.midTexCoordAttrib);
/* 413 */       GL20.glDisableVertexAttribArray(Shaders.tangentAttrib);
/* 414 */       GL20.glDisableVertexAttribArray(Shaders.entityAttrib);
/*     */     } 
/*     */     
/* 417 */     if (Shaders.isRenderBackFace(blockLayerIn))
/*     */     {
/* 419 */       GlStateManager.enableCull();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static void setupArrayPointersVbo() {
/* 425 */     int i = 14;
/* 426 */     GL11.glVertexPointer(3, 5126, 56, 0L);
/* 427 */     GL11.glColorPointer(4, 5121, 56, 12L);
/* 428 */     GL11.glTexCoordPointer(2, 5126, 56, 16L);
/* 429 */     OpenGlHelper.setClientActiveTexture(OpenGlHelper.lightmapTexUnit);
/* 430 */     GL11.glTexCoordPointer(2, 5122, 56, 24L);
/* 431 */     OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
/* 432 */     GL11.glNormalPointer(5120, 56, 28L);
/* 433 */     GL20.glVertexAttribPointer(Shaders.midTexCoordAttrib, 2, 5126, false, 56, 32L);
/* 434 */     GL20.glVertexAttribPointer(Shaders.tangentAttrib, 4, 5122, false, 56, 40L);
/* 435 */     GL20.glVertexAttribPointer(Shaders.entityAttrib, 3, 5122, false, 56, 48L);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void beaconBeamBegin() {
/* 440 */     Shaders.useProgram(Shaders.ProgramBeaconBeam);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void beaconBeamStartQuad1() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public static void beaconBeamStartQuad2() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public static void beaconBeamDraw1() {}
/*     */ 
/*     */   
/*     */   public static void beaconBeamDraw2() {
/* 457 */     GlStateManager.disableBlend();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void renderEnchantedGlintBegin() {
/* 462 */     Shaders.useProgram(Shaders.ProgramArmorGlint);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void renderEnchantedGlintEnd() {
/* 467 */     if (Shaders.isRenderingWorld) {
/*     */       
/* 469 */       if (Shaders.isRenderingFirstPersonHand() && Shaders.isRenderBothHands())
/*     */       {
/* 471 */         Shaders.useProgram(Shaders.ProgramHand);
/*     */       }
/*     */       else
/*     */       {
/* 475 */         Shaders.useProgram(Shaders.ProgramEntities);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 480 */       Shaders.useProgram(Shaders.ProgramNone);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean renderEndPortal(TileEntityEndPortal te, double x, double y, double z, float partialTicks, int destroyStage, float offset) {
/* 486 */     if (!Shaders.isShadowPass && Shaders.activeProgram.getId() == 0)
/*     */     {
/* 488 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 492 */     GlStateManager.disableLighting();
/* 493 */     Config.getTextureManager().bindTexture(END_PORTAL_TEXTURE);
/* 494 */     Tessellator tessellator = Tessellator.getInstance();
/* 495 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 496 */     worldrenderer.begin(7, DefaultVertexFormats.BLOCK);
/* 497 */     float f = 0.5F;
/* 498 */     float f1 = f * 0.15F;
/* 499 */     float f2 = f * 0.3F;
/* 500 */     float f3 = f * 0.4F;
/* 501 */     float f4 = 0.0F;
/* 502 */     float f5 = 0.2F;
/* 503 */     float f6 = (float)(System.currentTimeMillis() % 100000L) / 100000.0F;
/* 504 */     int i = 240;
/* 505 */     worldrenderer.pos(x, y + offset, z + 1.0D).color(f1, f2, f3, 1.0F).tex((f4 + f6), (f4 + f6)).lightmap(i, i).endVertex();
/* 506 */     worldrenderer.pos(x + 1.0D, y + offset, z + 1.0D).color(f1, f2, f3, 1.0F).tex((f4 + f6), (f5 + f6)).lightmap(i, i).endVertex();
/* 507 */     worldrenderer.pos(x + 1.0D, y + offset, z).color(f1, f2, f3, 1.0F).tex((f5 + f6), (f5 + f6)).lightmap(i, i).endVertex();
/* 508 */     worldrenderer.pos(x, y + offset, z).color(f1, f2, f3, 1.0F).tex((f5 + f6), (f4 + f6)).lightmap(i, i).endVertex();
/* 509 */     tessellator.draw();
/* 510 */     GlStateManager.enableLighting();
/* 511 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\shaders\ShadersRender.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */