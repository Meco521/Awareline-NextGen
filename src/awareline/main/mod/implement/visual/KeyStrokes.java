/*    */ package awareline.main.mod.implement.visual;
/*    */ 
/*    */ import awareline.main.Client;
/*    */ import awareline.main.event.EventHandler;
/*    */ import awareline.main.event.events.world.renderEvents.EventRender2D;
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ import awareline.main.ui.animations.AnimationUtil;
/*    */ import awareline.main.ui.gui.hud.minekey.SimpleWhiteKey;
/*    */ import awareline.main.ui.gui.hud.minekey.SimpleWhiteMouseButton;
/*    */ 
/*    */ public class KeyStrokes extends Module {
/*    */   public static KeyStrokes getInstance;
/* 14 */   private final SimpleWhiteKey[] movementSimpleWhiteKeys = new SimpleWhiteKey[4];
/* 15 */   private final SimpleWhiteMouseButton[] simpleWhiteMouseButtons = new SimpleWhiteMouseButton[2];
/*    */   float x;
/*    */   
/*    */   public KeyStrokes() {
/* 19 */     super("KeyStrokes", ModuleType.Render);
/* 20 */     getInstance = this;
/* 21 */     this.movementSimpleWhiteKeys[0] = new SimpleWhiteKey(mc.gameSettings.keyBindForward, 26.0F, 2.0F);
/* 22 */     this.movementSimpleWhiteKeys[1] = new SimpleWhiteKey(mc.gameSettings.keyBindLeft, 2.0F, 26.0F);
/* 23 */     this.movementSimpleWhiteKeys[2] = new SimpleWhiteKey(mc.gameSettings.keyBindBack, 26.0F, 26.0F);
/* 24 */     this.movementSimpleWhiteKeys[3] = new SimpleWhiteKey(mc.gameSettings.keyBindRight, 50.0F, 26.0F);
/* 25 */     this.simpleWhiteMouseButtons[0] = new SimpleWhiteMouseButton(0, 2.0F);
/* 26 */     this.simpleWhiteMouseButtons[1] = new SimpleWhiteMouseButton(1, 38.0F);
/*    */   }
/*    */   float y;
/*    */   
/*    */   public void onDisable() {
/* 31 */     this.x = this.y = 0.0F;
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void renderKeystrokes(EventRender2D e) {
/* 36 */     if (mc.gameSettings.showDebugInfo) {
/*    */       return;
/*    */     }
/* 39 */     awareline.main.ui.draghud.component.impl.KeyStrokes dwm = (awareline.main.ui.draghud.component.impl.KeyStrokes)Client.instance.draggable.getDraggableComponentByClass(awareline.main.ui.draghud.component.impl.KeyStrokes.class);
/*    */     
/* 41 */     dwm.setWidth(80.0F);
/* 42 */     dwm.setHeight(80);
/* 43 */     this.x = AnimationUtil.moveUDFaster(this.x, dwm.getX());
/* 44 */     this.y = AnimationUtil.moveUDFaster(this.y, dwm.getY());
/* 45 */     drawMovementKeys(this.x, this.y);
/* 46 */     drawMouseButtons(this.x, this.y);
/*    */   }
/*    */ 
/*    */   
/*    */   private void drawMovementKeys(float x, float y) {
/* 51 */     for (SimpleWhiteKey simpleWhiteKey : this.movementSimpleWhiteKeys) {
/* 52 */       simpleWhiteKey.renderKey(x, y);
/*    */     }
/*    */   }
/*    */   
/*    */   private void drawMouseButtons(float x, float y) {
/* 57 */     for (SimpleWhiteMouseButton button : this.simpleWhiteMouseButtons)
/* 58 */       button.renderMouseButton(x, y); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\visual\KeyStrokes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */