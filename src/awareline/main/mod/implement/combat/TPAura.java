/*     */ package awareline.main.mod.implement.combat;
/*     */ import awareline.antileak.VerifyData;
/*     */ import awareline.main.Client;
/*     */ import awareline.main.event.EventHandler;
/*     */ import awareline.main.event.events.world.packetEvents.PacketEvent;
/*     */ import awareline.main.event.events.world.renderEvents.EventRender3D;
/*     */ import awareline.main.event.events.world.updateEvents.EventPreUpdate;
/*     */ import awareline.main.mod.Module;
/*     */ import awareline.main.mod.ModuleType;
/*     */ import awareline.main.mod.implement.move.antifallutily.TimeHelper;
/*     */ import awareline.main.mod.values.Mode;
/*     */ import awareline.main.mod.values.Numbers;
/*     */ import awareline.main.mod.values.Option;
/*     */ import awareline.main.mod.values.Value;
/*     */ import awareline.main.utility.math.RotationUtil;
/*     */ import awareline.main.utility.render.RenderUtil;
/*     */ import awareline.main.utility.timer.TimerUtil;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.CopyOnWriteArrayList;
/*     */ import net.minecraft.client.entity.EntityOtherPlayerMP;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.PlayerCapabilities;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.C03PacketPlayer;
/*     */ import net.minecraft.network.play.client.C07PacketPlayerDigging;
/*     */ import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
/*     */ import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
/*     */ import net.minecraft.network.play.client.C13PacketPlayerAbilities;
/*     */ import net.minecraft.network.play.server.S08PacketPlayerPosLook;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class TPAura extends Module {
/*  42 */   private final Numbers<Double> CPS = new Numbers("CPS", Double.valueOf(10.0D), Double.valueOf(1.0D), Double.valueOf(20.0D), Double.valueOf(0.5D));
/*  43 */   public final Numbers<Double> range = new Numbers("Range", Double.valueOf(20.0D), Double.valueOf(1.0D), Double.valueOf(300.0D), Double.valueOf(1.0D));
/*  44 */   public final Numbers<Integer> maxTargets = new Numbers("MaxTargets", Integer.valueOf(3), Integer.valueOf(1), Integer.valueOf(50), Integer.valueOf(1));
/*  45 */   private final Numbers<Double> packetDistance = new Numbers("PacketDistance", Double.valueOf(5.0D), Double.valueOf(0.5D), Double.valueOf(10.0D), Double.valueOf(0.5D));
/*  46 */   private final Option<Boolean> autoBlock = new Option("AutoBlock", Boolean.valueOf(true));
/*  47 */   private final Option<Boolean> showPath = new Option("ShowPath", Boolean.valueOf(true));
/*     */   
/*  49 */   private final Option<Boolean> noRegenValue = new Option("NoRegen", Boolean.valueOf(true));
/*  50 */   private final Option<Boolean> noLagBackValue = new Option("NoLagback", Boolean.valueOf(true));
/*     */   
/*  52 */   public final Option<Boolean> playerValue = new Option("Player", Boolean.valueOf(true));
/*  53 */   public final Option<Boolean> invisibleValue = new Option("Invisible", Boolean.valueOf(true));
/*  54 */   public final Option<Boolean> mobsValue = new Option("Mobs", Boolean.valueOf(true));
/*  55 */   public final Option<Boolean> animalsValue = new Option("Animals", Boolean.valueOf(false));
/*  56 */   public final Option<Boolean> deadValue = new Option("Dead", Boolean.valueOf(false));
/*     */   
/*  58 */   private final Mode<String> priority = new Mode("Priority", new String[] { "Distance", "Health" }, "Distance");
/*     */ 
/*     */   
/*  61 */   private ArrayList<Vec3> path = new ArrayList<>();
/*  62 */   private ArrayList[] test = new ArrayList[50];
/*  63 */   private final ArrayList<Packet> packetList = new ArrayList<>();
/*  64 */   public List<EntityLivingBase> targets = new CopyOnWriteArrayList<>();
/*  65 */   private final TimeHelper cps = new TimeHelper();
/*  66 */   public final TimerUtil timer = new TimerUtil();
/*     */   public boolean isBlocking;
/*     */   private final Comparator<Entity> distanceComparator;
/*     */   private final Comparator<EntityLivingBase> healthComparator;
/*     */   private Entity target;
/*     */   public static TPAura getInstance;
/*     */   private final Comparator<EntityLivingBase> angleComparator;
/*     */   
/*  74 */   public TPAura() { super("TPAura", ModuleType.Combat);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     this.distanceComparator = Comparator.comparingDouble(e3 -> e3.getDistanceToEntity((Entity)mc.thePlayer));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     this.healthComparator = Comparator.comparingDouble(EntityLivingBase::getHealth);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  91 */     this.angleComparator = Comparator.comparingDouble(e2 -> e2.getDistanceToEntity((Entity)mc.thePlayer));
/*     */     addSettings(new Value[] { 
/*     */           (Value)this.priority, (Value)this.CPS, (Value)this.range, (Value)this.packetDistance, (Value)this.maxTargets, (Value)this.playerValue, (Value)this.invisibleValue, (Value)this.mobsValue, (Value)this.animalsValue, (Value)this.deadValue, 
/*     */           (Value)this.autoBlock, (Value)this.showPath, (Value)this.noLagBackValue, (Value)this.noRegenValue });
/*  95 */     getInstance = this; } @EventHandler public void onUpdate(EventPreUpdate e) { VerifyData.instance.getClass();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 100 */     setSuffix((Serializable)this.range.get());
/* 101 */     int delayValue = 20 / ((Double)this.CPS.get()).intValue() * 50;
/* 102 */     this.targets = getTargets();
/* 103 */     this.targets.sort(this.angleComparator);
/* 104 */     if (this.targets.isEmpty()) {
/* 105 */       if (((Boolean)this.autoBlock.get()).booleanValue() && hasSword() && this.isBlocking) {
/* 106 */         this.isBlocking = false;
/*     */       }
/*     */       return;
/*     */     } 
/* 110 */     this.target = (Entity)this.targets.get(0);
/* 111 */     if (this.target == null && ((Boolean)this.autoBlock.get()).booleanValue() && hasSword() && this.isBlocking) {
/* 112 */       this.isBlocking = false;
/*     */     }
/* 114 */     if (this.cps.delay(delayValue, true) && 
/* 115 */       !this.targets.isEmpty()) {
/* 116 */       this.test = new ArrayList[50];
/* 117 */       for (int i = 0; i < 1; i++) {
/* 118 */         Vec3 topFrom = new Vec3(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
/* 119 */         Vec3 to = new Vec3(this.target.posX, this.target.posY, this.target.posZ);
/*     */         
/* 121 */         this.path = TPUtil.computePath(topFrom, to, ((Double)this.packetDistance.get()).doubleValue(), false);
/* 122 */         this.test[i] = this.path;
/* 123 */         mc.thePlayer.sendQueue.addToSendQueue((Packet)new C0FPacketConfirmTransaction(0, (short)-1, false));
/* 124 */         PlayerCapabilities playerCapabilities = new PlayerCapabilities();
/* 125 */         playerCapabilities.allowFlying = true;
/* 126 */         playerCapabilities.isFlying = true;
/* 127 */         mc.thePlayer.sendQueue.addToSendQueue((Packet)new C13PacketPlayerAbilities(playerCapabilities));
/* 128 */         this.packetList.clear();
/* 129 */         for (Vec3 pathElm : this.path) {
/* 130 */           EntityOtherPlayerMP entityOtherPlayerMP = new EntityOtherPlayerMP((World)mc.theWorld, new GameProfile(new UUID(69L, 96L), mc.thePlayer.getName()));
/* 131 */           entityOtherPlayerMP.inventory = mc.thePlayer.inventory;
/* 132 */           entityOtherPlayerMP.inventoryContainer = mc.thePlayer.inventoryContainer;
/* 133 */           entityOtherPlayerMP.setPositionAndRotation(pathElm.xCoord, pathElm.yCoord, pathElm.zCoord, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch);
/* 134 */           if (entityOtherPlayerMP.onGround || entityOtherPlayerMP.isCollidedVertically) {
/* 135 */             playerCapabilities.isFlying = false;
/* 136 */             playerCapabilities.allowFlying = false;
/* 137 */             mc.thePlayer.sendQueue.addToSendQueue((Packet)new C13PacketPlayerAbilities(playerCapabilities));
/* 138 */             mc.thePlayer.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(entityOtherPlayerMP.posX, entityOtherPlayerMP.posY, entityOtherPlayerMP.posZ, true));
/* 139 */             playerCapabilities.isFlying = true;
/* 140 */             playerCapabilities.allowFlying = true;
/* 141 */             mc.thePlayer.sendQueue.addToSendQueue((Packet)new C13PacketPlayerAbilities(playerCapabilities));
/* 142 */             float f3 = (float)(pathElm.xCoord - this.target.posX);
/* 143 */             float f4 = (float)(pathElm.yCoord - this.target.posY);
/* 144 */             float f5 = (float)(pathElm.zCoord - this.target.posZ);
/* 145 */             if (MathHelper.sqrt_float(f3 * f3 + f4 * f4 + f5 * f5) < 2.5F) {
/* 146 */               for (int k = 0; k <= 2; k++)
/* 147 */                 mc.thePlayer.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C05PacketPlayerLook(RotationUtil.teleportRot((Entity)entityOtherPlayerMP, this.target, 600.0F, 600.0F)[0], RotationUtil.teleportRot((Entity)entityOtherPlayerMP, this.target, 1000.0F, 1000.0F)[1], false)); 
/*     */               break;
/*     */             } 
/*     */             continue;
/*     */           } 
/* 152 */           mc.thePlayer.sendQueue.sendQueueWithoutEvent((Packet)new C03PacketPlayer.C04PacketPlayerPosition(pathElm.xCoord, pathElm.yCoord, pathElm.zCoord, false));
/* 153 */           this.packetList.add(new C03PacketPlayer.C04PacketPlayerPosition(pathElm.xCoord, pathElm.yCoord, pathElm.zCoord, false));
/* 154 */           entityOtherPlayerMP.rotationYawHead = mc.thePlayer.rotationYawHead;
/* 155 */           float f = (float)(pathElm.xCoord - this.target.posX);
/* 156 */           float f1 = (float)(pathElm.yCoord - this.target.posY);
/* 157 */           float f2 = (float)(pathElm.zCoord - this.target.posZ);
/* 158 */           if (MathHelper.sqrt_float(f * f + f1 * f1 + f2 * f2) < 2.5F) {
/* 159 */             for (int k = 0; k <= 2; k++) {
/* 160 */               mc.thePlayer.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C05PacketPlayerLook(RotationUtil.teleportRot((Entity)entityOtherPlayerMP, this.target, 600.0F, 600.0F)[0], RotationUtil.teleportRot((Entity)entityOtherPlayerMP, this.target, 1000.0F, 1000.0F)[1], false));
/*     */             }
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/* 166 */         ArrayList<Packet> packetArrayList = new ArrayList<>();
/* 167 */         if (this.packetList.isEmpty()) {
/*     */           return;
/*     */         }
/* 170 */         for (int j = this.packetList.size() - 1; j > 0; j--) {
/* 171 */           packetArrayList.add(this.packetList.get(j));
/*     */         }
/* 173 */         if (((Boolean)this.autoBlock.get()).booleanValue() && hasSword() && this.isBlocking) {
/* 174 */           sendPacket((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
/* 175 */           this.isBlocking = false;
/*     */         } 
/* 177 */         mc.thePlayer.swingItem();
/* 178 */         mc.playerController.attackEntity((EntityPlayer)mc.thePlayer, this.target);
/*     */         
/* 180 */         playerCapabilities.allowFlying = true;
/* 181 */         playerCapabilities.isFlying = true;
/* 182 */         mc.thePlayer.sendQueue.addToSendQueue((Packet)new C13PacketPlayerAbilities(playerCapabilities));
/* 183 */         for (Packet packet : packetArrayList) {
/* 184 */           mc.thePlayer.sendQueue.sendQueueWithoutEvent(packet);
/*     */         }
/*     */       } 
/* 187 */       this.cps.reset();
/*     */     } 
/*     */     
/* 190 */     if (((Boolean)this.autoBlock.get()).booleanValue() && !this.isBlocking && hasSword() && this.target != null) {
/* 191 */       sendPacket((Packet)new C08PacketPlayerBlockPlacement(mc.thePlayer.getHeldItem()));
/* 192 */       this.isBlocking = true;
/*     */     }  }
/*     */   public void onEnable() { if (this.isBlocking)
/*     */       this.isBlocking = false; 
/*     */     this.timer.reset();
/*     */     this.targets.clear();
/* 198 */     this.target = null; } @EventHandler public void onPacket(PacketEvent event) { if (event.getPacket() instanceof S08PacketPlayerPosLook) {
/* 199 */       this.timer.reset();
/*     */     }
/* 201 */     boolean isMovePacket = (event.getPacket() instanceof C03PacketPlayer.C04PacketPlayerPosition || event.getPacket() instanceof C03PacketPlayer.C06PacketPlayerPosLook);
/* 202 */     if (((Boolean)this.noRegenValue.get()).booleanValue() && event.getPacket() instanceof C03PacketPlayer && !isMovePacket) {
/* 203 */       event.cancelEvent();
/*     */     }
/* 205 */     if (((Boolean)this.noLagBackValue.get()).booleanValue() && event.getPacket() instanceof S08PacketPlayerPosLook) {
/* 206 */       PlayerCapabilities capabilities = new PlayerCapabilities();
/* 207 */       capabilities.allowFlying = true;
/* 208 */       mc.getNetHandler().addToSendQueue((Packet)new C13PacketPlayerAbilities(capabilities));
/*     */       
/* 210 */       event.cancelEvent();
/* 211 */       sendPacketNoEvent((Packet)new C03PacketPlayer.C06PacketPlayerPosLook(((S08PacketPlayerPosLook)event.getPacket()).getX(), ((S08PacketPlayerPosLook)event.getPacket()).getY(), ((S08PacketPlayerPosLook)event.getPacket()).getZ(), ((S08PacketPlayerPosLook)event
/* 212 */             .getPacket()).getYaw(), ((S08PacketPlayerPosLook)event.getPacket()).getPitch(), true));
/*     */     }  }
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onRender(EventRender3D e) {
/* 219 */     if (!((Boolean)this.showPath.get()).booleanValue()) {
/*     */       return;
/*     */     }
/* 222 */     if (!this.targets.isEmpty()) {
/* 223 */       RenderUtil.drawESP((Entity)this.targets.get(0), Client.instance.getClientColor(((EntityLivingBase)this.targets.get(0)).hurtResistantTime));
/*     */     }
/* 225 */     if (!this.path.isEmpty()) {
/* 226 */       for (int i = 0; i < this.targets.size(); i++) {
/* 227 */         if (this.test != null) {
/* 228 */           for (Object pos : this.test[i]) {
/* 229 */             Vec3 vec3 = (Vec3)pos;
/* 230 */             if (pos != null) {
/* 231 */               float f = (float)(vec3.xCoord - this.target.posX);
/* 232 */               float f1 = (float)(vec3.yCoord - this.target.posY);
/* 233 */               float f2 = (float)(vec3.zCoord - this.target.posZ);
/* 234 */               if (MathHelper.sqrt_float(f * f + f1 * f1 + f2 * f2) < 2.5F)
/* 235 */                 continue;  RenderUtil.drawPath(vec3);
/*     */             } 
/*     */           } 
/*     */         }
/*     */       } 
/* 240 */       if (this.cps.delay(1000.0F, true)) {
/* 241 */         this.test = new ArrayList[50];
/* 242 */         this.path.clear();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisable() {
/* 249 */     if (((Boolean)this.autoBlock.getValue()).booleanValue() && hasSword() && (mc.thePlayer.isBlocking() || this.isBlocking)) {
/* 250 */       mc.getNetHandler().addToSendQueue((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
/* 251 */       this.isBlocking = false;
/*     */     } 
/* 253 */     super.onDisable();
/*     */   }
/*     */   
/*     */   private List<EntityLivingBase> getTargets() {
/* 257 */     List<EntityLivingBase> targets = new ArrayList<>();
/* 258 */     for (Object o : mc.theWorld.getLoadedEntityList()) {
/* 259 */       if (o instanceof EntityLivingBase) {
/* 260 */         EntityLivingBase entity = (EntityLivingBase)o;
/* 261 */         if (!EntityUtils.isSelectedForTPAura((Entity)entity, true))
/*     */           continue; 
/* 263 */         targets.add(entity);
/*     */       } 
/*     */     } 
/*     */     
/* 267 */     if (!targets.isEmpty()) {
/* 268 */       if (this.priority.isCurrentMode("Distance")) {
/* 269 */         this.targets.sort(this.distanceComparator);
/*     */       } else {
/* 271 */         this.targets.sort(this.healthComparator);
/*     */       } 
/*     */     }
/* 274 */     return targets;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\combat\TPAura.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */