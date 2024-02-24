/*     */ package net.minecraft.command.server;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.concurrent.Callable;
/*     */ import net.minecraft.command.CommandResultStats;
/*     */ import net.minecraft.command.ICommandManager;
/*     */ import net.minecraft.command.ICommandSender;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.ReportedException;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ public abstract class CommandBlockLogic
/*     */   implements ICommandSender
/*     */ {
/*  24 */   private static final SimpleDateFormat timestampFormat = new SimpleDateFormat("HH:mm:ss");
/*     */ 
/*     */   
/*     */   private int successCount;
/*     */   
/*     */   private boolean trackOutput = true;
/*     */   
/*  31 */   private IChatComponent lastOutput = null;
/*     */ 
/*     */   
/*  34 */   private String commandStored = "";
/*     */ 
/*     */   
/*  37 */   private String customName = "@";
/*  38 */   private final CommandResultStats resultStats = new CommandResultStats();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSuccessCount() {
/*  45 */     return this.successCount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IChatComponent getLastOutput() {
/*  53 */     return this.lastOutput;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeDataToNBT(NBTTagCompound tagCompound) {
/*  61 */     tagCompound.setString("Command", this.commandStored);
/*  62 */     tagCompound.setInteger("SuccessCount", this.successCount);
/*  63 */     tagCompound.setString("CustomName", this.customName);
/*  64 */     tagCompound.setBoolean("TrackOutput", this.trackOutput);
/*     */     
/*  66 */     if (this.lastOutput != null && this.trackOutput)
/*     */     {
/*  68 */       tagCompound.setString("LastOutput", IChatComponent.Serializer.componentToJson(this.lastOutput));
/*     */     }
/*     */     
/*  71 */     this.resultStats.writeStatsToNBT(tagCompound);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readDataFromNBT(NBTTagCompound nbt) {
/*  79 */     this.commandStored = nbt.getString("Command");
/*  80 */     this.successCount = nbt.getInteger("SuccessCount");
/*     */     
/*  82 */     if (nbt.hasKey("CustomName", 8))
/*     */     {
/*  84 */       this.customName = nbt.getString("CustomName");
/*     */     }
/*     */     
/*  87 */     if (nbt.hasKey("TrackOutput", 1))
/*     */     {
/*  89 */       this.trackOutput = nbt.getBoolean("TrackOutput");
/*     */     }
/*     */     
/*  92 */     if (nbt.hasKey("LastOutput", 8) && this.trackOutput)
/*     */     {
/*  94 */       this.lastOutput = IChatComponent.Serializer.jsonToComponent(nbt.getString("LastOutput"));
/*     */     }
/*     */     
/*  97 */     this.resultStats.readStatsFromNBT(nbt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canCommandSenderUseCommand(int permLevel, String commandName) {
/* 105 */     return (permLevel <= 2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCommand(String command) {
/* 113 */     this.commandStored = command;
/* 114 */     this.successCount = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommand() {
/* 122 */     return this.commandStored;
/*     */   }
/*     */ 
/*     */   
/*     */   public void trigger(World worldIn) {
/* 127 */     if (worldIn.isRemote)
/*     */     {
/* 129 */       this.successCount = 0;
/*     */     }
/*     */     
/* 132 */     MinecraftServer minecraftserver = MinecraftServer.getServer();
/*     */     
/* 134 */     if (minecraftserver != null && minecraftserver.isAnvilFileSet() && minecraftserver.isCommandBlockEnabled()) {
/*     */       
/* 136 */       ICommandManager icommandmanager = minecraftserver.getCommandManager();
/*     */ 
/*     */       
/*     */       try {
/* 140 */         this.lastOutput = null;
/* 141 */         this.successCount = icommandmanager.executeCommand(this, this.commandStored);
/*     */       }
/* 143 */       catch (Throwable throwable) {
/*     */         
/* 145 */         CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Executing command block");
/* 146 */         CrashReportCategory crashreportcategory = crashreport.makeCategory("Command to be executed");
/* 147 */         crashreportcategory.addCrashSectionCallable("Command", new Callable<String>()
/*     */             {
/*     */               public String call() {
/* 150 */                 return CommandBlockLogic.this.getCommand();
/*     */               }
/*     */             });
/* 153 */         crashreportcategory.addCrashSectionCallable("Name", new Callable<String>()
/*     */             {
/*     */               public String call() {
/* 156 */                 return CommandBlockLogic.this.getName();
/*     */               }
/*     */             });
/* 159 */         throw new ReportedException(crashreport);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 164 */       this.successCount = 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 173 */     return this.customName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IChatComponent getDisplayName() {
/* 181 */     return (IChatComponent)new ChatComponentText(this.customName);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setName(String p_145754_1_) {
/* 186 */     this.customName = p_145754_1_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addChatMessage(IChatComponent component) {
/* 194 */     if (this.trackOutput && getEntityWorld() != null && !(getEntityWorld()).isRemote) {
/*     */       
/* 196 */       this.lastOutput = (new ChatComponentText("[" + timestampFormat.format(new Date()) + "] ")).appendSibling(component);
/* 197 */       updateCommand();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean sendCommandFeedback() {
/* 206 */     MinecraftServer minecraftserver = MinecraftServer.getServer();
/* 207 */     return (minecraftserver == null || !minecraftserver.isAnvilFileSet() || minecraftserver.worldServers[0].getGameRules().getBoolean("commandBlockOutput"));
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCommandStat(CommandResultStats.Type type, int amount) {
/* 212 */     this.resultStats.setCommandStatScore(this, type, amount);
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract void updateCommand();
/*     */   
/*     */   public abstract int func_145751_f();
/*     */   
/*     */   public abstract void func_145757_a(ByteBuf paramByteBuf);
/*     */   
/*     */   public void setLastOutput(IChatComponent lastOutputMessage) {
/* 223 */     this.lastOutput = lastOutputMessage;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTrackOutput(boolean shouldTrackOutput) {
/* 228 */     this.trackOutput = shouldTrackOutput;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldTrackOutput() {
/* 233 */     return this.trackOutput;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean tryOpenEditCommandBlock(EntityPlayer playerIn) {
/* 238 */     if (!playerIn.capabilities.isCreativeMode)
/*     */     {
/* 240 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 244 */     if ((playerIn.getEntityWorld()).isRemote)
/*     */     {
/* 246 */       playerIn.openEditCommandBlock(this);
/*     */     }
/*     */     
/* 249 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public CommandResultStats getCommandResultStats() {
/* 255 */     return this.resultStats;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\command\server\CommandBlockLogic.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */