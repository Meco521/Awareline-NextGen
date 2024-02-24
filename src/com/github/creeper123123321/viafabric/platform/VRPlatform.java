/*     */ package com.github.creeper123123321.viafabric.platform;
/*     */ 
/*     */ import com.github.creeper123123321.viafabric.ViaFabric;
/*     */ import com.github.creeper123123321.viafabric.util.FutureTaskId;
/*     */ import com.github.creeper123123321.viafabric.util.JLoggerToLog4j;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
/*     */ import java.io.File;
/*     */ import java.nio.file.Path;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.Future;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.logging.Logger;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import us.myles.ViaVersion.api.ViaAPI;
/*     */ import us.myles.ViaVersion.api.ViaVersionConfig;
/*     */ import us.myles.ViaVersion.api.command.ViaCommandSender;
/*     */ import us.myles.ViaVersion.api.configuration.ConfigurationProvider;
/*     */ import us.myles.ViaVersion.api.platform.TaskId;
/*     */ import us.myles.ViaVersion.api.platform.ViaConnectionManager;
/*     */ import us.myles.ViaVersion.api.platform.ViaPlatform;
/*     */ import us.myles.viaversion.libs.gson.JsonObject;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class VRPlatform
/*     */   implements ViaPlatform<UUID>
/*     */ {
/*  55 */   private final Logger logger = (Logger)new JLoggerToLog4j(LogManager.getLogger("ViaVersion"));
/*     */   private final VRViaConfig config;
/*     */   private final File dataFolder;
/*     */   private final ViaConnectionManager connectionManager;
/*     */   private final ViaAPI<UUID> api;
/*     */   
/*     */   public VRPlatform() {
/*  62 */     Path configDir = (Minecraft.getMinecraft()).mcDataDir.toPath().resolve("ViaFabric");
/*  63 */     this.config = new VRViaConfig(configDir.resolve("viaversion.yml").toFile());
/*  64 */     this.dataFolder = configDir.toFile();
/*  65 */     this.connectionManager = new VRConnectionManager();
/*  66 */     this.api = new VRViaAPI();
/*     */   }
/*     */ 
/*     */   
/*     */   public static MinecraftServer getServer() {
/*  71 */     if (!Minecraft.getMinecraft().isIntegratedServerRunning()) return null; 
/*  72 */     return MinecraftServer.getServer();
/*     */   }
/*     */ 
/*     */   
/*     */   public Logger getLogger() {
/*  77 */     return this.logger;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPlatformName() {
/*  82 */     return "ViaFabric";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPlatformVersion() {
/*  87 */     return ViaFabric.getVersion();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPluginVersion() {
/*  92 */     return "3.3.0";
/*     */   }
/*     */ 
/*     */   
/*     */   public TaskId runAsync(Runnable runnable) {
/*  97 */     return (TaskId)new FutureTaskId(
/*  98 */         CompletableFuture.runAsync(runnable, ViaFabric.ASYNC_EXECUTOR)
/*  99 */         .exceptionally(throwable -> {
/*     */             if (!(throwable instanceof java.util.concurrent.CancellationException)) {
/*     */               throwable.printStackTrace();
/*     */             }
/*     */             return null;
/*     */           }));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public TaskId runSync(Runnable runnable) {
/* 110 */     if (getServer() != null) {
/* 111 */       return runServerSync(runnable);
/*     */     }
/* 113 */     return runEventLoop(runnable);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private TaskId runServerSync(Runnable runnable) {
/* 119 */     return (TaskId)new FutureTaskId(CompletableFuture.runAsync(runnable, it -> getServer().callFromMainThread(())));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private TaskId runEventLoop(Runnable runnable) {
/* 127 */     return (TaskId)new FutureTaskId((Future)ViaFabric.EVENT_LOOP
/*     */         
/* 129 */         .submit(runnable)
/* 130 */         .addListener(errorLogger()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TaskId runSync(Runnable runnable, Long ticks) {
/* 137 */     return (TaskId)new FutureTaskId((Future)ViaFabric.EVENT_LOOP
/*     */         
/* 139 */         .schedule(() -> runSync(runnable), ticks.longValue() * 50L, TimeUnit.MILLISECONDS)
/* 140 */         .addListener(errorLogger()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TaskId runRepeatingSync(Runnable runnable, Long ticks) {
/* 147 */     return (TaskId)new FutureTaskId((Future)ViaFabric.EVENT_LOOP
/*     */         
/* 149 */         .scheduleAtFixedRate(() -> runSync(runnable), 0L, ticks.longValue() * 50L, TimeUnit.MILLISECONDS)
/* 150 */         .addListener(errorLogger()));
/*     */   }
/*     */ 
/*     */   
/*     */   private <T extends Future<?>> GenericFutureListener<T> errorLogger() {
/* 155 */     return future -> {
/*     */         if (!future.isCancelled() && future.cause() != null) {
/*     */           future.cause().printStackTrace();
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public void cancelTask(TaskId taskId) {
/* 164 */     if (taskId instanceof FutureTaskId) {
/* 165 */       ((FutureTaskId)taskId).getObject().cancel(false);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ViaCommandSender[] getOnlinePlayers() {
/* 172 */     return new ViaCommandSender[0];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendMessage(UUID uuid, String s) {}
/*     */ 
/*     */   
/*     */   public boolean kickPlayer(UUID uuid, String s) {
/* 181 */     return kickServer();
/*     */   }
/*     */   
/*     */   private boolean kickServer() {
/* 185 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPluginEnabled() {
/* 190 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public ViaAPI<UUID> getApi() {
/* 195 */     return this.api;
/*     */   }
/*     */ 
/*     */   
/*     */   public ViaVersionConfig getConf() {
/* 200 */     return (ViaVersionConfig)this.config;
/*     */   }
/*     */ 
/*     */   
/*     */   public ConfigurationProvider getConfigurationProvider() {
/* 205 */     return (ConfigurationProvider)this.config;
/*     */   }
/*     */ 
/*     */   
/*     */   public File getDataFolder() {
/* 210 */     return this.dataFolder;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onReload() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonObject getDump() {
/* 220 */     return new JsonObject();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOldClientsAllowed() {
/* 225 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public ViaConnectionManager getConnectionManager() {
/* 230 */     return this.connectionManager;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\github\creeper123123321\viafabric\platform\VRPlatform.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */