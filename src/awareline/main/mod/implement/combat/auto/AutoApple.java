/*     */ package awareline.main.mod.implement.combat.auto;
/*     */ 
/*     */ import awareline.main.event.EventHandler;
/*     */ import awareline.main.event.events.world.packetEvents.PacketEvent;
/*     */ import awareline.main.event.events.world.updateEvents.EventPreUpdate;
/*     */ import awareline.main.event.events.world.worldChangeEvents.LoadWorldEvent;
/*     */ import awareline.main.mod.Module;
/*     */ import awareline.main.mod.ModuleType;
/*     */ import awareline.main.mod.implement.combat.advanced.sucks.utils.InventoryUtils;
/*     */ import awareline.main.mod.implement.combat.advanced.sucks.utils.MSTimer;
/*     */ import awareline.main.mod.values.Mode;
/*     */ import awareline.main.mod.values.Numbers;
/*     */ import awareline.main.mod.values.Option;
/*     */ import awareline.main.mod.values.Value;
/*     */ import java.util.Random;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.C03PacketPlayer;
/*     */ import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
/*     */ import net.minecraft.network.play.client.C09PacketHeldItemChange;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.util.MathHelper;
/*     */ 
/*     */ public class AutoApple
/*     */   extends Module
/*     */ {
/*  27 */   public final Mode<String> modeValue = new Mode("Mode", new String[] { "Auto", "LegitAuto", "Legit", "Head" }, "Auto");
/*     */ 
/*     */ 
/*     */   
/*  31 */   private final Numbers<Double> percent = new Numbers("HealthPercent", Double.valueOf(75.0D), Double.valueOf(1.0D), Double.valueOf(100.0D), Double.valueOf(1.0D));
/*  32 */   private final Numbers<Double> regenSec = new Numbers("MinRegenSec", Double.valueOf(4.6D), Double.valueOf(0.0D), Double.valueOf(10.0D), Double.valueOf(1.0D));
/*  33 */   private final Numbers<Double> eatDelayValue = new Numbers("FastEatDelay", Double.valueOf(14.0D), Double.valueOf(0.0D), Double.valueOf(35.0D), Double.valueOf(1.0D));
/*  34 */   private final Numbers<Double> max = new Numbers("MaxDelay", Double.valueOf(75.0D), Double.valueOf(5.0D), Double.valueOf(5000.0D), Double.valueOf(5.0D));
/*  35 */   private final Numbers<Double> min = new Numbers("MinDelay", Double.valueOf(125.0D), Double.valueOf(5.0D), Double.valueOf(5000.0D), Double.valueOf(5.0D));
/*  36 */   private final Option<Boolean> waitRegen = new Option("WaitRegen", Boolean.valueOf(false));
/*  37 */   private final Option<Boolean> fastEatValue = new Option("FastEat", Boolean.valueOf(false));
/*  38 */   private final Option<Boolean> invCheck = new Option("InvCheck", Boolean.valueOf(false));
/*  39 */   private final Option<Boolean> absorpCheck = new Option("NoAbsorption", Boolean.valueOf(false));
/*  40 */   private final Option<Boolean> groundCheck = new Option("OnlyOnGround", Boolean.valueOf(false));
/*     */   
/*     */   private final MSTimer timer;
/*     */   private int eating;
/*     */   private int delay;
/*     */   private boolean isDisable;
/*     */   private boolean tryHeal;
/*     */   private int prevSlot;
/*     */   private boolean switchBack;
/*     */   
/*     */   public AutoApple() {
/*  51 */     super("AutoApple", ModuleType.Combat);
/*  52 */     addSettings(new Value[] { (Value)this.modeValue, (Value)this.percent, (Value)this.regenSec, (Value)this.max, (Value)this.min, (Value)this.waitRegen, (Value)this.fastEatValue, (Value)this.eatDelayValue, (Value)this.invCheck, (Value)this.absorpCheck, (Value)this.groundCheck });
/*  53 */     this.timer = new MSTimer();
/*  54 */     this.eating = -1;
/*  55 */     this.prevSlot = -1;
/*     */   }
/*     */   
/*     */   public int getDelay() {
/*  59 */     return this.delay;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEnable() {
/*  64 */     this.eating = -1;
/*  65 */     this.prevSlot = -1;
/*  66 */     this.switchBack = false;
/*  67 */     this.timer.reset();
/*  68 */     this.isDisable = false;
/*  69 */     this.tryHeal = false;
/*  70 */     this.delay = MathHelper.getRandomIntegerInRange(new Random(), ((Double)this.min.get()).intValue(), ((Double)this.max.get()).intValue());
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onWorldChange(LoadWorldEvent event) {
/*  75 */     this.isDisable = true;
/*  76 */     this.tryHeal = false;
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onPacket(PacketEvent event) {
/*  81 */     Packet packet = event.getPacket();
/*  82 */     if (this.eating != -1 && packet instanceof C03PacketPlayer) {
/*  83 */       this.eating++;
/*  84 */     } else if (packet instanceof net.minecraft.network.play.server.S09PacketHeldItemChange || packet instanceof C09PacketHeldItemChange) {
/*  85 */       this.eating = -1;
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onUpdate(EventPreUpdate event) {
/*  91 */     if (this.tryHeal) {
/*  92 */       int headInHotbar; int gappleInHotbar; switch (((String)this.modeValue.get()).toLowerCase()) {
/*     */         case "head":
/*  94 */           headInHotbar = InventoryUtils.findItem(36, 45, Items.skull);
/*  95 */           if (headInHotbar != -1) {
/*  96 */             sendPacket((Packet)new C09PacketHeldItemChange(headInHotbar - 36));
/*  97 */             sendPacket((Packet)new C08PacketPlayerBlockPlacement(mc.thePlayer.getHeldItem()));
/*  98 */             sendPacket((Packet)new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
/*  99 */             this.timer.reset();
/* 100 */             this.tryHeal = false;
/* 101 */             this.delay = MathHelper.getRandomIntegerInRange(new Random(), ((Double)this.min.get()).intValue(), ((Double)this.max.get()).intValue());
/*     */             break;
/*     */           } 
/* 104 */           this.tryHeal = false;
/*     */           break;
/*     */         
/*     */         case "auto":
/* 108 */           gappleInHotbar = InventoryUtils.findItem(36, 45, Items.golden_apple);
/* 109 */           if (gappleInHotbar != -1) {
/* 110 */             sendPacketNoEvent((Packet)new C09PacketHeldItemChange(gappleInHotbar - 36));
/* 111 */             sendPacketNoEvent((Packet)new C08PacketPlayerBlockPlacement(mc.thePlayer.getHeldItem()));
/* 112 */             int i = 0;
/* 113 */             while (i < 35) {
/* 114 */               i++;
/* 115 */               sendPacket((Packet)new C03PacketPlayer(mc.thePlayer.onGround));
/*     */             } 
/* 117 */             sendPacketNoEvent((Packet)new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
/* 118 */             this.tryHeal = false;
/* 119 */             this.timer.reset();
/* 120 */             this.delay = MathHelper.getRandomIntegerInRange(new Random(), ((Double)this.min.get()).intValue(), ((Double)this.max.get()).intValue()); break;
/*     */           } 
/* 122 */           this.tryHeal = false;
/*     */           break;
/*     */ 
/*     */         
/*     */         case "legit":
/* 127 */           if (this.eating == -1) {
/* 128 */             gappleInHotbar = InventoryUtils.findItem(36, 45, Items.golden_apple);
/* 129 */             if (gappleInHotbar == -1) {
/* 130 */               this.tryHeal = false;
/*     */               return;
/*     */             } 
/* 133 */             if (this.prevSlot == -1) {
/* 134 */               this.prevSlot = mc.thePlayer.inventory.currentItem;
/*     */             }
/* 136 */             mc.thePlayer.inventory.currentItem = gappleInHotbar - 36;
/* 137 */             sendPacket((Packet)new C08PacketPlayerBlockPlacement(mc.thePlayer.getHeldItem()));
/* 138 */             this.eating = 0;
/*     */             break;
/*     */           } 
/* 141 */           if (this.eating > 35 || (((Boolean)this.fastEatValue.get()).booleanValue() && this.eating > ((Double)this.eatDelayValue.get()).intValue())) {
/* 142 */             int n4 = 35 - this.eating;
/* 143 */             int j = 0;
/* 144 */             while (j < n4) {
/* 145 */               j++;
/* 146 */               sendPacket((Packet)new C03PacketPlayer(mc.thePlayer.onGround));
/*     */             } 
/* 148 */             this.timer.reset();
/* 149 */             this.tryHeal = false;
/* 150 */             this.delay = MathHelper.getRandomIntegerInRange(new Random(), ((Double)this.min
/* 151 */                 .get()).intValue(), ((Double)this.max.get()).intValue());
/*     */           } 
/*     */           break;
/*     */ 
/*     */ 
/*     */         
/*     */         case "legitauto":
/* 158 */           if (this.eating == -1) {
/* 159 */             gappleInHotbar = InventoryUtils.findItem(36, 45, Items.golden_apple);
/* 160 */             if (gappleInHotbar == -1) {
/* 161 */               this.tryHeal = false;
/*     */               return;
/*     */             } 
/* 164 */             sendPacket((Packet)new C09PacketHeldItemChange(gappleInHotbar - 36));
/* 165 */             sendPacket((Packet)new C08PacketPlayerBlockPlacement(mc.thePlayer.getHeldItem()));
/* 166 */             this.eating = 0;
/*     */             break;
/*     */           } 
/* 169 */           if (this.eating > 35 || (((Boolean)this.fastEatValue.get()).booleanValue() && this.eating > ((Double)this.eatDelayValue.get()).intValue())) {
/* 170 */             int n7 = 35 - this.eating;
/* 171 */             int k = 0;
/* 172 */             while (k < n7) {
/* 173 */               k++;
/* 174 */               sendPacket((Packet)new C03PacketPlayer(mc.thePlayer.onGround));
/*     */             } 
/* 176 */             sendPacket((Packet)new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
/* 177 */             this.timer.reset();
/* 178 */             this.tryHeal = false;
/* 179 */             this.delay = MathHelper.getRandomIntegerInRange(new Random(), ((Double)this.min
/* 180 */                 .get()).intValue(), ((Double)this.max.get()).intValue());
/*     */           } 
/*     */           break;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     } 
/* 190 */     if (mc.thePlayer.ticksExisted <= 10 && this.isDisable) {
/* 191 */       this.isDisable = false;
/*     */     }
/* 193 */     int absorp = MathHelper.ceiling_double_int(mc.thePlayer.getAbsorptionAmount());
/* 194 */     if (!this.tryHeal && this.prevSlot != -1) {
/* 195 */       if (!this.switchBack) {
/* 196 */         this.switchBack = true;
/*     */         return;
/*     */       } 
/* 199 */       mc.thePlayer.inventory.currentItem = this.prevSlot;
/* 200 */       this.eating = -1;
/* 201 */       this.prevSlot = -1;
/* 202 */       this.switchBack = false;
/*     */     } 
/* 204 */     if ((((Boolean)this.groundCheck.get()).booleanValue() && !mc.thePlayer.onGround) || (((Boolean)this.invCheck.get()).booleanValue() && mc.currentScreen instanceof net.minecraft.client.gui.inventory.GuiContainer) || (absorp > 0 && ((Boolean)this.absorpCheck.get()).booleanValue())) {
/*     */       return;
/*     */     }
/* 207 */     if (((Boolean)this.waitRegen.get()).booleanValue() && mc.thePlayer.isPotionActive(Potion.regeneration) && mc.thePlayer.getActivePotionEffect(Potion.regeneration).getDuration() > ((Double)this.regenSec
/* 208 */       .get()).floatValue() * 20.0F) {
/*     */       return;
/*     */     }
/* 211 */     if (!this.isDisable && mc.thePlayer.getHealth() <= ((Double)this.percent.get()).floatValue() / 100.0F * mc.thePlayer.getMaxHealth() && this.timer
/* 212 */       .hasTimePassed(this.delay)) {
/* 213 */       if (this.tryHeal) {
/*     */         return;
/*     */       }
/* 216 */       this.tryHeal = true;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\combat\auto\AutoApple.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */