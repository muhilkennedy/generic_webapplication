package com.platform.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.docx4j.Docx4J;
import org.docx4j.convert.out.FOSettings;
import org.docx4j.fonts.IdentityPlusMapper;
import org.docx4j.fonts.Mapper;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;

public class DocxUtil {
	
	/**
	 * @param docFile
	 * @param pdfFile
	 * @throws Exception - Docx4jException
	 */
	public static void convertDocToPDF(File docFile, File pdfFile) throws Exception {
		WordprocessingMLPackage word = WordprocessingMLPackage.load(docFile);
		OutputStream os = new FileOutputStream(pdfFile);
		FOSettings fset = Docx4J.createFOSettings();
		Path tempPath = Files.createTempDirectory(null);
		fset.setImageDirPath(tempPath.toString());
		fset.setWmlPackage(word);
		Docx4J.toFO(fset, os, Docx4J.FLAG_EXPORT_PREFER_XSL);
		// flush Image Directory
		FileUtil.deleteDirectoryOrFile(tempPath.toFile());
	}

	public static File convertDocToPDF(File docFile) throws Exception {
		File pdfFile = File.createTempFile("Temp", ".pdf");
		WordprocessingMLPackage word = WordprocessingMLPackage.load(docFile);
		OutputStream os = new FileOutputStream(pdfFile);
		FOSettings fset = Docx4J.createFOSettings();
		Path tempPath = Files.createTempDirectory(null);
		fset.setImageDirPath(tempPath.toString());
		fset.setWmlPackage(word);
		Docx4J.toFO(fset, os, Docx4J.FLAG_EXPORT_PREFER_XSL);
		// flush Image Directory
		FileUtil.deleteDirectoryOrFile(tempPath.toFile());
		return pdfFile;
	}
	
	//need to take care of custom fonts (only tamil font is added for now)
			/*Mapper fontMapper = new IdentityPlusMapper();
			String fontFamily = "Arima Madurai";
			ClassPathResource classPathResource = new ClassPathResource("fonts/ArimaMadurai-Regular.ttf");
			URL simsunUrl = classPathResource.getURL();
			PhysicalFonts.addPhysicalFonts(fontFamily, simsunUrl);
			PhysicalFont simsunFont = PhysicalFonts.get(fontFamily);
			fontMapper.put(fontFamily, simsunFont);
			word.setFontMapper(fontMapper);*/

}
