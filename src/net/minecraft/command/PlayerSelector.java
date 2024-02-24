/*     */ package net.minecraft.command;
/*     */ import com.google.common.base.Predicate;
/*     */ import com.google.common.base.Predicates;
/*     */ import com.google.common.collect.ComparisonChain;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityList;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.scoreboard.Score;
/*     */ import net.minecraft.scoreboard.ScoreObjective;
/*     */ import net.minecraft.scoreboard.Scoreboard;
/*     */ import net.minecraft.scoreboard.Team;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.EntitySelectors;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldSettings;
/*     */ 
/*     */ public class PlayerSelector {
/*  33 */   private static final Pattern tokenPattern = Pattern.compile("^@([pare])(?:\\[([\\w=,!-]*)\\])?$");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  38 */   private static final Pattern intListPattern = Pattern.compile("\\G([-!]?[\\w-]*)(?:$|,)");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  43 */   private static final Pattern keyValueListPattern = Pattern.compile("\\G(\\w+)=([-!]?[\\w-]*)(?:$|,)");
/*  44 */   private static final Set<String> WORLD_BINDING_ARGS = Sets.newHashSet((Object[])new String[] { "x", "y", "z", "dx", "dy", "dz", "rm", "r" });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static EntityPlayerMP matchOnePlayer(ICommandSender sender, String token) {
/*  51 */     return matchOneEntity(sender, token, EntityPlayerMP.class);
/*     */   }
/*     */ 
/*     */   
/*     */   public static <T extends Entity> T matchOneEntity(ICommandSender sender, String token, Class<? extends T> targetClass) {
/*  56 */     List<T> list = matchEntities(sender, token, targetClass);
/*  57 */     return (list.size() == 1) ? list.get(0) : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static IChatComponent matchEntitiesToChatComponent(ICommandSender sender, String token) {
/*  62 */     List<Entity> list = matchEntities(sender, token, Entity.class);
/*     */     
/*  64 */     if (list.isEmpty())
/*     */     {
/*  66 */       return null;
/*     */     }
/*     */ 
/*     */     
/*  70 */     List<IChatComponent> list1 = Lists.newArrayList();
/*     */     
/*  72 */     for (Entity entity : list)
/*     */     {
/*  74 */       list1.add(entity.getDisplayName());
/*     */     }
/*     */     
/*  77 */     return CommandBase.join(list1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T extends Entity> List<T> matchEntities(ICommandSender sender, String token, Class<? extends T> targetClass) {
/*  83 */     Matcher matcher = tokenPattern.matcher(token);
/*     */     
/*  85 */     if (matcher.matches() && sender.canCommandSenderUseCommand(1, "@")) {
/*     */       
/*  87 */       Map<String, String> map = getArgumentMap(matcher.group(2));
/*     */       
/*  89 */       if (!isEntityTypeValid(sender, map))
/*     */       {
/*  91 */         return Collections.emptyList();
/*     */       }
/*     */ 
/*     */       
/*  95 */       String s = matcher.group(1);
/*  96 */       BlockPos blockpos = func_179664_b(map, sender.getPosition());
/*  97 */       List<World> list = getWorlds(sender, map);
/*  98 */       List<T> list1 = Lists.newArrayList();
/*     */       
/* 100 */       for (World world : list) {
/*     */         
/* 102 */         if (world != null) {
/*     */           
/* 104 */           List<Predicate<Entity>> list2 = Lists.newArrayList();
/* 105 */           list2.addAll(func_179663_a(map, s));
/* 106 */           list2.addAll(getXpLevelPredicates(map));
/* 107 */           list2.addAll(getGamemodePredicates(map));
/* 108 */           list2.addAll(getTeamPredicates(map));
/* 109 */           list2.addAll(getScorePredicates(map));
/* 110 */           list2.addAll(getNamePredicates(map));
/* 111 */           list2.addAll(func_180698_a(map, blockpos));
/* 112 */           list2.addAll(getRotationsPredicates(map));
/* 113 */           list1.addAll(filterResults(map, targetClass, list2, s, world, blockpos));
/*     */         } 
/*     */       } 
/*     */       
/* 117 */       return func_179658_a(list1, map, sender, targetClass, s, blockpos);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 122 */     return Collections.emptyList();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static List<World> getWorlds(ICommandSender sender, Map<String, String> argumentMap) {
/* 128 */     List<World> list = Lists.newArrayList();
/*     */     
/* 130 */     if (func_179665_h(argumentMap)) {
/*     */       
/* 132 */       list.add(sender.getEntityWorld());
/*     */     }
/*     */     else {
/*     */       
/* 136 */       Collections.addAll(list, (Object[])(MinecraftServer.getServer()).worldServers);
/*     */     } 
/*     */     
/* 139 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   private static <T extends Entity> boolean isEntityTypeValid(ICommandSender commandSender, Map<String, String> params) {
/* 144 */     String s = func_179651_b(params, "type");
/* 145 */     s = (s != null && !s.isEmpty() && s.charAt(0) == '!') ? s.substring(1) : s;
/*     */     
/* 147 */     if (s != null && !EntityList.isStringValidEntityName(s)) {
/*     */       
/* 149 */       ChatComponentTranslation chatcomponenttranslation = new ChatComponentTranslation("commands.generic.entity.invalidType", new Object[] { s });
/* 150 */       chatcomponenttranslation.getChatStyle().setColor(EnumChatFormatting.RED);
/* 151 */       commandSender.addChatMessage((IChatComponent)chatcomponenttranslation);
/* 152 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 156 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static List<Predicate<Entity>> func_179663_a(Map<String, String> p_179663_0_, String p_179663_1_) {
/* 162 */     List<Predicate<Entity>> list = Lists.newArrayList();
/* 163 */     String s = func_179651_b(p_179663_0_, "type");
/* 164 */     final boolean flag = (s != null && !s.isEmpty() && s.charAt(0) == '!');
/*     */     
/* 166 */     if (flag)
/*     */     {
/* 168 */       s = s.substring(1);
/*     */     }
/*     */     
/* 171 */     boolean flag1 = !p_179663_1_.equals("e");
/* 172 */     boolean flag2 = (p_179663_1_.equals("r") && s != null);
/*     */     
/* 174 */     if ((s == null || !p_179663_1_.equals("e")) && !flag2) {
/*     */       
/* 176 */       if (flag1)
/*     */       {
/* 178 */         list.add(new Predicate<Entity>()
/*     */             {
/*     */               public boolean apply(Entity p_apply_1_)
/*     */               {
/* 182 */                 return p_apply_1_ instanceof net.minecraft.entity.player.EntityPlayer;
/*     */               }
/*     */             });
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 189 */       final String s_f = s;
/* 190 */       list.add(new Predicate<Entity>()
/*     */           {
/*     */             public boolean apply(Entity p_apply_1_)
/*     */             {
/* 194 */               return (EntityList.isStringEntityName(p_apply_1_, s_f) != flag);
/*     */             }
/*     */           });
/*     */     } 
/*     */     
/* 199 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   private static List<Predicate<Entity>> getXpLevelPredicates(Map<String, String> p_179648_0_) {
/* 204 */     List<Predicate<Entity>> list = Lists.newArrayList();
/* 205 */     final int i = parseIntWithDefault(p_179648_0_, "lm", -1);
/* 206 */     final int j = parseIntWithDefault(p_179648_0_, "l", -1);
/*     */     
/* 208 */     if (i > -1 || j > -1)
/*     */     {
/* 210 */       list.add(new Predicate<Entity>()
/*     */           {
/*     */             public boolean apply(Entity p_apply_1_)
/*     */             {
/* 214 */               if (!(p_apply_1_ instanceof EntityPlayerMP))
/*     */               {
/* 216 */                 return false;
/*     */               }
/*     */ 
/*     */               
/* 220 */               EntityPlayerMP entityplayermp = (EntityPlayerMP)p_apply_1_;
/* 221 */               return ((i <= -1 || entityplayermp.experienceLevel >= i) && (j <= -1 || entityplayermp.experienceLevel <= j));
/*     */             }
/*     */           });
/*     */     }
/*     */ 
/*     */     
/* 227 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   private static List<Predicate<Entity>> getGamemodePredicates(Map<String, String> p_179649_0_) {
/* 232 */     List<Predicate<Entity>> list = Lists.newArrayList();
/* 233 */     final int i = parseIntWithDefault(p_179649_0_, "m", WorldSettings.GameType.NOT_SET.getID());
/*     */     
/* 235 */     if (i != WorldSettings.GameType.NOT_SET.getID())
/*     */     {
/* 237 */       list.add(new Predicate<Entity>()
/*     */           {
/*     */             public boolean apply(Entity p_apply_1_)
/*     */             {
/* 241 */               if (!(p_apply_1_ instanceof EntityPlayerMP))
/*     */               {
/* 243 */                 return false;
/*     */               }
/*     */ 
/*     */               
/* 247 */               EntityPlayerMP entityplayermp = (EntityPlayerMP)p_apply_1_;
/* 248 */               return (entityplayermp.theItemInWorldManager.getGameType().getID() == i);
/*     */             }
/*     */           });
/*     */     }
/*     */ 
/*     */     
/* 254 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   private static List<Predicate<Entity>> getTeamPredicates(Map<String, String> p_179659_0_) {
/* 259 */     List<Predicate<Entity>> list = Lists.newArrayList();
/* 260 */     String s = func_179651_b(p_179659_0_, "team");
/* 261 */     final boolean flag = (s != null && !s.isEmpty() && s.charAt(0) == '!');
/*     */     
/* 263 */     if (flag)
/*     */     {
/* 265 */       s = s.substring(1);
/*     */     }
/*     */     
/* 268 */     if (s != null) {
/*     */       
/* 270 */       final String s_f = s;
/* 271 */       list.add(new Predicate<Entity>()
/*     */           {
/*     */             public boolean apply(Entity p_apply_1_)
/*     */             {
/* 275 */               if (!(p_apply_1_ instanceof EntityLivingBase))
/*     */               {
/* 277 */                 return false;
/*     */               }
/*     */ 
/*     */               
/* 281 */               EntityLivingBase entitylivingbase = (EntityLivingBase)p_apply_1_;
/* 282 */               Team team = entitylivingbase.getTeam();
/* 283 */               String s1 = (team == null) ? "" : team.getRegisteredName();
/* 284 */               return (s1.equals(s_f) != flag);
/*     */             }
/*     */           });
/*     */     } 
/*     */ 
/*     */     
/* 290 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   private static List<Predicate<Entity>> getScorePredicates(Map<String, String> p_179657_0_) {
/* 295 */     List<Predicate<Entity>> list = Lists.newArrayList();
/* 296 */     final Map<String, Integer> map = func_96560_a(p_179657_0_);
/*     */     
/* 298 */     if (map != null && !map.isEmpty())
/*     */     {
/* 300 */       list.add(new Predicate<Entity>()
/*     */           {
/*     */             public boolean apply(Entity p_apply_1_)
/*     */             {
/* 304 */               Scoreboard scoreboard = MinecraftServer.getServer().worldServerForDimension(0).getScoreboard();
/*     */               
/* 306 */               for (Map.Entry<String, Integer> entry : (Iterable<Map.Entry<String, Integer>>)map.entrySet()) {
/*     */                 
/* 308 */                 String s = entry.getKey();
/* 309 */                 boolean flag = false;
/*     */                 
/* 311 */                 if (s.endsWith("_min") && s.length() > 4) {
/*     */                   
/* 313 */                   flag = true;
/* 314 */                   s = s.substring(0, s.length() - 4);
/*     */                 } 
/*     */                 
/* 317 */                 ScoreObjective scoreobjective = scoreboard.getObjective(s);
/*     */                 
/* 319 */                 if (scoreobjective == null)
/*     */                 {
/* 321 */                   return false;
/*     */                 }
/*     */                 
/* 324 */                 String s1 = (p_apply_1_ instanceof EntityPlayerMP) ? p_apply_1_.getName() : p_apply_1_.getUniqueID().toString();
/*     */                 
/* 326 */                 if (!scoreboard.entityHasObjective(s1, scoreobjective))
/*     */                 {
/* 328 */                   return false;
/*     */                 }
/*     */                 
/* 331 */                 Score score = scoreboard.getValueFromObjective(s1, scoreobjective);
/* 332 */                 int i = score.getScorePoints();
/*     */                 
/* 334 */                 if (i < ((Integer)entry.getValue()).intValue() && flag)
/*     */                 {
/* 336 */                   return false;
/*     */                 }
/*     */                 
/* 339 */                 if (i > ((Integer)entry.getValue()).intValue() && !flag)
/*     */                 {
/* 341 */                   return false;
/*     */                 }
/*     */               } 
/*     */               
/* 345 */               return true;
/*     */             }
/*     */           });
/*     */     }
/*     */     
/* 350 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   private static List<Predicate<Entity>> getNamePredicates(Map<String, String> p_179647_0_) {
/* 355 */     List<Predicate<Entity>> list = Lists.newArrayList();
/* 356 */     String s = func_179651_b(p_179647_0_, "name");
/* 357 */     final boolean flag = (s != null && !s.isEmpty() && s.charAt(0) == '!');
/*     */     
/* 359 */     if (flag)
/*     */     {
/* 361 */       s = s.substring(1);
/*     */     }
/*     */     
/* 364 */     if (s != null) {
/*     */       
/* 366 */       final String s_f = s;
/* 367 */       list.add(new Predicate<Entity>()
/*     */           {
/*     */             public boolean apply(Entity p_apply_1_)
/*     */             {
/* 371 */               return (p_apply_1_.getName().equals(s_f) != flag);
/*     */             }
/*     */           });
/*     */     } 
/*     */     
/* 376 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   private static List<Predicate<Entity>> func_180698_a(Map<String, String> p_180698_0_, final BlockPos p_180698_1_) {
/* 381 */     List<Predicate<Entity>> list = Lists.newArrayList();
/* 382 */     final int i = parseIntWithDefault(p_180698_0_, "rm", -1);
/* 383 */     final int j = parseIntWithDefault(p_180698_0_, "r", -1);
/*     */     
/* 385 */     if (p_180698_1_ != null && (i >= 0 || j >= 0)) {
/*     */       
/* 387 */       final int k = i * i;
/* 388 */       final int l = j * j;
/* 389 */       list.add(new Predicate<Entity>()
/*     */           {
/*     */             public boolean apply(Entity p_apply_1_)
/*     */             {
/* 393 */               int i1 = (int)p_apply_1_.getDistanceSqToCenter(p_180698_1_);
/* 394 */               return ((i < 0 || i1 >= k) && (j < 0 || i1 <= l));
/*     */             }
/*     */           });
/*     */     } 
/*     */     
/* 399 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   private static List<Predicate<Entity>> getRotationsPredicates(Map<String, String> p_179662_0_) {
/* 404 */     List<Predicate<Entity>> list = Lists.newArrayList();
/*     */     
/* 406 */     if (p_179662_0_.containsKey("rym") || p_179662_0_.containsKey("ry")) {
/*     */       
/* 408 */       final int i = func_179650_a(parseIntWithDefault(p_179662_0_, "rym", 0));
/* 409 */       final int j = func_179650_a(parseIntWithDefault(p_179662_0_, "ry", 359));
/* 410 */       list.add(new Predicate<Entity>()
/*     */           {
/*     */             public boolean apply(Entity p_apply_1_)
/*     */             {
/* 414 */               int i1 = PlayerSelector.func_179650_a((int)Math.floor(p_apply_1_.rotationYaw));
/* 415 */               return (i > j) ? ((i1 >= i || i1 <= j)) : ((i1 >= i && i1 <= j));
/*     */             }
/*     */           });
/*     */     } 
/*     */     
/* 420 */     if (p_179662_0_.containsKey("rxm") || p_179662_0_.containsKey("rx")) {
/*     */       
/* 422 */       final int k = func_179650_a(parseIntWithDefault(p_179662_0_, "rxm", 0));
/* 423 */       final int l = func_179650_a(parseIntWithDefault(p_179662_0_, "rx", 359));
/* 424 */       list.add(new Predicate<Entity>()
/*     */           {
/*     */             public boolean apply(Entity p_apply_1_)
/*     */             {
/* 428 */               int i1 = PlayerSelector.func_179650_a((int)Math.floor(p_apply_1_.rotationPitch));
/* 429 */               return (k > l) ? ((i1 >= k || i1 <= l)) : ((i1 >= k && i1 <= l));
/*     */             }
/*     */           });
/*     */     } 
/*     */     
/* 434 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   private static <T extends Entity> List<T> filterResults(Map<String, String> params, Class<? extends T> entityClass, List<Predicate<Entity>> inputList, String type, World worldIn, BlockPos position) {
/* 439 */     List<T> list = Lists.newArrayList();
/* 440 */     String s = func_179651_b(params, "type");
/* 441 */     s = (s != null && !s.isEmpty() && s.charAt(0) == '!') ? s.substring(1) : s;
/* 442 */     boolean flag = !type.equals("e");
/* 443 */     boolean flag1 = (type.equals("r") && s != null);
/* 444 */     int i = parseIntWithDefault(params, "dx", 0);
/* 445 */     int j = parseIntWithDefault(params, "dy", 0);
/* 446 */     int k = parseIntWithDefault(params, "dz", 0);
/* 447 */     int l = parseIntWithDefault(params, "r", -1);
/* 448 */     Predicate<Entity> predicate = Predicates.and(inputList);
/* 449 */     Predicate<Entity> predicate1 = Predicates.and(EntitySelectors.selectAnything, predicate);
/*     */     
/* 451 */     if (position != null) {
/*     */       
/* 453 */       int i1 = worldIn.playerEntities.size();
/* 454 */       int j1 = worldIn.loadedEntityList.size();
/* 455 */       boolean flag2 = (i1 < j1 / 16);
/*     */       
/* 457 */       if (!params.containsKey("dx") && !params.containsKey("dy") && !params.containsKey("dz")) {
/*     */         
/* 459 */         if (l >= 0) {
/*     */           
/* 461 */           AxisAlignedBB axisalignedbb1 = new AxisAlignedBB((position.getX() - l), (position.getY() - l), (position.getZ() - l), (position.getX() + l + 1), (position.getY() + l + 1), (position.getZ() + l + 1));
/*     */           
/* 463 */           if (flag && flag2 && !flag1)
/*     */           {
/* 465 */             list.addAll(worldIn.getPlayers(entityClass, predicate1));
/*     */           }
/*     */           else
/*     */           {
/* 469 */             list.addAll(worldIn.getEntitiesWithinAABB(entityClass, axisalignedbb1, predicate1));
/*     */           }
/*     */         
/* 472 */         } else if (type.equals("a")) {
/*     */           
/* 474 */           list.addAll(worldIn.getPlayers(entityClass, predicate));
/*     */         }
/* 476 */         else if (!type.equals("p") && (!type.equals("r") || flag1)) {
/*     */           
/* 478 */           list.addAll(worldIn.getEntities(entityClass, predicate1));
/*     */         }
/*     */         else {
/*     */           
/* 482 */           list.addAll(worldIn.getPlayers(entityClass, predicate1));
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 487 */         final AxisAlignedBB axisalignedbb = func_179661_a(position, i, j, k);
/*     */         
/* 489 */         if (flag && flag2 && !flag1)
/*     */         {
/* 491 */           Predicate<Entity> predicate2 = new Predicate<Entity>()
/*     */             {
/*     */               public boolean apply(Entity p_apply_1_)
/*     */               {
/* 495 */                 return (p_apply_1_.posX >= axisalignedbb.minX && p_apply_1_.posY >= axisalignedbb.minY && p_apply_1_.posZ >= axisalignedbb.minZ) ? ((p_apply_1_.posX < axisalignedbb.maxX && p_apply_1_.posY < axisalignedbb.maxY && p_apply_1_.posZ < axisalignedbb.maxZ)) : false;
/*     */               }
/*     */             };
/* 498 */           list.addAll(worldIn.getPlayers(entityClass, Predicates.and(predicate1, predicate2)));
/*     */         }
/*     */         else
/*     */         {
/* 502 */           list.addAll(worldIn.getEntitiesWithinAABB(entityClass, axisalignedbb, predicate1));
/*     */         }
/*     */       
/*     */       } 
/* 506 */     } else if (type.equals("a")) {
/*     */       
/* 508 */       list.addAll(worldIn.getPlayers(entityClass, predicate));
/*     */     }
/* 510 */     else if (!type.equals("p") && (!type.equals("r") || flag1)) {
/*     */       
/* 512 */       list.addAll(worldIn.getEntities(entityClass, predicate1));
/*     */     }
/*     */     else {
/*     */       
/* 516 */       list.addAll(worldIn.getPlayers(entityClass, predicate1));
/*     */     } 
/*     */     
/* 519 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   private static <T extends Entity> List<T> func_179658_a(List<T> p_179658_0_, Map<String, String> p_179658_1_, ICommandSender p_179658_2_, Class<? extends T> p_179658_3_, String p_179658_4_, final BlockPos p_179658_5_) {
/* 524 */     int i = parseIntWithDefault(p_179658_1_, "c", (!p_179658_4_.equals("a") && !p_179658_4_.equals("e")) ? 1 : 0);
/*     */     
/* 526 */     if (!p_179658_4_.equals("p") && !p_179658_4_.equals("a") && !p_179658_4_.equals("e")) {
/*     */       
/* 528 */       if (p_179658_4_.equals("r"))
/*     */       {
/* 530 */         Collections.shuffle(p_179658_0_);
/*     */       }
/*     */     }
/* 533 */     else if (p_179658_5_ != null) {
/*     */       
/* 535 */       p_179658_0_.sort((Comparator)new Comparator<Entity>() {
/*     */             public int compare(Entity p_compare_1_, Entity p_compare_2_) {
/* 537 */               return ComparisonChain.start().compare(p_compare_1_.getDistanceSq(p_179658_5_), p_compare_2_.getDistanceSq(p_179658_5_)).result();
/*     */             }
/*     */           });
/*     */     } 
/*     */     
/* 542 */     Entity entity = p_179658_2_.getCommandSenderEntity();
/*     */     
/* 544 */     if (entity != null && p_179658_3_.isAssignableFrom(entity.getClass()) && i == 1 && p_179658_0_.contains(entity) && !"r".equals(p_179658_4_))
/*     */     {
/* 546 */       p_179658_0_ = Lists.newArrayList((Object[])new Entity[] { entity });
/*     */     }
/*     */     
/* 549 */     if (i != 0) {
/*     */       
/* 551 */       if (i < 0)
/*     */       {
/* 553 */         Collections.reverse(p_179658_0_);
/*     */       }
/*     */       
/* 556 */       p_179658_0_ = p_179658_0_.subList(0, Math.min(Math.abs(i), p_179658_0_.size()));
/*     */     } 
/*     */     
/* 559 */     return p_179658_0_;
/*     */   }
/*     */ 
/*     */   
/*     */   private static AxisAlignedBB func_179661_a(BlockPos p_179661_0_, int p_179661_1_, int p_179661_2_, int p_179661_3_) {
/* 564 */     boolean flag = (p_179661_1_ < 0);
/* 565 */     boolean flag1 = (p_179661_2_ < 0);
/* 566 */     boolean flag2 = (p_179661_3_ < 0);
/* 567 */     int i = p_179661_0_.getX() + (flag ? p_179661_1_ : 0);
/* 568 */     int j = p_179661_0_.getY() + (flag1 ? p_179661_2_ : 0);
/* 569 */     int k = p_179661_0_.getZ() + (flag2 ? p_179661_3_ : 0);
/* 570 */     int l = p_179661_0_.getX() + (flag ? 0 : p_179661_1_) + 1;
/* 571 */     int i1 = p_179661_0_.getY() + (flag1 ? 0 : p_179661_2_) + 1;
/* 572 */     int j1 = p_179661_0_.getZ() + (flag2 ? 0 : p_179661_3_) + 1;
/* 573 */     return new AxisAlignedBB(i, j, k, l, i1, j1);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int func_179650_a(int p_179650_0_) {
/* 578 */     p_179650_0_ %= 360;
/*     */     
/* 580 */     if (p_179650_0_ >= 160)
/*     */     {
/* 582 */       p_179650_0_ -= 360;
/*     */     }
/*     */     
/* 585 */     if (p_179650_0_ < 0)
/*     */     {
/* 587 */       p_179650_0_ += 360;
/*     */     }
/*     */     
/* 590 */     return p_179650_0_;
/*     */   }
/*     */ 
/*     */   
/*     */   private static BlockPos func_179664_b(Map<String, String> p_179664_0_, BlockPos p_179664_1_) {
/* 595 */     return new BlockPos(parseIntWithDefault(p_179664_0_, "x", p_179664_1_.getX()), parseIntWithDefault(p_179664_0_, "y", p_179664_1_.getY()), parseIntWithDefault(p_179664_0_, "z", p_179664_1_.getZ()));
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean func_179665_h(Map<String, String> p_179665_0_) {
/* 600 */     for (String s : WORLD_BINDING_ARGS) {
/*     */       
/* 602 */       if (p_179665_0_.containsKey(s))
/*     */       {
/* 604 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 608 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private static int parseIntWithDefault(Map<String, String> p_179653_0_, String p_179653_1_, int p_179653_2_) {
/* 613 */     return p_179653_0_.containsKey(p_179653_1_) ? MathHelper.parseIntWithDefault(p_179653_0_.get(p_179653_1_), p_179653_2_) : p_179653_2_;
/*     */   }
/*     */ 
/*     */   
/*     */   private static String func_179651_b(Map<String, String> p_179651_0_, String p_179651_1_) {
/* 618 */     return p_179651_0_.get(p_179651_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Map<String, Integer> func_96560_a(Map<String, String> p_96560_0_) {
/* 623 */     Map<String, Integer> map = Maps.newHashMap();
/*     */     
/* 625 */     for (Map.Entry<String, String> entry : p_96560_0_.entrySet()) {
/*     */       
/* 627 */       String s = entry.getKey();
/* 628 */       if (s.startsWith("score_") && s.length() > "score_".length())
/*     */       {
/* 630 */         map.put(s.substring("score_".length()), Integer.valueOf(MathHelper.parseIntWithDefault(entry.getValue(), 1)));
/*     */       }
/*     */     } 
/*     */     
/* 634 */     return map;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean matchesMultiplePlayers(String p_82377_0_) {
/* 642 */     Matcher matcher = tokenPattern.matcher(p_82377_0_);
/*     */     
/* 644 */     if (!matcher.matches())
/*     */     {
/* 646 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 650 */     Map<String, String> map = getArgumentMap(matcher.group(2));
/* 651 */     String s = matcher.group(1);
/* 652 */     int i = (!"a".equals(s) && !"e".equals(s)) ? 1 : 0;
/* 653 */     return (parseIntWithDefault(map, "c", i) != 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean hasArguments(String p_82378_0_) {
/* 662 */     return tokenPattern.matcher(p_82378_0_).matches();
/*     */   }
/*     */ 
/*     */   
/*     */   private static Map<String, String> getArgumentMap(String argumentString) {
/* 667 */     Map<String, String> map = Maps.newHashMap();
/*     */     
/* 669 */     if (argumentString == null)
/*     */     {
/* 671 */       return map;
/*     */     }
/*     */ 
/*     */     
/* 675 */     int i = 0;
/* 676 */     int j = -1;
/*     */     
/* 678 */     for (Matcher matcher = intListPattern.matcher(argumentString); matcher.find(); j = matcher.end()) {
/*     */       
/* 680 */       String s = null;
/*     */       
/* 682 */       switch (i++) {
/*     */         
/*     */         case 0:
/* 685 */           s = "x";
/*     */           break;
/*     */         
/*     */         case 1:
/* 689 */           s = "y";
/*     */           break;
/*     */         
/*     */         case 2:
/* 693 */           s = "z";
/*     */           break;
/*     */         
/*     */         case 3:
/* 697 */           s = "r";
/*     */           break;
/*     */       } 
/* 700 */       if (s != null && !matcher.group(1).isEmpty())
/*     */       {
/* 702 */         map.put(s, matcher.group(1));
/*     */       }
/*     */     } 
/*     */     
/* 706 */     if (j < argumentString.length()) {
/*     */       
/* 708 */       Matcher matcher1 = keyValueListPattern.matcher((j == -1) ? argumentString : argumentString.substring(j));
/*     */       
/* 710 */       while (matcher1.find())
/*     */       {
/* 712 */         map.put(matcher1.group(1), matcher1.group(2));
/*     */       }
/*     */     } 
/*     */     
/* 716 */     return map;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\command\PlayerSelector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */