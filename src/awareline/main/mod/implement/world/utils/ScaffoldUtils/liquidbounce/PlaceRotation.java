/*    */ package awareline.main.mod.implement.world.utils.ScaffoldUtils.liquidbounce;
/*    */ 
/*    */ import awareline.main.mod.implement.combat.advanced.sucks.utils.liquidbounce.Rotation;
/*    */ 
/*    */ public final class PlaceRotation {
/*    */   private final PlaceInfo placeInfo;
/*    */   private final Rotation rotation;
/*    */   
/*    */   public PlaceInfo getPlaceInfo() {
/* 10 */     return this.placeInfo;
/*    */   }
/*    */   
/*    */   public Rotation getRotation() {
/* 14 */     return this.rotation;
/*    */   }
/*    */   
/*    */   public PlaceRotation(PlaceInfo placeInfo, Rotation rotation) {
/* 18 */     this.placeInfo = placeInfo;
/* 19 */     this.rotation = rotation;
/*    */   }
/*    */   
/*    */   public PlaceInfo component1() {
/* 23 */     return this.placeInfo;
/*    */   }
/*    */   
/*    */   public Rotation component2() {
/* 27 */     return this.rotation;
/*    */   }
/*    */   
/*    */   public PlaceRotation copy(PlaceInfo placeInfo, Rotation rotation) {
/* 31 */     return new PlaceRotation(placeInfo, rotation);
/*    */   }
/*    */   
/*    */   public static PlaceRotation copy$default(PlaceRotation var0, PlaceInfo var1, Rotation var2, int var3, Object var4) {
/* 35 */     if ((var3 & 0x1) != 0) {
/* 36 */       var1 = var0.placeInfo;
/*    */     }
/* 38 */     if ((var3 & 0x2) != 0) {
/* 39 */       var2 = var0.rotation;
/*    */     }
/* 41 */     return var0.copy(var1, var2);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 45 */     return "PlaceRotation(placeInfo=" + this.placeInfo + ", rotation=" + this.rotation + ")";
/*    */   }
/*    */   
/*    */   public int hashCode() {
/* 49 */     return ((this.placeInfo != null) ? this.placeInfo.hashCode() : 0) * 31 + ((this.rotation != null) ? this.rotation.hashCode() : 0);
/*    */   }
/*    */   
/*    */   public boolean equals(Object var1) {
/* 53 */     if (this != var1) {
/* 54 */       if (var1 instanceof PlaceRotation) {
/* 55 */         PlaceRotation var2 = (PlaceRotation)var1;
/* 56 */         return (this.placeInfo.equals(var2.placeInfo) && this.rotation.equals(var2.rotation));
/*    */       } 
/* 58 */       return false;
/*    */     } 
/* 60 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\worl\\utils\ScaffoldUtils\liquidbounce\PlaceRotation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */