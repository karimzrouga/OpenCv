package game;

import javax.media.j3d.Appearance;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.Sphere;

public class Roue {
	private Vector3f position;

	public Roue(Vector3f position) {
		super();
		this.position = position;

	}

	public TransformGroup generateroue() {
		Sphere s = new Sphere(0.02f, 1, 50);
		Appearance ap = new Appearance();
		TransformGroup tg = new TransformGroup();
		Transform3D T = new Transform3D();
		T.setTranslation(this.position);
		tg.addChild(s);
		tg.setTransform(T);
		/********************************************/
		Color3f col = new Color3f(0.0f, 0.0f, 1.0f);
		ColoringAttributes ca = new ColoringAttributes(col, ColoringAttributes.NICEST);
		ap.setColoringAttributes(ca);
		/**********************************************************/
		s.setAppearance(ap);
		return tg;

	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

}
