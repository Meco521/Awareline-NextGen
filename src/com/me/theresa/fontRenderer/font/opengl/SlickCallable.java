/*    */ package com.me.theresa.fontRenderer.font.opengl;
/*    */ 
/*    */ import com.me.theresa.fontRenderer.font.SlickException;
/*    */ import com.me.theresa.fontRenderer.font.opengl.renderer.Renderer;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class SlickCallable
/*    */ {
/*    */   private static Texture lastUsed;
/*    */   private static boolean inSafe = false;
/*    */   
/*    */   public static void enterSafeBlock() {
/* 16 */     if (inSafe) {
/*    */       return;
/*    */     }
/*    */     
/* 20 */     Renderer.get().flush();
/* 21 */     lastUsed = TextureImpl.getLastBind();
/* 22 */     TextureImpl.bindNone();
/* 23 */     GL11.glPushAttrib(1048575);
/* 24 */     GL11.glPushClientAttrib(-1);
/* 25 */     GL11.glMatrixMode(5888);
/* 26 */     GL11.glPushMatrix();
/* 27 */     GL11.glMatrixMode(5889);
/* 28 */     GL11.glPushMatrix();
/* 29 */     GL11.glMatrixMode(5888);
/*    */     
/* 31 */     inSafe = true;
/*    */   }
/*    */ 
/*    */   
/*    */   public static void leaveSafeBlock() {
/* 36 */     if (!inSafe) {
/*    */       return;
/*    */     }
/*    */     
/* 40 */     GL11.glMatrixMode(5889);
/* 41 */     GL11.glPopMatrix();
/* 42 */     GL11.glMatrixMode(5888);
/* 43 */     GL11.glPopMatrix();
/* 44 */     GL11.glPopClientAttrib();
/* 45 */     GL11.glPopAttrib();
/*    */     
/* 47 */     if (lastUsed != null) {
/* 48 */       lastUsed.bind();
/*    */     } else {
/* 50 */       TextureImpl.bindNone();
/*    */     } 
/*    */     
/* 53 */     inSafe = false;
/*    */   }
/*    */ 
/*    */   
/*    */   public final void call() throws SlickException {
/* 58 */     enterSafeBlock();
/*    */     
/* 60 */     performGLOperations();
/*    */     
/* 62 */     leaveSafeBlock();
/*    */   }
/*    */   
/*    */   protected abstract void performGLOperations();
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\theresa\fontRenderer\font\opengl\SlickCallable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */