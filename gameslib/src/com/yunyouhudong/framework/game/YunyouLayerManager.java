package com.yunyouhudong.framework.game;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.Layer;

/**
 * This is designed as an enhanced class to {@link javax.microedition.lcdui.game.LayerManager}<br/>
 * <br/>
 * In a {@link YunyouLayerManager}, it will maintain a list of lenses, where each of the lens will maintain a stack of layers. Lens with a greater
 * index will be rendered on top of the others with smaller index. Same as layers. Within a same lens, layer with a greater index will be rendered on
 * top of the others with smaller index.
 * 
 * @author Lufei Deng
 * @since 2020/11/06
 */
public class YunyouLayerManager {

	private static final int LAYERS_INC = 4;

	private int nLenses;
	private int[] nLayersInLens;

	private Layer[][] layerLenses;

	private int viewX;
	private int viewY;
	private int viewWidth;
	private int viewHeight;

	public YunyouLayerManager(int initalLayerLenses) {
		layerLenses = new Layer[initalLayerLenses][];
		nLayersInLens = new int[initalLayerLenses];
		for (int i = 0; i < initalLayerLenses; i++) {
			layerLenses[i] = new Layer[LAYERS_INC];
			nLayersInLens[i] = 0;
		}
		nLenses = initalLayerLenses;
		setViewWindow(0, 0, Integer.MAX_VALUE, Integer.MAX_VALUE);
	}

	public void append(Layer layer) {
		removeImpl(layer);
		addImpl(layer, nLenses - 1, nLayersInLens[nLenses]);
	}

	public void append(Layer layer, int lensIndex) {
		removeImpl(layer);
		addImpl(layer, lensIndex, nLayersInLens[lensIndex]);
	}

	public void insert(Layer layer, int lensIndex, int layerIndex) {
		if (lensIndex < 0 || lensIndex >= nLenses || layerIndex < 0 || layerIndex >= nLayersInLens[lensIndex]) {
			throw new IndexOutOfBoundsException();
		}
		if (layer == null) {
			throw new NullPointerException();
		}

		for (int i = 0; i < nLenses; i++) {
			for (int j = 0; j < nLayersInLens[i]; j++) {
				if (layerLenses[i][j] == layer) {
					removeImpl(layer);
					break;
				}
			}
		}

		addImpl(layer, lensIndex, layerIndex);
	}

	public Layer getLayerAt(int lensIndex, int layerIndex) {
		if (lensIndex < 0 || lensIndex >= nLenses || layerIndex < 0 || layerIndex >= nLayersInLens[lensIndex]) {
			throw new IndexOutOfBoundsException();
		}
		return layerLenses[lensIndex][layerIndex];
	}

	public int getLensSize() {
		return nLenses;
	}

	public int getLayerSizeInLens(int lensIndex) {
		return nLayersInLens[lensIndex];
	}

	public int getLayerSize() {
		int count = 0;
		for (int i = 0; i < nLenses; i++) {
			count += nLayersInLens[i];
		}
		return count;
	}

	public void remove(Layer layer) {
		removeImpl(layer);
	}

	public void paint(Graphics g, int x, int y) {
		int clipX = g.getClipX();
		int clipY = g.getClipY();
		int clipW = g.getClipWidth();
		int clipH = g.getClipHeight();

		g.translate(x - viewX, y - viewY);

		g.clipRect(viewX, viewY, viewWidth, viewHeight);

		for (int i = 0; i < nLenses; i++) {
			for (int j = 0; j < nLayersInLens[i]; j++) {
				Layer comp = layerLenses[i][j];
				if (comp.isVisible()) {
					comp.paint(g);
				}
			}
		}

		g.translate(-x + viewX, -y + viewY);
		g.setClip(clipX, clipY, clipW, clipH);
	}

	public void setViewWindow(int x, int y, int width, int height) {
		if ((width < 0) || (height < 0)) {
			throw new IllegalArgumentException();
		}

		viewX = x;
		viewY = y;
		viewWidth = width;
		viewHeight = height;
	}

	public void clearAllLayers() {
		for (int i = 0; i < nLenses; i++) {
			for (int j = 0; j < nLayersInLens[i]; j++) {
				layerLenses[i][j] = null;
			}
			layerLenses[i] = null;
		}
	}

	private void addImpl(Layer layer, int lensIndex, int layerIndex) {
		if (nLayersInLens[lensIndex] == layerLenses[lensIndex].length) {
			Layer[] newLayerLens = new Layer[nLayersInLens[lensIndex] + LAYERS_INC];
			System.arraycopy(layerLenses[lensIndex], 0, newLayerLens, 0, layerIndex);
			System.arraycopy(layerLenses[lensIndex], layerIndex, newLayerLens, layerIndex + 1, nLayersInLens[lensIndex] - layerIndex);
			layerLenses[lensIndex] = newLayerLens;
		} else {
			System.arraycopy(layerLenses[lensIndex], layerIndex, layerLenses[lensIndex], layerIndex + 1, nLayersInLens[lensIndex] - layerIndex);
		}

		layerLenses[lensIndex][layerIndex] = layer;
		nLayersInLens[lensIndex] += 1;
	}

	private void removeImpl(Layer layer) {
		if (layer == null) {
			throw new NullPointerException();
		}

		for (int i = 0; i < nLenses; i++) {
			for (int j = 0; j < nLayersInLens[i]; j++) {
				if (layerLenses[i][j] == layer) {
					remove(i, j);
					break;
				}
			}
		}
	}

	private void remove(int lensIndex, int layerIndex) {
		System.arraycopy(layerLenses[lensIndex], layerIndex + 1, layerLenses[lensIndex], layerIndex, nLayersInLens[lensIndex] - layerIndex - 1);
		layerLenses[lensIndex][--nLayersInLens[lensIndex]] = null;
	}
}
