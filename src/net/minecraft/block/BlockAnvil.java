/*     */ package net.minecraft.block;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyDirection;
/*     */ import net.minecraft.block.properties.PropertyInteger;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.item.EntityFallingBlock;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.ContainerRepair;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.IInteractionObject;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockAnvil
/*     */   extends BlockFalling {
/*  31 */   public static final PropertyDirection FACING = PropertyDirection.create("facing", (Predicate)EnumFacing.Plane.HORIZONTAL);
/*  32 */   public static final PropertyInteger DAMAGE = PropertyInteger.create("damage", 0, 2);
/*     */ 
/*     */   
/*     */   protected BlockAnvil() {
/*  36 */     super(Material.anvil);
/*  37 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)FACING, (Comparable)EnumFacing.NORTH).withProperty((IProperty)DAMAGE, Integer.valueOf(0)));
/*  38 */     setLightOpacity(0);
/*  39 */     setCreativeTab(CreativeTabs.tabDecorations);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube() {
/*  44 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/*  52 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/*  61 */     EnumFacing enumfacing = placer.getHorizontalFacing().rotateY();
/*  62 */     return super.onBlockPlaced(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer).withProperty((IProperty)FACING, (Comparable)enumfacing).withProperty((IProperty)DAMAGE, Integer.valueOf(meta >> 2));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
/*  67 */     if (!worldIn.isRemote)
/*     */     {
/*  69 */       playerIn.displayGui(new Anvil(worldIn, pos));
/*     */     }
/*     */     
/*  72 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int damageDropped(IBlockState state) {
/*  81 */     return ((Integer)state.getValue((IProperty)DAMAGE)).intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
/*  86 */     EnumFacing enumfacing = (EnumFacing)worldIn.getBlockState(pos).getValue((IProperty)FACING);
/*     */     
/*  88 */     if (enumfacing.getAxis() == EnumFacing.Axis.X) {
/*     */       
/*  90 */       setBlockBounds(0.0F, 0.0F, 0.125F, 1.0F, 1.0F, 0.875F);
/*     */     }
/*     */     else {
/*     */       
/*  94 */       setBlockBounds(0.125F, 0.0F, 0.0F, 0.875F, 1.0F, 1.0F);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
/* 103 */     list.add(new ItemStack(itemIn, 1, 0));
/* 104 */     list.add(new ItemStack(itemIn, 1, 1));
/* 105 */     list.add(new ItemStack(itemIn, 1, 2));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onStartFalling(EntityFallingBlock fallingEntity) {
/* 110 */     fallingEntity.setHurtEntities(true);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEndFalling(World worldIn, BlockPos pos) {
/* 115 */     worldIn.playAuxSFX(1022, pos, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
/* 120 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateForEntityRender(IBlockState state) {
/* 128 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)EnumFacing.SOUTH);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 136 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)EnumFacing.getHorizontal(meta & 0x3)).withProperty((IProperty)DAMAGE, Integer.valueOf((meta & 0xF) >> 2));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 144 */     int i = 0;
/* 145 */     i |= ((EnumFacing)state.getValue((IProperty)FACING)).getHorizontalIndex();
/* 146 */     i |= ((Integer)state.getValue((IProperty)DAMAGE)).intValue() << 2;
/* 147 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockState createBlockState() {
/* 152 */     return new BlockState(this, new IProperty[] { (IProperty)FACING, (IProperty)DAMAGE });
/*     */   }
/*     */   
/*     */   public static class Anvil
/*     */     implements IInteractionObject
/*     */   {
/*     */     private final World world;
/*     */     private final BlockPos position;
/*     */     
/*     */     public Anvil(World worldIn, BlockPos pos) {
/* 162 */       this.world = worldIn;
/* 163 */       this.position = pos;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getName() {
/* 168 */       return "anvil";
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasCustomName() {
/* 173 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public IChatComponent getDisplayName() {
/* 178 */       return (IChatComponent)new ChatComponentTranslation(Blocks.anvil.getUnlocalizedName() + ".name", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*     */     public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
/* 183 */       return (Container)new ContainerRepair(playerInventory, this.world, this.position, playerIn);
/*     */     }
/*     */ 
/*     */     
/*     */     public String getGuiID() {
/* 188 */       return "minecraft:anvil";
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\block\BlockAnvil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */