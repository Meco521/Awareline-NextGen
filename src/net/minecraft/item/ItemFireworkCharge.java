/*     */ package net.minecraft.item;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagIntArray;
/*     */ import net.minecraft.util.StatCollector;
/*     */ 
/*     */ 
/*     */ public class ItemFireworkCharge
/*     */   extends Item
/*     */ {
/*     */   public int getColorFromItemStack(ItemStack stack, int renderPass) {
/*  15 */     if (renderPass != 1)
/*     */     {
/*  17 */       return super.getColorFromItemStack(stack, renderPass);
/*     */     }
/*     */ 
/*     */     
/*  21 */     NBTBase nbtbase = getExplosionTag(stack, "Colors");
/*     */     
/*  23 */     if (!(nbtbase instanceof NBTTagIntArray))
/*     */     {
/*  25 */       return 9079434;
/*     */     }
/*     */ 
/*     */     
/*  29 */     NBTTagIntArray nbttagintarray = (NBTTagIntArray)nbtbase;
/*  30 */     int[] aint = nbttagintarray.getIntArray();
/*     */     
/*  32 */     if (aint.length == 1)
/*     */     {
/*  34 */       return aint[0];
/*     */     }
/*     */ 
/*     */     
/*  38 */     int i = 0;
/*  39 */     int j = 0;
/*  40 */     int k = 0;
/*     */     
/*  42 */     for (int l : aint) {
/*     */       
/*  44 */       i += (l & 0xFF0000) >> 16;
/*  45 */       j += (l & 0xFF00) >> 8;
/*  46 */       k += (l & 0xFF) >> 0;
/*     */     } 
/*     */     
/*  49 */     i /= aint.length;
/*  50 */     j /= aint.length;
/*  51 */     k /= aint.length;
/*  52 */     return i << 16 | j << 8 | k;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static NBTBase getExplosionTag(ItemStack stack, String key) {
/*  60 */     if (stack.hasTagCompound()) {
/*     */       
/*  62 */       NBTTagCompound nbttagcompound = stack.getTagCompound().getCompoundTag("Explosion");
/*     */       
/*  64 */       if (nbttagcompound != null)
/*     */       {
/*  66 */         return nbttagcompound.getTag(key);
/*     */       }
/*     */     } 
/*     */     
/*  70 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
/*  78 */     if (stack.hasTagCompound()) {
/*     */       
/*  80 */       NBTTagCompound nbttagcompound = stack.getTagCompound().getCompoundTag("Explosion");
/*     */       
/*  82 */       if (nbttagcompound != null)
/*     */       {
/*  84 */         addExplosionInfo(nbttagcompound, tooltip);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void addExplosionInfo(NBTTagCompound nbt, List<String> tooltip) {
/*  91 */     byte b0 = nbt.getByte("Type");
/*     */     
/*  93 */     if (b0 >= 0 && b0 <= 4) {
/*     */       
/*  95 */       tooltip.add(StatCollector.translateToLocal("item.fireworksCharge.type." + b0).trim());
/*     */     }
/*     */     else {
/*     */       
/*  99 */       tooltip.add(StatCollector.translateToLocal("item.fireworksCharge.type").trim());
/*     */     } 
/*     */     
/* 102 */     int[] aint = nbt.getIntArray("Colors");
/*     */     
/* 104 */     if (aint.length > 0) {
/*     */       
/* 106 */       boolean flag = true;
/* 107 */       StringBuilder s = new StringBuilder();
/*     */       
/* 109 */       for (int i : aint) {
/*     */         
/* 111 */         if (!flag)
/*     */         {
/* 113 */           s.append(", ");
/*     */         }
/*     */         
/* 116 */         flag = false;
/* 117 */         boolean flag1 = false;
/*     */         
/* 119 */         for (int j = 0; j < ItemDye.dyeColors.length; j++) {
/*     */           
/* 121 */           if (i == ItemDye.dyeColors[j]) {
/*     */             
/* 123 */             flag1 = true;
/* 124 */             s.append(StatCollector.translateToLocal("item.fireworksCharge." + EnumDyeColor.byDyeDamage(j).getUnlocalizedName()));
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/* 129 */         if (!flag1)
/*     */         {
/* 131 */           s.append(StatCollector.translateToLocal("item.fireworksCharge.customColor"));
/*     */         }
/*     */       } 
/*     */       
/* 135 */       tooltip.add(s.toString());
/*     */     } 
/*     */     
/* 138 */     int[] aint1 = nbt.getIntArray("FadeColors");
/*     */     
/* 140 */     if (aint1.length > 0) {
/*     */       
/* 142 */       boolean flag2 = true;
/* 143 */       StringBuilder s1 = new StringBuilder(StatCollector.translateToLocal("item.fireworksCharge.fadeTo") + " ");
/*     */       
/* 145 */       for (int l : aint1) {
/*     */         
/* 147 */         if (!flag2)
/*     */         {
/* 149 */           s1.append(", ");
/*     */         }
/*     */         
/* 152 */         flag2 = false;
/* 153 */         boolean flag5 = false;
/*     */         
/* 155 */         for (int k = 0; k < 16; k++) {
/*     */           
/* 157 */           if (l == ItemDye.dyeColors[k]) {
/*     */             
/* 159 */             flag5 = true;
/* 160 */             s1.append(StatCollector.translateToLocal("item.fireworksCharge." + EnumDyeColor.byDyeDamage(k).getUnlocalizedName()));
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/* 165 */         if (!flag5)
/*     */         {
/* 167 */           s1.append(StatCollector.translateToLocal("item.fireworksCharge.customColor"));
/*     */         }
/*     */       } 
/*     */       
/* 171 */       tooltip.add(s1.toString());
/*     */     } 
/*     */     
/* 174 */     boolean flag3 = nbt.getBoolean("Trail");
/*     */     
/* 176 */     if (flag3)
/*     */     {
/* 178 */       tooltip.add(StatCollector.translateToLocal("item.fireworksCharge.trail"));
/*     */     }
/*     */     
/* 181 */     boolean flag4 = nbt.getBoolean("Flicker");
/*     */     
/* 183 */     if (flag4)
/*     */     {
/* 185 */       tooltip.add(StatCollector.translateToLocal("item.fireworksCharge.flicker"));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\item\ItemFireworkCharge.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */