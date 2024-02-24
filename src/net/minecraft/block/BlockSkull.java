/*     */ package net.minecraft.block;
/*     */ import com.google.common.base.Predicate;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.properties.PropertyDirection;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.BlockWorldState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.block.state.pattern.BlockPattern;
/*     */ import net.minecraft.block.state.pattern.BlockStateHelper;
/*     */ import net.minecraft.block.state.pattern.FactoryBlockPattern;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.boss.EntityWither;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTUtil;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntitySkull;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.world.EnumDifficulty;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockSkull extends BlockContainer {
/*  35 */   public static final PropertyDirection FACING = PropertyDirection.create("facing");
/*  36 */   public static final PropertyBool NODROP = PropertyBool.create("nodrop");
/*  37 */   private static final Predicate<BlockWorldState> IS_WITHER_SKELETON = new Predicate<BlockWorldState>()
/*     */     {
/*     */       public boolean apply(BlockWorldState p_apply_1_)
/*     */       {
/*  41 */         return (p_apply_1_.getBlockState() != null && p_apply_1_.getBlockState().getBlock() == Blocks.skull && p_apply_1_.getTileEntity() instanceof TileEntitySkull && ((TileEntitySkull)p_apply_1_.getTileEntity()).getSkullType() == 1);
/*     */       }
/*     */     };
/*     */   
/*     */   private BlockPattern witherBasePattern;
/*     */   private BlockPattern witherPattern;
/*     */   
/*     */   protected BlockSkull() {
/*  49 */     super(Material.circuits);
/*  50 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)FACING, (Comparable)EnumFacing.NORTH).withProperty((IProperty)NODROP, Boolean.valueOf(false)));
/*  51 */     setBlockBounds(0.25F, 0.0F, 0.25F, 0.75F, 0.5F, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLocalizedName() {
/*  59 */     return StatCollector.translateToLocal("tile.skull.skeleton.name");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/*  67 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube() {
/*  72 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
/*  77 */     switch ((EnumFacing)worldIn.getBlockState(pos).getValue((IProperty)FACING)) {
/*     */ 
/*     */       
/*     */       default:
/*  81 */         setBlockBounds(0.25F, 0.0F, 0.25F, 0.75F, 0.5F, 0.75F);
/*     */         return;
/*     */       
/*     */       case NORTH:
/*  85 */         setBlockBounds(0.25F, 0.25F, 0.5F, 0.75F, 0.75F, 1.0F);
/*     */         return;
/*     */       
/*     */       case SOUTH:
/*  89 */         setBlockBounds(0.25F, 0.25F, 0.0F, 0.75F, 0.75F, 0.5F);
/*     */         return;
/*     */       
/*     */       case WEST:
/*  93 */         setBlockBounds(0.5F, 0.25F, 0.25F, 1.0F, 0.75F, 0.75F); return;
/*     */       case EAST:
/*     */         break;
/*     */     } 
/*  97 */     setBlockBounds(0.0F, 0.25F, 0.25F, 0.5F, 0.75F, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
/* 103 */     setBlockBoundsBasedOnState((IBlockAccess)worldIn, pos);
/* 104 */     return super.getCollisionBoundingBox(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/* 113 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)placer.getHorizontalFacing()).withProperty((IProperty)NODROP, Boolean.valueOf(false));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TileEntity createNewTileEntity(World worldIn, int meta) {
/* 121 */     return (TileEntity)new TileEntitySkull();
/*     */   }
/*     */ 
/*     */   
/*     */   public Item getItem(World worldIn, BlockPos pos) {
/* 126 */     return Items.skull;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDamageValue(World worldIn, BlockPos pos) {
/* 134 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/* 135 */     return (tileentity instanceof TileEntitySkull) ? ((TileEntitySkull)tileentity).getSkullType() : super.getDamageValue(worldIn, pos);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
/* 147 */     if (player.capabilities.isCreativeMode) {
/*     */       
/* 149 */       state = state.withProperty((IProperty)NODROP, Boolean.valueOf(true));
/* 150 */       worldIn.setBlockState(pos, state, 4);
/*     */     } 
/*     */     
/* 153 */     super.onBlockHarvested(worldIn, pos, state, player);
/*     */   }
/*     */ 
/*     */   
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
/* 158 */     if (!worldIn.isRemote) {
/*     */       
/* 160 */       if (!((Boolean)state.getValue((IProperty)NODROP)).booleanValue()) {
/*     */         
/* 162 */         TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */         
/* 164 */         if (tileentity instanceof TileEntitySkull) {
/*     */           
/* 166 */           TileEntitySkull tileentityskull = (TileEntitySkull)tileentity;
/* 167 */           ItemStack itemstack = new ItemStack(Items.skull, 1, getDamageValue(worldIn, pos));
/*     */           
/* 169 */           if (tileentityskull.getSkullType() == 3 && tileentityskull.getPlayerProfile() != null) {
/*     */             
/* 171 */             itemstack.setTagCompound(new NBTTagCompound());
/* 172 */             NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 173 */             NBTUtil.writeGameProfile(nbttagcompound, tileentityskull.getPlayerProfile());
/* 174 */             itemstack.getTagCompound().setTag("SkullOwner", (NBTBase)nbttagcompound);
/*     */           } 
/*     */           
/* 177 */           spawnAsEntity(worldIn, pos, itemstack);
/*     */         } 
/*     */       } 
/*     */       
/* 181 */       super.breakBlock(worldIn, pos, state);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/* 190 */     return Items.skull;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canDispenserPlace(World worldIn, BlockPos pos, ItemStack stack) {
/* 195 */     return (stack.getMetadata() == 1 && pos.getY() >= 2 && worldIn.getDifficulty() != EnumDifficulty.PEACEFUL && !worldIn.isRemote) ? ((getWitherBasePattern().match(worldIn, pos) != null)) : false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void checkWitherSpawn(World worldIn, BlockPos pos, TileEntitySkull te) {
/* 200 */     if (te.getSkullType() == 1 && pos.getY() >= 2 && worldIn.getDifficulty() != EnumDifficulty.PEACEFUL && !worldIn.isRemote) {
/*     */       
/* 202 */       BlockPattern blockpattern = getWitherPattern();
/* 203 */       BlockPattern.PatternHelper blockpattern$patternhelper = blockpattern.match(worldIn, pos);
/*     */       
/* 205 */       if (blockpattern$patternhelper != null) {
/*     */         
/* 207 */         for (int i = 0; i < 3; i++) {
/*     */           
/* 209 */           BlockWorldState blockworldstate = blockpattern$patternhelper.translateOffset(i, 0, 0);
/* 210 */           worldIn.setBlockState(blockworldstate.getPos(), blockworldstate.getBlockState().withProperty((IProperty)NODROP, Boolean.valueOf(true)), 2);
/*     */         } 
/*     */         
/* 213 */         for (int j = 0; j < blockpattern.getPalmLength(); j++) {
/*     */           
/* 215 */           for (int k = 0; k < blockpattern.getThumbLength(); k++) {
/*     */             
/* 217 */             BlockWorldState blockworldstate1 = blockpattern$patternhelper.translateOffset(j, k, 0);
/* 218 */             worldIn.setBlockState(blockworldstate1.getPos(), Blocks.air.getDefaultState(), 2);
/*     */           } 
/*     */         } 
/*     */         
/* 222 */         BlockPos blockpos = blockpattern$patternhelper.translateOffset(1, 0, 0).getPos();
/* 223 */         EntityWither entitywither = new EntityWither(worldIn);
/* 224 */         BlockPos blockpos1 = blockpattern$patternhelper.translateOffset(1, 2, 0).getPos();
/* 225 */         entitywither.setLocationAndAngles(blockpos1.getX() + 0.5D, blockpos1.getY() + 0.55D, blockpos1.getZ() + 0.5D, (blockpattern$patternhelper.getFinger().getAxis() == EnumFacing.Axis.X) ? 0.0F : 90.0F, 0.0F);
/* 226 */         entitywither.renderYawOffset = (blockpattern$patternhelper.getFinger().getAxis() == EnumFacing.Axis.X) ? 0.0F : 90.0F;
/* 227 */         entitywither.func_82206_m();
/*     */         
/* 229 */         for (EntityPlayer entityplayer : worldIn.getEntitiesWithinAABB(EntityPlayer.class, entitywither.getEntityBoundingBox().expand(50.0D, 50.0D, 50.0D)))
/*     */         {
/* 231 */           entityplayer.triggerAchievement((StatBase)AchievementList.spawnWither);
/*     */         }
/*     */         
/* 234 */         worldIn.spawnEntityInWorld((Entity)entitywither);
/*     */         
/* 236 */         for (int l = 0; l < 120; l++)
/*     */         {
/* 238 */           worldIn.spawnParticle(EnumParticleTypes.SNOWBALL, blockpos.getX() + worldIn.rand.nextDouble(), (blockpos.getY() - 2) + worldIn.rand.nextDouble() * 3.9D, blockpos.getZ() + worldIn.rand.nextDouble(), 0.0D, 0.0D, 0.0D, new int[0]);
/*     */         }
/*     */         
/* 241 */         for (int i1 = 0; i1 < blockpattern.getPalmLength(); i1++) {
/*     */           
/* 243 */           for (int j1 = 0; j1 < blockpattern.getThumbLength(); j1++) {
/*     */             
/* 245 */             BlockWorldState blockworldstate2 = blockpattern$patternhelper.translateOffset(i1, j1, 0);
/* 246 */             worldIn.notifyNeighborsRespectDebug(blockworldstate2.getPos(), Blocks.air);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 258 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)EnumFacing.getFront(meta & 0x7)).withProperty((IProperty)NODROP, Boolean.valueOf(((meta & 0x8) > 0)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 266 */     int i = 0;
/* 267 */     i |= ((EnumFacing)state.getValue((IProperty)FACING)).getIndex();
/*     */     
/* 269 */     if (((Boolean)state.getValue((IProperty)NODROP)).booleanValue())
/*     */     {
/* 271 */       i |= 0x8;
/*     */     }
/*     */     
/* 274 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockState createBlockState() {
/* 279 */     return new BlockState(this, new IProperty[] { (IProperty)FACING, (IProperty)NODROP });
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockPattern getWitherBasePattern() {
/* 284 */     if (this.witherBasePattern == null)
/*     */     {
/* 286 */       this.witherBasePattern = FactoryBlockPattern.start().aisle(new String[] { "   ", "###", "~#~" }).where('#', BlockWorldState.hasState((Predicate)BlockStateHelper.forBlock(Blocks.soul_sand))).where('~', BlockWorldState.hasState((Predicate)BlockStateHelper.forBlock(Blocks.air))).build();
/*     */     }
/*     */     
/* 289 */     return this.witherBasePattern;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockPattern getWitherPattern() {
/* 294 */     if (this.witherPattern == null)
/*     */     {
/* 296 */       this.witherPattern = FactoryBlockPattern.start().aisle(new String[] { "^^^", "###", "~#~" }).where('#', BlockWorldState.hasState((Predicate)BlockStateHelper.forBlock(Blocks.soul_sand))).where('^', IS_WITHER_SKELETON).where('~', BlockWorldState.hasState((Predicate)BlockStateHelper.forBlock(Blocks.air))).build();
/*     */     }
/*     */     
/* 299 */     return this.witherPattern;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\block\BlockSkull.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */