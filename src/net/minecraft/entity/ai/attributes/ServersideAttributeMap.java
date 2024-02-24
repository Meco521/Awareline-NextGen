/*    */ package net.minecraft.entity.ai.attributes;
/*    */ 
/*    */ import com.google.common.collect.Sets;
/*    */ import java.util.Collection;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import net.minecraft.server.management.LowerStringMap;
/*    */ 
/*    */ public class ServersideAttributeMap
/*    */   extends BaseAttributeMap
/*    */ {
/* 12 */   private final Set<IAttributeInstance> attributeInstanceSet = Sets.newHashSet();
/* 13 */   protected final Map<String, IAttributeInstance> descriptionToAttributeInstanceMap = (Map<String, IAttributeInstance>)new LowerStringMap();
/*    */ 
/*    */   
/*    */   public ModifiableAttributeInstance getAttributeInstance(IAttribute attribute) {
/* 17 */     return (ModifiableAttributeInstance)super.getAttributeInstance(attribute);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModifiableAttributeInstance getAttributeInstanceByName(String attributeName) {
/* 22 */     IAttributeInstance iattributeinstance = super.getAttributeInstanceByName(attributeName);
/*    */     
/* 24 */     if (iattributeinstance == null)
/*    */     {
/* 26 */       iattributeinstance = this.descriptionToAttributeInstanceMap.get(attributeName);
/*    */     }
/*    */     
/* 29 */     return (ModifiableAttributeInstance)iattributeinstance;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IAttributeInstance registerAttribute(IAttribute attribute) {
/* 37 */     IAttributeInstance iattributeinstance = super.registerAttribute(attribute);
/*    */     
/* 39 */     if (attribute instanceof RangedAttribute && ((RangedAttribute)attribute).getDescription() != null)
/*    */     {
/* 41 */       this.descriptionToAttributeInstanceMap.put(((RangedAttribute)attribute).getDescription(), iattributeinstance);
/*    */     }
/*    */     
/* 44 */     return iattributeinstance;
/*    */   }
/*    */ 
/*    */   
/*    */   protected IAttributeInstance func_180376_c(IAttribute attribute) {
/* 49 */     return new ModifiableAttributeInstance(this, attribute);
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_180794_a(IAttributeInstance instance) {
/* 54 */     if (instance.getAttribute().getShouldWatch())
/*    */     {
/* 56 */       this.attributeInstanceSet.add(instance);
/*    */     }
/*    */     
/* 59 */     for (IAttribute iattribute : this.field_180377_c.get(instance.getAttribute())) {
/*    */       
/* 61 */       ModifiableAttributeInstance modifiableattributeinstance = getAttributeInstance(iattribute);
/*    */       
/* 63 */       if (modifiableattributeinstance != null)
/*    */       {
/* 65 */         modifiableattributeinstance.flagForUpdate();
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<IAttributeInstance> getAttributeInstanceSet() {
/* 72 */     return this.attributeInstanceSet;
/*    */   }
/*    */ 
/*    */   
/*    */   public Collection<IAttributeInstance> getWatchedAttributes() {
/* 77 */     Set<IAttributeInstance> set = Sets.newHashSet();
/*    */     
/* 79 */     for (IAttributeInstance iattributeinstance : getAllAttributes()) {
/*    */       
/* 81 */       if (iattributeinstance.getAttribute().getShouldWatch())
/*    */       {
/* 83 */         set.add(iattributeinstance);
/*    */       }
/*    */     } 
/*    */     
/* 87 */     return set;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\entity\ai\attributes\ServersideAttributeMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */