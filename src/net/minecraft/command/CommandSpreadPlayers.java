/*     */ package net.minecraft.command;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import java.util.Set;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.scoreboard.Team;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommandSpreadPlayers
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  28 */     return "spreadplayers";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequiredPermissionLevel() {
/*  36 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandUsage(ICommandSender sender) {
/*  44 */     return "commands.spreadplayers.usage";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/*  52 */     if (args.length < 6)
/*     */     {
/*  54 */       throw new WrongUsageException("commands.spreadplayers.usage", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  58 */     int i = 0;
/*  59 */     BlockPos blockpos = sender.getPosition();
/*  60 */     double d0 = parseDouble(blockpos.getX(), args[i++], true);
/*  61 */     double d1 = parseDouble(blockpos.getZ(), args[i++], true);
/*  62 */     double d2 = parseDouble(args[i++], 0.0D);
/*  63 */     double d3 = parseDouble(args[i++], d2 + 1.0D);
/*  64 */     boolean flag = parseBoolean(args[i++]);
/*  65 */     List<Entity> list = Lists.newArrayList();
/*     */     
/*  67 */     while (i < args.length) {
/*     */       
/*  69 */       String s = args[i++];
/*     */       
/*  71 */       if (PlayerSelector.hasArguments(s)) {
/*     */         
/*  73 */         List<Entity> list1 = PlayerSelector.matchEntities(sender, s, Entity.class);
/*     */         
/*  75 */         if (list1.isEmpty())
/*     */         {
/*  77 */           throw new EntityNotFoundException();
/*     */         }
/*     */         
/*  80 */         list.addAll(list1);
/*     */         
/*     */         continue;
/*     */       } 
/*  84 */       EntityPlayerMP entityPlayerMP = MinecraftServer.getServer().getConfigurationManager().getPlayerByUsername(s);
/*     */       
/*  86 */       if (entityPlayerMP == null)
/*     */       {
/*  88 */         throw new PlayerNotFoundException();
/*     */       }
/*     */       
/*  91 */       list.add(entityPlayerMP);
/*     */     } 
/*     */ 
/*     */     
/*  95 */     sender.setCommandStat(CommandResultStats.Type.AFFECTED_ENTITIES, list.size());
/*     */     
/*  97 */     if (list.isEmpty())
/*     */     {
/*  99 */       throw new EntityNotFoundException();
/*     */     }
/*     */ 
/*     */     
/* 103 */     sender.addChatMessage((IChatComponent)new ChatComponentTranslation("commands.spreadplayers.spreading." + (flag ? "teams" : "players"), new Object[] { Integer.valueOf(list.size()), Double.valueOf(d3), Double.valueOf(d0), Double.valueOf(d1), Double.valueOf(d2) }));
/* 104 */     func_110669_a(sender, list, new Position(d0, d1), d2, d3, ((Entity)list.get(0)).worldObj, flag);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void func_110669_a(ICommandSender p_110669_1_, List<Entity> p_110669_2_, Position p_110669_3_, double p_110669_4_, double p_110669_6_, World worldIn, boolean p_110669_9_) throws CommandException {
/* 111 */     Random random = new Random();
/* 112 */     double d0 = p_110669_3_.field_111101_a - p_110669_6_;
/* 113 */     double d1 = p_110669_3_.field_111100_b - p_110669_6_;
/* 114 */     double d2 = p_110669_3_.field_111101_a + p_110669_6_;
/* 115 */     double d3 = p_110669_3_.field_111100_b + p_110669_6_;
/* 116 */     Position[] acommandspreadplayers$position = func_110670_a(random, p_110669_9_ ? func_110667_a(p_110669_2_) : p_110669_2_.size(), d0, d1, d2, d3);
/* 117 */     int i = func_110668_a(p_110669_3_, p_110669_4_, worldIn, random, d0, d1, d2, d3, acommandspreadplayers$position, p_110669_9_);
/* 118 */     double d4 = func_110671_a(p_110669_2_, worldIn, acommandspreadplayers$position, p_110669_9_);
/* 119 */     notifyOperators(p_110669_1_, this, "commands.spreadplayers.success." + (p_110669_9_ ? "teams" : "players"), new Object[] { Integer.valueOf(acommandspreadplayers$position.length), Double.valueOf(p_110669_3_.field_111101_a), Double.valueOf(p_110669_3_.field_111100_b) });
/*     */     
/* 121 */     if (acommandspreadplayers$position.length > 1)
/*     */     {
/* 123 */       p_110669_1_.addChatMessage((IChatComponent)new ChatComponentTranslation("commands.spreadplayers.info." + (p_110669_9_ ? "teams" : "players"), new Object[] { String.format("%.2f", new Object[] { Double.valueOf(d4) }), Integer.valueOf(i) }));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private int func_110667_a(List<Entity> p_110667_1_) {
/* 129 */     Set<Team> set = Sets.newHashSet();
/*     */     
/* 131 */     for (Entity entity : p_110667_1_) {
/*     */       
/* 133 */       if (entity instanceof EntityPlayer) {
/*     */         
/* 135 */         set.add(((EntityPlayer)entity).getTeam());
/*     */         
/*     */         continue;
/*     */       } 
/* 139 */       set.add((Team)null);
/*     */     } 
/*     */ 
/*     */     
/* 143 */     return set.size();
/*     */   }
/*     */ 
/*     */   
/*     */   private int func_110668_a(Position p_110668_1_, double p_110668_2_, World worldIn, Random p_110668_5_, double p_110668_6_, double p_110668_8_, double p_110668_10_, double p_110668_12_, Position[] p_110668_14_, boolean p_110668_15_) throws CommandException {
/* 148 */     boolean flag = true;
/* 149 */     double d0 = 3.4028234663852886E38D;
/*     */     
/*     */     int i;
/* 152 */     for (i = 0; i < 10000 && flag; i++) {
/*     */       
/* 154 */       flag = false;
/* 155 */       d0 = 3.4028234663852886E38D;
/*     */       
/* 157 */       for (int j = 0; j < p_110668_14_.length; j++) {
/*     */         
/* 159 */         Position commandspreadplayers$position = p_110668_14_[j];
/* 160 */         int k = 0;
/* 161 */         Position commandspreadplayers$position1 = new Position();
/*     */         
/* 163 */         for (int l = 0; l < p_110668_14_.length; l++) {
/*     */           
/* 165 */           if (j != l) {
/*     */             
/* 167 */             Position commandspreadplayers$position2 = p_110668_14_[l];
/* 168 */             double d1 = commandspreadplayers$position.func_111099_a(commandspreadplayers$position2);
/* 169 */             d0 = Math.min(d1, d0);
/*     */             
/* 171 */             if (d1 < p_110668_2_) {
/*     */               
/* 173 */               k++;
/* 174 */               commandspreadplayers$position1.field_111101_a += commandspreadplayers$position2.field_111101_a - commandspreadplayers$position.field_111101_a;
/* 175 */               commandspreadplayers$position1.field_111100_b += commandspreadplayers$position2.field_111100_b - commandspreadplayers$position.field_111100_b;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 180 */         if (k > 0) {
/*     */           
/* 182 */           commandspreadplayers$position1.field_111101_a /= k;
/* 183 */           commandspreadplayers$position1.field_111100_b /= k;
/* 184 */           double d2 = commandspreadplayers$position1.func_111096_b();
/*     */           
/* 186 */           if (d2 > 0.0D) {
/*     */             
/* 188 */             commandspreadplayers$position1.func_111095_a();
/* 189 */             commandspreadplayers$position.func_111094_b(commandspreadplayers$position1);
/*     */           }
/*     */           else {
/*     */             
/* 193 */             commandspreadplayers$position.func_111097_a(p_110668_5_, p_110668_6_, p_110668_8_, p_110668_10_, p_110668_12_);
/*     */           } 
/*     */           
/* 196 */           flag = true;
/*     */         } 
/*     */         
/* 199 */         if (commandspreadplayers$position.func_111093_a(p_110668_6_, p_110668_8_, p_110668_10_, p_110668_12_))
/*     */         {
/* 201 */           flag = true;
/*     */         }
/*     */       } 
/*     */       
/* 205 */       if (!flag)
/*     */       {
/* 207 */         for (Position commandspreadplayers$position3 : p_110668_14_) {
/*     */           
/* 209 */           if (!commandspreadplayers$position3.func_111098_b(worldIn)) {
/*     */             
/* 211 */             commandspreadplayers$position3.func_111097_a(p_110668_5_, p_110668_6_, p_110668_8_, p_110668_10_, p_110668_12_);
/* 212 */             flag = true;
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 218 */     if (i >= 10000)
/*     */     {
/* 220 */       throw new CommandException("commands.spreadplayers.failure." + (p_110668_15_ ? "teams" : "players"), new Object[] { Integer.valueOf(p_110668_14_.length), Double.valueOf(p_110668_1_.field_111101_a), Double.valueOf(p_110668_1_.field_111100_b), String.format("%.2f", new Object[] { Double.valueOf(d0) }) });
/*     */     }
/*     */ 
/*     */     
/* 224 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private double func_110671_a(List<Entity> p_110671_1_, World worldIn, Position[] p_110671_3_, boolean p_110671_4_) {
/* 230 */     double d0 = 0.0D;
/* 231 */     int i = 0;
/* 232 */     Map<Team, Position> map = Maps.newHashMap();
/*     */     
/* 234 */     for (int j = 0; j < p_110671_1_.size(); j++) {
/*     */       Position commandspreadplayers$position;
/* 236 */       Entity entity = p_110671_1_.get(j);
/*     */ 
/*     */       
/* 239 */       if (p_110671_4_) {
/*     */         
/* 241 */         Team team = (entity instanceof EntityPlayer) ? ((EntityPlayer)entity).getTeam() : null;
/*     */         
/* 243 */         if (!map.containsKey(team))
/*     */         {
/* 245 */           map.put(team, p_110671_3_[i++]);
/*     */         }
/*     */         
/* 248 */         commandspreadplayers$position = map.get(team);
/*     */       }
/*     */       else {
/*     */         
/* 252 */         commandspreadplayers$position = p_110671_3_[i++];
/*     */       } 
/*     */       
/* 255 */       entity.setPositionAndUpdate((MathHelper.floor_double(commandspreadplayers$position.field_111101_a) + 0.5F), commandspreadplayers$position.func_111092_a(worldIn), MathHelper.floor_double(commandspreadplayers$position.field_111100_b) + 0.5D);
/* 256 */       double d2 = Double.MAX_VALUE;
/*     */       
/* 258 */       for (int k = 0; k < p_110671_3_.length; k++) {
/*     */         
/* 260 */         if (commandspreadplayers$position != p_110671_3_[k]) {
/*     */           
/* 262 */           double d1 = commandspreadplayers$position.func_111099_a(p_110671_3_[k]);
/* 263 */           d2 = Math.min(d1, d2);
/*     */         } 
/*     */       } 
/*     */       
/* 267 */       d0 += d2;
/*     */     } 
/*     */     
/* 270 */     d0 /= p_110671_1_.size();
/* 271 */     return d0;
/*     */   }
/*     */ 
/*     */   
/*     */   private Position[] func_110670_a(Random p_110670_1_, int p_110670_2_, double p_110670_3_, double p_110670_5_, double p_110670_7_, double p_110670_9_) {
/* 276 */     Position[] acommandspreadplayers$position = new Position[p_110670_2_];
/*     */     
/* 278 */     for (int i = 0; i < acommandspreadplayers$position.length; i++) {
/*     */       
/* 280 */       Position commandspreadplayers$position = new Position();
/* 281 */       commandspreadplayers$position.func_111097_a(p_110670_1_, p_110670_3_, p_110670_5_, p_110670_7_, p_110670_9_);
/* 282 */       acommandspreadplayers$position[i] = commandspreadplayers$position;
/*     */     } 
/*     */     
/* 285 */     return acommandspreadplayers$position;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 290 */     return (args.length >= 1 && args.length <= 2) ? func_181043_b(args, 0, pos) : null;
/*     */   }
/*     */ 
/*     */   
/*     */   static class Position
/*     */   {
/*     */     double field_111101_a;
/*     */     
/*     */     double field_111100_b;
/*     */ 
/*     */     
/*     */     Position() {}
/*     */     
/*     */     Position(double p_i1358_1_, double p_i1358_3_) {
/* 304 */       this.field_111101_a = p_i1358_1_;
/* 305 */       this.field_111100_b = p_i1358_3_;
/*     */     }
/*     */ 
/*     */     
/*     */     double func_111099_a(Position p_111099_1_) {
/* 310 */       double d0 = this.field_111101_a - p_111099_1_.field_111101_a;
/* 311 */       double d1 = this.field_111100_b - p_111099_1_.field_111100_b;
/* 312 */       return Math.sqrt(d0 * d0 + d1 * d1);
/*     */     }
/*     */ 
/*     */     
/*     */     void func_111095_a() {
/* 317 */       double d0 = func_111096_b();
/* 318 */       this.field_111101_a /= d0;
/* 319 */       this.field_111100_b /= d0;
/*     */     }
/*     */ 
/*     */     
/*     */     float func_111096_b() {
/* 324 */       return MathHelper.sqrt_double(this.field_111101_a * this.field_111101_a + this.field_111100_b * this.field_111100_b);
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_111094_b(Position p_111094_1_) {
/* 329 */       this.field_111101_a -= p_111094_1_.field_111101_a;
/* 330 */       this.field_111100_b -= p_111094_1_.field_111100_b;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_111093_a(double p_111093_1_, double p_111093_3_, double p_111093_5_, double p_111093_7_) {
/* 335 */       boolean flag = false;
/*     */       
/* 337 */       if (this.field_111101_a < p_111093_1_) {
/*     */         
/* 339 */         this.field_111101_a = p_111093_1_;
/* 340 */         flag = true;
/*     */       }
/* 342 */       else if (this.field_111101_a > p_111093_5_) {
/*     */         
/* 344 */         this.field_111101_a = p_111093_5_;
/* 345 */         flag = true;
/*     */       } 
/*     */       
/* 348 */       if (this.field_111100_b < p_111093_3_) {
/*     */         
/* 350 */         this.field_111100_b = p_111093_3_;
/* 351 */         flag = true;
/*     */       }
/* 353 */       else if (this.field_111100_b > p_111093_7_) {
/*     */         
/* 355 */         this.field_111100_b = p_111093_7_;
/* 356 */         flag = true;
/*     */       } 
/*     */       
/* 359 */       return flag;
/*     */     }
/*     */ 
/*     */     
/*     */     public int func_111092_a(World worldIn) {
/* 364 */       BlockPos blockpos = new BlockPos(this.field_111101_a, 256.0D, this.field_111100_b);
/*     */       
/* 366 */       while (blockpos.getY() > 0) {
/*     */         
/* 368 */         blockpos = blockpos.down();
/*     */         
/* 370 */         if (worldIn.getBlockState(blockpos).getBlock().getMaterial() != Material.air)
/*     */         {
/* 372 */           return blockpos.getY() + 1;
/*     */         }
/*     */       } 
/*     */       
/* 376 */       return 257;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_111098_b(World worldIn) {
/* 381 */       BlockPos blockpos = new BlockPos(this.field_111101_a, 256.0D, this.field_111100_b);
/*     */       
/* 383 */       while (blockpos.getY() > 0) {
/*     */         
/* 385 */         blockpos = blockpos.down();
/* 386 */         Material material = worldIn.getBlockState(blockpos).getBlock().getMaterial();
/*     */         
/* 388 */         if (material != Material.air)
/*     */         {
/* 390 */           return (!material.isLiquid() && material != Material.fire);
/*     */         }
/*     */       } 
/*     */       
/* 394 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_111097_a(Random p_111097_1_, double p_111097_2_, double p_111097_4_, double p_111097_6_, double p_111097_8_) {
/* 399 */       this.field_111101_a = MathHelper.getRandomDoubleInRange(p_111097_1_, p_111097_2_, p_111097_6_);
/* 400 */       this.field_111100_b = MathHelper.getRandomDoubleInRange(p_111097_1_, p_111097_4_, p_111097_8_);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\command\CommandSpreadPlayers.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */