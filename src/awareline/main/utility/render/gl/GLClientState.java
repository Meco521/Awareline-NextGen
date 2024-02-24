/*    */ package awareline.main.utility.render.gl;
/*    */ 
/*    */ public enum GLClientState
/*    */   implements GLenum
/*    */ {
/*    */   private final String name;
/*  7 */   COLOR("GL_COLOR_ARRAY", 32886),
/*  8 */   EDGE("GL_EDGE_FLAG_ARRAY", 32889),
/*  9 */   FOG("GL_FOG_COORD_ARRAY", 33879),
/* 10 */   INDEX("GL_INDEX_ARRAY", 32887),
/* 11 */   NORMAL("GL_NORMAL_ARRAY", 32885),
/* 12 */   SECONDARY_COLOR("GL_SECONDARY_COLOR_ARRAY", 33886),
/* 13 */   TEXTURE("GL_TEXTURE_COORD_ARRAY", 32888),
/* 14 */   VERTEX("GL_VERTEX_ARRAY", 32884); private final int cap;
/*    */   public String getName() {
/* 16 */     return this.name; } public int getCap() {
/* 17 */     return this.cap;
/*    */   }
/*    */   GLClientState(String name, int cap) {
/* 20 */     this.name = name;
/* 21 */     this.cap = cap;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\utility\render\gl\GLClientState.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */