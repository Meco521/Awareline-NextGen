/*     */ package net.minecraft.entity;
/*     */ import java.util.Collection;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*     */ import net.minecraft.entity.ai.attributes.BaseAttributeMap;
/*     */ import net.minecraft.entity.ai.attributes.IAttribute;
/*     */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*     */ import net.minecraft.entity.ai.attributes.RangedAttribute;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ 
/*     */ public class SharedMonsterAttributes {
/*  14 */   private static final Logger logger = LogManager.getLogger();
/*  15 */   public static final IAttribute maxHealth = (IAttribute)(new RangedAttribute((IAttribute)null, "generic.maxHealth", 20.0D, 0.0D, 1024.0D)).setDescription("Max Health").setShouldWatch(true);
/*  16 */   public static final IAttribute followRange = (IAttribute)(new RangedAttribute((IAttribute)null, "generic.followRange", 32.0D, 0.0D, 2048.0D)).setDescription("Follow Range");
/*  17 */   public static final IAttribute knockbackResistance = (IAttribute)(new RangedAttribute((IAttribute)null, "generic.knockbackResistance", 0.0D, 0.0D, 1.0D)).setDescription("Knockback Resistance");
/*  18 */   public static final IAttribute movementSpeed = (IAttribute)(new RangedAttribute((IAttribute)null, "generic.movementSpeed", 0.699999988079071D, 0.0D, 1024.0D)).setDescription("Movement Speed").setShouldWatch(true);
/*  19 */   public static final IAttribute attackDamage = (IAttribute)new RangedAttribute((IAttribute)null, "generic.attackDamage", 2.0D, 0.0D, 2048.0D);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static NBTTagList writeBaseAttributeMapToNBT(BaseAttributeMap map) {
/*  26 */     NBTTagList nbttaglist = new NBTTagList();
/*     */     
/*  28 */     for (IAttributeInstance iattributeinstance : map.getAllAttributes())
/*     */     {
/*  30 */       nbttaglist.appendTag((NBTBase)writeAttributeInstanceToNBT(iattributeinstance));
/*     */     }
/*     */     
/*  33 */     return nbttaglist;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static NBTTagCompound writeAttributeInstanceToNBT(IAttributeInstance instance) {
/*  41 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/*  42 */     IAttribute iattribute = instance.getAttribute();
/*  43 */     nbttagcompound.setString("Name", iattribute.getAttributeUnlocalizedName());
/*  44 */     nbttagcompound.setDouble("Base", instance.getBaseValue());
/*  45 */     Collection<AttributeModifier> collection = instance.func_111122_c();
/*     */     
/*  47 */     if (collection != null && !collection.isEmpty()) {
/*     */       
/*  49 */       NBTTagList nbttaglist = new NBTTagList();
/*     */       
/*  51 */       for (AttributeModifier attributemodifier : collection) {
/*     */         
/*  53 */         if (attributemodifier.isSaved())
/*     */         {
/*  55 */           nbttaglist.appendTag((NBTBase)writeAttributeModifierToNBT(attributemodifier));
/*     */         }
/*     */       } 
/*     */       
/*  59 */       nbttagcompound.setTag("Modifiers", (NBTBase)nbttaglist);
/*     */     } 
/*     */     
/*  62 */     return nbttagcompound;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static NBTTagCompound writeAttributeModifierToNBT(AttributeModifier modifier) {
/*  70 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/*  71 */     nbttagcompound.setString("Name", modifier.getName());
/*  72 */     nbttagcompound.setDouble("Amount", modifier.getAmount());
/*  73 */     nbttagcompound.setInteger("Operation", modifier.getOperation());
/*  74 */     nbttagcompound.setLong("UUIDMost", modifier.getID().getMostSignificantBits());
/*  75 */     nbttagcompound.setLong("UUIDLeast", modifier.getID().getLeastSignificantBits());
/*  76 */     return nbttagcompound;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void setAttributeModifiers(BaseAttributeMap map, NBTTagList list) {
/*  81 */     for (int i = 0; i < list.tagCount(); i++) {
/*     */       
/*  83 */       NBTTagCompound nbttagcompound = list.getCompoundTagAt(i);
/*  84 */       IAttributeInstance iattributeinstance = map.getAttributeInstanceByName(nbttagcompound.getString("Name"));
/*     */       
/*  86 */       if (iattributeinstance != null) {
/*     */         
/*  88 */         applyModifiersToAttributeInstance(iattributeinstance, nbttagcompound);
/*     */       }
/*     */       else {
/*     */         
/*  92 */         logger.warn("Ignoring unknown attribute '" + nbttagcompound.getString("Name") + "'");
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void applyModifiersToAttributeInstance(IAttributeInstance instance, NBTTagCompound compound) {
/*  99 */     instance.setBaseValue(compound.getDouble("Base"));
/*     */     
/* 101 */     if (compound.hasKey("Modifiers", 9)) {
/*     */       
/* 103 */       NBTTagList nbttaglist = compound.getTagList("Modifiers", 10);
/*     */       
/* 105 */       for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*     */         
/* 107 */         AttributeModifier attributemodifier = readAttributeModifierFromNBT(nbttaglist.getCompoundTagAt(i));
/*     */         
/* 109 */         if (attributemodifier != null) {
/*     */           
/* 111 */           AttributeModifier attributemodifier1 = instance.getModifier(attributemodifier.getID());
/*     */           
/* 113 */           if (attributemodifier1 != null)
/*     */           {
/* 115 */             instance.removeModifier(attributemodifier1);
/*     */           }
/*     */           
/* 118 */           instance.applyModifier(attributemodifier);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static AttributeModifier readAttributeModifierFromNBT(NBTTagCompound compound) {
/* 129 */     UUID uuid = new UUID(compound.getLong("UUIDMost"), compound.getLong("UUIDLeast"));
/*     */ 
/*     */     
/*     */     try {
/* 133 */       return new AttributeModifier(uuid, compound.getString("Name"), compound.getDouble("Amount"), compound.getInteger("Operation"));
/*     */     }
/* 135 */     catch (Exception exception) {
/*     */       
/* 137 */       logger.warn("Unable to create attribute: " + exception.getMessage());
/* 138 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\entity\SharedMonsterAttributes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */