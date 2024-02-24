/*    */ package awareline.main.mod.implement.misc;
/*    */ 
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ import net.minecraft.entity.Entity;
/*    */ 
/*    */ public class Teams extends Module {
/*    */   public static Teams getInstance;
/*    */   
/*    */   public Teams() {
/* 11 */     super("Teams", ModuleType.Misc);
/* 12 */     getInstance = this;
/*    */   }
/*    */   
/*    */   public boolean isOnSameTeam(Entity entity) {
/* 16 */     if (!isEnabled()) {
/* 17 */       return false;
/*    */     }
/* 19 */     if (!mc.thePlayer.getDisplayName().getUnformattedText().isEmpty() && mc.thePlayer.getDisplayName().getUnformattedText().charAt(0) == '§') {
/* 20 */       if (mc.thePlayer.getDisplayName().getUnformattedText().length() <= 2 || entity.getDisplayName().getUnformattedText().length() <= 2) {
/* 21 */         return false;
/*    */       }
/* 23 */       return mc.thePlayer.getDisplayName().getUnformattedText().substring(0, 2).equals(entity.getDisplayName().getUnformattedText().substring(0, 2));
/*    */     } 
/* 25 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\misc\Teams.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */