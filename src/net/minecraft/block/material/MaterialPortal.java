/*    */ package net.minecraft.block.material;
/*    */ 
/*    */ public class MaterialPortal
/*    */   extends Material
/*    */ {
/*    */   public MaterialPortal(MapColor color) {
/*  7 */     super(color);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isSolid() {
/* 15 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean blocksLight() {
/* 23 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean blocksMovement() {
/* 31 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\block\material\MaterialPortal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */