/*     */ package net.minecraft.entity.ai;
/*     */ 
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockBed;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.EntityCreature;
/*     */ import net.minecraft.entity.passive.EntityOcelot;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityChest;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityAIOcelotSit extends EntityAIMoveToBlock {
/*     */   private final EntityOcelot ocelot;
/*     */   
/*     */   public EntityAIOcelotSit(EntityOcelot ocelotIn, double p_i45315_2_) {
/*  19 */     super((EntityCreature)ocelotIn, p_i45315_2_, 8);
/*  20 */     this.ocelot = ocelotIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldExecute() {
/*  28 */     return (this.ocelot.isTamed() && !this.ocelot.isSitting() && super.shouldExecute());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean continueExecuting() {
/*  36 */     return super.continueExecuting();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startExecuting() {
/*  44 */     super.startExecuting();
/*  45 */     this.ocelot.getAISit().setSitting(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetTask() {
/*  53 */     super.resetTask();
/*  54 */     this.ocelot.setSitting(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateTask() {
/*  62 */     super.updateTask();
/*  63 */     this.ocelot.getAISit().setSitting(false);
/*     */     
/*  65 */     if (!getIsAboveDestination()) {
/*     */       
/*  67 */       this.ocelot.setSitting(false);
/*     */     }
/*  69 */     else if (!this.ocelot.isSitting()) {
/*     */       
/*  71 */       this.ocelot.setSitting(true);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean shouldMoveTo(World worldIn, BlockPos pos) {
/*  80 */     if (!worldIn.isAirBlock(pos.up()))
/*     */     {
/*  82 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  86 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/*  87 */     Block block = iblockstate.getBlock();
/*     */     
/*  89 */     if (block == Blocks.chest) {
/*     */       
/*  91 */       TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */       
/*  93 */       if (tileentity instanceof TileEntityChest && ((TileEntityChest)tileentity).numPlayersUsing < 1)
/*     */       {
/*  95 */         return true;
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 100 */       if (block == Blocks.lit_furnace)
/*     */       {
/* 102 */         return true;
/*     */       }
/*     */       
/* 105 */       if (block == Blocks.bed && iblockstate.getValue((IProperty)BlockBed.PART) != BlockBed.EnumPartType.HEAD)
/*     */       {
/* 107 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 111 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\entity\ai\EntityAIOcelotSit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */