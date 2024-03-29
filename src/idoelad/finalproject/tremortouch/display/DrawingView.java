package idoelad.finalproject.tremortouch.display;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Toast;

public class DrawingView extends View implements OnTouchListener{

	private Paint paint;
	private String resultsFilePath;
	private String circlesFilePath;
	private ArrayList<TestPoint> testPoints;
	private TestPoint currTestPoint;
	private ListIterator<TestPoint> testPointsIter;
	private Context context;

	private float x1,x2;
	private Random rand;
	static final int MIN_DISTANCE = 20;


	public DrawingView(Context context, String fileSelected, String circlesFilePath) {
		super(context);
		this.resultsFilePath = fileSelected;
		this.circlesFilePath = circlesFilePath;
		this.context = context;
		this.setOnTouchListener(this);
		rand = new Random();

		paint = new Paint();
		createTestPoints();
		testPointsIter = testPoints.listIterator();
		if (testPointsIter.hasNext()){
			currTestPoint = testPointsIter.next();
		}

	}

	private void createTestPoints() {
		testPoints = new ArrayList<TestPoint>();
		File resultsFile = new File(resultsFilePath);
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(resultsFile));
			String line = br.readLine(); //Skip first line

			int lastShapeId = 0;
			int currShapeId;
			TestPoint currTestPoint = null;
			while ((line = br.readLine()) != null && !line.startsWith("TPS")) {
				String[] lineParts = line.split(",");
				currShapeId = Integer.parseInt(lineParts[0]);
				if (currShapeId != lastShapeId){
					if (currTestPoint != null){
						testPoints.add(currTestPoint);
					}
					currTestPoint = new TestPoint(getTatgetCircle(Integer.parseInt(lineParts[0])));
					TestCircle tc = new TestCircle(Float.parseFloat(lineParts[6]), Float.parseFloat(lineParts[7]), Float.parseFloat(lineParts[9]), Float.parseFloat(lineParts[8]), Integer.parseInt(lineParts[1]));
					currTestPoint.addTestCircle(tc);
					lastShapeId = currShapeId;
				}
				else{
					TestCircle tc = new TestCircle(Float.parseFloat(lineParts[6]), Float.parseFloat(lineParts[7]), Float.parseFloat(lineParts[9]), Float.parseFloat(lineParts[8]), Integer.parseInt(lineParts[1]));
					currTestPoint.addTestCircle(tc);
				}
			}

			if (currTestPoint != null){
				testPoints.add(currTestPoint);
			}
			br.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private TestCircle getTatgetCircle(int shapeId) throws NumberFormatException, IOException {
		File circlesFile = new File(circlesFilePath);
		BufferedReader br = null;
		br = new BufferedReader(new FileReader(circlesFile));
		String line;
		while ((line = br.readLine()) != null){
			String[] lineParts = line.split(",");
			if (Integer.parseInt(lineParts[0]) == shapeId){
				br.close();
				return new TestCircle(Float.parseFloat(lineParts[1]),Float.parseFloat(lineParts[2]),(float)(Float.parseFloat(lineParts[3])*0.7));
			}
		}	
		br.close();
		return null;
	}


	@Override
	protected void onDraw(Canvas canvas) {
		//Clear canvas
		canvas.drawColor(Color.BLACK);

		//Set paint general properties
		paint.setAntiAlias(true);

		//Draw target circle
		paint.setColor(Color.MAGENTA);
		paint.setAlpha(255);
		TestCircle tc = currTestPoint.getTargetCircle();
		canvas.drawCircle(tc.getX(), tc.getY(), tc.getRadius(), paint);

		//Draw touches
		int lastTouchId = -1;
		int currTouchId;
		for (TestCircle circle : currTestPoint.getTestCircles()){
			currTouchId = circle.getTouchId();
			if (currTouchId != lastTouchId){
				paint.setColor(getColorFromTouchId(currTouchId));
				lastTouchId = currTouchId;
			}
			paint.setAlpha(circle.getAlpha());
			canvas.drawCircle(circle.getX(), circle.getY(), circle.getRadius(), paint);
		}

		//Draw target circle
		paint.setColor(Color.MAGENTA);
		paint.setAlpha(30);
		TestCircle tc2 = currTestPoint.getTargetCircle();
		canvas.drawCircle(tc2.getX(), tc2.getY(), tc2.getRadius(), paint);


	}

	private int getColorFromTouchId(int touchId){
		rand.setSeed(touchId*1000);
		int r = rand.nextInt(255);
		int g = rand.nextInt(255);
		int b = rand.nextInt(255);
		return Color.rgb(r,g,b);

	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch(event.getAction())
		{
		case MotionEvent.ACTION_DOWN:
			x1 = event.getX();                         
			break;         
		case MotionEvent.ACTION_UP:
			x2 = event.getX();
			float deltaX = x2 - x1;
			if (Math.abs(deltaX) > MIN_DISTANCE && deltaX < 0)
			{
				if (testPointsIter.hasPrevious()){
					currTestPoint = testPointsIter.previous();
					invalidate();
				}
				else{
					Toast.makeText(context, "< No more shapes", Toast.LENGTH_SHORT).show();
				}
			}
			else if (Math.abs(deltaX) > MIN_DISTANCE && deltaX > 0)
			{
				if (testPointsIter.hasNext()){
					currTestPoint = testPointsIter.next();
					invalidate();
				}
				else{
					Toast.makeText(context, "No more shapes >", Toast.LENGTH_SHORT).show();
				}
			}     
			break;   
		}           
		return true;
	}


}
