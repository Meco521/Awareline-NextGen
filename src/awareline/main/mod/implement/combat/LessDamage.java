/*     */ package awareline.main.mod.implement.combat;
/*     */ 
/*     */ import awareline.main.event.EventHandler;
/*     */ import awareline.main.event.events.LBEvents.EventWorldChanged;
/*     */ import awareline.main.event.events.world.packetEvents.PacketEvent;
/*     */ import awareline.main.event.events.world.updateEvents.EventPreUpdate;
/*     */ import awareline.main.mod.Module;
/*     */ import awareline.main.mod.ModuleType;
/*     */ import awareline.main.mod.values.Option;
/*     */ import awareline.main.mod.values.Value;
/*     */ import awareline.main.utility.chat.Helper;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.C03PacketPlayer;
/*     */ import net.minecraft.network.play.client.C07PacketPlayerDigging;
/*     */ import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ 
/*     */ 
/*     */ public class LessDamage
/*     */   extends Module
/*     */ {
/*  24 */   private final Option<Boolean> regen = new Option("Regen", Boolean.valueOf(false)), onlyStopMove = new Option("OnlyNoMove", Boolean.valueOf(false)), onlyDamage = new Option("OnlyHurt", 
/*  25 */       Boolean.valueOf(false)), debug = new Option("Log", Boolean.valueOf(false));
/*     */   
/*     */   public LessDamage() {
/*  28 */     super("LessDamage", new String[] { "ld" }, ModuleType.Combat);
/*  29 */     addSettings(new Value[] { (Value)this.regen, (Value)this.onlyDamage, (Value)this.onlyStopMove, (Value)this.debug });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEnable() {
/*  39 */     Helper.sendMessage("LessDamage: After activating this module, the character will remain in an anti hacking state");
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisable() {
/*  44 */     sendPacketNoEvent((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onWorldChange(EventWorldChanged event) {
/*  49 */     message("world change, try sending c07");
/*  50 */     sendPacketNoEvent((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler(0)
/*     */   public void onPacket(PacketEvent event) {
/*  56 */     if (KillAura.getInstance.target != null && event.getPacket() instanceof net.minecraft.network.play.client.C09PacketHeldItemChange) {
/*  57 */       message("c09 change");
/*  58 */       event.setCancelled(true);
/*     */     } 
/*     */     
/*  61 */     if (event.getState() == PacketEvent.State.INCOMING && event.getPacket() instanceof C07PacketPlayerDigging && ((C07PacketPlayerDigging)event.getPacket()).getStatus() == C07PacketPlayerDigging.Action.RELEASE_USE_ITEM) {
/*  62 */       event.cancelEvent();
/*  63 */       message("server reback c07");
/*  64 */       sendPacketNoEvent((Packet)new C08PacketPlayerBlockPlacement(mc.thePlayer.inventory.getCurrentItem()));
/*     */     } 
/*     */     
/*  67 */     if (event.getPacket() instanceof net.minecraft.network.play.server.S08PacketPlayerPosLook) {
/*  68 */       message("lag back, try re blocking");
/*  69 */       sendPacketNoEvent((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
/*  70 */       sendPacketNoEvent((Packet)new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, mc.thePlayer.inventory.getCurrentItem(), 0.0F, 0.0F, 0.0F));
/*     */     } 
/*     */     
/*  73 */     if (event.getState() == PacketEvent.State.OUTGOING && event.getPacket() instanceof C07PacketPlayerDigging && ((C07PacketPlayerDigging)event.getPacket()).getStatus() == C07PacketPlayerDigging.Action.RELEASE_USE_ITEM) {
/*  74 */       event.cancelEvent();
/*  75 */       message("client sending c07");
/*  76 */       sendPacketNoEvent((Packet)new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, mc.thePlayer.inventory.getCurrentItem(), 0.0F, 0.0F, 0.0F));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler(0)
/*     */   public void onMotion(EventPreUpdate event) {
/*  83 */     setSuffix(((Boolean)this.regen.get()).booleanValue() ? "Regen" : "Packet");
/*  84 */     if (mc.thePlayer == null) {
/*     */       return;
/*     */     }
/*  87 */     if (((Boolean)this.regen.get()).booleanValue() && mc.thePlayer.hurtResistantTime > 0 && mc.thePlayer.getFoodStats().getFoodLevel() >= 19) {
/*  88 */       sendPacketNoEvent((Packet)new C03PacketPlayer(true));
/*  89 */       sendPacketNoEvent((Packet)new C03PacketPlayer(true));
/*     */     } 
/*     */     
/*  92 */     if (mc.currentScreen instanceof net.minecraft.client.gui.inventory.GuiContainer) {
/*     */       return;
/*     */     }
/*     */     
/*  96 */     if (mc.thePlayer.inventory.getStackInSlot(0) != null) {
/*     */       
/*  98 */       if (mc.thePlayer.inventory.getStackInSlot(0).getItem() == Items.compass && mc.thePlayer.inventory.getStackInSlot(0).getDisplayName().contains("Teleporter")) {
/*     */         return;
/*     */       }
/*     */       
/* 102 */       if (hasSword() && (mc.thePlayer.inventory.getStackInSlot(0).getDisplayName().contains("娓告垙鍒楄〃") || mc.thePlayer.inventory.getStackInSlot(0).getDisplayName().contains("寮�濮嬪尮閰�"))) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */     
/* 107 */     if (hasSword()) {
/*     */       
/* 109 */       if (((Boolean)this.onlyStopMove.get()).booleanValue() && isMoving()) {
/*     */         return;
/*     */       }
/*     */       
/* 113 */       if (((Boolean)this.onlyDamage.get()).booleanValue() && mc.thePlayer.hurtResistantTime == 0) {
/*     */         return;
/*     */       }
/*     */       
/* 117 */       if (mc.thePlayer.ticksExisted % 7 == 0) {
/* 118 */         message("Less damage....");
/*     */       }
/*     */       
/* 121 */       sendPacketNoEvent((Packet)new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, mc.thePlayer.inventory
/* 122 */             .getCurrentItem(), 0.0F, 0.0F, 0.0F));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void message(String msg1) {
/* 129 */     if (((Boolean)this.debug.get()).booleanValue() && mc.thePlayer.ticksExisted % 2 == 0)
/* 130 */       msg(msg1); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\combat\LessDamage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */