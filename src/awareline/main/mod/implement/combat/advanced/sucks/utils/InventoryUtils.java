/*     */ package awareline.main.mod.implement.combat.advanced.sucks.utils;
/*     */ import java.util.Arrays;
/*     */ import java.util.Optional;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.enchantment.Enchantment;
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.entity.EnumCreatureAttribute;
/*     */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemArmor;
/*     */ import net.minecraft.item.ItemBlock;
/*     */ import net.minecraft.item.ItemPotion;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ 
/*     */ public class InventoryUtils {
/*     */   public static boolean hotbarHas(Item item, int slotID) {
/*  22 */     int index = 0;
/*  23 */     while (index <= 36) {
/*  24 */       ItemStack stack = (Minecraft.getMinecraft()).thePlayer.inventory.getStackInSlot(index);
/*  25 */       if (stack != null && stack.getItem() == item && getSlotID(stack.getItem()) == slotID) {
/*  26 */         return true;
/*     */       }
/*  28 */       index++;
/*     */     } 
/*  30 */     return false;
/*     */   }
/*     */   
/*     */   public static int getSlotID(Item item) {
/*  34 */     int index = 0;
/*  35 */     while (index <= 36) {
/*  36 */       ItemStack stack = (Minecraft.getMinecraft()).thePlayer.inventory.getStackInSlot(index);
/*  37 */       if (stack != null && stack.getItem() == item) {
/*  38 */         return index;
/*     */       }
/*  40 */       index++;
/*     */     } 
/*  42 */     return -1;
/*     */   }
/*     */   
/*     */   public static ItemStack getItemBySlotID(int slotID) {
/*  46 */     int index = 0;
/*  47 */     while (index <= 36) {
/*  48 */       ItemStack stack = (Minecraft.getMinecraft()).thePlayer.inventory.getStackInSlot(index);
/*  49 */       if (stack != null && getSlotID(stack.getItem()) == slotID) {
/*  50 */         return stack;
/*     */       }
/*  52 */       index++;
/*     */     } 
/*  54 */     return null;
/*     */   }
/*     */   
/*     */   public static int getBestSwordSlotID(ItemStack item) {
/*  58 */     int index = 0;
/*  59 */     while (index <= 36) {
/*  60 */       ItemStack stack = (Minecraft.getMinecraft()).thePlayer.inventory.getStackInSlot(index);
/*  61 */       if (stack != null && stack == item && getSwordDamage(stack) == getSwordDamage(item)) {
/*  62 */         return index;
/*     */       }
/*  64 */       index++;
/*     */     } 
/*  66 */     return -1;
/*     */   }
/*     */   
/*     */   private static double getSwordDamage(ItemStack itemStack) {
/*  70 */     double damage = 0.0D;
/*  71 */     Optional<AttributeModifier> attributeModifier = itemStack.getAttributeModifiers().values().stream().findFirst();
/*  72 */     if (attributeModifier.isPresent()) {
/*  73 */       damage = ((AttributeModifier)attributeModifier.get()).getAmount();
/*     */     }
/*  75 */     return damage + EnchantmentHelper.getModifierForCreature(itemStack, EnumCreatureAttribute.UNDEFINED);
/*     */   }
/*     */   
/*     */   public static boolean isBadPotion(ItemStack stack) {
/*  79 */     if (stack != null && stack.getItem() instanceof ItemPotion) {
/*  80 */       ItemPotion potion = (ItemPotion)stack.getItem();
/*  81 */       if (ItemPotion.isSplash(stack.getItemDamage())) {
/*  82 */         for (PotionEffect o : potion.getEffects(stack)) {
/*  83 */           if (o.getPotionID() == Potion.poison.getId() || o.getPotionID() == Potion.harm.getId() || o.getPotionID() == Potion.moveSlowdown.getId() || o.getPotionID() == Potion.weakness.getId()) {
/*  84 */             return true;
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/*  89 */     return false;
/*     */   }
/*     */   
/*     */   private static float getDamage(ItemStack stack) {
/*  93 */     if (!(stack.getItem() instanceof ItemSword)) {
/*  94 */       return 0.0F;
/*     */     }
/*  96 */     return EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, stack) * 1.25F + ((ItemSword)stack.getItem()).getDamageVsEntity();
/*     */   }
/*     */   
/*     */   public static float getProtection(ItemStack stack) {
/* 100 */     float prot = 0.0F;
/* 101 */     if (stack.getItem() instanceof ItemArmor) {
/* 102 */       ItemArmor armor = (ItemArmor)stack.getItem();
/*     */       
/* 104 */       prot = (float)(prot + armor.damageReduceAmount + ((100 - armor.damageReduceAmount) * EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, stack)) * 0.0075D);
/* 105 */       prot = (float)(prot + EnchantmentHelper.getEnchantmentLevel(Enchantment.blastProtection.effectId, stack) / 100.0D);
/* 106 */       prot = (float)(prot + EnchantmentHelper.getEnchantmentLevel(Enchantment.fireProtection.effectId, stack) / 100.0D);
/* 107 */       prot = (float)(prot + EnchantmentHelper.getEnchantmentLevel(Enchantment.thorns.effectId, stack) / 100.0D);
/* 108 */       prot = (float)(prot + EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack) / 50.0D);
/* 109 */       prot = (float)(prot + EnchantmentHelper.getEnchantmentLevel(Enchantment.projectileProtection.effectId, stack) / 100.0D);
/*     */     } 
/* 111 */     return prot;
/*     */   }
/*     */   
/*     */   public static boolean isBestWeapon(ItemStack stack) {
/* 115 */     float damage = getDamage(stack);
/* 116 */     for (int i = 9; i < 45; ) {
/*     */       ItemStack is;
/* 118 */       if (!(Minecraft.getMinecraft()).thePlayer.inventoryContainer.getSlot(i).getHasStack() || getDamage(is = (Minecraft.getMinecraft()).thePlayer.inventoryContainer.getSlot(i).getStack()) <= damage || !(is.getItem() instanceof ItemSword)) {
/*     */         i++; continue;
/* 120 */       }  return false;
/*     */     } 
/* 122 */     return stack.getItem() instanceof ItemSword;
/*     */   }
/*     */   
/*     */   public static boolean isBad(ItemStack item) {
/* 126 */     return (item.getItem() instanceof ItemArmor || item.getItem() instanceof net.minecraft.item.ItemTool || item.getItem() instanceof ItemBlock || item.getItem() instanceof ItemSword || item.getItem() instanceof net.minecraft.item.ItemEnderPearl || item.getItem() instanceof net.minecraft.item.ItemFood || (item.getItem() instanceof ItemPotion && !isBadPotion(item)) || item.getDisplayName().toLowerCase().contains(EnumChatFormatting.GRAY + "(right click)"));
/*     */   }
/*     */   
/*     */   public static int findItem(int startSlot, int endSlot, Item item) {
/* 130 */     for (int i = startSlot; i < endSlot; i++) {
/* 131 */       ItemStack stack = (Minecraft.getMinecraft()).thePlayer.inventoryContainer.getSlot(i).getStack();
/*     */       
/* 133 */       if (stack != null && stack.getItem() == item)
/* 134 */         return i; 
/*     */     } 
/* 136 */     return -1;
/*     */   }
/*     */   
/* 139 */   public static final List<Block> BLOCK_BLACKLIST = Arrays.asList(new Block[] { Blocks.enchanting_table, (Block)Blocks.chest, Blocks.ender_chest, Blocks.trapped_chest, Blocks.anvil, (Block)Blocks.sand, Blocks.web, Blocks.torch, Blocks.crafting_table, Blocks.furnace, Blocks.waterlily, Blocks.dispenser, Blocks.stone_pressure_plate, Blocks.wooden_pressure_plate, Blocks.noteblock, Blocks.dropper, Blocks.tnt, Blocks.standing_banner, Blocks.wall_banner });
/*     */ 
/*     */   
/*     */   public static int findAutoBlockBlock() {
/*     */     int i;
/* 144 */     for (i = 36; i < 45; i++) {
/* 145 */       ItemStack itemStack = (Minecraft.getMinecraft()).thePlayer.inventoryContainer.getSlot(i).getStack();
/*     */       
/* 147 */       if (itemStack != null && itemStack.getItem() instanceof ItemBlock) {
/* 148 */         ItemBlock itemBlock = (ItemBlock)itemStack.getItem();
/* 149 */         Block block = itemBlock.getBlock();
/*     */         
/* 151 */         if (block.isFullCube() && !BLOCK_BLACKLIST.contains(block)) {
/* 152 */           return i;
/*     */         }
/*     */       } 
/*     */     } 
/* 156 */     for (i = 36; i < 45; i++) {
/* 157 */       ItemStack itemStack = (Minecraft.getMinecraft()).thePlayer.inventoryContainer.getSlot(i).getStack();
/*     */       
/* 159 */       if (itemStack != null && itemStack.getItem() instanceof ItemBlock) {
/* 160 */         ItemBlock itemBlock = (ItemBlock)itemStack.getItem();
/* 161 */         Block block = itemBlock.getBlock();
/*     */         
/* 163 */         if (!BLOCK_BLACKLIST.contains(block)) {
/* 164 */           return i;
/*     */         }
/*     */       } 
/*     */     } 
/* 168 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public static ItemStack getCurrentItem() {
/* 173 */     return ((Minecraft.getMinecraft()).thePlayer.getCurrentEquippedItem() == null) ? new ItemStack(Blocks.air) : (Minecraft.getMinecraft()).thePlayer.getCurrentEquippedItem();
/*     */   }
/*     */   
/*     */   public static ItemStack getItemBySlot(int slot) {
/* 177 */     return ((Minecraft.getMinecraft()).thePlayer.inventory.mainInventory[slot] == null) ? new ItemStack(Blocks.air) : (Minecraft.getMinecraft()).thePlayer.inventory.mainInventory[slot];
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\combat\advanced\suck\\utils\InventoryUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */