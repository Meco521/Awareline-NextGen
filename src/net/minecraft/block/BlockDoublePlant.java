/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.IStringSerializable;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.biome.BiomeColorHelper;
/*     */ 
/*     */ public class BlockDoublePlant
/*     */   extends BlockBush
/*     */   implements IGrowable {
/*  29 */   public static final PropertyEnum<EnumPlantType> VARIANT = PropertyEnum.create("variant", EnumPlantType.class);
/*  30 */   public static final PropertyEnum<EnumBlockHalf> HALF = PropertyEnum.create("half", EnumBlockHalf.class);
/*  31 */   public static final PropertyEnum<EnumFacing> FACING = (PropertyEnum<EnumFacing>)BlockDirectional.FACING;
/*     */ 
/*     */   
/*     */   public BlockDoublePlant() {
/*  35 */     super(Material.vine);
/*  36 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)VARIANT, EnumPlantType.SUNFLOWER).withProperty((IProperty)HALF, EnumBlockHalf.LOWER).withProperty((IProperty)FACING, (Comparable)EnumFacing.NORTH));
/*  37 */     setHardness(0.0F);
/*  38 */     setStepSound(soundTypeGrass);
/*  39 */     setUnlocalizedName("doublePlant");
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
/*  44 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumPlantType getVariant(IBlockAccess worldIn, BlockPos pos) {
/*  49 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/*     */     
/*  51 */     if (iblockstate.getBlock() == this) {
/*     */       
/*  53 */       iblockstate = getActualState(iblockstate, worldIn, pos);
/*  54 */       return (EnumPlantType)iblockstate.getValue((IProperty)VARIANT);
/*     */     } 
/*     */ 
/*     */     
/*  58 */     return EnumPlantType.FERN;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/*  64 */     return (super.canPlaceBlockAt(worldIn, pos) && worldIn.isAirBlock(pos.up()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isReplaceable(World worldIn, BlockPos pos) {
/*  72 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/*     */     
/*  74 */     if (iblockstate.getBlock() != this)
/*     */     {
/*  76 */       return true;
/*     */     }
/*     */ 
/*     */     
/*  80 */     EnumPlantType blockdoubleplant$enumplanttype = (EnumPlantType)getActualState(iblockstate, (IBlockAccess)worldIn, pos).getValue((IProperty)VARIANT);
/*  81 */     return (blockdoubleplant$enumplanttype == EnumPlantType.FERN || blockdoubleplant$enumplanttype == EnumPlantType.GRASS);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void checkAndDropBlock(World worldIn, BlockPos pos, IBlockState state) {
/*  87 */     if (!canBlockStay(worldIn, pos, state)) {
/*     */       
/*  89 */       boolean flag = (state.getValue((IProperty)HALF) == EnumBlockHalf.UPPER);
/*  90 */       BlockPos blockpos = flag ? pos : pos.up();
/*  91 */       BlockPos blockpos1 = flag ? pos.down() : pos;
/*  92 */       Block block = flag ? this : worldIn.getBlockState(blockpos).getBlock();
/*  93 */       Block block1 = flag ? worldIn.getBlockState(blockpos1).getBlock() : this;
/*     */       
/*  95 */       if (block == this)
/*     */       {
/*  97 */         worldIn.setBlockState(blockpos, Blocks.air.getDefaultState(), 2);
/*     */       }
/*     */       
/* 100 */       if (block1 == this) {
/*     */         
/* 102 */         worldIn.setBlockState(blockpos1, Blocks.air.getDefaultState(), 3);
/*     */         
/* 104 */         if (!flag)
/*     */         {
/* 106 */           dropBlockAsItem(worldIn, blockpos1, state, 0);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
/* 114 */     if (state.getValue((IProperty)HALF) == EnumBlockHalf.UPPER)
/*     */     {
/* 116 */       return (worldIn.getBlockState(pos.down()).getBlock() == this);
/*     */     }
/*     */ 
/*     */     
/* 120 */     IBlockState iblockstate = worldIn.getBlockState(pos.up());
/* 121 */     return (iblockstate.getBlock() == this && super.canBlockStay(worldIn, pos, iblockstate));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/* 130 */     if (state.getValue((IProperty)HALF) == EnumBlockHalf.UPPER)
/*     */     {
/* 132 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 136 */     EnumPlantType blockdoubleplant$enumplanttype = (EnumPlantType)state.getValue((IProperty)VARIANT);
/* 137 */     return (blockdoubleplant$enumplanttype == EnumPlantType.FERN) ? null : ((blockdoubleplant$enumplanttype == EnumPlantType.GRASS) ? ((rand.nextInt(8) == 0) ? Items.wheat_seeds : null) : Item.getItemFromBlock(this));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int damageDropped(IBlockState state) {
/* 147 */     return (state.getValue((IProperty)HALF) != EnumBlockHalf.UPPER && state.getValue((IProperty)VARIANT) != EnumPlantType.GRASS) ? ((EnumPlantType)state.getValue((IProperty)VARIANT)).getMeta() : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int colorMultiplier(IBlockAccess worldIn, BlockPos pos, int renderPass) {
/* 152 */     EnumPlantType blockdoubleplant$enumplanttype = getVariant(worldIn, pos);
/* 153 */     return (blockdoubleplant$enumplanttype != EnumPlantType.GRASS && blockdoubleplant$enumplanttype != EnumPlantType.FERN) ? 16777215 : BiomeColorHelper.getGrassColorAtPos(worldIn, pos);
/*     */   }
/*     */ 
/*     */   
/*     */   public void placeAt(World worldIn, BlockPos lowerPos, EnumPlantType variant, int flags) {
/* 158 */     worldIn.setBlockState(lowerPos, getDefaultState().withProperty((IProperty)HALF, EnumBlockHalf.LOWER).withProperty((IProperty)VARIANT, variant), flags);
/* 159 */     worldIn.setBlockState(lowerPos.up(), getDefaultState().withProperty((IProperty)HALF, EnumBlockHalf.UPPER), flags);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
/* 167 */     worldIn.setBlockState(pos.up(), getDefaultState().withProperty((IProperty)HALF, EnumBlockHalf.UPPER), 2);
/*     */   }
/*     */ 
/*     */   
/*     */   public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te) {
/* 172 */     if (worldIn.isRemote || player.getCurrentEquippedItem() == null || player.getCurrentEquippedItem().getItem() != Items.shears || state.getValue((IProperty)HALF) != EnumBlockHalf.LOWER || !onHarvest(worldIn, pos, state, player))
/*     */     {
/* 174 */       super.harvestBlock(worldIn, player, pos, state, te);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
/* 180 */     if (state.getValue((IProperty)HALF) == EnumBlockHalf.UPPER) {
/*     */       
/* 182 */       if (worldIn.getBlockState(pos.down()).getBlock() == this)
/*     */       {
/* 184 */         if (!player.capabilities.isCreativeMode) {
/*     */           
/* 186 */           IBlockState iblockstate = worldIn.getBlockState(pos.down());
/* 187 */           EnumPlantType blockdoubleplant$enumplanttype = (EnumPlantType)iblockstate.getValue((IProperty)VARIANT);
/*     */           
/* 189 */           if (blockdoubleplant$enumplanttype != EnumPlantType.FERN && blockdoubleplant$enumplanttype != EnumPlantType.GRASS) {
/*     */             
/* 191 */             worldIn.destroyBlock(pos.down(), true);
/*     */           }
/* 193 */           else if (!worldIn.isRemote) {
/*     */             
/* 195 */             if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() == Items.shears)
/*     */             {
/* 197 */               onHarvest(worldIn, pos, iblockstate, player);
/* 198 */               worldIn.setBlockToAir(pos.down());
/*     */             }
/*     */             else
/*     */             {
/* 202 */               worldIn.destroyBlock(pos.down(), true);
/*     */             }
/*     */           
/*     */           } else {
/*     */             
/* 207 */             worldIn.setBlockToAir(pos.down());
/*     */           }
/*     */         
/*     */         } else {
/*     */           
/* 212 */           worldIn.setBlockToAir(pos.down());
/*     */         }
/*     */       
/*     */       }
/* 216 */     } else if (player.capabilities.isCreativeMode && worldIn.getBlockState(pos.up()).getBlock() == this) {
/*     */       
/* 218 */       worldIn.setBlockState(pos.up(), Blocks.air.getDefaultState(), 2);
/*     */     } 
/*     */     
/* 221 */     super.onBlockHarvested(worldIn, pos, state, player);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean onHarvest(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
/* 226 */     EnumPlantType blockdoubleplant$enumplanttype = (EnumPlantType)state.getValue((IProperty)VARIANT);
/*     */     
/* 228 */     if (blockdoubleplant$enumplanttype != EnumPlantType.FERN && blockdoubleplant$enumplanttype != EnumPlantType.GRASS)
/*     */     {
/* 230 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 234 */     player.triggerAchievement(StatList.mineBlockStatArray[Block.getIdFromBlock(this)]);
/* 235 */     int i = ((blockdoubleplant$enumplanttype == EnumPlantType.GRASS) ? BlockTallGrass.EnumType.GRASS : BlockTallGrass.EnumType.FERN).getMeta();
/* 236 */     spawnAsEntity(worldIn, pos, new ItemStack(Blocks.tallgrass, 2, i));
/* 237 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
/* 246 */     for (EnumPlantType blockdoubleplant$enumplanttype : EnumPlantType.values())
/*     */     {
/* 248 */       list.add(new ItemStack(itemIn, 1, blockdoubleplant$enumplanttype.getMeta()));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDamageValue(World worldIn, BlockPos pos) {
/* 257 */     return getVariant((IBlockAccess)worldIn, pos).getMeta();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
/* 265 */     EnumPlantType blockdoubleplant$enumplanttype = getVariant((IBlockAccess)worldIn, pos);
/* 266 */     return (blockdoubleplant$enumplanttype != EnumPlantType.GRASS && blockdoubleplant$enumplanttype != EnumPlantType.FERN);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
/* 271 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
/* 276 */     spawnAsEntity(worldIn, pos, new ItemStack(this, 1, getVariant((IBlockAccess)worldIn, pos).getMeta()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 284 */     return ((meta & 0x8) > 0) ? getDefaultState().withProperty((IProperty)HALF, EnumBlockHalf.UPPER) : getDefaultState().withProperty((IProperty)HALF, EnumBlockHalf.LOWER).withProperty((IProperty)VARIANT, EnumPlantType.byMetadata(meta & 0x7));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
/* 293 */     if (state.getValue((IProperty)HALF) == EnumBlockHalf.UPPER) {
/*     */       
/* 295 */       IBlockState iblockstate = worldIn.getBlockState(pos.down());
/*     */       
/* 297 */       if (iblockstate.getBlock() == this)
/*     */       {
/* 299 */         state = state.withProperty((IProperty)VARIANT, iblockstate.getValue((IProperty)VARIANT));
/*     */       }
/*     */     } 
/*     */     
/* 303 */     return state;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 311 */     return (state.getValue((IProperty)HALF) == EnumBlockHalf.UPPER) ? (0x8 | ((EnumFacing)state.getValue((IProperty)FACING)).getHorizontalIndex()) : ((EnumPlantType)state.getValue((IProperty)VARIANT)).getMeta();
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockState createBlockState() {
/* 316 */     return new BlockState(this, new IProperty[] { (IProperty)HALF, (IProperty)VARIANT, (IProperty)FACING });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Block.EnumOffsetType getOffsetType() {
/* 324 */     return Block.EnumOffsetType.XZ;
/*     */   }
/*     */   
/*     */   public enum EnumBlockHalf
/*     */     implements IStringSerializable {
/* 329 */     UPPER,
/* 330 */     LOWER;
/*     */ 
/*     */     
/*     */     public String toString() {
/* 334 */       return getName();
/*     */     }
/*     */ 
/*     */     
/*     */     public String getName() {
/* 339 */       return (this == UPPER) ? "upper" : "lower";
/*     */     }
/*     */   }
/*     */   
/*     */   public enum EnumPlantType
/*     */     implements IStringSerializable {
/* 345 */     SUNFLOWER(0, "sunflower"),
/* 346 */     SYRINGA(1, "syringa"),
/* 347 */     GRASS(2, "double_grass", "grass"),
/* 348 */     FERN(3, "double_fern", "fern"),
/* 349 */     ROSE(4, "double_rose", "rose"),
/* 350 */     PAEONIA(5, "paeonia");
/*     */     
/* 352 */     private static final EnumPlantType[] META_LOOKUP = new EnumPlantType[(values()).length];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final int meta;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final String name;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final String unlocalizedName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/* 400 */       for (EnumPlantType blockdoubleplant$enumplanttype : values())
/*     */       {
/* 402 */         META_LOOKUP[blockdoubleplant$enumplanttype.meta] = blockdoubleplant$enumplanttype;
/*     */       }
/*     */     }
/*     */     
/*     */     EnumPlantType(int meta, String name, String unlocalizedName) {
/*     */       this.meta = meta;
/*     */       this.name = name;
/*     */       this.unlocalizedName = unlocalizedName;
/*     */     }
/*     */     
/*     */     public int getMeta() {
/*     */       return this.meta;
/*     */     }
/*     */     
/*     */     public String toString() {
/*     */       return this.name;
/*     */     }
/*     */     
/*     */     public static EnumPlantType byMetadata(int meta) {
/*     */       if (meta < 0 || meta >= META_LOOKUP.length)
/*     */         meta = 0; 
/*     */       return META_LOOKUP[meta];
/*     */     }
/*     */     
/*     */     public String getName() {
/*     */       return this.name;
/*     */     }
/*     */     
/*     */     public String getUnlocalizedName() {
/*     */       return this.unlocalizedName;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\block\BlockDoublePlant.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */