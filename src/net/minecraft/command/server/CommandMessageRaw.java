/*    */ package net.minecraft.command.server;
/*    */ 
/*    */ import com.google.gson.JsonParseException;
/*    */ import java.util.List;
/*    */ import net.minecraft.command.CommandBase;
/*    */ import net.minecraft.command.CommandException;
/*    */ import net.minecraft.command.ICommandSender;
/*    */ import net.minecraft.command.SyntaxErrorException;
/*    */ import net.minecraft.command.WrongUsageException;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.ChatComponentProcessor;
/*    */ import net.minecraft.util.IChatComponent;
/*    */ import org.apache.commons.lang3.exception.ExceptionUtils;
/*    */ 
/*    */ public class CommandMessageRaw
/*    */   extends CommandBase {
/*    */   public String getCommandName() {
/* 21 */     return "tellraw";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getRequiredPermissionLevel() {
/* 29 */     return 2;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCommandUsage(ICommandSender sender) {
/* 37 */     return "commands.tellraw.usage";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/* 45 */     if (args.length < 2)
/*    */     {
/* 47 */       throw new WrongUsageException("commands.tellraw.usage", new Object[0]);
/*    */     }
/*    */ 
/*    */     
/* 51 */     EntityPlayerMP entityPlayerMP = getPlayer(sender, args[0]);
/* 52 */     String s = buildString(args, 1);
/*    */ 
/*    */     
/*    */     try {
/* 56 */       IChatComponent ichatcomponent = IChatComponent.Serializer.jsonToComponent(s);
/* 57 */       entityPlayerMP.addChatMessage(ChatComponentProcessor.processComponent(sender, ichatcomponent, (Entity)entityPlayerMP));
/*    */     }
/* 59 */     catch (JsonParseException jsonparseexception) {
/*    */       
/* 61 */       Throwable throwable = ExceptionUtils.getRootCause((Throwable)jsonparseexception);
/* 62 */       throw new SyntaxErrorException("commands.tellraw.jsonException", new Object[] { (throwable == null) ? "" : throwable.getMessage() });
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 69 */     return (args.length == 1) ? getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames()) : null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isUsernameIndex(String[] args, int index) {
/* 77 */     return (index == 0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\command\server\CommandMessageRaw.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */