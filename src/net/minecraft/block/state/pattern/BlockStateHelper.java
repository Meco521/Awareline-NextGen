/*    */ package net.minecraft.block.state.pattern;
/*    */ 
/*    */ import com.google.common.base.Predicate;
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.Map;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.properties.IProperty;
/*    */ import net.minecraft.block.state.BlockState;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ 
/*    */ 
/*    */ public class BlockStateHelper
/*    */   implements Predicate<IBlockState>
/*    */ {
/*    */   private final BlockState blockstate;
/* 16 */   private final Map<IProperty, Predicate> propertyPredicates = Maps.newHashMap();
/*    */ 
/*    */   
/*    */   private BlockStateHelper(BlockState blockStateIn) {
/* 20 */     this.blockstate = blockStateIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public static BlockStateHelper forBlock(Block blockIn) {
/* 25 */     return new BlockStateHelper(blockIn.getBlockState());
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean apply(IBlockState p_apply_1_) {
/* 30 */     if (p_apply_1_ != null && p_apply_1_.getBlock().equals(this.blockstate.getBlock())) {
/*    */       
/* 32 */       for (Map.Entry<IProperty, Predicate> entry : this.propertyPredicates.entrySet()) {
/*    */         
/* 34 */         Object object = p_apply_1_.getValue(entry.getKey());
/*    */         
/* 36 */         if (!((Predicate)entry.getValue()).apply(object))
/*    */         {
/* 38 */           return false;
/*    */         }
/*    */       } 
/*    */       
/* 42 */       return true;
/*    */     } 
/*    */ 
/*    */     
/* 46 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public <V extends Comparable<V>> BlockStateHelper where(IProperty<V> property, Predicate<? extends V> is) {
/* 52 */     if (!this.blockstate.getProperties().contains(property))
/*    */     {
/* 54 */       throw new IllegalArgumentException(this.blockstate + " cannot support property " + property);
/*    */     }
/*    */ 
/*    */     
/* 58 */     this.propertyPredicates.put(property, is);
/* 59 */     return this;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\block\state\pattern\BlockStateHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */