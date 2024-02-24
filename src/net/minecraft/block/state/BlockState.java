/*     */ package net.minecraft.block.state;
/*     */ import com.google.common.base.Function;
/*     */ import com.google.common.collect.HashBasedTable;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import com.google.common.collect.ImmutableTable;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.UnmodifiableIterator;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ 
/*     */ public class BlockState {
/*  16 */   private static final Joiner COMMA_JOINER = Joiner.on(", ");
/*  17 */   private static final Function<IProperty, String> GET_NAME_FUNC = new Function<IProperty, String>()
/*     */     {
/*     */       public String apply(IProperty p_apply_1_)
/*     */       {
/*  21 */         return (p_apply_1_ == null) ? "<NULL>" : p_apply_1_.getName();
/*     */       }
/*     */     };
/*     */   
/*     */   private final Block block;
/*     */   private final ImmutableList<IProperty> properties;
/*     */   private final ImmutableList<IBlockState> validStates;
/*     */   
/*     */   public BlockState(Block blockIn, IProperty... properties) {
/*  30 */     this.block = blockIn;
/*  31 */     Arrays.sort(properties, new Comparator<IProperty>()
/*     */         {
/*     */           public int compare(IProperty p_compare_1_, IProperty p_compare_2_)
/*     */           {
/*  35 */             return p_compare_1_.getName().compareTo(p_compare_2_.getName());
/*     */           }
/*     */         });
/*  38 */     this.properties = ImmutableList.copyOf((Object[])properties);
/*  39 */     Map<Map<IProperty, Comparable>, StateImplementation> map = Maps.newLinkedHashMap();
/*  40 */     List<StateImplementation> list = Lists.newArrayList();
/*     */     
/*  42 */     for (List<Comparable> list1 : (Iterable<List<Comparable>>)Cartesian.cartesianProduct(getAllowedValues())) {
/*     */       
/*  44 */       Map<IProperty, Comparable> map1 = MapPopulator.createMap((Iterable)this.properties, list1);
/*  45 */       StateImplementation blockstate$stateimplementation = new StateImplementation(blockIn, ImmutableMap.copyOf(map1));
/*  46 */       map.put(map1, blockstate$stateimplementation);
/*  47 */       list.add(blockstate$stateimplementation);
/*     */     } 
/*     */     
/*  50 */     for (StateImplementation blockstate$stateimplementation1 : list)
/*     */     {
/*  52 */       blockstate$stateimplementation1.buildPropertyValueTable(map);
/*     */     }
/*     */     
/*  55 */     this.validStates = ImmutableList.copyOf(list);
/*     */   }
/*     */ 
/*     */   
/*     */   public ImmutableList<IBlockState> getValidStates() {
/*  60 */     return this.validStates;
/*     */   }
/*     */ 
/*     */   
/*     */   private List<Iterable<Comparable>> getAllowedValues() {
/*  65 */     List<Iterable<Comparable>> list = Lists.newArrayList();
/*     */     
/*  67 */     for (int i = 0; i < this.properties.size(); i++)
/*     */     {
/*  69 */       list.add(((IProperty)this.properties.get(i)).getAllowedValues());
/*     */     }
/*     */     
/*  72 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public IBlockState getBaseState() {
/*  77 */     return (IBlockState)this.validStates.get(0);
/*     */   }
/*     */ 
/*     */   
/*     */   public Block getBlock() {
/*  82 */     return this.block;
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<IProperty> getProperties() {
/*  87 */     return (Collection<IProperty>)this.properties;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  92 */     return Objects.toStringHelper(this).add("block", Block.blockRegistry.getNameForObject(this.block)).add("properties", Iterables.transform((Iterable)this.properties, GET_NAME_FUNC)).toString();
/*     */   }
/*     */   
/*     */   static class StateImplementation
/*     */     extends BlockStateBase
/*     */   {
/*     */     private final Block block;
/*     */     private final ImmutableMap<IProperty, Comparable> properties;
/*     */     private ImmutableTable<IProperty, Comparable, IBlockState> propertyValueTable;
/*     */     
/*     */     StateImplementation(Block blockIn, ImmutableMap<IProperty, Comparable> propertiesIn) {
/* 103 */       this.block = blockIn;
/* 104 */       this.properties = propertiesIn;
/*     */     }
/*     */ 
/*     */     
/*     */     public Collection<IProperty> getPropertyNames() {
/* 109 */       return Collections.unmodifiableCollection((Collection<? extends IProperty>)this.properties.keySet());
/*     */     }
/*     */ 
/*     */     
/*     */     public <T extends Comparable<T>> T getValue(IProperty<T> property) {
/* 114 */       if (!this.properties.containsKey(property))
/*     */       {
/* 116 */         throw new IllegalArgumentException("Cannot get property " + property + " as it does not exist in " + this.block.getBlockState());
/*     */       }
/*     */ 
/*     */       
/* 120 */       return (T)property.getValueClass().cast(this.properties.get(property));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public <T extends Comparable<T>, V extends T> IBlockState withProperty(IProperty<T> property, V value) {
/* 126 */       if (!this.properties.containsKey(property))
/*     */       {
/* 128 */         throw new IllegalArgumentException("Cannot set property " + property + " as it does not exist in " + this.block.getBlockState());
/*     */       }
/* 130 */       if (!property.getAllowedValues().contains(value))
/*     */       {
/* 132 */         throw new IllegalArgumentException("Cannot set property " + property + " to " + value + " on block " + Block.blockRegistry.getNameForObject(this.block) + ", it is not an allowed value");
/*     */       }
/*     */ 
/*     */       
/* 136 */       return (this.properties.get(property) == value) ? this : (IBlockState)this.propertyValueTable.get(property, value);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ImmutableMap<IProperty, Comparable> getProperties() {
/* 142 */       return this.properties;
/*     */     }
/*     */ 
/*     */     
/*     */     public Block getBlock() {
/* 147 */       return this.block;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object p_equals_1_) {
/* 152 */       return (this == p_equals_1_);
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 157 */       return this.properties.hashCode();
/*     */     }
/*     */ 
/*     */     
/*     */     public void buildPropertyValueTable(Map<Map<IProperty, Comparable>, StateImplementation> map) {
/* 162 */       if (this.propertyValueTable != null)
/*     */       {
/* 164 */         throw new IllegalStateException();
/*     */       }
/*     */ 
/*     */       
/* 168 */       HashBasedTable hashBasedTable = HashBasedTable.create();
/*     */       
/* 170 */       for (UnmodifiableIterator<Map.Entry<IProperty, Comparable>> unmodifiableIterator = this.properties.entrySet().iterator(); unmodifiableIterator.hasNext(); ) { Map.Entry<IProperty, Comparable> entry = unmodifiableIterator.next();
/*     */         
/* 172 */         IProperty<? extends Comparable> iproperty = entry.getKey();
/* 173 */         for (Comparable comparable : iproperty.getAllowedValues()) {
/*     */           
/* 175 */           if (comparable != entry.getValue())
/*     */           {
/* 177 */             hashBasedTable.put(iproperty, comparable, map.get(getPropertiesWithValue(iproperty, comparable)));
/*     */           }
/*     */         }  }
/*     */ 
/*     */       
/* 182 */       this.propertyValueTable = ImmutableTable.copyOf((Table)hashBasedTable);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private Map<IProperty, Comparable> getPropertiesWithValue(IProperty property, Comparable value) {
/* 188 */       Map<IProperty, Comparable> map = Maps.newHashMap((Map)this.properties);
/* 189 */       map.put(property, value);
/* 190 */       return map;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\block\state\BlockState.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */