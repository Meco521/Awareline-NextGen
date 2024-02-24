/*    */ package net.minecraft.client.renderer.block.statemap;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.properties.IProperty;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.client.resources.model.ModelResourceLocation;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ 
/*    */ public class StateMap
/*    */   extends StateMapperBase
/*    */ {
/*    */   private final IProperty<?> name;
/*    */   private final String suffix;
/*    */   private final List<IProperty<?>> ignored;
/*    */   
/*    */   StateMap(IProperty<?> name, String suffix, List<IProperty<?>> ignored) {
/* 23 */     this.name = name;
/* 24 */     this.suffix = suffix;
/* 25 */     this.ignored = ignored;
/*    */   }
/*    */   
/*    */   protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
/*    */     String s;
/* 30 */     Map<IProperty, Comparable> map = Maps.newLinkedHashMap((Map)state.getProperties());
/*    */ 
/*    */     
/* 33 */     if (this.name == null) {
/*    */       
/* 35 */       s = ((ResourceLocation)Block.blockRegistry.getNameForObject(state.getBlock())).toString();
/*    */     }
/*    */     else {
/*    */       
/* 39 */       s = this.name.getName(map.remove(this.name));
/*    */     } 
/*    */     
/* 42 */     if (this.suffix != null)
/*    */     {
/* 44 */       s = s + this.suffix;
/*    */     }
/*    */     
/* 47 */     for (IProperty<?> iproperty : this.ignored)
/*    */     {
/* 49 */       map.remove(iproperty);
/*    */     }
/*    */     
/* 52 */     return new ModelResourceLocation(s, getPropertyString(map));
/*    */   }
/*    */   
/*    */   public static class Builder
/*    */   {
/*    */     private IProperty<?> name;
/*    */     private String suffix;
/* 59 */     private final List<IProperty<?>> ignored = Lists.newArrayList();
/*    */ 
/*    */     
/*    */     public Builder withName(IProperty<?> builderPropertyIn) {
/* 63 */       this.name = builderPropertyIn;
/* 64 */       return this;
/*    */     }
/*    */ 
/*    */     
/*    */     public Builder withSuffix(String builderSuffixIn) {
/* 69 */       this.suffix = builderSuffixIn;
/* 70 */       return this;
/*    */     }
/*    */ 
/*    */     
/*    */     public Builder ignore(IProperty<?>... p_178442_1_) {
/* 75 */       Collections.addAll(this.ignored, p_178442_1_);
/* 76 */       return this;
/*    */     }
/*    */ 
/*    */     
/*    */     public StateMap build() {
/* 81 */       return new StateMap(this.name, this.suffix, this.ignored);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\block\statemap\StateMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */