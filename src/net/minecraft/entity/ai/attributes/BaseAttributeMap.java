/*    */ package net.minecraft.entity.ai.attributes;
/*    */ 
/*    */ import com.google.common.collect.HashMultimap;
/*    */ import com.google.common.collect.Maps;
/*    */ import com.google.common.collect.Multimap;
/*    */ import java.util.Collection;
/*    */ import java.util.Map;
/*    */ import net.minecraft.server.management.LowerStringMap;
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class BaseAttributeMap
/*    */ {
/* 14 */   protected final Map<IAttribute, IAttributeInstance> attributes = Maps.newHashMap();
/* 15 */   protected final Map<String, IAttributeInstance> attributesByName = (Map<String, IAttributeInstance>)new LowerStringMap();
/* 16 */   protected final Multimap<IAttribute, IAttribute> field_180377_c = (Multimap<IAttribute, IAttribute>)HashMultimap.create();
/*    */ 
/*    */   
/*    */   public IAttributeInstance getAttributeInstance(IAttribute attribute) {
/* 20 */     return this.attributes.get(attribute);
/*    */   }
/*    */ 
/*    */   
/*    */   public IAttributeInstance getAttributeInstanceByName(String attributeName) {
/* 25 */     return this.attributesByName.get(attributeName);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IAttributeInstance registerAttribute(IAttribute attribute) {
/* 33 */     if (this.attributesByName.containsKey(attribute.getAttributeUnlocalizedName()))
/*    */     {
/* 35 */       throw new IllegalArgumentException("Attribute is already registered!");
/*    */     }
/*    */ 
/*    */     
/* 39 */     IAttributeInstance iattributeinstance = func_180376_c(attribute);
/* 40 */     this.attributesByName.put(attribute.getAttributeUnlocalizedName(), iattributeinstance);
/* 41 */     this.attributes.put(attribute, iattributeinstance);
/*    */     
/* 43 */     for (IAttribute iattribute = attribute.func_180372_d(); iattribute != null; iattribute = iattribute.func_180372_d())
/*    */     {
/* 45 */       this.field_180377_c.put(iattribute, attribute);
/*    */     }
/*    */     
/* 48 */     return iattributeinstance;
/*    */   }
/*    */ 
/*    */   
/*    */   protected abstract IAttributeInstance func_180376_c(IAttribute paramIAttribute);
/*    */ 
/*    */   
/*    */   public Collection<IAttributeInstance> getAllAttributes() {
/* 56 */     return this.attributesByName.values();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void func_180794_a(IAttributeInstance instance) {}
/*    */ 
/*    */   
/*    */   public void removeAttributeModifiers(Multimap<String, AttributeModifier> modifiers) {
/* 65 */     for (Map.Entry<String, AttributeModifier> entry : (Iterable<Map.Entry<String, AttributeModifier>>)modifiers.entries()) {
/*    */       
/* 67 */       IAttributeInstance iattributeinstance = getAttributeInstanceByName(entry.getKey());
/*    */       
/* 69 */       if (iattributeinstance != null)
/*    */       {
/* 71 */         iattributeinstance.removeModifier(entry.getValue());
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void applyAttributeModifiers(Multimap<String, AttributeModifier> modifiers) {
/* 78 */     for (Map.Entry<String, AttributeModifier> entry : (Iterable<Map.Entry<String, AttributeModifier>>)modifiers.entries()) {
/*    */       
/* 80 */       IAttributeInstance iattributeinstance = getAttributeInstanceByName(entry.getKey());
/*    */       
/* 82 */       if (iattributeinstance != null) {
/*    */         
/* 84 */         iattributeinstance.removeModifier(entry.getValue());
/* 85 */         iattributeinstance.applyModifier(entry.getValue());
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\entity\ai\attributes\BaseAttributeMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */