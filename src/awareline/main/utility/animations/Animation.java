/*    */ package awareline.main.utility.animations;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class Animation
/*    */ {
/*    */   protected int duration;
/*    */   protected double endPoint;
/*    */   protected Direction direction;
/* 16 */   public final TimerUtil timerUtil = new TimerUtil();
/*    */   public double getEndPoint() {
/* 18 */     return this.endPoint;
/*    */   } public Direction getDirection() {
/* 20 */     return this.direction;
/*    */   }
/*    */   
/*    */   public Animation(int ms, double endPoint) {
/* 24 */     this(ms, endPoint, Direction.FORWARDS);
/*    */   }
/*    */   
/*    */   public Animation(int ms, double endPoint, Direction direction) {
/* 28 */     this.duration = ms;
/* 29 */     this.endPoint = endPoint;
/* 30 */     this.direction = direction;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean finished(Direction direction) {
/* 35 */     return (isDone() && this.direction.equals(direction));
/*    */   }
/*    */   
/*    */   public double getLinearOutput() {
/* 39 */     return 1.0D - this.timerUtil.getTime() / this.duration * this.endPoint;
/*    */   }
/*    */   
/*    */   public void setEndPoint(double endPoint) {
/* 43 */     this.endPoint = endPoint;
/*    */   }
/*    */   
/*    */   public void reset() {
/* 47 */     this.timerUtil.reset();
/*    */   }
/*    */   
/*    */   public boolean isDone() {
/* 51 */     return this.timerUtil.hasTimeElapsed(this.duration);
/*    */   }
/*    */   
/*    */   public void changeDirection() {
/* 55 */     setDirection(this.direction.opposite());
/*    */   }
/*    */   
/*    */   public Animation setDirection(Direction direction) {
/* 59 */     if (this.direction != direction) {
/* 60 */       this.direction = direction;
/* 61 */       this.timerUtil.setTime(System.currentTimeMillis() - this.duration - Math.min(this.duration, this.timerUtil.getTime()));
/*    */     } 
/* 63 */     return this;
/*    */   }
/*    */   
/*    */   public void setDuration(int duration) {
/* 67 */     this.duration = duration;
/*    */   }
/*    */   
/*    */   protected boolean correctOutput() {
/* 71 */     return false;
/*    */   }
/*    */   
/*    */   public Double getOutput() {
/* 75 */     if (this.direction.forwards()) {
/* 76 */       if (isDone()) {
/* 77 */         return Double.valueOf(this.endPoint);
/*    */       }
/*    */       
/* 80 */       return Double.valueOf(getEquation(this.timerUtil.getTime() / this.duration) * this.endPoint);
/*    */     } 
/* 82 */     if (isDone()) {
/* 83 */       return Double.valueOf(0.0D);
/*    */     }
/*    */     
/* 86 */     if (correctOutput()) {
/* 87 */       double revTime = Math.min(this.duration, Math.max(0L, this.duration - this.timerUtil.getTime()));
/* 88 */       return Double.valueOf(getEquation(revTime / this.duration) * this.endPoint);
/*    */     } 
/*    */     
/* 91 */     return Double.valueOf((1.0D - getEquation(this.timerUtil.getTime() / this.duration)) * this.endPoint);
/*    */   }
/*    */   
/*    */   protected abstract double getEquation(double paramDouble);
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\utility\animations\Animation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */