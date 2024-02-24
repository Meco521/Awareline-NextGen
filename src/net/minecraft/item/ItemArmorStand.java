/*     */ package net.minecraft.item;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityArmorStand;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.Rotations;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class ItemArmorStand extends Item {
/*     */   public ItemArmorStand() {
/*  18 */     setCreativeTab(CreativeTabs.tabDecorations);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
/*  26 */     if (side == EnumFacing.DOWN)
/*     */     {
/*  28 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  32 */     boolean flag = worldIn.getBlockState(pos).getBlock().isReplaceable(worldIn, pos);
/*  33 */     BlockPos blockpos = flag ? pos : pos.offset(side);
/*     */     
/*  35 */     if (!playerIn.canPlayerEdit(blockpos, side, stack))
/*     */     {
/*  37 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  41 */     BlockPos blockpos1 = blockpos.up();
/*  42 */     boolean flag1 = (!worldIn.isAirBlock(blockpos) && !worldIn.getBlockState(blockpos).getBlock().isReplaceable(worldIn, blockpos));
/*  43 */     int i = flag1 | ((!worldIn.isAirBlock(blockpos1) && !worldIn.getBlockState(blockpos1).getBlock().isReplaceable(worldIn, blockpos1)) ? 1 : 0);
/*     */     
/*  45 */     if (i != 0)
/*     */     {
/*  47 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  51 */     double d0 = blockpos.getX();
/*  52 */     double d1 = blockpos.getY();
/*  53 */     double d2 = blockpos.getZ();
/*  54 */     List<Entity> list = worldIn.getEntitiesWithinAABBExcludingEntity((Entity)null, AxisAlignedBB.fromBounds(d0, d1, d2, d0 + 1.0D, d1 + 2.0D, d2 + 1.0D));
/*     */     
/*  56 */     if (!list.isEmpty())
/*     */     {
/*  58 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  62 */     if (!worldIn.isRemote) {
/*     */       
/*  64 */       worldIn.setBlockToAir(blockpos);
/*  65 */       worldIn.setBlockToAir(blockpos1);
/*  66 */       EntityArmorStand entityarmorstand = new EntityArmorStand(worldIn, d0 + 0.5D, d1, d2 + 0.5D);
/*  67 */       float f = MathHelper.floor_float((MathHelper.wrapAngleTo180_float(playerIn.rotationYaw - 180.0F) + 22.5F) / 45.0F) * 45.0F;
/*  68 */       entityarmorstand.setLocationAndAngles(d0 + 0.5D, d1, d2 + 0.5D, f, 0.0F);
/*  69 */       applyRandomRotations(entityarmorstand, worldIn.rand);
/*  70 */       NBTTagCompound nbttagcompound = stack.getTagCompound();
/*     */       
/*  72 */       if (nbttagcompound != null && nbttagcompound.hasKey("EntityTag", 10)) {
/*     */         
/*  74 */         NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/*  75 */         entityarmorstand.writeToNBTOptional(nbttagcompound1);
/*  76 */         nbttagcompound1.merge(nbttagcompound.getCompoundTag("EntityTag"));
/*  77 */         entityarmorstand.readFromNBT(nbttagcompound1);
/*     */       } 
/*     */       
/*  80 */       worldIn.spawnEntityInWorld((Entity)entityarmorstand);
/*     */     } 
/*     */     
/*  83 */     stack.stackSize--;
/*  84 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void applyRandomRotations(EntityArmorStand armorStand, Random rand) {
/*  93 */     Rotations rotations = armorStand.getHeadRotation();
/*  94 */     float f = rand.nextFloat() * 5.0F;
/*  95 */     float f1 = rand.nextFloat() * 20.0F - 10.0F;
/*  96 */     Rotations rotations1 = new Rotations(rotations.getX() + f, rotations.getY() + f1, rotations.getZ());
/*  97 */     armorStand.setHeadRotation(rotations1);
/*  98 */     rotations = armorStand.getBodyRotation();
/*  99 */     f = rand.nextFloat() * 10.0F - 5.0F;
/* 100 */     rotations1 = new Rotations(rotations.getX(), rotations.getY() + f, rotations.getZ());
/* 101 */     armorStand.setBodyRotation(rotations1);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\item\ItemArmorStand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */