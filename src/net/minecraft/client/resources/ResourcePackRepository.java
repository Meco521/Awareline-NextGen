/*     */ package net.minecraft.client.resources;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.hash.Hashing;
/*     */ import com.google.common.io.Files;
/*     */ import com.google.common.util.concurrent.FutureCallback;
/*     */ import com.google.common.util.concurrent.Futures;
/*     */ import com.google.common.util.concurrent.ListenableFuture;
/*     */ import com.google.common.util.concurrent.SettableFuture;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.Closeable;
/*     */ import java.io.File;
/*     */ import java.io.FileFilter;
/*     */ import java.io.IOException;
/*     */ import java.util.Arrays;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.Future;
/*     */ import java.util.concurrent.locks.ReentrantLock;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.gui.GuiScreenWorking;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.client.resources.data.IMetadataSerializer;
/*     */ import net.minecraft.client.resources.data.PackMetadataSection;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.util.HttpUtil;
/*     */ import net.minecraft.util.IProgressUpdate;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.apache.commons.io.FileUtils;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.apache.commons.io.filefilter.IOFileFilter;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class ResourcePackRepository {
/*  39 */   private static final Logger logger = LogManager.getLogger();
/*  40 */   private static final FileFilter resourcePackFilter = new FileFilter()
/*     */     {
/*     */       public boolean accept(File p_accept_1_)
/*     */       {
/*  44 */         boolean flag = (p_accept_1_.isFile() && p_accept_1_.getName().endsWith(".zip"));
/*  45 */         boolean flag1 = (p_accept_1_.isDirectory() && (new File(p_accept_1_, "pack.mcmeta")).isFile());
/*  46 */         return (flag || flag1);
/*     */       }
/*     */     };
/*     */   private final File dirResourcepacks;
/*     */   public final IResourcePack rprDefaultResourcePack;
/*     */   private final File dirServerResourcepacks;
/*     */   public final IMetadataSerializer rprMetadataSerializer;
/*     */   private IResourcePack resourcePackInstance;
/*  54 */   private final ReentrantLock lock = new ReentrantLock();
/*     */   private ListenableFuture<Object> downloadingPacks;
/*  56 */   private List<Entry> repositoryEntriesAll = Lists.newArrayList();
/*  57 */   public List<Entry> repositoryEntries = Lists.newArrayList();
/*     */ 
/*     */   
/*     */   public ResourcePackRepository(File dirResourcepacksIn, File dirServerResourcepacksIn, IResourcePack rprDefaultResourcePackIn, IMetadataSerializer rprMetadataSerializerIn, GameSettings settings) {
/*  61 */     this.dirResourcepacks = dirResourcepacksIn;
/*  62 */     this.dirServerResourcepacks = dirServerResourcepacksIn;
/*  63 */     this.rprDefaultResourcePack = rprDefaultResourcePackIn;
/*  64 */     this.rprMetadataSerializer = rprMetadataSerializerIn;
/*  65 */     fixDirResourcepacks();
/*  66 */     updateRepositoryEntriesAll();
/*  67 */     Iterator<String> iterator = settings.resourcePacks.iterator();
/*     */     
/*  69 */     while (iterator.hasNext()) {
/*     */       
/*  71 */       String s = iterator.next();
/*     */       
/*  73 */       for (Entry resourcepackrepository$entry : this.repositoryEntriesAll) {
/*     */         
/*  75 */         if (resourcepackrepository$entry.getResourcePackName().equals(s)) {
/*     */           
/*  77 */           if (resourcepackrepository$entry.func_183027_f() == 1 || settings.incompatibleResourcePacks.contains(resourcepackrepository$entry.getResourcePackName())) {
/*     */             
/*  79 */             this.repositoryEntries.add(resourcepackrepository$entry);
/*     */             
/*     */             break;
/*     */           } 
/*  83 */           iterator.remove();
/*  84 */           logger.warn("Removed selected resource pack {} because it's no longer compatible", new Object[] { resourcepackrepository$entry.getResourcePackName() });
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void fixDirResourcepacks() {
/*  92 */     if (this.dirResourcepacks.exists()) {
/*     */       
/*  94 */       if (!this.dirResourcepacks.isDirectory() && (!this.dirResourcepacks.delete() || !this.dirResourcepacks.mkdirs()))
/*     */       {
/*  96 */         logger.warn("Unable to recreate resourcepack folder, it exists but is not a directory: " + this.dirResourcepacks);
/*     */       }
/*     */     }
/*  99 */     else if (!this.dirResourcepacks.mkdirs()) {
/*     */       
/* 101 */       logger.warn("Unable to create resourcepack folder: " + this.dirResourcepacks);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private List<File> getResourcePackFiles() {
/* 107 */     return this.dirResourcepacks.isDirectory() ? Arrays.<File>asList(this.dirResourcepacks.listFiles(resourcePackFilter)) : Collections.<File>emptyList();
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateRepositoryEntriesAll() {
/* 112 */     List<Entry> list = Lists.newArrayList();
/*     */     
/* 114 */     for (File file1 : getResourcePackFiles()) {
/*     */       
/* 116 */       Entry resourcepackrepository$entry = new Entry(file1);
/*     */       
/* 118 */       if (!this.repositoryEntriesAll.contains(resourcepackrepository$entry)) {
/*     */ 
/*     */         
/*     */         try {
/* 122 */           resourcepackrepository$entry.updateResourcePack();
/* 123 */           list.add(resourcepackrepository$entry);
/*     */         }
/* 125 */         catch (Exception var61) {
/*     */           
/* 127 */           list.remove(resourcepackrepository$entry);
/*     */         } 
/*     */         
/*     */         continue;
/*     */       } 
/* 132 */       int i = this.repositoryEntriesAll.indexOf(resourcepackrepository$entry);
/*     */       
/* 134 */       if (i > -1 && i < this.repositoryEntriesAll.size())
/*     */       {
/* 136 */         list.add(this.repositoryEntriesAll.get(i));
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 141 */     this.repositoryEntriesAll.removeAll(list);
/*     */     
/* 143 */     for (Entry resourcepackrepository$entry1 : this.repositoryEntriesAll)
/*     */     {
/* 145 */       resourcepackrepository$entry1.closeResourcePack();
/*     */     }
/*     */     
/* 148 */     this.repositoryEntriesAll = list;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Entry> getRepositoryEntriesAll() {
/* 153 */     return (List<Entry>)ImmutableList.copyOf(this.repositoryEntriesAll);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Entry> getRepositoryEntries() {
/* 158 */     return (List<Entry>)ImmutableList.copyOf(this.repositoryEntries);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRepositories(List<Entry> repositories) {
/* 163 */     this.repositoryEntries.clear();
/* 164 */     this.repositoryEntries.addAll(repositories);
/*     */   }
/*     */ 
/*     */   
/*     */   public File getDirResourcepacks() {
/* 169 */     return this.dirResourcepacks;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ListenableFuture<Object> downloadResourcePack(String url, String hash) {
/*     */     String s;
/* 176 */     if (hash.matches("^[a-f0-9]{40}$")) {
/*     */       
/* 178 */       s = hash;
/*     */     }
/*     */     else {
/*     */       
/* 182 */       s = "legacy";
/*     */     } 
/*     */     
/* 185 */     final File file1 = new File(this.dirServerResourcepacks, s);
/* 186 */     this.lock.lock();
/*     */ 
/*     */     
/*     */     try {
/* 190 */       clearResourcePack();
/*     */       
/* 192 */       if (file1.exists() && hash.length() == 40) {
/*     */         
/*     */         try {
/*     */           
/* 196 */           String s1 = Hashing.sha1().hashBytes(Files.toByteArray(file1)).toString();
/*     */           
/* 198 */           if (s1.equals(hash)) {
/*     */             
/* 200 */             ListenableFuture<Object> listenablefuture2 = setResourcePackInstance(file1);
/* 201 */             ListenableFuture<Object> listenablefuture3 = listenablefuture2;
/* 202 */             return listenablefuture3;
/*     */           } 
/*     */           
/* 205 */           logger.warn("File " + file1 + " had wrong hash (expected " + hash + ", found " + s1 + "). Deleting it.");
/* 206 */           FileUtils.deleteQuietly(file1);
/*     */         }
/* 208 */         catch (IOException ioexception) {
/*     */           
/* 210 */           logger.warn("File " + file1 + " couldn't be hashed. Deleting it.", ioexception);
/* 211 */           FileUtils.deleteQuietly(file1);
/*     */         } 
/*     */       }
/*     */       
/* 215 */       deleteOldServerResourcesPacks();
/* 216 */       final GuiScreenWorking guiscreenworking = new GuiScreenWorking();
/* 217 */       Map<String, String> map = Minecraft.getSessionInfo();
/* 218 */       final Minecraft minecraft = Minecraft.getMinecraft();
/* 219 */       Futures.getUnchecked((Future)minecraft.addScheduledTask(new Runnable()
/*     */             {
/*     */               public void run()
/*     */               {
/* 223 */                 minecraft.displayGuiScreen((GuiScreen)guiscreenworking);
/*     */               }
/*     */             }));
/* 226 */       final SettableFuture<Object> settablefuture = SettableFuture.create();
/* 227 */       this.downloadingPacks = HttpUtil.downloadResourcePack(file1, url, map, 52428800, (IProgressUpdate)guiscreenworking, minecraft.getProxy());
/* 228 */       Futures.addCallback(this.downloadingPacks, new FutureCallback<Object>()
/*     */           {
/*     */             public void onSuccess(Object p_onSuccess_1_)
/*     */             {
/* 232 */               ResourcePackRepository.this.setResourcePackInstance(file1);
/* 233 */               settablefuture.set(null);
/*     */             }
/*     */             
/*     */             public void onFailure(Throwable p_onFailure_1_) {
/* 237 */               settablefuture.setException(p_onFailure_1_);
/*     */             }
/*     */           });
/* 240 */       ListenableFuture<Object> listenablefuture = this.downloadingPacks;
/* 241 */       ListenableFuture<Object> listenablefuture11 = listenablefuture;
/* 242 */       return listenablefuture11;
/*     */     }
/*     */     finally {
/*     */       
/* 246 */       this.lock.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void deleteOldServerResourcesPacks() {
/* 255 */     List<File> list = Lists.newArrayList(FileUtils.listFiles(this.dirServerResourcepacks, TrueFileFilter.TRUE, (IOFileFilter)null));
/* 256 */     list.sort(LastModifiedFileComparator.LASTMODIFIED_REVERSE);
/* 257 */     int i = 0;
/*     */     
/* 259 */     for (File file1 : list) {
/*     */       
/* 261 */       if (i++ >= 10) {
/*     */         
/* 263 */         logger.info("Deleting old server resource pack " + file1.getName());
/* 264 */         FileUtils.deleteQuietly(file1);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ListenableFuture<Object> setResourcePackInstance(File resourceFile) {
/* 271 */     this.resourcePackInstance = new FileResourcePack(resourceFile);
/* 272 */     return Minecraft.getMinecraft().scheduleResourcesRefresh();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IResourcePack getResourcePackInstance() {
/* 280 */     return this.resourcePackInstance;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clearResourcePack() {
/* 285 */     this.lock.lock();
/*     */ 
/*     */     
/*     */     try {
/* 289 */       if (this.downloadingPacks != null)
/*     */       {
/* 291 */         this.downloadingPacks.cancel(true);
/*     */       }
/*     */       
/* 294 */       this.downloadingPacks = null;
/*     */       
/* 296 */       if (this.resourcePackInstance != null)
/*     */       {
/* 298 */         this.resourcePackInstance = null;
/* 299 */         Minecraft.getMinecraft().scheduleResourcesRefresh();
/*     */       }
/*     */     
/*     */     } finally {
/*     */       
/* 304 */       this.lock.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public class Entry
/*     */   {
/*     */     private final File resourcePackFile;
/*     */     private IResourcePack reResourcePack;
/*     */     private PackMetadataSection rePackMetadataSection;
/*     */     private BufferedImage texturePackIcon;
/*     */     private ResourceLocation locationTexturePackIcon;
/*     */     
/*     */     Entry(File resourcePackFileIn) {
/* 318 */       this.resourcePackFile = resourcePackFileIn;
/*     */     }
/*     */ 
/*     */     
/*     */     public void updateResourcePack() throws IOException {
/* 323 */       this.reResourcePack = this.resourcePackFile.isDirectory() ? new FolderResourcePack(this.resourcePackFile) : new FileResourcePack(this.resourcePackFile);
/* 324 */       this.rePackMetadataSection = this.reResourcePack.<PackMetadataSection>getPackMetadata(ResourcePackRepository.this.rprMetadataSerializer, "pack");
/*     */ 
/*     */       
/*     */       try {
/* 328 */         this.texturePackIcon = this.reResourcePack.getPackImage();
/*     */       }
/* 330 */       catch (IOException iOException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 335 */       if (this.texturePackIcon == null)
/*     */       {
/* 337 */         this.texturePackIcon = ResourcePackRepository.this.rprDefaultResourcePack.getPackImage();
/*     */       }
/*     */       
/* 340 */       closeResourcePack();
/*     */     }
/*     */ 
/*     */     
/*     */     public void bindTexturePackIcon(TextureManager textureManagerIn) {
/* 345 */       if (this.locationTexturePackIcon == null)
/*     */       {
/* 347 */         this.locationTexturePackIcon = textureManagerIn.getDynamicTextureLocation("texturepackicon", new DynamicTexture(this.texturePackIcon));
/*     */       }
/*     */       
/* 350 */       textureManagerIn.bindTexture(this.locationTexturePackIcon);
/*     */     }
/*     */ 
/*     */     
/*     */     public void closeResourcePack() {
/* 355 */       if (this.reResourcePack instanceof Closeable)
/*     */       {
/* 357 */         IOUtils.closeQuietly((Closeable)this.reResourcePack);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public IResourcePack getResourcePack() {
/* 363 */       return this.reResourcePack;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getResourcePackName() {
/* 368 */       return this.reResourcePack.getPackName();
/*     */     }
/*     */ 
/*     */     
/*     */     public String getTexturePackDescription() {
/* 373 */       return (this.rePackMetadataSection == null) ? (EnumChatFormatting.RED + "Invalid pack.mcmeta (or missing 'pack' section)") : this.rePackMetadataSection.getPackDescription().getFormattedText();
/*     */     }
/*     */ 
/*     */     
/*     */     public int func_183027_f() {
/* 378 */       return this.rePackMetadataSection.getPackFormat();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object p_equals_1_) {
/* 383 */       return (this == p_equals_1_) ? true : ((p_equals_1_ instanceof Entry) ? toString().equals(p_equals_1_.toString()) : false);
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 388 */       return toString().hashCode();
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 393 */       return String.format("%s:%s:%d", new Object[] { this.resourcePackFile.getName(), this.resourcePackFile.isDirectory() ? "folder" : "zip", Long.valueOf(this.resourcePackFile.lastModified()) });
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\resources\ResourcePackRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */