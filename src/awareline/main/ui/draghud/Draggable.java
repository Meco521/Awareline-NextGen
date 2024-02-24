/*    */ package awareline.main.ui.draghud;
/*    */ import awareline.main.ui.draghud.component.DraggableComponent;
/*    */ import awareline.main.ui.draghud.component.impl.Health;
/*    */ import awareline.main.ui.draghud.component.impl.KeyStrokes;
/*    */ import awareline.main.ui.draghud.component.impl.Radar;
/*    */ import awareline.main.ui.draghud.component.impl.SessionInfo;
/*    */ import awareline.main.ui.draghud.component.impl.TargetHUDMod;
/*    */ import java.util.Collection;
/*    */ import java.util.LinkedHashMap;
/*    */ 
/*    */ public class Draggable {
/* 12 */   private final LinkedHashMap<Class<? extends DraggableComponent>, DraggableComponent> components = new LinkedHashMap<>(); public DraggableScreen getScreen() {
/* 13 */     return this.screen;
/*    */   }
/*    */   private final DraggableScreen screen;
/*    */   public Draggable() {
/* 17 */     this.screen = new DraggableScreen();
/* 18 */     regMods(new DraggableComponent[] { (DraggableComponent)new TargetHUDMod(), (DraggableComponent)new Radar(), (DraggableComponent)new SessionInfo(), (DraggableComponent)new Health(), (DraggableComponent)new KeyStrokes(), (DraggableComponent)new ModuleIndicator() });
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public final void regMods(DraggableComponent... mods) {
/* 24 */     for (DraggableComponent m : mods) {
/* 25 */       this.components.put(m.getClass(), m);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public final Collection<DraggableComponent> getComponents() {
/* 31 */     return this.components.values();
/*    */   }
/*    */ 
/*    */   
/*    */   public DraggableComponent getDraggableComponentByClass(Class<? extends DraggableComponent> classs) {
/* 36 */     return this.components.get(classs);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\draghud\Draggable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */