/*     */ package awareline.main.mod.implement.visual;
/*     */ 
/*     */ import awareline.main.event.EventHandler;
/*     */ import awareline.main.event.events.world.renderEvents.EventRender2D;
/*     */ import awareline.main.mod.Module;
/*     */ import awareline.main.mod.ModuleType;
/*     */ import awareline.main.mod.values.Option;
/*     */ import awareline.main.mod.values.Value;
/*     */ import awareline.main.ui.simplecore.SimpleRender;
/*     */ import awareline.main.utility.render.RenderUtils;
/*     */ import awareline.main.utility.render.gl.ScaleUtils;
/*     */ import java.awt.Color;
/*     */ import java.nio.FloatBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import javax.vecmath.Vector3d;
/*     */ import javax.vecmath.Vector4d;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.client.renderer.GLAllocation;
/*     */ import net.minecraft.enchantment.Enchantment;
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemArmor;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.item.ItemSword;
/*     */ import net.minecraft.item.ItemTool;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import org.lwjgl.opengl.Display;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.lwjgl.util.glu.GLU;
/*     */ 
/*     */ public class ItemESP
/*     */   extends Module {
/*  41 */   private final Option<Boolean> names = new Option("Names", Boolean.valueOf(true)); private final Option<Boolean> neededOnly = new Option("NeededOnly", 
/*  42 */       Boolean.valueOf(false)); private final IntBuffer viewport; private final FloatBuffer modelView;
/*  43 */   private final Option<Boolean> itemColor = new Option("AutoColor", Boolean.valueOf(true)); private final FloatBuffer projection; private final FloatBuffer vector;
/*     */   
/*     */   public ItemESP() {
/*  46 */     super("ItemESP", ModuleType.Render);
/*     */ 
/*     */ 
/*     */     
/*  50 */     this.viewport = GLAllocation.createDirectIntBuffer(16);
/*  51 */     this.modelView = GLAllocation.createDirectFloatBuffer(16);
/*  52 */     this.projection = GLAllocation.createDirectFloatBuffer(16);
/*  53 */     this.vector = GLAllocation.createDirectFloatBuffer(4);
/*     */     addSettings(new Value[] { (Value)this.names, (Value)this.neededOnly, (Value)this.itemColor });
/*     */   } @EventHandler(3)
/*     */   public void onRender(EventRender2D er) {
/*  57 */     for (Entity o : mc.theWorld.getLoadedEntityList()) {
/*  58 */       if (o instanceof EntityItem) {
/*  59 */         float f1 = MathHelper.sin((((EntityItem)o).getAge() + er.getPartialTicks()) / 10.0F + ((EntityItem)o).hoverStart) * 0.1F + 0.1F;
/*  60 */         double x = RenderUtils.interpolate(o.posX, o.lastTickPosX, er.getPartialTicks());
/*  61 */         double y = RenderUtils.interpolate(o.posY + f1, o.lastTickPosY + f1, er.getPartialTicks());
/*  62 */         double z = RenderUtils.interpolate(o.posZ, o.lastTickPosZ, er.getPartialTicks());
/*  63 */         double width = o.width / 1.4D;
/*  64 */         double height = o.height + 0.2D;
/*     */         
/*  66 */         AxisAlignedBB aabb = new AxisAlignedBB(x - width, y, z - width, x + width, y + height, z + width);
/*  67 */         List<Vector3d> vectors = Arrays.asList(new Vector3d[] { new Vector3d(aabb.minX, aabb.minY, aabb.minZ), new Vector3d(aabb.minX, aabb.maxY, aabb.minZ), new Vector3d(aabb.maxX, aabb.minY, aabb.minZ), new Vector3d(aabb.maxX, aabb.maxY, aabb.minZ), new Vector3d(aabb.minX, aabb.minY, aabb.maxZ), new Vector3d(aabb.minX, aabb.maxY, aabb.maxZ), new Vector3d(aabb.maxX, aabb.minY, aabb.maxZ), new Vector3d(aabb.maxX, aabb.maxY, aabb.maxZ) });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  73 */         mc.entityRenderer.setupCameraTransform(er.getPartialTicks(), 0);
/*     */         
/*  75 */         Vector4d position = null;
/*     */         
/*  77 */         for (Vector3d vector : vectors) {
/*  78 */           vector = project2D(er.getResolution(), vector.x - (mc.getRenderManager()).viewerPosX, vector.y - 
/*  79 */               (mc.getRenderManager()).viewerPosY, vector.z - 
/*  80 */               (mc.getRenderManager()).viewerPosZ);
/*     */           
/*  82 */           if (vector != null && vector.z >= 0.0D && vector.z < 1.0D) {
/*  83 */             if (position == null) {
/*  84 */               position = new Vector4d(vector.x, vector.y, vector.z, 0.0D);
/*     */             }
/*     */             
/*  87 */             position.x = Math.min(vector.x, position.x);
/*  88 */             position.y = Math.min(vector.y, position.y);
/*  89 */             position.z = Math.max(vector.x, position.z);
/*  90 */             position.w = Math.max(vector.y, position.w);
/*     */           } 
/*     */         } 
/*     */         
/*  94 */         mc.entityRenderer.setupOverlayRendering();
/*     */         
/*  96 */         if (position != null && (!((Boolean)this.neededOnly.get()).booleanValue() || isItemSpecial((EntityItem)o))) {
/*  97 */           double posX = position.x;
/*  98 */           double posY = position.y;
/*  99 */           double endPosX = position.z;
/* 100 */           double endPosY = position.w;
/*     */           
/* 102 */           RenderUtils.drawCornerBox(posX, posY, endPosX, endPosY, isItemSpecial((EntityItem)o) ? 4.0D : 3.0D, Color.BLACK);
/* 103 */           RenderUtils.drawCornerBox(posX, posY, endPosX, endPosY, isItemSpecial((EntityItem)o) ? 2.0D : 1.0D, getItemColor((EntityItem)o));
/*     */           
/* 105 */           if (((Boolean)this.names.get()).booleanValue()) {
/* 106 */             float amp = 1.0F;
/* 107 */             switch (mc.gameSettings.guiScale) {
/*     */               case 0:
/* 109 */                 amp = 0.5F;
/*     */                 break;
/*     */               case 1:
/* 112 */                 amp = 2.0F;
/*     */                 break;
/*     */               case 3:
/* 115 */                 amp = 0.6666667F;
/*     */                 break;
/*     */             } 
/* 118 */             double[] positions = ScaleUtils.getScaledMouseCoordinates(mc, posX, posY);
/* 119 */             double[] positionsEnd = ScaleUtils.getScaledMouseCoordinates(mc, endPosX, endPosY);
/* 120 */             double[] scaledPositions = { positions[0] * 2.0D, positions[1] * 2.0D, positionsEnd[0] * 2.0D, positionsEnd[1] * 2.0D };
/*     */             
/* 122 */             GL11.glPushMatrix();
/* 123 */             GL11.glScalef(0.5F * amp, 0.5F * amp, 0.5F * amp);
/* 124 */             double _width = Math.abs(scaledPositions[2] - scaledPositions[0]);
/* 125 */             float v = (mc.fontRendererObj.getHeight() << 1) - (mc.fontRendererObj.getHeight() / 2);
/*     */             
/* 127 */             mc.fontRendererObj.drawStringWithShadow(((EntityItem)o).getEntityItem().getDisplayName(), 
/*     */                 
/* 129 */                 (float)(scaledPositions[0] + _width / 2.0D - (mc.fontRendererObj.getStringWidth(((EntityItem)o).getEntityItem().getDisplayName()) / 2)), (float)positionsEnd[1] * 2.0F + v, 
/*     */                 
/* 131 */                 getItemColor((EntityItem)o).brighter().getRGB());
/* 132 */             GL11.glPopMatrix();
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isItemSpecial(EntityItem o) {
/* 148 */     boolean special = (o.getEntityItem().getItem() instanceof ItemArmor || (o.getEntityItem().getItem() == Items.skull && !o.getEntityItem().getDisplayName().equalsIgnoreCase("Zombie Head") && !o.getEntityItem().getDisplayName().equalsIgnoreCase("Creeper Head") && !o.getEntityItem().getDisplayName().equalsIgnoreCase("Skeleton Skull") && !o.getEntityItem().getDisplayName().equalsIgnoreCase("Wither Skeleton Skull") && !o.getEntityItem().getDisplayName().equalsIgnoreCase(EnumChatFormatting.GREEN + "Frog's Hat")) || o.getEntityItem().getItem() instanceof net.minecraft.item.ItemAppleGold);
/*     */ 
/*     */     
/* 151 */     if (o.getEntityItem().getItem() instanceof ItemArmor) {
/*     */       
/* 153 */       for (int type = 1; type < 5; type++) {
/* 154 */         String strType = "";
/*     */         
/* 156 */         switch (type) {
/*     */           case 1:
/* 158 */             strType = "helmet";
/*     */             break;
/*     */           case 2:
/* 161 */             strType = "chestplate";
/*     */             break;
/*     */           case 3:
/* 164 */             strType = "leggings";
/*     */             break;
/*     */           case 4:
/* 167 */             strType = "boots";
/*     */             break;
/*     */         } 
/*     */ 
/*     */         
/* 172 */         if (mc.thePlayer.getSlotFromPlayerContainer(4 + type).getHasStack()) {
/* 173 */           ItemStack is = mc.thePlayer.getSlotFromPlayerContainer(4 + type).getStack();
/* 174 */           if (is.getItem().getUnlocalizedName().contains(strType) && o.getEntityItem().getItem().getUnlocalizedName().contains(strType)) {
/* 175 */             return (getProtection(o.getEntityItem()) > getProtection(mc.thePlayer.getSlotFromPlayerContainer(4 + type).getStack()));
/*     */           }
/*     */         } 
/*     */       } 
/* 179 */       return !hasItem(o.getEntityItem());
/* 180 */     }  if (o.getEntityItem().getItem() instanceof ItemSword) {
/* 181 */       for (int i = 9; i < 45; i++) {
/* 182 */         if (mc.thePlayer.getSlotFromPlayerContainer(i).getHasStack() && 
/* 183 */           mc.thePlayer.getSlotFromPlayerContainer(i).getStack().getItem() instanceof ItemSword) {
/* 184 */           return (getDamage(o.getEntityItem()) > getDamage(mc.thePlayer.getSlotFromPlayerContainer(i).getStack()));
/*     */         }
/*     */       } 
/*     */       
/* 188 */       return !hasItem(o.getEntityItem());
/* 189 */     }  if (o.getEntityItem().getItem() instanceof net.minecraft.item.ItemPickaxe) {
/* 190 */       for (int i = 9; i < 45; i++) {
/* 191 */         if (mc.thePlayer.getSlotFromPlayerContainer(i).getHasStack() && 
/* 192 */           mc.thePlayer.getSlotFromPlayerContainer(i).getStack().getItem() instanceof net.minecraft.item.ItemPickaxe) {
/* 193 */           return (getToolEffect(o.getEntityItem()) > getToolEffect(mc.thePlayer.getSlotFromPlayerContainer(i).getStack()));
/*     */         }
/*     */       } 
/*     */       
/* 197 */       return !hasItem(o.getEntityItem());
/* 198 */     }  if (o.getEntityItem().getItem() instanceof net.minecraft.item.ItemSpade) {
/* 199 */       for (int i = 9; i < 45; i++) {
/* 200 */         if (mc.thePlayer.getSlotFromPlayerContainer(i).getHasStack() && 
/* 201 */           mc.thePlayer.getSlotFromPlayerContainer(i).getStack().getItem() instanceof net.minecraft.item.ItemSpade) {
/* 202 */           return (getToolEffect(o.getEntityItem()) > getToolEffect(mc.thePlayer.getSlotFromPlayerContainer(i).getStack()));
/*     */         }
/*     */       } 
/*     */       
/* 206 */       return !hasItem(o.getEntityItem());
/* 207 */     }  if (o.getEntityItem().getItem() instanceof net.minecraft.item.ItemAxe) {
/* 208 */       for (int i = 9; i < 45; i++) {
/* 209 */         if (mc.thePlayer.getSlotFromPlayerContainer(i).getHasStack() && 
/* 210 */           mc.thePlayer.getSlotFromPlayerContainer(i).getStack().getItem() instanceof net.minecraft.item.ItemAxe) {
/* 211 */           return (getToolEffect(o.getEntityItem()) > getToolEffect(mc.thePlayer.getSlotFromPlayerContainer(i).getStack()));
/*     */         }
/*     */       } 
/*     */       
/* 215 */       return !hasItem(o.getEntityItem());
/*     */     } 
/* 217 */     return special;
/*     */   }
/*     */   
/*     */   private float getProtection(ItemStack stack) {
/* 221 */     float protection = 0.0F;
/*     */     
/* 223 */     if (stack.getItem() instanceof ItemArmor) {
/* 224 */       ItemArmor armor = (ItemArmor)stack.getItem();
/*     */       
/* 226 */       protection = (float)(protection + armor.damageReduceAmount + ((100 - armor.damageReduceAmount) * EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, stack)) * 0.0075D);
/*     */       
/* 228 */       protection = (float)(protection + EnchantmentHelper.getEnchantmentLevel(Enchantment.blastProtection.effectId, stack) / 100.0D);
/* 229 */       protection = (float)(protection + EnchantmentHelper.getEnchantmentLevel(Enchantment.fireProtection.effectId, stack) / 100.0D);
/* 230 */       protection = (float)(protection + EnchantmentHelper.getEnchantmentLevel(Enchantment.thorns.effectId, stack) / 100.0D);
/* 231 */       protection = (float)(protection + EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack) / 50.0D);
/* 232 */       protection = (float)(protection + EnchantmentHelper.getEnchantmentLevel(Enchantment.projectileProtection.effectId, stack) / 100.0D);
/*     */     } 
/*     */     
/* 235 */     return protection;
/*     */   }
/*     */   
/*     */   private float getDamage(ItemStack stack) {
/* 239 */     float damage = 0.0F;
/* 240 */     Item item = stack.getItem();
/*     */     
/* 242 */     if (item instanceof ItemTool) {
/* 243 */       damage += ((ItemTool)item).getDamage();
/* 244 */     } else if (item instanceof ItemSword) {
/* 245 */       damage += ((ItemSword)item).getAttackDamage();
/*     */     } 
/*     */     
/* 248 */     damage += EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, stack) * 1.25F + EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, stack) * 0.01F;
/*     */     
/* 250 */     return damage;
/*     */   }
/*     */   
/*     */   private float getToolEffect(ItemStack stack) {
/* 254 */     Item item = stack.getItem();
/*     */     
/* 256 */     if (!(item instanceof ItemTool)) {
/* 257 */       return 0.0F;
/*     */     }
/*     */     
/* 260 */     String name = item.getUnlocalizedName();
/* 261 */     ItemTool tool = (ItemTool)item;
/*     */ 
/*     */     
/* 264 */     if (item instanceof net.minecraft.item.ItemPickaxe)
/* 265 */     { value = tool.getStrVsBlock(stack, Blocks.stone);
/* 266 */       if (name.toLowerCase().contains("gold")) value -= 5.0F;  }
/* 267 */     else if (item instanceof net.minecraft.item.ItemSpade)
/* 268 */     { value = tool.getStrVsBlock(stack, Blocks.dirt);
/* 269 */       if (name.toLowerCase().contains("gold")) value -= 5.0F;  }
/* 270 */     else if (item instanceof net.minecraft.item.ItemAxe)
/* 271 */     { value = tool.getStrVsBlock(stack, Blocks.log);
/* 272 */       if (name.toLowerCase().contains("gold")) value -= 5.0F;  }
/* 273 */     else { return 1.0F; }
/*     */     
/* 275 */     float value = (float)(value + EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, stack) * 0.0075D);
/* 276 */     value = (float)(value + EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack) / 100.0D);
/*     */     
/* 278 */     return value;
/*     */   }
/*     */   
/*     */   private boolean hasItem(ItemStack is) {
/*     */     int i;
/* 283 */     for (i = 0; i < 3; i++) {
/* 284 */       if (mc.thePlayer.inventory.armorInventory[i] != null && 
/* 285 */         mc.thePlayer.inventory.armorInventory[i].getItem() == is.getItem()) {
/* 286 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 290 */     for (i = 9; i < 45; i++) {
/* 291 */       if (mc.thePlayer.getSlotFromPlayerContainer(i).getHasStack()) {
/* 292 */         ItemStack is1 = mc.thePlayer.getSlotFromPlayerContainer(i).getStack();
/* 293 */         if (is.getItem() == is1.getItem()) {
/* 294 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/* 298 */     return false;
/*     */   }
/*     */   
/*     */   private Color getItemColor(EntityItem o) {
/* 302 */     if (((Boolean)this.itemColor.get()).booleanValue()) {
/* 303 */       String displayName = o.getEntityItem().getDisplayName();
/* 304 */       if (displayName.equalsIgnoreCase(EnumChatFormatting.GOLD + "Excalibur") || displayName.equalsIgnoreCase("aDragon Sword") || displayName
/* 305 */         .equalsIgnoreCase(EnumChatFormatting.GREEN + "Cornucopia") || displayName
/* 306 */         .equalsIgnoreCase(EnumChatFormatting.RED + "Bloodlust") || displayName.equalsIgnoreCase(EnumChatFormatting.RED + "Artemis' Bow") || displayName
/* 307 */         .equalsIgnoreCase(EnumChatFormatting.GREEN + "Miner's Blessing") || displayName.equalsIgnoreCase(EnumChatFormatting.GOLD + "Axe of Perun") || displayName
/* 308 */         .equalsIgnoreCase(EnumChatFormatting.GOLD + "Cornucopia")) {
/* 309 */         return new Color(SimpleRender.getArrayRainbow((int)(System.currentTimeMillis() / 100000L), 255));
/*     */       }
/*     */       
/* 312 */       if (!isItemSpecial(o)) {
/* 313 */         return new Color(255, 255, 255);
/*     */       }
/*     */       
/* 316 */       if (o.getEntityItem().getItem() instanceof ItemArmor)
/* 317 */         return new Color(75, 189, 193); 
/* 318 */       if (o.getEntityItem().getItem() instanceof net.minecraft.item.ItemAppleGold)
/* 319 */         return new Color(255, 199, 71); 
/* 320 */       if (o.getEntityItem().getItem() instanceof net.minecraft.item.ItemSkull && isItemSpecial(o))
/* 321 */         return new Color(255, 199, 71); 
/* 322 */       if (o.getEntityItem().getItem() instanceof ItemSword)
/* 323 */         return new Color(255, 117, 117); 
/* 324 */       if (o.getEntityItem().getItem() instanceof net.minecraft.item.ItemPickaxe)
/* 325 */         return new Color(130, 219, 82); 
/* 326 */       if (o.getEntityItem().getItem() instanceof net.minecraft.item.ItemSpade)
/* 327 */         return new Color(130, 219, 82); 
/* 328 */       if (o.getEntityItem().getItem() instanceof net.minecraft.item.ItemAxe) {
/* 329 */         return new Color(130, 219, 82);
/*     */       }
/* 331 */       return new Color(255, 255, 255);
/*     */     } 
/*     */     
/* 334 */     return new Color(((Double)HUD.r.get()).intValue(), ((Double)HUD.g.get()).intValue(), ((Double)HUD.b.get()).intValue());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Vector3d project2D(ScaledResolution scaledResolution, double x, double y, double z) {
/* 340 */     GL11.glGetFloat(2982, this.modelView);
/* 341 */     GL11.glGetFloat(2983, this.projection);
/* 342 */     GL11.glGetInteger(2978, this.viewport);
/*     */     
/* 344 */     if (GLU.gluProject((float)x, (float)y, (float)z, this.modelView, this.projection, this.viewport, this.vector))
/*     */     {
/* 346 */       return new Vector3d((this.vector.get(0) / scaledResolution.getScaleFactor()), ((
/* 347 */           Display.getHeight() - this.vector.get(1)) / scaledResolution.getScaleFactor()), this.vector.get(2));
/*     */     }
/*     */     
/* 350 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\visual\ItemESP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */