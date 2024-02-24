/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.world.ColorizerFoliage;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.biome.BiomeColorHelper;
/*     */ 
/*     */ public abstract class BlockLeaves
/*     */   extends BlockLeavesBase {
/*  22 */   public static final PropertyBool DECAYABLE = PropertyBool.create("decayable");
/*  23 */   public static final PropertyBool CHECK_DECAY = PropertyBool.create("check_decay");
/*     */   
/*     */   int[] surroundings;
/*     */   protected int iconIndex;
/*     */   protected boolean isTransparent;
/*     */   
/*     */   public BlockLeaves() {
/*  30 */     super(Material.leaves, false);
/*  31 */     setTickRandomly(true);
/*  32 */     setCreativeTab(CreativeTabs.tabDecorations);
/*  33 */     setHardness(0.2F);
/*  34 */     setLightOpacity(1);
/*  35 */     setStepSound(soundTypeGrass);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBlockColor() {
/*  40 */     return ColorizerFoliage.getFoliageColor(0.5D, 1.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRenderColor(IBlockState state) {
/*  45 */     return ColorizerFoliage.getFoliageColorBasic();
/*     */   }
/*     */ 
/*     */   
/*     */   public int colorMultiplier(IBlockAccess worldIn, BlockPos pos, int renderPass) {
/*  50 */     return BiomeColorHelper.getFoliageColorAtPos(worldIn, pos);
/*     */   }
/*     */ 
/*     */   
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
/*  55 */     int i = 1;
/*  56 */     int j = i + 1;
/*  57 */     int k = pos.getX();
/*  58 */     int l = pos.getY();
/*  59 */     int i1 = pos.getZ();
/*     */     
/*  61 */     if (worldIn.isAreaLoaded(new BlockPos(k - j, l - j, i1 - j), new BlockPos(k + j, l + j, i1 + j)))
/*     */     {
/*  63 */       for (int j1 = -i; j1 <= i; j1++) {
/*     */         
/*  65 */         for (int k1 = -i; k1 <= i; k1++) {
/*     */           
/*  67 */           for (int l1 = -i; l1 <= i; l1++) {
/*     */             
/*  69 */             BlockPos blockpos = pos.add(j1, k1, l1);
/*  70 */             IBlockState iblockstate = worldIn.getBlockState(blockpos);
/*     */             
/*  72 */             if (iblockstate.getBlock().getMaterial() == Material.leaves && !((Boolean)iblockstate.getValue((IProperty)CHECK_DECAY)).booleanValue())
/*     */             {
/*  74 */               worldIn.setBlockState(blockpos, iblockstate.withProperty((IProperty)CHECK_DECAY, Boolean.valueOf(true)), 4);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/*  84 */     if (!worldIn.isRemote)
/*     */     {
/*  86 */       if (((Boolean)state.getValue((IProperty)CHECK_DECAY)).booleanValue() && ((Boolean)state.getValue((IProperty)DECAYABLE)).booleanValue()) {
/*     */         
/*  88 */         int i = 4;
/*  89 */         int j = i + 1;
/*  90 */         int k = pos.getX();
/*  91 */         int l = pos.getY();
/*  92 */         int i1 = pos.getZ();
/*  93 */         int j1 = 32;
/*  94 */         int k1 = j1 * j1;
/*  95 */         int l1 = j1 / 2;
/*     */         
/*  97 */         if (this.surroundings == null)
/*     */         {
/*  99 */           this.surroundings = new int[j1 * j1 * j1];
/*     */         }
/*     */         
/* 102 */         if (worldIn.isAreaLoaded(new BlockPos(k - j, l - j, i1 - j), new BlockPos(k + j, l + j, i1 + j))) {
/*     */           
/* 104 */           BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*     */           
/* 106 */           for (int i2 = -i; i2 <= i; i2++) {
/*     */             
/* 108 */             for (int j2 = -i; j2 <= i; j2++) {
/*     */               
/* 110 */               for (int k2 = -i; k2 <= i; k2++) {
/*     */                 
/* 112 */                 Block block = worldIn.getBlockState((BlockPos)blockpos$mutableblockpos.set(k + i2, l + j2, i1 + k2)).getBlock();
/*     */                 
/* 114 */                 if (block != Blocks.log && block != Blocks.log2) {
/*     */                   
/* 116 */                   if (block.getMaterial() == Material.leaves)
/*     */                   {
/* 118 */                     this.surroundings[(i2 + l1) * k1 + (j2 + l1) * j1 + k2 + l1] = -2;
/*     */                   }
/*     */                   else
/*     */                   {
/* 122 */                     this.surroundings[(i2 + l1) * k1 + (j2 + l1) * j1 + k2 + l1] = -1;
/*     */                   }
/*     */                 
/*     */                 } else {
/*     */                   
/* 127 */                   this.surroundings[(i2 + l1) * k1 + (j2 + l1) * j1 + k2 + l1] = 0;
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */           
/* 133 */           for (int i3 = 1; i3 <= 4; i3++) {
/*     */             
/* 135 */             for (int j3 = -i; j3 <= i; j3++) {
/*     */               
/* 137 */               for (int k3 = -i; k3 <= i; k3++) {
/*     */                 
/* 139 */                 for (int l3 = -i; l3 <= i; l3++) {
/*     */                   
/* 141 */                   if (this.surroundings[(j3 + l1) * k1 + (k3 + l1) * j1 + l3 + l1] == i3 - 1) {
/*     */                     
/* 143 */                     if (this.surroundings[(j3 + l1 - 1) * k1 + (k3 + l1) * j1 + l3 + l1] == -2)
/*     */                     {
/* 145 */                       this.surroundings[(j3 + l1 - 1) * k1 + (k3 + l1) * j1 + l3 + l1] = i3;
/*     */                     }
/*     */                     
/* 148 */                     if (this.surroundings[(j3 + l1 + 1) * k1 + (k3 + l1) * j1 + l3 + l1] == -2)
/*     */                     {
/* 150 */                       this.surroundings[(j3 + l1 + 1) * k1 + (k3 + l1) * j1 + l3 + l1] = i3;
/*     */                     }
/*     */                     
/* 153 */                     if (this.surroundings[(j3 + l1) * k1 + (k3 + l1 - 1) * j1 + l3 + l1] == -2)
/*     */                     {
/* 155 */                       this.surroundings[(j3 + l1) * k1 + (k3 + l1 - 1) * j1 + l3 + l1] = i3;
/*     */                     }
/*     */                     
/* 158 */                     if (this.surroundings[(j3 + l1) * k1 + (k3 + l1 + 1) * j1 + l3 + l1] == -2)
/*     */                     {
/* 160 */                       this.surroundings[(j3 + l1) * k1 + (k3 + l1 + 1) * j1 + l3 + l1] = i3;
/*     */                     }
/*     */                     
/* 163 */                     if (this.surroundings[(j3 + l1) * k1 + (k3 + l1) * j1 + l3 + l1 - 1] == -2)
/*     */                     {
/* 165 */                       this.surroundings[(j3 + l1) * k1 + (k3 + l1) * j1 + l3 + l1 - 1] = i3;
/*     */                     }
/*     */                     
/* 168 */                     if (this.surroundings[(j3 + l1) * k1 + (k3 + l1) * j1 + l3 + l1 + 1] == -2)
/*     */                     {
/* 170 */                       this.surroundings[(j3 + l1) * k1 + (k3 + l1) * j1 + l3 + l1 + 1] = i3;
/*     */                     }
/*     */                   } 
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 179 */         int l2 = this.surroundings[l1 * k1 + l1 * j1 + l1];
/*     */         
/* 181 */         if (l2 >= 0) {
/*     */           
/* 183 */           worldIn.setBlockState(pos, state.withProperty((IProperty)CHECK_DECAY, Boolean.valueOf(false)), 4);
/*     */         }
/*     */         else {
/*     */           
/* 187 */           destroy(worldIn, pos);
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/* 195 */     if (worldIn.isRainingAt(pos.up()) && !World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, pos.down()) && rand.nextInt(15) == 1) {
/*     */       
/* 197 */       double d0 = (pos.getX() + rand.nextFloat());
/* 198 */       double d1 = pos.getY() - 0.05D;
/* 199 */       double d2 = (pos.getZ() + rand.nextFloat());
/* 200 */       worldIn.spawnParticle(EnumParticleTypes.DRIP_WATER, d0, d1, d2, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void destroy(World worldIn, BlockPos pos) {
/* 206 */     dropBlockAsItem(worldIn, pos, worldIn.getBlockState(pos), 0);
/* 207 */     worldIn.setBlockToAir(pos);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int quantityDropped(Random random) {
/* 215 */     return (random.nextInt(20) == 0) ? 1 : 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/* 223 */     return Item.getItemFromBlock(Blocks.sapling);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
/* 231 */     if (!worldIn.isRemote) {
/*     */       
/* 233 */       int i = getSaplingDropChance(state);
/*     */       
/* 235 */       if (fortune > 0) {
/*     */         
/* 237 */         i -= 2 << fortune;
/*     */         
/* 239 */         if (i < 10)
/*     */         {
/* 241 */           i = 10;
/*     */         }
/*     */       } 
/*     */       
/* 245 */       if (worldIn.rand.nextInt(i) == 0) {
/*     */         
/* 247 */         Item item = getItemDropped(state, worldIn.rand, fortune);
/* 248 */         spawnAsEntity(worldIn, pos, new ItemStack(item, 1, damageDropped(state)));
/*     */       } 
/*     */       
/* 251 */       i = 200;
/*     */       
/* 253 */       if (fortune > 0) {
/*     */         
/* 255 */         i -= 10 << fortune;
/*     */         
/* 257 */         if (i < 40)
/*     */         {
/* 259 */           i = 40;
/*     */         }
/*     */       } 
/*     */       
/* 263 */       dropApple(worldIn, pos, state, i);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void dropApple(World worldIn, BlockPos pos, IBlockState state, int chance) {}
/*     */ 
/*     */   
/*     */   protected int getSaplingDropChance(IBlockState state) {
/* 273 */     return 20;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/* 281 */     return !this.fancyGraphics;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setGraphicsLevel(boolean fancy) {
/* 289 */     this.isTransparent = fancy;
/* 290 */     this.fancyGraphics = fancy;
/* 291 */     this.iconIndex = fancy ? 0 : 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumWorldBlockLayer getBlockLayer() {
/* 296 */     return this.isTransparent ? EnumWorldBlockLayer.CUTOUT_MIPPED : EnumWorldBlockLayer.SOLID;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isVisuallyOpaque() {
/* 301 */     return false;
/*     */   }
/*     */   
/*     */   public abstract BlockPlanks.EnumType getWoodType(int paramInt);
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\block\BlockLeaves.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */