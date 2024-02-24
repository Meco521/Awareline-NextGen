/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.MapColor;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.command.server.CommandBlockLogic;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityCommandBlock;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockCommandBlock
/*     */   extends BlockContainer
/*     */ {
/*  23 */   public static final PropertyBool TRIGGERED = PropertyBool.create("triggered");
/*     */ 
/*     */   
/*     */   public BlockCommandBlock() {
/*  27 */     super(Material.iron, MapColor.adobeColor);
/*  28 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)TRIGGERED, Boolean.valueOf(false)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TileEntity createNewTileEntity(World worldIn, int meta) {
/*  36 */     return (TileEntity)new TileEntityCommandBlock();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/*  44 */     if (!worldIn.isRemote) {
/*     */       
/*  46 */       boolean flag = worldIn.isBlockPowered(pos);
/*  47 */       boolean flag1 = ((Boolean)state.getValue((IProperty)TRIGGERED)).booleanValue();
/*     */       
/*  49 */       if (flag && !flag1) {
/*     */         
/*  51 */         worldIn.setBlockState(pos, state.withProperty((IProperty)TRIGGERED, Boolean.valueOf(true)), 4);
/*  52 */         worldIn.scheduleUpdate(pos, this, tickRate(worldIn));
/*     */       }
/*  54 */       else if (!flag && flag1) {
/*     */         
/*  56 */         worldIn.setBlockState(pos, state.withProperty((IProperty)TRIGGERED, Boolean.valueOf(false)), 4);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/*  63 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */     
/*  65 */     if (tileentity instanceof TileEntityCommandBlock) {
/*     */       
/*  67 */       ((TileEntityCommandBlock)tileentity).getCommandBlockLogic().trigger(worldIn);
/*  68 */       worldIn.updateComparatorOutputLevel(pos, this);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int tickRate(World worldIn) {
/*  77 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
/*  82 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/*  83 */     return (tileentity instanceof TileEntityCommandBlock) ? ((TileEntityCommandBlock)tileentity).getCommandBlockLogic().tryOpenEditCommandBlock(playerIn) : false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasComparatorInputOverride() {
/*  88 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getComparatorInputOverride(World worldIn, BlockPos pos) {
/*  93 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/*  94 */     return (tileentity instanceof TileEntityCommandBlock) ? ((TileEntityCommandBlock)tileentity).getCommandBlockLogic().getSuccessCount() : 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
/* 102 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */     
/* 104 */     if (tileentity instanceof TileEntityCommandBlock) {
/*     */       
/* 106 */       CommandBlockLogic commandblocklogic = ((TileEntityCommandBlock)tileentity).getCommandBlockLogic();
/*     */       
/* 108 */       if (stack.hasDisplayName())
/*     */       {
/* 110 */         commandblocklogic.setName(stack.getDisplayName());
/*     */       }
/*     */       
/* 113 */       if (!worldIn.isRemote)
/*     */       {
/* 115 */         commandblocklogic.setTrackOutput(worldIn.getGameRules().getBoolean("sendCommandFeedback"));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int quantityDropped(Random random) {
/* 125 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRenderType() {
/* 133 */     return 3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 141 */     return getDefaultState().withProperty((IProperty)TRIGGERED, Boolean.valueOf(((meta & 0x1) > 0)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 149 */     int i = 0;
/*     */     
/* 151 */     if (((Boolean)state.getValue((IProperty)TRIGGERED)).booleanValue())
/*     */     {
/* 153 */       i |= 0x1;
/*     */     }
/*     */     
/* 156 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockState createBlockState() {
/* 161 */     return new BlockState(this, new IProperty[] { (IProperty)TRIGGERED });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/* 170 */     return getDefaultState().withProperty((IProperty)TRIGGERED, Boolean.valueOf(false));
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\block\BlockCommandBlock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */