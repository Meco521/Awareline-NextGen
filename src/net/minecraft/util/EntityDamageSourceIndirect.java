/*    */ package net.minecraft.util;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class EntityDamageSourceIndirect
/*    */   extends EntityDamageSource
/*    */ {
/*    */   private final Entity indirectEntity;
/*    */   
/*    */   public EntityDamageSourceIndirect(String damageTypeIn, Entity source, Entity indirectEntityIn) {
/* 13 */     super(damageTypeIn, source);
/* 14 */     this.indirectEntity = indirectEntityIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public Entity getSourceOfDamage() {
/* 19 */     return this.damageSourceEntity;
/*    */   }
/*    */ 
/*    */   
/*    */   public Entity getEntity() {
/* 24 */     return this.indirectEntity;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IChatComponent getDeathMessage(EntityLivingBase entityLivingBaseIn) {
/* 34 */     IChatComponent ichatcomponent = (this.indirectEntity == null) ? this.damageSourceEntity.getDisplayName() : this.indirectEntity.getDisplayName();
/* 35 */     ItemStack itemstack = (this.indirectEntity instanceof EntityLivingBase) ? ((EntityLivingBase)this.indirectEntity).getHeldItem() : null;
/* 36 */     String s = "death.attack." + this.damageType;
/* 37 */     String s1 = s + ".item";
/* 38 */     return (itemstack != null && itemstack.hasDisplayName() && StatCollector.canTranslate(s1)) ? new ChatComponentTranslation(s1, new Object[] { entityLivingBaseIn.getDisplayName(), ichatcomponent, itemstack.getChatComponent() }) : new ChatComponentTranslation(s, new Object[] { entityLivingBaseIn.getDisplayName(), ichatcomponent });
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraf\\util\EntityDamageSourceIndirect.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */