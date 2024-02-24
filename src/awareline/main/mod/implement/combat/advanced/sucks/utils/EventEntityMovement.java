/*    */ package awareline.main.mod.implement.combat.advanced.sucks.utils;
/*    */ 
/*    */ import awareline.main.event.Event;
/*    */ import net.minecraft.entity.Entity;
/*    */ 
/*    */ public class EventEntityMovement extends Event {
/*    */   final Entity entity;
/*    */   
/*    */   public EventEntityMovement(Entity entityIn) {
/* 10 */     this.entity = entityIn;
/*    */   }
/*    */   
/*    */   public Entity getMovedEntity() {
/* 14 */     return this.entity;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\combat\advanced\suck\\utils\EventEntityMovement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */