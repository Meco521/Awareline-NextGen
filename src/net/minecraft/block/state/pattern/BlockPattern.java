/*     */ package net.minecraft.block.state.pattern;
/*     */ 
/*     */ import com.google.common.base.Objects;
/*     */ import com.google.common.base.Predicate;
/*     */ import com.google.common.cache.CacheBuilder;
/*     */ import com.google.common.cache.LoadingCache;
/*     */ import net.minecraft.block.state.BlockWorldState;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.Vec3i;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ public class BlockPattern
/*     */ {
/*     */   private final Predicate<BlockWorldState>[][][] blockMatches;
/*     */   private final int fingerLength;
/*     */   private final int thumbLength;
/*     */   private final int palmLength;
/*     */   
/*     */   public BlockPattern(Predicate<BlockWorldState>[][][] predicatesIn) {
/*  22 */     this.blockMatches = predicatesIn;
/*  23 */     this.fingerLength = predicatesIn.length;
/*     */     
/*  25 */     if (this.fingerLength > 0) {
/*     */       
/*  27 */       this.thumbLength = (predicatesIn[0]).length;
/*     */       
/*  29 */       if (this.thumbLength > 0)
/*     */       {
/*  31 */         this.palmLength = (predicatesIn[0][0]).length;
/*     */       }
/*     */       else
/*     */       {
/*  35 */         this.palmLength = 0;
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/*  40 */       this.thumbLength = 0;
/*  41 */       this.palmLength = 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getThumbLength() {
/*  47 */     return this.thumbLength;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPalmLength() {
/*  52 */     return this.palmLength;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private PatternHelper checkPatternAt(BlockPos pos, EnumFacing finger, EnumFacing thumb, LoadingCache<BlockPos, BlockWorldState> lcache) {
/*  60 */     for (int i = 0; i < this.palmLength; i++) {
/*     */       
/*  62 */       for (int j = 0; j < this.thumbLength; j++) {
/*     */         
/*  64 */         for (int k = 0; k < this.fingerLength; k++) {
/*     */           
/*  66 */           if (!this.blockMatches[k][j][i].apply(lcache.getUnchecked(translateOffset(pos, finger, thumb, i, j, k))))
/*     */           {
/*  68 */             return null;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  74 */     return new PatternHelper(pos, finger, thumb, lcache, this.palmLength, this.thumbLength, this.fingerLength);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PatternHelper match(World worldIn, BlockPos pos) {
/*  83 */     LoadingCache<BlockPos, BlockWorldState> loadingcache = func_181627_a(worldIn, false);
/*  84 */     int i = Math.max(Math.max(this.palmLength, this.thumbLength), this.fingerLength);
/*     */     
/*  86 */     for (BlockPos blockpos : BlockPos.getAllInBox(pos, pos.add(i - 1, i - 1, i - 1))) {
/*     */       
/*  88 */       for (EnumFacing enumfacing : EnumFacing.values()) {
/*     */         
/*  90 */         for (EnumFacing enumfacing1 : EnumFacing.values()) {
/*     */           
/*  92 */           if (enumfacing1 != enumfacing && enumfacing1 != enumfacing.getOpposite()) {
/*     */             
/*  94 */             PatternHelper blockpattern$patternhelper = checkPatternAt(blockpos, enumfacing, enumfacing1, loadingcache);
/*     */             
/*  96 */             if (blockpattern$patternhelper != null)
/*     */             {
/*  98 */               return blockpattern$patternhelper;
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 105 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static LoadingCache<BlockPos, BlockWorldState> func_181627_a(World p_181627_0_, boolean p_181627_1_) {
/* 110 */     return CacheBuilder.newBuilder().build(new CacheLoader(p_181627_0_, p_181627_1_));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static BlockPos translateOffset(BlockPos pos, EnumFacing finger, EnumFacing thumb, int palmOffset, int thumbOffset, int fingerOffset) {
/* 119 */     if (finger != thumb && finger != thumb.getOpposite()) {
/*     */       
/* 121 */       Vec3i vec3i = new Vec3i(finger.getFrontOffsetX(), finger.getFrontOffsetY(), finger.getFrontOffsetZ());
/* 122 */       Vec3i vec3i1 = new Vec3i(thumb.getFrontOffsetX(), thumb.getFrontOffsetY(), thumb.getFrontOffsetZ());
/* 123 */       Vec3i vec3i2 = vec3i.crossProduct(vec3i1);
/* 124 */       return pos.add(vec3i1.getX() * -thumbOffset + vec3i2.getX() * palmOffset + vec3i.getX() * fingerOffset, vec3i1.getY() * -thumbOffset + vec3i2.getY() * palmOffset + vec3i.getY() * fingerOffset, vec3i1.getZ() * -thumbOffset + vec3i2.getZ() * palmOffset + vec3i.getZ() * fingerOffset);
/*     */     } 
/*     */ 
/*     */     
/* 128 */     throw new IllegalArgumentException("Invalid forwards & up combination");
/*     */   }
/*     */ 
/*     */   
/*     */   static class CacheLoader
/*     */     extends com.google.common.cache.CacheLoader<BlockPos, BlockWorldState>
/*     */   {
/*     */     private final World world;
/*     */     private final boolean field_181626_b;
/*     */     
/*     */     public CacheLoader(World worldIn, boolean p_i46460_2_) {
/* 139 */       this.world = worldIn;
/* 140 */       this.field_181626_b = p_i46460_2_;
/*     */     }
/*     */     
/*     */     public BlockWorldState load(BlockPos p_load_1_) {
/* 144 */       return new BlockWorldState(this.world, p_load_1_, this.field_181626_b);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class PatternHelper
/*     */   {
/*     */     private final BlockPos pos;
/*     */     private final EnumFacing finger;
/*     */     private final EnumFacing thumb;
/*     */     private final LoadingCache<BlockPos, BlockWorldState> lcache;
/*     */     private final int field_181120_e;
/*     */     private final int field_181121_f;
/*     */     private final int field_181122_g;
/*     */     
/*     */     public PatternHelper(BlockPos posIn, EnumFacing fingerIn, EnumFacing thumbIn, LoadingCache<BlockPos, BlockWorldState> lcacheIn, int p_i46378_5_, int p_i46378_6_, int p_i46378_7_) {
/* 160 */       this.pos = posIn;
/* 161 */       this.finger = fingerIn;
/* 162 */       this.thumb = thumbIn;
/* 163 */       this.lcache = lcacheIn;
/* 164 */       this.field_181120_e = p_i46378_5_;
/* 165 */       this.field_181121_f = p_i46378_6_;
/* 166 */       this.field_181122_g = p_i46378_7_;
/*     */     }
/*     */ 
/*     */     
/*     */     public BlockPos getPos() {
/* 171 */       return this.pos;
/*     */     }
/*     */ 
/*     */     
/*     */     public EnumFacing getFinger() {
/* 176 */       return this.finger;
/*     */     }
/*     */ 
/*     */     
/*     */     public EnumFacing getThumb() {
/* 181 */       return this.thumb;
/*     */     }
/*     */ 
/*     */     
/*     */     public int func_181118_d() {
/* 186 */       return this.field_181120_e;
/*     */     }
/*     */ 
/*     */     
/*     */     public int func_181119_e() {
/* 191 */       return this.field_181121_f;
/*     */     }
/*     */ 
/*     */     
/*     */     public BlockWorldState translateOffset(int palmOffset, int thumbOffset, int fingerOffset) {
/* 196 */       return (BlockWorldState)this.lcache.getUnchecked(BlockPattern.translateOffset(this.pos, this.finger, this.thumb, palmOffset, thumbOffset, fingerOffset));
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 201 */       return Objects.toStringHelper(this).add("up", this.thumb).add("forwards", this.finger).add("frontTopLeft", this.pos).toString();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\block\state\pattern\BlockPattern.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */