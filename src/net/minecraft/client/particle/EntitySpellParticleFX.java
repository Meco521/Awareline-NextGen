/*     */ package net.minecraft.client.particle;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntitySpellParticleFX
/*     */   extends EntityFX
/*     */ {
/*  12 */   private static final Random RANDOM = new Random();
/*     */ 
/*     */   
/*  15 */   private int baseSpellTextureIndex = 128;
/*     */ 
/*     */   
/*     */   protected EntitySpellParticleFX(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double p_i1229_8_, double p_i1229_10_, double p_i1229_12_) {
/*  19 */     super(worldIn, xCoordIn, yCoordIn, zCoordIn, 0.5D - RANDOM.nextDouble(), p_i1229_10_, 0.5D - RANDOM.nextDouble());
/*  20 */     this.motionY *= 0.20000000298023224D;
/*     */     
/*  22 */     if (p_i1229_8_ == 0.0D && p_i1229_12_ == 0.0D) {
/*     */       
/*  24 */       this.motionX *= 0.10000000149011612D;
/*  25 */       this.motionZ *= 0.10000000149011612D;
/*     */     } 
/*     */     
/*  28 */     this.particleScale *= 0.75F;
/*  29 */     this.particleMaxAge = (int)(8.0D / (Math.random() * 0.8D + 0.2D));
/*  30 */     this.noClip = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderParticle(WorldRenderer worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
/*  38 */     float f = (this.particleAge + partialTicks) / this.particleMaxAge * 32.0F;
/*  39 */     f = MathHelper.clamp_float(f, 0.0F, 1.0F);
/*  40 */     super.renderParticle(worldRendererIn, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  48 */     this.prevPosX = this.posX;
/*  49 */     this.prevPosY = this.posY;
/*  50 */     this.prevPosZ = this.posZ;
/*     */     
/*  52 */     if (this.particleAge++ >= this.particleMaxAge)
/*     */     {
/*  54 */       setDead();
/*     */     }
/*     */     
/*  57 */     setParticleTextureIndex(this.baseSpellTextureIndex + 7 - (this.particleAge << 3) / this.particleMaxAge);
/*  58 */     this.motionY += 0.004D;
/*  59 */     moveEntity(this.motionX, this.motionY, this.motionZ);
/*     */     
/*  61 */     if (this.posY == this.prevPosY) {
/*     */       
/*  63 */       this.motionX *= 1.1D;
/*  64 */       this.motionZ *= 1.1D;
/*     */     } 
/*     */     
/*  67 */     this.motionX *= 0.9599999785423279D;
/*  68 */     this.motionY *= 0.9599999785423279D;
/*  69 */     this.motionZ *= 0.9599999785423279D;
/*     */     
/*  71 */     if (this.onGround) {
/*     */       
/*  73 */       this.motionX *= 0.699999988079071D;
/*  74 */       this.motionZ *= 0.699999988079071D;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBaseSpellTextureIndex(int baseSpellTextureIndexIn) {
/*  83 */     this.baseSpellTextureIndex = baseSpellTextureIndexIn;
/*     */   }
/*     */   
/*     */   public static class AmbientMobFactory
/*     */     implements IParticleFactory
/*     */   {
/*     */     public EntityFX getEntityFX(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/*  90 */       EntityFX entityfx = new EntitySpellParticleFX(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
/*  91 */       entityfx.setAlphaF(0.15F);
/*  92 */       entityfx.setRBGColorF((float)xSpeedIn, (float)ySpeedIn, (float)zSpeedIn);
/*  93 */       return entityfx;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Factory
/*     */     implements IParticleFactory
/*     */   {
/*     */     public EntityFX getEntityFX(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/* 101 */       return new EntitySpellParticleFX(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class InstantFactory
/*     */     implements IParticleFactory
/*     */   {
/*     */     public EntityFX getEntityFX(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/* 109 */       EntityFX entityfx = new EntitySpellParticleFX(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
/* 110 */       ((EntitySpellParticleFX)entityfx).setBaseSpellTextureIndex(144);
/* 111 */       return entityfx;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class MobFactory
/*     */     implements IParticleFactory
/*     */   {
/*     */     public EntityFX getEntityFX(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/* 119 */       EntityFX entityfx = new EntitySpellParticleFX(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
/* 120 */       entityfx.setRBGColorF((float)xSpeedIn, (float)ySpeedIn, (float)zSpeedIn);
/* 121 */       return entityfx;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class WitchFactory
/*     */     implements IParticleFactory
/*     */   {
/*     */     public EntityFX getEntityFX(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/* 129 */       EntityFX entityfx = new EntitySpellParticleFX(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
/* 130 */       ((EntitySpellParticleFX)entityfx).setBaseSpellTextureIndex(144);
/* 131 */       float f = worldIn.rand.nextFloat() * 0.5F + 0.35F;
/* 132 */       entityfx.setRBGColorF(f, 0.0F * f, f);
/* 133 */       return entityfx;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\particle\EntitySpellParticleFX.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */