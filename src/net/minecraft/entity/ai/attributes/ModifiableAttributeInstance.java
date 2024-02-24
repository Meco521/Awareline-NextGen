/*     */ package net.minecraft.entity.ai.attributes;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.Collection;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ModifiableAttributeInstance
/*     */   implements IAttributeInstance
/*     */ {
/*     */   private final BaseAttributeMap attributeMap;
/*     */   private final IAttribute genericAttribute;
/*  19 */   private final Map<Integer, Set<AttributeModifier>> mapByOperation = Maps.newHashMap();
/*  20 */   private final Map<String, Set<AttributeModifier>> mapByName = Maps.newHashMap();
/*  21 */   private final Map<UUID, AttributeModifier> mapByUUID = Maps.newHashMap();
/*     */   
/*     */   private double baseValue;
/*     */   private boolean needsUpdate = true;
/*     */   private double cachedValue;
/*     */   
/*     */   public ModifiableAttributeInstance(BaseAttributeMap attributeMapIn, IAttribute genericAttributeIn) {
/*  28 */     this.attributeMap = attributeMapIn;
/*  29 */     this.genericAttribute = genericAttributeIn;
/*  30 */     this.baseValue = genericAttributeIn.getDefaultValue();
/*     */     
/*  32 */     for (int i = 0; i < 3; i++)
/*     */     {
/*  34 */       this.mapByOperation.put(Integer.valueOf(i), Sets.newHashSet());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IAttribute getAttribute() {
/*  43 */     return this.genericAttribute;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getBaseValue() {
/*  48 */     return this.baseValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBaseValue(double baseValue) {
/*  53 */     if (baseValue != this.baseValue) {
/*     */       
/*  55 */       this.baseValue = baseValue;
/*  56 */       flagForUpdate();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<AttributeModifier> getModifiersByOperation(int operation) {
/*  62 */     return this.mapByOperation.get(Integer.valueOf(operation));
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<AttributeModifier> func_111122_c() {
/*  67 */     Set<AttributeModifier> set = Sets.newHashSet();
/*     */     
/*  69 */     for (int i = 0; i < 3; i++)
/*     */     {
/*  71 */       set.addAll(getModifiersByOperation(i));
/*     */     }
/*     */     
/*  74 */     return set;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AttributeModifier getModifier(UUID uuid) {
/*  82 */     return this.mapByUUID.get(uuid);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasModifier(AttributeModifier modifier) {
/*  87 */     return (this.mapByUUID.get(modifier.getID()) != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void applyModifier(AttributeModifier modifier) {
/*  92 */     if (getModifier(modifier.getID()) != null)
/*     */     {
/*  94 */       throw new IllegalArgumentException("Modifier is already applied on this attribute!");
/*     */     }
/*     */ 
/*     */     
/*  98 */     Set<AttributeModifier> set = this.mapByName.get(modifier.getName());
/*     */     
/* 100 */     if (set == null) {
/*     */       
/* 102 */       set = Sets.newHashSet();
/* 103 */       this.mapByName.put(modifier.getName(), set);
/*     */     } 
/*     */     
/* 106 */     ((Set<AttributeModifier>)this.mapByOperation.get(Integer.valueOf(modifier.getOperation()))).add(modifier);
/* 107 */     set.add(modifier);
/* 108 */     this.mapByUUID.put(modifier.getID(), modifier);
/* 109 */     flagForUpdate();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void flagForUpdate() {
/* 115 */     this.needsUpdate = true;
/* 116 */     this.attributeMap.func_180794_a(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeModifier(AttributeModifier modifier) {
/* 121 */     for (int i = 0; i < 3; i++) {
/*     */       
/* 123 */       Set<AttributeModifier> set = this.mapByOperation.get(Integer.valueOf(i));
/* 124 */       set.remove(modifier);
/*     */     } 
/*     */     
/* 127 */     Set<AttributeModifier> set1 = this.mapByName.get(modifier.getName());
/*     */     
/* 129 */     if (set1 != null) {
/*     */       
/* 131 */       set1.remove(modifier);
/*     */       
/* 133 */       if (set1.isEmpty())
/*     */       {
/* 135 */         this.mapByName.remove(modifier.getName());
/*     */       }
/*     */     } 
/*     */     
/* 139 */     this.mapByUUID.remove(modifier.getID());
/* 140 */     flagForUpdate();
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeAllModifiers() {
/* 145 */     Collection<AttributeModifier> collection = func_111122_c();
/*     */     
/* 147 */     if (collection != null)
/*     */     {
/* 149 */       for (AttributeModifier attributemodifier : Lists.newArrayList(collection))
/*     */       {
/* 151 */         removeModifier(attributemodifier);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public double getAttributeValue() {
/* 158 */     if (this.needsUpdate) {
/*     */       
/* 160 */       this.cachedValue = computeValue();
/* 161 */       this.needsUpdate = false;
/*     */     } 
/*     */     
/* 164 */     return this.cachedValue;
/*     */   }
/*     */ 
/*     */   
/*     */   private double computeValue() {
/* 169 */     double d0 = this.baseValue;
/*     */     
/* 171 */     for (AttributeModifier attributemodifier : func_180375_b(0))
/*     */     {
/* 173 */       d0 += attributemodifier.getAmount();
/*     */     }
/*     */     
/* 176 */     double d1 = d0;
/*     */     
/* 178 */     for (AttributeModifier attributemodifier1 : func_180375_b(1))
/*     */     {
/* 180 */       d1 += d0 * attributemodifier1.getAmount();
/*     */     }
/*     */     
/* 183 */     for (AttributeModifier attributemodifier2 : func_180375_b(2))
/*     */     {
/* 185 */       d1 *= 1.0D + attributemodifier2.getAmount();
/*     */     }
/*     */     
/* 188 */     return this.genericAttribute.clampValue(d1);
/*     */   }
/*     */ 
/*     */   
/*     */   private Collection<AttributeModifier> func_180375_b(int operation) {
/* 193 */     Set<AttributeModifier> set = Sets.newHashSet(getModifiersByOperation(operation));
/*     */     
/* 195 */     for (IAttribute iattribute = this.genericAttribute.func_180372_d(); iattribute != null; iattribute = iattribute.func_180372_d()) {
/*     */       
/* 197 */       IAttributeInstance iattributeinstance = this.attributeMap.getAttributeInstance(iattribute);
/*     */       
/* 199 */       if (iattributeinstance != null)
/*     */       {
/* 201 */         set.addAll(iattributeinstance.getModifiersByOperation(operation));
/*     */       }
/*     */     } 
/*     */     
/* 205 */     return set;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\entity\ai\attributes\ModifiableAttributeInstance.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */