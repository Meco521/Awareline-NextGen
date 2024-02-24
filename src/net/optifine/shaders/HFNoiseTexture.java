/*    */ package net.optifine.shaders;
/*    */ 
/*    */ import java.nio.ByteBuffer;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import org.lwjgl.BufferUtils;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ public class HFNoiseTexture
/*    */   implements ICustomTexture
/*    */ {
/* 11 */   private int texID = GL11.glGenTextures();
/* 12 */   private final int textureUnit = 15;
/*    */ 
/*    */   
/*    */   public HFNoiseTexture(int width, int height) {
/* 16 */     byte[] abyte = genHFNoiseImage(width, height);
/* 17 */     ByteBuffer bytebuffer = BufferUtils.createByteBuffer(abyte.length);
/* 18 */     bytebuffer.put(abyte);
/* 19 */     bytebuffer.flip();
/* 20 */     GlStateManager.bindTexture(this.texID);
/* 21 */     GL11.glTexImage2D(3553, 0, 6407, width, height, 0, 6407, 5121, bytebuffer);
/* 22 */     GL11.glTexParameteri(3553, 10242, 10497);
/* 23 */     GL11.glTexParameteri(3553, 10243, 10497);
/* 24 */     GL11.glTexParameteri(3553, 10240, 9729);
/* 25 */     GL11.glTexParameteri(3553, 10241, 9729);
/* 26 */     GlStateManager.bindTexture(0);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getID() {
/* 31 */     return this.texID;
/*    */   }
/*    */ 
/*    */   
/*    */   public void deleteTexture() {
/* 36 */     GlStateManager.deleteTexture(this.texID);
/* 37 */     this.texID = 0;
/*    */   }
/*    */ 
/*    */   
/*    */   private int random(int seed) {
/* 42 */     seed ^= seed << 13;
/* 43 */     seed ^= seed >> 17;
/* 44 */     seed ^= seed << 5;
/* 45 */     return seed;
/*    */   }
/*    */ 
/*    */   
/*    */   private byte random(int x, int y, int z) {
/* 50 */     int i = (random(x) + random(y * 19)) * random(z * 23) - z;
/* 51 */     return (byte)(random(i) % 128);
/*    */   }
/*    */ 
/*    */   
/*    */   private byte[] genHFNoiseImage(int width, int height) {
/* 56 */     byte[] abyte = new byte[width * height * 3];
/* 57 */     int i = 0;
/*    */     
/* 59 */     for (int j = 0; j < height; j++) {
/*    */       
/* 61 */       for (int k = 0; k < width; k++) {
/*    */         
/* 63 */         for (int l = 1; l < 4; l++)
/*    */         {
/* 65 */           abyte[i++] = random(k, j, l);
/*    */         }
/*    */       } 
/*    */     } 
/*    */     
/* 70 */     return abyte;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getTextureId() {
/* 75 */     return this.texID;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getTextureUnit() {
/* 80 */     getClass(); return 15;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getTarget() {
/* 85 */     return 3553;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\shaders\HFNoiseTexture.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */