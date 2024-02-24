/*    */ package com.me.theresa.fontRenderer.font.opengl;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ public class CompositeIOException
/*    */   extends IOException
/*    */ {
/*  9 */   private final ArrayList exceptions = new ArrayList();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void addException(Exception e) {
/* 17 */     this.exceptions.add(e);
/*    */   }
/*    */   
/*    */   public String getMessage() {
/* 21 */     StringBuilder msg = new StringBuilder("Composite Exception: \n");
/* 22 */     for (int i = 0; i < this.exceptions.size(); i++) {
/* 23 */       msg.append("\t").append(((IOException)this.exceptions.get(i)).getMessage()).append("\n");
/*    */     }
/*    */     
/* 26 */     return msg.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\theresa\fontRenderer\font\opengl\CompositeIOException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */