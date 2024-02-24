/*    */ package net.minecraft.client.renderer;
/*    */ 
/*    */ import java.nio.ByteBuffer;
/*    */ import java.nio.ByteOrder;
/*    */ import java.nio.FloatBuffer;
/*    */ import java.nio.IntBuffer;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ import org.lwjgl.util.glu.GLU;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GLAllocation
/*    */ {
/*    */   public static synchronized int generateDisplayLists(int range) {
/* 19 */     int i = GL11.glGenLists(range);
/*    */     
/* 21 */     if (i == 0) {
/*    */       
/* 23 */       int j = GL11.glGetError();
/* 24 */       String s = "No error code reported";
/*    */       
/* 26 */       if (j != 0)
/*    */       {
/* 28 */         s = GLU.gluErrorString(j);
/*    */       }
/*    */       
/* 31 */       throw new IllegalStateException("glGenLists returned an ID of 0 for a count of " + range + ", GL error (" + j + "): " + s);
/*    */     } 
/*    */ 
/*    */     
/* 35 */     return i;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static synchronized void deleteDisplayLists(int list, int range) {
/* 41 */     GL11.glDeleteLists(list, range);
/*    */   }
/*    */ 
/*    */   
/*    */   public static synchronized void deleteDisplayLists(int list) {
/* 46 */     GL11.glDeleteLists(list, 1);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static synchronized ByteBuffer createDirectByteBuffer(int capacity) {
/* 54 */     return ByteBuffer.allocateDirect(capacity).order(ByteOrder.nativeOrder());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static IntBuffer createDirectIntBuffer(int capacity) {
/* 62 */     return createDirectByteBuffer(capacity << 2).asIntBuffer();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static FloatBuffer createDirectFloatBuffer(int capacity) {
/* 71 */     return createDirectByteBuffer(capacity << 2).asFloatBuffer();
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\GLAllocation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */