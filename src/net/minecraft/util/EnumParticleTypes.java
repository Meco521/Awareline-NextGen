/*     */ package net.minecraft.util;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ public enum EnumParticleTypes
/*     */ {
/*  11 */   EXPLOSION_NORMAL("explode", 0, true),
/*  12 */   EXPLOSION_LARGE("largeexplode", 1, true),
/*  13 */   EXPLOSION_HUGE("hugeexplosion", 2, true),
/*  14 */   FIREWORKS_SPARK("fireworksSpark", 3, false),
/*  15 */   WATER_BUBBLE("bubble", 4, false),
/*  16 */   WATER_SPLASH("splash", 5, false),
/*  17 */   WATER_WAKE("wake", 6, false),
/*  18 */   SUSPENDED("suspended", 7, false),
/*  19 */   SUSPENDED_DEPTH("depthsuspend", 8, false),
/*  20 */   CRIT("crit", 9, false),
/*  21 */   CRIT_MAGIC("magicCrit", 10, false),
/*  22 */   SMOKE_NORMAL("smoke", 11, false),
/*  23 */   SMOKE_LARGE("largesmoke", 12, false),
/*  24 */   SPELL("spell", 13, false),
/*  25 */   SPELL_INSTANT("instantSpell", 14, false),
/*  26 */   SPELL_MOB("mobSpell", 15, false),
/*  27 */   SPELL_MOB_AMBIENT("mobSpellAmbient", 16, false),
/*  28 */   SPELL_WITCH("witchMagic", 17, false),
/*  29 */   DRIP_WATER("dripWater", 18, false),
/*  30 */   DRIP_LAVA("dripLava", 19, false),
/*  31 */   VILLAGER_ANGRY("angryVillager", 20, false),
/*  32 */   VILLAGER_HAPPY("happyVillager", 21, false),
/*  33 */   TOWN_AURA("townaura", 22, false),
/*  34 */   NOTE("note", 23, false),
/*  35 */   PORTAL("portal", 24, false),
/*  36 */   ENCHANTMENT_TABLE("enchantmenttable", 25, false),
/*  37 */   FLAME("flame", 26, false),
/*  38 */   LAVA("lava", 27, false),
/*  39 */   FOOTSTEP("footstep", 28, false),
/*  40 */   CLOUD("cloud", 29, false),
/*  41 */   REDSTONE("reddust", 30, false),
/*  42 */   SNOWBALL("snowballpoof", 31, false),
/*  43 */   SNOW_SHOVEL("snowshovel", 32, false),
/*  44 */   SLIME("slime", 33, false),
/*  45 */   HEART("heart", 34, false),
/*  46 */   BARRIER("barrier", 35, false),
/*  47 */   ITEM_CRACK("iconcrack_", 36, false, 2),
/*  48 */   BLOCK_CRACK("blockcrack_", 37, false, 1),
/*  49 */   BLOCK_DUST("blockdust_", 38, false, 1),
/*  50 */   WATER_DROP("droplet", 39, false),
/*  51 */   ITEM_TAKE("take", 40, false),
/*  52 */   MOB_APPEARANCE("mobappearance", 41, true);
/*     */   private final String particleName;
/*     */   private final int particleID;
/*     */   private final boolean shouldIgnoreRange;
/*     */   
/*     */   static {
/*  58 */     PARTICLES = Maps.newHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 113 */     List<String> list = Lists.newArrayList();
/*     */     
/* 115 */     for (EnumParticleTypes enumparticletypes : values()) {
/*     */       
/* 117 */       PARTICLES.put(Integer.valueOf(enumparticletypes.particleID), enumparticletypes);
/*     */       
/* 119 */       if (enumparticletypes.particleName.isEmpty() || enumparticletypes.particleName.charAt(enumparticletypes.particleName.length() - 1) != '_')
/*     */       {
/* 121 */         list.add(enumparticletypes.particleName);
/*     */       }
/*     */     } 
/*     */     
/* 125 */     PARTICLE_NAMES = list.<String>toArray(new String[0]);
/*     */   }
/*     */   
/*     */   private final int argumentCount;
/*     */   private static final Map<Integer, EnumParticleTypes> PARTICLES;
/*     */   private static final String[] PARTICLE_NAMES;
/*     */   
/*     */   EnumParticleTypes(String particleNameIn, int particleIDIn, boolean p_i46011_5_, int argumentCountIn) {
/*     */     this.particleName = particleNameIn;
/*     */     this.particleID = particleIDIn;
/*     */     this.shouldIgnoreRange = p_i46011_5_;
/*     */     this.argumentCount = argumentCountIn;
/*     */   }
/*     */   
/*     */   public static String[] getParticleNames() {
/*     */     return PARTICLE_NAMES;
/*     */   }
/*     */   
/*     */   public String getParticleName() {
/*     */     return this.particleName;
/*     */   }
/*     */   
/*     */   public int getParticleID() {
/*     */     return this.particleID;
/*     */   }
/*     */   
/*     */   public int getArgumentCount() {
/*     */     return this.argumentCount;
/*     */   }
/*     */   
/*     */   public boolean getShouldIgnoreRange() {
/*     */     return this.shouldIgnoreRange;
/*     */   }
/*     */   
/*     */   public boolean hasArguments() {
/*     */     return (this.argumentCount > 0);
/*     */   }
/*     */   
/*     */   public static EnumParticleTypes getParticleFromId(int particleId) {
/*     */     return PARTICLES.get(Integer.valueOf(particleId));
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraf\\util\EnumParticleTypes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */