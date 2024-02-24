/*     */ package net.minecraft.init;
/*     */ 
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemArmor;
/*     */ import net.minecraft.item.ItemArmorStand;
/*     */ import net.minecraft.item.ItemBow;
/*     */ import net.minecraft.item.ItemEmptyMap;
/*     */ import net.minecraft.item.ItemEnchantedBook;
/*     */ import net.minecraft.item.ItemFishingRod;
/*     */ import net.minecraft.item.ItemMap;
/*     */ import net.minecraft.item.ItemPotion;
/*     */ import net.minecraft.item.ItemShears;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Items
/*     */ {
/*     */   private static Item getRegisteredItem(String name) {
/* 198 */     return (Item)Item.itemRegistry.getObject(new ResourceLocation(name));
/*     */   }
/*     */ 
/*     */   
/*     */   static {
/* 203 */     if (!Bootstrap.isRegistered())
/*     */     {
/* 205 */       throw new RuntimeException("Accessed Items before Bootstrap!");
/*     */     }
/*     */   }
/*     */   
/* 209 */   public static final Item iron_shovel = getRegisteredItem("iron_shovel");
/* 210 */   public static final Item iron_pickaxe = getRegisteredItem("iron_pickaxe");
/* 211 */   public static final Item iron_axe = getRegisteredItem("iron_axe");
/* 212 */   public static final Item flint_and_steel = getRegisteredItem("flint_and_steel");
/* 213 */   public static final Item apple = getRegisteredItem("apple");
/* 214 */   public static final ItemBow bow = (ItemBow)getRegisteredItem("bow");
/* 215 */   public static final Item arrow = getRegisteredItem("arrow");
/* 216 */   public static final Item coal = getRegisteredItem("coal");
/* 217 */   public static final Item diamond = getRegisteredItem("diamond");
/* 218 */   public static final Item iron_ingot = getRegisteredItem("iron_ingot");
/* 219 */   public static final Item gold_ingot = getRegisteredItem("gold_ingot");
/* 220 */   public static final Item iron_sword = getRegisteredItem("iron_sword");
/* 221 */   public static final Item wooden_sword = getRegisteredItem("wooden_sword");
/* 222 */   public static final Item wooden_shovel = getRegisteredItem("wooden_shovel");
/* 223 */   public static final Item wooden_pickaxe = getRegisteredItem("wooden_pickaxe");
/* 224 */   public static final Item wooden_axe = getRegisteredItem("wooden_axe");
/* 225 */   public static final Item stone_sword = getRegisteredItem("stone_sword");
/* 226 */   public static final Item stone_shovel = getRegisteredItem("stone_shovel");
/* 227 */   public static final Item stone_pickaxe = getRegisteredItem("stone_pickaxe");
/* 228 */   public static final Item stone_axe = getRegisteredItem("stone_axe");
/* 229 */   public static final Item diamond_sword = getRegisteredItem("diamond_sword");
/* 230 */   public static final Item diamond_shovel = getRegisteredItem("diamond_shovel");
/* 231 */   public static final Item diamond_pickaxe = getRegisteredItem("diamond_pickaxe");
/* 232 */   public static final Item diamond_axe = getRegisteredItem("diamond_axe");
/* 233 */   public static final Item stick = getRegisteredItem("stick");
/* 234 */   public static final Item bowl = getRegisteredItem("bowl");
/* 235 */   public static final Item mushroom_stew = getRegisteredItem("mushroom_stew");
/* 236 */   public static final Item golden_sword = getRegisteredItem("golden_sword");
/* 237 */   public static final Item golden_shovel = getRegisteredItem("golden_shovel");
/* 238 */   public static final Item golden_pickaxe = getRegisteredItem("golden_pickaxe");
/* 239 */   public static final Item golden_axe = getRegisteredItem("golden_axe");
/* 240 */   public static final Item string = getRegisteredItem("string");
/* 241 */   public static final Item feather = getRegisteredItem("feather");
/* 242 */   public static final Item gunpowder = getRegisteredItem("gunpowder");
/* 243 */   public static final Item wooden_hoe = getRegisteredItem("wooden_hoe");
/* 244 */   public static final Item stone_hoe = getRegisteredItem("stone_hoe");
/* 245 */   public static final Item iron_hoe = getRegisteredItem("iron_hoe");
/* 246 */   public static final Item diamond_hoe = getRegisteredItem("diamond_hoe");
/* 247 */   public static final Item golden_hoe = getRegisteredItem("golden_hoe");
/* 248 */   public static final Item wheat_seeds = getRegisteredItem("wheat_seeds");
/* 249 */   public static final Item wheat = getRegisteredItem("wheat");
/* 250 */   public static final Item bread = getRegisteredItem("bread");
/* 251 */   public static final ItemArmor leather_helmet = (ItemArmor)getRegisteredItem("leather_helmet");
/* 252 */   public static final ItemArmor leather_chestplate = (ItemArmor)getRegisteredItem("leather_chestplate");
/* 253 */   public static final ItemArmor leather_leggings = (ItemArmor)getRegisteredItem("leather_leggings");
/* 254 */   public static final ItemArmor leather_boots = (ItemArmor)getRegisteredItem("leather_boots");
/* 255 */   public static final ItemArmor chainmail_helmet = (ItemArmor)getRegisteredItem("chainmail_helmet");
/* 256 */   public static final ItemArmor chainmail_chestplate = (ItemArmor)getRegisteredItem("chainmail_chestplate");
/* 257 */   public static final ItemArmor chainmail_leggings = (ItemArmor)getRegisteredItem("chainmail_leggings");
/* 258 */   public static final ItemArmor chainmail_boots = (ItemArmor)getRegisteredItem("chainmail_boots");
/* 259 */   public static final ItemArmor iron_helmet = (ItemArmor)getRegisteredItem("iron_helmet");
/* 260 */   public static final ItemArmor iron_chestplate = (ItemArmor)getRegisteredItem("iron_chestplate");
/* 261 */   public static final ItemArmor iron_leggings = (ItemArmor)getRegisteredItem("iron_leggings");
/* 262 */   public static final ItemArmor iron_boots = (ItemArmor)getRegisteredItem("iron_boots");
/* 263 */   public static final ItemArmor diamond_helmet = (ItemArmor)getRegisteredItem("diamond_helmet");
/* 264 */   public static final ItemArmor diamond_chestplate = (ItemArmor)getRegisteredItem("diamond_chestplate");
/* 265 */   public static final ItemArmor diamond_leggings = (ItemArmor)getRegisteredItem("diamond_leggings");
/* 266 */   public static final ItemArmor diamond_boots = (ItemArmor)getRegisteredItem("diamond_boots");
/* 267 */   public static final ItemArmor golden_helmet = (ItemArmor)getRegisteredItem("golden_helmet");
/* 268 */   public static final ItemArmor golden_chestplate = (ItemArmor)getRegisteredItem("golden_chestplate");
/* 269 */   public static final ItemArmor golden_leggings = (ItemArmor)getRegisteredItem("golden_leggings");
/* 270 */   public static final ItemArmor golden_boots = (ItemArmor)getRegisteredItem("golden_boots");
/* 271 */   public static final Item flint = getRegisteredItem("flint");
/* 272 */   public static final Item porkchop = getRegisteredItem("porkchop");
/* 273 */   public static final Item cooked_porkchop = getRegisteredItem("cooked_porkchop");
/* 274 */   public static final Item painting = getRegisteredItem("painting");
/* 275 */   public static final Item golden_apple = getRegisteredItem("golden_apple");
/* 276 */   public static final Item sign = getRegisteredItem("sign");
/* 277 */   public static final Item oak_door = getRegisteredItem("wooden_door");
/* 278 */   public static final Item spruce_door = getRegisteredItem("spruce_door");
/* 279 */   public static final Item birch_door = getRegisteredItem("birch_door");
/* 280 */   public static final Item jungle_door = getRegisteredItem("jungle_door");
/* 281 */   public static final Item acacia_door = getRegisteredItem("acacia_door");
/* 282 */   public static final Item dark_oak_door = getRegisteredItem("dark_oak_door");
/* 283 */   public static final Item bucket = getRegisteredItem("bucket");
/* 284 */   public static final Item water_bucket = getRegisteredItem("water_bucket");
/* 285 */   public static final Item lava_bucket = getRegisteredItem("lava_bucket");
/* 286 */   public static final Item minecart = getRegisteredItem("minecart");
/* 287 */   public static final Item saddle = getRegisteredItem("saddle");
/* 288 */   public static final Item iron_door = getRegisteredItem("iron_door");
/* 289 */   public static final Item redstone = getRegisteredItem("redstone");
/* 290 */   public static final Item snowball = getRegisteredItem("snowball");
/* 291 */   public static final Item boat = getRegisteredItem("boat");
/* 292 */   public static final Item leather = getRegisteredItem("leather");
/* 293 */   public static final Item milk_bucket = getRegisteredItem("milk_bucket");
/* 294 */   public static final Item brick = getRegisteredItem("brick");
/* 295 */   public static final Item clay_ball = getRegisteredItem("clay_ball");
/* 296 */   public static final Item reeds = getRegisteredItem("reeds");
/* 297 */   public static final Item paper = getRegisteredItem("paper");
/* 298 */   public static final Item book = getRegisteredItem("book");
/* 299 */   public static final Item slime_ball = getRegisteredItem("slime_ball");
/* 300 */   public static final Item chest_minecart = getRegisteredItem("chest_minecart");
/* 301 */   public static final Item furnace_minecart = getRegisteredItem("furnace_minecart");
/* 302 */   public static final Item egg = getRegisteredItem("egg");
/* 303 */   public static final Item compass = getRegisteredItem("compass");
/* 304 */   public static final ItemFishingRod fishing_rod = (ItemFishingRod)getRegisteredItem("fishing_rod");
/* 305 */   public static final Item clock = getRegisteredItem("clock");
/* 306 */   public static final Item glowstone_dust = getRegisteredItem("glowstone_dust");
/* 307 */   public static final Item fish = getRegisteredItem("fish");
/* 308 */   public static final Item cooked_fish = getRegisteredItem("cooked_fish");
/* 309 */   public static final Item dye = getRegisteredItem("dye");
/* 310 */   public static final Item bone = getRegisteredItem("bone");
/* 311 */   public static final Item sugar = getRegisteredItem("sugar");
/* 312 */   public static final Item cake = getRegisteredItem("cake");
/* 313 */   public static final Item bed = getRegisteredItem("bed");
/* 314 */   public static final Item repeater = getRegisteredItem("repeater");
/* 315 */   public static final Item cookie = getRegisteredItem("cookie");
/* 316 */   public static final ItemMap filled_map = (ItemMap)getRegisteredItem("filled_map");
/* 317 */   public static final ItemShears shears = (ItemShears)getRegisteredItem("shears");
/* 318 */   public static final Item melon = getRegisteredItem("melon");
/* 319 */   public static final Item pumpkin_seeds = getRegisteredItem("pumpkin_seeds");
/* 320 */   public static final Item melon_seeds = getRegisteredItem("melon_seeds");
/* 321 */   public static final Item beef = getRegisteredItem("beef");
/* 322 */   public static final Item cooked_beef = getRegisteredItem("cooked_beef");
/* 323 */   public static final Item chicken = getRegisteredItem("chicken");
/* 324 */   public static final Item cooked_chicken = getRegisteredItem("cooked_chicken");
/* 325 */   public static final Item mutton = getRegisteredItem("mutton");
/* 326 */   public static final Item cooked_mutton = getRegisteredItem("cooked_mutton");
/* 327 */   public static final Item rabbit = getRegisteredItem("rabbit");
/* 328 */   public static final Item cooked_rabbit = getRegisteredItem("cooked_rabbit");
/* 329 */   public static final Item rabbit_stew = getRegisteredItem("rabbit_stew");
/* 330 */   public static final Item rabbit_foot = getRegisteredItem("rabbit_foot");
/* 331 */   public static final Item rabbit_hide = getRegisteredItem("rabbit_hide");
/* 332 */   public static final Item rotten_flesh = getRegisteredItem("rotten_flesh");
/* 333 */   public static final Item ender_pearl = getRegisteredItem("ender_pearl");
/* 334 */   public static final Item blaze_rod = getRegisteredItem("blaze_rod");
/* 335 */   public static final Item ghast_tear = getRegisteredItem("ghast_tear");
/* 336 */   public static final Item gold_nugget = getRegisteredItem("gold_nugget");
/* 337 */   public static final Item nether_wart = getRegisteredItem("nether_wart");
/* 338 */   public static final ItemPotion potionitem = (ItemPotion)getRegisteredItem("potion");
/* 339 */   public static final Item glass_bottle = getRegisteredItem("glass_bottle");
/* 340 */   public static final Item spider_eye = getRegisteredItem("spider_eye");
/* 341 */   public static final Item fermented_spider_eye = getRegisteredItem("fermented_spider_eye");
/* 342 */   public static final Item blaze_powder = getRegisteredItem("blaze_powder");
/* 343 */   public static final Item magma_cream = getRegisteredItem("magma_cream");
/* 344 */   public static final Item brewing_stand = getRegisteredItem("brewing_stand");
/* 345 */   public static final Item cauldron = getRegisteredItem("cauldron");
/* 346 */   public static final Item ender_eye = getRegisteredItem("ender_eye");
/* 347 */   public static final Item speckled_melon = getRegisteredItem("speckled_melon");
/* 348 */   public static final Item spawn_egg = getRegisteredItem("spawn_egg");
/* 349 */   public static final Item experience_bottle = getRegisteredItem("experience_bottle");
/* 350 */   public static final Item fire_charge = getRegisteredItem("fire_charge");
/* 351 */   public static final Item writable_book = getRegisteredItem("writable_book");
/* 352 */   public static final Item written_book = getRegisteredItem("written_book");
/* 353 */   public static final Item emerald = getRegisteredItem("emerald");
/* 354 */   public static final Item item_frame = getRegisteredItem("item_frame");
/* 355 */   public static final Item flower_pot = getRegisteredItem("flower_pot");
/* 356 */   public static final Item carrot = getRegisteredItem("carrot");
/* 357 */   public static final Item potato = getRegisteredItem("potato");
/* 358 */   public static final Item baked_potato = getRegisteredItem("baked_potato");
/* 359 */   public static final Item poisonous_potato = getRegisteredItem("poisonous_potato");
/* 360 */   public static final ItemEmptyMap map = (ItemEmptyMap)getRegisteredItem("map");
/* 361 */   public static final Item golden_carrot = getRegisteredItem("golden_carrot");
/* 362 */   public static final Item skull = getRegisteredItem("skull");
/* 363 */   public static final Item carrot_on_a_stick = getRegisteredItem("carrot_on_a_stick");
/* 364 */   public static final Item nether_star = getRegisteredItem("nether_star");
/* 365 */   public static final Item pumpkin_pie = getRegisteredItem("pumpkin_pie");
/* 366 */   public static final Item fireworks = getRegisteredItem("fireworks");
/* 367 */   public static final Item firework_charge = getRegisteredItem("firework_charge");
/* 368 */   public static final ItemEnchantedBook enchanted_book = (ItemEnchantedBook)getRegisteredItem("enchanted_book");
/* 369 */   public static final Item comparator = getRegisteredItem("comparator");
/* 370 */   public static final Item netherbrick = getRegisteredItem("netherbrick");
/* 371 */   public static final Item quartz = getRegisteredItem("quartz");
/* 372 */   public static final Item tnt_minecart = getRegisteredItem("tnt_minecart");
/* 373 */   public static final Item hopper_minecart = getRegisteredItem("hopper_minecart");
/* 374 */   public static final ItemArmorStand armor_stand = (ItemArmorStand)getRegisteredItem("armor_stand");
/* 375 */   public static final Item iron_horse_armor = getRegisteredItem("iron_horse_armor");
/* 376 */   public static final Item golden_horse_armor = getRegisteredItem("golden_horse_armor");
/* 377 */   public static final Item diamond_horse_armor = getRegisteredItem("diamond_horse_armor");
/* 378 */   public static final Item lead = getRegisteredItem("lead");
/* 379 */   public static final Item name_tag = getRegisteredItem("name_tag");
/* 380 */   public static final Item command_block_minecart = getRegisteredItem("command_block_minecart");
/* 381 */   public static final Item record_13 = getRegisteredItem("record_13");
/* 382 */   public static final Item record_cat = getRegisteredItem("record_cat");
/* 383 */   public static final Item record_blocks = getRegisteredItem("record_blocks");
/* 384 */   public static final Item record_chirp = getRegisteredItem("record_chirp");
/* 385 */   public static final Item record_far = getRegisteredItem("record_far");
/* 386 */   public static final Item record_mall = getRegisteredItem("record_mall");
/* 387 */   public static final Item record_mellohi = getRegisteredItem("record_mellohi");
/* 388 */   public static final Item record_stal = getRegisteredItem("record_stal");
/* 389 */   public static final Item record_strad = getRegisteredItem("record_strad");
/* 390 */   public static final Item record_ward = getRegisteredItem("record_ward");
/* 391 */   public static final Item record_11 = getRegisteredItem("record_11");
/* 392 */   public static final Item record_wait = getRegisteredItem("record_wait");
/* 393 */   public static final Item prismarine_shard = getRegisteredItem("prismarine_shard");
/* 394 */   public static final Item prismarine_crystals = getRegisteredItem("prismarine_crystals");
/* 395 */   public static final Item banner = getRegisteredItem("banner");
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\init\Items.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */