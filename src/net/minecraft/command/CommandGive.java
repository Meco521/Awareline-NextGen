/*     */ package net.minecraft.command;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.JsonToNBT;
/*     */ import net.minecraft.nbt.NBTException;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.BlockPos;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommandGive
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  21 */     return "give";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequiredPermissionLevel() {
/*  29 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandUsage(ICommandSender sender) {
/*  37 */     return "commands.give.usage";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/*  45 */     if (args.length < 2)
/*     */     {
/*  47 */       throw new WrongUsageException("commands.give.usage", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  51 */     EntityPlayerMP entityPlayerMP = getPlayer(sender, args[0]);
/*  52 */     Item item = getItemByText(sender, args[1]);
/*  53 */     int i = (args.length >= 3) ? parseInt(args[2], 1, 64) : 1;
/*  54 */     int j = (args.length >= 4) ? parseInt(args[3]) : 0;
/*  55 */     ItemStack itemstack = new ItemStack(item, i, j);
/*     */     
/*  57 */     if (args.length >= 5) {
/*     */       
/*  59 */       String s = getChatComponentFromNthArg(sender, args, 4).getUnformattedText();
/*     */ 
/*     */       
/*     */       try {
/*  63 */         itemstack.setTagCompound(JsonToNBT.getTagFromJson(s));
/*     */       }
/*  65 */       catch (NBTException nbtexception) {
/*     */         
/*  67 */         throw new CommandException("commands.give.tagError", new Object[] { nbtexception.getMessage() });
/*     */       } 
/*     */     } 
/*     */     
/*  71 */     boolean flag = ((EntityPlayer)entityPlayerMP).inventory.addItemStackToInventory(itemstack);
/*     */     
/*  73 */     if (flag) {
/*     */       
/*  75 */       ((EntityPlayer)entityPlayerMP).worldObj.playSoundAtEntity((Entity)entityPlayerMP, "random.pop", 0.2F, ((entityPlayerMP.getRNG().nextFloat() - entityPlayerMP.getRNG().nextFloat()) * 0.7F + 1.0F) * 2.0F);
/*  76 */       ((EntityPlayer)entityPlayerMP).inventoryContainer.detectAndSendChanges();
/*     */     } 
/*     */     
/*  79 */     if (flag && itemstack.stackSize <= 0) {
/*     */       
/*  81 */       itemstack.stackSize = 1;
/*  82 */       sender.setCommandStat(CommandResultStats.Type.AFFECTED_ITEMS, i);
/*  83 */       EntityItem entityitem1 = entityPlayerMP.dropPlayerItemWithRandomChoice(itemstack, false);
/*     */       
/*  85 */       if (entityitem1 != null)
/*     */       {
/*  87 */         entityitem1.func_174870_v();
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/*  92 */       sender.setCommandStat(CommandResultStats.Type.AFFECTED_ITEMS, i - itemstack.stackSize);
/*  93 */       EntityItem entityitem = entityPlayerMP.dropPlayerItemWithRandomChoice(itemstack, false);
/*     */       
/*  95 */       if (entityitem != null) {
/*     */         
/*  97 */         entityitem.setNoPickupDelay();
/*  98 */         entityitem.setOwner(entityPlayerMP.getName());
/*     */       } 
/*     */     } 
/*     */     
/* 102 */     notifyOperators(sender, this, "commands.give.success", new Object[] { itemstack.getChatComponent(), Integer.valueOf(i), entityPlayerMP.getName() });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 108 */     return (args.length == 1) ? getListOfStringsMatchingLastWord(args, getPlayers()) : ((args.length == 2) ? getListOfStringsMatchingLastWord(args, Item.itemRegistry.getKeys()) : null);
/*     */   }
/*     */ 
/*     */   
/*     */   protected String[] getPlayers() {
/* 113 */     return MinecraftServer.getServer().getAllUsernames();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUsernameIndex(String[] args, int index) {
/* 121 */     return (index == 0);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\command\CommandGive.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */