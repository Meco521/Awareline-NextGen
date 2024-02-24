/*    */ package awareline.main.event.events.misc;
/*    */ 
/*    */ import awareline.main.event.Event;
/*    */ 
/*    */ public class EventRenderEntity extends Event {
/*    */   private final float partialTicks;
/*    */   private final double x;
/*    */   private final double y;
/*    */   
/* 10 */   public float getPartialTicks() { return this.partialTicks; } private final double z; Entity entity; Event.State state; public double getX() {
/* 11 */     return this.x; } public double getY() { return this.y; } public double getZ() { return this.z; }
/* 12 */   public Entity getEntity() { return this.entity; } public Event.State getState() {
/* 13 */     return this.state;
/*    */   }
/*    */   public EventRenderEntity(Entity entity, Event.State state, float partialTicks, double x, double y, double z) {
/* 16 */     this.entity = entity;
/* 17 */     this.state = state;
/* 18 */     this.partialTicks = partialTicks;
/* 19 */     this.x = x;
/* 20 */     this.y = y;
/* 21 */     this.z = z;
/*    */   }
/*    */   
/*    */   public void setEntity(Entity entity) {
/* 25 */     this.entity = entity;
/*    */   }
/*    */   
/*    */   public void setEntity(EntityLivingBase entity) {
/* 29 */     this.entity = (Entity)entity;
/*    */   }
/*    */   
/*    */   public void setState(Event.State state) {
/* 33 */     this.state = state;
/*    */   }
/*    */   
/*    */   public boolean isPost() {
/* 37 */     return (this.state == Event.State.POST);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\event\events\misc\EventRenderEntity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */