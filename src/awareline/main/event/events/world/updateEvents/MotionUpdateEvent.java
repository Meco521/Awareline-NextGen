/*     */ package awareline.main.event.events.world.updateEvents;
/*     */ 
/*     */ import awareline.main.event.Event;
/*     */ 
/*     */ public class MotionUpdateEvent
/*     */   extends Event {
/*     */   private float yaw;
/*     */   private float pitch;
/*     */   private double posY;
/*     */   private double posZ;
/*     */   
/*     */   public float getYaw() {
/*  13 */     return this.yaw;
/*     */   }
/*     */   private double posX; private boolean onGround; private State state; private EventPreUpdate event;
/*     */   
/*     */   public float getPitch() {
/*  18 */     return this.pitch; }
/*  19 */   public double getPosY() { return this.posY; }
/*  20 */   public double getPosZ() { return this.posZ; } public double getPosX() {
/*  21 */     return this.posX;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOnGround() {
/*  26 */     return this.onGround;
/*     */   } public State getState() {
/*  28 */     return this.state;
/*     */   } public EventPreUpdate getEvent() {
/*  30 */     return this.event;
/*     */   }
/*     */   public MotionUpdateEvent(EventPreUpdate eventPreUpdate, double posX, double posY, double posZ, float yaw, float pitch, boolean onGround, State state) {
/*  33 */     this.posX = posX;
/*  34 */     this.posY = posY;
/*  35 */     this.posZ = posZ;
/*  36 */     this.yaw = yaw;
/*  37 */     this.pitch = pitch;
/*  38 */     this.onGround = onGround;
/*  39 */     this.state = state;
/*  40 */     this.event = eventPreUpdate;
/*     */   }
/*     */   
/*     */   public MotionUpdateEvent(State state) {
/*  44 */     this.state = state;
/*     */   }
/*     */   
/*     */   public enum State {
/*  48 */     PRE,
/*  49 */     POST;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getY() {
/*  56 */     return this.posY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getZ() {
/*  63 */     return this.posZ;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getX() {
/*  70 */     return this.posX;
/*     */   }
/*     */   
/*     */   public void setYaw(float yaw) {
/*  74 */     this.event.setYaw(yaw);
/*  75 */     this.yaw = yaw;
/*     */   }
/*     */   
/*     */   public void setPitch(float pitch) {
/*  79 */     this.event.setPitch(pitch);
/*  80 */     this.pitch = pitch;
/*     */   }
/*     */   
/*     */   public void setY(double posY) {
/*  84 */     this.event.setY(posY);
/*  85 */     this.posY = posY;
/*     */   }
/*     */   
/*     */   public void setZ(double posZ) {
/*  89 */     this.event.setZ(posZ);
/*  90 */     this.posZ = posZ;
/*     */   }
/*     */   
/*     */   public void setX(double posX) {
/*  94 */     this.event.setX(posX);
/*  95 */     this.posX = posX;
/*     */   }
/*     */   
/*     */   public void setOnGround(boolean onGround) {
/*  99 */     this.event.setOnGround(onGround);
/* 100 */     this.onGround = onGround;
/*     */   }
/*     */   
/*     */   public void setState(State state) {
/* 104 */     this.state = state;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\event\events\worl\\updateEvents\MotionUpdateEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */