/*    */ package awareline.main.utility.chat;
/*    */ 
/*    */ import java.awt.Desktop;
/*    */ import java.io.IOException;
/*    */ import java.net.URI;
/*    */ import net.minecraft.util.EnumChatFormatting;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Helper
/*    */ {
/*    */   public static void sendMessage(String message) {
/* 14 */     (new ChatUtils.ChatMessageBuilder(true)).appendText(message).setColor(EnumChatFormatting.GRAY).build().displayClientSided();
/*    */   }
/*    */   
/*    */   public static void sendMessage(String prefix, String message) {
/* 18 */     (new ChatUtils.ChatMessageBuilder(prefix)).appendText(message).setColor(EnumChatFormatting.GRAY).build().displayClientSided();
/*    */   }
/*    */   
/*    */   public static void sendMessageWithoutPrefix(String message) {
/* 22 */     (new ChatUtils.ChatMessageBuilder(false)).appendText(message).setColor(EnumChatFormatting.GRAY).build().displayClientSided();
/*    */   }
/*    */   
/*    */   public static void addChat(String text) {
/* 26 */     sendMessage(text);
/*    */   }
/*    */   
/*    */   public static void showURL(String url) {
/*    */     try {
/* 31 */       Desktop.getDesktop().browse(new URI(url));
/* 32 */     } catch (IOException|java.net.URISyntaxException e) {
/* 33 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\utility\chat\Helper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */