/*    */ package awareline.main.ui.draghud;
/*    */ 
/*    */ import awareline.main.Client;
/*    */ import awareline.main.ui.draghud.component.DraggableComponent;
/*    */ 
/*    */ 
/*    */ public class DraggableScreen
/*    */ {
/*    */   public void draw(int mouseX, int mouseY) {
/* 10 */     for (DraggableComponent draggableComponent : Client.instance.draggable.getComponents()) {
/* 11 */       if (draggableComponent.allowDraw()) {
/* 12 */         drawComponent(mouseX, mouseY, draggableComponent);
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   private void drawComponent(int mouseX, int mouseY, DraggableComponent draggableComponent) {
/* 18 */     draggableComponent.draw(mouseX, mouseY);
/*    */     
/* 20 */     boolean hover = isMouseHoveringOnRect(draggableComponent.getX(), draggableComponent.getY(), draggableComponent.getWidth(), draggableComponent.getHeight(), mouseX, mouseY);
/*    */   }
/*    */   
/*    */   public boolean isMouseHoveringOnRect(double x, double y, double width, double height, int mouseX, int mouseY) {
/* 24 */     return (mouseX >= x && mouseY >= y && mouseX <= x + width && mouseY <= y + height);
/*    */   }
/*    */   
/*    */   public void click(int mouseX, int mouseY) {
/* 28 */     for (DraggableComponent draggableComponent : Client.instance.draggable.getComponents()) {
/* 29 */       if (draggableComponent.allowDraw()) {
/* 30 */         draggableComponent.click(mouseX, mouseY);
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   public void release() {
/* 36 */     for (DraggableComponent draggableComponent : Client.instance.draggable.getComponents())
/* 37 */       draggableComponent.release(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\draghud\DraggableScreen.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */