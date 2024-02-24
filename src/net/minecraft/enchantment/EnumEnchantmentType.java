/*    */ package net.minecraft.enchantment;
/*    */ 
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemArmor;
/*    */ 
/*    */ public enum EnumEnchantmentType {
/*  7 */   ALL,
/*  8 */   ARMOR,
/*  9 */   ARMOR_FEET,
/* 10 */   ARMOR_LEGS,
/* 11 */   ARMOR_TORSO,
/* 12 */   ARMOR_HEAD,
/* 13 */   WEAPON,
/* 14 */   DIGGER,
/* 15 */   FISHING_ROD,
/* 16 */   BREAKABLE,
/* 17 */   BOW;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canEnchantItem(Item p_77557_1_) {
/* 24 */     if (this == ALL)
/*    */     {
/* 26 */       return true;
/*    */     }
/* 28 */     if (this == BREAKABLE && p_77557_1_.isDamageable())
/*    */     {
/* 30 */       return true;
/*    */     }
/* 32 */     if (p_77557_1_ instanceof ItemArmor) {
/*    */       
/* 34 */       if (this == ARMOR)
/*    */       {
/* 36 */         return true;
/*    */       }
/*    */ 
/*    */       
/* 40 */       ItemArmor itemarmor = (ItemArmor)p_77557_1_;
/* 41 */       return (itemarmor.armorType == 0) ? ((this == ARMOR_HEAD)) : ((itemarmor.armorType == 2) ? ((this == ARMOR_LEGS)) : ((itemarmor.armorType == 1) ? ((this == ARMOR_TORSO)) : ((itemarmor.armorType == 3) ? ((this == ARMOR_FEET)) : false)));
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 46 */     return (p_77557_1_ instanceof net.minecraft.item.ItemSword) ? ((this == WEAPON)) : ((p_77557_1_ instanceof net.minecraft.item.ItemTool) ? ((this == DIGGER)) : ((p_77557_1_ instanceof net.minecraft.item.ItemBow) ? ((this == BOW)) : ((p_77557_1_ instanceof net.minecraft.item.ItemFishingRod) ? ((this == FISHING_ROD)) : false)));
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\enchantment\EnumEnchantmentType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */