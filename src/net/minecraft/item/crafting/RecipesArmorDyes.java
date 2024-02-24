/*     */ package net.minecraft.item.crafting;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.passive.EntitySheep;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.InventoryCrafting;
/*     */ import net.minecraft.item.EnumDyeColor;
/*     */ import net.minecraft.item.ItemArmor;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RecipesArmorDyes
/*     */   implements IRecipe
/*     */ {
/*     */   public boolean matches(InventoryCrafting inv, World worldIn) {
/*  21 */     ItemStack itemstack = null;
/*  22 */     List<ItemStack> list = Lists.newArrayList();
/*     */     
/*  24 */     for (int i = 0; i < inv.getSizeInventory(); i++) {
/*     */       
/*  26 */       ItemStack itemstack1 = inv.getStackInSlot(i);
/*     */       
/*  28 */       if (itemstack1 != null)
/*     */       {
/*  30 */         if (itemstack1.getItem() instanceof ItemArmor) {
/*     */           
/*  32 */           ItemArmor itemarmor = (ItemArmor)itemstack1.getItem();
/*     */           
/*  34 */           if (itemarmor.getArmorMaterial() != ItemArmor.ArmorMaterial.LEATHER || itemstack != null)
/*     */           {
/*  36 */             return false;
/*     */           }
/*     */           
/*  39 */           itemstack = itemstack1;
/*     */         }
/*     */         else {
/*     */           
/*  43 */           if (itemstack1.getItem() != Items.dye)
/*     */           {
/*  45 */             return false;
/*     */           }
/*     */           
/*  48 */           list.add(itemstack1);
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/*  53 */     return (itemstack != null && !list.isEmpty());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getCraftingResult(InventoryCrafting inv) {
/*  61 */     ItemStack itemstack = null;
/*  62 */     int[] aint = new int[3];
/*  63 */     int i = 0;
/*  64 */     int j = 0;
/*  65 */     ItemArmor itemarmor = null;
/*     */     
/*  67 */     for (int k = 0; k < inv.getSizeInventory(); k++) {
/*     */       
/*  69 */       ItemStack itemstack1 = inv.getStackInSlot(k);
/*     */       
/*  71 */       if (itemstack1 != null)
/*     */       {
/*  73 */         if (itemstack1.getItem() instanceof ItemArmor) {
/*     */           
/*  75 */           itemarmor = (ItemArmor)itemstack1.getItem();
/*     */           
/*  77 */           if (itemarmor.getArmorMaterial() != ItemArmor.ArmorMaterial.LEATHER || itemstack != null)
/*     */           {
/*  79 */             return null;
/*     */           }
/*     */           
/*  82 */           itemstack = itemstack1.copy();
/*  83 */           itemstack.stackSize = 1;
/*     */           
/*  85 */           if (itemarmor.hasColor(itemstack1))
/*     */           {
/*  87 */             int l = itemarmor.getColor(itemstack);
/*  88 */             float f = (l >> 16 & 0xFF) / 255.0F;
/*  89 */             float f1 = (l >> 8 & 0xFF) / 255.0F;
/*  90 */             float f2 = (l & 0xFF) / 255.0F;
/*  91 */             i = (int)(i + Math.max(f, Math.max(f1, f2)) * 255.0F);
/*  92 */             aint[0] = (int)(aint[0] + f * 255.0F);
/*  93 */             aint[1] = (int)(aint[1] + f1 * 255.0F);
/*  94 */             aint[2] = (int)(aint[2] + f2 * 255.0F);
/*  95 */             j++;
/*     */           }
/*     */         
/*     */         } else {
/*     */           
/* 100 */           if (itemstack1.getItem() != Items.dye)
/*     */           {
/* 102 */             return null;
/*     */           }
/*     */           
/* 105 */           float[] afloat = EntitySheep.getDyeRgb(EnumDyeColor.byDyeDamage(itemstack1.getMetadata()));
/* 106 */           int l1 = (int)(afloat[0] * 255.0F);
/* 107 */           int i2 = (int)(afloat[1] * 255.0F);
/* 108 */           int j2 = (int)(afloat[2] * 255.0F);
/* 109 */           i += Math.max(l1, Math.max(i2, j2));
/* 110 */           aint[0] = aint[0] + l1;
/* 111 */           aint[1] = aint[1] + i2;
/* 112 */           aint[2] = aint[2] + j2;
/* 113 */           j++;
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 118 */     if (itemarmor == null)
/*     */     {
/* 120 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 124 */     int i1 = aint[0] / j;
/* 125 */     int j1 = aint[1] / j;
/* 126 */     int k1 = aint[2] / j;
/* 127 */     float f3 = i / j;
/* 128 */     float f4 = Math.max(i1, Math.max(j1, k1));
/* 129 */     i1 = (int)(i1 * f3 / f4);
/* 130 */     j1 = (int)(j1 * f3 / f4);
/* 131 */     k1 = (int)(k1 * f3 / f4);
/* 132 */     int lvt_12_3_ = (i1 << 8) + j1;
/* 133 */     lvt_12_3_ = (lvt_12_3_ << 8) + k1;
/* 134 */     itemarmor.setColor(itemstack, lvt_12_3_);
/* 135 */     return itemstack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRecipeSize() {
/* 144 */     return 10;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getRecipeOutput() {
/* 149 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack[] getRemainingItems(InventoryCrafting inv) {
/* 154 */     ItemStack[] aitemstack = new ItemStack[inv.getSizeInventory()];
/*     */     
/* 156 */     for (int i = 0; i < aitemstack.length; i++) {
/*     */       
/* 158 */       ItemStack itemstack = inv.getStackInSlot(i);
/*     */       
/* 160 */       if (itemstack != null && itemstack.getItem().hasContainerItem())
/*     */       {
/* 162 */         aitemstack[i] = new ItemStack(itemstack.getItem().getContainerItem());
/*     */       }
/*     */     } 
/*     */     
/* 166 */     return aitemstack;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\item\crafting\RecipesArmorDyes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */