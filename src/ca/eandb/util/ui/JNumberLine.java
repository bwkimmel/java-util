/*
 * Copyright (c) 2008 Bradley W. Kimmel
 * 
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package ca.eandb.util.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;

/**
 * @author brad
 *
 */
public class JNumberLine extends JComponent {
	
	/** Serialization version ID. */
	private static final long serialVersionUID = 6382523327122484024L;

	private Color backgroundColor = Color.WHITE;
	
	private Color tickColor = Color.BLACK;
	
	private Color markColor = Color.RED;
	
	private Color zeroMarkColor = Color.BLUE;
	
	private Color borderColor = Color.BLACK;
	
	private double value = Math.PI;

	private double minimumVisible = -10.0;
	
	private double maximumVisible = 10.0;
	
	private Point dragRefPoint = null;
	
	/**
	 * 
	 */
	public JNumberLine() {
		addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				onComponentResized(e);
			}
		});
		addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				onMouseDragged(e);
			}
		});
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				onMousePressed(e);
			}
			public void mouseReleased(MouseEvent e) {
				onMouseReleased(e);
			}
			public void mouseClicked(MouseEvent e) {
				onMouseClicked(e);
			}
		});
	}

	protected void onComponentResized(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	private void onMouseDragged(MouseEvent e) {
		boolean button1 = (e.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) != 0;
		boolean button2 = (e.getModifiersEx() & MouseEvent.BUTTON3_DOWN_MASK) != 0;
		if (button1) {
			int dx = e.getX() - dragRefPoint.x;
			if (dx != 0) {
				double dv = getPixelIncrement() * (double) dx;
				minimumVisible -= dv;
				maximumVisible -= dv;
				value -= dv;
				repaint();
				System.out.println(value);
			}
		} else if (button2) {
			int x = e.getX();
			int x0 = getXCoordinate(value);
			if (Math.abs(x - x0) > 2) {
				int rx = dragRefPoint.x;
				double rv = getValueForPixel(rx);
				double newIncr = Math.abs((rv - value) / (double) (x - x0));
				int w = getWidth();
				minimumVisible = value - newIncr * (double) x0;
				maximumVisible = value + newIncr * (double) (w - 1 - x0);
				repaint();
			}
		}

		dragRefPoint = e.getPoint();
	}
 
	private void onMouseReleased(MouseEvent e) {
		System.out.println("onMouseReleased");
		dragRefPoint = null;
	}

	private void onMousePressed(MouseEvent e) {
		dragRefPoint = e.getPoint();
	}

	private void onMouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			value = getValueForPixel(e.getX());
			repaint();
		}
	}

	/* (fnon-Javadoc)
	 * @see javax.swing.JComponent#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		paintSlider(g);
	}
	
	protected void paintSlider(Graphics g) {
		paintBackground(g);
		paintTicks(g);
		paintZeroMark(g);
		paintMark(g);
		
		g.setColor(Color.BLACK);

		double pi = getPixelIncrement();
		double v = value;
		int digits = 1 - (int) Math.ceil(Math.log10(3.0 * pi));
		if (digits < 0) {
			double roundTo = Math.pow(10.0, -digits);
			v = roundTo * Math.round(v / roundTo);
			digits = 0;
		}
		String fmt = String.format("%%.%df", digits);
		String label = String.format(fmt, v);
//		String label = Double.toString(value);
		Rectangle2D rect = g.getFontMetrics().getStringBounds(label, g);

		double w = rect.getWidth();
		double h = rect.getHeight();
		double w0 = (double) getWidth();
		double h0 = (double) getHeight();
		g.drawString(label, (int) Math.floor(w0 - w) - 2, (int) Math.round((h0 + h) / 2.0));
		
		
	}
	
	private void paintBackground(Graphics g) {
		Dimension d = getSize();
		g.setColor(backgroundColor);
		g.fillRect(0, 0, d.width - 1, d.height - 1);
		g.setColor(borderColor);
		g.drawRect(0, 0, d.width - 1, d.height - 1);
	}
	
	private void paintMark(Graphics g) {
		int x = getXCoordinate(value);
		int h = getHeight();
		g.setColor(markColor);
		g.drawLine(x, 0, x, h - 1);
	}
	
	private void paintZeroMark(Graphics g) {
		if (minimumVisible <= 0.0 && 0.0 <= maximumVisible) {
			int x = getXCoordinate(0.0);
			int h = getHeight();
			g.setColor(zeroMarkColor);
			g.drawLine(x, 0, x, h - 1);			
		}
	}
	
	private void paintTicks(Graphics g) {
		g.setColor(tickColor);
		
		double tickIncr = getMinorTickIncrement();
		Dimension d = getSize();
		for (int j = 1; j <= 2; j++) {
			double minTick = tickIncr * Math.ceil(minimumVisible / tickIncr);
			double maxTick = tickIncr * Math.floor(maximumVisible / tickIncr);
			int numTicks = (int) Math.round((maxTick - minTick) / tickIncr);
			for (int i = 0; i <= numTicks; i++) {
				double tickValue = minTick + (maxTick - minTick) * ((double) i / (double) numTicks);
				int x = getXCoordinate(tickValue);
				int y0 = 0;
				int y1 = 3 * j;
				g.drawLine(x, y0, x, y1);
				g.drawLine(x, d.height - 1 - y1, x, d.height - 1 - y0);			
			}
			tickIncr *= 10.0;
		}
	}
	
	private int getXCoordinate(double value) {
		int w = getWidth();
		return (int) Math.round(((double) (w - 1)) * (value - minimumVisible) / (maximumVisible - minimumVisible));
	}
	
	private double getValueForPixel(int x) {
		int w = getWidth();
		double t = (double) x / (double) (w - 1);
		return minimumVisible + t * (maximumVisible - minimumVisible);
	}
	
	protected double getPixelIncrement() {
		int w = getWidth();
		return (maximumVisible - minimumVisible) / (double) w;
	}
	
	protected double getMinorTickIncrement() {
		double pi = getPixelIncrement();
		return Math.pow(10, Math.ceil(Math.log10(3.0 * pi)));
	}
	
	protected double getMajorTickIncrement() {
		return 10.0 * getMinorTickIncrement();
	}
	
}
