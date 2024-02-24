/*    */ package awareline.main.ui.draghud.component.impl;
/*    */ 
/*    */ import awareline.main.ui.draghud.component.DraggableComponent;
/*    */ 
/*    */ public class Health
/*    */   extends DraggableComponent {
/*    */   public Health() {
/*  8 */     super("Health", 4, 280, 4, 1);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean allowDraw() {
/* 13 */     return awareline.main.mod.implement.visual.Health.getInstance.isEnabled();
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\draghud\component\impl\Health.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */