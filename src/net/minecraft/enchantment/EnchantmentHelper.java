/*     */ package net.minecraft.enchantment;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.EnumCreatureAttribute;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.WeightedRandom;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EnchantmentHelper
/*     */ {
/*  25 */   private static final Random enchantmentRand = new Random();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  30 */   private static final ModifierDamage enchantmentModifierDamage = new ModifierDamage();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  35 */   private static final ModifierLiving enchantmentModifierLiving = new ModifierLiving();
/*  36 */   private static final HurtIterator ENCHANTMENT_ITERATOR_HURT = new HurtIterator();
/*  37 */   private static final DamageIterator ENCHANTMENT_ITERATOR_DAMAGE = new DamageIterator();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getEnchantmentLevel(int enchID, ItemStack stack) {
/*  44 */     if (stack == null)
/*     */     {
/*  46 */       return 0;
/*     */     }
/*     */ 
/*     */     
/*  50 */     NBTTagList nbttaglist = stack.getEnchantmentTagList();
/*     */     
/*  52 */     if (nbttaglist == null)
/*     */     {
/*  54 */       return 0;
/*     */     }
/*     */ 
/*     */     
/*  58 */     for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*     */       
/*  60 */       int j = nbttaglist.getCompoundTagAt(i).getShort("id");
/*  61 */       int k = nbttaglist.getCompoundTagAt(i).getShort("lvl");
/*     */       
/*  63 */       if (j == enchID)
/*     */       {
/*  65 */         return k;
/*     */       }
/*     */     } 
/*     */     
/*  69 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Map<Integer, Integer> getEnchantments(ItemStack stack) {
/*  76 */     Map<Integer, Integer> map = Maps.newLinkedHashMap();
/*  77 */     NBTTagList nbttaglist = (stack.getItem() == Items.enchanted_book) ? Items.enchanted_book.getEnchantments(stack) : stack.getEnchantmentTagList();
/*     */     
/*  79 */     if (nbttaglist != null)
/*     */     {
/*  81 */       for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*     */         
/*  83 */         int j = nbttaglist.getCompoundTagAt(i).getShort("id");
/*  84 */         int k = nbttaglist.getCompoundTagAt(i).getShort("lvl");
/*  85 */         map.put(Integer.valueOf(j), Integer.valueOf(k));
/*     */       } 
/*     */     }
/*     */     
/*  89 */     return map;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setEnchantments(Map<Integer, Integer> enchMap, ItemStack stack) {
/*  97 */     NBTTagList nbttaglist = new NBTTagList();
/*  98 */     Iterator<Integer> iterator = enchMap.keySet().iterator();
/*     */     
/* 100 */     while (iterator.hasNext()) {
/*     */       
/* 102 */       int i = ((Integer)iterator.next()).intValue();
/* 103 */       Enchantment enchantment = Enchantment.getEnchantmentById(i);
/*     */       
/* 105 */       if (enchantment != null) {
/*     */         
/* 107 */         NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 108 */         nbttagcompound.setShort("id", (short)i);
/* 109 */         nbttagcompound.setShort("lvl", (short)((Integer)enchMap.get(Integer.valueOf(i))).intValue());
/* 110 */         nbttaglist.appendTag((NBTBase)nbttagcompound);
/*     */         
/* 112 */         if (stack.getItem() == Items.enchanted_book)
/*     */         {
/* 114 */           Items.enchanted_book.addEnchantment(stack, new EnchantmentData(enchantment, ((Integer)enchMap.get(Integer.valueOf(i))).intValue()));
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 119 */     if (nbttaglist.tagCount() > 0) {
/*     */       
/* 121 */       if (stack.getItem() != Items.enchanted_book)
/*     */       {
/* 123 */         stack.setTagInfo("ench", (NBTBase)nbttaglist);
/*     */       }
/*     */     }
/* 126 */     else if (stack.hasTagCompound()) {
/*     */       
/* 128 */       stack.getTagCompound().removeTag("ench");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getMaxEnchantmentLevel(int enchID, ItemStack[] stacks) {
/* 137 */     if (stacks == null)
/*     */     {
/* 139 */       return 0;
/*     */     }
/*     */ 
/*     */     
/* 143 */     int i = 0;
/*     */     
/* 145 */     for (ItemStack itemstack : stacks) {
/*     */       
/* 147 */       int j = getEnchantmentLevel(enchID, itemstack);
/*     */       
/* 149 */       if (j > i)
/*     */       {
/* 151 */         i = j;
/*     */       }
/*     */     } 
/*     */     
/* 155 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void applyEnchantmentModifier(IModifier modifier, ItemStack stack) {
/* 164 */     if (stack != null) {
/*     */       
/* 166 */       NBTTagList nbttaglist = stack.getEnchantmentTagList();
/*     */       
/* 168 */       if (nbttaglist != null)
/*     */       {
/* 170 */         for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*     */           
/* 172 */           int j = nbttaglist.getCompoundTagAt(i).getShort("id");
/* 173 */           int k = nbttaglist.getCompoundTagAt(i).getShort("lvl");
/*     */           
/* 175 */           if (Enchantment.getEnchantmentById(j) != null)
/*     */           {
/* 177 */             modifier.calculateModifier(Enchantment.getEnchantmentById(j), k);
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void applyEnchantmentModifierArray(IModifier modifier, ItemStack[] stacks) {
/* 189 */     for (ItemStack itemstack : stacks)
/*     */     {
/* 191 */       applyEnchantmentModifier(modifier, itemstack);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getEnchantmentModifierDamage(ItemStack[] stacks, DamageSource source) {
/* 200 */     enchantmentModifierDamage.damageModifier = 0;
/* 201 */     enchantmentModifierDamage.source = source;
/* 202 */     applyEnchantmentModifierArray(enchantmentModifierDamage, stacks);
/*     */     
/* 204 */     if (enchantmentModifierDamage.damageModifier > 25) {
/*     */       
/* 206 */       enchantmentModifierDamage.damageModifier = 25;
/*     */     }
/* 208 */     else if (enchantmentModifierDamage.damageModifier < 0) {
/*     */       
/* 210 */       enchantmentModifierDamage.damageModifier = 0;
/*     */     } 
/*     */     
/* 213 */     return (enchantmentModifierDamage.damageModifier + 1 >> 1) + enchantmentRand.nextInt((enchantmentModifierDamage.damageModifier >> 1) + 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public static float getModifierForCreature(ItemStack p_152377_0_, EnumCreatureAttribute p_152377_1_) {
/* 218 */     enchantmentModifierLiving.livingModifier = 0.0F;
/* 219 */     enchantmentModifierLiving.entityLiving = p_152377_1_;
/* 220 */     applyEnchantmentModifier(enchantmentModifierLiving, p_152377_0_);
/* 221 */     return enchantmentModifierLiving.livingModifier;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void applyThornEnchantments(EntityLivingBase p_151384_0_, Entity p_151384_1_) {
/* 226 */     ENCHANTMENT_ITERATOR_HURT.attacker = p_151384_1_;
/* 227 */     ENCHANTMENT_ITERATOR_HURT.user = p_151384_0_;
/*     */     
/* 229 */     if (p_151384_0_ != null)
/*     */     {
/* 231 */       applyEnchantmentModifierArray(ENCHANTMENT_ITERATOR_HURT, p_151384_0_.getInventory());
/*     */     }
/*     */     
/* 234 */     if (p_151384_1_ instanceof net.minecraft.entity.player.EntityPlayer)
/*     */     {
/* 236 */       applyEnchantmentModifier(ENCHANTMENT_ITERATOR_HURT, p_151384_0_.getHeldItem());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static void applyArthropodEnchantments(EntityLivingBase p_151385_0_, Entity p_151385_1_) {
/* 242 */     ENCHANTMENT_ITERATOR_DAMAGE.user = p_151385_0_;
/* 243 */     ENCHANTMENT_ITERATOR_DAMAGE.target = p_151385_1_;
/*     */     
/* 245 */     if (p_151385_0_ != null)
/*     */     {
/* 247 */       applyEnchantmentModifierArray(ENCHANTMENT_ITERATOR_DAMAGE, p_151385_0_.getInventory());
/*     */     }
/*     */     
/* 250 */     if (p_151385_0_ instanceof net.minecraft.entity.player.EntityPlayer)
/*     */     {
/* 252 */       applyEnchantmentModifier(ENCHANTMENT_ITERATOR_DAMAGE, p_151385_0_.getHeldItem());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getKnockbackModifier(EntityLivingBase player) {
/* 261 */     return getEnchantmentLevel(Enchantment.knockback.effectId, player.getHeldItem());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getFireAspectModifier(EntityLivingBase player) {
/* 269 */     return getEnchantmentLevel(Enchantment.fireAspect.effectId, player.getHeldItem());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getRespiration(Entity player) {
/* 277 */     return getMaxEnchantmentLevel(Enchantment.respiration.effectId, player.getInventory());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getDepthStriderModifier(Entity player) {
/* 285 */     return getMaxEnchantmentLevel(Enchantment.depthStrider.effectId, player.getInventory());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getEfficiencyModifier(EntityLivingBase player) {
/* 293 */     return getEnchantmentLevel(Enchantment.efficiency.effectId, player.getHeldItem());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean getSilkTouchModifier(EntityLivingBase player) {
/* 301 */     return (getEnchantmentLevel(Enchantment.silkTouch.effectId, player.getHeldItem()) > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getFortuneModifier(EntityLivingBase player) {
/* 309 */     return getEnchantmentLevel(Enchantment.fortune.effectId, player.getHeldItem());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getLuckOfSeaModifier(EntityLivingBase player) {
/* 317 */     return getEnchantmentLevel(Enchantment.luckOfTheSea.effectId, player.getHeldItem());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getLureModifier(EntityLivingBase player) {
/* 325 */     return getEnchantmentLevel(Enchantment.lure.effectId, player.getHeldItem());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getLootingModifier(EntityLivingBase player) {
/* 333 */     return getEnchantmentLevel(Enchantment.looting.effectId, player.getHeldItem());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean getAquaAffinityModifier(EntityLivingBase player) {
/* 341 */     return (getMaxEnchantmentLevel(Enchantment.aquaAffinity.effectId, player.getInventory()) > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public static ItemStack getEnchantedItem(Enchantment p_92099_0_, EntityLivingBase p_92099_1_) {
/* 346 */     for (ItemStack itemstack : p_92099_1_.getInventory()) {
/*     */       
/* 348 */       if (itemstack != null && getEnchantmentLevel(p_92099_0_.effectId, itemstack) > 0)
/*     */       {
/* 350 */         return itemstack;
/*     */       }
/*     */     } 
/*     */     
/* 354 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int calcItemStackEnchantability(Random rand, int enchantNum, int power, ItemStack stack) {
/* 363 */     Item item = stack.getItem();
/* 364 */     int i = item.getItemEnchantability();
/*     */     
/* 366 */     if (i <= 0)
/*     */     {
/* 368 */       return 0;
/*     */     }
/*     */ 
/*     */     
/* 372 */     if (power > 15)
/*     */     {
/* 374 */       power = 15;
/*     */     }
/*     */     
/* 377 */     int j = rand.nextInt(8) + 1 + (power >> 1) + rand.nextInt(power + 1);
/* 378 */     return (enchantNum == 0) ? Math.max(j / 3, 1) : ((enchantNum == 1) ? ((j << 1) / 3 + 1) : Math.max(j, power << 1));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ItemStack addRandomEnchantment(Random p_77504_0_, ItemStack p_77504_1_, int p_77504_2_) {
/* 387 */     List<EnchantmentData> list = buildEnchantmentList(p_77504_0_, p_77504_1_, p_77504_2_);
/* 388 */     boolean flag = (p_77504_1_.getItem() == Items.book);
/*     */     
/* 390 */     if (flag)
/*     */     {
/* 392 */       p_77504_1_.setItem((Item)Items.enchanted_book);
/*     */     }
/*     */     
/* 395 */     if (list != null)
/*     */     {
/* 397 */       for (EnchantmentData enchantmentdata : list) {
/*     */         
/* 399 */         if (flag) {
/*     */           
/* 401 */           Items.enchanted_book.addEnchantment(p_77504_1_, enchantmentdata);
/*     */           
/*     */           continue;
/*     */         } 
/* 405 */         p_77504_1_.addEnchantment(enchantmentdata.enchantmentobj, enchantmentdata.enchantmentLevel);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 410 */     return p_77504_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public static List<EnchantmentData> buildEnchantmentList(Random randomIn, ItemStack itemStackIn, int p_77513_2_) {
/* 415 */     Item item = itemStackIn.getItem();
/* 416 */     int i = item.getItemEnchantability();
/*     */     
/* 418 */     if (i <= 0)
/*     */     {
/* 420 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 424 */     i /= 2;
/* 425 */     i = 1 + randomIn.nextInt((i >> 1) + 1) + randomIn.nextInt((i >> 1) + 1);
/* 426 */     int j = i + p_77513_2_;
/* 427 */     float f = (randomIn.nextFloat() + randomIn.nextFloat() - 1.0F) * 0.15F;
/* 428 */     int k = (int)(j * (1.0F + f) + 0.5F);
/*     */     
/* 430 */     if (k < 1)
/*     */     {
/* 432 */       k = 1;
/*     */     }
/*     */     
/* 435 */     List<EnchantmentData> list = null;
/* 436 */     Map<Integer, EnchantmentData> map = mapEnchantmentData(k, itemStackIn);
/*     */     
/* 438 */     if (map != null && !map.isEmpty()) {
/*     */       
/* 440 */       EnchantmentData enchantmentdata = (EnchantmentData)WeightedRandom.getRandomItem(randomIn, map.values());
/*     */       
/* 442 */       if (enchantmentdata != null) {
/*     */         
/* 444 */         list = Lists.newArrayList();
/* 445 */         list.add(enchantmentdata);
/*     */         int l;
/* 447 */         for (l = k; randomIn.nextInt(50) <= l; l >>= 1) {
/*     */           
/* 449 */           Iterator<Integer> iterator = map.keySet().iterator();
/*     */           
/* 451 */           while (iterator.hasNext()) {
/*     */             
/* 453 */             Integer integer = iterator.next();
/* 454 */             boolean flag = true;
/*     */             
/* 456 */             for (EnchantmentData enchantmentdata1 : list) {
/*     */               
/* 458 */               if (!enchantmentdata1.enchantmentobj.canApplyTogether(Enchantment.getEnchantmentById(integer.intValue()))) {
/*     */                 
/* 460 */                 flag = false;
/*     */                 
/*     */                 break;
/*     */               } 
/*     */             } 
/* 465 */             if (!flag)
/*     */             {
/* 467 */               iterator.remove();
/*     */             }
/*     */           } 
/*     */           
/* 471 */           if (!map.isEmpty()) {
/*     */             
/* 473 */             EnchantmentData enchantmentdata2 = (EnchantmentData)WeightedRandom.getRandomItem(randomIn, map.values());
/* 474 */             list.add(enchantmentdata2);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 480 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Map<Integer, EnchantmentData> mapEnchantmentData(int p_77505_0_, ItemStack p_77505_1_) {
/* 486 */     Item item = p_77505_1_.getItem();
/* 487 */     Map<Integer, EnchantmentData> map = null;
/* 488 */     boolean flag = (p_77505_1_.getItem() == Items.book);
/*     */     
/* 490 */     for (Enchantment enchantment : Enchantment.enchantmentsBookList) {
/*     */       
/* 492 */       if (enchantment != null && (enchantment.type.canEnchantItem(item) || flag))
/*     */       {
/* 494 */         for (int i = enchantment.getMinLevel(); i <= enchantment.getMaxLevel(); i++) {
/*     */           
/* 496 */           if (p_77505_0_ >= enchantment.getMinEnchantability(i) && p_77505_0_ <= enchantment.getMaxEnchantability(i)) {
/*     */             
/* 498 */             if (map == null)
/*     */             {
/* 500 */               map = Maps.newHashMap();
/*     */             }
/*     */             
/* 503 */             map.put(Integer.valueOf(enchantment.effectId), new EnchantmentData(enchantment, i));
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 509 */     return map;
/*     */   }
/*     */ 
/*     */   
/*     */   public static float func_152377_a(ItemStack p_152377_0_, EnumCreatureAttribute p_152377_1_) {
/* 514 */     enchantmentModifierLiving.livingModifier = 0.0F;
/* 515 */     enchantmentModifierLiving.entityLiving = p_152377_1_;
/* 516 */     applyEnchantmentModifier(enchantmentModifierLiving, p_152377_0_);
/* 517 */     return enchantmentModifierLiving.livingModifier;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static final class DamageIterator
/*     */     implements IModifier
/*     */   {
/*     */     public EntityLivingBase user;
/*     */     
/*     */     public Entity target;
/*     */ 
/*     */     
/*     */     public void calculateModifier(Enchantment enchantmentIn, int enchantmentLevel) {
/* 531 */       enchantmentIn.onEntityDamaged(this.user, this.target, enchantmentLevel);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static final class HurtIterator
/*     */     implements IModifier
/*     */   {
/*     */     public EntityLivingBase user;
/*     */     
/*     */     public Entity attacker;
/*     */ 
/*     */     
/*     */     public void calculateModifier(Enchantment enchantmentIn, int enchantmentLevel) {
/* 546 */       enchantmentIn.onUserHurt(this.user, this.attacker, enchantmentLevel);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static interface IModifier
/*     */   {
/*     */     void calculateModifier(Enchantment param1Enchantment, int param1Int);
/*     */   }
/*     */ 
/*     */   
/*     */   static final class ModifierDamage
/*     */     implements IModifier
/*     */   {
/*     */     public int damageModifier;
/*     */     
/*     */     public DamageSource source;
/*     */ 
/*     */     
/*     */     public void calculateModifier(Enchantment enchantmentIn, int enchantmentLevel) {
/* 566 */       this.damageModifier += enchantmentIn.calcModifierDamage(enchantmentLevel, this.source);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static final class ModifierLiving
/*     */     implements IModifier
/*     */   {
/*     */     public float livingModifier;
/*     */     
/*     */     public EnumCreatureAttribute entityLiving;
/*     */ 
/*     */     
/*     */     public void calculateModifier(Enchantment enchantmentIn, int enchantmentLevel) {
/* 581 */       this.livingModifier += enchantmentIn.calcDamageByCreature(enchantmentLevel, this.entityLiving);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\enchantment\EnchantmentHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */