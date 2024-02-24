/*    */ package awareline.main.ui.draghud.component;
/*    */ public class DraggableComponent {
/*    */   private final String name;
/*    */   private float x;
/*    */   private float y;
/*    */   private float width;
/*    */   
/*  8 */   public String getName() { return this.name; } private float height; private boolean drag; private float prevX; private float prevY; public float getX() {
/*  9 */     return this.x; } public float getY() { return this.y; } public float getWidth() { return this.width; } public float getHeight() { return this.height; }
/*    */ 
/*    */   
/* 12 */   public boolean isDrag() { return this.drag; }
/* 13 */   public float getPrevX() { return this.prevX; } public float getPrevY() { return this.prevY; }
/*    */   
/*    */   public void setX(float x) {
/* 16 */     this.x = x;
/*    */   }
/*    */   
/*    */   public void setY(float y) {
/* 20 */     this.y = y;
/*    */   }
/*    */   
/*    */   public void setWidth(float width) {
/* 24 */     this.width = width;
/*    */   }
/*    */   
/*    */   public void setHeight(int height) {
/* 28 */     this.height = height;
/*    */   }
/*    */   
/*    */   public DraggableComponent(String name, int x, int y, int width, int height) {
/* 32 */     this.name = name;
/* 33 */     this.x = x;
/* 34 */     this.y = y;
/* 35 */     this.width = width;
/* 36 */     this.height = height;
/*    */   }
/*    */   
/*    */   public void draw(int mouseX, int mouseY) {
/* 40 */     if (this.drag) {
/* 41 */       this.x = mouseX - this.prevX;
/* 42 */       this.y = mouseY - this.prevY;
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean isMouseHoveringOnRect(double x, double y, double width, double height, int mouseX, int mouseY) {
/* 47 */     return (mouseX >= x && mouseY >= y && mouseX <= x + width && mouseY <= y + height);
/*    */   }
/*    */   
/*    */   public void click(int mouseX, int mouseY) {
/* 51 */     if (isMouseHoveringOnRect(this.x, this.y, this.width, this.height, mouseX, mouseY)) {
/* 52 */       this.drag = true;
/* 53 */       this.prevX = mouseX - this.x;
/* 54 */       this.prevY = mouseY - this.y;
/*    */     } 
/*    */   }
/*    */   
/*    */   public void release() {
/* 59 */     this.drag = false;
/*    */   }
/*    */   
/*    */   public boolean allowDraw() {
/* 63 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\draghud\component\DraggableComponent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */