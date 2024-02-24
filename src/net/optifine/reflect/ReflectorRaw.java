/*     */ package net.optifine.reflect;
/*     */ 
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ReflectorRaw
/*     */ {
/*     */   public static Field getField(Class cls, Class<?> fieldType) {
/*     */     try {
/*  15 */       Field[] afield = cls.getDeclaredFields();
/*     */       
/*  17 */       for (int i = 0; i < afield.length; i++) {
/*     */         
/*  19 */         Field field = afield[i];
/*     */         
/*  21 */         if (field.getType() == fieldType) {
/*     */           
/*  23 */           field.setAccessible(true);
/*  24 */           return field;
/*     */         } 
/*     */       } 
/*     */       
/*  28 */       return null;
/*     */     }
/*  30 */     catch (Exception var5) {
/*     */       
/*  32 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Field[] getFields(Class cls, Class fieldType) {
/*     */     try {
/*  40 */       Field[] afield = cls.getDeclaredFields();
/*  41 */       return getFields(afield, fieldType);
/*     */     }
/*  43 */     catch (Exception var3) {
/*     */       
/*  45 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Field[] getFields(Field[] fields, Class<?> fieldType) {
/*     */     try {
/*  53 */       List<Field> list = new ArrayList();
/*     */       
/*  55 */       for (int i = 0; i < fields.length; i++) {
/*     */         
/*  57 */         Field field = fields[i];
/*     */         
/*  59 */         if (field.getType() == fieldType) {
/*     */           
/*  61 */           field.setAccessible(true);
/*  62 */           list.add(field);
/*     */         } 
/*     */       } 
/*     */       
/*  66 */       Field[] afield = list.<Field>toArray(new Field[0]);
/*  67 */       return afield;
/*     */     }
/*  69 */     catch (Exception var5) {
/*     */       
/*  71 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Field[] getFieldsAfter(Class cls, Field field, Class fieldType) {
/*     */     try {
/*  79 */       Field[] afield = cls.getDeclaredFields();
/*  80 */       List<Field> list = Arrays.asList(afield);
/*  81 */       int i = list.indexOf(field);
/*     */       
/*  83 */       if (i < 0)
/*     */       {
/*  85 */         return new Field[0];
/*     */       }
/*     */ 
/*     */       
/*  89 */       List<Field> list1 = list.subList(i + 1, list.size());
/*  90 */       Field[] afield1 = list1.<Field>toArray(new Field[0]);
/*  91 */       return getFields(afield1, fieldType);
/*     */     
/*     */     }
/*  94 */     catch (Exception var8) {
/*     */       
/*  96 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Field[] getFields(Object obj, Field[] fields, Class<?> fieldType, Object value) {
/*     */     try {
/* 104 */       List<Field> list = new ArrayList<>();
/*     */       
/* 106 */       for (int i = 0; i < fields.length; i++) {
/*     */         
/* 108 */         Field field = fields[i];
/*     */         
/* 110 */         if (field.getType() == fieldType) {
/*     */           
/* 112 */           boolean flag = Modifier.isStatic(field.getModifiers());
/*     */           
/* 114 */           if ((obj != null || flag) && (obj == null || !flag)) {
/*     */             
/* 116 */             field.setAccessible(true);
/* 117 */             Object object = field.get(obj);
/*     */             
/* 119 */             if (object == value) {
/*     */               
/* 121 */               list.add(field);
/*     */             }
/* 123 */             else if (object != null && value != null && object.equals(value)) {
/*     */               
/* 125 */               list.add(field);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 131 */       Field[] afield = list.<Field>toArray(new Field[0]);
/* 132 */       return afield;
/*     */     }
/* 134 */     catch (Exception var9) {
/*     */       
/* 136 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static Field getField(Class cls, Class fieldType, int index) {
/* 142 */     Field[] afield = getFields(cls, fieldType);
/* 143 */     return (index >= 0 && index < afield.length) ? afield[index] : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Field getFieldAfter(Class cls, Field field, Class fieldType, int index) {
/* 148 */     Field[] afield = getFieldsAfter(cls, field, fieldType);
/* 149 */     return (index >= 0 && index < afield.length) ? afield[index] : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Object getFieldValue(Object obj, Class cls, Class fieldType) {
/* 154 */     ReflectorField reflectorfield = getReflectorField(cls, fieldType);
/* 155 */     return (reflectorfield == null) ? null : (!reflectorfield.exists() ? null : Reflector.getFieldValue(obj, reflectorfield));
/*     */   }
/*     */ 
/*     */   
/*     */   public static Object getFieldValue(Object obj, Class cls, Class fieldType, int index) {
/* 160 */     ReflectorField reflectorfield = getReflectorField(cls, fieldType, index);
/* 161 */     return (reflectorfield == null) ? null : (!reflectorfield.exists() ? null : Reflector.getFieldValue(obj, reflectorfield));
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean setFieldValue(Object obj, Class cls, Class fieldType, Object value) {
/* 166 */     ReflectorField reflectorfield = getReflectorField(cls, fieldType);
/* 167 */     return (reflectorfield == null) ? false : (!reflectorfield.exists() ? false : Reflector.setFieldValue(obj, reflectorfield, value));
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean setFieldValue(Object obj, Class cls, Class fieldType, int index, Object value) {
/* 172 */     ReflectorField reflectorfield = getReflectorField(cls, fieldType, index);
/* 173 */     return (reflectorfield == null) ? false : (!reflectorfield.exists() ? false : Reflector.setFieldValue(obj, reflectorfield, value));
/*     */   }
/*     */ 
/*     */   
/*     */   public static ReflectorField getReflectorField(Class cls, Class fieldType) {
/* 178 */     Field field = getField(cls, fieldType);
/*     */     
/* 180 */     if (field == null)
/*     */     {
/* 182 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 186 */     ReflectorClass reflectorclass = new ReflectorClass(cls);
/* 187 */     return new ReflectorField(reflectorclass, field.getName());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static ReflectorField getReflectorField(Class cls, Class fieldType, int index) {
/* 193 */     Field field = getField(cls, fieldType, index);
/*     */     
/* 195 */     if (field == null)
/*     */     {
/* 197 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 201 */     ReflectorClass reflectorclass = new ReflectorClass(cls);
/* 202 */     return new ReflectorField(reflectorclass, field.getName());
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\reflect\ReflectorRaw.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */