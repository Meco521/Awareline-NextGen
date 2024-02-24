/*    */ package awareline.main.event.events.world.renderEvents;
/*    */ 
/*    */ import net.minecraft.client.gui.ScaledResolution;
/*    */ 
/*    */ public class EventRender2D extends Event {
/*    */   private float partialTicks;
/*    */   private final ScaledResolution resolution;
/*    */   
/*  9 */   public float getPartialTicks() { return this.partialTicks; } private float width; private float height; public ScaledResolution getResolution() {
/* 10 */     return this.resolution; }
/* 11 */   public float getWidth() { return this.width; } public float getHeight() { return this.height; }
/*    */   
/*    */   public EventRender2D(ScaledResolution resolution, float partialTicks) {
/* 14 */     this.resolution = resolution;
/* 15 */     this.partialTicks = partialTicks;
/* 16 */     this.width = resolution.getScaledWidth();
/* 17 */     this.height = resolution.getScaledHeight();
/*    */   }
/*    */   
/*    */   public void setPartialTicks(float partialTicks) {
/* 21 */     this.partialTicks = partialTicks;
/*    */   }
/*    */   
/*    */   public void setHeight(float height) {
/* 25 */     this.height = height;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setWidth(float width) {
/* 30 */     this.width = width;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\event\events\world\renderEvents\EventRender2D.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */