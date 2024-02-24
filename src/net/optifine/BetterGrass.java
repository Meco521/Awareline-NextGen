/*     */ package net.optifine;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.List;
/*     */ import java.util.Properties;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockDirt;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.client.resources.model.IBakedModel;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.optifine.model.BlockModelUtils;
/*     */ import net.optifine.util.PropertiesOrdered;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BetterGrass
/*     */ {
/*     */   private static boolean betterGrass = true;
/*     */   private static boolean betterMycelium = true;
/*     */   private static boolean betterPodzol = true;
/*     */   private static boolean betterGrassSnow = true;
/*     */   private static boolean betterMyceliumSnow = true;
/*     */   private static boolean betterPodzolSnow = true;
/*     */   private static boolean grassMultilayer = false;
/*  34 */   private static TextureAtlasSprite spriteGrass = null;
/*  35 */   private static TextureAtlasSprite spriteGrassSide = null;
/*  36 */   private static TextureAtlasSprite spriteMycelium = null;
/*  37 */   private static TextureAtlasSprite spritePodzol = null;
/*  38 */   private static TextureAtlasSprite spriteSnow = null;
/*     */   private static boolean spritesLoaded = false;
/*  40 */   private static IBakedModel modelCubeGrass = null;
/*  41 */   private static IBakedModel modelCubeMycelium = null;
/*  42 */   private static IBakedModel modelCubePodzol = null;
/*  43 */   private static IBakedModel modelCubeSnow = null;
/*     */   
/*     */   private static boolean modelsLoaded = false;
/*     */   private static final String TEXTURE_GRASS_DEFAULT = "blocks/grass_top";
/*     */   private static final String TEXTURE_GRASS_SIDE_DEFAULT = "blocks/grass_side";
/*     */   private static final String TEXTURE_MYCELIUM_DEFAULT = "blocks/mycelium_top";
/*     */   private static final String TEXTURE_PODZOL_DEFAULT = "blocks/dirt_podzol_top";
/*     */   private static final String TEXTURE_SNOW_DEFAULT = "blocks/snow";
/*     */   
/*     */   public static void updateIcons(TextureMap textureMap) {
/*  53 */     spritesLoaded = false;
/*  54 */     modelsLoaded = false;
/*  55 */     loadProperties(textureMap);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void update() {
/*  60 */     if (spritesLoaded) {
/*     */       
/*  62 */       modelCubeGrass = BlockModelUtils.makeModelCube(spriteGrass, 0);
/*     */       
/*  64 */       if (grassMultilayer) {
/*     */         
/*  66 */         IBakedModel ibakedmodel = BlockModelUtils.makeModelCube(spriteGrassSide, -1);
/*  67 */         modelCubeGrass = BlockModelUtils.joinModelsCube(ibakedmodel, modelCubeGrass);
/*     */       } 
/*     */       
/*  70 */       modelCubeMycelium = BlockModelUtils.makeModelCube(spriteMycelium, -1);
/*  71 */       modelCubePodzol = BlockModelUtils.makeModelCube(spritePodzol, 0);
/*  72 */       modelCubeSnow = BlockModelUtils.makeModelCube(spriteSnow, -1);
/*  73 */       modelsLoaded = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void loadProperties(TextureMap textureMap) {
/*  79 */     betterGrass = true;
/*  80 */     betterMycelium = true;
/*  81 */     betterPodzol = true;
/*  82 */     betterGrassSnow = true;
/*  83 */     betterMyceliumSnow = true;
/*  84 */     betterPodzolSnow = true;
/*  85 */     spriteGrass = textureMap.registerSprite(new ResourceLocation("blocks/grass_top"));
/*  86 */     spriteGrassSide = textureMap.registerSprite(new ResourceLocation("blocks/grass_side"));
/*  87 */     spriteMycelium = textureMap.registerSprite(new ResourceLocation("blocks/mycelium_top"));
/*  88 */     spritePodzol = textureMap.registerSprite(new ResourceLocation("blocks/dirt_podzol_top"));
/*  89 */     spriteSnow = textureMap.registerSprite(new ResourceLocation("blocks/snow"));
/*  90 */     spritesLoaded = true;
/*  91 */     String s = "optifine/bettergrass.properties";
/*     */ 
/*     */     
/*     */     try {
/*  95 */       ResourceLocation resourcelocation = new ResourceLocation(s);
/*     */       
/*  97 */       if (!Config.hasResource(resourcelocation)) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 102 */       InputStream inputstream = Config.getResourceStream(resourcelocation);
/*     */       
/* 104 */       if (inputstream == null) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 109 */       boolean flag = Config.isFromDefaultResourcePack(resourcelocation);
/*     */       
/* 111 */       if (flag) {
/*     */         
/* 113 */         Config.dbg("BetterGrass: Parsing default configuration " + s);
/*     */       }
/*     */       else {
/*     */         
/* 117 */         Config.dbg("BetterGrass: Parsing configuration " + s);
/*     */       } 
/*     */       
/* 120 */       PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
/* 121 */       propertiesOrdered.load(inputstream);
/* 122 */       inputstream.close();
/* 123 */       betterGrass = getBoolean((Properties)propertiesOrdered, "grass", true);
/* 124 */       betterMycelium = getBoolean((Properties)propertiesOrdered, "mycelium", true);
/* 125 */       betterPodzol = getBoolean((Properties)propertiesOrdered, "podzol", true);
/* 126 */       betterGrassSnow = getBoolean((Properties)propertiesOrdered, "grass.snow", true);
/* 127 */       betterMyceliumSnow = getBoolean((Properties)propertiesOrdered, "mycelium.snow", true);
/* 128 */       betterPodzolSnow = getBoolean((Properties)propertiesOrdered, "podzol.snow", true);
/* 129 */       grassMultilayer = getBoolean((Properties)propertiesOrdered, "grass.multilayer", false);
/* 130 */       spriteGrass = registerSprite((Properties)propertiesOrdered, "texture.grass", "blocks/grass_top", textureMap);
/* 131 */       spriteGrassSide = registerSprite((Properties)propertiesOrdered, "texture.grass_side", "blocks/grass_side", textureMap);
/* 132 */       spriteMycelium = registerSprite((Properties)propertiesOrdered, "texture.mycelium", "blocks/mycelium_top", textureMap);
/* 133 */       spritePodzol = registerSprite((Properties)propertiesOrdered, "texture.podzol", "blocks/dirt_podzol_top", textureMap);
/* 134 */       spriteSnow = registerSprite((Properties)propertiesOrdered, "texture.snow", "blocks/snow", textureMap);
/*     */     }
/* 136 */     catch (IOException ioexception) {
/*     */       
/* 138 */       Config.warn("Error reading: " + s + ", " + ioexception.getClass().getName() + ": " + ioexception.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static TextureAtlasSprite registerSprite(Properties props, String key, String textureDefault, TextureMap textureMap) {
/* 144 */     String s = props.getProperty(key);
/*     */     
/* 146 */     if (s == null)
/*     */     {
/* 148 */       s = textureDefault;
/*     */     }
/*     */     
/* 151 */     ResourceLocation resourcelocation = new ResourceLocation("textures/" + s + ".png");
/*     */     
/* 153 */     if (!Config.hasResource(resourcelocation)) {
/*     */       
/* 155 */       Config.warn("BetterGrass texture not found: " + resourcelocation);
/* 156 */       s = textureDefault;
/*     */     } 
/*     */     
/* 159 */     ResourceLocation resourcelocation1 = new ResourceLocation(s);
/* 160 */     TextureAtlasSprite textureatlassprite = textureMap.registerSprite(resourcelocation1);
/* 161 */     return textureatlassprite;
/*     */   }
/*     */ 
/*     */   
/*     */   public static List getFaceQuads(IBlockAccess blockAccess, IBlockState blockState, BlockPos blockPos, EnumFacing facing, List quads) {
/* 166 */     if (facing != EnumFacing.UP && facing != EnumFacing.DOWN) {
/*     */       
/* 168 */       if (!modelsLoaded)
/*     */       {
/* 170 */         return quads;
/*     */       }
/*     */ 
/*     */       
/* 174 */       Block block = blockState.getBlock();
/* 175 */       return (block instanceof net.minecraft.block.BlockMycelium) ? getFaceQuadsMycelium(blockAccess, blockState, blockPos, facing, quads) : ((block instanceof BlockDirt) ? getFaceQuadsDirt(blockAccess, blockState, blockPos, facing, quads) : ((block instanceof net.minecraft.block.BlockGrass) ? getFaceQuadsGrass(blockAccess, blockState, blockPos, facing, quads) : quads));
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 180 */     return quads;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static List getFaceQuadsMycelium(IBlockAccess blockAccess, IBlockState blockState, BlockPos blockPos, EnumFacing facing, List quads) {
/* 186 */     Block block = blockAccess.getBlockState(blockPos.up()).getBlock();
/* 187 */     boolean flag = (block == Blocks.snow || block == Blocks.snow_layer);
/*     */     
/* 189 */     if (Config.isBetterGrassFancy()) {
/*     */       
/* 191 */       if (flag)
/*     */       {
/* 193 */         if (betterMyceliumSnow && getBlockAt(blockPos, facing, blockAccess) == Blocks.snow_layer)
/*     */         {
/* 195 */           return modelCubeSnow.getFaceQuads(facing);
/*     */         }
/*     */       }
/* 198 */       else if (betterMycelium && getBlockAt(blockPos.down(), facing, blockAccess) == Blocks.mycelium)
/*     */       {
/* 200 */         return modelCubeMycelium.getFaceQuads(facing);
/*     */       }
/*     */     
/* 203 */     } else if (flag) {
/*     */       
/* 205 */       if (betterMyceliumSnow)
/*     */       {
/* 207 */         return modelCubeSnow.getFaceQuads(facing);
/*     */       }
/*     */     }
/* 210 */     else if (betterMycelium) {
/*     */       
/* 212 */       return modelCubeMycelium.getFaceQuads(facing);
/*     */     } 
/*     */     
/* 215 */     return quads;
/*     */   }
/*     */ 
/*     */   
/*     */   private static List getFaceQuadsDirt(IBlockAccess blockAccess, IBlockState blockState, BlockPos blockPos, EnumFacing facing, List quads) {
/* 220 */     Block block = getBlockAt(blockPos, EnumFacing.UP, blockAccess);
/*     */     
/* 222 */     if (blockState.getValue((IProperty)BlockDirt.VARIANT) != BlockDirt.DirtType.PODZOL)
/*     */     {
/* 224 */       return quads;
/*     */     }
/*     */ 
/*     */     
/* 228 */     boolean flag = (block == Blocks.snow || block == Blocks.snow_layer);
/*     */     
/* 230 */     if (Config.isBetterGrassFancy()) {
/*     */       
/* 232 */       if (flag)
/*     */       {
/* 234 */         if (betterPodzolSnow && getBlockAt(blockPos, facing, blockAccess) == Blocks.snow_layer)
/*     */         {
/* 236 */           return modelCubeSnow.getFaceQuads(facing);
/*     */         }
/*     */       }
/* 239 */       else if (betterPodzol)
/*     */       {
/* 241 */         BlockPos blockpos = blockPos.down().offset(facing);
/* 242 */         IBlockState iblockstate = blockAccess.getBlockState(blockpos);
/*     */         
/* 244 */         if (iblockstate.getBlock() == Blocks.dirt && iblockstate.getValue((IProperty)BlockDirt.VARIANT) == BlockDirt.DirtType.PODZOL)
/*     */         {
/* 246 */           return modelCubePodzol.getFaceQuads(facing);
/*     */         }
/*     */       }
/*     */     
/* 250 */     } else if (flag) {
/*     */       
/* 252 */       if (betterPodzolSnow)
/*     */       {
/* 254 */         return modelCubeSnow.getFaceQuads(facing);
/*     */       }
/*     */     }
/* 257 */     else if (betterPodzol) {
/*     */       
/* 259 */       return modelCubePodzol.getFaceQuads(facing);
/*     */     } 
/*     */     
/* 262 */     return quads;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static List getFaceQuadsGrass(IBlockAccess blockAccess, IBlockState blockState, BlockPos blockPos, EnumFacing facing, List quads) {
/* 268 */     Block block = blockAccess.getBlockState(blockPos.up()).getBlock();
/* 269 */     boolean flag = (block == Blocks.snow || block == Blocks.snow_layer);
/*     */     
/* 271 */     if (Config.isBetterGrassFancy()) {
/*     */       
/* 273 */       if (flag)
/*     */       {
/* 275 */         if (betterGrassSnow && getBlockAt(blockPos, facing, blockAccess) == Blocks.snow_layer)
/*     */         {
/* 277 */           return modelCubeSnow.getFaceQuads(facing);
/*     */         }
/*     */       }
/* 280 */       else if (betterGrass && getBlockAt(blockPos.down(), facing, blockAccess) == Blocks.grass)
/*     */       {
/* 282 */         return modelCubeGrass.getFaceQuads(facing);
/*     */       }
/*     */     
/* 285 */     } else if (flag) {
/*     */       
/* 287 */       if (betterGrassSnow)
/*     */       {
/* 289 */         return modelCubeSnow.getFaceQuads(facing);
/*     */       }
/*     */     }
/* 292 */     else if (betterGrass) {
/*     */       
/* 294 */       return modelCubeGrass.getFaceQuads(facing);
/*     */     } 
/*     */     
/* 297 */     return quads;
/*     */   }
/*     */ 
/*     */   
/*     */   private static Block getBlockAt(BlockPos blockPos, EnumFacing facing, IBlockAccess blockAccess) {
/* 302 */     BlockPos blockpos = blockPos.offset(facing);
/* 303 */     Block block = blockAccess.getBlockState(blockpos).getBlock();
/* 304 */     return block;
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean getBoolean(Properties props, String key, boolean def) {
/* 309 */     String s = props.getProperty(key);
/* 310 */     return (s == null) ? def : Boolean.parseBoolean(s);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\BetterGrass.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */