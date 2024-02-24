/*     */ package net.minecraft.client.particle;
/*     */ 
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityDiggingFX
/*     */   extends EntityFX {
/*     */   private final IBlockState sourceState;
/*     */   private BlockPos sourcePos;
/*     */   
/*     */   protected EntityDiggingFX(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, IBlockState state) {
/*  19 */     super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
/*  20 */     this.sourceState = state;
/*  21 */     setParticleIcon(Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getTexture(state));
/*  22 */     this.particleGravity = (state.getBlock()).blockParticleGravity;
/*  23 */     this.particleRed = this.particleGreen = this.particleBlue = 0.6F;
/*  24 */     this.particleScale /= 2.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityDiggingFX setBlockPos(BlockPos pos) {
/*  32 */     this.sourcePos = pos;
/*     */     
/*  34 */     if (this.sourceState.getBlock() == Blocks.grass)
/*     */     {
/*  36 */       return this;
/*     */     }
/*     */ 
/*     */     
/*  40 */     int i = this.sourceState.getBlock().colorMultiplier((IBlockAccess)this.worldObj, pos);
/*  41 */     this.particleRed *= (i >> 16 & 0xFF) / 255.0F;
/*  42 */     this.particleGreen *= (i >> 8 & 0xFF) / 255.0F;
/*  43 */     this.particleBlue *= (i & 0xFF) / 255.0F;
/*  44 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityDiggingFX func_174845_l() {
/*  50 */     this.sourcePos = new BlockPos(this.posX, this.posY, this.posZ);
/*  51 */     Block block = this.sourceState.getBlock();
/*     */     
/*  53 */     if (block == Blocks.grass)
/*     */     {
/*  55 */       return this;
/*     */     }
/*     */ 
/*     */     
/*  59 */     int i = block.getRenderColor(this.sourceState);
/*  60 */     this.particleRed *= (i >> 16 & 0xFF) / 255.0F;
/*  61 */     this.particleGreen *= (i >> 8 & 0xFF) / 255.0F;
/*  62 */     this.particleBlue *= (i & 0xFF) / 255.0F;
/*  63 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getFXLayer() {
/*  69 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderParticle(WorldRenderer worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
/*  77 */     float f = (this.particleTextureIndexX + this.particleTextureJitterX / 4.0F) / 16.0F;
/*  78 */     float f1 = f + 0.015609375F;
/*  79 */     float f2 = (this.particleTextureIndexY + this.particleTextureJitterY / 4.0F) / 16.0F;
/*  80 */     float f3 = f2 + 0.015609375F;
/*  81 */     float f4 = 0.1F * this.particleScale;
/*     */     
/*  83 */     if (this.particleIcon != null) {
/*     */       
/*  85 */       f = this.particleIcon.getInterpolatedU((this.particleTextureJitterX / 4.0F * 16.0F));
/*  86 */       f1 = this.particleIcon.getInterpolatedU(((this.particleTextureJitterX + 1.0F) / 4.0F * 16.0F));
/*  87 */       f2 = this.particleIcon.getInterpolatedV((this.particleTextureJitterY / 4.0F * 16.0F));
/*  88 */       f3 = this.particleIcon.getInterpolatedV(((this.particleTextureJitterY + 1.0F) / 4.0F * 16.0F));
/*     */     } 
/*     */     
/*  91 */     float f5 = (float)(this.prevPosX + (this.posX - this.prevPosX) * partialTicks - interpPosX);
/*  92 */     float f6 = (float)(this.prevPosY + (this.posY - this.prevPosY) * partialTicks - interpPosY);
/*  93 */     float f7 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * partialTicks - interpPosZ);
/*  94 */     int i = getBrightnessForRender(partialTicks);
/*  95 */     int j = i >> 16 & 0xFFFF;
/*  96 */     int k = i & 0xFFFF;
/*  97 */     worldRendererIn.pos((f5 - rotationX * f4 - rotationXY * f4), (f6 - rotationZ * f4), (f7 - rotationYZ * f4 - rotationXZ * f4)).tex(f, f3).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F).lightmap(j, k).endVertex();
/*  98 */     worldRendererIn.pos((f5 - rotationX * f4 + rotationXY * f4), (f6 + rotationZ * f4), (f7 - rotationYZ * f4 + rotationXZ * f4)).tex(f, f2).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F).lightmap(j, k).endVertex();
/*  99 */     worldRendererIn.pos((f5 + rotationX * f4 + rotationXY * f4), (f6 + rotationZ * f4), (f7 + rotationYZ * f4 + rotationXZ * f4)).tex(f1, f2).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F).lightmap(j, k).endVertex();
/* 100 */     worldRendererIn.pos((f5 + rotationX * f4 - rotationXY * f4), (f6 - rotationZ * f4), (f7 + rotationYZ * f4 - rotationXZ * f4)).tex(f1, f3).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F).lightmap(j, k).endVertex();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBrightnessForRender(float partialTicks) {
/* 105 */     int i = super.getBrightnessForRender(partialTicks);
/* 106 */     int j = 0;
/*     */     
/* 108 */     if (this.worldObj.isBlockLoaded(this.sourcePos))
/*     */     {
/* 110 */       j = this.worldObj.getCombinedLight(this.sourcePos, 0);
/*     */     }
/*     */     
/* 113 */     return (i == 0) ? j : i;
/*     */   }
/*     */   
/*     */   public static class Factory
/*     */     implements IParticleFactory
/*     */   {
/*     */     public EntityFX getEntityFX(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/* 120 */       return (new EntityDiggingFX(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn, Block.getStateById(p_178902_15_[0]))).func_174845_l();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\particle\EntityDiggingFX.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */