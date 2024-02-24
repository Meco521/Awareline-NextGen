/*     */ package net.minecraft.world.border;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.ChunkCoordIntPair;
/*     */ 
/*     */ 
/*     */ public class WorldBorder
/*     */ {
/*  13 */   private final List<IBorderListener> listeners = Lists.newArrayList();
/*  14 */   private double centerX = 0.0D;
/*  15 */   private double centerZ = 0.0D;
/*  16 */   private double startDiameter = 6.0E7D;
/*     */   
/*     */   private double endDiameter;
/*     */   private long endTime;
/*     */   private long startTime;
/*     */   private int worldSize;
/*     */   private double damageAmount;
/*     */   private double damageBuffer;
/*     */   private int warningTime;
/*     */   private int warningDistance;
/*     */   
/*     */   public WorldBorder() {
/*  28 */     this.endDiameter = this.startDiameter;
/*  29 */     this.worldSize = 29999984;
/*  30 */     this.damageAmount = 0.2D;
/*  31 */     this.damageBuffer = 5.0D;
/*  32 */     this.warningTime = 15;
/*  33 */     this.warningDistance = 5;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(BlockPos pos) {
/*  38 */     return ((pos.getX() + 1) > minX() && pos.getX() < maxX() && (pos.getZ() + 1) > minZ() && pos.getZ() < maxZ());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(ChunkCoordIntPair range) {
/*  43 */     return (range.getXEnd() > minX() && range.getXStart() < maxX() && range.getZEnd() > minZ() && range.getZStart() < maxZ());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(AxisAlignedBB bb) {
/*  48 */     return (bb.maxX > minX() && bb.minX < maxX() && bb.maxZ > minZ() && bb.minZ < maxZ());
/*     */   }
/*     */ 
/*     */   
/*     */   public double getClosestDistance(Entity entityIn) {
/*  53 */     return getClosestDistance(entityIn.posX, entityIn.posZ);
/*     */   }
/*     */ 
/*     */   
/*     */   public double getClosestDistance(double x, double z) {
/*  58 */     double d0 = z - minZ();
/*  59 */     double d1 = maxZ() - z;
/*  60 */     double d2 = x - minX();
/*  61 */     double d3 = maxX() - x;
/*  62 */     double d4 = Math.min(d2, d3);
/*  63 */     d4 = Math.min(d4, d0);
/*  64 */     return Math.min(d4, d1);
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumBorderStatus getStatus() {
/*  69 */     return (this.endDiameter < this.startDiameter) ? EnumBorderStatus.SHRINKING : ((this.endDiameter > this.startDiameter) ? EnumBorderStatus.GROWING : EnumBorderStatus.STATIONARY);
/*     */   }
/*     */ 
/*     */   
/*     */   public double minX() {
/*  74 */     double d0 = getCenterX() - getDiameter() / 2.0D;
/*     */     
/*  76 */     if (d0 < -this.worldSize)
/*     */     {
/*  78 */       d0 = -this.worldSize;
/*     */     }
/*     */     
/*  81 */     return d0;
/*     */   }
/*     */ 
/*     */   
/*     */   public double minZ() {
/*  86 */     double d0 = getCenterZ() - getDiameter() / 2.0D;
/*     */     
/*  88 */     if (d0 < -this.worldSize)
/*     */     {
/*  90 */       d0 = -this.worldSize;
/*     */     }
/*     */     
/*  93 */     return d0;
/*     */   }
/*     */ 
/*     */   
/*     */   public double maxX() {
/*  98 */     double d0 = getCenterX() + getDiameter() / 2.0D;
/*     */     
/* 100 */     if (d0 > this.worldSize)
/*     */     {
/* 102 */       d0 = this.worldSize;
/*     */     }
/*     */     
/* 105 */     return d0;
/*     */   }
/*     */ 
/*     */   
/*     */   public double maxZ() {
/* 110 */     double d0 = getCenterZ() + getDiameter() / 2.0D;
/*     */     
/* 112 */     if (d0 > this.worldSize)
/*     */     {
/* 114 */       d0 = this.worldSize;
/*     */     }
/*     */     
/* 117 */     return d0;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getCenterX() {
/* 122 */     return this.centerX;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getCenterZ() {
/* 127 */     return this.centerZ;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCenter(double x, double z) {
/* 132 */     this.centerX = x;
/* 133 */     this.centerZ = z;
/*     */     
/* 135 */     for (IBorderListener iborderlistener : getListeners())
/*     */     {
/* 137 */       iborderlistener.onCenterChanged(this, x, z);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public double getDiameter() {
/* 143 */     if (getStatus() != EnumBorderStatus.STATIONARY) {
/*     */       
/* 145 */       double d0 = ((float)(System.currentTimeMillis() - this.startTime) / (float)(this.endTime - this.startTime));
/*     */       
/* 147 */       if (d0 < 1.0D)
/*     */       {
/* 149 */         return this.startDiameter + (this.endDiameter - this.startDiameter) * d0;
/*     */       }
/*     */       
/* 152 */       setTransition(this.endDiameter);
/*     */     } 
/*     */     
/* 155 */     return this.startDiameter;
/*     */   }
/*     */ 
/*     */   
/*     */   public long getTimeUntilTarget() {
/* 160 */     return (getStatus() != EnumBorderStatus.STATIONARY) ? (this.endTime - System.currentTimeMillis()) : 0L;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getTargetSize() {
/* 165 */     return this.endDiameter;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTransition(double newSize) {
/* 170 */     this.startDiameter = newSize;
/* 171 */     this.endDiameter = newSize;
/* 172 */     this.endTime = System.currentTimeMillis();
/* 173 */     this.startTime = this.endTime;
/*     */     
/* 175 */     for (IBorderListener iborderlistener : getListeners())
/*     */     {
/* 177 */       iborderlistener.onSizeChanged(this, newSize);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTransition(double oldSize, double newSize, long time) {
/* 183 */     this.startDiameter = oldSize;
/* 184 */     this.endDiameter = newSize;
/* 185 */     this.startTime = System.currentTimeMillis();
/* 186 */     this.endTime = this.startTime + time;
/*     */     
/* 188 */     for (IBorderListener iborderlistener : getListeners())
/*     */     {
/* 190 */       iborderlistener.onTransitionStarted(this, oldSize, newSize, time);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected List<IBorderListener> getListeners() {
/* 196 */     return Lists.newArrayList(this.listeners);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addListener(IBorderListener listener) {
/* 201 */     this.listeners.add(listener);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSize(int size) {
/* 206 */     this.worldSize = size;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSize() {
/* 211 */     return this.worldSize;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getDamageBuffer() {
/* 216 */     return this.damageBuffer;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDamageBuffer(double bufferSize) {
/* 221 */     this.damageBuffer = bufferSize;
/*     */     
/* 223 */     for (IBorderListener iborderlistener : getListeners())
/*     */     {
/* 225 */       iborderlistener.onDamageBufferChanged(this, bufferSize);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public double getDamageAmount() {
/* 231 */     return this.damageAmount;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDamageAmount(double newAmount) {
/* 236 */     this.damageAmount = newAmount;
/*     */     
/* 238 */     for (IBorderListener iborderlistener : getListeners())
/*     */     {
/* 240 */       iborderlistener.onDamageAmountChanged(this, newAmount);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public double getResizeSpeed() {
/* 246 */     return (this.endTime == this.startTime) ? 0.0D : (Math.abs(this.startDiameter - this.endDiameter) / (this.endTime - this.startTime));
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWarningTime() {
/* 251 */     return this.warningTime;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setWarningTime(int warningTime) {
/* 256 */     this.warningTime = warningTime;
/*     */     
/* 258 */     for (IBorderListener iborderlistener : getListeners())
/*     */     {
/* 260 */       iborderlistener.onWarningTimeChanged(this, warningTime);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWarningDistance() {
/* 266 */     return this.warningDistance;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setWarningDistance(int warningDistance) {
/* 271 */     this.warningDistance = warningDistance;
/*     */     
/* 273 */     for (IBorderListener iborderlistener : getListeners())
/*     */     {
/* 275 */       iborderlistener.onWarningDistanceChanged(this, warningDistance);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\border\WorldBorder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */