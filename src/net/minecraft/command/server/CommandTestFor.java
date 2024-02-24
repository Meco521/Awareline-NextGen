/*    */ package net.minecraft.command.server;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.command.CommandBase;
/*    */ import net.minecraft.command.CommandException;
/*    */ import net.minecraft.command.ICommand;
/*    */ import net.minecraft.command.ICommandSender;
/*    */ import net.minecraft.command.WrongUsageException;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.nbt.JsonToNBT;
/*    */ import net.minecraft.nbt.NBTBase;
/*    */ import net.minecraft.nbt.NBTException;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.nbt.NBTUtil;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.util.BlockPos;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandTestFor
/*    */   extends CommandBase
/*    */ {
/*    */   public String getCommandName() {
/* 24 */     return "testfor";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getRequiredPermissionLevel() {
/* 32 */     return 2;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCommandUsage(ICommandSender sender) {
/* 40 */     return "commands.testfor.usage";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/* 48 */     if (args.length < 1)
/*    */     {
/* 50 */       throw new WrongUsageException("commands.testfor.usage", new Object[0]);
/*    */     }
/*    */ 
/*    */     
/* 54 */     Entity entity = getEntity(sender, args[0]);
/* 55 */     NBTTagCompound nbttagcompound = null;
/*    */     
/* 57 */     if (args.length >= 2) {
/*    */       
/*    */       try {
/*    */         
/* 61 */         nbttagcompound = JsonToNBT.getTagFromJson(buildString(args, 1));
/*    */       }
/* 63 */       catch (NBTException nbtexception) {
/*    */         
/* 65 */         throw new CommandException("commands.testfor.tagError", new Object[] { nbtexception.getMessage() });
/*    */       } 
/*    */     }
/*    */     
/* 69 */     if (nbttagcompound != null) {
/*    */       
/* 71 */       NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/* 72 */       entity.writeToNBT(nbttagcompound1);
/*    */       
/* 74 */       if (!NBTUtil.func_181123_a((NBTBase)nbttagcompound, (NBTBase)nbttagcompound1, true))
/*    */       {
/* 76 */         throw new CommandException("commands.testfor.failure", new Object[] { entity.getName() });
/*    */       }
/*    */     } 
/*    */     
/* 80 */     notifyOperators(sender, (ICommand)this, "commands.testfor.success", new Object[] { entity.getName() });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isUsernameIndex(String[] args, int index) {
/* 89 */     return (index == 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 94 */     return (args.length == 1) ? getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames()) : null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\command\server\CommandTestFor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */