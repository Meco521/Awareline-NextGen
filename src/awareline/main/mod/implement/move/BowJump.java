/*     */ package awareline.main.mod.implement.move;
/*     */ 
/*     */ import awareline.main.Client;
/*     */ import awareline.main.event.EventHandler;
/*     */ import awareline.main.event.events.world.moveEvents.EventMove;
/*     */ import awareline.main.event.events.world.packetEvents.PacketEvent;
/*     */ import awareline.main.event.events.world.renderEvents.EventRender2D;
/*     */ import awareline.main.event.events.world.updateEvents.EventPreUpdate;
/*     */ import awareline.main.event.events.world.worldChangeEvents.RespawnEvent;
/*     */ import awareline.main.mod.Module;
/*     */ import awareline.main.mod.ModuleType;
/*     */ import awareline.main.mod.implement.combat.KillAura;
/*     */ import awareline.main.mod.values.Mode;
/*     */ import awareline.main.mod.values.Numbers;
/*     */ import awareline.main.mod.values.Option;
/*     */ import awareline.main.mod.values.Value;
/*     */ import awareline.main.ui.gui.clickgui.RenderUtil;
/*     */ import awareline.main.utility.MoveUtils;
/*     */ import java.awt.Color;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.C03PacketPlayer;
/*     */ import net.minecraft.network.play.client.C07PacketPlayerDigging;
/*     */ import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
/*     */ import net.minecraft.network.play.client.C09PacketHeldItemChange;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ 
/*     */ public class BowJump extends Module {
/*  32 */   private final Option<Boolean> hypixelBypassValue = new Option("WatchdogBypass", Boolean.valueOf(true));
/*  33 */   private final Mode<String> modeValue = new Mode("BoostMode", new String[] { "Strafe", "SpeedInAir" }, "SpeedInAir", () -> Boolean.valueOf(!((Boolean)this.hypixelBypassValue.get()).booleanValue()));
/*     */   
/*  35 */   private final Numbers<Double> speedInAirBoostValue = new Numbers("SpeedInAir", 
/*  36 */       Double.valueOf(0.03D), Double.valueOf(0.02D), Double.valueOf(0.1D), Double.valueOf(0.01D), () -> Boolean.valueOf(this.modeValue.is("SpeedInAir")));
/*  37 */   private final Numbers<Double> boostValue = new Numbers("Boost", 
/*  38 */       Double.valueOf(0.0D), Double.valueOf(0.0D), Double.valueOf(10.0D), Double.valueOf(0.25D), () -> Boolean.valueOf(this.modeValue.is("Strafe")));
/*  39 */   private final Numbers<Double> heightValue = new Numbers("Height", 
/*  40 */       Double.valueOf(0.42D), Double.valueOf(0.0D), Double.valueOf(10.0D), Double.valueOf(0.01D), () -> Boolean.valueOf(!((Boolean)this.hypixelBypassValue.get()).booleanValue()));
/*  41 */   private final Numbers<Double> timerValue = new Numbers("Timer", Double.valueOf(1.0D), Double.valueOf(0.1D), Double.valueOf(10.0D), Double.valueOf(0.1D));
/*     */   
/*  43 */   private final Numbers<Double> delayBeforeLaunch = new Numbers("DelayBeforeArrowLaunch", Double.valueOf(1.0D), Double.valueOf(1.0D), Double.valueOf(20.0D), Double.valueOf(1.0D));
/*  44 */   private final Option<Boolean> autoDisable = new Option("AutoDisable", Boolean.valueOf(true));
/*  45 */   private final Option<Boolean> renderValue = new Option("RenderStatus", Boolean.valueOf(true));
/*  46 */   private final Option<Boolean> spoofY = new Option("SpoofY", Boolean.valueOf(false));
/*  47 */   private final Option<Boolean> viewBobbing = new Option("ViewBobbing", Boolean.valueOf(false));
/*  48 */   private final Option<Boolean> combatSpoof = new Option("CombatSpoof", Boolean.valueOf(false));
/*     */   private int bowState;
/*     */   private long lastPlayerTick;
/*  51 */   private int lastSlot = -1;
/*     */   private double y;
/*     */   
/*     */   public BowJump() {
/*  55 */     super("BowJump", ModuleType.Movement);
/*  56 */     addSettings(new Value[] { (Value)this.modeValue, (Value)this.speedInAirBoostValue, (Value)this.boostValue, (Value)this.heightValue, (Value)this.timerValue, (Value)this.delayBeforeLaunch, (Value)this.hypixelBypassValue, (Value)this.spoofY, (Value)this.combatSpoof, (Value)this.viewBobbing, (Value)this.autoDisable, (Value)this.renderValue });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEnable() {
/*  64 */     if (mc.thePlayer == null) {
/*     */       return;
/*     */     }
/*  67 */     this.y = mc.thePlayer.posY;
/*  68 */     this.bowState = 0;
/*  69 */     this.lastPlayerTick = -1L;
/*  70 */     this.lastSlot = mc.thePlayer.inventory.currentItem;
/*  71 */     MoveUtils.INSTANCE.strafe(0.0F);
/*  72 */     mc.thePlayer.onGround = false;
/*  73 */     mc.thePlayer.jumpMovementFactor = 0.0F;
/*     */   }
/*     */   
/*     */   private void setViewBobbing() {
/*  77 */     mc.thePlayer.cameraYaw = ((Boolean)this.viewBobbing.get()).booleanValue() ? 0.095F : 0.0F;
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onMove(EventMove event) {
/*  82 */     setViewBobbing();
/*  83 */     if (mc.thePlayer.onGround && this.bowState < 3) {
/*  84 */       event.cancelEvent();
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onPacket(PacketEvent event) {
/*  90 */     if (event.getPacket() instanceof C09PacketHeldItemChange) {
/*  91 */       C09PacketHeldItemChange c09 = (C09PacketHeldItemChange)event.getPacket();
/*  92 */       this.lastSlot = c09.getSlotId();
/*  93 */       event.cancelEvent();
/*     */     } 
/*     */     
/*  96 */     if (event.getPacket() instanceof C03PacketPlayer) {
/*  97 */       C03PacketPlayer c03 = (C03PacketPlayer)event.getPacket();
/*  98 */       if (this.bowState < 3)
/*  99 */         c03.setMoving(false); 
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onUpdate(EventPreUpdate event) {
/*     */     int slot, reSlot;
/* 106 */     mc.timer.timerSpeed = 1.0F;
/* 107 */     if (((Boolean)this.spoofY.get()).booleanValue()) {
/* 108 */       mc.thePlayer.posY = this.y;
/*     */     }
/* 110 */     if (((Boolean)this.combatSpoof.get()).booleanValue()) {
/* 111 */       KillAura.getInstance.target = null;
/*     */     }
/*     */     
/* 114 */     boolean forceDisable = false;
/* 115 */     switch (this.bowState) {
/*     */       case 0:
/* 117 */         slot = getBowSlot();
/* 118 */         if (slot < 0 || !mc.thePlayer.inventory.hasItem(Items.arrow)) {
/* 119 */           forceDisable = true;
/* 120 */           this.bowState = 5; break;
/*     */         } 
/* 122 */         if (this.lastPlayerTick == -1L) {
/* 123 */           ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(slot + 36).getStack();
/*     */           
/* 125 */           if (this.lastSlot != slot) sendPacketNoEvent((Packet)new C09PacketHeldItemChange(slot)); 
/* 126 */           sendPacketNoEvent((Packet)new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, mc.thePlayer.inventoryContainer.getSlot(slot + 36).getStack(), 0.0F, 0.0F, 0.0F));
/*     */           
/* 128 */           this.lastPlayerTick = mc.thePlayer.ticksExisted;
/* 129 */           this.bowState = 1;
/*     */         } 
/*     */         break;
/*     */       case 1:
/* 133 */         reSlot = getBowSlot();
/* 134 */         if ((mc.thePlayer.ticksExisted - this.lastPlayerTick) > ((Double)this.delayBeforeLaunch.get()).doubleValue()) {
/* 135 */           sendPacketNoEvent((Packet)new C03PacketPlayer.C05PacketPlayerLook(mc.thePlayer.rotationYaw, -90.0F, mc.thePlayer.onGround));
/* 136 */           sendPacketNoEvent((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
/*     */           
/* 138 */           if (this.lastSlot != reSlot) sendPacketNoEvent((Packet)new C09PacketHeldItemChange(this.lastSlot)); 
/* 139 */           this.bowState = 2;
/*     */         } 
/*     */         break;
/*     */       case 2:
/* 143 */         if (mc.thePlayer.hurtTime > 0)
/* 144 */           this.bowState = 3; 
/*     */         break;
/*     */       case 3:
/* 147 */         if (((Boolean)this.hypixelBypassValue.get()).booleanValue()) {
/* 148 */           if (mc.thePlayer.hurtTime >= 8) {
/* 149 */             mc.thePlayer.motionY = 0.58D;
/* 150 */             mc.thePlayer.jump();
/*     */           } 
/* 152 */           if (mc.thePlayer.hurtTime == 8) {
/* 153 */             MoveUtils.INSTANCE.strafe(0.72F);
/*     */           }
/*     */           
/* 156 */           if (mc.thePlayer.hurtTime == 7) {
/* 157 */             mc.thePlayer.motionY += 0.03D;
/*     */           }
/*     */           
/* 160 */           if (mc.thePlayer.hurtTime <= 6) {
/* 161 */             mc.thePlayer.motionY += 0.015D;
/*     */           }
/* 163 */           mc.timer.timerSpeed = ((Double)this.timerValue.get()).floatValue();
/* 164 */           if (mc.thePlayer.onGround && mc.thePlayer.ticksExisted - this.lastPlayerTick >= 1L)
/* 165 */             this.bowState = 5; 
/*     */         } else {
/* 167 */           switch ((String)this.modeValue.get()) {
/*     */             case "Strafe":
/* 169 */               MoveUtils.INSTANCE.strafe(((Double)this.boostValue.get()).floatValue());
/*     */               break;
/*     */             
/*     */             case "SpeedInAir":
/* 173 */               mc.thePlayer.speedInAir = ((Double)this.speedInAirBoostValue.getValue()).floatValue();
/* 174 */               mc.thePlayer.jump();
/*     */               break;
/*     */           } 
/* 177 */           mc.thePlayer.motionY = ((Double)this.heightValue.get()).doubleValue();
/* 178 */           this.bowState = 4;
/* 179 */           this.lastPlayerTick = mc.thePlayer.ticksExisted;
/*     */           break;
/*     */         } 
/*     */       case 4:
/* 183 */         mc.timer.timerSpeed = ((Double)this.timerValue.get()).floatValue();
/* 184 */         if (mc.thePlayer.onGround && mc.thePlayer.ticksExisted - this.lastPlayerTick >= 1L) {
/* 185 */           this.bowState = 5;
/*     */         }
/*     */         break;
/*     */     } 
/* 189 */     if (this.bowState < 3) {
/* 190 */       mc.thePlayer.movementInput.moveForward = 0.0F;
/* 191 */       mc.thePlayer.movementInput.moveStrafe = 0.0F;
/*     */     } 
/*     */     
/* 194 */     if (this.bowState == 5 && (((Boolean)this.autoDisable.get()).booleanValue() || forceDisable))
/* 195 */       checkModule(BowJump.class); 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onWorldChange(RespawnEvent event) {
/* 200 */     checkModule(BowJump.class);
/*     */   }
/*     */   
/*     */   public void onDisable() {
/* 204 */     mc.timer.timerSpeed = 1.0F;
/* 205 */     mc.thePlayer.speedInAir = 0.02F;
/*     */   }
/*     */   
/*     */   private int getBowSlot() {
/* 209 */     for (int i = 36; i < 45; i++) {
/* 210 */       ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
/* 211 */       if (stack != null && stack.getItem() instanceof net.minecraft.item.ItemBow) {
/* 212 */         return i - 36;
/*     */       }
/*     */     } 
/* 215 */     return -1;
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onRender2D(EventRender2D event) {
/* 220 */     if (!((Boolean)this.renderValue.get()).booleanValue()) {
/*     */       return;
/*     */     }
/* 223 */     ScaledResolution scaledRes = event.getResolution();
/* 224 */     float width = this.bowState / 5.0F * 60.0F;
/* 225 */     Client.instance.FontLoaders.regular18.drawCenteredString(getBowStatus(), scaledRes.getScaledWidth() / 2.0F, scaledRes.getScaledHeight() / 2.0F + 14.0F, -1);
/* 226 */     RenderUtil.drawRect(scaledRes.getScaledWidth() / 2.0F - 31.0F, scaledRes.getScaledHeight() / 2.0F + 25.0F, scaledRes.getScaledWidth() / 2.0F + 31.0F, scaledRes.getScaledHeight() / 2.0F + 29.0F, -1610612736);
/* 227 */     RenderUtil.drawRect(scaledRes.getScaledWidth() / 2.0F - 30.0F, scaledRes.getScaledHeight() / 2.0F + 26.0F, scaledRes.getScaledWidth() / 2.0F - 30.0F + width, scaledRes.getScaledHeight() / 2.0F + 28.0F, getStatusColor().hashCode());
/*     */   }
/*     */   
/*     */   public String getBowStatus() {
/* 231 */     switch (this.bowState) {
/*     */       case 0:
/* 233 */         return "Idle...";
/*     */       
/*     */       case 1:
/* 236 */         return "Preparing...";
/*     */       
/*     */       case 2:
/* 239 */         return "Waiting for damage...";
/*     */       
/*     */       case 3:
/*     */       case 4:
/* 243 */         return "Boost!";
/*     */     } 
/*     */     
/* 246 */     return "Task completed.";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Color getStatusColor() {
/* 252 */     switch (this.bowState) {
/*     */       case 0:
/* 254 */         return new Color(21, 21, 21);
/*     */       
/*     */       case 1:
/* 257 */         return new Color(48, 48, 48);
/*     */       
/*     */       case 2:
/* 260 */         return Color.yellow;
/*     */       
/*     */       case 3:
/*     */       case 4:
/* 264 */         return Color.green;
/*     */     } 
/*     */     
/* 267 */     return new Color(0, 111, 255);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\move\BowJump.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */