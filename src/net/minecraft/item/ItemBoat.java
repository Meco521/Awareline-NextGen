/*     */ package net.minecraft.item;
/*     */ import java.util.List;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityBoat;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class ItemBoat extends Item {
/*     */   public ItemBoat() {
/*  18 */     this.maxStackSize = 1;
/*  19 */     setCreativeTab(CreativeTabs.tabTransport);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
/*  27 */     float f = 1.0F;
/*  28 */     float f1 = playerIn.prevRotationPitch + (playerIn.rotationPitch - playerIn.prevRotationPitch) * f;
/*  29 */     float f2 = playerIn.prevRotationYaw + (playerIn.rotationYaw - playerIn.prevRotationYaw) * f;
/*  30 */     double d0 = playerIn.prevPosX + (playerIn.posX - playerIn.prevPosX) * f;
/*  31 */     double d1 = playerIn.prevPosY + (playerIn.posY - playerIn.prevPosY) * f + playerIn.getEyeHeight();
/*  32 */     double d2 = playerIn.prevPosZ + (playerIn.posZ - playerIn.prevPosZ) * f;
/*  33 */     Vec3 vec3 = new Vec3(d0, d1, d2);
/*  34 */     float f3 = MathHelper.cos(-f2 * 0.017453292F - 3.1415927F);
/*  35 */     float f4 = MathHelper.sin(-f2 * 0.017453292F - 3.1415927F);
/*  36 */     float f5 = -MathHelper.cos(-f1 * 0.017453292F);
/*  37 */     float f6 = MathHelper.sin(-f1 * 0.017453292F);
/*  38 */     float f7 = f4 * f5;
/*  39 */     float f8 = f3 * f5;
/*  40 */     double d3 = 5.0D;
/*  41 */     Vec3 vec31 = vec3.addVector(f7 * d3, f6 * d3, f8 * d3);
/*  42 */     MovingObjectPosition movingobjectposition = worldIn.rayTraceBlocks(vec3, vec31, true);
/*     */     
/*  44 */     if (movingobjectposition == null)
/*     */     {
/*  46 */       return itemStackIn;
/*     */     }
/*     */ 
/*     */     
/*  50 */     Vec3 vec32 = playerIn.getLook(f);
/*  51 */     boolean flag = false;
/*  52 */     float f9 = 1.0F;
/*  53 */     List<Entity> list = worldIn.getEntitiesWithinAABBExcludingEntity((Entity)playerIn, playerIn.getEntityBoundingBox().addCoord(vec32.xCoord * d3, vec32.yCoord * d3, vec32.zCoord * d3).expand(f9, f9, f9));
/*     */     
/*  55 */     for (int i = 0; i < list.size(); i++) {
/*     */       
/*  57 */       Entity entity = list.get(i);
/*     */       
/*  59 */       if (entity.canBeCollidedWith()) {
/*     */         
/*  61 */         float f10 = entity.getCollisionBorderSize();
/*  62 */         AxisAlignedBB axisalignedbb = entity.getEntityBoundingBox().expand(f10, f10, f10);
/*     */         
/*  64 */         if (axisalignedbb.isVecInside(vec3))
/*     */         {
/*  66 */           flag = true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  71 */     if (flag)
/*     */     {
/*  73 */       return itemStackIn;
/*     */     }
/*     */ 
/*     */     
/*  77 */     if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
/*     */       
/*  79 */       BlockPos blockpos = movingobjectposition.getBlockPos();
/*     */       
/*  81 */       if (worldIn.getBlockState(blockpos).getBlock() == Blocks.snow_layer)
/*     */       {
/*  83 */         blockpos = blockpos.down();
/*     */       }
/*     */       
/*  86 */       EntityBoat entityboat = new EntityBoat(worldIn, (blockpos.getX() + 0.5F), (blockpos.getY() + 1.0F), (blockpos.getZ() + 0.5F));
/*  87 */       entityboat.rotationYaw = (((MathHelper.floor_double((playerIn.rotationYaw * 4.0F / 360.0F) + 0.5D) & 0x3) - 1) * 90);
/*     */       
/*  89 */       if (!worldIn.getCollidingBoundingBoxes((Entity)entityboat, entityboat.getEntityBoundingBox().expand(-0.1D, -0.1D, -0.1D)).isEmpty())
/*     */       {
/*  91 */         return itemStackIn;
/*     */       }
/*     */       
/*  94 */       if (!worldIn.isRemote)
/*     */       {
/*  96 */         worldIn.spawnEntityInWorld((Entity)entityboat);
/*     */       }
/*     */       
/*  99 */       if (!playerIn.capabilities.isCreativeMode)
/*     */       {
/* 101 */         itemStackIn.stackSize--;
/*     */       }
/*     */       
/* 104 */       playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
/*     */     } 
/*     */     
/* 107 */     return itemStackIn;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\item\ItemBoat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */