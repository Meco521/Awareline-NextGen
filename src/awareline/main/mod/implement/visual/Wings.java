/*    */ package awareline.main.mod.implement.visual;
/*    */ 
/*    */ import awareline.main.event.EventHandler;
/*    */ import awareline.main.event.events.misc.EventRenderEntity;
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ import awareline.main.mod.implement.visual.wings.WingModel;
/*    */ import awareline.main.mod.values.Numbers;
/*    */ import awareline.main.mod.values.Value;
/*    */ import java.awt.Color;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ 
/*    */ public class Wings extends Module {
/* 14 */   public final Numbers<Double> red = new Numbers("Red", Double.valueOf(255.0D), Double.valueOf(0.0D), Double.valueOf(255.0D), Double.valueOf(1.0D)); public static Wings getInstance;
/* 15 */   public final Numbers<Double> blue = new Numbers("Blue", Double.valueOf(255.0D), Double.valueOf(0.0D), Double.valueOf(255.0D), Double.valueOf(1.0D));
/* 16 */   public final Numbers<Double> green = new Numbers("Green", Double.valueOf(255.0D), Double.valueOf(0.0D), Double.valueOf(255.0D), Double.valueOf(1.0D));
/* 17 */   public final Numbers<Double> scale = new Numbers("Scale", Double.valueOf(1.0D), Double.valueOf(1.25D), Double.valueOf(0.75D), Double.valueOf(0.25D));
/* 18 */   private final WingModel model = new WingModel();
/*    */   
/*    */   public Wings() {
/* 21 */     super("Wings", new String[] { "Wings" }, ModuleType.Render);
/* 22 */     addSettings(new Value[] { (Value)this.red, (Value)this.blue, (Value)this.green, (Value)this.scale });
/* 23 */     getInstance = this;
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onRendererLivingEntityEvent(EventRenderEntity event) {
/* 28 */     if (event.isPost() && event.getEntity() == mc.thePlayer && !mc.thePlayer.isInvisible())
/* 29 */       this.model.renderWings((EntityPlayer)mc.thePlayer, event.getPartialTicks(), ((Double)this.scale
/* 30 */           .getValue()).doubleValue(), new Color(((Double)this.red.getValue()).intValue(), ((Double)this.green.get()).intValue(), ((Double)this.blue.get()).intValue())); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\visual\Wings.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */