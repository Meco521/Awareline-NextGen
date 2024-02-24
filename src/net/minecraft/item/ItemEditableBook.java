/*     */ package net.minecraft.item;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.Slot;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.nbt.NBTTagString;
/*     */ import net.minecraft.network.play.server.S2FPacketSetSlot;
/*     */ import net.minecraft.util.ChatComponentProcessor;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.util.StringUtils;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class ItemEditableBook extends Item {
/*     */   public ItemEditableBook() {
/*  20 */     setMaxStackSize(1);
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean validBookTagContents(NBTTagCompound nbt) {
/*  25 */     if (!ItemWritableBook.isNBTValid(nbt))
/*     */     {
/*  27 */       return false;
/*     */     }
/*  29 */     if (!nbt.hasKey("title", 8))
/*     */     {
/*  31 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  35 */     String s = nbt.getString("title");
/*  36 */     return (s != null && s.length() <= 32) ? nbt.hasKey("author", 8) : false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getGeneration(ItemStack book) {
/*  45 */     return book.getTagCompound().getInteger("generation");
/*     */   }
/*     */ 
/*     */   
/*     */   public String getItemStackDisplayName(ItemStack stack) {
/*  50 */     if (stack.hasTagCompound()) {
/*     */       
/*  52 */       NBTTagCompound nbttagcompound = stack.getTagCompound();
/*  53 */       String s = nbttagcompound.getString("title");
/*     */       
/*  55 */       if (!StringUtils.isNullOrEmpty(s))
/*     */       {
/*  57 */         return s;
/*     */       }
/*     */     } 
/*     */     
/*  61 */     return super.getItemStackDisplayName(stack);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
/*  69 */     if (stack.hasTagCompound()) {
/*     */       
/*  71 */       NBTTagCompound nbttagcompound = stack.getTagCompound();
/*  72 */       String s = nbttagcompound.getString("author");
/*     */       
/*  74 */       if (!StringUtils.isNullOrEmpty(s))
/*     */       {
/*  76 */         tooltip.add(EnumChatFormatting.GRAY + StatCollector.translateToLocalFormatted("book.byAuthor", new Object[] { s }));
/*     */       }
/*     */       
/*  79 */       tooltip.add(EnumChatFormatting.GRAY + StatCollector.translateToLocal("book.generation." + nbttagcompound.getInteger("generation")));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
/*  88 */     if (!worldIn.isRemote)
/*     */     {
/*  90 */       resolveContents(itemStackIn, playerIn);
/*     */     }
/*     */     
/*  93 */     playerIn.displayGUIBook(itemStackIn);
/*  94 */     playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
/*  95 */     return itemStackIn;
/*     */   }
/*     */ 
/*     */   
/*     */   private void resolveContents(ItemStack stack, EntityPlayer player) {
/* 100 */     if (stack != null && stack.getTagCompound() != null) {
/*     */       
/* 102 */       NBTTagCompound nbttagcompound = stack.getTagCompound();
/*     */       
/* 104 */       if (!nbttagcompound.getBoolean("resolved")) {
/*     */         
/* 106 */         nbttagcompound.setBoolean("resolved", true);
/*     */         
/* 108 */         if (validBookTagContents(nbttagcompound)) {
/*     */           
/* 110 */           NBTTagList nbttaglist = nbttagcompound.getTagList("pages", 8);
/*     */           
/* 112 */           for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*     */             ChatComponentText chatComponentText;
/* 114 */             String s = nbttaglist.getStringTagAt(i);
/*     */ 
/*     */ 
/*     */             
/*     */             try {
/* 119 */               IChatComponent ichatcomponent = IChatComponent.Serializer.jsonToComponent(s);
/* 120 */               ichatcomponent = ChatComponentProcessor.processComponent((ICommandSender)player, ichatcomponent, (Entity)player);
/*     */             }
/* 122 */             catch (Exception var9) {
/*     */               
/* 124 */               chatComponentText = new ChatComponentText(s);
/*     */             } 
/*     */             
/* 127 */             nbttaglist.set(i, (NBTBase)new NBTTagString(IChatComponent.Serializer.componentToJson((IChatComponent)chatComponentText)));
/*     */           } 
/*     */           
/* 130 */           nbttagcompound.setTag("pages", (NBTBase)nbttaglist);
/*     */           
/* 132 */           if (player instanceof EntityPlayerMP && player.getCurrentEquippedItem() == stack) {
/*     */             
/* 134 */             Slot slot = player.openContainer.getSlotFromInventory((IInventory)player.inventory, player.inventory.currentItem);
/* 135 */             ((EntityPlayerMP)player).playerNetServerHandler.sendPacket((Packet)new S2FPacketSetSlot(0, slot.slotNumber, stack));
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasEffect(ItemStack stack) {
/* 144 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\item\ItemEditableBook.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */