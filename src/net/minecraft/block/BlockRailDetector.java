/*     */ package net.minecraft.block;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityMinecartCommandBlock;
/*     */ import net.minecraft.entity.item.EntityMinecart;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EntitySelectors;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockRailDetector
/*     */   extends BlockRailBase
/*     */ {
/*  26 */   public static final PropertyEnum<BlockRailBase.EnumRailDirection> SHAPE = PropertyEnum.create("shape", BlockRailBase.EnumRailDirection.class, new Predicate<BlockRailBase.EnumRailDirection>()
/*     */       {
/*     */         public boolean apply(BlockRailBase.EnumRailDirection p_apply_1_)
/*     */         {
/*  30 */           return (p_apply_1_ != BlockRailBase.EnumRailDirection.NORTH_EAST && p_apply_1_ != BlockRailBase.EnumRailDirection.NORTH_WEST && p_apply_1_ != BlockRailBase.EnumRailDirection.SOUTH_EAST && p_apply_1_ != BlockRailBase.EnumRailDirection.SOUTH_WEST);
/*     */         }
/*     */       });
/*  33 */   public static final PropertyBool POWERED = PropertyBool.create("powered");
/*     */ 
/*     */   
/*     */   public BlockRailDetector() {
/*  37 */     super(true);
/*  38 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)POWERED, Boolean.valueOf(false)).withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.NORTH_SOUTH));
/*  39 */     setTickRandomly(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int tickRate(World worldIn) {
/*  47 */     return 20;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canProvidePower() {
/*  55 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
/*  63 */     if (!worldIn.isRemote)
/*     */     {
/*  65 */       if (!((Boolean)state.getValue((IProperty)POWERED)).booleanValue())
/*     */       {
/*  67 */         updatePoweredState(worldIn, pos, state);
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
/*  81 */     if (!worldIn.isRemote && ((Boolean)state.getValue((IProperty)POWERED)).booleanValue())
/*     */     {
/*  83 */       updatePoweredState(worldIn, pos, state);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWeakPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side) {
/*  89 */     return ((Boolean)state.getValue((IProperty)POWERED)).booleanValue() ? 15 : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getStrongPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side) {
/*  94 */     return !((Boolean)state.getValue((IProperty)POWERED)).booleanValue() ? 0 : ((side == EnumFacing.UP) ? 15 : 0);
/*     */   }
/*     */ 
/*     */   
/*     */   private void updatePoweredState(World worldIn, BlockPos pos, IBlockState state) {
/*  99 */     boolean flag = ((Boolean)state.getValue((IProperty)POWERED)).booleanValue();
/* 100 */     boolean flag1 = false;
/* 101 */     List<EntityMinecart> list = findMinecarts(worldIn, pos, EntityMinecart.class, (Predicate<Entity>[])new Predicate[0]);
/*     */     
/* 103 */     if (!list.isEmpty())
/*     */     {
/* 105 */       flag1 = true;
/*     */     }
/*     */     
/* 108 */     if (flag1 && !flag) {
/*     */       
/* 110 */       worldIn.setBlockState(pos, state.withProperty((IProperty)POWERED, Boolean.valueOf(true)), 3);
/* 111 */       worldIn.notifyNeighborsOfStateChange(pos, this);
/* 112 */       worldIn.notifyNeighborsOfStateChange(pos.down(), this);
/* 113 */       worldIn.markBlockRangeForRenderUpdate(pos, pos);
/*     */     } 
/*     */     
/* 116 */     if (!flag1 && flag) {
/*     */       
/* 118 */       worldIn.setBlockState(pos, state.withProperty((IProperty)POWERED, Boolean.valueOf(false)), 3);
/* 119 */       worldIn.notifyNeighborsOfStateChange(pos, this);
/* 120 */       worldIn.notifyNeighborsOfStateChange(pos.down(), this);
/* 121 */       worldIn.markBlockRangeForRenderUpdate(pos, pos);
/*     */     } 
/*     */     
/* 124 */     if (flag1)
/*     */     {
/* 126 */       worldIn.scheduleUpdate(pos, this, tickRate(worldIn));
/*     */     }
/*     */     
/* 129 */     worldIn.updateComparatorOutputLevel(pos, this);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
/* 134 */     super.onBlockAdded(worldIn, pos, state);
/* 135 */     updatePoweredState(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */   
/*     */   public IProperty<BlockRailBase.EnumRailDirection> getShapeProperty() {
/* 140 */     return (IProperty<BlockRailBase.EnumRailDirection>)SHAPE;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasComparatorInputOverride() {
/* 145 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getComparatorInputOverride(World worldIn, BlockPos pos) {
/* 150 */     if (((Boolean)worldIn.getBlockState(pos).getValue((IProperty)POWERED)).booleanValue()) {
/*     */       
/* 152 */       List<EntityMinecartCommandBlock> list = findMinecarts(worldIn, pos, EntityMinecartCommandBlock.class, (Predicate<Entity>[])new Predicate[0]);
/*     */       
/* 154 */       if (!list.isEmpty())
/*     */       {
/* 156 */         return ((EntityMinecartCommandBlock)list.get(0)).getCommandBlockLogic().getSuccessCount();
/*     */       }
/*     */       
/* 159 */       List<EntityMinecart> list1 = findMinecarts(worldIn, pos, EntityMinecart.class, (Predicate<Entity>[])new Predicate[] { EntitySelectors.selectInventories });
/*     */       
/* 161 */       if (!list1.isEmpty())
/*     */       {
/* 163 */         return Container.calcRedstoneFromInventory((IInventory)list1.get(0));
/*     */       }
/*     */     } 
/*     */     
/* 167 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   protected <T extends EntityMinecart> List<T> findMinecarts(World worldIn, BlockPos pos, Class<T> clazz, Predicate<Entity>... filter) {
/* 172 */     AxisAlignedBB axisalignedbb = getDectectionBox(pos);
/* 173 */     return (filter.length != 1) ? worldIn.getEntitiesWithinAABB(clazz, axisalignedbb) : worldIn.getEntitiesWithinAABB(clazz, axisalignedbb, filter[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   private AxisAlignedBB getDectectionBox(BlockPos pos) {
/* 178 */     float f = 0.2F;
/* 179 */     return new AxisAlignedBB((pos.getX() + 0.2F), pos.getY(), (pos.getZ() + 0.2F), ((pos.getX() + 1) - 0.2F), ((pos.getY() + 1) - 0.2F), ((pos.getZ() + 1) - 0.2F));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 187 */     return getDefaultState().withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.byMetadata(meta & 0x7)).withProperty((IProperty)POWERED, Boolean.valueOf(((meta & 0x8) > 0)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 195 */     int i = 0;
/* 196 */     i |= ((BlockRailBase.EnumRailDirection)state.getValue((IProperty)SHAPE)).getMetadata();
/*     */     
/* 198 */     if (((Boolean)state.getValue((IProperty)POWERED)).booleanValue())
/*     */     {
/* 200 */       i |= 0x8;
/*     */     }
/*     */     
/* 203 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockState createBlockState() {
/* 208 */     return new BlockState(this, new IProperty[] { (IProperty)SHAPE, (IProperty)POWERED });
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\block\BlockRailDetector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */