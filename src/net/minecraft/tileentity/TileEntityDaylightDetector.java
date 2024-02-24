/*    */ package net.minecraft.tileentity;
/*    */ 
/*    */ import net.minecraft.block.BlockDaylightDetector;
/*    */ import net.minecraft.util.ITickable;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TileEntityDaylightDetector
/*    */   extends TileEntity
/*    */   implements ITickable
/*    */ {
/*    */   public void update() {
/* 13 */     if (this.worldObj != null && !this.worldObj.isRemote && this.worldObj.getTotalWorldTime() % 20L == 0L) {
/*    */       
/* 15 */       this.blockType = getBlockType();
/*    */       
/* 17 */       if (this.blockType instanceof BlockDaylightDetector)
/*    */       {
/* 19 */         ((BlockDaylightDetector)this.blockType).updatePower(this.worldObj, this.pos);
/*    */       }
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\tileentity\TileEntityDaylightDetector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */