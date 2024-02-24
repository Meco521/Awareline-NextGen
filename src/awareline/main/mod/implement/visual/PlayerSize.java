/*    */ package awareline.main.mod.implement.visual;
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ import awareline.main.mod.values.Numbers;
/*    */ import awareline.main.mod.values.Value;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.entity.Entity;
/*    */ 
/*    */ public class PlayerSize extends Module {
/* 10 */   public static final Numbers<Double> sizeValue = new Numbers("Size", 
/* 11 */       Double.valueOf(1.0D), Double.valueOf(0.0D), Double.valueOf(10.0D), Double.valueOf(0.1D));
/*    */   public static PlayerSize getInstance;
/*    */   
/*    */   public PlayerSize() {
/* 15 */     super("PlayerSize", ModuleType.Render);
/* 16 */     addSettings(new Value[] { (Value)sizeValue });
/* 17 */     getInstance = this;
/*    */   }
/*    */   
/*    */   public static void setSize(Entity entityIn) {
/* 21 */     float size = ((Double)sizeValue.getValue()).floatValue();
/* 22 */     if (size == 1.0F) {
/*    */       return;
/*    */     }
/* 25 */     if (entityIn == mc.thePlayer && getInstance
/* 26 */       .isEnabled())
/* 27 */       GlStateManager.scale(size, size, size); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\visual\PlayerSize.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */