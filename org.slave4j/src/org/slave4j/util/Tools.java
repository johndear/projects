package org.slave4j.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.Javadoc;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.internal.core.JavaProject;
import org.eclipse.jdt.internal.core.PackageFragment;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.slave4j.bean.FieldBean;
import org.slave4j.bean.JavaBean;
import org.slave4j.bean.JavaBeanType;
import org.slave4j.bean.JspBean;
import org.slave4j.bean.JspBeanType;

public class Tools {
	@SuppressWarnings({ "unchecked", "restriction", "rawtypes" })
	public static List<JavaBean> loadJavaTemplateListFromEntity(IStructuredSelection selection) throws Exception {
		List javaTemplateArgsList = new ArrayList();

		Object obj = selection.getFirstElement();
		if ((obj instanceof PackageFragment)) {
			PackageFragment packageFragment = (PackageFragment) obj;
//			PackageNameInfo packageNameInfo = new PackageNameInfo(packageFragment);
			ICompilationUnit[] compilationUnits = packageFragment.getCompilationUnits();
			for (ICompilationUnit compilationUnit : compilationUnits) {
//				ClassNameInfo classNameInfo = new ClassNameInfo(compilationUnit);
				List entityFieldInfoList = scanEntityField(compilationUnit);

				JavaBean daoTemplateArgs = new JavaBean();
				daoTemplateArgs.setEntityPackageName(getEntityPackageName(packageFragment));
				daoTemplateArgs.setEntityClassName(getEntityClassName(compilationUnit));
				daoTemplateArgs.setModeName(getModeName(packageFragment));
				daoTemplateArgs.setEntityFieldInfoList(entityFieldInfoList);
				daoTemplateArgs.setType(JavaBeanType.DAO);
				javaTemplateArgsList.add(daoTemplateArgs);

				JavaBean serviceTemplateArgs = new JavaBean();
				serviceTemplateArgs.setEntityPackageName(getEntityPackageName(packageFragment));
				serviceTemplateArgs.setEntityClassName(getEntityClassName(compilationUnit));
				serviceTemplateArgs.setModeName(getModeName(packageFragment));
				serviceTemplateArgs.setEntityFieldInfoList(entityFieldInfoList);
				serviceTemplateArgs.setType(JavaBeanType.SERVICE);
				javaTemplateArgsList.add(serviceTemplateArgs);

				JavaBean actionTemplateArgs = new JavaBean();
				actionTemplateArgs.setEntityPackageName(getEntityPackageName(packageFragment));
				actionTemplateArgs.setEntityClassName(getEntityClassName(compilationUnit));
				actionTemplateArgs.setModeName(getModeName(packageFragment));
				actionTemplateArgs.setEntityFieldInfoList(entityFieldInfoList);
				actionTemplateArgs.setType(JavaBeanType.ACTION);
				javaTemplateArgsList.add(actionTemplateArgs);
			}
		}
		return javaTemplateArgsList;
	}

	@SuppressWarnings({ "unchecked", "rawtypes", "restriction" })
	public static List<JspBean> loadJspTemplateListFromEntity(IStructuredSelection selection) throws Exception {
		List jspTemplateArgsList = new ArrayList();

		Object obj = selection.getFirstElement();
		if ((obj instanceof PackageFragment)) {
			PackageFragment packageFragment = (PackageFragment) obj;
//			PackageNameInfo packageNameInfo = new PackageNameInfo(packageFragment);
			ICompilationUnit[] compilationUnits = packageFragment.getCompilationUnits();
			for (ICompilationUnit compilationUnit : compilationUnits) {
//				ClassNameInfo classNameInfo = new ClassNameInfo(compilationUnit);
				List entityFieldInfoList = scanEntityField(compilationUnit);

				JspBean JspListTemplateArgs = new JspBean();
				JspListTemplateArgs.setEntityPackageName(getEntityPackageName(packageFragment));
				JspListTemplateArgs.setEntityClassName(getEntityClassName(compilationUnit));
				JspListTemplateArgs.setModeName(getModeName(packageFragment));
				JspListTemplateArgs.setEntityFieldInfoList(entityFieldInfoList);
				JspListTemplateArgs.setJspType(JspBeanType.LIST);
				jspTemplateArgsList.add(JspListTemplateArgs);

				JspBean JspInputTemplateArgs = new JspBean();
				JspInputTemplateArgs.setEntityPackageName(getEntityPackageName(packageFragment));
				JspInputTemplateArgs.setEntityClassName(getEntityClassName(compilationUnit));
				JspInputTemplateArgs.setModeName(getModeName(packageFragment));
				JspInputTemplateArgs.setEntityFieldInfoList(entityFieldInfoList);
				JspInputTemplateArgs.setJspType(JspBeanType.ADD);
				jspTemplateArgsList.add(JspInputTemplateArgs);
				
				JspBean JspEditTemplateArgs = new JspBean();
				JspEditTemplateArgs.setEntityPackageName(getEntityPackageName(packageFragment));
				JspEditTemplateArgs.setEntityClassName(getEntityClassName(compilationUnit));
				JspEditTemplateArgs.setModeName(getModeName(packageFragment));
				JspEditTemplateArgs.setEntityFieldInfoList(entityFieldInfoList);
				JspEditTemplateArgs.setJspType(JspBeanType.EDIT);
				jspTemplateArgsList.add(JspEditTemplateArgs);
			}
		}
		return jspTemplateArgsList;
	}

//	@SuppressWarnings("restriction")
//	public static JsTemplate createCustomJsTemplateArgsList(IStructuredSelection selection) throws Exception {
//		Object obj = selection.getFirstElement();
//		if ((obj instanceof JavaProject)) {
//			JsTemplate jsTemplateArgs = new JsTemplate();
//			// jsTemplateArgs.setClassNameInfo(classNameInfo);
//			// jsTemplateArgs.setEntityFieldInfoList(entityFieldInfoList);
//			jsTemplateArgs.setJsName("test123");
//			return jsTemplateArgs;
//		}
//		return null;
//	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<FieldBean> scanEntityField(ICompilationUnit compilationUnit) {
		List entityFieldVos = new ArrayList();

		ASTParser parser = ASTParser.newParser(3);
		parser.setKind(8);
		parser.setSource(compilationUnit);
		parser.setResolveBindings(true);
		CompilationUnit cu = (CompilationUnit) parser.createAST(null);
		List types = cu.types();
		TypeDeclaration typeDec = (TypeDeclaration) types.get(0);
		FieldDeclaration[] fieldDecs = typeDec.getFields();
		for (FieldDeclaration fieldDec : fieldDecs) {
			FieldBean entityFieldVo = new FieldBean();
			entityFieldVo.setFieldType(fieldDec.getType().toString());
			for (Iterator localIterator = fieldDec.fragments().iterator(); localIterator.hasNext();) {
				Object fragment = localIterator.next();
				VariableDeclarationFragment frag = (VariableDeclarationFragment) fragment;
				entityFieldVo.setFieldName(frag.getName().toString());
			}
			
			Javadoc doc = fieldDec.getJavadoc();// 读取注释
			String cmt = "";
			if(doc!=null){
				cmt = doc.toString();
				cmt = cmt.substring(cmt.indexOf("/**\n")+5, cmt.indexOf("*/\n"));
				cmt = cmt.substring(cmt.indexOf("\n * ")+4, cmt.lastIndexOf("\n"));
			}else{
				cmt = entityFieldVo.getFieldName();
			}
			entityFieldVo.setFieldDesc(cmt);
			entityFieldVos.add(entityFieldVo);
		}
		return entityFieldVos;
	}
	
	public static void copyFileToProject(String source, String target) throws IOException {
		File sourceFile = new File(source);
		File targetFile = new File(target);

		if (sourceFile.isDirectory()) {
			File[] files = sourceFile.listFiles();
			for (File file : files) {
				if (file.isDirectory()) {
					FileUtils.copyDirectoryToDirectory(file, targetFile);
				} else
					FileUtils.copyFileToDirectory(file, targetFile, true);
			}
		} else {
			FileUtils.copyFileToDirectory(sourceFile, targetFile, true);
		}
	}

	public static void createDirectory(String path) {
		File file = new File(path);

		if (file.exists()) {
			file.delete();
		}
		file.mkdirs();
	}

	public static void createFile(String path) {
		File file = new File(path);
		if (file.exists()) {
			file.delete();
		}
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void writeStringToFile(String filePath, String content) {
		File file = new File(filePath);
		try {
			FileUtils.writeStringToFile(file, content, "utf-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getEntityPackageName(IPackageFragment packageFragment) {
		return packageFragment.getElementName();
	}
	
	public static String getModeName(IPackageFragment packageFragment) {
		String modeName ="";
		String entityPackageName = packageFragment.getElementName();
		if (entityPackageName.indexOf(".") != -1) {
			String basePackageName = entityPackageName.substring(0, entityPackageName.lastIndexOf("."));
			modeName = basePackageName.substring(
					basePackageName.lastIndexOf(".") + 1,
					basePackageName.length());
		}
		return modeName;
	}
	
	public static String getModeName(String entityPackageName) {
		String modeName ="";
		if (entityPackageName.indexOf(".") != -1) {
			String basePackageName = entityPackageName.substring(0, entityPackageName.lastIndexOf("."));
			modeName = basePackageName.substring(
					basePackageName.lastIndexOf(".") + 1,
					basePackageName.length());
		}
		return modeName;
	}
	
	public static String getEntityClassName(ICompilationUnit compilationUnit) {
		String entityFileName = compilationUnit.getElementName();
		return entityFileName.substring(0,entityFileName.lastIndexOf("."));
	}

}