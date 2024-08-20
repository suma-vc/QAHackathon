package utilities;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.Map;
import java.util.Set;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.relevantcodes.extentreports.ExtentTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtility {
	private static final Logger logger = LoggerFactory.getLogger(FileUtility.class);

	private static final int BUFFER_SIZE = 4096;
	/**
	 * Extracts a zip file specified by the zipFilePath to a directory specified by
	 * destDirectory (will be created if does not exists)
	 *
	 * @param zipFilePath   The path of the zip file to extract
	 * @param destDirectory The directory to extract the zip file to

	 */
	public void unzip(String zipFilePath, String destDirectory){
		File destDir = new File(destDirectory);
		if (!destDir.exists()) {
			boolean dirCreated = destDir.mkdir();
			if (!dirCreated) {
				logger.error("Error: Could not create directory {}", destDirectory);
				return;
			}
		}
		try(ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath))){
		ZipEntry entry = zipIn.getNextEntry();
		// iterates over entries in the zip file
		while (entry != null) {
			String filePath = destDirectory + File.separator + entry.getName();
			if (!entry.isDirectory()) {
				// if the entry is a file, extracts it
				extractFile(zipIn, filePath);
			} else {
				// if the entry is a directory, make the directory
				File dir = new File(filePath);
				boolean subDirCreated = dir.mkdirs();
				if (!subDirCreated) {
					logger.error("Error: Could not create subdirectory {}", filePath);
				}
			}
			zipIn.closeEntry();
			entry = zipIn.getNextEntry();
		}
	} catch (IOException e) {
			logger.error("Error unzipping file {}: {}", zipFilePath, e.getMessage());
	}
	}
	/**
	 * Extracts a zip entry (file entry)
	 * @param zipIn
	 * @param filePath
	 * @throws IOException
	 */
	private void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
		byte[] bytesIn = new byte[BUFFER_SIZE];
		int read = 0;
		while ((read = zipIn.read(bytesIn)) != -1) {
			bos.write(bytesIn, 0, read);
		}
		bos.close();
	}
	public  void decompressGzip(Path source, Path target) throws IOException {

		try {

			if(source.toString().contains(".gz")){
				try (GZIPInputStream gis = new GZIPInputStream(new FileInputStream(source.toFile()));
						FileOutputStream fos = new FileOutputStream(target.toFile())) {

					// copy GZIPInputStream to FileOutputStream
					byte[] buffer = new byte[1024];
					int len;
					while ((len = gis.read(buffer)) > 0) {
						fos.write(buffer, 0, len);
					}

				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	// write data into excel sheet

	public  void writeToExcel(String path,String sheetName,Map<String, Object> map) throws Exception
	{

		// workbook object
		XSSFWorkbook workbook = new XSSFWorkbook();

		// spreadsheet object
		XSSFSheet spreadsheet
		= workbook.createSheet(sheetName);

		// creating a row object
		XSSFRow row;



		Set<String> keyid = map.keySet();

		int rowid = 0;

		// writing the data into the sheets...

		for (String key : keyid) {

			row = spreadsheet.createRow(rowid++);
			Object object = map.get(key);
			int cellid = 0;


			Cell cell = row.createCell(cellid++);
			cell.setCellValue(key);
			cell= row.createCell(cellid);
			cell.setCellValue((String)object);

		}

		// .xlsx is the format for Excel Sheets...
		// writing the workbook into the file...
		FileOutputStream out = new FileOutputStream(
				new File(path));

		workbook.write(out);
		out.close();
		workbook.close();
	}
	/** This method used to read the excel data and store it two dimensional Array
	 * 
	 * @param fileName - Where the file located in project
	 * @param sheetName - Sheet, where data needs to be extracted
	 * @return
	 */
	public String[][] getExcelData(String fileName, String sheetName) 
	{
		String[][] arrayExcelData = null;
		Workbook  tempWB;

		try {

			if(fileName.contains(".xlsx")){
				tempWB = new XSSFWorkbook(fileName);
			}
			else{				
				InputStream inp = new FileInputStream(fileName);
				tempWB =  new HSSFWorkbook(new POIFSFileSystem(inp));					
			}

			Sheet sheet = tempWB.getSheet(sheetName);

			// Total rows counts the top heading row
			int totalNoOfRows = sheet.getLastRowNum();
			System.out.println("The total rows are : " + totalNoOfRows);
			Row row = sheet.getRow(0);
			int totalNoOfCols = row.getLastCellNum();
			System.out.println("The total colums are : " + totalNoOfCols);

			arrayExcelData = new String[totalNoOfRows][totalNoOfCols];

			try {
				for (int i= 0 ; i < totalNoOfRows; i++) {

					for (int j=0; j < totalNoOfCols; j++) 
					{
						row = sheet.getRow(i);
						try{
							//	System.out.println(row.getCell(j).toString().trim());
							//	System.out.println("The cell values are : " +i +"," +j+": "+ row.getCell(j).toString().trim() );
							arrayExcelData[i][j] = row.getCell(j).toString().trim();
						}
						catch(Exception e){
							arrayExcelData[i][j] = "";
						}
					}
				}
				tempWB.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
				tempWB.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return arrayExcelData;
	}

	/** This method used to find the Column Index in excel sheet, depends on the column name
	 * 
	 * @param sheet - Excel sheet, where needs to find the column
	 * @param ColName - Col Name
	 * @return
	 */
	public int findCol(Sheet sheet,String ColName)
	{
		Row row = null;
		int colCount = 0;

		row = sheet.getRow(0);
		if (!(row==null))
		{
			colCount = row.getLastCellNum();
		}
		else
			colCount = 0;

		for (int j=0;j<colCount;j++)
		{
			if(!( row.getCell(j)==null)){
				if (row.getCell(j).toString().trim().equalsIgnoreCase(ColName)|| row.getCell(j).toString().trim().equalsIgnoreCase((ColName+"[][String]"))){
					return j;
				}
			}
		}
		return -1;
	}

	/*** This method used to get the value from the excel sheet
	 * 
	 * @param SheetName - Sheet, where data needs to extracted
	 * @param colName - Column Name
	 * @param rowNo - Row number, at which data needs to extracted
	 * @return
	 */
	public String getValueFromDatasheet(String SheetName,String colName,int rowNo,ExtentTest logger)
	{
		try
		{
			Workbook tempWB;
			String value ="";
			if (EnvironmentVariables.dataPoolPath.contains(".xlsx"))
				tempWB = new XSSFWorkbook(EnvironmentVariables.dataPoolPath);

			else
			{
				FileInputStream inp = new FileInputStream(EnvironmentVariables.dataPoolPath);
				tempWB = (Workbook) new HSSFWorkbook(new POIFSFileSystem(inp));	
			}

			Sheet sheet = tempWB.getSheet(SheetName);
			Row row = sheet.getRow(rowNo);

			if(row == null){
				return null;
			}
			try{
				value = row.getCell(findCol(sheet, colName)).toString().trim();
				return value;
			}
			finally {}
		}
		catch(FileNotFoundException e)
		{
			LoggerUtility.logFail(logger, "File not found in the path : "+ EnvironmentVariables.dataPoolPath);
		}
		catch(IOException e)
		{
			LoggerUtility.logFail(logger, "Problem in reading the File");
		}
		return null;
	}
	public void removeFilesFromDirectory(File directoryPath) {
		try {
			FileUtils.cleanDirectory(directoryPath);
		} catch (IOException e) {
			
			e.printStackTrace();
		} 
	}
	public void FileMerge(String file1Path,String file2Path,String newFilePath) {
		try {
			// PrintWriter object for file3.txt
					PrintWriter pw = new PrintWriter(newFilePath);
					
					// BufferedReader object for file1.txt
					BufferedReader br = new BufferedReader(new FileReader(file1Path));
					
					String line = br.readLine();
					
					// loop to copy each line of
					// file1.txt to file3.txt
					while (line != null)
					{
						pw.println(line);
						line = br.readLine();
					}
					
					br = new BufferedReader(new FileReader(file2Path));
					
					line = br.readLine();
					
					// loop to copy each line of
					// file2.txt to file3.txt
					while(line != null)
					{
						pw.println(line);
						line = br.readLine();
					}
					
					pw.flush();
					
					// closing resources
					br.close();
					pw.close();
					
					System.out.println("Merged file1.txt and file2.txt into file3.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
