/*    */ package awareline.main.cmd.cmds;
/*    */ 
/*    */ import awareline.main.Client;
/*    */ import awareline.main.cmd.Command;
/*    */ import awareline.main.utility.chat.Helper;
/*    */ import com.allatori.annotations.StringEncryption;
/*    */ import com.allatori.annotations.StringEncryptionType;
/*    */ 
/*    */ @StringEncryption("maximum")
/*    */ @StringEncryptionType("fast")
/*    */ public class Prefix
/*    */   extends Command {
/*    */   public Prefix() {
/* 14 */     super("prefix");
/*    */   }
/*    */   
/*    */   public void execute(String[] args) {
/* 18 */     if (args.length == 0) {
/* 19 */       Client.instance.prefix = Client.instance.lastPrefix;
/*    */     } else {
/* 21 */       StringBuilder Ganga = null;
/* 22 */       boolean a = false;
/* 23 */       for (String s : args) {
/* 24 */         if (!a) {
/* 25 */           Ganga = new StringBuilder(s);
/* 26 */           a = true;
/*    */         } else {
/*    */           
/* 29 */           Ganga.append(" ").append(s);
/*    */         } 
/* 31 */       }  Client.instance.prefix = Ganga.toString();
/* 32 */       Helper.sendMessage("Prefix is " + Ganga + " now!");
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\cmd\cmds\Prefix.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */