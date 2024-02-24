/*     */ package awareline.main.mod.implement.move;
/*     */ 
/*     */ import awareline.main.event.EventHandler;
/*     */ import awareline.main.event.events.world.BBSetEvent;
/*     */ import awareline.main.event.events.world.moveEvents.EventJump;
/*     */ import awareline.main.event.events.world.updateEvents.EventPreUpdate;
/*     */ import awareline.main.mod.Module;
/*     */ import awareline.main.mod.ModuleType;
/*     */ import awareline.main.mod.values.Mode;
/*     */ import awareline.main.mod.values.Option;
/*     */ import awareline.main.mod.values.Value;
/*     */ import awareline.main.utility.BlockUtils;
/*     */ import awareline.main.utility.MoveUtils;
/*     */ import awareline.main.utility.PlayerUtil;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import net.minecraft.block.BlockLiquid;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ 
/*     */ public class WaterWalk extends Module {
/*     */   public static WaterWalk getInstance;
/*  25 */   public final Option<Boolean> stopSpeed = new Option("StopSpeed", Boolean.valueOf(false));
/*  26 */   private final String[] modes = new String[] { "Solid", "NCP", "Free", "Dolphin", "Horizon", "Karhu", "AAC3.3.11", "Jump", "Watchdog" };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  31 */   private final Mode<String> mode = new Mode("Mode", this.modes, this.modes[0]);
/*     */   private boolean wasWater;
/*     */   private int ticks;
/*     */   private boolean shouldJesus;
/*     */   
/*     */   public WaterWalk() {
/*  37 */     super("WaterWalk", ModuleType.Movement);
/*  38 */     addSettings(new Value[] { (Value)this.mode, (Value)this.stopSpeed });
/*  39 */     getInstance = this;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEnable() {
/*  44 */     this.wasWater = false;
/*  45 */     this.shouldJesus = false;
/*  46 */     super.onEnable();
/*     */   }
/*     */   
/*     */   public boolean canJeboos() {
/*  50 */     return (mc.thePlayer.fallDistance < 3.0F && !mc.gameSettings.keyBindJump.isPressed() && 
/*  51 */       !BlockUtils.isInLiquid() && !mc.thePlayer.isSneaking());
/*     */   }
/*     */   
/*     */   public boolean shouldJesus() {
/*  55 */     double x = mc.thePlayer.posX;
/*  56 */     double y = mc.thePlayer.posY;
/*  57 */     double z = mc.thePlayer.posZ;
/*  58 */     ArrayList<BlockPos> pos = new ArrayList<>(Arrays.asList(new BlockPos[] { new BlockPos(x + 0.3D, y, z + 0.3D), new BlockPos(x - 0.3D, y, z + 0.3D), new BlockPos(x + 0.3D, y, z - 0.3D), new BlockPos(x - 0.3D, y, z - 0.3D) }));
/*  59 */     for (BlockPos po : pos) {
/*  60 */       if (!(mc.theWorld.getBlockState(po).getBlock() instanceof BlockLiquid))
/*     */         continue; 
/*  62 */       if (mc.theWorld.getBlockState(po).getProperties().get(BlockLiquid.LEVEL) instanceof Integer && (
/*  63 */         (Integer)mc.theWorld.getBlockState(po).getProperties().get(BlockLiquid.LEVEL)).intValue() <= 4) {
/*  64 */         return true;
/*     */       }
/*     */     } 
/*     */     
/*  68 */     return false;
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onPre(EventPreUpdate e) {
/*  73 */     setSuffix((Serializable)this.mode.get());
/*  74 */     if (this.mode.is("Dolphin")) {
/*  75 */       if (mc.thePlayer.isInWater() && !mc.thePlayer.isSneaking() && shouldJesus()) {
/*  76 */         mc.thePlayer.motionY = 0.09D;
/*     */       }
/*  78 */       if (e.getType() == 1) {
/*     */         return;
/*     */       }
/*  81 */       if (mc.thePlayer.onGround || mc.thePlayer.isOnLadder()) {
/*  82 */         this.wasWater = false;
/*     */       }
/*  84 */       if (mc.thePlayer.motionY > 0.0D && this.wasWater) {
/*  85 */         if (mc.thePlayer.motionY <= 0.11D) {
/*  86 */           mc.thePlayer.motionY *= 1.2671D;
/*     */         }
/*  88 */         mc.thePlayer.motionY += 0.05172D;
/*     */       } 
/*  90 */       if (BlockUtils.isInLiquid() && !mc.thePlayer.isSneaking()) {
/*  91 */         if (this.ticks < 3) {
/*  92 */           mc.thePlayer.motionY = 0.13D;
/*  93 */           this.ticks++;
/*  94 */           this.wasWater = false;
/*     */         } else {
/*  96 */           mc.thePlayer.motionY = 0.5D;
/*  97 */           this.ticks = 0;
/*  98 */           this.wasWater = true;
/*     */         } 
/*     */       }
/* 101 */     } else if (this.mode.is("Solid") || this.mode.is("NCP")) {
/* 102 */       if (mc.thePlayer.ticksExisted % 2 == 0 && PlayerUtil.onLiquid()) {
/* 103 */         e.setPosY(e.getPosY() - 0.015625D);
/*     */       }
/* 105 */     } else if (this.mode.is("Horizon")) {
/* 106 */       if (mc.thePlayer.isInWater() && !mc.thePlayer.isSneaking() && shouldJesus()) {
/* 107 */         mc.thePlayer.motionY = 0.09D;
/*     */       }
/* 109 */     } else if (this.mode.is("AAC3.3.11")) {
/* 110 */       if (mc.thePlayer.isInWater()) {
/* 111 */         mc.thePlayer.motionX *= 1.17D;
/* 112 */         mc.thePlayer.motionZ *= 1.17D;
/*     */         
/* 114 */         if (mc.thePlayer.isCollidedHorizontally)
/* 115 */         { mc.thePlayer.motionY = 0.24D; }
/* 116 */         else if (mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY + 1.0D, mc.thePlayer.posZ)).getBlock() != Blocks.air)
/* 117 */         { mc.thePlayer.motionY += 0.04D; } 
/*     */       } 
/* 119 */     } else if (this.mode.is("Jump")) {
/* 120 */       if (BlockUtils.isOnLiquid() && !mc.thePlayer.isSneaking() && !mc.gameSettings.keyBindJump.isKeyDown()) {
/* 121 */         mc.thePlayer.jump();
/* 122 */         MoveUtils.INSTANCE.strafe(0.02F);
/*     */       } 
/* 124 */     } else if (this.mode.is("Karhu")) {
/* 125 */       if (PlayerUtil.onLiquid()) {
/* 126 */         e.setPosY(e.getPosY() - ((mc.thePlayer.ticksExisted % 2 == 0) ? 0.015625D : 0.0D));
/* 127 */         e.setOnGround(false);
/*     */       } 
/* 129 */     } else if (this.mode.is("Watchdog")) {
/* 130 */       if (!mc.thePlayer.movementInput.sneak && BlockUtils.isInLiquid() && neededLevel(e.getX(), e.getY(), e.getZ())) {
/* 131 */         mc.thePlayer.motionY = 0.11999999731779099D;
/*     */       }
/* 133 */     } else if (this.mode.is("Verus")) {
/* 134 */       if (this.shouldJesus && 
/* 135 */         mc.thePlayer.ticksExisted % 5 == 0) {
/* 136 */         mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.1D, mc.thePlayer.posZ);
/*     */       }
/*     */       
/* 139 */       this.shouldJesus = false;
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onJump(EventJump e) {
/* 145 */     if ((((Boolean)this.stopSpeed.get()).booleanValue() && Speed.getInstance.isEnabled()) || (Longjump.getInstance.isEnabled() && 
/* 146 */       isMoving() && (mc.thePlayer.isInWater() || BlockUtils.isInLiquid() || PlayerUtil.onLiquid()))) {
/* 147 */       e.cancelEvent();
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onBB(BBSetEvent e) {
/* 153 */     if (this.mode.is("Free")) {
/* 154 */       if (!mc.thePlayer.movementInput.sneak && BlockUtils.isInLiquid() && neededLevel(e.getX(), e.getY(), e.getZ())) {
/* 155 */         mc.thePlayer.motionY = 0.12D;
/*     */       }
/* 157 */     } else if (this.mode.is("Solid") || this.mode.is("NCP") || this.mode.is("Karhu")) {
/* 158 */       if (e.getBlock() instanceof BlockLiquid && !mc.gameSettings.keyBindSneak.isKeyDown()) {
/* 159 */         int x = e.getBlockPos().getX();
/* 160 */         int y = e.getBlockPos().getY();
/* 161 */         int z = e.getBlockPos().getZ();
/* 162 */         e.setBoundingBox(AxisAlignedBB.fromBounds(x, y, z, (x + 1), (y + 1), (z + 1)));
/*     */       } 
/* 164 */     } else if (this.mode.is("Verus") && 
/* 165 */       e.getBlock().getMaterial().isLiquid()) {
/* 166 */       AxisAlignedBB axisAlignedBB = AxisAlignedBB.fromBounds(-5.0D, -1.0D, -5.0D, 5.0D, 1.0D, 5.0D).offset(e
/* 167 */           .getBlockPos().getX(), e.getBlockPos().getY(), e.getBlockPos().getZ());
/* 168 */       this.shouldJesus = true;
/* 169 */       e.setBoundingBox(axisAlignedBB);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean neededLevel(double x, double y, double z) {
/* 175 */     return 
/* 176 */       (((Integer)mc.theWorld.getBlockState(new BlockPos(x, y, z)).getProperties().get(BlockLiquid.LEVEL)).intValue() < (Speed.getInstance.isEnabled() ? 2 : 4));
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\move\WaterWalk.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */