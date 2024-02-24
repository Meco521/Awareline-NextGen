/*    */ package awareline.main.utility.chat;
/*    */ 
/*    */ import awareline.main.Client;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.util.ChatComponentText;
/*    */ import net.minecraft.util.ChatStyle;
/*    */ import net.minecraft.util.EnumChatFormatting;
/*    */ import net.minecraft.util.IChatComponent;
/*    */ 
/*    */ public class ChatUtils
/*    */ {
/*    */   private final ChatComponentText message;
/*    */   
/*    */   ChatUtils(ChatComponentText message) {
/* 15 */     this.message = message;
/*    */   }
/*    */   
/*    */   public void displayClientSided() {
/* 19 */     (Minecraft.getMinecraft()).thePlayer.addChatMessage((IChatComponent)this.message);
/*    */   }
/*    */   
/*    */   ChatComponentText getChatComponent() {
/* 23 */     return this.message;
/*    */   }
/*    */   
/*    */   public static class ChatMessageBuilder
/*    */   {
/* 28 */     private static final EnumChatFormatting defaultMessageColor = EnumChatFormatting.WHITE;
/* 29 */     private final ChatComponentText theMessage = new ChatComponentText("");
/*    */     private final boolean useDefaultMessageColor;
/* 31 */     private ChatStyle workingStyle = new ChatStyle();
/* 32 */     private ChatComponentText workerMessage = new ChatComponentText("");
/*    */     
/*    */     private String getPrefix() {
/* 35 */       EnumChatFormatting color = EnumChatFormatting.RED;
/* 36 */       Client.instance.getClass(); return color + "Awareline" + EnumChatFormatting.RESET + color + " > " + EnumChatFormatting.RESET;
/*    */     }
/*    */ 
/*    */ 
/*    */     
/*    */     public ChatMessageBuilder(boolean prependDefaultPrefix) {
/* 42 */       if (prependDefaultPrefix) {
/* 43 */         this.theMessage.appendSibling((IChatComponent)(new ChatMessageBuilder(false)).appendText(
/* 44 */               getPrefix())
/* 45 */             .setColor(EnumChatFormatting.RED).build().getChatComponent());
/*    */       }
/* 47 */       this.useDefaultMessageColor = true;
/*    */     }
/*    */     
/*    */     public ChatMessageBuilder(String text) {
/* 51 */       this.theMessage.appendSibling((IChatComponent)(new ChatMessageBuilder(false)).appendText(EnumChatFormatting.GRAY + "[" + EnumChatFormatting.RED + text + EnumChatFormatting.GRAY + "]" + EnumChatFormatting.GRAY + " ").setColor(EnumChatFormatting.RED).build().getChatComponent());
/* 52 */       this.useDefaultMessageColor = true;
/*    */     }
/*    */     
/*    */     public ChatMessageBuilder appendText(String text) {
/* 56 */       appendSibling();
/* 57 */       this.workerMessage = new ChatComponentText(text);
/* 58 */       this.workingStyle = new ChatStyle();
/* 59 */       if (this.useDefaultMessageColor) {
/* 60 */         setColor(defaultMessageColor);
/*    */       }
/* 62 */       return this;
/*    */     }
/*    */     
/*    */     public ChatMessageBuilder setColor(EnumChatFormatting color) {
/* 66 */       this.workingStyle.setColor(color);
/* 67 */       return this;
/*    */     }
/*    */     
/*    */     public ChatMessageBuilder bold() {
/* 71 */       this.workingStyle.setBold(Boolean.valueOf(true));
/* 72 */       return this;
/*    */     }
/*    */     
/*    */     public ChatMessageBuilder italic() {
/* 76 */       this.workingStyle.setItalic(Boolean.valueOf(true));
/* 77 */       return this;
/*    */     }
/*    */     
/*    */     public ChatMessageBuilder strikethrough() {
/* 81 */       this.workingStyle.setStrikethrough(Boolean.valueOf(true));
/* 82 */       return this;
/*    */     }
/*    */     
/*    */     public ChatMessageBuilder underline() {
/* 86 */       this.workingStyle.setUnderlined(Boolean.valueOf(true));
/* 87 */       return this;
/*    */     }
/*    */     
/*    */     public ChatUtils build() {
/* 91 */       appendSibling();
/* 92 */       return new ChatUtils(this.theMessage);
/*    */     }
/*    */     
/*    */     private void appendSibling() {
/* 96 */       this.theMessage.appendSibling(this.workerMessage.setChatStyle(this.workingStyle));
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\utility\chat\ChatUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */