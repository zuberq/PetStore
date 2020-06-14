package Pages;

import Utilities.DataBase1;

/**
 * @author Zuber
 *
 */

//Function to read data from DataTable i.e. Excel file
public class DataProvider1 {
	
	DataBase1 data;
	Object[][] data2;
	public Object[][] DataFromDataTable(String FilePath){
		try{
			data = new DataBase1(FilePath);
			data2 = new Object[data.getRowcnt()-1][data.getColcnt()];
		
		for(int i=1;i<data.getRowcnt();++i){
			for(int j=0;j<data.getColcnt();++j){
				data2[i-1][j] = data.getSh1().getCell(j,i).getContents();
			}
		}			
		}catch(Exception e){
			e.printStackTrace();
		}
		return data2;
		}


}
