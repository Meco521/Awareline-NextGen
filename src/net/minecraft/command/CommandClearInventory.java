/*     */ package net.minecraft.command;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.nbt.JsonToNBT;
/*     */ import net.minecraft.nbt.NBTException;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommandClearInventory
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  21 */     return "clear";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandUsage(ICommandSender sender) {
/*  29 */     return "commands.clear.usage";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequiredPermissionLevel() {
/*  37 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/*  45 */     EntityPlayerMP entityplayermp = (args.length == 0) ? getCommandSenderAsPlayer(sender) : getPlayer(sender, args[0]);
/*  46 */     Item item = (args.length >= 2) ? getItemByText(sender, args[1]) : null;
/*  47 */     int i = (args.length >= 3) ? parseInt(args[2], -1) : -1;
/*  48 */     int j = (args.length >= 4) ? parseInt(args[3], -1) : -1;
/*  49 */     NBTTagCompound nbttagcompound = null;
/*     */     
/*  51 */     if (args.length >= 5) {
/*     */       
/*     */       try {
/*     */         
/*  55 */         nbttagcompound = JsonToNBT.getTagFromJson(buildString(args, 4));
/*     */       }
/*  57 */       catch (NBTException nbtexception) {
/*     */         
/*  59 */         throw new CommandException("commands.clear.tagError", new Object[] { nbtexception.getMessage() });
/*     */       } 
/*     */     }
/*     */     
/*  63 */     if (args.length >= 2 && item == null)
/*     */     {
/*  65 */       throw new CommandException("commands.clear.failure", new Object[] { entityplayermp.getName() });
/*     */     }
/*     */ 
/*     */     
/*  69 */     int k = entityplayermp.inventory.clearMatchingItems(item, i, j, nbttagcompound);
/*  70 */     entityplayermp.inventoryContainer.detectAndSendChanges();
/*     */     
/*  72 */     if (!entityplayermp.capabilities.isCreativeMode)
/*     */     {
/*  74 */       entityplayermp.updateHeldItem();
/*     */     }
/*     */     
/*  77 */     sender.setCommandStat(CommandResultStats.Type.AFFECTED_ITEMS, k);
/*     */     
/*  79 */     if (k == 0)
/*     */     {
/*  81 */       throw new CommandException("commands.clear.failure", new Object[] { entityplayermp.getName() });
/*     */     }
/*     */ 
/*     */     
/*  85 */     if (j == 0) {
/*     */       
/*  87 */       sender.addChatMessage((IChatComponent)new ChatComponentTranslation("commands.clear.testing", new Object[] { entityplayermp.getName(), Integer.valueOf(k) }));
/*     */     }
/*     */     else {
/*     */       
/*  91 */       notifyOperators(sender, this, "commands.clear.success", new Object[] { entityplayermp.getName(), Integer.valueOf(k) });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/*  99 */     return (args.length == 1) ? getListOfStringsMatchingLastWord(args, func_147209_d()) : ((args.length == 2) ? getListOfStringsMatchingLastWord(args, Item.itemRegistry.getKeys()) : null);
/*     */   }
/*     */ 
/*     */   
/*     */   protected String[] func_147209_d() {
/* 104 */     return MinecraftServer.getServer().getAllUsernames();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUsernameIndex(String[] args, int index) {
/* 112 */     return (index == 0);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\command\CommandClearInventory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */