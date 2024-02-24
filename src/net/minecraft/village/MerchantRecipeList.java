/*     */ package net.minecraft.village;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.nbt.NBTUtil;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MerchantRecipeList
/*     */   extends ArrayList<MerchantRecipe>
/*     */ {
/*     */   public MerchantRecipeList() {}
/*     */   
/*     */   public MerchantRecipeList(NBTTagCompound compound) {
/*  20 */     readRecipiesFromTags(compound);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MerchantRecipe canRecipeBeUsed(ItemStack p_77203_1_, ItemStack p_77203_2_, int p_77203_3_) {
/*  28 */     if (p_77203_3_ > 0 && p_77203_3_ < size()) {
/*     */       
/*  30 */       MerchantRecipe merchantrecipe1 = get(p_77203_3_);
/*  31 */       return (!func_181078_a(p_77203_1_, merchantrecipe1.getItemToBuy()) || ((p_77203_2_ != null || merchantrecipe1.hasSecondItemToBuy()) && (!merchantrecipe1.hasSecondItemToBuy() || !func_181078_a(p_77203_2_, merchantrecipe1.getSecondItemToBuy()))) || p_77203_1_.stackSize < (merchantrecipe1.getItemToBuy()).stackSize || (merchantrecipe1.hasSecondItemToBuy() && p_77203_2_.stackSize < (merchantrecipe1.getSecondItemToBuy()).stackSize)) ? null : merchantrecipe1;
/*     */     } 
/*     */ 
/*     */     
/*  35 */     for (int i = 0; i < size(); i++) {
/*     */       
/*  37 */       MerchantRecipe merchantrecipe = get(i);
/*     */       
/*  39 */       if (func_181078_a(p_77203_1_, merchantrecipe.getItemToBuy()) && p_77203_1_.stackSize >= (merchantrecipe.getItemToBuy()).stackSize && ((!merchantrecipe.hasSecondItemToBuy() && p_77203_2_ == null) || (merchantrecipe.hasSecondItemToBuy() && func_181078_a(p_77203_2_, merchantrecipe.getSecondItemToBuy()) && p_77203_2_.stackSize >= (merchantrecipe.getSecondItemToBuy()).stackSize)))
/*     */       {
/*  41 */         return merchantrecipe;
/*     */       }
/*     */     } 
/*     */     
/*  45 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean func_181078_a(ItemStack p_181078_1_, ItemStack p_181078_2_) {
/*  51 */     return (ItemStack.areItemsEqual(p_181078_1_, p_181078_2_) && (!p_181078_2_.hasTagCompound() || (p_181078_1_.hasTagCompound() && NBTUtil.func_181123_a((NBTBase)p_181078_2_.getTagCompound(), (NBTBase)p_181078_1_.getTagCompound(), false))));
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeToBuf(PacketBuffer buffer) {
/*  56 */     buffer.writeByte((byte)(size() & 0xFF));
/*     */     
/*  58 */     for (int i = 0; i < size(); i++) {
/*     */       
/*  60 */       MerchantRecipe merchantrecipe = get(i);
/*  61 */       buffer.writeItemStackToBuffer(merchantrecipe.getItemToBuy());
/*  62 */       buffer.writeItemStackToBuffer(merchantrecipe.getItemToSell());
/*  63 */       ItemStack itemstack = merchantrecipe.getSecondItemToBuy();
/*  64 */       buffer.writeBoolean((itemstack != null));
/*     */       
/*  66 */       if (itemstack != null)
/*     */       {
/*  68 */         buffer.writeItemStackToBuffer(itemstack);
/*     */       }
/*     */       
/*  71 */       buffer.writeBoolean(merchantrecipe.isRecipeDisabled());
/*  72 */       buffer.writeInt(merchantrecipe.getToolUses());
/*  73 */       buffer.writeInt(merchantrecipe.getMaxTradeUses());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static MerchantRecipeList readFromBuf(PacketBuffer buffer) throws IOException {
/*  79 */     MerchantRecipeList merchantrecipelist = new MerchantRecipeList();
/*  80 */     int i = buffer.readByte() & 0xFF;
/*     */     
/*  82 */     for (int j = 0; j < i; j++) {
/*     */       
/*  84 */       ItemStack itemstack = buffer.readItemStackFromBuffer();
/*  85 */       ItemStack itemstack1 = buffer.readItemStackFromBuffer();
/*  86 */       ItemStack itemstack2 = null;
/*     */       
/*  88 */       if (buffer.readBoolean())
/*     */       {
/*  90 */         itemstack2 = buffer.readItemStackFromBuffer();
/*     */       }
/*     */       
/*  93 */       boolean flag = buffer.readBoolean();
/*  94 */       int k = buffer.readInt();
/*  95 */       int l = buffer.readInt();
/*  96 */       MerchantRecipe merchantrecipe = new MerchantRecipe(itemstack, itemstack2, itemstack1, k, l);
/*     */       
/*  98 */       if (flag)
/*     */       {
/* 100 */         merchantrecipe.compensateToolUses();
/*     */       }
/*     */       
/* 103 */       merchantrecipelist.add(merchantrecipe);
/*     */     } 
/*     */     
/* 106 */     return merchantrecipelist;
/*     */   }
/*     */ 
/*     */   
/*     */   public void readRecipiesFromTags(NBTTagCompound compound) {
/* 111 */     NBTTagList nbttaglist = compound.getTagList("Recipes", 10);
/*     */     
/* 113 */     for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*     */       
/* 115 */       NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
/* 116 */       add(new MerchantRecipe(nbttagcompound));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public NBTTagCompound getRecipiesAsTags() {
/* 122 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 123 */     NBTTagList nbttaglist = new NBTTagList();
/*     */     
/* 125 */     for (int i = 0; i < size(); i++) {
/*     */       
/* 127 */       MerchantRecipe merchantrecipe = get(i);
/* 128 */       nbttaglist.appendTag((NBTBase)merchantrecipe.writeToTags());
/*     */     } 
/*     */     
/* 131 */     nbttagcompound.setTag("Recipes", (NBTBase)nbttaglist);
/* 132 */     return nbttagcompound;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\village\MerchantRecipeList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */