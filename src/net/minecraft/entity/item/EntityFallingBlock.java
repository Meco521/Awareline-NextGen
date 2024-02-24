/*     */ package net.minecraft.entity.item;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockAnvil;
/*     */ import net.minecraft.block.BlockFalling;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityFallingBlock
/*     */   extends Entity {
/*     */   private IBlockState fallTile;
/*     */   public int fallTime;
/*  29 */   private int fallHurtMax = 40; public boolean shouldDropItem = true; private boolean canSetAsBlock; private boolean hurtEntities;
/*  30 */   private float fallHurtAmount = 2.0F;
/*     */   
/*     */   public NBTTagCompound tileEntityData;
/*     */   
/*     */   public EntityFallingBlock(World worldIn) {
/*  35 */     super(worldIn);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityFallingBlock(World worldIn, double x, double y, double z, IBlockState fallingBlockState) {
/*  40 */     super(worldIn);
/*  41 */     this.fallTile = fallingBlockState;
/*  42 */     this.preventEntitySpawning = true;
/*  43 */     setSize(0.98F, 0.98F);
/*  44 */     setPosition(x, y, z);
/*  45 */     this.motionX = 0.0D;
/*  46 */     this.motionY = 0.0D;
/*  47 */     this.motionZ = 0.0D;
/*  48 */     this.prevPosX = x;
/*  49 */     this.prevPosY = y;
/*  50 */     this.prevPosZ = z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canTriggerWalking() {
/*  59 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void entityInit() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBeCollidedWith() {
/*  71 */     return !this.isDead;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  79 */     Block block = this.fallTile.getBlock();
/*     */     
/*  81 */     if (block.getMaterial() == Material.air) {
/*     */       
/*  83 */       setDead();
/*     */     }
/*     */     else {
/*     */       
/*  87 */       this.prevPosX = this.posX;
/*  88 */       this.prevPosY = this.posY;
/*  89 */       this.prevPosZ = this.posZ;
/*     */       
/*  91 */       if (this.fallTime++ == 0) {
/*     */         
/*  93 */         BlockPos blockpos = new BlockPos(this);
/*     */         
/*  95 */         if (this.worldObj.getBlockState(blockpos).getBlock() == block) {
/*     */           
/*  97 */           this.worldObj.setBlockToAir(blockpos);
/*     */         }
/*  99 */         else if (!this.worldObj.isRemote) {
/*     */           
/* 101 */           setDead();
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/* 106 */       this.motionY -= 0.03999999910593033D;
/* 107 */       moveEntity(this.motionX, this.motionY, this.motionZ);
/* 108 */       this.motionX *= 0.9800000190734863D;
/* 109 */       this.motionY *= 0.9800000190734863D;
/* 110 */       this.motionZ *= 0.9800000190734863D;
/*     */       
/* 112 */       if (!this.worldObj.isRemote) {
/*     */         
/* 114 */         BlockPos blockpos1 = new BlockPos(this);
/*     */         
/* 116 */         if (this.onGround) {
/*     */           
/* 118 */           this.motionX *= 0.699999988079071D;
/* 119 */           this.motionZ *= 0.699999988079071D;
/* 120 */           this.motionY *= -0.5D;
/*     */           
/* 122 */           if (this.worldObj.getBlockState(blockpos1).getBlock() != Blocks.piston_extension) {
/*     */             
/* 124 */             setDead();
/*     */             
/* 126 */             if (!this.canSetAsBlock)
/*     */             {
/* 128 */               if (this.worldObj.canBlockBePlaced(block, blockpos1, true, EnumFacing.UP, (Entity)null, (ItemStack)null) && !BlockFalling.canFallInto(this.worldObj, blockpos1.down()) && this.worldObj.setBlockState(blockpos1, this.fallTile, 3)) {
/*     */                 
/* 130 */                 if (block instanceof BlockFalling)
/*     */                 {
/* 132 */                   ((BlockFalling)block).onEndFalling(this.worldObj, blockpos1);
/*     */                 }
/*     */                 
/* 135 */                 if (this.tileEntityData != null && block instanceof net.minecraft.block.ITileEntityProvider) {
/*     */                   
/* 137 */                   TileEntity tileentity = this.worldObj.getTileEntity(blockpos1);
/*     */                   
/* 139 */                   if (tileentity != null)
/*     */                   {
/* 141 */                     NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 142 */                     tileentity.writeToNBT(nbttagcompound);
/*     */                     
/* 144 */                     for (String s : this.tileEntityData.getKeySet()) {
/*     */                       
/* 146 */                       NBTBase nbtbase = this.tileEntityData.getTag(s);
/*     */                       
/* 148 */                       if (!s.equals("x") && !s.equals("y") && !s.equals("z"))
/*     */                       {
/* 150 */                         nbttagcompound.setTag(s, nbtbase.copy());
/*     */                       }
/*     */                     } 
/*     */                     
/* 154 */                     tileentity.readFromNBT(nbttagcompound);
/* 155 */                     tileentity.markDirty();
/*     */                   }
/*     */                 
/*     */                 } 
/* 159 */               } else if (this.shouldDropItem && this.worldObj.getGameRules().getBoolean("doEntityDrops")) {
/*     */                 
/* 161 */                 entityDropItem(new ItemStack(block, 1, block.damageDropped(this.fallTile)), 0.0F);
/*     */               }
/*     */             
/*     */             }
/*     */           } 
/* 166 */         } else if ((this.fallTime > 100 && !this.worldObj.isRemote && (blockpos1.getY() < 1 || blockpos1.getY() > 256)) || this.fallTime > 600) {
/*     */           
/* 168 */           if (this.shouldDropItem && this.worldObj.getGameRules().getBoolean("doEntityDrops"))
/*     */           {
/* 170 */             entityDropItem(new ItemStack(block, 1, block.damageDropped(this.fallTile)), 0.0F);
/*     */           }
/*     */           
/* 173 */           setDead();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void fall(float distance, float damageMultiplier) {
/* 181 */     Block block = this.fallTile.getBlock();
/*     */     
/* 183 */     if (this.hurtEntities) {
/*     */       
/* 185 */       int i = MathHelper.ceiling_float_int(distance - 1.0F);
/*     */       
/* 187 */       if (i > 0) {
/*     */         
/* 189 */         List<Entity> list = Lists.newArrayList(this.worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox()));
/* 190 */         boolean flag = (block == Blocks.anvil);
/* 191 */         DamageSource damagesource = flag ? DamageSource.anvil : DamageSource.fallingBlock;
/*     */         
/* 193 */         for (Entity entity : list)
/*     */         {
/* 195 */           entity.attackEntityFrom(damagesource, Math.min(MathHelper.floor_float(i * this.fallHurtAmount), this.fallHurtMax));
/*     */         }
/*     */         
/* 198 */         if (flag && this.rand.nextFloat() < 0.05000000074505806D + i * 0.05D) {
/*     */           
/* 200 */           int j = ((Integer)this.fallTile.getValue((IProperty)BlockAnvil.DAMAGE)).intValue();
/* 201 */           j++;
/*     */           
/* 203 */           if (j > 2) {
/*     */             
/* 205 */             this.canSetAsBlock = true;
/*     */           }
/*     */           else {
/*     */             
/* 209 */             this.fallTile = this.fallTile.withProperty((IProperty)BlockAnvil.DAMAGE, Integer.valueOf(j));
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
/*     */   protected void writeEntityToNBT(NBTTagCompound tagCompound) {
/* 221 */     Block block = (this.fallTile != null) ? this.fallTile.getBlock() : Blocks.air;
/* 222 */     ResourceLocation resourcelocation = (ResourceLocation)Block.blockRegistry.getNameForObject(block);
/* 223 */     tagCompound.setString("Block", (resourcelocation == null) ? "" : resourcelocation.toString());
/* 224 */     tagCompound.setByte("Data", (byte)block.getMetaFromState(this.fallTile));
/* 225 */     tagCompound.setByte("Time", (byte)this.fallTime);
/* 226 */     tagCompound.setBoolean("DropItem", this.shouldDropItem);
/* 227 */     tagCompound.setBoolean("HurtEntities", this.hurtEntities);
/* 228 */     tagCompound.setFloat("FallHurtAmount", this.fallHurtAmount);
/* 229 */     tagCompound.setInteger("FallHurtMax", this.fallHurtMax);
/*     */     
/* 231 */     if (this.tileEntityData != null)
/*     */     {
/* 233 */       tagCompound.setTag("TileEntityData", (NBTBase)this.tileEntityData);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void readEntityFromNBT(NBTTagCompound tagCompund) {
/* 242 */     int i = tagCompund.getByte("Data") & 0xFF;
/*     */     
/* 244 */     if (tagCompund.hasKey("Block", 8)) {
/*     */       
/* 246 */       this.fallTile = Block.getBlockFromName(tagCompund.getString("Block")).getStateFromMeta(i);
/*     */     }
/* 248 */     else if (tagCompund.hasKey("TileID", 99)) {
/*     */       
/* 250 */       this.fallTile = Block.getBlockById(tagCompund.getInteger("TileID")).getStateFromMeta(i);
/*     */     }
/*     */     else {
/*     */       
/* 254 */       this.fallTile = Block.getBlockById(tagCompund.getByte("Tile") & 0xFF).getStateFromMeta(i);
/*     */     } 
/*     */     
/* 257 */     this.fallTime = tagCompund.getByte("Time") & 0xFF;
/* 258 */     Block block = this.fallTile.getBlock();
/*     */     
/* 260 */     if (tagCompund.hasKey("HurtEntities", 99)) {
/*     */       
/* 262 */       this.hurtEntities = tagCompund.getBoolean("HurtEntities");
/* 263 */       this.fallHurtAmount = tagCompund.getFloat("FallHurtAmount");
/* 264 */       this.fallHurtMax = tagCompund.getInteger("FallHurtMax");
/*     */     }
/* 266 */     else if (block == Blocks.anvil) {
/*     */       
/* 268 */       this.hurtEntities = true;
/*     */     } 
/*     */     
/* 271 */     if (tagCompund.hasKey("DropItem", 99))
/*     */     {
/* 273 */       this.shouldDropItem = tagCompund.getBoolean("DropItem");
/*     */     }
/*     */     
/* 276 */     if (tagCompund.hasKey("TileEntityData", 10))
/*     */     {
/* 278 */       this.tileEntityData = tagCompund.getCompoundTag("TileEntityData");
/*     */     }
/*     */     
/* 281 */     if (block == null || block.getMaterial() == Material.air)
/*     */     {
/* 283 */       this.fallTile = Blocks.sand.getDefaultState();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public World getWorldObj() {
/* 289 */     return this.worldObj;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setHurtEntities(boolean p_145806_1_) {
/* 294 */     this.hurtEntities = p_145806_1_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canRenderOnFire() {
/* 302 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addEntityCrashInfo(CrashReportCategory category) {
/* 307 */     super.addEntityCrashInfo(category);
/*     */     
/* 309 */     if (this.fallTile != null) {
/*     */       
/* 311 */       Block block = this.fallTile.getBlock();
/* 312 */       category.addCrashSection("Immitating block ID", Integer.valueOf(Block.getIdFromBlock(block)));
/* 313 */       category.addCrashSection("Immitating block data", Integer.valueOf(block.getMetaFromState(this.fallTile)));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public IBlockState getBlock() {
/* 319 */     return this.fallTile;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\entity\item\EntityFallingBlock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */