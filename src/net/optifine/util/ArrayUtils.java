/*     */ package net.optifine.util;
/*     */ 
/*     */ import java.lang.reflect.Array;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ public class ArrayUtils
/*     */ {
/*     */   public static boolean contains(Object[] arr, Object val) {
/*  13 */     if (arr == null)
/*     */     {
/*  15 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  19 */     for (int i = 0; i < arr.length; i++) {
/*     */       
/*  21 */       Object object = arr[i];
/*     */       
/*  23 */       if (object == val)
/*     */       {
/*  25 */         return true;
/*     */       }
/*     */     } 
/*     */     
/*  29 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int[] addIntsToArray(int[] intArray, int[] copyFrom) {
/*  35 */     if (intArray != null && copyFrom != null) {
/*     */       
/*  37 */       int i = intArray.length;
/*  38 */       int j = i + copyFrom.length;
/*  39 */       int[] aint = new int[j];
/*  40 */       System.arraycopy(intArray, 0, aint, 0, i);
/*     */       
/*  42 */       System.arraycopy(copyFrom, 0, aint, i, copyFrom.length);
/*     */       
/*  44 */       return aint;
/*     */     } 
/*     */ 
/*     */     
/*  48 */     throw new NullPointerException("The given array is NULL");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int[] addIntToArray(int[] intArray, int intValue) {
/*  54 */     return addIntsToArray(intArray, new int[] { intValue });
/*     */   }
/*     */ 
/*     */   
/*     */   public static Object[] addObjectsToArray(Object[] arr, Object[] objs) {
/*  59 */     if (arr == null)
/*     */     {
/*  61 */       throw new NullPointerException("The given array is NULL");
/*     */     }
/*  63 */     if (objs.length == 0)
/*     */     {
/*  65 */       return arr;
/*     */     }
/*     */ 
/*     */     
/*  69 */     int i = arr.length;
/*  70 */     int j = i + objs.length;
/*  71 */     Object[] aobject = (Object[])Array.newInstance(arr.getClass().getComponentType(), j);
/*  72 */     System.arraycopy(arr, 0, aobject, 0, i);
/*  73 */     System.arraycopy(objs, 0, aobject, i, objs.length);
/*  74 */     return aobject;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object[] addObjectToArray(Object[] arr, Object obj) {
/*  80 */     if (arr == null)
/*     */     {
/*  82 */       throw new NullPointerException("The given array is NULL");
/*     */     }
/*     */ 
/*     */     
/*  86 */     int i = arr.length;
/*  87 */     int j = i + 1;
/*  88 */     Object[] aobject = (Object[])Array.newInstance(arr.getClass().getComponentType(), j);
/*  89 */     System.arraycopy(arr, 0, aobject, 0, i);
/*  90 */     aobject[i] = obj;
/*  91 */     return aobject;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object[] addObjectToArray(Object[] arr, Object obj, int index) {
/*  97 */     List<Object> list = new ArrayList(Arrays.asList(arr));
/*  98 */     list.add(index, obj);
/*  99 */     Object[] aobject = (Object[])Array.newInstance(arr.getClass().getComponentType(), list.size());
/* 100 */     return list.toArray(aobject);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String arrayToString(boolean[] arr, String separator) {
/* 105 */     if (arr == null)
/*     */     {
/* 107 */       return "";
/*     */     }
/*     */ 
/*     */     
/* 111 */     StringBuffer stringbuffer = new StringBuffer(arr.length * 5);
/*     */     
/* 113 */     for (int i = 0; i < arr.length; i++) {
/*     */       
/* 115 */       boolean flag = arr[i];
/*     */       
/* 117 */       if (i > 0)
/*     */       {
/* 119 */         stringbuffer.append(separator);
/*     */       }
/*     */       
/* 122 */       stringbuffer.append(flag);
/*     */     } 
/*     */     
/* 125 */     return stringbuffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String arrayToString(float[] arr) {
/* 131 */     return arrayToString(arr, ", ");
/*     */   }
/*     */ 
/*     */   
/*     */   public static String arrayToString(float[] arr, String separator) {
/* 136 */     if (arr == null)
/*     */     {
/* 138 */       return "";
/*     */     }
/*     */ 
/*     */     
/* 142 */     StringBuffer stringbuffer = new StringBuffer(arr.length * 5);
/*     */     
/* 144 */     for (int i = 0; i < arr.length; i++) {
/*     */       
/* 146 */       float f = arr[i];
/*     */       
/* 148 */       if (i > 0)
/*     */       {
/* 150 */         stringbuffer.append(separator);
/*     */       }
/*     */       
/* 153 */       stringbuffer.append(f);
/*     */     } 
/*     */     
/* 156 */     return stringbuffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String arrayToString(float[] arr, String separator, String format) {
/* 162 */     if (arr == null)
/*     */     {
/* 164 */       return "";
/*     */     }
/*     */ 
/*     */     
/* 168 */     StringBuffer stringbuffer = new StringBuffer(arr.length * 5);
/*     */     
/* 170 */     for (int i = 0; i < arr.length; i++) {
/*     */       
/* 172 */       float f = arr[i];
/*     */       
/* 174 */       if (i > 0)
/*     */       {
/* 176 */         stringbuffer.append(separator);
/*     */       }
/*     */       
/* 179 */       stringbuffer.append(String.format(format, new Object[] { Float.valueOf(f) }));
/*     */     } 
/*     */     
/* 182 */     return stringbuffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String arrayToString(int[] arr) {
/* 188 */     return arrayToString(arr, ", ");
/*     */   }
/*     */ 
/*     */   
/*     */   public static String arrayToString(int[] arr, String separator) {
/* 193 */     if (arr == null)
/*     */     {
/* 195 */       return "";
/*     */     }
/*     */ 
/*     */     
/* 199 */     StringBuffer stringbuffer = new StringBuffer(arr.length * 5);
/*     */     
/* 201 */     for (int i = 0; i < arr.length; i++) {
/*     */       
/* 203 */       int j = arr[i];
/*     */       
/* 205 */       if (i > 0)
/*     */       {
/* 207 */         stringbuffer.append(separator);
/*     */       }
/*     */       
/* 210 */       stringbuffer.append(j);
/*     */     } 
/*     */     
/* 213 */     return stringbuffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String arrayToHexString(int[] arr, String separator) {
/* 219 */     if (arr == null)
/*     */     {
/* 221 */       return "";
/*     */     }
/*     */ 
/*     */     
/* 225 */     StringBuffer stringbuffer = new StringBuffer(arr.length * 5);
/*     */     
/* 227 */     for (int i = 0; i < arr.length; i++) {
/*     */       
/* 229 */       int j = arr[i];
/*     */       
/* 231 */       if (i > 0)
/*     */       {
/* 233 */         stringbuffer.append(separator);
/*     */       }
/*     */       
/* 236 */       stringbuffer.append("0x");
/* 237 */       stringbuffer.append(Integer.toHexString(j));
/*     */     } 
/*     */     
/* 240 */     return stringbuffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String arrayToString(Object[] arr) {
/* 246 */     return arrayToString(arr, ", ");
/*     */   }
/*     */ 
/*     */   
/*     */   public static String arrayToString(Object[] arr, String separator) {
/* 251 */     if (arr == null)
/*     */     {
/* 253 */       return "";
/*     */     }
/*     */ 
/*     */     
/* 257 */     StringBuffer stringbuffer = new StringBuffer(arr.length * 5);
/*     */     
/* 259 */     for (int i = 0; i < arr.length; i++) {
/*     */       
/* 261 */       Object object = arr[i];
/*     */       
/* 263 */       if (i > 0)
/*     */       {
/* 265 */         stringbuffer.append(separator);
/*     */       }
/*     */       
/* 268 */       stringbuffer.append(object);
/*     */     } 
/*     */     
/* 271 */     return stringbuffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object[] collectionToArray(Collection coll, Class<?> elementClass) {
/* 277 */     if (coll == null)
/*     */     {
/* 279 */       return null;
/*     */     }
/* 281 */     if (elementClass == null)
/*     */     {
/* 283 */       return null;
/*     */     }
/* 285 */     if (elementClass.isPrimitive())
/*     */     {
/* 287 */       throw new IllegalArgumentException("Can not make arrays with primitive elements (int, double), element class: " + elementClass);
/*     */     }
/*     */ 
/*     */     
/* 291 */     Object[] aobject = (Object[])Array.newInstance(elementClass, coll.size());
/* 292 */     return coll.toArray(aobject);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean equalsOne(int val, int[] vals) {
/* 298 */     for (int i = 0; i < vals.length; i++) {
/*     */       
/* 300 */       if (vals[i] == val)
/*     */       {
/* 302 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 306 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean equalsOne(Object a, Object[] bs) {
/* 311 */     if (bs == null)
/*     */     {
/* 313 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 317 */     for (int i = 0; i < bs.length; i++) {
/*     */       
/* 319 */       Object object = bs[i];
/*     */       
/* 321 */       if (equals(a, object))
/*     */       {
/* 323 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 327 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean equals(Object o1, Object o2) {
/* 333 */     return (o1 == o2) ? true : ((o1 == null) ? false : o1.equals(o2));
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isSameOne(Object a, Object[] bs) {
/* 338 */     if (bs == null)
/*     */     {
/* 340 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 344 */     for (int i = 0; i < bs.length; i++) {
/*     */       
/* 346 */       Object object = bs[i];
/*     */       
/* 348 */       if (a == object)
/*     */       {
/* 350 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 354 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object[] removeObjectFromArray(Object[] arr, Object obj) {
/* 360 */     List list = new ArrayList(Arrays.asList(arr));
/* 361 */     list.remove(obj);
/* 362 */     Object[] aobject = collectionToArray(list, arr.getClass().getComponentType());
/* 363 */     return aobject;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int[] toPrimitive(Integer[] arr) {
/* 368 */     if (arr == null)
/*     */     {
/* 370 */       return null;
/*     */     }
/* 372 */     if (arr.length == 0)
/*     */     {
/* 374 */       return new int[0];
/*     */     }
/*     */ 
/*     */     
/* 378 */     int[] aint = new int[arr.length];
/*     */     
/* 380 */     for (int i = 0; i < aint.length; i++)
/*     */     {
/* 382 */       aint[i] = arr[i].intValue();
/*     */     }
/*     */     
/* 385 */     return aint;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifin\\util\ArrayUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */