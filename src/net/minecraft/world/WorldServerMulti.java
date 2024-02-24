/*    */ package net.minecraft.world;
/*    */ 
/*    */ import net.minecraft.profiler.Profiler;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.village.VillageCollection;
/*    */ import net.minecraft.world.border.IBorderListener;
/*    */ import net.minecraft.world.border.WorldBorder;
/*    */ import net.minecraft.world.storage.DerivedWorldInfo;
/*    */ import net.minecraft.world.storage.ISaveHandler;
/*    */ import net.minecraft.world.storage.WorldInfo;
/*    */ 
/*    */ public class WorldServerMulti
/*    */   extends WorldServer {
/*    */   private final WorldServer delegate;
/*    */   
/*    */   public WorldServerMulti(MinecraftServer server, ISaveHandler saveHandlerIn, int dimensionId, WorldServer delegate, Profiler profilerIn) {
/* 17 */     super(server, saveHandlerIn, (WorldInfo)new DerivedWorldInfo(delegate.getWorldInfo()), dimensionId, profilerIn);
/* 18 */     this.delegate = delegate;
/* 19 */     delegate.getWorldBorder().addListener(new IBorderListener()
/*    */         {
/*    */           public void onSizeChanged(WorldBorder border, double newSize)
/*    */           {
/* 23 */             WorldServerMulti.this.getWorldBorder().setTransition(newSize);
/*    */           }
/*    */           
/*    */           public void onTransitionStarted(WorldBorder border, double oldSize, double newSize, long time) {
/* 27 */             WorldServerMulti.this.getWorldBorder().setTransition(oldSize, newSize, time);
/*    */           }
/*    */           
/*    */           public void onCenterChanged(WorldBorder border, double x, double z) {
/* 31 */             WorldServerMulti.this.getWorldBorder().setCenter(x, z);
/*    */           }
/*    */           
/*    */           public void onWarningTimeChanged(WorldBorder border, int newTime) {
/* 35 */             WorldServerMulti.this.getWorldBorder().setWarningTime(newTime);
/*    */           }
/*    */           
/*    */           public void onWarningDistanceChanged(WorldBorder border, int newDistance) {
/* 39 */             WorldServerMulti.this.getWorldBorder().setWarningDistance(newDistance);
/*    */           }
/*    */           
/*    */           public void onDamageAmountChanged(WorldBorder border, double newAmount) {
/* 43 */             WorldServerMulti.this.getWorldBorder().setDamageAmount(newAmount);
/*    */           }
/*    */           
/*    */           public void onDamageBufferChanged(WorldBorder border, double newSize) {
/* 47 */             WorldServerMulti.this.getWorldBorder().setDamageBuffer(newSize);
/*    */           }
/*    */         });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void saveLevel() {}
/*    */ 
/*    */ 
/*    */   
/*    */   public World init() {
/* 60 */     this.mapStorage = this.delegate.getMapStorage();
/* 61 */     this.worldScoreboard = this.delegate.getScoreboard();
/* 62 */     String s = VillageCollection.fileNameForProvider(this.provider);
/* 63 */     VillageCollection villagecollection = (VillageCollection)this.mapStorage.loadData(VillageCollection.class, s);
/*    */     
/* 65 */     if (villagecollection == null) {
/*    */       
/* 67 */       this.villageCollectionObj = new VillageCollection(this);
/* 68 */       this.mapStorage.setData(s, (WorldSavedData)this.villageCollectionObj);
/*    */     }
/*    */     else {
/*    */       
/* 72 */       this.villageCollectionObj = villagecollection;
/* 73 */       this.villageCollectionObj.setWorldsForAll(this);
/*    */     } 
/*    */     
/* 76 */     return this;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\WorldServerMulti.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */