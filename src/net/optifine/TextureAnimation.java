/*     */ package net.optifine;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.Properties;
/*     */ import net.minecraft.client.renderer.GLAllocation;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.texture.ITextureObject;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.optifine.util.TextureUtils;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ public class TextureAnimation
/*     */ {
/*  16 */   private String srcTex = null;
/*  17 */   private String dstTex = null;
/*  18 */   ResourceLocation dstTexLoc = null;
/*  19 */   private int dstTextId = -1;
/*  20 */   private int dstX = 0;
/*  21 */   private int dstY = 0;
/*  22 */   private int frameWidth = 0;
/*  23 */   private int frameHeight = 0;
/*  24 */   private TextureAnimationFrame[] frames = null;
/*  25 */   private int currentFrameIndex = 0;
/*     */   private boolean interpolate = false;
/*  27 */   private int interpolateSkip = 0;
/*  28 */   private ByteBuffer interpolateData = null;
/*  29 */   byte[] srcData = null;
/*  30 */   private ByteBuffer imageData = null;
/*     */   
/*     */   private boolean active = true;
/*     */   private boolean valid = true;
/*     */   
/*     */   public TextureAnimation(String texFrom, byte[] srcData, String texTo, ResourceLocation locTexTo, int dstX, int dstY, int frameWidth, int frameHeight, Properties props) {
/*  36 */     this.srcTex = texFrom;
/*  37 */     this.dstTex = texTo;
/*  38 */     this.dstTexLoc = locTexTo;
/*  39 */     this.dstX = dstX;
/*  40 */     this.dstY = dstY;
/*  41 */     this.frameWidth = frameWidth;
/*  42 */     this.frameHeight = frameHeight;
/*  43 */     int i = frameWidth * frameHeight * 4;
/*     */     
/*  45 */     if (srcData.length % i != 0)
/*     */     {
/*  47 */       Config.warn("Invalid animated texture length: " + srcData.length + ", frameWidth: " + frameWidth + ", frameHeight: " + frameHeight);
/*     */     }
/*     */     
/*  50 */     this.srcData = srcData;
/*  51 */     int j = srcData.length / i;
/*     */     
/*  53 */     if (props.get("tile.0") != null)
/*     */     {
/*  55 */       for (int k = 0; props.get("tile." + k) != null; k++)
/*     */       {
/*  57 */         j = k + 1;
/*     */       }
/*     */     }
/*     */     
/*  61 */     String s2 = (String)props.get("duration");
/*  62 */     int l = Math.max(Config.parseInt(s2, 1), 1);
/*  63 */     this.frames = new TextureAnimationFrame[j];
/*     */     
/*  65 */     for (int i1 = 0; i1 < this.frames.length; i1++) {
/*     */       
/*  67 */       String s = (String)props.get("tile." + i1);
/*  68 */       int j1 = Config.parseInt(s, i1);
/*  69 */       String s1 = (String)props.get("duration." + i1);
/*  70 */       int k1 = Math.max(Config.parseInt(s1, l), 1);
/*  71 */       TextureAnimationFrame textureanimationframe = new TextureAnimationFrame(j1, k1);
/*  72 */       this.frames[i1] = textureanimationframe;
/*     */     } 
/*     */     
/*  75 */     this.interpolate = Config.parseBoolean(props.getProperty("interpolate"), false);
/*  76 */     this.interpolateSkip = Config.parseInt(props.getProperty("skip"), 0);
/*     */     
/*  78 */     if (this.interpolate)
/*     */     {
/*  80 */       this.interpolateData = GLAllocation.createDirectByteBuffer(i);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean nextFrame() {
/*  86 */     TextureAnimationFrame textureanimationframe = getCurrentFrame();
/*     */     
/*  88 */     if (textureanimationframe == null)
/*     */     {
/*  90 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  94 */     textureanimationframe.counter++;
/*     */     
/*  96 */     if (textureanimationframe.counter < textureanimationframe.duration)
/*     */     {
/*  98 */       return this.interpolate;
/*     */     }
/*     */ 
/*     */     
/* 102 */     textureanimationframe.counter = 0;
/* 103 */     this.currentFrameIndex++;
/*     */     
/* 105 */     if (this.currentFrameIndex >= this.frames.length)
/*     */     {
/* 107 */       this.currentFrameIndex = 0;
/*     */     }
/*     */     
/* 110 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TextureAnimationFrame getCurrentFrame() {
/* 117 */     return getFrame(this.currentFrameIndex);
/*     */   }
/*     */ 
/*     */   
/*     */   public TextureAnimationFrame getFrame(int index) {
/* 122 */     if (this.frames.length <= 0)
/*     */     {
/* 124 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 128 */     if (index < 0 || index >= this.frames.length)
/*     */     {
/* 130 */       index = 0;
/*     */     }
/*     */     
/* 133 */     TextureAnimationFrame textureanimationframe = this.frames[index];
/* 134 */     return textureanimationframe;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getFrameCount() {
/* 140 */     return this.frames.length;
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateTexture() {
/* 145 */     if (this.valid) {
/*     */       
/* 147 */       if (this.dstTextId < 0) {
/*     */         
/* 149 */         ITextureObject itextureobject = TextureUtils.getTexture(this.dstTexLoc);
/*     */         
/* 151 */         if (itextureobject == null) {
/*     */           
/* 153 */           this.valid = false;
/*     */           
/*     */           return;
/*     */         } 
/* 157 */         this.dstTextId = itextureobject.getGlTextureId();
/*     */       } 
/*     */       
/* 160 */       if (this.imageData == null) {
/*     */         
/* 162 */         this.imageData = GLAllocation.createDirectByteBuffer(this.srcData.length);
/* 163 */         this.imageData.put(this.srcData);
/* 164 */         this.imageData.flip();
/* 165 */         this.srcData = null;
/*     */       } 
/*     */       
/* 168 */       this.active = SmartAnimations.isActive() ? SmartAnimations.isTextureRendered(this.dstTextId) : true;
/*     */       
/* 170 */       if (nextFrame())
/*     */       {
/* 172 */         if (this.active) {
/*     */           
/* 174 */           int j = this.frameWidth * this.frameHeight * 4;
/* 175 */           TextureAnimationFrame textureanimationframe = getCurrentFrame();
/*     */           
/* 177 */           if (textureanimationframe != null) {
/*     */             
/* 179 */             int i = j * textureanimationframe.index;
/*     */             
/* 181 */             if (i + j <= this.imageData.limit())
/*     */             {
/* 183 */               if (this.interpolate && textureanimationframe.counter > 0) {
/*     */                 
/* 185 */                 if (this.interpolateSkip <= 1 || textureanimationframe.counter % this.interpolateSkip == 0)
/*     */                 {
/* 187 */                   TextureAnimationFrame textureanimationframe1 = getFrame(this.currentFrameIndex + 1);
/* 188 */                   double d0 = textureanimationframe.counter / textureanimationframe.duration;
/* 189 */                   updateTextureInerpolate(textureanimationframe, textureanimationframe1, d0);
/*     */                 }
/*     */               
/*     */               } else {
/*     */                 
/* 194 */                 this.imageData.position(i);
/* 195 */                 GlStateManager.bindTexture(this.dstTextId);
/* 196 */                 GL11.glTexSubImage2D(3553, 0, this.dstX, this.dstY, this.frameWidth, this.frameHeight, 6408, 5121, this.imageData);
/*     */               } 
/*     */             }
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void updateTextureInerpolate(TextureAnimationFrame frame1, TextureAnimationFrame frame2, double dd) {
/* 206 */     int i = this.frameWidth * this.frameHeight * 4;
/* 207 */     int j = i * frame1.index;
/*     */     
/* 209 */     if (j + i <= this.imageData.limit()) {
/* 210 */       int k = i * frame2.index;
/*     */       
/* 212 */       if (k + i <= this.imageData.limit()) {
/* 213 */         this.interpolateData.clear();
/*     */         
/* 215 */         for (int l = 0; l < i; l++) {
/* 216 */           int i1 = this.imageData.get(j + l) & 0xFF;
/* 217 */           int j1 = this.imageData.get(k + l) & 0xFF;
/* 218 */           int k1 = mix(i1, j1, dd);
/* 219 */           byte b0 = (byte)k1;
/* 220 */           this.interpolateData.put(b0);
/*     */         } 
/*     */         
/* 223 */         this.interpolateData.flip();
/* 224 */         GlStateManager.bindTexture(this.dstTextId);
/* 225 */         GL11.glTexSubImage2D(3553, 0, this.dstX, this.dstY, this.frameWidth, this.frameHeight, 6408, 5121, this.interpolateData);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private int mix(int col1, int col2, double k) {
/* 232 */     return (int)(col1 * (1.0D - k) + col2 * k);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSrcTex() {
/* 237 */     return this.srcTex;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDstTex() {
/* 242 */     return this.dstTex;
/*     */   }
/*     */ 
/*     */   
/*     */   public ResourceLocation getDstTexLoc() {
/* 247 */     return this.dstTexLoc;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isActive() {
/* 252 */     return this.active;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\TextureAnimation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */