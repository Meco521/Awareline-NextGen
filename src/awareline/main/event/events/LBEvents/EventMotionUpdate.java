/*     */ package awareline.main.event.events.LBEvents;public final class EventMotionUpdate extends Event {
/*     */   private final EventPreUpdate event;
/*     */   private double posX;
/*     */   private double lastPosX;
/*     */   private double posY;
/*     */   private double lastPosY;
/*     */   private double posZ;
/*     */   
/*     */   public double getPosX() {
/*  10 */     return this.posX;
/*     */   } private double lastPosZ; private float yaw; private float lastYaw; private float pitch; private float lastPitch; private boolean onGround; private Type type; public double getLastPosX() {
/*  12 */     return this.lastPosX;
/*     */   } public double getPosY() {
/*  14 */     return this.posY;
/*     */   } public double getLastPosY() {
/*  16 */     return this.lastPosY;
/*     */   } public double getPosZ() {
/*  18 */     return this.posZ;
/*     */   } public double getLastPosZ() {
/*  20 */     return this.lastPosZ;
/*     */   } public float getYaw() {
/*  22 */     return this.yaw;
/*     */   } public float getLastYaw() {
/*  24 */     return this.lastYaw;
/*     */   } public float getPitch() {
/*  26 */     return this.pitch;
/*     */   } public float getLastPitch() {
/*  28 */     return this.lastPitch;
/*     */   } public boolean isOnGround() {
/*  30 */     return this.onGround;
/*     */   }
/*     */ 
/*     */   
/*     */   public EventMotionUpdate(EventPreUpdate eventPreUpdate, double posX, double posY, double posZ, float yaw, float pitch, boolean onGround, Type type) {
/*  35 */     this.posX = posX;
/*  36 */     this.posY = posY;
/*  37 */     this.posZ = posZ;
/*  38 */     this.yaw = yaw;
/*  39 */     this.pitch = pitch;
/*  40 */     this.onGround = onGround;
/*  41 */     this.type = type;
/*  42 */     this.event = eventPreUpdate;
/*     */   }
/*     */   
/*     */   public boolean isPre() {
/*  46 */     return (this.type == Type.PRE);
/*     */   }
/*     */   
/*     */   public void setPosX(double posX) {
/*  50 */     this.event.setX(posX);
/*  51 */     this.posX = posX;
/*     */   }
/*     */   
/*     */   public void setLastPosX(double lastPosX) {
/*  55 */     this.lastPosX = lastPosX;
/*     */   }
/*     */   
/*     */   public void setPosY(double posY) {
/*  59 */     this.event.setY(posY);
/*  60 */     this.posY = posY;
/*     */   }
/*     */   
/*     */   public void setLastPosY(double lastPosY) {
/*  64 */     this.lastPosY = lastPosY;
/*     */   }
/*     */   
/*     */   public void setPosZ(double posZ) {
/*  68 */     this.event.setZ(posZ);
/*  69 */     this.posZ = posZ;
/*     */   }
/*     */   
/*     */   public void setLastPosZ(double lastPosZ) {
/*  73 */     this.lastPosZ = lastPosZ;
/*     */   }
/*     */   
/*     */   public void setYaw(float yaw) {
/*  77 */     this.event.setYaw(this.yaw = yaw);
/*     */   }
/*     */   
/*     */   public void setLastYaw(float lastYaw) {
/*  81 */     this.lastYaw = lastYaw;
/*     */   }
/*     */   
/*     */   public void setPitch(float pitch) {
/*  85 */     this.event.setPitch(this.pitch = pitch);
/*     */   }
/*     */   
/*     */   public void setLastPitch(float lastPitch) {
/*  89 */     this.lastPitch = lastPitch;
/*     */   }
/*     */   
/*     */   public void setOnGround(boolean onGround) {
/*  93 */     this.event.setOnGround(onGround);
/*  94 */     this.onGround = onGround;
/*     */   }
/*     */   
/*     */   public Type getTypes() {
/*  98 */     return this.type;
/*     */   }
/*     */   
/*     */   public void setType(Type type) {
/* 102 */     this.type = type;
/*     */   }
/*     */   
/*     */   public enum Type {
/* 106 */     PRE,
/* 107 */     POST;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\event\events\LBEvents\EventMotionUpdate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */