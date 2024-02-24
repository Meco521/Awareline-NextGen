/*    */ package net.minecraft.item;
/*    */ 
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.stats.StatList;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraft.world.WorldSavedData;
/*    */ import net.minecraft.world.storage.MapData;
/*    */ 
/*    */ public class ItemEmptyMap
/*    */   extends ItemMapBase {
/*    */   protected ItemEmptyMap() {
/* 14 */     setCreativeTab(CreativeTabs.tabMisc);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
/* 22 */     ItemStack itemstack = new ItemStack(Items.filled_map, 1, worldIn.getUniqueDataId("map"));
/* 23 */     String s = "map_" + itemstack.getMetadata();
/* 24 */     MapData mapdata = new MapData(s);
/* 25 */     worldIn.setItemData(s, (WorldSavedData)mapdata);
/* 26 */     mapdata.scale = 0;
/* 27 */     mapdata.calculateMapCenter(playerIn.posX, playerIn.posZ, mapdata.scale);
/* 28 */     mapdata.dimension = (byte)worldIn.provider.getDimensionId();
/* 29 */     mapdata.markDirty();
/* 30 */     itemStackIn.stackSize--;
/*    */     
/* 32 */     if (itemStackIn.stackSize <= 0)
/*    */     {
/* 34 */       return itemstack;
/*    */     }
/*    */ 
/*    */     
/* 38 */     if (!playerIn.inventory.addItemStackToInventory(itemstack.copy()))
/*    */     {
/* 40 */       playerIn.dropPlayerItemWithRandomChoice(itemstack, false);
/*    */     }
/*    */     
/* 43 */     playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
/* 44 */     return itemStackIn;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\item\ItemEmptyMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */