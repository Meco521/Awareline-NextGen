/*     */ package net.minecraft.util;
/*     */ 
/*     */ import com.google.common.collect.AbstractIterator;
/*     */ import java.util.Iterator;
/*     */ import net.minecraft.entity.Entity;
/*     */ 
/*     */ 
/*     */ public class BlockPos
/*     */   extends Vec3i
/*     */ {
/*  11 */   public static final BlockPos ORIGIN = new BlockPos(0, 0, 0);
/*  12 */   private static final int NUM_X_BITS = 1 + MathHelper.calculateLogBaseTwo(MathHelper.roundUpToPowerOfTwo(30000000));
/*  13 */   private static final int NUM_Z_BITS = NUM_X_BITS;
/*  14 */   private static final int NUM_Y_BITS = 64 - NUM_X_BITS - NUM_Z_BITS;
/*  15 */   private static final int Y_SHIFT = NUM_Z_BITS;
/*  16 */   private static final int X_SHIFT = Y_SHIFT + NUM_Y_BITS;
/*  17 */   private static final long X_MASK = (1L << NUM_X_BITS) - 1L;
/*  18 */   private static final long Y_MASK = (1L << NUM_Y_BITS) - 1L;
/*  19 */   private static final long Z_MASK = (1L << NUM_Z_BITS) - 1L;
/*     */ 
/*     */   
/*     */   public BlockPos(int x, int y, int z) {
/*  23 */     super(x, y, z);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos(double x, double y, double z) {
/*  28 */     super(x, y, z);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos(Entity source) {
/*  33 */     this(source.posX, source.posY, source.posZ);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos(Vec3 source) {
/*  38 */     this(source.xCoord, source.yCoord, source.zCoord);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos(Vec3i source) {
/*  43 */     this(source.getX(), source.getY(), source.getZ());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos add(double x, double y, double z) {
/*  53 */     return (x == 0.0D && y == 0.0D && z == 0.0D) ? this : new BlockPos(getX() + x, getY() + y, getZ() + z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos add(int x, int y, int z) {
/*  61 */     return (x == 0 && y == 0 && z == 0) ? this : new BlockPos(getX() + x, getY() + y, getZ() + z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos add(Vec3i vec) {
/*  69 */     return (vec.getX() == 0 && vec.getY() == 0 && vec.getZ() == 0) ? this : new BlockPos(getX() + vec.getX(), getY() + vec.getY(), getZ() + vec.getZ());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos subtract(Vec3i vec) {
/*  77 */     return (vec.getX() == 0 && vec.getY() == 0 && vec.getZ() == 0) ? this : new BlockPos(getX() - vec.getX(), getY() - vec.getY(), getZ() - vec.getZ());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos up() {
/*  85 */     return up(1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos up(int n) {
/*  93 */     return offset(EnumFacing.UP, n);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos down() {
/* 101 */     return down(1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos down(int n) {
/* 109 */     return offset(EnumFacing.DOWN, n);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos north() {
/* 117 */     return north(1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos north(int n) {
/* 125 */     return offset(EnumFacing.NORTH, n);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos south() {
/* 133 */     return south(1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos south(int n) {
/* 141 */     return offset(EnumFacing.SOUTH, n);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos west() {
/* 149 */     return west(1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos west(int n) {
/* 157 */     return offset(EnumFacing.WEST, n);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos east() {
/* 165 */     return east(1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos east(int n) {
/* 173 */     return offset(EnumFacing.EAST, n);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos offset(EnumFacing facing) {
/* 181 */     return offset(facing, 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos offset(EnumFacing facing, int n) {
/* 189 */     return (n == 0) ? this : new BlockPos(getX() + facing.getFrontOffsetX() * n, getY() + facing.getFrontOffsetY() * n, getZ() + facing.getFrontOffsetZ() * n);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos crossProduct(Vec3i vec) {
/* 197 */     return new BlockPos(getY() * vec.getZ() - getZ() * vec.getY(), getZ() * vec.getX() - getX() * vec.getZ(), getX() * vec.getY() - getY() * vec.getX());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long toLong() {
/* 205 */     return (getX() & X_MASK) << X_SHIFT | (getY() & Y_MASK) << Y_SHIFT | (getZ() & Z_MASK) << 0L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BlockPos fromLong(long serialized) {
/* 213 */     int i = (int)(serialized << 64 - X_SHIFT - NUM_X_BITS >> 64 - NUM_X_BITS);
/* 214 */     int j = (int)(serialized << 64 - Y_SHIFT - NUM_Y_BITS >> 64 - NUM_Y_BITS);
/* 215 */     int k = (int)(serialized << 64 - NUM_Z_BITS >> 64 - NUM_Z_BITS);
/* 216 */     return new BlockPos(i, j, k);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Iterable<BlockPos> getAllInBox(BlockPos from, BlockPos to) {
/* 221 */     final BlockPos blockpos = new BlockPos(Math.min(from.getX(), to.getX()), Math.min(from.getY(), to.getY()), Math.min(from.getZ(), to.getZ()));
/* 222 */     final BlockPos blockpos1 = new BlockPos(Math.max(from.getX(), to.getX()), Math.max(from.getY(), to.getY()), Math.max(from.getZ(), to.getZ()));
/* 223 */     return new Iterable<BlockPos>()
/*     */       {
/*     */         public Iterator<BlockPos> iterator()
/*     */         {
/* 227 */           return (Iterator<BlockPos>)new AbstractIterator<BlockPos>()
/*     */             {
/* 229 */               private BlockPos lastReturned = null;
/*     */               
/*     */               protected BlockPos computeNext() {
/* 232 */                 if (this.lastReturned == null) {
/*     */                   
/* 234 */                   this.lastReturned = blockpos;
/* 235 */                   return this.lastReturned;
/*     */                 } 
/* 237 */                 if (this.lastReturned.equals(blockpos1))
/*     */                 {
/* 239 */                   return (BlockPos)endOfData();
/*     */                 }
/*     */ 
/*     */                 
/* 243 */                 int i = this.lastReturned.getX();
/* 244 */                 int j = this.lastReturned.getY();
/* 245 */                 int k = this.lastReturned.getZ();
/*     */                 
/* 247 */                 if (i < blockpos1.getX()) {
/*     */                   
/* 249 */                   i++;
/*     */                 }
/* 251 */                 else if (j < blockpos1.getY()) {
/*     */                   
/* 253 */                   i = blockpos.getX();
/* 254 */                   j++;
/*     */                 }
/* 256 */                 else if (k < blockpos1.getZ()) {
/*     */                   
/* 258 */                   i = blockpos.getX();
/* 259 */                   j = blockpos.getY();
/* 260 */                   k++;
/*     */                 } 
/*     */                 
/* 263 */                 this.lastReturned = new BlockPos(i, j, k);
/* 264 */                 return this.lastReturned;
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Iterable<MutableBlockPos> getAllInBoxMutable(BlockPos from, BlockPos to) {
/* 274 */     final BlockPos blockpos = new BlockPos(Math.min(from.getX(), to.getX()), Math.min(from.getY(), to.getY()), Math.min(from.getZ(), to.getZ()));
/* 275 */     final BlockPos blockpos1 = new BlockPos(Math.max(from.getX(), to.getX()), Math.max(from.getY(), to.getY()), Math.max(from.getZ(), to.getZ()));
/* 276 */     return new Iterable<MutableBlockPos>()
/*     */       {
/*     */         public Iterator<BlockPos.MutableBlockPos> iterator()
/*     */         {
/* 280 */           return (Iterator<BlockPos.MutableBlockPos>)new AbstractIterator<BlockPos.MutableBlockPos>()
/*     */             {
/* 282 */               private BlockPos.MutableBlockPos theBlockPos = null;
/*     */               
/*     */               protected BlockPos.MutableBlockPos computeNext() {
/* 285 */                 if (this.theBlockPos == null) {
/*     */                   
/* 287 */                   this.theBlockPos = new BlockPos.MutableBlockPos(blockpos.getX(), blockpos.getY(), blockpos.getZ());
/* 288 */                   return this.theBlockPos;
/*     */                 } 
/* 290 */                 if (this.theBlockPos.equals(blockpos1))
/*     */                 {
/* 292 */                   return (BlockPos.MutableBlockPos)endOfData();
/*     */                 }
/*     */ 
/*     */                 
/* 296 */                 int i = this.theBlockPos.getX();
/* 297 */                 int j = this.theBlockPos.getY();
/* 298 */                 int k = this.theBlockPos.getZ();
/*     */                 
/* 300 */                 if (i < blockpos1.getX()) {
/*     */                   
/* 302 */                   i++;
/*     */                 }
/* 304 */                 else if (j < blockpos1.getY()) {
/*     */                   
/* 306 */                   i = blockpos.getX();
/* 307 */                   j++;
/*     */                 }
/* 309 */                 else if (k < blockpos1.getZ()) {
/*     */                   
/* 311 */                   i = blockpos.getX();
/* 312 */                   j = blockpos.getY();
/* 313 */                   k++;
/*     */                 } 
/*     */                 
/* 316 */                 this.theBlockPos.x = i;
/* 317 */                 this.theBlockPos.y = j;
/* 318 */                 this.theBlockPos.z = k;
/* 319 */                 return this.theBlockPos;
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class MutableBlockPos
/*     */     extends BlockPos
/*     */   {
/*     */     int x;
/*     */     
/*     */     int y;
/*     */     int z;
/*     */     
/*     */     public MutableBlockPos() {
/* 336 */       this(0, 0, 0);
/*     */     }
/*     */ 
/*     */     
/*     */     public MutableBlockPos(int x_, int y_, int z_) {
/* 341 */       super(0, 0, 0);
/* 342 */       this.x = x_;
/* 343 */       this.y = y_;
/* 344 */       this.z = z_;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getX() {
/* 349 */       return this.x;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getY() {
/* 354 */       return this.y;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getZ() {
/* 359 */       return this.z;
/*     */     }
/*     */ 
/*     */     
/*     */     public MutableBlockPos set(int xIn, int yIn, int zIn) {
/* 364 */       this.x = xIn;
/* 365 */       this.y = yIn;
/* 366 */       this.z = zIn;
/* 367 */       return this;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraf\\util\BlockPos.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */