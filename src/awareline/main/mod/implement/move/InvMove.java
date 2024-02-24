/*     */ package awareline.main.mod.implement.move;
/*     */ import awareline.main.event.EventHandler;
/*     */ import awareline.main.event.events.world.packetEvents.PacketEvent;
/*     */ import awareline.main.event.events.world.renderEvents.EventRender2D;
/*     */ import awareline.main.event.events.world.worldChangeEvents.LoadWorldEvent;
/*     */ import awareline.main.mod.Module;
/*     */ import awareline.main.mod.ModuleType;
/*     */ import awareline.main.mod.implement.world.ChestStealer;
/*     */ import awareline.main.mod.values.Mode;
/*     */ import awareline.main.mod.values.Option;
/*     */ import awareline.main.mod.values.Value;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.C03PacketPlayer;
/*     */ import net.minecraft.network.play.client.C0BPacketEntityAction;
/*     */ import net.minecraft.network.play.client.C0DPacketCloseWindow;
/*     */ import net.minecraft.network.play.client.C0EPacketClickWindow;
/*     */ import net.minecraft.network.play.client.C16PacketClientStatus;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ 
/*     */ public class InvMove extends Module {
/*     */   public static InvMove getInstance;
/*  27 */   public final Option<Boolean> invJump = new Option("Jump", Boolean.valueOf(true)); public final Option<Boolean> invSneak = new Option("Sneak", Boolean.valueOf(false)); public final Option<Boolean> cancelClickPacket = new Option("CancelClick", 
/*  28 */       Boolean.valueOf(false)); public final Option<Boolean> cancelInventoryMove = new Option("CancelInventory", Boolean.valueOf(false));
/*  29 */   public final Option<Boolean> fakeWindowId = new Option("FakeWindowId", Boolean.valueOf(false)); public final Option<Boolean> slowMotion = new Option("SlowMotion", Boolean.valueOf(false));
/*  30 */   public final Mode<String> bypass = new Mode("Bypass", new String[] { "NoOpenPacket", "Blink", "Watchdog", "PacketInv", "None" }, "None");
/*     */   private final List<C03PacketPlayer> blinkPacketList;
/*     */   private final List<C0EPacketClickWindow> packetListYes;
/*     */   private final LinkedList<C0EPacketClickWindow> packets;
/*     */   public boolean lastInvOpen;
/*     */   public boolean invOpen;
/*     */   public boolean isInv;
/*     */   public boolean inInventory;
/*     */   
/*     */   public InvMove() {
/*  40 */     super("InventoryMove", ModuleType.Movement);
/*  41 */     addSettings(new Value[] { (Value)this.invJump, (Value)this.invSneak, (Value)this.bypass, (Value)this.cancelClickPacket, (Value)this.cancelInventoryMove, (Value)this.slowMotion, (Value)this.fakeWindowId });
/*     */ 
/*     */ 
/*     */     
/*  45 */     this.blinkPacketList = new ArrayList<>();
/*  46 */     this.packetListYes = new ArrayList<>();
/*  47 */     this.packets = new LinkedList<>();
/*  48 */     getInstance = this;
/*  49 */     setEnabled(true);
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onWorldChange(LoadWorldEvent e) {
/*  54 */     this.blinkPacketList.clear();
/*  55 */     this.invOpen = false;
/*  56 */     this.lastInvOpen = false;
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onPacket(PacketEvent event) {
/*  62 */     Packet<?> packet = event.getPacket();
/*     */     
/*  64 */     this.lastInvOpen = this.invOpen;
/*     */     
/*  66 */     if (packet instanceof net.minecraft.network.play.server.S2DPacketOpenWindow || (packet instanceof C16PacketClientStatus && ((C16PacketClientStatus)packet).getStatus() == C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT))
/*     */     {
/*  68 */       this.invOpen = true;
/*     */     }
/*     */     
/*  71 */     if (packet instanceof net.minecraft.network.play.server.S2EPacketCloseWindow || packet instanceof C0DPacketCloseWindow) {
/*  72 */       this.invOpen = false;
/*     */     }
/*     */     
/*  75 */     switch (((String)this.bypass.get()).toLowerCase()) {
/*     */       case "blink":
/*  77 */         if (packet instanceof C03PacketPlayer) {
/*  78 */           if (this.lastInvOpen) {
/*  79 */             C03PacketPlayer wrapper = (C03PacketPlayer)packet;
/*  80 */             this.blinkPacketList.add(wrapper);
/*  81 */             event.cancelEvent(); break;
/*  82 */           }  if (!this.blinkPacketList.isEmpty()) {
/*  83 */             C03PacketPlayer wrapper = (C03PacketPlayer)packet;
/*  84 */             this.blinkPacketList.add(wrapper);
/*  85 */             event.cancelEvent();
/*  86 */             this.blinkPacketList.forEach(this::sendNoEvent);
/*  87 */             this.blinkPacketList.clear();
/*     */           } 
/*     */         } 
/*     */         break;
/*     */       
/*     */       case "watchdog":
/*  93 */         if (packet instanceof C0EPacketClickWindow) {
/*  94 */           if (this.inInventory && !(mc.currentScreen instanceof net.minecraft.client.gui.inventory.GuiChest)) {
/*  95 */             this.packets.add((C0EPacketClickWindow)packet);
/*  96 */             event.setCancelled(true);
/*     */           } 
/*  98 */           if (mc.thePlayer.ticksExisted % 5 == 0 && this.inInventory && !(mc.currentScreen instanceof net.minecraft.client.gui.inventory.GuiChest) && !this.packets.isEmpty()) {
/*  99 */             sendPacketNoEvent((Packet)new C0BPacketEntityAction((Entity)mc.thePlayer, C0BPacketEntityAction.Action.OPEN_INVENTORY));
/* 100 */             this.packets.forEach(this::sendPacketNoEvent);
/* 101 */             this.packets.clear();
/* 102 */             sendPacketNoEvent((Packet)new C0DPacketCloseWindow(0));
/* 103 */           } else if (mc.thePlayer.ticksExisted % 7 == 0 && !this.packets.isEmpty()) {
/* 104 */             sendPacketNoEvent((Packet)new C0BPacketEntityAction((Entity)mc.thePlayer, C0BPacketEntityAction.Action.OPEN_INVENTORY));
/* 105 */             this.packets.forEach(this::sendPacketNoEvent);
/* 106 */             this.packets.clear();
/* 107 */             sendPacketNoEvent((Packet)new C0DPacketCloseWindow(0));
/*     */           } 
/*     */         } 
/*     */         
/* 111 */         if (packet instanceof C0DPacketCloseWindow) {
/* 112 */           event.setCancelled(true);
/* 113 */           this.inInventory = false;
/* 114 */           this.packets.forEach(this::sendPacketNoEvent);
/* 115 */           this.packets.clear();
/*     */         } 
/* 117 */         if (packet instanceof C16PacketClientStatus && ((C16PacketClientStatus)packet).getStatus() == C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT) {
/*     */           
/* 119 */           event.setCancelled(true);
/* 120 */           this.inInventory = true;
/*     */         } 
/*     */         
/* 123 */         if (packet instanceof C0BPacketEntityAction) {
/* 124 */           C0BPacketEntityAction p = (C0BPacketEntityAction)packet;
/* 125 */           if (p.getAction() == C0BPacketEntityAction.Action.OPEN_INVENTORY) {
/* 126 */             this.inInventory = true;
/* 127 */             event.setCancelled(true);
/*     */           } 
/*     */         } 
/*     */         break;
/*     */       
/*     */       case "noopenpacket":
/* 133 */         if (packet instanceof C16PacketClientStatus && ((C16PacketClientStatus)packet).getStatus() == C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT)
/*     */         {
/* 135 */           event.cancelEvent();
/*     */         }
/*     */         break;
/*     */       
/*     */       case "packetinv":
/* 140 */         if (packet instanceof C16PacketClientStatus && ((C16PacketClientStatus)packet).getStatus() == C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT) {
/*     */           
/* 142 */           event.cancelEvent();
/* 143 */           this.isInv = true;
/*     */         } 
/* 145 */         if (packet instanceof C0DPacketCloseWindow) {
/* 146 */           event.cancelEvent();
/* 147 */           this.isInv = false;
/*     */         } 
/* 149 */         if (!(packet instanceof C0EPacketClickWindow)) {
/*     */           break;
/*     */         }
/* 152 */         if (this.isInv) {
/*     */           return;
/*     */         }
/* 155 */         this.packetListYes.clear();
/* 156 */         this.packetListYes.add((C0EPacketClickWindow)packet);
/* 157 */         event.cancelEvent();
/* 158 */         sendPacketNoEvent((Packet)new C16PacketClientStatus(C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT));
/*     */         
/* 160 */         for (C0EPacketClickWindow element$iv : this.packetListYes) {
/* 161 */           sendPacketNoEvent((Packet)element$iv);
/*     */         }
/* 163 */         this.packetListYes.clear();
/* 164 */         sendPacketNoEvent((Packet)new C0DPacketCloseWindow(mc.thePlayer.inventoryContainer.windowId));
/*     */         break;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 171 */     if (((Boolean)this.slowMotion.get()).booleanValue() && packet instanceof C03PacketPlayer && 
/* 172 */       this.lastInvOpen) {
/* 173 */       mc.thePlayer.motionX *= 0.2D;
/* 174 */       mc.thePlayer.motionZ *= 0.2D;
/*     */     } 
/*     */ 
/*     */     
/* 178 */     if (((Boolean)this.cancelInventoryMove.get()).booleanValue()) {
/* 179 */       if (packet instanceof C16PacketClientStatus) {
/* 180 */         C16PacketClientStatus wrapper = (C16PacketClientStatus)packet;
/* 181 */         if (wrapper.getStatus() == C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT) {
/* 182 */           event.setCancelled(true);
/*     */         }
/*     */       } 
/* 185 */       if (packet instanceof C0BPacketEntityAction) {
/* 186 */         C0BPacketEntityAction wrapper = (C0BPacketEntityAction)packet;
/* 187 */         if (wrapper.getAction() == C0BPacketEntityAction.Action.OPEN_INVENTORY) {
/* 188 */           event.setCancelled(true);
/*     */         }
/*     */       } 
/*     */       
/* 192 */       if (packet instanceof C0DPacketCloseWindow) {
/* 193 */         event.setCancelled(true);
/*     */       }
/*     */     } 
/* 196 */     if (((Boolean)this.fakeWindowId.get()).booleanValue()) {
/*     */       
/* 198 */       if (packet instanceof C0DPacketCloseWindow) {
/* 199 */         event.setCancelled(true);
/*     */       }
/* 201 */       if (packet instanceof C0EPacketClickWindow) {
/* 202 */         event.setCancelled(true);
/* 203 */         sendPacketNoEvent((Packet)new C16PacketClientStatus(C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT));
/* 204 */         sendPacketNoEvent(event.getPacket());
/* 205 */         sendPacketNoEvent((Packet)new C0DPacketCloseWindow(mc.thePlayer.inventoryContainer.windowId));
/*     */       } 
/*     */       
/* 208 */       if (packet instanceof C16PacketClientStatus) {
/* 209 */         C16PacketClientStatus packetClientStatus = (C16PacketClientStatus)event.getPacket();
/* 210 */         if (packetClientStatus.getStatus() == C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT) {
/* 211 */           event.setCancelled(true);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onMove(PacketEvent e) {
/* 221 */     if (!isMoving()) {
/*     */       return;
/*     */     }
/* 224 */     if (((Boolean)this.cancelClickPacket.get()).booleanValue() && mc.currentScreen instanceof net.minecraft.client.gui.inventory.GuiInventory)
/*     */     {
/* 226 */       if (e.getState() == PacketEvent.State.OUTGOING && e.getPacket() instanceof C0EPacketClickWindow) {
/* 227 */         e.setCancelled(true);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisable() {
/* 234 */     this.blinkPacketList.clear();
/* 235 */     this.lastInvOpen = false;
/* 236 */     this.invOpen = false;
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onRender2D(EventRender2D eventRender2D) {
/* 241 */     setSuffix(this.bypass.is("None") ? "Normal" : (Serializable)this.bypass.get());
/* 242 */     if (((ChestStealer.getInstance.isEnabled() && ChestStealer.getInstance.isStealing() && ((Boolean)ChestStealer.getInstance.silent.get()).booleanValue()) || mc.currentScreen != null) && !(mc.currentScreen instanceof net.minecraft.client.gui.GuiChat)) {
/*     */       
/* 244 */       mc.gameSettings.keyBindForward.setPressed(Keyboard.isKeyDown(mc.gameSettings.keyBindForward.getKeyCode()));
/* 245 */       mc.gameSettings.keyBindBack.setPressed(Keyboard.isKeyDown(mc.gameSettings.keyBindBack.getKeyCode()));
/* 246 */       mc.gameSettings.keyBindLeft.setPressed(Keyboard.isKeyDown(mc.gameSettings.keyBindLeft.getKeyCode()));
/* 247 */       mc.gameSettings.keyBindRight.setPressed(Keyboard.isKeyDown(mc.gameSettings.keyBindRight.getKeyCode()));
/* 248 */       if (((Boolean)this.invJump.getValue()).booleanValue())
/* 249 */         mc.gameSettings.keyBindJump.setPressed(Keyboard.isKeyDown(mc.gameSettings.keyBindJump.getKeyCode())); 
/* 250 */       if (((Boolean)this.invSneak.getValue()).booleanValue())
/* 251 */         mc.gameSettings.keyBindSneak.setPressed(Keyboard.isKeyDown(mc.gameSettings.keyBindSneak.getKeyCode())); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\move\InvMove.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */