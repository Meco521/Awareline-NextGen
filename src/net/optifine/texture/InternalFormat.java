/*    */ package net.optifine.texture;
/*    */ 
/*    */ public enum InternalFormat
/*    */ {
/*  5 */   R8(33321),
/*  6 */   RG8(33323),
/*  7 */   RGB8(32849),
/*  8 */   RGBA8(32856),
/*  9 */   R8_SNORM(36756),
/* 10 */   RG8_SNORM(36757),
/* 11 */   RGB8_SNORM(36758),
/* 12 */   RGBA8_SNORM(36759),
/* 13 */   R16(33322),
/* 14 */   RG16(33324),
/* 15 */   RGB16(32852),
/* 16 */   RGBA16(32859),
/* 17 */   R16_SNORM(36760),
/* 18 */   RG16_SNORM(36761),
/* 19 */   RGB16_SNORM(36762),
/* 20 */   RGBA16_SNORM(36763),
/* 21 */   R16F(33325),
/* 22 */   RG16F(33327),
/* 23 */   RGB16F(34843),
/* 24 */   RGBA16F(34842),
/* 25 */   R32F(33326),
/* 26 */   RG32F(33328),
/* 27 */   RGB32F(34837),
/* 28 */   RGBA32F(34836),
/* 29 */   R32I(33333),
/* 30 */   RG32I(33339),
/* 31 */   RGB32I(36227),
/* 32 */   RGBA32I(36226),
/* 33 */   R32UI(33334),
/* 34 */   RG32UI(33340),
/* 35 */   RGB32UI(36209),
/* 36 */   RGBA32UI(36208),
/* 37 */   R3_G3_B2(10768),
/* 38 */   RGB5_A1(32855),
/* 39 */   RGB10_A2(32857),
/* 40 */   R11F_G11F_B10F(35898),
/* 41 */   RGB9_E5(35901);
/*    */   
/*    */   private final int id;
/*    */ 
/*    */   
/*    */   InternalFormat(int id) {
/* 47 */     this.id = id;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getId() {
/* 52 */     return this.id;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\texture\InternalFormat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */