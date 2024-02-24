/*     */ package net.minecraft.command.server;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.command.CommandBase;
/*     */ import net.minecraft.command.CommandException;
/*     */ import net.minecraft.command.ICommand;
/*     */ import net.minecraft.command.ICommandSender;
/*     */ import net.minecraft.command.WrongUsageException;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityList;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.IEntityLivingData;
/*     */ import net.minecraft.entity.effect.EntityLightningBolt;
/*     */ import net.minecraft.nbt.JsonToNBT;
/*     */ import net.minecraft.nbt.NBTException;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommandSummon
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  29 */     return "summon";
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
/*     */   public String getCommandUsage(ICommandSender sender) {
/*  45 */     return "commands.summon.usage";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/*  53 */     if (args.length < 1)
/*     */     {
/*  55 */       throw new WrongUsageException("commands.summon.usage", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  59 */     String s = args[0];
/*  60 */     BlockPos blockpos = sender.getPosition();
/*  61 */     Vec3 vec3 = sender.getPositionVector();
/*  62 */     double d0 = vec3.xCoord;
/*  63 */     double d1 = vec3.yCoord;
/*  64 */     double d2 = vec3.zCoord;
/*     */     
/*  66 */     if (args.length >= 4) {
/*     */       
/*  68 */       d0 = parseDouble(d0, args[1], true);
/*  69 */       d1 = parseDouble(d1, args[2], false);
/*  70 */       d2 = parseDouble(d2, args[3], true);
/*  71 */       blockpos = new BlockPos(d0, d1, d2);
/*     */     } 
/*     */     
/*  74 */     World world = sender.getEntityWorld();
/*     */     
/*  76 */     if (!world.isBlockLoaded(blockpos))
/*     */     {
/*  78 */       throw new CommandException("commands.summon.outOfWorld", new Object[0]);
/*     */     }
/*  80 */     if ("LightningBolt".equals(s)) {
/*     */       
/*  82 */       world.addWeatherEffect((Entity)new EntityLightningBolt(world, d0, d1, d2));
/*  83 */       notifyOperators(sender, (ICommand)this, "commands.summon.success", new Object[0]);
/*     */     } else {
/*     */       Entity entity2;
/*     */       
/*  87 */       NBTTagCompound nbttagcompound = new NBTTagCompound();
/*  88 */       boolean flag = false;
/*     */       
/*  90 */       if (args.length >= 5) {
/*     */         
/*  92 */         IChatComponent ichatcomponent = getChatComponentFromNthArg(sender, args, 4);
/*     */ 
/*     */         
/*     */         try {
/*  96 */           nbttagcompound = JsonToNBT.getTagFromJson(ichatcomponent.getUnformattedText());
/*  97 */           flag = true;
/*     */         }
/*  99 */         catch (NBTException nbtexception) {
/*     */           
/* 101 */           throw new CommandException("commands.summon.tagError", new Object[] { nbtexception.getMessage() });
/*     */         } 
/*     */       } 
/*     */       
/* 105 */       nbttagcompound.setString("id", s);
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 110 */         entity2 = EntityList.createEntityFromNBT(nbttagcompound, world);
/*     */       }
/* 112 */       catch (RuntimeException var19) {
/*     */         
/* 114 */         throw new CommandException("commands.summon.failed", new Object[0]);
/*     */       } 
/*     */       
/* 117 */       if (entity2 == null)
/*     */       {
/* 119 */         throw new CommandException("commands.summon.failed", new Object[0]);
/*     */       }
/*     */ 
/*     */       
/* 123 */       entity2.setLocationAndAngles(d0, d1, d2, entity2.rotationYaw, entity2.rotationPitch);
/*     */       
/* 125 */       if (!flag && entity2 instanceof EntityLiving)
/*     */       {
/* 127 */         ((EntityLiving)entity2).onInitialSpawn(world.getDifficultyForLocation(new BlockPos(entity2)), (IEntityLivingData)null);
/*     */       }
/*     */       
/* 130 */       world.spawnEntityInWorld(entity2);
/* 131 */       Entity entity = entity2;
/*     */       
/* 133 */       for (NBTTagCompound nbttagcompound1 = nbttagcompound; entity != null && nbttagcompound1.hasKey("Riding", 10); nbttagcompound1 = nbttagcompound1.getCompoundTag("Riding")) {
/*     */         
/* 135 */         Entity entity1 = EntityList.createEntityFromNBT(nbttagcompound1.getCompoundTag("Riding"), world);
/*     */         
/* 137 */         if (entity1 != null) {
/*     */           
/* 139 */           entity1.setLocationAndAngles(d0, d1, d2, entity1.rotationYaw, entity1.rotationPitch);
/* 140 */           world.spawnEntityInWorld(entity1);
/* 141 */           entity.mountEntity(entity1);
/*     */         } 
/*     */         
/* 144 */         entity = entity1;
/*     */       } 
/*     */       
/* 147 */       notifyOperators(sender, (ICommand)this, "commands.summon.success", new Object[0]);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 155 */     return (args.length == 1) ? getListOfStringsMatchingLastWord(args, EntityList.getEntityNameList()) : ((args.length > 1 && args.length <= 4) ? func_175771_a(args, 1, pos) : null);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\command\server\CommandSummon.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */