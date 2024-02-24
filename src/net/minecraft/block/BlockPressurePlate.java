/*    */ package net.minecraft.block;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.properties.IProperty;
/*    */ import net.minecraft.block.properties.PropertyBool;
/*    */ import net.minecraft.block.state.BlockState;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.util.AxisAlignedBB;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class BlockPressurePlate
/*    */   extends BlockBasePressurePlate
/*    */ {
/* 18 */   public static final PropertyBool POWERED = PropertyBool.create("powered");
/*    */   
/*    */   private final Sensitivity sensitivity;
/*    */   
/*    */   protected BlockPressurePlate(Material materialIn, Sensitivity sensitivityIn) {
/* 23 */     super(materialIn);
/* 24 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)POWERED, Boolean.valueOf(false)));
/* 25 */     this.sensitivity = sensitivityIn;
/*    */   }
/*    */ 
/*    */   
/*    */   protected int getRedstoneStrength(IBlockState state) {
/* 30 */     return ((Boolean)state.getValue((IProperty)POWERED)).booleanValue() ? 15 : 0;
/*    */   }
/*    */ 
/*    */   
/*    */   protected IBlockState setRedstoneStrength(IBlockState state, int strength) {
/* 35 */     return state.withProperty((IProperty)POWERED, Boolean.valueOf((strength > 0)));
/*    */   }
/*    */   
/*    */   protected int computeRedstoneStrength(World worldIn, BlockPos pos) {
/*    */     List<? extends Entity> list;
/* 40 */     AxisAlignedBB axisalignedbb = getSensitiveAABB(pos);
/*    */ 
/*    */     
/* 43 */     switch (this.sensitivity) {
/*    */       
/*    */       case EVERYTHING:
/* 46 */         list = worldIn.getEntitiesWithinAABBExcludingEntity((Entity)null, axisalignedbb);
/*    */         break;
/*    */       
/*    */       case MOBS:
/* 50 */         list = worldIn.getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);
/*    */         break;
/*    */       
/*    */       default:
/* 54 */         return 0;
/*    */     } 
/*    */     
/* 57 */     if (!list.isEmpty())
/*    */     {
/* 59 */       for (Entity entity : list) {
/*    */         
/* 61 */         if (!entity.doesEntityNotTriggerPressurePlate())
/*    */         {
/* 63 */           return 15;
/*    */         }
/*    */       } 
/*    */     }
/*    */     
/* 68 */     return 0;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IBlockState getStateFromMeta(int meta) {
/* 76 */     return getDefaultState().withProperty((IProperty)POWERED, Boolean.valueOf((meta == 1)));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMetaFromState(IBlockState state) {
/* 84 */     return ((Boolean)state.getValue((IProperty)POWERED)).booleanValue() ? 1 : 0;
/*    */   }
/*    */ 
/*    */   
/*    */   protected BlockState createBlockState() {
/* 89 */     return new BlockState(this, new IProperty[] { (IProperty)POWERED });
/*    */   }
/*    */   
/*    */   public enum Sensitivity
/*    */   {
/* 94 */     EVERYTHING,
/* 95 */     MOBS;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\block\BlockPressurePlate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */