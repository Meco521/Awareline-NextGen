/*    */ package net.minecraft.block.properties;
/*    */ 
/*    */ import com.google.common.collect.ImmutableSet;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public class PropertyBool
/*    */   extends PropertyHelper<Boolean>
/*    */ {
/*  9 */   private final ImmutableSet<Boolean> allowedValues = ImmutableSet.of(Boolean.valueOf(true), Boolean.valueOf(false));
/*    */ 
/*    */   
/*    */   protected PropertyBool(String name) {
/* 13 */     super(name, Boolean.class);
/*    */   }
/*    */ 
/*    */   
/*    */   public Collection<Boolean> getAllowedValues() {
/* 18 */     return (Collection<Boolean>)this.allowedValues;
/*    */   }
/*    */ 
/*    */   
/*    */   public static PropertyBool create(String name) {
/* 23 */     return new PropertyBool(name);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName(Boolean value) {
/* 31 */     return value.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\block\properties\PropertyBool.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */