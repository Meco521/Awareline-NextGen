/*    */ package net.optifine.shaders;
/*    */ 
/*    */ import net.minecraft.client.renderer.texture.ITextureObject;
/*    */ import net.minecraft.client.renderer.texture.TextureUtil;
/*    */ 
/*    */ public class CustomTexture
/*    */   implements ICustomTexture {
/*  8 */   private int textureUnit = -1;
/*  9 */   private String path = null;
/* 10 */   private ITextureObject texture = null;
/*    */ 
/*    */   
/*    */   public CustomTexture(int textureUnit, String path, ITextureObject texture) {
/* 14 */     this.textureUnit = textureUnit;
/* 15 */     this.path = path;
/* 16 */     this.texture = texture;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getTextureUnit() {
/* 21 */     return this.textureUnit;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getPath() {
/* 26 */     return this.path;
/*    */   }
/*    */ 
/*    */   
/*    */   public ITextureObject getTexture() {
/* 31 */     return this.texture;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getTextureId() {
/* 36 */     return this.texture.getGlTextureId();
/*    */   }
/*    */ 
/*    */   
/*    */   public void deleteTexture() {
/* 41 */     TextureUtil.deleteTexture(this.texture.getGlTextureId());
/*    */   }
/*    */ 
/*    */   
/*    */   public int getTarget() {
/* 46 */     return 3553;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 51 */     return "textureUnit: " + this.textureUnit + ", path: " + this.path + ", glTextureId: " + getTextureId();
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\shaders\CustomTexture.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */