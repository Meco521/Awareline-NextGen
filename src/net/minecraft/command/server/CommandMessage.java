/*    */ package net.minecraft.command.server;
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import net.minecraft.command.CommandBase;
/*    */ import net.minecraft.command.CommandException;
/*    */ import net.minecraft.command.ICommandSender;
/*    */ import net.minecraft.command.PlayerNotFoundException;
/*    */ import net.minecraft.command.WrongUsageException;
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.ChatComponentTranslation;
/*    */ import net.minecraft.util.EnumChatFormatting;
/*    */ import net.minecraft.util.IChatComponent;
/*    */ 
/*    */ public class CommandMessage extends CommandBase {
/*    */   public List<String> getCommandAliases() {
/* 18 */     return Arrays.asList(new String[] { "w", "msg" });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCommandName() {
/* 26 */     return "tell";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getRequiredPermissionLevel() {
/* 34 */     return 0;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCommandUsage(ICommandSender sender) {
/* 42 */     return "commands.message.usage";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/* 50 */     if (args.length < 2)
/*    */     {
/* 52 */       throw new WrongUsageException("commands.message.usage", new Object[0]);
/*    */     }
/*    */ 
/*    */     
/* 56 */     EntityPlayerMP entityPlayerMP = getPlayer(sender, args[0]);
/*    */     
/* 58 */     if (entityPlayerMP == sender)
/*    */     {
/* 60 */       throw new PlayerNotFoundException("commands.message.sameTarget", new Object[0]);
/*    */     }
/*    */ 
/*    */     
/* 64 */     IChatComponent ichatcomponent = getChatComponentFromNthArg(sender, args, 1, !(sender instanceof net.minecraft.entity.player.EntityPlayer));
/* 65 */     ChatComponentTranslation chatcomponenttranslation = new ChatComponentTranslation("commands.message.display.incoming", new Object[] { sender.getDisplayName(), ichatcomponent.createCopy() });
/* 66 */     ChatComponentTranslation chatcomponenttranslation1 = new ChatComponentTranslation("commands.message.display.outgoing", new Object[] { entityPlayerMP.getDisplayName(), ichatcomponent.createCopy() });
/* 67 */     chatcomponenttranslation.getChatStyle().setColor(EnumChatFormatting.GRAY).setItalic(Boolean.valueOf(true));
/* 68 */     chatcomponenttranslation1.getChatStyle().setColor(EnumChatFormatting.GRAY).setItalic(Boolean.valueOf(true));
/* 69 */     entityPlayerMP.addChatMessage((IChatComponent)chatcomponenttranslation);
/* 70 */     sender.addChatMessage((IChatComponent)chatcomponenttranslation1);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 77 */     return getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isUsernameIndex(String[] args, int index) {
/* 85 */     return (index == 0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\command\server\CommandMessage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */