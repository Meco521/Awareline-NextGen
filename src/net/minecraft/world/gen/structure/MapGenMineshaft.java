/*    */ package net.minecraft.world.gen.structure;
/*    */ 
/*    */ import java.util.Map;
/*    */ import net.minecraft.util.MathHelper;
/*    */ 
/*    */ 
/*    */ public class MapGenMineshaft
/*    */   extends MapGenStructure
/*    */ {
/* 10 */   private double field_82673_e = 0.004D;
/*    */ 
/*    */ 
/*    */   
/*    */   public MapGenMineshaft() {}
/*    */ 
/*    */   
/*    */   public String getStructureName() {
/* 18 */     return "Mineshaft";
/*    */   }
/*    */ 
/*    */   
/*    */   public MapGenMineshaft(Map<String, String> p_i2034_1_) {
/* 23 */     for (Map.Entry<String, String> entry : p_i2034_1_.entrySet()) {
/*    */       
/* 25 */       if (((String)entry.getKey()).equals("chance"))
/*    */       {
/* 27 */         this.field_82673_e = MathHelper.parseDoubleWithDefault(entry.getValue(), this.field_82673_e);
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean canSpawnStructureAtCoords(int chunkX, int chunkZ) {
/* 34 */     return (this.rand.nextDouble() < this.field_82673_e && this.rand.nextInt(80) < Math.max(Math.abs(chunkX), Math.abs(chunkZ)));
/*    */   }
/*    */ 
/*    */   
/*    */   protected StructureStart getStructureStart(int chunkX, int chunkZ) {
/* 39 */     return new StructureMineshaftStart(this.worldObj, this.rand, chunkX, chunkZ);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\gen\structure\MapGenMineshaft.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */