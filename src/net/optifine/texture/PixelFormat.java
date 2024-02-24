/*    */ package net.optifine.texture;
/*    */ 
/*    */ public enum PixelFormat
/*    */ {
/*  5 */   RED(6403),
/*  6 */   RG(33319),
/*  7 */   RGB(6407),
/*  8 */   BGR(32992),
/*  9 */   RGBA(6408),
/* 10 */   BGRA(32993),
/* 11 */   RED_INTEGER(36244),
/* 12 */   RG_INTEGER(33320),
/* 13 */   RGB_INTEGER(36248),
/* 14 */   BGR_INTEGER(36250),
/* 15 */   RGBA_INTEGER(36249),
/* 16 */   BGRA_INTEGER(36251);
/*    */   
/*    */   private final int id;
/*    */ 
/*    */   
/*    */   PixelFormat(int id) {
/* 22 */     this.id = id;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getId() {
/* 27 */     return this.id;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\texture\PixelFormat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */