/*    */ package net.minecraft.client.renderer.block.statemap;
/*    */ 
/*    */ import com.google.common.base.Objects;
/*    */ import com.google.common.collect.Maps;
/*    */ import com.google.common.collect.Sets;
/*    */ import java.util.Collections;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.client.resources.model.ModelResourceLocation;
/*    */ 
/*    */ 
/*    */ public class BlockStateMapper
/*    */ {
/* 16 */   private final Map<Block, IStateMapper> blockStateMap = Maps.newIdentityHashMap();
/* 17 */   private final Set<Block> setBuiltInBlocks = Sets.newIdentityHashSet();
/*    */ 
/*    */   
/*    */   public void registerBlockStateMapper(Block p_178447_1_, IStateMapper p_178447_2_) {
/* 21 */     this.blockStateMap.put(p_178447_1_, p_178447_2_);
/*    */   }
/*    */ 
/*    */   
/*    */   public void registerBuiltInBlocks(Block... p_178448_1_) {
/* 26 */     Collections.addAll(this.setBuiltInBlocks, p_178448_1_);
/*    */   }
/*    */ 
/*    */   
/*    */   public Map<IBlockState, ModelResourceLocation> putAllStateModelLocations() {
/* 31 */     Map<IBlockState, ModelResourceLocation> map = Maps.newIdentityHashMap();
/*    */     
/* 33 */     for (Block block : Block.blockRegistry) {
/*    */       
/* 35 */       if (!this.setBuiltInBlocks.contains(block))
/*    */       {
/* 37 */         map.putAll(((IStateMapper)Objects.firstNonNull(this.blockStateMap.get(block), new DefaultStateMapper())).putStateModelLocations(block));
/*    */       }
/*    */     } 
/*    */     
/* 41 */     return map;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\block\statemap\BlockStateMapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */