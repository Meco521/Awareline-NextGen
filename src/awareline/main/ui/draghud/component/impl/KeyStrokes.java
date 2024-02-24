/*    */ package awareline.main.ui.draghud.component.impl;
/*    */ 
/*    */ import awareline.main.ui.draghud.component.DraggableComponent;
/*    */ 
/*    */ public class KeyStrokes
/*    */   extends DraggableComponent {
/*    */   public KeyStrokes() {
/*  8 */     super("KeyStrokes", 4, 125, 4, 1);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean allowDraw() {
/* 13 */     return awareline.main.mod.implement.visual.KeyStrokes.getInstance.isEnabled();
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\draghud\component\impl\KeyStrokes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */