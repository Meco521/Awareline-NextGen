/*     */ package net.minecraft.util;
/*     */ 
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.projectile.EntityArrow;
/*     */ import net.minecraft.entity.projectile.EntityFireball;
/*     */ import net.minecraft.world.Explosion;
/*     */ 
/*     */ public class DamageSource
/*     */ {
/*  12 */   public static DamageSource inFire = (new DamageSource("inFire")).setFireDamage();
/*  13 */   public static DamageSource lightningBolt = new DamageSource("lightningBolt");
/*  14 */   public static DamageSource onFire = (new DamageSource("onFire")).setDamageBypassesArmor().setFireDamage();
/*  15 */   public static DamageSource lava = (new DamageSource("lava")).setFireDamage();
/*  16 */   public static DamageSource inWall = (new DamageSource("inWall")).setDamageBypassesArmor();
/*  17 */   public static DamageSource drown = (new DamageSource("drown")).setDamageBypassesArmor();
/*  18 */   public static DamageSource starve = (new DamageSource("starve")).setDamageBypassesArmor().setDamageIsAbsolute();
/*  19 */   public static DamageSource cactus = new DamageSource("cactus");
/*  20 */   public static DamageSource fall = (new DamageSource("fall")).setDamageBypassesArmor();
/*  21 */   public static DamageSource outOfWorld = (new DamageSource("outOfWorld")).setDamageBypassesArmor().setDamageAllowedInCreativeMode();
/*  22 */   public static DamageSource generic = (new DamageSource("generic")).setDamageBypassesArmor();
/*  23 */   public static DamageSource magic = (new DamageSource("magic")).setDamageBypassesArmor().setMagicDamage();
/*  24 */   public static DamageSource wither = (new DamageSource("wither")).setDamageBypassesArmor();
/*  25 */   public static DamageSource anvil = new DamageSource("anvil");
/*  26 */   public static DamageSource fallingBlock = new DamageSource("fallingBlock");
/*     */ 
/*     */   
/*     */   private boolean isUnblockable;
/*     */ 
/*     */   
/*     */   private boolean isDamageAllowedInCreativeMode;
/*     */   
/*     */   private boolean damageIsAbsolute;
/*     */   
/*  36 */   private float hungerDamage = 0.3F;
/*     */ 
/*     */   
/*     */   private boolean fireDamage;
/*     */ 
/*     */   
/*     */   private boolean projectile;
/*     */ 
/*     */   
/*     */   private boolean difficultyScaled;
/*     */ 
/*     */   
/*     */   private boolean magicDamage;
/*     */   
/*     */   private boolean explosion;
/*     */   
/*     */   public String damageType;
/*     */ 
/*     */   
/*     */   public static DamageSource causeMobDamage(EntityLivingBase mob) {
/*  56 */     return new EntityDamageSource("mob", (Entity)mob);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DamageSource causePlayerDamage(EntityPlayer player) {
/*  64 */     return new EntityDamageSource("player", (Entity)player);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DamageSource causeArrowDamage(EntityArrow arrow, Entity indirectEntityIn) {
/*  74 */     return (new EntityDamageSourceIndirect("arrow", (Entity)arrow, indirectEntityIn)).setProjectile();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DamageSource causeFireballDamage(EntityFireball fireball, Entity indirectEntityIn) {
/*  84 */     return (indirectEntityIn == null) ? (new EntityDamageSourceIndirect("onFire", (Entity)fireball, (Entity)fireball)).setFireDamage().setProjectile() : (new EntityDamageSourceIndirect("fireball", (Entity)fireball, indirectEntityIn)).setFireDamage().setProjectile();
/*     */   }
/*     */ 
/*     */   
/*     */   public static DamageSource causeThrownDamage(Entity source, Entity indirectEntityIn) {
/*  89 */     return (new EntityDamageSourceIndirect("thrown", source, indirectEntityIn)).setProjectile();
/*     */   }
/*     */ 
/*     */   
/*     */   public static DamageSource causeIndirectMagicDamage(Entity source, Entity indirectEntityIn) {
/*  94 */     return (new EntityDamageSourceIndirect("indirectMagic", source, indirectEntityIn)).setDamageBypassesArmor().setMagicDamage();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DamageSource causeThornsDamage(Entity source) {
/* 104 */     return (new EntityDamageSource("thorns", source)).setIsThornsDamage().setMagicDamage();
/*     */   }
/*     */ 
/*     */   
/*     */   public static DamageSource setExplosionSource(Explosion explosionIn) {
/* 109 */     return (explosionIn != null && explosionIn.getExplosivePlacedBy() != null) ? (new EntityDamageSource("explosion.player", (Entity)explosionIn.getExplosivePlacedBy())).setDifficultyScaled().setExplosion() : (new DamageSource("explosion")).setDifficultyScaled().setExplosion();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isProjectile() {
/* 117 */     return this.projectile;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DamageSource setProjectile() {
/* 125 */     this.projectile = true;
/* 126 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isExplosion() {
/* 131 */     return this.explosion;
/*     */   }
/*     */ 
/*     */   
/*     */   public DamageSource setExplosion() {
/* 136 */     this.explosion = true;
/* 137 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isUnblockable() {
/* 142 */     return this.isUnblockable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getHungerDamage() {
/* 150 */     return this.hungerDamage;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canHarmInCreative() {
/* 155 */     return this.isDamageAllowedInCreativeMode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDamageAbsolute() {
/* 163 */     return this.damageIsAbsolute;
/*     */   }
/*     */ 
/*     */   
/*     */   protected DamageSource(String damageTypeIn) {
/* 168 */     this.damageType = damageTypeIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public Entity getSourceOfDamage() {
/* 173 */     return getEntity();
/*     */   }
/*     */ 
/*     */   
/*     */   public Entity getEntity() {
/* 178 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected DamageSource setDamageBypassesArmor() {
/* 183 */     this.isUnblockable = true;
/* 184 */     this.hungerDamage = 0.0F;
/* 185 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected DamageSource setDamageAllowedInCreativeMode() {
/* 190 */     this.isDamageAllowedInCreativeMode = true;
/* 191 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected DamageSource setDamageIsAbsolute() {
/* 200 */     this.damageIsAbsolute = true;
/* 201 */     this.hungerDamage = 0.0F;
/* 202 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected DamageSource setFireDamage() {
/* 210 */     this.fireDamage = true;
/* 211 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IChatComponent getDeathMessage(EntityLivingBase entityLivingBaseIn) {
/* 221 */     EntityLivingBase entitylivingbase = entityLivingBaseIn.getAttackingEntity();
/* 222 */     String s = "death.attack." + this.damageType;
/* 223 */     String s1 = s + ".player";
/* 224 */     return (entitylivingbase != null && StatCollector.canTranslate(s1)) ? new ChatComponentTranslation(s1, new Object[] { entityLivingBaseIn.getDisplayName(), entitylivingbase.getDisplayName() }) : new ChatComponentTranslation(s, new Object[] { entityLivingBaseIn.getDisplayName() });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFireDamage() {
/* 232 */     return this.fireDamage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDamageType() {
/* 240 */     return this.damageType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DamageSource setDifficultyScaled() {
/* 248 */     this.difficultyScaled = true;
/* 249 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDifficultyScaled() {
/* 257 */     return this.difficultyScaled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isMagicDamage() {
/* 265 */     return this.magicDamage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DamageSource setMagicDamage() {
/* 273 */     this.magicDamage = true;
/* 274 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCreativePlayer() {
/* 279 */     Entity entity = getEntity();
/* 280 */     return (entity instanceof EntityPlayer && ((EntityPlayer)entity).capabilities.isCreativeMode);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraf\\util\DamageSource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */