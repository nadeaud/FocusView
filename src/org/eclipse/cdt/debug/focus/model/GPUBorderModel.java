package org.eclipse.cdt.debug.focus.model;

import java.util.ArrayList;
import java.util.List;

public class GPUBorderModel {
	
	private static GPUBorderModel instance;
	
	private List<Border> borders;
	
	private enum ModelProvider {
		INSTANCE;
		private List<Border> borders;
		private ModelProvider() {
			borders = new ArrayList<Border>();
			borders.add(new Border("x", -1, -1));
			borders.add(new Border("y", -1, -1));
			borders.add(new Border("z", -1, -1));
		}
		public List<Border> getData() {
			return borders;
		}
	}
	
	protected GPUBorderModel() {
		borders = new ArrayList<>();
		for(Border border : ModelProvider.INSTANCE.getData()) {
			borders.add(border);
		}
	}
	
	public static GPUBorderModel getInstance() {
		if(instance == null) {
			synchronized(GPUBorderModel.class) {
				if(instance == null) {
					instance = new GPUBorderModel();
				}
			}
		}
		return instance;
	}
	
	public List<Border> getBorders() {
		return borders;
	}
	
}
