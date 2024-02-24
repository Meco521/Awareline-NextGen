/*     */ package net.minecraft.item;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockPlanks;
/*     */ import net.minecraft.block.IGrowable;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.passive.EntitySheep;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class ItemDye extends Item {
/*  22 */   public static final int[] dyeColors = new int[] { 1973019, 11743532, 3887386, 5320730, 2437522, 8073150, 2651799, 11250603, 4408131, 14188952, 4312372, 14602026, 6719955, 12801229, 15435844, 15790320 };
/*     */ 
/*     */   
/*     */   public ItemDye() {
/*  26 */     setHasSubtypes(true);
/*  27 */     setMaxDamage(0);
/*  28 */     setCreativeTab(CreativeTabs.tabMaterials);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUnlocalizedName(ItemStack stack) {
/*  37 */     int i = stack.getMetadata();
/*  38 */     return getUnlocalizedName() + "." + EnumDyeColor.byDyeDamage(i).getUnlocalizedName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
/*  46 */     if (!playerIn.canPlayerEdit(pos.offset(side), side, stack))
/*     */     {
/*  48 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  52 */     EnumDyeColor enumdyecolor = EnumDyeColor.byDyeDamage(stack.getMetadata());
/*     */     
/*  54 */     if (enumdyecolor == EnumDyeColor.WHITE) {
/*     */       
/*  56 */       if (applyBonemeal(stack, worldIn, pos))
/*     */       {
/*  58 */         if (!worldIn.isRemote)
/*     */         {
/*  60 */           worldIn.playAuxSFX(2005, pos, 0);
/*     */         }
/*     */         
/*  63 */         return true;
/*     */       }
/*     */     
/*  66 */     } else if (enumdyecolor == EnumDyeColor.BROWN) {
/*     */       
/*  68 */       IBlockState iblockstate = worldIn.getBlockState(pos);
/*  69 */       Block block = iblockstate.getBlock();
/*     */       
/*  71 */       if (block == Blocks.log && iblockstate.getValue((IProperty)BlockPlanks.VARIANT) == BlockPlanks.EnumType.JUNGLE) {
/*     */         
/*  73 */         if (side == EnumFacing.DOWN)
/*     */         {
/*  75 */           return false;
/*     */         }
/*     */         
/*  78 */         if (side == EnumFacing.UP)
/*     */         {
/*  80 */           return false;
/*     */         }
/*     */         
/*  83 */         pos = pos.offset(side);
/*     */         
/*  85 */         if (worldIn.isAirBlock(pos)) {
/*     */           
/*  87 */           IBlockState iblockstate1 = Blocks.cocoa.onBlockPlaced(worldIn, pos, side, hitX, hitY, hitZ, 0, (EntityLivingBase)playerIn);
/*  88 */           worldIn.setBlockState(pos, iblockstate1, 2);
/*     */           
/*  90 */           if (!playerIn.capabilities.isCreativeMode)
/*     */           {
/*  92 */             stack.stackSize--;
/*     */           }
/*     */         } 
/*     */         
/*  96 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 100 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean applyBonemeal(ItemStack stack, World worldIn, BlockPos target) {
/* 106 */     IBlockState iblockstate = worldIn.getBlockState(target);
/*     */     
/* 108 */     if (iblockstate.getBlock() instanceof IGrowable) {
/*     */       
/* 110 */       IGrowable igrowable = (IGrowable)iblockstate.getBlock();
/*     */       
/* 112 */       if (igrowable.canGrow(worldIn, target, iblockstate, worldIn.isRemote)) {
/*     */         
/* 114 */         if (!worldIn.isRemote) {
/*     */           
/* 116 */           if (igrowable.canUseBonemeal(worldIn, worldIn.rand, target, iblockstate))
/*     */           {
/* 118 */             igrowable.grow(worldIn, worldIn.rand, target, iblockstate);
/*     */           }
/*     */           
/* 121 */           stack.stackSize--;
/*     */         } 
/*     */         
/* 124 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 128 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void spawnBonemealParticles(World worldIn, BlockPos pos, int amount) {
/* 133 */     if (amount == 0)
/*     */     {
/* 135 */       amount = 15;
/*     */     }
/*     */     
/* 138 */     Block block = worldIn.getBlockState(pos).getBlock();
/*     */     
/* 140 */     if (block.getMaterial() != Material.air) {
/*     */       
/* 142 */       block.setBlockBoundsBasedOnState((IBlockAccess)worldIn, pos);
/*     */       
/* 144 */       for (int i = 0; i < amount; i++) {
/*     */         
/* 146 */         double d0 = itemRand.nextGaussian() * 0.02D;
/* 147 */         double d1 = itemRand.nextGaussian() * 0.02D;
/* 148 */         double d2 = itemRand.nextGaussian() * 0.02D;
/* 149 */         worldIn.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, (pos.getX() + itemRand.nextFloat()), pos.getY() + itemRand.nextFloat() * block.getBlockBoundsMaxY(), (pos.getZ() + itemRand.nextFloat()), d0, d1, d2, new int[0]);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target) {
/* 159 */     if (target instanceof EntitySheep) {
/*     */       
/* 161 */       EntitySheep entitysheep = (EntitySheep)target;
/* 162 */       EnumDyeColor enumdyecolor = EnumDyeColor.byDyeDamage(stack.getMetadata());
/*     */       
/* 164 */       if (!entitysheep.getSheared() && entitysheep.getFleeceColor() != enumdyecolor) {
/*     */         
/* 166 */         entitysheep.setFleeceColor(enumdyecolor);
/* 167 */         stack.stackSize--;
/*     */       } 
/*     */       
/* 170 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 174 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
/* 183 */     for (int i = 0; i < 16; i++)
/*     */     {
/* 185 */       subItems.add(new ItemStack(itemIn, 1, i));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\item\ItemDye.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */