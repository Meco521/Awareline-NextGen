/*     */ package awareline.main.mod.implement.world;
/*     */ import awareline.main.event.EventHandler;
/*     */ import awareline.main.event.events.display.DisplayChestGuiEvent;
/*     */ import awareline.main.event.events.key.KeyPressEvent;
/*     */ import awareline.main.event.events.world.EventTick;
/*     */ import awareline.main.event.events.world.moveEvents.EventMove;
/*     */ import awareline.main.event.events.world.packetEvents.PacketEvent;
/*     */ import awareline.main.event.events.world.renderEvents.EventRender2D;
/*     */ import awareline.main.event.events.world.updateEvents.MotionUpdateEvent;
/*     */ import awareline.main.mod.Module;
/*     */ import awareline.main.mod.implement.combat.KillAura;
/*     */ import awareline.main.mod.implement.move.InvMove;
/*     */ import awareline.main.mod.values.Numbers;
/*     */ import awareline.main.mod.values.Option;
/*     */ import awareline.main.mod.values.Value;
/*     */ import awareline.main.ui.gui.hud.notification.ClientNotification;
/*     */ import awareline.main.utility.math.MathUtil;
/*     */ import awareline.main.utility.timer.Timer;
/*     */ import awareline.main.utility.timer.TimerUtil;
/*     */ import io.netty.util.internal.ThreadLocalRandom;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.CopyOnWriteArrayList;
/*     */ import java.util.stream.Collectors;
/*     */ import java.util.stream.Stream;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.enchantment.Enchantment;
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemArmor;
/*     */ import net.minecraft.item.ItemPotion;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.item.ItemSword;
/*     */ import net.minecraft.item.ItemTool;
/*     */ import net.minecraft.network.play.server.S2DPacketOpenWindow;
/*     */ import net.minecraft.network.play.server.S30PacketWindowItems;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityChest;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.StringUtils;
/*     */ import net.minecraft.util.Vec3;
/*     */ 
/*     */ public class ChestStealer extends Module {
/*  51 */   public final Numbers<Double> maxdelay = new Numbers("MaxDelay", Double.valueOf(150.0D), Double.valueOf(0.0D), Double.valueOf(1000.0D), Double.valueOf(10.0D)); public static ChestStealer getInstance;
/*  52 */   public final Numbers<Double> mindelay = new Numbers("MinDelay", Double.valueOf(150.0D), Double.valueOf(0.0D), Double.valueOf(1000.0D), Double.valueOf(10.0D));
/*  53 */   public final Option<Boolean> combatSpoof = new Option("CombatSpoof", Boolean.valueOf(false));
/*  54 */   public final Option<Boolean> aura = new Option("Aura", Boolean.valueOf(false));
/*  55 */   public final Numbers<Double> aura_range = new Numbers("AuraRange", Double.valueOf(4.0D), Double.valueOf(1.0D), Double.valueOf(5.0D), Double.valueOf(0.5D));
/*  56 */   public final Option<Boolean> silent = new Option("Silent", Boolean.valueOf(false));
/*  57 */   public final Option<Boolean> onlyNoMove = new Option("OnlyNoMove", Boolean.valueOf(false));
/*  58 */   public final Option<Boolean> ignore = new Option("Ignore", Boolean.valueOf(true));
/*  59 */   private final String[] list = new String[] { "mode", "delivery", "menu", "selector", "game", "gui", "server", "inventory", "play", "teleporter", "shop", "melee", "armor", "block", "castle", "mini", "warp", "teleport", "user", "team", "tool", "sure", "trade", "cancel", "accept", "soul", "book", "recipe", "profile", "tele", "port", "map", "kit", "select", "lobby", "vault", "lock", "anticheat", "travel", "settings", "user", "preference", "compass", "cake", "wars", "buy", "upgrade", "ranged", "potions", "utility" };
/*     */ 
/*     */ 
/*     */   
/*  63 */   private final List<Integer> containerSlots = new CopyOnWriteArrayList<>();
/*  64 */   private final List<Integer> chestIds = new CopyOnWriteArrayList<>();
/*  65 */   private final TimerUtil timer = new TimerUtil();
/*  66 */   private final Timer timerAura = new Timer();
/*  67 */   private final Option<Boolean> silentText = new Option("SilentText", Boolean.valueOf(false));
/*  68 */   private final Option<Boolean> extra_packet = new Option("ExtraPacket", Boolean.valueOf(true));
/*  69 */   private final Option<Boolean> auto_disable = new Option("AutoDisable", Boolean.valueOf(false)); boolean needStuck;
/*     */   private boolean isStealing;
/*     */   private boolean slotsFilled;
/*     */   private int containerSize;
/*     */   private int windowID;
/*     */   
/*     */   public ChestStealer() {
/*  76 */     super("ChestStealer", new String[] { "cs" }, ModuleType.World);
/*  77 */     addSettings(new Value[] { (Value)this.maxdelay, (Value)this.mindelay, (Value)this.silent, (Value)this.silentText, (Value)this.ignore, (Value)this.extra_packet, (Value)this.aura, (Value)this.aura_range, (Value)this.combatSpoof, (Value)this.onlyNoMove, (Value)this.auto_disable });
/*     */     
/*  79 */     getInstance = this;
/*     */   }
/*     */   
/*     */   public static Vec3 getVec3(BlockPos pos) {
/*  83 */     Vec3 vector = new Vec3((Vec3i)pos);
/*  84 */     EnumFacing facing = getFacingDirection(pos);
/*  85 */     double random = ThreadLocalRandom.current().nextDouble();
/*     */     
/*  87 */     if (facing == EnumFacing.NORTH) {
/*  88 */       vector.xCoord += random;
/*  89 */     } else if (facing == EnumFacing.SOUTH) {
/*  90 */       vector.xCoord += random;
/*  91 */       vector.zCoord++;
/*  92 */     } else if (facing == EnumFacing.WEST) {
/*  93 */       vector.zCoord += random;
/*  94 */     } else if (facing == EnumFacing.EAST) {
/*  95 */       vector.zCoord += random;
/*  96 */       vector.xCoord++;
/*     */     } 
/*     */     
/*  99 */     if (facing == EnumFacing.UP) {
/* 100 */       vector.xCoord += random;
/* 101 */       vector.zCoord += random;
/* 102 */       vector.yCoord++;
/*     */     } else {
/* 104 */       vector.yCoord += random;
/*     */     } 
/*     */     
/* 107 */     return vector;
/*     */   }
/*     */   
/*     */   public static EnumFacing getFacingDirection(BlockPos pos) {
/* 111 */     EnumFacing direction = null;
/* 112 */     if (!mc.theWorld.getBlockState(pos.add(0, 1, 0)).getBlock().isSolidFullCube()) {
/* 113 */       direction = EnumFacing.UP;
/*     */     }
/*     */     
/* 116 */     MovingObjectPosition rayResult = mc.theWorld.rayTraceBlocks(new Vec3(mc.thePlayer.posX, mc.thePlayer.posY + mc.thePlayer
/* 117 */           .getEyeHeight(), mc.thePlayer.posZ), new Vec3(pos
/* 118 */           .getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D));
/* 119 */     return (rayResult != null) ? rayResult.sideHit : direction;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisable() {
/* 124 */     this.slotsFilled = false;
/* 125 */     this.isStealing = false;
/* 126 */     this.containerSlots.clear();
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void on2D(EventRender2D event) {
/* 131 */     if (((Boolean)this.silent.get()).booleanValue() && this.isStealing && 
/* 132 */       !((Boolean)this.silentText.get()).booleanValue()) {
/* 133 */       ScaledResolution sr = event.getResolution();
/* 134 */       Client.instance.FontLoaders.regular18.drawCenteredStringWithShadow("Stealing chest...", (sr
/* 135 */           .getScaledWidth() / 2), (sr.getScaledHeight() / 2), -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onPacket(PacketEvent event) {
/* 142 */     if (event.getState().equals(PacketEvent.State.INCOMING)) {
/* 143 */       if (event.getPacket() instanceof S2DPacketOpenWindow) {
/* 144 */         S2DPacketOpenWindow packet = (S2DPacketOpenWindow)event.getPacket();
/* 145 */         for (String blacklisted : this.list) {
/* 146 */           if (packet.getWindowTitle().getUnformattedText().toLowerCase().contains(blacklisted)) {
/* 147 */             this.isStealing = false;
/*     */             return;
/*     */           } 
/*     */         } 
/* 151 */         this.isStealing = packet.getGuiId().equals("minecraft:chest");
/*     */         
/* 153 */         if (this.isStealing) {
/* 154 */           this.containerSize = packet.getSlotCount();
/* 155 */           this.windowID = packet.getWindowId();
/* 156 */           this.containerSlots.clear();
/* 157 */           this.slotsFilled = false;
/*     */         } 
/*     */       } 
/*     */       
/* 161 */       if (this.isStealing) {
/* 162 */         if (((Boolean)this.combatSpoof.get()).booleanValue()) {
/* 163 */           KillAura.getInstance.target = null;
/* 164 */           KillAura.getInstance.targets.clear();
/*     */         } 
/*     */         
/* 167 */         if (event.getPacket() instanceof S30PacketWindowItems) {
/* 168 */           S30PacketWindowItems packet = (S30PacketWindowItems)event.getPacket();
/* 169 */           if (packet.getWindowID() == this.windowID && 
/* 170 */             !this.slotsFilled) {
/* 171 */             for (int i = 0; i < this.containerSize; i++) {
/* 172 */               ItemStack stack = packet.getItemStacks()[i];
/*     */               
/* 174 */               if (stack != null && (!((Boolean)this.ignore.get()).booleanValue() || (isNotBad(stack) && checkArmor(stack, packet) && checkTool(stack, packet) && checkSword(stack, packet) && checkBow(stack, packet)))) {
/* 175 */                 this.containerSlots.add(Integer.valueOf(i));
/*     */               }
/*     */             } 
/*     */             
/* 179 */             this.slotsFilled = true;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onUpdate(EventPreUpdate e) {
/* 190 */     if (((Boolean)this.auto_disable.getValue()).booleanValue()) {
/* 191 */       if (!mc.thePlayer.isEntityAlive()) {
/* 192 */         ClientNotification.sendClientMessage(getHUDName(), "Auto disable due to respawn", 3000L, ClientNotification.Type.WARNING);
/*     */         
/* 194 */         setEnabled(false);
/*     */         return;
/*     */       } 
/* 197 */       if (mc.thePlayer.ticksExisted <= 1) {
/* 198 */         ClientNotification.sendClientMessage(getHUDName(), "Auto disable due to respawn", 3000L, ClientNotification.Type.WARNING);
/* 199 */         setEnabled(false);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private List<TileEntityChest> tileEntityChestList() {
/* 205 */     return (List<TileEntityChest>)mc.theWorld.getLoadedTileEntityList()
/* 206 */       .stream().filter(te -> te instanceof TileEntityChest)
/* 207 */       .map(te -> (TileEntityChest)te).filter(te -> (mc.thePlayer.getDistance(te.getPos()) <= ((Double)this.aura_range.get()).doubleValue()))
/* 208 */       .sorted(Comparator.comparing(o -> Double.valueOf(mc.thePlayer.getDistance(((TileEntityChest)o).getPos()))).reversed()).collect(Collectors.toList());
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onMotion(MotionUpdateEvent event) {
/* 213 */     if (((Boolean)this.aura.get()).booleanValue()) {
/* 214 */       if (KillAura.getInstance.target != null) {
/*     */         return;
/*     */       }
/* 217 */       if (event.getState().equals(MotionUpdateEvent.State.PRE)) {
/* 218 */         if (!this.isStealing) {
/* 219 */           for (TileEntityChest chest : tileEntityChestList()) {
/* 220 */             int id = Integer.parseInt(StringUtils.digitString(chest.toString().replace("net.minecraft.tileentity.TileEntityChest@", "")));
/*     */             
/* 222 */             if (!this.chestIds.contains(Integer.valueOf(id)) && this.timerAura.delay(300.0D)) {
/* 223 */               mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem(), chest
/* 224 */                   .getPos(), getFacingDirection(chest.getPos()), getVec3(chest.getPos()));
/* 225 */               this.chestIds.add(Integer.valueOf(id));
/* 226 */               this.timerAura.reset();
/*     */             } 
/*     */           } 
/*     */         } else {
/*     */           
/* 231 */           this.timerAura.reset();
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onTick(EventTick event) {
/* 239 */     if (this.isStealing) {
/* 240 */       if (!this.containerSlots.isEmpty()) {
/* 241 */         if (((Boolean)this.onlyNoMove.get()).booleanValue() && isMoving()) {
/*     */           return;
/*     */         }
/* 244 */         Collections.reverse(this.containerSlots);
/* 245 */         for (int count = 0; count < this.containerSlots.size(); count++) {
/* 246 */           if (((Boolean)this.extra_packet.get()).booleanValue() || this.timer.hasReached(MathUtil.randomNumber(((Double)this.maxdelay.get()).doubleValue(), ((Double)this.mindelay.get()).doubleValue()))) {
/* 247 */             mc.playerController.windowClick(this.windowID, ((Integer)this.containerSlots.get(count)).intValue(), 0, 1, (EntityPlayer)mc.thePlayer);
/* 248 */             this.containerSlots.remove(this.containerSlots.get(count));
/* 249 */             this.timer.reset();
/*     */           } 
/*     */         } 
/*     */       } else {
/*     */         
/* 254 */         mc.thePlayer.closeScreen(((Boolean)this.silent.get()).booleanValue() ? ((mc.currentScreen instanceof net.minecraft.client.gui.inventory.GuiInventory) ? null : mc.currentScreen) : null, this.windowID);
/* 255 */         this.isStealing = false;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onMove(EventMove e) {
/* 262 */     if (((Boolean)this.silent.get()).booleanValue() && this.needStuck && this.isStealing) {
/* 263 */       MoveUtils.INSTANCE.pause(e);
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onDisplayGuiChest(DisplayChestGuiEvent event) {
/* 269 */     if (((Boolean)this.silent.get()).booleanValue()) {
/* 270 */       this.needStuck = !InvMove.getInstance.isEnabled();
/* 271 */       if (event.getString().equals("minecraft:chest") || mc.thePlayer.openContainer instanceof net.minecraft.inventory.ContainerChest) {
/* 272 */         mc.displayGuiScreen(null);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onKeyPress(KeyPressEvent event) {
/* 279 */     if (((Boolean)this.silent.get()).booleanValue() && this.isStealing && (
/* 280 */       event.getKey() == -100 || event.getKey() == -99)) {
/* 281 */       event.setCancelled(true);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean checkArmor(ItemStack stack, S30PacketWindowItems packet) {
/* 287 */     if (stack.getItem().getUnlocalizedName().contains("helmet"))
/* 288 */       return (stack.equals(bestArmor(packet.getItemStacks(), Armor.HELMET)) && (bestArmor(Armor.HELMET) == null || 
/* 289 */         getProtection(bestArmor(packet.getItemStacks(), Armor.HELMET)) > getProtection(bestArmor(Armor.HELMET)))); 
/* 290 */     if (stack.getItem().getUnlocalizedName().contains("chestplate"))
/* 291 */       return (stack.equals(bestArmor(packet.getItemStacks(), Armor.CHESTPLATE)) && (bestArmor(Armor.CHESTPLATE) == null || 
/* 292 */         getProtection(bestArmor(packet.getItemStacks(), Armor.CHESTPLATE)) > getProtection(bestArmor(Armor.CHESTPLATE)))); 
/* 293 */     if (stack.getItem().getUnlocalizedName().contains("boots"))
/* 294 */       return (stack.equals(bestArmor(packet.getItemStacks(), Armor.BOOTS)) && (bestArmor(Armor.BOOTS) == null || 
/* 295 */         getProtection(bestArmor(packet.getItemStacks(), Armor.BOOTS)) > getProtection(bestArmor(Armor.BOOTS)))); 
/* 296 */     if (stack.getItem().getUnlocalizedName().contains("leggings")) {
/* 297 */       return (stack.equals(bestArmor(packet.getItemStacks(), Armor.LEGGINS)) && (bestArmor(Armor.LEGGINS) == null || 
/* 298 */         getProtection(bestArmor(packet.getItemStacks(), Armor.LEGGINS)) > getProtection(bestArmor(Armor.LEGGINS))));
/*     */     }
/*     */     
/* 301 */     return true;
/*     */   }
/*     */   
/*     */   private boolean checkTool(ItemStack stack, S30PacketWindowItems packet) {
/* 305 */     if (stack.getItem() instanceof net.minecraft.item.ItemPickaxe)
/* 306 */       return (stack.equals(bestTool(packet.getItemStacks(), Tool.PICKAXE)) && (bestTool(getInventory(), Tool.PICKAXE) == null || 
/* 307 */         getEfficiency(bestTool(packet.getItemStacks(), Tool.PICKAXE)) > getEfficiency(bestTool(getInventory(), Tool.PICKAXE)))); 
/* 308 */     if (stack.getItem() instanceof net.minecraft.item.ItemAxe)
/* 309 */       return (stack.equals(bestTool(packet.getItemStacks(), Tool.AXE)) && (bestTool(getInventory(), Tool.AXE) == null || 
/* 310 */         getEfficiency(bestTool(packet.getItemStacks(), Tool.AXE)) > getEfficiency(bestTool(getInventory(), Tool.AXE)))); 
/* 311 */     if (stack.getItem() instanceof net.minecraft.item.ItemSpade) {
/* 312 */       return (stack.equals(bestTool(packet.getItemStacks(), Tool.SHOVEL)) && (bestTool(getInventory(), Tool.SHOVEL) == null || 
/* 313 */         getEfficiency(bestTool(packet.getItemStacks(), Tool.SHOVEL)) > getEfficiency(bestTool(getInventory(), Tool.SHOVEL))));
/*     */     }
/*     */     
/* 316 */     return true;
/*     */   }
/*     */   
/*     */   private boolean checkSword(ItemStack stack, S30PacketWindowItems packet) {
/* 320 */     if (stack.getItem() instanceof ItemSword) {
/* 321 */       return (stack.equals(bestSword(packet.getItemStacks())) && (bestSword(getInventory()) == null || getDamage(bestSword(packet.getItemStacks())) > getDamage(bestSword(getInventory()))));
/*     */     }
/*     */     
/* 324 */     return true;
/*     */   }
/*     */   
/*     */   private boolean checkBow(ItemStack stack, S30PacketWindowItems packet) {
/* 328 */     if (stack.getItem() instanceof net.minecraft.item.ItemBow) {
/* 329 */       return (stack.equals(bestBow(packet.getItemStacks())) && (bestBow(getInventory()) == null || getPower(bestBow(packet.getItemStacks())) > getPower(bestBow(getInventory()))));
/*     */     }
/*     */     
/* 332 */     return true;
/*     */   }
/*     */   
/*     */   private boolean isNotBad(ItemStack item) {
/* 336 */     ItemStack stack = null;
/* 337 */     float lastDamage = -1.0F;
/*     */     
/* 339 */     for (int i = 9; i < 45; i++) {
/* 340 */       if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
/* 341 */         ItemStack is1 = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
/*     */         
/* 343 */         if (is1.getItem() instanceof ItemSword && item.getItem() instanceof ItemSword && 
/* 344 */           lastDamage < getDamage(is1)) {
/* 345 */           lastDamage = getDamage(is1);
/* 346 */           stack = is1;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 352 */     if (stack != null && stack.getItem() instanceof ItemSword && item.getItem() instanceof ItemSword) {
/* 353 */       float currentDamage = getDamage(stack);
/* 354 */       float itemDamage = getDamage(item);
/*     */       
/* 356 */       if (itemDamage > currentDamage) return true;
/*     */     
/*     */     } 
/* 359 */     return (item == null || (
/* 360 */       !item.getItem().getUnlocalizedName().contains("stick") && (!item.getItem().getUnlocalizedName().contains("egg") || item
/* 361 */       .getItem().getUnlocalizedName().contains("leg")) && 
/* 362 */       !item.getItem().getUnlocalizedName().contains("string") && 
/* 363 */       !item.getItem().getUnlocalizedName().contains("compass") && 
/* 364 */       !item.getItem().getUnlocalizedName().contains("feather") && !item.getItem().getUnlocalizedName().contains("bucket") && 
/* 365 */       !item.getItem().getUnlocalizedName().contains("snow") && !item.getItem().getUnlocalizedName().contains("fish") && 
/* 366 */       !item.getItem().getUnlocalizedName().contains("enchant") && 
/* 367 */       !item.getItem().getUnlocalizedName().contains("exp") && !item.getItem().getUnlocalizedName().contains("shears") && 
/* 368 */       !item.getItem().getUnlocalizedName().contains("anvil") && 
/* 369 */       !item.getItem().getUnlocalizedName().contains("torch") && !item.getItem().getUnlocalizedName().contains("seeds") && 
/* 370 */       !item.getItem().getUnlocalizedName().contains("leather") && !(item.getItem() instanceof net.minecraft.item.ItemGlassBottle) && 
/* 371 */       !item.getItem().getUnlocalizedName().contains("piston") && (
/* 372 */       !item.getItem().getUnlocalizedName().contains("potion") || !isBadPotion(item))));
/*     */   }
/*     */   
/*     */   public boolean isBadPotionEffect(ItemStack stack, ItemPotion pot) {
/* 376 */     for (PotionEffect effect : pot.getEffects(stack)) {
/* 377 */       Potion potion = Potion.potionTypes[effect.getPotionID()];
/*     */       
/* 379 */       if (potion.isBadEffect()) {
/* 380 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 384 */     return false;
/*     */   }
/*     */   
/*     */   private boolean isBadPotion(ItemStack stack) {
/* 388 */     if (stack != null && stack.getItem() instanceof ItemPotion) {
/* 389 */       ItemPotion potion = (ItemPotion)stack.getItem();
/* 390 */       if (ItemPotion.isSplash(stack.getItemDamage())) {
/* 391 */         return isBadPotionEffect(stack, potion);
/*     */       }
/*     */     } 
/*     */     
/* 395 */     return false;
/*     */   }
/*     */   
/*     */   private ItemStack[] getInventory() {
/* 399 */     return mc.thePlayer.inventory.mainInventory;
/*     */   }
/*     */   
/*     */   private ItemStack[] getArmorInventory() {
/* 403 */     return mc.thePlayer.inventory.armorInventory;
/*     */   }
/*     */   
/*     */   public ItemStack bestArmor(Armor armor) {
/* 407 */     if (hasArmor(getInventory(), armor) && hasArmor(getArmorInventory(), armor))
/* 408 */       return ((List<ItemStack>)Stream.<ItemStack>of(new ItemStack[] { ((List<ItemStack>)Arrays.<ItemStack>stream(getInventory()).filter(s -> (s != null && s.getItem().getUnlocalizedName().contains(armorType(armor))))
/* 409 */             .sorted((s1, s2) -> Float.compare(getProtection(s2), getProtection(s1))).collect(Collectors.toList())).stream().findFirst().get(), (
/* 410 */             (List<ItemStack>)Arrays.<ItemStack>stream(getArmorInventory()).filter(s -> (s != null && s.getItem().getUnlocalizedName().contains(armorType(armor))))
/* 411 */             .sorted((s1, s2) -> Float.compare(getProtection(s2), getProtection(s1))).collect(Collectors.toList()))
/* 412 */             .stream().findFirst().get() }).filter(s -> s.getItem().getUnlocalizedName().contains(armorType(armor)))
/* 413 */         .sorted((s1, s2) -> Integer.compare(s2.getMaxDamage() - s2.getItemDamage(), s1.getMaxDamage() - s1.getItemDamage()))
/* 414 */         .sorted((s1, s2) -> Float.compare(getProtection(s2), getProtection(s1))).collect(Collectors.toList())).stream().findFirst().get(); 
/* 415 */     if (hasArmor(getInventory(), armor))
/* 416 */       return ((List<ItemStack>)Arrays.<ItemStack>stream(getInventory()).filter(s -> (s != null && s.getItem().getUnlocalizedName().contains(armorType(armor))))
/* 417 */         .sorted((s1, s2) -> Integer.compare(s2.getMaxDamage() - s2.getItemDamage(), s1.getMaxDamage() - s1.getItemDamage()))
/* 418 */         .sorted((s1, s2) -> Float.compare(getProtection(s2), getProtection(s1))).collect(Collectors.toList())).stream().findFirst().get(); 
/* 419 */     if (hasArmor(getArmorInventory(), armor)) {
/* 420 */       return ((List<ItemStack>)Arrays.<ItemStack>stream(getArmorInventory()).filter(s -> (s != null && s.getItem().getUnlocalizedName().contains(armorType(armor))))
/* 421 */         .sorted((s1, s2) -> Integer.compare(s2.getMaxDamage() - s2.getItemDamage(), s1.getMaxDamage() - s1.getItemDamage()))
/* 422 */         .sorted((s1, s2) -> Float.compare(getProtection(s2), getProtection(s1))).collect(Collectors.toList())).stream().findFirst().get();
/*     */     }
/*     */     
/* 425 */     return null;
/*     */   }
/*     */   
/*     */   public ItemStack bestArmor(ItemStack[] container, Armor armor) {
/* 429 */     if (hasArmor(container, armor)) {
/* 430 */       return ((List<ItemStack>)Arrays.<ItemStack>stream(container).filter(s -> (s != null && s.getItem().getUnlocalizedName().contains(armorType(armor))))
/* 431 */         .sorted((s1, s2) -> Integer.compare(s2.getMaxDamage() - s2.getItemDamage(), s1.getMaxDamage() - s1.getItemDamage()))
/* 432 */         .sorted((s1, s2) -> Float.compare(getProtection(s2), getProtection(s1))).sorted((s1, s2) -> Integer.compare(s2.getItemDamage(), s1.getItemDamage())).collect(Collectors.toList())).stream().findFirst().get();
/*     */     }
/*     */     
/* 435 */     return null;
/*     */   }
/*     */   
/*     */   public ItemStack bestTool(ItemStack[] container, Tool tool) {
/* 439 */     if (hasTool(container, tool)) {
/* 440 */       if (tool.equals(Tool.PICKAXE))
/* 441 */         return Stream.<ItemStack>of(container).filter(s -> (s != null && s.getItem() instanceof net.minecraft.item.ItemPickaxe))
/* 442 */           .sorted((s1, s2) -> Integer.compare(s2.getMaxDamage() - s2.getItemDamage(), s1.getMaxDamage() - s1.getItemDamage()))
/* 443 */           .min((s1, s2) -> Float.compare(getEfficiency(s2), getEfficiency(s1))).get(); 
/* 444 */       if (tool.equals(Tool.SHOVEL))
/* 445 */         return Stream.<ItemStack>of(container).filter(s -> (s != null && s.getItem() instanceof net.minecraft.item.ItemSpade))
/* 446 */           .sorted((s1, s2) -> Integer.compare(s2.getMaxDamage() - s2.getItemDamage(), s1.getMaxDamage() - s1.getItemDamage()))
/* 447 */           .min((s1, s2) -> Float.compare(getEfficiency(s2), getEfficiency(s1))).get(); 
/* 448 */       if (tool.equals(Tool.AXE)) {
/* 449 */         return Stream.<ItemStack>of(container).filter(s -> (s != null && s.getItem() instanceof net.minecraft.item.ItemAxe))
/* 450 */           .sorted((s1, s2) -> Integer.compare(s2.getMaxDamage() - s2.getItemDamage(), s1.getMaxDamage() - s1.getItemDamage()))
/* 451 */           .min((s1, s2) -> Float.compare(getEfficiency(s2), getEfficiency(s1))).get();
/*     */       }
/*     */     } 
/*     */     
/* 455 */     return null;
/*     */   }
/*     */   
/*     */   public ItemStack bestSword(ItemStack[] container) {
/* 459 */     if (hasSword(container)) {
/* 460 */       return ((List<ItemStack>)Stream.<ItemStack>of(container).filter(s -> (s != null && s.getItem() instanceof ItemSword))
/* 461 */         .sorted((s1, s2) -> Integer.compare(s2.getMaxDamage() - s2.getItemDamage(), s1.getMaxDamage() - s1.getItemDamage()))
/* 462 */         .sorted((s1, s2) -> Float.compare(getDamage(s2), getDamage(s1))).collect(Collectors.toList())).stream().findFirst().get();
/*     */     }
/*     */     
/* 465 */     return null;
/*     */   }
/*     */   
/*     */   public ItemStack bestBow(ItemStack[] container) {
/* 469 */     if (hasBow(container)) {
/* 470 */       return ((List<ItemStack>)Stream.<ItemStack>of(container).filter(s -> (s != null && s.getItem() instanceof net.minecraft.item.ItemBow))
/* 471 */         .sorted((s1, s2) -> Float.compare(getPower(s2), getPower(s1))).collect(Collectors.toList())).stream().findFirst().get();
/*     */     }
/*     */     
/* 474 */     return null;
/*     */   }
/*     */   
/*     */   private int containerContainsArmor(ItemStack[] container, Armor armor) {
/* 478 */     for (int i = 0; i < container.length; i++) {
/* 479 */       if (container[i] != null && container[i].getItem().getUnlocalizedName().contains(armorType(armor))) {
/* 480 */         return i;
/*     */       }
/*     */     } 
/*     */     
/* 484 */     return -1;
/*     */   }
/*     */   
/*     */   private int containerContainsTool(ItemStack[] container, Tool tool) {
/* 488 */     for (int i = 0; i < container.length; i++) {
/* 489 */       if (container[i] != null && container[i].getItem().getUnlocalizedName().contains(toolType(tool))) {
/* 490 */         return i;
/*     */       }
/*     */     } 
/*     */     
/* 494 */     return -1;
/*     */   }
/*     */   
/*     */   private int containerContainsSword(ItemStack[] container) {
/* 498 */     for (int i = 0; i < container.length; i++) {
/* 499 */       if (container[i] != null && container[i].getItem() instanceof ItemSword) {
/* 500 */         return i;
/*     */       }
/*     */     } 
/*     */     
/* 504 */     return -1;
/*     */   }
/*     */   
/*     */   private int containerContainsBow(ItemStack[] container) {
/* 508 */     for (int i = 0; i < container.length; i++) {
/* 509 */       if (container[i] != null && container[i].getItem() instanceof net.minecraft.item.ItemBow) {
/* 510 */         return i;
/*     */       }
/*     */     } 
/*     */     
/* 514 */     return -1;
/*     */   }
/*     */   
/*     */   public boolean hasArmor(ItemStack[] container, Armor armor) {
/* 518 */     int i = containerContainsArmor(container, armor);
/* 519 */     return (i >= 0);
/*     */   }
/*     */   
/*     */   public boolean hasTool(ItemStack[] container, Tool tool) {
/* 523 */     int i = containerContainsTool(container, tool);
/* 524 */     return (i >= 0);
/*     */   }
/*     */   
/*     */   public boolean hasSword(ItemStack[] container) {
/* 528 */     int i = containerContainsSword(container);
/* 529 */     return (i >= 0);
/*     */   }
/*     */   
/*     */   public boolean hasBow(ItemStack[] container) {
/* 533 */     int i = containerContainsBow(container);
/* 534 */     return (i >= 0);
/*     */   }
/*     */   
/*     */   public String armorType(Armor armor) {
/* 538 */     if (armor.equals(Armor.LEGGINS))
/* 539 */       return "leggings"; 
/* 540 */     if (armor.equals(Armor.CHESTPLATE))
/* 541 */       return "chestplate"; 
/* 542 */     if (armor.equals(Armor.BOOTS))
/* 543 */       return "boots"; 
/* 544 */     if (armor.equals(Armor.HELMET)) {
/* 545 */       return "helmet";
/*     */     }
/* 547 */     return "";
/*     */   }
/*     */ 
/*     */   
/*     */   public String toolType(Tool tool) {
/* 552 */     if (tool.equals(Tool.AXE))
/* 553 */       return "hatchet"; 
/* 554 */     if (tool.equals(Tool.PICKAXE))
/* 555 */       return "pickaxe"; 
/* 556 */     if (tool.equals(Tool.SHOVEL)) {
/* 557 */       return "shovel";
/*     */     }
/* 559 */     return "";
/*     */   }
/*     */ 
/*     */   
/*     */   private float getPower(ItemStack stack) {
/* 564 */     return (1 + EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, stack) + EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, stack) + 
/* 565 */       EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, stack));
/*     */   }
/*     */ 
/*     */   
/*     */   private float getDamage(ItemStack stack) {
/* 570 */     float damage = 0.0F;
/* 571 */     Item item = stack.getItem();
/*     */     
/* 573 */     if (item instanceof ItemTool) {
/* 574 */       damage += ((ItemTool)item).getDamage();
/* 575 */     } else if (item instanceof ItemSword) {
/* 576 */       damage += ((ItemSword)item).getAttackDamage();
/*     */     } 
/*     */     
/* 579 */     damage += EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, stack) * 1.25F + 
/* 580 */       EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, stack) * 0.01F;
/*     */     
/* 582 */     return damage;
/*     */   }
/*     */   
/*     */   private float getProtection(ItemStack stack) {
/* 586 */     float protection = 0.0F;
/*     */     
/* 588 */     if (stack.getItem() instanceof ItemArmor) {
/* 589 */       ItemArmor armor = (ItemArmor)stack.getItem();
/*     */       
/* 591 */       protection = (float)(protection + armor.damageReduceAmount + ((100 - armor.damageReduceAmount) * EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, stack)) * 0.0075D);
/* 592 */       protection = (float)(protection + EnchantmentHelper.getEnchantmentLevel(Enchantment.blastProtection.effectId, stack) / 100.0D);
/* 593 */       protection = (float)(protection + EnchantmentHelper.getEnchantmentLevel(Enchantment.fireProtection.effectId, stack) / 100.0D);
/* 594 */       protection = (float)(protection + EnchantmentHelper.getEnchantmentLevel(Enchantment.thorns.effectId, stack) / 100.0D);
/* 595 */       protection = (float)(protection + EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack) / 50.0D);
/* 596 */       protection = (float)(protection + EnchantmentHelper.getEnchantmentLevel(Enchantment.projectileProtection.effectId, stack) / 100.0D);
/*     */     } 
/*     */     
/* 599 */     return protection;
/*     */   }
/*     */   
/*     */   private float getEfficiency(ItemStack stack) {
/* 603 */     Item item = stack.getItem();
/* 604 */     ItemTool tool = (ItemTool)item;
/* 605 */     if (!(item instanceof ItemTool)) {
/* 606 */       return 0.0F;
/*     */     }
/*     */     
/* 609 */     String name = item.getUnlocalizedName();
/*     */ 
/*     */     
/* 612 */     if (item instanceof net.minecraft.item.ItemPickaxe) {
/* 613 */       value = tool.getStrVsBlock(stack, Blocks.stone);
/*     */       
/* 615 */       if (name.toLowerCase().contains("gold")) {
/* 616 */         value -= 6.0F;
/*     */       }
/*     */     }
/* 619 */     else if (item instanceof net.minecraft.item.ItemSpade) {
/* 620 */       value = tool.getStrVsBlock(stack, Blocks.dirt);
/*     */       
/* 622 */       if (name.toLowerCase().contains("gold")) {
/* 623 */         value -= 6.0F;
/*     */       }
/*     */     }
/* 626 */     else if (item instanceof net.minecraft.item.ItemAxe) {
/* 627 */       value = tool.getStrVsBlock(stack, Blocks.log);
/*     */       
/* 629 */       if (name.toLowerCase().contains("gold")) {
/* 630 */         value -= 6.0F;
/*     */       }
/*     */     } else {
/*     */       
/* 634 */       return 1.0F;
/*     */     } 
/*     */     
/* 637 */     float value = (float)(value + EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, stack) * 0.0075D);
/* 638 */     value = (float)(value + EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack) / 100.0D);
/* 639 */     return value;
/*     */   }
/*     */   
/*     */   public List<Integer> getChestIds() {
/* 643 */     return this.chestIds;
/*     */   }
/*     */   
/*     */   public boolean isStealing() {
/* 647 */     return this.isStealing;
/*     */   }
/*     */   
/*     */   public enum Armor {
/* 651 */     CHESTPLATE,
/* 652 */     LEGGINS,
/* 653 */     HELMET,
/* 654 */     BOOTS;
/*     */   }
/*     */   
/*     */   public enum Tool {
/* 658 */     PICKAXE,
/* 659 */     SHOVEL,
/* 660 */     AXE;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\world\ChestStealer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */