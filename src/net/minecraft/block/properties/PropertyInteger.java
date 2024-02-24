/*    */ package net.minecraft.block.properties;
/*    */ 
/*    */ import com.google.common.collect.ImmutableSet;
/*    */ import com.google.common.collect.Sets;
/*    */ import java.util.Collection;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ public class PropertyInteger
/*    */   extends PropertyHelper<Integer>
/*    */ {
/*    */   private final ImmutableSet<Integer> allowedValues;
/*    */   
/*    */   protected PropertyInteger(String name, int min, int max) {
/* 15 */     super(name, Integer.class);
/*    */     
/* 17 */     if (min < 0)
/*    */     {
/* 19 */       throw new IllegalArgumentException("Min value of " + name + " must be 0 or greater");
/*    */     }
/* 21 */     if (max <= min)
/*    */     {
/* 23 */       throw new IllegalArgumentException("Max value of " + name + " must be greater than min (" + min + ")");
/*    */     }
/*    */ 
/*    */     
/* 27 */     Set<Integer> set = Sets.newHashSet();
/*    */     
/* 29 */     for (int i = min; i <= max; i++)
/*    */     {
/* 31 */       set.add(Integer.valueOf(i));
/*    */     }
/*    */     
/* 34 */     this.allowedValues = ImmutableSet.copyOf(set);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Collection<Integer> getAllowedValues() {
/* 40 */     return (Collection<Integer>)this.allowedValues;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object p_equals_1_) {
/* 45 */     if (this == p_equals_1_)
/*    */     {
/* 47 */       return true;
/*    */     }
/* 49 */     if (p_equals_1_ != null && getClass() == p_equals_1_.getClass()) {
/*    */       
/* 51 */       if (!super.equals(p_equals_1_))
/*    */       {
/* 53 */         return false;
/*    */       }
/*    */ 
/*    */       
/* 57 */       PropertyInteger propertyinteger = (PropertyInteger)p_equals_1_;
/* 58 */       return this.allowedValues.equals(propertyinteger.allowedValues);
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 63 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 69 */     int i = super.hashCode();
/* 70 */     i = 31 * i + this.allowedValues.hashCode();
/* 71 */     return i;
/*    */   }
/*    */ 
/*    */   
/*    */   public static PropertyInteger create(String name, int min, int max) {
/* 76 */     return new PropertyInteger(name, min, max);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName(Integer value) {
/* 84 */     return value.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\block\properties\PropertyInteger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */