/*     */ package net.minecraft.util;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.ItemStack;
/*     */ 
/*     */ 
/*     */ public class CombatTracker
/*     */ {
/*  15 */   private final List<CombatEntry> combatEntries = Lists.newArrayList();
/*     */   
/*     */   private final EntityLivingBase fighter;
/*     */   
/*     */   private int field_94555_c;
/*     */   
/*     */   private int field_152775_d;
/*     */   private int field_152776_e;
/*     */   private boolean field_94552_d;
/*     */   private boolean field_94553_e;
/*     */   private String field_94551_f;
/*     */   
/*     */   public CombatTracker(EntityLivingBase fighterIn) {
/*  28 */     this.fighter = fighterIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_94545_a() {
/*  33 */     func_94542_g();
/*     */     
/*  35 */     if (this.fighter.isOnLadder()) {
/*     */       
/*  37 */       Block block = this.fighter.worldObj.getBlockState(new BlockPos(this.fighter.posX, (this.fighter.getEntityBoundingBox()).minY, this.fighter.posZ)).getBlock();
/*     */       
/*  39 */       if (block == Blocks.ladder)
/*     */       {
/*  41 */         this.field_94551_f = "ladder";
/*     */       }
/*  43 */       else if (block == Blocks.vine)
/*     */       {
/*  45 */         this.field_94551_f = "vines";
/*     */       }
/*     */     
/*  48 */     } else if (this.fighter.isInWater()) {
/*     */       
/*  50 */       this.field_94551_f = "water";
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void trackDamage(DamageSource damageSrc, float healthIn, float damageAmount) {
/*  59 */     reset();
/*  60 */     func_94545_a();
/*  61 */     CombatEntry combatentry = new CombatEntry(damageSrc, this.fighter.ticksExisted, healthIn, damageAmount, this.field_94551_f, this.fighter.fallDistance);
/*  62 */     this.combatEntries.add(combatentry);
/*  63 */     this.field_94555_c = this.fighter.ticksExisted;
/*  64 */     this.field_94553_e = true;
/*     */     
/*  66 */     if (combatentry.isLivingDamageSrc() && !this.field_94552_d && this.fighter.isEntityAlive()) {
/*     */       
/*  68 */       this.field_94552_d = true;
/*  69 */       this.field_152775_d = this.fighter.ticksExisted;
/*  70 */       this.field_152776_e = this.field_152775_d;
/*  71 */       this.fighter.sendEnterCombat();
/*     */     } 
/*     */   }
/*     */   
/*     */   public IChatComponent getDeathMessage() {
/*     */     IChatComponent ichatcomponent;
/*  77 */     if (this.combatEntries.isEmpty())
/*     */     {
/*  79 */       return new ChatComponentTranslation("death.attack.generic", new Object[] { this.fighter.getDisplayName() });
/*     */     }
/*     */ 
/*     */     
/*  83 */     CombatEntry combatentry = func_94544_f();
/*  84 */     CombatEntry combatentry1 = this.combatEntries.get(this.combatEntries.size() - 1);
/*  85 */     IChatComponent ichatcomponent1 = combatentry1.getDamageSrcDisplayName();
/*  86 */     Entity entity = combatentry1.getDamageSrc().getEntity();
/*     */ 
/*     */     
/*  89 */     if (combatentry != null && combatentry1.getDamageSrc() == DamageSource.fall) {
/*     */       
/*  91 */       IChatComponent ichatcomponent2 = combatentry.getDamageSrcDisplayName();
/*     */       
/*  93 */       if (combatentry.getDamageSrc() != DamageSource.fall && combatentry.getDamageSrc() != DamageSource.outOfWorld) {
/*     */         
/*  95 */         if (ichatcomponent2 != null && (ichatcomponent1 == null || !ichatcomponent2.equals(ichatcomponent1))) {
/*     */           
/*  97 */           Entity entity1 = combatentry.getDamageSrc().getEntity();
/*  98 */           ItemStack itemstack1 = (entity1 instanceof EntityLivingBase) ? ((EntityLivingBase)entity1).getHeldItem() : null;
/*     */           
/* 100 */           if (itemstack1 != null && itemstack1.hasDisplayName())
/*     */           {
/* 102 */             ichatcomponent = new ChatComponentTranslation("death.fell.assist.item", new Object[] { this.fighter.getDisplayName(), ichatcomponent2, itemstack1.getChatComponent() });
/*     */           }
/*     */           else
/*     */           {
/* 106 */             ichatcomponent = new ChatComponentTranslation("death.fell.assist", new Object[] { this.fighter.getDisplayName(), ichatcomponent2 });
/*     */           }
/*     */         
/* 109 */         } else if (ichatcomponent1 != null) {
/*     */           
/* 111 */           ItemStack itemstack = (entity instanceof EntityLivingBase) ? ((EntityLivingBase)entity).getHeldItem() : null;
/*     */           
/* 113 */           if (itemstack != null && itemstack.hasDisplayName())
/*     */           {
/* 115 */             ichatcomponent = new ChatComponentTranslation("death.fell.finish.item", new Object[] { this.fighter.getDisplayName(), ichatcomponent1, itemstack.getChatComponent() });
/*     */           }
/*     */           else
/*     */           {
/* 119 */             ichatcomponent = new ChatComponentTranslation("death.fell.finish", new Object[] { this.fighter.getDisplayName(), ichatcomponent1 });
/*     */           }
/*     */         
/*     */         } else {
/*     */           
/* 124 */           ichatcomponent = new ChatComponentTranslation("death.fell.killer", new Object[] { this.fighter.getDisplayName() });
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 129 */         ichatcomponent = new ChatComponentTranslation("death.fell.accident." + func_94548_b(combatentry), new Object[] { this.fighter.getDisplayName() });
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 134 */       ichatcomponent = combatentry1.getDamageSrc().getDeathMessage(this.fighter);
/*     */     } 
/*     */     
/* 137 */     return ichatcomponent;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityLivingBase func_94550_c() {
/* 143 */     EntityLivingBase entitylivingbase = null;
/* 144 */     EntityPlayer entityplayer = null;
/* 145 */     float f = 0.0F;
/* 146 */     float f1 = 0.0F;
/*     */     
/* 148 */     for (CombatEntry combatentry : this.combatEntries) {
/*     */       
/* 150 */       if (combatentry.getDamageSrc().getEntity() instanceof EntityPlayer && (entityplayer == null || combatentry.func_94563_c() > f1)) {
/*     */         
/* 152 */         f1 = combatentry.func_94563_c();
/* 153 */         entityplayer = (EntityPlayer)combatentry.getDamageSrc().getEntity();
/*     */       } 
/*     */       
/* 156 */       if (combatentry.getDamageSrc().getEntity() instanceof EntityLivingBase && (entitylivingbase == null || combatentry.func_94563_c() > f)) {
/*     */         
/* 158 */         f = combatentry.func_94563_c();
/* 159 */         entitylivingbase = (EntityLivingBase)combatentry.getDamageSrc().getEntity();
/*     */       } 
/*     */     } 
/*     */     
/* 163 */     if (entityplayer != null && f1 >= f / 3.0F)
/*     */     {
/* 165 */       return (EntityLivingBase)entityplayer;
/*     */     }
/*     */ 
/*     */     
/* 169 */     return entitylivingbase;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private CombatEntry func_94544_f() {
/* 175 */     CombatEntry combatentry = null;
/* 176 */     CombatEntry combatentry1 = null;
/* 177 */     int i = 0;
/* 178 */     float f = 0.0F;
/*     */     
/* 180 */     for (int j = 0; j < this.combatEntries.size(); j++) {
/*     */       
/* 182 */       CombatEntry combatentry2 = this.combatEntries.get(j);
/* 183 */       CombatEntry combatentry3 = (j > 0) ? this.combatEntries.get(j - 1) : null;
/*     */       
/* 185 */       if ((combatentry2.getDamageSrc() == DamageSource.fall || combatentry2.getDamageSrc() == DamageSource.outOfWorld) && combatentry2.getDamageAmount() > 0.0F && (combatentry == null || combatentry2.getDamageAmount() > f)) {
/*     */         
/* 187 */         if (j > 0) {
/*     */           
/* 189 */           combatentry = combatentry3;
/*     */         }
/*     */         else {
/*     */           
/* 193 */           combatentry = combatentry2;
/*     */         } 
/*     */         
/* 196 */         f = combatentry2.getDamageAmount();
/*     */       } 
/*     */       
/* 199 */       if (combatentry2.func_94562_g() != null && (combatentry1 == null || combatentry2.func_94563_c() > i))
/*     */       {
/* 201 */         combatentry1 = combatentry2;
/*     */       }
/*     */     } 
/*     */     
/* 205 */     if (f > 5.0F && combatentry != null)
/*     */     {
/* 207 */       return combatentry;
/*     */     }
/* 209 */     if (i > 5 && combatentry1 != null)
/*     */     {
/* 211 */       return combatentry1;
/*     */     }
/*     */ 
/*     */     
/* 215 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private String func_94548_b(CombatEntry p_94548_1_) {
/* 221 */     return (p_94548_1_.func_94562_g() == null) ? "generic" : p_94548_1_.func_94562_g();
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_180134_f() {
/* 226 */     return this.field_94552_d ? (this.fighter.ticksExisted - this.field_152775_d) : (this.field_152776_e - this.field_152775_d);
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_94542_g() {
/* 231 */     this.field_94551_f = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reset() {
/* 239 */     int i = this.field_94552_d ? 300 : 100;
/*     */     
/* 241 */     if (this.field_94553_e && (!this.fighter.isEntityAlive() || this.fighter.ticksExisted - this.field_94555_c > i)) {
/*     */       
/* 243 */       boolean flag = this.field_94552_d;
/* 244 */       this.field_94553_e = false;
/* 245 */       this.field_94552_d = false;
/* 246 */       this.field_152776_e = this.fighter.ticksExisted;
/*     */       
/* 248 */       if (flag)
/*     */       {
/* 250 */         this.fighter.sendEndCombat();
/*     */       }
/*     */       
/* 253 */       this.combatEntries.clear();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityLivingBase getFighter() {
/* 262 */     return this.fighter;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraf\\util\CombatTracker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */