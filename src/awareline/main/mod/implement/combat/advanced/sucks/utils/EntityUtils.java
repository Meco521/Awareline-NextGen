/*     */ package awareline.main.mod.implement.combat.advanced.sucks.utils;
/*     */ 
/*     */ import awareline.main.Client;
/*     */ import awareline.main.mod.implement.combat.AntiBot;
/*     */ import awareline.main.mod.implement.combat.KillAura;
/*     */ import awareline.main.mod.implement.combat.TPAura;
/*     */ import awareline.main.mod.implement.combat.advanced.AdvancedAntiBot;
/*     */ import awareline.main.mod.implement.combat.advanced.AdvancedAura;
/*     */ import awareline.main.mod.implement.misc.AutoL;
/*     */ import awareline.main.mod.implement.misc.Teams;
/*     */ import awareline.main.mod.implement.player.anti.AntiAim;
/*     */ import awareline.main.ui.font.fontmanager.color.ColorUtils;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.network.NetworkPlayerInfo;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.scoreboard.ScorePlayerTeam;
/*     */ import net.minecraft.scoreboard.Team;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class EntityUtils
/*     */ {
/*  28 */   public static final Minecraft mc = Minecraft.getMinecraft();
/*     */   
/*  30 */   public static final AdvancedAura customAura = AdvancedAura.getInstance;
/*  31 */   public static final KillAura killAura = KillAura.getInstance;
/*  32 */   public static final AntiAim antiAim = AntiAim.getInstance;
/*  33 */   public static final TPAura tpAura = TPAura.getInstance;
/*     */   
/*  35 */   public static boolean targetInvisible = ((Boolean)customAura.invisibleValue.get()).booleanValue();
/*  36 */   public static boolean targetPlayer = ((Boolean)customAura.playerValue.get()).booleanValue();
/*  37 */   public static boolean targetMobs = ((Boolean)customAura.mobsValue.get()).booleanValue();
/*  38 */   public static boolean targetAnimals = ((Boolean)customAura.animalsValue.get()).booleanValue();
/*  39 */   public static boolean targetDead = ((Boolean)customAura.deadValue.get()).booleanValue();
/*     */   
/*     */   public static boolean isSelectedForAura(Entity entity, boolean canAttackCheck) {
/*  42 */     targetInvisible = ((Boolean)killAura.invisibleValue.get()).booleanValue();
/*  43 */     targetPlayer = ((Boolean)killAura.playerValue.get()).booleanValue();
/*  44 */     targetMobs = ((Boolean)killAura.mobsValue.get()).booleanValue();
/*  45 */     targetAnimals = ((Boolean)killAura.animalsValue.get()).booleanValue();
/*  46 */     targetDead = ((Boolean)killAura.deadValue.get()).booleanValue();
/*  47 */     if (mc.thePlayer.getDistanceToEntity(entity) > ((Double)killAura.range.getValue()).doubleValue()) {
/*  48 */       return false;
/*     */     }
/*  50 */     if (Math.abs(RotationUtils.getYawDifference(mc.thePlayer.rotationYaw, entity.posX, entity.posY, entity.posZ)) > ((Double)killAura.fovCheck.getValue()).floatValue()) {
/*  51 */       return false;
/*     */     }
/*  53 */     if (!mc.thePlayer.canEntityBeSeen(entity) && mc.thePlayer.getDistanceToEntity(entity) > ((Double)killAura.wallRange.getValue()).doubleValue()) {
/*  54 */       return false;
/*     */     }
/*  56 */     EntityLivingBase e2 = (EntityLivingBase)entity;
/*  57 */     if (e2.isDead || e2.getHealth() <= 0.0F || e2 == mc.thePlayer) {
/*  58 */       if (killAura.attacked.contains(e2)) {
/*  59 */         if (AutoL.getInstance.isEnabled()) {
/*  60 */           mc.thePlayer.sendChatMessage(AutoL.getInstance.getAutoLMessage(killAura.target.getName()));
/*     */         }
/*  62 */         killAura.killed++;
/*  63 */         killAura.attacked.remove(e2);
/*     */       } 
/*  65 */       return false;
/*     */     } 
/*  67 */     if ((targetDead || entity.isEntityAlive()) && entity != mc.thePlayer && (
/*  68 */       targetInvisible || !entity.isInvisible())) {
/*  69 */       if (targetPlayer && entity instanceof EntityPlayer) {
/*  70 */         EntityPlayer entityPlayer = (EntityPlayer)entity;
/*     */         
/*  72 */         if (canAttackCheck) {
/*  73 */           if (AdvancedAntiBot.isServerBot((Entity)entityPlayer)) {
/*  74 */             return false;
/*     */           }
/*  76 */           if (AntiBot.isBot((EntityLivingBase)entityPlayer)) {
/*  77 */             return false;
/*     */           }
/*  79 */           if (entityPlayer.isSpectator()) {
/*  80 */             return false;
/*     */           }
/*  82 */           Teams teams = Teams.getInstance;
/*  83 */           return (!teams.isEnabled() || !teams.isOnSameTeam((Entity)entityPlayer));
/*     */         } 
/*     */         
/*  86 */         return true;
/*     */       } 
/*     */       
/*  89 */       return ((targetMobs && isMob(entity)) || (targetAnimals && isAnimal(entity)));
/*     */     } 
/*     */ 
/*     */     
/*  93 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean isSelectedForAntiAim(Entity entity, boolean canAttackCheck) {
/*  97 */     targetInvisible = ((Boolean)antiAim.invisibleValue.get()).booleanValue();
/*  98 */     targetPlayer = ((Boolean)antiAim.playerValue.get()).booleanValue();
/*  99 */     targetMobs = ((Boolean)antiAim.mobsValue.get()).booleanValue();
/* 100 */     targetAnimals = ((Boolean)antiAim.animalsValue.get()).booleanValue();
/* 101 */     targetDead = ((Boolean)antiAim.deadValue.get()).booleanValue();
/* 102 */     if (mc.thePlayer.getDistanceToEntity(entity) > ((Double)antiAim.range.getValue()).doubleValue()) {
/* 103 */       return false;
/*     */     }
/* 105 */     if (entity instanceof EntityLivingBase && (targetDead || entity.isEntityAlive()) && entity != mc.thePlayer && (
/* 106 */       targetInvisible || !entity.isInvisible())) {
/* 107 */       if (targetPlayer && entity instanceof EntityPlayer) {
/* 108 */         EntityPlayer entityPlayer = (EntityPlayer)entity;
/*     */         
/* 110 */         if (canAttackCheck) {
/* 111 */           if (AdvancedAntiBot.isServerBot((Entity)entityPlayer)) {
/* 112 */             return false;
/*     */           }
/* 114 */           if (AntiBot.isBot((EntityLivingBase)entityPlayer)) {
/* 115 */             return false;
/*     */           }
/* 117 */           if (entityPlayer.isSpectator()) {
/* 118 */             return false;
/*     */           }
/* 120 */           Teams teams = Teams.getInstance;
/* 121 */           return (!teams.isEnabled() || !teams.isOnSameTeam((Entity)entityPlayer));
/*     */         } 
/*     */         
/* 124 */         return true;
/*     */       } 
/*     */       
/* 127 */       return ((targetMobs && isMob(entity)) || (targetAnimals && isAnimal(entity)));
/*     */     } 
/*     */ 
/*     */     
/* 131 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean isSelectedForAuraBlockTarget(Entity entity, boolean canAttackCheck) {
/* 135 */     targetInvisible = ((Boolean)killAura.invisibleValue.get()).booleanValue();
/* 136 */     targetPlayer = ((Boolean)killAura.playerValue.get()).booleanValue();
/* 137 */     targetMobs = ((Boolean)killAura.mobsValue.get()).booleanValue();
/* 138 */     targetAnimals = ((Boolean)killAura.animalsValue.get()).booleanValue();
/* 139 */     targetDead = ((Boolean)killAura.deadValue.get()).booleanValue();
/*     */     
/* 141 */     if (mc.thePlayer.getDistanceToEntity(entity) > ((Double)killAura.range.getValue()).doubleValue() + ((Double)killAura.blockRange.getValue()).doubleValue()) {
/* 142 */       return false;
/*     */     }
/* 144 */     if (Math.abs(RotationUtils.getYawDifference(mc.thePlayer.rotationYaw, entity.posX, entity.posY, entity.posZ)) > ((Double)killAura.fovCheck.getValue()).floatValue()) {
/* 145 */       return false;
/*     */     }
/* 147 */     if (!mc.thePlayer.canEntityBeSeen(entity) && mc.thePlayer.getDistanceToEntity(entity) > ((Double)killAura.wallRange.getValue()).doubleValue() + ((Double)killAura.blockRange.getValue()).doubleValue()) {
/* 148 */       return false;
/*     */     }
/* 150 */     if (entity instanceof EntityLivingBase && (targetDead || entity.isEntityAlive()) && entity != mc.thePlayer && (
/* 151 */       targetInvisible || !entity.isInvisible())) {
/* 152 */       if (targetPlayer && entity instanceof EntityPlayer) {
/* 153 */         EntityPlayer entityPlayer = (EntityPlayer)entity;
/*     */         
/* 155 */         if (canAttackCheck) {
/* 156 */           if (AdvancedAntiBot.isServerBot((Entity)entityPlayer)) {
/* 157 */             return false;
/*     */           }
/* 159 */           if (AntiBot.isBot((EntityLivingBase)entityPlayer)) {
/* 160 */             return false;
/*     */           }
/* 162 */           if (entityPlayer.isSpectator()) {
/* 163 */             return false;
/*     */           }
/* 165 */           Teams teams = Teams.getInstance;
/* 166 */           return (!teams.isEnabled() || !teams.isOnSameTeam((Entity)entityPlayer));
/*     */         } 
/*     */         
/* 169 */         return true;
/*     */       } 
/*     */       
/* 172 */       return ((targetMobs && isMob(entity)) || (targetAnimals && isAnimal(entity)));
/*     */     } 
/*     */ 
/*     */     
/* 176 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean isSelectedForTPAura(Entity entity, boolean canAttackCheck) {
/* 180 */     targetInvisible = ((Boolean)tpAura.invisibleValue.get()).booleanValue();
/* 181 */     targetPlayer = ((Boolean)tpAura.playerValue.get()).booleanValue();
/* 182 */     targetMobs = ((Boolean)tpAura.mobsValue.get()).booleanValue();
/* 183 */     targetAnimals = ((Boolean)tpAura.animalsValue.get()).booleanValue();
/* 184 */     targetDead = ((Boolean)tpAura.deadValue.get()).booleanValue();
/*     */     
/* 186 */     if (entity instanceof EntityLivingBase && (targetDead || entity.isEntityAlive()) && entity != mc.thePlayer && (
/* 187 */       targetInvisible || !entity.isInvisible())) {
/* 188 */       if (targetPlayer && entity instanceof EntityPlayer) {
/* 189 */         EntityPlayer entityPlayer = (EntityPlayer)entity;
/*     */         
/* 191 */         if (canAttackCheck) {
/* 192 */           if (AdvancedAntiBot.isServerBot((Entity)entityPlayer)) {
/* 193 */             return false;
/*     */           }
/* 195 */           if (AntiBot.isBot((EntityLivingBase)entityPlayer)) {
/* 196 */             return false;
/*     */           }
/* 198 */           if (entityPlayer.isSpectator()) {
/* 199 */             return false;
/*     */           }
/* 201 */           Teams teams = Teams.getInstance;
/* 202 */           return (!teams.isEnabled() || !teams.isOnSameTeam((Entity)entityPlayer));
/*     */         } 
/*     */         
/* 205 */         return true;
/*     */       } 
/*     */       
/* 208 */       return ((targetMobs && isMob(entity)) || (targetAnimals && isAnimal(entity)));
/*     */     } 
/*     */ 
/*     */     
/* 212 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean isSelected(Entity entity, boolean canAttackCheck) {
/* 216 */     targetInvisible = ((Boolean)customAura.invisibleValue.get()).booleanValue();
/* 217 */     targetPlayer = ((Boolean)customAura.playerValue.get()).booleanValue();
/* 218 */     targetMobs = ((Boolean)customAura.mobsValue.get()).booleanValue();
/* 219 */     targetAnimals = ((Boolean)customAura.animalsValue.get()).booleanValue();
/* 220 */     targetDead = ((Boolean)customAura.deadValue.get()).booleanValue();
/* 221 */     if (mc.thePlayer.getDistanceToEntity(entity) > ((Double)tpAura.range.get()).doubleValue()) {
/* 222 */       return false;
/*     */     }
/* 224 */     if (entity instanceof EntityLivingBase && (targetDead || entity.isEntityAlive()) && entity != mc.thePlayer && (
/* 225 */       targetInvisible || !entity.isInvisible())) {
/* 226 */       if (targetPlayer && entity instanceof EntityPlayer) {
/* 227 */         EntityPlayer entityPlayer = (EntityPlayer)entity;
/*     */         
/* 229 */         if (canAttackCheck) {
/* 230 */           if (AdvancedAntiBot.isServerBot((Entity)entityPlayer)) {
/* 231 */             return false;
/*     */           }
/* 233 */           if (AntiBot.isBot((EntityLivingBase)entityPlayer)) {
/* 234 */             return false;
/*     */           }
/* 236 */           if (entityPlayer.isSpectator()) {
/* 237 */             return false;
/*     */           }
/* 239 */           Teams teams = Teams.getInstance;
/* 240 */           return (!teams.isEnabled() || !teams.isOnSameTeam((Entity)entityPlayer));
/*     */         } 
/*     */         
/* 243 */         return true;
/*     */       } 
/*     */       
/* 246 */       return ((targetMobs && isMob(entity)) || (targetAnimals && isAnimal(entity)));
/*     */     } 
/*     */ 
/*     */     
/* 250 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean isFriend(Entity entity) {
/* 254 */     return (entity instanceof EntityPlayer && entity.getName() != null && Client.instance.friendManager
/* 255 */       .isFriend(ColorUtils.stripColor(entity.getName())));
/*     */   }
/*     */   
/*     */   public static boolean isAnimal(Entity entity) {
/* 259 */     return (entity instanceof net.minecraft.entity.passive.EntityAnimal || entity instanceof net.minecraft.entity.passive.EntitySquid || entity instanceof net.minecraft.entity.monster.EntityGolem || entity instanceof net.minecraft.entity.passive.EntityBat);
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isMob(Entity entity) {
/* 264 */     return (entity instanceof net.minecraft.entity.monster.EntityMob || entity instanceof net.minecraft.entity.passive.EntityVillager || entity instanceof net.minecraft.entity.monster.EntitySlime || entity instanceof net.minecraft.entity.monster.EntityGhast || entity instanceof net.minecraft.entity.boss.EntityDragon);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getName(NetworkPlayerInfo networkPlayerInfoIn) {
/* 269 */     return (networkPlayerInfoIn.getDisplayName() != null) ? networkPlayerInfoIn.getDisplayName().getFormattedText() : 
/* 270 */       ScorePlayerTeam.formatPlayerName((Team)networkPlayerInfoIn.getPlayerTeam(), networkPlayerInfoIn.getGameProfile().getName());
/*     */   }
/*     */   
/*     */   public static int getPing(EntityPlayer entityPlayer) {
/* 274 */     if (entityPlayer == null) {
/* 275 */       return 0;
/*     */     }
/* 277 */     NetworkPlayerInfo networkPlayerInfo = mc.getNetHandler().getPlayerInfo(entityPlayer.getUniqueID());
/*     */     
/* 279 */     return (networkPlayerInfo == null) ? 0 : networkPlayerInfo.getResponseTime();
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\combat\advanced\suck\\utils\EntityUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */