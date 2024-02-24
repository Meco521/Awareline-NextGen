/*     */ package net.minecraft.client.renderer.entity;
/*     */ import awareline.main.event.Event;
/*     */ import awareline.main.event.EventManager;
/*     */ import awareline.main.event.events.misc.EventRenderEntity;
/*     */ import awareline.main.event.events.world.RLEEvent;
/*     */ import awareline.main.event.events.world.renderEvents.EventRotationAnimation;
/*     */ import awareline.main.mod.implement.visual.NameTags;
/*     */ import awareline.main.mod.implement.visual.etype.ESP;
/*     */ import awareline.main.mod.implement.visual.etype.EntityHurtColor;
/*     */ import awareline.main.utility.render.OutlineUtils;
/*     */ import java.nio.FloatBuffer;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.client.renderer.GLAllocation;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.entity.layers.LayerRenderer;
/*     */ import net.minecraft.client.renderer.texture.DynamicTexture;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EnumPlayerModelParts;
/*     */ import net.minecraft.scoreboard.ScorePlayerTeam;
/*     */ import net.minecraft.scoreboard.Team;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.optifine.EmissiveTextures;
/*     */ import net.optifine.entity.model.CustomEntityModels;
/*     */ import net.optifine.reflect.Reflector;
/*     */ import net.optifine.shaders.Shaders;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public abstract class RendererLivingEntity<T extends EntityLivingBase> extends Render<T> {
/*  43 */   private static final Logger logger = LogManager.getLogger();
/*  44 */   private static final DynamicTexture textureBrightness = new DynamicTexture(16, 16);
/*     */   public ModelBase mainModel;
/*  46 */   protected FloatBuffer brightnessBuffer = GLAllocation.createDirectFloatBuffer(4);
/*  47 */   protected List<LayerRenderer<T>> layerRenderers = Lists.newArrayList();
/*     */   protected boolean renderOutlines = false;
/*  49 */   public static float NAME_TAG_RANGE = 64.0F;
/*  50 */   public static float NAME_TAG_RANGE_SNEAK = 32.0F;
/*     */   public EntityLivingBase renderEntity;
/*     */   public float renderLimbSwing;
/*     */   public float renderLimbSwingAmount;
/*     */   public float renderAgeInTicks;
/*     */   public float renderHeadYaw;
/*     */   public float renderHeadPitch;
/*     */   public float renderScaleFactor;
/*     */   public float renderPartialTicks;
/*     */   private boolean renderModelPushMatrix;
/*     */   private boolean renderLayersPushMatrix;
/*  61 */   public static final boolean animateModelLiving = Boolean.getBoolean("animate.model.living");
/*     */   
/*     */   public RendererLivingEntity(RenderManager renderManagerIn, ModelBase modelBaseIn, float shadowSizeIn) {
/*  64 */     super(renderManagerIn);
/*  65 */     this.mainModel = modelBaseIn;
/*  66 */     this.shadowSize = shadowSizeIn;
/*  67 */     this.renderModelPushMatrix = this.mainModel instanceof net.minecraft.client.model.ModelSpider;
/*     */   }
/*     */   
/*     */   public <V extends EntityLivingBase, U extends LayerRenderer<V>> boolean addLayer(U layer) {
/*  71 */     return this.layerRenderers.add((LayerRenderer<T>)layer);
/*     */   }
/*     */   
/*     */   protected <V extends EntityLivingBase, U extends LayerRenderer<V>> boolean removeLayer(U layer) {
/*  75 */     return this.layerRenderers.remove(layer);
/*     */   }
/*     */   
/*     */   public ModelBase getMainModel() {
/*  79 */     return this.mainModel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected float interpolateRotation(float par1, float par2, float par3) {
/*     */     float f;
/*  90 */     for (f = par2 - par1; f < -180.0F; f += 360.0F);
/*     */ 
/*     */ 
/*     */     
/*  94 */     while (f >= 180.0F) {
/*  95 */       f -= 360.0F;
/*     */     }
/*     */     
/*  98 */     return par1 + par3 * f;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void transformHeldFull3DItemLayer() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
/* 109 */     EventRenderEntity eventPre = new EventRenderEntity((Entity)entity, Event.State.PRE, partialTicks, x, y, z);
/* 110 */     EventManager.call((Event)eventPre);
/* 111 */     if (!Reflector.RenderLivingEvent_Pre_Constructor.exists() || !Reflector.postForgeBusEvent(Reflector.RenderLivingEvent_Pre_Constructor, new Object[] { entity, this, Double.valueOf(x), Double.valueOf(y), Double.valueOf(z) })) {
/* 112 */       if (animateModelLiving) {
/* 113 */         ((EntityLivingBase)entity).limbSwingAmount = 1.0F;
/*     */       }
/*     */       
/* 116 */       GlStateManager.pushMatrix();
/* 117 */       GlStateManager.disableCull();
/* 118 */       this.mainModel.swingProgress = getSwingProgress(entity, partialTicks);
/* 119 */       this.mainModel.isRiding = entity.isRiding();
/*     */       
/* 121 */       if (Reflector.ForgeEntity_shouldRiderSit.exists()) {
/* 122 */         this.mainModel.isRiding = (entity.isRiding() && ((EntityLivingBase)entity).ridingEntity != null && Reflector.callBoolean(((EntityLivingBase)entity).ridingEntity, Reflector.ForgeEntity_shouldRiderSit, new Object[0]));
/*     */       }
/*     */       
/* 125 */       this.mainModel.isChild = entity.isChild();
/*     */       
/*     */       try {
/* 128 */         float f = interpolateRotation(((EntityLivingBase)entity).prevRenderYawOffset, ((EntityLivingBase)entity).renderYawOffset, partialTicks);
/* 129 */         float f1 = interpolateRotation(((EntityLivingBase)entity).prevRotationYawHead, ((EntityLivingBase)entity).rotationYawHead, partialTicks);
/* 130 */         float f2 = f1 - f;
/*     */         
/* 132 */         if (this.mainModel.isRiding && ((EntityLivingBase)entity).ridingEntity instanceof EntityLivingBase) {
/* 133 */           EntityLivingBase entitylivingbase = (EntityLivingBase)((EntityLivingBase)entity).ridingEntity;
/* 134 */           f = interpolateRotation(entitylivingbase.prevRenderYawOffset, entitylivingbase.renderYawOffset, partialTicks);
/* 135 */           f2 = f1 - f;
/* 136 */           float f3 = MathHelper.wrapAngleTo180_float(f2);
/*     */           
/* 138 */           if (f3 < -85.0F) {
/* 139 */             f3 = -85.0F;
/*     */           }
/*     */           
/* 142 */           if (f3 >= 85.0F) {
/* 143 */             f3 = 85.0F;
/*     */           }
/*     */           
/* 146 */           f = f1 - f3;
/*     */           
/* 148 */           if (f3 * f3 > 2500.0F) {
/* 149 */             f += f3 * 0.2F;
/*     */           }
/*     */           
/* 152 */           f2 = f1 - f;
/*     */         } 
/*     */ 
/*     */         
/* 156 */         if (entity == (Minecraft.getMinecraft()).thePlayer) {
/* 157 */           f7 = ((EntityLivingBase)entity).prevRotationPitchHead + (((EntityLivingBase)entity).rotationPitchHead - ((EntityLivingBase)entity).prevRotationPitchHead) * partialTicks;
/*     */         } else {
/* 159 */           f7 = ((EntityLivingBase)entity).prevRotationPitch + (((EntityLivingBase)entity).rotationPitch - ((EntityLivingBase)entity).prevRotationPitch) * partialTicks;
/*     */         } 
/* 161 */         EventRotationAnimation event = new EventRotationAnimation((EntityLivingBase)entity, f, f1, f2, f7, partialTicks);
/* 162 */         EventManager.call((Event)event);
/*     */         
/* 164 */         f = event.getRenderYawOffset();
/* 165 */         f1 = event.getRotationYawHead();
/* 166 */         f2 = event.getRenderHeadYaw();
/* 167 */         float f7 = event.getRenderHeadPitch();
/*     */         
/* 169 */         renderLivingAt(entity, x, y, z);
/*     */         
/* 171 */         float f8 = ((EntityLivingBase)entity).prevRotationPitch + (((EntityLivingBase)entity).rotationPitch - ((EntityLivingBase)entity).prevRotationPitch) * partialTicks;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 180 */         rotateCorpse(entity, f8, f, partialTicks);
/* 181 */         GlStateManager.enableRescaleNormal();
/* 182 */         GlStateManager.scale(-1.0F, -1.0F, 1.0F);
/* 183 */         preRenderCallback(entity, partialTicks);
/* 184 */         float f4 = 0.0625F;
/* 185 */         GlStateManager.translate(0.0F, -1.5078125F, 0.0F);
/* 186 */         float f5 = ((EntityLivingBase)entity).prevLimbSwingAmount + (((EntityLivingBase)entity).limbSwingAmount - ((EntityLivingBase)entity).prevLimbSwingAmount) * partialTicks;
/* 187 */         float f6 = ((EntityLivingBase)entity).limbSwing - ((EntityLivingBase)entity).limbSwingAmount * (1.0F - partialTicks);
/* 188 */         if (entity instanceof EntityPlayer) {
/* 189 */           RLEEvent rleEvent = new RLEEvent((EntityLivingBase)entity, f6, f5, f7, f2, f8, f, 0.0625F, partialTicks);
/* 190 */           EventManager.call((Event)rleEvent);
/* 191 */           if (rleEvent.isCancelled()) {
/*     */             return;
/*     */           }
/*     */         } 
/* 195 */         if (entity.isChild()) {
/* 196 */           f6 *= 3.0F;
/*     */         }
/*     */         
/* 199 */         if (f5 > 1.0F) {
/* 200 */           f5 = 1.0F;
/*     */         }
/*     */         
/* 203 */         GlStateManager.enableAlpha();
/* 204 */         this.mainModel.setLivingAnimations((EntityLivingBase)entity, f6, f5, partialTicks);
/* 205 */         this.mainModel.setRotationAngles(f6, f5, f8, f2, f7, 0.0625F, (Entity)entity);
/*     */         
/* 207 */         if (CustomEntityModels.isActive()) {
/* 208 */           this.renderEntity = (EntityLivingBase)entity;
/* 209 */           this.renderLimbSwing = f6;
/* 210 */           this.renderLimbSwingAmount = f5;
/* 211 */           this.renderAgeInTicks = f8;
/* 212 */           this.renderHeadYaw = f2;
/* 213 */           this.renderHeadPitch = f7;
/* 214 */           this.renderScaleFactor = f4;
/* 215 */           this.renderPartialTicks = partialTicks;
/*     */         } 
/* 217 */         if (this.renderOutlines) {
/* 218 */           boolean flag1 = setScoreTeamColor(entity);
/* 219 */           ESP esp = ESP.getInstance;
/* 220 */           if (esp != null && esp.mode.is("Outline") && esp.isEnabled()) {
/* 221 */             GlStateManager.depthMask(true);
/* 222 */             if (!(entity instanceof EntityPlayer) || !((EntityPlayer)entity).isSpectator()) {
/* 223 */               renderLayers(entity, f6, f5, partialTicks, f7, f2, f8, 0.0625F);
/*     */             }
/* 225 */             if (entity instanceof EntityPlayer) {
/* 226 */               if ((Minecraft.getMinecraft()).theWorld != null) {
/* 227 */                 renderModel(entity, f6, f5, f7, f2, f8, 0.0625F);
/* 228 */                 OutlineUtils.renderOne();
/* 229 */                 renderModel(entity, f6, f5, f7, f2, f8, 0.0625F);
/* 230 */                 OutlineUtils.renderTwo();
/* 231 */                 renderModel(entity, f6, f5, f7, f2, f8, 0.0625F);
/* 232 */                 OutlineUtils.renderThree();
/* 233 */                 OutlineUtils.renderFour();
/* 234 */                 renderModel(entity, f6, f5, f7, f2, f8, 0.0625F);
/* 235 */                 OutlineUtils.renderFive();
/*     */               } 
/*     */             } else {
/* 238 */               renderModel(entity, f6, f5, f7, f2, f8, 0.0625F);
/*     */             } 
/*     */           } 
/* 241 */           if (flag1) {
/* 242 */             unsetScoreTeamColor();
/*     */           }
/*     */         } else {
/* 245 */           boolean flag = setDoRenderBrightness(entity, partialTicks);
/*     */           
/* 247 */           if (EmissiveTextures.isActive()) {
/* 248 */             EmissiveTextures.beginRender();
/*     */           }
/*     */           
/* 251 */           if (this.renderModelPushMatrix) {
/* 252 */             GlStateManager.pushMatrix();
/*     */           }
/*     */           
/* 255 */           renderModel(entity, f6, f5, f8, f2, f7, 0.0625F);
/*     */           
/* 257 */           if (this.renderModelPushMatrix) {
/* 258 */             GlStateManager.popMatrix();
/*     */           }
/*     */           
/* 261 */           if (EmissiveTextures.isActive()) {
/* 262 */             if (EmissiveTextures.hasEmissive()) {
/* 263 */               this.renderModelPushMatrix = true;
/* 264 */               EmissiveTextures.beginRenderEmissive();
/* 265 */               GlStateManager.pushMatrix();
/* 266 */               renderModel(entity, f6, f5, f8, f2, f7, f4);
/* 267 */               GlStateManager.popMatrix();
/* 268 */               EmissiveTextures.endRenderEmissive();
/*     */             } 
/*     */             
/* 271 */             EmissiveTextures.endRender();
/*     */           } 
/*     */           
/* 274 */           if (flag) {
/* 275 */             unsetBrightness();
/*     */           }
/*     */           
/* 278 */           GlStateManager.depthMask(true);
/*     */           
/* 280 */           if (!(entity instanceof EntityPlayer) || !((EntityPlayer)entity).isSpectator()) {
/* 281 */             renderLayers(entity, f6, f5, partialTicks, f8, f2, f7, 0.0625F);
/*     */           }
/*     */         } 
/*     */         
/* 285 */         if (CustomEntityModels.isActive()) {
/* 286 */           this.renderEntity = null;
/*     */         }
/*     */         
/* 289 */         GlStateManager.disableRescaleNormal();
/* 290 */       } catch (Exception exception) {
/* 291 */         logger.error("Couldn't render entity", exception);
/*     */       } 
/*     */       
/* 294 */       GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
/* 295 */       GlStateManager.enableTexture2D();
/* 296 */       GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
/* 297 */       GlStateManager.enableCull();
/* 298 */       GlStateManager.popMatrix();
/*     */       
/* 300 */       if (!this.renderOutlines) {
/* 301 */         super.doRender(entity, x, y, z, entityYaw, partialTicks);
/*     */       }
/* 303 */       if (entity instanceof EntityPlayer) {
/* 304 */         RLEEvent rleEvent = new RLEEvent((EntityLivingBase)entity);
/* 305 */         EventManager.call((Event)rleEvent);
/*     */       } 
/* 307 */       if (Reflector.RenderLivingEvent_Post_Constructor.exists()) {
/* 308 */         Reflector.postForgeBusEvent(Reflector.RenderLivingEvent_Post_Constructor, new Object[] { entity, this, Double.valueOf(x), Double.valueOf(y), Double.valueOf(z) });
/*     */       }
/*     */     } 
/* 311 */     EventRenderEntity eventPost = new EventRenderEntity((Entity)entity, Event.State.POST, partialTicks, x, y, z);
/* 312 */     EventManager.call((Event)eventPost);
/*     */   }
/*     */   
/*     */   protected boolean setScoreTeamColor(T entityLivingBaseIn) {
/* 316 */     int i = 16777215;
/*     */     
/* 318 */     if (entityLivingBaseIn instanceof EntityPlayer) {
/* 319 */       ScorePlayerTeam scoreplayerteam = (ScorePlayerTeam)entityLivingBaseIn.getTeam();
/*     */       
/* 321 */       if (scoreplayerteam != null) {
/* 322 */         String s = FontRenderer.getFormatFromString(scoreplayerteam.getColorPrefix());
/*     */         
/* 324 */         if (s.length() >= 2) {
/* 325 */           i = getFontRendererFromRenderManager().getColorCode(s.charAt(1));
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 330 */     float f1 = (i >> 16 & 0xFF) / 255.0F;
/* 331 */     float f2 = (i >> 8 & 0xFF) / 255.0F;
/* 332 */     float f = (i & 0xFF) / 255.0F;
/* 333 */     GlStateManager.disableLighting();
/* 334 */     GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
/* 335 */     GlStateManager.color(f1, f2, f, 1.0F);
/* 336 */     GlStateManager.disableTexture2D();
/* 337 */     GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
/* 338 */     GlStateManager.disableTexture2D();
/* 339 */     GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
/* 340 */     return true;
/*     */   }
/*     */   
/*     */   protected void unsetScoreTeamColor() {
/* 344 */     GlStateManager.enableLighting();
/* 345 */     GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
/* 346 */     GlStateManager.enableTexture2D();
/* 347 */     GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
/* 348 */     GlStateManager.enableTexture2D();
/* 349 */     GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderModel(T entitylivingbaseIn, float p_77036_2_, float p_77036_3_, float p_77036_4_, float p_77036_5_, float p_77036_6_, float scaleFactor) {
/* 356 */     boolean flag = !entitylivingbaseIn.isInvisible();
/* 357 */     boolean flag1 = (!flag && !entitylivingbaseIn.isInvisibleToPlayer((EntityPlayer)(Minecraft.getMinecraft()).thePlayer));
/*     */     
/* 359 */     if (flag || flag1) {
/* 360 */       if (!bindEntityTexture(entitylivingbaseIn)) {
/*     */         return;
/*     */       }
/*     */       
/* 364 */       if (flag1) {
/* 365 */         GlStateManager.pushMatrix();
/* 366 */         GlStateManager.color(1.0F, 1.0F, 1.0F, 0.15F);
/* 367 */         GlStateManager.depthMask(false);
/* 368 */         GlStateManager.enableBlend();
/* 369 */         GlStateManager.blendFunc(770, 771);
/* 370 */         GlStateManager.alphaFunc(516, 0.003921569F);
/*     */       } 
/* 372 */       this.mainModel.render((Entity)entitylivingbaseIn, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, scaleFactor);
/* 373 */       if (flag1) {
/* 374 */         GlStateManager.disableBlend();
/* 375 */         GlStateManager.alphaFunc(516, 0.1F);
/* 376 */         GlStateManager.popMatrix();
/* 377 */         GlStateManager.depthMask(true);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected boolean setDoRenderBrightness(T entityLivingBaseIn, float partialTicks) {
/* 383 */     return setBrightness(entityLivingBaseIn, partialTicks, true);
/*     */   }
/*     */   
/*     */   protected boolean setBrightness(T entitylivingbaseIn, float partialTicks, boolean combineTextures) {
/* 387 */     float f = entitylivingbaseIn.getBrightness(partialTicks);
/* 388 */     int i = getColorMultiplier(entitylivingbaseIn, f, partialTicks);
/* 389 */     boolean flag = ((i >> 24 & 0xFF) > 0);
/* 390 */     boolean flag1 = (((EntityLivingBase)entitylivingbaseIn).hurtTime > 0 || ((EntityLivingBase)entitylivingbaseIn).deathTime > 0);
/*     */     
/* 392 */     if (!flag && !flag1)
/* 393 */       return false; 
/* 394 */     if (!flag && !combineTextures) {
/* 395 */       return false;
/*     */     }
/* 397 */     GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
/* 398 */     GlStateManager.enableTexture2D();
/* 399 */     GL11.glTexEnvi(8960, 8704, OpenGlHelper.GL_COMBINE);
/* 400 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_RGB, 8448);
/* 401 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_RGB, OpenGlHelper.defaultTexUnit);
/* 402 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.GL_PRIMARY_COLOR);
/* 403 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_RGB, 768);
/* 404 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_RGB, 768);
/* 405 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_ALPHA, 7681);
/* 406 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_ALPHA, OpenGlHelper.defaultTexUnit);
/* 407 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_ALPHA, 770);
/* 408 */     GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
/* 409 */     GlStateManager.enableTexture2D();
/* 410 */     GL11.glTexEnvi(8960, 8704, OpenGlHelper.GL_COMBINE);
/* 411 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_RGB, OpenGlHelper.GL_INTERPOLATE);
/* 412 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_RGB, OpenGlHelper.GL_CONSTANT);
/* 413 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.GL_PREVIOUS);
/* 414 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE2_RGB, OpenGlHelper.GL_CONSTANT);
/* 415 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_RGB, 768);
/* 416 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_RGB, 768);
/* 417 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND2_RGB, 770);
/* 418 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_ALPHA, 7681);
/* 419 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_ALPHA, OpenGlHelper.GL_PREVIOUS);
/* 420 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_ALPHA, 770);
/* 421 */     this.brightnessBuffer.position(0);
/*     */     
/* 423 */     if (flag1) {
/* 424 */       if (EntityHurtColor.getInstance.isEnabled()) {
/* 425 */         this.brightnessBuffer.put(((Double)EntityHurtColor.getInstance.r.get()).floatValue() / 255.0F);
/* 426 */         this.brightnessBuffer.put(((Double)EntityHurtColor.getInstance.g.get()).floatValue() / 255.0F);
/* 427 */         this.brightnessBuffer.put(((Double)EntityHurtColor.getInstance.b.get()).floatValue() / 255.0F);
/* 428 */         this.brightnessBuffer.put(((Double)EntityHurtColor.getInstance.alpha.get()).floatValue() / 255.0F);
/*     */       } else {
/* 430 */         this.brightnessBuffer.put(1.0F);
/* 431 */         this.brightnessBuffer.put(0.0F);
/* 432 */         this.brightnessBuffer.put(0.0F);
/* 433 */         this.brightnessBuffer.put(0.3F);
/*     */       } 
/* 435 */       if (Config.isShaders()) {
/* 436 */         Shaders.setEntityColor(1.0F, 0.0F, 0.0F, 0.3F);
/*     */       }
/*     */     } else {
/* 439 */       float f1 = (i >> 24 & 0xFF) / 255.0F;
/* 440 */       float f2 = (i >> 16 & 0xFF) / 255.0F;
/* 441 */       float f3 = (i >> 8 & 0xFF) / 255.0F;
/* 442 */       float f4 = (i & 0xFF) / 255.0F;
/* 443 */       this.brightnessBuffer.put(f2);
/* 444 */       this.brightnessBuffer.put(f3);
/* 445 */       this.brightnessBuffer.put(f4);
/* 446 */       this.brightnessBuffer.put(1.0F - f1);
/*     */       
/* 448 */       if (Config.isShaders()) {
/* 449 */         Shaders.setEntityColor(f2, f3, f4, 1.0F - f1);
/*     */       }
/*     */     } 
/*     */     
/* 453 */     this.brightnessBuffer.flip();
/* 454 */     GL11.glTexEnv(8960, 8705, this.brightnessBuffer);
/* 455 */     GlStateManager.setActiveTexture(OpenGlHelper.GL_TEXTURE2);
/* 456 */     GlStateManager.enableTexture2D();
/* 457 */     GlStateManager.bindTexture(textureBrightness.getGlTextureId());
/* 458 */     GL11.glTexEnvi(8960, 8704, OpenGlHelper.GL_COMBINE);
/* 459 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_RGB, 8448);
/* 460 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_RGB, OpenGlHelper.GL_PREVIOUS);
/* 461 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.lightmapTexUnit);
/* 462 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_RGB, 768);
/* 463 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_RGB, 768);
/* 464 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_ALPHA, 7681);
/* 465 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_ALPHA, OpenGlHelper.GL_PREVIOUS);
/* 466 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_ALPHA, 770);
/* 467 */     GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
/* 468 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void unsetBrightness() {
/* 473 */     GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
/* 474 */     GlStateManager.enableTexture2D();
/* 475 */     GL11.glTexEnvi(8960, 8704, OpenGlHelper.GL_COMBINE);
/* 476 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_RGB, 8448);
/* 477 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_RGB, OpenGlHelper.defaultTexUnit);
/* 478 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.GL_PRIMARY_COLOR);
/* 479 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_RGB, 768);
/* 480 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_RGB, 768);
/* 481 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_ALPHA, 8448);
/* 482 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_ALPHA, OpenGlHelper.defaultTexUnit);
/* 483 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_ALPHA, OpenGlHelper.GL_PRIMARY_COLOR);
/* 484 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_ALPHA, 770);
/* 485 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_ALPHA, 770);
/* 486 */     GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
/* 487 */     GL11.glTexEnvi(8960, 8704, OpenGlHelper.GL_COMBINE);
/* 488 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_RGB, 8448);
/* 489 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_RGB, 768);
/* 490 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_RGB, 768);
/* 491 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_RGB, 5890);
/* 492 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.GL_PREVIOUS);
/* 493 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_ALPHA, 8448);
/* 494 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_ALPHA, 770);
/* 495 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_ALPHA, 5890);
/* 496 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 497 */     GlStateManager.setActiveTexture(OpenGlHelper.GL_TEXTURE2);
/* 498 */     GlStateManager.disableTexture2D();
/* 499 */     GlStateManager.bindTexture(0);
/* 500 */     GL11.glTexEnvi(8960, 8704, OpenGlHelper.GL_COMBINE);
/* 501 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_RGB, 8448);
/* 502 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_RGB, 768);
/* 503 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_RGB, 768);
/* 504 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_RGB, 5890);
/* 505 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.GL_PREVIOUS);
/* 506 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_ALPHA, 8448);
/* 507 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_ALPHA, 770);
/* 508 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_ALPHA, 5890);
/* 509 */     GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
/*     */     
/* 511 */     if (Config.isShaders()) {
/* 512 */       Shaders.setEntityColor(0.0F, 0.0F, 0.0F, 0.0F);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void renderLivingAt(T entityLivingBaseIn, double x, double y, double z) {
/* 520 */     GlStateManager.translate((float)x, (float)y, (float)z);
/*     */   }
/*     */   
/*     */   protected void rotateCorpse(T bat, float p_77043_2_, float p_77043_3_, float partialTicks) {
/* 524 */     GlStateManager.rotate(180.0F - p_77043_3_, 0.0F, 1.0F, 0.0F);
/*     */     
/* 526 */     if (((EntityLivingBase)bat).deathTime > 0) {
/* 527 */       float f = (((EntityLivingBase)bat).deathTime + partialTicks - 1.0F) / 20.0F * 1.6F;
/* 528 */       f = MathHelper.sqrt_float(f);
/*     */       
/* 530 */       if (f > 1.0F) {
/* 531 */         f = 1.0F;
/*     */       }
/*     */       
/* 534 */       GlStateManager.rotate(f * getDeathMaxRotation(bat), 0.0F, 0.0F, 1.0F);
/*     */     } else {
/* 536 */       String s = EnumChatFormatting.getTextWithoutFormattingCodes(bat.getName());
/*     */       
/* 538 */       if (s != null && (s.equals("Dinnerbone") || s.equals("Grumm")) && (!(bat instanceof EntityPlayer) || ((EntityPlayer)bat).isWearing(EnumPlayerModelParts.CAPE))) {
/* 539 */         GlStateManager.translate(0.0F, ((EntityLivingBase)bat).height + 0.1F, 0.0F);
/* 540 */         GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected float getSwingProgress(T livingBase, float partialTickTime) {
/* 549 */     return livingBase.getSwingProgress(partialTickTime);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected float handleRotationFloat(T livingBase, float partialTicks) {
/* 556 */     return ((EntityLivingBase)livingBase).ticksExisted + partialTicks;
/*     */   }
/*     */   
/*     */   public void renderLayers(T entitylivingbaseIn, float p_177093_2_, float p_177093_3_, float partialTicks, float p_177093_5_, float p_177093_6_, float p_177093_7_, float p_177093_8_) {
/* 560 */     for (LayerRenderer<T> layerrenderer : this.layerRenderers) {
/* 561 */       boolean flag = setBrightness(entitylivingbaseIn, partialTicks, layerrenderer.shouldCombineTextures());
/*     */       
/* 563 */       if (EmissiveTextures.isActive()) {
/* 564 */         EmissiveTextures.beginRender();
/*     */       }
/*     */       
/* 567 */       if (this.renderLayersPushMatrix) {
/* 568 */         GlStateManager.pushMatrix();
/*     */       }
/*     */       
/* 571 */       layerrenderer.doRenderLayer((EntityLivingBase)entitylivingbaseIn, p_177093_2_, p_177093_3_, partialTicks, p_177093_5_, p_177093_6_, p_177093_7_, p_177093_8_);
/*     */       
/* 573 */       if (this.renderLayersPushMatrix) {
/* 574 */         GlStateManager.popMatrix();
/*     */       }
/*     */       
/* 577 */       if (EmissiveTextures.isActive()) {
/* 578 */         if (EmissiveTextures.hasEmissive()) {
/* 579 */           this.renderLayersPushMatrix = true;
/* 580 */           EmissiveTextures.beginRenderEmissive();
/* 581 */           GlStateManager.pushMatrix();
/* 582 */           layerrenderer.doRenderLayer((EntityLivingBase)entitylivingbaseIn, p_177093_2_, p_177093_3_, partialTicks, p_177093_5_, p_177093_6_, p_177093_7_, p_177093_8_);
/* 583 */           GlStateManager.popMatrix();
/* 584 */           EmissiveTextures.endRenderEmissive();
/*     */         } 
/*     */         
/* 587 */         EmissiveTextures.endRender();
/*     */       } 
/*     */       
/* 590 */       if (flag) {
/* 591 */         unsetBrightness();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   protected float getDeathMaxRotation(T entityLivingBaseIn) {
/* 597 */     return 90.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getColorMultiplier(T entitylivingbaseIn, float lightBrightness, float partialTickTime) {
/* 604 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void preRenderCallback(T entitylivingbaseIn, float partialTickTime) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderName(T entity, double x, double y, double z) {
/* 615 */     if (!Reflector.RenderLivingEvent_Specials_Pre_Constructor.exists() || !Reflector.postForgeBusEvent(Reflector.RenderLivingEvent_Specials_Pre_Constructor, new Object[] { entity, this, Double.valueOf(x), Double.valueOf(y), Double.valueOf(z) })) {
/* 616 */       if (canRenderName(entity)) {
/* 617 */         if (NameTags.getInstance.isEnabled()) {
/*     */           return;
/*     */         }
/* 620 */         double d0 = entity.getDistanceSqToEntity(this.renderManager.livingPlayer);
/* 621 */         float f = entity.isSneaking() ? NAME_TAG_RANGE_SNEAK : NAME_TAG_RANGE;
/*     */         
/* 623 */         if (d0 < (f * f)) {
/* 624 */           String s = entity.getDisplayName().getFormattedText();
/* 625 */           GlStateManager.alphaFunc(516, 0.1F);
/*     */           
/* 627 */           if (entity.isSneaking()) {
/* 628 */             FontRenderer fontrenderer = getFontRendererFromRenderManager();
/* 629 */             GlStateManager.pushMatrix();
/* 630 */             GlStateManager.translate((float)x, (float)y + ((EntityLivingBase)entity).height + 0.5F - (entity.isChild() ? (((EntityLivingBase)entity).height / 2.0F) : 0.0F), (float)z);
/* 631 */             GL11.glNormal3f(0.0F, 1.0F, 0.0F);
/* 632 */             GlStateManager.rotate(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
/* 633 */             GlStateManager.rotate(this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
/* 634 */             GlStateManager.scale(-0.02666667F, -0.02666667F, 0.02666667F);
/* 635 */             GlStateManager.translate(0.0F, 9.374999F, 0.0F);
/* 636 */             GlStateManager.disableLighting();
/* 637 */             GlStateManager.depthMask(false);
/* 638 */             GlStateManager.enableBlend();
/* 639 */             GlStateManager.disableTexture2D();
/* 640 */             GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 641 */             int i = fontrenderer.getStringWidth(s) / 2;
/* 642 */             Tessellator tessellator = Tessellator.getInstance();
/* 643 */             WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 644 */             worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
/* 645 */             worldrenderer.pos((-i - 1), -1.0D, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
/* 646 */             worldrenderer.pos((-i - 1), 8.0D, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
/* 647 */             worldrenderer.pos((i + 1), 8.0D, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
/* 648 */             worldrenderer.pos((i + 1), -1.0D, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
/* 649 */             tessellator.draw();
/* 650 */             GlStateManager.enableTexture2D();
/* 651 */             GlStateManager.depthMask(true);
/* 652 */             fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, 0, 553648127);
/* 653 */             GlStateManager.enableLighting();
/* 654 */             GlStateManager.disableBlend();
/* 655 */             GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 656 */             GlStateManager.popMatrix();
/*     */           } else {
/* 658 */             renderOffsetLivingLabel(entity, x, y - (entity.isChild() ? (((EntityLivingBase)entity).height / 2.0F) : 0.0D), z, s, 0.02666667F, d0);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 663 */       if (Reflector.RenderLivingEvent_Specials_Post_Constructor.exists()) {
/* 664 */         Reflector.postForgeBusEvent(Reflector.RenderLivingEvent_Specials_Post_Constructor, new Object[] { entity, this, Double.valueOf(x), Double.valueOf(y), Double.valueOf(z) });
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   protected boolean canRenderName(T entity) {
/* 670 */     EntityPlayerSP entityplayersp = (Minecraft.getMinecraft()).thePlayer;
/*     */     
/* 672 */     if (entity instanceof EntityPlayer && entity != entityplayersp) {
/* 673 */       Team team = entity.getTeam();
/* 674 */       Team team1 = entityplayersp.getTeam();
/*     */       
/* 676 */       if (team != null) {
/* 677 */         Team.EnumVisible team$enumvisible = team.getNameTagVisibility();
/*     */         
/* 679 */         switch (team$enumvisible) {
/*     */           case ALWAYS:
/* 681 */             return true;
/*     */           
/*     */           case NEVER:
/* 684 */             return false;
/*     */           
/*     */           case HIDE_FOR_OTHER_TEAMS:
/* 687 */             return (team1 == null || team.isSameTeam(team1));
/*     */           
/*     */           case HIDE_FOR_OWN_TEAM:
/* 690 */             return (team1 == null || !team.isSameTeam(team1));
/*     */         } 
/*     */         
/* 693 */         return true;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 698 */     return (Minecraft.isGuiEnabled() && entity != this.renderManager.livingPlayer && !entity.isInvisibleToPlayer((EntityPlayer)entityplayersp) && ((EntityLivingBase)entity).riddenByEntity == null);
/*     */   }
/*     */   
/*     */   public void setRenderOutlines(boolean renderOutlinesIn) {
/* 702 */     this.renderOutlines = renderOutlinesIn;
/*     */   }
/*     */   
/*     */   public List<LayerRenderer<T>> getLayerRenderers() {
/* 706 */     return this.layerRenderers;
/*     */   }
/*     */   
/*     */   static {
/* 710 */     int[] aint = textureBrightness.getTextureData();
/*     */     
/* 712 */     for (int i = 0; i < 256; i++) {
/* 713 */       aint[i] = -1;
/*     */     }
/*     */     
/* 716 */     textureBrightness.updateDynamicTexture();
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\entity\RendererLivingEntity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */