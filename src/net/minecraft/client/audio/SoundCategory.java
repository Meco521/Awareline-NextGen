/*    */ package net.minecraft.client.audio;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ public enum SoundCategory
/*    */ {
/*  9 */   MASTER("master", 0),
/* 10 */   MUSIC("music", 1),
/* 11 */   RECORDS("record", 2),
/* 12 */   WEATHER("weather", 3),
/* 13 */   BLOCKS("block", 4),
/* 14 */   MOBS("hostile", 5),
/* 15 */   ANIMALS("neutral", 6),
/* 16 */   PLAYERS("player", 7),
/* 17 */   AMBIENT("ambient", 8);
/*    */   static {
/* 19 */     NAME_CATEGORY_MAP = Maps.newHashMap();
/* 20 */     ID_CATEGORY_MAP = Maps.newHashMap();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 46 */     for (SoundCategory soundcategory : values()) {
/*    */       
/* 48 */       if (NAME_CATEGORY_MAP.containsKey(soundcategory.categoryName) || ID_CATEGORY_MAP.containsKey(Integer.valueOf(soundcategory.categoryId)))
/*    */       {
/* 50 */         throw new Error("Clash in Sound Category ID & Name pools! Cannot insert " + soundcategory);
/*    */       }
/*    */       
/* 53 */       NAME_CATEGORY_MAP.put(soundcategory.categoryName, soundcategory);
/* 54 */       ID_CATEGORY_MAP.put(Integer.valueOf(soundcategory.categoryId), soundcategory);
/*    */     } 
/*    */   }
/*    */   
/*    */   private static final Map<String, SoundCategory> NAME_CATEGORY_MAP;
/*    */   private static final Map<Integer, SoundCategory> ID_CATEGORY_MAP;
/*    */   private final String categoryName;
/*    */   private final int categoryId;
/*    */   
/*    */   SoundCategory(String name, int id) {
/*    */     this.categoryName = name;
/*    */     this.categoryId = id;
/*    */   }
/*    */   
/*    */   public String getCategoryName() {
/*    */     return this.categoryName;
/*    */   }
/*    */   
/*    */   public int getCategoryId() {
/*    */     return this.categoryId;
/*    */   }
/*    */   
/*    */   public static SoundCategory getCategory(String name) {
/*    */     return NAME_CATEGORY_MAP.get(name);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\audio\SoundCategory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */