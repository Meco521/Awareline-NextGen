/*     */ package net.minecraft.entity.effect;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.EnumDifficulty;
/*     */ import net.minecraft.world.World;
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
/*     */ public class EntityLightningBolt
/*     */   extends EntityWeatherEffect
/*     */ {
/*     */   private int lightningState;
/*     */   public long boltVertex;
/*     */   private int boltLivingTime;
/*     */   
/*     */   public EntityLightningBolt(World worldIn, double posX, double posY, double posZ) {
/*  33 */     super(worldIn);
/*  34 */     setLocationAndAngles(posX, posY, posZ, 0.0F, 0.0F);
/*  35 */     this.lightningState = 2;
/*  36 */     this.boltVertex = this.rand.nextLong();
/*  37 */     this.boltLivingTime = this.rand.nextInt(3) + 1;
/*  38 */     BlockPos blockpos = new BlockPos(this);
/*     */     
/*  40 */     if (!worldIn.isRemote && worldIn.getGameRules().getBoolean("doFireTick") && (worldIn.getDifficulty() == EnumDifficulty.NORMAL || worldIn.getDifficulty() == EnumDifficulty.HARD) && worldIn.isAreaLoaded(blockpos, 10)) {
/*     */       
/*  42 */       if (worldIn.getBlockState(blockpos).getBlock().getMaterial() == Material.air && Blocks.fire.canPlaceBlockAt(worldIn, blockpos))
/*     */       {
/*  44 */         worldIn.setBlockState(blockpos, Blocks.fire.getDefaultState());
/*     */       }
/*     */       
/*  47 */       for (int i = 0; i < 4; i++) {
/*     */         
/*  49 */         BlockPos blockpos1 = blockpos.add(this.rand.nextInt(3) - 1, this.rand.nextInt(3) - 1, this.rand.nextInt(3) - 1);
/*     */         
/*  51 */         if (worldIn.getBlockState(blockpos1).getBlock().getMaterial() == Material.air && Blocks.fire.canPlaceBlockAt(worldIn, blockpos1))
/*     */         {
/*  53 */           worldIn.setBlockState(blockpos1, Blocks.fire.getDefaultState());
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  64 */     super.onUpdate();
/*     */     
/*  66 */     if (this.lightningState == 2) {
/*     */       
/*  68 */       this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "ambient.weather.thunder", 10000.0F, 0.8F + this.rand.nextFloat() * 0.2F);
/*  69 */       this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "random.explode", 2.0F, 0.5F + this.rand.nextFloat() * 0.2F);
/*     */     } 
/*     */     
/*  72 */     this.lightningState--;
/*     */     
/*  74 */     if (this.lightningState < 0)
/*     */     {
/*  76 */       if (this.boltLivingTime == 0) {
/*     */         
/*  78 */         setDead();
/*     */       }
/*  80 */       else if (this.lightningState < -this.rand.nextInt(10)) {
/*     */         
/*  82 */         this.boltLivingTime--;
/*  83 */         this.lightningState = 1;
/*  84 */         this.boltVertex = this.rand.nextLong();
/*  85 */         BlockPos blockpos = new BlockPos(this);
/*     */         
/*  87 */         if (!this.worldObj.isRemote && this.worldObj.getGameRules().getBoolean("doFireTick") && this.worldObj.isAreaLoaded(blockpos, 10) && this.worldObj.getBlockState(blockpos).getBlock().getMaterial() == Material.air && Blocks.fire.canPlaceBlockAt(this.worldObj, blockpos))
/*     */         {
/*  89 */           this.worldObj.setBlockState(blockpos, Blocks.fire.getDefaultState());
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/*  94 */     if (this.lightningState >= 0)
/*     */     {
/*  96 */       if (this.worldObj.isRemote) {
/*     */         
/*  98 */         this.worldObj.setLastLightningBolt(2);
/*     */       }
/*     */       else {
/*     */         
/* 102 */         double d0 = 3.0D;
/* 103 */         List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, new AxisAlignedBB(this.posX - d0, this.posY - d0, this.posZ - d0, this.posX + d0, this.posY + 6.0D + d0, this.posZ + d0));
/*     */         
/* 105 */         for (int i = 0; i < list.size(); i++) {
/*     */           
/* 107 */           Entity entity = list.get(i);
/* 108 */           entity.onStruckByLightning(this);
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   protected void entityInit() {}
/*     */   
/*     */   protected void readEntityFromNBT(NBTTagCompound tagCompund) {}
/*     */   
/*     */   protected void writeEntityToNBT(NBTTagCompound tagCompound) {}
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\entity\effect\EntityLightningBolt.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */