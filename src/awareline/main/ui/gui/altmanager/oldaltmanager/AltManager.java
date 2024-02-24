/*    */ package awareline.main.ui.gui.altmanager.oldaltmanager;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ public class AltManager {
/*    */   public static List<Alt> alts;
/*    */   
/*    */   public static List<Alt> getAlts() {
/*  9 */     return alts;
/*    */   } static Alt lastAlt; public static Alt getLastAlt() {
/* 11 */     return lastAlt;
/*    */   }
/*    */   
/*    */   public static void init() {
/* 15 */     setupAlts();
/*    */   }
/*    */   
/*    */   public static void setLastAlt(Alt alt) {
/* 19 */     lastAlt = alt;
/*    */   }
/*    */   
/*    */   public static void setupAlts() {
/* 23 */     alts = new ArrayList<>();
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\gui\altmanager\oldaltmanager\AltManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */