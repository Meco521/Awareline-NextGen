/*     */ package net.minecraft.item;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.enchantment.Enchantment;
/*     */ import net.minecraft.enchantment.EnchantmentData;
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.util.WeightedRandomChestContent;
/*     */ 
/*     */ public class ItemEnchantedBook
/*     */   extends Item
/*     */ {
/*     */   public boolean hasEffect(ItemStack stack) {
/*  19 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isItemTool(ItemStack stack) {
/*  27 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumRarity getRarity(ItemStack stack) {
/*  35 */     return (getEnchantments(stack).tagCount() > 0) ? EnumRarity.UNCOMMON : super.getRarity(stack);
/*     */   }
/*     */ 
/*     */   
/*     */   public NBTTagList getEnchantments(ItemStack stack) {
/*  40 */     NBTTagCompound nbttagcompound = stack.getTagCompound();
/*  41 */     return (nbttagcompound != null && nbttagcompound.hasKey("StoredEnchantments", 9)) ? (NBTTagList)nbttagcompound.getTag("StoredEnchantments") : new NBTTagList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
/*  49 */     super.addInformation(stack, playerIn, tooltip, advanced);
/*  50 */     NBTTagList nbttaglist = getEnchantments(stack);
/*     */     
/*  52 */     if (nbttaglist != null)
/*     */     {
/*  54 */       for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*     */         
/*  56 */         int j = nbttaglist.getCompoundTagAt(i).getShort("id");
/*  57 */         int k = nbttaglist.getCompoundTagAt(i).getShort("lvl");
/*     */         
/*  59 */         if (Enchantment.getEnchantmentById(j) != null)
/*     */         {
/*  61 */           tooltip.add(Enchantment.getEnchantmentById(j).getTranslatedName(k));
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addEnchantment(ItemStack stack, EnchantmentData enchantment) {
/*  72 */     NBTTagList nbttaglist = getEnchantments(stack);
/*  73 */     boolean flag = true;
/*     */     
/*  75 */     for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*     */       
/*  77 */       NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
/*     */       
/*  79 */       if (nbttagcompound.getShort("id") == enchantment.enchantmentobj.effectId) {
/*     */         
/*  81 */         if (nbttagcompound.getShort("lvl") < enchantment.enchantmentLevel)
/*     */         {
/*  83 */           nbttagcompound.setShort("lvl", (short)enchantment.enchantmentLevel);
/*     */         }
/*     */         
/*  86 */         flag = false;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/*  91 */     if (flag) {
/*     */       
/*  93 */       NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/*  94 */       nbttagcompound1.setShort("id", (short)enchantment.enchantmentobj.effectId);
/*  95 */       nbttagcompound1.setShort("lvl", (short)enchantment.enchantmentLevel);
/*  96 */       nbttaglist.appendTag((NBTBase)nbttagcompound1);
/*     */     } 
/*     */     
/*  99 */     if (!stack.hasTagCompound())
/*     */     {
/* 101 */       stack.setTagCompound(new NBTTagCompound());
/*     */     }
/*     */     
/* 104 */     stack.getTagCompound().setTag("StoredEnchantments", (NBTBase)nbttaglist);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getEnchantedItemStack(EnchantmentData data) {
/* 112 */     ItemStack itemstack = new ItemStack(this);
/* 113 */     addEnchantment(itemstack, data);
/* 114 */     return itemstack;
/*     */   }
/*     */ 
/*     */   
/*     */   public void getAll(Enchantment enchantment, List<ItemStack> list) {
/* 119 */     for (int i = enchantment.getMinLevel(); i <= enchantment.getMaxLevel(); i++)
/*     */     {
/* 121 */       list.add(getEnchantedItemStack(new EnchantmentData(enchantment, i)));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public WeightedRandomChestContent getRandom(Random rand) {
/* 127 */     return getRandom(rand, 1, 1, 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public WeightedRandomChestContent getRandom(Random rand, int minChance, int maxChance, int weight) {
/* 132 */     ItemStack itemstack = new ItemStack(Items.book, 1, 0);
/* 133 */     EnchantmentHelper.addRandomEnchantment(rand, itemstack, 30);
/* 134 */     return new WeightedRandomChestContent(itemstack, minChance, maxChance, weight);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\item\ItemEnchantedBook.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */