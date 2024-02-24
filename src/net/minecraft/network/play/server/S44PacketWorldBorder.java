/*     */ package net.minecraft.network.play.server;
/*     */ 
/*     */ import net.minecraft.network.INetHandler;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.INetHandlerPlayClient;
/*     */ import net.minecraft.world.border.WorldBorder;
/*     */ 
/*     */ 
/*     */ public class S44PacketWorldBorder
/*     */   implements Packet<INetHandlerPlayClient>
/*     */ {
/*     */   private Action action;
/*     */   private int size;
/*     */   private double centerX;
/*     */   private double centerZ;
/*     */   private double targetSize;
/*     */   private double diameter;
/*     */   private long timeUntilTarget;
/*     */   private int warningTime;
/*     */   private int warningDistance;
/*     */   
/*     */   public S44PacketWorldBorder() {}
/*     */   
/*     */   public S44PacketWorldBorder(WorldBorder border, Action actionIn) {
/*  26 */     this.action = actionIn;
/*  27 */     this.centerX = border.getCenterX();
/*  28 */     this.centerZ = border.getCenterZ();
/*  29 */     this.diameter = border.getDiameter();
/*  30 */     this.targetSize = border.getTargetSize();
/*  31 */     this.timeUntilTarget = border.getTimeUntilTarget();
/*  32 */     this.size = border.getSize();
/*  33 */     this.warningDistance = border.getWarningDistance();
/*  34 */     this.warningTime = border.getWarningTime();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readPacketData(PacketBuffer buf) {
/*  41 */     this.action = (Action)buf.readEnumValue(Action.class);
/*     */     
/*  43 */     switch (this.action) {
/*     */       
/*     */       case SET_SIZE:
/*  46 */         this.targetSize = buf.readDouble();
/*     */         break;
/*     */       
/*     */       case LERP_SIZE:
/*  50 */         this.diameter = buf.readDouble();
/*  51 */         this.targetSize = buf.readDouble();
/*  52 */         this.timeUntilTarget = buf.readVarLong();
/*     */         break;
/*     */       
/*     */       case SET_CENTER:
/*  56 */         this.centerX = buf.readDouble();
/*  57 */         this.centerZ = buf.readDouble();
/*     */         break;
/*     */       
/*     */       case SET_WARNING_BLOCKS:
/*  61 */         this.warningDistance = buf.readVarIntFromBuffer();
/*     */         break;
/*     */       
/*     */       case SET_WARNING_TIME:
/*  65 */         this.warningTime = buf.readVarIntFromBuffer();
/*     */         break;
/*     */       
/*     */       case INITIALIZE:
/*  69 */         this.centerX = buf.readDouble();
/*  70 */         this.centerZ = buf.readDouble();
/*  71 */         this.diameter = buf.readDouble();
/*  72 */         this.targetSize = buf.readDouble();
/*  73 */         this.timeUntilTarget = buf.readVarLong();
/*  74 */         this.size = buf.readVarIntFromBuffer();
/*  75 */         this.warningDistance = buf.readVarIntFromBuffer();
/*  76 */         this.warningTime = buf.readVarIntFromBuffer();
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writePacketData(PacketBuffer buf) {
/*  84 */     buf.writeEnumValue(this.action);
/*     */     
/*  86 */     switch (this.action) {
/*     */       
/*     */       case SET_SIZE:
/*  89 */         buf.writeDouble(this.targetSize);
/*     */         break;
/*     */       
/*     */       case LERP_SIZE:
/*  93 */         buf.writeDouble(this.diameter);
/*  94 */         buf.writeDouble(this.targetSize);
/*  95 */         buf.writeVarLong(this.timeUntilTarget);
/*     */         break;
/*     */       
/*     */       case SET_CENTER:
/*  99 */         buf.writeDouble(this.centerX);
/* 100 */         buf.writeDouble(this.centerZ);
/*     */         break;
/*     */       
/*     */       case SET_WARNING_BLOCKS:
/* 104 */         buf.writeVarIntToBuffer(this.warningDistance);
/*     */         break;
/*     */       
/*     */       case SET_WARNING_TIME:
/* 108 */         buf.writeVarIntToBuffer(this.warningTime);
/*     */         break;
/*     */       
/*     */       case INITIALIZE:
/* 112 */         buf.writeDouble(this.centerX);
/* 113 */         buf.writeDouble(this.centerZ);
/* 114 */         buf.writeDouble(this.diameter);
/* 115 */         buf.writeDouble(this.targetSize);
/* 116 */         buf.writeVarLong(this.timeUntilTarget);
/* 117 */         buf.writeVarIntToBuffer(this.size);
/* 118 */         buf.writeVarIntToBuffer(this.warningDistance);
/* 119 */         buf.writeVarIntToBuffer(this.warningTime);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processPacket(INetHandlerPlayClient handler) {
/* 128 */     handler.handleWorldBorder(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_179788_a(WorldBorder border) {
/* 133 */     switch (this.action) {
/*     */       
/*     */       case SET_SIZE:
/* 136 */         border.setTransition(this.targetSize);
/*     */         break;
/*     */       
/*     */       case LERP_SIZE:
/* 140 */         border.setTransition(this.diameter, this.targetSize, this.timeUntilTarget);
/*     */         break;
/*     */       
/*     */       case SET_CENTER:
/* 144 */         border.setCenter(this.centerX, this.centerZ);
/*     */         break;
/*     */       
/*     */       case SET_WARNING_BLOCKS:
/* 148 */         border.setWarningDistance(this.warningDistance);
/*     */         break;
/*     */       
/*     */       case SET_WARNING_TIME:
/* 152 */         border.setWarningTime(this.warningTime);
/*     */         break;
/*     */       
/*     */       case INITIALIZE:
/* 156 */         border.setCenter(this.centerX, this.centerZ);
/*     */         
/* 158 */         if (this.timeUntilTarget > 0L) {
/*     */           
/* 160 */           border.setTransition(this.diameter, this.targetSize, this.timeUntilTarget);
/*     */         }
/*     */         else {
/*     */           
/* 164 */           border.setTransition(this.targetSize);
/*     */         } 
/*     */         
/* 167 */         border.setSize(this.size);
/* 168 */         border.setWarningDistance(this.warningDistance);
/* 169 */         border.setWarningTime(this.warningTime);
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   public enum Action {
/* 175 */     SET_SIZE,
/* 176 */     LERP_SIZE,
/* 177 */     SET_CENTER,
/* 178 */     INITIALIZE,
/* 179 */     SET_WARNING_TIME,
/* 180 */     SET_WARNING_BLOCKS;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\play\server\S44PacketWorldBorder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */