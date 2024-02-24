/*     */ package net.minecraft.command;
/*     */ 
/*     */ import com.google.common.base.Functions;
/*     */ import com.google.common.collect.Iterables;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.primitives.Doubles;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ public abstract class CommandBase
/*     */   implements ICommand
/*     */ {
/*     */   private static IAdminCommand theAdmin;
/*     */   
/*     */   public int getRequiredPermissionLevel() {
/*  28 */     return 4;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getCommandAliases() {
/*  33 */     return Collections.emptyList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canCommandSenderUseCommand(ICommandSender sender) {
/*  41 */     return sender.canCommandSenderUseCommand(getRequiredPermissionLevel(), getCommandName());
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/*  46 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int parseInt(String input) throws NumberInvalidException {
/*     */     try {
/*  53 */       return Integer.parseInt(input);
/*     */     }
/*  55 */     catch (NumberFormatException var2) {
/*     */       
/*  57 */       throw new NumberInvalidException("commands.generic.num.invalid", new Object[] { input });
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static int parseInt(String input, int min) throws NumberInvalidException {
/*  63 */     return parseInt(input, min, 2147483647);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int parseInt(String input, int min, int max) throws NumberInvalidException {
/*  68 */     int i = parseInt(input);
/*     */     
/*  70 */     if (i < min)
/*     */     {
/*  72 */       throw new NumberInvalidException("commands.generic.num.tooSmall", new Object[] { Integer.valueOf(i), Integer.valueOf(min) });
/*     */     }
/*  74 */     if (i > max)
/*     */     {
/*  76 */       throw new NumberInvalidException("commands.generic.num.tooBig", new Object[] { Integer.valueOf(i), Integer.valueOf(max) });
/*     */     }
/*     */ 
/*     */     
/*  80 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long parseLong(String input) throws NumberInvalidException {
/*     */     try {
/*  88 */       return Long.parseLong(input);
/*     */     }
/*  90 */     catch (NumberFormatException var2) {
/*     */       
/*  92 */       throw new NumberInvalidException("commands.generic.num.invalid", new Object[] { input });
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static long parseLong(String input, long min, long max) throws NumberInvalidException {
/*  98 */     long i = parseLong(input);
/*     */     
/* 100 */     if (i < min)
/*     */     {
/* 102 */       throw new NumberInvalidException("commands.generic.num.tooSmall", new Object[] { Long.valueOf(i), Long.valueOf(min) });
/*     */     }
/* 104 */     if (i > max)
/*     */     {
/* 106 */       throw new NumberInvalidException("commands.generic.num.tooBig", new Object[] { Long.valueOf(i), Long.valueOf(max) });
/*     */     }
/*     */ 
/*     */     
/* 110 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static BlockPos parseBlockPos(ICommandSender sender, String[] args, int startIndex, boolean centerBlock) throws NumberInvalidException {
/* 116 */     BlockPos blockpos = sender.getPosition();
/* 117 */     return new BlockPos(parseDouble(blockpos.getX(), args[startIndex], -30000000, 30000000, centerBlock), parseDouble(blockpos.getY(), args[startIndex + 1], 0, 256, false), parseDouble(blockpos.getZ(), args[startIndex + 2], -30000000, 30000000, centerBlock));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static double parseDouble(String input) throws NumberInvalidException {
/*     */     try {
/* 124 */       double d0 = Double.parseDouble(input);
/*     */       
/* 126 */       if (!Doubles.isFinite(d0))
/*     */       {
/* 128 */         throw new NumberInvalidException("commands.generic.num.invalid", new Object[] { input });
/*     */       }
/*     */ 
/*     */       
/* 132 */       return d0;
/*     */     
/*     */     }
/* 135 */     catch (NumberFormatException var3) {
/*     */       
/* 137 */       throw new NumberInvalidException("commands.generic.num.invalid", new Object[] { input });
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static double parseDouble(String input, double min) throws NumberInvalidException {
/* 143 */     return parseDouble(input, min, Double.MAX_VALUE);
/*     */   }
/*     */ 
/*     */   
/*     */   public static double parseDouble(String input, double min, double max) throws NumberInvalidException {
/* 148 */     double d0 = parseDouble(input);
/*     */     
/* 150 */     if (d0 < min)
/*     */     {
/* 152 */       throw new NumberInvalidException("commands.generic.double.tooSmall", new Object[] { Double.valueOf(d0), Double.valueOf(min) });
/*     */     }
/* 154 */     if (d0 > max)
/*     */     {
/* 156 */       throw new NumberInvalidException("commands.generic.double.tooBig", new Object[] { Double.valueOf(d0), Double.valueOf(max) });
/*     */     }
/*     */ 
/*     */     
/* 160 */     return d0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean parseBoolean(String input) throws CommandException {
/* 166 */     if (!input.equals("true") && !input.equals("1")) {
/*     */       
/* 168 */       if (!input.equals("false") && !input.equals("0"))
/*     */       {
/* 170 */         throw new CommandException("commands.generic.boolean.invalid", new Object[] { input });
/*     */       }
/*     */ 
/*     */       
/* 174 */       return false;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 179 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static EntityPlayerMP getCommandSenderAsPlayer(ICommandSender sender) throws PlayerNotFoundException {
/* 188 */     if (sender instanceof EntityPlayerMP)
/*     */     {
/* 190 */       return (EntityPlayerMP)sender;
/*     */     }
/*     */ 
/*     */     
/* 194 */     throw new PlayerNotFoundException("You must specify which player you wish to perform this action on.", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static EntityPlayerMP getPlayer(ICommandSender sender, String username) throws PlayerNotFoundException {
/* 200 */     EntityPlayerMP entityplayermp = PlayerSelector.matchOnePlayer(sender, username);
/*     */     
/* 202 */     if (entityplayermp == null) {
/*     */       
/*     */       try {
/*     */         
/* 206 */         entityplayermp = MinecraftServer.getServer().getConfigurationManager().getPlayerByUUID(UUID.fromString(username));
/*     */       }
/* 208 */       catch (IllegalArgumentException illegalArgumentException) {}
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 214 */     if (entityplayermp == null)
/*     */     {
/* 216 */       entityplayermp = MinecraftServer.getServer().getConfigurationManager().getPlayerByUsername(username);
/*     */     }
/*     */     
/* 219 */     if (entityplayermp == null)
/*     */     {
/* 221 */       throw new PlayerNotFoundException();
/*     */     }
/*     */ 
/*     */     
/* 225 */     return entityplayermp;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Entity getEntity(ICommandSender p_175768_0_, String p_175768_1_) throws EntityNotFoundException {
/* 231 */     return getEntity(p_175768_0_, p_175768_1_, Entity.class);
/*     */   }
/*     */   
/*     */   public static <T extends Entity> T getEntity(ICommandSender commandSender, String p_175759_1_, Class<? extends T> p_175759_2_) throws EntityNotFoundException {
/*     */     EntityPlayerMP entityPlayerMP;
/* 236 */     Entity entity = PlayerSelector.matchOneEntity(commandSender, p_175759_1_, p_175759_2_);
/* 237 */     MinecraftServer minecraftserver = MinecraftServer.getServer();
/*     */     
/* 239 */     if (entity == null)
/*     */     {
/* 241 */       entityPlayerMP = minecraftserver.getConfigurationManager().getPlayerByUsername(p_175759_1_);
/*     */     }
/*     */     
/* 244 */     if (entityPlayerMP == null) {
/*     */       
/*     */       try {
/*     */         
/* 248 */         UUID uuid = UUID.fromString(p_175759_1_);
/* 249 */         Entity entity1 = minecraftserver.getEntityFromUuid(uuid);
/*     */         
/* 251 */         if (entity1 == null)
/*     */         {
/* 253 */           entityPlayerMP = minecraftserver.getConfigurationManager().getPlayerByUUID(uuid);
/*     */         }
/*     */       }
/* 256 */       catch (IllegalArgumentException var6) {
/*     */         
/* 258 */         throw new EntityNotFoundException("commands.generic.entity.invalidUuid", new Object[0]);
/*     */       } 
/*     */     }
/*     */     
/* 262 */     if (entityPlayerMP != null && p_175759_2_.isAssignableFrom(entityPlayerMP.getClass()))
/*     */     {
/* 264 */       return (T)entityPlayerMP;
/*     */     }
/*     */ 
/*     */     
/* 268 */     throw new EntityNotFoundException();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static List<Entity> func_175763_c(ICommandSender p_175763_0_, String p_175763_1_) throws EntityNotFoundException {
/* 274 */     return PlayerSelector.hasArguments(p_175763_1_) ? PlayerSelector.<Entity>matchEntities(p_175763_0_, p_175763_1_, Entity.class) : Lists.newArrayList((Object[])new Entity[] { getEntity(p_175763_0_, p_175763_1_) });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getPlayerName(ICommandSender sender, String query) throws PlayerNotFoundException {
/*     */     try {
/* 281 */       return getPlayer(sender, query).getName();
/*     */     }
/* 283 */     catch (PlayerNotFoundException playernotfoundexception) {
/*     */       
/* 285 */       if (PlayerSelector.hasArguments(query))
/*     */       {
/* 287 */         throw playernotfoundexception;
/*     */       }
/*     */ 
/*     */       
/* 291 */       return query;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getEntityName(ICommandSender p_175758_0_, String p_175758_1_) throws EntityNotFoundException {
/*     */     try {
/* 304 */       return getPlayer(p_175758_0_, p_175758_1_).getName();
/*     */     }
/* 306 */     catch (PlayerNotFoundException var5) {
/*     */ 
/*     */       
/*     */       try {
/* 310 */         return getEntity(p_175758_0_, p_175758_1_).getUniqueID().toString();
/*     */       }
/* 312 */       catch (EntityNotFoundException entitynotfoundexception) {
/*     */         
/* 314 */         if (PlayerSelector.hasArguments(p_175758_1_))
/*     */         {
/* 316 */           throw entitynotfoundexception;
/*     */         }
/*     */ 
/*     */         
/* 320 */         return p_175758_1_;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static IChatComponent getChatComponentFromNthArg(ICommandSender sender, String[] args, int p_147178_2_) throws CommandException {
/* 327 */     return getChatComponentFromNthArg(sender, args, p_147178_2_, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public static IChatComponent getChatComponentFromNthArg(ICommandSender sender, String[] args, int index, boolean p_147176_3_) throws PlayerNotFoundException {
/* 332 */     ChatComponentText chatComponentText = new ChatComponentText("");
/*     */     
/* 334 */     for (int i = index; i < args.length; i++) {
/*     */       IChatComponent iChatComponent;
/* 336 */       if (i > index)
/*     */       {
/* 338 */         chatComponentText.appendText(" ");
/*     */       }
/*     */       
/* 341 */       ChatComponentText chatComponentText1 = new ChatComponentText(args[i]);
/*     */       
/* 343 */       if (p_147176_3_) {
/*     */         
/* 345 */         IChatComponent ichatcomponent2 = PlayerSelector.matchEntitiesToChatComponent(sender, args[i]);
/*     */         
/* 347 */         if (ichatcomponent2 == null) {
/*     */           
/* 349 */           if (PlayerSelector.hasArguments(args[i]))
/*     */           {
/* 351 */             throw new PlayerNotFoundException();
/*     */           }
/*     */         }
/*     */         else {
/*     */           
/* 356 */           iChatComponent = ichatcomponent2;
/*     */         } 
/*     */       } 
/*     */       
/* 360 */       chatComponentText.appendSibling(iChatComponent);
/*     */     } 
/*     */     
/* 363 */     return (IChatComponent)chatComponentText;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String buildString(String[] args, int startPos) {
/* 371 */     StringBuilder stringbuilder = new StringBuilder();
/*     */     
/* 373 */     for (int i = startPos; i < args.length; i++) {
/*     */       
/* 375 */       if (i > startPos)
/*     */       {
/* 377 */         stringbuilder.append(" ");
/*     */       }
/*     */       
/* 380 */       String s = args[i];
/* 381 */       stringbuilder.append(s);
/*     */     } 
/*     */     
/* 384 */     return stringbuilder.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public static CoordinateArg parseCoordinate(double base, String p_175770_2_, boolean centerBlock) throws NumberInvalidException {
/* 389 */     return parseCoordinate(base, p_175770_2_, -30000000, 30000000, centerBlock);
/*     */   }
/*     */ 
/*     */   
/*     */   public static CoordinateArg parseCoordinate(double p_175767_0_, String p_175767_2_, int min, int max, boolean centerBlock) throws NumberInvalidException {
/* 394 */     boolean flag = (!p_175767_2_.isEmpty() && p_175767_2_.charAt(0) == '~');
/*     */     
/* 396 */     if (flag && Double.isNaN(p_175767_0_))
/*     */     {
/* 398 */       throw new NumberInvalidException("commands.generic.num.invalid", new Object[] { Double.valueOf(p_175767_0_) });
/*     */     }
/*     */ 
/*     */     
/* 402 */     double d0 = 0.0D;
/*     */     
/* 404 */     if (!flag || p_175767_2_.length() > 1) {
/*     */       
/* 406 */       boolean flag1 = p_175767_2_.contains(".");
/*     */       
/* 408 */       if (flag)
/*     */       {
/* 410 */         p_175767_2_ = p_175767_2_.substring(1);
/*     */       }
/*     */       
/* 413 */       d0 += parseDouble(p_175767_2_);
/*     */       
/* 415 */       if (!flag1 && !flag && centerBlock)
/*     */       {
/* 417 */         d0 += 0.5D;
/*     */       }
/*     */     } 
/*     */     
/* 421 */     if (min != 0 || max != 0) {
/*     */       
/* 423 */       if (d0 < min)
/*     */       {
/* 425 */         throw new NumberInvalidException("commands.generic.double.tooSmall", new Object[] { Double.valueOf(d0), Integer.valueOf(min) });
/*     */       }
/*     */       
/* 428 */       if (d0 > max)
/*     */       {
/* 430 */         throw new NumberInvalidException("commands.generic.double.tooBig", new Object[] { Double.valueOf(d0), Integer.valueOf(max) });
/*     */       }
/*     */     } 
/*     */     
/* 434 */     return new CoordinateArg(d0 + (flag ? p_175767_0_ : 0.0D), d0, flag);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static double parseDouble(double base, String input, boolean centerBlock) throws NumberInvalidException {
/* 440 */     return parseDouble(base, input, -30000000, 30000000, centerBlock);
/*     */   }
/*     */ 
/*     */   
/*     */   public static double parseDouble(double base, String input, int min, int max, boolean centerBlock) throws NumberInvalidException {
/* 445 */     boolean flag = (!input.isEmpty() && input.charAt(0) == '~');
/*     */     
/* 447 */     if (flag && Double.isNaN(base))
/*     */     {
/* 449 */       throw new NumberInvalidException("commands.generic.num.invalid", new Object[] { Double.valueOf(base) });
/*     */     }
/*     */ 
/*     */     
/* 453 */     double d0 = flag ? base : 0.0D;
/*     */     
/* 455 */     if (!flag || input.length() > 1) {
/*     */       
/* 457 */       boolean flag1 = input.contains(".");
/*     */       
/* 459 */       if (flag)
/*     */       {
/* 461 */         input = input.substring(1);
/*     */       }
/*     */       
/* 464 */       d0 += parseDouble(input);
/*     */       
/* 466 */       if (!flag1 && !flag && centerBlock)
/*     */       {
/* 468 */         d0 += 0.5D;
/*     */       }
/*     */     } 
/*     */     
/* 472 */     if (min != 0 || max != 0) {
/*     */       
/* 474 */       if (d0 < min)
/*     */       {
/* 476 */         throw new NumberInvalidException("commands.generic.double.tooSmall", new Object[] { Double.valueOf(d0), Integer.valueOf(min) });
/*     */       }
/*     */       
/* 479 */       if (d0 > max)
/*     */       {
/* 481 */         throw new NumberInvalidException("commands.generic.double.tooBig", new Object[] { Double.valueOf(d0), Integer.valueOf(max) });
/*     */       }
/*     */     } 
/*     */     
/* 485 */     return d0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Item getItemByText(ICommandSender sender, String id) throws NumberInvalidException {
/* 496 */     ResourceLocation resourcelocation = new ResourceLocation(id);
/* 497 */     Item item = (Item)Item.itemRegistry.getObject(resourcelocation);
/*     */     
/* 499 */     if (item == null)
/*     */     {
/* 501 */       throw new NumberInvalidException("commands.give.item.notFound", new Object[] { resourcelocation });
/*     */     }
/*     */ 
/*     */     
/* 505 */     return item;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Block getBlockByText(ICommandSender sender, String id) throws NumberInvalidException {
/* 516 */     ResourceLocation resourcelocation = new ResourceLocation(id);
/*     */     
/* 518 */     if (!Block.blockRegistry.containsKey(resourcelocation))
/*     */     {
/* 520 */       throw new NumberInvalidException("commands.give.block.notFound", new Object[] { resourcelocation });
/*     */     }
/*     */ 
/*     */     
/* 524 */     Block block = (Block)Block.blockRegistry.getObject(resourcelocation);
/*     */     
/* 526 */     if (block == null)
/*     */     {
/* 528 */       throw new NumberInvalidException("commands.give.block.notFound", new Object[] { resourcelocation });
/*     */     }
/*     */ 
/*     */     
/* 532 */     return block;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String joinNiceString(Object[] elements) {
/* 543 */     StringBuilder stringbuilder = new StringBuilder();
/*     */     
/* 545 */     for (int i = 0; i < elements.length; i++) {
/*     */       
/* 547 */       String s = elements[i].toString();
/*     */       
/* 549 */       if (i > 0)
/*     */       {
/* 551 */         if (i == elements.length - 1) {
/*     */           
/* 553 */           stringbuilder.append(" and ");
/*     */         }
/*     */         else {
/*     */           
/* 557 */           stringbuilder.append(", ");
/*     */         } 
/*     */       }
/*     */       
/* 561 */       stringbuilder.append(s);
/*     */     } 
/*     */     
/* 564 */     return stringbuilder.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public static IChatComponent join(List<IChatComponent> components) {
/* 569 */     ChatComponentText chatComponentText = new ChatComponentText("");
/*     */     
/* 571 */     for (int i = 0; i < components.size(); i++) {
/*     */       
/* 573 */       if (i > 0)
/*     */       {
/* 575 */         if (i == components.size() - 1) {
/*     */           
/* 577 */           chatComponentText.appendText(" and ");
/*     */         }
/* 579 */         else if (i > 0) {
/*     */           
/* 581 */           chatComponentText.appendText(", ");
/*     */         } 
/*     */       }
/*     */       
/* 585 */       chatComponentText.appendSibling(components.get(i));
/*     */     } 
/*     */     
/* 588 */     return (IChatComponent)chatComponentText;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String joinNiceStringFromCollection(Collection<String> strings) {
/* 598 */     return joinNiceString(strings.toArray((Object[])new String[0]));
/*     */   }
/*     */   
/*     */   public static List<String> func_175771_a(String[] p_175771_0_, int p_175771_1_, BlockPos p_175771_2_) {
/*     */     String s;
/* 603 */     if (p_175771_2_ == null)
/*     */     {
/* 605 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 609 */     int i = p_175771_0_.length - 1;
/*     */ 
/*     */     
/* 612 */     if (i == p_175771_1_) {
/*     */       
/* 614 */       s = Integer.toString(p_175771_2_.getX());
/*     */     }
/* 616 */     else if (i == p_175771_1_ + 1) {
/*     */       
/* 618 */       s = Integer.toString(p_175771_2_.getY());
/*     */     }
/*     */     else {
/*     */       
/* 622 */       if (i != p_175771_1_ + 2)
/*     */       {
/* 624 */         return null;
/*     */       }
/*     */       
/* 627 */       s = Integer.toString(p_175771_2_.getZ());
/*     */     } 
/*     */     
/* 630 */     return Lists.newArrayList((Object[])new String[] { s });
/*     */   }
/*     */ 
/*     */   
/*     */   public static List<String> func_181043_b(String[] p_181043_0_, int p_181043_1_, BlockPos p_181043_2_) {
/*     */     String s;
/* 636 */     if (p_181043_2_ == null)
/*     */     {
/* 638 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 642 */     int i = p_181043_0_.length - 1;
/*     */ 
/*     */     
/* 645 */     if (i == p_181043_1_) {
/*     */       
/* 647 */       s = Integer.toString(p_181043_2_.getX());
/*     */     }
/*     */     else {
/*     */       
/* 651 */       if (i != p_181043_1_ + 1)
/*     */       {
/* 653 */         return null;
/*     */       }
/*     */       
/* 656 */       s = Integer.toString(p_181043_2_.getZ());
/*     */     } 
/*     */     
/* 659 */     return Lists.newArrayList((Object[])new String[] { s });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean doesStringStartWith(String original, String region) {
/* 668 */     return region.regionMatches(true, 0, original, 0, original.length());
/*     */   }
/*     */ 
/*     */   
/*     */   public static List<String> getListOfStringsMatchingLastWord(String[] args, String... possibilities) {
/* 673 */     return getListOfStringsMatchingLastWord(args, Arrays.asList((Object[])possibilities));
/*     */   }
/*     */ 
/*     */   
/*     */   public static List<String> getListOfStringsMatchingLastWord(String[] p_175762_0_, Collection<?> p_175762_1_) {
/* 678 */     String s = p_175762_0_[p_175762_0_.length - 1];
/* 679 */     List<String> list = Lists.newArrayList();
/*     */     
/* 681 */     if (!p_175762_1_.isEmpty()) {
/*     */       
/* 683 */       for (String s1 : Iterables.transform(p_175762_1_, Functions.toStringFunction())) {
/*     */         
/* 685 */         if (doesStringStartWith(s, s1))
/*     */         {
/* 687 */           list.add(s1);
/*     */         }
/*     */       } 
/*     */       
/* 691 */       if (list.isEmpty())
/*     */       {
/* 693 */         for (Object object : p_175762_1_) {
/*     */           
/* 695 */           if (object instanceof ResourceLocation && doesStringStartWith(s, ((ResourceLocation)object).getResourcePath()))
/*     */           {
/* 697 */             list.add(String.valueOf(object));
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 703 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUsernameIndex(String[] args, int index) {
/* 711 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void notifyOperators(ICommandSender sender, ICommand command, String msgFormat, Object... msgParams) {
/* 716 */     notifyOperators(sender, command, 0, msgFormat, msgParams);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void notifyOperators(ICommandSender sender, ICommand command, int p_152374_2_, String msgFormat, Object... msgParams) {
/* 721 */     if (theAdmin != null)
/*     */     {
/* 723 */       theAdmin.notifyOperators(sender, command, p_152374_2_, msgFormat, msgParams);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setAdminCommander(IAdminCommand command) {
/* 732 */     theAdmin = command;
/*     */   }
/*     */ 
/*     */   
/*     */   public int compareTo(ICommand p_compareTo_1_) {
/* 737 */     return getCommandName().compareTo(p_compareTo_1_.getCommandName());
/*     */   }
/*     */ 
/*     */   
/*     */   public static class CoordinateArg
/*     */   {
/*     */     private final double field_179633_a;
/*     */     private final double field_179631_b;
/*     */     private final boolean field_179632_c;
/*     */     
/*     */     protected CoordinateArg(double p_i46051_1_, double p_i46051_3_, boolean p_i46051_5_) {
/* 748 */       this.field_179633_a = p_i46051_1_;
/* 749 */       this.field_179631_b = p_i46051_3_;
/* 750 */       this.field_179632_c = p_i46051_5_;
/*     */     }
/*     */ 
/*     */     
/*     */     public double func_179628_a() {
/* 755 */       return this.field_179633_a;
/*     */     }
/*     */ 
/*     */     
/*     */     public double func_179629_b() {
/* 760 */       return this.field_179631_b;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_179630_c() {
/* 765 */       return this.field_179632_c;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\command\CommandBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */