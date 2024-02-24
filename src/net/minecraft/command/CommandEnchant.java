/*     */ package net.minecraft.command;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.enchantment.Enchantment;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.BlockPos;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommandEnchant
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  19 */     return "enchant";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequiredPermissionLevel() {
/*  27 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandUsage(ICommandSender sender) {
/*  35 */     return "commands.enchant.usage";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/*     */     int i;
/*  43 */     if (args.length < 2)
/*     */     {
/*  45 */       throw new WrongUsageException("commands.enchant.usage", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  49 */     EntityPlayerMP entityPlayerMP = getPlayer(sender, args[0]);
/*  50 */     sender.setCommandStat(CommandResultStats.Type.AFFECTED_ITEMS, 0);
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  55 */       i = parseInt(args[1], 0);
/*     */     }
/*  57 */     catch (NumberInvalidException numberinvalidexception) {
/*     */       
/*  59 */       Enchantment enchantment = Enchantment.getEnchantmentByLocation(args[1]);
/*     */       
/*  61 */       if (enchantment == null)
/*     */       {
/*  63 */         throw numberinvalidexception;
/*     */       }
/*     */       
/*  66 */       i = enchantment.effectId;
/*     */     } 
/*     */     
/*  69 */     int j = 1;
/*  70 */     ItemStack itemstack = entityPlayerMP.getCurrentEquippedItem();
/*     */     
/*  72 */     if (itemstack == null)
/*     */     {
/*  74 */       throw new CommandException("commands.enchant.noItem", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  78 */     Enchantment enchantment1 = Enchantment.getEnchantmentById(i);
/*     */     
/*  80 */     if (enchantment1 == null)
/*     */     {
/*  82 */       throw new NumberInvalidException("commands.enchant.notFound", new Object[] { Integer.valueOf(i) });
/*     */     }
/*  84 */     if (!enchantment1.canApply(itemstack))
/*     */     {
/*  86 */       throw new CommandException("commands.enchant.cantEnchant", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  90 */     if (args.length >= 3)
/*     */     {
/*  92 */       j = parseInt(args[2], enchantment1.getMinLevel(), enchantment1.getMaxLevel());
/*     */     }
/*     */     
/*  95 */     if (itemstack.hasTagCompound()) {
/*     */       
/*  97 */       NBTTagList nbttaglist = itemstack.getEnchantmentTagList();
/*     */       
/*  99 */       if (nbttaglist != null)
/*     */       {
/* 101 */         for (int k = 0; k < nbttaglist.tagCount(); k++) {
/*     */           
/* 103 */           int l = nbttaglist.getCompoundTagAt(k).getShort("id");
/*     */           
/* 105 */           if (Enchantment.getEnchantmentById(l) != null) {
/*     */             
/* 107 */             Enchantment enchantment2 = Enchantment.getEnchantmentById(l);
/*     */             
/* 109 */             if (!enchantment2.canApplyTogether(enchantment1))
/*     */             {
/* 111 */               throw new CommandException("commands.enchant.cantCombine", new Object[] { enchantment1.getTranslatedName(j), enchantment2.getTranslatedName(nbttaglist.getCompoundTagAt(k).getShort("lvl")) });
/*     */             }
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 118 */     itemstack.addEnchantment(enchantment1, j);
/* 119 */     notifyOperators(sender, this, "commands.enchant.success", new Object[0]);
/* 120 */     sender.setCommandStat(CommandResultStats.Type.AFFECTED_ITEMS, 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 128 */     return (args.length == 1) ? getListOfStringsMatchingLastWord(args, getListOfPlayers()) : ((args.length == 2) ? getListOfStringsMatchingLastWord(args, Enchantment.func_181077_c()) : null);
/*     */   }
/*     */ 
/*     */   
/*     */   protected String[] getListOfPlayers() {
/* 133 */     return MinecraftServer.getServer().getAllUsernames();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUsernameIndex(String[] args, int index) {
/* 141 */     return (index == 0);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\command\CommandEnchant.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */