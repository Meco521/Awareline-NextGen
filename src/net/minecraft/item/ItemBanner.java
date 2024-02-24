/*     */ package net.minecraft.item;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.block.BlockStandingSign;
/*     */ import net.minecraft.block.BlockWallSign;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityBanner;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class ItemBanner
/*     */   extends ItemBlock {
/*     */   public ItemBanner() {
/*  24 */     super(Blocks.standing_banner);
/*  25 */     this.maxStackSize = 16;
/*  26 */     setCreativeTab(CreativeTabs.tabDecorations);
/*  27 */     setHasSubtypes(true);
/*  28 */     setMaxDamage(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
/*  36 */     if (side == EnumFacing.DOWN)
/*     */     {
/*  38 */       return false;
/*     */     }
/*  40 */     if (!worldIn.getBlockState(pos).getBlock().getMaterial().isSolid())
/*     */     {
/*  42 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  46 */     pos = pos.offset(side);
/*     */     
/*  48 */     if (!playerIn.canPlayerEdit(pos, side, stack))
/*     */     {
/*  50 */       return false;
/*     */     }
/*  52 */     if (!Blocks.standing_banner.canPlaceBlockAt(worldIn, pos))
/*     */     {
/*  54 */       return false;
/*     */     }
/*  56 */     if (worldIn.isRemote)
/*     */     {
/*  58 */       return true;
/*     */     }
/*     */ 
/*     */     
/*  62 */     if (side == EnumFacing.UP) {
/*     */       
/*  64 */       int i = MathHelper.floor_double(((playerIn.rotationYaw + 180.0F) * 16.0F / 360.0F) + 0.5D) & 0xF;
/*  65 */       worldIn.setBlockState(pos, Blocks.standing_banner.getDefaultState().withProperty((IProperty)BlockStandingSign.ROTATION, Integer.valueOf(i)), 3);
/*     */     }
/*     */     else {
/*     */       
/*  69 */       worldIn.setBlockState(pos, Blocks.wall_banner.getDefaultState().withProperty((IProperty)BlockWallSign.FACING, (Comparable)side), 3);
/*     */     } 
/*     */     
/*  72 */     stack.stackSize--;
/*  73 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */     
/*  75 */     if (tileentity instanceof TileEntityBanner)
/*     */     {
/*  77 */       ((TileEntityBanner)tileentity).setItemValues(stack);
/*     */     }
/*     */     
/*  80 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getItemStackDisplayName(ItemStack stack) {
/*  87 */     String s = "item.banner.";
/*  88 */     EnumDyeColor enumdyecolor = getBaseColor(stack);
/*  89 */     s = s + enumdyecolor.getUnlocalizedName() + ".name";
/*  90 */     return StatCollector.translateToLocal(s);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
/*  98 */     NBTTagCompound nbttagcompound = stack.getSubCompound("BlockEntityTag", false);
/*     */     
/* 100 */     if (nbttagcompound != null && nbttagcompound.hasKey("Patterns")) {
/*     */       
/* 102 */       NBTTagList nbttaglist = nbttagcompound.getTagList("Patterns", 10);
/*     */       
/* 104 */       for (int i = 0; i < nbttaglist.tagCount() && i < 6; i++) {
/*     */         
/* 106 */         NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
/* 107 */         EnumDyeColor enumdyecolor = EnumDyeColor.byDyeDamage(nbttagcompound1.getInteger("Color"));
/* 108 */         TileEntityBanner.EnumBannerPattern tileentitybanner$enumbannerpattern = TileEntityBanner.EnumBannerPattern.getPatternByID(nbttagcompound1.getString("Pattern"));
/*     */         
/* 110 */         if (tileentitybanner$enumbannerpattern != null)
/*     */         {
/* 112 */           tooltip.add(StatCollector.translateToLocal("item.banner." + tileentitybanner$enumbannerpattern.getPatternName() + "." + enumdyecolor.getUnlocalizedName()));
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getColorFromItemStack(ItemStack stack, int renderPass) {
/* 120 */     if (renderPass == 0)
/*     */     {
/* 122 */       return 16777215;
/*     */     }
/*     */ 
/*     */     
/* 126 */     EnumDyeColor enumdyecolor = getBaseColor(stack);
/* 127 */     return (enumdyecolor.getMapColor()).colorValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
/* 136 */     for (EnumDyeColor enumdyecolor : EnumDyeColor.values()) {
/*     */       
/* 138 */       NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 139 */       TileEntityBanner.setBaseColorAndPatterns(nbttagcompound, enumdyecolor.getDyeDamage(), (NBTTagList)null);
/* 140 */       NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/* 141 */       nbttagcompound1.setTag("BlockEntityTag", (NBTBase)nbttagcompound);
/* 142 */       ItemStack itemstack = new ItemStack(itemIn, 1, enumdyecolor.getDyeDamage());
/* 143 */       itemstack.setTagCompound(nbttagcompound1);
/* 144 */       subItems.add(itemstack);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CreativeTabs getCreativeTab() {
/* 153 */     return CreativeTabs.tabDecorations;
/*     */   }
/*     */ 
/*     */   
/*     */   private EnumDyeColor getBaseColor(ItemStack stack) {
/* 158 */     NBTTagCompound nbttagcompound = stack.getSubCompound("BlockEntityTag", false);
/* 159 */     EnumDyeColor enumdyecolor = null;
/*     */     
/* 161 */     if (nbttagcompound != null && nbttagcompound.hasKey("Base")) {
/*     */       
/* 163 */       enumdyecolor = EnumDyeColor.byDyeDamage(nbttagcompound.getInteger("Base"));
/*     */     }
/*     */     else {
/*     */       
/* 167 */       enumdyecolor = EnumDyeColor.byDyeDamage(stack.getMetadata());
/*     */     } 
/*     */     
/* 170 */     return enumdyecolor;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\item\ItemBanner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */