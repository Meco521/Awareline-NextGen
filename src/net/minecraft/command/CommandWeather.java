/*    */ package net.minecraft.command;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.Random;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.WorldServer;
/*    */ import net.minecraft.world.storage.WorldInfo;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandWeather
/*    */   extends CommandBase
/*    */ {
/*    */   public String getCommandName() {
/* 18 */     return "weather";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getRequiredPermissionLevel() {
/* 26 */     return 2;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCommandUsage(ICommandSender sender) {
/* 34 */     return "commands.weather.usage";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/* 42 */     if (args.length >= 1 && args.length <= 2) {
/*    */       
/* 44 */       int i = (300 + (new Random()).nextInt(600)) * 20;
/*    */       
/* 46 */       if (args.length >= 2)
/*    */       {
/* 48 */         i = parseInt(args[1], 1, 1000000) * 20;
/*    */       }
/*    */       
/* 51 */       WorldServer worldServer = (MinecraftServer.getServer()).worldServers[0];
/* 52 */       WorldInfo worldinfo = worldServer.getWorldInfo();
/*    */       
/* 54 */       if ("clear".equalsIgnoreCase(args[0]))
/*    */       {
/* 56 */         worldinfo.setCleanWeatherTime(i);
/* 57 */         worldinfo.setRainTime(0);
/* 58 */         worldinfo.setThunderTime(0);
/* 59 */         worldinfo.setRaining(false);
/* 60 */         worldinfo.setThundering(false);
/* 61 */         notifyOperators(sender, this, "commands.weather.clear", new Object[0]);
/*    */       }
/* 63 */       else if ("rain".equalsIgnoreCase(args[0]))
/*    */       {
/* 65 */         worldinfo.setCleanWeatherTime(0);
/* 66 */         worldinfo.setRainTime(i);
/* 67 */         worldinfo.setThunderTime(i);
/* 68 */         worldinfo.setRaining(true);
/* 69 */         worldinfo.setThundering(false);
/* 70 */         notifyOperators(sender, this, "commands.weather.rain", new Object[0]);
/*    */       }
/*    */       else
/*    */       {
/* 74 */         if (!"thunder".equalsIgnoreCase(args[0]))
/*    */         {
/* 76 */           throw new WrongUsageException("commands.weather.usage", new Object[0]);
/*    */         }
/*    */         
/* 79 */         worldinfo.setCleanWeatherTime(0);
/* 80 */         worldinfo.setRainTime(i);
/* 81 */         worldinfo.setThunderTime(i);
/* 82 */         worldinfo.setRaining(true);
/* 83 */         worldinfo.setThundering(true);
/* 84 */         notifyOperators(sender, this, "commands.weather.thunder", new Object[0]);
/*    */       }
/*    */     
/*    */     } else {
/*    */       
/* 89 */       throw new WrongUsageException("commands.weather.usage", new Object[0]);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 95 */     return (args.length == 1) ? getListOfStringsMatchingLastWord(args, new String[] { "clear", "rain", "thunder" }) : null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\command\CommandWeather.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */