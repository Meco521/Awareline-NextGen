/*     */ package net.minecraft.entity;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.Callable;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.util.IntHashMap;
/*     */ import net.minecraft.util.ReportedException;
/*     */ import net.minecraft.world.WorldServer;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityTracker
/*     */ {
/*  29 */   private static final Logger logger = LogManager.getLogger();
/*     */   private final WorldServer theWorld;
/*  31 */   private final Set<EntityTrackerEntry> trackedEntities = Sets.newHashSet();
/*  32 */   private final IntHashMap<EntityTrackerEntry> trackedEntityHashTable = new IntHashMap();
/*     */   
/*     */   private final int maxTrackingDistanceThreshold;
/*     */   
/*     */   public EntityTracker(WorldServer theWorldIn) {
/*  37 */     this.theWorld = theWorldIn;
/*  38 */     this.maxTrackingDistanceThreshold = theWorldIn.getMinecraftServer().getConfigurationManager().getEntityViewDistance();
/*     */   }
/*     */ 
/*     */   
/*     */   public void trackEntity(Entity entityIn) {
/*  43 */     if (entityIn instanceof EntityPlayerMP) {
/*     */       
/*  45 */       trackEntity(entityIn, 512, 2);
/*  46 */       EntityPlayerMP entityplayermp = (EntityPlayerMP)entityIn;
/*     */       
/*  48 */       for (EntityTrackerEntry entitytrackerentry : this.trackedEntities)
/*     */       {
/*  50 */         if (entitytrackerentry.trackedEntity != entityplayermp)
/*     */         {
/*  52 */           entitytrackerentry.updatePlayerEntity(entityplayermp);
/*     */         }
/*     */       }
/*     */     
/*  56 */     } else if (entityIn instanceof net.minecraft.entity.projectile.EntityFishHook) {
/*     */       
/*  58 */       addEntityToTracker(entityIn, 64, 5, true);
/*     */     }
/*  60 */     else if (entityIn instanceof net.minecraft.entity.projectile.EntityArrow) {
/*     */       
/*  62 */       addEntityToTracker(entityIn, 64, 20, false);
/*     */     }
/*  64 */     else if (entityIn instanceof net.minecraft.entity.projectile.EntitySmallFireball) {
/*     */       
/*  66 */       addEntityToTracker(entityIn, 64, 10, false);
/*     */     }
/*  68 */     else if (entityIn instanceof net.minecraft.entity.projectile.EntityFireball) {
/*     */       
/*  70 */       addEntityToTracker(entityIn, 64, 10, false);
/*     */     }
/*  72 */     else if (entityIn instanceof net.minecraft.entity.projectile.EntitySnowball) {
/*     */       
/*  74 */       addEntityToTracker(entityIn, 64, 10, true);
/*     */     }
/*  76 */     else if (entityIn instanceof net.minecraft.entity.item.EntityEnderPearl) {
/*     */       
/*  78 */       addEntityToTracker(entityIn, 64, 10, true);
/*     */     }
/*  80 */     else if (entityIn instanceof net.minecraft.entity.item.EntityEnderEye) {
/*     */       
/*  82 */       addEntityToTracker(entityIn, 64, 4, true);
/*     */     }
/*  84 */     else if (entityIn instanceof net.minecraft.entity.projectile.EntityEgg) {
/*     */       
/*  86 */       addEntityToTracker(entityIn, 64, 10, true);
/*     */     }
/*  88 */     else if (entityIn instanceof net.minecraft.entity.projectile.EntityPotion) {
/*     */       
/*  90 */       addEntityToTracker(entityIn, 64, 10, true);
/*     */     }
/*  92 */     else if (entityIn instanceof net.minecraft.entity.item.EntityExpBottle) {
/*     */       
/*  94 */       addEntityToTracker(entityIn, 64, 10, true);
/*     */     }
/*  96 */     else if (entityIn instanceof net.minecraft.entity.item.EntityFireworkRocket) {
/*     */       
/*  98 */       addEntityToTracker(entityIn, 64, 10, true);
/*     */     }
/* 100 */     else if (entityIn instanceof net.minecraft.entity.item.EntityItem) {
/*     */       
/* 102 */       addEntityToTracker(entityIn, 64, 20, true);
/*     */     }
/* 104 */     else if (entityIn instanceof net.minecraft.entity.item.EntityMinecart) {
/*     */       
/* 106 */       addEntityToTracker(entityIn, 80, 3, true);
/*     */     }
/* 108 */     else if (entityIn instanceof net.minecraft.entity.item.EntityBoat) {
/*     */       
/* 110 */       addEntityToTracker(entityIn, 80, 3, true);
/*     */     }
/* 112 */     else if (entityIn instanceof net.minecraft.entity.passive.EntitySquid) {
/*     */       
/* 114 */       addEntityToTracker(entityIn, 64, 3, true);
/*     */     }
/* 116 */     else if (entityIn instanceof net.minecraft.entity.boss.EntityWither) {
/*     */       
/* 118 */       addEntityToTracker(entityIn, 80, 3, false);
/*     */     }
/* 120 */     else if (entityIn instanceof net.minecraft.entity.passive.EntityBat) {
/*     */       
/* 122 */       addEntityToTracker(entityIn, 80, 3, false);
/*     */     }
/* 124 */     else if (entityIn instanceof net.minecraft.entity.boss.EntityDragon) {
/*     */       
/* 126 */       addEntityToTracker(entityIn, 160, 3, true);
/*     */     }
/* 128 */     else if (entityIn instanceof net.minecraft.entity.passive.IAnimals) {
/*     */       
/* 130 */       addEntityToTracker(entityIn, 80, 3, true);
/*     */     }
/* 132 */     else if (entityIn instanceof net.minecraft.entity.item.EntityTNTPrimed) {
/*     */       
/* 134 */       addEntityToTracker(entityIn, 160, 10, true);
/*     */     }
/* 136 */     else if (entityIn instanceof net.minecraft.entity.item.EntityFallingBlock) {
/*     */       
/* 138 */       addEntityToTracker(entityIn, 160, 20, true);
/*     */     }
/* 140 */     else if (entityIn instanceof EntityHanging) {
/*     */       
/* 142 */       addEntityToTracker(entityIn, 160, 2147483647, false);
/*     */     }
/* 144 */     else if (entityIn instanceof net.minecraft.entity.item.EntityArmorStand) {
/*     */       
/* 146 */       addEntityToTracker(entityIn, 160, 3, true);
/*     */     }
/* 148 */     else if (entityIn instanceof net.minecraft.entity.item.EntityXPOrb) {
/*     */       
/* 150 */       addEntityToTracker(entityIn, 160, 20, true);
/*     */     }
/* 152 */     else if (entityIn instanceof net.minecraft.entity.item.EntityEnderCrystal) {
/*     */       
/* 154 */       addEntityToTracker(entityIn, 256, 2147483647, false);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void trackEntity(Entity entityIn, int trackingRange, int updateFrequency) {
/* 160 */     addEntityToTracker(entityIn, trackingRange, updateFrequency, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addEntityToTracker(Entity entityIn, int trackingRange, final int updateFrequency, boolean sendVelocityUpdates) {
/* 168 */     if (trackingRange > this.maxTrackingDistanceThreshold)
/*     */     {
/* 170 */       trackingRange = this.maxTrackingDistanceThreshold;
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 175 */       if (this.trackedEntityHashTable.containsItem(entityIn.getEntityId()))
/*     */       {
/* 177 */         throw new IllegalStateException("Entity is already tracked!");
/*     */       }
/*     */       
/* 180 */       EntityTrackerEntry entitytrackerentry = new EntityTrackerEntry(entityIn, trackingRange, updateFrequency, sendVelocityUpdates);
/* 181 */       this.trackedEntities.add(entitytrackerentry);
/* 182 */       this.trackedEntityHashTable.addKey(entityIn.getEntityId(), entitytrackerentry);
/* 183 */       entitytrackerentry.updatePlayerEntities(this.theWorld.playerEntities);
/*     */     }
/* 185 */     catch (Throwable throwable) {
/*     */       
/* 187 */       CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Adding entity to track");
/* 188 */       CrashReportCategory crashreportcategory = crashreport.makeCategory("Entity To Track");
/* 189 */       crashreportcategory.addCrashSection("Tracking range", trackingRange + " blocks");
/* 190 */       crashreportcategory.addCrashSectionCallable("Update interval", new Callable<String>()
/*     */           {
/*     */             public String call() {
/* 193 */               String s = "Once per " + updateFrequency + " ticks";
/*     */               
/* 195 */               if (updateFrequency == Integer.MAX_VALUE)
/*     */               {
/* 197 */                 s = "Maximum (" + s + ")";
/*     */               }
/*     */               
/* 200 */               return s;
/*     */             }
/*     */           });
/* 203 */       entityIn.addEntityCrashInfo(crashreportcategory);
/* 204 */       CrashReportCategory crashreportcategory1 = crashreport.makeCategory("Entity That Is Already Tracked");
/* 205 */       ((EntityTrackerEntry)this.trackedEntityHashTable.lookup(entityIn.getEntityId())).trackedEntity.addEntityCrashInfo(crashreportcategory1);
/*     */ 
/*     */       
/*     */       try {
/* 209 */         throw new ReportedException(crashreport);
/*     */       }
/* 211 */       catch (ReportedException reportedexception) {
/*     */         
/* 213 */         logger.error("\"Silently\" catching entity tracking error.", (Throwable)reportedexception);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void untrackEntity(Entity entityIn) {
/* 220 */     if (entityIn instanceof EntityPlayerMP) {
/*     */       
/* 222 */       EntityPlayerMP entityplayermp = (EntityPlayerMP)entityIn;
/*     */       
/* 224 */       for (EntityTrackerEntry entitytrackerentry : this.trackedEntities)
/*     */       {
/* 226 */         entitytrackerentry.removeFromTrackedPlayers(entityplayermp);
/*     */       }
/*     */     } 
/*     */     
/* 230 */     EntityTrackerEntry entitytrackerentry1 = (EntityTrackerEntry)this.trackedEntityHashTable.removeObject(entityIn.getEntityId());
/*     */     
/* 232 */     if (entitytrackerentry1 != null) {
/*     */       
/* 234 */       this.trackedEntities.remove(entitytrackerentry1);
/* 235 */       entitytrackerentry1.sendDestroyEntityPacketToTrackedPlayers();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateTrackedEntities() {
/* 241 */     List<EntityPlayerMP> list = Lists.newArrayList();
/*     */     
/* 243 */     for (EntityTrackerEntry entitytrackerentry : this.trackedEntities) {
/*     */       
/* 245 */       entitytrackerentry.updatePlayerList(this.theWorld.playerEntities);
/*     */       
/* 247 */       if (entitytrackerentry.playerEntitiesUpdated && entitytrackerentry.trackedEntity instanceof EntityPlayerMP)
/*     */       {
/* 249 */         list.add((EntityPlayerMP)entitytrackerentry.trackedEntity);
/*     */       }
/*     */     } 
/*     */     
/* 253 */     for (int i = 0; i < list.size(); i++) {
/*     */       
/* 255 */       EntityPlayerMP entityplayermp = list.get(i);
/*     */       
/* 257 */       for (EntityTrackerEntry entitytrackerentry1 : this.trackedEntities) {
/*     */         
/* 259 */         if (entitytrackerentry1.trackedEntity != entityplayermp)
/*     */         {
/* 261 */           entitytrackerentry1.updatePlayerEntity(entityplayermp);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_180245_a(EntityPlayerMP p_180245_1_) {
/* 269 */     for (EntityTrackerEntry entitytrackerentry : this.trackedEntities) {
/*     */       
/* 271 */       if (entitytrackerentry.trackedEntity == p_180245_1_) {
/*     */         
/* 273 */         entitytrackerentry.updatePlayerEntities(this.theWorld.playerEntities);
/*     */         
/*     */         continue;
/*     */       } 
/* 277 */       entitytrackerentry.updatePlayerEntity(p_180245_1_);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendToAllTrackingEntity(Entity entityIn, Packet p_151247_2_) {
/* 284 */     EntityTrackerEntry entitytrackerentry = (EntityTrackerEntry)this.trackedEntityHashTable.lookup(entityIn.getEntityId());
/*     */     
/* 286 */     if (entitytrackerentry != null)
/*     */     {
/* 288 */       entitytrackerentry.sendPacketToTrackedPlayers(p_151247_2_);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_151248_b(Entity entityIn, Packet p_151248_2_) {
/* 294 */     EntityTrackerEntry entitytrackerentry = (EntityTrackerEntry)this.trackedEntityHashTable.lookup(entityIn.getEntityId());
/*     */     
/* 296 */     if (entitytrackerentry != null)
/*     */     {
/* 298 */       entitytrackerentry.func_151261_b(p_151248_2_);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void removePlayerFromTrackers(EntityPlayerMP p_72787_1_) {
/* 304 */     for (EntityTrackerEntry entitytrackerentry : this.trackedEntities)
/*     */     {
/* 306 */       entitytrackerentry.removeTrackedPlayerSymmetric(p_72787_1_);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_85172_a(EntityPlayerMP p_85172_1_, Chunk p_85172_2_) {
/* 312 */     for (EntityTrackerEntry entitytrackerentry : this.trackedEntities) {
/*     */       
/* 314 */       if (entitytrackerentry.trackedEntity != p_85172_1_ && entitytrackerentry.trackedEntity.chunkCoordX == p_85172_2_.xPosition && entitytrackerentry.trackedEntity.chunkCoordZ == p_85172_2_.zPosition)
/*     */       {
/* 316 */         entitytrackerentry.updatePlayerEntity(p_85172_1_);
/*     */       }
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\entity\EntityTracker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */