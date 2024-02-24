/*    */ package net.minecraft.tileentity;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TileEntityDropper
/*    */   extends TileEntityDispenser
/*    */ {
/*    */   public String getName() {
/* 10 */     return hasCustomName() ? this.customName : "container.dropper";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getGuiID() {
/* 15 */     return "minecraft:dropper";
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\tileentity\TileEntityDropper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */