/*    */ package net.minecraft.enchantment;
/*    */ 
/*    */ import net.minecraft.util.WeightedRandom;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EnchantmentData
/*    */   extends WeightedRandom.Item
/*    */ {
/*    */   public final Enchantment enchantmentobj;
/*    */   public final int enchantmentLevel;
/*    */   
/*    */   public EnchantmentData(Enchantment enchantmentObj, int enchLevel) {
/* 15 */     super(enchantmentObj.getWeight());
/* 16 */     this.enchantmentobj = enchantmentObj;
/* 17 */     this.enchantmentLevel = enchLevel;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\enchantment\EnchantmentData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */