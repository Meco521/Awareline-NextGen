/*     */ package net.optifine;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.List;
/*     */ import java.util.Properties;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.multiplayer.WorldClient;
/*     */ import net.minecraft.client.resources.IResourcePack;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.optifine.override.PlayerControllerOF;
/*     */ import net.optifine.util.PropertiesOrdered;
/*     */ import net.optifine.util.ResUtils;
/*     */ 
/*     */ public class CustomGuis {
/*  22 */   private static final Minecraft mc = Config.getMinecraft();
/*  23 */   private static final PlayerControllerOF playerControllerOF = null;
/*  24 */   private static CustomGuiProperties[][] guiProperties = (CustomGuiProperties[][])null;
/*  25 */   public static boolean isChristmas = isChristmas();
/*     */ 
/*     */   
/*     */   public static ResourceLocation getTextureLocation(ResourceLocation loc) {
/*  29 */     if (guiProperties == null)
/*     */     {
/*  31 */       return loc;
/*     */     }
/*     */ 
/*     */     
/*  35 */     GuiScreen guiscreen = mc.currentScreen;
/*     */     
/*  37 */     if (!(guiscreen instanceof net.minecraft.client.gui.inventory.GuiContainer))
/*     */     {
/*  39 */       return loc;
/*     */     }
/*  41 */     if (loc.getResourceDomain().equals("minecraft") && loc.getResourcePath().startsWith("textures/gui/")) {
/*     */       
/*  43 */       if (playerControllerOF == null)
/*     */       {
/*  45 */         return loc;
/*     */       }
/*     */ 
/*     */       
/*  49 */       WorldClient worldClient = mc.theWorld;
/*     */       
/*  51 */       if (worldClient == null)
/*     */       {
/*  53 */         return loc;
/*     */       }
/*  55 */       if (guiscreen instanceof net.minecraft.client.gui.inventory.GuiContainerCreative)
/*     */       {
/*  57 */         return getTexturePos(CustomGuiProperties.EnumContainer.CREATIVE, mc.thePlayer.getPosition(), (IBlockAccess)worldClient, loc, guiscreen);
/*     */       }
/*  59 */       if (guiscreen instanceof net.minecraft.client.gui.inventory.GuiInventory)
/*     */       {
/*  61 */         return getTexturePos(CustomGuiProperties.EnumContainer.INVENTORY, mc.thePlayer.getPosition(), (IBlockAccess)worldClient, loc, guiscreen);
/*     */       }
/*     */ 
/*     */       
/*  65 */       BlockPos blockpos = playerControllerOF.getLastClickBlockPos();
/*     */       
/*  67 */       if (blockpos != null) {
/*     */         
/*  69 */         if (guiscreen instanceof net.minecraft.client.gui.GuiRepair)
/*     */         {
/*  71 */           return getTexturePos(CustomGuiProperties.EnumContainer.ANVIL, blockpos, (IBlockAccess)worldClient, loc, guiscreen);
/*     */         }
/*     */         
/*  74 */         if (guiscreen instanceof net.minecraft.client.gui.inventory.GuiBeacon)
/*     */         {
/*  76 */           return getTexturePos(CustomGuiProperties.EnumContainer.BEACON, blockpos, (IBlockAccess)worldClient, loc, guiscreen);
/*     */         }
/*     */         
/*  79 */         if (guiscreen instanceof net.minecraft.client.gui.inventory.GuiBrewingStand)
/*     */         {
/*  81 */           return getTexturePos(CustomGuiProperties.EnumContainer.BREWING_STAND, blockpos, (IBlockAccess)worldClient, loc, guiscreen);
/*     */         }
/*     */         
/*  84 */         if (guiscreen instanceof net.minecraft.client.gui.inventory.GuiChest)
/*     */         {
/*  86 */           return getTexturePos(CustomGuiProperties.EnumContainer.CHEST, blockpos, (IBlockAccess)worldClient, loc, guiscreen);
/*     */         }
/*     */         
/*  89 */         if (guiscreen instanceof net.minecraft.client.gui.inventory.GuiCrafting)
/*     */         {
/*  91 */           return getTexturePos(CustomGuiProperties.EnumContainer.CRAFTING, blockpos, (IBlockAccess)worldClient, loc, guiscreen);
/*     */         }
/*     */         
/*  94 */         if (guiscreen instanceof net.minecraft.client.gui.inventory.GuiDispenser)
/*     */         {
/*  96 */           return getTexturePos(CustomGuiProperties.EnumContainer.DISPENSER, blockpos, (IBlockAccess)worldClient, loc, guiscreen);
/*     */         }
/*     */         
/*  99 */         if (guiscreen instanceof net.minecraft.client.gui.GuiEnchantment)
/*     */         {
/* 101 */           return getTexturePos(CustomGuiProperties.EnumContainer.ENCHANTMENT, blockpos, (IBlockAccess)worldClient, loc, guiscreen);
/*     */         }
/*     */         
/* 104 */         if (guiscreen instanceof net.minecraft.client.gui.inventory.GuiFurnace)
/*     */         {
/* 106 */           return getTexturePos(CustomGuiProperties.EnumContainer.FURNACE, blockpos, (IBlockAccess)worldClient, loc, guiscreen);
/*     */         }
/*     */         
/* 109 */         if (guiscreen instanceof net.minecraft.client.gui.GuiHopper)
/*     */         {
/* 111 */           return getTexturePos(CustomGuiProperties.EnumContainer.HOPPER, blockpos, (IBlockAccess)worldClient, loc, guiscreen);
/*     */         }
/*     */       } 
/*     */       
/* 115 */       Entity entity = playerControllerOF.getLastClickEntity();
/*     */       
/* 117 */       if (entity != null) {
/*     */         
/* 119 */         if (guiscreen instanceof net.minecraft.client.gui.inventory.GuiScreenHorseInventory)
/*     */         {
/* 121 */           return getTextureEntity(CustomGuiProperties.EnumContainer.HORSE, entity, (IBlockAccess)worldClient, loc);
/*     */         }
/*     */         
/* 124 */         if (guiscreen instanceof net.minecraft.client.gui.GuiMerchant)
/*     */         {
/* 126 */           return getTextureEntity(CustomGuiProperties.EnumContainer.VILLAGER, entity, (IBlockAccess)worldClient, loc);
/*     */         }
/*     */       } 
/*     */       
/* 130 */       return loc;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 136 */     return loc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static ResourceLocation getTexturePos(CustomGuiProperties.EnumContainer container, BlockPos pos, IBlockAccess blockAccess, ResourceLocation loc, GuiScreen screen) {
/* 143 */     CustomGuiProperties[] acustomguiproperties = guiProperties[container.ordinal()];
/*     */     
/* 145 */     if (acustomguiproperties == null)
/*     */     {
/* 147 */       return loc;
/*     */     }
/*     */ 
/*     */     
/* 151 */     for (int i = 0; i < acustomguiproperties.length; i++) {
/*     */       
/* 153 */       CustomGuiProperties customguiproperties = acustomguiproperties[i];
/*     */       
/* 155 */       if (customguiproperties.matchesPos(container, pos, blockAccess, screen))
/*     */       {
/* 157 */         return customguiproperties.getTextureLocation(loc);
/*     */       }
/*     */     } 
/*     */     
/* 161 */     return loc;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static ResourceLocation getTextureEntity(CustomGuiProperties.EnumContainer container, Entity entity, IBlockAccess blockAccess, ResourceLocation loc) {
/* 167 */     CustomGuiProperties[] acustomguiproperties = guiProperties[container.ordinal()];
/*     */     
/* 169 */     if (acustomguiproperties == null)
/*     */     {
/* 171 */       return loc;
/*     */     }
/*     */ 
/*     */     
/* 175 */     for (int i = 0; i < acustomguiproperties.length; i++) {
/*     */       
/* 177 */       CustomGuiProperties customguiproperties = acustomguiproperties[i];
/*     */       
/* 179 */       if (customguiproperties.matchesEntity(container, entity, blockAccess))
/*     */       {
/* 181 */         return customguiproperties.getTextureLocation(loc);
/*     */       }
/*     */     } 
/*     */     
/* 185 */     return loc;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void update() {
/* 191 */     guiProperties = (CustomGuiProperties[][])null;
/*     */     
/* 193 */     if (Config.isCustomGuis()) {
/*     */       
/* 195 */       List<List<CustomGuiProperties>> list = new ArrayList<>();
/* 196 */       IResourcePack[] airesourcepack = Config.getResourcePacks();
/*     */       
/* 198 */       for (int i = airesourcepack.length - 1; i >= 0; i--) {
/*     */         
/* 200 */         IResourcePack iresourcepack = airesourcepack[i];
/* 201 */         update(iresourcepack, list);
/*     */       } 
/*     */       
/* 204 */       guiProperties = propertyListToArray(list);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static CustomGuiProperties[][] propertyListToArray(List<List<CustomGuiProperties>> listProps) {
/* 210 */     if (listProps.isEmpty())
/*     */     {
/* 212 */       return (CustomGuiProperties[][])null;
/*     */     }
/*     */ 
/*     */     
/* 216 */     CustomGuiProperties[][] acustomguiproperties = new CustomGuiProperties[CustomGuiProperties.EnumContainer.VALUES.length][];
/*     */     
/* 218 */     for (int i = 0; i < acustomguiproperties.length; i++) {
/*     */       
/* 220 */       if (listProps.size() > i) {
/*     */         
/* 222 */         List<CustomGuiProperties> list = listProps.get(i);
/*     */         
/* 224 */         if (list != null) {
/*     */           
/* 226 */           CustomGuiProperties[] acustomguiproperties1 = list.<CustomGuiProperties>toArray(new CustomGuiProperties[0]);
/* 227 */           acustomguiproperties[i] = acustomguiproperties1;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 232 */     return acustomguiproperties;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void update(IResourcePack rp, List<List<CustomGuiProperties>> listProps) {
/* 238 */     String[] astring = ResUtils.collectFiles(rp, "optifine/gui/container/", ".properties", (String[])null);
/* 239 */     Arrays.sort((Object[])astring);
/*     */     
/* 241 */     for (int i = 0; i < astring.length; i++) {
/*     */       
/* 243 */       String s = astring[i];
/* 244 */       Config.dbg("CustomGuis: " + s);
/*     */ 
/*     */       
/*     */       try {
/* 248 */         ResourceLocation resourcelocation = new ResourceLocation(s);
/* 249 */         InputStream inputstream = rp.getInputStream(resourcelocation);
/*     */         
/* 251 */         if (inputstream == null)
/*     */         {
/* 253 */           Config.warn("CustomGuis file not found: " + s);
/*     */         }
/*     */         else
/*     */         {
/* 257 */           PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
/* 258 */           propertiesOrdered.load(inputstream);
/* 259 */           inputstream.close();
/* 260 */           CustomGuiProperties customguiproperties = new CustomGuiProperties((Properties)propertiesOrdered, s);
/*     */           
/* 262 */           if (customguiproperties.isValid(s))
/*     */           {
/* 264 */             addToList(customguiproperties, listProps);
/*     */           }
/*     */         }
/*     */       
/* 268 */       } catch (FileNotFoundException var9) {
/*     */         
/* 270 */         Config.warn("CustomGuis file not found: " + s);
/*     */       }
/* 272 */       catch (Exception exception) {
/*     */         
/* 274 */         exception.printStackTrace();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void addToList(CustomGuiProperties cgp, List<List<CustomGuiProperties>> listProps) {
/* 281 */     if (cgp.getContainer() == null) {
/*     */       
/* 283 */       warn("Invalid container: " + cgp.getContainer());
/*     */     }
/*     */     else {
/*     */       
/* 287 */       int i = cgp.getContainer().ordinal();
/*     */       
/* 289 */       while (listProps.size() <= i)
/*     */       {
/* 291 */         listProps.add(null);
/*     */       }
/*     */       
/* 294 */       List<CustomGuiProperties> list = listProps.get(i);
/*     */       
/* 296 */       if (list == null) {
/*     */         
/* 298 */         list = new ArrayList<>();
/* 299 */         listProps.set(i, list);
/*     */       } 
/*     */       
/* 302 */       list.add(cgp);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static PlayerControllerOF getPlayerControllerOF() {
/* 308 */     return playerControllerOF;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void setPlayerControllerOF(PlayerControllerOF playerControllerOF) {
/* 313 */     playerControllerOF = playerControllerOF;
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isChristmas() {
/* 318 */     Calendar calendar = Calendar.getInstance();
/* 319 */     return (calendar.get(2) + 1 == 12 && calendar.get(5) >= 24 && calendar.get(5) <= 26);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void warn(String str) {
/* 324 */     Config.warn("[CustomGuis] " + str);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\CustomGuis.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */