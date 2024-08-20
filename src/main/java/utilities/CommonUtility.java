package utilities;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utilities.EnvironmentVariables;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class CommonUtility {

	private static final Logger logger = LoggerFactory.getLogger(FileUtility.class);
	public static String getProperty(String propertyName)  {
		Properties p = new Properties();
		String property = null;
		FileReader reader = null;
		try {
			reader = new FileReader(EnvironmentVariables.projectPropertiesPath);

			p.load(reader);
			property = p.getProperty(propertyName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		return property;
	}
	public void setProperty(String propertyName,String propertyValue) throws IOException {
		Properties p = new Properties();
		try {
			p.setProperty(propertyName,propertyValue);  			  
			p.store(new FileWriter("info.properties"),"Javatpoint Properties Example");  

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();

		} catch (Exception e) {
			e.printStackTrace();

		} 
	}
	

	public String getCorrespondingFieldValue(String ColumnName,String fieldName,String [][] array) {
		boolean flagP=false;
		boolean flagf=false;
		int col=0;
		for(int i=0;i<array[1].length;i++) {
			//System.out.println(array[0][i].trim());
			if(array[1][i].trim().equalsIgnoreCase("Column ID")) {
				col=i;
			}
			if(array[1][i].trim().equalsIgnoreCase(ColumnName)) {
				flagP=true;

				for(int j=0;j<array.length-1;j++) {
					if(array[j][col].trim().equalsIgnoreCase(fieldName)) {
						flagf=true;
						System.out.println(array[j][col].trim());
						System.out.println(array[j][i+1].trim());
						if(!array[j][i+1].trim().equals(""))
							return array[j][i+1];
						else
							return array[j][i];

					}

				}
				if(!flagf) {
					System.out.println("Field not found");
				}


			}
		}
		if(!flagP) {
			System.out.println("Product not found");
		}

		return null;
	}

	public String getFieldForSpecifiedIndex(String product,String index,String [][] array) {
		boolean flagP=false;
		boolean flagf=false;
		int col=0;
		for(int i=0;i<array[1].length;i++) {
			//System.out.println(array[0][i].trim());
			if(array[1][i].trim().equalsIgnoreCase("Column ID")) {
				col=i;
			}
			if(array[1][i].trim().equalsIgnoreCase(product)) {
				flagP=true;

				for(int j=0;j<array.length-1;j++) {
					System.out.println(array[j][i-1].replace(".0", ""));
					if(product.contains("XSP vs MPTB")){
						if(array[j][i-2].replace(".0", "").equalsIgnoreCase(index)) {
							flagf=true;
							System.out.println(array[j][col].trim());
							System.out.println(array[j][i+1].trim());

							return array[j][col];

						}
					}
					else{
						if(array[j][i-1].replace(".0", "").equalsIgnoreCase(index)) {
							flagf=true;
							System.out.println(array[j][col].trim());
							System.out.println(array[j][i+1].trim());

							return array[j][col];

						}

					}

				}
				if(!flagf) {
					System.out.println(index+" :Index not found for product: "+product);
				}


			}
		}
		if(!flagP) {
			System.out.println("Product not found: "+product);
		}

		return null;
	}


	public String getValueOfField(String fieldName,String [][] array) {

		boolean flagf=false;

		for(int i=0;i<array.length;i++) {
			System.out.println(array[i][0].trim());

			if(array[i][0].trim().equalsIgnoreCase(fieldName)) {
				flagf=true;
				array[i][1]=array[i][1].replaceAll("\"", "");
				System.out.println(array[i][1].trim());

				return array[i][1];

			}

		}
		if(!flagf) {
			System.out.println("Field not found");
		}


		return null;



	}
	 public String generateDate() {
		 String date=null;
		 try {
			 DateFormat dateFormat=new SimpleDateFormat("dd_MMM_YYYY_HH_mm_ss_SSS");
			 Date object=new Date();
			 date=dateFormat.format(object);
		 }catch(Exception e) {
			 System.out.print("Exception occured While creating the date  for log file creation.Error Message"+e.toString());
		 }
		 return date;

	 }
	 public String generateDateWithFormatt(String formatt) {
		 String date=null;
		 try {
			 DateFormat dateFormat=new SimpleDateFormat(formatt);
			 Date object=new Date();
			 date=dateFormat.format(object);
		 }catch(Exception e) {
			 System.out.print("Exception occured While creating the date  for log file creation.Error Message"+e.toString());
		 }
		 return date;

	 }
	 public String addDaysToCurrentDate(int nDays, String formatt) {
		 String output = null;
		 try {

			 SimpleDateFormat sdf = new SimpleDateFormat(formatt);
			 Calendar c = Calendar.getInstance();
			 c.setTime(new Date()); // Using today's date
			 c.add(Calendar.DATE, nDays); // Adding n days
			 output = sdf.format(c.getTime());
			 System.out.println(output);


		 }catch(Exception e) {
			 System.out.print("Exception occured While creating the date  for log file creation.Error Message"+e.toString());
		 }

		 return output;

	 }

}
