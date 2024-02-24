/*    */ package net.optifine.texture;
/*    */ 
/*    */ public enum TextureType
/*    */ {
/*  5 */   TEXTURE_1D(3552),
/*  6 */   TEXTURE_2D(3553),
/*  7 */   TEXTURE_3D(32879),
/*  8 */   TEXTURE_RECTANGLE(34037);
/*    */   
/*    */   private final int id;
/*    */ 
/*    */   
/*    */   TextureType(int id) {
/* 14 */     this.id = id;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getId() {
/* 19 */     return this.id;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\texture\TextureType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */