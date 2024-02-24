/*    */ package awareline.main.ui.gui.altmanager.oldaltmanager;
/*    */ public class Alt {
/*    */   private String mask;
/*    */   private final String username;
/*    */   private String password;
/*    */   
/*  7 */   public String getMask() { return this.mask; }
/*  8 */   public String getUsername() { return this.username; } public String getPassword() {
/*  9 */     return this.password;
/*    */   }
/*    */   public Alt(String username, String password) {
/* 12 */     this(username, password, "");
/*    */   }
/*    */   
/*    */   public Alt(String username, String password, String mask) {
/* 16 */     this.username = username;
/* 17 */     this.password = password;
/* 18 */     this.mask = mask;
/*    */   }
/*    */   
/*    */   public void setMask(String mask) {
/* 22 */     this.mask = mask;
/*    */   }
/*    */   
/*    */   public void setPassword(String password) {
/* 26 */     this.password = password;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\gui\altmanager\oldaltmanager\Alt.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */