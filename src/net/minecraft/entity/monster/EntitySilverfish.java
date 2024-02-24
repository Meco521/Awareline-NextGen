/*     */ package net.minecraft.entity.monster;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockSilverfish;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EnumCreatureAttribute;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAIAttackOnCollide;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.entity.ai.EntityAIHurtByTarget;
/*     */ import net.minecraft.entity.ai.EntityAISwimming;
/*     */ import net.minecraft.entity.ai.EntityAIWander;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntitySilverfish extends EntityMob {
/*     */   public EntitySilverfish(World worldIn) {
/*  26 */     super(worldIn);
/*  27 */     setSize(0.4F, 0.3F);
/*  28 */     this.tasks.addTask(1, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
/*  29 */     this.tasks.addTask(3, this.summonSilverfish = new AISummonSilverfish(this));
/*  30 */     this.tasks.addTask(4, (EntityAIBase)new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0D, false));
/*  31 */     this.tasks.addTask(5, (EntityAIBase)new AIHideInStone(this));
/*  32 */     this.targetTasks.addTask(1, (EntityAIBase)new EntityAIHurtByTarget(this, true, new Class[0]));
/*  33 */     this.targetTasks.addTask(2, (EntityAIBase)new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
/*     */   }
/*     */ 
/*     */   
/*     */   private final AISummonSilverfish summonSilverfish;
/*     */ 
/*     */   
/*     */   public double getYOffset() {
/*  41 */     return 0.2D;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getEyeHeight() {
/*  46 */     return 0.1F;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void applyEntityAttributes() {
/*  51 */     super.applyEntityAttributes();
/*  52 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0D);
/*  53 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
/*  54 */     getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(1.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canTriggerWalking() {
/*  63 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getLivingSound() {
/*  71 */     return "mob.silverfish.say";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getHurtSound() {
/*  79 */     return "mob.silverfish.hit";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getDeathSound() {
/*  87 */     return "mob.silverfish.kill";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean attackEntityFrom(DamageSource source, float amount) {
/*  95 */     if (isEntityInvulnerable(source))
/*     */     {
/*  97 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 101 */     if (source instanceof net.minecraft.util.EntityDamageSource || source == DamageSource.magic)
/*     */     {
/* 103 */       this.summonSilverfish.func_179462_f();
/*     */     }
/*     */     
/* 106 */     return super.attackEntityFrom(source, amount);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void playStepSound(BlockPos pos, Block blockIn) {
/* 112 */     playSound("mob.silverfish.step", 0.15F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Item getDropItem() {
/* 117 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 125 */     this.renderYawOffset = this.rotationYaw;
/* 126 */     super.onUpdate();
/*     */   }
/*     */ 
/*     */   
/*     */   public float getBlockPathWeight(BlockPos pos) {
/* 131 */     return (this.worldObj.getBlockState(pos.down()).getBlock() == Blocks.stone) ? 10.0F : super.getBlockPathWeight(pos);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isValidLightLevel() {
/* 139 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getCanSpawnHere() {
/* 147 */     if (super.getCanSpawnHere()) {
/*     */       
/* 149 */       EntityPlayer entityplayer = this.worldObj.getClosestPlayerToEntity((Entity)this, 5.0D);
/* 150 */       return (entityplayer == null);
/*     */     } 
/*     */ 
/*     */     
/* 154 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumCreatureAttribute getCreatureAttribute() {
/* 163 */     return EnumCreatureAttribute.ARTHROPOD;
/*     */   }
/*     */   
/*     */   static class AIHideInStone
/*     */     extends EntityAIWander
/*     */   {
/*     */     private final EntitySilverfish silverfish;
/*     */     private EnumFacing facing;
/*     */     private boolean field_179484_c;
/*     */     
/*     */     public AIHideInStone(EntitySilverfish silverfishIn) {
/* 174 */       super(silverfishIn, 1.0D, 10);
/* 175 */       this.silverfish = silverfishIn;
/* 176 */       setMutexBits(1);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean shouldExecute() {
/* 181 */       if (this.silverfish.getAttackTarget() != null)
/*     */       {
/* 183 */         return false;
/*     */       }
/* 185 */       if (!this.silverfish.getNavigator().noPath())
/*     */       {
/* 187 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 191 */       Random random = this.silverfish.getRNG();
/*     */       
/* 193 */       if (random.nextInt(10) == 0) {
/*     */         
/* 195 */         this.facing = EnumFacing.random(random);
/* 196 */         BlockPos blockpos = (new BlockPos(this.silverfish.posX, this.silverfish.posY + 0.5D, this.silverfish.posZ)).offset(this.facing);
/* 197 */         IBlockState iblockstate = this.silverfish.worldObj.getBlockState(blockpos);
/*     */         
/* 199 */         if (BlockSilverfish.canContainSilverfish(iblockstate)) {
/*     */           
/* 201 */           this.field_179484_c = true;
/* 202 */           return true;
/*     */         } 
/*     */       } 
/*     */       
/* 206 */       this.field_179484_c = false;
/* 207 */       return super.shouldExecute();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean continueExecuting() {
/* 213 */       return this.field_179484_c ? false : super.continueExecuting();
/*     */     }
/*     */ 
/*     */     
/*     */     public void startExecuting() {
/* 218 */       if (!this.field_179484_c) {
/*     */         
/* 220 */         super.startExecuting();
/*     */       }
/*     */       else {
/*     */         
/* 224 */         World world = this.silverfish.worldObj;
/* 225 */         BlockPos blockpos = (new BlockPos(this.silverfish.posX, this.silverfish.posY + 0.5D, this.silverfish.posZ)).offset(this.facing);
/* 226 */         IBlockState iblockstate = world.getBlockState(blockpos);
/*     */         
/* 228 */         if (BlockSilverfish.canContainSilverfish(iblockstate)) {
/*     */           
/* 230 */           world.setBlockState(blockpos, Blocks.monster_egg.getDefaultState().withProperty((IProperty)BlockSilverfish.VARIANT, (Comparable)BlockSilverfish.EnumType.forModelBlock(iblockstate)), 3);
/* 231 */           this.silverfish.spawnExplosionParticle();
/* 232 */           this.silverfish.setDead();
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   static class AISummonSilverfish
/*     */     extends EntityAIBase
/*     */   {
/*     */     private final EntitySilverfish silverfish;
/*     */     private int field_179463_b;
/*     */     
/*     */     public AISummonSilverfish(EntitySilverfish silverfishIn) {
/* 245 */       this.silverfish = silverfishIn;
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_179462_f() {
/* 250 */       if (this.field_179463_b == 0)
/*     */       {
/* 252 */         this.field_179463_b = 20;
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean shouldExecute() {
/* 258 */       return (this.field_179463_b > 0);
/*     */     }
/*     */ 
/*     */     
/*     */     public void updateTask() {
/* 263 */       this.field_179463_b--;
/*     */       
/* 265 */       if (this.field_179463_b <= 0) {
/*     */         
/* 267 */         World world = this.silverfish.worldObj;
/* 268 */         Random random = this.silverfish.getRNG();
/* 269 */         BlockPos blockpos = new BlockPos((Entity)this.silverfish);
/*     */         int i;
/* 271 */         for (i = 0; i <= 5 && i >= -5; i = (i <= 0) ? (1 - i) : -i) {
/*     */           int j;
/* 273 */           for (j = 0; j <= 10 && j >= -10; j = (j <= 0) ? (1 - j) : -j) {
/*     */             int k;
/* 275 */             for (k = 0; k <= 10 && k >= -10; k = (k <= 0) ? (1 - k) : -k) {
/*     */               
/* 277 */               BlockPos blockpos1 = blockpos.add(j, i, k);
/* 278 */               IBlockState iblockstate = world.getBlockState(blockpos1);
/*     */               
/* 280 */               if (iblockstate.getBlock() == Blocks.monster_egg) {
/*     */                 
/* 282 */                 if (world.getGameRules().getBoolean("mobGriefing")) {
/*     */                   
/* 284 */                   world.destroyBlock(blockpos1, true);
/*     */                 }
/*     */                 else {
/*     */                   
/* 288 */                   world.setBlockState(blockpos1, ((BlockSilverfish.EnumType)iblockstate.getValue((IProperty)BlockSilverfish.VARIANT)).getModelBlock(), 3);
/*     */                 } 
/*     */                 
/* 291 */                 if (random.nextBoolean())
/*     */                   return; 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\entity\monster\EntitySilverfish.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */