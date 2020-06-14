package Utilities;

/**@author Zuber Qureshi
 * @description Class created to read the data from excel file
 * @version 1.0
 */

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jxl.Sheet;
import jxl.Workbook;

public class DataBase1 
{    
	List<String> Hdr = new ArrayList<String>();
    List<String> RList = new ArrayList<String>();
    	FileInputStream FileName1 = null;
		String st1;
		int flag=0;
		Sheet sh1 = null; 
		Workbook wbk = null;
		int rowcnt = 0;
		public int getRowcnt() {
			return rowcnt;
		}

		public void setRowcnt(int rowcnt) {
			this.rowcnt = rowcnt;
		}

		public int getColcnt() {
			return colcnt;
		}

		public void setColcnt(int colcnt) {
			this.colcnt = colcnt;
		}

		int colcnt = 0;
		
	    public Workbook getWbk() {
			return wbk;
		}

		public void setWbk(Workbook wbk) {
			this.wbk = wbk;
		}

		public Sheet getSh1() {
			return sh1;
		}

		public void setSh1(Sheet sh1) {
			this.sh1 = sh1;
		}
  
	         		
		public DataBase1(String FileName)
		{
			try {
				FileName1 = new FileInputStream(FileName);
				wbk = Workbook.getWorkbook(FileName1);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
	        
	        sh1 = wbk.getSheet(0);
	        rowcnt = sh1.getRows();
	        colcnt = sh1.getColumns();
	        //wbk.close();
	        
		}
	        	        		
	    /*Function to return the Header of the file*/
	    public List<String> Header(int rcnt,int ccnt, Sheet sh)
	    {
	    	List<String> Hdrlist = new ArrayList<String>();
	    	for (int g=0;g<1;g++)
	    	{
	    		for (int j=0;j<ccnt;j++)
	    		{
	    			Hdrlist.add(sh.getCell(j,g).getContents());
	    		}
	    	}
	    	return Hdrlist;   
	    }
	    
	    /*Function to return the Row value of the file*/
	    public List<String> RowValue(int a,int ccnt, Sheet sh)
	    {
	    	List<String> Rowlist = new ArrayList<String>();
	    	for (int j=0;j<ccnt;j++)
	    		{
	    			Rowlist.add(sh.getCell(j,a).getContents());     
	     		} 
	    	
	    	 	return Rowlist; 
	    }	
	    
	    /*Function to return the Dictionary of the file*/
	    public Map<String, String> dict(List<String> Hdr,List<String> Rlist)
	    {
	    	
	    				Map<String, String> dictionary = new HashMap<String, String>();
	    				for(int l=0;l<Hdr.size();l++)
	    				{
	    					dictionary.put(Hdr.get(l),Rlist.get(l));
	    				}
						return dictionary;
	    }

}

