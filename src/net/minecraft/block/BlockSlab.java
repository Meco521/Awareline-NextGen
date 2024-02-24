/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.IStringSerializable;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public abstract class BlockSlab
/*     */   extends Block
/*     */ {
/*  23 */   public static final PropertyEnum<EnumBlockHalf> HALF = PropertyEnum.create("half", EnumBlockHalf.class);
/*     */ 
/*     */   
/*     */   public BlockSlab(Material materialIn) {
/*  27 */     super(materialIn);
/*     */     
/*  29 */     if (isDouble()) {
/*     */       
/*  31 */       this.fullBlock = true;
/*     */     }
/*     */     else {
/*     */       
/*  35 */       setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
/*     */     } 
/*     */     
/*  38 */     setLightOpacity(255);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canSilkHarvest() {
/*  43 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
/*  48 */     if (isDouble()) {
/*     */       
/*  50 */       setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */     }
/*     */     else {
/*     */       
/*  54 */       IBlockState iblockstate = worldIn.getBlockState(pos);
/*     */       
/*  56 */       if (iblockstate.getBlock() == this)
/*     */       {
/*  58 */         if (iblockstate.getValue((IProperty)HALF) == EnumBlockHalf.TOP) {
/*     */           
/*  60 */           setBlockBounds(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */         }
/*     */         else {
/*     */           
/*  64 */           setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBlockBoundsForItemRender() {
/*  75 */     if (isDouble()) {
/*     */       
/*  77 */       setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */     }
/*     */     else {
/*     */       
/*  81 */       setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addCollisionBoxesToList(World worldIn, BlockPos pos, IBlockState state, AxisAlignedBB mask, List<AxisAlignedBB> list, Entity collidingEntity) {
/*  90 */     setBlockBoundsBasedOnState((IBlockAccess)worldIn, pos);
/*  91 */     super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/*  99 */     return isDouble();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/* 108 */     IBlockState iblockstate = super.onBlockPlaced(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer).withProperty((IProperty)HALF, EnumBlockHalf.BOTTOM);
/* 109 */     return isDouble() ? iblockstate : ((facing != EnumFacing.DOWN && (facing == EnumFacing.UP || hitY <= 0.5D)) ? iblockstate : iblockstate.withProperty((IProperty)HALF, EnumBlockHalf.TOP));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int quantityDropped(Random random) {
/* 117 */     return isDouble() ? 2 : 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube() {
/* 122 */     return isDouble();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
/* 127 */     if (isDouble())
/*     */     {
/* 129 */       return super.shouldSideBeRendered(worldIn, pos, side);
/*     */     }
/* 131 */     if (side != EnumFacing.UP && side != EnumFacing.DOWN && !super.shouldSideBeRendered(worldIn, pos, side))
/*     */     {
/* 133 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 137 */     BlockPos blockpos = pos.offset(side.getOpposite());
/* 138 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/* 139 */     IBlockState iblockstate1 = worldIn.getBlockState(blockpos);
/* 140 */     boolean flag = (isSlab(iblockstate.getBlock()) && iblockstate.getValue((IProperty)HALF) == EnumBlockHalf.TOP);
/* 141 */     boolean flag1 = (isSlab(iblockstate1.getBlock()) && iblockstate1.getValue((IProperty)HALF) == EnumBlockHalf.TOP);
/* 142 */     return flag1 ? ((side == EnumFacing.DOWN) ? true : ((side == EnumFacing.UP && super.shouldSideBeRendered(worldIn, pos, side)) ? true : ((!isSlab(iblockstate.getBlock()) || !flag)))) : ((side == EnumFacing.UP) ? true : ((side == EnumFacing.DOWN && super.shouldSideBeRendered(worldIn, pos, side)) ? true : ((!isSlab(iblockstate.getBlock()) || flag))));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static boolean isSlab(Block blockIn) {
/* 148 */     return (blockIn == Blocks.stone_slab || blockIn == Blocks.wooden_slab || blockIn == Blocks.stone_slab2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract String getUnlocalizedName(int paramInt);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDamageValue(World worldIn, BlockPos pos) {
/* 161 */     return super.getDamageValue(worldIn, pos) & 0x7;
/*     */   }
/*     */   
/*     */   public abstract boolean isDouble();
/*     */   
/*     */   public abstract IProperty<?> getVariantProperty();
/*     */   
/*     */   public abstract Object getVariant(ItemStack paramItemStack);
/*     */   
/*     */   public enum EnumBlockHalf
/*     */     implements IStringSerializable {
/* 172 */     TOP("top"),
/* 173 */     BOTTOM("bottom");
/*     */     
/*     */     private final String name;
/*     */ 
/*     */     
/*     */     EnumBlockHalf(String name) {
/* 179 */       this.name = name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 184 */       return this.name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getName() {
/* 189 */       return this.name;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\block\BlockSlab.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */