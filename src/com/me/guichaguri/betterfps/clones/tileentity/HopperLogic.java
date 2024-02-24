/*    */ package com.me.guichaguri.betterfps.clones.tileentity;
/*    */ 
/*    */ import com.me.guichaguri.betterfps.transformers.cloner.Named;
/*    */ import com.me.guichaguri.betterfps.tweaker.Naming;
/*    */ import net.minecraft.entity.item.EntityItem;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.inventory.ISidedInventory;
/*    */ import net.minecraft.tileentity.IHopper;
/*    */ import net.minecraft.tileentity.TileEntityHopper;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HopperLogic
/*    */   extends TileEntityHopper
/*    */ {
/* 19 */   public IInventory topInventory = null;
/* 20 */   public int topBlockUpdate = 1;
/*    */   
/*    */   public boolean canPickupDrops = true;
/*    */   public boolean isOnTransferCooldown = false;
/*    */   
/*    */   @Named(Naming.M_captureDroppedItems)
/*    */   public static boolean captureDroppedItems(IHopper hopper) {
/* 27 */     HopperLogic hopperTE = (hopper.getClass() == TileEntityHopper.class) ? (HopperLogic)hopper : null;
/*    */     
/* 29 */     IInventory iinventory = (hopperTE == null) ? getHopperInventory(hopper) : hopperTE.topInventory;
/*    */     
/* 31 */     if (iinventory != null) {
/* 32 */       EnumFacing enumfacing = EnumFacing.DOWN;
/*    */       
/* 34 */       if (isInventoryEmpty(iinventory, enumfacing)) return false;
/*    */       
/* 36 */       if (iinventory instanceof ISidedInventory) {
/* 37 */         ISidedInventory isidedinventory = (ISidedInventory)iinventory;
/* 38 */         int[] aint = isidedinventory.getSlotsForFace(enumfacing);
/*    */         
/* 40 */         for (int i = 0; i < aint.length; i++) {
/* 41 */           if (pullItemFromSlot(hopper, iinventory, aint[i], enumfacing)) return true; 
/*    */         } 
/*    */       } else {
/* 44 */         int j = iinventory.getSizeInventory();
/*    */         
/* 46 */         for (int k = 0; k < j; k++) {
/* 47 */           if (pullItemFromSlot(hopper, iinventory, k, enumfacing)) return true; 
/*    */         } 
/*    */       } 
/* 50 */     } else if (hopperTE == null || hopperTE.canPickupDrops) {
/*    */       
/* 52 */       for (EntityItem entityitem : func_181556_a(hopper.getWorld(), hopper.getXPos(), hopper.getYPos() + 1.0D, hopper.getZPos())) {
/* 53 */         if (putDropInInventoryAllSlots((IInventory)hopper, entityitem)) {
/* 54 */           return true;
/*    */         }
/*    */       } 
/*    */     } 
/*    */ 
/*    */     
/* 60 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void update() {
/* 70 */     if (this.worldObj != null && !this.worldObj.isRemote) {
/* 71 */       this.transferCooldown--;
/* 72 */       this.isOnTransferCooldown = (this.transferCooldown > 0);
/*    */ 
/*    */       
/* 75 */       setTransferCooldown(2);
/* 76 */       updateHopper();
/*    */       
/* 78 */       if (!this.isOnTransferCooldown && this.topBlockUpdate-- <= 0) {
/* 79 */         checkBlockOnTop();
/* 80 */         this.topBlockUpdate = 120;
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isOnTransferCooldown() {
/* 88 */     return this.isOnTransferCooldown;
/*    */   }
/*    */   
/*    */   public void checkBlockOnTop() {
/* 92 */     BlockPos topPos = new BlockPos(this.pos.getX(), this.pos.getY() + 1, this.pos.getZ());
/* 93 */     this.canPickupDrops = !this.worldObj.getBlockState(topPos).getBlock().isOpaqueCube();
/* 94 */     this.topInventory = getHopperInventory((IHopper)this);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\guichaguri\betterfps\clones\tileentity\HopperLogic.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */