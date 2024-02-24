/*    */ package awareline.main.mod.implement.visual.etype;
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ import awareline.main.mod.values.Numbers;
/*    */ import awareline.main.mod.values.Value;
/*    */ 
/*    */ public class EntityHurtColor extends Module {
/*  8 */   public final Numbers<Double> r = new Numbers("Red", 
/*  9 */       Double.valueOf(120.0D), Double.valueOf(0.0D), Double.valueOf(255.0D), Double.valueOf(5.0D));
/* 10 */   public final Numbers<Double> g = new Numbers("Green", 
/* 11 */       Double.valueOf(120.0D), Double.valueOf(0.0D), Double.valueOf(255.0D), Double.valueOf(5.0D));
/* 12 */   public final Numbers<Double> b = new Numbers("Blue", 
/* 13 */       Double.valueOf(255.0D), Double.valueOf(0.0D), Double.valueOf(255.0D), Double.valueOf(5.0D));
/* 14 */   public final Numbers<Double> alpha = new Numbers("Alpha", 
/* 15 */       Double.valueOf(255.0D), Double.valueOf(0.0D), Double.valueOf(255.0D), Double.valueOf(5.0D));
/*    */   public static EntityHurtColor getInstance;
/*    */   
/*    */   public EntityHurtColor() {
/* 19 */     super("EntityHurtColor", ModuleType.Render);
/* 20 */     addSettings(new Value[] { (Value)this.r, (Value)this.g, (Value)this.b, (Value)this.alpha });
/* 21 */     getInstance = this;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\visual\etype\EntityHurtColor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */