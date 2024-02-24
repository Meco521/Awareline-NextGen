/*    */ package net.minecraft.client.renderer.texture;
/*    */ 
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.optifine.shaders.MultiTexID;
/*    */ import net.optifine.shaders.ShadersTex;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ public abstract class AbstractTexture
/*    */   implements ITextureObject {
/* 10 */   protected int glTextureId = -1;
/*    */   
/*    */   protected boolean blur;
/*    */   protected boolean mipmap;
/*    */   protected boolean blurLast;
/*    */   protected boolean mipmapLast;
/*    */   public MultiTexID multiTex;
/*    */   
/*    */   public void setBlurMipmapDirect(boolean p_174937_1_, boolean p_174937_2_) {
/* 19 */     this.blur = p_174937_1_;
/* 20 */     this.mipmap = p_174937_2_;
/* 21 */     int i = -1;
/* 22 */     int j = -1;
/*    */     
/* 24 */     if (p_174937_1_) {
/*    */       
/* 26 */       i = p_174937_2_ ? 9987 : 9729;
/* 27 */       j = 9729;
/*    */     }
/*    */     else {
/*    */       
/* 31 */       i = p_174937_2_ ? 9986 : 9728;
/* 32 */       j = 9728;
/*    */     } 
/*    */     
/* 35 */     GlStateManager.bindTexture(getGlTextureId());
/* 36 */     GL11.glTexParameteri(3553, 10241, i);
/* 37 */     GL11.glTexParameteri(3553, 10240, j);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setBlurMipmap(boolean p_174936_1_, boolean p_174936_2_) {
/* 42 */     this.blurLast = this.blur;
/* 43 */     this.mipmapLast = this.mipmap;
/* 44 */     setBlurMipmapDirect(p_174936_1_, p_174936_2_);
/*    */   }
/*    */ 
/*    */   
/*    */   public void restoreLastBlurMipmap() {
/* 49 */     setBlurMipmapDirect(this.blurLast, this.mipmapLast);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getGlTextureId() {
/* 54 */     if (this.glTextureId == -1)
/*    */     {
/* 56 */       this.glTextureId = TextureUtil.glGenTextures();
/*    */     }
/*    */     
/* 59 */     return this.glTextureId;
/*    */   }
/*    */ 
/*    */   
/*    */   public void deleteGlTexture() {
/* 64 */     ShadersTex.deleteTextures(this, this.glTextureId);
/*    */     
/* 66 */     if (this.glTextureId != -1) {
/*    */       
/* 68 */       TextureUtil.deleteTexture(this.glTextureId);
/* 69 */       this.glTextureId = -1;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public MultiTexID getMultiTexID() {
/* 75 */     return ShadersTex.getMultiTexID(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\texture\AbstractTexture.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */