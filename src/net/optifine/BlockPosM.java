/*     */ package net.optifine;
/*     */ 
/*     */ import com.google.common.collect.AbstractIterator;
/*     */ import java.util.Iterator;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.Vec3i;
/*     */ 
/*     */ 
/*     */ public class BlockPosM
/*     */   extends BlockPos
/*     */ {
/*     */   private int mx;
/*     */   private int my;
/*     */   private int mz;
/*     */   private final int level;
/*     */   private BlockPosM[] facings;
/*     */   private boolean needsUpdate;
/*     */   
/*     */   public BlockPosM(int x, int y, int z) {
/*  22 */     this(x, y, z, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPosM(double xIn, double yIn, double zIn) {
/*  27 */     this(MathHelper.floor_double(xIn), MathHelper.floor_double(yIn), MathHelper.floor_double(zIn));
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPosM(int x, int y, int z, int level) {
/*  32 */     super(0, 0, 0);
/*  33 */     this.mx = x;
/*  34 */     this.my = y;
/*  35 */     this.mz = z;
/*  36 */     this.level = level;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getX() {
/*  44 */     return this.mx;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getY() {
/*  52 */     return this.my;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getZ() {
/*  60 */     return this.mz;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setXyz(int x, int y, int z) {
/*  65 */     this.mx = x;
/*  66 */     this.my = y;
/*  67 */     this.mz = z;
/*  68 */     this.needsUpdate = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setXyz(double xIn, double yIn, double zIn) {
/*  73 */     setXyz(MathHelper.floor_double(xIn), MathHelper.floor_double(yIn), MathHelper.floor_double(zIn));
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPosM set(Vec3i vec) {
/*  78 */     setXyz(vec.getX(), vec.getY(), vec.getZ());
/*  79 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPosM set(int xIn, int yIn, int zIn) {
/*  84 */     setXyz(xIn, yIn, zIn);
/*  85 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos offsetMutable(EnumFacing facing) {
/*  90 */     return offset(facing);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos offset(EnumFacing facing) {
/*  98 */     if (this.level <= 0)
/*     */     {
/* 100 */       return super.offset(facing, 1);
/*     */     }
/*     */ 
/*     */     
/* 104 */     if (this.facings == null)
/*     */     {
/* 106 */       this.facings = new BlockPosM[EnumFacing.VALUES.length];
/*     */     }
/*     */     
/* 109 */     if (this.needsUpdate)
/*     */     {
/* 111 */       update();
/*     */     }
/*     */     
/* 114 */     int i = facing.getIndex();
/* 115 */     BlockPosM blockposm = this.facings[i];
/*     */     
/* 117 */     if (blockposm == null) {
/*     */       
/* 119 */       int j = this.mx + facing.getFrontOffsetX();
/* 120 */       int k = this.my + facing.getFrontOffsetY();
/* 121 */       int l = this.mz + facing.getFrontOffsetZ();
/* 122 */       blockposm = new BlockPosM(j, k, l, this.level - 1);
/* 123 */       this.facings[i] = blockposm;
/*     */     } 
/*     */     
/* 126 */     return blockposm;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos offset(EnumFacing facing, int n) {
/* 135 */     return (n == 1) ? offset(facing) : super.offset(facing, n);
/*     */   }
/*     */ 
/*     */   
/*     */   private void update() {
/* 140 */     for (int i = 0; i < 6; i++) {
/*     */       
/* 142 */       BlockPosM blockposm = this.facings[i];
/*     */       
/* 144 */       if (blockposm != null) {
/*     */         
/* 146 */         EnumFacing enumfacing = EnumFacing.VALUES[i];
/* 147 */         int j = this.mx + enumfacing.getFrontOffsetX();
/* 148 */         int k = this.my + enumfacing.getFrontOffsetY();
/* 149 */         int l = this.mz + enumfacing.getFrontOffsetZ();
/* 150 */         blockposm.setXyz(j, k, l);
/*     */       } 
/*     */     } 
/*     */     
/* 154 */     this.needsUpdate = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos toImmutable() {
/* 159 */     return new BlockPos(this.mx, this.my, this.mz);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Iterable getAllInBoxMutable(BlockPos from, BlockPos to) {
/* 164 */     final BlockPos blockpos = new BlockPos(Math.min(from.getX(), to.getX()), Math.min(from.getY(), to.getY()), Math.min(from.getZ(), to.getZ()));
/* 165 */     final BlockPos blockpos1 = new BlockPos(Math.max(from.getX(), to.getX()), Math.max(from.getY(), to.getY()), Math.max(from.getZ(), to.getZ()));
/* 166 */     return new Iterable()
/*     */       {
/*     */         public Iterator iterator()
/*     */         {
/* 170 */           return (Iterator)new AbstractIterator()
/*     */             {
/* 172 */               private BlockPosM theBlockPosM = null;
/*     */               
/*     */               protected BlockPosM computeNext0() {
/* 175 */                 if (this.theBlockPosM == null) {
/*     */                   
/* 177 */                   this.theBlockPosM = new BlockPosM(blockpos.getX(), blockpos.getY(), blockpos.getZ(), 3);
/* 178 */                   return this.theBlockPosM;
/*     */                 } 
/* 180 */                 if (this.theBlockPosM.equals(blockpos1))
/*     */                 {
/* 182 */                   return (BlockPosM)endOfData();
/*     */                 }
/*     */ 
/*     */                 
/* 186 */                 int i = this.theBlockPosM.getX();
/* 187 */                 int j = this.theBlockPosM.getY();
/* 188 */                 int k = this.theBlockPosM.getZ();
/*     */                 
/* 190 */                 if (i < blockpos1.getX()) {
/*     */                   
/* 192 */                   i++;
/*     */                 }
/* 194 */                 else if (j < blockpos1.getY()) {
/*     */                   
/* 196 */                   i = blockpos.getX();
/* 197 */                   j++;
/*     */                 }
/* 199 */                 else if (k < blockpos1.getZ()) {
/*     */                   
/* 201 */                   i = blockpos.getX();
/* 202 */                   j = blockpos.getY();
/* 203 */                   k++;
/*     */                 } 
/*     */                 
/* 206 */                 this.theBlockPosM.setXyz(i, j, k);
/* 207 */                 return this.theBlockPosM;
/*     */               }
/*     */ 
/*     */               
/*     */               protected Object computeNext() {
/* 212 */                 return computeNext0();
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\BlockPosM.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */