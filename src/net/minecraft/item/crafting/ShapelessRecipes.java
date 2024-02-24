/*    */ package net.minecraft.item.crafting;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.List;
/*    */ import net.minecraft.inventory.InventoryCrafting;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ShapelessRecipes
/*    */   implements IRecipe
/*    */ {
/*    */   private final ItemStack recipeOutput;
/*    */   private final List<ItemStack> recipeItems;
/*    */   
/*    */   public ShapelessRecipes(ItemStack output, List<ItemStack> inputList) {
/* 18 */     this.recipeOutput = output;
/* 19 */     this.recipeItems = inputList;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack getRecipeOutput() {
/* 24 */     return this.recipeOutput;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack[] getRemainingItems(InventoryCrafting inv) {
/* 29 */     ItemStack[] aitemstack = new ItemStack[inv.getSizeInventory()];
/*    */     
/* 31 */     for (int i = 0; i < aitemstack.length; i++) {
/*    */       
/* 33 */       ItemStack itemstack = inv.getStackInSlot(i);
/*    */       
/* 35 */       if (itemstack != null && itemstack.getItem().hasContainerItem())
/*    */       {
/* 37 */         aitemstack[i] = new ItemStack(itemstack.getItem().getContainerItem());
/*    */       }
/*    */     } 
/*    */     
/* 41 */     return aitemstack;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean matches(InventoryCrafting inv, World worldIn) {
/* 49 */     List<ItemStack> list = Lists.newArrayList(this.recipeItems);
/*    */     
/* 51 */     for (int i = 0; i < inv.getHeight(); i++) {
/*    */       
/* 53 */       for (int j = 0; j < inv.getWidth(); j++) {
/*    */         
/* 55 */         ItemStack itemstack = inv.getStackInRowAndColumn(j, i);
/*    */         
/* 57 */         if (itemstack != null) {
/*    */           
/* 59 */           boolean flag = false;
/*    */           
/* 61 */           for (ItemStack itemstack1 : list) {
/*    */             
/* 63 */             if (itemstack.getItem() == itemstack1.getItem() && (itemstack1.getMetadata() == 32767 || itemstack.getMetadata() == itemstack1.getMetadata())) {
/*    */               
/* 65 */               flag = true;
/* 66 */               list.remove(itemstack1);
/*    */               
/*    */               break;
/*    */             } 
/*    */           } 
/* 71 */           if (!flag)
/*    */           {
/* 73 */             return false;
/*    */           }
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 79 */     return list.isEmpty();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemStack getCraftingResult(InventoryCrafting inv) {
/* 87 */     return this.recipeOutput.copy();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getRecipeSize() {
/* 95 */     return this.recipeItems.size();
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\item\crafting\ShapelessRecipes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */