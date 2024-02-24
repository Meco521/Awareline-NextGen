/*    */ package awareline.main.event.events.ketaShaderCall;
/*    */ 
/*    */ import awareline.main.event.Event;
/*    */ import net.minecraft.client.gui.ScaledResolution;
/*    */ 
/*    */ public class EventShader
/*    */   extends Event {
/*    */   private final boolean bloom;
/*    */   private final boolean blur;
/*    */   private final ScaledResolution resolution;
/*    */   
/*    */   public ScaledResolution getResolution() {
/* 13 */     return this.resolution;
/*    */   }
/*    */   public EventShader(boolean bloom, boolean blur, ScaledResolution resolution) {
/* 16 */     this.bloom = bloom;
/* 17 */     this.blur = blur;
/* 18 */     this.resolution = resolution;
/*    */   }
/*    */   
/*    */   public boolean onBloom() {
/* 22 */     return this.bloom;
/*    */   }
/*    */   
/*    */   public boolean onBlur() {
/* 26 */     return this.blur;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\event\events\ketaShaderCall\EventShader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */