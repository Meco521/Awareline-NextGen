/*    */ package net.minecraft.world.gen.feature;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldGenBlockBlob
/*    */   extends WorldGenerator
/*    */ {
/*    */   private final Block field_150545_a;
/*    */   private final int field_150544_b;
/*    */   
/*    */   public WorldGenBlockBlob(Block p_i45450_1_, int p_i45450_2_) {
/* 17 */     super(false);
/* 18 */     this.field_150545_a = p_i45450_1_;
/* 19 */     this.field_150544_b = p_i45450_2_;
/*    */   }
/*    */   
/*    */   public boolean generate(World worldIn, Random rand, BlockPos position) {
/*    */     // Byte code:
/*    */     //   0: aload_3
/*    */     //   1: invokevirtual getY : ()I
/*    */     //   4: iconst_3
/*    */     //   5: if_icmple -> 64
/*    */     //   8: aload_1
/*    */     //   9: aload_3
/*    */     //   10: invokevirtual down : ()Lnet/minecraft/util/BlockPos;
/*    */     //   13: invokevirtual isAirBlock : (Lnet/minecraft/util/BlockPos;)Z
/*    */     //   16: ifeq -> 22
/*    */     //   19: goto -> 281
/*    */     //   22: aload_1
/*    */     //   23: aload_3
/*    */     //   24: invokevirtual down : ()Lnet/minecraft/util/BlockPos;
/*    */     //   27: invokevirtual getBlockState : (Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;
/*    */     //   30: invokeinterface getBlock : ()Lnet/minecraft/block/Block;
/*    */     //   35: astore #4
/*    */     //   37: aload #4
/*    */     //   39: getstatic net/minecraft/init/Blocks.grass : Lnet/minecraft/block/BlockGrass;
/*    */     //   42: if_acmpeq -> 64
/*    */     //   45: aload #4
/*    */     //   47: getstatic net/minecraft/init/Blocks.dirt : Lnet/minecraft/block/Block;
/*    */     //   50: if_acmpeq -> 64
/*    */     //   53: aload #4
/*    */     //   55: getstatic net/minecraft/init/Blocks.stone : Lnet/minecraft/block/Block;
/*    */     //   58: if_acmpeq -> 64
/*    */     //   61: goto -> 281
/*    */     //   64: aload_3
/*    */     //   65: invokevirtual getY : ()I
/*    */     //   68: iconst_3
/*    */     //   69: if_icmpgt -> 74
/*    */     //   72: iconst_0
/*    */     //   73: ireturn
/*    */     //   74: aload_0
/*    */     //   75: getfield field_150544_b : I
/*    */     //   78: istore #4
/*    */     //   80: iconst_0
/*    */     //   81: istore #5
/*    */     //   83: iload #4
/*    */     //   85: iflt -> 279
/*    */     //   88: iload #5
/*    */     //   90: iconst_3
/*    */     //   91: if_icmpge -> 279
/*    */     //   94: iload #4
/*    */     //   96: aload_2
/*    */     //   97: iconst_2
/*    */     //   98: invokevirtual nextInt : (I)I
/*    */     //   101: iadd
/*    */     //   102: istore #6
/*    */     //   104: iload #4
/*    */     //   106: aload_2
/*    */     //   107: iconst_2
/*    */     //   108: invokevirtual nextInt : (I)I
/*    */     //   111: iadd
/*    */     //   112: istore #7
/*    */     //   114: iload #4
/*    */     //   116: aload_2
/*    */     //   117: iconst_2
/*    */     //   118: invokevirtual nextInt : (I)I
/*    */     //   121: iadd
/*    */     //   122: istore #8
/*    */     //   124: iload #6
/*    */     //   126: iload #7
/*    */     //   128: iadd
/*    */     //   129: iload #8
/*    */     //   131: iadd
/*    */     //   132: i2f
/*    */     //   133: ldc 0.333
/*    */     //   135: fmul
/*    */     //   136: ldc 0.5
/*    */     //   138: fadd
/*    */     //   139: fstore #9
/*    */     //   141: aload_3
/*    */     //   142: iload #6
/*    */     //   144: ineg
/*    */     //   145: iload #7
/*    */     //   147: ineg
/*    */     //   148: iload #8
/*    */     //   150: ineg
/*    */     //   151: invokevirtual add : (III)Lnet/minecraft/util/BlockPos;
/*    */     //   154: aload_3
/*    */     //   155: iload #6
/*    */     //   157: iload #7
/*    */     //   159: iload #8
/*    */     //   161: invokevirtual add : (III)Lnet/minecraft/util/BlockPos;
/*    */     //   164: invokestatic getAllInBox : (Lnet/minecraft/util/BlockPos;Lnet/minecraft/util/BlockPos;)Ljava/lang/Iterable;
/*    */     //   167: invokeinterface iterator : ()Ljava/util/Iterator;
/*    */     //   172: astore #10
/*    */     //   174: aload #10
/*    */     //   176: invokeinterface hasNext : ()Z
/*    */     //   181: ifeq -> 230
/*    */     //   184: aload #10
/*    */     //   186: invokeinterface next : ()Ljava/lang/Object;
/*    */     //   191: checkcast net/minecraft/util/BlockPos
/*    */     //   194: astore #11
/*    */     //   196: aload #11
/*    */     //   198: aload_3
/*    */     //   199: invokevirtual distanceSq : (Lnet/minecraft/util/Vec3i;)D
/*    */     //   202: fload #9
/*    */     //   204: fload #9
/*    */     //   206: fmul
/*    */     //   207: f2d
/*    */     //   208: dcmpg
/*    */     //   209: ifgt -> 227
/*    */     //   212: aload_1
/*    */     //   213: aload #11
/*    */     //   215: aload_0
/*    */     //   216: getfield field_150545_a : Lnet/minecraft/block/Block;
/*    */     //   219: invokevirtual getDefaultState : ()Lnet/minecraft/block/state/IBlockState;
/*    */     //   222: iconst_4
/*    */     //   223: invokevirtual setBlockState : (Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/state/IBlockState;I)Z
/*    */     //   226: pop
/*    */     //   227: goto -> 174
/*    */     //   230: aload_3
/*    */     //   231: iload #4
/*    */     //   233: iconst_1
/*    */     //   234: iadd
/*    */     //   235: ineg
/*    */     //   236: aload_2
/*    */     //   237: iconst_2
/*    */     //   238: iload #4
/*    */     //   240: iconst_1
/*    */     //   241: ishl
/*    */     //   242: iadd
/*    */     //   243: invokevirtual nextInt : (I)I
/*    */     //   246: iadd
/*    */     //   247: aload_2
/*    */     //   248: iconst_2
/*    */     //   249: invokevirtual nextInt : (I)I
/*    */     //   252: ineg
/*    */     //   253: iload #4
/*    */     //   255: iconst_1
/*    */     //   256: iadd
/*    */     //   257: ineg
/*    */     //   258: aload_2
/*    */     //   259: iconst_2
/*    */     //   260: iload #4
/*    */     //   262: iconst_1
/*    */     //   263: ishl
/*    */     //   264: iadd
/*    */     //   265: invokevirtual nextInt : (I)I
/*    */     //   268: iadd
/*    */     //   269: invokevirtual add : (III)Lnet/minecraft/util/BlockPos;
/*    */     //   272: astore_3
/*    */     //   273: iinc #5, 1
/*    */     //   276: goto -> 83
/*    */     //   279: iconst_1
/*    */     //   280: ireturn
/*    */     //   281: aload_3
/*    */     //   282: invokevirtual down : ()Lnet/minecraft/util/BlockPos;
/*    */     //   285: astore_3
/*    */     //   286: goto -> 0
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #28	-> 0
/*    */     //   #30	-> 8
/*    */     //   #32	-> 19
/*    */     //   #35	-> 22
/*    */     //   #37	-> 37
/*    */     //   #39	-> 61
/*    */     //   #43	-> 64
/*    */     //   #45	-> 72
/*    */     //   #48	-> 74
/*    */     //   #50	-> 80
/*    */     //   #52	-> 94
/*    */     //   #53	-> 104
/*    */     //   #54	-> 114
/*    */     //   #55	-> 124
/*    */     //   #57	-> 141
/*    */     //   #59	-> 196
/*    */     //   #61	-> 212
/*    */     //   #63	-> 227
/*    */     //   #65	-> 230
/*    */     //   #50	-> 273
/*    */     //   #68	-> 279
/*    */     //   #70	-> 281
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   37	27	4	block	Lnet/minecraft/block/Block;
/*    */     //   196	31	11	blockpos	Lnet/minecraft/util/BlockPos;
/*    */     //   104	169	6	j	I
/*    */     //   114	159	7	k	I
/*    */     //   124	149	8	l	I
/*    */     //   141	132	9	f	F
/*    */     //   83	196	5	i	I
/*    */     //   80	201	4	i1	I
/*    */     //   0	289	0	this	Lnet/minecraft/world/gen/feature/WorldGenBlockBlob;
/*    */     //   0	289	1	worldIn	Lnet/minecraft/world/World;
/*    */     //   0	289	2	rand	Ljava/util/Random;
/*    */     //   0	289	3	position	Lnet/minecraft/util/BlockPos;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\gen\feature\WorldGenBlockBlob.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */