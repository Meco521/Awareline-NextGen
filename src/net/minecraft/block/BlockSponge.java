/*     */ package net.minecraft.block;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Queue;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.Tuple;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockSponge extends Block {
/*  23 */   public static final PropertyBool WET = PropertyBool.create("wet");
/*     */ 
/*     */   
/*     */   protected BlockSponge() {
/*  27 */     super(Material.sponge);
/*  28 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)WET, Boolean.valueOf(false)));
/*  29 */     setCreativeTab(CreativeTabs.tabBlock);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLocalizedName() {
/*  37 */     return StatCollector.translateToLocal(getUnlocalizedName() + ".dry.name");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int damageDropped(IBlockState state) {
/*  46 */     return ((Boolean)state.getValue((IProperty)WET)).booleanValue() ? 1 : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
/*  51 */     tryAbsorb(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/*  59 */     tryAbsorb(worldIn, pos, state);
/*  60 */     super.onNeighborBlockChange(worldIn, pos, state, neighborBlock);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void tryAbsorb(World worldIn, BlockPos pos, IBlockState state) {
/*  65 */     if (!((Boolean)state.getValue((IProperty)WET)).booleanValue() && absorb(worldIn, pos)) {
/*     */       
/*  67 */       worldIn.setBlockState(pos, state.withProperty((IProperty)WET, Boolean.valueOf(true)), 2);
/*  68 */       worldIn.playAuxSFX(2001, pos, Block.getIdFromBlock(Blocks.water));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean absorb(World worldIn, BlockPos pos) {
/*  74 */     Queue<Tuple<BlockPos, Integer>> queue = Lists.newLinkedList();
/*  75 */     ArrayList<BlockPos> arraylist = Lists.newArrayList();
/*  76 */     queue.add(new Tuple(pos, Integer.valueOf(0)));
/*  77 */     int i = 0;
/*     */     
/*  79 */     while (!queue.isEmpty()) {
/*     */       
/*  81 */       Tuple<BlockPos, Integer> tuple = queue.poll();
/*  82 */       BlockPos blockpos = (BlockPos)tuple.getFirst();
/*  83 */       int j = ((Integer)tuple.getSecond()).intValue();
/*     */       
/*  85 */       for (EnumFacing enumfacing : EnumFacing.values()) {
/*     */         
/*  87 */         BlockPos blockpos1 = blockpos.offset(enumfacing);
/*     */         
/*  89 */         if (worldIn.getBlockState(blockpos1).getBlock().getMaterial() == Material.water) {
/*     */           
/*  91 */           worldIn.setBlockState(blockpos1, Blocks.air.getDefaultState(), 2);
/*  92 */           arraylist.add(blockpos1);
/*  93 */           i++;
/*     */           
/*  95 */           if (j < 6)
/*     */           {
/*  97 */             queue.add(new Tuple(blockpos1, Integer.valueOf(j + 1)));
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 102 */       if (i > 64) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 108 */     for (BlockPos blockpos2 : arraylist)
/*     */     {
/* 110 */       worldIn.notifyNeighborsOfStateChange(blockpos2, Blocks.air);
/*     */     }
/*     */     
/* 113 */     return (i > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
/* 121 */     list.add(new ItemStack(itemIn, 1, 0));
/* 122 */     list.add(new ItemStack(itemIn, 1, 1));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 130 */     return getDefaultState().withProperty((IProperty)WET, Boolean.valueOf(((meta & 0x1) == 1)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 138 */     return ((Boolean)state.getValue((IProperty)WET)).booleanValue() ? 1 : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockState createBlockState() {
/* 143 */     return new BlockState(this, new IProperty[] { (IProperty)WET });
/*     */   }
/*     */ 
/*     */   
/*     */   public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/* 148 */     if (((Boolean)state.getValue((IProperty)WET)).booleanValue()) {
/*     */       
/* 150 */       EnumFacing enumfacing = EnumFacing.random(rand);
/*     */       
/* 152 */       if (enumfacing != EnumFacing.UP && !World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, pos.offset(enumfacing))) {
/*     */         
/* 154 */         double d0 = pos.getX();
/* 155 */         double d1 = pos.getY();
/* 156 */         double d2 = pos.getZ();
/*     */         
/* 158 */         if (enumfacing == EnumFacing.DOWN) {
/*     */           
/* 160 */           d1 -= 0.05D;
/* 161 */           d0 += rand.nextDouble();
/* 162 */           d2 += rand.nextDouble();
/*     */         }
/*     */         else {
/*     */           
/* 166 */           d1 += rand.nextDouble() * 0.8D;
/*     */           
/* 168 */           if (enumfacing.getAxis() == EnumFacing.Axis.X) {
/*     */             
/* 170 */             d2 += rand.nextDouble();
/*     */             
/* 172 */             if (enumfacing == EnumFacing.EAST)
/*     */             {
/* 174 */               d0++;
/*     */             }
/*     */             else
/*     */             {
/* 178 */               d0 += 0.05D;
/*     */             }
/*     */           
/*     */           } else {
/*     */             
/* 183 */             d0 += rand.nextDouble();
/*     */             
/* 185 */             if (enumfacing == EnumFacing.SOUTH) {
/*     */               
/* 187 */               d2++;
/*     */             }
/*     */             else {
/*     */               
/* 191 */               d2 += 0.05D;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 196 */         worldIn.spawnParticle(EnumParticleTypes.DRIP_WATER, d0, d1, d2, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\block\BlockSponge.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */