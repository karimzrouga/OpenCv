package ar;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Group;
import javax.media.j3d.Node;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Switch;

public class MarkerModelDetail extends MarkerModel {
	private List<Shape3D> listeshape3D = new ArrayList<Shape3D>();
	private boolean[] etatshape3d;
	private BranchGroup scenegroupe = new BranchGroup();

	public MarkerModelDetail(String markerFnm, String modelFnm, double scale, boolean hasCoords) {
		super(markerFnm, modelFnm, scale, hasCoords);
		parcoursbranche(this.moveTg, 0);
		etatshape3d = new boolean[listeshape3D.size()];
		System.out.print(listeshape3D.size());
		for (int i = 0; i < listeshape3D.size(); i = i + 2)
			etatshape3d[i] = true;
		// visSwitch.setWhichChild(visSwitch.CHILD_ALL);
		scenegroupe = getPropMan().getSceneGroup();
		// for(int i=0;i<listeshape3D.size();i++)
		//dernierBG.removeAllChildren();
	}

	public void parcoursbranche(Group Noeud, int i) {
		Enumeration objets_child = Noeud.getAllChildren();
		while (objets_child.hasMoreElements()) {
			Object node = objets_child.nextElement();

			if (node instanceof Group) {
				Group Group3D = (Group) node;
				if (node instanceof BranchGroup) {
					scenegroupe = (BranchGroup) node;
				}
				parcoursbranche(Group3D, ++i);
			} else if (node instanceof Shape3D) {
				Shape3D objet3D = (Shape3D) node;
				listeshape3D.add(objet3D);
			}
		}
	}

	public int getNbshape3D() {
		return listeshape3D.size();
	}

	public void modifierModele(MarkerModelDetail modele1) {
		scenegroupe = getPropMan().getSceneGroup();
		// for(int i=0;i<listeshape3D.size();i++)
		this.scenegroupe.removeAllChildren();
		Enumeration <Node>objets_child = modele1.scenegroupe.getAllChildren();
		while (objets_child.hasMoreElements()) {
			Node node = objets_child.nextElement();
			modele1.scenegroupe.removeChild(node);
			this.scenegroupe.addChild(node);
		}
	}

	/*
	 * int nbShape3dModele1=modele1.getNbshape3D(); int
	 * nbShape3dModele2=modele2.getNbshape3D(); List<Shape3D> listeshape3DSave =
	 * new ArrayList<Shape3D>(); copyshape3d(modele1,listeshape3DSave);
	 * modele1.listeshape3D=modele2.listeshape3D;
	 * modele2.listeshape3D=listeshape3DSave;
	 */

	//
	private void copyshape3d(MarkerModelDetail modele1, List<Shape3D> listeshape3DSave) {
		for (int i = 0; i < modele1.getNbshape3D(); i++) {
			listeshape3DSave.add(modele1.listeshape3D.get(i));
		}
	}
}
