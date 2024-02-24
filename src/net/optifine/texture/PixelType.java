/*    */ package net.optifine.texture;
/*    */ 
/*    */ public enum PixelType
/*    */ {
/*  5 */   BYTE(5120),
/*  6 */   SHORT(5122),
/*  7 */   INT(5124),
/*  8 */   HALF_FLOAT(5131),
/*  9 */   FLOAT(5126),
/* 10 */   UNSIGNED_BYTE(5121),
/* 11 */   UNSIGNED_BYTE_3_3_2(32818),
/* 12 */   UNSIGNED_BYTE_2_3_3_REV(33634),
/* 13 */   UNSIGNED_SHORT(5123),
/* 14 */   UNSIGNED_SHORT_5_6_5(33635),
/* 15 */   UNSIGNED_SHORT_5_6_5_REV(33636),
/* 16 */   UNSIGNED_SHORT_4_4_4_4(32819),
/* 17 */   UNSIGNED_SHORT_4_4_4_4_REV(33637),
/* 18 */   UNSIGNED_SHORT_5_5_5_1(32820),
/* 19 */   UNSIGNED_SHORT_1_5_5_5_REV(33638),
/* 20 */   UNSIGNED_INT(5125),
/* 21 */   UNSIGNED_INT_8_8_8_8(32821),
/* 22 */   UNSIGNED_INT_8_8_8_8_REV(33639),
/* 23 */   UNSIGNED_INT_10_10_10_2(32822),
/* 24 */   UNSIGNED_INT_2_10_10_10_REV(33640);
/*    */   
/*    */   private final int id;
/*    */ 
/*    */   
/*    */   PixelType(int id) {
/* 30 */     this.id = id;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getId() {
/* 35 */     return this.id;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\texture\PixelType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */