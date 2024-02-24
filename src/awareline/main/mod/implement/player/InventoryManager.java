/*     */ package awareline.main.mod.implement.player;
/*     */ 
/*     */ import awareline.main.Client;
/*     */ import awareline.main.event.EventHandler;
/*     */ import awareline.main.event.events.display.DisplayChestGuiEvent;
/*     */ import awareline.main.event.events.world.packetEvents.PacketEvent;
/*     */ import awareline.main.event.events.world.renderEvents.EventRender2D;
/*     */ import awareline.main.event.events.world.updateEvents.EventPreUpdate;
/*     */ import awareline.main.event.events.world.updateEvents.MotionUpdateEvent;
/*     */ import awareline.main.event.events.world.worldChangeEvents.LoadWorldEvent;
/*     */ import awareline.main.mod.Module;
/*     */ import awareline.main.mod.ModuleType;
/*     */ import awareline.main.mod.implement.combat.KillAura;
/*     */ import awareline.main.mod.implement.combat.auto.AutoArmor;
/*     */ import awareline.main.mod.implement.world.ChestStealer;
/*     */ import awareline.main.mod.implement.world.Scaffold;
/*     */ import awareline.main.mod.implement.world.utils.StealerUtils.Timer;
/*     */ import awareline.main.mod.values.Numbers;
/*     */ import awareline.main.mod.values.Option;
/*     */ import awareline.main.mod.values.Value;
/*     */ import awareline.main.ui.gui.hud.notification.ClientNotification;
/*     */ import awareline.main.utility.MoveUtils;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.enchantment.Enchantment;
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemArmor;
/*     */ import net.minecraft.item.ItemBlock;
/*     */ import net.minecraft.item.ItemPotion;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.item.ItemSword;
/*     */ import net.minecraft.item.ItemTool;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.C16PacketClientStatus;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ 
/*     */ public class InventoryManager
/*     */   extends Module {
/*     */   public static InventoryManager getInstance;
/*  47 */   public final Option<Boolean> onlyNoMove = new Option("OnlyNoMove", Boolean.valueOf(false));
/*  48 */   private final Option<Boolean> inventoryCleaner = new Option("Cleaner", Boolean.valueOf(true));
/*  49 */   private final Numbers<Double> blocks = new Numbers("Blocks", 
/*  50 */       Double.valueOf(128.0D), Double.valueOf(0.0D), Double.valueOf(256.0D), Double.valueOf(64.0D), this.inventoryCleaner::get);
/*  51 */   private final Numbers<Double> delay = new Numbers("Delay", 
/*  52 */       Double.valueOf(1.0D), Double.valueOf(0.0D), Double.valueOf(10.0D), Double.valueOf(1.0D), this.inventoryCleaner::get);
/*  53 */   private final Option<Boolean> Sword = new Option("Sword", Boolean.valueOf(true));
/*  54 */   private final Option<Boolean> Bow = new Option("Bow", Boolean.valueOf(true));
/*  55 */   private final Option<Boolean> PickAxe = new Option("PickAxe", Boolean.valueOf(true));
/*  56 */   private final Option<Boolean> Axe = new Option("Axe", Boolean.valueOf(true));
/*  57 */   private final Option<Boolean> Ores = new Option("Ores", Boolean.valueOf(true));
/*  58 */   private final Option<Boolean> Sticks = new Option("Sticks", Boolean.valueOf(true));
/*  59 */   private final Option<Boolean> Buckets = new Option("Buckets", Boolean.valueOf(true));
/*  60 */   private final Option<Boolean> Shovel = new Option("Shovel", Boolean.valueOf(true));
/*  61 */   private final Option<Boolean> GoldenApple = new Option("GoldenApple", Boolean.valueOf(true));
/*  62 */   private final Option<Boolean> Head = new Option("Head", Boolean.valueOf(true));
/*     */   
/*  64 */   private final Numbers<Double> arrows = new Numbers("Arrows", 
/*  65 */       Double.valueOf(128.0D), Double.valueOf(64.0D), Double.valueOf(512.0D), Double.valueOf(64.0D), this.Bow::get);
/*     */   
/*  67 */   private final Numbers<Double> pickAxeSlot = new Numbers("PickaxeSlot", 
/*  68 */       Double.valueOf(7.0D), Double.valueOf(1.0D), Double.valueOf(9.0D), Double.valueOf(1.0D), this.PickAxe::get);
/*  69 */   private final Numbers<Double> axeSlot = new Numbers("AxeSlot", 
/*  70 */       Double.valueOf(8.0D), Double.valueOf(1.0D), Double.valueOf(9.0D), Double.valueOf(1.0D), this.Axe::get);
/*  71 */   private final Numbers<Double> shovelSlot = new Numbers("ShovelSlot", 
/*  72 */       Double.valueOf(9.0D), Double.valueOf(1.0D), Double.valueOf(9.0D), Double.valueOf(1.0D));
/*  73 */   private final Numbers<Double> swordSlot = new Numbers("SwordSlot", 
/*  74 */       Double.valueOf(1.0D), Double.valueOf(1.0D), Double.valueOf(9.0D), Double.valueOf(1.0D), this.Sword::get);
/*  75 */   private final Numbers<Double> bowSlot = new Numbers("BowSlot", 
/*  76 */       Double.valueOf(2.0D), Double.valueOf(1.0D), Double.valueOf(9.0D), Double.valueOf(1.0D), this.Bow::get);
/*  77 */   private final Numbers<Double> headSlot = new Numbers("HeadSlot", 
/*  78 */       Double.valueOf(3.0D), Double.valueOf(1.0D), Double.valueOf(9.0D), Double.valueOf(1.0D), this.Head::get);
/*  79 */   private final Numbers<Double> gappleSlot = new Numbers("GoldenAppleSlot", 
/*  80 */       Double.valueOf(3.0D), Double.valueOf(1.0D), Double.valueOf(9.0D), Double.valueOf(1.0D), this.GoldenApple::get);
/*  81 */   private final Option<Boolean> auto_disable = new Option("AutoDisable", Boolean.valueOf(false));
/*  82 */   private final Option<Boolean> open_inv = new Option("OpenInv", Boolean.valueOf(true));
/*  83 */   private final Option<Boolean> checkTarget = new Option("CheckTarget", Boolean.valueOf(false));
/*  84 */   private final Option<Boolean> onlyGround = new Option("OnlyGround", Boolean.valueOf(false));
/*  85 */   private final Option<Boolean> silentGui = new Option("SilentGui", Boolean.valueOf(false));
/*  86 */   private final Option<Boolean> rotate = new Option("RotationThrow", Boolean.valueOf(false));
/*  87 */   private final Timer timer = new Timer();
/*  88 */   private final List<Block> blacklistedBlocks2 = Arrays.asList(new Block[] { Blocks.air, (Block)Blocks.water, (Block)Blocks.flowing_water, (Block)Blocks.lava, (Block)Blocks.wooden_slab, (Block)Blocks.wooden_slab, (Block)Blocks.chest, (Block)Blocks.flowing_lava, Blocks.enchanting_table, Blocks.carpet, Blocks.glass_pane, (Block)Blocks.skull, (Block)Blocks.stained_glass_pane, Blocks.iron_bars, Blocks.snow_layer, Blocks.ice, Blocks.packed_ice, Blocks.coal_ore, Blocks.diamond_ore, Blocks.emerald_ore, (Block)Blocks.chest, Blocks.trapped_chest, Blocks.tnt, Blocks.torch, Blocks.anvil, Blocks.trapped_chest, Blocks.noteblock, Blocks.jukebox, Blocks.tnt, Blocks.gold_ore, Blocks.iron_ore, Blocks.lapis_ore, Blocks.lit_redstone_ore, Blocks.quartz_ore, Blocks.redstone_ore, Blocks.wooden_pressure_plate, Blocks.stone_pressure_plate, Blocks.trapped_chest, Blocks.light_weighted_pressure_plate, Blocks.heavy_weighted_pressure_plate, Blocks.stone_button, Blocks.wooden_button, Blocks.lever, (Block)Blocks.tallgrass, Blocks.tripwire, (Block)Blocks.tripwire_hook, Blocks.rail, Blocks.waterlily, (Block)Blocks.red_flower, (Block)Blocks.red_mushroom, (Block)Blocks.brown_mushroom, Blocks.vine, Blocks.trapdoor, (Block)Blocks.yellow_flower, Blocks.ladder, Blocks.furnace, (Block)Blocks.sand, (Block)Blocks.cactus, Blocks.dispenser, Blocks.noteblock, Blocks.dropper, Blocks.crafting_table, Blocks.pumpkin, Blocks.sapling, Blocks.cobblestone_wall, Blocks.oak_fence, Blocks.activator_rail, Blocks.detector_rail, Blocks.golden_rail, Blocks.redstone_torch, Blocks.acacia_stairs, Blocks.birch_stairs, Blocks.brick_stairs, Blocks.dark_oak_stairs, Blocks.jungle_stairs, Blocks.nether_brick_stairs, Blocks.oak_stairs, Blocks.quartz_stairs, Blocks.red_sandstone_stairs, Blocks.sandstone_stairs, Blocks.spruce_stairs, Blocks.stone_brick_stairs, Blocks.stone_stairs, (Block)Blocks.wooden_slab, (Block)Blocks.double_wooden_slab, (Block)Blocks.stone_slab, (Block)Blocks.double_stone_slab, (Block)Blocks.stone_slab2, (Block)Blocks.double_stone_slab2, Blocks.web, Blocks.gravel, (Block)Blocks.daylight_detector_inverted, (Block)Blocks.daylight_detector, Blocks.soul_sand, (Block)Blocks.piston, (Block)Blocks.piston_extension, (Block)Blocks.piston_head, (Block)Blocks.sticky_piston, Blocks.iron_trapdoor, Blocks.ender_chest, Blocks.end_portal, Blocks.end_portal_frame, Blocks.standing_banner, Blocks.wall_banner, (Block)Blocks.deadbush, Blocks.slime_block, Blocks.acacia_fence_gate, Blocks.birch_fence_gate, Blocks.dark_oak_fence_gate, Blocks.jungle_fence_gate, Blocks.spruce_fence_gate, Blocks.oak_fence_gate });
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean lastInvOpen;
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean invOpen;
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInv;
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] angles;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InventoryManager() {
/* 110 */     super("InventoryManager", new String[] { "InvCleaner", "invmanager", "im" }, ModuleType.Player);
/* 111 */     addSettings(new Value[] { (Value)this.blocks, (Value)this.delay, (Value)this.arrows, (Value)this.pickAxeSlot, (Value)this.axeSlot, (Value)this.shovelSlot, (Value)this.swordSlot, (Value)this.bowSlot, (Value)this.headSlot, (Value)this.gappleSlot, (Value)this.Sword, (Value)this.Bow, (Value)this.PickAxe, (Value)this.Axe, (Value)this.Shovel, (Value)this.GoldenApple, (Value)this.Head, (Value)this.inventoryCleaner, (Value)this.open_inv, (Value)this.checkTarget, (Value)this.onlyGround, (Value)this.onlyNoMove, (Value)this.rotate, (Value)this.silentGui, (Value)this.auto_disable });
/*     */ 
/*     */     
/* 114 */     getInstance = this;
/*     */   }
/*     */   
/*     */   private boolean isBestSword(ItemStack stack) {
/* 118 */     float damage = getDamage(stack);
/*     */     
/* 120 */     for (int i = 9; i < 45; i++) {
/* 121 */       if (mc.thePlayer.getSlotFromPlayerContainer(i).getHasStack()) {
/* 122 */         ItemStack is = mc.thePlayer.getSlotFromPlayerContainer(i).getStack();
/* 123 */         if (getDamage(is) > damage && is.getItem() instanceof ItemSword) {
/* 124 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 129 */     return stack.getItem() instanceof ItemSword;
/*     */   }
/*     */   
/*     */   private boolean isHead(ItemStack stack) {
/* 133 */     return (stack.getItem() instanceof net.minecraft.item.ItemSkull && stack.getDisplayName().contains("Head") && 
/* 134 */       !stack.getDisplayName().equalsIgnoreCase("Wither Skeleton Skull") && 
/* 135 */       !stack.getDisplayName().equalsIgnoreCase("Zombie Head") && !stack.getDisplayName().equalsIgnoreCase("Creeper Head") && 
/* 136 */       !stack.getDisplayName().equalsIgnoreCase("Skeleton Skull"));
/*     */   }
/*     */   
/*     */   private boolean isGoldenApple(ItemStack stack) {
/* 140 */     return stack.getItem() instanceof net.minecraft.item.ItemAppleGold;
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onWorldChange(LoadWorldEvent e) {
/* 145 */     this.invOpen = false;
/* 146 */     this.lastInvOpen = false;
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onPacket(PacketEvent event) {
/* 151 */     Packet<?> packet = event.getPacket();
/* 152 */     this.lastInvOpen = this.invOpen;
/* 153 */     if (packet instanceof net.minecraft.network.play.server.S2DPacketOpenWindow || (packet instanceof C16PacketClientStatus && ((C16PacketClientStatus)packet).getStatus() == C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT))
/*     */     {
/* 155 */       this.invOpen = true;
/*     */     }
/* 157 */     if (packet instanceof net.minecraft.network.play.server.S2EPacketCloseWindow || packet instanceof net.minecraft.network.play.client.C0DPacketCloseWindow) {
/* 158 */       this.invOpen = false;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onUpdate(EventPreUpdate e) {
/* 165 */     if (((Boolean)this.auto_disable.getValue()).booleanValue()) {
/* 166 */       if (!mc.thePlayer.isEntityAlive()) {
/* 167 */         ClientNotification.sendClientMessage(getHUDName(), "Auto disable due to respawn", 3000L, ClientNotification.Type.WARNING);
/*     */         
/* 169 */         setEnabled(false);
/*     */         return;
/*     */       } 
/* 172 */       if (mc.thePlayer.ticksExisted <= 1) {
/* 173 */         ClientNotification.sendClientMessage(getHUDName(), "Auto disable due to respawn", 3000L, ClientNotification.Type.WARNING);
/* 174 */         setEnabled(false);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onDisplayGuiChest(DisplayChestGuiEvent event) {
/* 181 */     if (((Boolean)this.silentGui.get()).booleanValue() && 
/* 182 */       mc.currentScreen instanceof net.minecraft.client.gui.inventory.GuiInventory) {
/* 183 */       mc.displayGuiScreen(null);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void on2D(EventRender2D event) {
/* 190 */     if (((Boolean)this.silentGui.get()).booleanValue() && mc.currentScreen instanceof net.minecraft.client.gui.inventory.GuiInventory) {
/* 191 */       ScaledResolution sr = event.getResolution();
/* 192 */       Client.instance.FontLoaders.regular18.drawCenteredStringWithShadow("Inventory Manager ...", (sr
/* 193 */           .getScaledWidth() / 2), (sr.getScaledHeight() / 2), -1);
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onPreUpdate(MotionUpdateEvent event) {
/* 199 */     if (((Boolean)this.checkTarget.get()).booleanValue() && 
/* 200 */       KillAura.getInstance.getTarget() != null) {
/*     */       return;
/*     */     }
/*     */     
/* 204 */     if (((Boolean)this.onlyNoMove.get()).booleanValue() && isMoving()) {
/*     */       return;
/*     */     }
/* 207 */     if (((Boolean)this.onlyGround.get()).booleanValue() && !mc.thePlayer.onGround) {
/*     */       return;
/*     */     }
/* 210 */     if (event.getState().equals(MotionUpdateEvent.State.PRE)) {
/* 211 */       if ((!(mc.currentScreen instanceof net.minecraft.client.gui.inventory.GuiInventory) && ((Boolean)this.open_inv.get()).booleanValue()) || (AutoArmor.getInstance
/* 212 */         .isEnabled() && AutoArmor.getInstance
/* 213 */         .isWorking()) || ChestStealer.getInstance.isStealing()) {
/*     */         return;
/*     */       }
/*     */       
/* 217 */       if (mc.currentScreen == null || mc.currentScreen instanceof net.minecraft.client.gui.inventory.GuiInventory || mc.currentScreen instanceof net.minecraft.client.gui.GuiChat) {
/* 218 */         int swordSlot = (int)(((Double)this.swordSlot.get()).doubleValue() - 1.0D), pickAxeSlot = (int)(((Double)this.pickAxeSlot.get()).doubleValue() - 1.0D);
/* 219 */         int bowSlot = (int)(((Double)this.bowSlot.get()).doubleValue() - 1.0D), shovelSlot = (int)(((Double)this.shovelSlot.get()).doubleValue() - 1.0D);
/* 220 */         int axeSlot = (int)(((Double)this.axeSlot.get()).doubleValue() - 1.0D), headSlot = (int)(((Double)this.headSlot.get()).doubleValue() - 1.0D), gappleSlot = (int)(((Double)this.gappleSlot.get()).doubleValue() - 1.0D);
/*     */         
/* 222 */         boolean pickAxe = ((Boolean)this.PickAxe.get()).booleanValue(), shovel = ((Boolean)this.Shovel.get()).booleanValue();
/* 223 */         boolean axe = ((Boolean)this.Axe.get()).booleanValue(), sword = ((Boolean)this.Sword.get()).booleanValue();
/* 224 */         boolean bow = ((Boolean)this.Bow.get()).booleanValue(), head = ((Boolean)this.Head.get()).booleanValue(), gapple = ((Boolean)this.GoldenApple.get()).booleanValue();
/*     */         
/* 226 */         int tickDelay = ((Double)this.delay.get()).intValue() * 50;
/*     */         int slotIndex;
/* 228 */         for (slotIndex = 9; slotIndex < 45; slotIndex++) {
/* 229 */           ItemStack stack = mc.thePlayer.getSlotFromPlayerContainer(slotIndex).getStack();
/* 230 */           if (stack != null && 
/* 231 */             this.timer.check(tickDelay)) {
/* 232 */             if (isBestSword(stack) && sword && shouldSwap(swordSlot)[0]) {
/* 233 */               mc.thePlayer.swap(slotIndex, swordSlot);
/* 234 */               this.timer.reset();
/* 235 */             } else if (isBestPickaxe(stack) && pickAxe && shouldSwap(pickAxeSlot)[2]) {
/* 236 */               mc.thePlayer.swap(slotIndex, pickAxeSlot);
/* 237 */               this.timer.reset();
/* 238 */             } else if (isBestAxe(stack) && axe && shouldSwap(axeSlot)[1]) {
/* 239 */               mc.thePlayer.swap(slotIndex, axeSlot);
/* 240 */               this.timer.reset();
/* 241 */             } else if (isBestBow(stack) && bow && shouldSwap(bowSlot)[5] && !stack.getDisplayName().toLowerCase().contains("kit selector")) {
/* 242 */               mc.thePlayer.swap(slotIndex, bowSlot);
/* 243 */               this.timer.reset();
/* 244 */             } else if (isHead(stack) && head && shouldSwap(headSlot)[4]) {
/* 245 */               mc.thePlayer.swap(slotIndex, headSlot);
/* 246 */               this.timer.reset();
/* 247 */             } else if (isBestShovel(stack) && shovel && shouldSwap(shovelSlot)[3]) {
/* 248 */               mc.thePlayer.swap(slotIndex, shovelSlot);
/* 249 */               this.timer.reset();
/* 250 */             } else if (isGoldenApple(stack) && gapple && shouldSwap(gappleSlot)[6]) {
/* 251 */               mc.thePlayer.swap(slotIndex, gappleSlot);
/* 252 */               this.timer.reset();
/*     */             } 
/*     */           }
/*     */         } 
/*     */ 
/*     */         
/* 258 */         for (slotIndex = 9; slotIndex < 45; slotIndex++) {
/* 259 */           if (mc.thePlayer.getSlotFromPlayerContainer(slotIndex).getHasStack()) {
/*     */ 
/*     */ 
/*     */             
/* 263 */             ItemStack stack = mc.thePlayer.getSlotFromPlayerContainer(slotIndex).getStack();
/*     */             
/* 265 */             if (stack != null && 
/* 266 */               shouldDrop(stack) && ((Boolean)this.inventoryCleaner.get()).booleanValue()) {
/* 267 */               if (((Boolean)this.rotate.get()).booleanValue() && 
/* 268 */                 checkWorking()) {
/* 269 */                 float moveDir = MoveUtils.INSTANCE.getMovementDirection(mc.thePlayer.moveForward, mc.thePlayer.moveStrafing, mc.thePlayer.rotationYaw);
/*     */ 
/*     */ 
/*     */                 
/* 273 */                 this.angles = new float[] { moveDir + 90.0F, 40.0F };
/* 274 */                 event.setYaw(this.angles[0]);
/* 275 */                 event.setPitch(this.angles[1]);
/*     */               } 
/*     */               
/* 278 */               if (this.timer.delay(tickDelay)) {
/* 279 */                 mc.thePlayer.drop(slotIndex);
/* 280 */                 this.timer.reset();
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean checkWorking() {
/* 290 */     if (Scaffold.getInstance.isEnabled()) {
/* 291 */       return false;
/*     */     }
/* 293 */     if (KillAura.getInstance.isEnabled() && KillAura.getInstance.getTarget() != null) {
/* 294 */       return false;
/*     */     }
/* 296 */     return true;
/*     */   }
/*     */   
/*     */   private boolean[] shouldSwap(int slot) {
/* 300 */     return new boolean[] { (
/* 301 */         !mc.thePlayer.getSlotFromPlayerContainer(slot + 36).getHasStack() || !isBestSword(mc.thePlayer.getSlotFromPlayerContainer(slot + 36).getStack())), (
/* 302 */         !mc.thePlayer.getSlotFromPlayerContainer(slot + 36).getHasStack() || !isBestAxe(mc.thePlayer.getSlotFromPlayerContainer(slot + 36).getStack())), (
/* 303 */         !mc.thePlayer.getSlotFromPlayerContainer(slot + 36).getHasStack() || !isBestPickaxe(mc.thePlayer.getSlotFromPlayerContainer(slot + 36).getStack())), (
/* 304 */         !mc.thePlayer.getSlotFromPlayerContainer(slot + 36).getHasStack() || !isBestShovel(mc.thePlayer.getSlotFromPlayerContainer(slot + 36).getStack())), (
/* 305 */         !mc.thePlayer.getSlotFromPlayerContainer(slot + 36).getHasStack() || !isHead(mc.thePlayer.getSlotFromPlayerContainer(slot + 36).getStack())), (
/* 306 */         !mc.thePlayer.getSlotFromPlayerContainer(slot + 36).getHasStack() || !isBestBow(mc.thePlayer.getSlotFromPlayerContainer(slot + 36).getStack())), (
/* 307 */         !mc.thePlayer.getSlotFromPlayerContainer(slot + 36).getHasStack() || !isGoldenApple(mc.thePlayer.getSlotFromPlayerContainer(slot + 36).getStack())) };
/*     */   }
/*     */   
/*     */   public boolean isWorking() {
/* 311 */     return !this.timer.check((float)(((Double)this.delay.get()).doubleValue() * 150.0D));
/*     */   }
/*     */   
/*     */   private float getDamage(ItemStack stack) {
/* 315 */     float damage = 0.0F;
/* 316 */     Item item = stack.getItem();
/*     */     
/* 318 */     if (item instanceof ItemTool) {
/* 319 */       damage += ((ItemTool)item).getDamage();
/* 320 */     } else if (item instanceof ItemSword) {
/* 321 */       damage += ((ItemSword)item).getAttackDamage();
/*     */     } 
/*     */     
/* 324 */     damage += EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, stack) * 1.25F + EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, stack) * 0.01F;
/*     */     
/* 326 */     return damage;
/*     */   }
/*     */   
/*     */   private int getBlocksCounter() {
/* 330 */     int blockCount = 0;
/*     */     
/* 332 */     for (int i = 0; i < 45; i++) {
/* 333 */       if (mc.thePlayer.getSlotFromPlayerContainer(i).getHasStack()) {
/* 334 */         ItemStack stack = mc.thePlayer.getSlotFromPlayerContainer(i).getStack();
/* 335 */         Item item = stack.getItem();
/*     */         
/* 337 */         if (stack.getItem() instanceof ItemBlock && !this.blacklistedBlocks2.contains(((ItemBlock)item).getBlock())) {
/* 338 */           blockCount += stack.stackSize;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 343 */     return blockCount;
/*     */   }
/*     */   
/*     */   private int getArrowsCounter() {
/* 347 */     int arrowCount = 0;
/*     */     
/* 349 */     for (int i = 0; i < 45; i++) {
/* 350 */       if (mc.thePlayer.getSlotFromPlayerContainer(i).getHasStack()) {
/* 351 */         ItemStack is = mc.thePlayer.getSlotFromPlayerContainer(i).getStack();
/* 352 */         if (is.getItem() == Items.arrow) arrowCount += is.stackSize;
/*     */       
/*     */       } 
/*     */     } 
/* 356 */     return arrowCount;
/*     */   }
/*     */   
/*     */   private int getIronIngotsCounter() {
/* 360 */     int count = 0;
/*     */     
/* 362 */     for (int i = 0; i < 45; i++) {
/* 363 */       if (mc.thePlayer.getSlotFromPlayerContainer(i).getHasStack()) {
/* 364 */         ItemStack stack = mc.thePlayer.getSlotFromPlayerContainer(i).getStack();
/* 365 */         if (stack.getItem() == Items.iron_ingot) count += stack.stackSize;
/*     */       
/*     */       } 
/*     */     } 
/* 369 */     return count;
/*     */   }
/*     */   
/*     */   private int getCoalCounter() {
/* 373 */     int count = 0;
/*     */     
/* 375 */     for (int i = 0; i < 45; i++) {
/* 376 */       if (mc.thePlayer.getSlotFromPlayerContainer(i).getHasStack()) {
/* 377 */         ItemStack stack = mc.thePlayer.getSlotFromPlayerContainer(i).getStack();
/* 378 */         if (stack.getItem() == Items.coal) count += stack.stackSize;
/*     */       
/*     */       } 
/*     */     } 
/* 382 */     return count;
/*     */   }
/*     */   
/*     */   private int getSwordsCounter() {
/* 386 */     int count = 0;
/*     */     
/* 388 */     for (int i = 0; i < 45; i++) {
/* 389 */       if (mc.thePlayer.getSlotFromPlayerContainer(i).getHasStack()) {
/* 390 */         ItemStack stack = mc.thePlayer.getSlotFromPlayerContainer(i).getStack();
/* 391 */         if (stack.getItem() instanceof ItemSword && isBestSword(stack)) count += stack.stackSize;
/*     */       
/*     */       } 
/*     */     } 
/* 395 */     return count;
/*     */   }
/*     */   
/*     */   private int getBowsCounter() {
/* 399 */     int count = 0;
/*     */     
/* 401 */     for (int i = 0; i < 45; i++) {
/* 402 */       if (mc.thePlayer.getSlotFromPlayerContainer(i).getHasStack()) {
/* 403 */         ItemStack stack = mc.thePlayer.getSlotFromPlayerContainer(i).getStack();
/* 404 */         if (stack.getItem() instanceof net.minecraft.item.ItemBow && isBestBow(stack)) count += stack.stackSize;
/*     */       
/*     */       } 
/*     */     } 
/* 408 */     return count;
/*     */   }
/*     */   
/*     */   private int getPickaxexCounter() {
/* 412 */     int count = 0;
/*     */     
/* 414 */     for (int i = 0; i < 45; i++) {
/* 415 */       if (mc.thePlayer.getSlotFromPlayerContainer(i).getHasStack()) {
/* 416 */         ItemStack stack = mc.thePlayer.getSlotFromPlayerContainer(i).getStack();
/* 417 */         if (stack.getItem() instanceof net.minecraft.item.ItemPickaxe && isBestPickaxe(stack)) count += stack.stackSize;
/*     */       
/*     */       } 
/*     */     } 
/* 421 */     return count;
/*     */   }
/*     */   
/*     */   private int getAxesCounter() {
/* 425 */     int count = 0;
/*     */     
/* 427 */     for (int i = 0; i < 45; i++) {
/* 428 */       if (mc.thePlayer.getSlotFromPlayerContainer(i).getHasStack()) {
/* 429 */         ItemStack stack = mc.thePlayer.getSlotFromPlayerContainer(i).getStack();
/* 430 */         if (stack.getItem() instanceof net.minecraft.item.ItemAxe && isBestAxe(stack)) count += stack.stackSize;
/*     */       
/*     */       } 
/*     */     } 
/* 434 */     return count;
/*     */   }
/*     */   
/*     */   private int getHeadsCounter() {
/* 438 */     int count = 0;
/*     */     
/* 440 */     for (int i = 0; i < 45; i++) {
/* 441 */       if (mc.thePlayer.getSlotFromPlayerContainer(i).getHasStack()) {
/* 442 */         ItemStack stack = mc.thePlayer.getSlotFromPlayerContainer(i).getStack();
/* 443 */         if (stack.getItem() instanceof net.minecraft.item.ItemSkull && isBestShovel(stack)) count += stack.stackSize;
/*     */       
/*     */       } 
/*     */     } 
/* 447 */     return count;
/*     */   }
/*     */   
/*     */   private int getShovelsCounter() {
/* 451 */     int count = 0;
/*     */     
/* 453 */     for (int i = 0; i < 45; i++) {
/* 454 */       if (mc.thePlayer.getSlotFromPlayerContainer(i).getHasStack()) {
/* 455 */         ItemStack stack = mc.thePlayer.getSlotFromPlayerContainer(i).getStack();
/* 456 */         if (stack.getItem() instanceof net.minecraft.item.ItemSpade && isBestShovel(stack)) count += stack.stackSize;
/*     */       
/*     */       } 
/*     */     } 
/* 460 */     return count;
/*     */   }
/*     */   
/*     */   private boolean isBestPickaxe(ItemStack stack) {
/* 464 */     Item item = stack.getItem();
/*     */     
/* 466 */     if (!(item instanceof net.minecraft.item.ItemPickaxe)) {
/* 467 */       return false;
/*     */     }
/*     */     
/* 470 */     float value = getToolEffect(stack);
/*     */     
/* 472 */     for (int i = 9; i < 45; i++) {
/* 473 */       if (mc.thePlayer.getSlotFromPlayerContainer(i).getHasStack()) {
/* 474 */         ItemStack slotStack = mc.thePlayer.getSlotFromPlayerContainer(i).getStack();
/* 475 */         if (getToolEffect(slotStack) > value && slotStack.getItem() instanceof net.minecraft.item.ItemPickaxe) return false;
/*     */       
/*     */       } 
/*     */     } 
/* 479 */     return true;
/*     */   }
/*     */   
/*     */   private float getProtection(ItemStack stack) {
/* 483 */     float protection = 0.0F;
/*     */     
/* 485 */     if (stack.getItem() instanceof ItemArmor) {
/* 486 */       ItemArmor armor = (ItemArmor)stack.getItem();
/*     */       
/* 488 */       protection = (float)(protection + armor.damageReduceAmount + ((100 - armor.damageReduceAmount) * EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, stack)) * 0.0075D);
/*     */       
/* 490 */       protection = (float)(protection + EnchantmentHelper.getEnchantmentLevel(Enchantment.blastProtection.effectId, stack) / 100.0D);
/* 491 */       protection = (float)(protection + EnchantmentHelper.getEnchantmentLevel(Enchantment.fireProtection.effectId, stack) / 100.0D);
/* 492 */       protection = (float)(protection + EnchantmentHelper.getEnchantmentLevel(Enchantment.thorns.effectId, stack) / 100.0D);
/* 493 */       protection = (float)(protection + EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack) / 50.0D);
/* 494 */       protection = (float)(protection + EnchantmentHelper.getEnchantmentLevel(Enchantment.projectileProtection.effectId, stack) / 100.0D);
/*     */     } 
/*     */     
/* 497 */     return protection;
/*     */   }
/*     */   
/*     */   public boolean isBestArmor(ItemStack stack, int type) {
/* 501 */     String strType = "";
/*     */     
/* 503 */     switch (type) {
/*     */       case 1:
/* 505 */         strType = "helmet";
/*     */         break;
/*     */       case 2:
/* 508 */         strType = "chestplate";
/*     */         break;
/*     */       case 3:
/* 511 */         strType = "leggings";
/*     */         break;
/*     */       case 4:
/* 514 */         strType = "boots";
/*     */         break;
/*     */     } 
/*     */     
/* 518 */     if (!stack.getUnlocalizedName().contains(strType)) {
/* 519 */       return false;
/*     */     }
/*     */     
/* 522 */     float protection = getProtection(stack);
/*     */     
/* 524 */     for (int i = 5; i < 45; i++) {
/* 525 */       if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
/* 526 */         ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
/* 527 */         if (getProtection(is) > protection && is.getUnlocalizedName().contains(strType)) return false;
/*     */       
/*     */       } 
/*     */     } 
/* 531 */     return true;
/*     */   }
/*     */   
/*     */   private boolean shouldDrop(ItemStack stack) {
/* 535 */     Item item = stack.getItem();
/* 536 */     String displayName = stack.getDisplayName();
/* 537 */     int idFromItem = Item.getIdFromItem(item);
/*     */     
/* 539 */     if (idFromItem == 58 || displayName.toLowerCase().contains(EnumChatFormatting.OBFUSCATED + "||") || displayName
/* 540 */       .contains(EnumChatFormatting.GREEN + "Game Menu " + EnumChatFormatting.GRAY + "(Right Click)") || displayName
/* 541 */       .equalsIgnoreCase(EnumChatFormatting.AQUA + "" + EnumChatFormatting.BOLD + "Spectator Settings " + EnumChatFormatting.GRAY + "(Right Click)") || displayName
/* 542 */       .equalsIgnoreCase(EnumChatFormatting.AQUA + "" + EnumChatFormatting.BOLD + "Play Again " + EnumChatFormatting.GRAY + "(Right Click)") || displayName
/* 543 */       .equalsIgnoreCase(EnumChatFormatting.GREEN + "" + EnumChatFormatting.BOLD + "Teleporter " + EnumChatFormatting.GRAY + "(Right Click)") || displayName
/* 544 */       .equalsIgnoreCase(EnumChatFormatting.GREEN + "SkyWars Challenges " + EnumChatFormatting.GRAY + "(Right Click)") || displayName
/* 545 */       .equalsIgnoreCase(EnumChatFormatting.GREEN + "Collectibles " + EnumChatFormatting.GRAY + "(Right Click)") || displayName
/* 546 */       .equalsIgnoreCase(EnumChatFormatting.GREEN + "Kit Selector " + EnumChatFormatting.GRAY + "(Right Click)") || displayName
/* 547 */       .equalsIgnoreCase(EnumChatFormatting.GREEN + "Kill Effect Selector " + EnumChatFormatting.GRAY + "(Right Click)") || displayName
/* 548 */       .equalsIgnoreCase(EnumChatFormatting.WHITE + "Players: " + EnumChatFormatting.RED + "Hidden " + EnumChatFormatting.GRAY + "(Right Click)") || displayName
/* 549 */       .equalsIgnoreCase(EnumChatFormatting.GREEN + "Shop " + EnumChatFormatting.GRAY + "(Right Click)") || displayName
/* 550 */       .equalsIgnoreCase(EnumChatFormatting.WHITE + "Players: " + EnumChatFormatting.RED + "Visible " + EnumChatFormatting.GRAY + "(Right Click)") || displayName
/* 551 */       .equalsIgnoreCase(EnumChatFormatting.GOLD + "Excalibur") || displayName.equalsIgnoreCase("aDragon Sword") || displayName
/* 552 */       .equalsIgnoreCase(EnumChatFormatting.GREEN + "Cornucopia") || displayName
/* 553 */       .equalsIgnoreCase(EnumChatFormatting.RED + "Bloodlust") || displayName.equalsIgnoreCase(EnumChatFormatting.RED + "Artemis' Bow") || displayName
/* 554 */       .equalsIgnoreCase(EnumChatFormatting.GREEN + "Miner's Blessing") || displayName.equalsIgnoreCase(EnumChatFormatting.GOLD + "Axe of Perun") || displayName
/* 555 */       .equalsIgnoreCase(EnumChatFormatting.GOLD + "Cornucopia") || idFromItem == 116 || idFromItem == 145 || ((idFromItem == 15 || idFromItem == 14) && ((Boolean)this.Ores
/* 556 */       .get()).booleanValue()) || displayName.equalsIgnoreCase("§aAndúril") || idFromItem == 259 || idFromItem == 46)
/*     */     {
/* 558 */       return false;
/*     */     }
/*     */     
/* 561 */     boolean pickAxe = ((Boolean)this.PickAxe.get()).booleanValue(), shovel = ((Boolean)this.Shovel.get()).booleanValue();
/* 562 */     boolean axe = ((Boolean)this.Axe.get()).booleanValue(), sword = ((Boolean)this.Sword.get()).booleanValue();
/* 563 */     boolean bow = ((Boolean)this.Bow.get()).booleanValue(), head = ((Boolean)this.Head.get()).booleanValue();
/* 564 */     int swordSlot = (int)(((Double)this.swordSlot.get()).doubleValue() - 1.0D), pickAxeSlot = (int)(((Double)this.pickAxeSlot.get()).doubleValue() - 1.0D);
/* 565 */     int bowSlot = (int)(((Double)this.bowSlot.get()).doubleValue() - 1.0D), shovelSlot = (int)(((Double)this.shovelSlot.get()).doubleValue() - 1.0D);
/* 566 */     int axeSlot = (int)(((Double)this.axeSlot.get()).doubleValue() - 1.0D), headSlot = (int)(((Double)this.headSlot.get()).doubleValue() - 1.0D);
/*     */     
/* 568 */     if ((((isBestShovel(stack) && getShovelsCounter() < 2) || (stack.getItem() instanceof net.minecraft.item.ItemSpade && stack == mc.thePlayer.inventory.getStackInSlot(shovelSlot))) && shovel) || (((
/* 569 */       isBestBow(stack) && getBowsCounter() < 2) || (stack.getItem() instanceof net.minecraft.item.ItemBow && stack == mc.thePlayer.inventory.getStackInSlot(bowSlot))) && bow) || (((
/* 570 */       isHead(stack) && getHeadsCounter() < 2) || (stack.getItem() instanceof net.minecraft.item.ItemSkull && stack == mc.thePlayer.inventory.getStackInSlot(headSlot))) && head) || (((
/* 571 */       isBestAxe(stack) && getAxesCounter() < 2) || (stack.getItem() instanceof net.minecraft.item.ItemAxe && stack == mc.thePlayer.inventory.getStackInSlot(axeSlot))) && axe) || (((
/* 572 */       isBestPickaxe(stack) && getPickaxexCounter() < 2) || (stack.getItem() instanceof net.minecraft.item.ItemPickaxe && stack == mc.thePlayer.inventory.getStackInSlot(pickAxeSlot))) && pickAxe) || (((
/* 573 */       isBestSword(stack) && getSwordsCounter() < 2) || (stack.getItem() instanceof ItemSword && stack == mc.thePlayer.inventory.getStackInSlot(swordSlot))) && sword)) {
/* 574 */       return false;
/*     */     }
/*     */     
/* 577 */     if (item instanceof ItemArmor)
/* 578 */       for (int type = 1; type < 5; type++) {
/* 579 */         if (mc.thePlayer.getSlotFromPlayerContainer(4 + type).getHasStack()) {
/* 580 */           ItemStack slotStack = mc.thePlayer.getSlotFromPlayerContainer(4 + type).getStack();
/* 581 */           if (isBestArmor(slotStack, type))
/*     */             continue; 
/*     */         } 
/* 584 */         if (isBestArmor(stack, type)) return false;
/*     */         
/*     */         continue;
/*     */       }  
/* 588 */     if ((item instanceof ItemBlock && (getBlocksCounter() > ((Double)this.blocks.get()).doubleValue() || this.blacklistedBlocks2
/* 589 */       .contains(((ItemBlock)item).getBlock()))) || (item instanceof ItemPotion && 
/* 590 */       isBadPotion(stack)) || (item instanceof net.minecraft.item.ItemFood && !(item instanceof net.minecraft.item.ItemAppleGold) && item != Items.bread && item != Items.pumpkin_pie && item != Items.baked_potato && item != Items.cooked_chicken && item != Items.carrot && item != Items.apple && item != Items.beef && item != Items.cooked_beef && item != Items.porkchop && item != Items.cooked_porkchop && item != Items.mushroom_stew && item != Items.cooked_fish && item != Items.melon) || item instanceof net.minecraft.item.ItemHoe || item instanceof ItemTool || item instanceof ItemSword || item instanceof ItemArmor)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 597 */       return true;
/*     */     }
/*     */     
/* 600 */     String unlocalizedName = item.getUnlocalizedName();
/*     */     
/* 602 */     return ((!((Boolean)this.Sticks.get()).booleanValue() && unlocalizedName.contains("stick")) || unlocalizedName.contains("egg") || (
/* 603 */       getIronIngotsCounter() > 64 && item == Items.iron_ingot) || (getCoalCounter() > 64 && item == Items.coal) || unlocalizedName
/* 604 */       .contains("string") || unlocalizedName.contains("flint") || unlocalizedName
/* 605 */       .contains("compass") || unlocalizedName.contains("dyePowder") || unlocalizedName
/* 606 */       .contains("feather") || (unlocalizedName
/* 607 */       .contains("chest") && !displayName.toLowerCase().contains("collect")) || unlocalizedName
/* 608 */       .contains("snow") || unlocalizedName.contains("torch") || unlocalizedName
/* 609 */       .contains("seeds") || unlocalizedName.contains("leather") || unlocalizedName
/* 610 */       .contains("reeds") || unlocalizedName.contains("record") || unlocalizedName
/* 611 */       .contains("snowball") || item instanceof net.minecraft.item.ItemGlassBottle || item instanceof net.minecraft.item.ItemSlab || idFromItem == 113 || idFromItem == 106 || idFromItem == 325 || (idFromItem == 326 && 
/*     */       
/* 613 */       !((Boolean)this.Buckets.get()).booleanValue()) || idFromItem == 327 || idFromItem == 111 || idFromItem == 85 || idFromItem == 188 || idFromItem == 189 || idFromItem == 190 || idFromItem == 191 || idFromItem == 401 || idFromItem == 192 || idFromItem == 81 || idFromItem == 32 || unlocalizedName
/*     */ 
/*     */ 
/*     */       
/* 617 */       .contains("gravel") || unlocalizedName
/* 618 */       .contains("flower") || unlocalizedName.contains("tallgrass") || item instanceof net.minecraft.item.ItemBow || (item == Items.arrow && 
/* 619 */       getArrowsCounter() > (((Boolean)this.Bow.get()).booleanValue() ? ((Double)this.arrows.get()).doubleValue() : 0.0D)) || idFromItem == 175 || idFromItem == 340 || idFromItem == 339 || idFromItem == 160 || idFromItem == 101 || idFromItem == 102 || idFromItem == 321 || idFromItem == 323 || idFromItem == 389 || idFromItem == 416 || idFromItem == 171 || idFromItem == 139 || idFromItem == 23 || idFromItem == 25 || idFromItem == 69 || idFromItem == 70 || idFromItem == 72 || idFromItem == 77 || idFromItem == 96 || idFromItem == 107 || idFromItem == 123 || idFromItem == 131 || idFromItem == 143 || idFromItem == 147 || idFromItem == 148 || idFromItem == 151 || idFromItem == 152 || idFromItem == 154 || idFromItem == 158 || idFromItem == 167 || idFromItem == 403 || idFromItem == 183 || idFromItem == 184 || idFromItem == 185 || idFromItem == 186 || idFromItem == 187 || idFromItem == 331 || idFromItem == 356 || idFromItem == 404 || idFromItem == 27 || idFromItem == 28 || idFromItem == 66 || idFromItem == 76 || idFromItem == 157 || idFromItem == 328 || idFromItem == 342 || idFromItem == 343 || idFromItem == 398 || idFromItem == 407 || idFromItem == 408 || idFromItem == 138 || idFromItem == 352 || idFromItem == 385 || idFromItem == 386 || idFromItem == 395 || idFromItem == 402 || idFromItem == 418 || idFromItem == 419 || idFromItem == 281 || idFromItem == 289 || idFromItem == 337 || idFromItem == 336 || idFromItem == 348 || idFromItem == 353 || idFromItem == 369 || idFromItem == 372 || idFromItem == 405 || idFromItem == 406 || idFromItem == 409 || idFromItem == 410 || idFromItem == 415 || idFromItem == 370 || idFromItem == 376 || idFromItem == 377 || idFromItem == 378 || idFromItem == 379 || idFromItem == 380 || idFromItem == 382 || idFromItem == 414 || idFromItem == 346 || idFromItem == 347 || idFromItem == 420 || idFromItem == 397 || idFromItem == 421 || idFromItem == 341 || unlocalizedName
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 650 */       .contains("sapling") || unlocalizedName.contains("stairs") || unlocalizedName
/* 651 */       .contains("door") || unlocalizedName.contains("monster_egg") || unlocalizedName
/* 652 */       .contains("sand") || unlocalizedName.contains("piston"));
/*     */   }
/*     */   
/*     */   private boolean isBestShovel(ItemStack stack) {
/* 656 */     Item item = stack.getItem();
/*     */     
/* 658 */     if (!(item instanceof net.minecraft.item.ItemSpade)) {
/* 659 */       return false;
/*     */     }
/*     */     
/* 662 */     float value = getToolEffect(stack);
/*     */     
/* 664 */     for (int i = 9; i < 45; i++) {
/* 665 */       if (mc.thePlayer.getSlotFromPlayerContainer(i).getHasStack()) {
/* 666 */         ItemStack is = mc.thePlayer.getSlotFromPlayerContainer(i).getStack();
/* 667 */         if (getToolEffect(is) > value && is.getItem() instanceof net.minecraft.item.ItemSpade) return false;
/*     */       
/*     */       } 
/*     */     } 
/* 671 */     return true;
/*     */   }
/*     */   
/*     */   private boolean isBestAxe(ItemStack stack) {
/* 675 */     Item item = stack.getItem();
/*     */     
/* 677 */     if (!(item instanceof net.minecraft.item.ItemAxe)) {
/* 678 */       return false;
/*     */     }
/*     */     
/* 681 */     float value = getToolEffect(stack);
/*     */     
/* 683 */     for (int i = 9; i < 45; i++) {
/* 684 */       if (mc.thePlayer.getSlotFromPlayerContainer(i).getHasStack()) {
/* 685 */         ItemStack is = mc.thePlayer.getSlotFromPlayerContainer(i).getStack();
/* 686 */         if (getToolEffect(is) > value && is.getItem() instanceof net.minecraft.item.ItemAxe && !isBestSword(stack)) return false;
/*     */       
/*     */       } 
/*     */     } 
/* 690 */     return true;
/*     */   }
/*     */   
/*     */   private float getToolEffect(ItemStack stack) {
/* 694 */     Item item = stack.getItem();
/*     */     
/* 696 */     if (!(item instanceof ItemTool)) {
/* 697 */       return 0.0F;
/*     */     }
/*     */     
/* 700 */     String name = item.getUnlocalizedName();
/* 701 */     ItemTool tool = (ItemTool)item;
/*     */ 
/*     */     
/* 704 */     if (item instanceof net.minecraft.item.ItemPickaxe)
/* 705 */     { value = tool.getStrVsBlock(stack, Blocks.stone);
/* 706 */       if (name.toLowerCase().contains("gold")) value -= 5.0F;  }
/* 707 */     else if (item instanceof net.minecraft.item.ItemSpade)
/* 708 */     { value = tool.getStrVsBlock(stack, Blocks.dirt);
/* 709 */       if (name.toLowerCase().contains("gold")) value -= 5.0F;  }
/* 710 */     else if (item instanceof net.minecraft.item.ItemAxe)
/* 711 */     { value = tool.getStrVsBlock(stack, Blocks.log);
/* 712 */       if (name.toLowerCase().contains("gold")) value -= 5.0F;  }
/* 713 */     else { return 1.0F; }
/*     */     
/* 715 */     float value = (float)(value + EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, stack) * 0.0075D);
/* 716 */     value = (float)(value + EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack) / 100.0D);
/*     */     
/* 718 */     return value;
/*     */   }
/*     */   
/*     */   private float getBowEffect(ItemStack stack) {
/* 722 */     return (1 + EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, stack) + EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, stack) + 
/* 723 */       EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, stack));
/*     */   }
/*     */   
/*     */   private boolean isBadPotion(ItemStack stack) {
/* 727 */     if (stack != null && stack.getItem() instanceof ItemPotion) {
/* 728 */       ItemPotion potion = (ItemPotion)stack.getItem();
/* 729 */       return (potion.getEffects(stack) == null || isBadPotionEffect(stack, potion));
/*     */     } 
/*     */     
/* 732 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isBadPotionEffect(ItemStack stack, ItemPotion pot) {
/* 736 */     for (PotionEffect effect : pot.getEffects(stack)) {
/* 737 */       Potion potion = Potion.potionTypes[effect.getPotionID()];
/*     */       
/* 739 */       if (potion.isBadEffect()) {
/* 740 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 744 */     return false;
/*     */   }
/*     */   
/*     */   private boolean isBestBow(ItemStack stack) {
/* 748 */     Item item = stack.getItem();
/* 749 */     if (!(item instanceof net.minecraft.item.ItemBow)) return false; 
/* 750 */     float value = getBowEffect(stack);
/*     */     
/* 752 */     for (int i = 9; i < 45; i++) {
/* 753 */       if (mc.thePlayer.getSlotFromPlayerContainer(i).getHasStack()) {
/* 754 */         ItemStack slotStack = mc.thePlayer.getSlotFromPlayerContainer(i).getStack();
/* 755 */         if (getBowEffect(slotStack) > value && slotStack.getItem() instanceof net.minecraft.item.ItemBow) return false;
/*     */       
/*     */       } 
/*     */     } 
/* 759 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisable() {
/* 764 */     this.timer.reset();
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\player\InventoryManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */