/*     */ package net.minecraft.tileentity;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockFlower;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.EnumDyeColor;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TileEntityBanner
/*     */   extends TileEntity
/*     */ {
/*     */   private int baseColor;
/*     */   private NBTTagList patterns;
/*     */   private boolean field_175119_g;
/*     */   private List<EnumBannerPattern> patternList;
/*     */   private List<EnumDyeColor> colorList;
/*     */   private String patternResourceLocation;
/*     */   
/*     */   public void setItemValues(ItemStack stack) {
/*  33 */     this.patterns = null;
/*     */     
/*  35 */     if (stack.hasTagCompound() && stack.getTagCompound().hasKey("BlockEntityTag", 10)) {
/*     */       
/*  37 */       NBTTagCompound nbttagcompound = stack.getTagCompound().getCompoundTag("BlockEntityTag");
/*     */       
/*  39 */       if (nbttagcompound.hasKey("Patterns"))
/*     */       {
/*  41 */         this.patterns = (NBTTagList)nbttagcompound.getTagList("Patterns", 10).copy();
/*     */       }
/*     */       
/*  44 */       if (nbttagcompound.hasKey("Base", 99))
/*     */       {
/*  46 */         this.baseColor = nbttagcompound.getInteger("Base");
/*     */       }
/*     */       else
/*     */       {
/*  50 */         this.baseColor = stack.getMetadata() & 0xF;
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/*  55 */       this.baseColor = stack.getMetadata() & 0xF;
/*     */     } 
/*     */     
/*  58 */     this.patternList = null;
/*  59 */     this.colorList = null;
/*  60 */     this.patternResourceLocation = "";
/*  61 */     this.field_175119_g = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeToNBT(NBTTagCompound compound) {
/*  66 */     super.writeToNBT(compound);
/*  67 */     setBaseColorAndPatterns(compound, this.baseColor, this.patterns);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void setBaseColorAndPatterns(NBTTagCompound compound, int baseColorIn, NBTTagList patternsIn) {
/*  72 */     compound.setInteger("Base", baseColorIn);
/*     */     
/*  74 */     if (patternsIn != null)
/*     */     {
/*  76 */       compound.setTag("Patterns", (NBTBase)patternsIn);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void readFromNBT(NBTTagCompound compound) {
/*  82 */     super.readFromNBT(compound);
/*  83 */     this.baseColor = compound.getInteger("Base");
/*  84 */     this.patterns = compound.getTagList("Patterns", 10);
/*  85 */     this.patternList = null;
/*  86 */     this.colorList = null;
/*  87 */     this.patternResourceLocation = null;
/*  88 */     this.field_175119_g = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Packet getDescriptionPacket() {
/*  97 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/*  98 */     writeToNBT(nbttagcompound);
/*  99 */     return (Packet)new S35PacketUpdateTileEntity(this.pos, 6, nbttagcompound);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBaseColor() {
/* 104 */     return this.baseColor;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getBaseColor(ItemStack stack) {
/* 109 */     NBTTagCompound nbttagcompound = stack.getSubCompound("BlockEntityTag", false);
/* 110 */     return (nbttagcompound != null && nbttagcompound.hasKey("Base")) ? nbttagcompound.getInteger("Base") : stack.getMetadata();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getPatterns(ItemStack stack) {
/* 118 */     NBTTagCompound nbttagcompound = stack.getSubCompound("BlockEntityTag", false);
/* 119 */     return (nbttagcompound != null && nbttagcompound.hasKey("Patterns")) ? nbttagcompound.getTagList("Patterns", 10).tagCount() : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<EnumBannerPattern> getPatternList() {
/* 124 */     initializeBannerData();
/* 125 */     return this.patternList;
/*     */   }
/*     */ 
/*     */   
/*     */   public NBTTagList getPatterns() {
/* 130 */     return this.patterns;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<EnumDyeColor> getColorList() {
/* 135 */     initializeBannerData();
/* 136 */     return this.colorList;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPatternResourceLocation() {
/* 141 */     initializeBannerData();
/* 142 */     return this.patternResourceLocation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initializeBannerData() {
/* 151 */     if (this.patternList == null || this.colorList == null || this.patternResourceLocation == null)
/*     */     {
/* 153 */       if (!this.field_175119_g) {
/*     */         
/* 155 */         this.patternResourceLocation = "";
/*     */       }
/*     */       else {
/*     */         
/* 159 */         this.patternList = Lists.newArrayList();
/* 160 */         this.colorList = Lists.newArrayList();
/* 161 */         this.patternList.add(EnumBannerPattern.BASE);
/* 162 */         this.colorList.add(EnumDyeColor.byDyeDamage(this.baseColor));
/* 163 */         this.patternResourceLocation = "b" + this.baseColor;
/*     */         
/* 165 */         if (this.patterns != null)
/*     */         {
/* 167 */           for (int i = 0; i < this.patterns.tagCount(); i++) {
/*     */             
/* 169 */             NBTTagCompound nbttagcompound = this.patterns.getCompoundTagAt(i);
/* 170 */             EnumBannerPattern tileentitybanner$enumbannerpattern = EnumBannerPattern.getPatternByID(nbttagcompound.getString("Pattern"));
/*     */             
/* 172 */             if (tileentitybanner$enumbannerpattern != null) {
/*     */               
/* 174 */               this.patternList.add(tileentitybanner$enumbannerpattern);
/* 175 */               int j = nbttagcompound.getInteger("Color");
/* 176 */               this.colorList.add(EnumDyeColor.byDyeDamage(j));
/* 177 */               this.patternResourceLocation += tileentitybanner$enumbannerpattern.getPatternID() + j;
/*     */             } 
/*     */           } 
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void removeBannerData(ItemStack stack) {
/* 190 */     NBTTagCompound nbttagcompound = stack.getSubCompound("BlockEntityTag", false);
/*     */     
/* 192 */     if (nbttagcompound != null && nbttagcompound.hasKey("Patterns", 9)) {
/*     */       
/* 194 */       NBTTagList nbttaglist = nbttagcompound.getTagList("Patterns", 10);
/*     */       
/* 196 */       if (nbttaglist.tagCount() > 0) {
/*     */         
/* 198 */         nbttaglist.removeTag(nbttaglist.tagCount() - 1);
/*     */         
/* 200 */         if (nbttaglist.hasNoTags()) {
/*     */           
/* 202 */           stack.getTagCompound().removeTag("BlockEntityTag");
/*     */           
/* 204 */           if (stack.getTagCompound().hasNoTags())
/*     */           {
/* 206 */             stack.setTagCompound((NBTTagCompound)null);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public enum EnumBannerPattern
/*     */   {
/* 215 */     BASE("base", "b"),
/* 216 */     SQUARE_BOTTOM_LEFT("square_bottom_left", "bl", "   ", "   ", "#  "),
/* 217 */     SQUARE_BOTTOM_RIGHT("square_bottom_right", "br", "   ", "   ", "  #"),
/* 218 */     SQUARE_TOP_LEFT("square_top_left", "tl", "#  ", "   ", "   "),
/* 219 */     SQUARE_TOP_RIGHT("square_top_right", "tr", "  #", "   ", "   "),
/* 220 */     STRIPE_BOTTOM("stripe_bottom", "bs", "   ", "   ", "###"),
/* 221 */     STRIPE_TOP("stripe_top", "ts", "###", "   ", "   "),
/* 222 */     STRIPE_LEFT("stripe_left", "ls", "#  ", "#  ", "#  "),
/* 223 */     STRIPE_RIGHT("stripe_right", "rs", "  #", "  #", "  #"),
/* 224 */     STRIPE_CENTER("stripe_center", "cs", " # ", " # ", " # "),
/* 225 */     STRIPE_MIDDLE("stripe_middle", "ms", "   ", "###", "   "),
/* 226 */     STRIPE_DOWNRIGHT("stripe_downright", "drs", "#  ", " # ", "  #"),
/* 227 */     STRIPE_DOWNLEFT("stripe_downleft", "dls", "  #", " # ", "#  "),
/* 228 */     STRIPE_SMALL("small_stripes", "ss", "# #", "# #", "   "),
/* 229 */     CROSS("cross", "cr", "# #", " # ", "# #"),
/* 230 */     STRAIGHT_CROSS("straight_cross", "sc", " # ", "###", " # "),
/* 231 */     TRIANGLE_BOTTOM("triangle_bottom", "bt", "   ", " # ", "# #"),
/* 232 */     TRIANGLE_TOP("triangle_top", "tt", "# #", " # ", "   "),
/* 233 */     TRIANGLES_BOTTOM("triangles_bottom", "bts", "   ", "# #", " # "),
/* 234 */     TRIANGLES_TOP("triangles_top", "tts", " # ", "# #", "   "),
/* 235 */     DIAGONAL_LEFT("diagonal_left", "ld", "## ", "#  ", "   "),
/* 236 */     DIAGONAL_RIGHT("diagonal_up_right", "rd", "   ", "  #", " ##"),
/* 237 */     DIAGONAL_LEFT_MIRROR("diagonal_up_left", "lud", "   ", "#  ", "## "),
/* 238 */     DIAGONAL_RIGHT_MIRROR("diagonal_right", "rud", " ##", "  #", "   "),
/* 239 */     CIRCLE_MIDDLE("circle", "mc", "   ", " # ", "   "),
/* 240 */     RHOMBUS_MIDDLE("rhombus", "mr", " # ", "# #", " # "),
/* 241 */     HALF_VERTICAL("half_vertical", "vh", "## ", "## ", "## "),
/* 242 */     HALF_HORIZONTAL("half_horizontal", "hh", "###", "###", "   "),
/* 243 */     HALF_VERTICAL_MIRROR("half_vertical_right", "vhr", " ##", " ##", " ##"),
/* 244 */     HALF_HORIZONTAL_MIRROR("half_horizontal_bottom", "hhb", "   ", "###", "###"),
/* 245 */     BORDER("border", "bo", "###", "# #", "###"),
/* 246 */     CURLY_BORDER("curly_border", "cbo", (String)new ItemStack(Blocks.vine)),
/* 247 */     CREEPER("creeper", "cre", (String)new ItemStack(Items.skull, 1, 4)),
/* 248 */     GRADIENT("gradient", "gra", "# #", " # ", " # "),
/* 249 */     GRADIENT_UP("gradient_up", "gru", " # ", " # ", "# #"),
/* 250 */     BRICKS("bricks", "bri", (String)new ItemStack(Blocks.brick_block)),
/* 251 */     SKULL("skull", "sku", (String)new ItemStack(Items.skull, 1, 1)),
/* 252 */     FLOWER("flower", "flo", (String)new ItemStack((Block)Blocks.red_flower, 1, BlockFlower.EnumFlowerType.OXEYE_DAISY.getMeta())),
/* 253 */     MOJANG("mojang", "moj", (String)new ItemStack(Items.golden_apple, 1, 1));
/*     */     
/*     */     private final String patternName;
/*     */     
/*     */     private final String patternID;
/*     */     private final String[] craftingLayers;
/*     */     private ItemStack patternCraftingStack;
/*     */     
/*     */     EnumBannerPattern(String name, String id) {
/* 262 */       this.craftingLayers = new String[3];
/* 263 */       this.patternName = name;
/* 264 */       this.patternID = id;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     EnumBannerPattern(String name, String id, ItemStack craftingItem) {
/* 270 */       this.patternCraftingStack = craftingItem;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     EnumBannerPattern(String name, String id, String craftingTop, String craftingMid, String craftingBot) {
/* 276 */       this.craftingLayers[0] = craftingTop;
/* 277 */       this.craftingLayers[1] = craftingMid;
/* 278 */       this.craftingLayers[2] = craftingBot;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getPatternName() {
/* 283 */       return this.patternName;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getPatternID() {
/* 288 */       return this.patternID;
/*     */     }
/*     */ 
/*     */     
/*     */     public String[] getCraftingLayers() {
/* 293 */       return this.craftingLayers;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasValidCrafting() {
/* 298 */       return (this.patternCraftingStack != null || this.craftingLayers[0] != null);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasCraftingStack() {
/* 303 */       return (this.patternCraftingStack != null);
/*     */     }
/*     */ 
/*     */     
/*     */     public ItemStack getCraftingStack() {
/* 308 */       return this.patternCraftingStack;
/*     */     }
/*     */ 
/*     */     
/*     */     public static EnumBannerPattern getPatternByID(String id) {
/* 313 */       for (EnumBannerPattern tileentitybanner$enumbannerpattern : values()) {
/*     */         
/* 315 */         if (tileentitybanner$enumbannerpattern.patternID.equals(id))
/*     */         {
/* 317 */           return tileentitybanner$enumbannerpattern;
/*     */         }
/*     */       } 
/*     */       
/* 321 */       return null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\tileentity\TileEntityBanner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */