/*     */ package net.minecraft.client.renderer;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Map;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockColored;
/*     */ import net.minecraft.block.BlockDirt;
/*     */ import net.minecraft.block.BlockDoor;
/*     */ import net.minecraft.block.BlockFenceGate;
/*     */ import net.minecraft.block.BlockLeaves;
/*     */ import net.minecraft.block.BlockQuartz;
/*     */ import net.minecraft.block.BlockStem;
/*     */ import net.minecraft.block.BlockStoneSlab;
/*     */ import net.minecraft.block.BlockStoneSlabNew;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.renderer.block.statemap.IStateMapper;
/*     */ import net.minecraft.client.renderer.block.statemap.StateMap;
/*     */ import net.minecraft.client.renderer.block.statemap.StateMapperBase;
/*     */ import net.minecraft.client.resources.model.IBakedModel;
/*     */ import net.minecraft.client.resources.model.ModelResourceLocation;
/*     */ import net.minecraft.init.Blocks;
/*     */ 
/*     */ public class BlockModelShapes {
/*  24 */   private final Map<IBlockState, IBakedModel> bakedModelStore = Maps.newIdentityHashMap();
/*  25 */   private final BlockStateMapper blockStateMapper = new BlockStateMapper();
/*     */   
/*     */   private final ModelManager modelManager;
/*     */   
/*     */   public BlockModelShapes(ModelManager manager) {
/*  30 */     this.modelManager = manager;
/*  31 */     registerAllBlocks();
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockStateMapper getBlockStateMapper() {
/*  36 */     return this.blockStateMapper;
/*     */   }
/*     */ 
/*     */   
/*     */   public TextureAtlasSprite getTexture(IBlockState state) {
/*  41 */     Block block = state.getBlock();
/*  42 */     IBakedModel ibakedmodel = getModelForState(state);
/*     */     
/*  44 */     if (ibakedmodel == null || ibakedmodel == this.modelManager.getMissingModel()) {
/*     */       
/*  46 */       if (block == Blocks.wall_sign || block == Blocks.standing_sign || block == Blocks.chest || block == Blocks.trapped_chest || block == Blocks.standing_banner || block == Blocks.wall_banner)
/*     */       {
/*  48 */         return this.modelManager.getTextureMap().getAtlasSprite("minecraft:blocks/planks_oak");
/*     */       }
/*     */       
/*  51 */       if (block == Blocks.ender_chest)
/*     */       {
/*  53 */         return this.modelManager.getTextureMap().getAtlasSprite("minecraft:blocks/obsidian");
/*     */       }
/*     */       
/*  56 */       if (block == Blocks.flowing_lava || block == Blocks.lava)
/*     */       {
/*  58 */         return this.modelManager.getTextureMap().getAtlasSprite("minecraft:blocks/lava_still");
/*     */       }
/*     */       
/*  61 */       if (block == Blocks.flowing_water || block == Blocks.water)
/*     */       {
/*  63 */         return this.modelManager.getTextureMap().getAtlasSprite("minecraft:blocks/water_still");
/*     */       }
/*     */       
/*  66 */       if (block == Blocks.skull)
/*     */       {
/*  68 */         return this.modelManager.getTextureMap().getAtlasSprite("minecraft:blocks/soul_sand");
/*     */       }
/*     */       
/*  71 */       if (block == Blocks.barrier)
/*     */       {
/*  73 */         return this.modelManager.getTextureMap().getAtlasSprite("minecraft:items/barrier");
/*     */       }
/*     */     } 
/*     */     
/*  77 */     if (ibakedmodel == null)
/*     */     {
/*  79 */       ibakedmodel = this.modelManager.getMissingModel();
/*     */     }
/*     */     
/*  82 */     return ibakedmodel.getParticleTexture();
/*     */   }
/*     */ 
/*     */   
/*     */   public IBakedModel getModelForState(IBlockState state) {
/*  87 */     IBakedModel ibakedmodel = this.bakedModelStore.get(state);
/*     */     
/*  89 */     if (ibakedmodel == null)
/*     */     {
/*  91 */       ibakedmodel = this.modelManager.getMissingModel();
/*     */     }
/*     */     
/*  94 */     return ibakedmodel;
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelManager getModelManager() {
/*  99 */     return this.modelManager;
/*     */   }
/*     */ 
/*     */   
/*     */   public void reloadModels() {
/* 104 */     this.bakedModelStore.clear();
/*     */     
/* 106 */     for (Map.Entry<IBlockState, ModelResourceLocation> entry : (Iterable<Map.Entry<IBlockState, ModelResourceLocation>>)this.blockStateMapper.putAllStateModelLocations().entrySet())
/*     */     {
/* 108 */       this.bakedModelStore.put(entry.getKey(), this.modelManager.getModel(entry.getValue()));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerBlockWithStateMapper(Block assoc, IStateMapper stateMapper) {
/* 114 */     this.blockStateMapper.registerBlockStateMapper(assoc, stateMapper);
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerBuiltInBlocks(Block... builtIns) {
/* 119 */     this.blockStateMapper.registerBuiltInBlocks(builtIns);
/*     */   }
/*     */ 
/*     */   
/*     */   private void registerAllBlocks() {
/* 124 */     registerBuiltInBlocks(new Block[] { Blocks.air, (Block)Blocks.flowing_water, (Block)Blocks.water, (Block)Blocks.flowing_lava, (Block)Blocks.lava, (Block)Blocks.piston_extension, (Block)Blocks.chest, Blocks.ender_chest, Blocks.trapped_chest, Blocks.standing_sign, (Block)Blocks.skull, Blocks.end_portal, Blocks.barrier, Blocks.wall_sign, Blocks.wall_banner, Blocks.standing_banner });
/* 125 */     registerBlockWithStateMapper(Blocks.stone, (IStateMapper)(new StateMap.Builder()).withName((IProperty)BlockStone.VARIANT).build());
/* 126 */     registerBlockWithStateMapper(Blocks.prismarine, (IStateMapper)(new StateMap.Builder()).withName((IProperty)BlockPrismarine.VARIANT).build());
/* 127 */     registerBlockWithStateMapper((Block)Blocks.leaves, (IStateMapper)(new StateMap.Builder()).withName((IProperty)BlockOldLeaf.VARIANT).withSuffix("_leaves").ignore(new IProperty[] { (IProperty)BlockLeaves.CHECK_DECAY, (IProperty)BlockLeaves.DECAYABLE }).build());
/* 128 */     registerBlockWithStateMapper((Block)Blocks.leaves2, (IStateMapper)(new StateMap.Builder()).withName((IProperty)BlockNewLeaf.VARIANT).withSuffix("_leaves").ignore(new IProperty[] { (IProperty)BlockLeaves.CHECK_DECAY, (IProperty)BlockLeaves.DECAYABLE }).build());
/* 129 */     registerBlockWithStateMapper((Block)Blocks.cactus, (IStateMapper)(new StateMap.Builder()).ignore(new IProperty[] { (IProperty)BlockCactus.AGE }).build());
/* 130 */     registerBlockWithStateMapper((Block)Blocks.reeds, (IStateMapper)(new StateMap.Builder()).ignore(new IProperty[] { (IProperty)BlockReed.AGE }).build());
/* 131 */     registerBlockWithStateMapper(Blocks.jukebox, (IStateMapper)(new StateMap.Builder()).ignore(new IProperty[] { (IProperty)BlockJukebox.HAS_RECORD }).build());
/* 132 */     registerBlockWithStateMapper(Blocks.command_block, (IStateMapper)(new StateMap.Builder()).ignore(new IProperty[] { (IProperty)BlockCommandBlock.TRIGGERED }).build());
/* 133 */     registerBlockWithStateMapper(Blocks.cobblestone_wall, (IStateMapper)(new StateMap.Builder()).withName((IProperty)BlockWall.VARIANT).withSuffix("_wall").build());
/* 134 */     registerBlockWithStateMapper((Block)Blocks.double_plant, (IStateMapper)(new StateMap.Builder()).withName((IProperty)BlockDoublePlant.VARIANT).ignore(new IProperty[] { (IProperty)BlockDoublePlant.FACING }).build());
/* 135 */     registerBlockWithStateMapper(Blocks.oak_fence_gate, (IStateMapper)(new StateMap.Builder()).ignore(new IProperty[] { (IProperty)BlockFenceGate.POWERED }).build());
/* 136 */     registerBlockWithStateMapper(Blocks.spruce_fence_gate, (IStateMapper)(new StateMap.Builder()).ignore(new IProperty[] { (IProperty)BlockFenceGate.POWERED }).build());
/* 137 */     registerBlockWithStateMapper(Blocks.birch_fence_gate, (IStateMapper)(new StateMap.Builder()).ignore(new IProperty[] { (IProperty)BlockFenceGate.POWERED }).build());
/* 138 */     registerBlockWithStateMapper(Blocks.jungle_fence_gate, (IStateMapper)(new StateMap.Builder()).ignore(new IProperty[] { (IProperty)BlockFenceGate.POWERED }).build());
/* 139 */     registerBlockWithStateMapper(Blocks.dark_oak_fence_gate, (IStateMapper)(new StateMap.Builder()).ignore(new IProperty[] { (IProperty)BlockFenceGate.POWERED }).build());
/* 140 */     registerBlockWithStateMapper(Blocks.acacia_fence_gate, (IStateMapper)(new StateMap.Builder()).ignore(new IProperty[] { (IProperty)BlockFenceGate.POWERED }).build());
/* 141 */     registerBlockWithStateMapper(Blocks.tripwire, (IStateMapper)(new StateMap.Builder()).ignore(new IProperty[] { (IProperty)BlockTripWire.DISARMED, (IProperty)BlockTripWire.POWERED }).build());
/* 142 */     registerBlockWithStateMapper((Block)Blocks.double_wooden_slab, (IStateMapper)(new StateMap.Builder()).withName((IProperty)BlockPlanks.VARIANT).withSuffix("_double_slab").build());
/* 143 */     registerBlockWithStateMapper((Block)Blocks.wooden_slab, (IStateMapper)(new StateMap.Builder()).withName((IProperty)BlockPlanks.VARIANT).withSuffix("_slab").build());
/* 144 */     registerBlockWithStateMapper(Blocks.tnt, (IStateMapper)(new StateMap.Builder()).ignore(new IProperty[] { (IProperty)BlockTNT.EXPLODE }).build());
/* 145 */     registerBlockWithStateMapper((Block)Blocks.fire, (IStateMapper)(new StateMap.Builder()).ignore(new IProperty[] { (IProperty)BlockFire.AGE }).build());
/* 146 */     registerBlockWithStateMapper((Block)Blocks.redstone_wire, (IStateMapper)(new StateMap.Builder()).ignore(new IProperty[] { (IProperty)BlockRedstoneWire.POWER }).build());
/* 147 */     registerBlockWithStateMapper(Blocks.oak_door, (IStateMapper)(new StateMap.Builder()).ignore(new IProperty[] { (IProperty)BlockDoor.POWERED }).build());
/* 148 */     registerBlockWithStateMapper(Blocks.spruce_door, (IStateMapper)(new StateMap.Builder()).ignore(new IProperty[] { (IProperty)BlockDoor.POWERED }).build());
/* 149 */     registerBlockWithStateMapper(Blocks.birch_door, (IStateMapper)(new StateMap.Builder()).ignore(new IProperty[] { (IProperty)BlockDoor.POWERED }).build());
/* 150 */     registerBlockWithStateMapper(Blocks.jungle_door, (IStateMapper)(new StateMap.Builder()).ignore(new IProperty[] { (IProperty)BlockDoor.POWERED }).build());
/* 151 */     registerBlockWithStateMapper(Blocks.acacia_door, (IStateMapper)(new StateMap.Builder()).ignore(new IProperty[] { (IProperty)BlockDoor.POWERED }).build());
/* 152 */     registerBlockWithStateMapper(Blocks.dark_oak_door, (IStateMapper)(new StateMap.Builder()).ignore(new IProperty[] { (IProperty)BlockDoor.POWERED }).build());
/* 153 */     registerBlockWithStateMapper(Blocks.iron_door, (IStateMapper)(new StateMap.Builder()).ignore(new IProperty[] { (IProperty)BlockDoor.POWERED }).build());
/* 154 */     registerBlockWithStateMapper(Blocks.wool, (IStateMapper)(new StateMap.Builder()).withName((IProperty)BlockColored.COLOR).withSuffix("_wool").build());
/* 155 */     registerBlockWithStateMapper(Blocks.carpet, (IStateMapper)(new StateMap.Builder()).withName((IProperty)BlockColored.COLOR).withSuffix("_carpet").build());
/* 156 */     registerBlockWithStateMapper(Blocks.stained_hardened_clay, (IStateMapper)(new StateMap.Builder()).withName((IProperty)BlockColored.COLOR).withSuffix("_stained_hardened_clay").build());
/* 157 */     registerBlockWithStateMapper((Block)Blocks.stained_glass_pane, (IStateMapper)(new StateMap.Builder()).withName((IProperty)BlockColored.COLOR).withSuffix("_stained_glass_pane").build());
/* 158 */     registerBlockWithStateMapper((Block)Blocks.stained_glass, (IStateMapper)(new StateMap.Builder()).withName((IProperty)BlockColored.COLOR).withSuffix("_stained_glass").build());
/* 159 */     registerBlockWithStateMapper(Blocks.sandstone, (IStateMapper)(new StateMap.Builder()).withName((IProperty)BlockSandStone.TYPE).build());
/* 160 */     registerBlockWithStateMapper(Blocks.red_sandstone, (IStateMapper)(new StateMap.Builder()).withName((IProperty)BlockRedSandstone.TYPE).build());
/* 161 */     registerBlockWithStateMapper((Block)Blocks.tallgrass, (IStateMapper)(new StateMap.Builder()).withName((IProperty)BlockTallGrass.TYPE).build());
/* 162 */     registerBlockWithStateMapper(Blocks.bed, (IStateMapper)(new StateMap.Builder()).ignore(new IProperty[] { (IProperty)BlockBed.OCCUPIED }).build());
/* 163 */     registerBlockWithStateMapper((Block)Blocks.yellow_flower, (IStateMapper)(new StateMap.Builder()).withName(Blocks.yellow_flower.getTypeProperty()).build());
/* 164 */     registerBlockWithStateMapper((Block)Blocks.red_flower, (IStateMapper)(new StateMap.Builder()).withName(Blocks.red_flower.getTypeProperty()).build());
/* 165 */     registerBlockWithStateMapper((Block)Blocks.stone_slab, (IStateMapper)(new StateMap.Builder()).withName((IProperty)BlockStoneSlab.VARIANT).withSuffix("_slab").build());
/* 166 */     registerBlockWithStateMapper((Block)Blocks.stone_slab2, (IStateMapper)(new StateMap.Builder()).withName((IProperty)BlockStoneSlabNew.VARIANT).withSuffix("_slab").build());
/* 167 */     registerBlockWithStateMapper(Blocks.monster_egg, (IStateMapper)(new StateMap.Builder()).withName((IProperty)BlockSilverfish.VARIANT).withSuffix("_monster_egg").build());
/* 168 */     registerBlockWithStateMapper(Blocks.stonebrick, (IStateMapper)(new StateMap.Builder()).withName((IProperty)BlockStoneBrick.VARIANT).build());
/* 169 */     registerBlockWithStateMapper(Blocks.dispenser, (IStateMapper)(new StateMap.Builder()).ignore(new IProperty[] { (IProperty)BlockDispenser.TRIGGERED }).build());
/* 170 */     registerBlockWithStateMapper(Blocks.dropper, (IStateMapper)(new StateMap.Builder()).ignore(new IProperty[] { (IProperty)BlockDropper.TRIGGERED }).build());
/* 171 */     registerBlockWithStateMapper(Blocks.log, (IStateMapper)(new StateMap.Builder()).withName((IProperty)BlockOldLog.VARIANT).withSuffix("_log").build());
/* 172 */     registerBlockWithStateMapper(Blocks.log2, (IStateMapper)(new StateMap.Builder()).withName((IProperty)BlockNewLog.VARIANT).withSuffix("_log").build());
/* 173 */     registerBlockWithStateMapper(Blocks.planks, (IStateMapper)(new StateMap.Builder()).withName((IProperty)BlockPlanks.VARIANT).withSuffix("_planks").build());
/* 174 */     registerBlockWithStateMapper(Blocks.sapling, (IStateMapper)(new StateMap.Builder()).withName((IProperty)BlockSapling.TYPE).withSuffix("_sapling").build());
/* 175 */     registerBlockWithStateMapper((Block)Blocks.sand, (IStateMapper)(new StateMap.Builder()).withName((IProperty)BlockSand.VARIANT).build());
/* 176 */     registerBlockWithStateMapper((Block)Blocks.hopper, (IStateMapper)(new StateMap.Builder()).ignore(new IProperty[] { (IProperty)BlockHopper.ENABLED }).build());
/* 177 */     registerBlockWithStateMapper(Blocks.flower_pot, (IStateMapper)(new StateMap.Builder()).ignore(new IProperty[] { (IProperty)BlockFlowerPot.LEGACY_DATA }).build());
/* 178 */     registerBlockWithStateMapper(Blocks.quartz_block, (IStateMapper)new StateMapperBase()
/*     */         {
/*     */           protected ModelResourceLocation getModelResourceLocation(IBlockState state)
/*     */           {
/* 182 */             BlockQuartz.EnumType blockquartz$enumtype = (BlockQuartz.EnumType)state.getValue((IProperty)BlockQuartz.VARIANT);
/*     */             
/* 184 */             switch (blockquartz$enumtype) {
/*     */ 
/*     */               
/*     */               default:
/* 188 */                 return new ModelResourceLocation("quartz_block", "normal");
/*     */               
/*     */               case CHISELED:
/* 191 */                 return new ModelResourceLocation("chiseled_quartz_block", "normal");
/*     */               
/*     */               case LINES_Y:
/* 194 */                 return new ModelResourceLocation("quartz_column", "axis=y");
/*     */               
/*     */               case LINES_X:
/* 197 */                 return new ModelResourceLocation("quartz_column", "axis=x");
/*     */               case LINES_Z:
/*     */                 break;
/* 200 */             }  return new ModelResourceLocation("quartz_column", "axis=z");
/*     */           }
/*     */         });
/*     */     
/* 204 */     registerBlockWithStateMapper((Block)Blocks.deadbush, (IStateMapper)new StateMapperBase()
/*     */         {
/*     */           protected ModelResourceLocation getModelResourceLocation(IBlockState state)
/*     */           {
/* 208 */             return new ModelResourceLocation("dead_bush", "normal");
/*     */           }
/*     */         });
/* 211 */     registerBlockWithStateMapper(Blocks.pumpkin_stem, (IStateMapper)new StateMapperBase()
/*     */         {
/*     */           protected ModelResourceLocation getModelResourceLocation(IBlockState state)
/*     */           {
/* 215 */             Map<IProperty, Comparable> map = Maps.newLinkedHashMap((Map)state.getProperties());
/*     */             
/* 217 */             if (state.getValue((IProperty)BlockStem.FACING) != EnumFacing.UP)
/*     */             {
/* 219 */               map.remove(BlockStem.AGE);
/*     */             }
/*     */             
/* 222 */             return new ModelResourceLocation((ResourceLocation)Block.blockRegistry.getNameForObject(state.getBlock()), getPropertyString(map));
/*     */           }
/*     */         });
/* 225 */     registerBlockWithStateMapper(Blocks.melon_stem, (IStateMapper)new StateMapperBase()
/*     */         {
/*     */           protected ModelResourceLocation getModelResourceLocation(IBlockState state)
/*     */           {
/* 229 */             Map<IProperty, Comparable> map = Maps.newLinkedHashMap((Map)state.getProperties());
/*     */             
/* 231 */             if (state.getValue((IProperty)BlockStem.FACING) != EnumFacing.UP)
/*     */             {
/* 233 */               map.remove(BlockStem.AGE);
/*     */             }
/*     */             
/* 236 */             return new ModelResourceLocation((ResourceLocation)Block.blockRegistry.getNameForObject(state.getBlock()), getPropertyString(map));
/*     */           }
/*     */         });
/* 239 */     registerBlockWithStateMapper(Blocks.dirt, (IStateMapper)new StateMapperBase()
/*     */         {
/*     */           protected ModelResourceLocation getModelResourceLocation(IBlockState state)
/*     */           {
/* 243 */             Map<IProperty, Comparable> map = Maps.newLinkedHashMap((Map)state.getProperties());
/* 244 */             String s = BlockDirt.VARIANT.getName((Enum)map.remove(BlockDirt.VARIANT));
/*     */             
/* 246 */             if (BlockDirt.DirtType.PODZOL != state.getValue((IProperty)BlockDirt.VARIANT))
/*     */             {
/* 248 */               map.remove(BlockDirt.SNOWY);
/*     */             }
/*     */             
/* 251 */             return new ModelResourceLocation(s, getPropertyString(map));
/*     */           }
/*     */         });
/* 254 */     registerBlockWithStateMapper((Block)Blocks.double_stone_slab, (IStateMapper)new StateMapperBase()
/*     */         {
/*     */           protected ModelResourceLocation getModelResourceLocation(IBlockState state)
/*     */           {
/* 258 */             Map<IProperty, Comparable> map = Maps.newLinkedHashMap((Map)state.getProperties());
/* 259 */             String s = BlockStoneSlab.VARIANT.getName((Enum)map.remove(BlockStoneSlab.VARIANT));
/* 260 */             map.remove(BlockStoneSlab.SEAMLESS);
/* 261 */             String s1 = ((Boolean)state.getValue((IProperty)BlockStoneSlab.SEAMLESS)).booleanValue() ? "all" : "normal";
/* 262 */             return new ModelResourceLocation(s + "_double_slab", s1);
/*     */           }
/*     */         });
/* 265 */     registerBlockWithStateMapper((Block)Blocks.double_stone_slab2, (IStateMapper)new StateMapperBase()
/*     */         {
/*     */           protected ModelResourceLocation getModelResourceLocation(IBlockState state)
/*     */           {
/* 269 */             Map<IProperty, Comparable> map = Maps.newLinkedHashMap((Map)state.getProperties());
/* 270 */             String s = BlockStoneSlabNew.VARIANT.getName((Enum)map.remove(BlockStoneSlabNew.VARIANT));
/* 271 */             map.remove(BlockStoneSlab.SEAMLESS);
/* 272 */             String s1 = ((Boolean)state.getValue((IProperty)BlockStoneSlabNew.SEAMLESS)).booleanValue() ? "all" : "normal";
/* 273 */             return new ModelResourceLocation(s + "_double_slab", s1);
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\BlockModelShapes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */