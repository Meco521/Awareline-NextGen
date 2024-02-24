/*     */ package net.minecraft.command.server;
/*     */ 
/*     */ import java.util.EnumSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import net.minecraft.command.CommandBase;
/*     */ import net.minecraft.command.CommandException;
/*     */ import net.minecraft.command.ICommand;
/*     */ import net.minecraft.command.ICommandSender;
/*     */ import net.minecraft.command.WrongUsageException;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.network.play.server.S08PacketPlayerPosLook;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.MathHelper;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommandTeleport
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  25 */     return "tp";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequiredPermissionLevel() {
/*  33 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandUsage(ICommandSender sender) {
/*  41 */     return "commands.tp.usage";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/*     */     Entity entity;
/*  49 */     if (args.length < 1)
/*     */     {
/*  51 */       throw new WrongUsageException("commands.tp.usage", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  55 */     int i = 0;
/*     */ 
/*     */     
/*  58 */     if (args.length != 2 && args.length != 4 && args.length != 6) {
/*     */       
/*  60 */       EntityPlayerMP entityPlayerMP = getCommandSenderAsPlayer(sender);
/*     */     }
/*     */     else {
/*     */       
/*  64 */       entity = getEntity(sender, args[0]);
/*  65 */       i = 1;
/*     */     } 
/*     */     
/*  68 */     if (args.length != 1 && args.length != 2) {
/*     */       
/*  70 */       if (args.length < i + 3)
/*     */       {
/*  72 */         throw new WrongUsageException("commands.tp.usage", new Object[0]);
/*     */       }
/*  74 */       if (entity.worldObj != null)
/*     */       {
/*  76 */         int lvt_5_2_ = i + 1;
/*  77 */         CommandBase.CoordinateArg commandbase$coordinatearg = parseCoordinate(entity.posX, args[i], true);
/*  78 */         CommandBase.CoordinateArg commandbase$coordinatearg1 = parseCoordinate(entity.posY, args[lvt_5_2_++], 0, 0, false);
/*  79 */         CommandBase.CoordinateArg commandbase$coordinatearg2 = parseCoordinate(entity.posZ, args[lvt_5_2_++], true);
/*  80 */         CommandBase.CoordinateArg commandbase$coordinatearg3 = parseCoordinate(entity.rotationYaw, (args.length > lvt_5_2_) ? args[lvt_5_2_++] : "~", false);
/*  81 */         CommandBase.CoordinateArg commandbase$coordinatearg4 = parseCoordinate(entity.rotationPitch, (args.length > lvt_5_2_) ? args[lvt_5_2_] : "~", false);
/*     */         
/*  83 */         if (entity instanceof EntityPlayerMP) {
/*     */           
/*  85 */           Set<S08PacketPlayerPosLook.EnumFlags> set = EnumSet.noneOf(S08PacketPlayerPosLook.EnumFlags.class);
/*     */           
/*  87 */           if (commandbase$coordinatearg.func_179630_c())
/*     */           {
/*  89 */             set.add(S08PacketPlayerPosLook.EnumFlags.X);
/*     */           }
/*     */           
/*  92 */           if (commandbase$coordinatearg1.func_179630_c())
/*     */           {
/*  94 */             set.add(S08PacketPlayerPosLook.EnumFlags.Y);
/*     */           }
/*     */           
/*  97 */           if (commandbase$coordinatearg2.func_179630_c())
/*     */           {
/*  99 */             set.add(S08PacketPlayerPosLook.EnumFlags.Z);
/*     */           }
/*     */           
/* 102 */           if (commandbase$coordinatearg4.func_179630_c())
/*     */           {
/* 104 */             set.add(S08PacketPlayerPosLook.EnumFlags.X_ROT);
/*     */           }
/*     */           
/* 107 */           if (commandbase$coordinatearg3.func_179630_c())
/*     */           {
/* 109 */             set.add(S08PacketPlayerPosLook.EnumFlags.Y_ROT);
/*     */           }
/*     */           
/* 112 */           float f = (float)commandbase$coordinatearg3.func_179629_b();
/*     */           
/* 114 */           if (!commandbase$coordinatearg3.func_179630_c())
/*     */           {
/* 116 */             f = MathHelper.wrapAngleTo180_float(f);
/*     */           }
/*     */           
/* 119 */           float f1 = (float)commandbase$coordinatearg4.func_179629_b();
/*     */           
/* 121 */           if (!commandbase$coordinatearg4.func_179630_c())
/*     */           {
/* 123 */             f1 = MathHelper.wrapAngleTo180_float(f1);
/*     */           }
/*     */           
/* 126 */           if (f1 > 90.0F || f1 < -90.0F) {
/*     */             
/* 128 */             f1 = MathHelper.wrapAngleTo180_float(180.0F - f1);
/* 129 */             f = MathHelper.wrapAngleTo180_float(f + 180.0F);
/*     */           } 
/*     */           
/* 132 */           entity.mountEntity((Entity)null);
/* 133 */           ((EntityPlayerMP)entity).playerNetServerHandler.setPlayerLocation(commandbase$coordinatearg.func_179629_b(), commandbase$coordinatearg1.func_179629_b(), commandbase$coordinatearg2.func_179629_b(), f, f1, set);
/* 134 */           entity.setRotationYawHead(f);
/*     */         }
/*     */         else {
/*     */           
/* 138 */           float f2 = (float)MathHelper.wrapAngleTo180_double(commandbase$coordinatearg3.func_179628_a());
/* 139 */           float f3 = (float)MathHelper.wrapAngleTo180_double(commandbase$coordinatearg4.func_179628_a());
/*     */           
/* 141 */           if (f3 > 90.0F || f3 < -90.0F) {
/*     */             
/* 143 */             f3 = MathHelper.wrapAngleTo180_float(180.0F - f3);
/* 144 */             f2 = MathHelper.wrapAngleTo180_float(f2 + 180.0F);
/*     */           } 
/*     */           
/* 147 */           entity.setLocationAndAngles(commandbase$coordinatearg.func_179628_a(), commandbase$coordinatearg1.func_179628_a(), commandbase$coordinatearg2.func_179628_a(), f2, f3);
/* 148 */           entity.setRotationYawHead(f2);
/*     */         } 
/*     */         
/* 151 */         notifyOperators(sender, (ICommand)this, "commands.tp.success.coordinates", new Object[] { entity.getName(), Double.valueOf(commandbase$coordinatearg.func_179628_a()), Double.valueOf(commandbase$coordinatearg1.func_179628_a()), Double.valueOf(commandbase$coordinatearg2.func_179628_a()) });
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 156 */       Entity entity1 = getEntity(sender, args[args.length - 1]);
/*     */       
/* 158 */       if (entity1.worldObj != entity.worldObj)
/*     */       {
/* 160 */         throw new CommandException("commands.tp.notSameDimension", new Object[0]);
/*     */       }
/*     */ 
/*     */       
/* 164 */       entity.mountEntity((Entity)null);
/*     */       
/* 166 */       if (entity instanceof EntityPlayerMP) {
/*     */         
/* 168 */         ((EntityPlayerMP)entity).playerNetServerHandler.setPlayerLocation(entity1.posX, entity1.posY, entity1.posZ, entity1.rotationYaw, entity1.rotationPitch);
/*     */       }
/*     */       else {
/*     */         
/* 172 */         entity.setLocationAndAngles(entity1.posX, entity1.posY, entity1.posZ, entity1.rotationYaw, entity1.rotationPitch);
/*     */       } 
/*     */       
/* 175 */       notifyOperators(sender, (ICommand)this, "commands.tp.success", new Object[] { entity.getName(), entity1.getName() });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 183 */     return (args.length != 1 && args.length != 2) ? null : getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUsernameIndex(String[] args, int index) {
/* 191 */     return (index == 0);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\command\server\CommandTeleport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */