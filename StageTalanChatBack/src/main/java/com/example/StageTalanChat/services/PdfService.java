package com.example.StageTalanChat.services;
import com.example.StageTalanChat.entities.User;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class PdfService {

  public ByteArrayInputStream generatePdf(List<User> users) {
    try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
      PdfWriter writer = new PdfWriter(out);
      PdfDocument pdf = new PdfDocument(writer);
      Document document = new Document(pdf);
      document.setMargins(20, 20, 20, 20);

      String logoPath = new ClassPathResource("C:\\\\Users\\\\jawhe\\\\OneDrive\\\\Bureau\\\\StageTalanChatFront\\\\src\\\\assets\\\\images.png").getPath();
      Image logo = new Image(ImageDataFactory.create(logoPath));
      logo.setWidth(100);
      logo.setHorizontalAlignment(HorizontalAlignment.CENTER);

      document.add(logo);

      PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
      document.add(new Paragraph("Liste des Utilisateurs")
        .setFont(font)
        .setFontSize(18)
        .setTextAlignment(TextAlignment.CENTER)
        .setMarginBottom(20));

      float[] columnWidths = {1, 3, 3};
      Table table = new Table(UnitValue.createPercentArray(columnWidths));
      table.setWidth(UnitValue.createPercentValue(100));
      table.addHeaderCell("Numero").setBackgroundColor(ColorConstants.LIGHT_GRAY);
      table.addHeaderCell("Nom et Prenom").setBackgroundColor(ColorConstants.LIGHT_GRAY);
      table.addHeaderCell("Email").setBackgroundColor(ColorConstants.LIGHT_GRAY);

      for (User user : users) {
        table.addCell(String.valueOf(user.getId()));
        table.addCell(user.getNom() + " " + user.getPrenom());
        table.addCell(user.getEmail());
      }

      document.add(table);

      document.close();
      return new ByteArrayInputStream(out.toByteArray());
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }


}

