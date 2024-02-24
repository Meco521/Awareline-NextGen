/*     */ package net.minecraft.item;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class ItemBlock
/*     */   extends Item
/*     */ {
/*     */   protected final Block block;
/*     */   
/*     */   public ItemBlock(Block block) {
/*  24 */     this.block = block;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemBlock setUnlocalizedName(String unlocalizedName) {
/*  32 */     super.setUnlocalizedName(unlocalizedName);
/*  33 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
/*  41 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/*  42 */     Block block = iblockstate.getBlock();
/*     */     
/*  44 */     if (!block.isReplaceable(worldIn, pos))
/*     */     {
/*  46 */       pos = pos.offset(side);
/*     */     }
/*     */     
/*  49 */     if (stack.stackSize == 0)
/*     */     {
/*  51 */       return false;
/*     */     }
/*  53 */     if (!playerIn.canPlayerEdit(pos, side, stack))
/*     */     {
/*  55 */       return false;
/*     */     }
/*  57 */     if (worldIn.canBlockBePlaced(this.block, pos, false, side, (Entity)null, stack)) {
/*     */       
/*  59 */       int i = getMetadata(stack.getMetadata());
/*  60 */       IBlockState iblockstate1 = this.block.onBlockPlaced(worldIn, pos, side, hitX, hitY, hitZ, i, (EntityLivingBase)playerIn);
/*     */       
/*  62 */       if (worldIn.setBlockState(pos, iblockstate1, 3)) {
/*     */         
/*  64 */         iblockstate1 = worldIn.getBlockState(pos);
/*     */         
/*  66 */         if (iblockstate1.getBlock() == this.block) {
/*     */           
/*  68 */           setTileEntityNBT(worldIn, playerIn, pos, stack);
/*  69 */           this.block.onBlockPlacedBy(worldIn, pos, iblockstate1, (EntityLivingBase)playerIn, stack);
/*     */         } 
/*     */         
/*  72 */         worldIn.playSoundEffect((pos.getX() + 0.5F), (pos.getY() + 0.5F), (pos.getZ() + 0.5F), this.block.stepSound.getPlaceSound(), (this.block.stepSound.getVolume() + 1.0F) / 2.0F, this.block.stepSound.getFrequency() * 0.8F);
/*  73 */         stack.stackSize--;
/*     */       } 
/*     */       
/*  76 */       return true;
/*     */     } 
/*     */ 
/*     */     
/*  80 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean setTileEntityNBT(World worldIn, EntityPlayer pos, BlockPos stack, ItemStack p_179224_3_) {
/*  86 */     MinecraftServer minecraftserver = MinecraftServer.getServer();
/*     */     
/*  88 */     if (minecraftserver == null)
/*     */     {
/*  90 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  94 */     if (p_179224_3_.hasTagCompound() && p_179224_3_.getTagCompound().hasKey("BlockEntityTag", 10)) {
/*     */       
/*  96 */       TileEntity tileentity = worldIn.getTileEntity(stack);
/*     */       
/*  98 */       if (tileentity != null) {
/*     */         
/* 100 */         if (!worldIn.isRemote && tileentity.func_183000_F() && !minecraftserver.getConfigurationManager().canSendCommands(pos.getGameProfile()))
/*     */         {
/* 102 */           return false;
/*     */         }
/*     */         
/* 105 */         NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 106 */         NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttagcompound.copy();
/* 107 */         tileentity.writeToNBT(nbttagcompound);
/* 108 */         NBTTagCompound nbttagcompound2 = (NBTTagCompound)p_179224_3_.getTagCompound().getTag("BlockEntityTag");
/* 109 */         nbttagcompound.merge(nbttagcompound2);
/* 110 */         nbttagcompound.setInteger("x", stack.getX());
/* 111 */         nbttagcompound.setInteger("y", stack.getY());
/* 112 */         nbttagcompound.setInteger("z", stack.getZ());
/*     */         
/* 114 */         if (!nbttagcompound.equals(nbttagcompound1)) {
/*     */           
/* 116 */           tileentity.readFromNBT(nbttagcompound);
/* 117 */           tileentity.markDirty();
/* 118 */           return true;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 123 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side, EntityPlayer player, ItemStack stack) {
/* 129 */     Block block = worldIn.getBlockState(pos).getBlock();
/*     */     
/* 131 */     if (block == Blocks.snow_layer) {
/*     */       
/* 133 */       side = EnumFacing.UP;
/*     */     }
/* 135 */     else if (!block.isReplaceable(worldIn, pos)) {
/*     */       
/* 137 */       pos = pos.offset(side);
/*     */     } 
/*     */     
/* 140 */     return worldIn.canBlockBePlaced(this.block, pos, false, side, (Entity)null, stack);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUnlocalizedName(ItemStack stack) {
/* 149 */     return this.block.getUnlocalizedName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUnlocalizedName() {
/* 157 */     return this.block.getUnlocalizedName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CreativeTabs getCreativeTab() {
/* 165 */     return this.block.getCreativeTabToDisplayOn();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
/* 173 */     this.block.getSubBlocks(itemIn, tab, subItems);
/*     */   }
/*     */ 
/*     */   
/*     */   public Block getBlock() {
/* 178 */     return this.block;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\item\ItemBlock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */