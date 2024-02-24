/*    */ package net.optifine.shaders;
/*    */ 
/*    */ import java.nio.ByteBuffer;
/*    */ import net.optifine.texture.InternalFormat;
/*    */ import net.optifine.texture.PixelFormat;
/*    */ import net.optifine.texture.PixelType;
/*    */ import net.optifine.texture.TextureType;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ import org.lwjgl.opengl.GL12;
/*    */ 
/*    */ 
/*    */ public class CustomTextureRaw
/*    */   implements ICustomTexture
/*    */ {
/*    */   private final TextureType type;
/*    */   private final int textureUnit;
/*    */   private int textureId;
/*    */   
/*    */   public CustomTextureRaw(TextureType type, InternalFormat internalFormat, int width, int height, int depth, PixelFormat pixelFormat, PixelType pixelType, ByteBuffer data, int textureUnit, boolean blur, boolean clamp) {
/* 20 */     this.type = type;
/* 21 */     this.textureUnit = textureUnit;
/* 22 */     this.textureId = GL11.glGenTextures();
/* 23 */     GL11.glBindTexture(getTarget(), this.textureId);
/* 24 */     int i = clamp ? 33071 : 10497;
/* 25 */     int j = blur ? 9729 : 9728;
/*    */     
/* 27 */     switch (type) {
/*    */       
/*    */       case TEXTURE_1D:
/* 30 */         GL11.glTexImage1D(3552, 0, internalFormat.getId(), width, 0, pixelFormat.getId(), pixelType.getId(), data);
/* 31 */         GL11.glTexParameteri(3552, 10242, i);
/* 32 */         GL11.glTexParameteri(3552, 10240, j);
/* 33 */         GL11.glTexParameteri(3552, 10241, j);
/*    */         break;
/*    */       
/*    */       case TEXTURE_2D:
/* 37 */         GL11.glTexImage2D(3553, 0, internalFormat.getId(), width, height, 0, pixelFormat.getId(), pixelType.getId(), data);
/* 38 */         GL11.glTexParameteri(3553, 10242, i);
/* 39 */         GL11.glTexParameteri(3553, 10243, i);
/* 40 */         GL11.glTexParameteri(3553, 10240, j);
/* 41 */         GL11.glTexParameteri(3553, 10241, j);
/*    */         break;
/*    */       
/*    */       case TEXTURE_3D:
/* 45 */         GL12.glTexImage3D(32879, 0, internalFormat.getId(), width, height, depth, 0, pixelFormat.getId(), pixelType.getId(), data);
/* 46 */         GL11.glTexParameteri(32879, 10242, i);
/* 47 */         GL11.glTexParameteri(32879, 10243, i);
/* 48 */         GL11.glTexParameteri(32879, 32882, i);
/* 49 */         GL11.glTexParameteri(32879, 10240, j);
/* 50 */         GL11.glTexParameteri(32879, 10241, j);
/*    */         break;
/*    */       
/*    */       case TEXTURE_RECTANGLE:
/* 54 */         GL11.glTexImage2D(34037, 0, internalFormat.getId(), width, height, 0, pixelFormat.getId(), pixelType.getId(), data);
/* 55 */         GL11.glTexParameteri(34037, 10242, i);
/* 56 */         GL11.glTexParameteri(34037, 10243, i);
/* 57 */         GL11.glTexParameteri(34037, 10240, j);
/* 58 */         GL11.glTexParameteri(34037, 10241, j);
/*    */         break;
/*    */     } 
/* 61 */     GL11.glBindTexture(getTarget(), 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getTarget() {
/* 66 */     return this.type.getId();
/*    */   }
/*    */ 
/*    */   
/*    */   public int getTextureId() {
/* 71 */     return this.textureId;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getTextureUnit() {
/* 76 */     return this.textureUnit;
/*    */   }
/*    */ 
/*    */   
/*    */   public void deleteTexture() {
/* 81 */     if (this.textureId > 0) {
/*    */       
/* 83 */       GL11.glDeleteTextures(this.textureId);
/* 84 */       this.textureId = 0;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\shaders\CustomTextureRaw.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */