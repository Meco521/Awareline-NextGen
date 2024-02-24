/*    */ package awareline.main.cmd;
/*    */ 
/*    */ import awareline.main.InstanceAccess;
/*    */ 
/*    */ public abstract class Command implements InstanceAccess {
/*    */   private final String name;
/*    */   private final String[] alias;
/*    */   
/*  9 */   public String getName() { return this.name; } public String[] getAlias() {
/* 10 */     return this.alias;
/*    */   }
/*    */   public Command(String name, String[] alias) {
/* 13 */     this.name = name.toLowerCase();
/* 14 */     this.alias = alias;
/*    */   }
/*    */   
/*    */   public Command(String name) {
/* 18 */     this.name = name.toLowerCase();
/* 19 */     this.alias = new String[] { name.toLowerCase(), name.toUpperCase() };
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static void syntaxError() {
/* 25 */     Helper.sendMessage("§7Invalid command usage");
/*    */   }
/*    */   
/*    */   public abstract void execute(String[] paramArrayOfString);
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\cmd\Command.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */