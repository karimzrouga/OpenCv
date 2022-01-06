package ar;

import javax.media.j3d.Alpha;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.PositionPathInterpolator;
import javax.media.j3d.Transform3D;
import javax.vecmath.Point3f;

public class MarkerModelAnimer extends MarkerModel {
	
	public MarkerModelAnimer(String markerFnm, String modelFnm, double scale,
			boolean hasCoords) {
		super(markerFnm, modelFnm, scale, hasCoords);
		animer();
	}

	public void animer() {
		Transform3D trans=new Transform3D();
		Alpha transAlpha=new Alpha(-1,4000);
	
		Point3f[] chemin=new Point3f[3];
		chemin[0]=new Point3f(-0.5f,0.0f,2.0f);
		chemin[1]=new Point3f(0.0f,0.0f,2.0f);
		chemin[2]=new Point3f(0.5f,-0.0f,2.0f);
		
		float[] timePosition={0.0f,0.5f,1f};
		PositionPathInterpolator interpol=new PositionPathInterpolator(transAlpha,moveTg,trans,timePosition,chemin);
		
		BoundingSphere bounds=new BoundingSphere();
		interpol.setSchedulingBounds(bounds);
		moveTg.addChild( interpol);
	}
}
