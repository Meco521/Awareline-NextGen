/*    */ package net.minecraft.client.resources;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import com.google.common.collect.Maps;
/*    */ import com.google.common.collect.Sets;
/*    */ import java.io.IOException;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.SortedSet;
/*    */ import net.minecraft.client.resources.data.IMetadataSerializer;
/*    */ import net.minecraft.client.resources.data.LanguageMetadataSection;
/*    */ import net.minecraft.util.StringTranslate;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ public class LanguageManager
/*    */   implements IResourceManagerReloadListener
/*    */ {
/* 19 */   private static final Logger logger = LogManager.getLogger();
/*    */   private final IMetadataSerializer theMetadataSerializer;
/*    */   private String currentLanguage;
/* 22 */   protected static final Locale currentLocale = new Locale();
/* 23 */   private final Map<String, Language> languageMap = Maps.newHashMap();
/*    */ 
/*    */   
/*    */   public LanguageManager(IMetadataSerializer theMetadataSerializerIn, String currentLanguageIn) {
/* 27 */     this.theMetadataSerializer = theMetadataSerializerIn;
/* 28 */     this.currentLanguage = currentLanguageIn;
/* 29 */     I18n.setLocale(currentLocale);
/*    */   }
/*    */ 
/*    */   
/*    */   public void parseLanguageMetadata(List<IResourcePack> resourcesPacks) {
/* 34 */     this.languageMap.clear();
/*    */     
/* 36 */     for (IResourcePack iresourcepack : resourcesPacks) {
/*    */ 
/*    */       
/*    */       try {
/* 40 */         LanguageMetadataSection languagemetadatasection = iresourcepack.<LanguageMetadataSection>getPackMetadata(this.theMetadataSerializer, "language");
/*    */         
/* 42 */         if (languagemetadatasection != null)
/*    */         {
/* 44 */           for (Language language : languagemetadatasection.getLanguages())
/*    */           {
/* 46 */             if (!this.languageMap.containsKey(language.getLanguageCode()))
/*    */             {
/* 48 */               this.languageMap.put(language.getLanguageCode(), language);
/*    */             }
/*    */           }
/*    */         
/*    */         }
/* 53 */       } catch (RuntimeException runtimeexception) {
/*    */         
/* 55 */         logger.warn("Unable to parse metadata section of resourcepack: " + iresourcepack.getPackName(), runtimeexception);
/*    */       }
/* 57 */       catch (IOException ioexception) {
/*    */         
/* 59 */         logger.warn("Unable to parse metadata section of resourcepack: " + iresourcepack.getPackName(), ioexception);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void onResourceManagerReload(IResourceManager resourceManager) {
/* 66 */     List<String> list = Lists.newArrayList((Object[])new String[] { "en_US" });
/*    */     
/* 68 */     if (!"en_US".equals(this.currentLanguage))
/*    */     {
/* 70 */       list.add(this.currentLanguage);
/*    */     }
/*    */     
/* 73 */     currentLocale.loadLocaleDataFiles(resourceManager, list);
/* 74 */     StringTranslate.replaceWith(currentLocale.properties);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isCurrentLocaleUnicode() {
/* 79 */     return currentLocale.isUnicode();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isCurrentLanguageBidirectional() {
/* 84 */     return (getCurrentLanguage() != null && getCurrentLanguage().isBidirectional());
/*    */   }
/*    */ 
/*    */   
/*    */   public void setCurrentLanguage(Language currentLanguageIn) {
/* 89 */     this.currentLanguage = currentLanguageIn.getLanguageCode();
/*    */   }
/*    */ 
/*    */   
/*    */   public Language getCurrentLanguage() {
/* 94 */     return this.languageMap.containsKey(this.currentLanguage) ? this.languageMap.get(this.currentLanguage) : this.languageMap.get("en_US");
/*    */   }
/*    */ 
/*    */   
/*    */   public SortedSet<Language> getLanguages() {
/* 99 */     return Sets.newTreeSet(this.languageMap.values());
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\resources\LanguageManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */