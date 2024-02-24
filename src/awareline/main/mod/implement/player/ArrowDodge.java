/*     */ package awareline.main.mod.implement.player;
/*     */ import awareline.main.Client;
/*     */ import awareline.main.event.EventHandler;
/*     */ import awareline.main.event.events.world.updateEvents.EventPreUpdate;
/*     */ import awareline.main.mod.Module;
/*     */ import awareline.main.mod.ModuleType;
/*     */ import awareline.main.mod.implement.misc.Teams;
/*     */ import awareline.main.mod.values.Option;
/*     */ import awareline.main.mod.values.Value;
/*     */ import awareline.main.ui.gui.hud.notification.ClientNotification;
/*     */ import awareline.main.utility.timer.TimerUtils;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.projectile.EntityArrow;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.C03PacketPlayer;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.Vec3;
/*     */ 
/*     */ public class ArrowDodge extends Module {
/*     */   private EntityArrow arrow;
/*  24 */   private final TimerUtils timer = new TimerUtils();
/*  25 */   private final Option<Boolean> msg = new Option("Helper", Boolean.valueOf(false));
/*  26 */   private final Option<Boolean> friend = new Option("NoFriends", Boolean.valueOf(false));
/*     */   private final TimerUtils timerUtils;
/*     */   
/*  29 */   public ArrowDodge() { super("ArrowDodge", ModuleType.Player);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  39 */     this.timerUtils = new TimerUtils();
/*     */     addSettings(new Value[] { (Value)this.msg, (Value)this.friend }); }
/*     */    @EventHandler
/*     */   public void onUpdate(EventPreUpdate event) {
/*  43 */     if (!this.timerUtils.hasTimeElapsed(1000L, false)) {
/*     */       return;
/*     */     }
/*  46 */     for (Entity e : mc.theWorld.loadedEntityList) {
/*  47 */       if (!(e instanceof EntityArrow)) {
/*     */         continue;
/*     */       }
/*  50 */       this.arrow = (EntityArrow)e;
/*  51 */       if (this.arrow.shootingEntity != null && this.arrow.shootingEntity.isEntityEqual((Entity)mc.thePlayer)) {
/*     */         continue;
/*     */       }
/*  54 */       if (this.arrow.shootingEntity instanceof EntityPlayer) {
/*  55 */         EntityPlayer player = (EntityPlayer)this.arrow.shootingEntity;
/*  56 */         if (Teams.getInstance.isEnabled() && 
/*  57 */           Teams.getInstance.isOnSameTeam((Entity)player)) {
/*     */           continue;
/*     */         }
/*     */         
/*  61 */         if (((Boolean)this.friend.getValue()).booleanValue() && 
/*  62 */           Client.instance.friendManager.isFriend(player.getName())) {
/*     */           continue;
/*     */         }
/*     */       } 
/*     */       
/*  67 */       if (this.arrow.inGround) {
/*     */         continue;
/*     */       }
/*  70 */       if (mc.thePlayer.getDistanceSqToEntity((Entity)this.arrow) >= 20.0D) {
/*     */         continue;
/*     */       }
/*  73 */       doBarrier();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEnable() {
/*  79 */     this.arrow = null;
/*  80 */     super.onEnable(); } public void onDisable() {
/*     */     this.arrow = null;
/*     */     super.onDisable();
/*     */   } private void doBarrier() {
/*  84 */     this.timer.reset();
/*  85 */     if (((Boolean)this.msg.getValue()).booleanValue()) {
/*  86 */       ClientNotification.sendClientMessage("ArrowDodge", "Arrow incoming!!", 2500L, ClientNotification.Type.WARNING);
/*     */     }
/*  88 */     int slot = -1;
/*  89 */     for (int i = 0; i < 9; i++) {
/*  90 */       ItemStack stack = mc.thePlayer.inventory.getStackInSlot(i);
/*  91 */       if (stack != null && 
/*  92 */         stack.getItem() != null && 
/*  93 */         stack.getItem() instanceof net.minecraft.item.ItemBlock) {
/*  94 */         slot = i;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/*     */     
/* 100 */     if (slot == -1) {
/*     */       return;
/*     */     }
/* 103 */     double angleA = Math.toRadians(this.arrow.rotationYaw);
/* 104 */     Vec3 cVec = new Vec3(mc.thePlayer.posX + Math.cos(angleA) * 0.5D, mc.thePlayer.posY, mc.thePlayer.posZ - Math.sin(angleA) * 0.7D);
/* 105 */     Vec3 cVec2 = new Vec3(mc.thePlayer.posX + Math.cos(angleA) * 1.7D, mc.thePlayer.posY, mc.thePlayer.posZ - Math.sin(angleA) * 1.7D);
/* 106 */     Vec3 vec = new Vec3(mc.thePlayer.posX + Math.cos(angleA) * 1.5D, mc.thePlayer.posY, mc.thePlayer.posZ - Math.sin(angleA) * 1.5D);
/* 107 */     if (!isBlockPosAir(getBlockPos(cVec)) || !isBlockPosAir(getBlockPos(cVec2))) {
/* 108 */       cVec = new Vec3(mc.thePlayer.posX - Math.cos(angleA) * 0.5D, mc.thePlayer.posY, mc.thePlayer.posZ + Math.sin(angleA) * 0.5D);
/* 109 */       cVec2 = new Vec3(mc.thePlayer.posX - Math.cos(angleA) * 1.7D, mc.thePlayer.posY, mc.thePlayer.posZ + Math.sin(angleA) * 1.7D);
/* 110 */       vec = new Vec3(mc.thePlayer.posX - Math.cos(angleA) * 1.5D, mc.thePlayer.posY, mc.thePlayer.posZ + Math.sin(angleA) * 1.5D);
/* 111 */     } else if (isBlockPosAir(getBlockPos(vec)) && isBlockPosAir(getBlockPos(vec).down(1)) && isBlockPosAir(getBlockPos(vec).down(2))) {
/* 112 */       vec = new Vec3(mc.thePlayer.posX - Math.cos(angleA) * 1.5D, mc.thePlayer.posY, mc.thePlayer.posZ + Math.sin(angleA) * 1.5D);
/*     */     } 
/* 114 */     if (!isBlockPosAir(getBlockPos(cVec)) || !isBlockPosAir(getBlockPos(cVec2))) {
/*     */       return;
/*     */     }
/* 117 */     if (isBlockPosAir(getBlockPos(vec)) && isBlockPosAir(getBlockPos(vec).down(1)) && isBlockPosAir(getBlockPos(vec).down(2))) {
/*     */       return;
/*     */     }
/* 120 */     mc.thePlayer.motionX = 0.0D;
/* 121 */     mc.thePlayer.motionZ = 0.0D;
/* 122 */     mc.thePlayer.setPosition(vec.xCoord, vec.yCoord, vec.zCoord);
/* 123 */     mc.getNetHandler().addToSendQueueSilent((Packet)new C03PacketPlayer.C04PacketPlayerPosition(vec.xCoord, vec.yCoord, vec.zCoord, mc.thePlayer.onGround));
/*     */   }
/*     */   
/*     */   public static BlockPos getBlockPos(Vec3 vec) {
/* 127 */     return new BlockPos(vec.xCoord, vec.yCoord, vec.zCoord);
/*     */   }
/*     */   
/*     */   public static boolean isBlockPosAir(BlockPos blockPos) {
/* 131 */     return (mc.theWorld.getBlockState(blockPos).getBlock().getMaterial() == Material.air);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\player\ArrowDodge.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */