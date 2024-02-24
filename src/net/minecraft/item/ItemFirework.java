/*    */ package net.minecraft.item;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.item.EntityFireworkRocket;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.nbt.NBTTagList;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.StatCollector;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemFirework
/*    */   extends Item
/*    */ {
/*    */   public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
/* 22 */     if (!worldIn.isRemote) {
/*    */       
/* 24 */       EntityFireworkRocket entityfireworkrocket = new EntityFireworkRocket(worldIn, (pos.getX() + hitX), (pos.getY() + hitY), (pos.getZ() + hitZ), stack);
/* 25 */       worldIn.spawnEntityInWorld((Entity)entityfireworkrocket);
/*    */       
/* 27 */       if (!playerIn.capabilities.isCreativeMode)
/*    */       {
/* 29 */         stack.stackSize--;
/*    */       }
/*    */       
/* 32 */       return true;
/*    */     } 
/*    */ 
/*    */     
/* 36 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
/* 45 */     if (stack.hasTagCompound()) {
/*    */       
/* 47 */       NBTTagCompound nbttagcompound = stack.getTagCompound().getCompoundTag("Fireworks");
/*    */       
/* 49 */       if (nbttagcompound != null) {
/*    */         
/* 51 */         if (nbttagcompound.hasKey("Flight", 99))
/*    */         {
/* 53 */           tooltip.add(StatCollector.translateToLocal("item.fireworks.flight") + " " + nbttagcompound.getByte("Flight"));
/*    */         }
/*    */         
/* 56 */         NBTTagList nbttaglist = nbttagcompound.getTagList("Explosions", 10);
/*    */         
/* 58 */         if (nbttaglist != null && nbttaglist.tagCount() > 0)
/*    */         {
/* 60 */           for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*    */             
/* 62 */             NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
/* 63 */             List<String> list = Lists.newArrayList();
/* 64 */             ItemFireworkCharge.addExplosionInfo(nbttagcompound1, list);
/*    */             
/* 66 */             if (!list.isEmpty()) {
/*    */               
/* 68 */               for (int j = 1; j < list.size(); j++)
/*    */               {
/* 70 */                 list.set(j, "  " + (String)list.get(j));
/*    */               }
/*    */               
/* 73 */               tooltip.addAll(list);
/*    */             } 
/*    */           } 
/*    */         }
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\item\ItemFirework.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */