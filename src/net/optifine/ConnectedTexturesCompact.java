/*     */ package net.optifine;
/*     */ 
/*     */ import java.util.IdentityHashMap;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.optifine.render.RenderEnv;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ConnectedTexturesCompact
/*     */ {
/*     */   private static final int COMPACT_NONE = 0;
/*     */   private static final int COMPACT_ALL = 1;
/*     */   private static final int COMPACT_V = 2;
/*     */   private static final int COMPACT_H = 3;
/*     */   private static final int COMPACT_HV = 4;
/*     */   
/*     */   public static BakedQuad[] getConnectedTextureCtmCompact(int ctmIndex, ConnectedProperties cp, int side, BakedQuad quad, RenderEnv renderEnv) {
/*  20 */     if (cp.ctmTileIndexes != null && ctmIndex >= 0 && ctmIndex < cp.ctmTileIndexes.length) {
/*     */       
/*  22 */       int i = cp.ctmTileIndexes[ctmIndex];
/*     */       
/*  24 */       if (i >= 0 && i <= cp.tileIcons.length)
/*     */       {
/*  26 */         return getQuadsCompact(i, cp.tileIcons, quad, renderEnv);
/*     */       }
/*     */     } 
/*     */     
/*  30 */     switch (ctmIndex) {
/*     */       
/*     */       case 1:
/*  33 */         return getQuadsCompactH(0, 3, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 2:
/*  36 */         return getQuadsCompact(3, cp.tileIcons, quad, renderEnv);
/*     */       
/*     */       case 3:
/*  39 */         return getQuadsCompactH(3, 0, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 4:
/*  42 */         return getQuadsCompact4(0, 3, 2, 4, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 5:
/*  45 */         return getQuadsCompact4(3, 0, 4, 2, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 6:
/*  48 */         return getQuadsCompact4(2, 4, 2, 4, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 7:
/*  51 */         return getQuadsCompact4(3, 3, 4, 4, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 8:
/*  54 */         return getQuadsCompact4(4, 1, 4, 4, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 9:
/*  57 */         return getQuadsCompact4(4, 4, 4, 1, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 10:
/*  60 */         return getQuadsCompact4(1, 4, 1, 4, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 11:
/*  63 */         return getQuadsCompact4(1, 1, 4, 4, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 12:
/*  66 */         return getQuadsCompactV(0, 2, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 13:
/*  69 */         return getQuadsCompact4(0, 3, 2, 1, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 14:
/*  72 */         return getQuadsCompactV(3, 1, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 15:
/*  75 */         return getQuadsCompact4(3, 0, 1, 2, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 16:
/*  78 */         return getQuadsCompact4(2, 4, 0, 3, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 17:
/*  81 */         return getQuadsCompact4(4, 2, 3, 0, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 18:
/*  84 */         return getQuadsCompact4(4, 4, 3, 3, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 19:
/*  87 */         return getQuadsCompact4(4, 2, 4, 2, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 20:
/*  90 */         return getQuadsCompact4(1, 4, 4, 4, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 21:
/*  93 */         return getQuadsCompact4(4, 4, 1, 4, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 22:
/*  96 */         return getQuadsCompact4(4, 4, 1, 1, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 23:
/*  99 */         return getQuadsCompact4(4, 1, 4, 1, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 24:
/* 102 */         return getQuadsCompact(2, cp.tileIcons, quad, renderEnv);
/*     */       
/*     */       case 25:
/* 105 */         return getQuadsCompactH(2, 1, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 26:
/* 108 */         return getQuadsCompact(1, cp.tileIcons, quad, renderEnv);
/*     */       
/*     */       case 27:
/* 111 */         return getQuadsCompactH(1, 2, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 28:
/* 114 */         return getQuadsCompact4(2, 4, 2, 1, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 29:
/* 117 */         return getQuadsCompact4(3, 3, 1, 4, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 30:
/* 120 */         return getQuadsCompact4(2, 1, 2, 4, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 31:
/* 123 */         return getQuadsCompact4(3, 3, 4, 1, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 32:
/* 126 */         return getQuadsCompact4(1, 1, 1, 4, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 33:
/* 129 */         return getQuadsCompact4(1, 1, 4, 1, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 34:
/* 132 */         return getQuadsCompact4(4, 1, 1, 4, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 35:
/* 135 */         return getQuadsCompact4(1, 4, 4, 1, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 36:
/* 138 */         return getQuadsCompactV(2, 0, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 37:
/* 141 */         return getQuadsCompact4(2, 1, 0, 3, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 38:
/* 144 */         return getQuadsCompactV(1, 3, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 39:
/* 147 */         return getQuadsCompact4(1, 2, 3, 0, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 40:
/* 150 */         return getQuadsCompact4(4, 1, 3, 3, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 41:
/* 153 */         return getQuadsCompact4(1, 2, 4, 2, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 42:
/* 156 */         return getQuadsCompact4(1, 4, 3, 3, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 43:
/* 159 */         return getQuadsCompact4(4, 2, 1, 2, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 44:
/* 162 */         return getQuadsCompact4(1, 4, 1, 1, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 45:
/* 165 */         return getQuadsCompact4(4, 1, 1, 1, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 46:
/* 168 */         return getQuadsCompact(4, cp.tileIcons, quad, renderEnv);
/*     */     } 
/*     */     
/* 171 */     return getQuadsCompact(0, cp.tileIcons, quad, renderEnv);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static BakedQuad[] getQuadsCompactH(int indexLeft, int indexRight, TextureAtlasSprite[] sprites, int side, BakedQuad quad, RenderEnv renderEnv) {
/* 177 */     return getQuadsCompact(Dir.LEFT, indexLeft, Dir.RIGHT, indexRight, sprites, side, quad, renderEnv);
/*     */   }
/*     */ 
/*     */   
/*     */   private static BakedQuad[] getQuadsCompactV(int indexUp, int indexDown, TextureAtlasSprite[] sprites, int side, BakedQuad quad, RenderEnv renderEnv) {
/* 182 */     return getQuadsCompact(Dir.UP, indexUp, Dir.DOWN, indexDown, sprites, side, quad, renderEnv);
/*     */   }
/*     */ 
/*     */   
/*     */   private static BakedQuad[] getQuadsCompact4(int upLeft, int upRight, int downLeft, int downRight, TextureAtlasSprite[] sprites, int side, BakedQuad quad, RenderEnv renderEnv) {
/* 187 */     return (upLeft == upRight) ? ((downLeft == downRight) ? getQuadsCompact(Dir.UP, upLeft, Dir.DOWN, downLeft, sprites, side, quad, renderEnv) : getQuadsCompact(Dir.UP, upLeft, Dir.DOWN_LEFT, downLeft, Dir.DOWN_RIGHT, downRight, sprites, side, quad, renderEnv)) : ((downLeft == downRight) ? getQuadsCompact(Dir.UP_LEFT, upLeft, Dir.UP_RIGHT, upRight, Dir.DOWN, downLeft, sprites, side, quad, renderEnv) : ((upLeft == downLeft) ? ((upRight == downRight) ? getQuadsCompact(Dir.LEFT, upLeft, Dir.RIGHT, upRight, sprites, side, quad, renderEnv) : getQuadsCompact(Dir.LEFT, upLeft, Dir.UP_RIGHT, upRight, Dir.DOWN_RIGHT, downRight, sprites, side, quad, renderEnv)) : ((upRight == downRight) ? getQuadsCompact(Dir.UP_LEFT, upLeft, Dir.DOWN_LEFT, downLeft, Dir.RIGHT, upRight, sprites, side, quad, renderEnv) : getQuadsCompact(Dir.UP_LEFT, upLeft, Dir.UP_RIGHT, upRight, Dir.DOWN_LEFT, downLeft, Dir.DOWN_RIGHT, downRight, sprites, side, quad, renderEnv))));
/*     */   }
/*     */ 
/*     */   
/*     */   private static BakedQuad[] getQuadsCompact(int index, TextureAtlasSprite[] sprites, BakedQuad quad, RenderEnv renderEnv) {
/* 192 */     TextureAtlasSprite textureatlassprite = sprites[index];
/* 193 */     return ConnectedTextures.getQuads(textureatlassprite, quad, renderEnv);
/*     */   }
/*     */ 
/*     */   
/*     */   private static BakedQuad[] getQuadsCompact(Dir dir1, int index1, Dir dir2, int index2, TextureAtlasSprite[] sprites, int side, BakedQuad quad, RenderEnv renderEnv) {
/* 198 */     BakedQuad bakedquad = getQuadCompact(sprites[index1], dir1, side, quad, renderEnv);
/* 199 */     BakedQuad bakedquad1 = getQuadCompact(sprites[index2], dir2, side, quad, renderEnv);
/* 200 */     return renderEnv.getArrayQuadsCtm(bakedquad, bakedquad1);
/*     */   }
/*     */ 
/*     */   
/*     */   private static BakedQuad[] getQuadsCompact(Dir dir1, int index1, Dir dir2, int index2, Dir dir3, int index3, TextureAtlasSprite[] sprites, int side, BakedQuad quad, RenderEnv renderEnv) {
/* 205 */     BakedQuad bakedquad = getQuadCompact(sprites[index1], dir1, side, quad, renderEnv);
/* 206 */     BakedQuad bakedquad1 = getQuadCompact(sprites[index2], dir2, side, quad, renderEnv);
/* 207 */     BakedQuad bakedquad2 = getQuadCompact(sprites[index3], dir3, side, quad, renderEnv);
/* 208 */     return renderEnv.getArrayQuadsCtm(bakedquad, bakedquad1, bakedquad2);
/*     */   }
/*     */ 
/*     */   
/*     */   private static BakedQuad[] getQuadsCompact(Dir dir1, int index1, Dir dir2, int index2, Dir dir3, int index3, Dir dir4, int index4, TextureAtlasSprite[] sprites, int side, BakedQuad quad, RenderEnv renderEnv) {
/* 213 */     BakedQuad bakedquad = getQuadCompact(sprites[index1], dir1, side, quad, renderEnv);
/* 214 */     BakedQuad bakedquad1 = getQuadCompact(sprites[index2], dir2, side, quad, renderEnv);
/* 215 */     BakedQuad bakedquad2 = getQuadCompact(sprites[index3], dir3, side, quad, renderEnv);
/* 216 */     BakedQuad bakedquad3 = getQuadCompact(sprites[index4], dir4, side, quad, renderEnv);
/* 217 */     return renderEnv.getArrayQuadsCtm(bakedquad, bakedquad1, bakedquad2, bakedquad3);
/*     */   }
/*     */ 
/*     */   
/*     */   private static BakedQuad getQuadCompact(TextureAtlasSprite sprite, Dir dir, int side, BakedQuad quad, RenderEnv renderEnv) {
/* 222 */     switch (dir) {
/*     */       
/*     */       case UP:
/* 225 */         return getQuadCompact(sprite, dir, 0, 0, 16, 8, side, quad, renderEnv);
/*     */       
/*     */       case UP_RIGHT:
/* 228 */         return getQuadCompact(sprite, dir, 8, 0, 16, 8, side, quad, renderEnv);
/*     */       
/*     */       case RIGHT:
/* 231 */         return getQuadCompact(sprite, dir, 8, 0, 16, 16, side, quad, renderEnv);
/*     */       
/*     */       case DOWN_RIGHT:
/* 234 */         return getQuadCompact(sprite, dir, 8, 8, 16, 16, side, quad, renderEnv);
/*     */       
/*     */       case DOWN:
/* 237 */         return getQuadCompact(sprite, dir, 0, 8, 16, 16, side, quad, renderEnv);
/*     */       
/*     */       case DOWN_LEFT:
/* 240 */         return getQuadCompact(sprite, dir, 0, 8, 8, 16, side, quad, renderEnv);
/*     */       
/*     */       case LEFT:
/* 243 */         return getQuadCompact(sprite, dir, 0, 0, 8, 16, side, quad, renderEnv);
/*     */       
/*     */       case UP_LEFT:
/* 246 */         return getQuadCompact(sprite, dir, 0, 0, 8, 8, side, quad, renderEnv);
/*     */     } 
/*     */     
/* 249 */     return quad;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static BakedQuad getQuadCompact(TextureAtlasSprite sprite, Dir dir, int x1, int y1, int x2, int y2, int side, BakedQuad quadIn, RenderEnv renderEnv) {
/* 255 */     Map[][] amap = ConnectedTextures.getSpriteQuadCompactMaps();
/*     */     
/* 257 */     if (amap == null)
/*     */     {
/* 259 */       return quadIn;
/*     */     }
/*     */ 
/*     */     
/* 263 */     int i = sprite.getIndexInMap();
/*     */     
/* 265 */     if (i >= 0 && i < amap.length) {
/*     */       
/* 267 */       Map[] amap1 = amap[i];
/*     */       
/* 269 */       if (amap1 == null) {
/*     */         
/* 271 */         amap1 = new Map[Dir.VALUES.length];
/* 272 */         amap[i] = amap1;
/*     */       } 
/*     */       
/* 275 */       Map<BakedQuad, BakedQuad> map = amap1[dir.ordinal()];
/*     */       
/* 277 */       if (map == null) {
/*     */         
/* 279 */         map = new IdentityHashMap<>(1);
/* 280 */         amap1[dir.ordinal()] = map;
/*     */       } 
/*     */       
/* 283 */       BakedQuad bakedquad = map.get(quadIn);
/*     */       
/* 285 */       if (bakedquad == null) {
/*     */         
/* 287 */         bakedquad = makeSpriteQuadCompact(quadIn, sprite, side, x1, y1, x2, y2);
/* 288 */         map.put(quadIn, bakedquad);
/*     */       } 
/*     */       
/* 291 */       return bakedquad;
/*     */     } 
/*     */ 
/*     */     
/* 295 */     return quadIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static BakedQuad makeSpriteQuadCompact(BakedQuad quad, TextureAtlasSprite sprite, int side, int x1, int y1, int x2, int y2) {
/* 302 */     int[] aint = (int[])quad.getVertexData().clone();
/* 303 */     TextureAtlasSprite textureatlassprite = quad.getSprite();
/*     */     
/* 305 */     for (int i = 0; i < 4; i++)
/*     */     {
/* 307 */       fixVertexCompact(aint, i, textureatlassprite, sprite, side, x1, y1, x2, y2);
/*     */     }
/*     */     
/* 310 */     BakedQuad bakedquad = new BakedQuad(aint, quad.getTintIndex(), quad.getFace(), sprite);
/* 311 */     return bakedquad;
/*     */   }
/*     */   
/*     */   private static void fixVertexCompact(int[] data, int vertex, TextureAtlasSprite spriteFrom, TextureAtlasSprite spriteTo, int side, int x1, int y1, int x2, int y2) {
/*     */     float f5, f6;
/* 316 */     int i = data.length / 4;
/* 317 */     int j = i * vertex;
/* 318 */     float f = Float.intBitsToFloat(data[j + 4]);
/* 319 */     float f1 = Float.intBitsToFloat(data[j + 4 + 1]);
/* 320 */     double d0 = spriteFrom.getSpriteU16(f);
/* 321 */     double d1 = spriteFrom.getSpriteV16(f1);
/* 322 */     float f2 = Float.intBitsToFloat(data[j]);
/* 323 */     float f3 = Float.intBitsToFloat(data[j + 1]);
/* 324 */     float f4 = Float.intBitsToFloat(data[j + 2]);
/*     */ 
/*     */ 
/*     */     
/* 328 */     switch (side) {
/*     */       
/*     */       case 0:
/* 331 */         f5 = f2;
/* 332 */         f6 = 1.0F - f4;
/*     */         break;
/*     */       
/*     */       case 1:
/* 336 */         f5 = f2;
/* 337 */         f6 = f4;
/*     */         break;
/*     */       
/*     */       case 2:
/* 341 */         f5 = 1.0F - f2;
/* 342 */         f6 = 1.0F - f3;
/*     */         break;
/*     */       
/*     */       case 3:
/* 346 */         f5 = f2;
/* 347 */         f6 = 1.0F - f3;
/*     */         break;
/*     */       
/*     */       case 4:
/* 351 */         f5 = f4;
/* 352 */         f6 = 1.0F - f3;
/*     */         break;
/*     */       
/*     */       case 5:
/* 356 */         f5 = 1.0F - f4;
/* 357 */         f6 = 1.0F - f3;
/*     */         break;
/*     */       
/*     */       default:
/*     */         return;
/*     */     } 
/*     */     
/* 364 */     float f7 = 15.968F;
/* 365 */     float f8 = 15.968F;
/*     */     
/* 367 */     if (d0 < x1) {
/*     */       
/* 369 */       f5 = (float)(f5 + (x1 - d0) / f7);
/* 370 */       d0 = x1;
/*     */     } 
/*     */     
/* 373 */     if (d0 > x2) {
/*     */       
/* 375 */       f5 = (float)(f5 - (d0 - x2) / f7);
/* 376 */       d0 = x2;
/*     */     } 
/*     */     
/* 379 */     if (d1 < y1) {
/*     */       
/* 381 */       f6 = (float)(f6 + (y1 - d1) / f8);
/* 382 */       d1 = y1;
/*     */     } 
/*     */     
/* 385 */     if (d1 > y2) {
/*     */       
/* 387 */       f6 = (float)(f6 - (d1 - y2) / f8);
/* 388 */       d1 = y2;
/*     */     } 
/*     */     
/* 391 */     switch (side) {
/*     */       
/*     */       case 0:
/* 394 */         f2 = f5;
/* 395 */         f4 = 1.0F - f6;
/*     */         break;
/*     */       
/*     */       case 1:
/* 399 */         f2 = f5;
/* 400 */         f4 = f6;
/*     */         break;
/*     */       
/*     */       case 2:
/* 404 */         f2 = 1.0F - f5;
/* 405 */         f3 = 1.0F - f6;
/*     */         break;
/*     */       
/*     */       case 3:
/* 409 */         f2 = f5;
/* 410 */         f3 = 1.0F - f6;
/*     */         break;
/*     */       
/*     */       case 4:
/* 414 */         f4 = f5;
/* 415 */         f3 = 1.0F - f6;
/*     */         break;
/*     */       
/*     */       case 5:
/* 419 */         f4 = 1.0F - f5;
/* 420 */         f3 = 1.0F - f6;
/*     */         break;
/*     */       
/*     */       default:
/*     */         return;
/*     */     } 
/*     */     
/* 427 */     data[j + 4] = Float.floatToRawIntBits(spriteTo.getInterpolatedU(d0));
/* 428 */     data[j + 4 + 1] = Float.floatToRawIntBits(spriteTo.getInterpolatedV(d1));
/* 429 */     data[j] = Float.floatToRawIntBits(f2);
/* 430 */     data[j + 1] = Float.floatToRawIntBits(f3);
/* 431 */     data[j + 2] = Float.floatToRawIntBits(f4);
/*     */   }
/*     */   
/*     */   private enum Dir
/*     */   {
/* 436 */     UP,
/* 437 */     UP_RIGHT,
/* 438 */     RIGHT,
/* 439 */     DOWN_RIGHT,
/* 440 */     DOWN,
/* 441 */     DOWN_LEFT,
/* 442 */     LEFT,
/* 443 */     UP_LEFT;
/*     */     
/* 445 */     public static final Dir[] VALUES = values();
/*     */     
/*     */     static {
/*     */     
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\ConnectedTexturesCompact.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */