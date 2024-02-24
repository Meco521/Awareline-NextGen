/*    */ package net.minecraft.util;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import java.util.Random;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.tileentity.TileEntityDispenser;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WeightedRandomChestContent
/*    */   extends WeightedRandom.Item
/*    */ {
/*    */   private final ItemStack theItemId;
/*    */   private final int minStackSize;
/*    */   private final int maxStackSize;
/*    */   
/*    */   public WeightedRandomChestContent(Item p_i45311_1_, int p_i45311_2_, int minimumChance, int maximumChance, int itemWeightIn) {
/* 26 */     super(itemWeightIn);
/* 27 */     this.theItemId = new ItemStack(p_i45311_1_, 1, p_i45311_2_);
/* 28 */     this.minStackSize = minimumChance;
/* 29 */     this.maxStackSize = maximumChance;
/*    */   }
/*    */ 
/*    */   
/*    */   public WeightedRandomChestContent(ItemStack stack, int minimumChance, int maximumChance, int itemWeightIn) {
/* 34 */     super(itemWeightIn);
/* 35 */     this.theItemId = stack;
/* 36 */     this.minStackSize = minimumChance;
/* 37 */     this.maxStackSize = maximumChance;
/*    */   }
/*    */ 
/*    */   
/*    */   public static void generateChestContents(Random random, List<WeightedRandomChestContent> listIn, IInventory inv, int max) {
/* 42 */     for (int i = 0; i < max; i++) {
/*    */       
/* 44 */       WeightedRandomChestContent weightedrandomchestcontent = WeightedRandom.<WeightedRandomChestContent>getRandomItem(random, listIn);
/* 45 */       int j = weightedrandomchestcontent.minStackSize + random.nextInt(weightedrandomchestcontent.maxStackSize - weightedrandomchestcontent.minStackSize + 1);
/*    */       
/* 47 */       if (weightedrandomchestcontent.theItemId.getMaxStackSize() >= j) {
/*    */         
/* 49 */         ItemStack itemstack1 = weightedrandomchestcontent.theItemId.copy();
/* 50 */         itemstack1.stackSize = j;
/* 51 */         inv.setInventorySlotContents(random.nextInt(inv.getSizeInventory()), itemstack1);
/*    */       }
/*    */       else {
/*    */         
/* 55 */         for (int k = 0; k < j; k++) {
/*    */           
/* 57 */           ItemStack itemstack = weightedrandomchestcontent.theItemId.copy();
/* 58 */           itemstack.stackSize = 1;
/* 59 */           inv.setInventorySlotContents(random.nextInt(inv.getSizeInventory()), itemstack);
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public static void generateDispenserContents(Random random, List<WeightedRandomChestContent> listIn, TileEntityDispenser dispenser, int max) {
/* 67 */     for (int i = 0; i < max; i++) {
/*    */       
/* 69 */       WeightedRandomChestContent weightedrandomchestcontent = WeightedRandom.<WeightedRandomChestContent>getRandomItem(random, listIn);
/* 70 */       int j = weightedrandomchestcontent.minStackSize + random.nextInt(weightedrandomchestcontent.maxStackSize - weightedrandomchestcontent.minStackSize + 1);
/*    */       
/* 72 */       if (weightedrandomchestcontent.theItemId.getMaxStackSize() >= j) {
/*    */         
/* 74 */         ItemStack itemstack1 = weightedrandomchestcontent.theItemId.copy();
/* 75 */         itemstack1.stackSize = j;
/* 76 */         dispenser.setInventorySlotContents(random.nextInt(dispenser.getSizeInventory()), itemstack1);
/*    */       }
/*    */       else {
/*    */         
/* 80 */         for (int k = 0; k < j; k++) {
/*    */           
/* 82 */           ItemStack itemstack = weightedrandomchestcontent.theItemId.copy();
/* 83 */           itemstack.stackSize = 1;
/* 84 */           dispenser.setInventorySlotContents(random.nextInt(dispenser.getSizeInventory()), itemstack);
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public static List<WeightedRandomChestContent> func_177629_a(List<WeightedRandomChestContent> p_177629_0_, WeightedRandomChestContent... p_177629_1_) {
/* 92 */     List<WeightedRandomChestContent> list = Lists.newArrayList(p_177629_0_);
/* 93 */     Collections.addAll(list, p_177629_1_);
/* 94 */     return list;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraf\\util\WeightedRandomChestContent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */