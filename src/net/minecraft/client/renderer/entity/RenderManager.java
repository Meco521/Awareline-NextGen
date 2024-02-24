/*     */ package net.minecraft.client.renderer.entity;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Map;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockBed;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.client.model.ModelCow;
/*     */ import net.minecraft.client.model.ModelOcelot;
/*     */ import net.minecraft.client.model.ModelZombie;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.RenderGlobal;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.culling.ICamera;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.item.EntityMinecart;
/*     */ import net.minecraft.entity.monster.EntityCaveSpider;
/*     */ import net.minecraft.entity.monster.EntityCreeper;
/*     */ import net.minecraft.entity.monster.EntityGuardian;
/*     */ import net.minecraft.entity.monster.EntityPigZombie;
/*     */ import net.minecraft.entity.monster.EntitySilverfish;
/*     */ import net.minecraft.entity.monster.EntitySpider;
/*     */ import net.minecraft.entity.passive.EntityCow;
/*     */ import net.minecraft.entity.passive.EntityPig;
/*     */ import net.minecraft.entity.passive.EntitySquid;
/*     */ import net.minecraft.entity.passive.EntityVillager;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.ReportedException;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.World;
/*     */ import net.optifine.reflect.Reflector;
/*     */ 
/*     */ public class RenderManager {
/*  45 */   private Map<Class, Render> entityRenderMap = Maps.newHashMap();
/*  46 */   private final Map<String, RenderPlayer> skinMap = Maps.newHashMap();
/*     */   
/*     */   private final RenderPlayer playerRenderer;
/*     */   
/*     */   private FontRenderer textRenderer;
/*     */   
/*     */   public double renderPosX;
/*     */   
/*     */   public double renderPosY;
/*     */   
/*     */   public double renderPosZ;
/*     */   
/*     */   public TextureManager renderEngine;
/*     */   
/*     */   public World worldObj;
/*     */   
/*     */   public Entity livingPlayer;
/*     */   
/*     */   public Entity pointedEntity;
/*     */   
/*     */   public float playerViewY;
/*     */   public float playerViewX;
/*     */   public GameSettings options;
/*     */   public double viewerPosX;
/*     */   public double viewerPosY;
/*     */   public double viewerPosZ;
/*     */   private boolean renderOutlines = false;
/*     */   private boolean renderShadow = true;
/*     */   private boolean debugBoundingBox = false;
/*  75 */   public Render renderRender = null;
/*     */ 
/*     */   
/*     */   public RenderManager(TextureManager renderEngineIn, RenderItem itemRendererIn) {
/*  79 */     this.renderEngine = renderEngineIn;
/*  80 */     this.entityRenderMap.put(EntityCaveSpider.class, new RenderCaveSpider(this));
/*  81 */     this.entityRenderMap.put(EntitySpider.class, new RenderSpider<>(this));
/*  82 */     this.entityRenderMap.put(EntityPig.class, new RenderPig(this, (ModelBase)new ModelPig(), 0.7F));
/*  83 */     this.entityRenderMap.put(EntitySheep.class, new RenderSheep(this, (ModelBase)new ModelSheep2(), 0.7F));
/*  84 */     this.entityRenderMap.put(EntityCow.class, new RenderCow(this, (ModelBase)new ModelCow(), 0.7F));
/*  85 */     this.entityRenderMap.put(EntityMooshroom.class, new RenderMooshroom(this, (ModelBase)new ModelCow(), 0.7F));
/*  86 */     this.entityRenderMap.put(EntityWolf.class, new RenderWolf(this, (ModelBase)new ModelWolf(), 0.5F));
/*  87 */     this.entityRenderMap.put(EntityChicken.class, new RenderChicken(this, (ModelBase)new ModelChicken(), 0.3F));
/*  88 */     this.entityRenderMap.put(EntityOcelot.class, new RenderOcelot(this, (ModelBase)new ModelOcelot(), 0.4F));
/*  89 */     this.entityRenderMap.put(EntityRabbit.class, new RenderRabbit(this, (ModelBase)new ModelRabbit(), 0.3F));
/*  90 */     this.entityRenderMap.put(EntitySilverfish.class, new RenderSilverfish(this));
/*  91 */     this.entityRenderMap.put(EntityEndermite.class, new RenderEndermite(this));
/*  92 */     this.entityRenderMap.put(EntityCreeper.class, new RenderCreeper(this));
/*  93 */     this.entityRenderMap.put(EntityEnderman.class, new RenderEnderman(this));
/*  94 */     this.entityRenderMap.put(EntitySnowman.class, new RenderSnowMan(this));
/*  95 */     this.entityRenderMap.put(EntitySkeleton.class, new RenderSkeleton(this));
/*  96 */     this.entityRenderMap.put(EntityWitch.class, new RenderWitch(this));
/*  97 */     this.entityRenderMap.put(EntityBlaze.class, new RenderBlaze(this));
/*  98 */     this.entityRenderMap.put(EntityPigZombie.class, new RenderPigZombie(this));
/*  99 */     this.entityRenderMap.put(EntityZombie.class, new RenderZombie(this));
/* 100 */     this.entityRenderMap.put(EntitySlime.class, new RenderSlime(this, (ModelBase)new ModelSlime(16), 0.25F));
/* 101 */     this.entityRenderMap.put(EntityMagmaCube.class, new RenderMagmaCube(this));
/* 102 */     this.entityRenderMap.put(EntityGiantZombie.class, new RenderGiantZombie(this, (ModelBase)new ModelZombie(), 0.5F, 6.0F));
/* 103 */     this.entityRenderMap.put(EntityGhast.class, new RenderGhast(this));
/* 104 */     this.entityRenderMap.put(EntitySquid.class, new RenderSquid(this, (ModelBase)new ModelSquid(), 0.7F));
/* 105 */     this.entityRenderMap.put(EntityVillager.class, new RenderVillager(this));
/* 106 */     this.entityRenderMap.put(EntityIronGolem.class, new RenderIronGolem(this));
/* 107 */     this.entityRenderMap.put(EntityBat.class, new RenderBat(this));
/* 108 */     this.entityRenderMap.put(EntityGuardian.class, new RenderGuardian(this));
/* 109 */     this.entityRenderMap.put(EntityDragon.class, new RenderDragon(this));
/* 110 */     this.entityRenderMap.put(EntityEnderCrystal.class, new RenderEnderCrystal(this));
/* 111 */     this.entityRenderMap.put(EntityWither.class, new RenderWither(this));
/* 112 */     this.entityRenderMap.put(Entity.class, new RenderEntity(this));
/* 113 */     this.entityRenderMap.put(EntityPainting.class, new RenderPainting(this));
/* 114 */     this.entityRenderMap.put(EntityItemFrame.class, new RenderItemFrame(this, itemRendererIn));
/* 115 */     this.entityRenderMap.put(EntityLeashKnot.class, new RenderLeashKnot(this));
/* 116 */     this.entityRenderMap.put(EntityArrow.class, new RenderArrow(this));
/* 117 */     this.entityRenderMap.put(EntitySnowball.class, new RenderSnowball<>(this, Items.snowball, itemRendererIn));
/* 118 */     this.entityRenderMap.put(EntityEnderPearl.class, new RenderSnowball<>(this, Items.ender_pearl, itemRendererIn));
/* 119 */     this.entityRenderMap.put(EntityEnderEye.class, new RenderSnowball<>(this, Items.ender_eye, itemRendererIn));
/* 120 */     this.entityRenderMap.put(EntityEgg.class, new RenderSnowball<>(this, Items.egg, itemRendererIn));
/* 121 */     this.entityRenderMap.put(EntityPotion.class, new RenderPotion(this, itemRendererIn));
/* 122 */     this.entityRenderMap.put(EntityExpBottle.class, new RenderSnowball<>(this, Items.experience_bottle, itemRendererIn));
/* 123 */     this.entityRenderMap.put(EntityFireworkRocket.class, new RenderSnowball<>(this, Items.fireworks, itemRendererIn));
/* 124 */     this.entityRenderMap.put(EntityLargeFireball.class, new RenderFireball(this, 2.0F));
/* 125 */     this.entityRenderMap.put(EntitySmallFireball.class, new RenderFireball(this, 0.5F));
/* 126 */     this.entityRenderMap.put(EntityWitherSkull.class, new RenderWitherSkull(this));
/* 127 */     this.entityRenderMap.put(EntityItem.class, new RenderEntityItem(this, itemRendererIn));
/* 128 */     this.entityRenderMap.put(EntityXPOrb.class, new RenderXPOrb(this));
/* 129 */     this.entityRenderMap.put(EntityTNTPrimed.class, new RenderTNTPrimed(this));
/* 130 */     this.entityRenderMap.put(EntityFallingBlock.class, new RenderFallingBlock(this));
/* 131 */     this.entityRenderMap.put(EntityArmorStand.class, new ArmorStandRenderer(this));
/* 132 */     this.entityRenderMap.put(EntityMinecartTNT.class, new RenderTntMinecart(this));
/* 133 */     this.entityRenderMap.put(EntityMinecartMobSpawner.class, new RenderMinecartMobSpawner(this));
/* 134 */     this.entityRenderMap.put(EntityMinecart.class, new RenderMinecart<>(this));
/* 135 */     this.entityRenderMap.put(EntityBoat.class, new RenderBoat(this));
/* 136 */     this.entityRenderMap.put(EntityFishHook.class, new RenderFish(this));
/* 137 */     this.entityRenderMap.put(EntityHorse.class, new RenderHorse(this, new ModelHorse(), 0.75F));
/* 138 */     this.entityRenderMap.put(EntityLightningBolt.class, new RenderLightningBolt(this));
/* 139 */     this.playerRenderer = new RenderPlayer(this);
/* 140 */     this.skinMap.put("default", this.playerRenderer);
/* 141 */     this.skinMap.put("slim", new RenderPlayer(this, true));
/* 142 */     PlayerItemsLayer.register(this.skinMap);
/*     */     
/* 144 */     if (Reflector.RenderingRegistry_loadEntityRenderers.exists())
/*     */     {
/* 146 */       Reflector.call(Reflector.RenderingRegistry_loadEntityRenderers, new Object[] { this, this.entityRenderMap });
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRenderPosition(double renderPosXIn, double renderPosYIn, double renderPosZIn) {
/* 152 */     this.renderPosX = renderPosXIn;
/* 153 */     this.renderPosY = renderPosYIn;
/* 154 */     this.renderPosZ = renderPosZIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public <T extends Entity> Render<T> getEntityClassRenderObject(Class<? extends Entity> entityClass) {
/* 159 */     Render<? extends Entity> render = this.entityRenderMap.get(entityClass);
/*     */     
/* 161 */     if (render == null && entityClass != Entity.class) {
/*     */       
/* 163 */       render = getEntityClassRenderObject((Class)entityClass.getSuperclass());
/* 164 */       this.entityRenderMap.put(entityClass, render);
/*     */     } 
/*     */     
/* 167 */     return (Render)render;
/*     */   }
/*     */ 
/*     */   
/*     */   public <T extends Entity> Render<T> getEntityRenderObject(Entity entityIn) {
/* 172 */     if (entityIn instanceof AbstractClientPlayer) {
/*     */       
/* 174 */       String s = ((AbstractClientPlayer)entityIn).getSkinType();
/* 175 */       RenderPlayer renderplayer = this.skinMap.get(s);
/* 176 */       return (renderplayer != null) ? renderplayer : this.playerRenderer;
/*     */     } 
/*     */ 
/*     */     
/* 180 */     return getEntityClassRenderObject((Class)entityIn.getClass());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void cacheActiveRenderInfo(World worldIn, FontRenderer textRendererIn, Entity livingPlayerIn, Entity pointedEntityIn, GameSettings optionsIn, float partialTicks) {
/* 186 */     this.worldObj = worldIn;
/* 187 */     this.options = optionsIn;
/* 188 */     this.livingPlayer = livingPlayerIn;
/* 189 */     this.pointedEntity = pointedEntityIn;
/* 190 */     this.textRenderer = textRendererIn;
/*     */     
/* 192 */     if (livingPlayerIn instanceof EntityLivingBase && ((EntityLivingBase)livingPlayerIn).isPlayerSleeping()) {
/*     */       
/* 194 */       IBlockState iblockstate = worldIn.getBlockState(new BlockPos(livingPlayerIn));
/* 195 */       Block block = iblockstate.getBlock();
/*     */       
/* 197 */       if (Reflector.callBoolean(block, Reflector.ForgeBlock_isBed, new Object[] { iblockstate, worldIn, new BlockPos(livingPlayerIn), livingPlayerIn }))
/*     */       {
/* 199 */         EnumFacing enumfacing = (EnumFacing)Reflector.call(block, Reflector.ForgeBlock_getBedDirection, new Object[] { iblockstate, worldIn, new BlockPos(livingPlayerIn) });
/* 200 */         int i = enumfacing.getHorizontalIndex();
/* 201 */         this.playerViewY = (i * 90 + 180);
/* 202 */         this.playerViewX = 0.0F;
/*     */       }
/* 204 */       else if (block == Blocks.bed)
/*     */       {
/* 206 */         int j = ((EnumFacing)iblockstate.getValue((IProperty)BlockBed.FACING)).getHorizontalIndex();
/* 207 */         this.playerViewY = (j * 90 + 180);
/* 208 */         this.playerViewX = 0.0F;
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 213 */       this.playerViewY = livingPlayerIn.prevRotationYaw + (livingPlayerIn.rotationYaw - livingPlayerIn.prevRotationYaw) * partialTicks;
/* 214 */       this.playerViewX = livingPlayerIn.prevRotationPitch + (livingPlayerIn.rotationPitch - livingPlayerIn.prevRotationPitch) * partialTicks;
/*     */     } 
/*     */     
/* 217 */     if (optionsIn.thirdPersonView == 2)
/*     */     {
/* 219 */       this.playerViewY += 180.0F;
/*     */     }
/*     */     
/* 222 */     this.viewerPosX = livingPlayerIn.lastTickPosX + (livingPlayerIn.posX - livingPlayerIn.lastTickPosX) * partialTicks;
/* 223 */     this.viewerPosY = livingPlayerIn.lastTickPosY + (livingPlayerIn.posY - livingPlayerIn.lastTickPosY) * partialTicks;
/* 224 */     this.viewerPosZ = livingPlayerIn.lastTickPosZ + (livingPlayerIn.posZ - livingPlayerIn.lastTickPosZ) * partialTicks;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPlayerViewY(float playerViewYIn) {
/* 229 */     this.playerViewY = playerViewYIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isRenderShadow() {
/* 234 */     return this.renderShadow;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRenderShadow(boolean renderShadowIn) {
/* 239 */     this.renderShadow = renderShadowIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDebugBoundingBox(boolean debugBoundingBoxIn) {
/* 244 */     this.debugBoundingBox = debugBoundingBoxIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDebugBoundingBox() {
/* 249 */     return this.debugBoundingBox;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean renderEntitySimple(Entity entityIn, float partialTicks) {
/* 254 */     return renderEntityStatic(entityIn, partialTicks, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldRender(Entity entityIn, ICamera camera, double camX, double camY, double camZ) {
/* 259 */     Render<Entity> render = getEntityRenderObject(entityIn);
/* 260 */     return (render != null && render.shouldRender(entityIn, camera, camX, camY, camZ));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean renderEntityStatic(Entity entity, float partialTicks, boolean hideDebugBox) {
/* 265 */     if (entity.ticksExisted == 0) {
/*     */       
/* 267 */       entity.lastTickPosX = entity.posX;
/* 268 */       entity.lastTickPosY = entity.posY;
/* 269 */       entity.lastTickPosZ = entity.posZ;
/*     */     } 
/*     */     
/* 272 */     double d0 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks;
/* 273 */     double d1 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks;
/* 274 */     double d2 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks;
/* 275 */     float f = entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks;
/* 276 */     int i = entity.getBrightnessForRender(partialTicks);
/*     */     
/* 278 */     if (entity.isBurning())
/*     */     {
/* 280 */       i = 15728880;
/*     */     }
/*     */     
/* 283 */     int j = i % 65536;
/* 284 */     int k = i / 65536;
/* 285 */     OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j, k);
/* 286 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 287 */     return doRenderEntity(entity, d0 - this.renderPosX, d1 - this.renderPosY, d2 - this.renderPosZ, f, partialTicks, hideDebugBox);
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderWitherSkull(Entity entityIn, float partialTicks) {
/* 292 */     double d0 = entityIn.lastTickPosX + (entityIn.posX - entityIn.lastTickPosX) * partialTicks;
/* 293 */     double d1 = entityIn.lastTickPosY + (entityIn.posY - entityIn.lastTickPosY) * partialTicks;
/* 294 */     double d2 = entityIn.lastTickPosZ + (entityIn.posZ - entityIn.lastTickPosZ) * partialTicks;
/* 295 */     Render<Entity> render = getEntityRenderObject(entityIn);
/*     */     
/* 297 */     if (render != null && this.renderEngine != null) {
/*     */       
/* 299 */       int i = entityIn.getBrightnessForRender(partialTicks);
/* 300 */       int j = i % 65536;
/* 301 */       int k = i / 65536;
/* 302 */       OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j, k);
/* 303 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 304 */       render.renderName(entityIn, d0 - this.renderPosX, d1 - this.renderPosY, d2 - this.renderPosZ);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean renderEntityWithPosYaw(Entity entityIn, double x, double y, double z, float entityYaw, float partialTicks) {
/* 310 */     return doRenderEntity(entityIn, x, y, z, entityYaw, partialTicks, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean doRenderEntity(Entity entity, double x, double y, double z, float entityYaw, float partialTicks, boolean hideDebugBox) {
/* 315 */     Render<Entity> render = null;
/*     */ 
/*     */     
/*     */     try {
/* 319 */       render = getEntityRenderObject(entity);
/*     */       
/* 321 */       if (render != null && this.renderEngine != null) {
/*     */ 
/*     */         
/*     */         try {
/* 325 */           if (render instanceof RendererLivingEntity)
/*     */           {
/* 327 */             ((RendererLivingEntity)render).setRenderOutlines(this.renderOutlines);
/*     */           }
/*     */           
/* 330 */           if (CustomEntityModels.isActive())
/*     */           {
/* 332 */             this.renderRender = render;
/*     */           }
/*     */           
/* 335 */           render.doRender(entity, x, y, z, entityYaw, partialTicks);
/*     */         }
/* 337 */         catch (Throwable throwable2) {
/*     */           
/* 339 */           throw new ReportedException(CrashReport.makeCrashReport(throwable2, "Rendering entity in world"));
/*     */         } 
/*     */ 
/*     */         
/*     */         try {
/* 344 */           if (!this.renderOutlines)
/*     */           {
/* 346 */             render.doRenderShadowAndFire(entity, x, y, z, entityYaw, partialTicks);
/*     */           }
/*     */         }
/* 349 */         catch (Throwable throwable1) {
/*     */           
/* 351 */           throw new ReportedException(CrashReport.makeCrashReport(throwable1, "Post-rendering entity in world"));
/*     */         } 
/*     */         
/* 354 */         if (this.debugBoundingBox && !entity.isInvisible() && !hideDebugBox) {
/*     */           try
/*     */           {
/*     */             
/* 358 */             renderDebugBoundingBox(entity, x, y, z, entityYaw, partialTicks);
/*     */           }
/* 360 */           catch (Throwable throwable)
/*     */           {
/* 362 */             throw new ReportedException(CrashReport.makeCrashReport(throwable, "Rendering entity hitbox in world"));
/*     */           }
/*     */         
/*     */         }
/* 366 */       } else if (this.renderEngine != null) {
/*     */         
/* 368 */         return false;
/*     */       } 
/*     */       
/* 371 */       return true;
/*     */     }
/* 373 */     catch (Throwable throwable3) {
/*     */       
/* 375 */       CrashReport crashreport = CrashReport.makeCrashReport(throwable3, "Rendering entity in world");
/* 376 */       CrashReportCategory crashreportcategory = crashreport.makeCategory("Entity being rendered");
/* 377 */       entity.addEntityCrashInfo(crashreportcategory);
/* 378 */       CrashReportCategory crashreportcategory1 = crashreport.makeCategory("Renderer details");
/* 379 */       crashreportcategory1.addCrashSection("Assigned renderer", render);
/* 380 */       crashreportcategory1.addCrashSection("Location", CrashReportCategory.getCoordinateInfo(x, y, z));
/* 381 */       crashreportcategory1.addCrashSection("Rotation", Float.valueOf(entityYaw));
/* 382 */       crashreportcategory1.addCrashSection("Delta", Float.valueOf(partialTicks));
/* 383 */       throw new ReportedException(crashreport);
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
/*     */   private void renderDebugBoundingBox(Entity entityIn, double x, double y, double z, float entityYaw, float partialTicks) {
/* 398 */     if (!Shaders.isShadowPass) {
/*     */       
/* 400 */       GlStateManager.depthMask(false);
/* 401 */       GlStateManager.disableTexture2D();
/* 402 */       GlStateManager.disableLighting();
/* 403 */       GlStateManager.disableCull();
/* 404 */       GlStateManager.disableBlend();
/* 405 */       float f = entityIn.width / 2.0F;
/* 406 */       AxisAlignedBB axisalignedbb = entityIn.getEntityBoundingBox();
/* 407 */       AxisAlignedBB axisalignedbb1 = new AxisAlignedBB(axisalignedbb.minX - entityIn.posX + x, axisalignedbb.minY - entityIn.posY + y, axisalignedbb.minZ - entityIn.posZ + z, axisalignedbb.maxX - entityIn.posX + x, axisalignedbb.maxY - entityIn.posY + y, axisalignedbb.maxZ - entityIn.posZ + z);
/* 408 */       RenderGlobal.drawOutlinedBoundingBox(axisalignedbb1, 255, 255, 255, 255);
/*     */       
/* 410 */       if (entityIn instanceof EntityLivingBase) {
/*     */         
/* 412 */         float f1 = 0.01F;
/* 413 */         RenderGlobal.drawOutlinedBoundingBox(new AxisAlignedBB(x - f, y + entityIn.getEyeHeight() - 0.009999999776482582D, z - f, x + f, y + entityIn.getEyeHeight() + 0.009999999776482582D, z + f), 255, 0, 0, 255);
/*     */       } 
/*     */       
/* 416 */       Tessellator tessellator = Tessellator.getInstance();
/* 417 */       WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 418 */       Vec3 vec3 = entityIn.getLook(partialTicks);
/* 419 */       worldrenderer.begin(3, DefaultVertexFormats.POSITION_COLOR);
/* 420 */       worldrenderer.pos(x, y + entityIn.getEyeHeight(), z).color(0, 0, 255, 255).endVertex();
/* 421 */       worldrenderer.pos(x + vec3.xCoord * 2.0D, y + entityIn.getEyeHeight() + vec3.yCoord * 2.0D, z + vec3.zCoord * 2.0D).color(0, 0, 255, 255).endVertex();
/* 422 */       tessellator.draw();
/* 423 */       GlStateManager.enableTexture2D();
/* 424 */       GlStateManager.enableLighting();
/* 425 */       GlStateManager.enableCull();
/* 426 */       GlStateManager.disableBlend();
/* 427 */       GlStateManager.depthMask(true);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(World worldIn) {
/* 436 */     this.worldObj = worldIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getDistanceToCamera(double x, double y, double z) {
/* 441 */     double d0 = x - this.viewerPosX;
/* 442 */     double d1 = y - this.viewerPosY;
/* 443 */     double d2 = z - this.viewerPosZ;
/* 444 */     return d0 * d0 + d1 * d1 + d2 * d2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FontRenderer getFontRenderer() {
/* 452 */     return this.textRenderer;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRenderOutlines(boolean renderOutlinesIn) {
/* 457 */     this.renderOutlines = renderOutlinesIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<Class, Render> getEntityRenderMap() {
/* 462 */     return this.entityRenderMap;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setEntityRenderMap(Map<Class, Render> p_setEntityRenderMap_1_) {
/* 467 */     this.entityRenderMap = p_setEntityRenderMap_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<String, RenderPlayer> getSkinMap() {
/* 472 */     return Collections.unmodifiableMap(this.skinMap);
/*     */   }
/*     */   
/*     */   public double getRenderPosX() {
/* 476 */     return this.renderPosX;
/*     */   }
/*     */   
/*     */   public double getRenderPosY() {
/* 480 */     return this.renderPosY;
/*     */   }
/*     */   
/*     */   public double getRenderPosZ() {
/* 484 */     return this.renderPosZ;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\entity\RenderManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */