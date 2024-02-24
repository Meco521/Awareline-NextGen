/*    */ package awareline.main.event.events.world.renderEvents;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ 
/*    */ public class RenderArmEvent extends Event {
/*    */   private final Entity entity;
/*    */   private final boolean pre;
/*    */   
/*  9 */   public Entity getEntity() { return this.entity; } public boolean isPre() {
/* 10 */     return this.pre;
/*    */   }
/*    */   public RenderArmEvent(Entity entity, boolean pre) {
/* 13 */     this.entity = entity;
/* 14 */     this.pre = pre;
/*    */   }
/*    */   
/*    */   public boolean isPost() {
/* 18 */     return !this.pre;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\event\events\world\renderEvents\RenderArmEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */