/*    */ package net.optifine.config;
/*    */ 
/*    */ import net.minecraft.src.Config;
/*    */ 
/*    */ 
/*    */ public class VillagerProfession
/*    */ {
/*    */   private final int profession;
/*    */   private int[] careers;
/*    */   
/*    */   public VillagerProfession(int profession) {
/* 12 */     this(profession, (int[])null);
/*    */   }
/*    */ 
/*    */   
/*    */   public VillagerProfession(int profession, int career) {
/* 17 */     this(profession, new int[] { career });
/*    */   }
/*    */ 
/*    */   
/*    */   public VillagerProfession(int profession, int[] careers) {
/* 22 */     this.profession = profession;
/* 23 */     this.careers = careers;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matches(int prof, int car) {
/* 28 */     return (this.profession != prof) ? false : ((this.careers == null || Config.equalsOne(car, this.careers)));
/*    */   }
/*    */ 
/*    */   
/*    */   private boolean hasCareer(int car) {
/* 33 */     return (this.careers == null) ? false : Config.equalsOne(car, this.careers);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean addCareer(int car) {
/* 38 */     if (this.careers == null) {
/*    */       
/* 40 */       this.careers = new int[] { car };
/* 41 */       return true;
/*    */     } 
/* 43 */     if (hasCareer(car))
/*    */     {
/* 45 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 49 */     this.careers = Config.addIntToArray(this.careers, car);
/* 50 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getProfession() {
/* 56 */     return this.profession;
/*    */   }
/*    */ 
/*    */   
/*    */   public int[] getCareers() {
/* 61 */     return this.careers;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 66 */     return (this.careers == null) ? String.valueOf(this.profession) : (this.profession + ":" + Config.arrayToString(this.careers));
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\config\VillagerProfession.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */