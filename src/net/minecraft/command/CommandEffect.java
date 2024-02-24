/*     */ package net.minecraft.command;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommandEffect
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  19 */     return "effect";
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
/*  35 */     return "commands.effect.usage";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/*  43 */     if (args.length < 2)
/*     */     {
/*  45 */       throw new WrongUsageException("commands.effect.usage", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  49 */     EntityLivingBase entitylivingbase = getEntity(sender, args[0], EntityLivingBase.class);
/*     */     
/*  51 */     if (args[1].equals("clear")) {
/*     */       
/*  53 */       if (entitylivingbase.getActivePotionEffects().isEmpty())
/*     */       {
/*  55 */         throw new CommandException("commands.effect.failure.notActive.all", new Object[] { entitylivingbase.getName() });
/*     */       }
/*     */ 
/*     */       
/*  59 */       entitylivingbase.clearActivePotions();
/*  60 */       notifyOperators(sender, this, "commands.effect.success.removed.all", new Object[] { entitylivingbase.getName() });
/*     */     } else {
/*     */       int i;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/*  69 */         i = parseInt(args[1], 1);
/*     */       }
/*  71 */       catch (NumberInvalidException numberinvalidexception) {
/*     */         
/*  73 */         Potion potion = Potion.getPotionFromResourceLocation(args[1]);
/*     */         
/*  75 */         if (potion == null)
/*     */         {
/*  77 */           throw numberinvalidexception;
/*     */         }
/*     */         
/*  80 */         i = potion.id;
/*     */       } 
/*     */       
/*  83 */       int j = 600;
/*  84 */       int l = 30;
/*  85 */       int k = 0;
/*     */       
/*  87 */       if (i >= 0 && i < Potion.potionTypes.length && Potion.potionTypes[i] != null) {
/*     */         
/*  89 */         Potion potion1 = Potion.potionTypes[i];
/*     */         
/*  91 */         if (args.length >= 3) {
/*     */           
/*  93 */           l = parseInt(args[2], 0, 1000000);
/*     */           
/*  95 */           if (potion1.isInstant())
/*     */           {
/*  97 */             j = l;
/*     */           }
/*     */           else
/*     */           {
/* 101 */             j = l * 20;
/*     */           }
/*     */         
/* 104 */         } else if (potion1.isInstant()) {
/*     */           
/* 106 */           j = 1;
/*     */         } 
/*     */         
/* 109 */         if (args.length >= 4)
/*     */         {
/* 111 */           k = parseInt(args[3], 0, 255);
/*     */         }
/*     */         
/* 114 */         boolean flag = true;
/*     */         
/* 116 */         if (args.length >= 5 && "true".equalsIgnoreCase(args[4]))
/*     */         {
/* 118 */           flag = false;
/*     */         }
/*     */         
/* 121 */         if (l > 0)
/*     */         {
/* 123 */           PotionEffect potioneffect = new PotionEffect(i, j, k, false, flag);
/* 124 */           entitylivingbase.addPotionEffect(potioneffect);
/* 125 */           notifyOperators(sender, this, "commands.effect.success", new Object[] { new ChatComponentTranslation(potioneffect.getEffectName(), new Object[0]), Integer.valueOf(i), Integer.valueOf(k), entitylivingbase.getName(), Integer.valueOf(l) });
/*     */         }
/* 127 */         else if (entitylivingbase.isPotionActive(i))
/*     */         {
/* 129 */           entitylivingbase.removePotionEffect(i);
/* 130 */           notifyOperators(sender, this, "commands.effect.success.removed", new Object[] { new ChatComponentTranslation(potion1.getName(), new Object[0]), entitylivingbase.getName() });
/*     */         }
/*     */         else
/*     */         {
/* 134 */           throw new CommandException("commands.effect.failure.notActive", new Object[] { new ChatComponentTranslation(potion1.getName(), new Object[0]), entitylivingbase.getName() });
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 139 */         throw new NumberInvalidException("commands.effect.notFound", new Object[] { Integer.valueOf(i) });
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 147 */     return (args.length == 1) ? getListOfStringsMatchingLastWord(args, getAllUsernames()) : ((args.length == 2) ? getListOfStringsMatchingLastWord(args, Potion.getPotionLocations()) : ((args.length == 5) ? getListOfStringsMatchingLastWord(args, new String[] { "true", "false" }) : null));
/*     */   }
/*     */ 
/*     */   
/*     */   protected String[] getAllUsernames() {
/* 152 */     return MinecraftServer.getServer().getAllUsernames();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUsernameIndex(String[] args, int index) {
/* 160 */     return (index == 0);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\command\CommandEffect.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */