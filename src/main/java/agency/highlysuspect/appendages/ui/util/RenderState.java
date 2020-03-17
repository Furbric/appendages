package agency.highlysuspect.appendages.ui.util;

import org.lwjgl.opengl.GL11;

import java.util.ArrayDeque;
import java.util.Deque;

//N.B. Assumes that the only "active" scissors are the ones in the stack.
//Completely disables scissoring when popping the last entry from the stack.
public class RenderState {
	private Deque<ScissorElement> scissorElements = new ArrayDeque<>();
	
	private Deque<TranslateElement> translateElements = new ArrayDeque<>();
	
	public void pushScissor(int left, int top, int width, int height) {
		if(scissorElements.isEmpty()) {
			GL11.glEnable(GL11.GL_SCISSOR_TEST);
			scissorElements.push(new ScissorElement(left, top, width, height).apply());
		} else {
			ScissorElement other = scissorElements.peek();
			scissorElements.push(new ScissorElement(left, top, width, height).cropInside(other).apply());
		}
	}
	
	public void popScissor() {
		scissorElements.pop();
		if(scissorElements.isEmpty()) GL11.glDisable(GL11.GL_SCISSOR_TEST);
		else scissorElements.getFirst().apply();
	}
	
	public void pushTranslate(int x, int y) {
		translateElements.push(new TranslateElement(x, y, translateElements.peek()).apply());
	}
	
	public void popTranslate() {
		translateElements.pop().unapply();
	}
	
	public static class ScissorElement {
		public ScissorElement(int left, int top, int width, int height) {
			this.left = left;
			this.top = top;
			this.width = width;
			this.height = height;
		}
		
		private int left, top, width, height;
		
		public ScissorElement apply() {
			GL11.glScissor(left, top, width, height);
			return this;
		}
		
		public ScissorElement cropInside(ScissorElement other) {
			if(this.left < other.left) {
				int difference = other.left - this.left;
				this.width -= difference;
				this.left += difference;
			}
			
			if(this.top < other.top) {
				int difference = other.top - this.top;
				this.height -= difference;
				this.top += difference;
			}
			
			if(this.left + this.width > other.left + other.width) {
				this.width -= (this.left + this.width) - (other.left + other.width);
			}
			
			if(this.top + this.height > other.top + other.height) {
				this.height += (this.top + this.height) - (other.top + other.height);
			}
			
			return this;
		}
	}
	
	public static class TranslateElement {
		public TranslateElement(float x, float y, TranslateElement previous) {
			this.x = x;
			this.y = y;
			
			if(previous == null) {
				absoluteX = x;
				absoluteY = y;
			} else {
				absoluteX = previous.absoluteX + x;
				absoluteY = previous.absoluteY + y;
			}
		}
		
		private final float x, y;
		private final float absoluteX, absoluteY;
		
		public TranslateElement apply() {
			GL11.glTranslatef(x, y, 0);
			return this;
		}
		
		public TranslateElement unapply() {
			GL11.glTranslatef(-x, -y, 0);
			return this;
		}
	}
}
