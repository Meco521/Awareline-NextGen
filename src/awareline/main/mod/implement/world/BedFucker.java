/*     */ package awareline.main.mod.implement.world;
/*     */ import awareline.main.event.EventHandler;
/*     */ import awareline.main.event.events.misc.EventChat;
/*     */ import awareline.main.event.events.world.updateEvents.EventPreUpdate;
/*     */ import awareline.main.mod.ModuleType;
/*     */ import awareline.main.mod.implement.combat.KillAura;
/*     */ import awareline.main.mod.implement.move.antifallutily.TimeHelper;
/*     */ import awareline.main.mod.values.Mode;
/*     */ import awareline.main.mod.values.Numbers;
/*     */ import awareline.main.mod.values.Option;
/*     */ import awareline.main.mod.values.Value;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.network.play.client.C0APacketAnimation;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.Vec3;
/*     */ 
/*     */ public class BedFucker extends Module {
/*  21 */   private final String[] breakingMode = new String[] { "Vanilla", "Hypixel" };
/*  22 */   public final Mode<String> mode = new Mode("Mode", this.breakingMode, this.breakingMode[0]);
/*  23 */   private final Numbers<Double> range = new Numbers("Range", Double.valueOf(3.0D), Double.valueOf(1.0D), Double.valueOf(6.0D), Double.valueOf(1.0D));
/*  24 */   private final Numbers<Double> delay = new Numbers("Delay", Double.valueOf(100.0D), Double.valueOf(100.0D), Double.valueOf(1000.0D), Double.valueOf(100.0D));
/*  25 */   private final Option<Boolean> swing = new Option("NoSwing", Boolean.valueOf(false));
/*  26 */   private final Option<Boolean> noHit = new Option("OnlyNoHit", Boolean.valueOf(false));
/*     */   public static BlockPos self;
/*     */   public static BlockPos ready;
/*     */   public static BlockPos fucking;
/*  30 */   private final TimeHelper timer = new TimeHelper();
/*     */   
/*     */   public BedFucker() {
/*  33 */     super("BedFucker", new String[] { "fucker" }, ModuleType.World);
/*  34 */     addSettings(new Value[] { (Value)this.mode, (Value)this.range, (Value)this.delay, (Value)this.swing, (Value)this.noHit });
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEnable() {
/*  39 */     this.timer.reset();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisable() {
/*  44 */     ready = null;
/*  45 */     fucking = null;
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onTurn(EventTurn e) {
/*  50 */     if (self != null) {
/*  51 */       self = null;
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onChat(EventChat e) {
/*  57 */     if (e.getMessage().contains("You can't destroy your own bed!") && ready != null) {
/*  58 */       self = ready;
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onPreUpdate(EventPreUpdate e) {
/*  64 */     setSuffix((Serializable)this.mode.get());
/*  65 */     if (((Boolean)this.noHit.get()).booleanValue() && isEnabled(KillAura.class) && KillAura.getInstance.getTarget() != null) {
/*     */       return;
/*     */     }
/*  68 */     int reach = ((Double)this.range.get()).intValue();
/*  69 */     for (int y = reach; y >= -reach; y--) {
/*  70 */       for (int x = -reach; x <= reach; x++) {
/*  71 */         for (int z = -reach; z <= reach; z++) {
/*  72 */           boolean confirm = (x != 0 || z != 0);
/*  73 */           if (mc.thePlayer.isSneaking()) {
/*  74 */             confirm = !confirm;
/*     */           }
/*  76 */           if (confirm) {
/*  77 */             BlockPos pos = new BlockPos(mc.thePlayer.posX + x, mc.thePlayer.posY + y, mc.thePlayer.posZ + z);
/*  78 */             if (getFacingDirection(pos) != null && blockChecks(mc.theWorld.getBlockState(pos).getBlock()) && mc.thePlayer.getDistance(mc.thePlayer.posX + x, mc.thePlayer.posY + y, mc.thePlayer.posZ + z) < mc.playerController.getBlockReachDistance() - 0.5D) {
/*  79 */               float[] rotations = getBlockRotations(mc.thePlayer.posX + x, mc.thePlayer.posY + y, mc.thePlayer.posZ + z);
/*  80 */               e.setYaw(rotations[0]);
/*  81 */               e.setPitch(rotations[1]);
/*  82 */               fucking = pos;
/*  83 */               ready = pos;
/*     */               return;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onPostUpdate(EventPostUpdate e2) {
/*  94 */     if (((Boolean)this.noHit.get()).booleanValue() && KillAura.getInstance.isEnabled() && KillAura.getInstance.getTarget() != null) {
/*     */       return;
/*     */     }
/*  97 */     if (!this.timer.isDelayComplete(((Double)this.delay.get()).longValue())) {
/*     */       return;
/*     */     }
/* 100 */     if (!blockChecks(mc.theWorld.getBlockState(fucking).getBlock())) this.timer.reset(); 
/* 101 */     if (fucking != null) {
/* 102 */       if (fucking == self) {
/* 103 */         fucking = null;
/*     */         return;
/*     */       } 
/* 106 */       if (mc.playerController.blockHitDelay > 1) {
/* 107 */         mc.playerController.blockHitDelay = 1;
/*     */       }
/* 109 */       if (!((Boolean)this.swing.get()).booleanValue()) {
/* 110 */         mc.thePlayer.swingItem();
/*     */       } else {
/* 112 */         mc.thePlayer.sendQueue.addToSendQueue((Packet)new C0APacketAnimation());
/*     */       } 
/* 114 */       EnumFacing direction = getFacingDirection(fucking);
/* 115 */       if (direction != null) {
/* 116 */         mc.playerController.onPlayerDamageBlock(fucking, direction);
/*     */       }
/* 118 */       fucking = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static boolean blockChecks(Block block) {
/* 123 */     return (block == Blocks.bed);
/*     */   }
/*     */   
/*     */   private static float[] getBlockRotations(double x, double y, double z) {
/* 127 */     double var4 = x - mc.thePlayer.posX + 0.5D;
/* 128 */     double var5 = z - mc.thePlayer.posZ + 0.5D;
/* 129 */     double var6 = y - mc.thePlayer.posY + mc.thePlayer.getEyeHeight() - 1.0D;
/* 130 */     double var7 = MathHelper.sqrt_double(var4 * var4 + var5 * var5);
/* 131 */     float var8 = (float)(Math.atan2(var5, var4) * 180.0D / Math.PI) - 90.0F;
/* 132 */     return new float[] { var8, (float)-(Math.atan2(var6, var7) * 180.0D / Math.PI) };
/*     */   }
/*     */   
/*     */   private static EnumFacing getFacingDirection(BlockPos pos) {
/* 136 */     EnumFacing direction = null;
/* 137 */     if (!mc.theWorld.getBlockState(pos.add(0, 1, 0)).getBlock().isSolidFullCube()) {
/* 138 */       direction = EnumFacing.UP;
/* 139 */     } else if (!mc.theWorld.getBlockState(pos.add(0, -1, 0)).getBlock().isSolidFullCube()) {
/* 140 */       direction = EnumFacing.DOWN;
/* 141 */     } else if (!mc.theWorld.getBlockState(pos.add(1, 0, 0)).getBlock().isSolidFullCube()) {
/* 142 */       direction = EnumFacing.EAST;
/* 143 */     } else if (!mc.theWorld.getBlockState(pos.add(-1, 0, 0)).getBlock().isSolidFullCube()) {
/* 144 */       direction = EnumFacing.WEST;
/* 145 */     } else if (!mc.theWorld.getBlockState(pos.add(0, 0, 1)).getBlock().isSolidFullCube()) {
/* 146 */       direction = EnumFacing.SOUTH;
/* 147 */     } else if (!mc.theWorld.getBlockState(pos.add(0, 0, 1)).getBlock().isSolidFullCube()) {
/* 148 */       direction = EnumFacing.NORTH;
/*     */     } 
/* 150 */     MovingObjectPosition rayResult = mc.theWorld.rayTraceBlocks(new Vec3(mc.thePlayer.posX, mc.thePlayer.posY + mc.thePlayer.getEyeHeight(), mc.thePlayer.posZ), new Vec3(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D));
/* 151 */     return (rayResult != null && rayResult.getBlockPos() == pos) ? rayResult.sideHit : direction;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\world\BedFucker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */