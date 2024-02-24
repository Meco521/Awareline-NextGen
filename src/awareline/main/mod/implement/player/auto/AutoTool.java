/*     */ package awareline.main.mod.implement.player.auto;
/*     */ import awareline.main.component.StopWatch;
/*     */ import awareline.main.event.EventHandler;
/*     */ import awareline.main.event.events.world.BlockBreakEvent;
/*     */ import awareline.main.event.events.world.BlockDamageEvent;
/*     */ import awareline.main.event.events.world.packetEvents.PacketEvent;
/*     */ import awareline.main.event.events.world.updateEvents.EventPreUpdate;
/*     */ import awareline.main.mod.Module;
/*     */ import awareline.main.mod.ModuleType;
/*     */ import awareline.main.mod.values.Option;
/*     */ import awareline.main.mod.values.Value;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.C07PacketPlayerDigging;
/*     */ import net.minecraft.network.play.client.C09PacketHeldItemChange;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class AutoTool extends Module {
/*  26 */   public static final Option<Boolean> silent = new Option("Silent", Boolean.valueOf(false));
/*  27 */   private final Option<Boolean> nohit = new Option("NoAttacking", Boolean.valueOf(true));
/*  28 */   private final Option<Boolean> switchback = new Option("Switchback", Boolean.valueOf(true));
/*  29 */   private final StopWatch stopWatch = new StopWatch();
/*     */   private int oldSlot;
/*     */   private int tick;
/*  32 */   private int serverSideSlot = -1;
/*  33 */   private int tool = -1;
/*     */   
/*     */   public AutoTool() {
/*  36 */     super("AutoTool", ModuleType.Player);
/*  37 */     addSettings(new Value[] { (Value)this.nohit, (Value)this.switchback, (Value)silent });
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisable() {
/*  42 */     if (this.serverSideSlot != mc.thePlayer.inventory.currentItem) {
/*  43 */       sendPacketNoEvent((Packet)new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
/*     */     }
/*     */   }
/*     */   
/*     */   private int findTool(BlockPos blockPos) {
/*  48 */     float bestSpeed = 1.0F;
/*  49 */     int bestSlot = -1;
/*     */     
/*  51 */     IBlockState blockState = mc.theWorld.getBlockState(blockPos);
/*     */     
/*  53 */     for (int i = 0; i < 9; i++) {
/*  54 */       ItemStack itemStack = mc.thePlayer.inventory.getStackInSlot(i);
/*     */       
/*  56 */       if (itemStack != null) {
/*     */ 
/*     */ 
/*     */         
/*  60 */         float speed = itemStack.getStrVsBlock(blockState.getBlock());
/*     */         
/*  62 */         if (speed > bestSpeed) {
/*  63 */           bestSpeed = speed;
/*  64 */           bestSlot = i;
/*     */         } 
/*     */       } 
/*     */     } 
/*  68 */     return bestSlot;
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onBlockBreak(BlockBreakEvent event) {
/*  73 */     if (((Boolean)silent.get()).booleanValue()) {
/*  74 */       int slot = findTool(event.getBlockPos());
/*  75 */       if (slot != -1) {
/*  76 */         this.tool = slot;
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onPacket(PacketEvent event) {
/*  83 */     if (((Boolean)silent.get()).booleanValue()) {
/*  84 */       Packet<?> p = event.getPacket();
/*     */       
/*  86 */       if (p instanceof net.minecraft.network.play.client.C08PacketPlayerBlockPlacement) {
/*  87 */         this.serverSideSlot = mc.thePlayer.inventory.currentItem;
/*     */       }
/*     */       
/*  90 */       if (p instanceof C07PacketPlayerDigging) {
/*  91 */         C07PacketPlayerDigging wrapper = (C07PacketPlayerDigging)p;
/*  92 */         int slot = findTool(wrapper.getPosition());
/*     */         
/*  94 */         if (slot != -1) {
/*  95 */           this.tool = slot;
/*  96 */           this.serverSideSlot = this.tool;
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 101 */       if (p instanceof C09PacketHeldItemChange) {
/* 102 */         C09PacketHeldItemChange wrapper = (C09PacketHeldItemChange)p;
/* 103 */         this.serverSideSlot = wrapper.getSlotId();
/* 104 */         this.stopWatch.reset();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean canHeldItemHarvest(Block blockIn, int slot) {
/* 110 */     if (blockIn.getMaterial().isToolNotRequired()) {
/* 111 */       return true;
/*     */     }
/* 113 */     ItemStack itemstack = mc.thePlayer.inventory.getStackInSlot(slot);
/* 114 */     return (itemstack != null && itemstack.canHarvestBlock(blockIn));
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getCurrentItemInSlot(int slot) {
/* 119 */     return (slot < 9 && slot >= 0) ? mc.thePlayer.inventory.mainInventory[slot] : null;
/*     */   }
/*     */   
/*     */   public float getStrVsBlock(Block blockIn, int slot) {
/* 123 */     float f = 1.0F;
/*     */     
/* 125 */     if (mc.thePlayer.inventory.mainInventory[slot] != null) {
/* 126 */       f *= mc.thePlayer.inventory.mainInventory[slot].getStrVsBlock(blockIn);
/*     */     }
/* 128 */     return f;
/*     */   }
/*     */   
/*     */   public float getToolDigEfficiency(Block blockIn, int slot) {
/* 132 */     float f = getStrVsBlock(blockIn, slot);
/*     */     
/* 134 */     if (f > 1.0F) {
/* 135 */       int i = EnchantmentHelper.getEfficiencyModifier((EntityLivingBase)mc.thePlayer);
/* 136 */       ItemStack itemstack = getCurrentItemInSlot(slot);
/*     */       
/* 138 */       if (i > 0 && itemstack != null) {
/* 139 */         f += (i * i + 1);
/*     */       }
/*     */     } 
/*     */     
/* 143 */     if (mc.thePlayer.isPotionActive(Potion.digSpeed)) {
/* 144 */       f *= 1.0F + (mc.thePlayer.getActivePotionEffect(Potion.digSpeed).getAmplifier() + 1) * 0.2F;
/*     */     }
/*     */     
/* 147 */     if (mc.thePlayer.isPotionActive(Potion.digSlowdown)) {
/*     */       float f1;
/*     */       
/* 150 */       switch (mc.thePlayer.getActivePotionEffect(Potion.digSlowdown).getAmplifier()) {
/*     */         case 0:
/* 152 */           f1 = 0.3F;
/*     */           break;
/*     */         
/*     */         case 1:
/* 156 */           f1 = 0.09F;
/*     */           break;
/*     */         
/*     */         case 2:
/* 160 */           f1 = 0.0027F;
/*     */           break;
/*     */ 
/*     */         
/*     */         default:
/* 165 */           f1 = 8.1E-4F;
/*     */           break;
/*     */       } 
/* 168 */       f *= f1;
/*     */     } 
/*     */     
/* 171 */     if (mc.thePlayer.isInsideOfMaterial(Material.water) && !EnchantmentHelper.getAquaAffinityModifier((EntityLivingBase)mc.thePlayer)) {
/* 172 */       f /= 5.0F;
/*     */     }
/*     */     
/* 175 */     if (!mc.thePlayer.onGround) {
/* 176 */       f /= 5.0F;
/*     */     }
/*     */     
/* 179 */     return f;
/*     */   }
/*     */   
/*     */   public float getPlayerRelativeBlockHardness(World worldIn, BlockPos pos, int slot) {
/* 183 */     Block block = mc.theWorld.getBlockState(pos).getBlock();
/* 184 */     float f = block.getBlockHardness(worldIn, pos);
/* 185 */     return (f < 0.0F) ? 0.0F : (!canHeldItemHarvest(block, slot) ? (getToolDigEfficiency(block, slot) / f / 100.0F) : (getToolDigEfficiency(block, slot) / f / 30.0F));
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onBlockDamage(BlockDamageEvent event) {
/* 190 */     if (((Boolean)silent.getValue()).booleanValue()) {
/* 191 */       event.setRelativeBlockHardness(getPlayerRelativeBlockHardness(event.getWorld(), event.getBlockPos(), this.serverSideSlot));
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onPre(EventPreUpdate event) {
/* 197 */     setSuffix(((Boolean)silent.get()).booleanValue() ? "Silent" : "");
/* 198 */     if (((Boolean)silent.get()).booleanValue()) {
/* 199 */       if (mc.gameSettings.keyBindAttack.isKeyDown()) {
/* 200 */         sendPacketNoEvent((Packet)new C09PacketHeldItemChange(this.serverSideSlot));
/*     */       }
/* 202 */       if (!mc.gameSettings.keyBindAttack.isKeyDown() && this.stopWatch.finished(60L) && 
/* 203 */         this.serverSideSlot != mc.thePlayer.inventory.currentItem) {
/* 204 */         this.serverSideSlot = mc.thePlayer.inventory.currentItem;
/* 205 */         sendPacketNoEvent((Packet)new C09PacketHeldItemChange(this.serverSideSlot));
/* 206 */         this.tool = -1;
/* 207 */         this.stopWatch.reset();
/*     */         
/*     */         return;
/*     */       } 
/*     */       return;
/*     */     } 
/* 213 */     if (((Boolean)this.nohit.get()).booleanValue() && 
/* 214 */       mc.objectMouseOver.entityHit != null) {
/*     */       return;
/*     */     }
/*     */     
/* 218 */     if (mc.playerController.isBreakingBlock()) {
/* 219 */       this.tick++;
/*     */       
/* 221 */       if (this.tick == 1) {
/* 222 */         this.oldSlot = mc.thePlayer.inventory.currentItem;
/*     */       }
/*     */       
/* 225 */       mc.thePlayer.updateTool(mc.objectMouseOver.getBlockPos());
/* 226 */     } else if (this.tick > 0) {
/* 227 */       if (((Boolean)this.switchback.get()).booleanValue()) {
/* 228 */         mc.thePlayer.inventory.currentItem = this.oldSlot;
/*     */       }
/* 230 */       this.tick = 0;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\player\auto\AutoTool.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */