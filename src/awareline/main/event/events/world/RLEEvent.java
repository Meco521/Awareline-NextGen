/*    */ package awareline.main.event.events.world;
/*    */ public class RLEEvent extends Event {
/*    */   private final EntityLivingBase entity;
/*    */   private float limbSwing;
/*    */   private float limbSwingAmount;
/*    */   private float ageInTicks;
/*    */   private float rotationYawHead;
/*    */   
/*  9 */   public EntityLivingBase getEntity() { return this.entity; } private float rotationPitch; private float chestRot; private float offset; private float fuckingTick; private final boolean pre; public float getLimbSwing() {
/* 10 */     return this.limbSwing; }
/* 11 */   public float getLimbSwingAmount() { return this.limbSwingAmount; }
/* 12 */   public float getAgeInTicks() { return this.ageInTicks; }
/* 13 */   public float getRotationYawHead() { return this.rotationYawHead; }
/* 14 */   public float getRotationPitch() { return this.rotationPitch; }
/* 15 */   public float getChestRot() { return this.chestRot; }
/* 16 */   public float getOffset() { return this.offset; }
/* 17 */   public float getFuckingTick() { return this.fuckingTick; } public boolean isPre() {
/* 18 */     return this.pre;
/*    */   }
/*    */   public RLEEvent(EntityLivingBase entity, float limbSwing, float limbSwingAmount, float ageInTicks, float rotationYawHead, float rotationPitch, float chestRot, float offset, float fuckingTick) {
/* 21 */     this.entity = entity;
/* 22 */     this.limbSwing = limbSwing;
/* 23 */     this.limbSwingAmount = limbSwingAmount;
/* 24 */     this.ageInTicks = ageInTicks;
/* 25 */     this.rotationYawHead = rotationYawHead;
/* 26 */     this.rotationPitch = rotationPitch;
/* 27 */     this.chestRot = chestRot;
/* 28 */     this.offset = offset;
/* 29 */     this.fuckingTick = fuckingTick;
/* 30 */     this.pre = true;
/*    */   }
/*    */   
/*    */   public RLEEvent(EntityLivingBase entity) {
/* 34 */     this.entity = entity;
/* 35 */     this.pre = false;
/*    */   }
/*    */   
/*    */   public void setLimbSwing(float limbSwing) {
/* 39 */     this.limbSwing = limbSwing;
/*    */   }
/*    */   
/*    */   public void setLimbSwingAmount(float limbSwingAmount) {
/* 43 */     this.limbSwingAmount = limbSwingAmount;
/*    */   }
/*    */   
/*    */   public void setAgeInTicks(float ageInTicks) {
/* 47 */     this.ageInTicks = ageInTicks;
/*    */   }
/*    */   
/*    */   public void setRotationYawHead(float rotationYawHead) {
/* 51 */     this.rotationYawHead = rotationYawHead;
/*    */   }
/*    */   
/*    */   public void setRotationPitch(float rotationPitch) {
/* 55 */     this.rotationPitch = rotationPitch;
/*    */   }
/*    */   
/*    */   public void setOffset(float offset) {
/* 59 */     this.offset = offset;
/*    */   }
/*    */   
/*    */   public float getRotationChest() {
/* 63 */     return this.chestRot;
/*    */   }
/*    */   
/*    */   public void setRotationChest(float rotationChest) {
/* 67 */     this.chestRot = rotationChest;
/*    */   }
/*    */   
/*    */   public float getTick() {
/* 71 */     return this.fuckingTick;
/*    */   }
/*    */   
/*    */   public boolean isPost() {
/* 75 */     return !this.pre;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\event\events\world\RLEEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */