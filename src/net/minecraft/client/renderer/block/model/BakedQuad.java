/*     */ package net.minecraft.client.renderer.block.model;
/*     */ 
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraftforge.client.model.pipeline.IVertexConsumer;
/*     */ import net.minecraftforge.client.model.pipeline.IVertexProducer;
/*     */ import net.optifine.model.QuadBounds;
/*     */ import net.optifine.reflect.Reflector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BakedQuad
/*     */   implements IVertexProducer
/*     */ {
/*     */   protected int[] vertexData;
/*     */   protected final int tintIndex;
/*     */   protected EnumFacing face;
/*     */   protected TextureAtlasSprite sprite;
/*  22 */   private int[] vertexDataSingle = null;
/*     */   
/*     */   private QuadBounds quadBounds;
/*     */   private boolean quadEmissiveChecked;
/*     */   private BakedQuad quadEmissive;
/*     */   
/*     */   public BakedQuad(int[] p_i3_1_, int p_i3_2_, EnumFacing p_i3_3_, TextureAtlasSprite p_i3_4_) {
/*  29 */     this.vertexData = p_i3_1_;
/*  30 */     this.tintIndex = p_i3_2_;
/*  31 */     this.face = p_i3_3_;
/*  32 */     this.sprite = p_i3_4_;
/*  33 */     fixVertexData();
/*     */   }
/*     */ 
/*     */   
/*     */   public BakedQuad(int[] vertexDataIn, int tintIndexIn, EnumFacing faceIn) {
/*  38 */     this.vertexData = vertexDataIn;
/*  39 */     this.tintIndex = tintIndexIn;
/*  40 */     this.face = faceIn;
/*  41 */     fixVertexData();
/*     */   }
/*     */ 
/*     */   
/*     */   public TextureAtlasSprite getSprite() {
/*  46 */     if (this.sprite == null)
/*     */     {
/*  48 */       this.sprite = getSpriteByUv(getVertexData());
/*     */     }
/*     */     
/*  51 */     return this.sprite;
/*     */   }
/*     */ 
/*     */   
/*     */   public int[] getVertexData() {
/*  56 */     fixVertexData();
/*  57 */     return this.vertexData;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasTintIndex() {
/*  62 */     return (this.tintIndex != -1);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getTintIndex() {
/*  67 */     return this.tintIndex;
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumFacing getFace() {
/*  72 */     if (this.face == null)
/*     */     {
/*  74 */       this.face = FaceBakery.getFacingFromVertexData(getVertexData());
/*     */     }
/*     */     
/*  77 */     return this.face;
/*     */   }
/*     */ 
/*     */   
/*     */   public int[] getVertexDataSingle() {
/*  82 */     if (this.vertexDataSingle == null)
/*     */     {
/*  84 */       this.vertexDataSingle = makeVertexDataSingle(getVertexData(), getSprite());
/*     */     }
/*     */     
/*  87 */     return this.vertexDataSingle;
/*     */   }
/*     */ 
/*     */   
/*     */   private static int[] makeVertexDataSingle(int[] p_makeVertexDataSingle_0_, TextureAtlasSprite p_makeVertexDataSingle_1_) {
/*  92 */     int[] aint = (int[])p_makeVertexDataSingle_0_.clone();
/*  93 */     int i = aint.length / 4;
/*     */     
/*  95 */     for (int j = 0; j < 4; j++) {
/*     */       
/*  97 */       int k = j * i;
/*  98 */       float f = Float.intBitsToFloat(aint[k + 4]);
/*  99 */       float f1 = Float.intBitsToFloat(aint[k + 4 + 1]);
/* 100 */       float f2 = p_makeVertexDataSingle_1_.toSingleU(f);
/* 101 */       float f3 = p_makeVertexDataSingle_1_.toSingleV(f1);
/* 102 */       aint[k + 4] = Float.floatToRawIntBits(f2);
/* 103 */       aint[k + 4 + 1] = Float.floatToRawIntBits(f3);
/*     */     } 
/*     */     
/* 106 */     return aint;
/*     */   }
/*     */ 
/*     */   
/*     */   public void pipe(IVertexConsumer p_pipe_1_) {
/* 111 */     Reflector.callVoid(Reflector.LightUtil_putBakedQuad, new Object[] { p_pipe_1_, this });
/*     */   }
/*     */ 
/*     */   
/*     */   private static TextureAtlasSprite getSpriteByUv(int[] p_getSpriteByUv_0_) {
/* 116 */     float f = 1.0F;
/* 117 */     float f1 = 1.0F;
/* 118 */     float f2 = 0.0F;
/* 119 */     float f3 = 0.0F;
/* 120 */     int i = p_getSpriteByUv_0_.length / 4;
/*     */     
/* 122 */     for (int j = 0; j < 4; j++) {
/*     */       
/* 124 */       int k = j * i;
/* 125 */       float f4 = Float.intBitsToFloat(p_getSpriteByUv_0_[k + 4]);
/* 126 */       float f5 = Float.intBitsToFloat(p_getSpriteByUv_0_[k + 4 + 1]);
/* 127 */       f = Math.min(f, f4);
/* 128 */       f1 = Math.min(f1, f5);
/* 129 */       f2 = Math.max(f2, f4);
/* 130 */       f3 = Math.max(f3, f5);
/*     */     } 
/*     */     
/* 133 */     float f6 = (f + f2) / 2.0F;
/* 134 */     float f7 = (f1 + f3) / 2.0F;
/* 135 */     return Minecraft.getMinecraft().getTextureMapBlocks().getIconByUV(f6, f7);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void fixVertexData() {
/* 140 */     if (Config.isShaders()) {
/*     */       
/* 142 */       if (this.vertexData.length == 28)
/*     */       {
/* 144 */         this.vertexData = expandVertexData(this.vertexData);
/*     */       }
/*     */     }
/* 147 */     else if (this.vertexData.length == 56) {
/*     */       
/* 149 */       this.vertexData = compactVertexData(this.vertexData);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static int[] expandVertexData(int[] p_expandVertexData_0_) {
/* 155 */     int i = p_expandVertexData_0_.length / 4;
/* 156 */     int j = i << 1;
/* 157 */     int[] aint = new int[j << 2];
/*     */     
/* 159 */     for (int k = 0; k < 4; k++)
/*     */     {
/* 161 */       System.arraycopy(p_expandVertexData_0_, k * i, aint, k * j, i);
/*     */     }
/*     */     
/* 164 */     return aint;
/*     */   }
/*     */ 
/*     */   
/*     */   private static int[] compactVertexData(int[] p_compactVertexData_0_) {
/* 169 */     int i = p_compactVertexData_0_.length / 4;
/* 170 */     int j = i / 2;
/* 171 */     int[] aint = new int[j << 2];
/*     */     
/* 173 */     for (int k = 0; k < 4; k++)
/*     */     {
/* 175 */       System.arraycopy(p_compactVertexData_0_, k * i, aint, k * j, j);
/*     */     }
/*     */     
/* 178 */     return aint;
/*     */   }
/*     */ 
/*     */   
/*     */   public QuadBounds getQuadBounds() {
/* 183 */     if (this.quadBounds == null)
/*     */     {
/* 185 */       this.quadBounds = new QuadBounds(getVertexData());
/*     */     }
/*     */     
/* 188 */     return this.quadBounds;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getMidX() {
/* 193 */     QuadBounds quadbounds = getQuadBounds();
/* 194 */     return (quadbounds.getMaxX() + quadbounds.getMinX()) / 2.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getMidY() {
/* 199 */     QuadBounds quadbounds = getQuadBounds();
/* 200 */     return ((quadbounds.getMaxY() + quadbounds.getMinY()) / 2.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public double getMidZ() {
/* 205 */     QuadBounds quadbounds = getQuadBounds();
/* 206 */     return ((quadbounds.getMaxZ() + quadbounds.getMinZ()) / 2.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFaceQuad() {
/* 211 */     QuadBounds quadbounds = getQuadBounds();
/* 212 */     return quadbounds.isFaceQuad(this.face);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullQuad() {
/* 217 */     QuadBounds quadbounds = getQuadBounds();
/* 218 */     return quadbounds.isFullQuad(this.face);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullFaceQuad() {
/* 223 */     return (isFullQuad() && isFaceQuad());
/*     */   }
/*     */ 
/*     */   
/*     */   public BakedQuad getQuadEmissive() {
/* 228 */     if (this.quadEmissiveChecked)
/*     */     {
/* 230 */       return this.quadEmissive;
/*     */     }
/*     */ 
/*     */     
/* 234 */     if (this.quadEmissive == null && this.sprite != null && this.sprite.spriteEmissive != null)
/*     */     {
/* 236 */       this.quadEmissive = new BreakingFour(this, this.sprite.spriteEmissive);
/*     */     }
/*     */     
/* 239 */     this.quadEmissiveChecked = true;
/* 240 */     return this.quadEmissive;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 246 */     return "vertex: " + (this.vertexData.length / 7) + ", tint: " + this.tintIndex + ", facing: " + this.face + ", sprite: " + this.sprite;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\block\model\BakedQuad.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */