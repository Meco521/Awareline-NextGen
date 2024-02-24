/*    */ package net.optifine.reflect;
/*    */ 
/*    */ import java.lang.reflect.Field;
/*    */ import java.util.Arrays;
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.src.Config;
/*    */ 
/*    */ 
/*    */ public class FieldLocatorActionKeyF3
/*    */   implements IFieldLocator
/*    */ {
/*    */   public Field getField() {
/* 15 */     Class<Minecraft> oclass = Minecraft.class;
/* 16 */     Field field = getFieldRenderChunksMany();
/*    */     
/* 18 */     if (field == null) {
/*    */       
/* 20 */       Config.log("(Reflector) Field not present: " + oclass.getName() + ".actionKeyF3 (field renderChunksMany not found)");
/* 21 */       return null;
/*    */     } 
/*    */ 
/*    */     
/* 25 */     Field field1 = ReflectorRaw.getFieldAfter(Minecraft.class, field, boolean.class, 0);
/*    */     
/* 27 */     if (field1 == null) {
/*    */       
/* 29 */       Config.log("(Reflector) Field not present: " + oclass.getName() + ".actionKeyF3");
/* 30 */       return null;
/*    */     } 
/*    */ 
/*    */     
/* 34 */     return field1;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private Field getFieldRenderChunksMany() {
/* 41 */     Minecraft minecraft = Minecraft.getMinecraft();
/* 42 */     boolean flag = minecraft.renderChunksMany;
/* 43 */     Field[] afield = Minecraft.class.getDeclaredFields();
/* 44 */     minecraft.renderChunksMany = true;
/* 45 */     Field[] afield1 = ReflectorRaw.getFields(minecraft, afield, boolean.class, Boolean.TRUE);
/* 46 */     minecraft.renderChunksMany = false;
/* 47 */     Field[] afield2 = ReflectorRaw.getFields(minecraft, afield, boolean.class, Boolean.FALSE);
/* 48 */     minecraft.renderChunksMany = flag;
/* 49 */     Set<Field> set = new HashSet<>(Arrays.asList(afield1));
/* 50 */     Set<Field> set1 = new HashSet<>(Arrays.asList(afield2));
/* 51 */     Set<Field> set2 = new HashSet<>(set);
/* 52 */     set2.retainAll(set1);
/* 53 */     Field[] afield3 = set2.<Field>toArray(new Field[0]);
/* 54 */     return (afield3.length != 1) ? null : afield3[0];
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\reflect\FieldLocatorActionKeyF3.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */