/*    */ package net.minecraft.item;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import net.minecraft.block.BlockJukebox;
/*    */ import net.minecraft.block.properties.IProperty;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.stats.StatList;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.StatCollector;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemRecord
/*    */   extends Item {
/* 20 */   private static final Map<String, ItemRecord> RECORDS = Maps.newHashMap();
/*    */ 
/*    */   
/*    */   public final String recordName;
/*    */ 
/*    */   
/*    */   protected ItemRecord(String name) {
/* 27 */     this.recordName = name;
/* 28 */     this.maxStackSize = 1;
/* 29 */     setCreativeTab(CreativeTabs.tabMisc);
/* 30 */     RECORDS.put("records." + name, this);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
/* 38 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/*    */     
/* 40 */     if (iblockstate.getBlock() == Blocks.jukebox && !((Boolean)iblockstate.getValue((IProperty)BlockJukebox.HAS_RECORD)).booleanValue()) {
/*    */       
/* 42 */       if (worldIn.isRemote)
/*    */       {
/* 44 */         return true;
/*    */       }
/*    */ 
/*    */       
/* 48 */       ((BlockJukebox)Blocks.jukebox).insertRecord(worldIn, pos, iblockstate, stack);
/* 49 */       worldIn.playAuxSFXAtEntity((EntityPlayer)null, 1005, pos, Item.getIdFromItem(this));
/* 50 */       stack.stackSize--;
/* 51 */       playerIn.triggerAchievement(StatList.field_181740_X);
/* 52 */       return true;
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 57 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
/* 66 */     tooltip.add(getRecordNameLocal());
/*    */   }
/*    */ 
/*    */   
/*    */   public String getRecordNameLocal() {
/* 71 */     return StatCollector.translateToLocal("item.record." + this.recordName + ".desc");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public EnumRarity getRarity(ItemStack stack) {
/* 79 */     return EnumRarity.RARE;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static ItemRecord getRecord(String name) {
/* 87 */     return RECORDS.get(name);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\item\ItemRecord.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */