/*    */ package awareline.main.ui.draghud.component.impl;
/*    */ 
/*    */ import awareline.main.ui.draghud.component.DraggableComponent;
/*    */ 
/*    */ public class ModuleIndicator
/*    */   extends DraggableComponent {
/*    */   public ModuleIndicator() {
/*  8 */     super("ModuleIndicator", 0, 150, 4, 1);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean allowDraw() {
/* 13 */     return awareline.main.mod.implement.visual.ModuleIndicator.getInstance.isEnabled();
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\draghud\component\impl\ModuleIndicator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */