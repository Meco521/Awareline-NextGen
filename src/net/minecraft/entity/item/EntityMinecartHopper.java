/*     */ package net.minecraft.entity.item;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.ContainerHopper;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.IHopper;
/*     */ import net.minecraft.tileentity.TileEntityHopper;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EntitySelectors;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityMinecartHopper
/*     */   extends EntityMinecartContainer implements IHopper {
/*     */   private boolean isBlocked = true;
/*  24 */   private int transferTicker = -1;
/*  25 */   private final BlockPos field_174900_c = BlockPos.ORIGIN;
/*     */ 
/*     */   
/*     */   public EntityMinecartHopper(World worldIn) {
/*  29 */     super(worldIn);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityMinecartHopper(World worldIn, double x, double y, double z) {
/*  34 */     super(worldIn, x, y, z);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityMinecart.EnumMinecartType getMinecartType() {
/*  39 */     return EntityMinecart.EnumMinecartType.HOPPER;
/*     */   }
/*     */ 
/*     */   
/*     */   public IBlockState getDefaultDisplayTile() {
/*  44 */     return Blocks.hopper.getDefaultState();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDefaultDisplayTileOffset() {
/*  49 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSizeInventory() {
/*  57 */     return 5;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean interactFirst(EntityPlayer playerIn) {
/*  65 */     if (!this.worldObj.isRemote)
/*     */     {
/*  67 */       playerIn.displayGUIChest((IInventory)this);
/*     */     }
/*     */     
/*  70 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onActivatorRailPass(int x, int y, int z, boolean receivingPower) {
/*  78 */     boolean flag = !receivingPower;
/*     */     
/*  80 */     if (flag != this.isBlocked)
/*     */     {
/*  82 */       this.isBlocked = flag;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getBlocked() {
/*  91 */     return this.isBlocked;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBlocked(boolean p_96110_1_) {
/*  99 */     this.isBlocked = p_96110_1_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public World getWorld() {
/* 107 */     return this.worldObj;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getXPos() {
/* 115 */     return this.posX;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getYPos() {
/* 123 */     return this.posY + 0.5D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getZPos() {
/* 131 */     return this.posZ;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 139 */     super.onUpdate();
/*     */     
/* 141 */     if (!this.worldObj.isRemote && isEntityAlive() && this.isBlocked) {
/*     */       
/* 143 */       BlockPos blockpos = new BlockPos(this);
/*     */       
/* 145 */       if (blockpos.equals(this.field_174900_c)) {
/*     */         
/* 147 */         this.transferTicker--;
/*     */       }
/*     */       else {
/*     */         
/* 151 */         this.transferTicker = 0;
/*     */       } 
/*     */       
/* 154 */       if (!canTransfer()) {
/*     */         
/* 156 */         this.transferTicker = 0;
/*     */         
/* 158 */         if (func_96112_aD()) {
/*     */           
/* 160 */           this.transferTicker = 4;
/* 161 */           markDirty();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_96112_aD() {
/* 169 */     if (TileEntityHopper.captureDroppedItems(this))
/*     */     {
/* 171 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 175 */     List<EntityItem> list = this.worldObj.getEntitiesWithinAABB(EntityItem.class, getEntityBoundingBox().expand(0.25D, 0.0D, 0.25D), EntitySelectors.selectAnything);
/*     */     
/* 177 */     if (!list.isEmpty())
/*     */     {
/* 179 */       TileEntityHopper.putDropInInventoryAllSlots((IInventory)this, list.get(0));
/*     */     }
/*     */     
/* 182 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void killMinecart(DamageSource source) {
/* 188 */     super.killMinecart(source);
/*     */     
/* 190 */     if (this.worldObj.getGameRules().getBoolean("doEntityDrops"))
/*     */     {
/* 192 */       dropItemWithOffset(Item.getItemFromBlock((Block)Blocks.hopper), 1, 0.0F);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeEntityToNBT(NBTTagCompound tagCompound) {
/* 201 */     super.writeEntityToNBT(tagCompound);
/* 202 */     tagCompound.setInteger("TransferCooldown", this.transferTicker);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void readEntityFromNBT(NBTTagCompound tagCompund) {
/* 210 */     super.readEntityFromNBT(tagCompund);
/* 211 */     this.transferTicker = tagCompund.getInteger("TransferCooldown");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTransferTicker(int p_98042_1_) {
/* 219 */     this.transferTicker = p_98042_1_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canTransfer() {
/* 227 */     return (this.transferTicker > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getGuiID() {
/* 232 */     return "minecraft:hopper";
/*     */   }
/*     */ 
/*     */   
/*     */   public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
/* 237 */     return (Container)new ContainerHopper(playerInventory, (IInventory)this, playerIn);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\entity\item\EntityMinecartHopper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */