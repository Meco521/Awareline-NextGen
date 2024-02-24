/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockTripWire
/*     */   extends Block
/*     */ {
/*  25 */   public static final PropertyBool POWERED = PropertyBool.create("powered");
/*  26 */   public static final PropertyBool SUSPENDED = PropertyBool.create("suspended");
/*  27 */   public static final PropertyBool ATTACHED = PropertyBool.create("attached");
/*  28 */   public static final PropertyBool DISARMED = PropertyBool.create("disarmed");
/*  29 */   public static final PropertyBool NORTH = PropertyBool.create("north");
/*  30 */   public static final PropertyBool EAST = PropertyBool.create("east");
/*  31 */   public static final PropertyBool SOUTH = PropertyBool.create("south");
/*  32 */   public static final PropertyBool WEST = PropertyBool.create("west");
/*     */ 
/*     */   
/*     */   public BlockTripWire() {
/*  36 */     super(Material.circuits);
/*  37 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)POWERED, Boolean.valueOf(false)).withProperty((IProperty)SUSPENDED, Boolean.valueOf(false)).withProperty((IProperty)ATTACHED, Boolean.valueOf(false)).withProperty((IProperty)DISARMED, Boolean.valueOf(false)).withProperty((IProperty)NORTH, Boolean.valueOf(false)).withProperty((IProperty)EAST, Boolean.valueOf(false)).withProperty((IProperty)SOUTH, Boolean.valueOf(false)).withProperty((IProperty)WEST, Boolean.valueOf(false)));
/*  38 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.15625F, 1.0F);
/*  39 */     setTickRandomly(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
/*  48 */     return state.withProperty((IProperty)NORTH, Boolean.valueOf(isConnectedTo(worldIn, pos, state, EnumFacing.NORTH))).withProperty((IProperty)EAST, Boolean.valueOf(isConnectedTo(worldIn, pos, state, EnumFacing.EAST))).withProperty((IProperty)SOUTH, Boolean.valueOf(isConnectedTo(worldIn, pos, state, EnumFacing.SOUTH))).withProperty((IProperty)WEST, Boolean.valueOf(isConnectedTo(worldIn, pos, state, EnumFacing.WEST)));
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
/*  53 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/*  61 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube() {
/*  66 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumWorldBlockLayer getBlockLayer() {
/*  71 */     return EnumWorldBlockLayer.TRANSLUCENT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/*  79 */     return Items.string;
/*     */   }
/*     */ 
/*     */   
/*     */   public Item getItem(World worldIn, BlockPos pos) {
/*  84 */     return Items.string;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/*  92 */     boolean flag = ((Boolean)state.getValue((IProperty)SUSPENDED)).booleanValue();
/*  93 */     boolean flag1 = !World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, pos.down());
/*     */     
/*  95 */     if (flag != flag1) {
/*     */       
/*  97 */       dropBlockAsItem(worldIn, pos, state, 0);
/*  98 */       worldIn.setBlockToAir(pos);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
/* 104 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/* 105 */     boolean flag = ((Boolean)iblockstate.getValue((IProperty)ATTACHED)).booleanValue();
/* 106 */     boolean flag1 = ((Boolean)iblockstate.getValue((IProperty)SUSPENDED)).booleanValue();
/*     */     
/* 108 */     if (!flag1) {
/*     */       
/* 110 */       setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.09375F, 1.0F);
/*     */     }
/* 112 */     else if (!flag) {
/*     */       
/* 114 */       setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
/*     */     }
/*     */     else {
/*     */       
/* 118 */       setBlockBounds(0.0F, 0.0625F, 0.0F, 1.0F, 0.15625F, 1.0F);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
/* 124 */     state = state.withProperty((IProperty)SUSPENDED, Boolean.valueOf(!World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, pos.down())));
/* 125 */     worldIn.setBlockState(pos, state, 3);
/* 126 */     notifyHook(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */   
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
/* 131 */     notifyHook(worldIn, pos, state.withProperty((IProperty)POWERED, Boolean.valueOf(true)));
/*     */   }
/*     */ 
/*     */   
/*     */   public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
/* 136 */     if (!worldIn.isRemote)
/*     */     {
/* 138 */       if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() == Items.shears)
/*     */       {
/* 140 */         worldIn.setBlockState(pos, state.withProperty((IProperty)DISARMED, Boolean.valueOf(true)), 4);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void notifyHook(World worldIn, BlockPos pos, IBlockState state) {
/* 147 */     for (EnumFacing enumfacing : new EnumFacing[] { EnumFacing.SOUTH, EnumFacing.WEST }) {
/*     */       
/* 149 */       for (int i = 1; i < 42; i++) {
/*     */         
/* 151 */         BlockPos blockpos = pos.offset(enumfacing, i);
/* 152 */         IBlockState iblockstate = worldIn.getBlockState(blockpos);
/*     */         
/* 154 */         if (iblockstate.getBlock() == Blocks.tripwire_hook) {
/*     */           
/* 156 */           if (iblockstate.getValue((IProperty)BlockTripWireHook.FACING) == enumfacing.getOpposite())
/*     */           {
/* 158 */             Blocks.tripwire_hook.func_176260_a(worldIn, blockpos, iblockstate, false, true, i, state);
/*     */           }
/*     */           
/*     */           break;
/*     */         } 
/*     */         
/* 164 */         if (iblockstate.getBlock() != Blocks.tripwire) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
/* 177 */     if (!worldIn.isRemote)
/*     */     {
/* 179 */       if (!((Boolean)state.getValue((IProperty)POWERED)).booleanValue())
/*     */       {
/* 181 */         updateState(worldIn, pos);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/* 195 */     if (!worldIn.isRemote)
/*     */     {
/* 197 */       if (((Boolean)worldIn.getBlockState(pos).getValue((IProperty)POWERED)).booleanValue())
/*     */       {
/* 199 */         updateState(worldIn, pos);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateState(World worldIn, BlockPos pos) {
/* 206 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/* 207 */     boolean flag = ((Boolean)iblockstate.getValue((IProperty)POWERED)).booleanValue();
/* 208 */     boolean flag1 = false;
/* 209 */     List<? extends Entity> list = worldIn.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(pos.getX() + this.minX, pos.getY() + this.minY, pos.getZ() + this.minZ, pos.getX() + this.maxX, pos.getY() + this.maxY, pos.getZ() + this.maxZ));
/*     */     
/* 211 */     if (!list.isEmpty())
/*     */     {
/* 213 */       for (Entity entity : list) {
/*     */         
/* 215 */         if (!entity.doesEntityNotTriggerPressurePlate()) {
/*     */           
/* 217 */           flag1 = true;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     }
/* 223 */     if (flag1 != flag) {
/*     */       
/* 225 */       iblockstate = iblockstate.withProperty((IProperty)POWERED, Boolean.valueOf(flag1));
/* 226 */       worldIn.setBlockState(pos, iblockstate, 3);
/* 227 */       notifyHook(worldIn, pos, iblockstate);
/*     */     } 
/*     */     
/* 230 */     if (flag1)
/*     */     {
/* 232 */       worldIn.scheduleUpdate(pos, this, tickRate(worldIn));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isConnectedTo(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing direction) {
/* 238 */     BlockPos blockpos = pos.offset(direction);
/* 239 */     IBlockState iblockstate = worldIn.getBlockState(blockpos);
/* 240 */     Block block = iblockstate.getBlock();
/*     */     
/* 242 */     if (block == Blocks.tripwire_hook) {
/*     */       
/* 244 */       EnumFacing enumfacing = direction.getOpposite();
/* 245 */       return (iblockstate.getValue((IProperty)BlockTripWireHook.FACING) == enumfacing);
/*     */     } 
/* 247 */     if (block == Blocks.tripwire) {
/*     */       
/* 249 */       boolean flag = ((Boolean)state.getValue((IProperty)SUSPENDED)).booleanValue();
/* 250 */       boolean flag1 = ((Boolean)iblockstate.getValue((IProperty)SUSPENDED)).booleanValue();
/* 251 */       return (flag == flag1);
/*     */     } 
/*     */ 
/*     */     
/* 255 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 264 */     return getDefaultState().withProperty((IProperty)POWERED, Boolean.valueOf(((meta & 0x1) > 0))).withProperty((IProperty)SUSPENDED, Boolean.valueOf(((meta & 0x2) > 0))).withProperty((IProperty)ATTACHED, Boolean.valueOf(((meta & 0x4) > 0))).withProperty((IProperty)DISARMED, Boolean.valueOf(((meta & 0x8) > 0)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 272 */     int i = 0;
/*     */     
/* 274 */     if (((Boolean)state.getValue((IProperty)POWERED)).booleanValue())
/*     */     {
/* 276 */       i |= 0x1;
/*     */     }
/*     */     
/* 279 */     if (((Boolean)state.getValue((IProperty)SUSPENDED)).booleanValue())
/*     */     {
/* 281 */       i |= 0x2;
/*     */     }
/*     */     
/* 284 */     if (((Boolean)state.getValue((IProperty)ATTACHED)).booleanValue())
/*     */     {
/* 286 */       i |= 0x4;
/*     */     }
/*     */     
/* 289 */     if (((Boolean)state.getValue((IProperty)DISARMED)).booleanValue())
/*     */     {
/* 291 */       i |= 0x8;
/*     */     }
/*     */     
/* 294 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockState createBlockState() {
/* 299 */     return new BlockState(this, new IProperty[] { (IProperty)POWERED, (IProperty)SUSPENDED, (IProperty)ATTACHED, (IProperty)DISARMED, (IProperty)NORTH, (IProperty)EAST, (IProperty)WEST, (IProperty)SOUTH });
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\block\BlockTripWire.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */