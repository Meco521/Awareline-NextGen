/*     */ package net.minecraft.item;
/*     */ 
/*     */ import com.google.common.collect.HashMultimap;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*     */ import net.minecraft.entity.ai.attributes.IAttribute;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.projectile.EntityPotion;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.potion.PotionHelper;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class ItemPotion
/*     */   extends Item
/*     */ {
/*  30 */   private final Map<Integer, List<PotionEffect>> effectCache = Maps.newHashMap();
/*  31 */   private static final Map<List<PotionEffect>, Integer> SUB_ITEMS_CACHE = Maps.newLinkedHashMap();
/*     */ 
/*     */   
/*     */   public ItemPotion() {
/*  35 */     setMaxStackSize(1);
/*  36 */     setHasSubtypes(true);
/*  37 */     setMaxDamage(0);
/*  38 */     setCreativeTab(CreativeTabs.tabBrewing);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<PotionEffect> getEffects(ItemStack stack) {
/*  43 */     if (stack.hasTagCompound() && stack.getTagCompound().hasKey("CustomPotionEffects", 9)) {
/*     */       
/*  45 */       List<PotionEffect> list1 = Lists.newArrayList();
/*  46 */       NBTTagList nbttaglist = stack.getTagCompound().getTagList("CustomPotionEffects", 10);
/*     */       
/*  48 */       for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*     */         
/*  50 */         NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
/*  51 */         PotionEffect potioneffect = PotionEffect.readCustomPotionEffectFromNBT(nbttagcompound);
/*     */         
/*  53 */         if (potioneffect != null)
/*     */         {
/*  55 */           list1.add(potioneffect);
/*     */         }
/*     */       } 
/*     */       
/*  59 */       return list1;
/*     */     } 
/*     */ 
/*     */     
/*  63 */     List<PotionEffect> list = this.effectCache.get(Integer.valueOf(stack.getMetadata()));
/*     */     
/*  65 */     if (list == null) {
/*     */       
/*  67 */       list = PotionHelper.getPotionEffects(stack.getMetadata(), false);
/*  68 */       this.effectCache.put(Integer.valueOf(stack.getMetadata()), list);
/*     */     } 
/*     */     
/*  71 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<PotionEffect> getEffects(int meta) {
/*  77 */     List<PotionEffect> list = this.effectCache.get(Integer.valueOf(meta));
/*     */     
/*  79 */     if (list == null) {
/*     */       
/*  81 */       list = PotionHelper.getPotionEffects(meta, false);
/*  82 */       this.effectCache.put(Integer.valueOf(meta), list);
/*     */     } 
/*     */     
/*  85 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityPlayer playerIn) {
/*  94 */     if (!playerIn.capabilities.isCreativeMode)
/*     */     {
/*  96 */       stack.stackSize--;
/*     */     }
/*     */     
/*  99 */     if (!worldIn.isRemote) {
/*     */       
/* 101 */       List<PotionEffect> list = getEffects(stack);
/*     */       
/* 103 */       if (list != null)
/*     */       {
/* 105 */         for (PotionEffect potioneffect : list)
/*     */         {
/* 107 */           playerIn.addPotionEffect(new PotionEffect(potioneffect));
/*     */         }
/*     */       }
/*     */     } 
/*     */     
/* 112 */     playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
/*     */     
/* 114 */     if (!playerIn.capabilities.isCreativeMode) {
/*     */       
/* 116 */       if (stack.stackSize <= 0)
/*     */       {
/* 118 */         return new ItemStack(Items.glass_bottle);
/*     */       }
/*     */       
/* 121 */       playerIn.inventory.addItemStackToInventory(new ItemStack(Items.glass_bottle));
/*     */     } 
/*     */     
/* 124 */     return stack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxItemUseDuration(ItemStack stack) {
/* 132 */     return 32;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumAction getItemUseAction(ItemStack stack) {
/* 140 */     return EnumAction.DRINK;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
/* 148 */     if (isSplash(itemStackIn.getMetadata())) {
/*     */       
/* 150 */       if (!playerIn.capabilities.isCreativeMode)
/*     */       {
/* 152 */         itemStackIn.stackSize--;
/*     */       }
/*     */       
/* 155 */       worldIn.playSoundAtEntity((Entity)playerIn, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
/*     */       
/* 157 */       if (!worldIn.isRemote)
/*     */       {
/* 159 */         worldIn.spawnEntityInWorld((Entity)new EntityPotion(worldIn, (EntityLivingBase)playerIn, itemStackIn));
/*     */       }
/*     */       
/* 162 */       playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
/* 163 */       return itemStackIn;
/*     */     } 
/*     */ 
/*     */     
/* 167 */     playerIn.setItemInUse(itemStackIn, getMaxItemUseDuration(itemStackIn));
/* 168 */     return itemStackIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isSplash(int meta) {
/* 177 */     return ((meta & 0x4000) != 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getColorFromDamage(int meta) {
/* 182 */     return PotionHelper.getLiquidColor(meta, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getColorFromItemStack(ItemStack stack, int renderPass) {
/* 187 */     return (renderPass > 0) ? 16777215 : getColorFromDamage(stack.getMetadata());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEffectInstant(int meta) {
/* 192 */     List<PotionEffect> list = getEffects(meta);
/*     */     
/* 194 */     if (list != null && !list.isEmpty()) {
/*     */       
/* 196 */       for (PotionEffect potioneffect : list) {
/*     */         
/* 198 */         if (Potion.potionTypes[potioneffect.getPotionID()].isInstant())
/*     */         {
/* 200 */           return true;
/*     */         }
/*     */       } 
/*     */       
/* 204 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 208 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getItemStackDisplayName(ItemStack stack) {
/* 214 */     if (stack.getMetadata() == 0)
/*     */     {
/* 216 */       return StatCollector.translateToLocal("item.emptyPotion.name").trim();
/*     */     }
/*     */ 
/*     */     
/* 220 */     String s = "";
/*     */     
/* 222 */     if (isSplash(stack.getMetadata()))
/*     */     {
/* 224 */       s = StatCollector.translateToLocal("potion.prefix.grenade").trim() + " ";
/*     */     }
/*     */     
/* 227 */     List<PotionEffect> list = Items.potionitem.getEffects(stack);
/*     */     
/* 229 */     if (list != null && !list.isEmpty()) {
/*     */       
/* 231 */       String s2 = ((PotionEffect)list.get(0)).getEffectName();
/* 232 */       s2 = s2 + ".postfix";
/* 233 */       return s + StatCollector.translateToLocal(s2).trim();
/*     */     } 
/*     */ 
/*     */     
/* 237 */     String s1 = PotionHelper.getPotionPrefix(stack.getMetadata());
/* 238 */     return StatCollector.translateToLocal(s1).trim() + " " + super.getItemStackDisplayName(stack);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
/* 248 */     if (stack.getMetadata() != 0) {
/*     */       
/* 250 */       List<PotionEffect> list = Items.potionitem.getEffects(stack);
/* 251 */       HashMultimap hashMultimap = HashMultimap.create();
/*     */       
/* 253 */       if (list != null && !list.isEmpty()) {
/*     */         
/* 255 */         for (PotionEffect potioneffect : list)
/*     */         {
/* 257 */           String s1 = StatCollector.translateToLocal(potioneffect.getEffectName()).trim();
/* 258 */           Potion potion = Potion.potionTypes[potioneffect.getPotionID()];
/* 259 */           Map<IAttribute, AttributeModifier> map = potion.getAttributeModifierMap();
/*     */           
/* 261 */           if (map != null && !map.isEmpty())
/*     */           {
/* 263 */             for (Map.Entry<IAttribute, AttributeModifier> entry : map.entrySet()) {
/*     */               
/* 265 */               AttributeModifier attributemodifier = entry.getValue();
/* 266 */               AttributeModifier attributemodifier1 = new AttributeModifier(attributemodifier.getName(), potion.getAttributeModifierAmount(potioneffect.getAmplifier(), attributemodifier), attributemodifier.getOperation());
/* 267 */               hashMultimap.put(((IAttribute)entry.getKey()).getAttributeUnlocalizedName(), attributemodifier1);
/*     */             } 
/*     */           }
/*     */           
/* 271 */           if (potioneffect.getAmplifier() > 0)
/*     */           {
/* 273 */             s1 = s1 + " " + StatCollector.translateToLocal("potion.potency." + potioneffect.getAmplifier()).trim();
/*     */           }
/*     */           
/* 276 */           if (potioneffect.getDuration() > 20)
/*     */           {
/* 278 */             s1 = s1 + " (" + Potion.getDurationString(potioneffect) + ")";
/*     */           }
/*     */           
/* 281 */           if (potion.isBadEffect()) {
/*     */             
/* 283 */             tooltip.add(EnumChatFormatting.RED + s1);
/*     */             
/*     */             continue;
/*     */           } 
/* 287 */           tooltip.add(EnumChatFormatting.GRAY + s1);
/*     */         }
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 293 */         String s = StatCollector.translateToLocal("potion.empty").trim();
/* 294 */         tooltip.add(EnumChatFormatting.GRAY + s);
/*     */       } 
/*     */       
/* 297 */       if (!hashMultimap.isEmpty()) {
/*     */         
/* 299 */         tooltip.add("");
/* 300 */         tooltip.add(EnumChatFormatting.DARK_PURPLE + StatCollector.translateToLocal("potion.effects.whenDrank"));
/*     */         
/* 302 */         for (Map.Entry<String, AttributeModifier> entry1 : (Iterable<Map.Entry<String, AttributeModifier>>)hashMultimap.entries()) {
/*     */           double d1;
/* 304 */           AttributeModifier attributemodifier2 = entry1.getValue();
/* 305 */           double d0 = attributemodifier2.getAmount();
/*     */ 
/*     */           
/* 308 */           if (attributemodifier2.getOperation() != 1 && attributemodifier2.getOperation() != 2) {
/*     */             
/* 310 */             d1 = attributemodifier2.getAmount();
/*     */           }
/*     */           else {
/*     */             
/* 314 */             d1 = attributemodifier2.getAmount() * 100.0D;
/*     */           } 
/*     */           
/* 317 */           if (d0 > 0.0D) {
/*     */             
/* 319 */             tooltip.add(EnumChatFormatting.BLUE + StatCollector.translateToLocalFormatted("attribute.modifier.plus." + attributemodifier2.getOperation(), new Object[] { ItemStack.DECIMALFORMAT.format(d1), StatCollector.translateToLocal("attribute.name." + (String)entry1.getKey()) })); continue;
/*     */           } 
/* 321 */           if (d0 < 0.0D) {
/*     */             
/* 323 */             d1 *= -1.0D;
/* 324 */             tooltip.add(EnumChatFormatting.RED + StatCollector.translateToLocalFormatted("attribute.modifier.take." + attributemodifier2.getOperation(), new Object[] { ItemStack.DECIMALFORMAT.format(d1), StatCollector.translateToLocal("attribute.name." + (String)entry1.getKey()) }));
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasEffect(ItemStack stack) {
/* 333 */     List<PotionEffect> list = getEffects(stack);
/* 334 */     return (list != null && !list.isEmpty());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
/* 342 */     super.getSubItems(itemIn, tab, subItems);
/*     */     
/* 344 */     if (SUB_ITEMS_CACHE.isEmpty())
/*     */     {
/* 346 */       for (int i = 0; i <= 15; i++) {
/*     */         
/* 348 */         for (int j = 0; j <= 1; j++) {
/*     */           int lvt_6_1_;
/*     */ 
/*     */           
/* 352 */           if (j == 0) {
/*     */             
/* 354 */             lvt_6_1_ = i | 0x2000;
/*     */           }
/*     */           else {
/*     */             
/* 358 */             lvt_6_1_ = i | 0x4000;
/*     */           } 
/*     */           
/* 361 */           for (int l = 0; l <= 2; l++) {
/*     */             
/* 363 */             int i1 = lvt_6_1_;
/*     */             
/* 365 */             if (l != 0)
/*     */             {
/* 367 */               if (l == 1) {
/*     */                 
/* 369 */                 i1 = lvt_6_1_ | 0x20;
/*     */               }
/* 371 */               else if (l == 2) {
/*     */                 
/* 373 */                 i1 = lvt_6_1_ | 0x40;
/*     */               } 
/*     */             }
/*     */             
/* 377 */             List<PotionEffect> list = PotionHelper.getPotionEffects(i1, false);
/*     */             
/* 379 */             if (list != null && !list.isEmpty())
/*     */             {
/* 381 */               SUB_ITEMS_CACHE.put(list, Integer.valueOf(i1));
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 388 */     Iterator<Integer> iterator = SUB_ITEMS_CACHE.values().iterator();
/*     */     
/* 390 */     while (iterator.hasNext()) {
/*     */       
/* 392 */       int j1 = ((Integer)iterator.next()).intValue();
/* 393 */       subItems.add(new ItemStack(itemIn, 1, j1));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\item\ItemPotion.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */