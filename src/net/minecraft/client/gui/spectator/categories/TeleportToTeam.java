/*     */ package net.minecraft.client.gui.spectator.categories;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.AbstractClientPlayer;
/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.client.gui.Gui;
/*     */ import net.minecraft.client.gui.GuiSpectator;
/*     */ import net.minecraft.client.gui.spectator.ISpectatorMenuObject;
/*     */ import net.minecraft.client.gui.spectator.ISpectatorMenuView;
/*     */ import net.minecraft.client.gui.spectator.SpectatorMenu;
/*     */ import net.minecraft.client.network.NetworkPlayerInfo;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.resources.DefaultPlayerSkin;
/*     */ import net.minecraft.scoreboard.ScorePlayerTeam;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ public class TeleportToTeam
/*     */   implements ISpectatorMenuView, ISpectatorMenuObject
/*     */ {
/*  26 */   private final List<ISpectatorMenuObject> field_178672_a = Lists.newArrayList();
/*     */ 
/*     */   
/*     */   public TeleportToTeam() {
/*  30 */     Minecraft minecraft = Minecraft.getMinecraft();
/*     */     
/*  32 */     for (ScorePlayerTeam scoreplayerteam : minecraft.theWorld.getScoreboard().getTeams())
/*     */     {
/*  34 */       this.field_178672_a.add(new TeamSelectionObject(scoreplayerteam));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public List<ISpectatorMenuObject> func_178669_a() {
/*  40 */     return this.field_178672_a;
/*     */   }
/*     */ 
/*     */   
/*     */   public IChatComponent func_178670_b() {
/*  45 */     return (IChatComponent)new ChatComponentText("Select a team to teleport to");
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_178661_a(SpectatorMenu menu) {
/*  50 */     menu.func_178647_a(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public IChatComponent getSpectatorName() {
/*  55 */     return (IChatComponent)new ChatComponentText("Teleport to team member");
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_178663_a(float p_178663_1_, int alpha) {
/*  60 */     Minecraft.getMinecraft().getTextureManager().bindTexture(GuiSpectator.field_175269_a);
/*  61 */     Gui.drawModalRectWithCustomSizedTexture(0, 0, 16.0F, 0.0F, 16, 16, 256.0F, 256.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_178662_A_() {
/*  66 */     for (ISpectatorMenuObject ispectatormenuobject : this.field_178672_a) {
/*     */       
/*  68 */       if (ispectatormenuobject.func_178662_A_())
/*     */       {
/*  70 */         return true;
/*     */       }
/*     */     } 
/*     */     
/*  74 */     return false;
/*     */   }
/*     */   
/*     */   static class TeamSelectionObject
/*     */     implements ISpectatorMenuObject
/*     */   {
/*     */     private final ScorePlayerTeam field_178676_b;
/*     */     private final ResourceLocation field_178677_c;
/*     */     private final List<NetworkPlayerInfo> field_178675_d;
/*     */     
/*     */     public TeamSelectionObject(ScorePlayerTeam p_i45492_2_) {
/*  85 */       this.field_178676_b = p_i45492_2_;
/*  86 */       this.field_178675_d = Lists.newArrayList();
/*     */       
/*  88 */       for (String s : p_i45492_2_.getMembershipCollection()) {
/*     */         
/*  90 */         NetworkPlayerInfo networkplayerinfo = Minecraft.getMinecraft().getNetHandler().getPlayerInfo(s);
/*     */         
/*  92 */         if (networkplayerinfo != null)
/*     */         {
/*  94 */           this.field_178675_d.add(networkplayerinfo);
/*     */         }
/*     */       } 
/*     */       
/*  98 */       if (!this.field_178675_d.isEmpty()) {
/*     */         
/* 100 */         String s1 = ((NetworkPlayerInfo)this.field_178675_d.get((new Random()).nextInt(this.field_178675_d.size()))).getGameProfile().getName();
/* 101 */         this.field_178677_c = AbstractClientPlayer.getLocationSkin(s1);
/* 102 */         AbstractClientPlayer.getDownloadImageSkin(this.field_178677_c, s1);
/*     */       }
/*     */       else {
/*     */         
/* 106 */         this.field_178677_c = DefaultPlayerSkin.getDefaultSkinLegacy();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_178661_a(SpectatorMenu menu) {
/* 112 */       menu.func_178647_a(new TeleportToPlayer(this.field_178675_d));
/*     */     }
/*     */ 
/*     */     
/*     */     public IChatComponent getSpectatorName() {
/* 117 */       return (IChatComponent)new ChatComponentText(this.field_178676_b.getTeamName());
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_178663_a(float p_178663_1_, int alpha) {
/* 122 */       int i = -1;
/* 123 */       String s = FontRenderer.getFormatFromString(this.field_178676_b.getColorPrefix());
/*     */       
/* 125 */       if (s.length() >= 2)
/*     */       {
/* 127 */         i = (Minecraft.getMinecraft()).fontRendererObj.getColorCode(s.charAt(1));
/*     */       }
/*     */       
/* 130 */       if (i >= 0) {
/*     */         
/* 132 */         float f = (i >> 16 & 0xFF) / 255.0F;
/* 133 */         float f1 = (i >> 8 & 0xFF) / 255.0F;
/* 134 */         float f2 = (i & 0xFF) / 255.0F;
/* 135 */         Gui.drawRect(1, 1, 15, 15, MathHelper.func_180183_b(f * p_178663_1_, f1 * p_178663_1_, f2 * p_178663_1_) | alpha << 24);
/*     */       } 
/*     */       
/* 138 */       Minecraft.getMinecraft().getTextureManager().bindTexture(this.field_178677_c);
/* 139 */       GlStateManager.color(p_178663_1_, p_178663_1_, p_178663_1_, alpha / 255.0F);
/* 140 */       Gui.drawScaledCustomSizeModalRect(2, 2, 8.0F, 8.0F, 8, 8, 12, 12, 64.0F, 64.0F);
/* 141 */       Gui.drawScaledCustomSizeModalRect(2, 2, 40.0F, 8.0F, 8, 8, 12, 12, 64.0F, 64.0F);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_178662_A_() {
/* 146 */       return !this.field_178675_d.isEmpty();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\gui\spectator\categories\TeleportToTeam.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */