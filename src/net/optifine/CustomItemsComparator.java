/*    */ package net.optifine;
/*    */ 
/*    */ import java.util.Comparator;
/*    */ import net.minecraft.src.Config;
/*    */ 
/*    */ 
/*    */ public class CustomItemsComparator
/*    */   implements Comparator
/*    */ {
/*    */   public int compare(Object o1, Object o2) {
/* 11 */     CustomItemProperties customitemproperties = (CustomItemProperties)o1;
/* 12 */     CustomItemProperties customitemproperties1 = (CustomItemProperties)o2;
/* 13 */     return (customitemproperties.weight != customitemproperties1.weight) ? (customitemproperties1.weight - customitemproperties.weight) : (!Config.equals(customitemproperties.basePath, customitemproperties1.basePath) ? customitemproperties.basePath.compareTo(customitemproperties1.basePath) : customitemproperties.name.compareTo(customitemproperties1.name));
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\CustomItemsComparator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */