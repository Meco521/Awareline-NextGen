/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.MapColor;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityEnchantmentTable;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.world.IInteractionObject;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockEnchantmentTable
/*     */   extends BlockContainer
/*     */ {
/*     */   protected BlockEnchantmentTable() {
/*  24 */     super(Material.rock, MapColor.redColor);
/*  25 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.75F, 1.0F);
/*  26 */     setLightOpacity(0);
/*  27 */     setCreativeTab(CreativeTabs.tabDecorations);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube() {
/*  32 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/*  37 */     super.randomDisplayTick(worldIn, pos, state, rand);
/*     */     
/*  39 */     for (int i = -2; i <= 2; i++) {
/*     */       
/*  41 */       for (int j = -2; j <= 2; j++) {
/*     */         
/*  43 */         if (i > -2 && i < 2 && j == -1)
/*     */         {
/*  45 */           j = 2;
/*     */         }
/*     */         
/*  48 */         if (rand.nextInt(16) == 0)
/*     */         {
/*  50 */           for (int k = 0; k <= 1; k++) {
/*     */             
/*  52 */             BlockPos blockpos = pos.add(i, k, j);
/*     */             
/*  54 */             if (worldIn.getBlockState(blockpos).getBlock() == Blocks.bookshelf) {
/*     */               
/*  56 */               if (!worldIn.isAirBlock(pos.add(i / 2, 0, j / 2))) {
/*     */                 break;
/*     */               }
/*     */ 
/*     */               
/*  61 */               worldIn.spawnParticle(EnumParticleTypes.ENCHANTMENT_TABLE, pos.getX() + 0.5D, pos.getY() + 2.0D, pos.getZ() + 0.5D, (i + rand.nextFloat()) - 0.5D, (k - rand.nextFloat() - 1.0F), (j + rand.nextFloat()) - 0.5D, new int[0]);
/*     */             } 
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
/*     */   public boolean isOpaqueCube() {
/*  74 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRenderType() {
/*  82 */     return 3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TileEntity createNewTileEntity(World worldIn, int meta) {
/*  90 */     return (TileEntity)new TileEntityEnchantmentTable();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
/*  95 */     if (worldIn.isRemote)
/*     */     {
/*  97 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 101 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */     
/* 103 */     if (tileentity instanceof TileEntityEnchantmentTable)
/*     */     {
/* 105 */       playerIn.displayGui((IInteractionObject)tileentity);
/*     */     }
/*     */     
/* 108 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
/* 117 */     super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
/*     */     
/* 119 */     if (stack.hasDisplayName()) {
/*     */       
/* 121 */       TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */       
/* 123 */       if (tileentity instanceof TileEntityEnchantmentTable)
/*     */       {
/* 125 */         ((TileEntityEnchantmentTable)tileentity).setCustomName(stack.getDisplayName());
/*     */       }
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\block\BlockEnchantmentTable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */