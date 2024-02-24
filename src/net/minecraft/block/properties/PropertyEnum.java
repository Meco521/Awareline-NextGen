/*    */ package net.minecraft.block.properties;
/*    */ 
/*    */ import com.google.common.base.Predicate;
/*    */ import com.google.common.base.Predicates;
/*    */ import com.google.common.collect.Collections2;
/*    */ import com.google.common.collect.ImmutableSet;
/*    */ import com.google.common.collect.Lists;
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.Collection;
/*    */ import java.util.Map;
/*    */ import net.minecraft.util.IStringSerializable;
/*    */ 
/*    */ public class PropertyEnum<T extends Enum<T> & IStringSerializable>
/*    */   extends PropertyHelper<T>
/*    */ {
/*    */   private final ImmutableSet<T> allowedValues;
/* 17 */   private final Map<String, T> nameToValue = Maps.newHashMap();
/*    */ 
/*    */   
/*    */   protected PropertyEnum(String name, Class<T> valueClass, Collection<T> allowedValues) {
/* 21 */     super(name, valueClass);
/* 22 */     this.allowedValues = ImmutableSet.copyOf(allowedValues);
/*    */     
/* 24 */     for (Enum enum_ : allowedValues) {
/*    */       
/* 26 */       String s = ((IStringSerializable)enum_).getName();
/*    */       
/* 28 */       if (this.nameToValue.containsKey(s))
/*    */       {
/* 30 */         throw new IllegalArgumentException("Multiple values have the same name '" + s + "'");
/*    */       }
/*    */       
/* 33 */       this.nameToValue.put(s, (T)enum_);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public Collection<T> getAllowedValues() {
/* 39 */     return (Collection<T>)this.allowedValues;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName(T value) {
/* 47 */     return ((IStringSerializable)value).getName();
/*    */   }
/*    */ 
/*    */   
/*    */   public static <T extends Enum<T> & IStringSerializable> PropertyEnum<T> create(String name, Class<T> clazz) {
/* 52 */     return create(name, clazz, Predicates.alwaysTrue());
/*    */   }
/*    */ 
/*    */   
/*    */   public static <T extends Enum<T> & IStringSerializable> PropertyEnum<T> create(String name, Class<T> clazz, Predicate<T> filter) {
/* 57 */     return create(name, clazz, Collections2.filter(Lists.newArrayList((Object[])clazz.getEnumConstants()), filter));
/*    */   }
/*    */ 
/*    */   
/*    */   public static <T extends Enum<T> & IStringSerializable> PropertyEnum<T> create(String name, Class<T> clazz, T... values) {
/* 62 */     return create(name, clazz, Lists.newArrayList((Object[])values));
/*    */   }
/*    */ 
/*    */   
/*    */   public static <T extends Enum<T> & IStringSerializable> PropertyEnum<T> create(String name, Class<T> clazz, Collection<T> values) {
/* 67 */     return new PropertyEnum<>(name, clazz, values);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\block\properties\PropertyEnum.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */