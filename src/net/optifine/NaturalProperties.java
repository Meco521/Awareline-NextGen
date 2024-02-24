/*     */ package net.optifine;
/*     */ 
/*     */ import java.util.IdentityHashMap;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MathHelper;
/*     */ 
/*     */ 
/*     */ public class NaturalProperties
/*     */ {
/*  14 */   public int rotation = 1;
/*     */   public boolean flip = false;
/*  16 */   private final Map[] quadMaps = new Map[8];
/*     */ 
/*     */   
/*     */   public NaturalProperties(String type) {
/*  20 */     if (type.equals("4")) {
/*     */       
/*  22 */       this.rotation = 4;
/*     */     }
/*  24 */     else if (type.equals("2")) {
/*     */       
/*  26 */       this.rotation = 2;
/*     */     }
/*  28 */     else if (type.equals("F")) {
/*     */       
/*  30 */       this.flip = true;
/*     */     }
/*  32 */     else if (type.equals("4F")) {
/*     */       
/*  34 */       this.rotation = 4;
/*  35 */       this.flip = true;
/*     */     }
/*  37 */     else if (type.equals("2F")) {
/*     */       
/*  39 */       this.rotation = 2;
/*  40 */       this.flip = true;
/*     */     }
/*     */     else {
/*     */       
/*  44 */       Config.warn("NaturalTextures: Unknown type: " + type);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isValid() {
/*  50 */     return (this.rotation != 2 && this.rotation != 4) ? this.flip : true;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized BakedQuad getQuad(BakedQuad quadIn, int rotate, boolean flipU) {
/*  55 */     int i = rotate;
/*     */     
/*  57 */     if (flipU)
/*     */     {
/*  59 */       i = rotate | 0x4;
/*     */     }
/*     */     
/*  62 */     if (i > 0 && i < this.quadMaps.length) {
/*     */       
/*  64 */       Map<Object, Object> map = this.quadMaps[i];
/*     */       
/*  66 */       if (map == null) {
/*     */         
/*  68 */         map = new IdentityHashMap<>(1);
/*  69 */         this.quadMaps[i] = map;
/*     */       } 
/*     */       
/*  72 */       BakedQuad bakedquad = (BakedQuad)map.get(quadIn);
/*     */       
/*  74 */       if (bakedquad == null) {
/*     */         
/*  76 */         bakedquad = makeQuad(quadIn, rotate, flipU);
/*  77 */         map.put(quadIn, bakedquad);
/*     */       } 
/*     */       
/*  80 */       return bakedquad;
/*     */     } 
/*     */ 
/*     */     
/*  84 */     return quadIn;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private BakedQuad makeQuad(BakedQuad quad, int rotate, boolean flipU) {
/*  90 */     int[] aint = quad.getVertexData();
/*  91 */     int i = quad.getTintIndex();
/*  92 */     EnumFacing enumfacing = quad.getFace();
/*  93 */     TextureAtlasSprite textureatlassprite = quad.getSprite();
/*     */     
/*  95 */     if (!isFullSprite(quad))
/*     */     {
/*  97 */       rotate = 0;
/*     */     }
/*     */     
/* 100 */     aint = transformVertexData(aint, rotate, flipU);
/* 101 */     BakedQuad bakedquad = new BakedQuad(aint, i, enumfacing, textureatlassprite);
/* 102 */     return bakedquad;
/*     */   }
/*     */ 
/*     */   
/*     */   private int[] transformVertexData(int[] vertexData, int rotate, boolean flipU) {
/* 107 */     int[] aint = (int[])vertexData.clone();
/* 108 */     int i = 4 - rotate;
/*     */     
/* 110 */     if (flipU)
/*     */     {
/* 112 */       i += 3;
/*     */     }
/*     */     
/* 115 */     i %= 4;
/* 116 */     int j = aint.length / 4;
/*     */     
/* 118 */     for (int k = 0; k < 4; k++) {
/*     */       
/* 120 */       int l = k * j;
/* 121 */       int i1 = i * j;
/* 122 */       aint[i1 + 4] = vertexData[l + 4];
/* 123 */       aint[i1 + 4 + 1] = vertexData[l + 4 + 1];
/*     */       
/* 125 */       if (flipU) {
/*     */         
/* 127 */         i--;
/*     */         
/* 129 */         if (i < 0)
/*     */         {
/* 131 */           i = 3;
/*     */         }
/*     */       }
/*     */       else {
/*     */         
/* 136 */         i++;
/*     */         
/* 138 */         if (i > 3)
/*     */         {
/* 140 */           i = 0;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 145 */     return aint;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isFullSprite(BakedQuad quad) {
/* 150 */     TextureAtlasSprite textureatlassprite = quad.getSprite();
/* 151 */     float f = textureatlassprite.getMinU();
/* 152 */     float f1 = textureatlassprite.getMaxU();
/* 153 */     float f2 = f1 - f;
/* 154 */     float f3 = f2 / 256.0F;
/* 155 */     float f4 = textureatlassprite.getMinV();
/* 156 */     float f5 = textureatlassprite.getMaxV();
/* 157 */     float f6 = f5 - f4;
/* 158 */     float f7 = f6 / 256.0F;
/* 159 */     int[] aint = quad.getVertexData();
/* 160 */     int i = aint.length / 4;
/*     */     
/* 162 */     for (int j = 0; j < 4; j++) {
/*     */       
/* 164 */       int k = j * i;
/* 165 */       float f8 = Float.intBitsToFloat(aint[k + 4]);
/* 166 */       float f9 = Float.intBitsToFloat(aint[k + 4 + 1]);
/*     */       
/* 168 */       if (!equalsDelta(f8, f, f3) && !equalsDelta(f8, f1, f3))
/*     */       {
/* 170 */         return false;
/*     */       }
/*     */       
/* 173 */       if (!equalsDelta(f9, f4, f7) && !equalsDelta(f9, f5, f7))
/*     */       {
/* 175 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 179 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean equalsDelta(float x1, float x2, float deltaMax) {
/* 184 */     float f = MathHelper.abs(x1 - x2);
/* 185 */     return (f < deltaMax);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\NaturalProperties.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */