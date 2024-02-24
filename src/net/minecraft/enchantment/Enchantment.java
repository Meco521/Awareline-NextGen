/*     */ package net.minecraft.enchantment;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.EnumCreatureAttribute;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.StatCollector;
/*     */ 
/*     */ 
/*     */ public abstract class Enchantment
/*     */ {
/*  19 */   private static final Enchantment[] enchantmentsList = new Enchantment[256];
/*     */   public static final Enchantment[] enchantmentsBookList;
/*  21 */   private static final Map<ResourceLocation, Enchantment> locationEnchantments = Maps.newHashMap();
/*  22 */   public static final Enchantment protection = new EnchantmentProtection(0, new ResourceLocation("protection"), 10, 0);
/*     */ 
/*     */   
/*  25 */   public static final Enchantment fireProtection = new EnchantmentProtection(1, new ResourceLocation("fire_protection"), 5, 1);
/*  26 */   public static final Enchantment featherFalling = new EnchantmentProtection(2, new ResourceLocation("feather_falling"), 5, 2);
/*     */ 
/*     */   
/*  29 */   public static final Enchantment blastProtection = new EnchantmentProtection(3, new ResourceLocation("blast_protection"), 2, 3);
/*  30 */   public static final Enchantment projectileProtection = new EnchantmentProtection(4, new ResourceLocation("projectile_protection"), 5, 4);
/*  31 */   public static final Enchantment respiration = new EnchantmentOxygen(5, new ResourceLocation("respiration"), 2);
/*     */ 
/*     */   
/*  34 */   public static final Enchantment aquaAffinity = new EnchantmentWaterWorker(6, new ResourceLocation("aqua_affinity"), 2);
/*  35 */   public static final Enchantment thorns = new EnchantmentThorns(7, new ResourceLocation("thorns"), 1);
/*  36 */   public static final Enchantment depthStrider = new EnchantmentWaterWalker(8, new ResourceLocation("depth_strider"), 2);
/*  37 */   public static final Enchantment sharpness = new EnchantmentDamage(16, new ResourceLocation("sharpness"), 10, 0);
/*  38 */   public static final Enchantment smite = new EnchantmentDamage(17, new ResourceLocation("smite"), 5, 1);
/*  39 */   public static final Enchantment baneOfArthropods = new EnchantmentDamage(18, new ResourceLocation("bane_of_arthropods"), 5, 2);
/*  40 */   public static final Enchantment knockback = new EnchantmentKnockback(19, new ResourceLocation("knockback"), 5);
/*     */ 
/*     */   
/*  43 */   public static final Enchantment fireAspect = new EnchantmentFireAspect(20, new ResourceLocation("fire_aspect"), 2);
/*     */ 
/*     */   
/*  46 */   public static final Enchantment looting = new EnchantmentLootBonus(21, new ResourceLocation("looting"), 2, EnumEnchantmentType.WEAPON);
/*     */ 
/*     */   
/*  49 */   public static final Enchantment efficiency = new EnchantmentDigging(32, new ResourceLocation("efficiency"), 10);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  55 */   public static final Enchantment silkTouch = new EnchantmentUntouching(33, new ResourceLocation("silk_touch"), 1);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  60 */   public static final Enchantment unbreaking = new EnchantmentDurability(34, new ResourceLocation("unbreaking"), 5);
/*     */ 
/*     */   
/*  63 */   public static final Enchantment fortune = new EnchantmentLootBonus(35, new ResourceLocation("fortune"), 2, EnumEnchantmentType.DIGGER);
/*     */ 
/*     */   
/*  66 */   public static final Enchantment power = new EnchantmentArrowDamage(48, new ResourceLocation("power"), 10);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  71 */   public static final Enchantment punch = new EnchantmentArrowKnockback(49, new ResourceLocation("punch"), 2);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  76 */   public static final Enchantment flame = new EnchantmentArrowFire(50, new ResourceLocation("flame"), 2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  82 */   public static final Enchantment infinity = new EnchantmentArrowInfinite(51, new ResourceLocation("infinity"), 1);
/*  83 */   public static final Enchantment luckOfTheSea = new EnchantmentLootBonus(61, new ResourceLocation("luck_of_the_sea"), 2, EnumEnchantmentType.FISHING_ROD);
/*  84 */   public static final Enchantment lure = new EnchantmentFishingSpeed(62, new ResourceLocation("lure"), 2, EnumEnchantmentType.FISHING_ROD);
/*     */ 
/*     */   
/*     */   public final int effectId;
/*     */ 
/*     */   
/*     */   private final int weight;
/*     */ 
/*     */   
/*     */   public EnumEnchantmentType type;
/*     */   
/*     */   protected String name;
/*     */ 
/*     */   
/*     */   public static Enchantment getEnchantmentById(int enchID) {
/*  99 */     return (enchID >= 0 && enchID < enchantmentsList.length) ? enchantmentsList[enchID] : null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Enchantment(int enchID, ResourceLocation enchName, int enchWeight, EnumEnchantmentType enchType) {
/* 104 */     this.effectId = enchID;
/* 105 */     this.weight = enchWeight;
/* 106 */     this.type = enchType;
/*     */     
/* 108 */     if (enchantmentsList[enchID] != null)
/*     */     {
/* 110 */       throw new IllegalArgumentException("Duplicate enchantment id!");
/*     */     }
/*     */ 
/*     */     
/* 114 */     enchantmentsList[enchID] = this;
/* 115 */     locationEnchantments.put(enchName, this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Enchantment getEnchantmentByLocation(String location) {
/* 124 */     return locationEnchantments.get(new ResourceLocation(location));
/*     */   }
/*     */ 
/*     */   
/*     */   public static Set<ResourceLocation> func_181077_c() {
/* 129 */     return locationEnchantments.keySet();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWeight() {
/* 138 */     return this.weight;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMinLevel() {
/* 146 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxLevel() {
/* 154 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMinEnchantability(int enchantmentLevel) {
/* 162 */     return 1 + enchantmentLevel * 10;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxEnchantability(int enchantmentLevel) {
/* 170 */     return getMinEnchantability(enchantmentLevel) + 5;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int calcModifierDamage(int level, DamageSource source) {
/* 178 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float calcDamageByCreature(int level, EnumCreatureAttribute creatureType) {
/* 187 */     return 0.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canApplyTogether(Enchantment ench) {
/* 195 */     return (this != ench);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Enchantment setName(String enchName) {
/* 203 */     this.name = enchName;
/* 204 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 212 */     return "enchantment." + this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTranslatedName(int level) {
/* 220 */     String s = StatCollector.translateToLocal(getName());
/* 221 */     return s + " " + StatCollector.translateToLocal("enchantment.level." + level);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canApply(ItemStack stack) {
/* 229 */     return this.type.canEnchantItem(stack.getItem());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEntityDamaged(EntityLivingBase user, Entity target, int level) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUserHurt(EntityLivingBase user, Entity attacker, int level) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 249 */     List<Enchantment> list = Lists.newArrayList();
/*     */     
/* 251 */     for (Enchantment enchantment : enchantmentsList) {
/*     */       
/* 253 */       if (enchantment != null)
/*     */       {
/* 255 */         list.add(enchantment);
/*     */       }
/*     */     } 
/*     */     
/* 259 */     enchantmentsBookList = list.<Enchantment>toArray(new Enchantment[0]);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\enchantment\Enchantment.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */