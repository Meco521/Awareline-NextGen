/*    */ package net.minecraft.client.particle;
/*    */ 
/*    */ import net.minecraft.client.renderer.WorldRenderer;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.EnumParticleTypes;
/*    */ import net.minecraft.util.MathHelper;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class EntityLavaFX
/*    */   extends EntityFX
/*    */ {
/*    */   private final float lavaParticleScale;
/*    */   
/*    */   protected EntityLavaFX(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn) {
/* 15 */     super(worldIn, xCoordIn, yCoordIn, zCoordIn, 0.0D, 0.0D, 0.0D);
/* 16 */     this.motionX *= 0.800000011920929D;
/* 17 */     this.motionY *= 0.800000011920929D;
/* 18 */     this.motionZ *= 0.800000011920929D;
/* 19 */     this.motionY = (this.rand.nextFloat() * 0.4F + 0.05F);
/* 20 */     this.particleRed = this.particleGreen = this.particleBlue = 1.0F;
/* 21 */     this.particleScale *= this.rand.nextFloat() * 2.0F + 0.2F;
/* 22 */     this.lavaParticleScale = this.particleScale;
/* 23 */     this.particleMaxAge = (int)(16.0D / (Math.random() * 0.8D + 0.2D));
/* 24 */     this.noClip = false;
/* 25 */     setParticleTextureIndex(49);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getBrightnessForRender(float partialTicks) {
/* 30 */     float f = (this.particleAge + partialTicks) / this.particleMaxAge;
/* 31 */     f = MathHelper.clamp_float(f, 0.0F, 1.0F);
/* 32 */     int i = super.getBrightnessForRender(partialTicks);
/* 33 */     int j = 240;
/* 34 */     int k = i >> 16 & 0xFF;
/* 35 */     return j | k << 16;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public float getBrightness(float partialTicks) {
/* 43 */     return 1.0F;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void renderParticle(WorldRenderer worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
/* 51 */     float f = (this.particleAge + partialTicks) / this.particleMaxAge;
/* 52 */     this.particleScale = this.lavaParticleScale * (1.0F - f * f);
/* 53 */     super.renderParticle(worldRendererIn, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 61 */     this.prevPosX = this.posX;
/* 62 */     this.prevPosY = this.posY;
/* 63 */     this.prevPosZ = this.posZ;
/*    */     
/* 65 */     if (this.particleAge++ >= this.particleMaxAge)
/*    */     {
/* 67 */       setDead();
/*    */     }
/*    */     
/* 70 */     float f = this.particleAge / this.particleMaxAge;
/*    */     
/* 72 */     if (this.rand.nextFloat() > f)
/*    */     {
/* 74 */       this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX, this.posY, this.posZ, this.motionX, this.motionY, this.motionZ, new int[0]);
/*    */     }
/*    */     
/* 77 */     this.motionY -= 0.03D;
/* 78 */     moveEntity(this.motionX, this.motionY, this.motionZ);
/* 79 */     this.motionX *= 0.9990000128746033D;
/* 80 */     this.motionY *= 0.9990000128746033D;
/* 81 */     this.motionZ *= 0.9990000128746033D;
/*    */     
/* 83 */     if (this.onGround) {
/*    */       
/* 85 */       this.motionX *= 0.699999988079071D;
/* 86 */       this.motionZ *= 0.699999988079071D;
/*    */     } 
/*    */   }
/*    */   
/*    */   public static class Factory
/*    */     implements IParticleFactory
/*    */   {
/*    */     public EntityFX getEntityFX(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/* 94 */       return new EntityLavaFX(worldIn, xCoordIn, yCoordIn, zCoordIn);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\particle\EntityLavaFX.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */