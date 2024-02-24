/*    */ package net.minecraft.tileentity;
/*    */ 
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.util.ChatComponentText;
/*    */ import net.minecraft.util.ChatComponentTranslation;
/*    */ import net.minecraft.util.IChatComponent;
/*    */ import net.minecraft.world.ILockableContainer;
/*    */ import net.minecraft.world.LockCode;
/*    */ 
/*    */ public abstract class TileEntityLockable
/*    */   extends TileEntity implements ILockableContainer {
/* 12 */   private LockCode code = LockCode.EMPTY_CODE;
/*    */ 
/*    */   
/*    */   public void readFromNBT(NBTTagCompound compound) {
/* 16 */     super.readFromNBT(compound);
/* 17 */     this.code = LockCode.fromNBT(compound);
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeToNBT(NBTTagCompound compound) {
/* 22 */     super.writeToNBT(compound);
/*    */     
/* 24 */     if (this.code != null)
/*    */     {
/* 26 */       this.code.toNBT(compound);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isLocked() {
/* 32 */     return (this.code != null && !this.code.isEmpty());
/*    */   }
/*    */ 
/*    */   
/*    */   public LockCode getLockCode() {
/* 37 */     return this.code;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setLockCode(LockCode code) {
/* 42 */     this.code = code;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IChatComponent getDisplayName() {
/* 50 */     return hasCustomName() ? (IChatComponent)new ChatComponentText(getName()) : (IChatComponent)new ChatComponentTranslation(getName(), new Object[0]);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\tileentity\TileEntityLockable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */