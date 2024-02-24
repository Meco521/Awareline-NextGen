/*     */ package net.optifine.util;
/*     */ 
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import net.optifine.reflect.Reflector;
/*     */ import net.optifine.reflect.ReflectorClass;
/*     */ import net.optifine.reflect.ReflectorField;
/*     */ 
/*     */ 
/*     */ public class ChunkUtils
/*     */ {
/*  17 */   private static final ReflectorClass chunkClass = new ReflectorClass(Chunk.class);
/*  18 */   private static final ReflectorField fieldHasEntities = findFieldHasEntities();
/*  19 */   private static final ReflectorField fieldPrecipitationHeightMap = new ReflectorField(chunkClass, int[].class, 0);
/*     */ 
/*     */   
/*     */   public static boolean hasEntities(Chunk chunk) {
/*  23 */     return Reflector.getFieldValueBoolean(chunk, fieldHasEntities, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getPrecipitationHeight(Chunk chunk, BlockPos pos) {
/*  28 */     int[] aint = (int[])Reflector.getFieldValue(chunk, fieldPrecipitationHeightMap);
/*     */     
/*  30 */     if (aint != null && aint.length == 256) {
/*     */       
/*  32 */       int i = pos.getX() & 0xF;
/*  33 */       int j = pos.getZ() & 0xF;
/*  34 */       int k = i | j << 4;
/*  35 */       int l = aint[k];
/*     */       
/*  37 */       if (l >= 0)
/*     */       {
/*  39 */         return l;
/*     */       }
/*     */ 
/*     */       
/*  43 */       BlockPos blockpos = chunk.getPrecipitationHeight(pos);
/*  44 */       return blockpos.getY();
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  49 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static ReflectorField findFieldHasEntities() {
/*     */     try {
/*  57 */       Chunk chunk = new Chunk((World)null, 0, 0);
/*  58 */       List<Field> list = new ArrayList();
/*  59 */       List<Object> list1 = new ArrayList();
/*  60 */       Field[] afield = Chunk.class.getDeclaredFields();
/*     */       
/*  62 */       for (int i = 0; i < afield.length; i++) {
/*     */         
/*  64 */         Field field = afield[i];
/*     */         
/*  66 */         if (field.getType() == boolean.class) {
/*     */           
/*  68 */           field.setAccessible(true);
/*  69 */           list.add(field);
/*  70 */           list1.add(field.get(chunk));
/*     */         } 
/*     */       } 
/*     */       
/*  74 */       chunk.setHasEntities(false);
/*  75 */       List<Object> list2 = new ArrayList();
/*     */       
/*  77 */       for (Field e : list) {
/*     */         
/*  79 */         Field field1 = e;
/*  80 */         list2.add(field1.get(chunk));
/*     */       } 
/*     */       
/*  83 */       chunk.setHasEntities(true);
/*  84 */       List<Object> list3 = new ArrayList();
/*     */       
/*  86 */       for (Field e : list) {
/*     */         
/*  88 */         Field field2 = e;
/*  89 */         list3.add(field2.get(chunk));
/*     */       } 
/*     */       
/*  92 */       List<Field> list4 = new ArrayList();
/*     */       
/*  94 */       for (int j = 0; j < list.size(); j++) {
/*     */         
/*  96 */         Field field3 = list.get(j);
/*  97 */         Boolean obool = (Boolean)list2.get(j);
/*  98 */         Boolean obool1 = (Boolean)list3.get(j);
/*     */         
/* 100 */         if (!obool.booleanValue() && obool1.booleanValue()) {
/*     */           
/* 102 */           list4.add(field3);
/* 103 */           Boolean obool2 = (Boolean)list1.get(j);
/* 104 */           field3.set(chunk, obool2);
/*     */         } 
/*     */       } 
/*     */       
/* 108 */       if (list4.size() == 1)
/*     */       {
/* 110 */         Field field4 = list4.get(0);
/* 111 */         return new ReflectorField(field4);
/*     */       }
/*     */     
/* 114 */     } catch (Exception exception) {
/*     */       
/* 116 */       Config.warn(exception.getClass().getName() + " " + exception.getMessage());
/*     */     } 
/*     */     
/* 119 */     Config.warn("Error finding Chunk.hasEntities");
/* 120 */     return new ReflectorField(new ReflectorClass(Chunk.class), "hasEntities");
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifin\\util\ChunkUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */