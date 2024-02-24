/*    */ package net.optifine.config;
/*    */ 
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public enum Weather
/*    */ {
/*  7 */   CLEAR,
/*  8 */   RAIN,
/*  9 */   THUNDER;
/*    */ 
/*    */   
/*    */   public static Weather getWeather(World world, float partialTicks) {
/* 13 */     float f = world.getThunderStrength(partialTicks);
/*    */     
/* 15 */     if (f > 0.5F)
/*    */     {
/* 17 */       return THUNDER;
/*    */     }
/*    */ 
/*    */     
/* 21 */     float f1 = world.getRainStrength(partialTicks);
/* 22 */     return (f1 > 0.5F) ? RAIN : CLEAR;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\config\Weather.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */