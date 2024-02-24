/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.MapColor;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityFallingBlock;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockDragonEgg
/*     */   extends Block
/*     */ {
/*     */   public BlockDragonEgg() {
/*  21 */     super(Material.dragonEgg, MapColor.blackColor);
/*  22 */     setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 1.0F, 0.9375F);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
/*  27 */     worldIn.scheduleUpdate(pos, this, tickRate(worldIn));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/*  35 */     worldIn.scheduleUpdate(pos, this, tickRate(worldIn));
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/*  40 */     checkFall(worldIn, pos);
/*     */   }
/*     */ 
/*     */   
/*     */   private void checkFall(World worldIn, BlockPos pos) {
/*  45 */     if (BlockFalling.canFallInto(worldIn, pos.down()) && pos.getY() >= 0) {
/*     */       
/*  47 */       int i = 32;
/*     */       
/*  49 */       if (!BlockFalling.fallInstantly && worldIn.isAreaLoaded(pos.add(-i, -i, -i), pos.add(i, i, i))) {
/*     */         
/*  51 */         worldIn.spawnEntityInWorld((Entity)new EntityFallingBlock(worldIn, (pos.getX() + 0.5F), pos.getY(), (pos.getZ() + 0.5F), getDefaultState()));
/*     */       }
/*     */       else {
/*     */         
/*  55 */         worldIn.setBlockToAir(pos);
/*     */         
/*     */         BlockPos blockpos;
/*  58 */         for (blockpos = pos; BlockFalling.canFallInto(worldIn, blockpos) && blockpos.getY() > 0; blockpos = blockpos.down());
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  63 */         if (blockpos.getY() > 0)
/*     */         {
/*  65 */           worldIn.setBlockState(blockpos, getDefaultState(), 2);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
/*  73 */     teleport(worldIn, pos);
/*  74 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) {
/*  79 */     teleport(worldIn, pos);
/*     */   }
/*     */ 
/*     */   
/*     */   private void teleport(World worldIn, BlockPos pos) {
/*  84 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/*     */     
/*  86 */     if (iblockstate.getBlock() == this)
/*     */     {
/*  88 */       for (int i = 0; i < 1000; i++) {
/*     */         
/*  90 */         BlockPos blockpos = pos.add(worldIn.rand.nextInt(16) - worldIn.rand.nextInt(16), worldIn.rand.nextInt(8) - worldIn.rand.nextInt(8), worldIn.rand.nextInt(16) - worldIn.rand.nextInt(16));
/*     */         
/*  92 */         if ((worldIn.getBlockState(blockpos).getBlock()).blockMaterial == Material.air) {
/*     */           
/*  94 */           if (worldIn.isRemote) {
/*     */             
/*  96 */             for (int j = 0; j < 128; j++)
/*     */             {
/*  98 */               double d0 = worldIn.rand.nextDouble();
/*  99 */               float f = (worldIn.rand.nextFloat() - 0.5F) * 0.2F;
/* 100 */               float f1 = (worldIn.rand.nextFloat() - 0.5F) * 0.2F;
/* 101 */               float f2 = (worldIn.rand.nextFloat() - 0.5F) * 0.2F;
/* 102 */               double d1 = blockpos.getX() + (pos.getX() - blockpos.getX()) * d0 + worldIn.rand.nextDouble() - 0.5D + 0.5D;
/* 103 */               double d2 = blockpos.getY() + (pos.getY() - blockpos.getY()) * d0 + worldIn.rand.nextDouble() - 0.5D;
/* 104 */               double d3 = blockpos.getZ() + (pos.getZ() - blockpos.getZ()) * d0 + worldIn.rand.nextDouble() - 0.5D + 0.5D;
/* 105 */               worldIn.spawnParticle(EnumParticleTypes.PORTAL, d1, d2, d3, f, f1, f2, new int[0]);
/*     */             }
/*     */           
/*     */           } else {
/*     */             
/* 110 */             worldIn.setBlockState(blockpos, iblockstate, 2);
/* 111 */             worldIn.setBlockToAir(pos);
/*     */           } 
/*     */           return;
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
/*     */   public int tickRate(World worldIn) {
/* 125 */     return 5;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/* 133 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube() {
/* 138 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
/* 143 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public Item getItem(World worldIn, BlockPos pos) {
/* 148 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\block\BlockDragonEgg.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */