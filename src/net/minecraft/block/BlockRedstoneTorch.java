/*     */ package net.minecraft.block;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockRedstoneTorch
/*     */   extends BlockTorch {
/*  21 */   private static final Map<World, List<Toggle>> toggles = Maps.newHashMap();
/*     */   
/*     */   private final boolean isOn;
/*     */   
/*     */   private boolean isBurnedOut(World worldIn, BlockPos pos, boolean turnOff) {
/*  26 */     if (!toggles.containsKey(worldIn))
/*     */     {
/*  28 */       toggles.put(worldIn, Lists.newArrayList());
/*     */     }
/*     */     
/*  31 */     List<Toggle> list = toggles.get(worldIn);
/*     */     
/*  33 */     if (turnOff)
/*     */     {
/*  35 */       list.add(new Toggle(pos, worldIn.getTotalWorldTime()));
/*     */     }
/*     */     
/*  38 */     int i = 0;
/*     */     
/*  40 */     for (int j = 0; j < list.size(); j++) {
/*     */       
/*  42 */       Toggle blockredstonetorch$toggle = list.get(j);
/*     */       
/*  44 */       if (blockredstonetorch$toggle.pos.equals(pos)) {
/*     */         
/*  46 */         i++;
/*     */         
/*  48 */         if (i >= 8)
/*     */         {
/*  50 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  55 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockRedstoneTorch(boolean isOn) {
/*  60 */     this.isOn = isOn;
/*  61 */     setTickRandomly(true);
/*  62 */     setCreativeTab((CreativeTabs)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int tickRate(World worldIn) {
/*  70 */     return 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
/*  75 */     if (this.isOn)
/*     */     {
/*  77 */       for (EnumFacing enumfacing : EnumFacing.values())
/*     */       {
/*  79 */         worldIn.notifyNeighborsOfStateChange(pos.offset(enumfacing), this);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
/*  86 */     if (this.isOn)
/*     */     {
/*  88 */       for (EnumFacing enumfacing : EnumFacing.values())
/*     */       {
/*  90 */         worldIn.notifyNeighborsOfStateChange(pos.offset(enumfacing), this);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWeakPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side) {
/*  97 */     return (this.isOn && state.getValue((IProperty)FACING) != side) ? 15 : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean shouldBeOff(World worldIn, BlockPos pos, IBlockState state) {
/* 102 */     EnumFacing enumfacing = ((EnumFacing)state.getValue((IProperty)FACING)).getOpposite();
/* 103 */     return worldIn.isSidePowered(pos.offset(enumfacing), enumfacing);
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
/* 115 */     boolean flag = shouldBeOff(worldIn, pos, state);
/* 116 */     List<Toggle> list = toggles.get(worldIn);
/*     */     
/* 118 */     while (list != null && !list.isEmpty() && worldIn.getTotalWorldTime() - ((Toggle)list.get(0)).time > 60L)
/*     */     {
/* 120 */       list.remove(0);
/*     */     }
/*     */     
/* 123 */     if (this.isOn) {
/*     */       
/* 125 */       if (flag) {
/*     */         
/* 127 */         worldIn.setBlockState(pos, Blocks.unlit_redstone_torch.getDefaultState().withProperty((IProperty)FACING, state.getValue((IProperty)FACING)), 3);
/*     */         
/* 129 */         if (isBurnedOut(worldIn, pos, true))
/*     */         {
/* 131 */           worldIn.playSoundEffect((pos.getX() + 0.5F), (pos.getY() + 0.5F), (pos.getZ() + 0.5F), "random.fizz", 0.5F, 2.6F + (worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) * 0.8F);
/*     */           
/* 133 */           for (int i = 0; i < 5; i++) {
/*     */             
/* 135 */             double d0 = pos.getX() + rand.nextDouble() * 0.6D + 0.2D;
/* 136 */             double d1 = pos.getY() + rand.nextDouble() * 0.6D + 0.2D;
/* 137 */             double d2 = pos.getZ() + rand.nextDouble() * 0.6D + 0.2D;
/* 138 */             worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */           } 
/*     */           
/* 141 */           worldIn.scheduleUpdate(pos, worldIn.getBlockState(pos).getBlock(), 160);
/*     */         }
/*     */       
/*     */       } 
/* 145 */     } else if (!flag && !isBurnedOut(worldIn, pos, false)) {
/*     */       
/* 147 */       worldIn.setBlockState(pos, Blocks.redstone_torch.getDefaultState().withProperty((IProperty)FACING, state.getValue((IProperty)FACING)), 3);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/* 156 */     if (!onNeighborChangeInternal(worldIn, pos, state))
/*     */     {
/* 158 */       if (this.isOn == shouldBeOff(worldIn, pos, state))
/*     */       {
/* 160 */         worldIn.scheduleUpdate(pos, this, tickRate(worldIn));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int getStrongPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side) {
/* 167 */     return (side == EnumFacing.DOWN) ? getWeakPower(worldIn, pos, state, side) : 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/* 175 */     return Item.getItemFromBlock(Blocks.redstone_torch);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canProvidePower() {
/* 183 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/* 188 */     if (this.isOn) {
/*     */       
/* 190 */       double d0 = pos.getX() + 0.5D + (rand.nextDouble() - 0.5D) * 0.2D;
/* 191 */       double d1 = pos.getY() + 0.7D + (rand.nextDouble() - 0.5D) * 0.2D;
/* 192 */       double d2 = pos.getZ() + 0.5D + (rand.nextDouble() - 0.5D) * 0.2D;
/* 193 */       EnumFacing enumfacing = (EnumFacing)state.getValue((IProperty)FACING);
/*     */       
/* 195 */       if (enumfacing.getAxis().isHorizontal()) {
/*     */         
/* 197 */         EnumFacing enumfacing1 = enumfacing.getOpposite();
/* 198 */         double d3 = 0.27D;
/* 199 */         d0 += 0.27D * enumfacing1.getFrontOffsetX();
/* 200 */         d1 += 0.22D;
/* 201 */         d2 += 0.27D * enumfacing1.getFrontOffsetZ();
/*     */       } 
/*     */       
/* 204 */       worldIn.spawnParticle(EnumParticleTypes.REDSTONE, d0, d1, d2, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Item getItem(World worldIn, BlockPos pos) {
/* 210 */     return Item.getItemFromBlock(Blocks.redstone_torch);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAssociatedBlock(Block other) {
/* 215 */     return (other == Blocks.unlit_redstone_torch || other == Blocks.redstone_torch);
/*     */   }
/*     */ 
/*     */   
/*     */   static class Toggle
/*     */   {
/*     */     BlockPos pos;
/*     */     long time;
/*     */     
/*     */     public Toggle(BlockPos pos, long time) {
/* 225 */       this.pos = pos;
/* 226 */       this.time = time;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\block\BlockRedstoneTorch.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */