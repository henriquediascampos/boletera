package com.henriquediascampos.boletera;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.naming.NameNotFoundException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@RestController
public class BoletoController {

    @GetMapping("/generate")
    public void downloadPdf(@RequestParam final Map<String, String> parameters, final HttpServletResponse response)
            throws IOException, JRException, NameNotFoundException {

        InputStream pdfInputStream = generate(parameters);

        response.setContentType(MediaType.APPLICATION_PDF_VALUE);
        response.setHeader("Content-Disposition", "inline; filename=prontuario.pdf");

        try (OutputStream outputStream = response.getOutputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = pdfInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }

        pdfInputStream.close();
    }

    private InputStream generate(final Map<String, String> requestParam)
            throws IOException, JRException, NameNotFoundException {
        var parameters = new Param(requestParam);
        var detailList = generateDeteil(parameters);

        return buildPdf(detailList, parameters.getValues());
    }

    private InputStream buildPdf(final List<Parcela> detailList, final Map<String, Object> parameters)
            throws JRException {
        final InputStream file = this.getClass().getResourceAsStream("/static/boleto_tamplate.jrxml");
        final JasperReport jasperReport = JasperCompileManager.compileReport(file);
        final JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(detailList);
        final JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        var pdf = JasperExportManager.exportReportToPdf(jasperPrint);

        return new ByteArrayInputStream(pdf);
    }

    private List<Parcela> generateDeteil(final Param params) {

        int i = 1;
        var parcelas = new ArrayList<Parcela>();

        while (i <= params.getTotalParcelas()) {

            Double valor = params.getValorTotal() / params.getTotalParcelas();

            parcelas.add(
                Parcela.builder()
                    .valor(valor)
                    .parcela(i)
                    .data(params.getDataInicio().plusMonths(i))
                    .build()
            );

            i++;
        }

        return parcelas;
    }

}
