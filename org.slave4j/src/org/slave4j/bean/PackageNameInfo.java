package org.slave4j.bean;

import org.eclipse.jdt.core.IPackageFragment;

public class PackageNameInfo {
	private String entityPackageName;
	private String modeName;
	private boolean sameEntityPackageName;

	public PackageNameInfo(IPackageFragment packageFragment) {
		this.entityPackageName = packageFragment.getElementName();
		if (entityPackageName.indexOf(".") != -1) {
			String basePackageName = this.entityPackageName.substring(0,
					this.entityPackageName.lastIndexOf("."));
			this.modeName = basePackageName.substring(basePackageName
					.lastIndexOf(".") + 1, basePackageName.length());
		}
	}

	public void setEntityPackageName(String entityPackageName) {
		this.entityPackageName = entityPackageName;
	}

	public String getEntityPackageName() {
		if (sameEntityPackageName) {
			return this.entityPackageName;
		} else {
			return getPackageName(this.entityPackageName, "entity");
		}
	}

	public String getDaoPackageName() {
		if (sameEntityPackageName) {
			return this.entityPackageName;
		} else {
			return getPackageName(this.entityPackageName, "dao");
		}
	}

	public String getServicPackageName() {
		if (sameEntityPackageName) {
			return this.entityPackageName;
		} else {
			return getPackageName(this.entityPackageName, "service");
		}
	}

	public String getActionPackageName() {
		if (sameEntityPackageName) {
			return this.entityPackageName;
		} else {
			return getPackageName(this.entityPackageName, "action");
		}
	}

	public String getModeName() {
		return this.modeName;
	}

	private String getPackageName(String entityPackageName, String type) {
		if (entityPackageName.indexOf(".") != -1) {
			int index = entityPackageName.lastIndexOf(".");
			return entityPackageName.substring(0, index) + "." + type;
		}

		return type;
	}

	public void setSameEntityPackageName(boolean sameEntityPackageName) {
		this.sameEntityPackageName = sameEntityPackageName;
	}

	public boolean getSameEntityPackageName() {
		return sameEntityPackageName;
	}

}