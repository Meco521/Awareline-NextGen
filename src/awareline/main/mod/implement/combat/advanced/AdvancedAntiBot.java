/*     */ package awareline.main.mod.implement.combat.advanced;
/*     */ import awareline.main.event.EventHandler;
/*     */ import awareline.main.event.events.LBEvents.EventWorldChanged;
/*     */ import awareline.main.event.events.world.EventAttack;
/*     */ import awareline.main.event.events.world.packetEvents.EventPacketReceive;
/*     */ import awareline.main.mod.Module;
/*     */ import awareline.main.mod.ModuleType;
/*     */ import awareline.main.mod.implement.combat.advanced.sucks.utils.EntityUtils;
/*     */ import awareline.main.mod.values.Mode;
/*     */ import awareline.main.mod.values.Numbers;
/*     */ import awareline.main.mod.values.Option;
/*     */ import awareline.main.mod.values.Value;
/*     */ import awareline.main.ui.font.fontmanager.color.ColorUtils;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.network.NetworkPlayerInfo;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.server.S0BPacketAnimation;
/*     */ import net.minecraft.network.play.server.S14PacketEntity;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class AdvancedAntiBot extends Module {
/*  28 */   private final Option<Boolean> tabValue = new Option("Tab", Boolean.valueOf(true));
/*  29 */   private final Mode<String> tabModeValue = new Mode("TabMode", new String[] { "Equals", "Contains" }, "Contains");
/*  30 */   private final Option<Boolean> entityIDValue = new Option("EntityID", Boolean.valueOf(true));
/*  31 */   private final Option<Boolean> colorValue = new Option("Color", Boolean.valueOf(false));
/*  32 */   private final Option<Boolean> livingTimeValue = new Option("LivingTime", Boolean.valueOf(false));
/*  33 */   private final Numbers<Double> livingTimeTicksValue = new Numbers("LivingTimeTicks", Double.valueOf(40.0D), Double.valueOf(1.0D), Double.valueOf(200.0D), Double.valueOf(0.1D));
/*  34 */   private final Option<Boolean> groundValue = new Option("Ground", Boolean.valueOf(true));
/*  35 */   private final Option<Boolean> airValue = new Option("Air", Boolean.valueOf(false));
/*  36 */   private final Option<Boolean> invalidGroundValue = new Option("InvalidGround", Boolean.valueOf(true));
/*  37 */   private final Option<Boolean> swingValue = new Option("Swing", Boolean.valueOf(false));
/*  38 */   private final Option<Boolean> healthValue = new Option("Health", Boolean.valueOf(false));
/*  39 */   private final Option<Boolean> derpValue = new Option("Derp", Boolean.valueOf(true));
/*  40 */   private final Option<Boolean> wasInvisibleValue = new Option("WasInvisible", Boolean.valueOf(false));
/*  41 */   private final Option<Boolean> armorValue = new Option("Armor", Boolean.valueOf(false));
/*  42 */   private final Option<Boolean> pingValue = new Option("Ping", Boolean.valueOf(false));
/*  43 */   private final Option<Boolean> needHitValue = new Option("NeedHit", Boolean.valueOf(false));
/*  44 */   private final Option<Boolean> duplicateInWorldValue = new Option("DuplicateInWorld", Boolean.valueOf(false));
/*  45 */   private final Option<Boolean> duplicateInTabValue = new Option("DuplicateInTab", Boolean.valueOf(false));
/*     */   
/*  47 */   private final List<Integer> ground = new ArrayList<>();
/*  48 */   private final List<Integer> air = new ArrayList<>();
/*  49 */   private final Map<Integer, Integer> invalidGround = new HashMap<>();
/*  50 */   private final List<Integer> swing = new ArrayList<>();
/*  51 */   private final List<Integer> invisible = new ArrayList<>();
/*  52 */   private final List<Integer> hitted = new ArrayList<>();
/*     */   public static AdvancedAntiBot getInstance;
/*     */   
/*     */   public AdvancedAntiBot() {
/*  56 */     super("AdvancedAntiBot", new String[] { "cab" }, ModuleType.Combat);
/*  57 */     addSettings(new Value[] { (Value)this.tabValue, (Value)this.tabModeValue, (Value)this.entityIDValue, (Value)this.colorValue, (Value)this.livingTimeValue, (Value)this.livingTimeTicksValue, (Value)this.groundValue, (Value)this.airValue, (Value)this.invalidGroundValue, (Value)this.swingValue, (Value)this.healthValue, (Value)this.derpValue, (Value)this.wasInvisibleValue, (Value)this.armorValue, (Value)this.pingValue, (Value)this.needHitValue, (Value)this.duplicateInWorldValue, (Value)this.duplicateInTabValue });
/*     */     
/*  59 */     getInstance = this;
/*     */   }
/*     */   
/*     */   public void onDisable() {
/*  63 */     clearAll();
/*  64 */     super.onDisable();
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onPacket(EventPacketReceive event) {
/*  70 */     if (mc.thePlayer == null || mc.theWorld == null) {
/*     */       return;
/*     */     }
/*  73 */     Packet<?> packet = event.getPacket();
/*     */     
/*  75 */     if (packet instanceof S14PacketEntity) {
/*  76 */       S14PacketEntity packetEntity = (S14PacketEntity)event.getPacket();
/*  77 */       Entity entity = packetEntity.getEntity((World)mc.theWorld);
/*     */       
/*  79 */       if (entity instanceof EntityPlayer) {
/*  80 */         if (packetEntity.getOnGround() && !this.ground.contains(Integer.valueOf(entity.getEntityId()))) {
/*  81 */           this.ground.add(Integer.valueOf(entity.getEntityId()));
/*     */         }
/*  83 */         if (!packetEntity.getOnGround() && !this.air.contains(Integer.valueOf(entity.getEntityId()))) {
/*  84 */           this.air.add(Integer.valueOf(entity.getEntityId()));
/*     */         }
/*  86 */         if (packetEntity.getOnGround()) {
/*  87 */           if (entity.prevPosY != entity.posY)
/*  88 */             this.invalidGround.put(Integer.valueOf(entity.getEntityId()), Integer.valueOf(((Integer)this.invalidGround.getOrDefault(Integer.valueOf(entity.getEntityId()), Integer.valueOf(0))).intValue() + 1)); 
/*     */         } else {
/*  90 */           int currentVL = ((Integer)this.invalidGround.getOrDefault(Integer.valueOf(entity.getEntityId()), Integer.valueOf(0))).intValue() / 2;
/*     */           
/*  92 */           if (currentVL <= 0) {
/*  93 */             this.invalidGround.remove(Integer.valueOf(entity.getEntityId()));
/*     */           } else {
/*  95 */             this.invalidGround.put(Integer.valueOf(entity.getEntityId()), Integer.valueOf(currentVL));
/*     */           } 
/*     */         } 
/*  98 */         if (entity.isInvisible() && !this.invisible.contains(Integer.valueOf(entity.getEntityId()))) {
/*  99 */           this.invisible.add(Integer.valueOf(entity.getEntityId()));
/*     */         }
/*     */       } 
/*     */     } 
/* 103 */     if (packet instanceof S0BPacketAnimation) {
/* 104 */       S0BPacketAnimation packetAnimation = (S0BPacketAnimation)event.getPacket();
/* 105 */       Entity entity = mc.theWorld.getEntityByID(packetAnimation.getEntityID());
/*     */       
/* 107 */       if (entity instanceof EntityLivingBase && packetAnimation.getAnimationType() == 0 && !this.swing.contains(Integer.valueOf(entity.getEntityId())))
/* 108 */         this.swing.add(Integer.valueOf(entity.getEntityId())); 
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onAttack(EventAttack e) {
/* 114 */     Entity entity = e.getEntity();
/*     */     
/* 116 */     if (entity instanceof EntityLivingBase && !this.hitted.contains(Integer.valueOf(entity.getEntityId())))
/* 117 */       this.hitted.add(Integer.valueOf(entity.getEntityId())); 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onWorld(EventWorldChanged event) {
/* 122 */     clearAll();
/*     */   }
/*     */   
/*     */   private void clearAll() {
/* 126 */     this.hitted.clear();
/* 127 */     this.swing.clear();
/* 128 */     this.ground.clear();
/* 129 */     this.invalidGround.clear();
/* 130 */     this.invisible.clear();
/*     */   }
/*     */   
/*     */   public static boolean isServerBot(Entity entity) {
/* 134 */     if (!(entity instanceof EntityPlayer)) {
/* 135 */       return false;
/*     */     }
/* 137 */     AdvancedAntiBot antiBot = getInstance;
/*     */     
/* 139 */     if (antiBot == null || !antiBot.isEnabled()) {
/* 140 */       return false;
/*     */     }
/* 142 */     if (((Boolean)antiBot.colorValue.getValue()).booleanValue() && 
/* 143 */       !entity.getDisplayName().getFormattedText().replace("搂r", "").contains("搂")) {
/* 144 */       return true;
/*     */     }
/* 146 */     if (((Boolean)antiBot.livingTimeValue.getValue()).booleanValue() && entity.ticksExisted < ((Double)antiBot.livingTimeTicksValue.getValue()).doubleValue()) {
/* 147 */       return true;
/*     */     }
/* 149 */     if (((Boolean)antiBot.groundValue.getValue()).booleanValue() && !antiBot.ground.contains(Integer.valueOf(entity.getEntityId()))) {
/* 150 */       return true;
/*     */     }
/* 152 */     if (((Boolean)antiBot.airValue.getValue()).booleanValue() && !antiBot.air.contains(Integer.valueOf(entity.getEntityId()))) {
/* 153 */       return true;
/*     */     }
/* 155 */     if (((Boolean)antiBot.swingValue.getValue()).booleanValue() && !antiBot.swing.contains(Integer.valueOf(entity.getEntityId()))) {
/* 156 */       return true;
/*     */     }
/* 158 */     if (((Boolean)antiBot.healthValue.getValue()).booleanValue() && ((EntityLivingBase)entity).getHealth() > 20.0F) {
/* 159 */       return true;
/*     */     }
/* 161 */     if (((Boolean)antiBot.entityIDValue.getValue()).booleanValue() && (entity.getEntityId() >= 1000000000 || entity.getEntityId() <= -1)) {
/* 162 */       return true;
/*     */     }
/* 164 */     if (((Boolean)antiBot.derpValue.getValue()).booleanValue() && (entity.rotationPitch > 90.0F || entity.rotationPitch < -90.0F)) {
/* 165 */       return true;
/*     */     }
/* 167 */     if (((Boolean)antiBot.wasInvisibleValue.getValue()).booleanValue() && antiBot.invisible.contains(Integer.valueOf(entity.getEntityId()))) {
/* 168 */       return true;
/*     */     }
/* 170 */     if (((Boolean)antiBot.armorValue.getValue()).booleanValue()) {
/* 171 */       EntityPlayer player = (EntityPlayer)entity;
/*     */       
/* 173 */       if (player.inventory.armorInventory[0] == null && player.inventory.armorInventory[1] == null && player.inventory.armorInventory[2] == null && player.inventory.armorInventory[3] == null)
/*     */       {
/* 175 */         return true;
/*     */       }
/*     */     } 
/* 178 */     if (((Boolean)antiBot.pingValue.getValue()).booleanValue()) {
/* 179 */       EntityPlayer player = (EntityPlayer)entity;
/*     */       
/* 181 */       if (mc.getNetHandler().getPlayerInfo(player.getUniqueID()).getResponseTime() == 0) {
/* 182 */         return true;
/*     */       }
/*     */     } 
/* 185 */     if (((Boolean)antiBot.needHitValue.getValue()).booleanValue() && !antiBot.hitted.contains(Integer.valueOf(entity.getEntityId()))) {
/* 186 */       return true;
/*     */     }
/* 188 */     if (((Boolean)antiBot.invalidGroundValue.getValue()).booleanValue() && ((Integer)antiBot.invalidGround.getOrDefault(Integer.valueOf(entity.getEntityId()), (V)Integer.valueOf(0))).intValue() >= 10) {
/* 189 */       return true;
/*     */     }
/* 191 */     if (((Boolean)antiBot.tabValue.getValue()).booleanValue()) {
/* 192 */       boolean equals = antiBot.tabModeValue.is("Equals");
/* 193 */       String targetName = ColorUtils.stripColor(entity.getDisplayName().getFormattedText());
/*     */       
/* 195 */       if (targetName != null) {
/* 196 */         for (NetworkPlayerInfo networkPlayerInfo : mc.getNetHandler().getPlayerInfoMap()) {
/* 197 */           String networkName = ColorUtils.stripColor(EntityUtils.getName(networkPlayerInfo));
/*     */           
/* 199 */           if (networkName == null) {
/*     */             continue;
/*     */           }
/* 202 */           if (equals ? targetName.equals(networkName) : targetName.contains(networkName)) {
/* 203 */             return false;
/*     */           }
/*     */         } 
/* 206 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 210 */     if (((Boolean)antiBot.duplicateInWorldValue.getValue()).booleanValue() && 
/* 211 */       mc.theWorld.loadedEntityList.stream()
/* 212 */       .filter(currEntity -> (currEntity instanceof EntityPlayer && currEntity.getDisplayName().equals(currEntity.getDisplayName())))
/*     */       
/* 214 */       .count() > 1L) {
/* 215 */       return true;
/*     */     }
/*     */     
/* 218 */     if (((Boolean)antiBot.duplicateInTabValue.getValue()).booleanValue() && 
/* 219 */       mc.getNetHandler().getPlayerInfoMap().stream()
/* 220 */       .filter(networkPlayer -> entity.getName().equals(ColorUtils.stripColor(EntityUtils.getName(networkPlayer))))
/* 221 */       .count() > 1L) {
/* 222 */       return true;
/*     */     }
/*     */     
/* 225 */     return (entity.getName().isEmpty() || entity.getName().equals(mc.thePlayer.getName()));
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\combat\advanced\AdvancedAntiBot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */