/*     */ package net.minecraft.creativetab;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockDoublePlant;
/*     */ import net.minecraft.enchantment.Enchantment;
/*     */ import net.minecraft.enchantment.EnchantmentData;
/*     */ import net.minecraft.enchantment.EnumEnchantmentType;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ 
/*     */ public abstract class CreativeTabs
/*     */ {
/*  16 */   public static final CreativeTabs[] creativeTabArray = new CreativeTabs[12];
/*  17 */   public static final CreativeTabs tabBlock = new CreativeTabs(0, "buildingBlocks")
/*     */     {
/*     */       public Item getTabIconItem()
/*     */       {
/*  21 */         return Item.getItemFromBlock(Blocks.brick_block);
/*     */       }
/*     */     };
/*  24 */   public static final CreativeTabs tabDecorations = new CreativeTabs(1, "decorations")
/*     */     {
/*     */       public Item getTabIconItem()
/*     */       {
/*  28 */         return Item.getItemFromBlock((Block)Blocks.double_plant);
/*     */       }
/*     */       
/*     */       public int getIconItemDamage() {
/*  32 */         return BlockDoublePlant.EnumPlantType.PAEONIA.getMeta();
/*     */       }
/*     */     };
/*  35 */   public static final CreativeTabs tabRedstone = new CreativeTabs(2, "redstone")
/*     */     {
/*     */       public Item getTabIconItem()
/*     */       {
/*  39 */         return Items.redstone;
/*     */       }
/*     */     };
/*  42 */   public static final CreativeTabs tabTransport = new CreativeTabs(3, "transportation")
/*     */     {
/*     */       public Item getTabIconItem()
/*     */       {
/*  46 */         return Item.getItemFromBlock(Blocks.golden_rail);
/*     */       }
/*     */     };
/*  49 */   public static final CreativeTabs tabMisc = (new CreativeTabs(4, "misc")
/*     */     {
/*     */       public Item getTabIconItem()
/*     */       {
/*  53 */         return Items.lava_bucket;
/*     */       }
/*  55 */     }).setRelevantEnchantmentTypes(new EnumEnchantmentType[] { EnumEnchantmentType.ALL });
/*  56 */   public static final CreativeTabs tabAllSearch = (new CreativeTabs(5, "search")
/*     */     {
/*     */       public Item getTabIconItem()
/*     */       {
/*  60 */         return Items.compass;
/*     */       }
/*  62 */     }).setBackgroundImageName("item_search.png");
/*  63 */   public static final CreativeTabs tabFood = new CreativeTabs(6, "food")
/*     */     {
/*     */       public Item getTabIconItem()
/*     */       {
/*  67 */         return Items.apple;
/*     */       }
/*     */     };
/*  70 */   public static final CreativeTabs tabTools = (new CreativeTabs(7, "tools")
/*     */     {
/*     */       public Item getTabIconItem()
/*     */       {
/*  74 */         return Items.iron_axe;
/*     */       }
/*  76 */     }).setRelevantEnchantmentTypes(new EnumEnchantmentType[] { EnumEnchantmentType.DIGGER, EnumEnchantmentType.FISHING_ROD, EnumEnchantmentType.BREAKABLE });
/*  77 */   public static final CreativeTabs tabCombat = (new CreativeTabs(8, "combat")
/*     */     {
/*     */       public Item getTabIconItem()
/*     */       {
/*  81 */         return Items.golden_sword;
/*     */       }
/*  83 */     }).setRelevantEnchantmentTypes(new EnumEnchantmentType[] { EnumEnchantmentType.ARMOR, EnumEnchantmentType.ARMOR_FEET, EnumEnchantmentType.ARMOR_HEAD, EnumEnchantmentType.ARMOR_LEGS, EnumEnchantmentType.ARMOR_TORSO, EnumEnchantmentType.BOW, EnumEnchantmentType.WEAPON });
/*  84 */   public static final CreativeTabs tabBrewing = new CreativeTabs(9, "brewing")
/*     */     {
/*     */       public Item getTabIconItem()
/*     */       {
/*  88 */         return (Item)Items.potionitem;
/*     */       }
/*     */     };
/*  91 */   public static final CreativeTabs tabMaterials = new CreativeTabs(10, "materials")
/*     */     {
/*     */       public Item getTabIconItem()
/*     */       {
/*  95 */         return Items.stick;
/*     */       }
/*     */     };
/*  98 */   public static final CreativeTabs tabInventory = (new CreativeTabs(11, "inventory")
/*     */     {
/*     */       public Item getTabIconItem()
/*     */       {
/* 102 */         return Item.getItemFromBlock((Block)Blocks.chest);
/*     */       }
/* 104 */     }).setBackgroundImageName("inventory.png").setNoScrollbar().setNoTitle();
/*     */ 
/*     */   
/*     */   private final int tabIndex;
/*     */   private final String tabLabel;
/* 109 */   private String theTexture = "items.png";
/*     */   
/*     */   private boolean hasScrollbar = true;
/*     */   
/*     */   private boolean drawTitle = true;
/*     */   
/*     */   private EnumEnchantmentType[] enchantmentTypes;
/*     */   private ItemStack iconItemStack;
/*     */   
/*     */   public CreativeTabs(int index, String label) {
/* 119 */     this.tabIndex = index;
/* 120 */     this.tabLabel = label;
/* 121 */     creativeTabArray[index] = this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getTabIndex() {
/* 126 */     return this.tabIndex;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getTabLabel() {
/* 131 */     return this.tabLabel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTranslatedTabLabel() {
/* 139 */     return "itemGroup." + this.tabLabel;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getIconItemStack() {
/* 144 */     if (this.iconItemStack == null)
/*     */     {
/* 146 */       this.iconItemStack = new ItemStack(getTabIconItem(), 1, getIconItemDamage());
/*     */     }
/*     */     
/* 149 */     return this.iconItemStack;
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract Item getTabIconItem();
/*     */   
/*     */   public int getIconItemDamage() {
/* 156 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getBackgroundImageName() {
/* 161 */     return this.theTexture;
/*     */   }
/*     */ 
/*     */   
/*     */   public CreativeTabs setBackgroundImageName(String texture) {
/* 166 */     this.theTexture = texture;
/* 167 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean drawInForegroundOfTab() {
/* 172 */     return this.drawTitle;
/*     */   }
/*     */ 
/*     */   
/*     */   public CreativeTabs setNoTitle() {
/* 177 */     this.drawTitle = false;
/* 178 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldHidePlayerInventory() {
/* 183 */     return this.hasScrollbar;
/*     */   }
/*     */ 
/*     */   
/*     */   public CreativeTabs setNoScrollbar() {
/* 188 */     this.hasScrollbar = false;
/* 189 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTabColumn() {
/* 197 */     return this.tabIndex % 6;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isTabInFirstRow() {
/* 205 */     return (this.tabIndex < 6);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumEnchantmentType[] getRelevantEnchantmentTypes() {
/* 213 */     return this.enchantmentTypes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CreativeTabs setRelevantEnchantmentTypes(EnumEnchantmentType... types) {
/* 221 */     this.enchantmentTypes = types;
/* 222 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasRelevantEnchantmentType(EnumEnchantmentType enchantmentType) {
/* 227 */     if (this.enchantmentTypes == null)
/*     */     {
/* 229 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 233 */     for (EnumEnchantmentType enumenchantmenttype : this.enchantmentTypes) {
/*     */       
/* 235 */       if (enumenchantmenttype == enchantmentType)
/*     */       {
/* 237 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 241 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void displayAllReleventItems(List<ItemStack> p_78018_1_) {
/* 250 */     for (Item item : Item.itemRegistry) {
/*     */       
/* 252 */       if (item != null && item.getCreativeTab() == this)
/*     */       {
/* 254 */         item.getSubItems(item, this, p_78018_1_);
/*     */       }
/*     */     } 
/*     */     
/* 258 */     if (getRelevantEnchantmentTypes() != null)
/*     */     {
/* 260 */       addEnchantmentBooksToList(p_78018_1_, getRelevantEnchantmentTypes());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addEnchantmentBooksToList(List<ItemStack> itemList, EnumEnchantmentType... enchantmentType) {
/* 269 */     for (Enchantment enchantment : Enchantment.enchantmentsBookList) {
/*     */       
/* 271 */       if (enchantment != null && enchantment.type != null) {
/*     */         
/* 273 */         boolean flag = false;
/*     */         
/* 275 */         for (int i = 0; i < enchantmentType.length && !flag; i++) {
/*     */           
/* 277 */           if (enchantment.type == enchantmentType[i]) {
/* 278 */             flag = true;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/* 283 */         if (flag)
/*     */         {
/* 285 */           itemList.add(Items.enchanted_book.getEnchantedItemStack(new EnchantmentData(enchantment, enchantment.getMaxLevel())));
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\creativetab\CreativeTabs.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */