/*    */ package net.minecraft.block;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ 
/*    */ 
/*    */ public class BlockPackedIce
/*    */   extends Block
/*    */ {
/*    */   public BlockPackedIce() {
/* 12 */     super(Material.packedIce);
/* 13 */     this.slipperiness = 0.98F;
/* 14 */     setCreativeTab(CreativeTabs.tabBlock);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int quantityDropped(Random random) {
/* 22 */     return 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\block\BlockPackedIce.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */