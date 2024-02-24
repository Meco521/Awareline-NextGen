/*    */ package awareline.main.event.events.world;
/*    */ 
/*    */ import awareline.main.event.Event;
/*    */ import net.minecraft.entity.Entity;
/*    */ 
/*    */ public class EventAttack
/*    */   extends Event {
/*    */   public Entity getEntity() {
/*  9 */     return this.entity;
/*    */   } public final Entity entity;
/*    */   public EventAttack(Entity targetEntity) {
/* 12 */     this.entity = targetEntity;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\event\events\world\EventAttack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */