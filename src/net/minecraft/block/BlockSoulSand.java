/*    */ package net.minecraft.block;
/*    */ 
/*    */ import awareline.main.mod.implement.move.NoSlow;
/*    */ import net.minecraft.block.material.MapColor;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.AxisAlignedBB;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class BlockSoulSand
/*    */   extends Block
/*    */ {
/*    */   public BlockSoulSand() {
/* 17 */     super(Material.sand, MapColor.brownColor);
/* 18 */     setCreativeTab(CreativeTabs.tabBlock);
/*    */   }
/*    */ 
/*    */   
/*    */   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
/* 23 */     float f = 0.125F;
/* 24 */     return new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), (pos.getX() + 1), ((pos.getY() + 1) - f), (pos.getZ() + 1));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
/* 32 */     if (NoSlow.getInstance.isEnabled() && ((Boolean)NoSlow.getInstance.antiSoulSand
/* 33 */       .get()).booleanValue()) {
/*    */       return;
/*    */     }
/* 36 */     entityIn.motionX *= 0.4D;
/* 37 */     entityIn.motionZ *= 0.4D;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\block\BlockSoulSand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */