/*    */ package awareline.main.event.events.world;
/*    */ 
/*    */ import awareline.main.event.Event;
/*    */ 
/*    */ public class EventEntityBorderSize
/*    */   extends Event {
/*    */   public float getBorderSize() {
/*  8 */     return this.borderSize;
/*    */   } private float borderSize;
/*    */   public EventEntityBorderSize(float borderSize) {
/* 11 */     this.borderSize = borderSize;
/*    */   }
/*    */   
/*    */   public void setBorderSize(float borderSize) {
/* 15 */     this.borderSize = borderSize;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\event\events\world\EventEntityBorderSize.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */