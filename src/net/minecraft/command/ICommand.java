package net.minecraft.command;

import java.util.List;
import net.minecraft.util.BlockPos;

public interface ICommand extends Comparable<ICommand> {
  String getCommandName();
  
  String getCommandUsage(ICommandSender paramICommandSender);
  
  List<String> getCommandAliases();
  
  void processCommand(ICommandSender paramICommandSender, String[] paramArrayOfString) throws CommandException;
  
  boolean canCommandSenderUseCommand(ICommandSender paramICommandSender);
  
  List<String> addTabCompletionOptions(ICommandSender paramICommandSender, String[] paramArrayOfString, BlockPos paramBlockPos);
  
  boolean isUsernameIndex(String[] paramArrayOfString, int paramInt);
}


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\command\ICommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */