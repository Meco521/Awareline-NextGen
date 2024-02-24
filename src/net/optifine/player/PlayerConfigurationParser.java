/*     */ package net.optifine.player;
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParseException;
/*     */ import com.google.gson.JsonParser;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import javax.imageio.ImageIO;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.optifine.http.HttpPipeline;
/*     */ import net.optifine.http.HttpUtils;
/*     */ import net.optifine.util.Json;
/*     */ 
/*     */ public class PlayerConfigurationParser {
/*  20 */   private String player = null;
/*     */   
/*     */   public static final String CONFIG_ITEMS = "items";
/*     */   public static final String ITEM_TYPE = "type";
/*     */   public static final String ITEM_ACTIVE = "active";
/*     */   
/*     */   public PlayerConfigurationParser(String player) {
/*  27 */     this.player = player;
/*     */   }
/*     */ 
/*     */   
/*     */   public PlayerConfiguration parsePlayerConfiguration(JsonElement je) {
/*  32 */     if (je == null)
/*     */     {
/*  34 */       throw new JsonParseException("JSON object is null, player: " + this.player);
/*     */     }
/*     */ 
/*     */     
/*  38 */     JsonObject jsonobject = (JsonObject)je;
/*  39 */     PlayerConfiguration playerconfiguration = new PlayerConfiguration();
/*  40 */     JsonArray jsonarray = (JsonArray)jsonobject.get("items");
/*     */     
/*  42 */     if (jsonarray != null)
/*     */     {
/*  44 */       for (int i = 0; i < jsonarray.size(); i++) {
/*     */         
/*  46 */         JsonObject jsonobject1 = (JsonObject)jsonarray.get(i);
/*  47 */         boolean flag = Json.getBoolean(jsonobject1, "active", true);
/*     */         
/*  49 */         if (flag) {
/*     */           
/*  51 */           String s = Json.getString(jsonobject1, "type");
/*     */           
/*  53 */           if (s == null) {
/*     */             
/*  55 */             Config.warn("Item type is null, player: " + this.player);
/*     */             
/*     */             continue;
/*     */           } 
/*  59 */           String s1 = Json.getString(jsonobject1, "model");
/*     */           
/*  61 */           if (s1 == null)
/*     */           {
/*  63 */             s1 = "items/" + s + "/model.cfg";
/*     */           }
/*     */           
/*  66 */           PlayerItemModel playeritemmodel = downloadModel(s1);
/*     */           
/*  68 */           if (playeritemmodel != null) {
/*     */             
/*  70 */             if (!playeritemmodel.isUsePlayerTexture()) {
/*     */               
/*  72 */               String s2 = Json.getString(jsonobject1, "texture");
/*     */               
/*  74 */               if (s2 == null)
/*     */               {
/*  76 */                 s2 = "items/" + s + "/users/" + this.player + ".png";
/*     */               }
/*     */               
/*  79 */               BufferedImage bufferedimage = downloadTextureImage(s2);
/*     */               
/*  81 */               if (bufferedimage == null) {
/*     */                 continue;
/*     */               }
/*     */ 
/*     */               
/*  86 */               playeritemmodel.setTextureImage(bufferedimage);
/*  87 */               ResourceLocation resourcelocation = new ResourceLocation("optifine.net", s2);
/*  88 */               playeritemmodel.setTextureLocation(resourcelocation);
/*     */             } 
/*     */             
/*  91 */             playerconfiguration.addPlayerItemModel(playeritemmodel);
/*     */           } 
/*     */         } 
/*     */         
/*     */         continue;
/*     */       } 
/*     */     }
/*  98 */     return playerconfiguration;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private BufferedImage downloadTextureImage(String texturePath) {
/* 104 */     String s = HttpUtils.getPlayerItemsUrl() + "/" + texturePath;
/*     */ 
/*     */     
/*     */     try {
/* 108 */       byte[] abyte = HttpPipeline.get(s, Minecraft.getMinecraft().getProxy());
/* 109 */       BufferedImage bufferedimage = ImageIO.read(new ByteArrayInputStream(abyte));
/* 110 */       return bufferedimage;
/*     */     }
/* 112 */     catch (IOException ioexception) {
/*     */       
/* 114 */       Config.warn("Error loading item texture " + texturePath + ": " + ioexception.getClass().getName() + ": " + ioexception.getMessage());
/* 115 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private PlayerItemModel downloadModel(String modelPath) {
/* 121 */     String s = HttpUtils.getPlayerItemsUrl() + "/" + modelPath;
/*     */ 
/*     */     
/*     */     try {
/* 125 */       byte[] abyte = HttpPipeline.get(s, Minecraft.getMinecraft().getProxy());
/* 126 */       String s1 = new String(abyte, StandardCharsets.US_ASCII);
/* 127 */       JsonParser jsonparser = new JsonParser();
/* 128 */       JsonObject jsonobject = (JsonObject)jsonparser.parse(s1);
/* 129 */       PlayerItemModel playeritemmodel = PlayerItemParser.parseItemModel(jsonobject);
/* 130 */       return playeritemmodel;
/*     */     }
/* 132 */     catch (Exception exception) {
/*     */       
/* 134 */       Config.warn("Error loading item model " + modelPath + ": " + exception.getClass().getName() + ": " + exception.getMessage());
/* 135 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\player\PlayerConfigurationParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */