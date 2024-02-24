/*     */ package net.minecraft.client.resources;
/*     */ 
/*     */ import com.google.gson.JsonParseException;
/*     */ import java.io.IOException;
/*     */ import net.minecraft.client.gui.GuiScreenResourcePacks;
/*     */ import net.minecraft.client.renderer.texture.DynamicTexture;
/*     */ import net.minecraft.client.renderer.texture.TextureUtil;
/*     */ import net.minecraft.client.resources.data.PackMetadataSection;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class ResourcePackListEntryDefault
/*     */   extends ResourcePackListEntry
/*     */ {
/*  17 */   private static final Logger logger = LogManager.getLogger();
/*     */   
/*     */   private final IResourcePack field_148320_d;
/*     */   private final ResourceLocation resourcePackIcon;
/*     */   
/*     */   public ResourcePackListEntryDefault(GuiScreenResourcePacks resourcePacksGUIIn) {
/*  23 */     super(resourcePacksGUIIn);
/*  24 */     this.field_148320_d = (this.mc.getResourcePackRepository()).rprDefaultResourcePack;
/*  25 */     DynamicTexture dynamictexture = null;
/*     */ 
/*     */     
/*     */     try {
/*  29 */       if (this.field_148320_d != null) {
/*  30 */         dynamictexture = new DynamicTexture(this.field_148320_d.getPackImage());
/*     */       }
/*     */     }
/*  33 */     catch (IOException var4) {
/*     */       
/*  35 */       dynamictexture = TextureUtil.missingTexture;
/*     */     } 
/*  37 */     this.resourcePackIcon = this.mc.getTextureManager().getDynamicTextureLocation("texturepackicon", dynamictexture);
/*     */   }
/*     */ 
/*     */   
/*     */   protected int func_183019_a() {
/*  42 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected String func_148311_a() {
/*     */     try {
/*  49 */       PackMetadataSection packmetadatasection = this.field_148320_d.<PackMetadataSection>getPackMetadata((this.mc.getResourcePackRepository()).rprMetadataSerializer, "pack");
/*     */       
/*  51 */       if (packmetadatasection != null)
/*     */       {
/*  53 */         return packmetadatasection.getPackDescription().getFormattedText();
/*     */       }
/*     */     }
/*  56 */     catch (JsonParseException jsonparseexception) {
/*     */       
/*  58 */       logger.error("Couldn't load metadata info", (Throwable)jsonparseexception);
/*     */     }
/*  60 */     catch (IOException ioexception) {
/*     */       
/*  62 */       logger.error("Couldn't load metadata info", ioexception);
/*     */     } 
/*     */     
/*  65 */     return EnumChatFormatting.RED + "Missing pack.mcmeta :(";
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean func_148309_e() {
/*  70 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean func_148308_f() {
/*  75 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean func_148314_g() {
/*  80 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean func_148307_h() {
/*  85 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected String func_148312_b() {
/*  90 */     return "Default";
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_148313_c() {
/*  95 */     this.mc.getTextureManager().bindTexture(this.resourcePackIcon);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean func_148310_d() {
/* 100 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\resources\ResourcePackListEntryDefault.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */