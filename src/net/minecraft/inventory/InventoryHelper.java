/*    */ package net.minecraft.inventory;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.item.EntityItem;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ public class InventoryHelper
/*    */ {
/* 14 */   private static final Random RANDOM = new Random();
/*    */ 
/*    */   
/*    */   public static void dropInventoryItems(World worldIn, BlockPos pos, IInventory inventory) {
/* 18 */     dropInventoryItems(worldIn, pos.getX(), pos.getY(), pos.getZ(), inventory);
/*    */   }
/*    */ 
/*    */   
/*    */   public static void dropInventoryItems(World worldIn, Entity entityAt, IInventory inventory) {
/* 23 */     dropInventoryItems(worldIn, entityAt.posX, entityAt.posY, entityAt.posZ, inventory);
/*    */   }
/*    */ 
/*    */   
/*    */   private static void dropInventoryItems(World worldIn, double x, double y, double z, IInventory inventory) {
/* 28 */     for (int i = 0; i < inventory.getSizeInventory(); i++) {
/*    */       
/* 30 */       ItemStack itemstack = inventory.getStackInSlot(i);
/*    */       
/* 32 */       if (itemstack != null)
/*    */       {
/* 34 */         spawnItemStack(worldIn, x, y, z, itemstack);
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private static void spawnItemStack(World worldIn, double x, double y, double z, ItemStack stack) {
/* 41 */     float f = RANDOM.nextFloat() * 0.8F + 0.1F;
/* 42 */     float f1 = RANDOM.nextFloat() * 0.8F + 0.1F;
/* 43 */     float f2 = RANDOM.nextFloat() * 0.8F + 0.1F;
/*    */     
/* 45 */     while (stack.stackSize > 0) {
/*    */       
/* 47 */       int i = RANDOM.nextInt(21) + 10;
/*    */       
/* 49 */       if (i > stack.stackSize)
/*    */       {
/* 51 */         i = stack.stackSize;
/*    */       }
/*    */       
/* 54 */       stack.stackSize -= i;
/* 55 */       EntityItem entityitem = new EntityItem(worldIn, x + f, y + f1, z + f2, new ItemStack(stack.getItem(), i, stack.getMetadata()));
/*    */       
/* 57 */       if (stack.hasTagCompound())
/*    */       {
/* 59 */         entityitem.getEntityItem().setTagCompound((NBTTagCompound)stack.getTagCompound().copy());
/*    */       }
/*    */       
/* 62 */       float f3 = 0.05F;
/* 63 */       entityitem.motionX = RANDOM.nextGaussian() * f3;
/* 64 */       entityitem.motionY = RANDOM.nextGaussian() * f3 + 0.20000000298023224D;
/* 65 */       entityitem.motionZ = RANDOM.nextGaussian() * f3;
/* 66 */       worldIn.spawnEntityInWorld((Entity)entityitem);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\inventory\InventoryHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */