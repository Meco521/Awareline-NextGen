/*    */ package awareline.main.event.events.ketaShaderCall;
/*    */ 
/*    */ import awareline.main.event.Event;
/*    */ import net.minecraft.client.gui.ScaledResolution;
/*    */ 
/*    */ public final class RenderGameOverlayEvent
/*    */   extends Event {
/*    */   private final float partialTicks;
/*    */   private final ScaledResolution scaledResolution;
/*    */   private boolean renderBossHealth = true;
/*    */   private boolean renderCrossHair = true;
/*    */   
/*    */   public RenderGameOverlayEvent(float partialTicks, ScaledResolution scaledResolution) {
/* 14 */     this.partialTicks = partialTicks;
/* 15 */     this.scaledResolution = scaledResolution;
/*    */   }
/*    */   
/*    */   public boolean isRenderBossHealth() {
/* 19 */     return this.renderBossHealth;
/*    */   }
/*    */   
/*    */   public void setRenderBossHealth(boolean renderBossHealth) {
/* 23 */     this.renderBossHealth = renderBossHealth;
/*    */   }
/*    */   
/*    */   public boolean isRenderCrossHair() {
/* 27 */     return this.renderCrossHair;
/*    */   }
/*    */   
/*    */   public void setRenderCrossHair(boolean renderCrossHair) {
/* 31 */     this.renderCrossHair = renderCrossHair;
/*    */   }
/*    */   
/*    */   public float getPartialTicks() {
/* 35 */     return this.partialTicks;
/*    */   }
/*    */   
/*    */   public ScaledResolution getScaledResolution() {
/* 39 */     return this.scaledResolution;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\event\events\ketaShaderCall\RenderGameOverlayEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */