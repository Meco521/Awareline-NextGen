/*     */ package awareline.main.mod.implement.combat;
/*     */ 
/*     */ import awareline.main.Client;
/*     */ import awareline.main.event.EventHandler;
/*     */ import awareline.main.event.events.world.updateEvents.EventPostUpdate;
/*     */ import awareline.main.mod.Module;
/*     */ import awareline.main.mod.ModuleType;
/*     */ import awareline.main.mod.implement.combat.advanced.sucks.antibots.BotManager;
/*     */ import awareline.main.mod.values.Numbers;
/*     */ import awareline.main.mod.values.Option;
/*     */ import awareline.main.mod.values.Value;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.CopyOnWriteArrayList;
/*     */ import java.util.stream.Collectors;
/*     */ import net.minecraft.client.network.NetworkPlayerInfo;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import org.lwjgl.input.Mouse;
/*     */ 
/*     */ public class AntiBot
/*     */   extends Module {
/*  28 */   private static final Option<Boolean> hypixelBypass = new Option("HypixelBypass", Boolean.valueOf(true));
/*  29 */   private static final Option<Boolean> brokenID = new Option("BrokenID", Boolean.valueOf(false));
/*  30 */   private static final Option<Boolean> tab = new Option("Tab", Boolean.valueOf(false));
/*  31 */   private static final Option<Boolean> advanced = new Option("Advanced", Boolean.valueOf(false));
/*  32 */   private static final Option<Boolean> living = new Option("Living", Boolean.valueOf(false));
/*  33 */   private static final Option<Boolean> mineplex = new Option("Mineplex", Boolean.valueOf(false));
/*  34 */   private static final Option<Boolean> mineland = new Option("MineLand", Boolean.valueOf(false));
/*  35 */   private static final Option<Boolean> midClick = new Option("MidClick", Boolean.valueOf(false));
/*  36 */   private static final Option<Boolean> NPC = new Option("NPC", Boolean.valueOf(false));
/*  37 */   private static final Numbers<Double> livingTicksValue = new Numbers("LivingTicks", Double.valueOf(80.0D), Double.valueOf(0.0D), Double.valueOf(100.0D), Double.valueOf(10.0D));
/*  38 */   private static final ArrayList<Integer> ground = new ArrayList<>();
/*     */   public static AntiBot getInstance;
/*     */   
/*     */   public AntiBot() {
/*  42 */     super("AntiBot", new String[] { "ab" }, ModuleType.Combat);
/*  43 */     addSettings(new Value[] { (Value)hypixelBypass, (Value)midClick, (Value)brokenID, (Value)tab, (Value)NPC, (Value)advanced, (Value)mineplex, (Value)livingTicksValue });
/*  44 */     getInstance = this;
/*     */   }
/*     */   private boolean down;
/*     */   
/*     */   public void onEnable() {
/*  49 */     super.onEnable();
/*  50 */     ground.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisable() {
/*  55 */     super.onDisable();
/*  56 */     Client.instance.getBotManager().clear();
/*  57 */     ground.clear();
/*     */   }
/*     */ 
/*     */   
/*  61 */   private static final List invalid = new CopyOnWriteArrayList();
/*     */   
/*     */   @EventHandler
/*     */   public void onUpdate(EventPostUpdate event) {
/*  65 */     if (mc.thePlayer.ticksExisted <= 1) {
/*  66 */       ground.clear();
/*     */     }
/*  68 */     if (((Boolean)mineland.get()).booleanValue() && 
/*  69 */       !mc.isSingleplayer())
/*     */     {
/*     */       
/*  72 */       if (!mc.theWorld.getLoadedEntityList().isEmpty()) {
/*  73 */         Iterator<Entity> var2 = mc.theWorld.getLoadedEntityList().iterator();
/*     */         
/*  75 */         while (var2.hasNext()) {
/*  76 */           Entity ent = var2.next();
/*  77 */           if (ent instanceof EntityPlayer) {
/*  78 */             if (!invalid.contains(ent) && mc.thePlayer.getDistanceToEntity(ent) > 20.0F) {
/*  79 */               invalid.add(ent);
/*     */             }
/*     */             
/*  82 */             if (ent != mc.thePlayer && !invalid.contains(ent) && mc.thePlayer.getDistanceToEntity(ent) < 10.0F) {
/*  83 */               mc.theWorld.removeEntity(ent);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*  90 */     if (((Boolean)midClick.get()).booleanValue())
/*  91 */       if (Mouse.isButtonDown(2))
/*  92 */       { if (this.down)
/*  93 */           return;  this.down = true;
/*     */         
/*  95 */         if (mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY) {
/*  96 */           BotManager botManager = Client.instance.getBotManager();
/*  97 */           Entity entity = mc.objectMouseOver.entityHit;
/*     */           
/*  99 */           if (botManager.contains(entity)) {
/* 100 */             Client.instance.getBotManager().remove(entity);
/* 101 */             noti("Remove midclick type bot - " + entity.getName(), true);
/* 102 */             msg("Remove midclick type bot - " + entity.getName());
/*     */           } else {
/* 104 */             Client.instance.getBotManager().add(entity);
/* 105 */             noti("Add midclick type bot - " + entity.getName(), true);
/* 106 */             msg("Add midclick type bot - " + entity.getName());
/*     */           } 
/*     */         }  }
/* 109 */       else { this.down = false; }
/*     */        
/* 111 */     if (((Boolean)hypixelBypass.get()).booleanValue()) {
/* 112 */       for (EntityPlayer entity : mc.theWorld.playerEntities) {
/* 113 */         removeHypixelBot((EntityLivingBase)entity);
/*     */       }
/* 115 */       ground.addAll((Collection<? extends Integer>)getLivingPlayers().stream().filter(entityPlayer -> (entityPlayer.onGround && !ground.contains(Integer.valueOf(entityPlayer.getEntityId())))).map(Entity::getEntityId).collect(Collectors.toList()));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private ArrayList<EntityPlayer> getLivingPlayers() {
/* 121 */     return (ArrayList)Arrays.asList(mc.theWorld.loadedEntityList.stream().filter(entity -> entity instanceof EntityPlayer).filter(entity -> (entity != mc.thePlayer)).map(entity -> (EntityPlayer)entity).toArray(x$0 -> new EntityPlayer[x$0]));
/*     */   }
/*     */   
/*     */   private boolean inTab(EntityLivingBase entity) {
/* 125 */     for (NetworkPlayerInfo item : mc.getNetHandler().getPlayerInfoMap()) {
/* 126 */       if (item == null || item.getGameProfile() == null || !item.getGameProfile().getName().contains(entity.getName()))
/*     */         continue; 
/* 128 */       return true;
/*     */     } 
/* 130 */     return false;
/*     */   }
/*     */   
/*     */   private boolean isMineplexNPC(EntityLivingBase entity) {
/* 134 */     String custom = entity.getCustomNameTag();
/* 135 */     if (entity instanceof EntityPlayer && !(entity instanceof net.minecraft.client.entity.EntityPlayerSP)) {
/* 136 */       return (mc.thePlayer.ticksExisted > 40 && custom.isEmpty());
/*     */     }
/* 138 */     return false;
/*     */   }
/*     */   
/*     */   private boolean isHypixelNPC(EntityLivingBase entity) {
/* 142 */     String formatted = entity.getDisplayName().getFormattedText();
/* 143 */     if ((formatted.isEmpty() || formatted.charAt(0) != '§') && formatted.endsWith("§r")) {
/* 144 */       return true;
/*     */     }
/* 146 */     if (ground.contains(Integer.valueOf(entity.getEntityId()))) {
/* 147 */       return true;
/*     */     }
/* 149 */     return formatted.contains("§8[NPC]");
/*     */   }
/*     */   
/*     */   private void removeHypixelBot(EntityLivingBase entity) {
/* 153 */     if (entity instanceof net.minecraft.entity.boss.EntityWither && entity.isInvisible()) {
/*     */       return;
/*     */     }
/* 156 */     if (!inTab(entity) && !isHypixelNPC(entity) && entity.isEntityAlive() && 
/* 157 */       entity != mc.thePlayer) {
/* 158 */       mc.theWorld.removeEntity((Entity)entity);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isBot(EntityLivingBase entity) {
/* 165 */     if (getInstance.isEnabled() && entity != mc.thePlayer) {
/* 166 */       if ((((Boolean)living.get()).booleanValue() || ((Boolean)advanced.get()).booleanValue()) && entity.ticksExisted > ((Double)livingTicksValue.getValue()).intValue()) {
/* 167 */         return true;
/*     */       }
/* 169 */       if (((Boolean)midClick.get()).booleanValue() && Client.instance.getBotManager().contains(entity)) {
/* 170 */         return true;
/*     */       }
/* 172 */       if (((Boolean)advanced.get()).booleanValue() && !ground.contains(Integer.valueOf(entity.getEntityId()))) {
/* 173 */         return true;
/*     */       }
/* 175 */       if (((Boolean)brokenID.get()).booleanValue() && entity.getEntityId() > 1000000) {
/* 176 */         return true;
/*     */       }
/* 178 */       if (((Boolean)tab.get()).booleanValue() && !getInstance.inTab(entity)) {
/* 179 */         return true;
/*     */       }
/* 181 */       if (((Boolean)hypixelBypass.get()).booleanValue() && getInstance.isHypixelNPC(entity)) {
/* 182 */         return true;
/*     */       }
/* 184 */       if (((Boolean)mineplex.get()).booleanValue() && getInstance.isMineplexNPC(entity)) {
/* 185 */         return true;
/*     */       }
/*     */     } 
/* 188 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\combat\AntiBot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */