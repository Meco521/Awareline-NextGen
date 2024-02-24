/*     */ package net.minecraft.block;
/*     */ import com.google.common.base.Predicate;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.item.EntityItemFrame;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityComparator;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.IStringSerializable;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockRedstoneComparator extends BlockRedstoneDiode implements ITileEntityProvider {
/*  28 */   public static final PropertyBool POWERED = PropertyBool.create("powered");
/*  29 */   public static final PropertyEnum<Mode> MODE = PropertyEnum.create("mode", Mode.class);
/*     */ 
/*     */   
/*     */   public BlockRedstoneComparator(boolean powered) {
/*  33 */     super(powered);
/*  34 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)FACING, (Comparable)EnumFacing.NORTH).withProperty((IProperty)POWERED, Boolean.valueOf(false)).withProperty((IProperty)MODE, Mode.COMPARE));
/*  35 */     this.isBlockContainer = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLocalizedName() {
/*  43 */     return StatCollector.translateToLocal("item.comparator.name");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/*  51 */     return Items.comparator;
/*     */   }
/*     */ 
/*     */   
/*     */   public Item getItem(World worldIn, BlockPos pos) {
/*  56 */     return Items.comparator;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getDelay(IBlockState state) {
/*  61 */     return 2;
/*     */   }
/*     */ 
/*     */   
/*     */   protected IBlockState getPoweredState(IBlockState unpoweredState) {
/*  66 */     Boolean obool = (Boolean)unpoweredState.getValue((IProperty)POWERED);
/*  67 */     Mode blockredstonecomparator$mode = (Mode)unpoweredState.getValue((IProperty)MODE);
/*  68 */     EnumFacing enumfacing = (EnumFacing)unpoweredState.getValue((IProperty)FACING);
/*  69 */     return Blocks.powered_comparator.getDefaultState().withProperty((IProperty)FACING, (Comparable)enumfacing).withProperty((IProperty)POWERED, obool).withProperty((IProperty)MODE, blockredstonecomparator$mode);
/*     */   }
/*     */ 
/*     */   
/*     */   protected IBlockState getUnpoweredState(IBlockState poweredState) {
/*  74 */     Boolean obool = (Boolean)poweredState.getValue((IProperty)POWERED);
/*  75 */     Mode blockredstonecomparator$mode = (Mode)poweredState.getValue((IProperty)MODE);
/*  76 */     EnumFacing enumfacing = (EnumFacing)poweredState.getValue((IProperty)FACING);
/*  77 */     return Blocks.unpowered_comparator.getDefaultState().withProperty((IProperty)FACING, (Comparable)enumfacing).withProperty((IProperty)POWERED, obool).withProperty((IProperty)MODE, blockredstonecomparator$mode);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isPowered(IBlockState state) {
/*  82 */     return (this.isRepeaterPowered || ((Boolean)state.getValue((IProperty)POWERED)).booleanValue());
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getActiveSignal(IBlockAccess worldIn, BlockPos pos, IBlockState state) {
/*  87 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/*  88 */     return (tileentity instanceof TileEntityComparator) ? ((TileEntityComparator)tileentity).getOutputSignal() : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   private int calculateOutput(World worldIn, BlockPos pos, IBlockState state) {
/*  93 */     return (state.getValue((IProperty)MODE) == Mode.SUBTRACT) ? Math.max(calculateInputStrength(worldIn, pos, state) - getPowerOnSides((IBlockAccess)worldIn, pos, state), 0) : calculateInputStrength(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean shouldBePowered(World worldIn, BlockPos pos, IBlockState state) {
/*  98 */     int i = calculateInputStrength(worldIn, pos, state);
/*     */     
/* 100 */     if (i >= 15)
/*     */     {
/* 102 */       return true;
/*     */     }
/* 104 */     if (i == 0)
/*     */     {
/* 106 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 110 */     int j = getPowerOnSides((IBlockAccess)worldIn, pos, state);
/* 111 */     return (j == 0) ? true : ((i >= j));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected int calculateInputStrength(World worldIn, BlockPos pos, IBlockState state) {
/* 117 */     int i = super.calculateInputStrength(worldIn, pos, state);
/* 118 */     EnumFacing enumfacing = (EnumFacing)state.getValue((IProperty)FACING);
/* 119 */     BlockPos blockpos = pos.offset(enumfacing);
/* 120 */     Block block = worldIn.getBlockState(blockpos).getBlock();
/*     */     
/* 122 */     if (block.hasComparatorInputOverride()) {
/*     */       
/* 124 */       i = block.getComparatorInputOverride(worldIn, blockpos);
/*     */     }
/* 126 */     else if (i < 15 && block.isNormalCube()) {
/*     */       
/* 128 */       blockpos = blockpos.offset(enumfacing);
/* 129 */       block = worldIn.getBlockState(blockpos).getBlock();
/*     */       
/* 131 */       if (block.hasComparatorInputOverride()) {
/*     */         
/* 133 */         i = block.getComparatorInputOverride(worldIn, blockpos);
/*     */       }
/* 135 */       else if (block.getMaterial() == Material.air) {
/*     */         
/* 137 */         EntityItemFrame entityitemframe = findItemFrame(worldIn, enumfacing, blockpos);
/*     */         
/* 139 */         if (entityitemframe != null)
/*     */         {
/* 141 */           i = entityitemframe.func_174866_q();
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 146 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   private EntityItemFrame findItemFrame(World worldIn, final EnumFacing facing, BlockPos pos) {
/* 151 */     List<EntityItemFrame> list = worldIn.getEntitiesWithinAABB(EntityItemFrame.class, new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), (pos.getX() + 1), (pos.getY() + 1), (pos.getZ() + 1)), new Predicate<Entity>()
/*     */         {
/*     */           public boolean apply(Entity p_apply_1_)
/*     */           {
/* 155 */             return (p_apply_1_ != null && p_apply_1_.getHorizontalFacing() == facing);
/*     */           }
/*     */         });
/* 158 */     return (list.size() == 1) ? list.get(0) : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
/* 163 */     if (!playerIn.capabilities.allowEdit)
/*     */     {
/* 165 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 169 */     state = state.cycleProperty((IProperty)MODE);
/* 170 */     worldIn.playSoundEffect(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, "random.click", 0.3F, (state.getValue((IProperty)MODE) == Mode.SUBTRACT) ? 0.55F : 0.5F);
/* 171 */     worldIn.setBlockState(pos, state, 2);
/* 172 */     onStateChange(worldIn, pos, state);
/* 173 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void updateState(World worldIn, BlockPos pos, IBlockState state) {
/* 179 */     if (!worldIn.isBlockTickPending(pos, this)) {
/*     */       
/* 181 */       int i = calculateOutput(worldIn, pos, state);
/* 182 */       TileEntity tileentity = worldIn.getTileEntity(pos);
/* 183 */       int j = (tileentity instanceof TileEntityComparator) ? ((TileEntityComparator)tileentity).getOutputSignal() : 0;
/*     */       
/* 185 */       if (i != j || isPowered(state) != shouldBePowered(worldIn, pos, state))
/*     */       {
/* 187 */         if (isFacingTowardsRepeater(worldIn, pos, state)) {
/*     */           
/* 189 */           worldIn.updateBlockTick(pos, this, 2, -1);
/*     */         }
/*     */         else {
/*     */           
/* 193 */           worldIn.updateBlockTick(pos, this, 2, 0);
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void onStateChange(World worldIn, BlockPos pos, IBlockState state) {
/* 201 */     int i = calculateOutput(worldIn, pos, state);
/* 202 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/* 203 */     int j = 0;
/*     */     
/* 205 */     if (tileentity instanceof TileEntityComparator) {
/*     */       
/* 207 */       TileEntityComparator tileentitycomparator = (TileEntityComparator)tileentity;
/* 208 */       j = tileentitycomparator.getOutputSignal();
/* 209 */       tileentitycomparator.setOutputSignal(i);
/*     */     } 
/*     */     
/* 212 */     if (j != i || state.getValue((IProperty)MODE) == Mode.COMPARE) {
/*     */       
/* 214 */       boolean flag1 = shouldBePowered(worldIn, pos, state);
/* 215 */       boolean flag = isPowered(state);
/*     */       
/* 217 */       if (flag && !flag1) {
/*     */         
/* 219 */         worldIn.setBlockState(pos, state.withProperty((IProperty)POWERED, Boolean.valueOf(false)), 2);
/*     */       }
/* 221 */       else if (!flag && flag1) {
/*     */         
/* 223 */         worldIn.setBlockState(pos, state.withProperty((IProperty)POWERED, Boolean.valueOf(true)), 2);
/*     */       } 
/*     */       
/* 226 */       notifyNeighbors(worldIn, pos, state);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/* 232 */     if (this.isRepeaterPowered)
/*     */     {
/* 234 */       worldIn.setBlockState(pos, getUnpoweredState(state).withProperty((IProperty)POWERED, Boolean.valueOf(true)), 4);
/*     */     }
/*     */     
/* 237 */     onStateChange(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
/* 242 */     super.onBlockAdded(worldIn, pos, state);
/* 243 */     worldIn.setTileEntity(pos, createNewTileEntity(worldIn, 0));
/*     */   }
/*     */ 
/*     */   
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
/* 248 */     super.breakBlock(worldIn, pos, state);
/* 249 */     worldIn.removeTileEntity(pos);
/* 250 */     notifyNeighbors(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onBlockEventReceived(World worldIn, BlockPos pos, IBlockState state, int eventID, int eventParam) {
/* 258 */     super.onBlockEventReceived(worldIn, pos, state, eventID, eventParam);
/* 259 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/* 260 */     return (tileentity == null) ? false : tileentity.receiveClientEvent(eventID, eventParam);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TileEntity createNewTileEntity(World worldIn, int meta) {
/* 268 */     return (TileEntity)new TileEntityComparator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 276 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)EnumFacing.getHorizontal(meta)).withProperty((IProperty)POWERED, Boolean.valueOf(((meta & 0x8) > 0))).withProperty((IProperty)MODE, ((meta & 0x4) > 0) ? Mode.SUBTRACT : Mode.COMPARE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 284 */     int i = 0;
/* 285 */     i |= ((EnumFacing)state.getValue((IProperty)FACING)).getHorizontalIndex();
/*     */     
/* 287 */     if (((Boolean)state.getValue((IProperty)POWERED)).booleanValue())
/*     */     {
/* 289 */       i |= 0x8;
/*     */     }
/*     */     
/* 292 */     if (state.getValue((IProperty)MODE) == Mode.SUBTRACT)
/*     */     {
/* 294 */       i |= 0x4;
/*     */     }
/*     */     
/* 297 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockState createBlockState() {
/* 302 */     return new BlockState(this, new IProperty[] { (IProperty)FACING, (IProperty)MODE, (IProperty)POWERED });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/* 311 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)placer.getHorizontalFacing().getOpposite()).withProperty((IProperty)POWERED, Boolean.valueOf(false)).withProperty((IProperty)MODE, Mode.COMPARE);
/*     */   }
/*     */   
/*     */   public enum Mode
/*     */     implements IStringSerializable {
/* 316 */     COMPARE("compare"),
/* 317 */     SUBTRACT("subtract");
/*     */     
/*     */     private final String name;
/*     */ 
/*     */     
/*     */     Mode(String name) {
/* 323 */       this.name = name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 328 */       return this.name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getName() {
/* 333 */       return this.name;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\block\BlockRedstoneComparator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */