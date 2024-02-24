/*    */ package net.minecraft.item;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.potion.Potion;
/*    */ import net.minecraft.potion.PotionEffect;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ public class ItemAppleGold
/*    */   extends ItemFood
/*    */ {
/*    */   public ItemAppleGold(int amount, float saturation, boolean isWolfFood) {
/* 15 */     super(amount, saturation, isWolfFood);
/* 16 */     setHasSubtypes(true);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasEffect(ItemStack stack) {
/* 21 */     return (stack.getMetadata() > 0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public EnumRarity getRarity(ItemStack stack) {
/* 29 */     return (stack.getMetadata() == 0) ? EnumRarity.RARE : EnumRarity.EPIC;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
/* 34 */     if (!worldIn.isRemote)
/*    */     {
/* 36 */       player.addPotionEffect(new PotionEffect(Potion.absorption.id, 2400, 0));
/*    */     }
/*    */     
/* 39 */     if (stack.getMetadata() > 0) {
/*    */       
/* 41 */       if (!worldIn.isRemote)
/*    */       {
/* 43 */         player.addPotionEffect(new PotionEffect(Potion.regeneration.id, 600, 4));
/* 44 */         player.addPotionEffect(new PotionEffect(Potion.resistance.id, 6000, 0));
/* 45 */         player.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 6000, 0));
/*    */       }
/*    */     
/*    */     } else {
/*    */       
/* 50 */       super.onFoodEaten(stack, worldIn, player);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
/* 59 */     subItems.add(new ItemStack(itemIn, 1, 0));
/* 60 */     subItems.add(new ItemStack(itemIn, 1, 1));
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\item\ItemAppleGold.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */