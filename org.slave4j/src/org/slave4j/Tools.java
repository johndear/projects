package org.slave4j;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.internal.core.JavaProject;
import org.eclipse.jdt.internal.core.PackageFragment;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.slave4j.bean.ClassNameInfo;
import org.slave4j.bean.EntityFieldInfo;
import org.slave4j.bean.JavaTemplateArgs;
import org.slave4j.bean.JavaTemplateType;
import org.slave4j.bean.JsTemplateArgs;
import org.slave4j.bean.JspTemplateArgs;
import org.slave4j.bean.JspTemplateType;
import org.slave4j.bean.PackageNameInfo;

public class Tools {
	public static void copyFileToProject(String source, String target)
			throws IOException {
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

	public static List<EntityFieldInfo> scanEntityField(
			ICompilationUnit compilationUnit) {
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
			EntityFieldInfo entityFieldVo = new EntityFieldInfo();

			entityFieldVo.setFieldType(fieldDec.getType().toString());
			for (Iterator localIterator = fieldDec.fragments().iterator(); localIterator
					.hasNext();) {
				Object fragment = localIterator.next();

				VariableDeclarationFragment frag = (VariableDeclarationFragment) fragment;

				entityFieldVo.addFieldName(frag.getName().toString());
			}
			entityFieldVos.add(entityFieldVo);
		}
		return entityFieldVos;
	}

	public static List<JavaTemplateArgs> createJavaTemplateArgsList(
			IStructuredSelection selection) throws Exception {
		List javaTemplateArgsList = new ArrayList();

		Object obj = selection.getFirstElement();
		if ((obj instanceof PackageFragment)) {
			PackageFragment packageFragment = (PackageFragment) obj;

			PackageNameInfo packageNameInfo = new PackageNameInfo(
					packageFragment);
			ICompilationUnit[] compilationUnits = packageFragment
					.getCompilationUnits();

			for (ICompilationUnit compilationUnit : compilationUnits) {
				ClassNameInfo classNameInfo = new ClassNameInfo(compilationUnit);

				List entityFieldInfoList = scanEntityField(compilationUnit);

				JavaTemplateArgs daoTemplateArgs = new JavaTemplateArgs();
				daoTemplateArgs.setPackageNameInfo(packageNameInfo);
				daoTemplateArgs.setClassNameInfo(classNameInfo);
				daoTemplateArgs.setEntityFieldInfoList(entityFieldInfoList);
				daoTemplateArgs.setType(JavaTemplateType.DAO);
				javaTemplateArgsList.add(daoTemplateArgs);

				JavaTemplateArgs serviceTemplateArgs = new JavaTemplateArgs();
				serviceTemplateArgs.setPackageNameInfo(packageNameInfo);
				serviceTemplateArgs.setClassNameInfo(classNameInfo);
				serviceTemplateArgs.setEntityFieldInfoList(entityFieldInfoList);
				serviceTemplateArgs.setType(JavaTemplateType.SERVICE);
				javaTemplateArgsList.add(serviceTemplateArgs);

				JavaTemplateArgs actionTemplateArgs = new JavaTemplateArgs();
				actionTemplateArgs.setPackageNameInfo(packageNameInfo);
				actionTemplateArgs.setClassNameInfo(classNameInfo);
				actionTemplateArgs.setEntityFieldInfoList(entityFieldInfoList);
				actionTemplateArgs.setType(JavaTemplateType.ACTION);
				javaTemplateArgsList.add(actionTemplateArgs);
			}
		}
		return javaTemplateArgsList;
	}

	public static List<JspTemplateArgs> createJspTemplateArgsList(
			IStructuredSelection selection) throws Exception {
		List jspTemplateArgsList = new ArrayList();

		Object obj = selection.getFirstElement();
		if ((obj instanceof PackageFragment)) {
			PackageFragment packageFragment = (PackageFragment) obj;

			PackageNameInfo packageNameInfo = new PackageNameInfo(
					packageFragment);
			ICompilationUnit[] compilationUnits = packageFragment
					.getCompilationUnits();

			for (ICompilationUnit compilationUnit : compilationUnits) {
				ClassNameInfo classNameInfo = new ClassNameInfo(compilationUnit);

				List entityFieldInfoList = scanEntityField(compilationUnit);

				JspTemplateArgs JspListTemplateArgs = new JspTemplateArgs();
				JspListTemplateArgs.setPackageNameInfo(packageNameInfo);
				JspListTemplateArgs.setClassNameInfo(classNameInfo);
				JspListTemplateArgs.setEntityFieldInfoList(entityFieldInfoList);
				JspListTemplateArgs.setType(JspTemplateType.LIST);
				jspTemplateArgsList.add(JspListTemplateArgs);

				JspTemplateArgs JspInputTemplateArgs = new JspTemplateArgs();
				JspInputTemplateArgs.setPackageNameInfo(packageNameInfo);
				JspInputTemplateArgs.setClassNameInfo(classNameInfo);
				JspInputTemplateArgs
						.setEntityFieldInfoList(entityFieldInfoList);
				JspInputTemplateArgs.setType(JspTemplateType.INPUT);
				jspTemplateArgsList.add(JspInputTemplateArgs);
			}
		}
		return jspTemplateArgsList;
	}

	public static JspTemplateArgs createCustomJspTemplateArgsList(
			IStructuredSelection selection) throws Exception {
		Object obj = selection.getFirstElement();
		if ((obj instanceof JavaProject)) {
			JavaProject javaProject = (JavaProject) obj;

			JspTemplateArgs jspTemplateArgs = new JspTemplateArgs();
			jspTemplateArgs.setJspName("test123");
			return jspTemplateArgs;
		}
		return null;
	}

	public static JsTemplateArgs createCustomJsTemplateArgsList(
			IStructuredSelection selection) throws Exception {
		Object obj = selection.getFirstElement();
		if ((obj instanceof JavaProject)) {
			JsTemplateArgs jsTemplateArgs = new JsTemplateArgs();
//				jsTemplateArgs.setClassNameInfo(classNameInfo);
//				jsTemplateArgs.setEntityFieldInfoList(entityFieldInfoList);
			jsTemplateArgs.setJsName("test123");
			return jsTemplateArgs;
		}
		return null;
	}

}