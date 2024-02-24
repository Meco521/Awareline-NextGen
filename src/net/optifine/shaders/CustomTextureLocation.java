/*    */ package net.optifine.shaders;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.texture.AbstractTexture;
/*    */ import net.minecraft.client.renderer.texture.ITextureObject;
/*    */ import net.minecraft.client.renderer.texture.SimpleTexture;
/*    */ import net.minecraft.client.renderer.texture.TextureManager;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class CustomTextureLocation
/*    */   implements ICustomTexture {
/* 12 */   private int textureUnit = -1;
/*    */   private final ResourceLocation location;
/* 14 */   private int variant = 0;
/*    */   
/*    */   private ITextureObject texture;
/*    */   public static final int VARIANT_BASE = 0;
/*    */   public static final int VARIANT_NORMAL = 1;
/*    */   public static final int VARIANT_SPECULAR = 2;
/*    */   
/*    */   public CustomTextureLocation(int textureUnit, ResourceLocation location, int variant) {
/* 22 */     this.textureUnit = textureUnit;
/* 23 */     this.location = location;
/* 24 */     this.variant = variant;
/*    */   }
/*    */ 
/*    */   
/*    */   public ITextureObject getTexture() {
/* 29 */     if (this.texture == null) {
/*    */       
/* 31 */       TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
/* 32 */       this.texture = texturemanager.getTexture(this.location);
/*    */       
/* 34 */       if (this.texture == null) {
/*    */         
/* 36 */         this.texture = (ITextureObject)new SimpleTexture(this.location);
/* 37 */         texturemanager.loadTexture(this.location, this.texture);
/* 38 */         this.texture = texturemanager.getTexture(this.location);
/*    */       } 
/*    */     } 
/*    */     
/* 42 */     return this.texture;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getTextureId() {
/* 47 */     ITextureObject itextureobject = getTexture();
/*    */     
/* 49 */     if (this.variant != 0 && itextureobject instanceof AbstractTexture) {
/*    */       
/* 51 */       AbstractTexture abstracttexture = (AbstractTexture)itextureobject;
/* 52 */       MultiTexID multitexid = abstracttexture.multiTex;
/*    */       
/* 54 */       if (multitexid != null) {
/*    */         
/* 56 */         if (this.variant == 1)
/*    */         {
/* 58 */           return multitexid.norm;
/*    */         }
/*    */         
/* 61 */         if (this.variant == 2)
/*    */         {
/* 63 */           return multitexid.spec;
/*    */         }
/*    */       } 
/*    */     } 
/*    */     
/* 68 */     return itextureobject.getGlTextureId();
/*    */   }
/*    */ 
/*    */   
/*    */   public int getTextureUnit() {
/* 73 */     return this.textureUnit;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void deleteTexture() {}
/*    */ 
/*    */   
/*    */   public int getTarget() {
/* 82 */     return 3553;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 87 */     return "textureUnit: " + this.textureUnit + ", location: " + this.location + ", glTextureId: " + ((this.texture != null) ? (String)Integer.valueOf(this.texture.getGlTextureId()) : "");
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\shaders\CustomTextureLocation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */