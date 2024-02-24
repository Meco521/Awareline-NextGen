/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.MapColor;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyInteger;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemArmor;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.tileentity.TileEntityBanner;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ public class BlockCauldron
/*     */   extends Block
/*     */ {
/*  31 */   public static final PropertyInteger LEVEL = PropertyInteger.create("level", 0, 3);
/*     */ 
/*     */   
/*     */   public BlockCauldron() {
/*  35 */     super(Material.iron, MapColor.stoneColor);
/*  36 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)LEVEL, Integer.valueOf(0)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addCollisionBoxesToList(World worldIn, BlockPos pos, IBlockState state, AxisAlignedBB mask, List<AxisAlignedBB> list, Entity collidingEntity) {
/*  44 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.3125F, 1.0F);
/*  45 */     super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/*  46 */     float f = 0.125F;
/*  47 */     setBlockBounds(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
/*  48 */     super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/*  49 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
/*  50 */     super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/*  51 */     setBlockBounds(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*  52 */     super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/*  53 */     setBlockBounds(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
/*  54 */     super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/*  55 */     setBlockBoundsForItemRender();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBlockBoundsForItemRender() {
/*  63 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/*  71 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube() {
/*  76 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
/*  84 */     int i = ((Integer)state.getValue((IProperty)LEVEL)).intValue();
/*  85 */     float f = pos.getY() + (6.0F + (3 * i)) / 16.0F;
/*     */     
/*  87 */     if (!worldIn.isRemote && entityIn.isBurning() && i > 0 && (entityIn.getEntityBoundingBox()).minY <= f) {
/*     */       
/*  89 */       entityIn.extinguish();
/*  90 */       setWaterLevel(worldIn, pos, state, i - 1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
/*  96 */     if (worldIn.isRemote)
/*     */     {
/*  98 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 102 */     ItemStack itemstack = playerIn.inventory.getCurrentItem();
/*     */     
/* 104 */     if (itemstack == null)
/*     */     {
/* 106 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 110 */     int i = ((Integer)state.getValue((IProperty)LEVEL)).intValue();
/* 111 */     Item item = itemstack.getItem();
/*     */     
/* 113 */     if (item == Items.water_bucket) {
/*     */       
/* 115 */       if (i < 3) {
/*     */         
/* 117 */         if (!playerIn.capabilities.isCreativeMode)
/*     */         {
/* 119 */           playerIn.inventory.setInventorySlotContents(playerIn.inventory.currentItem, new ItemStack(Items.bucket));
/*     */         }
/*     */         
/* 122 */         playerIn.triggerAchievement(StatList.field_181725_I);
/* 123 */         setWaterLevel(worldIn, pos, state, 3);
/*     */       } 
/*     */       
/* 126 */       return true;
/*     */     } 
/* 128 */     if (item == Items.glass_bottle) {
/*     */       
/* 130 */       if (i > 0) {
/*     */         
/* 132 */         if (!playerIn.capabilities.isCreativeMode) {
/*     */           
/* 134 */           ItemStack itemstack2 = new ItemStack((Item)Items.potionitem, 1, 0);
/*     */           
/* 136 */           if (!playerIn.inventory.addItemStackToInventory(itemstack2)) {
/*     */             
/* 138 */             worldIn.spawnEntityInWorld((Entity)new EntityItem(worldIn, pos.getX() + 0.5D, pos.getY() + 1.5D, pos.getZ() + 0.5D, itemstack2));
/*     */           }
/* 140 */           else if (playerIn instanceof EntityPlayerMP) {
/*     */             
/* 142 */             ((EntityPlayerMP)playerIn).sendContainerToPlayer(playerIn.inventoryContainer);
/*     */           } 
/*     */           
/* 145 */           playerIn.triggerAchievement(StatList.field_181726_J);
/* 146 */           itemstack.stackSize--;
/*     */           
/* 148 */           if (itemstack.stackSize <= 0)
/*     */           {
/* 150 */             playerIn.inventory.setInventorySlotContents(playerIn.inventory.currentItem, (ItemStack)null);
/*     */           }
/*     */         } 
/*     */         
/* 154 */         setWaterLevel(worldIn, pos, state, i - 1);
/*     */       } 
/*     */       
/* 157 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 161 */     if (i > 0 && item instanceof ItemArmor) {
/*     */       
/* 163 */       ItemArmor itemarmor = (ItemArmor)item;
/*     */       
/* 165 */       if (itemarmor.getArmorMaterial() == ItemArmor.ArmorMaterial.LEATHER && itemarmor.hasColor(itemstack)) {
/*     */         
/* 167 */         itemarmor.removeColor(itemstack);
/* 168 */         setWaterLevel(worldIn, pos, state, i - 1);
/* 169 */         playerIn.triggerAchievement(StatList.field_181727_K);
/* 170 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 174 */     if (i > 0 && item instanceof net.minecraft.item.ItemBanner && TileEntityBanner.getPatterns(itemstack) > 0) {
/*     */       
/* 176 */       ItemStack itemstack1 = itemstack.copy();
/* 177 */       itemstack1.stackSize = 1;
/* 178 */       TileEntityBanner.removeBannerData(itemstack1);
/*     */       
/* 180 */       if (itemstack.stackSize <= 1 && !playerIn.capabilities.isCreativeMode) {
/*     */         
/* 182 */         playerIn.inventory.setInventorySlotContents(playerIn.inventory.currentItem, itemstack1);
/*     */       }
/*     */       else {
/*     */         
/* 186 */         if (!playerIn.inventory.addItemStackToInventory(itemstack1)) {
/*     */           
/* 188 */           worldIn.spawnEntityInWorld((Entity)new EntityItem(worldIn, pos.getX() + 0.5D, pos.getY() + 1.5D, pos.getZ() + 0.5D, itemstack1));
/*     */         }
/* 190 */         else if (playerIn instanceof EntityPlayerMP) {
/*     */           
/* 192 */           ((EntityPlayerMP)playerIn).sendContainerToPlayer(playerIn.inventoryContainer);
/*     */         } 
/*     */         
/* 195 */         playerIn.triggerAchievement(StatList.field_181728_L);
/*     */         
/* 197 */         if (!playerIn.capabilities.isCreativeMode)
/*     */         {
/* 199 */           itemstack.stackSize--;
/*     */         }
/*     */       } 
/*     */       
/* 203 */       if (!playerIn.capabilities.isCreativeMode)
/*     */       {
/* 205 */         setWaterLevel(worldIn, pos, state, i - 1);
/*     */       }
/*     */       
/* 208 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 212 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWaterLevel(World worldIn, BlockPos pos, IBlockState state, int level) {
/* 221 */     worldIn.setBlockState(pos, state.withProperty((IProperty)LEVEL, Integer.valueOf(MathHelper.clamp_int(level, 0, 3))), 2);
/* 222 */     worldIn.updateComparatorOutputLevel(pos, this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fillWithRain(World worldIn, BlockPos pos) {
/* 230 */     if (worldIn.rand.nextInt(20) == 1) {
/*     */       
/* 232 */       IBlockState iblockstate = worldIn.getBlockState(pos);
/*     */       
/* 234 */       if (((Integer)iblockstate.getValue((IProperty)LEVEL)).intValue() < 3)
/*     */       {
/* 236 */         worldIn.setBlockState(pos, iblockstate.cycleProperty((IProperty)LEVEL), 2);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/* 246 */     return Items.cauldron;
/*     */   }
/*     */ 
/*     */   
/*     */   public Item getItem(World worldIn, BlockPos pos) {
/* 251 */     return Items.cauldron;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasComparatorInputOverride() {
/* 256 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getComparatorInputOverride(World worldIn, BlockPos pos) {
/* 261 */     return ((Integer)worldIn.getBlockState(pos).getValue((IProperty)LEVEL)).intValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 269 */     return getDefaultState().withProperty((IProperty)LEVEL, Integer.valueOf(meta));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 277 */     return ((Integer)state.getValue((IProperty)LEVEL)).intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockState createBlockState() {
/* 282 */     return new BlockState(this, new IProperty[] { (IProperty)LEVEL });
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\block\BlockCauldron.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */