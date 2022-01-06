package game;

import javax.media.j3d.Appearance;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.Cylinder;

public class Piquet {
	private Vector3f position;

	public Piquet(Vector3f position) {
		super();
		this.position = position;

	}

	public TransformGroup generatepiquet() {
		Cylinder cylindre = new Cylinder(0.01f, 0.1f, 0, null);
		Appearance ap = new Appearance();
		/********************************************************************/
		Transform3D rotation = new Transform3D();
		rotation.rotX(Math.PI / 2);
		Transform3D transform = new Transform3D();
		transform.setTranslation(this.position);
		transform.mul(rotation);
		TransformGroup transformGroup = new TransformGroup(transform);
		transformGroup.addChild(cylindre);
		/********************************************/
		Color3f col = new Color3f(1f, 1f, 0f);
		ColoringAttributes ca = new ColoringAttributes(col, ColoringAttributes.NICEST);
		ap.setColoringAttributes(ca);
		/**********************************************************/
		cylindre.setAppearance(ap);
		return transformGroup;

	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

}
