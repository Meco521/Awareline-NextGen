/*     */ package net.minecraft.block.state;
/*     */ 
/*     */ import com.google.common.base.Function;
/*     */ import com.google.common.base.Joiner;
/*     */ import com.google.common.collect.ImmutableTable;
/*     */ import com.google.common.collect.Iterables;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ public abstract class BlockStateBase
/*     */   implements IBlockState
/*     */ {
/*  17 */   private static final Joiner COMMA_JOINER = Joiner.on(',');
/*  18 */   private static final Function<Map.Entry<IProperty, Comparable>, String> MAP_ENTRY_TO_STRING = new Function<Map.Entry<IProperty, Comparable>, String>()
/*     */     {
/*     */       public String apply(Map.Entry<IProperty, Comparable> p_apply_1_)
/*     */       {
/*  22 */         if (p_apply_1_ == null)
/*     */         {
/*  24 */           return "<NULL>";
/*     */         }
/*     */ 
/*     */         
/*  28 */         IProperty iproperty = p_apply_1_.getKey();
/*  29 */         return iproperty.getName() + "=" + iproperty.getName(p_apply_1_.getValue());
/*     */       }
/*     */     };
/*     */   
/*  33 */   private int blockId = -1;
/*  34 */   private int blockStateId = -1;
/*  35 */   private int metadata = -1;
/*  36 */   private ResourceLocation blockLocation = null;
/*     */ 
/*     */   
/*     */   public int getBlockId() {
/*  40 */     if (this.blockId < 0)
/*     */     {
/*  42 */       this.blockId = Block.getIdFromBlock(getBlock());
/*     */     }
/*     */     
/*  45 */     return this.blockId;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBlockStateId() {
/*  50 */     if (this.blockStateId < 0)
/*     */     {
/*  52 */       this.blockStateId = Block.getStateId(this);
/*     */     }
/*     */     
/*  55 */     return this.blockStateId;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMetadata() {
/*  60 */     if (this.metadata < 0)
/*     */     {
/*  62 */       this.metadata = getBlock().getMetaFromState(this);
/*     */     }
/*     */     
/*  65 */     return this.metadata;
/*     */   }
/*     */ 
/*     */   
/*     */   public ResourceLocation getBlockLocation() {
/*  70 */     if (this.blockLocation == null)
/*     */     {
/*  72 */       this.blockLocation = (ResourceLocation)Block.blockRegistry.getNameForObject(getBlock());
/*     */     }
/*     */     
/*  75 */     return this.blockLocation;
/*     */   }
/*     */ 
/*     */   
/*     */   public ImmutableTable<IProperty<?>, Comparable<?>, IBlockState> getPropertyValueTable() {
/*  80 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public <T extends Comparable<T>> IBlockState cycleProperty(IProperty<T> property) {
/*  85 */     return withProperty(property, cyclePropertyValue(property.getAllowedValues(), getValue(property)));
/*     */   }
/*     */ 
/*     */   
/*     */   protected static <T> T cyclePropertyValue(Collection<T> values, T currentValue) {
/*  90 */     Iterator<T> iterator = values.iterator();
/*     */     
/*  92 */     while (iterator.hasNext()) {
/*     */       
/*  94 */       if (iterator.next().equals(currentValue)) {
/*     */         
/*  96 */         if (iterator.hasNext())
/*     */         {
/*  98 */           return iterator.next();
/*     */         }
/*     */         
/* 101 */         return values.iterator().next();
/*     */       } 
/*     */     } 
/*     */     
/* 105 */     return iterator.next();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 110 */     StringBuilder stringbuilder = new StringBuilder();
/* 111 */     stringbuilder.append(Block.blockRegistry.getNameForObject(getBlock()));
/*     */     
/* 113 */     if (!getProperties().isEmpty()) {
/*     */       
/* 115 */       stringbuilder.append("[");
/* 116 */       COMMA_JOINER.appendTo(stringbuilder, Iterables.transform((Iterable)getProperties().entrySet(), MAP_ENTRY_TO_STRING));
/* 117 */       stringbuilder.append("]");
/*     */     } 
/*     */     
/* 120 */     return stringbuilder.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\block\state\BlockStateBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */