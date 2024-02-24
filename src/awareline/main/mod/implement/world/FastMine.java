/*    */ package awareline.main.mod.implement.world;
/*    */ import awareline.main.event.EventHandler;
/*    */ import awareline.main.event.events.world.packetEvents.EventPacketSend;
/*    */ import awareline.main.event.events.world.updateEvents.EventPreUpdate;
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ import awareline.main.mod.values.Mode;
/*    */ import awareline.main.mod.values.Numbers;
/*    */ import awareline.main.mod.values.Option;
/*    */ import awareline.main.mod.values.Value;
/*    */ import java.io.Serializable;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.C07PacketPlayerDigging;
/*    */ import net.minecraft.potion.Potion;
/*    */ import net.minecraft.potion.PotionEffect;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class FastMine extends Module {
/*    */   private boolean bzs;
/* 24 */   private final Mode<String> mode = new Mode("Mode", new String[] { "Watchdog", "Minemora", "Custom" }, "Minemora"); private float bzx; public BlockPos blockPos;
/*    */   public EnumFacing facing;
/* 26 */   private final Numbers<Double> breakdmg = new Numbers("CustomBreakDamage", 
/* 27 */       Double.valueOf(0.01D), Double.valueOf(0.01D), Double.valueOf(1.0D), Double.valueOf(0.01D), () -> Boolean.valueOf(this.mode.isCurrentMode("Custom")));
/* 28 */   private final Option<Boolean> haste = new Option("HasteEffect", Boolean.valueOf(false));
/*    */   
/*    */   public FastMine() {
/* 31 */     super("FastMine", new String[] { "fastbreak" }, ModuleType.World);
/* 32 */     addSettings(new Value[] { (Value)this.mode, (Value)this.breakdmg, (Value)this.haste });
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onPre(EventPreUpdate e) {
/* 37 */     setSuffix((Serializable)this.mode.getValue());
/* 38 */     if (((Boolean)this.haste.getValue()).booleanValue() && 
/* 39 */       mc.thePlayer.onGround) {
/* 40 */       mc.thePlayer.addPotionEffect(new PotionEffect(Potion.digSpeed.id, 0, 1));
/*    */     }
/*    */     
/* 43 */     if (this.mode.isCurrentMode("Custom")) {
/* 44 */       mc.playerController.blockHitDelay = 0;
/* 45 */       if (mc.playerController.curBlockDamageMP > ((Double)this.breakdmg.getValue()).doubleValue())
/* 46 */         mc.playerController.curBlockDamageMP = 1.0F; 
/*    */     } 
/* 48 */     if (this.mode.isCurrentMode("Minemora") || this.mode.is("Watchdog")) {
/* 49 */       if (mc.playerController.extendedReach()) {
/* 50 */         mc.playerController.blockHitDelay = 0;
/* 51 */       } else if (this.bzs) {
/* 52 */         Block block = mc.theWorld.getBlockState(this.blockPos).getBlock();
/* 53 */         this.bzx += (float)(block.getPlayerRelativeBlockHardness((EntityPlayer)mc.thePlayer, (World)mc.theWorld, this.blockPos) * 1.4D);
/* 54 */         if (this.bzx >= 1.0F) {
/* 55 */           mc.theWorld.setBlockState(this.blockPos, Blocks.air.getDefaultState(), 11);
/* 56 */           sendPacket((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.blockPos, this.facing));
/* 57 */           this.bzx = 0.0F;
/* 58 */           this.bzs = false;
/*    */         } 
/*    */       } 
/*    */     }
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public final void onSendPacket(EventPacketSend event) {
/* 66 */     if ((this.mode.isCurrentMode("Minemora") || this.mode.is("Watchdog")) && 
/* 67 */       event.getPacket() instanceof C07PacketPlayerDigging && !mc.playerController.extendedReach() && mc.playerController != null) {
/* 68 */       C07PacketPlayerDigging c07PacketPlayerDigging = (C07PacketPlayerDigging)event.getPacket();
/* 69 */       if (c07PacketPlayerDigging.getStatus() == C07PacketPlayerDigging.Action.START_DESTROY_BLOCK) {
/* 70 */         this.bzs = true;
/* 71 */         this.blockPos = c07PacketPlayerDigging.getPosition();
/* 72 */         this.facing = c07PacketPlayerDigging.getFacing();
/* 73 */         this.bzx = 0.0F;
/* 74 */       } else if (c07PacketPlayerDigging.getStatus() == C07PacketPlayerDigging.Action.ABORT_DESTROY_BLOCK || c07PacketPlayerDigging.getStatus() == C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK) {
/* 75 */         this.bzs = false;
/* 76 */         this.blockPos = null;
/* 77 */         this.facing = null;
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\world\FastMine.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */