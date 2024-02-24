/*     */ package com.compiler;
/*     */ 
/*     */ import java.io.Closeable;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.WeakHashMap;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import javax.tools.Diagnostic;
/*     */ import javax.tools.DiagnosticListener;
/*     */ import javax.tools.JavaFileObject;
/*     */ import javax.tools.StandardJavaFileManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CachedCompiler
/*     */   implements Closeable
/*     */ {
/*  36 */   private static final PrintWriter DEFAULT_WRITER = new PrintWriter(System.err);
/*     */   
/*  38 */   private final Map<ClassLoader, Map<String, Class>> loadedClassesMap = Collections.synchronizedMap(new WeakHashMap<>());
/*  39 */   private final Map<ClassLoader, MyJavaFileManager> fileManagerMap = Collections.synchronizedMap(new WeakHashMap<>());
/*     */   
/*     */   private final File sourceDir;
/*     */   
/*     */   private final File classDir;
/*     */   
/*  45 */   private final ConcurrentMap<String, JavaFileObject> javaFileObjects = new ConcurrentHashMap<>();
/*     */   
/*     */   public CachedCompiler(File sourceDir, File classDir) {
/*  48 */     this.sourceDir = sourceDir;
/*  49 */     this.classDir = classDir;
/*     */   }
/*     */   
/*     */   public void close() {
/*     */     try {
/*  54 */       for (MyJavaFileManager fileManager : this.fileManagerMap.values()) {
/*  55 */         fileManager.close();
/*     */       }
/*  57 */     } catch (IOException e) {
/*  58 */       throw new AssertionError(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Class loadFromJava(String className, String javaCode) throws ClassNotFoundException {
/*  63 */     return loadFromJava(getClass().getClassLoader(), className, javaCode, DEFAULT_WRITER);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Class loadFromJava(ClassLoader classLoader, String className, String javaCode) throws ClassNotFoundException {
/*  69 */     return loadFromJava(classLoader, className, javaCode, DEFAULT_WRITER);
/*     */   }
/*     */ 
/*     */   
/*     */   Map<String, byte[]> compileFromJava(String className, String javaCode, MyJavaFileManager fileManager) {
/*  74 */     return compileFromJava(className, javaCode, DEFAULT_WRITER, fileManager);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Map<String, byte[]> compileFromJava(String className, String javaCode, final PrintWriter writer, MyJavaFileManager fileManager) {
/*     */     Iterable<? extends JavaFileObject> compilationUnits;
/*  83 */     if (this.sourceDir != null) {
/*  84 */       String filename = className.replaceAll("\\.", '\\' + File.separator) + ".java";
/*  85 */       File file = new File(this.sourceDir, filename);
/*  86 */       CompilerUtils.writeText(file, javaCode);
/*  87 */       if (CompilerUtils.s_standardJavaFileManager == null)
/*  88 */         CompilerUtils.s_standardJavaFileManager = CompilerUtils.s_compiler.getStandardFileManager(null, null, null); 
/*  89 */       compilationUnits = CompilerUtils.s_standardJavaFileManager.getJavaFileObjects(new File[] { file });
/*     */     } else {
/*     */       
/*  92 */       this.javaFileObjects.put(className, new JavaSourceFromString(className, javaCode));
/*  93 */       compilationUnits = new ArrayList<>(this.javaFileObjects.values());
/*     */     } 
/*     */     
/*  96 */     List<String> options = Arrays.asList(new String[] { "-g", "-nowarn" });
/*  97 */     boolean ok = CompilerUtils.s_compiler.getTask(writer, fileManager, new DiagnosticListener<JavaFileObject>()
/*     */         {
/*     */           public void report(Diagnostic<? extends JavaFileObject> diagnostic) {
/* 100 */             if (diagnostic.getKind() == Diagnostic.Kind.ERROR) {
/* 101 */               writer.println(diagnostic);
/*     */             }
/*     */           }
/* 104 */         },  options, null, compilationUnits).call().booleanValue();
/*     */     
/* 106 */     if (!ok) {
/*     */       
/* 108 */       if (this.sourceDir == null) {
/* 109 */         this.javaFileObjects.remove(className);
/*     */       }
/*     */       
/* 112 */       return (Map)Collections.emptyMap();
/*     */     } 
/*     */     
/* 115 */     return fileManager.getAllBuffers();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Class loadFromJava(ClassLoader classLoader, String className, String javaCode, PrintWriter writer) throws ClassNotFoundException {
/*     */     Map<String, Class<?>> loadedClasses;
/* 123 */     Class<?> clazz = null;
/*     */     
/* 125 */     synchronized (this.loadedClassesMap) {
/* 126 */       loadedClasses = this.loadedClassesMap.get(classLoader);
/* 127 */       if (loadedClasses == null) {
/* 128 */         this.loadedClassesMap.put(classLoader, loadedClasses = new LinkedHashMap<>());
/*     */       } else {
/* 130 */         clazz = loadedClasses.get(className);
/*     */       } 
/* 132 */     }  PrintWriter printWriter = (writer == null) ? DEFAULT_WRITER : writer;
/* 133 */     if (clazz != null) {
/* 134 */       return clazz;
/*     */     }
/* 136 */     MyJavaFileManager fileManager = this.fileManagerMap.get(classLoader);
/* 137 */     if (fileManager == null) {
/* 138 */       StandardJavaFileManager standardJavaFileManager = CompilerUtils.s_compiler.getStandardFileManager(null, null, null);
/* 139 */       this.fileManagerMap.put(classLoader, fileManager = new MyJavaFileManager(standardJavaFileManager));
/*     */     } 
/* 141 */     Map<String, byte[]> compiled = compileFromJava(className, javaCode, printWriter, fileManager);
/* 142 */     for (Map.Entry<String, byte[]> entry : compiled.entrySet()) {
/* 143 */       String className2 = entry.getKey();
/* 144 */       synchronized (this.loadedClassesMap) {
/* 145 */         if (loadedClasses.containsKey(className2))
/*     */           continue; 
/*     */       } 
/* 148 */       byte[] bytes = entry.getValue();
/* 149 */       if (this.classDir != null) {
/* 150 */         String filename = className2.replaceAll("\\.", '\\' + File.separator) + ".class";
/* 151 */         boolean changed = CompilerUtils.writeBytes(new File(this.classDir, filename), bytes);
/* 152 */         if (changed) {
/* 153 */           System.out.println("Updated " + className2 + "in " + this.classDir);
/*     */         }
/*     */       } 
/*     */       
/* 157 */       synchronized (className2.intern()) {
/* 158 */         synchronized (this.loadedClassesMap) {
/* 159 */           if (loadedClasses.containsKey(className2)) {
/*     */             continue;
/*     */           }
/*     */         } 
/* 163 */         Class<?> clazz2 = CompilerUtils.defineClass(classLoader, className2, bytes);
/* 164 */         synchronized (this.loadedClassesMap) {
/* 165 */           loadedClasses.put(className2, clazz2);
/*     */         } 
/*     */       } 
/*     */     } 
/* 169 */     synchronized (this.loadedClassesMap) {
/* 170 */       loadedClasses.put(className, clazz = classLoader.loadClass(className));
/*     */     } 
/* 172 */     return clazz;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\compiler\CachedCompiler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */