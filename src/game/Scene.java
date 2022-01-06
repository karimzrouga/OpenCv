package game;

import java.util.ArrayList;
import java.util.Random;

import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.universe.SimpleUniverse;

public class Scene {
	private SimpleUniverse universe;
	private BranchGroup group;
	private ArrayList<Vector3f> l;
	TransformGroup worldgroup;

	public Scene() {
		super();
		this.universe = new SimpleUniverse();
		this.group = new BranchGroup();
		TransformGroup worldgroup = new TransformGroup();
		group.addChild(worldgroup);
		/***********************************************/
		Appearance ap = new Appearance();
		Color3f col = new Color3f(0.6f, 0.6f, 0.6f);
		ColoringAttributes ca = new ColoringAttributes(col, ColoringAttributes.NICEST);
		Box plaqutte = new Box(0.5f, 0.3f, 0, ap);
		positionpiquet();
		/********************************************/
		for (int i = 0; i < this.l.size(); i++) {
			Piquet piquet = new Piquet(l.get(i));
			worldgroup.addChild(piquet.generatepiquet());
		}
		Roue roue = new Roue(new Vector3f(0.1f, 0.2f, 0.3f));
		worldgroup.addChild(roue.generateroue());
		// group.addChild(piquet.generatepiquet());
		//

		worldgroup.addChild(plaqutte);

		/*******************************************/
	//	Transform3D T = new Transform3D();
		// T.rotX((Math.PI/2));
	//	worldgroup.setTransform(T);
		/*******************************************/
	//	universe.getViewingPlatform().setNominalViewingTransform();
		//universe.addBranchGraph(group);
	//	universe.getViewingPlatform();
	//	System.out.println("end");

	}

	public TransformGroup getTG() {
		return worldgroup;
	}

	public void positionpiquet() {
		l = new ArrayList<Vector3f>();

		int index = 0;
		float x = -0.25f, z = -0.1f;
		int i = 0;
		while (index < 12) {

			if (i == 3) {
				i = 0;
				x = -0.25f;
				z += 0.1;
			}
			l.add(new Vector3f(x, 0.1f, z));
			x += 0.2;

			i++;
			index++;
			System.out.println(new Vector3f(x, -0.2f, z));
		}

	}

}
