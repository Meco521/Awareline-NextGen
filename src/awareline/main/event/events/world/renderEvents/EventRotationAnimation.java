/*    */ package awareline.main.event.events.world.renderEvents;
/*    */ 
/*    */ 
/*    */ public class EventRotationAnimation extends Event {
/*    */   private EntityLivingBase entity;
/*    */   private float rotationYawHead;
/*    */   private float renderYawOffset;
/*    */   
/*  9 */   public void setEntity(EntityLivingBase entity) { this.entity = entity; } private float renderHeadYaw; private float renderHeadPitch; private final float partialTicks; public void setRotationYawHead(float rotationYawHead) { this.rotationYawHead = rotationYawHead; } public void setRenderYawOffset(float renderYawOffset) { this.renderYawOffset = renderYawOffset; } public void setRenderHeadYaw(float renderHeadYaw) { this.renderHeadYaw = renderHeadYaw; } public void setRenderHeadPitch(float renderHeadPitch) { this.renderHeadPitch = renderHeadPitch; }
/*    */   
/* 11 */   public EntityLivingBase getEntity() { return this.entity; }
/* 12 */   public float getRotationYawHead() { return this.rotationYawHead; }
/* 13 */   public float getRenderYawOffset() { return this.renderYawOffset; }
/* 14 */   public float getRenderHeadYaw() { return this.renderHeadYaw; }
/* 15 */   public float getRenderHeadPitch() { return this.renderHeadPitch; } public float getPartialTicks() {
/* 16 */     return this.partialTicks;
/*    */   }
/*    */   public EventRotationAnimation(EntityLivingBase entity, float renderYawOffset, float rotationYawHead, float renderHeadYaw, float renderHeadPitch, float partialTicks) {
/* 19 */     this.entity = entity;
/* 20 */     this.rotationYawHead = rotationYawHead;
/* 21 */     this.renderYawOffset = renderYawOffset;
/* 22 */     this.renderHeadYaw = renderHeadYaw;
/* 23 */     this.renderHeadPitch = renderHeadPitch;
/* 24 */     this.partialTicks = partialTicks;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\event\events\world\renderEvents\EventRotationAnimation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */