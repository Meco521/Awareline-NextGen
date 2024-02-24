/*    */ package net.minecraft.block;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.util.EnumWorldBlockLayer;
/*    */ 
/*    */ 
/*    */ public class BlockGlass
/*    */   extends BlockBreakable
/*    */ {
/*    */   public BlockGlass(Material materialIn, boolean ignoreSimilarity) {
/* 13 */     super(materialIn, ignoreSimilarity);
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
/*    */ 
/*    */   
/*    */   public EnumWorldBlockLayer getBlockLayer() {
/* 27 */     return EnumWorldBlockLayer.CUTOUT;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isFullCube() {
/* 32 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean canSilkHarvest() {
/* 37 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\block\BlockGlass.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */