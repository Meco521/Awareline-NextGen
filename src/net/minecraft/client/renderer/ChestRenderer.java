/*    */ package net.minecraft.client.renderer;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ public class ChestRenderer
/*    */ {
/*    */   public void renderChestBrightness(Block p_178175_1_, float color) {
/* 11 */     GlStateManager.color(color, color, color, 1.0F);
/* 12 */     GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
/* 13 */     TileEntityItemStackRenderer.instance.renderByItem(new ItemStack(p_178175_1_));
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\ChestRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */