/*     */ package net.minecraft.inventory;
/*     */ 
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemPickaxe;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.item.crafting.CraftingManager;
/*     */ import net.minecraft.stats.AchievementList;
/*     */ import net.minecraft.stats.StatBase;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SlotCrafting
/*     */   extends Slot
/*     */ {
/*     */   private final InventoryCrafting craftMatrix;
/*     */   private final EntityPlayer thePlayer;
/*     */   private int amountCrafted;
/*     */   
/*     */   public SlotCrafting(EntityPlayer player, InventoryCrafting craftingInventory, IInventory p_i45790_3_, int slotIndex, int xPosition, int yPosition) {
/*  25 */     super(p_i45790_3_, slotIndex, xPosition, yPosition);
/*  26 */     this.thePlayer = player;
/*  27 */     this.craftMatrix = craftingInventory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isItemValid(ItemStack stack) {
/*  35 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack decrStackSize(int amount) {
/*  44 */     if (getHasStack())
/*     */     {
/*  46 */       this.amountCrafted += Math.min(amount, (getStack()).stackSize);
/*     */     }
/*     */     
/*  49 */     return super.decrStackSize(amount);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onCrafting(ItemStack stack, int amount) {
/*  58 */     this.amountCrafted += amount;
/*  59 */     onCrafting(stack);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onCrafting(ItemStack stack) {
/*  67 */     if (this.amountCrafted > 0)
/*     */     {
/*  69 */       stack.onCrafting(this.thePlayer.worldObj, this.thePlayer, this.amountCrafted);
/*     */     }
/*     */     
/*  72 */     this.amountCrafted = 0;
/*     */     
/*  74 */     if (stack.getItem() == Item.getItemFromBlock(Blocks.crafting_table))
/*     */     {
/*  76 */       this.thePlayer.triggerAchievement((StatBase)AchievementList.buildWorkBench);
/*     */     }
/*     */     
/*  79 */     if (stack.getItem() instanceof ItemPickaxe)
/*     */     {
/*  81 */       this.thePlayer.triggerAchievement((StatBase)AchievementList.buildPickaxe);
/*     */     }
/*     */     
/*  84 */     if (stack.getItem() == Item.getItemFromBlock(Blocks.furnace))
/*     */     {
/*  86 */       this.thePlayer.triggerAchievement((StatBase)AchievementList.buildFurnace);
/*     */     }
/*     */     
/*  89 */     if (stack.getItem() instanceof net.minecraft.item.ItemHoe)
/*     */     {
/*  91 */       this.thePlayer.triggerAchievement((StatBase)AchievementList.buildHoe);
/*     */     }
/*     */     
/*  94 */     if (stack.getItem() == Items.bread)
/*     */     {
/*  96 */       this.thePlayer.triggerAchievement((StatBase)AchievementList.makeBread);
/*     */     }
/*     */     
/*  99 */     if (stack.getItem() == Items.cake)
/*     */     {
/* 101 */       this.thePlayer.triggerAchievement((StatBase)AchievementList.bakeCake);
/*     */     }
/*     */     
/* 104 */     if (stack.getItem() instanceof ItemPickaxe && ((ItemPickaxe)stack.getItem()).getToolMaterial() != Item.ToolMaterial.WOOD)
/*     */     {
/* 106 */       this.thePlayer.triggerAchievement((StatBase)AchievementList.buildBetterPickaxe);
/*     */     }
/*     */     
/* 109 */     if (stack.getItem() instanceof net.minecraft.item.ItemSword)
/*     */     {
/* 111 */       this.thePlayer.triggerAchievement((StatBase)AchievementList.buildSword);
/*     */     }
/*     */     
/* 114 */     if (stack.getItem() == Item.getItemFromBlock(Blocks.enchanting_table))
/*     */     {
/* 116 */       this.thePlayer.triggerAchievement((StatBase)AchievementList.enchantments);
/*     */     }
/*     */     
/* 119 */     if (stack.getItem() == Item.getItemFromBlock(Blocks.bookshelf))
/*     */     {
/* 121 */       this.thePlayer.triggerAchievement((StatBase)AchievementList.bookcase);
/*     */     }
/*     */     
/* 124 */     if (stack.getItem() == Items.golden_apple && stack.getMetadata() == 1)
/*     */     {
/* 126 */       this.thePlayer.triggerAchievement((StatBase)AchievementList.overpowered);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onPickupFromSlot(EntityPlayer playerIn, ItemStack stack) {
/* 132 */     onCrafting(stack);
/* 133 */     ItemStack[] aitemstack = CraftingManager.getInstance().func_180303_b(this.craftMatrix, playerIn.worldObj);
/*     */     
/* 135 */     for (int i = 0; i < aitemstack.length; i++) {
/*     */       
/* 137 */       ItemStack itemstack = this.craftMatrix.getStackInSlot(i);
/* 138 */       ItemStack itemstack1 = aitemstack[i];
/*     */       
/* 140 */       if (itemstack != null)
/*     */       {
/* 142 */         this.craftMatrix.decrStackSize(i, 1);
/*     */       }
/*     */       
/* 145 */       if (itemstack1 != null)
/*     */       {
/* 147 */         if (this.craftMatrix.getStackInSlot(i) == null) {
/*     */           
/* 149 */           this.craftMatrix.setInventorySlotContents(i, itemstack1);
/*     */         }
/* 151 */         else if (!this.thePlayer.inventory.addItemStackToInventory(itemstack1)) {
/*     */           
/* 153 */           this.thePlayer.dropPlayerItemWithRandomChoice(itemstack1, false);
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\inventory\SlotCrafting.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */