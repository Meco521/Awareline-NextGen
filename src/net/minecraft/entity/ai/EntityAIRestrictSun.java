/*    */ package net.minecraft.entity.ai;
/*    */ 
/*    */ import net.minecraft.entity.EntityCreature;
/*    */ import net.minecraft.pathfinding.PathNavigateGround;
/*    */ 
/*    */ public class EntityAIRestrictSun
/*    */   extends EntityAIBase
/*    */ {
/*    */   private final EntityCreature theEntity;
/*    */   
/*    */   public EntityAIRestrictSun(EntityCreature creature) {
/* 12 */     this.theEntity = creature;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean shouldExecute() {
/* 20 */     return this.theEntity.worldObj.isDaytime();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void startExecuting() {
/* 28 */     ((PathNavigateGround)this.theEntity.getNavigator()).setAvoidSun(true);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void resetTask() {
/* 36 */     ((PathNavigateGround)this.theEntity.getNavigator()).setAvoidSun(false);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\entity\ai\EntityAIRestrictSun.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */