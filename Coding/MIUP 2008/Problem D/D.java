import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

/* Code Developed by Renato Rodrigues
 * rmbr@student.dei.uc.pt 
 * DEI-UC (LPA 09/10)*/

class Objs
{
	int minX;
	int minY;
	int maxX;
	int maxY;
	int minFloor;
	int height;
	int dist;
	
	public Objs()
	{
		this.minX=this.minY=0;
		this.maxX=this.maxY=0;
		this.minFloor=0;
		this.height=0;
		this.dist=0;
	}
	
	public Objs(int minX,int minY,int maxX,int maxY,int minFloor,int height,int dist)
	{
		this.minX=minX;
		this.minY=minY;
		this.maxX=maxX;
		this.maxY=maxY;
		this.minFloor=minFloor;
		this.height=height;
		this.dist=dist;
	}
	
	public boolean compare(Objs check)
	{
		boolean equal=true;
		if(this.minY != check.minY || this.minX != check.minX || this.maxY != check.maxY || this.maxX != check.maxX)
			return false;
		return equal;
	}
}

class Andar
{
	int nObjs;
	Objs objectos[];
	
	public Andar()
	{
		this.nObjs=0;
		this.objectos=new Objs[200];
	}
}

class Cmin implements Comparator<Objs> 
{
    public int compare(Objs a, Objs b) 
	{
        if (a.dist < b.dist) 
            return -1;
		else if (a.dist > b.dist)
            return 1;
		else
            return 0;
    }
}

public class D 
{
	static int readSize=256;
	
	static String readLn (int maxLg)
	{
		byte lin[] = new byte [maxLg];
		int lg = 0, car = -1;
		try {
			while (lg < maxLg)
			{
				car = System.in.read();
		        if ((car < 0) || (car == '\n')) break;
		        	lin [lg++] += car;
		     }
		} catch (IOException e){
			return (null);
		}
		if ((car < 0) && (lg == 0)) return (null);
			return (new String (lin, 0, lg));
	}
	
	static void calcula(Objs array[],Andar andares[],int N,int MaxFloor)
	{
		int i,z,w,xMin=0,propagation=0,xCalc;
		double y,v_y;
		/*Array Order by minDist X*/
		for(i=0;i<N;++i)
		{
			//System.out.println("Objecto: "+array[i].minX+" "+array[i].maxX+" minY: "+array[i].minY+" maxY: "+array[i].maxY);
			/*Already Have a Xmin and minDist always bigger than xMin*/
			if(xMin!=0 && xMin<array[i].dist)
				break;
			propagation=array[i].minFloor+array[i].height+1;
			/*Check for each Floor until Final Propagation*/
			for(z=array[i].minFloor;z<propagation;++z)
			{
				//System.out.println("Andar: "+z);
				v_y=array[i].minY+0.5;
				/*Take Intersections of our Object in Y=v_y*/
				for(y=v_y;y<array[i].maxY;++y)
				{
					xCalc=array[i].dist;
					//System.out.println("xCalc: "+xCalc+" V_Y: "+y);
					/*For each Obj in the Floor Check Intersections*/
					for(w=0;w<andares[z].nObjs;++w)
					{
						/*Not The Same Object*/
						if(array[i] != andares[z].objectos[w] && !array[i].compare(andares[z].objectos[w]))
						{	
							/*See Intersection -> v_y*/
							if(y > andares[z].objectos[w].minY && y < andares[z].objectos[w].maxY)
								xCalc+=andares[z].objectos[w].dist;
						}
						/*Already have a xMin and xCalc Bigger than xMin in this v_y*/
						if(xMin!=0 && xCalc > xMin)
							break;
					}
					if(xMin==0 || xCalc < xMin)
						xMin=xCalc;
				}
			}
		}
		System.out.println(xMin);
	}
	
	public static void main(String[] args) 
	{
		Comparator <Objs> bymin = new Cmin();	
		String line="",div[];
		int Ncasos,i,j,MaxFloor=0,propagation;
		line=readLn(readSize);
		div=line.trim().split(" ");
		Ncasos=Integer.parseInt(div[0]);
		Objs array[]=new Objs[Ncasos];
		Andar andares[]=new Andar[40000];
		for(i=0;i<Ncasos;++i)
		{
			line=readLn(readSize);
			div=line.trim().split(" ");
			/*<min x> <min y> <max x> <max y> <min floor> <height>*/
			Objs temp=new Objs(Integer.parseInt(div[0]),Integer.parseInt(div[1]),Integer.parseInt(div[2]),Integer.parseInt(div[3]),Integer.parseInt(div[4]),Integer.parseInt(div[5]),Integer.parseInt(div[2])-Integer.parseInt(div[0]));
			array[i]=temp;
			propagation=temp.minFloor+temp.height;
			for(j=temp.minFloor;j<=propagation;++j)
			{
				if(andares[j]==null)
					andares[j]=new Andar();
				andares[j].objectos[andares[j].nObjs]=temp;
				andares[j].nObjs++;
			}
			if((temp.minFloor+temp.height) > MaxFloor)
				MaxFloor=(temp.minFloor+temp.height);
		}
		/*Order by MinDist=(maxX-minX)*/
		Arrays.sort(array,0,Ncasos,bymin);
		calcula(array,andares,Ncasos,MaxFloor);
	}

}
