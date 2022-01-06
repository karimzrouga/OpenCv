package simple_ar;
// BoxNyAR.java
// Andrew Davison, ad@fivedots.coe.psu.ac.th, July 2013

/*  An Java3D NyARToolkit example using the built-in colored box,
    that reports position and rotation information.
    NYARToolkit is available at:
           http://nyatla.jp/nyartoolkit/wiki/index.php?NyARToolkit%20for%20Java.en

    --------------------
    Usage:
      > compile *.java
      > run BoxNyAR
*/


import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

import javax.media.j3d.*;

import ar.Ballon;
import ar.PropManager;

import com.sun.j3d.utils.universe.*;
import com.sun.j3d.utils.geometry.*;  // ColorCube
import javax.vecmath.*;

import org.opencv.core.Core;



import jp.nyatla.nyartoolkit.core.*;
import jp.nyatla.nyartoolkit.java3d.utils.*;



public class BoxNyAR extends JFrame
{
  private final String PARAMS_FNM = "Data/camera_para.dat";

  private static final int WIDTH = 640;   // size of window
  private static final int HEIGHT = 480; 

  private static final int BOUNDSIZE = 100;  // larger than world

  private J3dNyARParam cameraParams;

  private NyARBehavior nyaBeh;

  static {
  	  System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
  public BoxNyAR()
  {
    super("Box NyARToolkit Example");

    // Preload the opencv_objdetect module to work around a known bug.
    //Loader.load(opencv_objdetect.class);

    cameraParams = readCameraParams(PARAMS_FNM);

    Container cp = getContentPane();

    // create a JPanel in the center of JFrame
    JPanel p = new JPanel();
    p.setLayout( new BorderLayout() );
    p.setPreferredSize( new Dimension(WIDTH, HEIGHT) );
    cp.add(p, BorderLayout.CENTER);

    // put the 3D canvas inside the JPanel
    p.add(createCanvas3D(), BorderLayout.CENTER);

    // configure the JFrame
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e)
      { nyaBeh.stop();
        System.exit(0);
      }
    });

    setResizable(false);
    pack();  
    setLocationRelativeTo(null);
    setVisible(true);
  }  // end of BoxNyAR()



  private J3dNyARParam readCameraParams(String fnm)
  {
    J3dNyARParam cameraParams = null;  
    try {
      cameraParams = J3dNyARParam.loadARParamFile( new FileInputStream(fnm));
      cameraParams.changeScreenSize(WIDTH, HEIGHT);
    }
    catch(Exception e)
    {  System.out.println("Could not read camera parameters from " + fnm);
       System.exit(1);
    }
    return cameraParams;
  }  // end of readCameraParams()



  private Canvas3D createCanvas3D()
  /* Build a 3D canvas for a Universe which contains
     the 3D scene and view 
            univ --> locale --> scene BG
                          |
                           ---> view BG  --> Canvas3D
                              (set up using camera cameraParams)
   */
  { 
    Locale locale = new Locale( new VirtualUniverse() );
    locale.addBranchGraph( createSceneGraph() );   // add the scene

    // get the preferred graphics configuration for the default screen
    GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();

    Canvas3D c3d = new Canvas3D(config);
    locale.addBranchGraph( createView(c3d) );  // add view branch

    return c3d;
  }  // end of createCanvas3D()



  private BranchGroup createSceneGraph()
  /* The scene graph:
         sceneBG 
               ---> bg
               |
               -----> tg --> boxTG ---> cube  
               |
               ---> behavior  (controls the bg and the tg)
  */
  { 
    BranchGroup sceneBG = new BranchGroup();  
    TransformGroup modelTG = loadModel("terrain/terrain.obj",0.15, false);
    sceneBG.addChild(modelTG); 
    Background bg = makeBackground();
    sceneBG.addChild(bg);             // add background
    
    TransformGroup tg = makeCube(sceneBG);

    nyaBeh = new NyARBehavior(cameraParams, tg, bg);
    sceneBG.addChild(nyaBeh);

    sceneBG.compile();       // optimize the sceneBG graph
    return sceneBG;
  }  // end of createSceneGraph()



  private Background makeBackground()
  // the background will be the current image captured by the camera
  { 
    Background bg = new Background();
    BoundingSphere bounds = new BoundingSphere();
    bounds.setRadius(10.0);
    bg.setApplicationBounds(bounds);
    bg.setImageScaleMode(Background.SCALE_FIT_ALL);
    bg.setCapability(Background.ALLOW_IMAGE_WRITE);  // so can change image

    return bg;
  }  // end of makeBackground()



  private TransformGroup makeCube(BranchGroup sceneBG)
  {
    TransformGroup tg = new TransformGroup();
   
    //"patt.hiro", "robot.3ds", 
    tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

    Transform3D boxTrans = new Transform3D();
    boxTrans.setTranslation(new Vector3d(0.00, 0.0, 20*0.001));

    TransformGroup boxTG = new TransformGroup();
    boxTG.setTransform(boxTrans);
    boxTG.addChild( new ColorCube(20 * 0.001));

    tg.addChild(new Ballon().getTG());
    sceneBG.addChild(tg);
    return tg;
  }  // end of makeCube()

//
  private TransformGroup loadModel(String modelFnm, double scale, boolean hasCoords)
  // load the model, rotating and scaling it
  {
    PropManager propMan = new PropManager(modelFnm, hasCoords);  

    // get the TG for the prop (model)
    TransformGroup propTG = propMan.getTG();

    // rotate and scale the prop
    Transform3D modelT3d = new Transform3D();
    modelT3d.rotX( Math.PI/2.0 );    
         // the prop lies flat on the marker; rotate forwards 90 degrees so it is standing
    Vector3d scaleVec = calcScaleFactor(propTG, scale);   // scale the prop
    modelT3d.setScale( scaleVec );

    TransformGroup modelTG = new TransformGroup(modelT3d);
    modelTG.addChild(propTG);

    return modelTG;
  }  
  
  private Vector3d calcScaleFactor(TransformGroup modelTG, double scale)
  // Scale the prop based on its original bounding box size
  {
     BoundingBox boundbox = new BoundingBox( modelTG.getBounds() );
     System.out.println(boundbox);

     // obtain the upper and lower coordinates of the box
     Point3d lower = new Point3d();
     boundbox.getLower( lower );
     Point3d upper = new Point3d();
     boundbox.getUpper( upper );

     // store the largest X, Y, or Z dimension and calculate a scale factor
     double max = 0.0;
     if( Math.abs(upper.x - lower.x) > max)
       max = Math.abs(upper.x - lower.x);

     if( Math.abs(upper.y - lower.y) > max)
       max = Math.abs(upper.y - lower.y);

     if( Math.abs(upper.z - lower.z) > max)
       max = Math.abs(upper.z - lower.z);

     double scaleFactor = scale/max;
     System.out.printf("max dimension: %.3f;  scale factor: %.3f\n", max, scaleFactor);

     // limit the scaling so that a big model isn't scaled too much
     if( scaleFactor < 0.0005 )
         scaleFactor = 0.0005;

     return new Vector3d(scaleFactor, scaleFactor, scaleFactor);
  }  // end of calcScaleFactor()

// e
  //

  private BranchGroup createView(Canvas3D c3d)
  // create a view graph using the camera parameters
  {
    View view = new View();
    ViewPlatform viewPlatform = new ViewPlatform();
    view.attachViewPlatform(viewPlatform);
    view.addCanvas3D(c3d);

    view.setPhysicalBody(new PhysicalBody());
    view.setPhysicalEnvironment(new PhysicalEnvironment());

    view.setCompatibilityModeEnable(true);
    view.setProjectionPolicy(View.PERSPECTIVE_PROJECTION);
    view.setLeftProjection( cameraParams.getCameraTransform() );   // camera projection

    TransformGroup viewGroup = new TransformGroup();
    Transform3D viewTransform = new Transform3D();
    viewTransform.rotY(Math.PI);   // rotate 180 degrees
    viewTransform.setTranslation(new Vector3d(0.0, 0.0, 0.0));   // start at origin
    viewGroup.setTransform(viewTransform);
    viewGroup.addChild(viewPlatform);

    BranchGroup viewBG = new BranchGroup();
    viewBG.addChild(viewGroup);

    return viewBG;
  }  // end of createView()



  // ------------------------------------------------------------

  public static void main(String args[])
  {  new BoxNyAR();  }
    
    
} // end of BoxNyAR class
