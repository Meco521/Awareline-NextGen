/*     */ package net.minecraft.world;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import java.util.Set;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.enchantment.EnchantmentProtection;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.item.EntityTNTPrimed;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.Vec3;
/*     */ 
/*     */ public class Explosion
/*     */ {
/*     */   private final boolean isFlaming;
/*     */   private final boolean isSmoking;
/*     */   private final Random explosionRNG;
/*     */   private final World worldObj;
/*     */   private final double explosionX;
/*     */   private final double explosionY;
/*     */   private final double explosionZ;
/*     */   private final Entity exploder;
/*     */   private final float explosionSize;
/*     */   private final List<BlockPos> affectedBlockPositions;
/*     */   private final Map<EntityPlayer, Vec3> playerKnockbackMap;
/*     */   
/*     */   public Explosion(World worldIn, Entity entityIn, double x, double y, double z, float size, List<BlockPos> affectedPositions) {
/*  41 */     this(worldIn, entityIn, x, y, z, size, false, true, affectedPositions);
/*     */   }
/*     */ 
/*     */   
/*     */   public Explosion(World worldIn, Entity entityIn, double x, double y, double z, float size, boolean flaming, boolean smoking, List<BlockPos> affectedPositions) {
/*  46 */     this(worldIn, entityIn, x, y, z, size, flaming, smoking);
/*  47 */     this.affectedBlockPositions.addAll(affectedPositions);
/*     */   }
/*     */ 
/*     */   
/*     */   public Explosion(World worldIn, Entity entityIn, double x, double y, double z, float size, boolean flaming, boolean smoking) {
/*  52 */     this.explosionRNG = new Random();
/*  53 */     this.affectedBlockPositions = Lists.newArrayList();
/*  54 */     this.playerKnockbackMap = Maps.newHashMap();
/*  55 */     this.worldObj = worldIn;
/*  56 */     this.exploder = entityIn;
/*  57 */     this.explosionSize = size;
/*  58 */     this.explosionX = x;
/*  59 */     this.explosionY = y;
/*  60 */     this.explosionZ = z;
/*  61 */     this.isFlaming = flaming;
/*  62 */     this.isSmoking = smoking;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void doExplosionA() {
/*  70 */     Set<BlockPos> set = Sets.newHashSet();
/*  71 */     int i = 16;
/*     */     
/*  73 */     for (int j = 0; j < 16; j++) {
/*     */       
/*  75 */       for (int k = 0; k < 16; k++) {
/*     */         
/*  77 */         for (int l = 0; l < 16; l++) {
/*     */           
/*  79 */           if (j == 0 || j == 15 || k == 0 || k == 15 || l == 0 || l == 15) {
/*     */             
/*  81 */             double d0 = (j / 15.0F * 2.0F - 1.0F);
/*  82 */             double d1 = (k / 15.0F * 2.0F - 1.0F);
/*  83 */             double d2 = (l / 15.0F * 2.0F - 1.0F);
/*  84 */             double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
/*  85 */             d0 /= d3;
/*  86 */             d1 /= d3;
/*  87 */             d2 /= d3;
/*  88 */             float f = this.explosionSize * (0.7F + this.worldObj.rand.nextFloat() * 0.6F);
/*  89 */             double d4 = this.explosionX;
/*  90 */             double d6 = this.explosionY;
/*  91 */             double d8 = this.explosionZ;
/*     */             
/*  93 */             for (float f1 = 0.3F; f > 0.0F; f -= 0.22500001F) {
/*     */               
/*  95 */               BlockPos blockpos = new BlockPos(d4, d6, d8);
/*  96 */               IBlockState iblockstate = this.worldObj.getBlockState(blockpos);
/*     */               
/*  98 */               if (iblockstate.getBlock().getMaterial() != Material.air) {
/*     */                 
/* 100 */                 float f2 = (this.exploder != null) ? this.exploder.getExplosionResistance(this, this.worldObj, blockpos, iblockstate) : iblockstate.getBlock().getExplosionResistance((Entity)null);
/* 101 */                 f -= (f2 + 0.3F) * 0.3F;
/*     */               } 
/*     */               
/* 104 */               if (f > 0.0F && (this.exploder == null || this.exploder.verifyExplosion(this, this.worldObj, blockpos, iblockstate, f)))
/*     */               {
/* 106 */                 set.add(blockpos);
/*     */               }
/*     */               
/* 109 */               d4 += d0 * 0.30000001192092896D;
/* 110 */               d6 += d1 * 0.30000001192092896D;
/* 111 */               d8 += d2 * 0.30000001192092896D;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 118 */     this.affectedBlockPositions.addAll(set);
/* 119 */     float f3 = this.explosionSize * 2.0F;
/* 120 */     int k1 = MathHelper.floor_double(this.explosionX - f3 - 1.0D);
/* 121 */     int l1 = MathHelper.floor_double(this.explosionX + f3 + 1.0D);
/* 122 */     int i2 = MathHelper.floor_double(this.explosionY - f3 - 1.0D);
/* 123 */     int i1 = MathHelper.floor_double(this.explosionY + f3 + 1.0D);
/* 124 */     int j2 = MathHelper.floor_double(this.explosionZ - f3 - 1.0D);
/* 125 */     int j1 = MathHelper.floor_double(this.explosionZ + f3 + 1.0D);
/* 126 */     List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this.exploder, new AxisAlignedBB(k1, i2, j2, l1, i1, j1));
/* 127 */     Vec3 vec3 = new Vec3(this.explosionX, this.explosionY, this.explosionZ);
/*     */     
/* 129 */     for (int k2 = 0; k2 < list.size(); k2++) {
/*     */       
/* 131 */       Entity entity = list.get(k2);
/*     */       
/* 133 */       if (!entity.isImmuneToExplosions()) {
/*     */         
/* 135 */         double d12 = entity.getDistance(this.explosionX, this.explosionY, this.explosionZ) / f3;
/*     */         
/* 137 */         if (d12 <= 1.0D) {
/*     */           
/* 139 */           double d5 = entity.posX - this.explosionX;
/* 140 */           double d7 = entity.posY + entity.getEyeHeight() - this.explosionY;
/* 141 */           double d9 = entity.posZ - this.explosionZ;
/* 142 */           double d13 = MathHelper.sqrt_double(d5 * d5 + d7 * d7 + d9 * d9);
/*     */           
/* 144 */           if (d13 != 0.0D) {
/*     */             
/* 146 */             d5 /= d13;
/* 147 */             d7 /= d13;
/* 148 */             d9 /= d13;
/* 149 */             double d14 = this.worldObj.getBlockDensity(vec3, entity.getEntityBoundingBox());
/* 150 */             double d10 = (1.0D - d12) * d14;
/* 151 */             entity.attackEntityFrom(DamageSource.setExplosionSource(this), (int)((d10 * d10 + d10) / 2.0D * 8.0D * f3 + 1.0D));
/* 152 */             double d11 = EnchantmentProtection.func_92092_a(entity, d10);
/* 153 */             entity.motionX += d5 * d11;
/* 154 */             entity.motionY += d7 * d11;
/* 155 */             entity.motionZ += d9 * d11;
/*     */             
/* 157 */             if (entity instanceof EntityPlayer && !((EntityPlayer)entity).capabilities.disableDamage)
/*     */             {
/* 159 */               this.playerKnockbackMap.put((EntityPlayer)entity, new Vec3(d5 * d10, d7 * d10, d9 * d10));
/*     */             }
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
/*     */   public void doExplosionB(boolean spawnParticles) {
/* 172 */     this.worldObj.playSoundEffect(this.explosionX, this.explosionY, this.explosionZ, "random.explode", 4.0F, (1.0F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
/*     */     
/* 174 */     if (this.explosionSize >= 2.0F && this.isSmoking) {
/*     */       
/* 176 */       this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, this.explosionX, this.explosionY, this.explosionZ, 1.0D, 0.0D, 0.0D, new int[0]);
/*     */     }
/*     */     else {
/*     */       
/* 180 */       this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, this.explosionX, this.explosionY, this.explosionZ, 1.0D, 0.0D, 0.0D, new int[0]);
/*     */     } 
/*     */     
/* 183 */     if (this.isSmoking)
/*     */     {
/* 185 */       for (BlockPos blockpos : this.affectedBlockPositions) {
/*     */         
/* 187 */         Block block = this.worldObj.getBlockState(blockpos).getBlock();
/*     */         
/* 189 */         if (spawnParticles) {
/*     */           
/* 191 */           double d0 = (blockpos.getX() + this.worldObj.rand.nextFloat());
/* 192 */           double d1 = (blockpos.getY() + this.worldObj.rand.nextFloat());
/* 193 */           double d2 = (blockpos.getZ() + this.worldObj.rand.nextFloat());
/* 194 */           double d3 = d0 - this.explosionX;
/* 195 */           double d4 = d1 - this.explosionY;
/* 196 */           double d5 = d2 - this.explosionZ;
/* 197 */           double d6 = MathHelper.sqrt_double(d3 * d3 + d4 * d4 + d5 * d5);
/* 198 */           d3 /= d6;
/* 199 */           d4 /= d6;
/* 200 */           d5 /= d6;
/* 201 */           double d7 = 0.5D / (d6 / this.explosionSize + 0.1D);
/* 202 */           d7 *= (this.worldObj.rand.nextFloat() * this.worldObj.rand.nextFloat() + 0.3F);
/* 203 */           d3 *= d7;
/* 204 */           d4 *= d7;
/* 205 */           d5 *= d7;
/* 206 */           this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, (d0 + this.explosionX) / 2.0D, (d1 + this.explosionY) / 2.0D, (d2 + this.explosionZ) / 2.0D, d3, d4, d5, new int[0]);
/* 207 */           this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2, d3, d4, d5, new int[0]);
/*     */         } 
/*     */         
/* 210 */         if (block.getMaterial() != Material.air) {
/*     */           
/* 212 */           if (block.canDropFromExplosion(this))
/*     */           {
/* 214 */             block.dropBlockAsItemWithChance(this.worldObj, blockpos, this.worldObj.getBlockState(blockpos), 1.0F / this.explosionSize, 0);
/*     */           }
/*     */           
/* 217 */           this.worldObj.setBlockState(blockpos, Blocks.air.getDefaultState(), 3);
/* 218 */           block.onBlockDestroyedByExplosion(this.worldObj, blockpos, this);
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 223 */     if (this.isFlaming)
/*     */     {
/* 225 */       for (BlockPos blockpos1 : this.affectedBlockPositions) {
/*     */         
/* 227 */         if (this.worldObj.getBlockState(blockpos1).getBlock().getMaterial() == Material.air && this.worldObj.getBlockState(blockpos1.down()).getBlock().isFullBlock() && this.explosionRNG.nextInt(3) == 0)
/*     */         {
/* 229 */           this.worldObj.setBlockState(blockpos1, Blocks.fire.getDefaultState());
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<EntityPlayer, Vec3> getPlayerKnockbackMap() {
/* 237 */     return this.playerKnockbackMap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityLivingBase getExplosivePlacedBy() {
/* 245 */     return (this.exploder == null) ? null : ((this.exploder instanceof EntityTNTPrimed) ? ((EntityTNTPrimed)this.exploder).getTntPlacedBy() : ((this.exploder instanceof EntityLivingBase) ? (EntityLivingBase)this.exploder : null));
/*     */   }
/*     */ 
/*     */   
/*     */   public void clearAffectedBlockPositions() {
/* 250 */     this.affectedBlockPositions.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public List<BlockPos> getAffectedBlockPositions() {
/* 255 */     return this.affectedBlockPositions;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\Explosion.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */