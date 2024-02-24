/*    */ package net.minecraft.client.resources.data;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import net.minecraft.client.resources.Language;
/*    */ 
/*    */ 
/*    */ public class LanguageMetadataSection
/*    */   implements IMetadataSection
/*    */ {
/*    */   private final Collection<Language> languages;
/*    */   
/*    */   public LanguageMetadataSection(Collection<Language> p_i1311_1_) {
/* 13 */     this.languages = p_i1311_1_;
/*    */   }
/*    */ 
/*    */   
/*    */   public Collection<Language> getLanguages() {
/* 18 */     return this.languages;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\resources\data\LanguageMetadataSection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */