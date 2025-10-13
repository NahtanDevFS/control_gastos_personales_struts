package com.misgastos.action;

import com.misgastos.dao.GastoMensualDAO;
import com.misgastos.model.GastoMensual;
import com.misgastos.model.Usuario;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.apache.struts2.ServletActionContext;

public class ExportarAction extends ActionSupport implements SessionAware {

    private GastoMensualDAO gastoDAO = new GastoMensualDAO();
    private Map<String, Object> session;

    private String filtroTipo;
    private String filtroMes;
    private Integer filtroAnio;

    public String getFiltroTipo() { return filtroTipo; }
    public void setFiltroTipo(String filtroTipo) { this.filtroTipo = filtroTipo; }
    public String getFiltroMes() { return filtroMes; }
    public void setFiltroMes(String filtroMes) { this.filtroMes = filtroMes; }
    public Integer getFiltroAnio() { return filtroAnio; }
    public void setFiltroAnio(Integer filtroAnio) { this.filtroAnio = filtroAnio; }
    
    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }

    private Usuario getUsuarioDeSesion() {
        return (Usuario) session.get("usuarioLogueado");
    }

    public String aPdf() throws Exception {
        Usuario usuario = getUsuarioDeSesion();
        if (usuario == null) return LOGIN;

        List<GastoMensual> listaGastos = gastoDAO.obtenerGastosFiltrados(usuario.getId(), filtroTipo, filtroMes, filtroAnio);

        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"gastos.pdf\"");
        
        OutputStream out = response.getOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, out);

        document.open();
        document.add(new Paragraph("Reporte de Gastos Personales"));
        document.add(new Paragraph(" "));

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        table.setWidths(new float[] { 3f, 1.5f, 1.5f, 1f, 1.5f });

        Font headFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
        
        table.addCell(new PdfPCell(new Phrase("Descripción", headFont)));
        table.addCell(new PdfPCell(new Phrase("Tipo", headFont)));
        table.addCell(new PdfPCell(new Phrase("Mes", headFont)));
        table.addCell(new PdfPCell(new Phrase("Año", headFont))); 
        table.addCell(new PdfPCell(new Phrase("Monto (Q)", headFont)));

        for (GastoMensual g : listaGastos) {
            table.addCell(g.getDescripcion());
            table.addCell(g.getTipoGasto());
            table.addCell(g.getMes());
            table.addCell(String.valueOf(g.getAnio()));
            
            PdfPCell montoCell = new PdfPCell(new Phrase("Q " + g.getMonto().toPlainString()));
            montoCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(montoCell);
        }
        
        document.add(table);
        document.close();
        out.flush();
        out.close();

        return null;
    }

    public String aExcel() throws Exception {
        Usuario usuario = getUsuarioDeSesion();
        if (usuario == null) return LOGIN;
        
        List<GastoMensual> listaGastos = gastoDAO.obtenerGastosFiltrados(usuario.getId(), filtroTipo, filtroMes, filtroAnio);
        
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Gastos");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Descripción");
        headerRow.createCell(1).setCellValue("Tipo de Gasto");
        headerRow.createCell(2).setCellValue("Mes");
        headerRow.createCell(3).setCellValue("Año");
        headerRow.createCell(4).setCellValue("Monto (Q)");

        int rowNum = 1;
        for (GastoMensual g : listaGastos) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(g.getDescripcion());
            row.createCell(1).setCellValue(g.getTipoGasto());
            row.createCell(2).setCellValue(g.getMes());
            row.createCell(3).setCellValue(g.getAnio()); 
            
            BigDecimal monto = g.getMonto();
            row.createCell(4).setCellValue(monto != null ? monto.doubleValue() : 0.0);
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        workbook.write(baos);
        workbook.close();

        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=\"gastos.xlsx\"");
        response.setContentLength(baos.size());

        OutputStream out = response.getOutputStream();
        baos.writeTo(out);
        out.flush();
        out.close();
        baos.close();

        return null;
    }
}