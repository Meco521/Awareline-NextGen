/*    */ package net.minecraft.client.renderer.entity;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.projectile.EntityPotion;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class RenderPotion extends RenderSnowball<EntityPotion> {
/*    */   public RenderPotion(RenderManager renderManagerIn, RenderItem itemRendererIn) {
/* 11 */     super(renderManagerIn, (Item)Items.potionitem, itemRendererIn);
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack func_177082_d(EntityPotion entityIn) {
/* 16 */     return new ItemStack(this.field_177084_a, 1, entityIn.getPotionDamage());
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\entity\RenderPotion.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */