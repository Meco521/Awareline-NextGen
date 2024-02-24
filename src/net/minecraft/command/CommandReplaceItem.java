/*     */ package net.minecraft.command;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.JsonToNBT;
/*     */ import net.minecraft.nbt.NBTException;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class CommandReplaceItem
/*     */   extends CommandBase
/*     */ {
/*  23 */   private static final Map<String, Integer> SHORTCUTS = Maps.newHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandName() {
/*  30 */     return "replaceitem";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequiredPermissionLevel() {
/*  38 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandUsage(ICommandSender sender) {
/*  46 */     return "commands.replaceitem.usage";
/*     */   }
/*     */ 
/*     */   
/*     */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/*     */     boolean flag;
/*     */     int i;
/*     */     Item item;
/*  54 */     if (args.length < 1)
/*     */     {
/*  56 */       throw new WrongUsageException("commands.replaceitem.usage", new Object[0]);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  62 */     if (args[0].equals("entity")) {
/*     */       
/*  64 */       flag = false;
/*     */     }
/*     */     else {
/*     */       
/*  68 */       if (!args[0].equals("block"))
/*     */       {
/*  70 */         throw new WrongUsageException("commands.replaceitem.usage", new Object[0]);
/*     */       }
/*     */       
/*  73 */       flag = true;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  78 */     if (flag) {
/*     */       
/*  80 */       if (args.length < 6)
/*     */       {
/*  82 */         throw new WrongUsageException("commands.replaceitem.block.usage", new Object[0]);
/*     */       }
/*     */       
/*  85 */       i = 4;
/*     */     }
/*     */     else {
/*     */       
/*  89 */       if (args.length < 4)
/*     */       {
/*  91 */         throw new WrongUsageException("commands.replaceitem.entity.usage", new Object[0]);
/*     */       }
/*     */       
/*  94 */       i = 2;
/*     */     } 
/*     */     
/*  97 */     int j = getSlotForShortcut(args[i++]);
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 102 */       item = getItemByText(sender, args[i]);
/*     */     }
/* 104 */     catch (NumberInvalidException numberinvalidexception) {
/*     */       
/* 106 */       if (Block.getBlockFromName(args[i]) != Blocks.air)
/*     */       {
/* 108 */         throw numberinvalidexception;
/*     */       }
/*     */       
/* 111 */       item = null;
/*     */     } 
/*     */     
/* 114 */     i++;
/* 115 */     int k = (args.length > i) ? parseInt(args[i++], 1, 64) : 1;
/* 116 */     int l = (args.length > i) ? parseInt(args[i++]) : 0;
/* 117 */     ItemStack itemstack = new ItemStack(item, k, l);
/*     */     
/* 119 */     if (args.length > i) {
/*     */       
/* 121 */       String s = getChatComponentFromNthArg(sender, args, i).getUnformattedText();
/*     */ 
/*     */       
/*     */       try {
/* 125 */         itemstack.setTagCompound(JsonToNBT.getTagFromJson(s));
/*     */       }
/* 127 */       catch (NBTException nbtexception) {
/*     */         
/* 129 */         throw new CommandException("commands.replaceitem.tagError", new Object[] { nbtexception.getMessage() });
/*     */       } 
/*     */     } 
/*     */     
/* 133 */     if (itemstack.getItem() == null)
/*     */     {
/* 135 */       itemstack = null;
/*     */     }
/*     */     
/* 138 */     if (flag) {
/*     */       
/* 140 */       sender.setCommandStat(CommandResultStats.Type.AFFECTED_ITEMS, 0);
/* 141 */       BlockPos blockpos = parseBlockPos(sender, args, 1, false);
/* 142 */       World world = sender.getEntityWorld();
/* 143 */       TileEntity tileentity = world.getTileEntity(blockpos);
/*     */       
/* 145 */       if (tileentity == null || !(tileentity instanceof IInventory))
/*     */       {
/* 147 */         throw new CommandException("commands.replaceitem.noContainer", new Object[] { Integer.valueOf(blockpos.getX()), Integer.valueOf(blockpos.getY()), Integer.valueOf(blockpos.getZ()) });
/*     */       }
/*     */       
/* 150 */       IInventory iinventory = (IInventory)tileentity;
/*     */       
/* 152 */       if (j >= 0 && j < iinventory.getSizeInventory())
/*     */       {
/* 154 */         iinventory.setInventorySlotContents(j, itemstack);
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 159 */       Entity entity = getEntity(sender, args[1]);
/* 160 */       sender.setCommandStat(CommandResultStats.Type.AFFECTED_ITEMS, 0);
/*     */       
/* 162 */       if (entity instanceof EntityPlayer)
/*     */       {
/* 164 */         ((EntityPlayer)entity).inventoryContainer.detectAndSendChanges();
/*     */       }
/*     */       
/* 167 */       if (!entity.replaceItemInInventory(j, itemstack))
/*     */       {
/* 169 */         throw new CommandException("commands.replaceitem.failed", new Object[] { Integer.valueOf(j), Integer.valueOf(k), (itemstack == null) ? "Air" : itemstack.getChatComponent() });
/*     */       }
/*     */       
/* 172 */       if (entity instanceof EntityPlayer)
/*     */       {
/* 174 */         ((EntityPlayer)entity).inventoryContainer.detectAndSendChanges();
/*     */       }
/*     */     } 
/*     */     
/* 178 */     sender.setCommandStat(CommandResultStats.Type.AFFECTED_ITEMS, k);
/* 179 */     notifyOperators(sender, this, "commands.replaceitem.success", new Object[] { Integer.valueOf(j), Integer.valueOf(k), (itemstack == null) ? "Air" : itemstack.getChatComponent() });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private int getSlotForShortcut(String shortcut) throws CommandException {
/* 185 */     if (!SHORTCUTS.containsKey(shortcut))
/*     */     {
/* 187 */       throw new CommandException("commands.generic.parameter.invalid", new Object[] { shortcut });
/*     */     }
/*     */ 
/*     */     
/* 191 */     return ((Integer)SHORTCUTS.get(shortcut)).intValue();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 197 */     return (args.length == 1) ? getListOfStringsMatchingLastWord(args, new String[] { "entity", "block" }) : ((args.length == 2 && args[0].equals("entity")) ? getListOfStringsMatchingLastWord(args, getUsernames()) : ((args.length >= 2 && args.length <= 4 && args[0].equals("block")) ? func_175771_a(args, 1, pos) : (((args.length != 3 || !args[0].equals("entity")) && (args.length != 5 || !args[0].equals("block"))) ? (((args.length != 4 || !args[0].equals("entity")) && (args.length != 6 || !args[0].equals("block"))) ? null : getListOfStringsMatchingLastWord(args, Item.itemRegistry.getKeys())) : getListOfStringsMatchingLastWord(args, SHORTCUTS.keySet()))));
/*     */   }
/*     */ 
/*     */   
/*     */   protected String[] getUsernames() {
/* 202 */     return MinecraftServer.getServer().getAllUsernames();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUsernameIndex(String[] args, int index) {
/* 210 */     return (args.length > 0 && args[0].equals("entity") && index == 1);
/*     */   }
/*     */ 
/*     */   
/*     */   static {
/* 215 */     for (int i = 0; i < 54; i++)
/*     */     {
/* 217 */       SHORTCUTS.put("slot.container." + i, Integer.valueOf(i));
/*     */     }
/*     */     
/* 220 */     for (int j = 0; j < 9; j++)
/*     */     {
/* 222 */       SHORTCUTS.put("slot.hotbar." + j, Integer.valueOf(j));
/*     */     }
/*     */     
/* 225 */     for (int k = 0; k < 27; k++)
/*     */     {
/* 227 */       SHORTCUTS.put("slot.inventory." + k, Integer.valueOf(9 + k));
/*     */     }
/*     */     
/* 230 */     for (int l = 0; l < 27; l++)
/*     */     {
/* 232 */       SHORTCUTS.put("slot.enderchest." + l, Integer.valueOf(200 + l));
/*     */     }
/*     */     
/* 235 */     for (int i1 = 0; i1 < 8; i1++)
/*     */     {
/* 237 */       SHORTCUTS.put("slot.villager." + i1, Integer.valueOf(300 + i1));
/*     */     }
/*     */     
/* 240 */     for (int j1 = 0; j1 < 15; j1++)
/*     */     {
/* 242 */       SHORTCUTS.put("slot.horse." + j1, Integer.valueOf(500 + j1));
/*     */     }
/*     */     
/* 245 */     SHORTCUTS.put("slot.weapon", Integer.valueOf(99));
/* 246 */     SHORTCUTS.put("slot.armor.head", Integer.valueOf(103));
/* 247 */     SHORTCUTS.put("slot.armor.chest", Integer.valueOf(102));
/* 248 */     SHORTCUTS.put("slot.armor.legs", Integer.valueOf(101));
/* 249 */     SHORTCUTS.put("slot.armor.feet", Integer.valueOf(100));
/* 250 */     SHORTCUTS.put("slot.horse.saddle", Integer.valueOf(400));
/* 251 */     SHORTCUTS.put("slot.horse.armor", Integer.valueOf(401));
/* 252 */     SHORTCUTS.put("slot.horse.chest", Integer.valueOf(499));
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\command\CommandReplaceItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */