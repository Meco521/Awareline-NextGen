/*     */ package net.minecraft.command;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldServer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommandParticle
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  18 */     return "particle";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequiredPermissionLevel() {
/*  26 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandUsage(ICommandSender sender) {
/*  34 */     return "commands.particle.usage";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/*  42 */     if (args.length < 8)
/*     */     {
/*  44 */       throw new WrongUsageException("commands.particle.usage", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  48 */     boolean flag = false;
/*  49 */     EnumParticleTypes enumparticletypes = null;
/*     */     
/*  51 */     for (EnumParticleTypes enumparticletypes1 : EnumParticleTypes.values()) {
/*     */       
/*  53 */       if (enumparticletypes1.hasArguments()) {
/*     */         
/*  55 */         if (args[0].startsWith(enumparticletypes1.getParticleName())) {
/*     */           
/*  57 */           flag = true;
/*  58 */           enumparticletypes = enumparticletypes1;
/*     */           
/*     */           break;
/*     */         } 
/*  62 */       } else if (args[0].equals(enumparticletypes1.getParticleName())) {
/*     */         
/*  64 */         flag = true;
/*  65 */         enumparticletypes = enumparticletypes1;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/*  70 */     if (!flag)
/*     */     {
/*  72 */       throw new CommandException("commands.particle.notFound", new Object[] { args[0] });
/*     */     }
/*     */ 
/*     */     
/*  76 */     String s = args[0];
/*  77 */     Vec3 vec3 = sender.getPositionVector();
/*  78 */     double d6 = (float)parseDouble(vec3.xCoord, args[1], true);
/*  79 */     double d0 = (float)parseDouble(vec3.yCoord, args[2], true);
/*  80 */     double d1 = (float)parseDouble(vec3.zCoord, args[3], true);
/*  81 */     double d2 = (float)parseDouble(args[4]);
/*  82 */     double d3 = (float)parseDouble(args[5]);
/*  83 */     double d4 = (float)parseDouble(args[6]);
/*  84 */     double d5 = (float)parseDouble(args[7]);
/*  85 */     int i = 0;
/*     */     
/*  87 */     if (args.length > 8)
/*     */     {
/*  89 */       i = parseInt(args[8], 0);
/*     */     }
/*     */     
/*  92 */     boolean flag1 = false;
/*     */     
/*  94 */     if (args.length > 9 && "force".equals(args[9]))
/*     */     {
/*  96 */       flag1 = true;
/*     */     }
/*     */     
/*  99 */     World world = sender.getEntityWorld();
/*     */     
/* 101 */     if (world instanceof WorldServer) {
/*     */       
/* 103 */       WorldServer worldserver = (WorldServer)world;
/* 104 */       int[] aint = new int[enumparticletypes.getArgumentCount()];
/*     */       
/* 106 */       if (enumparticletypes.hasArguments()) {
/*     */         
/* 108 */         String[] astring = args[0].split("_", 3);
/*     */         
/* 110 */         for (int j = 1; j < astring.length; j++) {
/*     */ 
/*     */           
/*     */           try {
/* 114 */             aint[j - 1] = Integer.parseInt(astring[j]);
/*     */           }
/* 116 */           catch (NumberFormatException var29) {
/*     */             
/* 118 */             throw new CommandException("commands.particle.notFound", new Object[] { args[0] });
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 123 */       worldserver.spawnParticle(enumparticletypes, flag1, d6, d0, d1, i, d2, d3, d4, d5, aint);
/* 124 */       notifyOperators(sender, this, "commands.particle.success", new Object[] { s, Integer.valueOf(Math.max(i, 1)) });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 132 */     return (args.length == 1) ? getListOfStringsMatchingLastWord(args, EnumParticleTypes.getParticleNames()) : ((args.length > 1 && args.length <= 4) ? func_175771_a(args, 1, pos) : ((args.length == 10) ? getListOfStringsMatchingLastWord(args, new String[] { "normal", "force" }) : null));
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\command\CommandParticle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */