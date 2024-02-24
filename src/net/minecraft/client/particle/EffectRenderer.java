/*     */ package net.minecraft.client.particle;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import java.util.concurrent.Callable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.renderer.ActiveRenderInfo;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.ReportedException;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.World;
/*     */ import net.optifine.reflect.Reflector;
/*     */ 
/*     */ public class EffectRenderer {
/*  31 */   private static final ResourceLocation particleTextures = new ResourceLocation("textures/particle/particles.png");
/*     */   
/*     */   protected World worldObj;
/*     */   
/*  35 */   private final List<EntityFX>[][] fxLayers = (List<EntityFX>[][])new List[4][];
/*  36 */   private final List<EntityParticleEmitter> particleEmitters = Lists.newArrayList();
/*     */   
/*     */   private final TextureManager renderer;
/*     */   
/*  40 */   private final Random rand = new Random();
/*  41 */   private final Map<Integer, IParticleFactory> particleTypes = Maps.newHashMap();
/*     */ 
/*     */   
/*     */   public EffectRenderer(World worldIn, TextureManager rendererIn) {
/*  45 */     this.worldObj = worldIn;
/*  46 */     this.renderer = rendererIn;
/*     */     
/*  48 */     for (int i = 0; i < 4; i++) {
/*     */       
/*  50 */       this.fxLayers[i] = (List<EntityFX>[])new List[2];
/*     */       
/*  52 */       for (int j = 0; j < 2; j++)
/*     */       {
/*  54 */         this.fxLayers[i][j] = Lists.newArrayList();
/*     */       }
/*     */     } 
/*     */     
/*  58 */     registerVanillaParticles();
/*     */   }
/*     */ 
/*     */   
/*     */   private void registerVanillaParticles() {
/*  63 */     registerParticle(EnumParticleTypes.EXPLOSION_NORMAL.getParticleID(), new EntityExplodeFX.Factory());
/*  64 */     registerParticle(EnumParticleTypes.WATER_BUBBLE.getParticleID(), new EntityBubbleFX.Factory());
/*  65 */     registerParticle(EnumParticleTypes.WATER_SPLASH.getParticleID(), new EntitySplashFX.Factory());
/*  66 */     registerParticle(EnumParticleTypes.WATER_WAKE.getParticleID(), new EntityFishWakeFX.Factory());
/*  67 */     registerParticle(EnumParticleTypes.WATER_DROP.getParticleID(), new EntityRainFX.Factory());
/*  68 */     registerParticle(EnumParticleTypes.SUSPENDED.getParticleID(), new EntitySuspendFX.Factory());
/*  69 */     registerParticle(EnumParticleTypes.SUSPENDED_DEPTH.getParticleID(), new EntityAuraFX.Factory());
/*  70 */     registerParticle(EnumParticleTypes.CRIT.getParticleID(), new EntityCrit2FX.Factory());
/*  71 */     registerParticle(EnumParticleTypes.CRIT_MAGIC.getParticleID(), new EntityCrit2FX.MagicFactory());
/*  72 */     registerParticle(EnumParticleTypes.SMOKE_NORMAL.getParticleID(), new EntitySmokeFX.Factory());
/*  73 */     registerParticle(EnumParticleTypes.SMOKE_LARGE.getParticleID(), new EntityCritFX.Factory());
/*  74 */     registerParticle(EnumParticleTypes.SPELL.getParticleID(), new EntitySpellParticleFX.Factory());
/*  75 */     registerParticle(EnumParticleTypes.SPELL_INSTANT.getParticleID(), new EntitySpellParticleFX.InstantFactory());
/*  76 */     registerParticle(EnumParticleTypes.SPELL_MOB.getParticleID(), new EntitySpellParticleFX.MobFactory());
/*  77 */     registerParticle(EnumParticleTypes.SPELL_MOB_AMBIENT.getParticleID(), new EntitySpellParticleFX.AmbientMobFactory());
/*  78 */     registerParticle(EnumParticleTypes.SPELL_WITCH.getParticleID(), new EntitySpellParticleFX.WitchFactory());
/*  79 */     registerParticle(EnumParticleTypes.DRIP_WATER.getParticleID(), new EntityDropParticleFX.WaterFactory());
/*  80 */     registerParticle(EnumParticleTypes.DRIP_LAVA.getParticleID(), new EntityDropParticleFX.LavaFactory());
/*  81 */     registerParticle(EnumParticleTypes.VILLAGER_ANGRY.getParticleID(), new EntityHeartFX.AngryVillagerFactory());
/*  82 */     registerParticle(EnumParticleTypes.VILLAGER_HAPPY.getParticleID(), new EntityAuraFX.HappyVillagerFactory());
/*  83 */     registerParticle(EnumParticleTypes.TOWN_AURA.getParticleID(), new EntityAuraFX.Factory());
/*  84 */     registerParticle(EnumParticleTypes.NOTE.getParticleID(), new EntityNoteFX.Factory());
/*  85 */     registerParticle(EnumParticleTypes.PORTAL.getParticleID(), new EntityPortalFX.Factory());
/*  86 */     registerParticle(EnumParticleTypes.ENCHANTMENT_TABLE.getParticleID(), new EntityEnchantmentTableParticleFX.EnchantmentTable());
/*  87 */     registerParticle(EnumParticleTypes.FLAME.getParticleID(), new EntityFlameFX.Factory());
/*  88 */     registerParticle(EnumParticleTypes.LAVA.getParticleID(), new EntityLavaFX.Factory());
/*  89 */     registerParticle(EnumParticleTypes.FOOTSTEP.getParticleID(), new EntityFootStepFX.Factory());
/*  90 */     registerParticle(EnumParticleTypes.CLOUD.getParticleID(), new EntityCloudFX.Factory());
/*  91 */     registerParticle(EnumParticleTypes.REDSTONE.getParticleID(), new EntityReddustFX.Factory());
/*  92 */     registerParticle(EnumParticleTypes.SNOWBALL.getParticleID(), new EntityBreakingFX.SnowballFactory());
/*  93 */     registerParticle(EnumParticleTypes.SNOW_SHOVEL.getParticleID(), new EntitySnowShovelFX.Factory());
/*  94 */     registerParticle(EnumParticleTypes.SLIME.getParticleID(), new EntityBreakingFX.SlimeFactory());
/*  95 */     registerParticle(EnumParticleTypes.HEART.getParticleID(), new EntityHeartFX.Factory());
/*  96 */     registerParticle(EnumParticleTypes.BARRIER.getParticleID(), new Barrier.Factory());
/*  97 */     registerParticle(EnumParticleTypes.ITEM_CRACK.getParticleID(), new EntityBreakingFX.Factory());
/*  98 */     registerParticle(EnumParticleTypes.BLOCK_CRACK.getParticleID(), new EntityDiggingFX.Factory());
/*  99 */     registerParticle(EnumParticleTypes.BLOCK_DUST.getParticleID(), new EntityBlockDustFX.Factory());
/* 100 */     registerParticle(EnumParticleTypes.EXPLOSION_HUGE.getParticleID(), new EntityHugeExplodeFX.Factory());
/* 101 */     registerParticle(EnumParticleTypes.EXPLOSION_LARGE.getParticleID(), new EntityLargeExplodeFX.Factory());
/* 102 */     registerParticle(EnumParticleTypes.FIREWORKS_SPARK.getParticleID(), new EntityFirework.Factory());
/* 103 */     registerParticle(EnumParticleTypes.MOB_APPEARANCE.getParticleID(), new MobAppearance.Factory());
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerParticle(int id, IParticleFactory particleFactory) {
/* 108 */     this.particleTypes.put(Integer.valueOf(id), particleFactory);
/*     */   }
/*     */ 
/*     */   
/*     */   public void emitParticleAtEntity(Entity entityIn, EnumParticleTypes particleTypes) {
/* 113 */     this.particleEmitters.add(new EntityParticleEmitter(this.worldObj, entityIn, particleTypes));
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
/*     */ 
/*     */   
/*     */   public EntityFX spawnEffectParticle(int particleId, double xCoord, double yCoord, double zCoord, double xSpeed, double ySpeed, double zSpeed, int... parameters) {
/* 129 */     IParticleFactory iparticlefactory = this.particleTypes.get(Integer.valueOf(particleId));
/*     */     
/* 131 */     if (iparticlefactory != null) {
/*     */       
/* 133 */       EntityFX entityfx = iparticlefactory.getEntityFX(particleId, this.worldObj, xCoord, yCoord, zCoord, xSpeed, ySpeed, zSpeed, parameters);
/*     */       
/* 135 */       if (entityfx != null) {
/*     */         
/* 137 */         addEffect(entityfx);
/* 138 */         return entityfx;
/*     */       } 
/*     */     } 
/*     */     
/* 142 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addEffect(EntityFX effect) {
/* 147 */     if (effect != null)
/*     */     {
/* 149 */       if (!(effect instanceof EntityFirework.SparkFX) || Config.isFireworkParticles()) {
/*     */         
/* 151 */         int i = effect.getFXLayer();
/* 152 */         int j = (effect.getAlpha() != 1.0F) ? 0 : 1;
/*     */         
/* 154 */         if (this.fxLayers[i][j].size() >= 4000)
/*     */         {
/* 156 */           this.fxLayers[i][j].remove(0);
/*     */         }
/*     */         
/* 159 */         this.fxLayers[i][j].add(effect);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateEffects() {
/* 166 */     for (int i = 0; i < 4; i++)
/*     */     {
/* 168 */       updateEffectLayer(i);
/*     */     }
/*     */     
/* 171 */     List<EntityParticleEmitter> list = Lists.newArrayList();
/*     */     
/* 173 */     for (EntityParticleEmitter entityparticleemitter : this.particleEmitters) {
/*     */       
/* 175 */       entityparticleemitter.onUpdate();
/*     */       
/* 177 */       if (entityparticleemitter.isDead)
/*     */       {
/* 179 */         list.add(entityparticleemitter);
/*     */       }
/*     */     } 
/*     */     
/* 183 */     this.particleEmitters.removeAll(list);
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateEffectLayer(int p_178922_1_) {
/* 188 */     for (int i = 0; i < 2; i++)
/*     */     {
/* 190 */       updateEffectAlphaLayer(this.fxLayers[p_178922_1_][i]);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateEffectAlphaLayer(List<EntityFX> entitiesFX) {
/* 196 */     List<EntityFX> list = Lists.newArrayList();
/* 197 */     long i = System.currentTimeMillis();
/* 198 */     int j = entitiesFX.size();
/*     */     
/* 200 */     for (int k = 0; k < entitiesFX.size(); k++) {
/*     */       
/* 202 */       EntityFX entityfx = entitiesFX.get(k);
/* 203 */       tickParticle(entityfx);
/*     */       
/* 205 */       if (entityfx.isDead)
/*     */       {
/* 207 */         list.add(entityfx);
/*     */       }
/*     */       
/* 210 */       j--;
/*     */       
/* 212 */       if (System.currentTimeMillis() > i + 20L) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 218 */     if (j > 0) {
/*     */       
/* 220 */       int l = j;
/*     */       
/* 222 */       for (Iterator<EntityFX> iterator = entitiesFX.iterator(); iterator.hasNext() && l > 0; l--) {
/*     */         
/* 224 */         EntityFX entityfx1 = iterator.next();
/* 225 */         entityfx1.setDead();
/* 226 */         iterator.remove();
/*     */       } 
/*     */     } 
/*     */     
/* 230 */     entitiesFX.removeAll(list);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void tickParticle(final EntityFX particle) {
/*     */     try {
/* 237 */       particle.onUpdate();
/*     */     }
/* 239 */     catch (Throwable throwable) {
/*     */       
/* 241 */       CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Ticking Particle");
/* 242 */       CrashReportCategory crashreportcategory = crashreport.makeCategory("Particle being ticked");
/* 243 */       final int i = particle.getFXLayer();
/* 244 */       crashreportcategory.addCrashSectionCallable("Particle", new Callable<String>()
/*     */           {
/*     */             public String call() {
/* 247 */               return particle.toString();
/*     */             }
/*     */           });
/* 250 */       crashreportcategory.addCrashSectionCallable("Particle Type", new Callable<String>()
/*     */           {
/*     */             public String call() {
/* 253 */               return (i == 0) ? "MISC_TEXTURE" : ((i == 1) ? "TERRAIN_TEXTURE" : ((i == 3) ? "ENTITY_PARTICLE_TEXTURE" : ("Unknown - " + i)));
/*     */             }
/*     */           });
/* 256 */       throw new ReportedException(crashreport);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderParticles(Entity entityIn, float partialTicks) {
/* 265 */     float f = ActiveRenderInfo.getRotationX();
/* 266 */     float f1 = ActiveRenderInfo.getRotationZ();
/* 267 */     float f2 = ActiveRenderInfo.getRotationYZ();
/* 268 */     float f3 = ActiveRenderInfo.getRotationXY();
/* 269 */     float f4 = ActiveRenderInfo.getRotationXZ();
/* 270 */     EntityFX.interpPosX = entityIn.lastTickPosX + (entityIn.posX - entityIn.lastTickPosX) * partialTicks;
/* 271 */     EntityFX.interpPosY = entityIn.lastTickPosY + (entityIn.posY - entityIn.lastTickPosY) * partialTicks;
/* 272 */     EntityFX.interpPosZ = entityIn.lastTickPosZ + (entityIn.posZ - entityIn.lastTickPosZ) * partialTicks;
/* 273 */     GlStateManager.enableBlend();
/* 274 */     GlStateManager.blendFunc(770, 771);
/* 275 */     GlStateManager.alphaFunc(516, 0.003921569F);
/* 276 */     Block block = ActiveRenderInfo.getBlockAtEntityViewpoint(this.worldObj, entityIn, partialTicks);
/* 277 */     boolean flag = (block.getMaterial() == Material.water);
/*     */     
/* 279 */     for (int i = 0; i < 3; i++) {
/*     */       
/* 281 */       for (int j = 0; j < 2; j++) {
/*     */         
/* 283 */         final int i_f = i;
/*     */         
/* 285 */         if (!this.fxLayers[i][j].isEmpty()) {
/*     */           
/* 287 */           switch (j) {
/*     */             
/*     */             case 0:
/* 290 */               GlStateManager.depthMask(false);
/*     */               break;
/*     */             
/*     */             case 1:
/* 294 */               GlStateManager.depthMask(true);
/*     */               break;
/*     */           } 
/* 297 */           switch (i) {
/*     */ 
/*     */             
/*     */             default:
/* 301 */               this.renderer.bindTexture(particleTextures);
/*     */               break;
/*     */             
/*     */             case 1:
/* 305 */               this.renderer.bindTexture(TextureMap.locationBlocksTexture);
/*     */               break;
/*     */           } 
/* 308 */           GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 309 */           Tessellator tessellator = Tessellator.getInstance();
/* 310 */           WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 311 */           worldrenderer.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
/*     */           
/* 313 */           for (int k = 0; k < this.fxLayers[i][j].size(); k++) {
/*     */             
/* 315 */             final EntityFX entityfx = this.fxLayers[i][j].get(k);
/*     */ 
/*     */             
/*     */             try {
/* 319 */               if (flag || !(entityfx instanceof EntitySuspendFX))
/*     */               {
/* 321 */                 entityfx.renderParticle(worldrenderer, entityIn, partialTicks, f, f4, f1, f2, f3);
/*     */               }
/*     */             }
/* 324 */             catch (Throwable throwable) {
/*     */               
/* 326 */               CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Rendering Particle");
/* 327 */               CrashReportCategory crashreportcategory = crashreport.makeCategory("Particle being rendered");
/* 328 */               crashreportcategory.addCrashSectionCallable("Particle", new Callable<String>()
/*     */                   {
/*     */                     public String call() {
/* 331 */                       return entityfx.toString();
/*     */                     }
/*     */                   });
/* 334 */               crashreportcategory.addCrashSectionCallable("Particle Type", new Callable<String>()
/*     */                   {
/*     */                     public String call() {
/* 337 */                       return (i_f == 0) ? "MISC_TEXTURE" : ((i_f == 1) ? "TERRAIN_TEXTURE" : ((i_f == 3) ? "ENTITY_PARTICLE_TEXTURE" : ("Unknown - " + i_f)));
/*     */                     }
/*     */                   });
/* 340 */               throw new ReportedException(crashreport);
/*     */             } 
/*     */           } 
/*     */           
/* 344 */           tessellator.draw();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 349 */     GlStateManager.depthMask(true);
/* 350 */     GlStateManager.disableBlend();
/* 351 */     GlStateManager.alphaFunc(516, 0.1F);
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderLitParticles(Entity entityIn, float partialTick) {
/* 356 */     float f = 0.017453292F;
/* 357 */     float f1 = MathHelper.cos(entityIn.rotationYaw * 0.017453292F);
/* 358 */     float f2 = MathHelper.sin(entityIn.rotationYaw * 0.017453292F);
/* 359 */     float f3 = -f2 * MathHelper.sin(entityIn.rotationPitch * 0.017453292F);
/* 360 */     float f4 = f1 * MathHelper.sin(entityIn.rotationPitch * 0.017453292F);
/* 361 */     float f5 = MathHelper.cos(entityIn.rotationPitch * 0.017453292F);
/*     */     
/* 363 */     for (int i = 0; i < 2; i++) {
/*     */       
/* 365 */       List<EntityFX> list = this.fxLayers[3][i];
/*     */       
/* 367 */       if (!list.isEmpty()) {
/*     */         
/* 369 */         Tessellator tessellator = Tessellator.getInstance();
/* 370 */         WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/*     */         
/* 372 */         for (int j = 0; j < list.size(); j++) {
/*     */           
/* 374 */           EntityFX entityfx = list.get(j);
/* 375 */           entityfx.renderParticle(worldrenderer, entityIn, partialTick, f1, f5, f2, f3, f4);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void clearEffects(World worldIn) {
/* 383 */     this.worldObj = worldIn;
/*     */     
/* 385 */     for (int i = 0; i < 4; i++) {
/*     */       
/* 387 */       for (int j = 0; j < 2; j++)
/*     */       {
/* 389 */         this.fxLayers[i][j].clear();
/*     */       }
/*     */     } 
/*     */     
/* 393 */     this.particleEmitters.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addBlockDestroyEffects(BlockPos pos, IBlockState state) {
/*     */     boolean flag;
/* 400 */     if (Reflector.ForgeBlock_addDestroyEffects.exists() && Reflector.ForgeBlock_isAir.exists()) {
/*     */       
/* 402 */       Block block = state.getBlock();
/* 403 */       flag = (!Reflector.callBoolean(block, Reflector.ForgeBlock_isAir, new Object[] { this.worldObj, pos }) && !Reflector.callBoolean(block, Reflector.ForgeBlock_addDestroyEffects, new Object[] { this.worldObj, pos, this }));
/*     */     }
/*     */     else {
/*     */       
/* 407 */       flag = (state.getBlock().getMaterial() != Material.air);
/*     */     } 
/*     */     
/* 410 */     if (flag) {
/*     */       
/* 412 */       state = state.getBlock().getActualState(state, (IBlockAccess)this.worldObj, pos);
/* 413 */       int l = 4;
/*     */       
/* 415 */       for (int i = 0; i < l; i++) {
/*     */         
/* 417 */         for (int j = 0; j < l; j++) {
/*     */           
/* 419 */           for (int k = 0; k < l; k++) {
/*     */             
/* 421 */             double d0 = pos.getX() + (i + 0.5D) / l;
/* 422 */             double d1 = pos.getY() + (j + 0.5D) / l;
/* 423 */             double d2 = pos.getZ() + (k + 0.5D) / l;
/* 424 */             addEffect((new EntityDiggingFX(this.worldObj, d0, d1, d2, d0 - pos.getX() - 0.5D, d1 - pos.getY() - 0.5D, d2 - pos.getZ() - 0.5D, state)).setBlockPos(pos));
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addBlockHitEffects(BlockPos pos, EnumFacing side) {
/* 436 */     IBlockState iblockstate = this.worldObj.getBlockState(pos);
/* 437 */     Block block = iblockstate.getBlock();
/*     */     
/* 439 */     if (block.getRenderType() != -1) {
/*     */       
/* 441 */       int i = pos.getX();
/* 442 */       int j = pos.getY();
/* 443 */       int k = pos.getZ();
/* 444 */       float f = 0.1F;
/* 445 */       double d0 = i + this.rand.nextDouble() * (block.getBlockBoundsMaxX() - block.getBlockBoundsMinX() - (f * 2.0F)) + f + block.getBlockBoundsMinX();
/* 446 */       double d1 = j + this.rand.nextDouble() * (block.getBlockBoundsMaxY() - block.getBlockBoundsMinY() - (f * 2.0F)) + f + block.getBlockBoundsMinY();
/* 447 */       double d2 = k + this.rand.nextDouble() * (block.getBlockBoundsMaxZ() - block.getBlockBoundsMinZ() - (f * 2.0F)) + f + block.getBlockBoundsMinZ();
/*     */       
/* 449 */       if (side == EnumFacing.DOWN)
/*     */       {
/* 451 */         d1 = j + block.getBlockBoundsMinY() - f;
/*     */       }
/*     */       
/* 454 */       if (side == EnumFacing.UP)
/*     */       {
/* 456 */         d1 = j + block.getBlockBoundsMaxY() + f;
/*     */       }
/*     */       
/* 459 */       if (side == EnumFacing.NORTH)
/*     */       {
/* 461 */         d2 = k + block.getBlockBoundsMinZ() - f;
/*     */       }
/*     */       
/* 464 */       if (side == EnumFacing.SOUTH)
/*     */       {
/* 466 */         d2 = k + block.getBlockBoundsMaxZ() + f;
/*     */       }
/*     */       
/* 469 */       if (side == EnumFacing.WEST)
/*     */       {
/* 471 */         d0 = i + block.getBlockBoundsMinX() - f;
/*     */       }
/*     */       
/* 474 */       if (side == EnumFacing.EAST)
/*     */       {
/* 476 */         d0 = i + block.getBlockBoundsMaxX() + f;
/*     */       }
/*     */       
/* 479 */       addEffect((new EntityDiggingFX(this.worldObj, d0, d1, d2, 0.0D, 0.0D, 0.0D, iblockstate)).setBlockPos(pos).multiplyVelocity(0.2F).multipleParticleScaleBy(0.6F));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void moveToAlphaLayer(EntityFX effect) {
/* 485 */     moveToLayer(effect, 1, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void moveToNoAlphaLayer(EntityFX effect) {
/* 490 */     moveToLayer(effect, 0, 1);
/*     */   }
/*     */ 
/*     */   
/*     */   private void moveToLayer(EntityFX effect, int layerFrom, int layerTo) {
/* 495 */     for (int i = 0; i < 4; i++) {
/*     */       
/* 497 */       if (this.fxLayers[i][layerFrom].contains(effect)) {
/*     */         
/* 499 */         this.fxLayers[i][layerFrom].remove(effect);
/* 500 */         this.fxLayers[i][layerTo].add(effect);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getStatistics() {
/* 507 */     int i = 0;
/*     */     
/* 509 */     for (int j = 0; j < 4; j++) {
/*     */       
/* 511 */       for (int k = 0; k < 2; k++)
/*     */       {
/* 513 */         i += this.fxLayers[j][k].size();
/*     */       }
/*     */     } 
/*     */     
/* 517 */     return String.valueOf(i);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addBlockHitEffects(BlockPos p_addBlockHitEffects_1_, MovingObjectPosition p_addBlockHitEffects_2_) {
/* 522 */     IBlockState iblockstate = this.worldObj.getBlockState(p_addBlockHitEffects_1_);
/*     */     
/* 524 */     if (iblockstate != null) {
/*     */       
/* 526 */       boolean flag = Reflector.callBoolean(iblockstate.getBlock(), Reflector.ForgeBlock_addHitEffects, new Object[] { this.worldObj, p_addBlockHitEffects_2_, this });
/*     */       
/* 528 */       if (iblockstate != null && !flag)
/*     */       {
/* 530 */         addBlockHitEffects(p_addBlockHitEffects_1_, p_addBlockHitEffects_2_.sideHit);
/*     */       }
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\particle\EffectRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */