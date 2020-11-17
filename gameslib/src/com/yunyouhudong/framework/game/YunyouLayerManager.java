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

	/**
	 * Put the specified layer on top of all layers
	 * 
	 * @param layer
	 */
	public void putLayerOnTopOfAll(Layer layer) {
		append(layer); // This function ensures no duplicate on layers
	}

	/**
	 * Put the specified layer on top of all layers within same Lens
	 * 
	 * @param layer
	 */
	public void putLayerOnTopInLens(Layer layer) {
		for (int i = 0; i < nLenses; i++) {
			for (int j = 0; j < nLayersInLens[i]; j++) {
				if (layerLenses[i][j] == layer) {
					append(layer, i); // This function ensures no duplicate on layers
					return;
				}
			}
		}
	}

	/**
	 * Switch LensA and LensB if LensA is not on top of LensB, otherwise do nothing
	 * 
	 * @param lensAIndex
	 * @param lensBIndex
	 */
	public void switchLensZIndex(int lensAIndex, int lensBIndex) {
		if (lensAIndex < 0 || lensAIndex >= nLenses || lensBIndex < 0 || lensBIndex >= nLenses) {
			throw new IndexOutOfBoundsException();
		}

		if (lensAIndex < lensBIndex) {
			Layer[] layerTemp = layerLenses[lensAIndex];
			layerLenses[lensAIndex] = layerLenses[lensBIndex];
			layerLenses[lensBIndex] = layerTemp;
		}
	}

	/**
	 * Switch layerA and layerB if layerA is not on top of layerB, otherwise do nothing
	 * 
	 * @param layerA
	 * @param layerB
	 */
	public void switchLayerZIndex(Layer layerA, Layer layerB) {
		boolean isLayerAFound = false;
		boolean isLayerBFound = false;
		int layerALensIndex = -1;
		int layerALayerIndex = -1;
		for (int i = 0; i < nLenses; i++) {
			for (int j = 0; j < nLayersInLens[i]; j++) {
				if (!isLayerAFound && layerLenses[i][j] == layerA) {
					isLayerAFound = true;
					layerALensIndex = i;
					layerALayerIndex = j;
					continue;
				}
				if (!isLayerBFound && layerLenses[i][j] == layerB) {
					isLayerBFound = true;
					if (isLayerAFound && layerALensIndex >= 0 && layerALayerIndex >= 0) {
						layerLenses[i][j] = layerA;
						layerLenses[layerALensIndex][layerALayerIndex] = layerB;
						return;
					} else {
						return;
					}
				}
			}
		}
	}

	/**
	 * Append new Layer on top of all layers in top lens
	 * 
	 * @param layer
	 */
	public void append(Layer layer) {
		removeImpl(layer);
		addImpl(layer, nLenses - 1, nLayersInLens[nLenses]);
	}

	/**
	 * Append new layer on top of the layers in lensIndex'th lens
	 * 
	 * @param layer
	 * @param lensIndex
	 */
	public void append(Layer layer, int lensIndex) {
		removeImpl(layer);
		addImpl(layer, lensIndex, nLayersInLens[lensIndex]);
	}

	/**
	 * Insert new Layer on specified lensIndex and layerIndex
	 * 
	 * @param layer
	 * @param lensIndex
	 * @param layerIndex
	 */
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

	/**
	 * Return the Layer at specified lensIndex and layerIndex
	 * 
	 * @param lensIndex
	 * @param layerIndex
	 * @return
	 */
	public Layer getLayerAt(int lensIndex, int layerIndex) {
		if (lensIndex < 0 || lensIndex >= nLenses || layerIndex < 0 || layerIndex >= nLayersInLens[lensIndex]) {
			throw new IndexOutOfBoundsException();
		}
		return layerLenses[lensIndex][layerIndex];
	}

	/**
	 * Return total number of Lenses
	 * 
	 * @return
	 */
	public int getLensSize() {
		return nLenses;
	}

	/**
	 * Return number of layers in Lens of specified lensIndex
	 * 
	 * @param lensIndex
	 * @return
	 */
	public int getLayerSizeInLens(int lensIndex) {
		return nLayersInLens[lensIndex];
	}

	/**
	 * Return total number of Layers in all Lenses
	 * 
	 * @return
	 */
	public int getLayerSize() {
		int count = 0;
		for (int i = 0; i < nLenses; i++) {
			count += nLayersInLens[i];
		}
		return count;
	}

	/**
	 * Remove the specified Layer
	 * 
	 * @param layer
	 */
	public void remove(Layer layer) {
		removeImpl(layer);
	}

	/**
	 * This function paints all Layers in below order:<br/>
	 * 1. Paints layers in lower index Lens first<br/>
	 * 2. Within same Lens, paints lower index layers first<br/>
	 * The last painted layer will stay on top of all others
	 * 
	 * @param graphics
	 * @param x
	 * @param y
	 */
	public void paint(Graphics graphics, int x, int y) {
		int clipX = graphics.getClipX();
		int clipY = graphics.getClipY();
		int clipW = graphics.getClipWidth();
		int clipH = graphics.getClipHeight();

		graphics.translate(x - viewX, y - viewY);

		graphics.clipRect(viewX, viewY, viewWidth, viewHeight);

		for (int i = 0; i < nLenses; i++) {
			for (int j = 0; j < nLayersInLens[i]; j++) {
				Layer comp = layerLenses[i][j];
				if (comp.isVisible()) {
					comp.paint(graphics);
				}
			}
		}

		graphics.translate(-x + viewX, -y + viewY);
		graphics.setClip(clipX, clipY, clipW, clipH);
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
