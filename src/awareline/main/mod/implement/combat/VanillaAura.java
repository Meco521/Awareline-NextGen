/*     */ package awareline.main.mod.implement.combat;
/*     */ 
/*     */ import awareline.main.Client;
/*     */ import awareline.main.event.EventHandler;
/*     */ import awareline.main.event.events.LBEvents.EventWorldChanged;
/*     */ import awareline.main.event.events.world.renderEvents.EventRender2D;
/*     */ import awareline.main.event.events.world.updateEvents.EventPreUpdate;
/*     */ import awareline.main.mod.Module;
/*     */ import awareline.main.mod.ModuleType;
/*     */ import awareline.main.mod.values.Option;
/*     */ import awareline.main.mod.values.Value;
/*     */ import awareline.main.utility.timer.TimerUtil;
/*     */ import java.awt.Color;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.CopyOnWriteArrayList;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.client.settings.KeyBinding;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.C02PacketUseEntity;
/*     */ import net.minecraft.network.play.client.C07PacketPlayerDigging;
/*     */ import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ 
/*     */ public class VanillaAura
/*     */   extends Module {
/*     */   public static VanillaAura getInstance;
/*  31 */   private final TimerUtil cpsTimerUtil = new TimerUtil();
/*  32 */   private final TimerUtil debugDelay = new TimerUtil();
/*  33 */   private final Option<Boolean> debug = new Option("Debug", Boolean.valueOf(false));
/*     */   public EntityLivingBase lastEntity;
/*     */   
/*     */   public VanillaAura() {
/*  37 */     super("VanillaAura", ModuleType.Combat);
/*  38 */     addSettings(new Value[] { (Value)this.debug });
/*  39 */     getInstance = this;
/*     */   }
/*     */   public EntityLivingBase target;
/*     */   private boolean shouldAdd(Entity entity) {
/*  43 */     if (entity == mc.thePlayer) {
/*  44 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  48 */     return entity instanceof net.minecraft.entity.player.EntityPlayer;
/*     */   }
/*     */   
/*     */   @EventHandler(0)
/*     */   public void onWorldChange(EventWorldChanged e) {
/*  53 */     checkModule(VanillaAura.class);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEnable() {
/*  58 */     checkModule(KillAura.class);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisable() {
/*  63 */     this.lastEntity = null;
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onRender(EventRender2D event) {
/*  69 */     if (this.lastEntity == null) {
/*     */       return;
/*     */     }
/*  72 */     boolean checkWinning = (this.lastEntity.getHealth() < mc.thePlayer.getHealth());
/*  73 */     String f = checkWinning ? "Win due to your health > target health" : "death, target health > your health";
/*  74 */     Client.instance.FontManager.SF18.drawString(f, event
/*  75 */         .getResolution().getScaledWidth() / 2 - Client.instance.FontManager.SF18
/*  76 */         .getStringWidth(f) / 2, event.getResolution().getScaledHeight() / 2 - 20, !checkWinning ? (new Color(255, 0, 0))
/*  77 */         .getRGB() : (new Color(0, 255, 0)).getRGB());
/*     */     
/*  79 */     if (this.debugDelay.hasReached(500.0D)) {
/*  80 */       message("Health : " + mc.thePlayer.getHealth() + "  target health : " + this.lastEntity.getHealth());
/*  81 */       this.debugDelay.reset();
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler(0)
/*     */   public void onUpdate(EventPreUpdate e) {
/*  87 */     setSuffix("20Range");
/*  88 */     List<EntityLivingBase> inRangeEntities = new CopyOnWriteArrayList<>();
/*  89 */     for (Entity entity : mc.theWorld.loadedEntityList) {
/*  90 */       if (mc.thePlayer.getDistanceToEntity(entity) <= 20.0F && shouldAdd(entity) && entity instanceof EntityLivingBase) {
/*  91 */         inRangeEntities.add((EntityLivingBase)entity);
/*     */       }
/*     */     } 
/*  94 */     inRangeEntities.sort((e1, e2) -> (int)(mc.thePlayer.getDistanceToEntity((Entity)e1) - mc.thePlayer.getDistanceToEntity((Entity)e2)));
/*  95 */     if (!inRangeEntities.isEmpty()) {
/*  96 */       EntityLivingBase currentEntity = inRangeEntities.get(0);
/*  97 */       this.lastEntity = currentEntity;
/*  98 */       if (shouldAttack()) {
/*  99 */         sendPacketNoEvent((Packet)new C02PacketUseEntity((Entity)currentEntity, C02PacketUseEntity.Action.ATTACK));
/* 100 */         sendPacketNoEvent((Packet)new C02PacketUseEntity((Entity)currentEntity, C02PacketUseEntity.Action.ATTACK));
/*     */       } 
/* 102 */       doBlock();
/*     */       
/* 104 */       mc.thePlayer.swingItem();
/* 105 */       sendPacketNoEvent((Packet)new C02PacketUseEntity((Entity)currentEntity, C02PacketUseEntity.Action.ATTACK));
/* 106 */       doBlock();
/*     */     } else {
/*     */       
/* 109 */       if (mc.thePlayer.isBlocking()) {
/* 110 */         sendPacketNoEvent((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
/* 111 */         KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), false);
/*     */       } 
/* 113 */       this.lastEntity = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void doBlock() {
/* 118 */     if (hasSword()) {
/* 119 */       EntityPlayerSP player = mc.thePlayer;
/* 120 */       ItemStack item = player.inventory.getCurrentItem();
/*     */       
/* 122 */       if (this.lastEntity.hurtResistantTime == 0) {
/* 123 */         message("sending c07");
/*     */       }
/*     */       
/* 126 */       player.setItemInUse(item, item.getMaxItemUseDuration());
/* 127 */       sendPacketNoEvent((Packet)new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, item, 0.0F, 0.0F, 0.0F));
/* 128 */       KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), true);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void message(String msg1) {
/* 133 */     if (((Boolean)this.debug.get()).booleanValue() && mc.thePlayer.ticksExisted % 2 == 0) {
/* 134 */       msg(msg1);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean shouldAttack() {
/* 139 */     if (this.cpsTimerUtil.hasReached(500.0D)) {
/* 140 */       this.cpsTimerUtil.reset();
/* 141 */       return true;
/*     */     } 
/* 143 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\combat\VanillaAura.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */