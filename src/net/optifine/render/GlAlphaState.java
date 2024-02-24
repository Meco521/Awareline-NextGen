/*    */ package net.optifine.render;
/*    */ 
/*    */ 
/*    */ public class GlAlphaState
/*    */ {
/*    */   private boolean enabled;
/*    */   private int func;
/*    */   private float ref;
/*    */   
/*    */   public GlAlphaState() {
/* 11 */     this(false, 519, 0.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public GlAlphaState(boolean enabled) {
/* 16 */     this(enabled, 519, 0.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public GlAlphaState(boolean enabled, int func, float ref) {
/* 21 */     this.enabled = enabled;
/* 22 */     this.func = func;
/* 23 */     this.ref = ref;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setState(boolean enabled, int func, float ref) {
/* 28 */     this.enabled = enabled;
/* 29 */     this.func = func;
/* 30 */     this.ref = ref;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setState(GlAlphaState state) {
/* 35 */     this.enabled = state.enabled;
/* 36 */     this.func = state.func;
/* 37 */     this.ref = state.ref;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setFuncRef(int func, float ref) {
/* 42 */     this.func = func;
/* 43 */     this.ref = ref;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setEnabled(boolean enabled) {
/* 48 */     this.enabled = enabled;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setEnabled() {
/* 53 */     this.enabled = true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setDisabled() {
/* 58 */     this.enabled = false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isEnabled() {
/* 63 */     return this.enabled;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getFunc() {
/* 68 */     return this.func;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getRef() {
/* 73 */     return this.ref;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 78 */     return "enabled: " + this.enabled + ", func: " + this.func + ", ref: " + this.ref;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\render\GlAlphaState.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */