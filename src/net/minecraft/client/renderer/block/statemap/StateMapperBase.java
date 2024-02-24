/*    */ package net.minecraft.client.renderer.block.statemap;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import com.google.common.collect.UnmodifiableIterator;
/*    */ import java.util.Map;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.properties.IProperty;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.client.resources.model.ModelResourceLocation;
/*    */ 
/*    */ public abstract class StateMapperBase
/*    */   implements IStateMapper
/*    */ {
/* 14 */   protected Map<IBlockState, ModelResourceLocation> mapStateModelLocations = Maps.newLinkedHashMap();
/*    */ 
/*    */   
/*    */   public String getPropertyString(Map<IProperty, Comparable> p_178131_1_) {
/* 18 */     StringBuilder stringbuilder = new StringBuilder();
/*    */     
/* 20 */     for (Map.Entry<IProperty, Comparable> entry : p_178131_1_.entrySet()) {
/*    */       
/* 22 */       if (stringbuilder.length() != 0)
/*    */       {
/* 24 */         stringbuilder.append(",");
/*    */       }
/*    */       
/* 27 */       IProperty iproperty = entry.getKey();
/* 28 */       Comparable comparable = entry.getValue();
/* 29 */       stringbuilder.append(iproperty.getName());
/* 30 */       stringbuilder.append("=");
/* 31 */       stringbuilder.append(iproperty.getName(comparable));
/*    */     } 
/*    */     
/* 34 */     if (stringbuilder.length() == 0)
/*    */     {
/* 36 */       stringbuilder.append("normal");
/*    */     }
/*    */     
/* 39 */     return stringbuilder.toString();
/*    */   }
/*    */ 
/*    */   
/*    */   public Map<IBlockState, ModelResourceLocation> putStateModelLocations(Block blockIn) {
/* 44 */     for (UnmodifiableIterator<IBlockState> unmodifiableIterator = blockIn.getBlockState().getValidStates().iterator(); unmodifiableIterator.hasNext(); ) { IBlockState iblockstate = unmodifiableIterator.next();
/*    */       
/* 46 */       this.mapStateModelLocations.put(iblockstate, getModelResourceLocation(iblockstate)); }
/*    */ 
/*    */     
/* 49 */     return this.mapStateModelLocations;
/*    */   }
/*    */   
/*    */   protected abstract ModelResourceLocation getModelResourceLocation(IBlockState paramIBlockState);
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\block\statemap\StateMapperBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */