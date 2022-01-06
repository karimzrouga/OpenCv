package game;

import java.util.ArrayList;

import javax.media.j3d.Appearance;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.ColorCube;

public class terrain {
	private ArrayList<Vector3f> l = new ArrayList<Vector3f>();
	private TransformGroup TGObjet;

	public terrain() {
		// TODO Auto-generated constructor stub
		dessiner();
	}

	public TransformGroup dessiner() {
		TGObjet = new TransformGroup();
		Transform3D TR3D = new Transform3D();
		TR3D.setTranslation(new Vector3f(-0.0f, 0.0f, 3.0f));
		TGObjet.setTransform(TR3D);
		Appearance ap = new Appearance();
		Color3f col = new Color3f(0.6f, 0.6f, 0.6f);
		ColoringAttributes ca = new ColoringAttributes(col, ColoringAttributes.NICEST);
		Box plaqutte = new Box(0f,1.0f, 0, ap);
		positionpiquet();
		TGObjet.addChild(plaqutte);
		/********************************************************/
		for (int i = 0; i < this.l.size(); i++) {
			Piquet piquet = new Piquet(l.get(i));
			TGObjet.addChild(piquet.generatepiquet());
		}

		Roue roue = new Roue(new Vector3f(0.1f, 0, -2f));
		// Roue roue = new Roue(new Vector3f(0.1f, 0, -2f));
		TGObjet.addChild(roue.generateroue());
		// TGObjet.addChild(new Piquet(new Vector3f(-0.0f, 0.0f,
		// -2.5f)).generatepiquet());
		return TGObjet;
	}

	public void positionpiquet() {
		l = new ArrayList<Vector3f>();

		int index = 0;
		float x = -0.2f, z = -2f, y = -0.15f;
		int i = 0;
		while (index < 12) {

			if (i == 3) {
				i = 0;
				x = -0.2f;
				y += 0.1;
			}
			l.add(new Vector3f(x, y, z));
			x += 0.2;

			i++;
			index++;
			System.out.println("piquetpos " + new Vector3f(x, y, z));
		}

	}

	public TransformGroup getTG() {

		return TGObjet;
	}
}
