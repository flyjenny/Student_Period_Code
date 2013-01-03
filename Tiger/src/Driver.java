
public class Driver {
	
	public static void main(String[] args){	
		try{
       Tiger.parser.main(args);
       if(Tiger.Overall.myerror.anyErrors)
			System.exit(1);
            System.exit(0);
		}catch(Exception e){
			e.printStackTrace();
			System.exit(1);
		}
	}
}
