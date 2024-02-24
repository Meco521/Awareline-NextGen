/*     */ package net.minecraft.init;
/*     */ 
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockBeacon;
/*     */ import net.minecraft.block.BlockBush;
/*     */ import net.minecraft.block.BlockCactus;
/*     */ import net.minecraft.block.BlockCauldron;
/*     */ import net.minecraft.block.BlockChest;
/*     */ import net.minecraft.block.BlockDaylightDetector;
/*     */ import net.minecraft.block.BlockDeadBush;
/*     */ import net.minecraft.block.BlockDoublePlant;
/*     */ import net.minecraft.block.BlockDynamicLiquid;
/*     */ import net.minecraft.block.BlockFire;
/*     */ import net.minecraft.block.BlockFlower;
/*     */ import net.minecraft.block.BlockGrass;
/*     */ import net.minecraft.block.BlockHopper;
/*     */ import net.minecraft.block.BlockLeaves;
/*     */ import net.minecraft.block.BlockMycelium;
/*     */ import net.minecraft.block.BlockPistonBase;
/*     */ import net.minecraft.block.BlockPistonExtension;
/*     */ import net.minecraft.block.BlockPistonMoving;
/*     */ import net.minecraft.block.BlockPortal;
/*     */ import net.minecraft.block.BlockRedstoneComparator;
/*     */ import net.minecraft.block.BlockRedstoneRepeater;
/*     */ import net.minecraft.block.BlockRedstoneWire;
/*     */ import net.minecraft.block.BlockReed;
/*     */ import net.minecraft.block.BlockSand;
/*     */ import net.minecraft.block.BlockSkull;
/*     */ import net.minecraft.block.BlockSlab;
/*     */ import net.minecraft.block.BlockStainedGlass;
/*     */ import net.minecraft.block.BlockStainedGlassPane;
/*     */ import net.minecraft.block.BlockStaticLiquid;
/*     */ import net.minecraft.block.BlockTallGrass;
/*     */ import net.minecraft.block.BlockTripWireHook;
/*     */ import net.minecraft.util.ResourceLocation;
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
/*     */ public class Blocks
/*     */ {
/*     */   private static Block getRegisteredBlock(String blockName) {
/* 214 */     return (Block)Block.blockRegistry.getObject(new ResourceLocation(blockName));
/*     */   }
/*     */ 
/*     */   
/*     */   static {
/* 219 */     if (!Bootstrap.isRegistered())
/*     */     {
/* 221 */       throw new RuntimeException("Accessed Blocks before Bootstrap!");
/*     */     }
/*     */   }
/*     */   
/* 225 */   public static final Block air = getRegisteredBlock("air");
/* 226 */   public static final Block stone = getRegisteredBlock("stone");
/* 227 */   public static final BlockGrass grass = (BlockGrass)getRegisteredBlock("grass");
/* 228 */   public static final Block dirt = getRegisteredBlock("dirt");
/* 229 */   public static final Block cobblestone = getRegisteredBlock("cobblestone");
/* 230 */   public static final Block planks = getRegisteredBlock("planks");
/* 231 */   public static final Block sapling = getRegisteredBlock("sapling");
/* 232 */   public static final Block bedrock = getRegisteredBlock("bedrock");
/* 233 */   public static final BlockDynamicLiquid flowing_water = (BlockDynamicLiquid)getRegisteredBlock("flowing_water");
/* 234 */   public static final BlockStaticLiquid water = (BlockStaticLiquid)getRegisteredBlock("water");
/* 235 */   public static final BlockDynamicLiquid flowing_lava = (BlockDynamicLiquid)getRegisteredBlock("flowing_lava");
/* 236 */   public static final BlockStaticLiquid lava = (BlockStaticLiquid)getRegisteredBlock("lava");
/* 237 */   public static final BlockSand sand = (BlockSand)getRegisteredBlock("sand");
/* 238 */   public static final Block gravel = getRegisteredBlock("gravel");
/* 239 */   public static final Block gold_ore = getRegisteredBlock("gold_ore");
/* 240 */   public static final Block iron_ore = getRegisteredBlock("iron_ore");
/* 241 */   public static final Block coal_ore = getRegisteredBlock("coal_ore");
/* 242 */   public static final Block log = getRegisteredBlock("log");
/* 243 */   public static final Block log2 = getRegisteredBlock("log2");
/* 244 */   public static final BlockLeaves leaves = (BlockLeaves)getRegisteredBlock("leaves");
/* 245 */   public static final BlockLeaves leaves2 = (BlockLeaves)getRegisteredBlock("leaves2");
/* 246 */   public static final Block sponge = getRegisteredBlock("sponge");
/* 247 */   public static final Block glass = getRegisteredBlock("glass");
/* 248 */   public static final Block lapis_ore = getRegisteredBlock("lapis_ore");
/* 249 */   public static final Block lapis_block = getRegisteredBlock("lapis_block");
/* 250 */   public static final Block dispenser = getRegisteredBlock("dispenser");
/* 251 */   public static final Block sandstone = getRegisteredBlock("sandstone");
/* 252 */   public static final Block noteblock = getRegisteredBlock("noteblock");
/* 253 */   public static final Block bed = getRegisteredBlock("bed");
/* 254 */   public static final Block golden_rail = getRegisteredBlock("golden_rail");
/* 255 */   public static final Block detector_rail = getRegisteredBlock("detector_rail");
/* 256 */   public static final BlockPistonBase sticky_piston = (BlockPistonBase)getRegisteredBlock("sticky_piston");
/* 257 */   public static final Block web = getRegisteredBlock("web");
/* 258 */   public static final BlockTallGrass tallgrass = (BlockTallGrass)getRegisteredBlock("tallgrass");
/* 259 */   public static final BlockDeadBush deadbush = (BlockDeadBush)getRegisteredBlock("deadbush");
/* 260 */   public static final BlockPistonBase piston = (BlockPistonBase)getRegisteredBlock("piston");
/* 261 */   public static final BlockPistonExtension piston_head = (BlockPistonExtension)getRegisteredBlock("piston_head");
/* 262 */   public static final Block wool = getRegisteredBlock("wool");
/* 263 */   public static final BlockPistonMoving piston_extension = (BlockPistonMoving)getRegisteredBlock("piston_extension");
/* 264 */   public static final BlockFlower yellow_flower = (BlockFlower)getRegisteredBlock("yellow_flower");
/* 265 */   public static final BlockFlower red_flower = (BlockFlower)getRegisteredBlock("red_flower");
/* 266 */   public static final BlockBush brown_mushroom = (BlockBush)getRegisteredBlock("brown_mushroom");
/* 267 */   public static final BlockBush red_mushroom = (BlockBush)getRegisteredBlock("red_mushroom");
/* 268 */   public static final Block gold_block = getRegisteredBlock("gold_block");
/* 269 */   public static final Block iron_block = getRegisteredBlock("iron_block");
/* 270 */   public static final BlockSlab double_stone_slab = (BlockSlab)getRegisteredBlock("double_stone_slab");
/* 271 */   public static final BlockSlab stone_slab = (BlockSlab)getRegisteredBlock("stone_slab");
/* 272 */   public static final Block brick_block = getRegisteredBlock("brick_block");
/* 273 */   public static final Block tnt = getRegisteredBlock("tnt");
/* 274 */   public static final Block bookshelf = getRegisteredBlock("bookshelf");
/* 275 */   public static final Block mossy_cobblestone = getRegisteredBlock("mossy_cobblestone");
/* 276 */   public static final Block obsidian = getRegisteredBlock("obsidian");
/* 277 */   public static final Block torch = getRegisteredBlock("torch");
/* 278 */   public static final BlockFire fire = (BlockFire)getRegisteredBlock("fire");
/* 279 */   public static final Block mob_spawner = getRegisteredBlock("mob_spawner");
/* 280 */   public static final Block oak_stairs = getRegisteredBlock("oak_stairs");
/* 281 */   public static final BlockChest chest = (BlockChest)getRegisteredBlock("chest");
/* 282 */   public static final BlockRedstoneWire redstone_wire = (BlockRedstoneWire)getRegisteredBlock("redstone_wire");
/* 283 */   public static final Block diamond_ore = getRegisteredBlock("diamond_ore");
/* 284 */   public static final Block diamond_block = getRegisteredBlock("diamond_block");
/* 285 */   public static final Block crafting_table = getRegisteredBlock("crafting_table");
/* 286 */   public static final Block wheat = getRegisteredBlock("wheat");
/* 287 */   public static final Block farmland = getRegisteredBlock("farmland");
/* 288 */   public static final Block furnace = getRegisteredBlock("furnace");
/* 289 */   public static final Block lit_furnace = getRegisteredBlock("lit_furnace");
/* 290 */   public static final Block standing_sign = getRegisteredBlock("standing_sign");
/* 291 */   public static final Block oak_door = getRegisteredBlock("wooden_door");
/* 292 */   public static final Block spruce_door = getRegisteredBlock("spruce_door");
/* 293 */   public static final Block birch_door = getRegisteredBlock("birch_door");
/* 294 */   public static final Block jungle_door = getRegisteredBlock("jungle_door");
/* 295 */   public static final Block acacia_door = getRegisteredBlock("acacia_door");
/* 296 */   public static final Block dark_oak_door = getRegisteredBlock("dark_oak_door");
/* 297 */   public static final Block ladder = getRegisteredBlock("ladder");
/* 298 */   public static final Block rail = getRegisteredBlock("rail");
/* 299 */   public static final Block stone_stairs = getRegisteredBlock("stone_stairs");
/* 300 */   public static final Block wall_sign = getRegisteredBlock("wall_sign");
/* 301 */   public static final Block lever = getRegisteredBlock("lever");
/* 302 */   public static final Block stone_pressure_plate = getRegisteredBlock("stone_pressure_plate");
/* 303 */   public static final Block iron_door = getRegisteredBlock("iron_door");
/* 304 */   public static final Block wooden_pressure_plate = getRegisteredBlock("wooden_pressure_plate");
/* 305 */   public static final Block redstone_ore = getRegisteredBlock("redstone_ore");
/* 306 */   public static final Block lit_redstone_ore = getRegisteredBlock("lit_redstone_ore");
/* 307 */   public static final Block unlit_redstone_torch = getRegisteredBlock("unlit_redstone_torch");
/* 308 */   public static final Block redstone_torch = getRegisteredBlock("redstone_torch");
/* 309 */   public static final Block stone_button = getRegisteredBlock("stone_button");
/* 310 */   public static final Block snow_layer = getRegisteredBlock("snow_layer");
/* 311 */   public static final Block ice = getRegisteredBlock("ice");
/* 312 */   public static final Block snow = getRegisteredBlock("snow");
/* 313 */   public static final BlockCactus cactus = (BlockCactus)getRegisteredBlock("cactus");
/* 314 */   public static final Block clay = getRegisteredBlock("clay");
/* 315 */   public static final BlockReed reeds = (BlockReed)getRegisteredBlock("reeds");
/* 316 */   public static final Block jukebox = getRegisteredBlock("jukebox");
/* 317 */   public static final Block oak_fence = getRegisteredBlock("fence");
/* 318 */   public static final Block spruce_fence = getRegisteredBlock("spruce_fence");
/* 319 */   public static final Block birch_fence = getRegisteredBlock("birch_fence");
/* 320 */   public static final Block jungle_fence = getRegisteredBlock("jungle_fence");
/* 321 */   public static final Block dark_oak_fence = getRegisteredBlock("dark_oak_fence");
/* 322 */   public static final Block acacia_fence = getRegisteredBlock("acacia_fence");
/* 323 */   public static final Block pumpkin = getRegisteredBlock("pumpkin");
/* 324 */   public static final Block netherrack = getRegisteredBlock("netherrack");
/* 325 */   public static final Block soul_sand = getRegisteredBlock("soul_sand");
/* 326 */   public static final Block glowstone = getRegisteredBlock("glowstone");
/* 327 */   public static final BlockPortal portal = (BlockPortal)getRegisteredBlock("portal");
/* 328 */   public static final Block lit_pumpkin = getRegisteredBlock("lit_pumpkin");
/* 329 */   public static final Block cake = getRegisteredBlock("cake");
/* 330 */   public static final BlockRedstoneRepeater unpowered_repeater = (BlockRedstoneRepeater)getRegisteredBlock("unpowered_repeater");
/* 331 */   public static final BlockRedstoneRepeater powered_repeater = (BlockRedstoneRepeater)getRegisteredBlock("powered_repeater");
/* 332 */   public static final Block trapdoor = getRegisteredBlock("trapdoor");
/* 333 */   public static final Block monster_egg = getRegisteredBlock("monster_egg");
/* 334 */   public static final Block stonebrick = getRegisteredBlock("stonebrick");
/* 335 */   public static final Block brown_mushroom_block = getRegisteredBlock("brown_mushroom_block");
/* 336 */   public static final Block red_mushroom_block = getRegisteredBlock("red_mushroom_block");
/* 337 */   public static final Block iron_bars = getRegisteredBlock("iron_bars");
/* 338 */   public static final Block glass_pane = getRegisteredBlock("glass_pane");
/* 339 */   public static final Block melon_block = getRegisteredBlock("melon_block");
/* 340 */   public static final Block pumpkin_stem = getRegisteredBlock("pumpkin_stem");
/* 341 */   public static final Block melon_stem = getRegisteredBlock("melon_stem");
/* 342 */   public static final Block vine = getRegisteredBlock("vine");
/* 343 */   public static final Block oak_fence_gate = getRegisteredBlock("fence_gate");
/* 344 */   public static final Block spruce_fence_gate = getRegisteredBlock("spruce_fence_gate");
/* 345 */   public static final Block birch_fence_gate = getRegisteredBlock("birch_fence_gate");
/* 346 */   public static final Block jungle_fence_gate = getRegisteredBlock("jungle_fence_gate");
/* 347 */   public static final Block dark_oak_fence_gate = getRegisteredBlock("dark_oak_fence_gate");
/* 348 */   public static final Block acacia_fence_gate = getRegisteredBlock("acacia_fence_gate");
/* 349 */   public static final Block brick_stairs = getRegisteredBlock("brick_stairs");
/* 350 */   public static final Block stone_brick_stairs = getRegisteredBlock("stone_brick_stairs");
/* 351 */   public static final BlockMycelium mycelium = (BlockMycelium)getRegisteredBlock("mycelium");
/* 352 */   public static final Block waterlily = getRegisteredBlock("waterlily");
/* 353 */   public static final Block nether_brick = getRegisteredBlock("nether_brick");
/* 354 */   public static final Block nether_brick_fence = getRegisteredBlock("nether_brick_fence");
/* 355 */   public static final Block nether_brick_stairs = getRegisteredBlock("nether_brick_stairs");
/* 356 */   public static final Block nether_wart = getRegisteredBlock("nether_wart");
/* 357 */   public static final Block enchanting_table = getRegisteredBlock("enchanting_table");
/* 358 */   public static final Block brewing_stand = getRegisteredBlock("brewing_stand");
/* 359 */   public static final BlockCauldron cauldron = (BlockCauldron)getRegisteredBlock("cauldron");
/* 360 */   public static final Block end_portal = getRegisteredBlock("end_portal");
/* 361 */   public static final Block end_portal_frame = getRegisteredBlock("end_portal_frame");
/* 362 */   public static final Block end_stone = getRegisteredBlock("end_stone");
/* 363 */   public static final Block dragon_egg = getRegisteredBlock("dragon_egg");
/* 364 */   public static final Block redstone_lamp = getRegisteredBlock("redstone_lamp");
/* 365 */   public static final Block lit_redstone_lamp = getRegisteredBlock("lit_redstone_lamp");
/* 366 */   public static final BlockSlab double_wooden_slab = (BlockSlab)getRegisteredBlock("double_wooden_slab");
/* 367 */   public static final BlockSlab wooden_slab = (BlockSlab)getRegisteredBlock("wooden_slab");
/* 368 */   public static final Block cocoa = getRegisteredBlock("cocoa");
/* 369 */   public static final Block sandstone_stairs = getRegisteredBlock("sandstone_stairs");
/* 370 */   public static final Block emerald_ore = getRegisteredBlock("emerald_ore");
/* 371 */   public static final Block ender_chest = getRegisteredBlock("ender_chest");
/* 372 */   public static final BlockTripWireHook tripwire_hook = (BlockTripWireHook)getRegisteredBlock("tripwire_hook");
/* 373 */   public static final Block tripwire = getRegisteredBlock("tripwire");
/* 374 */   public static final Block emerald_block = getRegisteredBlock("emerald_block");
/* 375 */   public static final Block spruce_stairs = getRegisteredBlock("spruce_stairs");
/* 376 */   public static final Block birch_stairs = getRegisteredBlock("birch_stairs");
/* 377 */   public static final Block jungle_stairs = getRegisteredBlock("jungle_stairs");
/* 378 */   public static final Block command_block = getRegisteredBlock("command_block");
/* 379 */   public static final BlockBeacon beacon = (BlockBeacon)getRegisteredBlock("beacon");
/* 380 */   public static final Block cobblestone_wall = getRegisteredBlock("cobblestone_wall");
/* 381 */   public static final Block flower_pot = getRegisteredBlock("flower_pot");
/* 382 */   public static final Block carrots = getRegisteredBlock("carrots");
/* 383 */   public static final Block potatoes = getRegisteredBlock("potatoes");
/* 384 */   public static final Block wooden_button = getRegisteredBlock("wooden_button");
/* 385 */   public static final BlockSkull skull = (BlockSkull)getRegisteredBlock("skull");
/* 386 */   public static final Block anvil = getRegisteredBlock("anvil");
/* 387 */   public static final Block trapped_chest = getRegisteredBlock("trapped_chest");
/* 388 */   public static final Block light_weighted_pressure_plate = getRegisteredBlock("light_weighted_pressure_plate");
/* 389 */   public static final Block heavy_weighted_pressure_plate = getRegisteredBlock("heavy_weighted_pressure_plate");
/* 390 */   public static final BlockRedstoneComparator unpowered_comparator = (BlockRedstoneComparator)getRegisteredBlock("unpowered_comparator");
/* 391 */   public static final BlockRedstoneComparator powered_comparator = (BlockRedstoneComparator)getRegisteredBlock("powered_comparator");
/* 392 */   public static final BlockDaylightDetector daylight_detector = (BlockDaylightDetector)getRegisteredBlock("daylight_detector");
/* 393 */   public static final BlockDaylightDetector daylight_detector_inverted = (BlockDaylightDetector)getRegisteredBlock("daylight_detector_inverted");
/* 394 */   public static final Block redstone_block = getRegisteredBlock("redstone_block");
/* 395 */   public static final Block quartz_ore = getRegisteredBlock("quartz_ore");
/* 396 */   public static final BlockHopper hopper = (BlockHopper)getRegisteredBlock("hopper");
/* 397 */   public static final Block quartz_block = getRegisteredBlock("quartz_block");
/* 398 */   public static final Block quartz_stairs = getRegisteredBlock("quartz_stairs");
/* 399 */   public static final Block activator_rail = getRegisteredBlock("activator_rail");
/* 400 */   public static final Block dropper = getRegisteredBlock("dropper");
/* 401 */   public static final Block stained_hardened_clay = getRegisteredBlock("stained_hardened_clay");
/* 402 */   public static final Block barrier = getRegisteredBlock("barrier");
/* 403 */   public static final Block iron_trapdoor = getRegisteredBlock("iron_trapdoor");
/* 404 */   public static final Block hay_block = getRegisteredBlock("hay_block");
/* 405 */   public static final Block carpet = getRegisteredBlock("carpet");
/* 406 */   public static final Block hardened_clay = getRegisteredBlock("hardened_clay");
/* 407 */   public static final Block coal_block = getRegisteredBlock("coal_block");
/* 408 */   public static final Block packed_ice = getRegisteredBlock("packed_ice");
/* 409 */   public static final Block acacia_stairs = getRegisteredBlock("acacia_stairs");
/* 410 */   public static final Block dark_oak_stairs = getRegisteredBlock("dark_oak_stairs");
/* 411 */   public static final Block slime_block = getRegisteredBlock("slime");
/* 412 */   public static final BlockDoublePlant double_plant = (BlockDoublePlant)getRegisteredBlock("double_plant");
/* 413 */   public static final BlockStainedGlass stained_glass = (BlockStainedGlass)getRegisteredBlock("stained_glass");
/* 414 */   public static final BlockStainedGlassPane stained_glass_pane = (BlockStainedGlassPane)getRegisteredBlock("stained_glass_pane");
/* 415 */   public static final Block prismarine = getRegisteredBlock("prismarine");
/* 416 */   public static final Block sea_lantern = getRegisteredBlock("sea_lantern");
/* 417 */   public static final Block standing_banner = getRegisteredBlock("standing_banner");
/* 418 */   public static final Block wall_banner = getRegisteredBlock("wall_banner");
/* 419 */   public static final Block red_sandstone = getRegisteredBlock("red_sandstone");
/* 420 */   public static final Block red_sandstone_stairs = getRegisteredBlock("red_sandstone_stairs");
/* 421 */   public static final BlockSlab double_stone_slab2 = (BlockSlab)getRegisteredBlock("double_stone_slab2");
/* 422 */   public static final BlockSlab stone_slab2 = (BlockSlab)getRegisteredBlock("stone_slab2");
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\init\Blocks.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */