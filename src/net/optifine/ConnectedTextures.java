/*      */ package net.optifine;
/*      */ import java.io.InputStream;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.HashSet;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.block.BlockPane;
/*      */ import net.minecraft.block.BlockStainedGlassPane;
/*      */ import net.minecraft.block.properties.IProperty;
/*      */ import net.minecraft.block.state.BlockStateBase;
/*      */ import net.minecraft.block.state.IBlockState;
/*      */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*      */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*      */ import net.minecraft.client.renderer.texture.TextureMap;
/*      */ import net.minecraft.client.resources.IResourcePack;
/*      */ import net.minecraft.client.resources.model.IBakedModel;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.src.Config;
/*      */ import net.minecraft.util.BlockPos;
/*      */ import net.minecraft.util.EnumFacing;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.minecraft.world.IBlockAccess;
/*      */ import net.minecraft.world.biome.BiomeGenBase;
/*      */ import net.optifine.model.ListQuadsOverlay;
/*      */ import net.optifine.reflect.Reflector;
/*      */ import net.optifine.render.RenderEnv;
/*      */ import net.optifine.util.PropertiesOrdered;
/*      */ import net.optifine.util.ResUtils;
/*      */ 
/*      */ public class ConnectedTextures {
/*   34 */   private static Map[] spriteQuadMaps = null;
/*   35 */   private static Map[] spriteQuadFullMaps = null;
/*   36 */   private static Map[][] spriteQuadCompactMaps = (Map[][])null;
/*   37 */   private static ConnectedProperties[][] blockProperties = (ConnectedProperties[][])null;
/*   38 */   private static ConnectedProperties[][] tileProperties = (ConnectedProperties[][])null;
/*      */   private static boolean multipass = false;
/*      */   protected static final int UNKNOWN = -1;
/*      */   protected static final int Y_NEG_DOWN = 0;
/*      */   protected static final int Y_POS_UP = 1;
/*      */   protected static final int Z_NEG_NORTH = 2;
/*      */   protected static final int Z_POS_SOUTH = 3;
/*      */   protected static final int X_NEG_WEST = 4;
/*      */   protected static final int X_POS_EAST = 5;
/*      */   private static final int Y_AXIS = 0;
/*      */   private static final int Z_AXIS = 1;
/*      */   private static final int X_AXIS = 2;
/*   50 */   public static final IBlockState AIR_DEFAULT_STATE = Blocks.air.getDefaultState();
/*   51 */   private static TextureAtlasSprite emptySprite = null;
/*   52 */   private static final BlockDir[] SIDES_Y_NEG_DOWN = new BlockDir[] { BlockDir.WEST, BlockDir.EAST, BlockDir.NORTH, BlockDir.SOUTH };
/*   53 */   private static final BlockDir[] SIDES_Y_POS_UP = new BlockDir[] { BlockDir.WEST, BlockDir.EAST, BlockDir.SOUTH, BlockDir.NORTH };
/*   54 */   private static final BlockDir[] SIDES_Z_NEG_NORTH = new BlockDir[] { BlockDir.EAST, BlockDir.WEST, BlockDir.DOWN, BlockDir.UP };
/*   55 */   private static final BlockDir[] SIDES_Z_POS_SOUTH = new BlockDir[] { BlockDir.WEST, BlockDir.EAST, BlockDir.DOWN, BlockDir.UP };
/*   56 */   private static final BlockDir[] SIDES_X_NEG_WEST = new BlockDir[] { BlockDir.NORTH, BlockDir.SOUTH, BlockDir.DOWN, BlockDir.UP };
/*   57 */   private static final BlockDir[] SIDES_X_POS_EAST = new BlockDir[] { BlockDir.SOUTH, BlockDir.NORTH, BlockDir.DOWN, BlockDir.UP };
/*   58 */   private static final BlockDir[] SIDES_Z_NEG_NORTH_Z_AXIS = new BlockDir[] { BlockDir.WEST, BlockDir.EAST, BlockDir.UP, BlockDir.DOWN };
/*   59 */   private static final BlockDir[] SIDES_X_POS_EAST_X_AXIS = new BlockDir[] { BlockDir.NORTH, BlockDir.SOUTH, BlockDir.UP, BlockDir.DOWN };
/*   60 */   private static final BlockDir[] EDGES_Y_NEG_DOWN = new BlockDir[] { BlockDir.NORTH_EAST, BlockDir.NORTH_WEST, BlockDir.SOUTH_EAST, BlockDir.SOUTH_WEST };
/*   61 */   private static final BlockDir[] EDGES_Y_POS_UP = new BlockDir[] { BlockDir.SOUTH_EAST, BlockDir.SOUTH_WEST, BlockDir.NORTH_EAST, BlockDir.NORTH_WEST };
/*   62 */   private static final BlockDir[] EDGES_Z_NEG_NORTH = new BlockDir[] { BlockDir.DOWN_WEST, BlockDir.DOWN_EAST, BlockDir.UP_WEST, BlockDir.UP_EAST };
/*   63 */   private static final BlockDir[] EDGES_Z_POS_SOUTH = new BlockDir[] { BlockDir.DOWN_EAST, BlockDir.DOWN_WEST, BlockDir.UP_EAST, BlockDir.UP_WEST };
/*   64 */   private static final BlockDir[] EDGES_X_NEG_WEST = new BlockDir[] { BlockDir.DOWN_SOUTH, BlockDir.DOWN_NORTH, BlockDir.UP_SOUTH, BlockDir.UP_NORTH };
/*   65 */   private static final BlockDir[] EDGES_X_POS_EAST = new BlockDir[] { BlockDir.DOWN_NORTH, BlockDir.DOWN_SOUTH, BlockDir.UP_NORTH, BlockDir.UP_SOUTH };
/*   66 */   private static final BlockDir[] EDGES_Z_NEG_NORTH_Z_AXIS = new BlockDir[] { BlockDir.UP_EAST, BlockDir.UP_WEST, BlockDir.DOWN_EAST, BlockDir.DOWN_WEST };
/*   67 */   private static final BlockDir[] EDGES_X_POS_EAST_X_AXIS = new BlockDir[] { BlockDir.UP_SOUTH, BlockDir.UP_NORTH, BlockDir.DOWN_SOUTH, BlockDir.DOWN_NORTH };
/*   68 */   public static final TextureAtlasSprite SPRITE_DEFAULT = new TextureAtlasSprite("<default>");
/*      */ 
/*      */   
/*      */   public static BakedQuad[] getConnectedTexture(IBlockAccess blockAccess, IBlockState blockState, BlockPos blockPos, BakedQuad quad, RenderEnv renderEnv) {
/*   72 */     TextureAtlasSprite textureatlassprite = quad.getSprite();
/*      */     
/*   74 */     if (textureatlassprite == null)
/*      */     {
/*   76 */       return renderEnv.getArrayQuadsCtm(quad);
/*      */     }
/*      */ 
/*      */     
/*   80 */     Block block = blockState.getBlock();
/*      */     
/*   82 */     if (skipConnectedTexture(blockAccess, blockState, blockPos, quad, renderEnv)) {
/*      */       
/*   84 */       quad = getQuad(emptySprite, quad);
/*   85 */       return renderEnv.getArrayQuadsCtm(quad);
/*      */     } 
/*      */ 
/*      */     
/*   89 */     EnumFacing enumfacing = quad.getFace();
/*   90 */     BakedQuad[] abakedquad = getConnectedTextureMultiPass(blockAccess, blockState, blockPos, enumfacing, quad, renderEnv);
/*   91 */     return abakedquad;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean skipConnectedTexture(IBlockAccess blockAccess, IBlockState blockState, BlockPos blockPos, BakedQuad quad, RenderEnv renderEnv) {
/*   98 */     Block block = blockState.getBlock();
/*      */     
/*  100 */     if (block instanceof BlockPane) {
/*      */       
/*  102 */       TextureAtlasSprite textureatlassprite = quad.getSprite();
/*      */       
/*  104 */       if (textureatlassprite.getIconName().startsWith("minecraft:blocks/glass_pane_top")) {
/*      */         
/*  106 */         IBlockState iblockstate1 = blockAccess.getBlockState(blockPos.offset(quad.getFace()));
/*  107 */         return (iblockstate1 == blockState);
/*      */       } 
/*      */     } 
/*      */     
/*  111 */     if (block instanceof BlockPane) {
/*      */       
/*  113 */       EnumFacing enumfacing = quad.getFace();
/*      */       
/*  115 */       if (enumfacing != EnumFacing.UP && enumfacing != EnumFacing.DOWN)
/*      */       {
/*  117 */         return false;
/*      */       }
/*      */       
/*  120 */       if (!quad.isFaceQuad())
/*      */       {
/*  122 */         return false;
/*      */       }
/*      */       
/*  125 */       BlockPos blockpos = blockPos.offset(quad.getFace());
/*  126 */       IBlockState iblockstate = blockAccess.getBlockState(blockpos);
/*      */       
/*  128 */       if (iblockstate.getBlock() != block)
/*      */       {
/*  130 */         return false;
/*      */       }
/*      */       
/*  133 */       if (block == Blocks.stained_glass_pane && iblockstate.getValue((IProperty)BlockStainedGlassPane.COLOR) != blockState.getValue((IProperty)BlockStainedGlassPane.COLOR))
/*      */       {
/*  135 */         return false;
/*      */       }
/*      */       
/*  138 */       iblockstate = iblockstate.getBlock().getActualState(iblockstate, blockAccess, blockpos);
/*  139 */       double d0 = quad.getMidX();
/*      */       
/*  141 */       if (d0 < 0.4D) {
/*      */         
/*  143 */         if (((Boolean)iblockstate.getValue((IProperty)BlockPane.WEST)).booleanValue())
/*      */         {
/*  145 */           return true;
/*      */         }
/*      */       }
/*  148 */       else if (d0 > 0.6D) {
/*      */         
/*  150 */         if (((Boolean)iblockstate.getValue((IProperty)BlockPane.EAST)).booleanValue())
/*      */         {
/*  152 */           return true;
/*      */         }
/*      */       }
/*      */       else {
/*      */         
/*  157 */         double d1 = quad.getMidZ();
/*      */         
/*  159 */         if (d1 < 0.4D) {
/*      */           
/*  161 */           if (((Boolean)iblockstate.getValue((IProperty)BlockPane.NORTH)).booleanValue())
/*      */           {
/*  163 */             return true;
/*      */           }
/*      */         }
/*      */         else {
/*      */           
/*  168 */           if (d1 <= 0.6D)
/*      */           {
/*  170 */             return true;
/*      */           }
/*      */           
/*  173 */           if (((Boolean)iblockstate.getValue((IProperty)BlockPane.SOUTH)).booleanValue())
/*      */           {
/*  175 */             return true;
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  181 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   protected static BakedQuad[] getQuads(TextureAtlasSprite sprite, BakedQuad quadIn, RenderEnv renderEnv) {
/*  186 */     if (sprite == null)
/*      */     {
/*  188 */       return null;
/*      */     }
/*  190 */     if (sprite == SPRITE_DEFAULT)
/*      */     {
/*  192 */       return renderEnv.getArrayQuadsCtm(quadIn);
/*      */     }
/*      */ 
/*      */     
/*  196 */     BakedQuad bakedquad = getQuad(sprite, quadIn);
/*  197 */     BakedQuad[] abakedquad = renderEnv.getArrayQuadsCtm(bakedquad);
/*  198 */     return abakedquad;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static synchronized BakedQuad getQuad(TextureAtlasSprite sprite, BakedQuad quadIn) {
/*  204 */     if (spriteQuadMaps == null)
/*      */     {
/*  206 */       return quadIn;
/*      */     }
/*      */ 
/*      */     
/*  210 */     int i = sprite.getIndexInMap();
/*      */     
/*  212 */     if (i >= 0 && i < spriteQuadMaps.length) {
/*      */       
/*  214 */       Map<Object, Object> map = spriteQuadMaps[i];
/*      */       
/*  216 */       if (map == null) {
/*      */         
/*  218 */         map = new IdentityHashMap<>(1);
/*  219 */         spriteQuadMaps[i] = map;
/*      */       } 
/*      */       
/*  222 */       BakedQuad bakedquad = (BakedQuad)map.get(quadIn);
/*      */       
/*  224 */       if (bakedquad == null) {
/*      */         
/*  226 */         bakedquad = makeSpriteQuad(quadIn, sprite);
/*  227 */         map.put(quadIn, bakedquad);
/*      */       } 
/*      */       
/*  230 */       return bakedquad;
/*      */     } 
/*      */ 
/*      */     
/*  234 */     return quadIn;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static synchronized BakedQuad getQuadFull(TextureAtlasSprite sprite, BakedQuad quadIn, int tintIndex) {
/*  241 */     if (spriteQuadFullMaps == null)
/*      */     {
/*  243 */       return null;
/*      */     }
/*  245 */     if (sprite == null)
/*      */     {
/*  247 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  251 */     int i = sprite.getIndexInMap();
/*      */     
/*  253 */     if (i >= 0 && i < spriteQuadFullMaps.length) {
/*      */       
/*  255 */       Map<EnumFacing, Object> map = spriteQuadFullMaps[i];
/*      */       
/*  257 */       if (map == null) {
/*      */         
/*  259 */         map = new EnumMap<>(EnumFacing.class);
/*  260 */         spriteQuadFullMaps[i] = map;
/*      */       } 
/*      */       
/*  263 */       EnumFacing enumfacing = quadIn.getFace();
/*  264 */       BakedQuad bakedquad = (BakedQuad)map.get(enumfacing);
/*      */       
/*  266 */       if (bakedquad == null) {
/*      */         
/*  268 */         bakedquad = BlockModelUtils.makeBakedQuad(enumfacing, sprite, tintIndex);
/*  269 */         map.put(enumfacing, bakedquad);
/*      */       } 
/*      */       
/*  272 */       return bakedquad;
/*      */     } 
/*      */ 
/*      */     
/*  276 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static BakedQuad makeSpriteQuad(BakedQuad quad, TextureAtlasSprite sprite) {
/*  283 */     int[] aint = (int[])quad.getVertexData().clone();
/*  284 */     TextureAtlasSprite textureatlassprite = quad.getSprite();
/*      */     
/*  286 */     for (int i = 0; i < 4; i++)
/*      */     {
/*  288 */       fixVertex(aint, i, textureatlassprite, sprite);
/*      */     }
/*      */     
/*  291 */     BakedQuad bakedquad = new BakedQuad(aint, quad.getTintIndex(), quad.getFace(), sprite);
/*  292 */     return bakedquad;
/*      */   }
/*      */ 
/*      */   
/*      */   private static void fixVertex(int[] data, int vertex, TextureAtlasSprite spriteFrom, TextureAtlasSprite spriteTo) {
/*  297 */     int i = data.length / 4;
/*  298 */     int j = i * vertex;
/*  299 */     float f = Float.intBitsToFloat(data[j + 4]);
/*  300 */     float f1 = Float.intBitsToFloat(data[j + 4 + 1]);
/*  301 */     double d0 = spriteFrom.getSpriteU16(f);
/*  302 */     double d1 = spriteFrom.getSpriteV16(f1);
/*  303 */     data[j + 4] = Float.floatToRawIntBits(spriteTo.getInterpolatedU(d0));
/*  304 */     data[j + 4 + 1] = Float.floatToRawIntBits(spriteTo.getInterpolatedV(d1));
/*      */   }
/*      */ 
/*      */   
/*      */   private static BakedQuad[] getConnectedTextureMultiPass(IBlockAccess blockAccess, IBlockState blockState, BlockPos blockPos, EnumFacing side, BakedQuad quad, RenderEnv renderEnv) {
/*  309 */     BakedQuad[] abakedquad = getConnectedTextureSingle(blockAccess, blockState, blockPos, side, quad, true, 0, renderEnv);
/*      */     
/*  311 */     if (!multipass)
/*      */     {
/*  313 */       return abakedquad;
/*      */     }
/*  315 */     if (abakedquad.length == 1 && abakedquad[0] == quad)
/*      */     {
/*  317 */       return abakedquad;
/*      */     }
/*      */ 
/*      */     
/*  321 */     List<BakedQuad> list = renderEnv.getListQuadsCtmMultipass(abakedquad);
/*      */     
/*  323 */     for (int i = 0; i < list.size(); i++) {
/*      */       
/*  325 */       BakedQuad bakedquad = list.get(i);
/*  326 */       BakedQuad bakedquad1 = bakedquad;
/*      */       
/*  328 */       for (int j = 0; j < 3; j++) {
/*      */         
/*  330 */         BakedQuad[] abakedquad1 = getConnectedTextureSingle(blockAccess, blockState, blockPos, side, bakedquad1, false, j + 1, renderEnv);
/*      */         
/*  332 */         if (abakedquad1.length != 1 || abakedquad1[0] == bakedquad1) {
/*      */           break;
/*      */         }
/*      */ 
/*      */         
/*  337 */         bakedquad1 = abakedquad1[0];
/*      */       } 
/*      */       
/*  340 */       list.set(i, bakedquad1);
/*      */     } 
/*      */     
/*  343 */     for (int k = 0; k < abakedquad.length; k++)
/*      */     {
/*  345 */       abakedquad[k] = list.get(k);
/*      */     }
/*      */     
/*  348 */     return abakedquad;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static BakedQuad[] getConnectedTextureSingle(IBlockAccess blockAccess, IBlockState blockState, BlockPos blockPos, EnumFacing facing, BakedQuad quad, boolean checkBlocks, int pass, RenderEnv renderEnv) {
/*  354 */     Block block = blockState.getBlock();
/*      */     
/*  356 */     if (!(blockState instanceof BlockStateBase))
/*      */     {
/*  358 */       return renderEnv.getArrayQuadsCtm(quad);
/*      */     }
/*      */ 
/*      */     
/*  362 */     BlockStateBase blockstatebase = (BlockStateBase)blockState;
/*  363 */     TextureAtlasSprite textureatlassprite = quad.getSprite();
/*      */     
/*  365 */     if (tileProperties != null) {
/*      */       
/*  367 */       int i = textureatlassprite.getIndexInMap();
/*      */       
/*  369 */       if (i >= 0 && i < tileProperties.length) {
/*      */         
/*  371 */         ConnectedProperties[] aconnectedproperties = tileProperties[i];
/*      */         
/*  373 */         if (aconnectedproperties != null) {
/*      */           
/*  375 */           int j = getSide(facing);
/*      */           
/*  377 */           for (int k = 0; k < aconnectedproperties.length; k++) {
/*      */             
/*  379 */             ConnectedProperties connectedproperties = aconnectedproperties[k];
/*      */             
/*  381 */             if (connectedproperties != null && connectedproperties.matchesBlockId(blockstatebase.getBlockId())) {
/*      */               
/*  383 */               BakedQuad[] abakedquad = getConnectedTexture(connectedproperties, blockAccess, blockstatebase, blockPos, j, quad, pass, renderEnv);
/*      */               
/*  385 */               if (abakedquad != null)
/*      */               {
/*  387 */                 return abakedquad;
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  395 */     if (blockProperties != null && checkBlocks) {
/*      */       
/*  397 */       int l = renderEnv.getBlockId();
/*      */       
/*  399 */       if (l >= 0 && l < blockProperties.length) {
/*      */         
/*  401 */         ConnectedProperties[] aconnectedproperties1 = blockProperties[l];
/*      */         
/*  403 */         if (aconnectedproperties1 != null) {
/*      */           
/*  405 */           int i1 = getSide(facing);
/*      */           
/*  407 */           for (int j1 = 0; j1 < aconnectedproperties1.length; j1++) {
/*      */             
/*  409 */             ConnectedProperties connectedproperties1 = aconnectedproperties1[j1];
/*      */             
/*  411 */             if (connectedproperties1 != null && connectedproperties1.matchesIcon(textureatlassprite)) {
/*      */               
/*  413 */               BakedQuad[] abakedquad1 = getConnectedTexture(connectedproperties1, blockAccess, blockstatebase, blockPos, i1, quad, pass, renderEnv);
/*      */               
/*  415 */               if (abakedquad1 != null)
/*      */               {
/*  417 */                 return abakedquad1;
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  425 */     return renderEnv.getArrayQuadsCtm(quad);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getSide(EnumFacing facing) {
/*  431 */     if (facing == null)
/*      */     {
/*  433 */       return -1;
/*      */     }
/*      */ 
/*      */     
/*  437 */     switch (facing) {
/*      */       
/*      */       case DOWN:
/*  440 */         return 0;
/*      */       
/*      */       case UP:
/*  443 */         return 1;
/*      */       
/*      */       case EAST:
/*  446 */         return 5;
/*      */       
/*      */       case WEST:
/*  449 */         return 4;
/*      */       
/*      */       case NORTH:
/*  452 */         return 2;
/*      */       
/*      */       case SOUTH:
/*  455 */         return 3;
/*      */     } 
/*      */     
/*  458 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static EnumFacing getFacing(int side) {
/*  465 */     switch (side) {
/*      */       
/*      */       case 0:
/*  468 */         return EnumFacing.DOWN;
/*      */       
/*      */       case 1:
/*  471 */         return EnumFacing.UP;
/*      */       
/*      */       case 2:
/*  474 */         return EnumFacing.NORTH;
/*      */       
/*      */       case 3:
/*  477 */         return EnumFacing.SOUTH;
/*      */       
/*      */       case 4:
/*  480 */         return EnumFacing.WEST;
/*      */       
/*      */       case 5:
/*  483 */         return EnumFacing.EAST;
/*      */     } 
/*      */     
/*  486 */     return EnumFacing.UP;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static BakedQuad[] getConnectedTexture(ConnectedProperties cp, IBlockAccess blockAccess, BlockStateBase blockState, BlockPos blockPos, int side, BakedQuad quad, int pass, RenderEnv renderEnv) {
/*  492 */     int i = 0;
/*  493 */     int j = blockState.getMetadata();
/*  494 */     int k = j;
/*  495 */     Block block = blockState.getBlock();
/*      */     
/*  497 */     if (block instanceof net.minecraft.block.BlockRotatedPillar) {
/*      */       
/*  499 */       i = getWoodAxis(side, j);
/*      */       
/*  501 */       if (cp.getMetadataMax() <= 3)
/*      */       {
/*  503 */         k = j & 0x3;
/*      */       }
/*      */     } 
/*      */     
/*  507 */     if (block instanceof net.minecraft.block.BlockQuartz) {
/*      */       
/*  509 */       i = getQuartzAxis(side, j);
/*      */       
/*  511 */       if (cp.getMetadataMax() <= 2 && k > 2)
/*      */       {
/*  513 */         k = 2;
/*      */       }
/*      */     } 
/*      */     
/*  517 */     if (!cp.matchesBlock(blockState.getBlockId(), k))
/*      */     {
/*  519 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  523 */     if (side >= 0 && cp.faces != 63) {
/*      */       
/*  525 */       int l = side;
/*      */       
/*  527 */       if (i != 0)
/*      */       {
/*  529 */         l = fixSideByAxis(side, i);
/*      */       }
/*      */       
/*  532 */       if ((1 << l & cp.faces) == 0)
/*      */       {
/*  534 */         return null;
/*      */       }
/*      */     } 
/*      */     
/*  538 */     int i1 = blockPos.getY();
/*      */     
/*  540 */     if (cp.heights != null && !cp.heights.isInRange(i1))
/*      */     {
/*  542 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  546 */     if (cp.biomes != null) {
/*      */       
/*  548 */       BiomeGenBase biomegenbase = blockAccess.getBiomeGenForCoords(blockPos);
/*      */       
/*  550 */       if (!cp.matchesBiome(biomegenbase))
/*      */       {
/*  552 */         return null;
/*      */       }
/*      */     } 
/*      */     
/*  556 */     if (cp.nbtName != null) {
/*      */       
/*  558 */       String s = TileEntityUtils.getTileEntityName(blockAccess, blockPos);
/*      */       
/*  560 */       if (!cp.nbtName.matchesValue(s))
/*      */       {
/*  562 */         return null;
/*      */       }
/*      */     } 
/*      */     
/*  566 */     TextureAtlasSprite textureatlassprite = quad.getSprite();
/*      */     
/*  568 */     switch (cp.method) {
/*      */       
/*      */       case 1:
/*  571 */         return getQuads(getConnectedTextureCtm(cp, blockAccess, (IBlockState)blockState, blockPos, i, side, textureatlassprite, j, renderEnv), quad, renderEnv);
/*      */       
/*      */       case 2:
/*  574 */         return getQuads(getConnectedTextureHorizontal(cp, blockAccess, (IBlockState)blockState, blockPos, i, side, textureatlassprite, j), quad, renderEnv);
/*      */       
/*      */       case 3:
/*  577 */         return getQuads(getConnectedTextureTop(cp, blockAccess, (IBlockState)blockState, blockPos, i, side, textureatlassprite, j), quad, renderEnv);
/*      */       
/*      */       case 4:
/*  580 */         return getQuads(getConnectedTextureRandom(cp, blockAccess, blockState, blockPos, side), quad, renderEnv);
/*      */       
/*      */       case 5:
/*  583 */         return getQuads(getConnectedTextureRepeat(cp, blockPos, side), quad, renderEnv);
/*      */       
/*      */       case 6:
/*  586 */         return getQuads(getConnectedTextureVertical(cp, blockAccess, (IBlockState)blockState, blockPos, i, side, textureatlassprite, j), quad, renderEnv);
/*      */       
/*      */       case 7:
/*  589 */         return getQuads(getConnectedTextureFixed(cp), quad, renderEnv);
/*      */       
/*      */       case 8:
/*  592 */         return getQuads(getConnectedTextureHorizontalVertical(cp, blockAccess, (IBlockState)blockState, blockPos, i, side, textureatlassprite, j), quad, renderEnv);
/*      */       
/*      */       case 9:
/*  595 */         return getQuads(getConnectedTextureVerticalHorizontal(cp, blockAccess, (IBlockState)blockState, blockPos, i, side, textureatlassprite, j), quad, renderEnv);
/*      */       
/*      */       case 10:
/*  598 */         if (pass == 0)
/*      */         {
/*  600 */           return getConnectedTextureCtmCompact(cp, blockAccess, (IBlockState)blockState, blockPos, i, side, quad, j, renderEnv);
/*      */         }
/*      */       
/*      */       default:
/*  604 */         return null;
/*      */       
/*      */       case 11:
/*  607 */         return getConnectedTextureOverlay(cp, blockAccess, (IBlockState)blockState, blockPos, i, side, quad, j, renderEnv);
/*      */       
/*      */       case 12:
/*  610 */         return getConnectedTextureOverlayFixed(cp, quad, renderEnv);
/*      */       
/*      */       case 13:
/*  613 */         return getConnectedTextureOverlayRandom(cp, blockAccess, blockState, blockPos, side, quad, renderEnv);
/*      */       
/*      */       case 14:
/*  616 */         return getConnectedTextureOverlayRepeat(cp, blockPos, side, quad, renderEnv);
/*      */       case 15:
/*      */         break;
/*  619 */     }  return getConnectedTextureOverlayCtm(cp, blockAccess, (IBlockState)blockState, blockPos, i, side, quad, j, renderEnv);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int fixSideByAxis(int side, int vertAxis) {
/*  627 */     switch (vertAxis) {
/*      */       
/*      */       case 0:
/*  630 */         return side;
/*      */       
/*      */       case 1:
/*  633 */         switch (side) {
/*      */           
/*      */           case 0:
/*  636 */             return 2;
/*      */           
/*      */           case 1:
/*  639 */             return 3;
/*      */           
/*      */           case 2:
/*  642 */             return 1;
/*      */           
/*      */           case 3:
/*  645 */             return 0;
/*      */         } 
/*      */         
/*  648 */         return side;
/*      */ 
/*      */       
/*      */       case 2:
/*  652 */         switch (side) {
/*      */           
/*      */           case 0:
/*  655 */             return 4;
/*      */           
/*      */           case 1:
/*  658 */             return 5;
/*      */ 
/*      */ 
/*      */           
/*      */           default:
/*  663 */             return side;
/*      */           
/*      */           case 4:
/*  666 */             return 1;
/*      */           case 5:
/*      */             break;
/*  669 */         }  return 0;
/*      */     } 
/*      */ 
/*      */     
/*  673 */     return side;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static int getWoodAxis(int side, int metadata) {
/*  679 */     int i = (metadata & 0xC) >> 2;
/*      */     
/*  681 */     switch (i) {
/*      */       
/*      */       case 1:
/*  684 */         return 2;
/*      */       
/*      */       case 2:
/*  687 */         return 1;
/*      */     } 
/*      */     
/*  690 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static int getQuartzAxis(int side, int metadata) {
/*  696 */     switch (metadata) {
/*      */       
/*      */       case 3:
/*  699 */         return 2;
/*      */       
/*      */       case 4:
/*  702 */         return 1;
/*      */     } 
/*      */     
/*  705 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static TextureAtlasSprite getConnectedTextureRandom(ConnectedProperties cp, IBlockAccess blockAccess, BlockStateBase blockState, BlockPos blockPos, int side) {
/*  711 */     if (cp.tileIcons.length == 1)
/*      */     {
/*  713 */       return cp.tileIcons[0];
/*      */     }
/*      */ 
/*      */     
/*  717 */     int i = side / cp.symmetry * cp.symmetry;
/*      */     
/*  719 */     if (cp.linked) {
/*      */       
/*  721 */       BlockPos blockpos = blockPos.down();
/*      */       
/*  723 */       for (IBlockState iblockstate = blockAccess.getBlockState(blockpos); iblockstate.getBlock() == blockState.getBlock(); iblockstate = blockAccess.getBlockState(blockpos)) {
/*      */         
/*  725 */         blockPos = blockpos;
/*  726 */         blockpos = blockpos.down();
/*      */         
/*  728 */         if (blockpos.getY() < 0) {
/*      */           break;
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  735 */     int l = Config.getRandom(blockPos, i) & Integer.MAX_VALUE;
/*      */     
/*  737 */     for (int i1 = 0; i1 < cp.randomLoops; i1++)
/*      */     {
/*  739 */       l = Config.intHash(l);
/*      */     }
/*      */     
/*  742 */     int j1 = 0;
/*      */     
/*  744 */     if (cp.weights == null) {
/*      */       
/*  746 */       j1 = l % cp.tileIcons.length;
/*      */     }
/*      */     else {
/*      */       
/*  750 */       int j = l % cp.sumAllWeights;
/*  751 */       int[] aint = cp.sumWeights;
/*      */       
/*  753 */       for (int k = 0; k < aint.length; k++) {
/*      */         
/*  755 */         if (j < aint[k]) {
/*      */           
/*  757 */           j1 = k;
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     } 
/*  763 */     return cp.tileIcons[j1];
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static TextureAtlasSprite getConnectedTextureFixed(ConnectedProperties cp) {
/*  769 */     return cp.tileIcons[0];
/*      */   }
/*      */ 
/*      */   
/*      */   private static TextureAtlasSprite getConnectedTextureRepeat(ConnectedProperties cp, BlockPos blockPos, int side) {
/*  774 */     if (cp.tileIcons.length == 1)
/*      */     {
/*  776 */       return cp.tileIcons[0];
/*      */     }
/*      */ 
/*      */     
/*  780 */     int i = blockPos.getX();
/*  781 */     int j = blockPos.getY();
/*  782 */     int k = blockPos.getZ();
/*  783 */     int l = 0;
/*  784 */     int i1 = 0;
/*      */     
/*  786 */     switch (side) {
/*      */       
/*      */       case 0:
/*  789 */         l = i;
/*  790 */         i1 = -k - 1;
/*      */         break;
/*      */       
/*      */       case 1:
/*  794 */         l = i;
/*  795 */         i1 = k;
/*      */         break;
/*      */       
/*      */       case 2:
/*  799 */         l = -i - 1;
/*  800 */         i1 = -j;
/*      */         break;
/*      */       
/*      */       case 3:
/*  804 */         l = i;
/*  805 */         i1 = -j;
/*      */         break;
/*      */       
/*      */       case 4:
/*  809 */         l = k;
/*  810 */         i1 = -j;
/*      */         break;
/*      */       
/*      */       case 5:
/*  814 */         l = -k - 1;
/*  815 */         i1 = -j;
/*      */         break;
/*      */     } 
/*  818 */     l %= cp.width;
/*  819 */     i1 %= cp.height;
/*      */     
/*  821 */     if (l < 0)
/*      */     {
/*  823 */       l += cp.width;
/*      */     }
/*      */     
/*  826 */     if (i1 < 0)
/*      */     {
/*  828 */       i1 += cp.height;
/*      */     }
/*      */     
/*  831 */     int j1 = i1 * cp.width + l;
/*  832 */     return cp.tileIcons[j1];
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static TextureAtlasSprite getConnectedTextureCtm(ConnectedProperties cp, IBlockAccess blockAccess, IBlockState blockState, BlockPos blockPos, int vertAxis, int side, TextureAtlasSprite icon, int metadata, RenderEnv renderEnv) {
/*  838 */     int i = getConnectedTextureCtmIndex(cp, blockAccess, blockState, blockPos, vertAxis, side, icon, metadata, renderEnv);
/*  839 */     return cp.tileIcons[i];
/*      */   }
/*      */ 
/*      */   
/*      */   private static synchronized BakedQuad[] getConnectedTextureCtmCompact(ConnectedProperties cp, IBlockAccess blockAccess, IBlockState blockState, BlockPos blockPos, int vertAxis, int side, BakedQuad quad, int metadata, RenderEnv renderEnv) {
/*  844 */     TextureAtlasSprite textureatlassprite = quad.getSprite();
/*  845 */     int i = getConnectedTextureCtmIndex(cp, blockAccess, blockState, blockPos, vertAxis, side, textureatlassprite, metadata, renderEnv);
/*  846 */     return ConnectedTexturesCompact.getConnectedTextureCtmCompact(i, cp, side, quad, renderEnv);
/*      */   }
/*      */   
/*      */   private static BakedQuad[] getConnectedTextureOverlay(ConnectedProperties cp, IBlockAccess blockAccess, IBlockState blockState, BlockPos blockPos, int vertAxis, int side, BakedQuad quad, int metadata, RenderEnv renderEnv) {
/*      */     Object dirEdges;
/*  851 */     if (!quad.isFullQuad())
/*      */     {
/*  853 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  857 */     TextureAtlasSprite textureatlassprite = quad.getSprite();
/*  858 */     BlockDir[] ablockdir = getSideDirections(side, vertAxis);
/*  859 */     boolean[] aboolean = renderEnv.getBorderFlags();
/*      */     
/*  861 */     for (int i = 0; i < 4; i++)
/*      */     {
/*  863 */       aboolean[i] = isNeighbourOverlay(cp, blockAccess, blockState, ablockdir[i].offset(blockPos), side, textureatlassprite, metadata);
/*      */     }
/*      */     
/*  866 */     ListQuadsOverlay listquadsoverlay = renderEnv.getListQuadsOverlay(cp.layer);
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  871 */       if (!aboolean[0] || !aboolean[1] || !aboolean[2] || !aboolean[3]) {
/*      */         
/*  873 */         if (aboolean[0] && aboolean[1] && aboolean[2]) {
/*      */           
/*  875 */           listquadsoverlay.addQuad(getQuadFull(cp.tileIcons[5], quad, cp.tintIndex), cp.tintBlockState);
/*  876 */           Object object = null;
/*  877 */           return (BakedQuad[])object;
/*      */         } 
/*      */         
/*  880 */         if (aboolean[0] && aboolean[2] && aboolean[3]) {
/*      */           
/*  882 */           listquadsoverlay.addQuad(getQuadFull(cp.tileIcons[6], quad, cp.tintIndex), cp.tintBlockState);
/*  883 */           Object object = null;
/*  884 */           return (BakedQuad[])object;
/*      */         } 
/*      */         
/*  887 */         if (aboolean[1] && aboolean[2] && aboolean[3]) {
/*      */           
/*  889 */           listquadsoverlay.addQuad(getQuadFull(cp.tileIcons[12], quad, cp.tintIndex), cp.tintBlockState);
/*  890 */           Object object = null;
/*  891 */           return (BakedQuad[])object;
/*      */         } 
/*      */         
/*  894 */         if (aboolean[0] && aboolean[1] && aboolean[3]) {
/*      */           
/*  896 */           listquadsoverlay.addQuad(getQuadFull(cp.tileIcons[13], quad, cp.tintIndex), cp.tintBlockState);
/*  897 */           Object object = null;
/*  898 */           return (BakedQuad[])object;
/*      */         } 
/*      */         
/*  901 */         BlockDir[] ablockdir1 = getEdgeDirections(side, vertAxis);
/*  902 */         boolean[] aboolean1 = renderEnv.getBorderFlags2();
/*      */         
/*  904 */         for (int j = 0; j < 4; j++)
/*      */         {
/*  906 */           aboolean1[j] = isNeighbourOverlay(cp, blockAccess, blockState, ablockdir1[j].offset(blockPos), side, textureatlassprite, metadata);
/*      */         }
/*      */         
/*  909 */         if (aboolean[1] && aboolean[2]) {
/*      */           
/*  911 */           listquadsoverlay.addQuad(getQuadFull(cp.tileIcons[3], quad, cp.tintIndex), cp.tintBlockState);
/*      */           
/*  913 */           if (aboolean1[3])
/*      */           {
/*  915 */             listquadsoverlay.addQuad(getQuadFull(cp.tileIcons[16], quad, cp.tintIndex), cp.tintBlockState);
/*      */           }
/*      */           
/*  918 */           Object object4 = null;
/*  919 */           return (BakedQuad[])object4;
/*      */         } 
/*      */         
/*  922 */         if (aboolean[0] && aboolean[2]) {
/*      */           
/*  924 */           listquadsoverlay.addQuad(getQuadFull(cp.tileIcons[4], quad, cp.tintIndex), cp.tintBlockState);
/*      */           
/*  926 */           if (aboolean1[2])
/*      */           {
/*  928 */             listquadsoverlay.addQuad(getQuadFull(cp.tileIcons[14], quad, cp.tintIndex), cp.tintBlockState);
/*      */           }
/*      */           
/*  931 */           Object object3 = null;
/*  932 */           return (BakedQuad[])object3;
/*      */         } 
/*      */         
/*  935 */         if (aboolean[1] && aboolean[3]) {
/*      */           
/*  937 */           listquadsoverlay.addQuad(getQuadFull(cp.tileIcons[10], quad, cp.tintIndex), cp.tintBlockState);
/*      */           
/*  939 */           if (aboolean1[1])
/*      */           {
/*  941 */             listquadsoverlay.addQuad(getQuadFull(cp.tileIcons[2], quad, cp.tintIndex), cp.tintBlockState);
/*      */           }
/*      */           
/*  944 */           Object object2 = null;
/*  945 */           return (BakedQuad[])object2;
/*      */         } 
/*      */         
/*  948 */         if (aboolean[0] && aboolean[3]) {
/*      */           
/*  950 */           listquadsoverlay.addQuad(getQuadFull(cp.tileIcons[11], quad, cp.tintIndex), cp.tintBlockState);
/*      */           
/*  952 */           if (aboolean1[0])
/*      */           {
/*  954 */             listquadsoverlay.addQuad(getQuadFull(cp.tileIcons[0], quad, cp.tintIndex), cp.tintBlockState);
/*      */           }
/*      */           
/*  957 */           Object object1 = null;
/*  958 */           return (BakedQuad[])object1;
/*      */         } 
/*      */         
/*  961 */         boolean[] aboolean2 = renderEnv.getBorderFlags3();
/*      */         
/*  963 */         for (int k = 0; k < 4; k++)
/*      */         {
/*  965 */           aboolean2[k] = isNeighbourMatching(cp, blockAccess, blockState, ablockdir[k].offset(blockPos), side, textureatlassprite, metadata);
/*      */         }
/*      */         
/*  968 */         if (aboolean[0])
/*      */         {
/*  970 */           listquadsoverlay.addQuad(getQuadFull(cp.tileIcons[9], quad, cp.tintIndex), cp.tintBlockState);
/*      */         }
/*      */         
/*  973 */         if (aboolean[1])
/*      */         {
/*  975 */           listquadsoverlay.addQuad(getQuadFull(cp.tileIcons[7], quad, cp.tintIndex), cp.tintBlockState);
/*      */         }
/*      */         
/*  978 */         if (aboolean[2])
/*      */         {
/*  980 */           listquadsoverlay.addQuad(getQuadFull(cp.tileIcons[1], quad, cp.tintIndex), cp.tintBlockState);
/*      */         }
/*      */         
/*  983 */         if (aboolean[3])
/*      */         {
/*  985 */           listquadsoverlay.addQuad(getQuadFull(cp.tileIcons[15], quad, cp.tintIndex), cp.tintBlockState);
/*      */         }
/*      */         
/*  988 */         if (aboolean1[0] && (aboolean2[1] || aboolean2[2]) && !aboolean[1] && !aboolean[2])
/*      */         {
/*  990 */           listquadsoverlay.addQuad(getQuadFull(cp.tileIcons[0], quad, cp.tintIndex), cp.tintBlockState);
/*      */         }
/*      */         
/*  993 */         if (aboolean1[1] && (aboolean2[0] || aboolean2[2]) && !aboolean[0] && !aboolean[2])
/*      */         {
/*  995 */           listquadsoverlay.addQuad(getQuadFull(cp.tileIcons[2], quad, cp.tintIndex), cp.tintBlockState);
/*      */         }
/*      */         
/*  998 */         if (aboolean1[2] && (aboolean2[1] || aboolean2[3]) && !aboolean[1] && !aboolean[3])
/*      */         {
/* 1000 */           listquadsoverlay.addQuad(getQuadFull(cp.tileIcons[14], quad, cp.tintIndex), cp.tintBlockState);
/*      */         }
/*      */         
/* 1003 */         if (aboolean1[3] && (aboolean2[0] || aboolean2[3]) && !aboolean[0] && !aboolean[3])
/*      */         {
/* 1005 */           listquadsoverlay.addQuad(getQuadFull(cp.tileIcons[16], quad, cp.tintIndex), cp.tintBlockState);
/*      */         }
/*      */         
/* 1008 */         Object object5 = null;
/* 1009 */         return (BakedQuad[])object5;
/*      */       } 
/*      */       
/* 1012 */       listquadsoverlay.addQuad(getQuadFull(cp.tileIcons[8], quad, cp.tintIndex), cp.tintBlockState);
/* 1013 */       dirEdges = null;
/*      */     }
/*      */     finally {
/*      */       
/* 1017 */       if (listquadsoverlay.size() > 0)
/*      */       {
/* 1019 */         renderEnv.setOverlaysRendered(true);
/*      */       }
/*      */     } 
/*      */     
/* 1023 */     return (BakedQuad[])dirEdges;
/*      */   }
/*      */ 
/*      */   
/*      */   private static BakedQuad[] getConnectedTextureOverlayFixed(ConnectedProperties cp, BakedQuad quad, RenderEnv renderEnv) {
/*      */     Object object;
/* 1029 */     if (!quad.isFullQuad())
/*      */     {
/* 1031 */       return null;
/*      */     }
/*      */ 
/*      */     
/* 1035 */     ListQuadsOverlay listquadsoverlay = renderEnv.getListQuadsOverlay(cp.layer);
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1040 */       TextureAtlasSprite textureatlassprite = getConnectedTextureFixed(cp);
/*      */       
/* 1042 */       if (textureatlassprite != null)
/*      */       {
/* 1044 */         listquadsoverlay.addQuad(getQuadFull(textureatlassprite, quad, cp.tintIndex), cp.tintBlockState);
/*      */       }
/*      */       
/* 1047 */       object = null;
/*      */     }
/*      */     finally {
/*      */       
/* 1051 */       if (listquadsoverlay.size() > 0)
/*      */       {
/* 1053 */         renderEnv.setOverlaysRendered(true);
/*      */       }
/*      */     } 
/*      */     
/* 1057 */     return (BakedQuad[])object;
/*      */   }
/*      */ 
/*      */   
/*      */   private static BakedQuad[] getConnectedTextureOverlayRandom(ConnectedProperties cp, IBlockAccess blockAccess, BlockStateBase blockState, BlockPos blockPos, int side, BakedQuad quad, RenderEnv renderEnv) {
/*      */     Object object;
/* 1063 */     if (!quad.isFullQuad())
/*      */     {
/* 1065 */       return null;
/*      */     }
/*      */ 
/*      */     
/* 1069 */     ListQuadsOverlay listquadsoverlay = renderEnv.getListQuadsOverlay(cp.layer);
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1074 */       TextureAtlasSprite textureatlassprite = getConnectedTextureRandom(cp, blockAccess, blockState, blockPos, side);
/*      */       
/* 1076 */       if (textureatlassprite != null)
/*      */       {
/* 1078 */         listquadsoverlay.addQuad(getQuadFull(textureatlassprite, quad, cp.tintIndex), cp.tintBlockState);
/*      */       }
/*      */       
/* 1081 */       object = null;
/*      */     }
/*      */     finally {
/*      */       
/* 1085 */       if (listquadsoverlay.size() > 0)
/*      */       {
/* 1087 */         renderEnv.setOverlaysRendered(true);
/*      */       }
/*      */     } 
/*      */     
/* 1091 */     return (BakedQuad[])object;
/*      */   }
/*      */ 
/*      */   
/*      */   private static BakedQuad[] getConnectedTextureOverlayRepeat(ConnectedProperties cp, BlockPos blockPos, int side, BakedQuad quad, RenderEnv renderEnv) {
/*      */     Object object;
/* 1097 */     if (!quad.isFullQuad())
/*      */     {
/* 1099 */       return null;
/*      */     }
/*      */ 
/*      */     
/* 1103 */     ListQuadsOverlay listquadsoverlay = renderEnv.getListQuadsOverlay(cp.layer);
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1108 */       TextureAtlasSprite textureatlassprite = getConnectedTextureRepeat(cp, blockPos, side);
/*      */       
/* 1110 */       if (textureatlassprite != null)
/*      */       {
/* 1112 */         listquadsoverlay.addQuad(getQuadFull(textureatlassprite, quad, cp.tintIndex), cp.tintBlockState);
/*      */       }
/*      */       
/* 1115 */       object = null;
/*      */     }
/*      */     finally {
/*      */       
/* 1119 */       if (listquadsoverlay.size() > 0)
/*      */       {
/* 1121 */         renderEnv.setOverlaysRendered(true);
/*      */       }
/*      */     } 
/*      */     
/* 1125 */     return (BakedQuad[])object;
/*      */   }
/*      */ 
/*      */   
/*      */   private static BakedQuad[] getConnectedTextureOverlayCtm(ConnectedProperties cp, IBlockAccess blockAccess, IBlockState blockState, BlockPos blockPos, int vertAxis, int side, BakedQuad quad, int metadata, RenderEnv renderEnv) {
/*      */     Object object;
/* 1131 */     if (!quad.isFullQuad())
/*      */     {
/* 1133 */       return null;
/*      */     }
/*      */ 
/*      */     
/* 1137 */     ListQuadsOverlay listquadsoverlay = renderEnv.getListQuadsOverlay(cp.layer);
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1142 */       TextureAtlasSprite textureatlassprite = getConnectedTextureCtm(cp, blockAccess, blockState, blockPos, vertAxis, side, quad.getSprite(), metadata, renderEnv);
/*      */       
/* 1144 */       if (textureatlassprite != null)
/*      */       {
/* 1146 */         listquadsoverlay.addQuad(getQuadFull(textureatlassprite, quad, cp.tintIndex), cp.tintBlockState);
/*      */       }
/*      */       
/* 1149 */       object = null;
/*      */     }
/*      */     finally {
/*      */       
/* 1153 */       if (listquadsoverlay.size() > 0)
/*      */       {
/* 1155 */         renderEnv.setOverlaysRendered(true);
/*      */       }
/*      */     } 
/*      */     
/* 1159 */     return (BakedQuad[])object;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static BlockDir[] getSideDirections(int side, int vertAxis) {
/* 1165 */     switch (side) {
/*      */       
/*      */       case 0:
/* 1168 */         return SIDES_Y_NEG_DOWN;
/*      */       
/*      */       case 1:
/* 1171 */         return SIDES_Y_POS_UP;
/*      */       
/*      */       case 2:
/* 1174 */         if (vertAxis == 1)
/*      */         {
/* 1176 */           return SIDES_Z_NEG_NORTH_Z_AXIS;
/*      */         }
/*      */         
/* 1179 */         return SIDES_Z_NEG_NORTH;
/*      */       
/*      */       case 3:
/* 1182 */         return SIDES_Z_POS_SOUTH;
/*      */       
/*      */       case 4:
/* 1185 */         return SIDES_X_NEG_WEST;
/*      */       
/*      */       case 5:
/* 1188 */         if (vertAxis == 2)
/*      */         {
/* 1190 */           return SIDES_X_POS_EAST_X_AXIS;
/*      */         }
/*      */         
/* 1193 */         return SIDES_X_POS_EAST;
/*      */     } 
/*      */     
/* 1196 */     throw new IllegalArgumentException("Unknown side: " + side);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static BlockDir[] getEdgeDirections(int side, int vertAxis) {
/* 1202 */     switch (side) {
/*      */       
/*      */       case 0:
/* 1205 */         return EDGES_Y_NEG_DOWN;
/*      */       
/*      */       case 1:
/* 1208 */         return EDGES_Y_POS_UP;
/*      */       
/*      */       case 2:
/* 1211 */         if (vertAxis == 1)
/*      */         {
/* 1213 */           return EDGES_Z_NEG_NORTH_Z_AXIS;
/*      */         }
/*      */         
/* 1216 */         return EDGES_Z_NEG_NORTH;
/*      */       
/*      */       case 3:
/* 1219 */         return EDGES_Z_POS_SOUTH;
/*      */       
/*      */       case 4:
/* 1222 */         return EDGES_X_NEG_WEST;
/*      */       
/*      */       case 5:
/* 1225 */         if (vertAxis == 2)
/*      */         {
/* 1227 */           return EDGES_X_POS_EAST_X_AXIS;
/*      */         }
/*      */         
/* 1230 */         return EDGES_X_POS_EAST;
/*      */     } 
/*      */     
/* 1233 */     throw new IllegalArgumentException("Unknown side: " + side);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected static Map[][] getSpriteQuadCompactMaps() {
/* 1239 */     return spriteQuadCompactMaps;
/*      */   }
/*      */ 
/*      */   
/*      */   private static int getConnectedTextureCtmIndex(ConnectedProperties cp, IBlockAccess blockAccess, IBlockState blockState, BlockPos blockPos, int vertAxis, int side, TextureAtlasSprite icon, int metadata, RenderEnv renderEnv) {
/* 1244 */     boolean[] aboolean = renderEnv.getBorderFlags();
/*      */     
/* 1246 */     switch (side) {
/*      */       
/*      */       case 0:
/* 1249 */         aboolean[0] = isNeighbour(cp, blockAccess, blockState, blockPos.west(), side, icon, metadata);
/* 1250 */         aboolean[1] = isNeighbour(cp, blockAccess, blockState, blockPos.east(), side, icon, metadata);
/* 1251 */         aboolean[2] = isNeighbour(cp, blockAccess, blockState, blockPos.north(), side, icon, metadata);
/* 1252 */         aboolean[3] = isNeighbour(cp, blockAccess, blockState, blockPos.south(), side, icon, metadata);
/*      */         
/* 1254 */         if (cp.innerSeams) {
/*      */           
/* 1256 */           BlockPos blockpos6 = blockPos.down();
/* 1257 */           aboolean[0] = (aboolean[0] && !isNeighbour(cp, blockAccess, blockState, blockpos6.west(), side, icon, metadata));
/* 1258 */           aboolean[1] = (aboolean[1] && !isNeighbour(cp, blockAccess, blockState, blockpos6.east(), side, icon, metadata));
/* 1259 */           aboolean[2] = (aboolean[2] && !isNeighbour(cp, blockAccess, blockState, blockpos6.north(), side, icon, metadata));
/* 1260 */           aboolean[3] = (aboolean[3] && !isNeighbour(cp, blockAccess, blockState, blockpos6.south(), side, icon, metadata));
/*      */         } 
/*      */         break;
/*      */ 
/*      */       
/*      */       case 1:
/* 1266 */         aboolean[0] = isNeighbour(cp, blockAccess, blockState, blockPos.west(), side, icon, metadata);
/* 1267 */         aboolean[1] = isNeighbour(cp, blockAccess, blockState, blockPos.east(), side, icon, metadata);
/* 1268 */         aboolean[2] = isNeighbour(cp, blockAccess, blockState, blockPos.south(), side, icon, metadata);
/* 1269 */         aboolean[3] = isNeighbour(cp, blockAccess, blockState, blockPos.north(), side, icon, metadata);
/*      */         
/* 1271 */         if (cp.innerSeams) {
/*      */           
/* 1273 */           BlockPos blockpos5 = blockPos.up();
/* 1274 */           aboolean[0] = (aboolean[0] && !isNeighbour(cp, blockAccess, blockState, blockpos5.west(), side, icon, metadata));
/* 1275 */           aboolean[1] = (aboolean[1] && !isNeighbour(cp, blockAccess, blockState, blockpos5.east(), side, icon, metadata));
/* 1276 */           aboolean[2] = (aboolean[2] && !isNeighbour(cp, blockAccess, blockState, blockpos5.south(), side, icon, metadata));
/* 1277 */           aboolean[3] = (aboolean[3] && !isNeighbour(cp, blockAccess, blockState, blockpos5.north(), side, icon, metadata));
/*      */         } 
/*      */         break;
/*      */ 
/*      */       
/*      */       case 2:
/* 1283 */         aboolean[0] = isNeighbour(cp, blockAccess, blockState, blockPos.east(), side, icon, metadata);
/* 1284 */         aboolean[1] = isNeighbour(cp, blockAccess, blockState, blockPos.west(), side, icon, metadata);
/* 1285 */         aboolean[2] = isNeighbour(cp, blockAccess, blockState, blockPos.down(), side, icon, metadata);
/* 1286 */         aboolean[3] = isNeighbour(cp, blockAccess, blockState, blockPos.up(), side, icon, metadata);
/*      */         
/* 1288 */         if (cp.innerSeams) {
/*      */           
/* 1290 */           BlockPos blockpos4 = blockPos.north();
/* 1291 */           aboolean[0] = (aboolean[0] && !isNeighbour(cp, blockAccess, blockState, blockpos4.east(), side, icon, metadata));
/* 1292 */           aboolean[1] = (aboolean[1] && !isNeighbour(cp, blockAccess, blockState, blockpos4.west(), side, icon, metadata));
/* 1293 */           aboolean[2] = (aboolean[2] && !isNeighbour(cp, blockAccess, blockState, blockpos4.down(), side, icon, metadata));
/* 1294 */           aboolean[3] = (aboolean[3] && !isNeighbour(cp, blockAccess, blockState, blockpos4.up(), side, icon, metadata));
/*      */         } 
/*      */         
/* 1297 */         if (vertAxis == 1) {
/*      */           
/* 1299 */           switchValues(0, 1, aboolean);
/* 1300 */           switchValues(2, 3, aboolean);
/*      */         } 
/*      */         break;
/*      */ 
/*      */       
/*      */       case 3:
/* 1306 */         aboolean[0] = isNeighbour(cp, blockAccess, blockState, blockPos.west(), side, icon, metadata);
/* 1307 */         aboolean[1] = isNeighbour(cp, blockAccess, blockState, blockPos.east(), side, icon, metadata);
/* 1308 */         aboolean[2] = isNeighbour(cp, blockAccess, blockState, blockPos.down(), side, icon, metadata);
/* 1309 */         aboolean[3] = isNeighbour(cp, blockAccess, blockState, blockPos.up(), side, icon, metadata);
/*      */         
/* 1311 */         if (cp.innerSeams) {
/*      */           
/* 1313 */           BlockPos blockpos3 = blockPos.south();
/* 1314 */           aboolean[0] = (aboolean[0] && !isNeighbour(cp, blockAccess, blockState, blockpos3.west(), side, icon, metadata));
/* 1315 */           aboolean[1] = (aboolean[1] && !isNeighbour(cp, blockAccess, blockState, blockpos3.east(), side, icon, metadata));
/* 1316 */           aboolean[2] = (aboolean[2] && !isNeighbour(cp, blockAccess, blockState, blockpos3.down(), side, icon, metadata));
/* 1317 */           aboolean[3] = (aboolean[3] && !isNeighbour(cp, blockAccess, blockState, blockpos3.up(), side, icon, metadata));
/*      */         } 
/*      */         break;
/*      */ 
/*      */       
/*      */       case 4:
/* 1323 */         aboolean[0] = isNeighbour(cp, blockAccess, blockState, blockPos.north(), side, icon, metadata);
/* 1324 */         aboolean[1] = isNeighbour(cp, blockAccess, blockState, blockPos.south(), side, icon, metadata);
/* 1325 */         aboolean[2] = isNeighbour(cp, blockAccess, blockState, blockPos.down(), side, icon, metadata);
/* 1326 */         aboolean[3] = isNeighbour(cp, blockAccess, blockState, blockPos.up(), side, icon, metadata);
/*      */         
/* 1328 */         if (cp.innerSeams) {
/*      */           
/* 1330 */           BlockPos blockpos2 = blockPos.west();
/* 1331 */           aboolean[0] = (aboolean[0] && !isNeighbour(cp, blockAccess, blockState, blockpos2.north(), side, icon, metadata));
/* 1332 */           aboolean[1] = (aboolean[1] && !isNeighbour(cp, blockAccess, blockState, blockpos2.south(), side, icon, metadata));
/* 1333 */           aboolean[2] = (aboolean[2] && !isNeighbour(cp, blockAccess, blockState, blockpos2.down(), side, icon, metadata));
/* 1334 */           aboolean[3] = (aboolean[3] && !isNeighbour(cp, blockAccess, blockState, blockpos2.up(), side, icon, metadata));
/*      */         } 
/*      */         break;
/*      */ 
/*      */       
/*      */       case 5:
/* 1340 */         aboolean[0] = isNeighbour(cp, blockAccess, blockState, blockPos.south(), side, icon, metadata);
/* 1341 */         aboolean[1] = isNeighbour(cp, blockAccess, blockState, blockPos.north(), side, icon, metadata);
/* 1342 */         aboolean[2] = isNeighbour(cp, blockAccess, blockState, blockPos.down(), side, icon, metadata);
/* 1343 */         aboolean[3] = isNeighbour(cp, blockAccess, blockState, blockPos.up(), side, icon, metadata);
/*      */         
/* 1345 */         if (cp.innerSeams) {
/*      */           
/* 1347 */           BlockPos blockpos = blockPos.east();
/* 1348 */           aboolean[0] = (aboolean[0] && !isNeighbour(cp, blockAccess, blockState, blockpos.south(), side, icon, metadata));
/* 1349 */           aboolean[1] = (aboolean[1] && !isNeighbour(cp, blockAccess, blockState, blockpos.north(), side, icon, metadata));
/* 1350 */           aboolean[2] = (aboolean[2] && !isNeighbour(cp, blockAccess, blockState, blockpos.down(), side, icon, metadata));
/* 1351 */           aboolean[3] = (aboolean[3] && !isNeighbour(cp, blockAccess, blockState, blockpos.up(), side, icon, metadata));
/*      */         } 
/*      */         
/* 1354 */         if (vertAxis == 2) {
/*      */           
/* 1356 */           switchValues(0, 1, aboolean);
/* 1357 */           switchValues(2, 3, aboolean);
/*      */         } 
/*      */         break;
/*      */     } 
/* 1361 */     int i = 0;
/*      */     
/* 1363 */     if ((aboolean[0] & (!aboolean[1] ? 1 : 0) & (!aboolean[2] ? 1 : 0) & (!aboolean[3] ? 1 : 0)) != 0) {
/*      */       
/* 1365 */       i = 3;
/*      */     }
/* 1367 */     else if (((!aboolean[0] ? 1 : 0) & aboolean[1] & (!aboolean[2] ? 1 : 0) & (!aboolean[3] ? 1 : 0)) != 0) {
/*      */       
/* 1369 */       i = 1;
/*      */     }
/* 1371 */     else if (((!aboolean[0] ? 1 : 0) & (!aboolean[1] ? 1 : 0) & aboolean[2] & (!aboolean[3] ? 1 : 0)) != 0) {
/*      */       
/* 1373 */       i = 12;
/*      */     }
/* 1375 */     else if (((!aboolean[0] ? 1 : 0) & (!aboolean[1] ? 1 : 0) & (!aboolean[2] ? 1 : 0) & aboolean[3]) != 0) {
/*      */       
/* 1377 */       i = 36;
/*      */     }
/* 1379 */     else if ((aboolean[0] & aboolean[1] & (!aboolean[2] ? 1 : 0) & (!aboolean[3] ? 1 : 0)) != 0) {
/*      */       
/* 1381 */       i = 2;
/*      */     }
/* 1383 */     else if (((!aboolean[0] ? 1 : 0) & (!aboolean[1] ? 1 : 0) & aboolean[2] & aboolean[3]) != 0) {
/*      */       
/* 1385 */       i = 24;
/*      */     }
/* 1387 */     else if ((aboolean[0] & (!aboolean[1] ? 1 : 0) & aboolean[2] & (!aboolean[3] ? 1 : 0)) != 0) {
/*      */       
/* 1389 */       i = 15;
/*      */     }
/* 1391 */     else if ((aboolean[0] & (!aboolean[1] ? 1 : 0) & (!aboolean[2] ? 1 : 0) & aboolean[3]) != 0) {
/*      */       
/* 1393 */       i = 39;
/*      */     }
/* 1395 */     else if (((!aboolean[0] ? 1 : 0) & aboolean[1] & aboolean[2] & (!aboolean[3] ? 1 : 0)) != 0) {
/*      */       
/* 1397 */       i = 13;
/*      */     }
/* 1399 */     else if (((!aboolean[0] ? 1 : 0) & aboolean[1] & (!aboolean[2] ? 1 : 0) & aboolean[3]) != 0) {
/*      */       
/* 1401 */       i = 37;
/*      */     }
/* 1403 */     else if (((!aboolean[0] ? 1 : 0) & aboolean[1] & aboolean[2] & aboolean[3]) != 0) {
/*      */       
/* 1405 */       i = 25;
/*      */     }
/* 1407 */     else if ((aboolean[0] & (!aboolean[1] ? 1 : 0) & aboolean[2] & aboolean[3]) != 0) {
/*      */       
/* 1409 */       i = 27;
/*      */     }
/* 1411 */     else if ((aboolean[0] & aboolean[1] & (!aboolean[2] ? 1 : 0) & aboolean[3]) != 0) {
/*      */       
/* 1413 */       i = 38;
/*      */     }
/* 1415 */     else if ((aboolean[0] & aboolean[1] & aboolean[2] & (!aboolean[3] ? 1 : 0)) != 0) {
/*      */       
/* 1417 */       i = 14;
/*      */     }
/* 1419 */     else if ((aboolean[0] & aboolean[1] & aboolean[2] & aboolean[3]) != 0) {
/*      */       
/* 1421 */       i = 26;
/*      */     } 
/*      */     
/* 1424 */     if (i == 0)
/*      */     {
/* 1426 */       return i;
/*      */     }
/* 1428 */     if (!Config.isConnectedTexturesFancy())
/*      */     {
/* 1430 */       return i;
/*      */     }
/*      */ 
/*      */     
/* 1434 */     switch (side) {
/*      */       
/*      */       case 0:
/* 1437 */         aboolean[0] = !isNeighbour(cp, blockAccess, blockState, blockPos.east().north(), side, icon, metadata);
/* 1438 */         aboolean[1] = !isNeighbour(cp, blockAccess, blockState, blockPos.west().north(), side, icon, metadata);
/* 1439 */         aboolean[2] = !isNeighbour(cp, blockAccess, blockState, blockPos.east().south(), side, icon, metadata);
/* 1440 */         aboolean[3] = !isNeighbour(cp, blockAccess, blockState, blockPos.west().south(), side, icon, metadata);
/*      */         
/* 1442 */         if (cp.innerSeams) {
/*      */           
/* 1444 */           BlockPos blockpos11 = blockPos.down();
/* 1445 */           aboolean[0] = (aboolean[0] || isNeighbour(cp, blockAccess, blockState, blockpos11.east().north(), side, icon, metadata));
/* 1446 */           aboolean[1] = (aboolean[1] || isNeighbour(cp, blockAccess, blockState, blockpos11.west().north(), side, icon, metadata));
/* 1447 */           aboolean[2] = (aboolean[2] || isNeighbour(cp, blockAccess, blockState, blockpos11.east().south(), side, icon, metadata));
/* 1448 */           aboolean[3] = (aboolean[3] || isNeighbour(cp, blockAccess, blockState, blockpos11.west().south(), side, icon, metadata));
/*      */         } 
/*      */         break;
/*      */ 
/*      */       
/*      */       case 1:
/* 1454 */         aboolean[0] = !isNeighbour(cp, blockAccess, blockState, blockPos.east().south(), side, icon, metadata);
/* 1455 */         aboolean[1] = !isNeighbour(cp, blockAccess, blockState, blockPos.west().south(), side, icon, metadata);
/* 1456 */         aboolean[2] = !isNeighbour(cp, blockAccess, blockState, blockPos.east().north(), side, icon, metadata);
/* 1457 */         aboolean[3] = !isNeighbour(cp, blockAccess, blockState, blockPos.west().north(), side, icon, metadata);
/*      */         
/* 1459 */         if (cp.innerSeams) {
/*      */           
/* 1461 */           BlockPos blockpos10 = blockPos.up();
/* 1462 */           aboolean[0] = (aboolean[0] || isNeighbour(cp, blockAccess, blockState, blockpos10.east().south(), side, icon, metadata));
/* 1463 */           aboolean[1] = (aboolean[1] || isNeighbour(cp, blockAccess, blockState, blockpos10.west().south(), side, icon, metadata));
/* 1464 */           aboolean[2] = (aboolean[2] || isNeighbour(cp, blockAccess, blockState, blockpos10.east().north(), side, icon, metadata));
/* 1465 */           aboolean[3] = (aboolean[3] || isNeighbour(cp, blockAccess, blockState, blockpos10.west().north(), side, icon, metadata));
/*      */         } 
/*      */         break;
/*      */ 
/*      */       
/*      */       case 2:
/* 1471 */         aboolean[0] = !isNeighbour(cp, blockAccess, blockState, blockPos.west().down(), side, icon, metadata);
/* 1472 */         aboolean[1] = !isNeighbour(cp, blockAccess, blockState, blockPos.east().down(), side, icon, metadata);
/* 1473 */         aboolean[2] = !isNeighbour(cp, blockAccess, blockState, blockPos.west().up(), side, icon, metadata);
/* 1474 */         aboolean[3] = !isNeighbour(cp, blockAccess, blockState, blockPos.east().up(), side, icon, metadata);
/*      */         
/* 1476 */         if (cp.innerSeams) {
/*      */           
/* 1478 */           BlockPos blockpos9 = blockPos.north();
/* 1479 */           aboolean[0] = (aboolean[0] || isNeighbour(cp, blockAccess, blockState, blockpos9.west().down(), side, icon, metadata));
/* 1480 */           aboolean[1] = (aboolean[1] || isNeighbour(cp, blockAccess, blockState, blockpos9.east().down(), side, icon, metadata));
/* 1481 */           aboolean[2] = (aboolean[2] || isNeighbour(cp, blockAccess, blockState, blockpos9.west().up(), side, icon, metadata));
/* 1482 */           aboolean[3] = (aboolean[3] || isNeighbour(cp, blockAccess, blockState, blockpos9.east().up(), side, icon, metadata));
/*      */         } 
/*      */         
/* 1485 */         if (vertAxis == 1) {
/*      */           
/* 1487 */           switchValues(0, 3, aboolean);
/* 1488 */           switchValues(1, 2, aboolean);
/*      */         } 
/*      */         break;
/*      */ 
/*      */       
/*      */       case 3:
/* 1494 */         aboolean[0] = !isNeighbour(cp, blockAccess, blockState, blockPos.east().down(), side, icon, metadata);
/* 1495 */         aboolean[1] = !isNeighbour(cp, blockAccess, blockState, blockPos.west().down(), side, icon, metadata);
/* 1496 */         aboolean[2] = !isNeighbour(cp, blockAccess, blockState, blockPos.east().up(), side, icon, metadata);
/* 1497 */         aboolean[3] = !isNeighbour(cp, blockAccess, blockState, blockPos.west().up(), side, icon, metadata);
/*      */         
/* 1499 */         if (cp.innerSeams) {
/*      */           
/* 1501 */           BlockPos blockpos8 = blockPos.south();
/* 1502 */           aboolean[0] = (aboolean[0] || isNeighbour(cp, blockAccess, blockState, blockpos8.east().down(), side, icon, metadata));
/* 1503 */           aboolean[1] = (aboolean[1] || isNeighbour(cp, blockAccess, blockState, blockpos8.west().down(), side, icon, metadata));
/* 1504 */           aboolean[2] = (aboolean[2] || isNeighbour(cp, blockAccess, blockState, blockpos8.east().up(), side, icon, metadata));
/* 1505 */           aboolean[3] = (aboolean[3] || isNeighbour(cp, blockAccess, blockState, blockpos8.west().up(), side, icon, metadata));
/*      */         } 
/*      */         break;
/*      */ 
/*      */       
/*      */       case 4:
/* 1511 */         aboolean[0] = !isNeighbour(cp, blockAccess, blockState, blockPos.down().south(), side, icon, metadata);
/* 1512 */         aboolean[1] = !isNeighbour(cp, blockAccess, blockState, blockPos.down().north(), side, icon, metadata);
/* 1513 */         aboolean[2] = !isNeighbour(cp, blockAccess, blockState, blockPos.up().south(), side, icon, metadata);
/* 1514 */         aboolean[3] = !isNeighbour(cp, blockAccess, blockState, blockPos.up().north(), side, icon, metadata);
/*      */         
/* 1516 */         if (cp.innerSeams) {
/*      */           
/* 1518 */           BlockPos blockpos7 = blockPos.west();
/* 1519 */           aboolean[0] = (aboolean[0] || isNeighbour(cp, blockAccess, blockState, blockpos7.down().south(), side, icon, metadata));
/* 1520 */           aboolean[1] = (aboolean[1] || isNeighbour(cp, blockAccess, blockState, blockpos7.down().north(), side, icon, metadata));
/* 1521 */           aboolean[2] = (aboolean[2] || isNeighbour(cp, blockAccess, blockState, blockpos7.up().south(), side, icon, metadata));
/* 1522 */           aboolean[3] = (aboolean[3] || isNeighbour(cp, blockAccess, blockState, blockpos7.up().north(), side, icon, metadata));
/*      */         } 
/*      */         break;
/*      */ 
/*      */       
/*      */       case 5:
/* 1528 */         aboolean[0] = !isNeighbour(cp, blockAccess, blockState, blockPos.down().north(), side, icon, metadata);
/* 1529 */         aboolean[1] = !isNeighbour(cp, blockAccess, blockState, blockPos.down().south(), side, icon, metadata);
/* 1530 */         aboolean[2] = !isNeighbour(cp, blockAccess, blockState, blockPos.up().north(), side, icon, metadata);
/* 1531 */         aboolean[3] = !isNeighbour(cp, blockAccess, blockState, blockPos.up().south(), side, icon, metadata);
/*      */         
/* 1533 */         if (cp.innerSeams) {
/*      */           
/* 1535 */           BlockPos blockpos1 = blockPos.east();
/* 1536 */           aboolean[0] = (aboolean[0] || isNeighbour(cp, blockAccess, blockState, blockpos1.down().north(), side, icon, metadata));
/* 1537 */           aboolean[1] = (aboolean[1] || isNeighbour(cp, blockAccess, blockState, blockpos1.down().south(), side, icon, metadata));
/* 1538 */           aboolean[2] = (aboolean[2] || isNeighbour(cp, blockAccess, blockState, blockpos1.up().north(), side, icon, metadata));
/* 1539 */           aboolean[3] = (aboolean[3] || isNeighbour(cp, blockAccess, blockState, blockpos1.up().south(), side, icon, metadata));
/*      */         } 
/*      */         
/* 1542 */         if (vertAxis == 2) {
/*      */           
/* 1544 */           switchValues(0, 3, aboolean);
/* 1545 */           switchValues(1, 2, aboolean);
/*      */         } 
/*      */         break;
/*      */     } 
/* 1549 */     if (i == 13 && aboolean[0]) {
/*      */       
/* 1551 */       i = 4;
/*      */     }
/* 1553 */     else if (i == 15 && aboolean[1]) {
/*      */       
/* 1555 */       i = 5;
/*      */     }
/* 1557 */     else if (i == 37 && aboolean[2]) {
/*      */       
/* 1559 */       i = 16;
/*      */     }
/* 1561 */     else if (i == 39 && aboolean[3]) {
/*      */       
/* 1563 */       i = 17;
/*      */     }
/* 1565 */     else if (i == 14 && aboolean[0] && aboolean[1]) {
/*      */       
/* 1567 */       i = 7;
/*      */     }
/* 1569 */     else if (i == 25 && aboolean[0] && aboolean[2]) {
/*      */       
/* 1571 */       i = 6;
/*      */     }
/* 1573 */     else if (i == 27 && aboolean[3] && aboolean[1]) {
/*      */       
/* 1575 */       i = 19;
/*      */     }
/* 1577 */     else if (i == 38 && aboolean[3] && aboolean[2]) {
/*      */       
/* 1579 */       i = 18;
/*      */     }
/* 1581 */     else if (i == 14 && !aboolean[0] && aboolean[1]) {
/*      */       
/* 1583 */       i = 31;
/*      */     }
/* 1585 */     else if (i == 25 && aboolean[0] && !aboolean[2]) {
/*      */       
/* 1587 */       i = 30;
/*      */     }
/* 1589 */     else if (i == 27 && !aboolean[3] && aboolean[1]) {
/*      */       
/* 1591 */       i = 41;
/*      */     }
/* 1593 */     else if (i == 38 && aboolean[3] && !aboolean[2]) {
/*      */       
/* 1595 */       i = 40;
/*      */     }
/* 1597 */     else if (i == 14 && aboolean[0] && !aboolean[1]) {
/*      */       
/* 1599 */       i = 29;
/*      */     }
/* 1601 */     else if (i == 25 && !aboolean[0] && aboolean[2]) {
/*      */       
/* 1603 */       i = 28;
/*      */     }
/* 1605 */     else if (i == 27 && aboolean[3] && !aboolean[1]) {
/*      */       
/* 1607 */       i = 43;
/*      */     }
/* 1609 */     else if (i == 38 && !aboolean[3] && aboolean[2]) {
/*      */       
/* 1611 */       i = 42;
/*      */     }
/* 1613 */     else if (i == 26 && aboolean[0] && aboolean[1] && aboolean[2] && aboolean[3]) {
/*      */       
/* 1615 */       i = 46;
/*      */     }
/* 1617 */     else if (i == 26 && !aboolean[0] && aboolean[1] && aboolean[2] && aboolean[3]) {
/*      */       
/* 1619 */       i = 9;
/*      */     }
/* 1621 */     else if (i == 26 && aboolean[0] && !aboolean[1] && aboolean[2] && aboolean[3]) {
/*      */       
/* 1623 */       i = 21;
/*      */     }
/* 1625 */     else if (i == 26 && aboolean[0] && aboolean[1] && !aboolean[2] && aboolean[3]) {
/*      */       
/* 1627 */       i = 8;
/*      */     }
/* 1629 */     else if (i == 26 && aboolean[0] && aboolean[1] && aboolean[2] && !aboolean[3]) {
/*      */       
/* 1631 */       i = 20;
/*      */     }
/* 1633 */     else if (i == 26 && aboolean[0] && aboolean[1] && !aboolean[2] && !aboolean[3]) {
/*      */       
/* 1635 */       i = 11;
/*      */     }
/* 1637 */     else if (i == 26 && !aboolean[0] && !aboolean[1] && aboolean[2] && aboolean[3]) {
/*      */       
/* 1639 */       i = 22;
/*      */     }
/* 1641 */     else if (i == 26 && !aboolean[0] && aboolean[1] && !aboolean[2] && aboolean[3]) {
/*      */       
/* 1643 */       i = 23;
/*      */     }
/* 1645 */     else if (i == 26 && aboolean[0] && !aboolean[1] && aboolean[2] && !aboolean[3]) {
/*      */       
/* 1647 */       i = 10;
/*      */     }
/* 1649 */     else if (i == 26 && aboolean[0] && !aboolean[1] && !aboolean[2] && aboolean[3]) {
/*      */       
/* 1651 */       i = 34;
/*      */     }
/* 1653 */     else if (i == 26 && !aboolean[0] && aboolean[1] && aboolean[2] && !aboolean[3]) {
/*      */       
/* 1655 */       i = 35;
/*      */     }
/* 1657 */     else if (i == 26 && aboolean[0] && !aboolean[1] && !aboolean[2] && !aboolean[3]) {
/*      */       
/* 1659 */       i = 32;
/*      */     }
/* 1661 */     else if (i == 26 && !aboolean[0] && aboolean[1] && !aboolean[2] && !aboolean[3]) {
/*      */       
/* 1663 */       i = 33;
/*      */     }
/* 1665 */     else if (i == 26 && !aboolean[0] && !aboolean[1] && aboolean[2] && !aboolean[3]) {
/*      */       
/* 1667 */       i = 44;
/*      */     }
/* 1669 */     else if (i == 26 && !aboolean[0] && !aboolean[1] && !aboolean[2] && aboolean[3]) {
/*      */       
/* 1671 */       i = 45;
/*      */     } 
/*      */     
/* 1674 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void switchValues(int ix1, int ix2, boolean[] arr) {
/* 1680 */     boolean flag = arr[ix1];
/* 1681 */     arr[ix1] = arr[ix2];
/* 1682 */     arr[ix2] = flag;
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean isNeighbourOverlay(ConnectedProperties cp, IBlockAccess iblockaccess, IBlockState blockState, BlockPos blockPos, int side, TextureAtlasSprite icon, int metadata) {
/* 1687 */     IBlockState iblockstate = iblockaccess.getBlockState(blockPos);
/*      */     
/* 1689 */     if (!isFullCubeModel(iblockstate))
/*      */     {
/* 1691 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1695 */     if (cp.connectBlocks != null) {
/*      */       
/* 1697 */       BlockStateBase blockstatebase = (BlockStateBase)iblockstate;
/*      */       
/* 1699 */       if (!Matches.block(blockstatebase.getBlockId(), blockstatebase.getMetadata(), cp.connectBlocks))
/*      */       {
/* 1701 */         return false;
/*      */       }
/*      */     } 
/*      */     
/* 1705 */     if (cp.connectTileIcons != null) {
/*      */       
/* 1707 */       TextureAtlasSprite textureatlassprite = getNeighbourIcon(iblockaccess, blockState, blockPos, iblockstate, side);
/*      */       
/* 1709 */       if (!Config.isSameOne(textureatlassprite, (Object[])cp.connectTileIcons))
/*      */       {
/* 1711 */         return false;
/*      */       }
/*      */     } 
/*      */     
/* 1715 */     IBlockState iblockstate1 = iblockaccess.getBlockState(blockPos.offset(getFacing(side)));
/* 1716 */     return iblockstate1.getBlock().isOpaqueCube() ? false : ((side == 1 && iblockstate1.getBlock() == Blocks.snow_layer) ? false : (!isNeighbour(cp, iblockaccess, blockState, blockPos, iblockstate, side, icon, metadata)));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isFullCubeModel(IBlockState state) {
/* 1722 */     if (state.getBlock().isFullCube())
/*      */     {
/* 1724 */       return true;
/*      */     }
/*      */ 
/*      */     
/* 1728 */     Block block = state.getBlock();
/* 1729 */     return (block instanceof net.minecraft.block.BlockGlass) ? true : (block instanceof net.minecraft.block.BlockStainedGlass);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isNeighbourMatching(ConnectedProperties cp, IBlockAccess iblockaccess, IBlockState blockState, BlockPos blockPos, int side, TextureAtlasSprite icon, int metadata) {
/* 1735 */     IBlockState iblockstate = iblockaccess.getBlockState(blockPos);
/*      */     
/* 1737 */     if (iblockstate == AIR_DEFAULT_STATE)
/*      */     {
/* 1739 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1743 */     if (cp.matchBlocks != null && iblockstate instanceof BlockStateBase) {
/*      */       
/* 1745 */       BlockStateBase blockstatebase = (BlockStateBase)iblockstate;
/*      */       
/* 1747 */       if (!cp.matchesBlock(blockstatebase.getBlockId(), blockstatebase.getMetadata()))
/*      */       {
/* 1749 */         return false;
/*      */       }
/*      */     } 
/*      */     
/* 1753 */     if (cp.matchTileIcons != null) {
/*      */       
/* 1755 */       TextureAtlasSprite textureatlassprite = getNeighbourIcon(iblockaccess, blockState, blockPos, iblockstate, side);
/*      */       
/* 1757 */       if (textureatlassprite != icon)
/*      */       {
/* 1759 */         return false;
/*      */       }
/*      */     } 
/*      */     
/* 1763 */     IBlockState iblockstate1 = iblockaccess.getBlockState(blockPos.offset(getFacing(side)));
/* 1764 */     return iblockstate1.getBlock().isOpaqueCube() ? false : ((side != 1 || iblockstate1.getBlock() != Blocks.snow_layer));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isNeighbour(ConnectedProperties cp, IBlockAccess iblockaccess, IBlockState blockState, BlockPos blockPos, int side, TextureAtlasSprite icon, int metadata) {
/* 1770 */     IBlockState iblockstate = iblockaccess.getBlockState(blockPos);
/* 1771 */     return isNeighbour(cp, iblockaccess, blockState, blockPos, iblockstate, side, icon, metadata);
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean isNeighbour(ConnectedProperties cp, IBlockAccess iblockaccess, IBlockState blockState, BlockPos blockPos, IBlockState neighbourState, int side, TextureAtlasSprite icon, int metadata) {
/* 1776 */     if (blockState == neighbourState)
/*      */     {
/* 1778 */       return true;
/*      */     }
/* 1780 */     if (cp.connect == 2) {
/*      */       
/* 1782 */       if (neighbourState == null)
/*      */       {
/* 1784 */         return false;
/*      */       }
/* 1786 */       if (neighbourState == AIR_DEFAULT_STATE)
/*      */       {
/* 1788 */         return false;
/*      */       }
/*      */ 
/*      */       
/* 1792 */       TextureAtlasSprite textureatlassprite = getNeighbourIcon(iblockaccess, blockState, blockPos, neighbourState, side);
/* 1793 */       return (textureatlassprite == icon);
/*      */     } 
/*      */     
/* 1796 */     if (cp.connect == 3)
/*      */     {
/* 1798 */       return (neighbourState == null) ? false : ((neighbourState == AIR_DEFAULT_STATE) ? false : ((neighbourState.getBlock().getMaterial() == blockState.getBlock().getMaterial())));
/*      */     }
/* 1800 */     if (!(neighbourState instanceof BlockStateBase))
/*      */     {
/* 1802 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1806 */     BlockStateBase blockstatebase = (BlockStateBase)neighbourState;
/* 1807 */     Block block = blockstatebase.getBlock();
/* 1808 */     int i = blockstatebase.getMetadata();
/* 1809 */     return (block == blockState.getBlock() && i == metadata);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static TextureAtlasSprite getNeighbourIcon(IBlockAccess iblockaccess, IBlockState blockState, BlockPos blockPos, IBlockState neighbourState, int side) {
/* 1815 */     neighbourState = neighbourState.getBlock().getActualState(neighbourState, iblockaccess, blockPos);
/* 1816 */     IBakedModel ibakedmodel = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(neighbourState);
/*      */     
/* 1818 */     if (ibakedmodel == null)
/*      */     {
/* 1820 */       return null;
/*      */     }
/*      */ 
/*      */     
/* 1824 */     if (Reflector.ForgeBlock_getExtendedState.exists())
/*      */     {
/* 1826 */       neighbourState = (IBlockState)Reflector.call(neighbourState.getBlock(), Reflector.ForgeBlock_getExtendedState, new Object[] { neighbourState, iblockaccess, blockPos });
/*      */     }
/*      */     
/* 1829 */     EnumFacing enumfacing = getFacing(side);
/* 1830 */     List<BakedQuad> list = ibakedmodel.getFaceQuads(enumfacing);
/*      */     
/* 1832 */     if (list == null)
/*      */     {
/* 1834 */       return null;
/*      */     }
/*      */ 
/*      */     
/* 1838 */     if (Config.isBetterGrass())
/*      */     {
/* 1840 */       list = BetterGrass.getFaceQuads(iblockaccess, neighbourState, blockPos, enumfacing, list);
/*      */     }
/*      */     
/* 1843 */     if (!list.isEmpty()) {
/*      */       
/* 1845 */       BakedQuad bakedquad1 = list.get(0);
/* 1846 */       return bakedquad1.getSprite();
/*      */     } 
/*      */ 
/*      */     
/* 1850 */     List<BakedQuad> list1 = ibakedmodel.getGeneralQuads();
/*      */     
/* 1852 */     if (list1 == null)
/*      */     {
/* 1854 */       return null;
/*      */     }
/*      */ 
/*      */     
/* 1858 */     for (int i = 0; i < list1.size(); i++) {
/*      */       
/* 1860 */       BakedQuad bakedquad = list1.get(i);
/*      */       
/* 1862 */       if (bakedquad.getFace() == enumfacing)
/*      */       {
/* 1864 */         return bakedquad.getSprite();
/*      */       }
/*      */     } 
/*      */     
/* 1868 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static TextureAtlasSprite getConnectedTextureHorizontal(ConnectedProperties cp, IBlockAccess blockAccess, IBlockState blockState, BlockPos blockPos, int vertAxis, int side, TextureAtlasSprite icon, int metadata) {
/* 1879 */     boolean flag = false;
/* 1880 */     boolean flag1 = false;
/*      */ 
/*      */     
/* 1883 */     switch (vertAxis) {
/*      */       
/*      */       case 0:
/* 1886 */         switch (side) {
/*      */           
/*      */           case 0:
/* 1889 */             flag = isNeighbour(cp, blockAccess, blockState, blockPos.west(), side, icon, metadata);
/* 1890 */             flag1 = isNeighbour(cp, blockAccess, blockState, blockPos.east(), side, icon, metadata);
/*      */             break;
/*      */           
/*      */           case 1:
/* 1894 */             flag = isNeighbour(cp, blockAccess, blockState, blockPos.west(), side, icon, metadata);
/* 1895 */             flag1 = isNeighbour(cp, blockAccess, blockState, blockPos.east(), side, icon, metadata);
/*      */             break;
/*      */           
/*      */           case 2:
/* 1899 */             flag = isNeighbour(cp, blockAccess, blockState, blockPos.east(), side, icon, metadata);
/* 1900 */             flag1 = isNeighbour(cp, blockAccess, blockState, blockPos.west(), side, icon, metadata);
/*      */             break;
/*      */           
/*      */           case 3:
/* 1904 */             flag = isNeighbour(cp, blockAccess, blockState, blockPos.west(), side, icon, metadata);
/* 1905 */             flag1 = isNeighbour(cp, blockAccess, blockState, blockPos.east(), side, icon, metadata);
/*      */             break;
/*      */           
/*      */           case 4:
/* 1909 */             flag = isNeighbour(cp, blockAccess, blockState, blockPos.north(), side, icon, metadata);
/* 1910 */             flag1 = isNeighbour(cp, blockAccess, blockState, blockPos.south(), side, icon, metadata);
/*      */             break;
/*      */           
/*      */           case 5:
/* 1914 */             flag = isNeighbour(cp, blockAccess, blockState, blockPos.south(), side, icon, metadata);
/* 1915 */             flag1 = isNeighbour(cp, blockAccess, blockState, blockPos.north(), side, icon, metadata);
/*      */             break;
/*      */         } 
/*      */         
/*      */         break;
/*      */       
/*      */       case 1:
/* 1922 */         switch (side) {
/*      */           
/*      */           case 0:
/* 1925 */             flag = isNeighbour(cp, blockAccess, blockState, blockPos.east(), side, icon, metadata);
/* 1926 */             flag1 = isNeighbour(cp, blockAccess, blockState, blockPos.west(), side, icon, metadata);
/*      */             break;
/*      */           
/*      */           case 1:
/* 1930 */             flag = isNeighbour(cp, blockAccess, blockState, blockPos.west(), side, icon, metadata);
/* 1931 */             flag1 = isNeighbour(cp, blockAccess, blockState, blockPos.east(), side, icon, metadata);
/*      */             break;
/*      */           
/*      */           case 2:
/* 1935 */             flag = isNeighbour(cp, blockAccess, blockState, blockPos.west(), side, icon, metadata);
/* 1936 */             flag1 = isNeighbour(cp, blockAccess, blockState, blockPos.east(), side, icon, metadata);
/*      */             break;
/*      */           
/*      */           case 3:
/* 1940 */             flag = isNeighbour(cp, blockAccess, blockState, blockPos.west(), side, icon, metadata);
/* 1941 */             flag1 = isNeighbour(cp, blockAccess, blockState, blockPos.east(), side, icon, metadata);
/*      */             break;
/*      */           
/*      */           case 4:
/* 1945 */             flag = isNeighbour(cp, blockAccess, blockState, blockPos.down(), side, icon, metadata);
/* 1946 */             flag1 = isNeighbour(cp, blockAccess, blockState, blockPos.up(), side, icon, metadata);
/*      */             break;
/*      */           
/*      */           case 5:
/* 1950 */             flag = isNeighbour(cp, blockAccess, blockState, blockPos.up(), side, icon, metadata);
/* 1951 */             flag1 = isNeighbour(cp, blockAccess, blockState, blockPos.down(), side, icon, metadata);
/*      */             break;
/*      */         } 
/*      */         
/*      */         break;
/*      */       
/*      */       case 2:
/* 1958 */         switch (side) {
/*      */           
/*      */           case 0:
/* 1961 */             flag = isNeighbour(cp, blockAccess, blockState, blockPos.south(), side, icon, metadata);
/* 1962 */             flag1 = isNeighbour(cp, blockAccess, blockState, blockPos.north(), side, icon, metadata);
/*      */             break;
/*      */           
/*      */           case 1:
/* 1966 */             flag = isNeighbour(cp, blockAccess, blockState, blockPos.north(), side, icon, metadata);
/* 1967 */             flag1 = isNeighbour(cp, blockAccess, blockState, blockPos.south(), side, icon, metadata);
/*      */             break;
/*      */           
/*      */           case 2:
/* 1971 */             flag = isNeighbour(cp, blockAccess, blockState, blockPos.down(), side, icon, metadata);
/* 1972 */             flag1 = isNeighbour(cp, blockAccess, blockState, blockPos.up(), side, icon, metadata);
/*      */             break;
/*      */           
/*      */           case 3:
/* 1976 */             flag = isNeighbour(cp, blockAccess, blockState, blockPos.up(), side, icon, metadata);
/* 1977 */             flag1 = isNeighbour(cp, blockAccess, blockState, blockPos.down(), side, icon, metadata);
/*      */             break;
/*      */           
/*      */           case 4:
/* 1981 */             flag = isNeighbour(cp, blockAccess, blockState, blockPos.north(), side, icon, metadata);
/* 1982 */             flag1 = isNeighbour(cp, blockAccess, blockState, blockPos.south(), side, icon, metadata);
/*      */             break;
/*      */           
/*      */           case 5:
/* 1986 */             flag = isNeighbour(cp, blockAccess, blockState, blockPos.north(), side, icon, metadata);
/* 1987 */             flag1 = isNeighbour(cp, blockAccess, blockState, blockPos.south(), side, icon, metadata); break;
/*      */         } 
/*      */         break;
/*      */     } 
/* 1991 */     int i = 3;
/*      */     
/* 1993 */     if (flag) {
/*      */       
/* 1995 */       if (flag1)
/*      */       {
/* 1997 */         i = 1;
/*      */       }
/*      */       else
/*      */       {
/* 2001 */         i = 2;
/*      */       }
/*      */     
/* 2004 */     } else if (flag1) {
/*      */       
/* 2006 */       i = 0;
/*      */     }
/*      */     else {
/*      */       
/* 2010 */       i = 3;
/*      */     } 
/*      */     
/* 2013 */     return cp.tileIcons[i];
/*      */   }
/*      */ 
/*      */   
/*      */   private static TextureAtlasSprite getConnectedTextureVertical(ConnectedProperties cp, IBlockAccess blockAccess, IBlockState blockState, BlockPos blockPos, int vertAxis, int side, TextureAtlasSprite icon, int metadata) {
/* 2018 */     boolean flag = false;
/* 2019 */     boolean flag1 = false;
/*      */     
/* 2021 */     switch (vertAxis) {
/*      */       
/*      */       case 0:
/* 2024 */         if (side == 1) {
/*      */           
/* 2026 */           flag = isNeighbour(cp, blockAccess, blockState, blockPos.south(), side, icon, metadata);
/* 2027 */           flag1 = isNeighbour(cp, blockAccess, blockState, blockPos.north(), side, icon, metadata); break;
/*      */         } 
/* 2029 */         if (side == 0) {
/*      */           
/* 2031 */           flag = isNeighbour(cp, blockAccess, blockState, blockPos.north(), side, icon, metadata);
/* 2032 */           flag1 = isNeighbour(cp, blockAccess, blockState, blockPos.south(), side, icon, metadata);
/*      */           
/*      */           break;
/*      */         } 
/* 2036 */         flag = isNeighbour(cp, blockAccess, blockState, blockPos.down(), side, icon, metadata);
/* 2037 */         flag1 = isNeighbour(cp, blockAccess, blockState, blockPos.up(), side, icon, metadata);
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case 1:
/* 2043 */         if (side == 3) {
/*      */           
/* 2045 */           flag = isNeighbour(cp, blockAccess, blockState, blockPos.down(), side, icon, metadata);
/* 2046 */           flag1 = isNeighbour(cp, blockAccess, blockState, blockPos.up(), side, icon, metadata); break;
/*      */         } 
/* 2048 */         if (side == 2) {
/*      */           
/* 2050 */           flag = isNeighbour(cp, blockAccess, blockState, blockPos.up(), side, icon, metadata);
/* 2051 */           flag1 = isNeighbour(cp, blockAccess, blockState, blockPos.down(), side, icon, metadata);
/*      */           
/*      */           break;
/*      */         } 
/* 2055 */         flag = isNeighbour(cp, blockAccess, blockState, blockPos.south(), side, icon, metadata);
/* 2056 */         flag1 = isNeighbour(cp, blockAccess, blockState, blockPos.north(), side, icon, metadata);
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case 2:
/* 2062 */         if (side == 5) {
/*      */           
/* 2064 */           flag = isNeighbour(cp, blockAccess, blockState, blockPos.up(), side, icon, metadata);
/* 2065 */           flag1 = isNeighbour(cp, blockAccess, blockState, blockPos.down(), side, icon, metadata); break;
/*      */         } 
/* 2067 */         if (side == 4) {
/*      */           
/* 2069 */           flag = isNeighbour(cp, blockAccess, blockState, blockPos.down(), side, icon, metadata);
/* 2070 */           flag1 = isNeighbour(cp, blockAccess, blockState, blockPos.up(), side, icon, metadata);
/*      */           
/*      */           break;
/*      */         } 
/* 2074 */         flag = isNeighbour(cp, blockAccess, blockState, blockPos.west(), side, icon, metadata);
/* 2075 */         flag1 = isNeighbour(cp, blockAccess, blockState, blockPos.east(), side, icon, metadata);
/*      */         break;
/*      */     } 
/*      */     
/* 2079 */     int i = 3;
/*      */     
/* 2081 */     if (flag) {
/*      */       
/* 2083 */       if (flag1)
/*      */       {
/* 2085 */         i = 1;
/*      */       }
/*      */       else
/*      */       {
/* 2089 */         i = 2;
/*      */       }
/*      */     
/* 2092 */     } else if (flag1) {
/*      */       
/* 2094 */       i = 0;
/*      */     }
/*      */     else {
/*      */       
/* 2098 */       i = 3;
/*      */     } 
/*      */     
/* 2101 */     return cp.tileIcons[i];
/*      */   }
/*      */ 
/*      */   
/*      */   private static TextureAtlasSprite getConnectedTextureHorizontalVertical(ConnectedProperties cp, IBlockAccess blockAccess, IBlockState blockState, BlockPos blockPos, int vertAxis, int side, TextureAtlasSprite icon, int metadata) {
/* 2106 */     TextureAtlasSprite[] atextureatlassprite = cp.tileIcons;
/* 2107 */     TextureAtlasSprite textureatlassprite = getConnectedTextureHorizontal(cp, blockAccess, blockState, blockPos, vertAxis, side, icon, metadata);
/*      */     
/* 2109 */     if (textureatlassprite != null && textureatlassprite != icon && textureatlassprite != atextureatlassprite[3])
/*      */     {
/* 2111 */       return textureatlassprite;
/*      */     }
/*      */ 
/*      */     
/* 2115 */     TextureAtlasSprite textureatlassprite1 = getConnectedTextureVertical(cp, blockAccess, blockState, blockPos, vertAxis, side, icon, metadata);
/* 2116 */     return (textureatlassprite1 == atextureatlassprite[0]) ? atextureatlassprite[4] : ((textureatlassprite1 == atextureatlassprite[1]) ? atextureatlassprite[5] : ((textureatlassprite1 == atextureatlassprite[2]) ? atextureatlassprite[6] : textureatlassprite1));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static TextureAtlasSprite getConnectedTextureVerticalHorizontal(ConnectedProperties cp, IBlockAccess blockAccess, IBlockState blockState, BlockPos blockPos, int vertAxis, int side, TextureAtlasSprite icon, int metadata) {
/* 2122 */     TextureAtlasSprite[] atextureatlassprite = cp.tileIcons;
/* 2123 */     TextureAtlasSprite textureatlassprite = getConnectedTextureVertical(cp, blockAccess, blockState, blockPos, vertAxis, side, icon, metadata);
/*      */     
/* 2125 */     if (textureatlassprite != null && textureatlassprite != icon && textureatlassprite != atextureatlassprite[3])
/*      */     {
/* 2127 */       return textureatlassprite;
/*      */     }
/*      */ 
/*      */     
/* 2131 */     TextureAtlasSprite textureatlassprite1 = getConnectedTextureHorizontal(cp, blockAccess, blockState, blockPos, vertAxis, side, icon, metadata);
/* 2132 */     return (textureatlassprite1 == atextureatlassprite[0]) ? atextureatlassprite[4] : ((textureatlassprite1 == atextureatlassprite[1]) ? atextureatlassprite[5] : ((textureatlassprite1 == atextureatlassprite[2]) ? atextureatlassprite[6] : textureatlassprite1));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static TextureAtlasSprite getConnectedTextureTop(ConnectedProperties cp, IBlockAccess blockAccess, IBlockState blockState, BlockPos blockPos, int vertAxis, int side, TextureAtlasSprite icon, int metadata) {
/* 2138 */     boolean flag = false;
/*      */     
/* 2140 */     switch (vertAxis) {
/*      */       
/*      */       case 0:
/* 2143 */         if (side == 1 || side == 0)
/*      */         {
/* 2145 */           return null;
/*      */         }
/*      */         
/* 2148 */         flag = isNeighbour(cp, blockAccess, blockState, blockPos.up(), side, icon, metadata);
/*      */         break;
/*      */       
/*      */       case 1:
/* 2152 */         if (side == 3 || side == 2)
/*      */         {
/* 2154 */           return null;
/*      */         }
/*      */         
/* 2157 */         flag = isNeighbour(cp, blockAccess, blockState, blockPos.south(), side, icon, metadata);
/*      */         break;
/*      */       
/*      */       case 2:
/* 2161 */         if (side == 5 || side == 4)
/*      */         {
/* 2163 */           return null;
/*      */         }
/*      */         
/* 2166 */         flag = isNeighbour(cp, blockAccess, blockState, blockPos.east(), side, icon, metadata);
/*      */         break;
/*      */     } 
/* 2169 */     if (flag)
/*      */     {
/* 2171 */       return cp.tileIcons[0];
/*      */     }
/*      */ 
/*      */     
/* 2175 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void updateIcons(TextureMap textureMap) {
/* 2181 */     blockProperties = (ConnectedProperties[][])null;
/* 2182 */     tileProperties = (ConnectedProperties[][])null;
/* 2183 */     spriteQuadMaps = null;
/* 2184 */     spriteQuadCompactMaps = (Map[][])null;
/*      */     
/* 2186 */     if (Config.isConnectedTextures()) {
/*      */       
/* 2188 */       IResourcePack[] airesourcepack = Config.getResourcePacks();
/*      */       
/* 2190 */       for (int i = airesourcepack.length - 1; i >= 0; i--) {
/*      */         
/* 2192 */         IResourcePack iresourcepack = airesourcepack[i];
/* 2193 */         updateIcons(textureMap, iresourcepack);
/*      */       } 
/*      */       
/* 2196 */       updateIcons(textureMap, (IResourcePack)Config.getDefaultResourcePack());
/* 2197 */       ResourceLocation resourcelocation = new ResourceLocation("mcpatcher/ctm/default/empty");
/* 2198 */       emptySprite = textureMap.registerSprite(resourcelocation);
/* 2199 */       spriteQuadMaps = new Map[textureMap.getCountRegisteredSprites() + 1];
/* 2200 */       spriteQuadFullMaps = new Map[textureMap.getCountRegisteredSprites() + 1];
/* 2201 */       spriteQuadCompactMaps = new Map[textureMap.getCountRegisteredSprites() + 1][];
/*      */       
/* 2203 */       if (blockProperties.length <= 0)
/*      */       {
/* 2205 */         blockProperties = (ConnectedProperties[][])null;
/*      */       }
/*      */       
/* 2208 */       if (tileProperties.length <= 0)
/*      */       {
/* 2210 */         tileProperties = (ConnectedProperties[][])null;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void updateIconEmpty(TextureMap textureMap) {}
/*      */ 
/*      */   
/*      */   public static void updateIcons(TextureMap textureMap, IResourcePack rp) {
/* 2221 */     String[] astring = ResUtils.collectFiles(rp, "mcpatcher/ctm/", ".properties", getDefaultCtmPaths());
/* 2222 */     Arrays.sort((Object[])astring);
/* 2223 */     List list = makePropertyList(tileProperties);
/* 2224 */     List list1 = makePropertyList(blockProperties);
/*      */     
/* 2226 */     for (int i = 0; i < astring.length; i++) {
/*      */       
/* 2228 */       String s = astring[i];
/* 2229 */       Config.dbg("ConnectedTextures: " + s);
/*      */ 
/*      */       
/*      */       try {
/* 2233 */         ResourceLocation resourcelocation = new ResourceLocation(s);
/* 2234 */         InputStream inputstream = rp.getInputStream(resourcelocation);
/*      */         
/* 2236 */         if (inputstream == null) {
/*      */           
/* 2238 */           Config.warn("ConnectedTextures file not found: " + s);
/*      */         }
/*      */         else {
/*      */           
/* 2242 */           PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
/* 2243 */           propertiesOrdered.load(inputstream);
/* 2244 */           inputstream.close();
/* 2245 */           ConnectedProperties connectedproperties = new ConnectedProperties((Properties)propertiesOrdered, s);
/*      */           
/* 2247 */           if (connectedproperties.isValid(s))
/*      */           {
/* 2249 */             connectedproperties.updateIcons(textureMap);
/* 2250 */             addToTileList(connectedproperties, list);
/* 2251 */             addToBlockList(connectedproperties, list1);
/*      */           }
/*      */         
/*      */         } 
/* 2255 */       } catch (FileNotFoundException var11) {
/*      */         
/* 2257 */         Config.warn("ConnectedTextures file not found: " + s);
/*      */       }
/* 2259 */       catch (Exception exception) {
/*      */         
/* 2261 */         exception.printStackTrace();
/*      */       } 
/*      */     } 
/*      */     
/* 2265 */     blockProperties = propertyListToArray(list1);
/* 2266 */     tileProperties = propertyListToArray(list);
/* 2267 */     multipass = detectMultipass();
/* 2268 */     Config.dbg("Multipass connected textures: " + multipass);
/*      */   }
/*      */ 
/*      */   
/*      */   private static List makePropertyList(ConnectedProperties[][] propsArr) {
/* 2273 */     List<List> list = new ArrayList();
/*      */     
/* 2275 */     if (propsArr != null)
/*      */     {
/* 2277 */       for (int i = 0; i < propsArr.length; i++) {
/*      */         
/* 2279 */         ConnectedProperties[] aconnectedproperties = propsArr[i];
/* 2280 */         List list1 = null;
/*      */         
/* 2282 */         if (aconnectedproperties != null)
/*      */         {
/* 2284 */           list1 = new ArrayList(Arrays.asList((Object[])aconnectedproperties));
/*      */         }
/*      */         
/* 2287 */         list.add(list1);
/*      */       } 
/*      */     }
/*      */     
/* 2291 */     return list;
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean detectMultipass() {
/* 2296 */     List list = new ArrayList();
/*      */     
/* 2298 */     for (int i = 0; i < tileProperties.length; i++) {
/*      */       
/* 2300 */       ConnectedProperties[] aconnectedproperties = tileProperties[i];
/*      */       
/* 2302 */       if (aconnectedproperties != null)
/*      */       {
/* 2304 */         list.addAll(Arrays.asList(aconnectedproperties));
/*      */       }
/*      */     } 
/*      */     
/* 2308 */     for (int k = 0; k < blockProperties.length; k++) {
/*      */       
/* 2310 */       ConnectedProperties[] aconnectedproperties2 = blockProperties[k];
/*      */       
/* 2312 */       if (aconnectedproperties2 != null)
/*      */       {
/* 2314 */         list.addAll(Arrays.asList(aconnectedproperties2));
/*      */       }
/*      */     } 
/*      */     
/* 2318 */     ConnectedProperties[] aconnectedproperties1 = (ConnectedProperties[])list.toArray((Object[])new ConnectedProperties[0]);
/* 2319 */     Set set1 = new HashSet();
/* 2320 */     Set<?> set = new HashSet();
/*      */     
/* 2322 */     for (int j = 0; j < aconnectedproperties1.length; j++) {
/*      */       
/* 2324 */       ConnectedProperties connectedproperties = aconnectedproperties1[j];
/*      */       
/* 2326 */       if (connectedproperties.matchTileIcons != null)
/*      */       {
/* 2328 */         set1.addAll(Arrays.asList(connectedproperties.matchTileIcons));
/*      */       }
/*      */       
/* 2331 */       if (connectedproperties.tileIcons != null)
/*      */       {
/* 2333 */         set.addAll(Arrays.asList(connectedproperties.tileIcons));
/*      */       }
/*      */     } 
/*      */     
/* 2337 */     set1.retainAll(set);
/* 2338 */     return !set1.isEmpty();
/*      */   }
/*      */   
/*      */   private static ConnectedProperties[][] propertyListToArray(List<List> list) {
/* 2342 */     ConnectedProperties[][] propArr = new ConnectedProperties[list.size()][];
/* 2343 */     for (int i = 0; i < list.size(); i++) {
/* 2344 */       List subList = list.get(i);
/* 2345 */       if (subList != null) {
/* 2346 */         ConnectedProperties[] subArr = (ConnectedProperties[])subList.toArray((Object[])new ConnectedProperties[0]);
/* 2347 */         propArr[i] = subArr;
/*      */       } 
/*      */     } 
/* 2350 */     return propArr;
/*      */   }
/*      */ 
/*      */   
/*      */   private static void addToTileList(ConnectedProperties cp, List tileList) {
/* 2355 */     if (cp.matchTileIcons != null)
/*      */     {
/* 2357 */       for (int i = 0; i < cp.matchTileIcons.length; i++) {
/*      */         
/* 2359 */         TextureAtlasSprite textureatlassprite = cp.matchTileIcons[i];
/*      */         
/* 2361 */         if (!(textureatlassprite instanceof TextureAtlasSprite)) {
/*      */           
/* 2363 */           Config.warn("TextureAtlasSprite is not TextureAtlasSprite: " + textureatlassprite + ", name: " + textureatlassprite.getIconName());
/*      */         }
/*      */         else {
/*      */           
/* 2367 */           int j = textureatlassprite.getIndexInMap();
/*      */           
/* 2369 */           if (j < 0) {
/*      */             
/* 2371 */             Config.warn("Invalid tile ID: " + j + ", icon: " + textureatlassprite.getIconName());
/*      */           }
/*      */           else {
/*      */             
/* 2375 */             addToList(cp, tileList, j);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static void addToBlockList(ConnectedProperties cp, List blockList) {
/* 2384 */     if (cp.matchBlocks != null)
/*      */     {
/* 2386 */       for (int i = 0; i < cp.matchBlocks.length; i++) {
/*      */         
/* 2388 */         int j = cp.matchBlocks[i].getBlockId();
/*      */         
/* 2390 */         if (j < 0) {
/*      */           
/* 2392 */           Config.warn("Invalid block ID: " + j);
/*      */         }
/*      */         else {
/*      */           
/* 2396 */           addToList(cp, blockList, j);
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static void addToList(ConnectedProperties cp, List<List> list, int id) {
/* 2404 */     while (id >= list.size())
/*      */     {
/* 2406 */       list.add(null);
/*      */     }
/*      */     
/* 2409 */     List<ConnectedProperties> l = list.get(id);
/*      */     
/* 2411 */     if (l == null) {
/*      */       
/* 2413 */       l = new ArrayList();
/* 2414 */       list.set(id, l);
/*      */     } 
/*      */     
/* 2417 */     l.add(cp);
/*      */   }
/*      */ 
/*      */   
/*      */   private static String[] getDefaultCtmPaths() {
/* 2422 */     List<String> list = new ArrayList();
/* 2423 */     String s = "mcpatcher/ctm/default/";
/*      */     
/* 2425 */     if (Config.isFromDefaultResourcePack(new ResourceLocation("textures/blocks/glass.png"))) {
/*      */       
/* 2427 */       list.add(s + "glass.properties");
/* 2428 */       list.add(s + "glasspane.properties");
/*      */     } 
/*      */     
/* 2431 */     if (Config.isFromDefaultResourcePack(new ResourceLocation("textures/blocks/bookshelf.png")))
/*      */     {
/* 2433 */       list.add(s + "bookshelf.properties");
/*      */     }
/*      */     
/* 2436 */     if (Config.isFromDefaultResourcePack(new ResourceLocation("textures/blocks/sandstone_normal.png")))
/*      */     {
/* 2438 */       list.add(s + "sandstone.properties");
/*      */     }
/*      */     
/* 2441 */     String[] astring = { "white", "orange", "magenta", "light_blue", "yellow", "lime", "pink", "gray", "silver", "cyan", "purple", "blue", "brown", "green", "red", "black" };
/*      */     
/* 2443 */     for (int i = 0; i < astring.length; i++) {
/*      */       
/* 2445 */       String s1 = astring[i];
/*      */       
/* 2447 */       if (Config.isFromDefaultResourcePack(new ResourceLocation("textures/blocks/glass_" + s1 + ".png"))) {
/*      */         
/* 2449 */         list.add(s + i + "_glass_" + s1 + "/glass_" + s1 + ".properties");
/* 2450 */         list.add(s + i + "_glass_" + s1 + "/glass_pane_" + s1 + ".properties");
/*      */       } 
/*      */     } 
/*      */     
/* 2454 */     String[] astring1 = list.<String>toArray(new String[0]);
/* 2455 */     return astring1;
/*      */   }
/*      */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\ConnectedTextures.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */