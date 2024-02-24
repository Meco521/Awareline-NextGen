/*    */ package awareline.main.mod.implement.combat;
/*    */ 
/*    */ import awareline.main.event.EventHandler;
/*    */ import awareline.main.event.events.world.EventAttack;
/*    */ import awareline.main.event.events.world.updateEvents.EventPreUpdate;
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ import awareline.main.mod.values.Numbers;
/*    */ import awareline.main.mod.values.Option;
/*    */ import awareline.main.mod.values.Value;
/*    */ import awareline.main.utility.BlockUtils;
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.util.AxisAlignedBB;
/*    */ 
/*    */ public class Reach extends Module {
/*    */   public static Reach getInstance;
/* 18 */   public final Numbers<Double> maxRange = new Numbers("MaxRange", Double.valueOf(4.0D), Double.valueOf(3.0D), Double.valueOf(8.0D), Double.valueOf(0.1D)); public final Numbers<Double> minRange = new Numbers("MinRange", 
/* 19 */       Double.valueOf(3.0D), Double.valueOf(3.0D), Double.valueOf(8.0D), Double.valueOf(0.1D));
/* 20 */   public final Option<Boolean> onlySprint = new Option("OnlySprint", Boolean.valueOf(false)); public final Option<Boolean> onlyMoving = new Option("OnlyMoving", 
/* 21 */       Boolean.valueOf(false));
/* 22 */   public final Option<Boolean> stopInWater = new Option("DisableInWater", Boolean.valueOf(false));
/* 23 */   public final Option<Boolean> combo = new Option("Combo", Boolean.valueOf(false));
/*    */   
/* 25 */   protected final Random rand = new Random();
/* 26 */   public double currentRange = 3.0D; public long lastAttack;
/* 27 */   public long lastMS = -1L;
/*    */   
/*    */   public Reach() {
/* 30 */     super("Reach", ModuleType.Combat);
/* 31 */     addSettings(new Value[] { (Value)this.maxRange, (Value)this.minRange, (Value)this.combo, (Value)this.onlySprint, (Value)this.onlyMoving, (Value)this.stopInWater });
/* 32 */     getInstance = this;
/*    */   }
/*    */   
/*    */   public boolean hasTimePassedMS(long MS) {
/* 36 */     return (getCurrentMS() >= this.lastMS + MS);
/*    */   }
/*    */   
/*    */   public void updatebefore() {
/* 40 */     this.lastMS = getCurrentMS();
/*    */   }
/*    */   
/*    */   public long getCurrentMS() {
/* 44 */     return System.nanoTime() / 1000000L;
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onAttack(EventAttack event) {
/* 49 */     if (event.entity != null) {
/* 50 */       this.lastAttack = System.currentTimeMillis();
/*    */     }
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onUpdate(EventPreUpdate e) {
/* 56 */     setSuffix(Integer.valueOf((int)this.currentRange));
/* 57 */     if (hasTimePassedMS(2000L)) {
/*    */       
/* 59 */       double rangeMin = ((Double)this.minRange.getValue()).doubleValue();
/* 60 */       double rangeMax = ((Double)this.maxRange.getValue()).doubleValue();
/* 61 */       double rangeDiff = rangeMax - rangeMin;
/*    */       
/* 63 */       if (rangeDiff < 0.0D) {
/*    */         return;
/*    */       }
/* 66 */       this.currentRange = rangeMin + this.rand.nextDouble() * rangeDiff;
/* 67 */       updatebefore();
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean check() {
/* 72 */     if (((Boolean)this.stopInWater.get()).booleanValue()) {
/*    */       
/* 74 */       if (mc.thePlayer.isInWater() || mc.thePlayer.isInLava()) {
/* 75 */         return false;
/*    */       }
/* 77 */       if (BlockUtils.isOnLiquid() || BlockUtils.isInLiquid()) {
/* 78 */         return false;
/*    */       }
/* 80 */       if (!BlockUtils.collideBlock2(mc.thePlayer.getEntityBoundingBox(), block -> block instanceof net.minecraft.block.BlockLiquid))
/* 81 */       { if (BlockUtils.collideBlock2(new AxisAlignedBB((mc.thePlayer.getEntityBoundingBox()).maxX, (mc.thePlayer.getEntityBoundingBox()).maxY, 
/* 82 */               (mc.thePlayer.getEntityBoundingBox()).maxZ, (mc.thePlayer.getEntityBoundingBox()).minX, 
/* 83 */               (mc.thePlayer.getEntityBoundingBox()).minY - 0.01D, (mc.thePlayer.getEntityBoundingBox()).minZ), block -> block instanceof net.minecraft.block.BlockLiquid))
/*    */         {
/* 85 */           return false; }  } else { return false; }
/*    */     
/*    */     } 
/* 88 */     if (((Boolean)this.combo.getValue()).booleanValue() && System.currentTimeMillis() - this.lastAttack > 300L) {
/* 89 */       return false;
/*    */     }
/* 91 */     if (((Boolean)this.onlyMoving.get()).booleanValue() && !mc.thePlayer.moving()) {
/* 92 */       return false;
/*    */     }
/* 94 */     return (!((Boolean)this.onlySprint.get()).booleanValue() || mc.thePlayer.isSprinting());
/*    */   }
/*    */   
/*    */   public double getMax() {
/* 98 */     return check() ? this.currentRange : mc.playerController.getBlockReachDistance();
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\combat\Reach.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */