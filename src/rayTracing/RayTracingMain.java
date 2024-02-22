package rayTracing;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;

import lSystem.LSystem;
import lSystem.Letter;
import lSystem.Rule;
import math.Ray;
import math.Vector3D;
import perlinNoise.Noise;
import perlinNoise.Vector2D;
import rayTracing.lights.DirectionalLight;
import rayTracing.lights.LightSource;
import rayTracing.lights.PointLight;
import rayTracing.primitives.BoundingBox;
import rayTracing.primitives.Parallelogram;
import rayTracing.primitives.Primitive;
import rayTracing.primitives.Sphere;
import rayTracing.primitives.Triangle;
import rayTracing.primitives.WindowWall;

public class RayTracingMain {
	
	private static final int WIDTH = 700;
	private static final int HEIGHT = 700;
	
	private static final ArrayList<LightSource> LIGHTS = new ArrayList<LightSource>();
	private static Camera CAMERA;

	private static final Letter[] LSYSTEM_AXIOM = {Letter.BLDG, Letter.MOVE};
	private static final Rule[] LSYSTEM_RULES;
	static {
		// Sets up the BLDG rule
		final Rule bldgRule = new Rule(Letter.BLDG);
		bldgRule.addTransform(new Letter[] {Letter.BLDG, Letter.BLDG});
		bldgRule.addTransform(new Letter[] {Letter.BLDG, Letter.BLDG});
		bldgRule.addTransform(new Letter[] {Letter.BLDG});
		bldgRule.addTransform(new Letter[] {Letter.BLDG});
		bldgRule.addTransform(new Letter[] {});
		
		// Sets up the BLDG rule
		final Rule moveRule = new Rule(Letter.MOVE);
		moveRule.addTransform(new Letter[] {Letter.BLDG, Letter.MOVE});
		moveRule.addTransform(new Letter[] {Letter.BLDG, Letter.MOVE});
		moveRule.addTransform(new Letter[] {Letter.MOVE, Letter.MOVE});
		moveRule.addTransform(new Letter[] {Letter.MOVE});
		moveRule.addTransform(new Letter[] {Letter.MOVE});
		
		LSYSTEM_RULES = new Rule[] {bldgRule, moveRule};
	}
	
	private static final Vector3D BACKGROUND_COLOR = new Vector3D(0.54, 0.78, 0.99); // Sky Blue
	private static final Vector3D AMBIENT_LIGHT = new Vector3D(0.1);

	private static final BoundingBox BOX = new BoundingBox();
	private static final BoundingBox GRASS_BOX = new BoundingBox();
	private static final BoundingBox BLDG_BOX = new BoundingBox();

	private static final double BLOCK_SIZE = 1;
	private static final double GRASS_BLOCK_SIZE = 16;
	private static final double GRASS_STEP = BLOCK_SIZE/GRASS_BLOCK_SIZE;

	private static final Material GRASS = new Material(
		new Vector3D(0, 1, 0), // Ambient
		new Vector3D(0, 1, 0), // Diffuse
		new Vector3D(0),       // Specular
		20
	);
	private static final Material ROAD = new Material(
		new Vector3D(0.5), // Ambient
		new Vector3D(0.5), // Diffuse
		new Vector3D(0),       // Specular
		20
	);
	private static final Material[] WALL_TEXTURES = {
		new Material(
			new Vector3D(0, 0, 0.2), // Ambient
			new Vector3D(0, 0, 0.2), // Diffuse
			new Vector3D(0),         // Specular
			20
		),
		new Material(
			new Vector3D(0.95, 0.80, 0.45), // Ambient
			new Vector3D(0.95, 0.80, 0.45), // Diffuse
			new Vector3D(0),                // Specular
			20
		),
		new Material(
			new Vector3D(0.8), // Ambient
			new Vector3D(0.8), // Diffuse
			new Vector3D(0),   // Specular
			20
		)
	};
	
	private static void setupMirrorRoom() {
		
		// Creates the camera
		CAMERA = new Camera(
			new Vector3D(4, 3, 4).scale(1),
			new Vector3D(-1, -0.8, -1).scale(1),
			Vector3D.UP.scale(1)
		);
		
		// Creates the scene nodes
		BOX.add(new Sphere(1, new Vector3D(0, 0, 0), new Material(
			new Vector3D(1, 0, 0), // Ambient
			new Vector3D(1, 0, 0), // Diffuse
			new Vector3D(0),       // Specular
			20
		)));
		
		final Material mirror = new Material(
			new Vector3D(1), // Ambient
			new Vector3D(1), // Diffuse
			new Vector3D(1), // Specular
			20
		);

		BOX.add(new Triangle(
			new Vector3D(-5, -5, -5),
			new Vector3D(-5, -5, 5),
			new Vector3D(5, -5, -5),
			mirror
		));
		BOX.add(new Triangle(
			new Vector3D(5, -5, 5),
			new Vector3D(5, -5, -5),
			new Vector3D(-5, -5, 5),
			mirror
		));
		BOX.add(new Triangle(
			new Vector3D(5, 5, 5),
			new Vector3D(-5, 5, 5),
			new Vector3D(5, 5, -5),
			mirror
		));
		BOX.add(new Triangle(
			new Vector3D(-5, 5, -5),
			new Vector3D(5, 5, -5),
			new Vector3D(-5, 5, 5),
			mirror
		));

		BOX.add(new Triangle(
			new Vector3D(-5, -5, -5),
			new Vector3D(-5, 5, -5),
			new Vector3D(-5, -5, 5),
			mirror
		));
		BOX.add(new Triangle(
			new Vector3D(-5, 5, 5),
			new Vector3D(-5, -5, 5),
			new Vector3D(-5, 5, -5),
			mirror
		));
		BOX.add(new Triangle(
			new Vector3D(5, 5, 5),
			new Vector3D(5, 5, -5),
			new Vector3D(5, -5, 5),
			mirror
		));
		BOX.add(new Triangle(
			new Vector3D(5, -5, -5),
			new Vector3D(5, -5, 5),
			new Vector3D(5, 5, -5),
			mirror
		));

		BOX.add(new Triangle(
			new Vector3D(-5, -5, -5),
			new Vector3D(5, -5, -5),
			new Vector3D(-5, 5, -5),
			mirror
		));
		BOX.add(new Triangle(
			new Vector3D(5, 5, -5),
			new Vector3D(-5, 5, -5),
			new Vector3D(5, -5, -5),
			mirror
		));
		BOX.add(new Triangle(
			new Vector3D(5, 5, 5),
			new Vector3D(5, -5, 5),
			new Vector3D(-5, 5, 5),
			mirror
		));
		BOX.add(new Triangle(
			new Vector3D(-5, -5, 5),
			new Vector3D(-5, 5, 5),
			new Vector3D(5, -5, 5),
			mirror
		));
				
		// Creates the scene lights
		LIGHTS.add(new PointLight(new Vector3D(4, 3, 4), new Vector3D(1), 0.25));
	}
	
	private static double grassHeight(double x, double y) {
		return 0.4*Noise.noise(new Vector2D(x, y), 100);
	}
	
	private static void makeBlock(int xMin, int zMin, int bldgs) {
		
		// Prepares the grass bounding boxes
		final BoundingBox grassBox = new BoundingBox();
		final BoundingBox[][] subBoxes = new BoundingBox[4][4]; // Depends on GRASS_BLOCK_SIZE
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
				subBoxes[i][j] = new BoundingBox();
		
		// Creates the grass triangle mesh
		for (int xStep = 0; xStep < GRASS_BLOCK_SIZE; xStep++) {
			for (int zStep = 0; zStep < GRASS_BLOCK_SIZE; zStep++) {
				
				// Stores the coordinates
				final double x0 = xMin*BLOCK_SIZE + GRASS_STEP* xStep;
				final double x1 = xMin*BLOCK_SIZE + GRASS_STEP*(xStep+1);
				final double z0 = zMin*BLOCK_SIZE + GRASS_STEP* zStep;
				final double z1 = zMin*BLOCK_SIZE + GRASS_STEP*(zStep+1);
				
				// Computes the proper material
				final Material material = (Math.abs(xStep-GRASS_BLOCK_SIZE/2.0) < 7
										&& Math.abs(zStep-GRASS_BLOCK_SIZE/2.0) < 7)
										? GRASS : ROAD;

				// Creates the grass triangles at this step
				subBoxes[xStep/4][zStep/4].add(new Triangle(
					new Vector3D(x0, grassHeight(x0, z0), z0),
					new Vector3D(x0, grassHeight(x0, z1), z1),
					new Vector3D(x1, grassHeight(x1, z0), z0),
					material
				));
				subBoxes[xStep/4][zStep/4].add(new Triangle(
					new Vector3D(x1, grassHeight(x1, z1), z1),
					new Vector3D(x1, grassHeight(x1, z0), z0),
					new Vector3D(x0, grassHeight(x0, z1), z1),
					material
				));
			}
		}

		// Stores the grass bounding boxes
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
				grassBox.add(subBoxes[i][j]);
		GRASS_BOX.add(grassBox);
		
		if (bldgs > 0) {
			// Prepares the building bounding box
			final BoundingBox bldgBox = new BoundingBox();
			
			// Creates the buildings
			for (int x = 0; x < bldgs; x++) {
				for (int z = 0; z < bldgs; z++) {
					
					// Stores distance values
					final double size = 11*GRASS_STEP;
					final double step = size/bldgs;
					
					// Stores position/distance values
					final Vector3D center = new Vector3D(xMin + 3*GRASS_STEP + step*x, -0.3, zMin + 3*GRASS_STEP + step*z);
					final Vector3D right = new Vector3D(step-GRASS_STEP, 0, 0);
					final Vector3D back = new Vector3D(0, 0, step-GRASS_STEP);
					final Vector3D up = new Vector3D(0, 1.5+Math.random()/2, 0);
					
					// Creates the building
					bldgBox.add(makeBldg(
						center,
						center.add(right),
						center.add(back),
						center.add(up),
						2+(int)(3*Math.random()),
						4+(int)(3*Math.random())
					));
				}
			}
			BLDG_BOX.add(bldgBox);
		}
	}
	
	private static BoundingBox makeBldg(Vector3D centerCorner, Vector3D rightCorner, Vector3D backCorner, Vector3D topCorner, int windowsH, int windowsV) {
		
		// Creates the wall material
		final Material wall = WALL_TEXTURES[(int)(WALL_TEXTURES.length*Math.random())];
		
		// Stores the directions
		final Vector3D right = rightCorner.subtract(centerCorner);
		final Vector3D back = backCorner.subtract(centerCorner);
		final Vector3D up = topCorner.subtract(centerCorner);
		
		final BoundingBox bldgBox = new BoundingBox();
		bldgBox.add(new WindowWall(
			centerCorner,
			rightCorner,
			topCorner,
			wall,
			windowsH, windowsV
		));
		bldgBox.add(new WindowWall(
			backCorner,
			centerCorner,
			backCorner.add(up),
			wall,
			windowsH, windowsV
		));
		bldgBox.add(new WindowWall(
			rightCorner,
			rightCorner.add(back),
			rightCorner.add(up),
			wall,
			windowsH, windowsV
		));
		bldgBox.add(new WindowWall(
			rightCorner.add(back),
			backCorner,
			rightCorner.add(back).add(up),
			wall,
			windowsH, windowsV
		));
		bldgBox.add(new Parallelogram(
			topCorner,
			topCorner.add(right),
			topCorner.add(back),
			wall
		));
		
		return bldgBox;
	}
	
	private static void model() {
		
		// Sets up the building count L-System
		final LSystem lSystem = new LSystem(LSYSTEM_AXIOM, LSYSTEM_RULES);
		lSystem.steps(10);
		
		// Checks how many moves there are
		int moveCount = 0;
		for (Letter letter : lSystem.getWord())
			if (letter == Letter.MOVE)
				moveCount++;
		
		// Stores the bldg counts
		final int[] bldgs = new int[moveCount];
		int index = 0;
		for (Letter letter : lSystem.getWord()) {
			if (letter == Letter.MOVE) {
				index++;
				continue;
			}
			bldgs[index]++;
		}

		// Sets up the blocks
		final int size = 5;
		for (int x = 0; x < size; x++)
			for (int z = 0; z < size; z++)
				makeBlock(x, z, Math.min(bldgs[(z+x*size) % bldgs.length], 2)); // Limits # bldgs to 2
		BOX.add(GRASS_BOX);
		BOX.add(BLDG_BOX);
		System.out.println("Modeling finished!");
	}
	
	private static void render(String filename) throws Exception {
		
		// Computes the image individual pixel values
		double totalTime = 0;
		final BufferedImage img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < WIDTH;) {
			final long sTime = System.currentTimeMillis();
			for (int y = 0; y < HEIGHT; y++) {
				
				// Runs 3x3 anti-aliasing averaging filter
				Vector3D color = Vector3D.ZERO;
				for (double dx = -0.3; dx <= 0.4; dx += 0.3) {
					for (double dy = -0.3; dy <= 0.4; dy += 0.3) {
						
						// Transforms the coordinates, with anti-aliasing
						final double transX = (x-WIDTH/2.0+dx)/(WIDTH/2);
						final double transY = (y-HEIGHT/2.0+dy)/(HEIGHT/2);
						
						// Computes the ray
						final Vector3D rayDirection = CAMERA.direction
												.add(CAMERA.right.scale(transX))
												.add(CAMERA.up.scale(transY))
												.normalize();
						final Ray ray = new Ray(CAMERA.origin, rayDirection);
						
						// Computes the color
						color = color.add(traceRay(ray, 4));
					}
				}
				color = color.scale(1/9.0);
				
				// Computes and stores the final color values
				final int red = Math.min(255, (int)(255*color.x)) << 16;
				final int green = Math.min(255, (int)(255*color.y)) << 8;
				final int blue = Math.min(255, (int)(255*color.z));
				
				img.setRGB(x, y, 0xff000000 + red+green+blue);
			}
			final double dt = (System.currentTimeMillis()-sTime)*1e-3;
			totalTime += dt;
			x++;
			System.out.println("Rendering: " + new DecimalFormat("0.000").format(100.0*x/WIDTH) + "%...");
			System.out.println("Estimated Time Left: " + new DecimalFormat("0.000").format(totalTime*(WIDTH-x)/x) + "s");
			System.out.println("Step Time Elapsed: " + new DecimalFormat("0.000").format(dt) + "s");
			System.out.println("Total Time Elapsed: " + new DecimalFormat("0.000").format(totalTime) + "s");
			System.out.println();
		}
		ImageIO.write(img, "png", new File(filename));
		System.out.println("Finished!");
	}
	
	public static void main(String[] args) throws Exception {
		
		// Sets up the light
		LIGHTS.add(new DirectionalLight(new Vector3D(-1, -5, 1), new Vector3D(0.3)));

		for (int i = 4; i < 5; i++) {
			model();
			
			// Renders the flying perspective
			CAMERA = new Camera(
				new Vector3D(-1, 3, -1),
				new Vector3D(1, -0.8, 1),
				new Vector3D(-0.8, -1, -0.8)
			);
			render("final-renders/flying" + i + ".png");
			
			// Renders the walking perspective
			CAMERA = new Camera(
				new Vector3D(2, 0.3, 1.5),
				new Vector3D(0, 0, 1),
				new Vector3D(0, -1, 0)
			);
			render("final-renders/walking" + i + ".png");
			
			// Resets the scene
			BOX.reset();
		}
	}
	
	private static Vector3D traceRay(Ray ray, int bounces) {
		
		// Checks if even in bounding box
		final ArrayList<Primitive> nodes = BOX.intersectingNodes(ray, 0);
		
		// Checks for an intersection with each primitive
		final Record record = new Record();
		for (Primitive node : nodes)
			node.intersects(ray, 0, record);
		
		// Checks if an intersection happened
		if (record.missed())
			return BACKGROUND_COLOR;
		
		// Stores relevant values
		final Vector3D intersectionPoint = ray.at(record.getT());
		final Material material = record.getMaterial();
		final Vector3D normal = record.getNormal();
		
		// Computes the diffuse & specular color for each light
		final Vector3D eyeReflection = ray.direction.add(normal.scale(-2*normal.dot(ray.direction)));
		Vector3D diffuseColor = Vector3D.ZERO;
		Vector3D specularColor = Vector3D.ZERO;
		for (LightSource light : LIGHTS) {
			
			// Computes relevant values from the light
			final double distanceToLight = light.distanceFrom(intersectionPoint);
			final Vector3D lightDirection = light.directionFrom(intersectionPoint);
			final Vector3D intensity = light.intensity(intersectionPoint);
			
			// Checks for shadows
			final Record shadowRecord = new Record(distanceToLight);
			final Ray rayToLight = new Ray(intersectionPoint.add(normal.scale(1e-3)), lightDirection);
			final ArrayList<Primitive> shadowNodes = BOX.intersectingNodes(rayToLight, 0);
			for (Primitive node : shadowNodes)
				if (node.intersects(rayToLight, 0, shadowRecord))
					break;
			
			// Skips lighting for this light if the node is in under a shadow
			if (shadowRecord.getT() < distanceToLight)
				continue;
			
			// Computes the diffuse color
			final double clampDiffuse = Math.max(0, lightDirection.dot(normal));
			final Vector3D diffuseIntensity = intensity.multiply(material.diffuseColor).scale(clampDiffuse);
			diffuseColor = diffuseColor.add(diffuseIntensity);
			
			// Computes the specular color
			final double clampSpecular = Math.pow(Math.max(0, lightDirection.dot(eyeReflection)), material.shininess);
			final Vector3D specularIntensity = intensity.multiply(material.specularColor).scale(clampSpecular);
			specularColor = specularColor.add(specularIntensity);
		}
		
		// Computes the ambient color
		final Vector3D ambientColor = AMBIENT_LIGHT.multiply(material.ambientColor);
		
		// Computes the direct color
		Vector3D totalColor = diffuseColor.add(specularColor).add(ambientColor);
		
		// Computes the indirect color, if applicable
		if (bounces > 0 && !material.specularColor.equals(Vector3D.ZERO)) {
			final Ray reflectRay = new Ray(intersectionPoint.add(normal.scale(1e-3)), eyeReflection);
			final Vector3D indirectColor = traceRay(reflectRay, bounces-1).multiply(material.specularColor);
			totalColor = totalColor.add(indirectColor);
		}
		
		return totalColor;
	}
	
}
