/*    */ package com.github.creeper123123321.viafabric;
/*    */ 
/*    */ import com.github.creeper123123321.viafabric.platform.VRInjector;
/*    */ import com.github.creeper123123321.viafabric.platform.VRLoader;
/*    */ import com.github.creeper123123321.viafabric.platform.VRPlatform;
/*    */ import com.google.common.util.concurrent.ThreadFactoryBuilder;
/*    */ import com.viamcp.platform.ViaBackwardsPlatformImplementation;
/*    */ import com.viamcp.platform.ViaRewindPlatformImplementation;
/*    */ import io.netty.channel.EventLoop;
/*    */ import io.netty.channel.local.LocalEventLoopGroup;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import java.util.concurrent.ExecutorService;
/*    */ import java.util.concurrent.Executors;
/*    */ import java.util.concurrent.ThreadFactory;
/*    */ import us.myles.ViaVersion.ViaManager;
/*    */ import us.myles.ViaVersion.api.Via;
/*    */ import us.myles.ViaVersion.api.data.MappingDataLoader;
/*    */ import us.myles.ViaVersion.api.platform.ViaInjector;
/*    */ import us.myles.ViaVersion.api.platform.ViaPlatform;
/*    */ import us.myles.ViaVersion.api.platform.ViaPlatformLoader;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ViaFabric
/*    */ {
/* 47 */   public static int clientSideVersion = 47;
/*    */   
/*    */   public static final ExecutorService ASYNC_EXECUTOR;
/*    */   public static final EventLoop EVENT_LOOP;
/* 51 */   public static final CompletableFuture<Void> INIT_FUTURE = new CompletableFuture<>();
/*    */ 
/*    */   
/*    */   static {
/* 55 */     ThreadFactory factory = (new ThreadFactoryBuilder()).setDaemon(true).setNameFormat("ViaFabric-%d").build();
/* 56 */     ASYNC_EXECUTOR = Executors.newFixedThreadPool(8, factory);
/* 57 */     EVENT_LOOP = (new LocalEventLoopGroup(1, factory)).next();
/* 58 */     EVENT_LOOP.submit(INIT_FUTURE::join);
/*    */   }
/*    */   
/*    */   public static String getVersion() {
/* 62 */     return "1.0";
/*    */   }
/*    */   
/*    */   public void onInitialize() {
/* 66 */     Via.init(ViaManager.builder().injector((ViaInjector)new VRInjector()).loader((ViaPlatformLoader)new VRLoader()).platform((ViaPlatform)new VRPlatform()).build());
/*    */     
/* 68 */     MappingDataLoader.enableMappingsCache();
/* 69 */     new ViaBackwardsPlatformImplementation();
/* 70 */     new ViaRewindPlatformImplementation();
/*    */     
/* 72 */     Via.getManager().init();
/*    */     
/* 74 */     INIT_FUTURE.complete(null);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\github\creeper123123321\viafabric\ViaFabric.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */