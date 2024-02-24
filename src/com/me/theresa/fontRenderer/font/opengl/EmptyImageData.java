/*    */ package com.me.theresa.fontRenderer.font.opengl;
/*    */ 
/*    */ import java.nio.ByteBuffer;
/*    */ import org.lwjgl.BufferUtils;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EmptyImageData
/*    */   implements ImageData
/*    */ {
/*    */   private final int width;
/*    */   private final int height;
/*    */   
/*    */   public EmptyImageData(int width, int height) {
/* 16 */     this.width = width;
/* 17 */     this.height = height;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getDepth() {
/* 22 */     return 32;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getHeight() {
/* 27 */     return this.height;
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuffer getImageBufferData() {
/* 32 */     return BufferUtils.createByteBuffer(getTexWidth() * getTexHeight() * 4);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getTexHeight() {
/* 37 */     return InternalTextureLoader.get2Fold(this.height);
/*    */   }
/*    */   
/*    */   public int getTexWidth() {
/* 41 */     return InternalTextureLoader.get2Fold(this.width);
/*    */   }
/*    */   
/*    */   public int getWidth() {
/* 45 */     return this.width;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\theresa\fontRenderer\font\opengl\EmptyImageData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */