package moa.classifiers.a.VAC.other;

import cutpointdetection.CutPointDetector;
import volatilityevaluation.Buffer;

public class SimpleCurrentVolatilityMeasure implements CurrentVolatilityMeasure
{
	private CutPointDetector cutPointDetector;
	private boolean isDrifting;
	private int timestamp;
	private int coolingPeriod;
	//	private int instanceSeen;

	public SimpleCurrentVolatilityMeasure(int bufferSize, CutPointDetector cutPointDetector, int coolingPeriod)
	{
		this.cutPointDetector = cutPointDetector;
		this.isDrifting = false;
		this.timestamp = 0;
		this.coolingPeriod = coolingPeriod;
		//		this.instanceSeen = 0;
	}

	@Override
	public int setInput(double input)
	{

		if(timestamp > coolingPeriod){

			double oldError = cutPointDetector.getEstimation();
			boolean errorChange = cutPointDetector.setInput(input);

			if(oldError > cutPointDetector.getEstimation())
			{
				errorChange = false;
			}

			if(errorChange)
			{
				double result = this.timestamp;
				isDrifting = true;
				this.timestamp = 0;
				//				instanceSeen = 0

				return (int)result;
			}
			else
			{
				isDrifting = false;
				this.timestamp++;
				return -1;

			}
		}
		else{
			isDrifting = false;
			this.timestamp++;
			return -1;
		}




	}

	@Override
	public boolean conceptDrift()
	{
		return isDrifting;
	}

	@Override
	public double getMeasure()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int setInput(boolean drift)
	{
		// TODO Auto-generated method stub
		return 0;
	}

}
