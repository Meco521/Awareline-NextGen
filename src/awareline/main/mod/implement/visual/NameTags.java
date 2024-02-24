/*     */ package awareline.main.mod.implement.visual;
/*     */ 
/*     */ import awareline.main.Client;
/*     */ import awareline.main.event.EventHandler;
/*     */ import awareline.main.event.events.world.renderEvents.EventRender;
/*     */ import awareline.main.event.events.world.renderEvents.EventRender2D;
/*     */ import awareline.main.mod.Module;
/*     */ import awareline.main.mod.ModuleType;
/*     */ import awareline.main.mod.implement.combat.AntiBot;
/*     */ import awareline.main.mod.implement.misc.Teams;
/*     */ import awareline.main.mod.values.Option;
/*     */ import awareline.main.mod.values.Value;
/*     */ import awareline.main.ui.font.cfont.CFontRenderer;
/*     */ import awareline.main.ui.gui.clickgui.RenderUtil;
/*     */ import awareline.main.utility.math.RotationUtil;
/*     */ import awareline.main.utility.render.color.Colors;
/*     */ import java.awt.Color;
/*     */ import java.nio.FloatBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ import java.text.DecimalFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.enchantment.Enchantment;
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.item.EnumRarity;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import org.lwjgl.BufferUtils;
/*     */ import org.lwjgl.opengl.Display;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.lwjgl.util.glu.GLU;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NameTags
/*     */   extends Module
/*     */ {
/*  45 */   public final Option<Boolean> invis = new Option("Invisible", Boolean.valueOf(false));
/*  46 */   public final Option<Boolean> armor = new Option("Armor", Boolean.valueOf(false));
/*     */   public static NameTags getInstance;
/*     */   
/*     */   public NameTags() {
/*  50 */     super("NameTags", new String[] { "NameTag" }, ModuleType.Render);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  56 */     this.format = new DecimalFormat("0.0");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  63 */     this.wqy16 = Client.instance.FontLoaders.regular15;
/*     */     addSettings(new Value[] { (Value)this.invis, (Value)this.armor });
/*     */     getInstance = this;
/*     */   } public static final Map<EntityLivingBase, double[]> entityPositions = (Map)new HashMap<>(); public final DecimalFormat format; @EventHandler
/*  67 */   public void onRender2D(EventRender2D class112) { if (mc.thePlayer == null) {
/*     */       return;
/*     */     }
/*  70 */     GL11.glPopMatrix();
/*  71 */     for (Map.Entry<EntityLivingBase, double[]> entry : entityPositions.entrySet()) {
/*  72 */       EntityLivingBase entity = entry.getKey();
/*  73 */       if (((Boolean)this.invis.getValue()).booleanValue() || !entity.isInvisible()) {
/*  74 */         if (entity instanceof net.minecraft.entity.player.EntityPlayer) {
/*  75 */           String s, s2, s3; double[] array = entry.getValue();
/*  76 */           if (array[3] < 0.0D || array[3] >= 1.0D) {
/*     */             continue;
/*     */           }
/*     */           
/*  80 */           if (AntiBot.isBot(entity)) {
/*  81 */             s = "[Bot]";
/*     */           } else {
/*  83 */             s = "  ";
/*     */           } 
/*     */           
/*  86 */           if (Client.instance.friendManager.isFriend(entity.getName())) {
/*  87 */             s2 = "[Friend]";
/*     */           } else {
/*  89 */             s2 = "  ";
/*     */           } 
/*     */           
/*  92 */           if (Teams.getInstance.isOnSameTeam((Entity)entity)) {
/*  93 */             s3 = "[Team]";
/*     */           } else {
/*  95 */             s3 = "  ";
/*     */           } 
/*  97 */           String string = "Health: " + this.format.format(entity.getHealth());
/*  98 */           String string2 = s2 + s3 + s + entity.getDisplayName().getUnformattedText();
/*  99 */           float n = this.wqy16.getStringWidth(string2);
/* 100 */           float n2 = Client.instance.FontLoaders.regular13.getStringWidth(string);
/* 101 */           float n3 = Math.max(n, n2);
/* 102 */           float n4 = n3 - 8.0F;
/* 103 */           RenderUtil.drawGradientSidewaysV((-n4 / 2.0F), -22.0D, (n4 / 2.0F), -8.0D, Colors.getColor(0, 180), Colors.getColor(0, 100));
/*     */           
/* 105 */           Client.instance.FontLoaders.regular15.drawString(string2, -n4 / 2.0F - 10.5F, -18.0F, Colors.WHITE.c);
/*     */           
/* 107 */           float n11 = (float)Math.ceil((entity.getHealth() + entity.getAbsorptionAmount())) / (entity.getMaxHealth() + entity.getAbsorptionAmount());
/* 108 */           int n12 = Colors.RED.c;
/* 109 */           String formattedText = entity.getDisplayName().getFormattedText();
/* 110 */           int i = 0;
/* 111 */           while (i < formattedText.length()) {
/* 112 */             if (formattedText.charAt(i) == '搂' && i + 1 < formattedText.length()) {
/* 113 */               int index = "0123456789abcdefklmnorg".indexOf(Character.toLowerCase(formattedText.charAt(i + 1)));
/* 114 */               if (index < 16) {
/*     */                 try {
/* 116 */                   Color color = new Color(mc.fontRendererObj.colorCode[index]);
/* 117 */                   n12 = getColor(color.getRed(), color.getGreen(), color.getBlue(), 255);
/* 118 */                 } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {}
/*     */               }
/*     */             } 
/*     */             
/* 122 */             i++;
/*     */           } 
/* 124 */           RenderUtil.drawGradientSideways((-n4 / 2.0F), -10.0D, (n4 / 2.0F - n4 / 2.0F * (1.0F - n11) * 2.0F), -8.0D, (new Color((new Color(n12)).getRed() / 255.0F * 0.8F, (new Color(n12)).getGreen() / 255.0F * 0.5F, (new Color(n12)).getBlue() / 255.0F)).brighter().getRGB(), n12);
/* 125 */           RenderUtil.drawRect(0.0F, 0.0F, 0.0F, 0.0F, 0);
/*     */           
/* 127 */           if (((Boolean)this.armor.getValue()).booleanValue()) {
/* 128 */             ArrayList<ItemStack> list = new ArrayList<>();
/* 129 */             int j = 0;
/* 130 */             while (j < 5) {
/* 131 */               ItemStack equipmentInSlot = entity.getEquipmentInSlot(j);
/* 132 */               if (equipmentInSlot != null) {
/* 133 */                 list.add(equipmentInSlot);
/*     */               }
/* 135 */               j++;
/*     */             } 
/* 137 */             int p_renderItemOverlays_3_ = -(list.size() * 9);
/* 138 */             for (ItemStack p_getEnchantmentLevel_1_ : list) {
/* 139 */               RenderHelper.enableGUIStandardItemLighting();
/* 140 */               (mc.getRenderItem()).zLevel = -150.0F;
/* 141 */               fixGlintShit();
/* 142 */               mc.getRenderItem().renderItemIntoGUI(p_getEnchantmentLevel_1_, p_renderItemOverlays_3_ + 6, -42);
/* 143 */               mc.getRenderItem().renderItemOverlays(mc.fontRendererObj, p_getEnchantmentLevel_1_, p_renderItemOverlays_3_, -42);
/* 144 */               (mc.getRenderItem()).zLevel = 0.0F;
/* 145 */               p_renderItemOverlays_3_ += 3;
/* 146 */               RenderHelper.disableStandardItemLighting();
/* 147 */               if (p_getEnchantmentLevel_1_ != null) {
/* 148 */                 int n13 = 21;
/* 149 */                 int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, p_getEnchantmentLevel_1_);
/* 150 */                 int enchantmentLevel2 = EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, p_getEnchantmentLevel_1_);
/* 151 */                 int enchantmentLevel3 = EnchantmentHelper.getEnchantmentLevel(Enchantment.knockback.effectId, p_getEnchantmentLevel_1_);
/* 152 */                 if (enchantmentLevel > 0) {
/* 153 */                   drawEnchantTag("Sh" + getColor(enchantmentLevel) + enchantmentLevel, p_renderItemOverlays_3_, n13);
/* 154 */                   n13 += 6;
/*     */                 } 
/* 156 */                 if (enchantmentLevel2 > 0) {
/* 157 */                   drawEnchantTag("Fir" + getColor(enchantmentLevel2) + enchantmentLevel2, p_renderItemOverlays_3_, n13);
/* 158 */                   n13 += 6;
/*     */                 } 
/* 160 */                 if (enchantmentLevel3 > 0) {
/* 161 */                   drawEnchantTag("Kb" + getColor(enchantmentLevel3) + enchantmentLevel3, p_renderItemOverlays_3_, n13);
/* 162 */                 } else if (p_getEnchantmentLevel_1_.getItem() instanceof net.minecraft.item.ItemArmor) {
/* 163 */                   int enchantmentLevel4 = EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, p_getEnchantmentLevel_1_);
/* 164 */                   int enchantmentLevel5 = EnchantmentHelper.getEnchantmentLevel(Enchantment.thorns.effectId, p_getEnchantmentLevel_1_);
/* 165 */                   int enchantmentLevel6 = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, p_getEnchantmentLevel_1_);
/* 166 */                   if (enchantmentLevel4 > 0) {
/* 167 */                     drawEnchantTag("P" + getColor(enchantmentLevel4) + enchantmentLevel4, p_renderItemOverlays_3_, n13);
/* 168 */                     n13 += 6;
/*     */                   } 
/* 170 */                   if (enchantmentLevel5 > 0) {
/* 171 */                     drawEnchantTag("Th" + getColor(enchantmentLevel5) + enchantmentLevel5, p_renderItemOverlays_3_, n13);
/* 172 */                     n13 += 6;
/*     */                   } 
/* 174 */                   if (enchantmentLevel6 > 0) {
/* 175 */                     drawEnchantTag("Unb" + getColor(enchantmentLevel6) + enchantmentLevel6, p_renderItemOverlays_3_, n13);
/*     */                   }
/* 177 */                 } else if (p_getEnchantmentLevel_1_.getItem() instanceof net.minecraft.item.ItemBow) {
/* 178 */                   int enchantmentLevel7 = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, p_getEnchantmentLevel_1_);
/* 179 */                   int enchantmentLevel8 = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, p_getEnchantmentLevel_1_);
/* 180 */                   int enchantmentLevel9 = EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, p_getEnchantmentLevel_1_);
/* 181 */                   if (enchantmentLevel7 > 0) {
/* 182 */                     drawEnchantTag("Pow" + getColor(enchantmentLevel7) + enchantmentLevel7, p_renderItemOverlays_3_, n13);
/* 183 */                     n13 += 6;
/*     */                   } 
/* 185 */                   if (enchantmentLevel8 > 0) {
/* 186 */                     drawEnchantTag("Pun" + getColor(enchantmentLevel8) + enchantmentLevel8, p_renderItemOverlays_3_, n13);
/* 187 */                     n13 += 6;
/*     */                   } 
/* 189 */                   if (enchantmentLevel9 > 0) {
/* 190 */                     drawEnchantTag("Fir" + getColor(enchantmentLevel9) + enchantmentLevel9, p_renderItemOverlays_3_, n13);
/*     */                   }
/* 192 */                 } else if (p_getEnchantmentLevel_1_.getRarity() == EnumRarity.EPIC) {
/* 193 */                   drawEnchantTag("搂6搂lGod", p_renderItemOverlays_3_ - 2, n13);
/*     */                 } 
/* 195 */                 float n14 = (float)(p_renderItemOverlays_3_ * 1.05D) - 2.0F;
/*     */                 
/* 197 */                 if (p_getEnchantmentLevel_1_.getMaxDamage() - p_getEnchantmentLevel_1_.getItemDamage() > 0) {
/* 198 */                   GlStateManager.pushMatrix();
/* 199 */                   GlStateManager.disableDepth();
/* 200 */                   Client.instance.FontLoaders.regular14.drawString(String.valueOf(p_getEnchantmentLevel_1_.getMaxDamage() - p_getEnchantmentLevel_1_.getItemDamage()), n14 + 6.0F, -32.0F, Colors.WHITE.c);
/* 201 */                   GlStateManager.enableDepth();
/* 202 */                   GlStateManager.popMatrix();
/*     */                 } 
/* 204 */                 p_renderItemOverlays_3_ += 12;
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/* 209 */         GlStateManager.popMatrix();
/*     */       } 
/*     */     } 
/* 212 */     GlStateManager.pushMatrix(); } private final CFontRenderer wqy16; @EventHandler
/*     */   private void onRender(EventRender e) {
/*     */     updatePositions();
/*     */   } private String getColor(int n) {
/* 216 */     if (n != 1) {
/* 217 */       if (n == 2) {
/* 218 */         return "搂a";
/*     */       }
/* 220 */       if (n == 3) {
/* 221 */         return "搂3";
/*     */       }
/* 223 */       if (n == 4) {
/* 224 */         return "搂4";
/*     */       }
/* 226 */       if (n >= 5) {
/* 227 */         return "搂6";
/*     */       }
/*     */     } 
/* 230 */     return "搂f";
/*     */   }
/*     */   
/*     */   private void drawEnchantTag(String s, int n, int n2) {
/* 234 */     GlStateManager.pushMatrix();
/* 235 */     GlStateManager.disableDepth();
/* 236 */     n2 -= 6;
/* 237 */     Client.instance.FontLoaders.regular10.drawStringWithColor(s, (n + 9), (-30 - n2), Colors.getColor(255), 255);
/* 238 */     GlStateManager.enableDepth();
/* 239 */     GlStateManager.popMatrix();
/*     */   }
/*     */   
/*     */   public int getColor(int p_clamp_int_0_, int p_clamp_int_0_2, int p_clamp_int_0_3, int p_clamp_int_0_4) {
/* 243 */     return MathHelper.clamp_int(p_clamp_int_0_4, 0, 255) << 24 | MathHelper.clamp_int(p_clamp_int_0_, 0, 255) << 16 | MathHelper.clamp_int(p_clamp_int_0_2, 0, 255) << 8 | MathHelper.clamp_int(p_clamp_int_0_3, 0, 255);
/*     */   }
/*     */   
/*     */   private void scale() {
/* 247 */     float n = 1.05F;
/* 248 */     GlStateManager.scale(1.05F, 1.05F, 1.05F);
/*     */   }
/*     */   
/*     */   private void fixGlintShit() {
/* 252 */     GlStateManager.disableLighting();
/* 253 */     GlStateManager.disableDepth();
/* 254 */     GlStateManager.disableBlend();
/* 255 */     GlStateManager.enableLighting();
/* 256 */     GlStateManager.enableDepth();
/* 257 */     GlStateManager.disableLighting();
/* 258 */     GlStateManager.disableDepth();
/* 259 */     GlStateManager.disableTexture2D();
/* 260 */     GlStateManager.disableAlpha();
/* 261 */     GlStateManager.disableBlend();
/* 262 */     GlStateManager.enableBlend();
/* 263 */     GlStateManager.enableAlpha();
/* 264 */     GlStateManager.enableTexture2D();
/* 265 */     GlStateManager.enableLighting();
/* 266 */     GlStateManager.enableDepth();
/*     */   }
/*     */   
/*     */   private void updatePositions() {
/* 270 */     entityPositions.clear();
/* 271 */     float pTicks = mc.timer.renderPartialTicks;
/* 272 */     for (Entity o : mc.theWorld.loadedEntityList) {
/* 273 */       if (o != mc.thePlayer && o instanceof net.minecraft.entity.player.EntityPlayer && (
/* 274 */         !o.isInvisible() || !((Boolean)this.invis.getValue()).booleanValue())) {
/* 275 */         double x = o.lastTickPosX + (o.posX - o.lastTickPosX) * pTicks - (mc.getRenderManager()).viewerPosX;
/* 276 */         double y = o.lastTickPosY + (o.posY - o.lastTickPosY) * pTicks - (mc.getRenderManager()).viewerPosY;
/* 277 */         double z = o.lastTickPosZ + (o.posZ - o.lastTickPosZ) * pTicks - (mc.getRenderManager()).viewerPosZ;
/* 278 */         y += o.height + 0.2D;
/* 279 */         if (((double[])Objects.requireNonNull((T)convertTo2D(x, y, z)))[2] >= 0.0D && ((double[])Objects.requireNonNull((T)convertTo2D(x, y, z)))[2] < 1.0D)
/* 280 */           entityPositions.put(o, new double[] {
/* 281 */                 ((double[])Objects.requireNonNull((T)convertTo2D(x, y, z)))[0], ((double[])Objects.requireNonNull((T)convertTo2D(x, y, z)))[1], 
/* 282 */                 Math.abs(convertTo2D(x, y + 1.0D, z, o)[1] - convertTo2D(x, y, z, o)[1]), (
/* 283 */                 (double[])Objects.requireNonNull((T)convertTo2D(x, y, z)))[2]
/*     */               }); 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private double[] convertTo2D(double x, double y, double z, Entity ent) {
/* 290 */     float pTicks = mc.timer.renderPartialTicks;
/* 291 */     float prevYaw = mc.thePlayer.rotationYaw;
/* 292 */     float prevPrevYaw = mc.thePlayer.prevRotationYaw;
/* 293 */     float[] rotations = RotationUtil.getRotationFromPosition(ent.lastTickPosX + (ent.posX - ent.lastTickPosX) * pTicks, ent.lastTickPosZ + (ent.posZ - ent.lastTickPosZ) * pTicks, ent.lastTickPosY + (ent.posY - ent.lastTickPosY) * pTicks - 1.6D);
/*     */ 
/*     */ 
/*     */     
/* 297 */     (mc.getRenderViewEntity()).rotationYaw = (mc.getRenderViewEntity()).prevRotationYaw = rotations[0];
/* 298 */     mc.entityRenderer.setupCameraTransform(pTicks, 0);
/* 299 */     double[] convertedPoints = convertTo2D(x, y, z);
/* 300 */     (mc.getRenderViewEntity()).rotationYaw = prevYaw;
/* 301 */     (mc.getRenderViewEntity()).prevRotationYaw = prevPrevYaw;
/* 302 */     mc.entityRenderer.setupCameraTransform(pTicks, 0);
/* 303 */     return convertedPoints;
/*     */   }
/*     */   
/*     */   private double[] convertTo2D(double x, double y, double z) {
/* 307 */     FloatBuffer screenCoords = BufferUtils.createFloatBuffer(3);
/* 308 */     IntBuffer viewport = BufferUtils.createIntBuffer(16);
/* 309 */     FloatBuffer modelView = BufferUtils.createFloatBuffer(16);
/* 310 */     FloatBuffer projection = BufferUtils.createFloatBuffer(16);
/* 311 */     GL11.glGetFloat(2982, modelView);
/* 312 */     GL11.glGetFloat(2983, projection);
/* 313 */     GL11.glGetInteger(2978, viewport);
/* 314 */     boolean result = GLU.gluProject((float)x, (float)y, (float)z, modelView, projection, viewport, screenCoords);
/* 315 */     if (result) {
/* 316 */       return new double[] { screenCoords.get(0), (Display.getHeight() - screenCoords.get(1)), screenCoords.get(2) };
/*     */     }
/* 318 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\visual\NameTags.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */