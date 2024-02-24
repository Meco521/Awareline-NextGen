/*     */ package net.minecraft.item;
/*     */ 
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockSkull;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTUtil;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntitySkull;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class ItemSkull extends Item {
/*  25 */   private static final String[] skullTypes = new String[] { "skeleton", "wither", "zombie", "char", "creeper" };
/*     */ 
/*     */   
/*     */   public ItemSkull() {
/*  29 */     setCreativeTab(CreativeTabs.tabDecorations);
/*  30 */     setMaxDamage(0);
/*  31 */     setHasSubtypes(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
/*  39 */     if (side == EnumFacing.DOWN)
/*     */     {
/*  41 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  45 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/*  46 */     Block block = iblockstate.getBlock();
/*  47 */     boolean flag = block.isReplaceable(worldIn, pos);
/*     */     
/*  49 */     if (!flag) {
/*     */       
/*  51 */       if (!worldIn.getBlockState(pos).getBlock().getMaterial().isSolid())
/*     */       {
/*  53 */         return false;
/*     */       }
/*     */       
/*  56 */       pos = pos.offset(side);
/*     */     } 
/*     */     
/*  59 */     if (!playerIn.canPlayerEdit(pos, side, stack))
/*     */     {
/*  61 */       return false;
/*     */     }
/*  63 */     if (!Blocks.skull.canPlaceBlockAt(worldIn, pos))
/*     */     {
/*  65 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  69 */     if (!worldIn.isRemote) {
/*     */       
/*  71 */       worldIn.setBlockState(pos, Blocks.skull.getDefaultState().withProperty((IProperty)BlockSkull.FACING, (Comparable)side), 3);
/*  72 */       int i = 0;
/*     */       
/*  74 */       if (side == EnumFacing.UP)
/*     */       {
/*  76 */         i = MathHelper.floor_double((playerIn.rotationYaw * 16.0F / 360.0F) + 0.5D) & 0xF;
/*     */       }
/*     */       
/*  79 */       TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */       
/*  81 */       if (tileentity instanceof TileEntitySkull) {
/*     */         
/*  83 */         TileEntitySkull tileentityskull = (TileEntitySkull)tileentity;
/*     */         
/*  85 */         if (stack.getMetadata() == 3) {
/*     */           
/*  87 */           GameProfile gameprofile = null;
/*     */           
/*  89 */           if (stack.hasTagCompound()) {
/*     */             
/*  91 */             NBTTagCompound nbttagcompound = stack.getTagCompound();
/*     */             
/*  93 */             if (nbttagcompound.hasKey("SkullOwner", 10)) {
/*     */               
/*  95 */               gameprofile = NBTUtil.readGameProfileFromNBT(nbttagcompound.getCompoundTag("SkullOwner"));
/*     */             }
/*  97 */             else if (nbttagcompound.hasKey("SkullOwner", 8) && !nbttagcompound.getString("SkullOwner").isEmpty()) {
/*     */               
/*  99 */               gameprofile = new GameProfile((UUID)null, nbttagcompound.getString("SkullOwner"));
/*     */             } 
/*     */           } 
/*     */           
/* 103 */           tileentityskull.setPlayerProfile(gameprofile);
/*     */         }
/*     */         else {
/*     */           
/* 107 */           tileentityskull.setType(stack.getMetadata());
/*     */         } 
/*     */         
/* 110 */         tileentityskull.setSkullRotation(i);
/* 111 */         Blocks.skull.checkWitherSpawn(worldIn, pos, tileentityskull);
/*     */       } 
/*     */       
/* 114 */       stack.stackSize--;
/*     */     } 
/*     */     
/* 117 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
/* 127 */     for (int i = 0; i < skullTypes.length; i++)
/*     */     {
/* 129 */       subItems.add(new ItemStack(itemIn, 1, i));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetadata(int damage) {
/* 139 */     return damage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUnlocalizedName(ItemStack stack) {
/* 148 */     int i = stack.getMetadata();
/*     */     
/* 150 */     if (i < 0 || i >= skullTypes.length)
/*     */     {
/* 152 */       i = 0;
/*     */     }
/*     */     
/* 155 */     return getUnlocalizedName() + "." + skullTypes[i];
/*     */   }
/*     */ 
/*     */   
/*     */   public String getItemStackDisplayName(ItemStack stack) {
/* 160 */     if (stack.getMetadata() == 3 && stack.hasTagCompound()) {
/*     */       
/* 162 */       if (stack.getTagCompound().hasKey("SkullOwner", 8))
/*     */       {
/* 164 */         return StatCollector.translateToLocalFormatted("item.skull.player.name", new Object[] { stack.getTagCompound().getString("SkullOwner") });
/*     */       }
/*     */       
/* 167 */       if (stack.getTagCompound().hasKey("SkullOwner", 10)) {
/*     */         
/* 169 */         NBTTagCompound nbttagcompound = stack.getTagCompound().getCompoundTag("SkullOwner");
/*     */         
/* 171 */         if (nbttagcompound.hasKey("Name", 8))
/*     */         {
/* 173 */           return StatCollector.translateToLocalFormatted("item.skull.player.name", new Object[] { nbttagcompound.getString("Name") });
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 178 */     return super.getItemStackDisplayName(stack);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean updateItemStackNBT(NBTTagCompound nbt) {
/* 186 */     super.updateItemStackNBT(nbt);
/*     */     
/* 188 */     if (nbt.hasKey("SkullOwner", 8) && !nbt.getString("SkullOwner").isEmpty()) {
/*     */       
/* 190 */       GameProfile gameprofile = new GameProfile((UUID)null, nbt.getString("SkullOwner"));
/* 191 */       gameprofile = TileEntitySkull.updateGameprofile(gameprofile);
/* 192 */       nbt.setTag("SkullOwner", (NBTBase)NBTUtil.writeGameProfile(new NBTTagCompound(), gameprofile));
/* 193 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 197 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\item\ItemSkull.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */