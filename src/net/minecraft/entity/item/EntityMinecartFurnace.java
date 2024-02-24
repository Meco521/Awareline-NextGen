/*     */ package net.minecraft.entity.item;
/*     */ import net.minecraft.block.BlockFurnace;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityMinecartFurnace extends EntityMinecart {
/*     */   private int fuel;
/*     */   
/*     */   public EntityMinecartFurnace(World worldIn) {
/*  21 */     super(worldIn);
/*     */   }
/*     */   public double pushX; public double pushZ;
/*     */   
/*     */   public EntityMinecartFurnace(World worldIn, double x, double y, double z) {
/*  26 */     super(worldIn, x, y, z);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityMinecart.EnumMinecartType getMinecartType() {
/*  31 */     return EntityMinecart.EnumMinecartType.FURNACE;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void entityInit() {
/*  36 */     super.entityInit();
/*  37 */     this.dataWatcher.addObject(16, new Byte((byte)0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  45 */     super.onUpdate();
/*     */     
/*  47 */     if (this.fuel > 0)
/*     */     {
/*  49 */       this.fuel--;
/*     */     }
/*     */     
/*  52 */     if (this.fuel <= 0)
/*     */     {
/*  54 */       this.pushX = this.pushZ = 0.0D;
/*     */     }
/*     */     
/*  57 */     setMinecartPowered((this.fuel > 0));
/*     */     
/*  59 */     if (isMinecartPowered() && this.rand.nextInt(4) == 0)
/*     */     {
/*  61 */       this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.posX, this.posY + 0.8D, this.posZ, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected double getMaximumSpeed() {
/*  70 */     return 0.2D;
/*     */   }
/*     */ 
/*     */   
/*     */   public void killMinecart(DamageSource source) {
/*  75 */     super.killMinecart(source);
/*     */     
/*  77 */     if (!source.isExplosion() && this.worldObj.getGameRules().getBoolean("doEntityDrops"))
/*     */     {
/*  79 */       entityDropItem(new ItemStack(Blocks.furnace, 1), 0.0F);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_180460_a(BlockPos p_180460_1_, IBlockState p_180460_2_) {
/*  85 */     super.func_180460_a(p_180460_1_, p_180460_2_);
/*  86 */     double d0 = this.pushX * this.pushX + this.pushZ * this.pushZ;
/*     */     
/*  88 */     if (d0 > 1.0E-4D && this.motionX * this.motionX + this.motionZ * this.motionZ > 0.001D) {
/*     */       
/*  90 */       d0 = MathHelper.sqrt_double(d0);
/*  91 */       this.pushX /= d0;
/*  92 */       this.pushZ /= d0;
/*     */       
/*  94 */       if (this.pushX * this.motionX + this.pushZ * this.motionZ < 0.0D) {
/*     */         
/*  96 */         this.pushX = 0.0D;
/*  97 */         this.pushZ = 0.0D;
/*     */       }
/*     */       else {
/*     */         
/* 101 */         double d1 = d0 / getMaximumSpeed();
/* 102 */         this.pushX *= d1;
/* 103 */         this.pushZ *= d1;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void applyDrag() {
/* 110 */     double d0 = this.pushX * this.pushX + this.pushZ * this.pushZ;
/*     */     
/* 112 */     if (d0 > 1.0E-4D) {
/*     */       
/* 114 */       d0 = MathHelper.sqrt_double(d0);
/* 115 */       this.pushX /= d0;
/* 116 */       this.pushZ /= d0;
/* 117 */       double d1 = 1.0D;
/* 118 */       this.motionX *= 0.800000011920929D;
/* 119 */       this.motionY *= 0.0D;
/* 120 */       this.motionZ *= 0.800000011920929D;
/* 121 */       this.motionX += this.pushX * d1;
/* 122 */       this.motionZ += this.pushZ * d1;
/*     */     }
/*     */     else {
/*     */       
/* 126 */       this.motionX *= 0.9800000190734863D;
/* 127 */       this.motionY *= 0.0D;
/* 128 */       this.motionZ *= 0.9800000190734863D;
/*     */     } 
/*     */     
/* 131 */     super.applyDrag();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean interactFirst(EntityPlayer playerIn) {
/* 139 */     ItemStack itemstack = playerIn.inventory.getCurrentItem();
/*     */     
/* 141 */     if (itemstack != null && itemstack.getItem() == Items.coal) {
/*     */       
/* 143 */       if (!playerIn.capabilities.isCreativeMode && --itemstack.stackSize == 0)
/*     */       {
/* 145 */         playerIn.inventory.setInventorySlotContents(playerIn.inventory.currentItem, (ItemStack)null);
/*     */       }
/*     */       
/* 148 */       this.fuel += 3600;
/*     */     } 
/*     */     
/* 151 */     this.pushX = this.posX - playerIn.posX;
/* 152 */     this.pushZ = this.posZ - playerIn.posZ;
/* 153 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeEntityToNBT(NBTTagCompound tagCompound) {
/* 161 */     super.writeEntityToNBT(tagCompound);
/* 162 */     tagCompound.setDouble("PushX", this.pushX);
/* 163 */     tagCompound.setDouble("PushZ", this.pushZ);
/* 164 */     tagCompound.setShort("Fuel", (short)this.fuel);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void readEntityFromNBT(NBTTagCompound tagCompund) {
/* 172 */     super.readEntityFromNBT(tagCompund);
/* 173 */     this.pushX = tagCompund.getDouble("PushX");
/* 174 */     this.pushZ = tagCompund.getDouble("PushZ");
/* 175 */     this.fuel = tagCompund.getShort("Fuel");
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isMinecartPowered() {
/* 180 */     return ((this.dataWatcher.getWatchableObjectByte(16) & 0x1) != 0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setMinecartPowered(boolean p_94107_1_) {
/* 185 */     if (p_94107_1_) {
/*     */       
/* 187 */       this.dataWatcher.updateObject(16, Byte.valueOf((byte)(this.dataWatcher.getWatchableObjectByte(16) | 0x1)));
/*     */     }
/*     */     else {
/*     */       
/* 191 */       this.dataWatcher.updateObject(16, Byte.valueOf((byte)(this.dataWatcher.getWatchableObjectByte(16) & 0xFFFFFFFE)));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public IBlockState getDefaultDisplayTile() {
/* 197 */     return (isMinecartPowered() ? Blocks.lit_furnace : Blocks.furnace).getDefaultState().withProperty((IProperty)BlockFurnace.FACING, (Comparable)EnumFacing.NORTH);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\entity\item\EntityMinecartFurnace.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */