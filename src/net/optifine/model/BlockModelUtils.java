/*     */ package net.optifine.model;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*     */ import net.minecraft.client.renderer.block.model.BlockFaceUV;
/*     */ import net.minecraft.client.renderer.block.model.BreakingFour;
/*     */ import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.client.resources.model.IBakedModel;
/*     */ import net.minecraft.client.resources.model.ModelManager;
/*     */ import net.minecraft.client.resources.model.ModelResourceLocation;
/*     */ import net.minecraft.client.resources.model.ModelRotation;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import org.lwjgl.util.vector.Vector3f;
/*     */ 
/*     */ public class BlockModelUtils {
/*     */   public static IBakedModel makeModelCube(String spriteName, int tintIndex) {
/*  23 */     TextureAtlasSprite textureatlassprite = Config.getMinecraft().getTextureMapBlocks().getAtlasSprite(spriteName);
/*  24 */     return makeModelCube(textureatlassprite, tintIndex);
/*     */   }
/*     */   private static final float VERTEX_COORD_ACCURACY = 1.0E-6F;
/*     */   
/*     */   public static IBakedModel makeModelCube(TextureAtlasSprite sprite, int tintIndex) {
/*  29 */     List list = new ArrayList();
/*  30 */     EnumFacing[] aenumfacing = EnumFacing.VALUES;
/*  31 */     List<List<BakedQuad>> list1 = new ArrayList<>();
/*     */     
/*  33 */     for (int i = 0; i < aenumfacing.length; i++) {
/*     */       
/*  35 */       EnumFacing enumfacing = aenumfacing[i];
/*  36 */       List<BakedQuad> list2 = new ArrayList();
/*  37 */       list2.add(makeBakedQuad(enumfacing, sprite, tintIndex));
/*  38 */       list1.add(list2);
/*     */     } 
/*     */     
/*  41 */     return (IBakedModel)new SimpleBakedModel(list, list1, true, true, sprite, ItemCameraTransforms.DEFAULT);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static IBakedModel joinModelsCube(IBakedModel modelBase, IBakedModel modelAdd) {
/*  47 */     List<BakedQuad> list = new ArrayList<>();
/*  48 */     list.addAll(modelBase.getGeneralQuads());
/*  49 */     list.addAll(modelAdd.getGeneralQuads());
/*  50 */     EnumFacing[] aenumfacing = EnumFacing.VALUES;
/*  51 */     List<List> list1 = new ArrayList();
/*     */     
/*  53 */     for (int i = 0; i < aenumfacing.length; i++) {
/*     */       
/*  55 */       EnumFacing enumfacing = aenumfacing[i];
/*  56 */       List list2 = new ArrayList();
/*  57 */       list2.addAll(modelBase.getFaceQuads(enumfacing));
/*  58 */       list2.addAll(modelAdd.getFaceQuads(enumfacing));
/*  59 */       list1.add(list2);
/*     */     } 
/*     */     
/*  62 */     boolean flag = modelBase.isAmbientOcclusion();
/*  63 */     boolean flag1 = modelBase.isBuiltInRenderer();
/*  64 */     TextureAtlasSprite textureatlassprite = modelBase.getParticleTexture();
/*  65 */     ItemCameraTransforms itemcameratransforms = modelBase.getItemCameraTransforms();
/*  66 */     return (IBakedModel)new SimpleBakedModel(list, list1, flag, flag1, textureatlassprite, itemcameratransforms);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static BakedQuad makeBakedQuad(EnumFacing facing, TextureAtlasSprite sprite, int tintIndex) {
/*  72 */     Vector3f vector3f = new Vector3f(0.0F, 0.0F, 0.0F);
/*  73 */     Vector3f vector3f1 = new Vector3f(16.0F, 16.0F, 16.0F);
/*  74 */     BlockFaceUV blockfaceuv = new BlockFaceUV(new float[] { 0.0F, 0.0F, 16.0F, 16.0F }, 0);
/*  75 */     BlockPartFace blockpartface = new BlockPartFace(facing, tintIndex, "#" + facing.getName(), blockfaceuv);
/*  76 */     ModelRotation modelrotation = ModelRotation.X0_Y0;
/*  77 */     BlockPartRotation blockpartrotation = null;
/*  78 */     boolean flag = false;
/*  79 */     boolean flag1 = true;
/*  80 */     FaceBakery facebakery = new FaceBakery();
/*  81 */     BakedQuad bakedquad = facebakery.makeBakedQuad(vector3f, vector3f1, blockpartface, sprite, facing, modelrotation, blockpartrotation, flag, flag1);
/*  82 */     return bakedquad;
/*     */   }
/*     */ 
/*     */   
/*     */   public static IBakedModel makeModel(String modelName, String spriteOldName, String spriteNewName) {
/*  87 */     TextureMap texturemap = Config.getMinecraft().getTextureMapBlocks();
/*  88 */     TextureAtlasSprite textureatlassprite = texturemap.getSpriteSafe(spriteOldName);
/*  89 */     TextureAtlasSprite textureatlassprite1 = texturemap.getSpriteSafe(spriteNewName);
/*  90 */     return makeModel(modelName, textureatlassprite, textureatlassprite1);
/*     */   }
/*     */ 
/*     */   
/*     */   public static IBakedModel makeModel(String modelName, TextureAtlasSprite spriteOld, TextureAtlasSprite spriteNew) {
/*  95 */     if (spriteOld != null && spriteNew != null) {
/*     */       
/*  97 */       ModelManager modelmanager = Config.getModelManager();
/*     */       
/*  99 */       if (modelmanager == null)
/*     */       {
/* 101 */         return null;
/*     */       }
/*     */ 
/*     */       
/* 105 */       ModelResourceLocation modelresourcelocation = new ModelResourceLocation(modelName, "normal");
/* 106 */       IBakedModel ibakedmodel = modelmanager.getModel(modelresourcelocation);
/*     */       
/* 108 */       if (ibakedmodel != null && ibakedmodel != modelmanager.getMissingModel()) {
/*     */         
/* 110 */         IBakedModel ibakedmodel1 = ModelUtils.duplicateModel(ibakedmodel);
/* 111 */         EnumFacing[] aenumfacing = EnumFacing.VALUES;
/*     */         
/* 113 */         for (int i = 0; i < aenumfacing.length; i++) {
/*     */           
/* 115 */           EnumFacing enumfacing = aenumfacing[i];
/* 116 */           List<BakedQuad> list = ibakedmodel1.getFaceQuads(enumfacing);
/* 117 */           replaceTexture(list, spriteOld, spriteNew);
/*     */         } 
/*     */         
/* 120 */         List<BakedQuad> list1 = ibakedmodel1.getGeneralQuads();
/* 121 */         replaceTexture(list1, spriteOld, spriteNew);
/* 122 */         return ibakedmodel1;
/*     */       } 
/*     */ 
/*     */       
/* 126 */       return null;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 132 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void replaceTexture(List<BakedQuad> quads, TextureAtlasSprite spriteOld, TextureAtlasSprite spriteNew) {
/* 138 */     List<BakedQuad> list = new ArrayList<>();
/*     */     
/* 140 */     for (BakedQuad bakedquad : quads) {
/*     */       BreakingFour breakingFour;
/* 142 */       if (bakedquad.getSprite() == spriteOld)
/*     */       {
/* 144 */         breakingFour = new BreakingFour(bakedquad, spriteNew);
/*     */       }
/*     */       
/* 147 */       list.add(breakingFour);
/*     */     } 
/*     */     
/* 150 */     quads.clear();
/* 151 */     quads.addAll(list);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void snapVertexPosition(Vector3f pos) {
/* 156 */     pos.setX(snapVertexCoord(pos.getX()));
/* 157 */     pos.setY(snapVertexCoord(pos.getY()));
/* 158 */     pos.setZ(snapVertexCoord(pos.getZ()));
/*     */   }
/*     */ 
/*     */   
/*     */   private static float snapVertexCoord(float x) {
/* 163 */     return (x > -1.0E-6F && x < 1.0E-6F) ? 0.0F : ((x > 0.999999F && x < 1.000001F) ? 1.0F : x);
/*     */   }
/*     */ 
/*     */   
/*     */   public static AxisAlignedBB getOffsetBoundingBox(AxisAlignedBB aabb, Block.EnumOffsetType offsetType, BlockPos pos) {
/* 168 */     int i = pos.getX();
/* 169 */     int j = pos.getZ();
/* 170 */     long k = (i * 3129871) ^ j * 116129781L;
/* 171 */     k = k * k * 42317861L + k * 11L;
/* 172 */     double d0 = (((float)(k >> 16L & 0xFL) / 15.0F) - 0.5D) * 0.5D;
/* 173 */     double d1 = (((float)(k >> 24L & 0xFL) / 15.0F) - 0.5D) * 0.5D;
/* 174 */     double d2 = 0.0D;
/*     */     
/* 176 */     if (offsetType == Block.EnumOffsetType.XYZ)
/*     */     {
/* 178 */       d2 = (((float)(k >> 20L & 0xFL) / 15.0F) - 1.0D) * 0.2D;
/*     */     }
/*     */     
/* 181 */     return aabb.offset(d0, d2, d1);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\model\BlockModelUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */