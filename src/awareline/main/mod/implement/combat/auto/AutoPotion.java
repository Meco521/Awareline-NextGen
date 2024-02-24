/*     */ package awareline.main.mod.implement.combat.auto;
/*     */ 
/*     */ import awareline.main.Client;
/*     */ import awareline.main.event.EventHandler;
/*     */ import awareline.main.event.events.world.EventTick;
/*     */ import awareline.main.event.events.world.moveEvents.EventMove;
/*     */ import awareline.main.event.events.world.updateEvents.EventPostUpdate;
/*     */ import awareline.main.event.events.world.updateEvents.EventPreUpdate;
/*     */ import awareline.main.mod.Module;
/*     */ import awareline.main.mod.ModuleType;
/*     */ import awareline.main.mod.implement.combat.KillAura;
/*     */ import awareline.main.mod.implement.world.Scaffold;
/*     */ import awareline.main.mod.implement.world.utils.DelayTimer;
/*     */ import awareline.main.mod.values.Numbers;
/*     */ import awareline.main.mod.values.Option;
/*     */ import awareline.main.mod.values.Value;
/*     */ import awareline.main.utility.BlockUtils;
/*     */ import awareline.main.utility.MoveUtils;
/*     */ import awareline.main.utility.PlayerUtil;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemPotion;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
/*     */ import net.minecraft.network.play.client.C09PacketHeldItemChange;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ 
/*     */ public class AutoPotion
/*     */   extends Module
/*     */ {
/*     */   public static AutoPotion getInstance;
/*  35 */   private final Numbers<Double> health = new Numbers("Health", Double.valueOf(10.0D), Double.valueOf(0.0D), Double.valueOf(20.0D), Double.valueOf(0.5D)); private final Numbers<Double> delay = new Numbers("Delay", 
/*  36 */       Double.valueOf(1000.0D), Double.valueOf(0.0D), Double.valueOf(5000.0D), Double.valueOf(250.0D));
/*     */   
/*  38 */   private final Option<Boolean> heal = new Option("Heal", Boolean.valueOf(true)); private final Option<Boolean> regen = new Option("Regen", Boolean.valueOf(true)); private final Option<Boolean> jump = new Option("JumpThrow", 
/*  39 */       Boolean.valueOf(false)); private final Option<Boolean> speed = new Option("Speed", Boolean.valueOf(true));
/*  40 */   private final Option<Boolean> nofrog = new Option("NoFrog", Boolean.valueOf(true)); private final Option<Boolean> stuckMove = new Option("Sutck", Boolean.valueOf(false));
/*  41 */   private final Option<Boolean> safeSwap = new Option("SafeSwap", Boolean.valueOf(false)); private final Option<Boolean> vanilla = new Option("Vanilla", Boolean.valueOf(false));
/*  42 */   private final Option<Boolean> onlyNoMove = new Option("OnlyNoMove", Boolean.valueOf(false), () -> Boolean.valueOf(!((Boolean)this.vanilla.get()).booleanValue()));
/*  43 */   private final Option<Boolean> onlyGround = new Option("OnlyGround", Boolean.valueOf(false), () -> Boolean.valueOf(!((Boolean)this.vanilla.get()).booleanValue()));
/*     */   
/*  45 */   private final DelayTimer cooldown = new DelayTimer(); private final DelayTimer timer = new DelayTimer();
/*     */   public boolean stuck;
/*     */   public boolean jumping;
/*     */   public boolean rotated;
/*     */   private int lastPottedSlot;
/*     */   
/*     */   public AutoPotion() {
/*  52 */     super("AutoPot", ModuleType.Combat);
/*  53 */     addSettings(new Value[] { (Value)this.health, (Value)this.delay, (Value)this.heal, (Value)this.regen, (Value)this.jump, (Value)this.speed, (Value)this.nofrog, (Value)this.stuckMove, (Value)this.safeSwap, (Value)this.onlyNoMove, (Value)this.onlyGround, (Value)this.vanilla });
/*     */     
/*  55 */     getInstance = this;
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onMove(EventMove event) {
/*  60 */     if (((Boolean)this.stuckMove.get()).booleanValue()) {
/*  61 */       if (this.stuck) {
/*  62 */         this; mc.thePlayer.motionX = 0.0D;
/*  63 */         this; mc.thePlayer.motionZ = 0.0D;
/*  64 */         event.x = 0.0D;
/*  65 */         event.z = 0.0D;
/*  66 */         if (this.timer.hasPassed(100.0D)) { this; if (mc.thePlayer.onGround)
/*  67 */             this.stuck = false;  }
/*     */       
/*     */       } 
/*     */     } else {
/*  71 */       this.stuck = false;
/*     */     } 
/*  73 */     if (this.jumping) {
/*  74 */       this; mc.thePlayer.motionX = 0.0D;
/*  75 */       this; mc.thePlayer.motionZ = 0.0D;
/*  76 */       event.x = 0.0D;
/*  77 */       event.z = 0.0D;
/*     */       
/*  79 */       if (this.cooldown.hasPassed(100.0D)) { this; if (mc.thePlayer.onGround)
/*  80 */           this.jumping = false;  }
/*     */     
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler(1)
/*     */   private void onPreUpdate(EventPreUpdate event) {
/*  87 */     if (!((Boolean)this.vanilla.get()).booleanValue()) {
/*     */       
/*  89 */       if (MoveUtils.INSTANCE.getBlockUnderPlayer((EntityPlayer)mc.thePlayer, 0.01D) instanceof net.minecraft.block.BlockGlass || 
/*  90 */         !MoveUtils.INSTANCE.isOnGround(0.01D)) {
/*  91 */         this.timer.reset();
/*     */         
/*     */         return;
/*     */       } 
/*  95 */       if (((Boolean)this.onlyGround.get()).booleanValue() && !mc.thePlayer.onGround) {
/*     */         return;
/*     */       }
/*     */       
/*  99 */       if (((Boolean)this.onlyNoMove.get()).booleanValue() && isMoving()) {
/*     */         return;
/*     */       }
/*     */       
/* 103 */       if (Scaffold.getInstance.isEnabled() || mc.currentScreen instanceof net.minecraft.client.gui.inventory.GuiContainerCreative) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 108 */       if (mc.thePlayer.openContainer != null && 
/* 109 */         mc.thePlayer.openContainer instanceof net.minecraft.inventory.ContainerChest) {
/* 110 */         this.timer.reset();
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 115 */       if (event.isModified() || KillAura.getInstance.target != null) {
/* 116 */         this.rotated = false;
/* 117 */         this.timer.reset();
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 122 */     int potSlot = getPotFromInventory();
/* 123 */     if (potSlot != -1 && this.timer.hasPassed(((Double)this.delay.getValue()).doubleValue())) {
/* 124 */       if (((Boolean)this.jump.getValue()).booleanValue() && !BlockUtils.isInLiquid()) {
/* 125 */         event.setPitch(-89.5F);
/*     */         
/* 127 */         this.jumping = true;
/* 128 */         this; if (mc.thePlayer.onGround) {
/* 129 */           this; mc.thePlayer.jump();
/* 130 */           this.cooldown.reset();
/*     */         } 
/*     */       } else {
/* 133 */         event.setPitch(90.0F);
/*     */       } 
/* 135 */       this.rotated = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   private void onPostUpdate(EventPostUpdate event) {
/* 142 */     if (!this.rotated) {
/*     */       return;
/*     */     }
/* 145 */     this.rotated = false;
/*     */     
/* 147 */     int potSlot = getPotFromInventory();
/* 148 */     if (potSlot != -1 && this.timer.hasPassed(((Double)this.delay.getValue()).doubleValue()) && mc.thePlayer.isCollidedVertically && 
/* 149 */       !Client.instance.badPacketsComponent.bad()) {
/* 150 */       int prevSlot = mc.thePlayer.inventory.currentItem;
/* 151 */       if (potSlot < 9) {
/* 152 */         sendPacket((Packet)new C09PacketHeldItemChange(potSlot));
/* 153 */         sendPacket((Packet)new C08PacketPlayerBlockPlacement(mc.thePlayer.inventory.getCurrentItem()));
/* 154 */         sendPacket((Packet)new C09PacketHeldItemChange(prevSlot));
/* 155 */         mc.thePlayer.inventory.currentItem = prevSlot;
/* 156 */         this.timer.reset();
/* 157 */         this.lastPottedSlot = potSlot;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onTick(EventTick event) {
/* 167 */     if (((Boolean)this.safeSwap.get()).booleanValue() && isMoving()) {
/*     */       return;
/*     */     }
/* 170 */     this; if (mc.currentScreen != null) {
/*     */       return;
/*     */     }
/* 173 */     int potSlot = getPotFromInventory();
/* 174 */     if (potSlot != -1 && potSlot > 8) { this; if (mc.thePlayer.ticksExisted % 4 == 0) {
/* 175 */         this.stuck = true;
/* 176 */         swap(potSlot, PlayerUtil.findEmptySlot(this.lastPottedSlot));
/*     */       }  }
/*     */   
/*     */   }
/*     */   private void swap(int slot, int hotbarNum) {
/* 181 */     this; this; this; mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, slot, hotbarNum, 2, (EntityPlayer)mc.thePlayer);
/*     */   }
/*     */ 
/*     */   
/*     */   private int getPotFromInventory() {
/*     */     int i;
/* 187 */     for (i = 0; i < 36; i++) {
/* 188 */       if (mc.thePlayer.inventory.mainInventory[i] != null) {
/* 189 */         ItemStack is = mc.thePlayer.inventory.mainInventory[i];
/* 190 */         Item item = is.getItem();
/*     */         
/* 192 */         if (item instanceof ItemPotion) {
/*     */ 
/*     */ 
/*     */           
/* 196 */           ItemPotion pot = (ItemPotion)item;
/*     */           
/* 198 */           if (ItemPotion.isSplash(is.getMetadata())) {
/*     */ 
/*     */ 
/*     */             
/* 202 */             List<PotionEffect> effects = pot.getEffects(is);
/*     */             
/* 204 */             for (PotionEffect effect : effects) {
/* 205 */               if (mc.thePlayer.getHealth() < ((Double)this.health.getValue()).doubleValue() && ((((Boolean)this.heal.getValue()).booleanValue() && effect.getPotionID() == Potion.heal.id) || (((Boolean)this.regen.getValue()).booleanValue() && effect.getPotionID() == Potion.regeneration.id && !hasEffect(Potion.regeneration.id))))
/* 206 */                 return i; 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 212 */     for (i = 0; i < 36; i++) {
/* 213 */       this; if (mc.thePlayer.inventory.mainInventory[i] != null) {
/* 214 */         this; ItemStack is = mc.thePlayer.inventory.mainInventory[i];
/* 215 */         Item item = is.getItem();
/*     */         
/* 217 */         if (item instanceof ItemPotion) {
/*     */ 
/*     */ 
/*     */           
/* 221 */           List<PotionEffect> effects = ((ItemPotion)item).getEffects(is);
/*     */           
/* 223 */           for (PotionEffect effect : effects) {
/* 224 */             if (effect.getPotionID() == Potion.moveSpeed.id && ((Boolean)this.speed.getValue()).booleanValue() && 
/* 225 */               !hasEffect(Potion.moveSpeed.id) && (
/* 226 */               !is.getDisplayName().contains("§a") || !((Boolean)this.nofrog.getValueState()).booleanValue()))
/* 227 */               return i; 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 232 */     return -1;
/*     */   }
/*     */   
/*     */   private boolean hasEffect(int potionId) {
/* 236 */     for (PotionEffect item : mc.thePlayer.getActivePotionEffects()) {
/* 237 */       if (item.getPotionID() == potionId)
/* 238 */         return true; 
/*     */     } 
/* 240 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\combat\auto\AutoPotion.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */