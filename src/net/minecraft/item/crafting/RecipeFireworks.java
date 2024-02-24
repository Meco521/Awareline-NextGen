/*     */ package net.minecraft.item.crafting;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.InventoryCrafting;
/*     */ import net.minecraft.item.ItemDye;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RecipeFireworks
/*     */   implements IRecipe
/*     */ {
/*     */   private ItemStack field_92102_a;
/*     */   
/*     */   public boolean matches(InventoryCrafting inv, World worldIn) {
/*  23 */     this.field_92102_a = null;
/*  24 */     int i = 0;
/*  25 */     int j = 0;
/*  26 */     int k = 0;
/*  27 */     int l = 0;
/*  28 */     int i1 = 0;
/*  29 */     int j1 = 0;
/*     */     
/*  31 */     for (int k1 = 0; k1 < inv.getSizeInventory(); k1++) {
/*     */       
/*  33 */       ItemStack itemstack = inv.getStackInSlot(k1);
/*     */       
/*  35 */       if (itemstack != null)
/*     */       {
/*  37 */         if (itemstack.getItem() == Items.gunpowder) {
/*     */           
/*  39 */           j++;
/*     */         }
/*  41 */         else if (itemstack.getItem() == Items.firework_charge) {
/*     */           
/*  43 */           l++;
/*     */         }
/*  45 */         else if (itemstack.getItem() == Items.dye) {
/*     */           
/*  47 */           k++;
/*     */         }
/*  49 */         else if (itemstack.getItem() == Items.paper) {
/*     */           
/*  51 */           i++;
/*     */         }
/*  53 */         else if (itemstack.getItem() == Items.glowstone_dust) {
/*     */           
/*  55 */           i1++;
/*     */         }
/*  57 */         else if (itemstack.getItem() == Items.diamond) {
/*     */           
/*  59 */           i1++;
/*     */         }
/*  61 */         else if (itemstack.getItem() == Items.fire_charge) {
/*     */           
/*  63 */           j1++;
/*     */         }
/*  65 */         else if (itemstack.getItem() == Items.feather) {
/*     */           
/*  67 */           j1++;
/*     */         }
/*  69 */         else if (itemstack.getItem() == Items.gold_nugget) {
/*     */           
/*  71 */           j1++;
/*     */         }
/*     */         else {
/*     */           
/*  75 */           if (itemstack.getItem() != Items.skull)
/*     */           {
/*  77 */             return false;
/*     */           }
/*     */           
/*  80 */           j1++;
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/*  85 */     i1 = i1 + k + j1;
/*     */     
/*  87 */     if (j <= 3 && i <= 1) {
/*     */       
/*  89 */       if (j >= 1 && i == 1 && i1 == 0) {
/*     */         
/*  91 */         this.field_92102_a = new ItemStack(Items.fireworks);
/*     */         
/*  93 */         if (l > 0) {
/*     */           
/*  95 */           NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/*  96 */           NBTTagCompound nbttagcompound3 = new NBTTagCompound();
/*  97 */           NBTTagList nbttaglist = new NBTTagList();
/*     */           
/*  99 */           for (int k2 = 0; k2 < inv.getSizeInventory(); k2++) {
/*     */             
/* 101 */             ItemStack itemstack3 = inv.getStackInSlot(k2);
/*     */             
/* 103 */             if (itemstack3 != null && itemstack3.getItem() == Items.firework_charge && itemstack3.hasTagCompound() && itemstack3.getTagCompound().hasKey("Explosion", 10))
/*     */             {
/* 105 */               nbttaglist.appendTag((NBTBase)itemstack3.getTagCompound().getCompoundTag("Explosion"));
/*     */             }
/*     */           } 
/*     */           
/* 109 */           nbttagcompound3.setTag("Explosions", (NBTBase)nbttaglist);
/* 110 */           nbttagcompound3.setByte("Flight", (byte)j);
/* 111 */           nbttagcompound1.setTag("Fireworks", (NBTBase)nbttagcompound3);
/* 112 */           this.field_92102_a.setTagCompound(nbttagcompound1);
/*     */         } 
/*     */         
/* 115 */         return true;
/*     */       } 
/* 117 */       if (j == 1 && i == 0 && l == 0 && k > 0 && j1 <= 1) {
/*     */         
/* 119 */         this.field_92102_a = new ItemStack(Items.firework_charge);
/* 120 */         NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 121 */         NBTTagCompound nbttagcompound2 = new NBTTagCompound();
/* 122 */         byte b0 = 0;
/* 123 */         List<Integer> list = Lists.newArrayList();
/*     */         
/* 125 */         for (int l1 = 0; l1 < inv.getSizeInventory(); l1++) {
/*     */           
/* 127 */           ItemStack itemstack2 = inv.getStackInSlot(l1);
/*     */           
/* 129 */           if (itemstack2 != null)
/*     */           {
/* 131 */             if (itemstack2.getItem() == Items.dye) {
/*     */               
/* 133 */               list.add(Integer.valueOf(ItemDye.dyeColors[itemstack2.getMetadata() & 0xF]));
/*     */             }
/* 135 */             else if (itemstack2.getItem() == Items.glowstone_dust) {
/*     */               
/* 137 */               nbttagcompound2.setBoolean("Flicker", true);
/*     */             }
/* 139 */             else if (itemstack2.getItem() == Items.diamond) {
/*     */               
/* 141 */               nbttagcompound2.setBoolean("Trail", true);
/*     */             }
/* 143 */             else if (itemstack2.getItem() == Items.fire_charge) {
/*     */               
/* 145 */               b0 = 1;
/*     */             }
/* 147 */             else if (itemstack2.getItem() == Items.feather) {
/*     */               
/* 149 */               b0 = 4;
/*     */             }
/* 151 */             else if (itemstack2.getItem() == Items.gold_nugget) {
/*     */               
/* 153 */               b0 = 2;
/*     */             }
/* 155 */             else if (itemstack2.getItem() == Items.skull) {
/*     */               
/* 157 */               b0 = 3;
/*     */             } 
/*     */           }
/*     */         } 
/*     */         
/* 162 */         int[] aint1 = new int[list.size()];
/*     */         
/* 164 */         for (int l2 = 0; l2 < aint1.length; l2++)
/*     */         {
/* 166 */           aint1[l2] = ((Integer)list.get(l2)).intValue();
/*     */         }
/*     */         
/* 169 */         nbttagcompound2.setIntArray("Colors", aint1);
/* 170 */         nbttagcompound2.setByte("Type", b0);
/* 171 */         nbttagcompound.setTag("Explosion", (NBTBase)nbttagcompound2);
/* 172 */         this.field_92102_a.setTagCompound(nbttagcompound);
/* 173 */         return true;
/*     */       } 
/* 175 */       if (j == 0 && i == 0 && l == 1 && k > 0 && k == i1) {
/*     */         
/* 177 */         List<Integer> list1 = Lists.newArrayList();
/*     */         
/* 179 */         for (int i2 = 0; i2 < inv.getSizeInventory(); i2++) {
/*     */           
/* 181 */           ItemStack itemstack1 = inv.getStackInSlot(i2);
/*     */           
/* 183 */           if (itemstack1 != null)
/*     */           {
/* 185 */             if (itemstack1.getItem() == Items.dye) {
/*     */               
/* 187 */               list1.add(Integer.valueOf(ItemDye.dyeColors[itemstack1.getMetadata() & 0xF]));
/*     */             }
/* 189 */             else if (itemstack1.getItem() == Items.firework_charge) {
/*     */               
/* 191 */               this.field_92102_a = itemstack1.copy();
/* 192 */               this.field_92102_a.stackSize = 1;
/*     */             } 
/*     */           }
/*     */         } 
/*     */         
/* 197 */         int[] aint = new int[list1.size()];
/*     */         
/* 199 */         for (int j2 = 0; j2 < aint.length; j2++)
/*     */         {
/* 201 */           aint[j2] = ((Integer)list1.get(j2)).intValue();
/*     */         }
/*     */         
/* 204 */         if (this.field_92102_a != null && this.field_92102_a.hasTagCompound()) {
/*     */           
/* 206 */           NBTTagCompound nbttagcompound4 = this.field_92102_a.getTagCompound().getCompoundTag("Explosion");
/*     */           
/* 208 */           if (nbttagcompound4 == null)
/*     */           {
/* 210 */             return false;
/*     */           }
/*     */ 
/*     */           
/* 214 */           nbttagcompound4.setIntArray("FadeColors", aint);
/* 215 */           return true;
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 220 */         return false;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 225 */       return false;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 230 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getCraftingResult(InventoryCrafting inv) {
/* 239 */     return this.field_92102_a.copy();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRecipeSize() {
/* 247 */     return 10;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getRecipeOutput() {
/* 252 */     return this.field_92102_a;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack[] getRemainingItems(InventoryCrafting inv) {
/* 257 */     ItemStack[] aitemstack = new ItemStack[inv.getSizeInventory()];
/*     */     
/* 259 */     for (int i = 0; i < aitemstack.length; i++) {
/*     */       
/* 261 */       ItemStack itemstack = inv.getStackInSlot(i);
/*     */       
/* 263 */       if (itemstack != null && itemstack.getItem().hasContainerItem())
/*     */       {
/* 265 */         aitemstack[i] = new ItemStack(itemstack.getItem().getContainerItem());
/*     */       }
/*     */     } 
/*     */     
/* 269 */     return aitemstack;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\item\crafting\RecipeFireworks.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */