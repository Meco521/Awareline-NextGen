/*    */ package net.minecraft.util;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.enchantment.EnchantmentHelper;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ public class WeightedRandomFishable
/*    */   extends WeightedRandom.Item
/*    */ {
/*    */   private final ItemStack returnStack;
/*    */   private float maxDamagePercent;
/*    */   private boolean enchantable;
/*    */   
/*    */   public WeightedRandomFishable(ItemStack returnStackIn, int itemWeightIn) {
/* 16 */     super(itemWeightIn);
/* 17 */     this.returnStack = returnStackIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack getItemStack(Random random) {
/* 22 */     ItemStack itemstack = this.returnStack.copy();
/*    */     
/* 24 */     if (this.maxDamagePercent > 0.0F) {
/*    */       
/* 26 */       int i = (int)(this.maxDamagePercent * this.returnStack.getMaxDamage());
/* 27 */       int j = itemstack.getMaxDamage() - random.nextInt(random.nextInt(i) + 1);
/*    */       
/* 29 */       if (j > i)
/*    */       {
/* 31 */         j = i;
/*    */       }
/*    */       
/* 34 */       if (j < 1)
/*    */       {
/* 36 */         j = 1;
/*    */       }
/*    */       
/* 39 */       itemstack.setItemDamage(j);
/*    */     } 
/*    */     
/* 42 */     if (this.enchantable)
/*    */     {
/* 44 */       EnchantmentHelper.addRandomEnchantment(random, itemstack, 30);
/*    */     }
/*    */     
/* 47 */     return itemstack;
/*    */   }
/*    */ 
/*    */   
/*    */   public WeightedRandomFishable setMaxDamagePercent(float maxDamagePercentIn) {
/* 52 */     this.maxDamagePercent = maxDamagePercentIn;
/* 53 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public WeightedRandomFishable setEnchantable() {
/* 58 */     this.enchantable = true;
/* 59 */     return this;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraf\\util\WeightedRandomFishable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */