/*     */ package net.minecraft.client.renderer.block.model;
/*     */ 
/*     */ import net.minecraft.client.renderer.EnumFaceDirection;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.resources.model.ModelRotation;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.Vec3i;
/*     */ import net.minecraftforge.client.model.ITransformation;
/*     */ import net.optifine.model.BlockModelUtils;
/*     */ import net.optifine.reflect.Reflector;
/*     */ import net.optifine.shaders.Shaders;
/*     */ import org.lwjgl.util.vector.Matrix4f;
/*     */ import org.lwjgl.util.vector.ReadableVector3f;
/*     */ import org.lwjgl.util.vector.Vector3f;
/*     */ import org.lwjgl.util.vector.Vector4f;
/*     */ 
/*     */ public class FaceBakery {
/*  20 */   private static final float SCALE_ROTATION_22_5 = 1.0F / (float)Math.cos(0.39269909262657166D) - 1.0F;
/*  21 */   private static final float SCALE_ROTATION_GENERAL = 1.0F / (float)Math.cos(0.7853981633974483D) - 1.0F;
/*     */ 
/*     */   
/*     */   public BakedQuad makeBakedQuad(Vector3f posFrom, Vector3f posTo, BlockPartFace face, TextureAtlasSprite sprite, EnumFacing facing, ModelRotation modelRotationIn, BlockPartRotation partRotation, boolean uvLocked, boolean shade) {
/*  25 */     return makeBakedQuad(posFrom, posTo, face, sprite, facing, (ITransformation)modelRotationIn, partRotation, uvLocked, shade);
/*     */   }
/*     */ 
/*     */   
/*     */   public BakedQuad makeBakedQuad(Vector3f p_makeBakedQuad_1_, Vector3f p_makeBakedQuad_2_, BlockPartFace p_makeBakedQuad_3_, TextureAtlasSprite p_makeBakedQuad_4_, EnumFacing p_makeBakedQuad_5_, ITransformation p_makeBakedQuad_6_, BlockPartRotation p_makeBakedQuad_7_, boolean p_makeBakedQuad_8_, boolean p_makeBakedQuad_9_) {
/*  30 */     int[] aint = makeQuadVertexData(p_makeBakedQuad_3_, p_makeBakedQuad_4_, p_makeBakedQuad_5_, getPositionsDiv16(p_makeBakedQuad_1_, p_makeBakedQuad_2_), p_makeBakedQuad_6_, p_makeBakedQuad_7_, p_makeBakedQuad_8_, p_makeBakedQuad_9_);
/*  31 */     EnumFacing enumfacing = getFacingFromVertexData(aint);
/*     */     
/*  33 */     if (p_makeBakedQuad_8_)
/*     */     {
/*  35 */       lockUv(aint, enumfacing, p_makeBakedQuad_3_.blockFaceUV, p_makeBakedQuad_4_);
/*     */     }
/*     */     
/*  38 */     if (p_makeBakedQuad_7_ == null)
/*     */     {
/*  40 */       applyFacing(aint, enumfacing);
/*     */     }
/*     */     
/*  43 */     if (Reflector.ForgeHooksClient_fillNormal.exists())
/*     */     {
/*  45 */       Reflector.call(Reflector.ForgeHooksClient_fillNormal, new Object[] { aint, enumfacing });
/*     */     }
/*     */     
/*  48 */     return new BakedQuad(aint, p_makeBakedQuad_3_.tintIndex, enumfacing);
/*     */   }
/*     */ 
/*     */   
/*     */   private int[] makeQuadVertexData(BlockPartFace p_makeQuadVertexData_1_, TextureAtlasSprite p_makeQuadVertexData_2_, EnumFacing p_makeQuadVertexData_3_, float[] p_makeQuadVertexData_4_, ITransformation p_makeQuadVertexData_5_, BlockPartRotation p_makeQuadVertexData_6_, boolean p_makeQuadVertexData_7_, boolean p_makeQuadVertexData_8_) {
/*  53 */     int i = 28;
/*     */     
/*  55 */     if (Config.isShaders())
/*     */     {
/*  57 */       i = 56;
/*     */     }
/*     */     
/*  60 */     int[] aint = new int[i];
/*     */     
/*  62 */     for (int j = 0; j < 4; j++)
/*     */     {
/*  64 */       fillVertexData(aint, j, p_makeQuadVertexData_3_, p_makeQuadVertexData_1_, p_makeQuadVertexData_4_, p_makeQuadVertexData_2_, p_makeQuadVertexData_5_, p_makeQuadVertexData_6_, p_makeQuadVertexData_7_, p_makeQuadVertexData_8_);
/*     */     }
/*     */     
/*  67 */     return aint;
/*     */   }
/*     */ 
/*     */   
/*     */   private int getFaceShadeColor(EnumFacing facing) {
/*  72 */     float f = getFaceBrightness(facing);
/*  73 */     int i = MathHelper.clamp_int((int)(f * 255.0F), 0, 255);
/*  74 */     return 0xFF000000 | i << 16 | i << 8 | i;
/*     */   }
/*     */ 
/*     */   
/*     */   public static float getFaceBrightness(EnumFacing p_178412_0_) {
/*  79 */     switch (p_178412_0_) {
/*     */       
/*     */       case X:
/*  82 */         if (Config.isShaders())
/*     */         {
/*  84 */           return Shaders.blockLightLevel05;
/*     */         }
/*     */         
/*  87 */         return 0.5F;
/*     */       
/*     */       case Y:
/*  90 */         return 1.0F;
/*     */       
/*     */       case Z:
/*     */       case null:
/*  94 */         if (Config.isShaders())
/*     */         {
/*  96 */           return Shaders.blockLightLevel08;
/*     */         }
/*     */         
/*  99 */         return 0.8F;
/*     */       
/*     */       case null:
/*     */       case null:
/* 103 */         if (Config.isShaders())
/*     */         {
/* 105 */           return Shaders.blockLightLevel06;
/*     */         }
/*     */         
/* 108 */         return 0.6F;
/*     */     } 
/*     */     
/* 111 */     return 1.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private float[] getPositionsDiv16(Vector3f pos1, Vector3f pos2) {
/* 117 */     float[] afloat = new float[(EnumFacing.values()).length];
/* 118 */     afloat[EnumFaceDirection.Constants.WEST_INDEX] = pos1.x / 16.0F;
/* 119 */     afloat[EnumFaceDirection.Constants.DOWN_INDEX] = pos1.y / 16.0F;
/* 120 */     afloat[EnumFaceDirection.Constants.NORTH_INDEX] = pos1.z / 16.0F;
/* 121 */     afloat[EnumFaceDirection.Constants.EAST_INDEX] = pos2.x / 16.0F;
/* 122 */     afloat[EnumFaceDirection.Constants.UP_INDEX] = pos2.y / 16.0F;
/* 123 */     afloat[EnumFaceDirection.Constants.SOUTH_INDEX] = pos2.z / 16.0F;
/* 124 */     return afloat;
/*     */   }
/*     */ 
/*     */   
/*     */   private void fillVertexData(int[] p_fillVertexData_1_, int p_fillVertexData_2_, EnumFacing p_fillVertexData_3_, BlockPartFace p_fillVertexData_4_, float[] p_fillVertexData_5_, TextureAtlasSprite p_fillVertexData_6_, ITransformation p_fillVertexData_7_, BlockPartRotation p_fillVertexData_8_, boolean p_fillVertexData_9_, boolean p_fillVertexData_10_) {
/* 129 */     EnumFacing enumfacing = p_fillVertexData_7_.rotate(p_fillVertexData_3_);
/* 130 */     int i = p_fillVertexData_10_ ? getFaceShadeColor(enumfacing) : -1;
/* 131 */     EnumFaceDirection.VertexInformation enumfacedirection$vertexinformation = EnumFaceDirection.getFacing(p_fillVertexData_3_).getVertexInformation(p_fillVertexData_2_);
/* 132 */     Vector3f vector3f = new Vector3f(p_fillVertexData_5_[enumfacedirection$vertexinformation.xIndex], p_fillVertexData_5_[enumfacedirection$vertexinformation.yIndex], p_fillVertexData_5_[enumfacedirection$vertexinformation.zIndex]);
/* 133 */     rotatePart(vector3f, p_fillVertexData_8_);
/* 134 */     int j = rotateVertex(vector3f, p_fillVertexData_3_, p_fillVertexData_2_, p_fillVertexData_7_, p_fillVertexData_9_);
/* 135 */     BlockModelUtils.snapVertexPosition(vector3f);
/* 136 */     storeVertexData(p_fillVertexData_1_, j, p_fillVertexData_2_, vector3f, i, p_fillVertexData_6_, p_fillVertexData_4_.blockFaceUV);
/*     */   }
/*     */ 
/*     */   
/*     */   private void storeVertexData(int[] faceData, int storeIndex, int vertexIndex, Vector3f position, int shadeColor, TextureAtlasSprite sprite, BlockFaceUV faceUV) {
/* 141 */     int i = faceData.length / 4;
/* 142 */     int j = storeIndex * i;
/* 143 */     faceData[j] = Float.floatToRawIntBits(position.x);
/* 144 */     faceData[j + 1] = Float.floatToRawIntBits(position.y);
/* 145 */     faceData[j + 2] = Float.floatToRawIntBits(position.z);
/* 146 */     faceData[j + 3] = shadeColor;
/* 147 */     faceData[j + 4] = Float.floatToRawIntBits(sprite.getInterpolatedU(faceUV.func_178348_a(vertexIndex) * 0.999D + faceUV.func_178348_a((vertexIndex + 2) % 4) * 0.001D));
/* 148 */     faceData[j + 4 + 1] = Float.floatToRawIntBits(sprite.getInterpolatedV(faceUV.func_178346_b(vertexIndex) * 0.999D + faceUV.func_178346_b((vertexIndex + 2) % 4) * 0.001D));
/*     */   }
/*     */ 
/*     */   
/*     */   private void rotatePart(Vector3f p_178407_1_, BlockPartRotation partRotation) {
/* 153 */     if (partRotation != null) {
/*     */       
/* 155 */       Matrix4f matrix4f = getMatrixIdentity();
/* 156 */       Vector3f vector3f = new Vector3f(0.0F, 0.0F, 0.0F);
/*     */       
/* 158 */       switch (partRotation.axis) {
/*     */         
/*     */         case X:
/* 161 */           Matrix4f.rotate(partRotation.angle * 0.017453292F, new Vector3f(1.0F, 0.0F, 0.0F), matrix4f, matrix4f);
/* 162 */           vector3f.set(0.0F, 1.0F, 1.0F);
/*     */           break;
/*     */         
/*     */         case Y:
/* 166 */           Matrix4f.rotate(partRotation.angle * 0.017453292F, new Vector3f(0.0F, 1.0F, 0.0F), matrix4f, matrix4f);
/* 167 */           vector3f.set(1.0F, 0.0F, 1.0F);
/*     */           break;
/*     */         
/*     */         case Z:
/* 171 */           Matrix4f.rotate(partRotation.angle * 0.017453292F, new Vector3f(0.0F, 0.0F, 1.0F), matrix4f, matrix4f);
/* 172 */           vector3f.set(1.0F, 1.0F, 0.0F);
/*     */           break;
/*     */       } 
/* 175 */       if (partRotation.rescale) {
/*     */         
/* 177 */         if (Math.abs(partRotation.angle) == 22.5F) {
/*     */           
/* 179 */           vector3f.scale(SCALE_ROTATION_22_5);
/*     */         }
/*     */         else {
/*     */           
/* 183 */           vector3f.scale(SCALE_ROTATION_GENERAL);
/*     */         } 
/*     */         
/* 186 */         Vector3f.add(vector3f, new Vector3f(1.0F, 1.0F, 1.0F), vector3f);
/*     */       }
/*     */       else {
/*     */         
/* 190 */         vector3f.set(1.0F, 1.0F, 1.0F);
/*     */       } 
/*     */       
/* 193 */       rotateScale(p_178407_1_, new Vector3f((ReadableVector3f)partRotation.origin), matrix4f, vector3f);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int rotateVertex(Vector3f position, EnumFacing facing, int vertexIndex, ModelRotation modelRotationIn, boolean uvLocked) {
/* 199 */     return rotateVertex(position, facing, vertexIndex, modelRotationIn, uvLocked);
/*     */   }
/*     */ 
/*     */   
/*     */   public int rotateVertex(Vector3f p_rotateVertex_1_, EnumFacing p_rotateVertex_2_, int p_rotateVertex_3_, ITransformation p_rotateVertex_4_, boolean p_rotateVertex_5_) {
/* 204 */     if (p_rotateVertex_4_ == ModelRotation.X0_Y0)
/*     */     {
/* 206 */       return p_rotateVertex_3_;
/*     */     }
/*     */ 
/*     */     
/* 210 */     if (Reflector.ForgeHooksClient_transform.exists()) {
/*     */       
/* 212 */       Reflector.call(Reflector.ForgeHooksClient_transform, new Object[] { p_rotateVertex_1_, p_rotateVertex_4_.getMatrix() });
/*     */     }
/*     */     else {
/*     */       
/* 216 */       rotateScale(p_rotateVertex_1_, new Vector3f(0.5F, 0.5F, 0.5F), ((ModelRotation)p_rotateVertex_4_).getMatrix4d(), new Vector3f(1.0F, 1.0F, 1.0F));
/*     */     } 
/*     */     
/* 219 */     return p_rotateVertex_4_.rotate(p_rotateVertex_2_, p_rotateVertex_3_);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void rotateScale(Vector3f position, Vector3f rotationOrigin, Matrix4f rotationMatrix, Vector3f scale) {
/* 225 */     Vector4f vector4f = new Vector4f(position.x - rotationOrigin.x, position.y - rotationOrigin.y, position.z - rotationOrigin.z, 1.0F);
/* 226 */     Matrix4f.transform(rotationMatrix, vector4f, vector4f);
/* 227 */     vector4f.x *= scale.x;
/* 228 */     vector4f.y *= scale.y;
/* 229 */     vector4f.z *= scale.z;
/* 230 */     position.set(vector4f.x + rotationOrigin.x, vector4f.y + rotationOrigin.y, vector4f.z + rotationOrigin.z);
/*     */   }
/*     */ 
/*     */   
/*     */   private Matrix4f getMatrixIdentity() {
/* 235 */     Matrix4f matrix4f = new Matrix4f();
/* 236 */     matrix4f.setIdentity();
/* 237 */     return matrix4f;
/*     */   }
/*     */ 
/*     */   
/*     */   public static EnumFacing getFacingFromVertexData(int[] faceData) {
/* 242 */     int i = faceData.length / 4;
/* 243 */     int j = i << 1;
/* 244 */     int k = i * 3;
/* 245 */     Vector3f vector3f = new Vector3f(Float.intBitsToFloat(faceData[0]), Float.intBitsToFloat(faceData[1]), Float.intBitsToFloat(faceData[2]));
/* 246 */     Vector3f vector3f1 = new Vector3f(Float.intBitsToFloat(faceData[i]), Float.intBitsToFloat(faceData[i + 1]), Float.intBitsToFloat(faceData[i + 2]));
/* 247 */     Vector3f vector3f2 = new Vector3f(Float.intBitsToFloat(faceData[j]), Float.intBitsToFloat(faceData[j + 1]), Float.intBitsToFloat(faceData[j + 2]));
/* 248 */     Vector3f vector3f3 = new Vector3f();
/* 249 */     Vector3f vector3f4 = new Vector3f();
/* 250 */     Vector3f vector3f5 = new Vector3f();
/* 251 */     Vector3f.sub(vector3f, vector3f1, vector3f3);
/* 252 */     Vector3f.sub(vector3f2, vector3f1, vector3f4);
/* 253 */     Vector3f.cross(vector3f4, vector3f3, vector3f5);
/* 254 */     float f = (float)Math.sqrt((vector3f5.x * vector3f5.x + vector3f5.y * vector3f5.y + vector3f5.z * vector3f5.z));
/* 255 */     vector3f5.x /= f;
/* 256 */     vector3f5.y /= f;
/* 257 */     vector3f5.z /= f;
/* 258 */     EnumFacing enumfacing = null;
/* 259 */     float f1 = 0.0F;
/*     */     
/* 261 */     for (EnumFacing enumfacing1 : EnumFacing.values()) {
/*     */       
/* 263 */       Vec3i vec3i = enumfacing1.getDirectionVec();
/* 264 */       Vector3f vector3f6 = new Vector3f(vec3i.getX(), vec3i.getY(), vec3i.getZ());
/* 265 */       float f2 = Vector3f.dot(vector3f5, vector3f6);
/*     */       
/* 267 */       if (f2 >= 0.0F && f2 > f1) {
/*     */         
/* 269 */         f1 = f2;
/* 270 */         enumfacing = enumfacing1;
/*     */       } 
/*     */     } 
/*     */     
/* 274 */     if (enumfacing == null)
/*     */     {
/* 276 */       return EnumFacing.UP;
/*     */     }
/*     */ 
/*     */     
/* 280 */     return enumfacing;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void lockUv(int[] p_178409_1_, EnumFacing facing, BlockFaceUV p_178409_3_, TextureAtlasSprite p_178409_4_) {
/* 286 */     for (int i = 0; i < 4; i++)
/*     */     {
/* 288 */       lockVertexUv(i, p_178409_1_, facing, p_178409_3_, p_178409_4_);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void applyFacing(int[] p_178408_1_, EnumFacing p_178408_2_) {
/* 294 */     int[] aint = new int[p_178408_1_.length];
/* 295 */     System.arraycopy(p_178408_1_, 0, aint, 0, p_178408_1_.length);
/* 296 */     float[] afloat = new float[(EnumFacing.values()).length];
/* 297 */     afloat[EnumFaceDirection.Constants.WEST_INDEX] = 999.0F;
/* 298 */     afloat[EnumFaceDirection.Constants.DOWN_INDEX] = 999.0F;
/* 299 */     afloat[EnumFaceDirection.Constants.NORTH_INDEX] = 999.0F;
/* 300 */     afloat[EnumFaceDirection.Constants.EAST_INDEX] = -999.0F;
/* 301 */     afloat[EnumFaceDirection.Constants.UP_INDEX] = -999.0F;
/* 302 */     afloat[EnumFaceDirection.Constants.SOUTH_INDEX] = -999.0F;
/* 303 */     int i = p_178408_1_.length / 4;
/*     */     
/* 305 */     for (int j = 0; j < 4; j++) {
/*     */       
/* 307 */       int k = i * j;
/* 308 */       float f = Float.intBitsToFloat(aint[k]);
/* 309 */       float f1 = Float.intBitsToFloat(aint[k + 1]);
/* 310 */       float f2 = Float.intBitsToFloat(aint[k + 2]);
/*     */       
/* 312 */       if (f < afloat[EnumFaceDirection.Constants.WEST_INDEX])
/*     */       {
/* 314 */         afloat[EnumFaceDirection.Constants.WEST_INDEX] = f;
/*     */       }
/*     */       
/* 317 */       if (f1 < afloat[EnumFaceDirection.Constants.DOWN_INDEX])
/*     */       {
/* 319 */         afloat[EnumFaceDirection.Constants.DOWN_INDEX] = f1;
/*     */       }
/*     */       
/* 322 */       if (f2 < afloat[EnumFaceDirection.Constants.NORTH_INDEX])
/*     */       {
/* 324 */         afloat[EnumFaceDirection.Constants.NORTH_INDEX] = f2;
/*     */       }
/*     */       
/* 327 */       if (f > afloat[EnumFaceDirection.Constants.EAST_INDEX])
/*     */       {
/* 329 */         afloat[EnumFaceDirection.Constants.EAST_INDEX] = f;
/*     */       }
/*     */       
/* 332 */       if (f1 > afloat[EnumFaceDirection.Constants.UP_INDEX])
/*     */       {
/* 334 */         afloat[EnumFaceDirection.Constants.UP_INDEX] = f1;
/*     */       }
/*     */       
/* 337 */       if (f2 > afloat[EnumFaceDirection.Constants.SOUTH_INDEX])
/*     */       {
/* 339 */         afloat[EnumFaceDirection.Constants.SOUTH_INDEX] = f2;
/*     */       }
/*     */     } 
/*     */     
/* 343 */     EnumFaceDirection enumfacedirection = EnumFaceDirection.getFacing(p_178408_2_);
/*     */     
/* 345 */     for (int j1 = 0; j1 < 4; j1++) {
/*     */       
/* 347 */       int k1 = i * j1;
/* 348 */       EnumFaceDirection.VertexInformation enumfacedirection$vertexinformation = enumfacedirection.getVertexInformation(j1);
/* 349 */       float f8 = afloat[enumfacedirection$vertexinformation.xIndex];
/* 350 */       float f3 = afloat[enumfacedirection$vertexinformation.yIndex];
/* 351 */       float f4 = afloat[enumfacedirection$vertexinformation.zIndex];
/* 352 */       p_178408_1_[k1] = Float.floatToRawIntBits(f8);
/* 353 */       p_178408_1_[k1 + 1] = Float.floatToRawIntBits(f3);
/* 354 */       p_178408_1_[k1 + 2] = Float.floatToRawIntBits(f4);
/*     */       
/* 356 */       for (int l = 0; l < 4; l++) {
/*     */         
/* 358 */         int i1 = i * l;
/* 359 */         float f5 = Float.intBitsToFloat(aint[i1]);
/* 360 */         float f6 = Float.intBitsToFloat(aint[i1 + 1]);
/* 361 */         float f7 = Float.intBitsToFloat(aint[i1 + 2]);
/*     */         
/* 363 */         if (MathHelper.epsilonEquals(f8, f5) && MathHelper.epsilonEquals(f3, f6) && MathHelper.epsilonEquals(f4, f7)) {
/*     */           
/* 365 */           p_178408_1_[k1 + 4] = aint[i1 + 4];
/* 366 */           p_178408_1_[k1 + 4 + 1] = aint[i1 + 4 + 1];
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void lockVertexUv(int p_178401_1_, int[] p_178401_2_, EnumFacing facing, BlockFaceUV p_178401_4_, TextureAtlasSprite p_178401_5_) {
/* 374 */     int i = p_178401_2_.length / 4;
/* 375 */     int j = i * p_178401_1_;
/* 376 */     float f = Float.intBitsToFloat(p_178401_2_[j]);
/* 377 */     float f1 = Float.intBitsToFloat(p_178401_2_[j + 1]);
/* 378 */     float f2 = Float.intBitsToFloat(p_178401_2_[j + 2]);
/*     */     
/* 380 */     if (f < -0.1F || f >= 1.1F)
/*     */     {
/* 382 */       f -= MathHelper.floor_float(f);
/*     */     }
/*     */     
/* 385 */     if (f1 < -0.1F || f1 >= 1.1F)
/*     */     {
/* 387 */       f1 -= MathHelper.floor_float(f1);
/*     */     }
/*     */     
/* 390 */     if (f2 < -0.1F || f2 >= 1.1F)
/*     */     {
/* 392 */       f2 -= MathHelper.floor_float(f2);
/*     */     }
/*     */     
/* 395 */     float f3 = 0.0F;
/* 396 */     float f4 = 0.0F;
/*     */     
/* 398 */     switch (facing) {
/*     */       
/*     */       case X:
/* 401 */         f3 = f * 16.0F;
/* 402 */         f4 = (1.0F - f2) * 16.0F;
/*     */         break;
/*     */       
/*     */       case Y:
/* 406 */         f3 = f * 16.0F;
/* 407 */         f4 = f2 * 16.0F;
/*     */         break;
/*     */       
/*     */       case Z:
/* 411 */         f3 = (1.0F - f) * 16.0F;
/* 412 */         f4 = (1.0F - f1) * 16.0F;
/*     */         break;
/*     */       
/*     */       case null:
/* 416 */         f3 = f * 16.0F;
/* 417 */         f4 = (1.0F - f1) * 16.0F;
/*     */         break;
/*     */       
/*     */       case null:
/* 421 */         f3 = f2 * 16.0F;
/* 422 */         f4 = (1.0F - f1) * 16.0F;
/*     */         break;
/*     */       
/*     */       case null:
/* 426 */         f3 = (1.0F - f2) * 16.0F;
/* 427 */         f4 = (1.0F - f1) * 16.0F;
/*     */         break;
/*     */     } 
/* 430 */     int k = p_178401_4_.func_178345_c(p_178401_1_) * i;
/* 431 */     p_178401_2_[k + 4] = Float.floatToRawIntBits(p_178401_5_.getInterpolatedU(f3));
/* 432 */     p_178401_2_[k + 4 + 1] = Float.floatToRawIntBits(p_178401_5_.getInterpolatedV(f4));
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\block\model\FaceBakery.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */