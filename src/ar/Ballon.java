package ar;

import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.ColorCube;

public class Ballon {
	private TransformGroup TGObjet;
	public Ballon() {
		// TODO Auto-generated constructor stub
		dessiner();
	}

	public TransformGroup dessiner() {
		TGObjet = new TransformGroup();
		Transform3D TR3D = new Transform3D();
		TR3D.setTranslation(new Vector3f(-0.0f, 0.0f, -1.0f));
		TGObjet.setTransform(TR3D);
		//TGObjet.setName("TGterrain");
		TGObjet.addChild(new ColorCube(0.9));
		return TGObjet;
	}
	public TransformGroup getTG()
	{
		
		return TGObjet;
	}

}
