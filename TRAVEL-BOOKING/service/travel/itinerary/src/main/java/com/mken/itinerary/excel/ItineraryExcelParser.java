package com.mken.itinerary.excel;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.platform.service.ExcelService;

/**
 * @author Muhil
 * 
 * Read Excel for itinerary details.
 * 
 * Sheet-1 (ITINERARY)
 * DAY-DATE-TIME-DETAIL-AMENETIES-CONTACTS-DESTINATIONID-TOURISTATTRACTIONID
 * 
 * Sheet-2 (NOTES)
 * INCLUSION-EXCLUSION-CUSTOMER ADVICE
 *
 */
public class ItineraryExcelParser extends ExcelService implements Runnable {
	
	private static final String ITINERARY_SHEET = "ITINERARY";

	public ItineraryExcelParser(File file) throws IOException {
		super(file);
	}
	
	@Override
	public void run() {
		//run parsers
	}
	
	public void processItinerarySheet() {
		Sheet sheet = getSheetByName(ITINERARY_SHEET);
		DataFormatter formatter = new DataFormatter();
        
        for (int rowIndex = 0; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
        	Row row = sheet.getRow(rowIndex);
        	if(row != null) {
        		Iterator<Cell> columnIterator = row.iterator();
                while(columnIterator.hasNext()) {
                	Cell cell = columnIterator.next();
                	System.out.println(formatter.formatCellValue(cell));
                }
        	}
        }
	}

	public void processNotesSheet() {
		
	}
	
}
