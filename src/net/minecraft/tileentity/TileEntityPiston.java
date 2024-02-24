/*     */ package net.minecraft.tileentity;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.ITickable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TileEntityPiston
/*     */   extends TileEntity
/*     */   implements ITickable
/*     */ {
/*     */   private IBlockState pistonState;
/*     */   private EnumFacing pistonFacing;
/*     */   private boolean extending;
/*     */   private boolean shouldHeadBeRendered;
/*     */   private float progress;
/*     */   private float lastProgress;
/*  27 */   private final List<Entity> field_174933_k = Lists.newArrayList();
/*     */ 
/*     */ 
/*     */   
/*     */   public TileEntityPiston() {}
/*     */ 
/*     */   
/*     */   public TileEntityPiston(IBlockState pistonStateIn, EnumFacing pistonFacingIn, boolean extendingIn, boolean shouldHeadBeRenderedIn) {
/*  35 */     this.pistonState = pistonStateIn;
/*  36 */     this.pistonFacing = pistonFacingIn;
/*  37 */     this.extending = extendingIn;
/*  38 */     this.shouldHeadBeRendered = shouldHeadBeRenderedIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public IBlockState getPistonState() {
/*  43 */     return this.pistonState;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBlockMetadata() {
/*  48 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isExtending() {
/*  56 */     return this.extending;
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumFacing getFacing() {
/*  61 */     return this.pistonFacing;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldPistonHeadBeRendered() {
/*  66 */     return this.shouldHeadBeRendered;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getProgress(float ticks) {
/*  75 */     if (ticks > 1.0F)
/*     */     {
/*  77 */       ticks = 1.0F;
/*     */     }
/*     */     
/*  80 */     return this.lastProgress + (this.progress - this.lastProgress) * ticks;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getOffsetX(float ticks) {
/*  85 */     return this.extending ? ((getProgress(ticks) - 1.0F) * this.pistonFacing.getFrontOffsetX()) : ((1.0F - getProgress(ticks)) * this.pistonFacing.getFrontOffsetX());
/*     */   }
/*     */ 
/*     */   
/*     */   public float getOffsetY(float ticks) {
/*  90 */     return this.extending ? ((getProgress(ticks) - 1.0F) * this.pistonFacing.getFrontOffsetY()) : ((1.0F - getProgress(ticks)) * this.pistonFacing.getFrontOffsetY());
/*     */   }
/*     */ 
/*     */   
/*     */   public float getOffsetZ(float ticks) {
/*  95 */     return this.extending ? ((getProgress(ticks) - 1.0F) * this.pistonFacing.getFrontOffsetZ()) : ((1.0F - getProgress(ticks)) * this.pistonFacing.getFrontOffsetZ());
/*     */   }
/*     */ 
/*     */   
/*     */   private void launchWithSlimeBlock(float p_145863_1_, float p_145863_2_) {
/* 100 */     if (this.extending) {
/*     */       
/* 102 */       p_145863_1_ = 1.0F - p_145863_1_;
/*     */     }
/*     */     else {
/*     */       
/* 106 */       p_145863_1_--;
/*     */     } 
/*     */     
/* 109 */     AxisAlignedBB axisalignedbb = Blocks.piston_extension.getBoundingBox(this.worldObj, this.pos, this.pistonState, p_145863_1_, this.pistonFacing);
/*     */     
/* 111 */     if (axisalignedbb != null) {
/*     */       
/* 113 */       List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity((Entity)null, axisalignedbb);
/*     */       
/* 115 */       if (!list.isEmpty()) {
/*     */         
/* 117 */         this.field_174933_k.addAll(list);
/*     */         
/* 119 */         for (Entity entity : this.field_174933_k) {
/*     */           
/* 121 */           if (this.pistonState.getBlock() == Blocks.slime_block && this.extending) {
/*     */             
/* 123 */             switch (this.pistonFacing.getAxis()) {
/*     */               
/*     */               case X:
/* 126 */                 entity.motionX = this.pistonFacing.getFrontOffsetX();
/*     */                 continue;
/*     */               
/*     */               case Y:
/* 130 */                 entity.motionY = this.pistonFacing.getFrontOffsetY();
/*     */                 continue;
/*     */               
/*     */               case Z:
/* 134 */                 entity.motionZ = this.pistonFacing.getFrontOffsetZ();
/*     */                 continue;
/*     */             } 
/*     */             continue;
/*     */           } 
/* 139 */           entity.moveEntity((p_145863_2_ * this.pistonFacing.getFrontOffsetX()), (p_145863_2_ * this.pistonFacing.getFrontOffsetY()), (p_145863_2_ * this.pistonFacing.getFrontOffsetZ()));
/*     */         } 
/*     */ 
/*     */         
/* 143 */         this.field_174933_k.clear();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearPistonTileEntity() {
/* 153 */     if (this.lastProgress < 1.0F && this.worldObj != null) {
/*     */       
/* 155 */       this.lastProgress = this.progress = 1.0F;
/* 156 */       this.worldObj.removeTileEntity(this.pos);
/* 157 */       invalidate();
/*     */       
/* 159 */       if (this.worldObj.getBlockState(this.pos).getBlock() == Blocks.piston_extension) {
/*     */         
/* 161 */         this.worldObj.setBlockState(this.pos, this.pistonState, 3);
/* 162 */         this.worldObj.notifyBlockOfStateChange(this.pos, this.pistonState.getBlock());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update() {
/* 172 */     this.lastProgress = this.progress;
/*     */     
/* 174 */     if (this.lastProgress >= 1.0F) {
/*     */       
/* 176 */       launchWithSlimeBlock(1.0F, 0.25F);
/* 177 */       this.worldObj.removeTileEntity(this.pos);
/* 178 */       invalidate();
/*     */       
/* 180 */       if (this.worldObj.getBlockState(this.pos).getBlock() == Blocks.piston_extension)
/*     */       {
/* 182 */         this.worldObj.setBlockState(this.pos, this.pistonState, 3);
/* 183 */         this.worldObj.notifyBlockOfStateChange(this.pos, this.pistonState.getBlock());
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 188 */       this.progress += 0.5F;
/*     */       
/* 190 */       if (this.progress >= 1.0F)
/*     */       {
/* 192 */         this.progress = 1.0F;
/*     */       }
/*     */       
/* 195 */       if (this.extending)
/*     */       {
/* 197 */         launchWithSlimeBlock(this.progress, this.progress - this.lastProgress + 0.0625F);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void readFromNBT(NBTTagCompound compound) {
/* 204 */     super.readFromNBT(compound);
/* 205 */     this.pistonState = Block.getBlockById(compound.getInteger("blockId")).getStateFromMeta(compound.getInteger("blockData"));
/* 206 */     this.pistonFacing = EnumFacing.getFront(compound.getInteger("facing"));
/* 207 */     this.lastProgress = this.progress = compound.getFloat("progress");
/* 208 */     this.extending = compound.getBoolean("extending");
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeToNBT(NBTTagCompound compound) {
/* 213 */     super.writeToNBT(compound);
/* 214 */     compound.setInteger("blockId", Block.getIdFromBlock(this.pistonState.getBlock()));
/* 215 */     compound.setInteger("blockData", this.pistonState.getBlock().getMetaFromState(this.pistonState));
/* 216 */     compound.setInteger("facing", this.pistonFacing.getIndex());
/* 217 */     compound.setFloat("progress", this.lastProgress);
/* 218 */     compound.setBoolean("extending", this.extending);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\tileentity\TileEntityPiston.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */